package com.loconav.translation;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import org.assertj.core.api.SoftAssertions;
import com.loconav.datahelpers.PropertiesReader;
import com.loconav.datahelpers.RepoReader;
import com.loconav.namedexception.CustomException;
import com.loconav.utils.BrowserUtils;
import com.loconav.utils.PlaywrightUtils;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

/**
 * @author shrinivasbagewadi
 *
 */
@SuppressWarnings("unchecked")
public class InitPage 
{
	public PlaywrightUtils playwrightUtils;
	public Page page;
	public RepoReader uiRepo;
	
	public InitPage(Page page) throws Exception {
		this.page=page;
		playwrightUtils= new PlaywrightUtils(page,60000);
		uiRepo = new RepoReader();
	}
	
    public HomePage landOnHomePage() throws Exception {
    	Properties props = new PropertiesReader().readPropertyFile();
    	playwrightUtils.navigate(props.getProperty("appurl"));
		return new HomePage(page);
    }
    
    public static Boolean createSessionFile(String browserName) throws Exception {
    	Page page=null;
    	try {
    	String userNameSelector = new RepoReader().getSelectorFor("username");
    	String passwordSelector = new RepoReader().getSelectorFor("password");
    	String loginBtnSelector = new RepoReader().getSelectorFor("loginbtn");
    	BrowserUtils browserUtils= new BrowserUtils();
		page=browserUtils.startHeadlessBrowser(browserName);
		Properties properties = new PropertiesReader().readPropertyFile();
		String url = properties.getProperty("appurl");
		String email = properties.getProperty("appemail");
		String password = properties.getProperty("apppassword");
		PlaywrightUtils playwrightUtils = new PlaywrightUtils(page,60000);
		if (!new File(System.getenv("user.dir") + File.separator + browserName.toLowerCase() + "auth.json").exists()) {
			playwrightUtils.navigate(url)
						   .fill(userNameSelector, email)
						   .fill(passwordSelector, password)
						   .click(loginBtnSelector)
						   .waitForLoadState(LoadState.DOMCONTENTLOADED,new Page.WaitForLoadStateOptions().setTimeout(60000))
						   .storageState(new BrowserContext.StorageStateOptions().setPath(Paths.get(
							System.getProperty("user.dir") + File.separator + browserName.toLowerCase() + "auth.json")))
						   .closeBrowser();
			browserUtils.playwright.close();
			return true;
		} else if (new File(System.getenv("user.dir") + File.separator + "auth.json").exists()) {
			new File(System.getenv("user.dir") + File.separator + "auth.json").delete();
			System.out.println("Session file was already persistant, No new file creation required.");
			playwrightUtils.navigate(url)
						   .fill(userNameSelector, email)
						   .fill(passwordSelector, password)
						   .click(loginBtnSelector)
						   .waitForLoadState(LoadState.DOMCONTENTLOADED,new Page.WaitForLoadStateOptions().setTimeout(60000))
						   .storageState(new BrowserContext.StorageStateOptions().setPath(Paths.get(
							System.getProperty("user.dir") + File.separator + browserName.toLowerCase() + "auth.json")))
						   .closeBrowser();
			browserUtils.playwright.close();
			return true;
		} else {
			return false;
		}
    	}
    	catch(Exception e) {
    		throw new CustomException(e,page);
    	}
	}
    
    public void verifyTranslations(Map<String,Object> repoReader) throws Exception {
    	SoftAssertions softAssertions =  new SoftAssertions();
    	InitPage initPage= new InitPage(page);
    	for(String str:repoReader.keySet()) {
			Object obj = initPage.evaluteLocatorsAndReturn(str, (Map<String,Object>)repoReader.get(str));
			if (obj instanceof String) {
				softAssertions.assertThat(obj.toString()).isEqualTo(((Map<String,Object>)repoReader.get(str)).values().toArray()[0].toString());
			}
			else if(obj instanceof ArrayList || obj instanceof List) {
				((List<String>) obj).removeAll(Arrays.asList(""));
				Map<String,Object> mp=(Map<String, Object>) repoReader.get(str);
				softAssertions.assertThat((ArrayList<String>)obj).isEqualTo((ArrayList<String>)mp.values().toArray()[0]);
			}
		}
    	softAssertions.assertAll();
    }
    
    public Object evaluteLocatorsAndReturn(String locator, Map<String,Object> translations) {
    	if(!translations.keySet().toArray()[0].toString().equalsIgnoreCase("innertext") && 
    			!translations.keySet().toArray()[0].toString().equalsIgnoreCase("innertexts")) {
    		return playwrightUtils.getAttribute(locator, translations.keySet().toArray()[0].toString()).trim();
    	}
    	if(translations.keySet().toArray()[0].toString().equalsIgnoreCase("innertext")) {
    		return playwrightUtils.innerText(locator).toString().trim();
    	}
    	if(translations.keySet().toArray()[0].toString().equalsIgnoreCase("innertexts")) {
    		return playwrightUtils.querySelectorAll(locator).stream().map(ElementHandle::innerText).map(String::trim).collect(Collectors.toList());
    	}
    	return null;
    }
    
    public void killBrowser() {
    	playwrightUtils.closeBrowser();
    }
    
     
}
