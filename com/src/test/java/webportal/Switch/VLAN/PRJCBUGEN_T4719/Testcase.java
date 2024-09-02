package webportal.Switch.VLAN.PRJCBUGEN_T4719;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import util.SwitchTelnet;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredQuickViewPage;
import webportal.weboperation.WiredVLANPage;
import webportal.weboperation.WiredVLANPageForVLANPage;

/**
 *
 * @author xuchen
 *
 */
public class Testcase extends TestCaseBase implements Config {
    public boolean Result   = true;
    String         vlanName = "abcdDfhjk1234 j6-hB198fffafkljk";

    @Feature("Switch.VLAN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4719") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("007-Create a vlan and change its name and ID") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4719") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    @Issue("PRJCBUGEN-16160")
    public void test() throws Exception {
        runTest(this);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: enter setting page")
    public void step1() {
        // link up dut 1 port1
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();

    }

    @Step("Test Step 2: Create vlan 200 from Web GUI,and set vlan name as vlan200.")
    public void step2() {
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPage wiredVLANPage = new WiredVLANPage(false);

        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.addCustomVlanWithPorts("vlan200", "200", null, null, null, null, null, null, null);
        MyCommonAPIs.sleep(5000);
        handle.waitCmdReady("vlan200", false);

        SwitchTelnet switchTelnet = new SwitchTelnet(WebportalParam.sw1IPaddress, false);
        String result2 = switchTelnet.getCLICommand("show vlan 200");
        System.out.println(result2);
        if (result2.contains("vlan200")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 1 Fail:show vlan, cli is:" + result2);
        }
    }

    @Step("Test Step 3: Web Portal change vlan 200 name to 32 alphanumeric")
    public void step3() {
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();

        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.editVlanWithPorts("vlan200", "200", vlanName, null, null, null, null, null, null, null);

        SwitchTelnet switchTelnet = new SwitchTelnet(WebportalParam.sw1IPaddress, false);
        String result2 = switchTelnet.getCLICommand("show vlan 200");
        System.out.println(result2);
        if (result2.contains(vlanName)) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 2 Fail:show vlan, cli is:" + result2);
        }
    }

    @Step("Test Step 4: Check configuration on webportal")
    public void step4() {
        handle.refresh();
        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        List<String> vlans = vlanPage.getVlans();
        MyCommonAPIs.sleep(3000);

        System.out.println("vlan is:" + vlans);
        if (vlans.contains(vlanName)) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "------Check Point 3: 32 chars name vlan is:" + vlans);
        }
    }

    @Step("Test Step 5: Change vlan 200's name to shortest with 2 characters by Web Portal")
    public void step5() {
        // check sw1 on webportal
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();

        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.editVlanWithPorts(vlanName, "200", "xx", null, null, null, null, null, null, null);

        SwitchTelnet switchTelnet = new SwitchTelnet(WebportalParam.sw1IPaddress, false);
        String result2 = switchTelnet.getCLICommand("show vlan 200");
        System.out.println(result2);
        if (result2.contains("xx")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 4 Fail:show vlan, cli is:" + result2);
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
