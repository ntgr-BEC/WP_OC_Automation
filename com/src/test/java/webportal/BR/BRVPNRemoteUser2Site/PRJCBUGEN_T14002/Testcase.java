package webportal.BR.BRVPNRemoteUser2Site.PRJCBUGEN_T14002;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.VPNClient;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String sUser     = webportalParam.winVPNClientUser;
    String groupName = "testvpn";

    @Feature("BR.BRVPNRemoteUser2Site") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14002") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("021-Login and Logout insight vpn client 10 times") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14002") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        brdvup.gotoPage();
        for (String user : brdvup.getUsers()) {
            if (user.contains(sUser)) {
                brdvup.deleteUser(user);
            }
        }
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: IM APP discover and manage BR500")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.openOneBRDevice(true);
    }

    @Step("Test Step 2: IM APP add the account to VPN Group A;\r\n" + "There is one BR on the VPN Group A;")
    public void step2() {
        brdvgp.gotoPage();
        brdvgp.createVpnGroupWithDevices(groupName);
    }

    @Step("Test Step 3: User has register one Netgear SSO account")
    public void step3() {
        brdvup.gotoPage();
        brdvup.addUser(sUser);
        handle.refresh();

        if (!brdvup.isUserActive(sUser)) {
            brdvup.reInviteUser(sUser);
            assertTrue(brdvup.acceptInviteFromMail(sUser), sUser + " cannot be actived");
        }
    }

    @Step("Test Step 4: Repeat step1 10 times continuously,")
    public void step4() {
        assertTrue(new VPNClient(sUser).callStumb("checkLoginLogout"));
    }
}
