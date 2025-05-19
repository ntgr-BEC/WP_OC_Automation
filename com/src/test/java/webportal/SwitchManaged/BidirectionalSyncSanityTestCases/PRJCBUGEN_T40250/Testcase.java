package webportal.SwitchManaged.BidirectionalSyncSanityTestCases.PRJCBUGEN_T40250;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.WiredDhcpSnoopingElement;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.DevicesDashPageMNG;
import webportal.weboperation.DevicesSwitchConnectedNeighboursPortConfiqSettingsPage;
import webportal.weboperation.DevicesSwitchSummaryPage;
import webportal.weboperation.SwitchGUIPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredVLANPageForVLANPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    
    String Dis = "auto";

    @Feature("Switch.BidirectionalSyncSanityTestCases") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T40250") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to reboot the device post applying configurations like VLAN, DHCP port configurations etc.. and the configurations should not be overridden by network level operations") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T40250") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p3") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("tare down");
       
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: create a vlan")
    public void step1() {       
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();       
        handle.gotoLoction();  
        
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: create Port Discription")
    public void step2() {
         new SwitchGUIPage().openLocalGUI(WebportalParam.sw1IPaddress, WebportalParam.loginDevicePassword, WebportalParam.sw1Model);
         new SwitchGUIPage().gotoswitchportconfig(WebportalParam.sw1Model,WebportalParam.sw1LagPort1CLI, Dis );
         MyCommonAPIs.sleepi(30);
         
    }
    
    @Step("Test Step 3: Reboot switch")
    public void step3() {
        ddp.gotoPage();
        ddp.waitDevicesReboot(WebportalParam.sw1serialNo);
        
    }
    
    @Step("Test Step 4: Verify in insight")
    public void step4() {
        new DevicesDashPageMNG().enterDevice(WebportalParam.sw1serialNo);
        DevicesSwitchSummaryPage devicesSwitchSummaryPage = new DevicesSwitchSummaryPage();
        String port = new WebportalParam().getPlainSwitchPort(WebportalParam.sw1Model,WebportalParam.sw1LagPort1CLI);
        devicesSwitchSummaryPage.enterPortConfigSummary(port);
        
        
        DevicesSwitchConnectedNeighboursPortConfiqSettingsPage devicesSwitchConnectedNeighboursPortConfiqSettingsPage = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        String PostDis = devicesSwitchConnectedNeighboursPortConfiqSettingsPage.getPortDescription();
        System.out.println(PostDis);
        
        assertTrue(PostDis.contains(Dis), "device is added in insight");
       
    }
}
