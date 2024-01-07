package com.loconav.translation;

import com.loconav.utils.PlaywrightUtils;
import com.microsoft.playwright.Page;

public class Vehicles extends InitPage {

	public Vehicles(Page page) throws Exception {
		super(page);
	}
	
	public Vehicles searchVehicles() throws Exception {
		playwrightUtils.fill("", "demo123")
				       .click("");
		return this;
	}
	
	public AlertsPage navigateToAlerts() throws Exception {
		playwrightUtils.click(null);
		return new AlertsPage(page);
	}

}
