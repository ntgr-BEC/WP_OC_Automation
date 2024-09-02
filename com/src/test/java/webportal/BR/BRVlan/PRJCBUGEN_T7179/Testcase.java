package webportal.BR.BRVlan.PRJCBUGEN_T7179;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.BRUtils;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("BR.BRVlan") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7179") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("001-Verify all LAN port can get IP address on VLAN1 by default") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T7179") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: IM APP discover and manage BR500")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.openOneBRDevice(true);
    }

    @Step("Test Step 2: Set LAN IP(by default, it's VLAN1 IP address) and enable DHCP Server,\r\n"
            + "connect client to all 4 LAN port, check client IP status;")
    public void step2() {
        brddchps.gotoPage();
        if (!brddchps.getListIP().contains(WebportalParam.br1IPaddress)) {
            brddchps.setDHCP("1", WebportalParam.br1IPaddress, true);
        }

        handle.waitRestReady(BRUtils.api_lan_ip_settings, WebportalParam.br1IPaddress, false, 0);
        assertTrue(new BRUtils().Dump().contains(WebportalParam.br1IPaddress), "vlan 1 dhcp is not set");
    }

}
