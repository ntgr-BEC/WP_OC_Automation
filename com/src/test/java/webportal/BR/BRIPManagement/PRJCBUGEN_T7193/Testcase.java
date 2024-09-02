package webportal.BR.BRIPManagement.PRJCBUGEN_T7193;

import static org.testng.Assert.assertFalse;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String sExpect  = "192.192.192.1";
    String vlanName = "testvlan";
    String vlanId   = "1192";

    @Feature("BR.BRIPManagement") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7193") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("007-Disable LAN1 DHCP Server") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T7193") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        handle.refresh();
        brddchps.gotoPage();
        brddchps.setDHCP("1", true);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: IM APP discover and manage BR500")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.openOneBRDevice(true);
    }

    @Step("Test Step 2: Go to BR-->DHCP Servers page, disable LAN1 DHCP Server;")
    public void step2() {
        brddchps.gotoPage();
        brddchps.setDHCP("1", false);
    }

    @Step("Test Step 3: Deploy successfully, check via Insight and BR GUI;")
    public void step3() {
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.br1serialNo);
        handle.openOneBRDevice(true);
        brddchps.gotoPage();
        brddchps.openOne("1");

        assertFalse(brddchps.txtIpStart.exists(), "dhcp is not disabled");
    }
}
