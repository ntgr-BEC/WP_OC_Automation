package webportal.Switch.ACL.PRJCBUGEN_T4951;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.SwitchCLIUtils;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    String tclname = getClass().getName();

    String vlanName = "testvlan";

    String ipaclName = "test";
    String ipaclIp   = "20.1.1.1";
    String expStr1   = "i20111000020";
    String expStr2   = "i201110000202";

    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4951") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("007-Test IP ACL name") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4951") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p3") // Use p1/p2/p3 to high/normal/low on priority
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
    @Step("Test Step 1: create a vlan")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wvp.gotoPage();
        wvp.openVlan(vlanName, "20", 0);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: ​Option select \"Allow access from this device\" and \"Allow access to this\n" + "device")
    public void step2() {
        wvp.editVlanIpFilteringAllow(vlanName, ipaclName, ipaclIp);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: ​​​​​Generate 2 ip acl on switch,the IP acl name format is\n"
            + "\"i+ip+mask+vlan+number\",so the name is \"i20111000020\" and \"i201110000202\"")
    public void step3() {
        String sRet = handle.waitCmdReady(expStr2, false);
        sRet = SwitchCLIUtils.getIpMACACL(true, "20");
        assertTrue(sRet.contains(expStr1));
        assertTrue(sRet.contains(expStr2));
    }
}
