package webportal.ProManagedSwitch.VLAN.PRJCBUGEN_T4709;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredQuickViewPage;
import webportal.weboperation.WiredVLANPage;
import webportal.weboperation.WiredVLANPageForVLANPage;

/**
 *
 * @author sumanta
 *
 */
public class Testcase extends TestCaseBase implements Config {
    public boolean Result = true;

    @Feature("Switch.VLAN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4709") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("012-Create vlan 0 vlan 4093 and vlan 4094") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4709") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p3")
    public void test() throws Exception {
        runTest(this);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: enter setting page")
    public void step1() {
        // link up dut 1 port1
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
        handle.gotoLoction();

    }

    @Step("Test Step 2: Create vlan with id 0 from Web Portal.")
    public void step2() {
        netsp.gotoPage();
        netsp.clickAdd();
        netsp.setNetwork1("testnet", "", 0, "vlan0", "0");

        String result1 = handle.getPageErrorMsg();
        System.out.println("getPageErrorMsg:" + result1);
        if (result1.length() > 0) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 1 Fail:add vlan 0 getPageErrorMsg is:" + result1);
        }
    }

    @Step("Test Step 3: Create vlan with id 4094 from Web Portal.")
    public void step3() {
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPage wiredVLANPage = new WiredVLANPage(false);

        netsp.gotoPage();
        netsp.clickAdd();
        netsp.setNetwork1("testnet", "", 0, "vlan4094", "4094");

        String result1 = handle.getPageErrorMsg();
        System.out.println("getPageErrorMsg:" + result1);
        if (result1.length() > 0) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 2 Fail:add vlan 4093 getPageErrorMsg is:" + result1);
        }
    }

    @Step("Test Step 4: Create vlan with id 4093 from Web Portal.")
    public void step4() {
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPage wiredVLANPage = new WiredVLANPage(false);

        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.addCustomVlanWithPorts("vlan4093", "4093", null, null, null, null, null, null, null);
        WiredVLANPageForVLANPage vlanPage1 = new WiredVLANPageForVLANPage();
        MyCommonAPIs.sleep(2000);

        List<String> vlans = vlanPage1.getVlans();
        MyCommonAPIs.sleep(3000);

        System.out.println("vlan is:" + vlans);
        if (vlans.contains("vlan4093")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "------Check Point 3:vlan4093 is added right:" + vlans);
        }
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.deleteAllVlan();
    }

}
