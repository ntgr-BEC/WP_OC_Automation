package webportal.ScheduledReboot.OneSwitchAnd3AP.PRJCBUGEN_T40574;

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

    String Name = "Schedule12";

    
    @Feature("ScheduledReboot") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T40574") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify whether device which are removed from a location where it has been configured with Scheduled reboot is not going for reboot") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T40574") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.openOrg(WebportalParam.Organizations);
        new OrganizationPage().gotoSchedulereboot();
        new OrganizationPage(false).DeleteSchedule(Name);
        
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        new MyCommonAPIs().gotoLoction(WebportalParam.location1);
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ap1serialNo);
        devInfo.put("Device Name", WebportalParam.ap1deveiceName);
        devInfo.put("MAC Address", WebportalParam.ap1macaddress);
        new DevicesDashPage().addNewDevice(devInfo);
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        new MyCommonAPIs().gotoLoction(WebportalParam.location1);
        Map<String, String> devInfo1 = new HashMap<String, String>();
        devInfo1.put("Serial Number", WebportalParam.sw1serialNo);
        devInfo1.put("Device Name", WebportalParam.sw1deveiceName);
        devInfo1.put("MAC Address", WebportalParam.sw1MacAddress);
        new DevicesDashPage().addNewDevice(devInfo1);
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
        handle.gotoLoction();
        
    }

    @Step("Test Step 2: go to Organization and schedule Reboot for all devices;")
    public void step2() throws ParseException {
        
       new OrganizationPage().gotoSchedulereboot();
       new RunCommand().enableSSH4APALL(WebportalParam.ap1IPaddress);
       new RunCommand().enableSSH4APALL(WebportalParam.ap2IPaddress);
       new RunCommand().enableSSH4APALL(WebportalParam.ap3IPaddress);
       assertTrue(new OrganizationPage(false).PickDateTimeForAllDevices(Name, "None"), "Warnning pop up did not appear");   
       
    }
    
    @Step("Test Step 3: Now delete one AP and one switch from account;")
    public void step3() {
        
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        new MyCommonAPIs().gotoLoction(WebportalParam.location1);
        new WirelessQuickViewPage().deleteDeviceYes(WebportalParam.ap1serialNo);
        assertTrue(!new WirelessQuickViewPage().checkApIsExist(WebportalParam.ap1serialNo),"Device delete unsuccessful");
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        new MyCommonAPIs().gotoLoction(WebportalParam.location1);
        new DevicesDashPage().deleteSwitch(WebportalParam.sw1serialNo);
        assertTrue(!new WirelessQuickViewPage().checkApIsExist(WebportalParam.sw1serialNo),"Device delete unsuccessful");
        
    }
    
    @Step("Test Step 4: Now try to launch local GUI of both devices; iuf they are not rebooting then it should login to local GUI;")
    public void step4() {
        
        UserManage userManage = new UserManage();
        userManage.logout();
        assertTrue(new HamburgerMenuPage(false).verifyLocalGUIofAP(), "AP1 is went for rebooting");
        assertTrue(new HamburgerMenuPage(false).verifyLocalGUIofSwitch(), "SW1 is went for rebooting");
        
    }    
}
