package webportal.RadioLevelConfigurations.Default.PRJCBUGEN_T24818;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshiwni K V
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("RadioLevelConfigurations") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T24818") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Advance Wireless Settings with  Enable / Disable toggle for each radio configuration section such as 2.4GHz / 5GHz Low / 5GHz High ") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T24818") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccount();
    }

    @Step("Test Step 2: Advance Wireless Settings default status;")
    public void step2() {
        
        new WirelessQuickViewPage().GoToWirelessSettings();
        MyCommonAPIs.sleepi(10);
        assertTrue(new WirelessQuickViewPage(false).EnableorDisable24(),"2.4 is not anabled by default");
        new WirelessQuickViewPage(false).DropDown5GhzLowWireless.click();       
        assertTrue(new WirelessQuickViewPage(false).EnableorDisable5Low(),"5GHlow is not enabled by default");
        new WirelessQuickViewPage(false).DropDown5GhzHighWireless.click();       
        assertTrue(new WirelessQuickViewPage(false).EnableorDisable5High(),"5GH High is not enabled by default");
    }

}
