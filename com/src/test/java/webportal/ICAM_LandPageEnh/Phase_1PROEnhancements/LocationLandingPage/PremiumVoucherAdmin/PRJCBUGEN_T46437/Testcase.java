package webportal.ICAM_LandPageEnh.Phase_1PROEnhancements.LocationLandingPage.PremiumVoucherAdmin.PRJCBUGEN_T46437;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Condition;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.Javasocket;
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
 * @author Pratik
 *
 */
public class Testcase extends TestCaseBase {

    Map<String, String> managerInfo = new HashMap<String, String>();
    UserManage          userManage  = new UserManage();
    Random              r           = new Random();
    int                 num         = r.nextInt(10000000);
    String              mailname    = "vcadm" + String.valueOf(num);
    String              mailvc      = mailname + "@yopmail.com";

    @Feature("PremiumVoucherAdmin") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T46437") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that after created voucher admin, able to login from same account")
    @TmsLink("PRJCBUGEN_T46437") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        assertTrue(new OrganizationPage(false).deleteVCAdmin(managerInfo.get("Email Address")), "VC Admin not added successfully");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

    }

    @Step("Test Step 2: Verify Add voucher admin;")
    public void step2() {

        managerInfo.put("Name", mailname);
        managerInfo.put("Email Address", mailvc);
        assertTrue(new OrganizationPage(false).verifyAddVoucherAdminInPremiumAcc(managerInfo), "VC Admin not added successfully");
    }

    @Step("Test Step 3: after inviting vc admin check its success;")
    public void step3() {

        userManage.logout();
        assertTrue(new HamburgerMenuPage(false).checkEmailMessageForInvitemangaerOwner(managerInfo.get("Email Address")),
                "Not received Invitation email.");
        assertTrue(new HamburgerMenuPage(false).inviteEmailFillDateandAccept(), "Not received Invitation email.");

    }

    @Step("Test Step 4: Login with vc admin account;")
    public void step4() {

        Map<String, String> proAccountInfo = new HashMap<String, String>();
        proAccountInfo.put("Confirm Email", managerInfo.get("Email Address"));
        proAccountInfo.put("Password", "Netgear1@");
        proAccountInfo.put("Confirm Password", "Netgear1@");
        proAccountInfo.put("Country", "United States of America");
        proAccountInfo.put("Phone Number", "1234567890");
        assertTrue(new HamburgerMenuPage(false).FillInvitemanagerVCAdminInfoAndVerifylogin(proAccountInfo),
                "Voucher Admin account not able to login");
        userManage.logout();

    }

}
