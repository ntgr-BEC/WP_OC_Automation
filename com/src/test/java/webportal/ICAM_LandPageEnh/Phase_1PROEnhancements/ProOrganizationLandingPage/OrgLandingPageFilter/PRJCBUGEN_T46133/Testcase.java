package webportal.ICAM_LandPageEnh.Phase_1PROEnhancements.ProOrganizationLandingPage.OrgLandingPageFilter.PRJCBUGEN_T46133;

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

    Map<String, String> locationInfo = new HashMap<String, String>();

    @Feature("OrgLandingPageFilter") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T46133") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify Filter by \"Device Credit Status\"") // It's                                                                                                                                                     // Jira
    // Test
    // Case.
    @TmsLink("PRJCBUGEN_T46133") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        new OrganizationPage().deleteOrganizationNew("Netgear1");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to pro account success.")
    public void step1() {

        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);        
           
    }
    
    @Step("Test Step 2: Create new Organization and location onboard dummy devices")
    public void step2() {

        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", "Netgear1");
        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.addOrganization(organizationInfo);
        OrganizationPage.creditAllocation("Netgear1");
        MyCommonAPIs.sleepi(5);

        new OrganizationPage(false).openOrg("Netgear1");
        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", WebportalParam.location1);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "70184");
        locationInfo.put("Country", "Germany");
        new AccountPage(false).addNetwork(locationInfo);
        MyCommonAPIs.waitReady();

        new OrganizationPage(false).openOrg("Netgear1");
        new MyCommonAPIs().getLocationCurrentLocation(WebportalParam.location1);
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ap5serialNo);
        devInfo.put("Device Name", WebportalParam.ap5deveiceName);
        devInfo.put("MAC Address", WebportalParam.ap5macaddress);
        new DevicesDashPage().addNewDevice(devInfo);

        new OrganizationPage(false).openOrg("Netgear1");
        new MyCommonAPIs().getLocationCurrentLocation(WebportalParam.location1);
        Map<String, String> devInfo1 = new HashMap<String, String>();
        devInfo1.put("Serial Number", WebportalParam.sw2serialNo);
        devInfo1.put("Device Name", WebportalParam.sw2deveiceName);
        devInfo1.put("MAC Address", WebportalParam.sw2MacAddress);
        new DevicesDashPage().addNewDevice(devInfo1);

        new OrganizationPage(false).openOrg("Netgear1");
        new MyCommonAPIs().getLocationCurrentLocation(WebportalParam.location1);
        Map<String, String> devInfo2 = new HashMap<String, String>();
        devInfo2.put("Serial Number", WebportalParam.ob2serialNo);
        devInfo2.put("Device Name", WebportalParam.ob2deveiceName);
        devInfo2.put("MAC Address", WebportalParam.ob2MAC_Address);
        new DevicesDashPage().addNewDevice(devInfo2);

    }
        
      @Step("Test Step 3: Verify On Organization dashboard Credits Filter Devices option ")
      public void step3() {
          
          new OrganizationPage(false).gotoPage();
          MyCommonAPIs.sleepi(5);
          assertTrue(new OrganizationPage(false).verifyCreditsFilterOptionsFunctionality("Netgear","Netgear1"),"On Organization dashboard Credits Filter devices options are not working");

      }
 
}
