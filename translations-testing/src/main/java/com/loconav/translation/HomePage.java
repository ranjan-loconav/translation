package com.loconav.translation;

import java.util.Properties;
import com.loconav.datahelpers.PropertiesReader;
import com.loconav.datahelpers.RepoReader;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

/**
 * @author shrinivasbagewadi
 *
 */
public class HomePage extends InitPage {
	
	RepoReader homePageRepo;

	public HomePage(Page page) throws Exception {
		super(page);
		homePageRepo = new RepoReader();
	}

	public HomePage waitTillHomePageLoads() {
		playwrightUtils.waitForLoadState(LoadState.DOMCONTENTLOADED);
		return this;
	}

	public MyDriversPage NavigateToDriversPage() throws Exception {
		Properties props = new PropertiesReader().readPropertyFile();
		boolean mnuOpened = new HomePage(page).clickMenu("Drivers", "My Drivers");
		if(mnuOpened==true) {
			playwrightUtils.navigate("https://staging01.loconav.com/v2/drivers?locale=" + props.getProperty("prefix"))
			   			   .waitForLoadState(LoadState.DOMCONTENTLOADED);			
			return new MyDriversPage(page);
		}
		return null;
	}	
	
	public Boolean clickMenu(String mainMenu, String subMenu) throws Exception {
		String mnuSideBar = homePageRepo.getSelectorFor("mnuSideBar");
		String mnuMainMenu = homePageRepo.getSelectorFor("mnuMainMenu").replace("MAINMENU", mainMenu);
		String mnuSubMenu = homePageRepo.getSelectorFor("mnuSubMenu").replace("SUBMENU", subMenu);
		playwrightUtils.hover(mnuSideBar);
		playwrightUtils.click(mnuMainMenu)
		   			   .click(mnuSubMenu)
		   			   .waitForLoadState(LoadState.DOMCONTENTLOADED);
		return true;
	}

}
