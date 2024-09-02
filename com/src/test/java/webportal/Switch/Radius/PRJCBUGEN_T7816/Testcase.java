package webportal.Switch.Radius.PRJCBUGEN_T7816;

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
import util.SwitchCLIUtils;
import util.SwitchTelnet;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String vlanName = "testvlan";
    String vlanId   = "816";
    String ip1      = "11.22.33.44";
    String ip2      = "11.22.33.45";
    
    @Feature("Switch.Radius") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7816") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("010 - Enable/Disable Radius configuration per switch on vlan level") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T7816") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1")
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        rcp.gotoPage();
        rcp.disableRadius(false);
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
        handle.gotoLoction();
    }
    
    @Step("Test Step 2: Go to Location->Edit Location-> RADIUS")
    public void step2() {
        rdp.gotoPage();
        rdp.enableAuth(ip1, ip2);
    }
    
    @Step("Test Step 3: Go Enable radius server option on VLAN200 level")
    public void step3() {
        wvp.gotoPage();
        wvp.newVlan(vlanName, vlanId, 0);
        wvp.editPortMode(vlanId, true, false, false);
    }
    
    @Step("Test Step 4: Go to local switch GUI, Radius server info is deployed and enable on VLAN200")
    public void step4() {
        handle.waitCmdReady(ip1, false);
        String tmpStr = SwitchCLIUtils.getRadiusInfo("g" + WebportalParam.sw1LagPort1);
        assertTrue(SwitchCLIUtils.RadiusClass.portStatus == 1, "check radius option on g4 of sw1");
        
        SwitchTelnet switchTelnet = new SwitchTelnet(WebportalParam.sw2IPaddress, false);
        SwitchCLIUtils.RadiusClass.init(switchTelnet, switchTelnet.getCLICommand("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw2Model, WebportalParam.sw2LagPort1)));
        assertTrue(SwitchCLIUtils.RadiusClass.portStatus == 2, "check radius option on g4 of sw2");
    }
    
    @Step("Test Step 5: Disable radius server option on VLAN200 level")
    public void step5() {
        wvp.gotoPage();
        wvp.openVlan(vlanName, vlanId, 0);
        wvp.showPortMember();
        wvp.setRadiusConfig(false);
        handle.clickButton(0);
        MyCommonAPIs.sleepsync();
    }
    
    @Step("Test Step 6: Go to local switch GUI, Radius server info is deployed and enable on VLAN200")
    public void step6() {
        handle.waitCmdReady(ip1, false);
        String tmpStr = SwitchCLIUtils.getRadiusInfo("g" + WebportalParam.sw1LagPort1);
        assertTrue(!SwitchCLIUtils.RadiusClass.isEnabled, "check radius option");
    }
    
}
