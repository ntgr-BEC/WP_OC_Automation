package webportal.MoveDevice.ProCases.PRJCBUGEN_T35187;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.APUtils;
import util.MyCommonAPIs;
import util.RunCommand;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Pratik
 *
 */
public class Testcase extends TestCaseBase {


    Random r = new Random();
    String organizationName = "Netgear1";
    String locationName     = "office";
    Map<String, String> locationInfo = new HashMap<String, String>();

    @Feature("MoveDevice") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T35187") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify when the user moved the device then in the older location no stats are present for the moved device.") // It's                                                                                                                                                     // Jira
    // Test
    // Case.
    @TmsLink("PRJCBUGEN_T35187") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new OrganizationPage(false).openOrg(organizationName);
        new DevicesDashPage(false).openLocationFromotherOrg();
        assertTrue(new DevicesDashPage().moveDevicetoOrg1orloc1AndVerify(WebportalParam.ap1serialNo),"Move device functionality is failed.");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to pro account success.")
    public void step1() {

        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        new MyCommonAPIs().gotoLoction(WebportalParam.location1);
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
           
    }
        
      @Step("Test Step 2: Move deice to another organization")

      public void step2() {
          
          assertTrue(new DevicesDashPage().moveDeviceAndVerify(WebportalParam.ap1serialNo),"Move device functionality is failed.");
          new OrganizationPage(false).openOrg(organizationName);
          new DevicesDashPage(false).openLocationFromotherOrg();
          assertTrue(new DevicesDashPage().isDeviceRebooting(WebportalParam.ap1serialNo), "Device rebooting Status is not received");
          assertTrue(new DevicesDashPage().isDeviceResetConfiguration(WebportalParam.ap1serialNo), "New configuration is not applied to Device");
          
          new OrganizationPage(false).openOrg(WebportalParam.Organizations);
          new MyCommonAPIs().gotoLoction(WebportalParam.location1);
          assertTrue(new DevicesDashPage().noDevicesAvailableonoffice1Location(), "Same deive present is previous location");
          

      }
      
}
