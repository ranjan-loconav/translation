package com.loconav.translation;

import com.loconav.datahelpers.RepoReader;
import com.microsoft.playwright.Page;

/**
 * @author shrinivasbagewadi
 *
 */
public class LoginPage extends InitPage {

	public RepoReader loginRepo;
	
	public LoginPage(Page page) throws Exception {
		super(page);
		loginRepo = new RepoReader();
	}

}
