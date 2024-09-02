package webportal.Switch.Routing.PRJCBUGEN_T6218;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

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
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String vlanName = "testvlan";
    String vlanId   = "118";
    String ipMask   = "255.255.255.0";
    String ip1      = "218.1.1.1";
    String ip2      = "218.1.1.2";

    @Feature("Switch.Routing") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T6218") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("003-Delete ip address of a vlan") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T6218") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        if (!rtp.isRoutingDisabled()) {
            runTest(this);
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (!rtp.isRoutingDisabled()) {
            wvp.gotoPage();
            wvp.deleteAllVlan();
        }
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create a new vlan 100")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wvp.gotoPage();
        wvp.newVlan(vlanName, vlanId, 0);

        new AccountPage().enterEditNetworkPage();
        rtp.gotoPage();
    }

    @Step("Test Step 2: Add ip address and mask to vlan 100")
    public void step2() {
        rtp.deleteVlanRoute(vlanId);
        rtp.addIpToVlan(vlanId, ipMask, ip1, ip2);
        rtp.openVlan(vlanId);
        assertTrue(rtp.seMask.getValue().equals(ipMask), "check mask");
        assertTrue(MyCommonAPIs.getValue(rtp.getIpAddressXpath(WebportalParam.sw1deveiceName)).equals(ip1), "check ip 1");
        if (!rtp.isRoutingDisabled(WebportalParam.sw2Model)) {
            assertTrue(MyCommonAPIs.getValue(rtp.getIpAddressXpath(WebportalParam.sw2deveiceName)).equals(ip2), "check ip 2");
        }
        handle.refresh();
    }

    @Step("Test Step 3: Click clear button on vlan 100")
    public void step3() {
        rtp.gotoPage();
        rtp.deleteVlanRoute(vlanId);
    }

    @Step("Test Step 4: Check vlan 100 on app")
    public void step4() {
        rtp.openVlan(vlanId);
        assertTrue(rtp.seMask.getValue().isEmpty(), "check mask after delete");
        assertTrue(MyCommonAPIs.getValue(rtp.getIpAddressXpath(WebportalParam.sw1deveiceName)).isEmpty(), "check ip 1 after delete");
        if (!rtp.isRoutingDisabled(WebportalParam.sw2Model)) {
            assertTrue(MyCommonAPIs.getValue(rtp.getIpAddressXpath(WebportalParam.sw2deveiceName)).isEmpty(), "check ip 2 after delete");
        }
        handle.refresh();
    }

    @Step("Test Step 5: Check vlan 100 on switch web gui")
    public void step5() {
        MyCommonAPIs.waitCliReady("show running-config", ip1, true);
        String tmpStr = MyCommonAPIs.getCmdOutput("show running-config", false);
        assertFalse(tmpStr.contains(ip1) || tmpStr.contains(ip2), "verify ip is set");
    }
}
