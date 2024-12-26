package webportal.SwitchManaged.Routing.PRJCBUGEN_T6231;

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
    String vlanId   = "131";
    String ipMask   = "255.255.255.0";
    String ip1      = "131.1.1.1";
    String ip2      = "131.1.1.2";
    String ip3      = "131.1.2.1";
    String ip4      = "131.1.2.2";
    
    @Feature("Switch.Routing") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T6231") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("016-Modify ip address of a vlan") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T6231") // It's a testcase id/link from Jira Test Case.
    
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
    
    @Step("Test Step 2: Add IP1 to vlan 100")
    public void step2() {
        rtp.deleteVlanRoute(vlanId);
        rtp.addIpToVlan(vlanName, ipMask, ip1, ip2);
    }
    
    @Step("Test Step 3: Check configuraiton from app and local web gui")
    public void step3() {
        rtp.openVlan(vlanName);
        assertTrue(rtp.seMask.getValue().equals(ipMask), "check mask");
        assertTrue(MyCommonAPIs.getValue(rtp.getIpAddressXpath(WebportalParam.sw1deveiceName)).equals(ip1), "check ip 1");
        if (!rtp.isRoutingDisabled(WebportalParam.sw2Model)) {
            assertTrue(MyCommonAPIs.getValue(rtp.getIpAddressXpath(WebportalParam.sw2deveiceName)).equals(ip2), "check ip 2");
        }
        handle.refresh();
    }
    
    @Step("Test Step 4: delete and add new IP2 to vlan 100 and check")
    public void step5() {
        rtp.deleteVlanRoute(vlanId);
        handle.waitCmdReady(ip3.substring(0, ip1.lastIndexOf(".")), true);
        
        rtp.addIpToVlan(vlanName, ipMask, ip3, ip4);
        handle.waitCmdReady(ip3.substring(0, ip3.lastIndexOf(".")), false);
        String tmpStr = MyCommonAPIs.getCmdOutput("show ip interface vlan " + vlanId, false);
        assertFalse(tmpStr.contains(ip1), "verify ip1 is removed");
        assertTrue(tmpStr.contains(ip3) || tmpStr.contains(ip4), "verify ip3/ip4 is added");
    }
}
