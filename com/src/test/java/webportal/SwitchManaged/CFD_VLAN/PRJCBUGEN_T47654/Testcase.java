package webportal.SwitchManaged.CFD_VLAN.PRJCBUGEN_T47654;

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
import util.SwitchCLIUtils;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.DevicesSwitchConnectedNeighboursPortConfiqSettingsPage;
import webportal.weboperation.DevicesSwitchSummaryPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredGroupPortConfigPage;
import webportal.weboperation.WiredQuickViewPage;
import webportal.weboperation.WiredVLANPage;
import webportal.weboperation.WiredVLANPageForVLANPage;

/**
 * @author Tejeshwinin K V
 */
public class Testcase extends TestCaseBase  {
    public boolean Result = true;
    String         vlanId = "100";
    
    @Feature("Switch.CFD_VLAN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T41654") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Onboard Smart switch in Day-0 to insight location with default configuration") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T41654") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p1")
    public void test() throws Exception {
        runTest(this);
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to Page ")
    public void step1() {
        // link up dut 1 port1
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();
//        wvp.gotoPage();
//        wvp.deleteAllVlan();
//        wvp.deleteAllVlanCli();

    }
    
    @Step("Test Step 2: Reset the switch and on  board")
    public void step2() {

        new DevicesDashPage().restoreDevice(WebportalParam.sw1serialNo);

        WebCheck.checkHrefIcon(URLParam.hrefDevices);

        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.sw1serialNo);
        devInfo.put("MAC Address1", WebportalParam.sw1MacAddress);

        new DevicesDashPage(false).addNewDevice(devInfo);

        new DevicesDashPage().waitDevicesReConnected(WebportalParam.sw1serialNo);

    }
    
    
//

    
}
