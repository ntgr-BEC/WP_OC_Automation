package webportal.Switch.ACL.PRJCBUGEN_T4976;

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
    String tclname = getClass().getName();

    String                vlanName = "testvlan";
    String                vlanId   = "200";
    String                tmpStr;

    String ipaclName = "test";
    String ipaclIp   = "11.1.1.1";
    String ipaclMac  = "11:22:33:11:22:33";

    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4976") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("069-Deploy max(26) IP ACL for one vlan") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4976") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p5") // "p3"
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        wvp.gotoPage();
        wvp.deleteAllVlan();
        wvp.removeAllAclCli();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login WP")
    public void step1() {
        final WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wvp.gotoPage();
        wvp.openVlan(vlanName, vlanId, 0);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: Create one vlan,deploy 26 IP ACL for this vlan")
    public void step2() {
        for (int i = 1; i <= 26; i++) {
            ipaclIp = String.format("200.1.1.%d", i);
            System.out.printf("create ip acl: %s", ipaclIp);
            wvp.editVlanIpFilteringAllow(vlanName, ipaclName, ipaclIp);
        }
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: Deploy 27th IP ACL for vlan200")
    public void step4() {
        ipaclIp = String.format("200.1.1.200");
        wvp.editVlanIpFilteringAllow(vlanName, ipaclName, ipaclIp);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 4: webportal app pop up error message")
    public void step5() {
        assertTrue(handle.getPageErrorMsg().contains("max vlan acl limit"));
    }

}
