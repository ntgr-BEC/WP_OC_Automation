package webportal.ScreenNavigationProAcct.PRJCBUGEN_T34402;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.APUtils;
import util.MyCommonAPIs;
import util.RunCommand;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Anusha H
 *
 */
public class Testcase extends TestCaseBase {
    String organizationName = "organization";
    String locationName     = "office1";
    Map<String, String> organizationInfo = new HashMap<String, String>();
    OrganizationPage OrganizationPage = new OrganizationPage();

    @Feature("Screen Navigation ProAcct IM-7.0") // It's a folder/component name to make test suite more readable from Jira
                                                                      // Test Case.
    @Story("PRJCBUGEN_T34402") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("IM-7.0-Verify Device Reboot Settings page navigation in the pro Account") // It's
                                                                                                                             // a
                                                                                                                             // testcase
                                                                                                                             // title
                                                                                                                             // from
                                                                                                                             // Jira
    // Test Case.
    @TmsLink("PRJCBUGEN-T34402") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p3") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        OrganizationPage page = new OrganizationPage();
        page.deleteOrganizationNew(organizationName);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login With Pro Account IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
    }

    @Step("Test Step 2: Create a organization1 and a location")
    public void step2() {
        
        organizationInfo.put("Name", organizationName);  
        OrganizationPage.addOrganization(organizationInfo);
        OrganizationPage.openOrg(organizationName);
        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", locationName);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "United States of America");
        new AccountPage(false).addNetwork(locationInfo);
        MyCommonAPIs.sleepi(5);
    }
    
    @Step("Test Step 3:Onboard a dummy device")
    public void step3() {
        Map<String, String> firststdevInfo = new HashMap<String, String>();
        firststdevInfo.put("Serial Number1", WebportalParam.ap5serialNo);
        firststdevInfo.put("MAC Address1", WebportalParam.ap5macaddress);
        new DevicesDashPage().addNewdummyDeviceProAccount(firststdevInfo);
    }
    
    @Step("Test Step 4: Verify the Device Reboot page")
    public void step4() {
        OrganizationPage.openOrg(organizationName);
        assertTrue(new OrganizationPage(false).VerifyDeviceRebootPage(WebportalParam.ap5serialNo), "Device Reboot data is showing wrong");
    }
}
    