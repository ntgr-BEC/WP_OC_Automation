package webportal.FlexibleProSubscription.PRJCBUGEN_T16032;

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
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    Map<String, String>     devInfo          = new HashMap<String, String>();
    Map<String, String>     devInfoNew       = new HashMap<String, String>();
    HashMap<String, String> locationInfo     = new HashMap<String, String>();
    HashMap<String, String> locationInfoNew  = new HashMap<String, String>();
    Map<String, String>     ssidInfo         = new HashMap<String, String>();
    String                  organizationName = "test16032";
    String                  locationName     = "ntk16032";
    String                  devicesCredits   = "2";
    String                  icpCredits       = "2";

    @Feature("FlexibleProSubscription") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T16032") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the credit allocation count while deleting network location from an organization") // It's a test case title from
                                                                                                                    // Jira Test Case.
    @TmsLink("PRJCBUGEN-T16032") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new OrganizationPage().deleteOrganizationNew(organizationName);

//        handle.gotoLoction();
//        if (new DevicesDashPage().getDeviceName(devInfo.get("Serial Number")).equals("")) {
//            new DevicesDashPage().addNewDevice(devInfo);
//            new DevicesDashPage().waitDevicesReConnected(devInfo.get("Serial Number"));
//        }
//        if (new DevicesDashPage().getDeviceName(devInfoNew.get("Serial Number")).equals("")) {
//            new DevicesDashPage().addNewDevice(devInfoNew);
//            new DevicesDashPage().waitDevicesReConnected(devInfoNew.get("Serial Number"));
//        }
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        devInfo.put("Serial Number", WebportalParam.ap5serialNo);
        devInfo.put("Device Name", WebportalParam.ap5deveiceName);
        devInfo.put("MAC Address", WebportalParam.ap5macaddress);
        devInfoNew.put("Serial Number", WebportalParam.sw1serialNo);
        devInfoNew.put("Device Name", WebportalParam.sw1deveiceName);
        devInfoNew.put("MAC Address", WebportalParam.sw1MacAddress);
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);

//        handle.gotoLoction();
//        new DevicesDashPage().checkDutInNormalAccount("admin", devInfo.get("Serial Number"), devInfo.get("Device Name"), devInfo.get("MAC Address"));
//        new DevicesDashPage().checkDutInNormalAccount("admin", devInfoNew.get("Serial Number"), devInfoNew.get("Device Name"), devInfoNew.get("MAC Address"));
    }

    @Step("Test Step 2: Add new organization and add devices, then check allocate credits result;")
    public void step2() {
//        new DevicesDashPage().deleteDeviceYes(devInfo.get("Serial Number"));
//        new DevicesDashPage().deleteDeviceYes(devInfoNew.get("Serial Number"));

        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName);

        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.addOrganization(organizationInfo);
        OrganizationPage.addAllocateCredits(organizationName, devicesCredits, "0", icpCredits);
        OrganizationPage.openOrg(organizationName);

        locationInfo.put("Location Name", locationName);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "China");
        new AccountPage(false).addNetwork(locationInfo);

        locationInfoNew.put("Location Name", locationName + "new");
        locationInfoNew.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfoNew.put("Zip Code", "12345");
        locationInfoNew.put("Country", "China");
        new AccountPage(false).addNetwork(locationInfoNew);

        new AccountPage(false).enterLocation(locationInfo.get("Location Name"));
        new DevicesDashPage().addNewDevice(devInfo);
//        new DevicesDashPage().waitDevicesReConnected(devInfo.get("Serial Number"));
        ssidInfo.put("SSID", "apwp16032");
        ssidInfo.put("Security", "WPA2 Personal");
        ssidInfo.put("Password", "12345678");
        new WirelessQuickViewPage().addSsid(ssidInfo);

        Map<String, String> icpInfo = new HashMap<String, String>();
        icpInfo.put("Portal Name", "showad");
        icpInfo.put("Welcome Headline", "dhfjsdfjasdfhjsdhfshdfhsdjhfsdfjsdgfsk;fgjsdgsdfjsfdgsgfd");
        icpInfo.put("Captive Portal Logo", "DEFAULT_LOGO");
        icpInfo.put("Desktop Background Image", "DEFAULT_BG");
        icpInfo.put("Landing Page URL", "https://www.rediff.com");
        icpInfo.put("Session Duration", "5 min");
        icpInfo.put("Step Type", "Authentication Method");
        icpInfo.put("Login Modes", "Facebook.");
        new WirelessQuickViewPage().enableCaptivePortalType(ssidInfo.get("SSID"), icpInfo);

        OrganizationPage.gotoPage();
        OrganizationPage.openOrg(organizationName);
        new AccountPage(false).enterLocation(locationInfoNew.get("Location Name"));
        new DevicesDashPage().addNewDevice(devInfoNew);
//        new DevicesDashPage().waitDevicesReConnected(devInfoNew.get("Serial Number"));
        HashMap<String, String> creditsInfo = new HamburgerMenuPage().getCreditAllocationStatus(organizationName);
        assertTrue(
                creditsInfo.get("Used Devices Credits").equals(devicesCredits) && creditsInfo.get("Unused Devices Credits").equals("0")
                        && creditsInfo.get("Used ICP Credits").equals("1") && creditsInfo.get("Unused ICP Credits").equals("1"),
                "Allocate credits error.");
    }

    @Step("Test Step 3: Delete one location, check allocate credits result;")
    public void step3() {
        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.openOrg(organizationName);
        new AccountPage(false).deleteLocation(locationInfo.get("Location Name"));

        HashMap<String, String> creditsInfo = new HamburgerMenuPage().getCreditAllocationStatus(organizationName);
        assertTrue(
                creditsInfo.get("Used Devices Credits").equals("1") && creditsInfo.get("Unused Devices Credits").equals("1")
                        && creditsInfo.get("Used ICP Credits").equals("0") && creditsInfo.get("Unused ICP Credits").equals(icpCredits),
                "Allocate credits error.");
    }

}
