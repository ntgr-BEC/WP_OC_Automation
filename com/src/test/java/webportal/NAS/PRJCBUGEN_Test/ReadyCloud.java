package webportal.NAS.PRJCBUGEN_Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;

import testbase.TestCaseBase;
import webportal.param.WebportalParam;


public class ReadyCloud extends TestCaseBase implements Config{
	@Test(alwaysRun = true, groups = "p5")
	public void test(){
		// step1();
		//step2();
		//step3();
		step4();
		//step5();

}
//	public void step1(){
//		
//		
//	}
//	public void step2(){
//		Selenide.refresh();
//		DevicesDashPage devicesDashPage = new DevicesDashPage();
//		devicesDashPage.deleteDeviceYes(SerialNo);
//		sleep(3*1000);
//		
//		
//	}
//	public void step3(){
//		Selenide.refresh();
//		DevicesDashPage devicesDashPage = new DevicesDashPage();
//		String string = devicesDashPage.confirmNoDevice();
//		assertEquals(nodeviceinfo,string);
//		
//	}
	public void step4(){
//		Selenide.sleep(2000);
		Selenide.open(hostnameurl, "", username, password);
		Selenide.sleep(5000);
		$("#cloud-tab").click();
		Selenide.sleep(5000);
		$(Selectors.byText("jietest3@163.com")).isDisplayed();	
		sleep(3000);
		$("#cloud-tab").click();
		Selenide.sleep(5000);
		$(".x-cloud-panel > div > div > div > div > div > div > table > tbody > tr > td[class='ui-buttononoff-small-btn']").click();
		Selenide.sleep(5000);
		$(".x-cloud-settings-rdcontent > tbody > tr > td > div > div > div > div > input").setValue(WebportalParam.loginName);
		$(".x-cloud-settings-rdcontent > tbody > tr > td > div > div > div > div > input[type='password']").setValue(WebportalParam.loginPassword);
		Selenide.actions().sendKeys(Keys.ENTER).build().perform();
		Selenide.sleep(10000);
//		Selenide.close();
	}
//	public void step5(){
//		WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
//	    webportalLoginPage.defaultLogin();
//	    sleep(5*1000);
//	    AccountPage accountPage = new AccountPage();
//	    accountPage.enterLocation(WebportalParam.location1);
//		Selenide.refresh();
//		DevicesDashPage devicesDashPage = new DevicesDashPage();
//		devicesDashPage.addNewDevice(DEVICE_INFO);
//		sleep(10*1000);
//		Selenide.refresh();
//}
	}