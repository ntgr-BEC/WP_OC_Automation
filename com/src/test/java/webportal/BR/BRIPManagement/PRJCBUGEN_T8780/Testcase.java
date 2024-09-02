package webportal.BR.BRIPManagement.PRJCBUGEN_T8780;

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
import util.BRUtils;
import webportal.param.WebportalParam;
import webportal.weboperation.DeviceBRPortSummaryPage;
import webportal.weboperation.DeviceBRSummaryPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String sExpect  = "192.168.160.1";
    String vlanName = "testvlan";
    String vlanId   = "1780";
    String sPort    = "2";

    @Feature("BR.BRIPManagement") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T8780") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("012-Add new LAN IP address from Insight") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T8780") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        wvp.gotoPage();
        wvp.deleteVlan(vlanName, false);
        wvp.gotoPage();
        wvp.editVlanPorts("1", WebportalParam.br1deveiceName, sPort, "", "", true);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: IM APP discover and manage BR500")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        wvp.gotoPage();
        wvp.deleteAllVlan();
    }

    @Step("Test Step 2: Go to VLAN config page, add new VLAN(100), add one port untag to this VLAN;")
    public void step2() {
        wvp.gotoPage();
        wvp.editVlanPorts("1", WebportalParam.br1deveiceName, "", "", sPort, true);

        wvp.newVlan(vlanName, vlanId, 0);
        wvp.editVlanPorts(vlanId, WebportalParam.br1deveiceName, sPort, "", "", true);
    }

    @Step("Test Step 3: Go to DHCP Servers page, add new LAN IP address(100.100.100.1/24), binding to VLAN(100), enable DHCP Server;")
    public void step3() {
        handle.openOneBRDevice(true);
        brddchps.gotoPage();
        brddchps.addOne(vlanName, sExpect);

        DevicesDashPage pageNew = new DevicesDashPage();
        pageNew.waitDevicesReConnected(WebportalParam.br1serialNo);
        pageNew.openBR1();

        DeviceBRSummaryPage page = new DeviceBRSummaryPage();
        page.portChoice(sPort).click();

        DeviceBRPortSummaryPage sumPage = new DeviceBRPortSummaryPage();
        sumPage.gotoPage();
        assertTrue(sumPage.getPVID().contains(vlanId), "pvid is not shown on port summary page");
        assertTrue(sumPage.getVlanID().contains(vlanId), "vlan id is not shown on port summary page");
    }

    @Step("Test Step 4: PC can get IP address from new LAN;")
    public void step4() {
        handle.waitRestReady(BRUtils.api_lan_subnet_stats, sExpect, false, 0);
        assertTrue(new BRUtils().Dump().contains(sExpect), "new vlan dchp is not config");
    }
    
    @Step("Test Step 5: Delete VLAN 100 from Insight directly;")
    public void step5() {
        wvp.gotoPage();
        wvp.deleteVlan(vlanId, true);
    }

    @Step("Test Step 6: Config success, checked by Insight and BR500 local GUI; PC cannot get IP address;")
    public void step6() {
        handle.waitRestReady(BRUtils.api_lan_subnet_stats, sExpect, true, 0);
        assertFalse(new BRUtils().Dump().contains(sExpect), "new vlan dchp is not removed: " + sExpect);
    }
}
