package com.loconav.translation;

import java.io.File;

import com.loconav.datahelpers.RepoReader;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * @author shrinivasbagewadi
 *
 */
public class MyDriversPage extends InitPage {
	
	RepoReader driversRepo;

	public MyDriversPage(Page page) throws Exception {
		super(page);
		driversRepo =  new RepoReader();
	}

	public MyDriversPage verifyElements() {
		return this;
	}

	public MyDriversPage clickAddDrivers() {
		String btnAddDriver = driversRepo.getSelectorFor("btnAddDriver");
		String cmbCountry = driversRepo.getSelectorFor("cmbCountry");
		String optCountry = driversRepo.getSelectorFor("optCountry");
		playwrightUtils.click(btnAddDriver);
//					   .click(cmbCountry)
//					   .click(optCountry.replace("COUNTRY", "India (+91)"))
//					   .uploadFile(() -> page.locator("//*[@name='profilePicture.image']//parent::label").click(), 
//						   System.getProperty("user.dir")+File.separator+"pom.xml");
		return this;
	}
	
	public MyDriversPage clickSaveDriver() {
		String btnSaveDriver = driversRepo.getSelectorFor("btnSaveDriver");
	    Locator btnSaveLoc = playwrightUtils.getElementWithSelector(btnSaveDriver);
	    btnSaveLoc.scrollIntoViewIfNeeded();
	    playwrightUtils.click(btnSaveDriver, new Page.ClickOptions().setForce(true));
		return this;
	}
	
}
