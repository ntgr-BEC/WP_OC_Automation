package webportal.FlexiProDeallocation.PRJCBUGEN_T16986;

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
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.ManagerPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    Map<String, String> ssidInfo     = new HashMap<String, String>();
    Random              r            = new Random();
    int                 num          = r.nextInt(10000000);
    String              accessCode   = "";
    String              managerEmail = "voucheradmin" + String.valueOf(num) + "@yopmail.com";
    String organizationName = "Netgear";

    @Feature("FlexiProDeallocation") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T16986") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify instant captive portal funcationally when user select tier type as \"voucher \"") // It's a test case title from Jira Test
                                                                                                           // Case.
    @TmsLink("PRJCBUGEN-T16986") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        String url = MyCommonAPIs.getCurrentUrl();
        if (url.contains("accounts") && url.contains("login")) {
            WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
            webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        } else if (url.contains("/voucherManagement/organisationView") && url.contains("insight.netgear")) {
            UserManage userManage = new UserManage();
            userManage.logout();

            WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
            webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        }

        new ManagerPage().deleteManager(managerEmail);
        handle.gotoLoction();
        new WirelessQuickViewPage().deleteSsidYes(ssidInfo.get("SSID"));
        new OrganizationPage().deleteOrganizationNew(organizationName);
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        
        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName);
        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.addOrganization(organizationInfo);

//        handle.gotoLoction();
    }

    @Step("Test Step 2: Add WIFI ssid and enable enable instant captive portal, then create voucher admin account;")
    public void step2() {
        
        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.openOrg(organizationName);
        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "OnBoardingTest");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "4560");
        locationInfo.put("Country", "Australia");
        new AccountPage().addNetwork(locationInfo);
        
        
        if (!new HamburgerMenuPage().checkCaptivePortalServicesCredits()) {
            assertTrue(false, "Account need to add instant captive portal key.");
        }
        
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        new MyCommonAPIs().gotoLoction("OnBoardingTest");
        new OrganizationPage(false).goWirelessSetting();

        ssidInfo.put("SSID", "apwp16986");
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
        icpInfo.put("Step Type", "Voucher");
        new WirelessQuickViewPage().enableCaptivePortalType(ssidInfo.get("SSID"), icpInfo);

        Map<String, String> vadminInfo = new HashMap<String, String>();
        vadminInfo.put("Email Address", managerEmail);
        vadminInfo.put("Name", "voucheradminwp");
        vadminInfo.put("Organization Name", WebportalParam.Organizations);

        new ManagerPage().addVoucherAdmin(vadminInfo);

        UserManage userManage = new UserManage();
        userManage.logout();
        assertTrue(new HamburgerMenuPage(false).checkEmailMessageForInvitemangaerOwner(vadminInfo.get("Email Address")), "Not received Invitation email.");
        assertTrue(new HamburgerMenuPage(false).inviteEmailFillDateandAccept(), "Not received Invitation email.");
        assertTrue(new HamburgerMenuPage(false).verifySignUpPage(), "After clicking on invite manager link not landed on sign up page");
        
    }

}
