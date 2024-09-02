package webportal.Switch.ACL.PRJCBUGEN_T4974;

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

    String       vlanName     = "testvlan";
    String       tmpStr;
    List<String> lsExpectACL  = new ArrayList<String>();
    List<String> lsExpectVlan = new ArrayList<String>();

    String ipaclName = "test";
    String ipaclIp   = "11.1.1.1";
    String ipaclMac  = "11:22:33:11:22:33";

    @Feature("Switch.ACL") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4974") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("066-Create Max(100) IP ACLs") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4974") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p5") // "p3"
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        try {
            wvp.gotoPage();
            for (String s : lsExpectVlan) {
                wvp.deleteVlan(s, false);
            }
            handle.sleep(120, "wait all clean sync");
            handle.waitCmdReady(vlanName, true);
        } catch (Throwable e) {
            e.printStackTrace();
        }
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
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: Create 10 vlans,one vlan include 4 IP address")
    public void step2() {
        for (int i = 0; i < 10; i++) {
            wvp.gotoPage();
            vlanName = String.format("10%d", i);
            lsExpectVlan.add(vlanName);
            System.out.printf("create vlan: %s", vlanName);
            wvp.openVlan(vlanName, vlanName, 0);
            for (int j = 1; j < 5; j++) {
                ipaclIp = String.format("100.1.%d.%d", i, j);
                lsExpectACL.add(ipaclIp);
                System.out.printf("create ip acl: %s", ipaclIp);
                wvp.editVlanIpFilteringAllow(vlanName, ipaclName, ipaclIp);
            }
        }
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: Create 5 vlans,one vlan include 2 IP address")
    public void step3() {
        for (int i = 0; i < 5; i++) {
            wvp.gotoPage();
            vlanName = String.format("11%d", i);
            lsExpectVlan.add(vlanName);
            System.out.printf("create vlan: %s", vlanName);
            wvp.openVlan(vlanName, vlanName, 0);
            for (int j = 1; j < 3; j++) {
                ipaclIp = String.format("110.1.%d.%d", i, j);
                lsExpectACL.add(ipaclIp);
                System.out.printf("create ip acl: %s", ipaclIp);
                wvp.editVlanIpFilteringAllow(vlanName, ipaclName, ipaclIp);
            }
        }
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 4: Create 1 vlan that include 1 IP ACL")
    public void step4() {
        vlanName = "120";
        wvp.gotoPage();
        lsExpectVlan.add(vlanName);
        wvp.openVlan(vlanName, vlanName, 0);

        ipaclIp = String.format("120.1.1.1");
        wvp.editVlanIpFilteringAllow(vlanName, ipaclName, ipaclIp);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 5: webportal app pop up error message")
    public void step5() {
        handle.clickBoxLastButton();
        assertTrue(handle.getPageErrorMsg().contains("max"), "check error msg with Switch Max Acl exceeded");
    }

    @Step("Test Step 6: verify all acl")
    public void step6() {
        handle.waitCmdReady(lsExpectACL.get(lsExpectACL.size() - 1), false);
        tmpStr = handle.getCmdOutputShowRunningConfig(false);
        for (String acl : lsExpectACL) {
            assertTrue(tmpStr.contains(acl), "check acl: " + acl);
        }
    }
}
