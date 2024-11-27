package webportal.SwitchManaged.System.OnPoESwitch.PRJCBUGEN_T4698;

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
import webportal.param.WebportalParam;
import webportal.publicstep.PublicButton;
import webportal.weboperation.DevicesDashPageMNG;
import webportal.weboperation.DevicesSwitchConnectedNeighboursPortConfiqSettingsPage;
import webportal.weboperation.DevicesSwitchSummaryPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase implements Config {
    public String expectValue = "";
    
    String sPortDesc  = "test123";
    String sFrameSize = "0";
    
    String vlanName    = "testvlan";
    String vlanId      = "4088";
    String networkName = "testnet" + vlanId;
    
    @Feature("Switch.System") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4698") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("039- Deploy \"Reload\" to Switch with group port configuration") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4698") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p4") // p2
    public void test() throws Exception {
        runTest(this);
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: enter setting page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
        handle.gotoLoction();
        handle.gotoLocationWireSettings();
        
        netsp.gotoPage();
        netsp.createNetwork(networkName, 1, vlanName, vlanId);
        handle.waitCmdReady(vlanId, false);
        
        DevicesDashPageMNG ddPage = new DevicesDashPageMNG();
        ddPage.openPoEDevice();
    }
    
    @Step("Test Step 2: enable port1 and set port description (e.g. test123)")
    public void step2() {
        DevicesSwitchSummaryPage DSSPage = new DevicesSwitchSummaryPage();
        DSSPage.portChoice("1").click();
        
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page1 = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        page1.modifyPortDescription(sPortDesc);
        page1.enablePort();
    }
    
    @Step("Test Step 3: disable port2")
    public void step3() {
        DevicesSwitchSummaryPage DSSPage = new DevicesSwitchSummaryPage();
        DSSPage.portChoice("2").click();
        
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page1 = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        page1.disablePort();
    }
    
    @Step("Test Step 4: enable port3 PoE and set power limit 10W")
    public void step4() {
        DevicesSwitchSummaryPage DSSPage = new DevicesSwitchSummaryPage();
        DSSPage.portChoice("3").click();
        
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page1 = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        page1.enablePort();
        page1.setPoEPort(true);
        page1.setPOEValue("Class0");
    }
    
    @Step("Test Step 5: disable port4 PoE")
    public void step5() {
        DevicesSwitchSummaryPage DSSPage = new DevicesSwitchSummaryPage();
        DSSPage.portChoice("4").click();
        
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page1 = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        page1.enablePort();
        page1.setPoEPort(false);
    }
    
    @Step("Test Step 6: set rate egress limit and storm control rate on port5")
    public void step6() {
        DevicesSwitchSummaryPage DSSPage = new DevicesSwitchSummaryPage();
        DSSPage.portChoice("5").click();
        
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page1 = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        page1.enablePort();
        handle.setExpand(page1.btnRateLimit, false);
        handle.setSlider(page1.stormSlider.getSearchCriteria(), "50");
        handle.setSlider(page1.egressSlider.getSearchCriteria(), "50");
        page1.clickSave();
    }
    
    @Step("Test Step 7: set PVID, duplex mode, max frame size and port speed on port6")
    public void step7() {
        DevicesSwitchSummaryPage DSSPage = new DevicesSwitchSummaryPage();
        DSSPage.portChoice("6").click();
        
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page1 = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
//        page1.lbVlanPVID.selectOption(1);
        page1.setPortPVID(vlanId);
        page1.enablePort();
        page1.setDeplexMode("Full");
        sFrameSize = page1.modifyMaxFrameSizeRandom();
        System.out.println("sFrameSize"+sFrameSize);
        page1.setPortSpeed("10 Mbps");
        page1.clickSave();
    }
    
    @Step("Test Step 8: Deploy \"Factory Default\" command from Web Portal to Switch")
    public void step8() {
        new DevicesDashPageMNG().enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
        new PublicButton().rebootDevice();
//        MyCommonAPIs.waitDeviceOnlineReload();
    }
    
    @Step("Test Step 9: After switch reload, check switch info on Web Portal and device Web GUI")
    public void step9() {
        String tmpStr1, tmpStr2, tmpStr3, tmpStr4, tmpStr5, tmpStr6;
        tmpStr1 = SwitchCLIUtils.getPortInfo("g1");
        assertTrue(tmpStr1.contains(sPortDesc), "check: " + sPortDesc);
        
        tmpStr2 = SwitchCLIUtils.getPortInfo("g2");
        assertTrue(SwitchCLIUtils.PortClass.isShutdown, "check shutdown");
        
        tmpStr3 = SwitchCLIUtils.getPortInfo("g3");
        assertTrue(tmpStr3.contains("15400"), "check power limit 15.4w");
        
        tmpStr4 = SwitchCLIUtils.getPoEInfo("g4");
        assertTrue(!SwitchCLIUtils.PoEClass.isEnabled, "check status Disabled");
        
        tmpStr5 = SwitchCLIUtils.getPortInfo("g5");
        assertTrue(SwitchCLIUtils.PortClass.sPortStormControlRate.contains("50"), "check storm-control broadcast level 50");
        assertTrue(SwitchCLIUtils.PortClass.sPortEgressRate.contains("50"), "check traffic-shape 50");
        
        tmpStr6 = SwitchCLIUtils.getPortInfo("g6");
        assertTrue(tmpStr6.contains("pvid 4088"), "check vlan pvid 4089");
        assertTrue(SwitchCLIUtils.PortClass.duplexMode == 1, "check speed 10");
        assertTrue(SwitchCLIUtils.PortClass.sPortSpeed.contains("10"), "check full-duplex");
        assertTrue(SwitchCLIUtils.PortClass.sPortFramesize.contains(sFrameSize), "check mtu size: " + sFrameSize);
    }
    
    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        WebportalParam.updateSwitchOneOption(false, null);
        try {
            DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
            devicesDashPage.waitAllSwitchDevicesConnected();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        DevicesSwitchSummaryPage DSSPage = new DevicesSwitchSummaryPage();
        DSSPage.portChoice("2").click();
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        page.enablePort();
    }
}
