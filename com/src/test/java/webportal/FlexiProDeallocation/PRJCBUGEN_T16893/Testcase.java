package webportal.FlexiProDeallocation.PRJCBUGEN_T16893;

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

    String                  organizationName      = "test16893";
    Map<String, String>     organizationInfo      = new HashMap<String, String>();
    Map<String, String>     organizationInfoTwo   = new HashMap<String, String>();
    Map<String, String>     organizationInfoThree = new HashMap<String, String>();
    HashMap<String, String> locationInfo          = new HashMap<String, String>();
    HashMap<String, String> locationInfoNew       = new HashMap<String, String>();
    Map<String, String>     ssidInfo              = new HashMap<String, String>();
    String                  locationName          = "ntk16893";
    int                     devNum                = 1;
    int                     devNumTwo             = 1;
    int                     devNumThree           = 1;
    int                     icpNum                = 2;
    int                     icpNumTwo             = 2;
    int                     icpNumThree           = 2;

    @Feature("FlexiProDeallocation") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T16893") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify whether Unused ICP Credits are shown in Deallocate page") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T16893") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new OrganizationPage().deleteOrganizationNew(organizationName);
        new OrganizationPage().deleteOrganizationNew(organizationInfoTwo.get("Name"));
        new OrganizationPage().deleteOrganizationNew(organizationInfoThree.get("Name"));
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
    }

    @Step("Test Step 2: Add new organization, then check devices credits page;")
    public void step2() {
        organizationInfo.put("Name", organizationName);
        organizationInfoTwo.put("Name", organizationName + "two");
        organizationInfoThree.put("Name", organizationName + "three");

        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.addOrganization(organizationInfo);
        OrganizationPage.addOrganization(organizationInfoTwo);
        OrganizationPage.addOrganization(organizationInfoThree);

        new HamburgerMenuPage().configCreditAllocation(organizationInfoThree.get("Name"), devNumTwo, 0, icpNumThree);
        new HamburgerMenuPage().configCreditAllocation(organizationInfoTwo.get("Name"), devNumTwo, 0, icpNumTwo);
        new HamburgerMenuPage().configCreditAllocation(organizationName, devNum, 0, icpNum);

        OrganizationPage.gotoPage();
        OrganizationPage.openOrg(organizationName);
        MyCommonAPIs.sleepi(10);
        locationInfo.put("Location Name", locationName);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "China");
        new AccountPage(false).addNetwork(locationInfo);

        new AccountPage(false).enterLocation(locationInfo.get("Location Name"));
       
        

        ssidInfo.put("SSID", "apwp16893");
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
        MyCommonAPIs.sleepi(10);
        new AccountPage(false).enterLocation(locationInfo.get("Location Name"));
        Map<String, String> firststdevInfo = new HashMap<String, String>();
        firststdevInfo.put("Serial Number1", WebportalParam.ap5serialNo);
        firststdevInfo.put("MAC Address1", WebportalParam.ap5macaddress);
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);

        OrganizationPage.gotoPage();
        OrganizationPage.openOrg(organizationInfoTwo.get("Name"));
        MyCommonAPIs.sleepi(15);
        locationInfo.put("Location Name", locationName);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "China");
        new AccountPage(false).addNetwork(locationInfo);
        MyCommonAPIs.sleepi(10);
        new AccountPage(false).enterLocation(locationInfo.get("Location Name"));

        ssidInfo.put("SSID", "apwp16893");
        ssidInfo.put("Security", "WPA2 Personal");
        ssidInfo.put("Password", "12345678");
        new WirelessQuickViewPage().addSsid(ssidInfo);

        Map<String, String> icpInfoTwo = new HashMap<String, String>();
        icpInfoTwo.put("Portal Name", "showad");
        icpInfoTwo.put("Welcome Headline", "dhfjsdfjasdfhjsdhfshdfhsdjhfsdfjsdgfsk;fgjsdgsdfjsfdgsgfd");
        icpInfoTwo.put("Captive Portal Logo", "DEFAULT_LOGO");
        icpInfoTwo.put("Desktop Background Image", "DEFAULT_BG");
        icpInfoTwo.put("Landing Page URL", "https://www.rediff.com");
        icpInfoTwo.put("Session Duration", "5 min");
        icpInfoTwo.put("Step Type", "Authentication Method");
        icpInfoTwo.put("Login Modes", "Facebook.");
        new WirelessQuickViewPage().enableCaptivePortalType(ssidInfo.get("SSID"), icpInfoTwo);
        
        OrganizationPage.gotoPage();
        OrganizationPage.openOrg(organizationInfoTwo.get("Name"));
        MyCommonAPIs.sleepi(10);
        new AccountPage(false).enterLocation(locationInfo.get("Location Name"));
        Map<String, String> firststdevInfo1 = new HashMap<String, String>();
        firststdevInfo1.put("Serial Number1", WebportalParam.ap6serialNo);
        firststdevInfo1.put("MAC Address1", WebportalParam.ap6macaddress);
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo1);

        OrganizationPage.gotoPage();
        OrganizationPage.openOrg(organizationName + "three");
        MyCommonAPIs.sleepi(10);
        locationInfo.put("Location Name", locationName);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "China");
        new AccountPage(false).addNetwork(locationInfo);

        new AccountPage(false).enterLocation(locationInfo.get("Location Name"));

        ssidInfo.put("SSID", "apwp16893");
        ssidInfo.put("Security", "WPA2 Personal");
        ssidInfo.put("Password", "12345678");
        new WirelessQuickViewPage().addSsid(ssidInfo);

        Map<String, String> icpInfoThree = new HashMap<String, String>();
        icpInfoThree.put("Portal Name", "showad");
        icpInfoThree.put("Welcome Headline", "dhfjsdfjasdfhjsdhfshdfhsdjhfsdfjsdgfsk;fgjsdgsdfjsfdgsgfd");
        icpInfoThree.put("Captive Portal Logo", "DEFAULT_LOGO");
        icpInfoThree.put("Desktop Background Image", "DEFAULT_BG");
        icpInfoThree.put("Landing Page URL", "https://www.rediff.com");
        icpInfoThree.put("Session Duration", "5 min");
        icpInfoThree.put("Step Type", "Authentication Method");
        icpInfoThree.put("Login Modes", "Facebook.");
        new WirelessQuickViewPage().enableCaptivePortalType(ssidInfo.get("SSID"), icpInfoThree);
        
        OrganizationPage.gotoPage();
        OrganizationPage.openOrg(organizationName + "three");
        MyCommonAPIs.sleepi(10);
        new AccountPage(false).enterLocation(locationInfo.get("Location Name"));
        Map<String, String> firststdevInfo2 = new HashMap<String, String>();
        firststdevInfo2.put("Serial Number1", WebportalParam.ap7serialNo);
        firststdevInfo2.put("MAC Address1", WebportalParam.ap7macaddress);
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo2);


        HashMap<String, String> creditsInfo = new HamburgerMenuPage().getDeallocatePageInfo();
        assertTrue(
                 creditsInfo.get("Deallocate IcpNum").equals(String.valueOf(icpNum + icpNumTwo + icpNumThree - 3)),
                "Allocate credits error.");
    }

}
