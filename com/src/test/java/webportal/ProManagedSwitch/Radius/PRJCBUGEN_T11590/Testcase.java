package webportal.SwitchManaged.Radius.PRJCBUGEN_T11590;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.SwitchCLIUtils;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredQuickViewPage;
import webportal.weboperation.WiredVLANPageForVLANPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String vlanName    = "testvlan";
    String vlanId      = "1590";
    String networkName = "testnet" + vlanId;
    String ip1         = "11.22.33.44";
    String ip2         = "11.22.33.45";
    
    @Feature("Switch.Radius") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T11590") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("016 - Enable/Disable Radius configuration per switch under network setup") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T11590") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1")
    public void test() throws Exception {
        ip1 = handle.getRandomIp();
        ip2 = handle.getRandomIp();
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        SwitchCLIUtils.setSwitchInfo(WebportalParam.sw1IPaddress, WebportalParam.sw1Model);
        netsp.gotoPage();
        netsp.deleteAllNetwork();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        SwitchCLIUtils.forceMangeportAuth();
        SwitchCLIUtils.cleanRadius();
    }

    @Step("Test Step 2: Go to Location->Edit Location-> RADIUS")
    public void step2() {
        rdp.gotoPage();
        rdp.enableAuth(ip1, ip2);
    }

    @Step("Test Step 3: Go to Network Setup, create VLAN200 (select 5 ports of switch A, 10 ports of switch B)")
    public void step3() {
        netsp.gotoPage();
        netsp.createNetwork(networkName, vlanId, true);
    }
    
    @Step("Test Step 4: Go to local switch GUI, Radius server info is deployed and enable on VLAN200")
    public void step4() {
        String tmpStr = SwitchCLIUtils.getRadiusInfo("g1");
        assertTrue(SwitchCLIUtils.RadiusClass.isServerConfiged, "check radius server option");
//        assertTrue(SwitchCLIUtils.RadiusClass.isEnabled, "check dot1x option");
        assertTrue(SwitchCLIUtils.RadiusClass.portStatus == 1, "check 1st sw port mode g1");
        // tmpStr = SwitchCLIUtils.getRadiusInfo("g3");
        // assertTrue(SwitchCLIUtils.RadiusClass.portStatus != 1, "check 1st sw port mode g3");
        
        SwitchCLIUtils.setSwitchInfo(WebportalParam.sw2IPaddress, WebportalParam.sw2Model);
        tmpStr = SwitchCLIUtils.getRadiusInfo("g3");
        assertTrue(SwitchCLIUtils.RadiusClass.portStatus == 1, "check 2nd sw port mode g3");
        // tmpStr = SwitchCLIUtils.getRadiusInfo("g1");
        // assertTrue(SwitchCLIUtils.RadiusClass.portStatus != 1, "check 2nd sw port mode g1");
    }
    
    @Step("Test Step 5: Go to Network setup,edit VLAN200,disable radius server option.")
    public void step5() {
        netsp.gotoPage();
        netsp.openNetwork(networkName);
        netsp.gotoStep(2);
        netsp.enableRadius(WebportalParam.sw1deveiceName, false);
        netsp.enableRadius(WebportalParam.sw2deveiceName, false);
        netsp.finishAllStep();
    }
    
//    @Step("Test Step 6: Go to local switch GUI, Radius server info is deployed and disable on VLAN200")
//    public void step6() {
//        SwitchCLIUtils.setSwitchInfo(WebportalParam.sw1IPaddress, WebportalParam.sw1Model);
//        SwitchCLIUtils.getRadiusInfo("g1");
//        assertTrue(!SwitchCLIUtils.RadiusClass.isEnabled, "check dot1x option on 1st switch");
//        
//        SwitchCLIUtils.setSwitchInfo(WebportalParam.sw2IPaddress, WebportalParam.sw2Model);
//        SwitchCLIUtils.getRadiusInfo("g1");
//        assertTrue(!SwitchCLIUtils.RadiusClass.isEnabled, "check dot1x option on 2nd switch");
//    }
    
    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.deleteAllVlan();
    }
}
