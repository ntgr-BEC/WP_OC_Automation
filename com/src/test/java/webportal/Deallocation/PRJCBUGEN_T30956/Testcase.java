package webportal.Deallocation.PRJCBUGEN_T30956;

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
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    OrganizationPage        OrganizationPage  = new OrganizationPage();
    String                  organizationName  = "Netgear";
    String                  organizationName1 = "PRJCBUGEN_T30956";
    String                  organizationName2 = "PRJCBUGEN_T30956a";
    HashMap<String, String> locationInfo      = new HashMap<String, String>();
    HashMap<String, String> locationInfo1     = new HashMap<String, String>();
    String                  locationName      = "PRJCBUGEN_T30956";
    String                  locationName1     = "PRJCBUGEN_T30956a";
    Map<String, String>     devInfo           = new HashMap<String, String>();

    @Feature("Deallocation") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T30956") // It's a testcase id/link from Jira Test Case but replace - wi_.
    @Description("Test to verify that user should able to add NHB device on new org after deleting from previous org along with deallocating all credit.") // It's
                                                                                                                                                           // a
                                                                                                                                                           // test
                                                                                                                                                           // case
                                                                                                                                                           // title
                                                                                                                                                           // from
                                                                                                                                                           // Jira
                                                                                                                                                           // Test
                                                                                                                                                           // Case.
    @TmsLink("PRJCBUGEN-T30956") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new OrganizationPage().deleteOrganizationNew(organizationName1);
        new OrganizationPage().deleteOrganizationNew(organizationName2);
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
    }

    @Step("Test Step 2: Create Orgnization and allocate credit")
    public void step3() {
        new OrganizationPage().deleteOrganizationNew(organizationName);
        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName1);

        OrganizationPage.addOrganization(organizationInfo);

        OrganizationPage.addAllocateCredits(organizationName1, "5", "0", "0");

    }

    @Step("Test Step 4: Add Dummy Device")
    public void step4() {

        OrganizationPage.openOrg(organizationName1);

        locationInfo.put("Location Name", locationName);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "China");
        new AccountPage(false).addNetwork(locationInfo);

        new AccountPage(false).enterLocation(locationInfo.get("Location Name"));

    }

    @Step("Test Step 5: allocate credit")
    public void step5() {

        devInfo.put("Serial Number1", WebportalParam.ap5serialNo);
        devInfo.put("MAC Address1", WebportalParam.ap5macaddress);

        new DevicesDashPage(false).addNewdummyDevice(devInfo);

        HashMap<String, String> creditsInfo = new HamburgerMenuPage().getCreditAllocationStatus(organizationName1);
        assertTrue(
                creditsInfo.get("Used Devices Credits").equals("1") && creditsInfo.get("Unused Devices Credits").equals("4")
                        && creditsInfo.get("Used ICP Credits").equals("0") && creditsInfo.get("Unused ICP Credits").equals("0"),
                "Allocate credits error.");

    }

    @Step("Test Step 6: Delete Device")
    public void step6() {

        new AccountPage().enterLocation(locationInfo.get("Location Name"));
        new WirelessQuickViewPage().deleteDeviceYes(WebportalParam.ap5serialNo);

    }

    @Step("Test Step 7: Create another Orgnization and allocate credit")
    public void step7() {

        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName2);
        new OrganizationPage().addOrganization(organizationInfo);
        new OrganizationPage(false).addAllocateCredits(organizationName2, "5", "0", "0");
    }

    @Step("Test Step 8: Create one more location")
    public void step8() {

        OrganizationPage.openOrg(organizationName2);

        locationInfo1.put("Location Name", locationName1);
        locationInfo1.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo1.put("Zip Code", "12345");
        locationInfo1.put("Country", "China");
        new AccountPage(false).addNetwork(locationInfo1);

        new AccountPage(false).enterLocation(locationInfo1.get("Location Name"));

    }

    @Step("Test Step 9: Add one Dummy AP")
    public void step9() {

        devInfo.put("Serial Number1", WebportalParam.ap5serialNo);
        devInfo.put("MAC Address1", WebportalParam.ap5macaddress);
        new DevicesDashPage(false).addNewdummyDevice(devInfo);

    }

    @Step("Test Step 10: Get Credit Allocation status and verify all data are correct")
    public void step10() {
        HashMap<String, String> creditsInfo = new HamburgerMenuPage().getCreditAllocationStatus(organizationName2);
        assertTrue(
                creditsInfo.get("Used Devices Credits").equals("1") && creditsInfo.get("Unused Devices Credits").equals("4")
                        && creditsInfo.get("Used ICP Credits").equals("0") && creditsInfo.get("Unused ICP Credits").equals("0"),
                "Allocate credits error.");
    }

    @Step("Test Step 11: Create Deleted Orgnization")
    public void step11() {
        new OrganizationPage().deleteOrganizationNew(organizationName);
        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName);

        OrganizationPage.addOrganization(organizationInfo);
        OrganizationPage.openOrg(organizationName);
        locationInfo.put("Location Name", WebportalParam.location1);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "China");
        new AccountPage(false).addNetwork(locationInfo);

    }

}
