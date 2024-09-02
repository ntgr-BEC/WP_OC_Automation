package webportal.NAS.PRJCBUGEN_T5964;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;

import org.testng.annotations.Test;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;

import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPageNas;
import webportal.weboperation.DevicesDashPageNas;
import webportal.weboperation.DevicesNasAntivirusPage;
import webportal.weboperation.WebportalLoginPage;


public class T5964 extends TestCaseBase implements Config{
	@Test(alwaysRun = true, groups = "p2")
	public void test(){
		step1();
		step2();
		step3();
		step4();
		step5();
		step6();
		step7();
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
		sleep(5*1000);
		
	}
	public void step3(){
		DevicesNasAntivirusPage devicesNasAntivirusPage = new DevicesNasAntivirusPage();
		sleep(10000);
		String text = devicesNasAntivirusPage.getAntivirusStatus();
		assertEquals(antivirusoff, text);
	}
	public void step4(){
		Selenide.sleep(2000);
		Selenide.open(hostnameurl, "", username, password);
		Selenide.sleep(10000);
		$(Selectors.byXpath("//span[text()='Disabled']")).click();
		$(Selectors.byXpath("//label[text()='Enable real-time antivirus scanning']")).click();
		$(Selectors.byCssSelector("button[title='Apply']")).click();
		sleep(5000);
	}
	public void step5(){
		DevicesNasAntivirusPage devicesNasAntivirusPage = new DevicesNasAntivirusPage();
		sleep(10000);
		String text = devicesNasAntivirusPage.getAntivirusStatus();
		boolean condition1 = text.contains(antivirusoff);
		assertFalse(condition1);
	}
	public void step6(){
		Selenide.sleep(2000);
		Selenide.open(hostnameurl, "", username, password);
		Selenide.sleep(10000);
		$(Selectors.byCssSelector(".x-system-panel-hware-devtable>table>tbody>tr>td>table>tbody>tr>td>span>span>span")).click();
		sleep(2000);
		$(Selectors.byXpath("//label[text()='Enable real-time antivirus scanning']")).click();
		$(Selectors.byCssSelector("button[title='Apply']")).click();
		sleep(5000);
}
	public void step7(){
		DevicesNasAntivirusPage devicesNasAntivirusPage = new DevicesNasAntivirusPage();
		String text = devicesNasAntivirusPage.getAntivirusStatus();
		assertEquals(antivirusoff, text);	
	}
	}