package webportal.AP.WiredVLANSupportWAC540564.PRJCBUGEN_T16602;

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
    String vlanId      = "1602";
    String networkName = "testnet";
    String ports       = "2";

    @Feature("AP.WiredVLANSupportWAC540564") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T16602") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify whether the user is able to alter the port PVID configs") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T16602") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        netsp.gotoPage();
        netsp.deleteAllNetwork();
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open page and goto Vlan Page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
        handle.gotoLoction();
    }
    
    @Step("Test Step 2: Created a network location and added a few AP models ")
    public void step2() {
        netsp.gotoPage();
        netsp.createNetwork(networkName, 0, vlanName, vlanId);
    }
    
    @Step("Test Step 3: Select the desired Vlan id from the drop down menu")
    public void step3() {
        ddp.gotoPage();
        ddp.enterDevicesSwitchSummary(WebportalParam.ap1serialNo, 4);
        new DevicesApSummaryPage().enterPortConfigSummary(ports);
        new DevicesApPortConfiqSettingPage().SetPortPvid(vlanId);
    }
    
    @Step("Test Step 4: user should be allowed to set the PVID for a port as per the vlan configs available")
    public void step4() {
        APUtils ap = new APUtils(WebportalParam.ap1IPaddress);
        ap.waitConfigReady(vlanId, false);

        String apConf = ap.getConfig(String.format("vlanName %s", vlanName));
        assertTrue(apConf.contains(vlanName), "vlan name must be on ap:" + vlanName);

        String name = ap.getLineField(apConf, "vlanProfile");
        apConf = ap.getConfig(String.format(":%s:", name));
        assertTrue(apConf.contains(String.format("lan%s:member 1", ports)), "Port must be a member now");
    }
}
