package webportal.SwitchManaged.ACL.PRJCBUGEN_T4979;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

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

    String vlanName = "testvlan";
    String vlanId   = "200";
    String tmpStr;

    String       ipaclName    = "test";
    String       ipaclIp      = "11.1.1.1";
    String       ipaclMac     = "11:22:33:11:22:33";
    List<String> lsExpectACL  = new ArrayList<String>();
    List<String> lsExpectVlan = new ArrayList<String>();

    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4979") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("072-Deploy max(26) MAC ACL for one vlan") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4979") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p5") // "p3"
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        wvp.deleteAllVlan();
        handle.sleep(120, "wait all clean sync");
        handle.waitCmdReady(vlanName, true);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login WP")
    public void step1() {
        final WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wvp.gotoPage();
        wvp.deleteAllVlan();
        wvp.deleteAllVlanCli();

        wvp.gotoPage();
        wvp.openVlan(vlanName, vlanId, 0);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: Create one vlan,deploy 26 MAC ACL for this vlan")
    public void step2() {
        for (int j = 1; j <= 26; j++) {
            ipaclMac = String.format("aa:bb:cc:dd:ee:%02d", j);
            lsExpectACL.add(ipaclMac);
            System.out.printf("create mac acl: %s", ipaclMac);
            wvp.editVlanMacAuthAllow(vlanName, ipaclName, ipaclMac);
        }
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: Deploy 27th MAC ACL for vlan200")
    public void step4() {
        wvp.gotoPage();
        wvp.openVlan(vlanName, vlanId, 0);

        ipaclMac = String.format("aa:bb:cc:dd:ee:ff");
        wvp.editVlanMacAuthAllow(vlanName, ipaclName, ipaclMac);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 4: webportal app pop up error message")
    public void step5() {
        String sRet = handle.getPageErrorMsg();
        assertTrue(sRet.contains("max"));
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 5: verify all acl")
    public void step6() {
        handle.waitCmdReady(lsExpectACL.get(lsExpectACL.size() - 1), false);
        tmpStr = handle.getCmdOutputShowRunningConfig(false);
        for (String acl : lsExpectACL) {
            assertTrue(tmpStr.toLowerCase().contains(acl), acl);
        }
    }

}
