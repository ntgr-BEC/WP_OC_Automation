package webportal.ConfigBackupAndRestore.Pro.PRJCBUGEN_T10380;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.CopyConfigurationPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    Map<String, String> devInfo          = new HashMap<String, String>();
    String              organizationName = "test10380";
    String              locationName     = "ntk10380";
    String              lagName          = "lag10380";

    @Feature("ConfigBackupAndRestore.Pro") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T10380") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify the location configuration-LAG configuration getting copied on copy configuration") // It's a test case title from Jira
                                                                                                             // Test Case.
    @TmsLink("PRJCBUGEN-T10380") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new OrganizationPage().deleteOrganizationNew(organizationName);

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wlp.gotoLagPage();
        wlp.deleteLag();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);

        handle.gotoLoction();
        new DevicesDashPage().checkDutInNormalAccount("admin", WebportalParam.sw1serialNo, WebportalParam.sw1deveiceName, WebportalParam.sw1MacAddress);
    }

    @Step("Test Step 2: Create new organization and add one lag;")
    public void step2() {
        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName);

        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.addOrganization(organizationInfo);
        OrganizationPage.addAllocateCredits(organizationName, "1");
        OrganizationPage.openOrg(organizationName);

        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", locationName);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "China");
        new AccountPage(false).addNetwork(locationInfo);

        // OrganizationPage.openOrg(organizationName);
        new AccountPage(false).enterLocation(locationName);

        devInfo.put("Serial Number", WebportalParam.sw2serialNo);
        devInfo.put("Device Name", WebportalParam.sw2deveiceName);
        new DevicesDashPage().addNewDevice(devInfo);

        if (!new DevicesDashPage().waitDevicesReConnected(WebportalParam.sw2serialNo)) {
            assertTrue(false, "Need add one switch to new location.");
        }

        new OrganizationPage().openOrg(WebportalParam.Organizations);
        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wlp.gotoLagPage();
        wlp.addLag(lagName, WebportalParam.sw1LagPort1);
    }

    @Step("Test Step 3: Copy configuration to new location;")
    public void step3() {
        new CopyConfigurationPage().copyConfigurationToTarget(organizationName);
        assertTrue(new CopyConfigurationPage(false).checkSuccessfulMessage(), "Copy configuration failed.");
    }

    @Step("Test Step 4: Check copy configuration result;")
    public void step4() {
        MyCommonAPIs.sleepi(3 * 60);
        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.openOrg(organizationName);
        new AccountPage(false).enterLocation(locationName);
        handle.gotoLocationWireSettings();

        wlp.gotoLagPage();
        assertTrue(wlp.checkLagIsExist(lagName), "Copy configuration failed.");
    }

}
