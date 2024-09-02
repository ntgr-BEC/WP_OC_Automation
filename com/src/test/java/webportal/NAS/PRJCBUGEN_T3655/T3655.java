package webportal.NAS.PRJCBUGEN_T3655;

import static com.codeborne.selenide.Selenide.sleep;
import static org.testng.AssertJUnit.assertTrue;

import org.testng.annotations.Test;

import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPageNas;
import webportal.weboperation.DevicesDashPageNas;
import webportal.weboperation.DevicesNasdiagnosticModePage;
import webportal.weboperation.WebportalLoginPage;

public class T3655 extends TestCaseBase implements Config{
	@Test(alwaysRun = true, groups = "p2")
    public void test(){
		step1();
		step2();
		step3();
		step4();
		step5();

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
		 DevicesNasdiagnosticModePage devicesNasdiagnosticModePage = new DevicesNasdiagnosticModePage();
		 String asttr=devicesNasdiagnosticModePage.confirmAttr();
		 boolean exist = asttr.contains(portnumberstatus);
		 assertTrue(exist);
	}
	public void step4(){
		sleep(4*1000);
		DevicesNasdiagnosticModePage devicesNasdiagnosticModePage= new DevicesNasdiagnosticModePage();
		devicesNasdiagnosticModePage.defaultSetting();
	}
	public void step5(){
		DevicesNasdiagnosticModePage devicesNasdiagnosticModePage = new DevicesNasdiagnosticModePage();
		devicesNasdiagnosticModePage.share();
		MyCommonAPIs.waitReady();
	}
	
}