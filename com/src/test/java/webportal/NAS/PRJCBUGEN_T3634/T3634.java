package webportal.NAS.PRJCBUGEN_T3634;

import static com.codeborne.selenide.Selenide.sleep;

import org.testng.annotations.Test;

import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPageNas;
import webportal.weboperation.DashboardLocationPage;
import webportal.weboperation.WebportalLoginPage;

public class T3634 extends TestCaseBase implements Config {
	@Test(alwaysRun = true, groups = "p2")
	public void test(){
			step1();
			step2();
		
			
			
		}
	public void step1(){
		WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
	    webportalLoginPage.defaultLogin();
	    sleep(5*1000);
	    AccountPageNas accountPage = new AccountPageNas();
		accountPage.enterLocation(WebportalParam.location1);
		sleep(5000);
	    
		
	}
	public void step2(){
		MyCommonAPIs.waitReady();
		DashboardLocationPage dashboardLocationPage =new DashboardLocationPage();
		dashboardLocationPage.refresh();		
	}
		
	
	}


