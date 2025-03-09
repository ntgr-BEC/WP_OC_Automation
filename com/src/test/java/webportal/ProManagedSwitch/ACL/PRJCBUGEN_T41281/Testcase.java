package webportal.ProManagedSwitch.ACL.PRJCBUGEN_T41281;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.SwitchCLIUtils;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author Sumanta
 *
 */
public class Testcase extends TestCaseBase {
    String tclname = getClass().getName();

    String        vlanName = "testvlan";

    String ipaclName = "test";
    String ipaclIp   = "11.11.11.11";

    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T41281") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("060-Set MAC ACL when the switch is down") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T41281") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p3") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        SwitchCLIUtils.CloudModeSet(true);
        wvp.gotoPage();
        wvp.deleteAllVlan();
        wvp.removeAllAclCli();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create vlan50 on switch")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wvp.gotoPage();
        wvp.openVlan(vlanName, "50", 0);
    }

    @Step("Test Step 2: Put DUT out of internet")
    public void step2() {
        SwitchCLIUtils.CloudModeSet(false);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: add an acl while dut was offline")
    public void step3() {
        wvp.editVlanIpFilteringAllow(vlanName, ipaclName, ipaclIp);

        Selenide.refresh();
    }

    @Step("Test Step 4: Put DUT back to internet")
    public void step4() {
        SwitchCLIUtils.CloudModeSet(true);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 5: ​​check acl")
    public void step5() {
        String sRet = handle.waitCmdReady(ipaclIp, false);
        assertTrue(sRet.contains(ipaclIp), "check for " + ipaclIp);
    }

}
