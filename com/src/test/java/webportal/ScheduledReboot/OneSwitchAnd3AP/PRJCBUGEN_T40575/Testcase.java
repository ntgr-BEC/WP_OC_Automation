package webportal.ScheduledReboot.OneSwitchAnd3AP.PRJCBUGEN_T40575;

import static org.testng.Assert.assertTrue;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

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
import util.MyCommonAPIs;
import util.RunCommand;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.weboperation.DevicesApSummaryPage;
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

    String Name = "Schedule2";

    
    @Feature("ScheduledReboot") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T40575") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify whether devices which are moved from one location to another location can be configured with Scheduled reboot") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T40575") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        new MyCommonAPIs().gotoLoction(WebportalParam.location2);
        assertTrue(new DevicesDashPage().moveDevicetoOrg1orloc1AndVerify(WebportalParam.ap1serialNo),"Move device functionality is failed.");
        assertTrue(new DevicesDashPage().moveDevicetoOrg1orloc1AndVerify(WebportalParam.sw1serialNo),"Move device functionality is failed.");
        
        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.openOrg(WebportalParam.Organizations);
        new OrganizationPage().gotoSchedulereboot();
        new OrganizationPage(false).DeleteSchedule(Name);
        
        OrganizationPage.openOrg(WebportalParam.Organizations);
        new MyCommonAPIs().gotoLoction(WebportalParam.location1);
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.sw1serialNo);
        System.out.println("start to do tearDown");
        
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.openOrg(WebportalParam.Organizations);
        new MyCommonAPIs().gotoLoction(WebportalParam.location1);
        
    }

    @Step("Test Step 2: go to Organization and schedule Reboot for all devices;")
    public void step2() {
       
       assertTrue(new DevicesDashPage().moveDevicewithinSameOrgAndVerify(WebportalParam.ap1serialNo),"Move device functionality is failed.");
       assertTrue(new DevicesDashPage().moveDevicewithinSameOrgAndVerify(WebportalParam.sw1serialNo),"Move device functionality is failed.");
       new OrganizationPage(false).openOrg(WebportalParam.Organizations);
       new MyCommonAPIs().gotoLoction(WebportalParam.location2);
       new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
       new DevicesDashPage().waitDevicesReConnected(WebportalParam.sw1serialNo);
       MyCommonAPIs.sleepi(10);
       new RunCommand().enableSSH4APALL(WebportalParam.ap1IPaddress);
       new RunCommand().enableSSH4APALL(WebportalParam.ap2IPaddress);
       new RunCommand().enableSSH4APALL(WebportalParam.ap3IPaddress);
       
    }
    
    @Step("Test Step 3: Now delete one AP and one switch from account;")
    public void step3() throws ParseException {
        
        new OrganizationPage().gotoSchedulereboot();
        assertTrue(new OrganizationPage(false).PickDateTimeForLocation2MovedDevices(Name, WebportalParam.ap1serialNo, WebportalParam.sw1serialNo, "None"), "Warnning pop up did not appear");
        
    }
   
}
