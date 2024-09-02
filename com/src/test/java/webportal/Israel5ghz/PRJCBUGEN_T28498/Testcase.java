package webportal.Israel5ghz.PRJCBUGEN_T28498;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.commands.IsDisplayed;

import util.MyCommonAPIs;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.APUtils;
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.Israel5ghz;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    
    String cmd = "iwconfig wifi0vap0 | grep Access";

    @Feature("Israel5ghz") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T28498") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Instant Wifi  for  Israel  for wax630") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T28498") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {     
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "OnBoardingTest");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "L6A");
        locationInfo.put("Country", "Canada");
        new AccountPage().addNetwork(locationInfo);
        new AccountPage(false).enterLocation(WebportalParam.location1);

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccount();
        
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ap1serialNo);
        devInfo.put("Device Name", WebportalParam.ap1deveiceName);
        devInfo.put("MAC Address", WebportalParam.ap1macaddress);

        new DevicesDashPage(false).addNewDevice(devInfo);
    }

    
    @Step("Test Step 2: Go to Radio and Channel")
    public void step2() throws InterruptedException {
        
          new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap1serialNo); 
          new WirelessQuickViewPage(false).Isrelchannel();   
    }
    
    

}
