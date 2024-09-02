package webportal.Orbi.Enhancement_BVPN_Feature.PRJCBUGEN_T29643;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

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
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.RoutersBusinessVPNPage;
import webportal.param.WebportalParam;

/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("Enhancement_BVPN_Feature") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T29643") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify When the user enables then wireless VPN SSID pushed on the Central router") // It's a testcase title from Jira Test
                                                                                                             // Case.
    @TmsLink("PRJCBUGEN-T29643") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("Teardown : delete VPN group");
        try {
            RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
            page.gotoPage();
            page.DelVPNGroup("TestGroup");
        } catch (Throwable e) {
            System.out.println("Failed to delete the vpn group");
        }
    }

    // Each step covers the operation in one page
    @Step("Test Step 1: Login to WP / Go to Location 1 where the central router is located")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
    }

    @Step("Test Step 2: Go to Routers page / User create a VPN group with router mode set as employee home.")
    public void step2() {
        boolean checkpoint;
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup", "Test Description", "1400");
        page.setPage2AddCentralRouter();
        page.setPage2AddRemoteRouter(WebportalParam.location2, WebportalParam.ob2deveiceName, 1, true);
        page.clickNext();
        page.setPage3WirelessSettings(true, "PRJCBUGEN-T29643", 1, "12345678");
        page.clickNext();
        page.clickNext(); // click next (actual is save btn)
        util.MyCommonAPIs.waitReady();
        page.successicon.waitUntil(Condition.appear, 30000, 5000); // wait business vpn group setup successfully
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi();
    }

    @Step("Test Step 3: Check wireless client can connect to central router")
    public void step3() {
        boolean checkpoint = false;
        String cmd;

        // restart wifi
        new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, "WAFdisconnect");
        util.MyCommonAPIs.sleep(5000);
        new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port,
                String.format("WAFwifiOff %s", WebportalParam.client1wifi));
        util.MyCommonAPIs.sleep(5000);
        new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port,
                String.format("WAFwifiOn %s", WebportalParam.client1wifi));
        util.MyCommonAPIs.sleep(5000);

        // connect to bssid
        checkpoint = true;
        cmd = String.format("WAFconnectBSSID PRJCBUGEN-T29643 12345678 WPA2PSK aes %s", WebportalParam.ob1SSIDWANbssid);
        if (!new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, cmd).equals("true")) {
            checkpoint = false;
        }
        System.out.println(checkpoint);

        assertTrue(checkpoint, "checkpoint 1 : wireless client can connect to central router");
    }

}
