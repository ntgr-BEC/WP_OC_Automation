package webportal.SwitchManaged.VlanOverhauling.PRJCBUGEN_T11342;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredQuickViewPage;
import webportal.weboperation.WiredVLANPageForVLANPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String vlanName    = "testvlan";
    String vlanId      = "1342";
    String ip1         = "192.168.34.100";
    String ip2         = "192.168.34.200";
    String networkName = "testnet" + vlanId;

    @Feature("Switch.VlanOverhauling") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T11342") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("074- Set invalid IP or duplicated IP address") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T11342") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.deleteAllVlan();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open page and goto Vlan Page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
    }

    @Step("Test Step 2: Set static IP address\r\n" + "1> 192.168.1.255\r\n" + "2> 256.1.1.1\r\n" + "3> 192.1.1\r\n" + "4> a.a.b.b\r\n"
            + "5> same IP address on two switch")
    public void step2() {
        netsp.gotoPage();
        netsp.clickAdd();
        netsp.setNetwork1(networkName, "", 0, vlanName, vlanId);
        netsp.gotoStep(4);
        netsp.setDhcpMode(false);
        netsp.txtMask.setValue("255.255.255.0");

        netsp.setStaticIP(WebportalParam.sw2deveiceName, ip1);

        netsp.setStaticIP(WebportalParam.sw1deveiceName, "192.168.1.255");
        netsp.clickNextOrSkip(false);
        assertTrue(handle.getPageErrorMsg().length() > 0, "error msg to invalid ip");

        netsp.setStaticIP(WebportalParam.sw1deveiceName, "256.1.1.1");
        netsp.clickNextOrSkip(false);
        assertTrue(handle.getPageErrorMsg().length() > 0, "error msg to invalid ip");

        netsp.setStaticIP(WebportalParam.sw1deveiceName, "192.1.1");
        netsp.clickNextOrSkip(false);
        assertTrue(handle.getPageErrorMsg().length() > 0, "error msg to invalid ip");

        netsp.setStaticIP(WebportalParam.sw1deveiceName, "a.a.b.b");
        netsp.clickNextOrSkip(false);
        assertTrue(handle.getPageErrorMsg().length() > 0, "error msg to invalid ip");

        netsp.setStaticIP(WebportalParam.sw1deveiceName, ip1);

        netsp.setStaticIP(WebportalParam.sw2deveiceName, "192.168.1.255");
        netsp.clickNextOrSkip(false);
        assertTrue(handle.getPageErrorMsg().length() > 0, "error msg to invalid ip");

        netsp.setStaticIP(WebportalParam.sw2deveiceName, "256.1.1.1");
        netsp.clickNextOrSkip(false);
        assertTrue(handle.getPageErrorMsg().length() > 0, "error msg to invalid ip");

        netsp.setStaticIP(WebportalParam.sw2deveiceName, "192.1.1");
        netsp.clickNextOrSkip(false);
        assertTrue(handle.getPageErrorMsg().length() > 0, "error msg to invalid ip");

        netsp.setStaticIP(WebportalParam.sw2deveiceName, "a.a.b.b");
        netsp.clickNextOrSkip(false);
        assertTrue(handle.getPageErrorMsg().length() > 0, "error msg to invalid ip");
    }
}
