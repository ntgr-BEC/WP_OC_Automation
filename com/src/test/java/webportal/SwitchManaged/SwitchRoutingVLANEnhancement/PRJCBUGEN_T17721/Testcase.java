package webportal.SwitchManaged.SwitchRoutingVLANEnhancement.PRJCBUGEN_T17721;

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

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String vlanName    = "testvlan";
    String vlanId      = "721";
    String networkName = "1";
    
    @Feature("Switch.SwitchRoutingVLANEnhancement") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T17721") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("002-Change Management VLAN mode from DHCP to Static on Network Setup page") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T17721") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        netsp.gotoPage();
        netsp.deleteAllNetwork();
        netsp.restoreVlan1toDHCP();
        ddpmg.gotoPage();
        ddpmg.waitDevicesReConnected(WebportalParam.sw1serialNo);
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open page and goto Vlan Page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
        handle.gotoLoction();
    }
    
    @Step("Test Step 2: Go to 'VLAN IP Configuration' step")
    public void step2() {
        netsp.gotoPage();
        netsp.openNetwork(networkName);
        netsp.gotoStep(4);
        netsp.setVlanIpMode(1);
        netsp.txtGateway.sendKeys("10.0.1.1");
        netsp.txtMask.sendKeys("255.255.255.0");
        netsp.setStaticIP(WebportalParam.sw1deveiceName, WebportalParam.sw1IPaddress);
        netsp.setStaticIP(WebportalParam.sw2deveiceName, WebportalParam.sw2IPaddress);
        netsp.setOtherIpAddress(WebportalParam.sw2IPaddress);
    }
    
    @Step("Test Step 3: set invalid parameter in 'VLAN IP Configuration' step -- ip")
    public void step3() {
        netsp.setStaticIP(WebportalParam.sw1deveiceName, "0.0.0.0");
        netsp.clickNextOrSkip(false);
        assertTrue(handle.getPageErrorMsg().length() > 0, "check invalid ip 0");
        
        netsp.setStaticIP(WebportalParam.sw1deveiceName, "255.255.255.255");
        netsp.clickNextOrSkip(false);
        assertTrue(handle.getPageErrorMsg().length() > 0, "check invalid ip 255.255");
        
        netsp.setStaticIP(WebportalParam.sw1deveiceName, "192.168.1.255");
        netsp.clickNextOrSkip(false);
        assertTrue(handle.getPageErrorMsg().length() > 0, "check invalid ip 1.255");
        
        // netsp.setStaticIP(WebportalParam.sw1deveiceName, "224.1.1.1");
        // netsp.clickNextOrSkip(false);
        // assertTrue(handle.getPageErrorMsg().length() > 0, "check invalid ip multicast");

        // restore
        netsp.setStaticIP(WebportalParam.sw1deveiceName, WebportalParam.sw1IPaddress);
    }
    
    @Step("Test Step 4: set invalid parameter in 'VLAN IP Configuration' step -- netmask")
    public void step4() {
        netsp.txtMask.clear();
        netsp.txtMask.sendKeys("0.0.255.255");
        netsp.clickNextOrSkip(false);
        assertTrue(handle.getPageErrorMsg().length() > 0, "check invalid mask 0.255");

        netsp.txtMask.clear();
        netsp.txtMask.sendKeys("0.0.0.0");
        netsp.clickNextOrSkip(false);
        assertTrue(handle.getPageErrorMsg().length() > 0, "check invalid mask 0.0");

        // restore
        netsp.txtMask.clear();
        netsp.txtMask.sendKeys("255.255.255.0");
    }

    @Step("Test Step 5: set invalid parameter in 'VLAN IP Configuration' step -- gw")
    public void step5() {
        netsp.txtGateway.clear();
        netsp.txtGateway.sendKeys("0.0.0.0");
        netsp.clickNextOrSkip(false);
        assertTrue(handle.getPageErrorMsg().length() > 0, "check invalid gw 0");
        
        netsp.txtGateway.clear();
        netsp.txtGateway.sendKeys("255.255.255.255");
        netsp.clickNextOrSkip(false);
        assertTrue(handle.getPageErrorMsg().length() > 0, "check invalid gw 255.255");
        
        netsp.txtGateway.clear();
        netsp.txtGateway.sendKeys("10.0.1.1");
    }
    
    @Step("Test Step 6: set invalid parameter in 'VLAN IP Configuration' step -- dns")
    public void step6() {
        // TODO
    }

    @Step("Test Step 7: set invalid parameter in 'VLAN IP Configuration' step -- IP and Gateway in different subnet")
    public void step7() {
        netsp.txtGateway.clear();
        netsp.txtGateway.sendKeys("10.0.1.1");
        netsp.setStaticIP(WebportalParam.sw1deveiceName, "10.0.2.2");
        netsp.clickNextOrSkip(false);
        assertTrue(handle.getPageErrorMsg().length() > 0, "check invalid subnet");
    }

    @Step("Test Step 8: 'VLAN IP Configuration' step, change mode from 'DHCP' to 'Static'")
    public void step8() {
        netsp.setNetwork4();
        netsp.finishAllStep();

        ddpmg.gotoPage();
        ddpmg.waitDevicesReConnected(WebportalParam.sw1serialNo);
        ddpmg.rebootSwitchDevice();
        ddpmg.waitDevicesReConnected(WebportalParam.sw1serialNo);

        ddpmg.gotoPage();
        String sStaticIp = handle.getVlan1StaticIp(false);
        String sStaticIpWP = ddpmg.getDeviceIP(WebportalParam.sw1serialNo);
        assertTrue(sStaticIpWP.equals(sStaticIp), String.format("check ip is expected on wp: %s/%s", sStaticIp, sStaticIpWP));
        assertTrue(SwitchCLIUtils.getVlan1Ip().contains(sStaticIp), String.format("check ip is expected on cli: %s/%s", sStaticIp, sStaticIpWP));
    }
}
