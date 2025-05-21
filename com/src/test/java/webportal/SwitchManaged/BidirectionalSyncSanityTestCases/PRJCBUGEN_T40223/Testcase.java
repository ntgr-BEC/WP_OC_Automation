package webportal.SwitchManaged.BidirectionalSyncSanityTestCases.PRJCBUGEN_T40223;

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

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {


    @Feature("Switch.BidirectionalSyncSanityTestCases") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T40223") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify enable MAC address validation from local UI and the changes should not reflect in Insight portal") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T40223") // It's a testcase id/link from Jira Test Case.
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
    @Step("Test Step 2: create a ip acl: option select Allow access from this device")
    public void step2() {
         new SwitchGUIPage().openLocalGUI(WebportalParam.sw1IPaddress, WebportalParam.loginDevicePassword, WebportalParam.sw1Model);        
         new SwitchGUIPage().MACAddressValidation(WebportalParam.sw1Model, true);
    }

    @Step("Test Step 3: Verify in insight")
    public void step3() {
        handle.gotoLocationWireSettings();
        wdsp.gotoDhcpSnoopingConfigPage(WiredDhcpSnoopingElement.dhcpSnoopingGlobalConfig);
        MyCommonAPIs.sleepi(5);
        assertTrue(WiredDhcpSnoopingElement.dhcpSnoopingMACAddressValidationbutton.isSelected(), "MAC addres Mode not disabled");
        
    }
}
