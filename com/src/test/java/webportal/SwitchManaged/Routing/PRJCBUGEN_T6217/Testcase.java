package webportal.SwitchManaged.Routing.PRJCBUGEN_T6217;

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
    String vlanName1 = "testvlan1";
    String vlanId1   = "1217";
    String vlanName2 = "testvlan2";
    String vlanId2   = "1218";
    String ipMask    = "255.255.255.0";
    String ip1       = "100.1.1.1";
    String ip2       = "100.1.1.2";
    String ip3       = "101.1.1.3";
    String ip4       = "101.1.1.4";
    String ip5       = "101.1.1.5";
    
    @Feature("Switch.Routing") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T6217") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("002-Add ip address when there is ip conflict") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T6217") // It's a testcase id/link from Jira Test Case.
    
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
            wvp.deleteAllVlanCli();
        }
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create a new vlan 100 and 200")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
        handle.gotoLoction();
        handle.gotoLocationWireSettings();
        
        wvp.gotoPage();
        wvp.newVlan(vlanName1, vlanId1, 0);
        
        wvp.gotoPage();
        wvp.newVlan(vlanName2, vlanId2, 0);
        
        new AccountPage().enterEditNetworkPage();
        rtp.gotoPage();
    }
    
    @Step("Test Step 2: Add IP1 to vlan 100")
    public void step2() {
        rtp.addIpToVlan(vlanName1, ipMask, ip1, ip2);
    }
    
    @Step("Test Step 3: Add same IP2 to vlan 200 and check msg")
    public void step3() {
        rtp.addIpToVlan(vlanName2, ipMask, ip3, ip2);
        if (!rtp.isRoutingDisabled(WebportalParam.sw2Model)) {
            rtp.openVlan(vlanName2);
            assertTrue(MyCommonAPIs.getValue(rtp.getIpAddressXpath(WebportalParam.sw2deveiceName)).length() == 0,
                    "check ip2 should not be set " + ip2);
            handle.clickBoxFirstButton();
        }
        rtp.addIpToVlan(vlanName2, ipMask, ip4, ip5);
    }
    
    @Step("Test Step 4: Apply IP2 successfully")
    public void step4() {
        rtp.openVlan(vlanName2);
        assertTrue(rtp.seMask.getValue().equals(ipMask), "check mask");
        assertTrue(MyCommonAPIs.getValue(rtp.getIpAddressXpath(WebportalParam.sw1deveiceName)).equals(ip4), "check ip " + ip4);
        if (!rtp.isRoutingDisabled(WebportalParam.sw2Model)) {
            assertTrue(MyCommonAPIs.getValue(rtp.getIpAddressXpath(WebportalParam.sw2deveiceName)).equals(ip5), "check ip " + ip5);
        }
        handle.clickBoxFirstButton();
    }
    
    @Step("Test Step 5: Check configuraiton from app and local web gui")
    public void step5() {
        MyCommonAPIs.waitCliReady("show running-config", ip4, false);
        String tmpStr = MyCommonAPIs.getCmdOutput("show running-config", false);
        assertTrue(tmpStr.contains(ip1) || tmpStr.contains(ip2), "verify ip is set in vlan1");
        assertTrue(tmpStr.contains(ip4) || tmpStr.contains(ip5), "verify ip is set in vlan2");
    }
}
