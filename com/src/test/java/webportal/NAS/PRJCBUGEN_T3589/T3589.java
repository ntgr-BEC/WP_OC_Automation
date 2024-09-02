package webportal.NAS.PRJCBUGEN_T3589;

import static com.codeborne.selenide.Selenide.sleep;

import org.testng.annotations.Test;

import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPageNas;
import webportal.weboperation.DevicesDashPageNas;
import webportal.weboperation.DevicesNasSummaryPage;
import webportal.weboperation.WebportalLoginPage;

public class T3589 extends TestCaseBase implements Config {
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
		DevicesDashPageNas devicesDashPage = new DevicesDashPageNas();
		devicesDashPage.enterDevicesNasSummary(SerialNo);
		sleep(5*1000);	
	}	
	public void step3(){
		sleep(10*1000);
		DevicesNasSummaryPage devicesNasSummaryPage = new DevicesNasSummaryPage();
		devicesNasSummaryPage.nextUsbPage();
		sleep(5*1000);
	}
	}


