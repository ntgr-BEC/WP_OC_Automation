package webportal.ProManagedSwitch.Port.PRJCBUGEN_T4902;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesSwitchConnectedNeighboursPortConfiqSummaryPage;
import webportal.weboperation.DevicesSwitchSummaryPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author Sumanta
 */
public class Testcase extends TestCaseBase implements Config {
    public String expectValue = "";
    public String portNo      = "g1";
    
    @Issue("PRJCBUGEN-23134")
    @Feature("Switch.Port") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4902") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("026-Get port vlan statistics") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4902") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p1")
    public void test() throws Exception {
        runTest(this);
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open wired vlan")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
        
        handle.gotoLoction();
        wvp.gotoPage();
    }
    
    @Step("Test Step 2: Remove port 1 from every vlan")
    public void step2() {
        wvp.editVlanPorts("1", WebportalParam.sw1deveiceName, "", "", "1", false);
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: Cehck port 1 vlan information \"Devices/Connected Neighbour/Port/Vlan ID\"")
    public void step3() {
        MyCommonAPIs.sleepsync();
        MyCommonAPIs.sleepsync();
        ddpmg.gotoPage();
        ddpmg.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
        DevicesSwitchSummaryPage devicesSwitchSummaryPage = new DevicesSwitchSummaryPage();
        DevicesSwitchConnectedNeighboursPortConfiqSummaryPage devicesSwitchConnectedNeighboursPortConfiqSummaryPage = devicesSwitchSummaryPage
                .enterPortConfigSummary("1");
        assertTrue(!devicesSwitchConnectedNeighboursPortConfiqSummaryPage.getVlanId().startsWith("1"), "vlan 1 is removed");
    }
    
    @AfterMethod(alwaysRun = true)
    public void restore() {
        wvp.gotoPage();
        wvp.editVlanPorts("1", WebportalParam.sw1deveiceName, "1", "", "", false);
    }
}
