package webportal.SwitchManaged.VlanOverhauling.PRJCBUGEN_T11338;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String vlanName    = "testvlan";
    String vlanId      = "1336";
    String networkName = "testnet" + vlanId;

    @Feature("Switch.VlanOverhauling") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T11338") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("071- Try to create two voice/video networks") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T11338") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open page and goto Vlan Page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
    }

    @Step("Test Step 2: There is 2 default VLANs: Management VLAN 1 and VLAN2:")
    public void step2() {
        netsp.gotoPage();
        netsp.createNetwork("Voice VLAN", 1, "", "");
        netsp.gotoPage();
        netsp.createNetwork("Video VLAN", 2, "", "");
        netsp.clickAdd();
        netsp.lbNetType.selectOption(1);
        assertTrue(handle.getPageErrorMsg().contains("already exists"), "check voice vlan");
        netsp.lbNetType.selectOption(2);
        assertTrue(handle.getPageErrorMsg().contains("already exists"), "check video VLAN");
    }
}
