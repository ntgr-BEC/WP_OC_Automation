package webportal.ScheduledReboot.OneSwitchAnd3AP.PRJCBUGEN_T40576;

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

    String Name = "Schedule3";
    int upTimeBefore;
    int upTimeBefore1;
    int upTimeBefore2;
    int upTimeBefore3;
    int upTimeAfter; 
    int upTimeAfter1; 
    int upTimeAfter2; 
    int upTimeAfter3; 
    
    @Feature("ScheduledReboot") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T40576") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test whether status of device scheduled reboot is displayed properly under organisation --> Setting --> Devices --> Device Reboot") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T40576") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        
        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.openOrg(WebportalParam.Organizations);
        new OrganizationPage().gotoSchedulereboot();
        new OrganizationPage(false).DeleteSchedule(Name);
        System.out.println("start to do tearDown");
        
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.openOrg(WebportalParam.Organizations);
        handle.gotoLoction();
        
    }

    @Step("Test Step 2: go to Organization and schedule Reboot for all devices;")
    public void step2() throws ParseException {
       upTimeBefore = new WirelessQuickViewPage().getApUptime(WebportalParam.ap1IPaddress);
       upTimeBefore1 = new WirelessQuickViewPage().getApUptime(WebportalParam.ap2IPaddress);
       upTimeBefore2 = new WirelessQuickViewPage().getApUptime(WebportalParam.ap3IPaddress);
       upTimeBefore3 = new WirelessQuickViewPage().getApUptime(WebportalParam.sw1serialNo);
       new OrganizationPage().gotoSchedulereboot();
       new RunCommand().enableSSH4APALL(WebportalParam.ap1IPaddress);
       new RunCommand().enableSSH4APALL(WebportalParam.ap2IPaddress);
       new RunCommand().enableSSH4APALL(WebportalParam.ap3IPaddress);
       assertTrue(new OrganizationPage(false).PickDateTimeForAllDevices1(Name, "None"), "Warnning pop up did not appear");   
       
    }
    
    @Step("Test Step 3: Now verify all devices went for reboot or not;")
    public void step3() {
        
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        new MyCommonAPIs().gotoLoction(WebportalParam.location1);
        
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap2serialNo);
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap3serialNo);
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.sw1serialNo);
       
        upTimeAfter = new WirelessQuickViewPage().getApUptime(WebportalParam.ap1IPaddress);
        upTimeAfter = new WirelessQuickViewPage().getApUptime(WebportalParam.ap2IPaddress);
        upTimeAfter = new WirelessQuickViewPage().getApUptime(WebportalParam.ap3IPaddress);
        upTimeAfter = new WirelessQuickViewPage().getApUptime(WebportalParam.sw1IPaddress);
        
        
        assertTrue(upTimeAfter < upTimeBefore, "Reboot AP device failed.");
        
//        assertTrue(new DevicesDashPage().isAllDeviceRebooting(WebportalParam.ap1serialNo, WebportalParam.ap2serialNo, WebportalParam.ap3serialNo, WebportalParam.sw1serialNo), "Device rebooting Status is not received");
  
    }
  
}
