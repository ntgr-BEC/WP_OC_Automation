package webportal.LongRunStability.PRJCBUGEN_T13781;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import util.SQLiteStability;
import util.VPNClientToMac;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    final String    tcName       = "[T13781] Establish client VPN and forwarding packets on Mac";
    String          stepInfo     = "";
    String          expectResult = "";
    String          actualResult = "";
    SQLiteStability dbHandle     = null;
    String          devNo        = null;
    String          devIp        = "192.168.11.8";
    String          vpnGroupName = WebportalParam.longrunVpnGroupName;
    boolean         vpnStatus    = false;
    VPNClientToMac  vpnHandle    = null;

    @Feature("LongRunStability") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T13781") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description(tcName) // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T13781") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        dbHandle = new SQLiteStability();
        vpnHandle = new VPNClientToMac();
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
    }

    @Step("Test Step 1: Log into Web Portal")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
    }

    @Step("Test Step 2: Login with accepted account")
    public void step2() {
        try {
            handle.gotoLoction();
            ddp.gotoPage();
            devNo = ddp.getSWDevice();
            devIp = ddp.getDeviceIP(devNo);
        } catch (Throwable e) {
            e.printStackTrace();
            MyCommonAPIs.takess();
        }

        stepInfo = "Check client can connect to vpn group";
        expectResult = "Client is login to vpn group";
        actualResult = "Client is not able to open vpn group";
        vpnHandle.clientLogout();
        vpnHandle.clientLogin();
        vpnStatus = vpnHandle.clientGroupOpen();

        if (vpnStatus) {
            dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
        } else {
            dbHandle.writeResultFail(tcName, stepInfo, expectResult, actualResult);
        }
    }

    @Step("Test Step 3: Ping remote IP or PC IP under BR500, keep 2 mins")
    public void step3() {
        stepInfo = "Check client can ping 2m to a client " + devIp;
        expectResult = "less than 5% packet loss";
        actualResult = "lost rate is: 100%";
        if (vpnStatus) {
            int lost = vpnHandle.clientPing(devIp);
            actualResult = String.format("lost rate is: %s%%", lost);
            if (lost < 5) {
                dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
                return;
            }
        }
        dbHandle.writeResultFail(tcName, stepInfo, expectResult, actualResult);

    }
}
