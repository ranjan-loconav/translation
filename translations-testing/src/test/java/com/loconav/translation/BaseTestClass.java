package com.loconav.translation;

import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import com.loconav.utils.BrowserUtils;

public class BaseTestClass {
	
	@BeforeSuite
	public void beforeAll() throws Exception {
		String browserSelected=System.getProperty("browser") != null?System.getProperty("browser").toLowerCase():"chrome";
		Assert.assertTrue(InitPage.createSessionFile(browserSelected));
	}
	
	@AfterSuite
	public void afterAll() {
		new BrowserUtils().cleanUpSessionFiles();
	}
	
	
}
