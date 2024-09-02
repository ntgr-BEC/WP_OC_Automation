package webportal.NAS.PRJCBUGEN_T3643;
import static com.codeborne.selenide.Selenide.sleep;
import static org.testng.AssertJUnit.assertTrue;

import org.testng.annotations.Test;

import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPageNas;
import webportal.weboperation.DevicesDashPageNas;
import webportal.weboperation.DevicesNasIpSettingsPage;
import webportal.weboperation.WebportalLoginPage;

public class T3643 extends TestCaseBase implements webportal.NAS.PRJCBUGEN_T3643.Config {
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
	    AccountPageNas accountPage = new AccountPageNas();
	    accountPage.enterLocation(WebportalParam.location1);
		
	}
	public void step2(){
		DevicesDashPageNas devicesDashPage = new DevicesDashPageNas();
		devicesDashPage.enterDevicesNasSummary(SerialNo);
		sleep(3*1000);
	}
	public void step3(){
		DevicesNasIpSettingsPage devicesNasIpSettingsPage = new DevicesNasIpSettingsPage();
		String text1 = devicesNasIpSettingsPage.getipaddress();
		boolean exist1 = text1.contains(ipvalue);
		assertTrue(exist1);
		String text2 = devicesNasIpSettingsPage.getsubnet();
		boolean exist2 = text2.contains(subnetvalue);
		assertTrue(exist2);
		String text3 = devicesNasIpSettingsPage.getgetaway();
		boolean exist3 = text3.contains(getawayvalue);
		assertTrue(exist3);
		String text4 = devicesNasIpSettingsPage.getdns();
		boolean exist4=text4.contains(dnsvalue);
		assertTrue(exist4);
		
		
		
            
}
	}
