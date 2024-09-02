package webportal.AP.WiredVLANSupportWAC540564.PRJCBUGEN_T15014;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.APUtils;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesApPortConfiqSettingPage;
import webportal.weboperation.DevicesApSummaryPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String vlanName    = "testvlan";
    String vlanId      = "1014";
    String networkName = "testnet";
    String accessport  = "2";

    @Feature("AP.WiredVLANSupportWAC540564") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T15014") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify if the user can make changes in the Ports default PVIDs in the Ports Settings page") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T15014") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        netsp.gotoPage();
        netsp.deleteNetwork(networkName);
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open page and goto Vlan Page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
        handle.gotoLoction();
    }
    
    @Step("Test Step 2: create a vlan")
    public void step2() {
        netsp.gotoPage();
        netsp.createNetwork(networkName, 0, vlanName, vlanId);
    }
    
    @Step("Test Step 3: assign a port into vlan")
    public void step3() {
        ddp.gotoPage();
        ddp.enterDevicesSwitchSummary(WebportalParam.ap1serialNo, 4);
        new DevicesApSummaryPage().enterPortConfigSummary(accessport);
        new DevicesApPortConfiqSettingPage().SetPortPvid(vlanId);
        
        APUtils ap = new APUtils(WebportalParam.ap1IPaddress);
        ap.waitConfigReady(vlanId, false);
        
        String apConf = ap.getConfig(String.format("vlanID %s", vlanId));
        assertTrue(apConf.contains(vlanId), "vlan id must be on ap:" + vlanId);
        
        String name = ap.getLineField(apConf, "vlanProfile");
        apConf = ap.getConfig(String.format(":%s:", name));
        assertTrue(apConf.contains(String.format("lan%s:member 1", accessport)), "Port 2 must be a member");
        assertTrue(apConf.contains(String.format("lan%s:tagged 0", accessport)), "Port 2 must be a untag");
    }
}
