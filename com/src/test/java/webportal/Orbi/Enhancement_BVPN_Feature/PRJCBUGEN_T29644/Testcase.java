package webportal.Orbi.Enhancement_BVPN_Feature.PRJCBUGEN_T29644;

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
import util.SocketCommand;
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
    @Story("PRJCBUGEN_T29644") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify if the user disables the VPN SSID then broadcasting is stopped from the central router.") // It's a testcase title from Jira Test
                                                                                                             // Case.
    @TmsLink("PRJCBUGEN-T29644") // It's a testcase id/link from Jira Test Case.
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
        page.setPage3WirelessSettings(true, "PRJCBUGEN-T29644", 1, "12345678");
        page.clickNext();
        page.clickNext(); // click next (actual is save btn)
        util.MyCommonAPIs.waitReady();
        page.successicon.waitUntil(Condition.appear, 30000, 3000); // wait business vpn group setup successfully
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi();
        
        // disable the VPN SSID for central router
        page.gotoPage();
        page.clickEditVPNGroup("TestGroup");
        util.MyCommonAPIs.sleepi(3);
        page.clickNext();
        util.MyCommonAPIs.sleepi(3);
        page.clickNext();
        util.MyCommonAPIs.sleepi(3);
        page.setSelected(page.VPNWirelessNetwork, false);
        page.clickNext();
        page.clickNext(); // click next (actual is save btn)
        page.successicon.waitUntil(Condition.appear, 30000, 3000); // wait business vpn group setup successfully
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi();
    }

    @Step("Test Step 3: Check wireless client cannot connect to central router")
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
        cmd = String.format("WAFconnectBSSID PRJCBUGEN-T29644 12345678 WPA2PSK aes %s", WebportalParam.ob1SSIDWANbssid);
        if (!new Javasocket().sendCommandToWinClient(WebportalParam.client1ip, WebportalParam.client1port, cmd).equals("true")) {
            checkpoint = false;
        }
        System.out.println(checkpoint);
        
        if(checkpoint) {
            checkpoint = new SocketCommand().checkNICSubnetPrefix(WebportalParam.client1ip, WebportalParam.client1port, WebportalParam.client1wifi, WebportalParam.ob1IPsubnet);
            assertFalse(checkpoint, "the subnet of the wireless network card should not be under the central router ");
        }

    }

}
