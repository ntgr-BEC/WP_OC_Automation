package webportal.BR.BRVlan.PRJCBUGEN_T7186;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.BRUtils;
import webportal.param.WebportalParam;
import webportal.publicstep.PublicButton;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String vlanName1 = "testvlan1";
    String vlanId1   = "3181";
    String vlanName2 = "testvlan2";
    String vlanId2   = "3182";
    String vlanName3 = "testvlan3";
    String vlanId3   = "3183";
    String vlanName4 = "testvlan4";
    String vlanId4   = "3184";
    String vlanName5 = "testvlan5";
    String vlanId5   = "3185";
    String sPort     = "2,3";
    String sExpect   = vlanId5 + "t";

    @Feature("BR.BRVlan") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7186") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("008-Add one port to many VLANs with tag/untag mode") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T7186") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        if (WebportalParam.br1model.contains("100"))
            throw new RuntimeException("Not applied to BR100");
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (!WebportalParam.br1model.contains("100")) {
            wvp.gotoPage();
            wvp.deleteAllVlan();
            wvp.editVlanPorts("1", WebportalParam.br1deveiceName, sPort, "", "", true);
        }
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: IM APP discover and manage BR500")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        wvp.gotoPage();
        wvp.editVlanPorts("1", WebportalParam.br1deveiceName, "", "", sPort, true);
    }

    @Step("Test Step 2: Go to VLAN config page, create some VLANs(more than 5), add one port to\r\n"
            + "these VLANs, some of with tag, some of without tag;")
    public void step2() {
        wvp.newVlan(vlanName1, vlanId1, 0);
        wvp.editVlanPorts(vlanId1, WebportalParam.br1deveiceName, "2", "", "", true);
        wvp.newVlan(vlanName2, vlanId2, 0);
        wvp.editVlanPorts(vlanId2, WebportalParam.br1deveiceName, "", "3", "", true);
        wvp.newVlan(vlanName3, vlanId3, 0);
        wvp.editVlanPorts(vlanId3, WebportalParam.br1deveiceName, "", "3", "", true);
        wvp.newVlan(vlanName4, vlanId4, 0);
        wvp.editVlanPorts(vlanId4, WebportalParam.br1deveiceName, "", "3", "", true);
        wvp.newVlan(vlanName5, vlanId5, 0);
        wvp.editVlanPorts(vlanId5, WebportalParam.br1deveiceName, "", "3", "", true);
    }

    @Step("Test Step 3: Reboot BR by IM APP, All VLAN configuration not lost after reboot;")
    public void step3() {
        new DevicesDashPage().enterDevicesSwitchSummary(WebportalParam.br1serialNo, 1);
        new PublicButton().rebootDevice();
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.br1serialNo);

        handle.waitRestReady(BRUtils.api_lan_port_stats, sExpect, false, 0);
        assertTrue(new BRUtils().Dump().contains(sExpect), "vlan is lost on device after reboot");
    }

}
