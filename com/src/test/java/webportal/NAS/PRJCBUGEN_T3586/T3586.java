package webportal.NAS.PRJCBUGEN_T3586;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;
import static org.testng.AssertJUnit.assertEquals;

import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;

import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPageNas;
import webportal.weboperation.DevicesDashPageNas;
import webportal.weboperation.WebportalLoginPage;


public class T3586 extends TestCaseBase implements Config{
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
		Selenide.refresh();
		DevicesDashPageNas devicesDashPage = new DevicesDashPageNas();
		devicesDashPage.deleteDeviceYes(webportalParam.nas1SerialNo);
		sleep(3*1000);	
	}
	public void step3(){
		Selenide.refresh();
		DevicesDashPageNas devicesDashPage = new DevicesDashPageNas();
		String string = devicesDashPage.confirmNoDevice();
		assertEquals(nodeviceinfo,string);	
		}
	public void step4(){
		Selenide.sleep(2000);
		Selenide.open(webportalParam.nas1UrlAddress, "", webportalParam.nas1Username, webportalParam.nas1Password);
		Selenide.sleep(5000);
		$("#cloud-tab").click();
		Selenide.sleep(5000);
		$(Selectors.byText(webportalParam.loginName)).isDisplayed();	
		sleep(3000);
		$("#cloud-tab").click();
		Selenide.sleep(5000);
		$(".x-cloud-panel > div > div > div > div > div > div > table > tbody > tr > td[class='ui-buttononoff-small-btn']").click();
		Selenide.sleep(5000);
		$(".x-cloud-settings-rdcontent > tbody > tr > td > div > div > div > div > input").setValue(WebportalParam.loginName);
		$(".x-cloud-settings-rdcontent > tbody > tr > td > div > div > div > div > input[type='password']").setValue(WebportalParam.loginPassword);
		Selenide.actions().sendKeys(Keys.ENTER).build().perform();
		Selenide.sleep(10000);
	}
	public void step5(){
		Selenide.refresh();
		DevicesDashPageNas devicesDashPage = new DevicesDashPageNas();
		devicesDashPage.addNewDevice(DEVICE_INFO);
		sleep(10*1000);
		Selenide.refresh();
}
	}