package webportal.NAS.PRJCBUGEN_T3631;

import static com.codeborne.selenide.Selenide.sleep;

import org.testng.annotations.Test;

import testbase.TestCaseBase;
import webportal.weboperation.AccountPageNas;
import webportal.weboperation.NetworkEditNetworkPage;
import webportal.weboperation.WebportalLoginPage;

public class T3631Update extends TestCaseBase implements Config {
	@Test(alwaysRun = true, groups = "p2")
	public void test(){
			step1();
			step2();
			step3();
			
			
		}
	public void step1(){
		WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
	    webportalLoginPage.defaultLogin();
	    sleep(5*1000);
	    
		
	}
	public void step2(){
		AccountPageNas accountPage = new AccountPageNas();
		accountPage.enterEditNetworkPage();
		NetworkEditNetworkPage networkEditNetworkPage=new NetworkEditNetworkPage();
		networkEditNetworkPage.modifyLocationName(newpassword);
	}
	public void step3(){
		AccountPageNas accountPage = new AccountPageNas();
		accountPage.enterEditNetworkPage();
		NetworkEditNetworkPage networkEditNetworkPage=new NetworkEditNetworkPage();
		networkEditNetworkPage.modifyLocationName(oldpassword);
		
            
}
	}


