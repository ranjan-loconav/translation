package com.loconav.translation;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.Properties;

import org.testng.annotations.AfterMethod;
import com.loconav.datahelpers.PropertiesReader;
import com.loconav.datahelpers.RepoReader;
import com.loconav.utils.BrowserUtils;
import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;

public class DriversTranslationTests extends BaseTestClass {

	Properties props;
	InitPage initPage;
	HomePage homePage;
	MyDriversPage driversPage;
	Page page;
	
	@BeforeMethod(alwaysRun = true)
	public void setUp() throws Exception {
		props = new PropertiesReader().readPropertyFile();
		String browserSelected=System.getProperty("browser") != null?System.getProperty("browser").toLowerCase():"chrome";
		page = new BrowserUtils().startBrowserUsingSessionFile(browserSelected);
		initPage = new InitPage(page);
		homePage =initPage.landOnHomePage();
	}
	
	@Test
	public void verifyDriversPageTranslations() throws Exception {
		driversPage = homePage.NavigateToDriversPage();
		Map<String,Object> repoReader = new RepoReader().returnLangMap("DriversPage.json", "DriversPage");
		initPage.verifyTranslations(repoReader);
	}
	
	@Test
	public void verifyAddDriverModalTranslations() throws Exception {
		driversPage = homePage.NavigateToDriversPage();
		driversPage.clickAddDrivers();
		Map<String,Object> labelsAndButtons = new RepoReader().returnLangMap("DriversPage.json", "Add Driver labels buttons");
		initPage.verifyTranslations(labelsAndButtons);
		driversPage.clickSaveDriver();
		Map<String,Object> errormsgs = new RepoReader().returnLangMap("DriversPage.json", "Modal Errors");
		initPage.verifyTranslations(errormsgs);
	}
	@AfterMethod(alwaysRun = true)
	public void killBrowser() {
		initPage.killBrowser();
	}
	
	@Attachment(value="Page Screenshot for failed step", type="image/png")
	public void attachss(Page page) {
		Allure.addAttachment("Any text", new ByteArrayInputStream(page.screenshot()));
	}
	
}
