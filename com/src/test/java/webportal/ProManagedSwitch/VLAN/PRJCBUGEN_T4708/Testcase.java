package webportal.ProManagedSwitch.VLAN.PRJCBUGEN_T4708;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import util.SwitchTelnet;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredGroupPortConfigPage;
import webportal.weboperation.WiredQuickViewPage;
import webportal.weboperation.WiredVLANPage;
import webportal.weboperation.WiredVLANPageForVLANPage;

/**
 * @author sumanta
 */
public class Testcase extends TestCaseBase implements Config {
    public boolean Result = true;

    @Feature("Switch.VLAN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4708") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("011-Delete a vlan that is pvid of some interface") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4708") // It's a testcase id/link from Jira Test Case.
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

    @Step("Test Step 2: Create vlan 200..")
    public void step2() {
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPage wiredVLANPage = new WiredVLANPage(false);
        WiredGroupPortConfigPage wiredGroupPortConfigPage = new WiredGroupPortConfigPage();
        wiredGroupPortConfigPage.multiSetting(SWITCH1_PORTARRAY, BATTCHSETTING1);
        MyCommonAPIs.sleep(8000);

        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.addCustomVlanWithPorts("vlan200", "200", null, null, null, null, null, null, null);

        WiredGroupPortConfigPage wiredGroupPortConfigPage2 = new WiredGroupPortConfigPage();
        wiredGroupPortConfigPage2.multiSetting(SWITCH1_PORTARRAY, BATTCHSETTING3);
        handle.waitCmdReady("vlan200", false);

        SwitchTelnet switchTelnet = new SwitchTelnet(WebportalParam.sw1IPaddress, false);
        String result2 = switchTelnet
                .sendCLICommand("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, sw1port[0]), null);
        System.out.println(result2);
        if (result2.contains("pvid 200")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "----Check Point 1 Fail:show running-config interface g4, 1 cli is:" + result2);
        }
    }

    @Step("Test Step 3: delete vlan")
    public void step3() {
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.deleteAllVlan();
        MyCommonAPIs.sleep(5000);

        SwitchTelnet switchTelnet = new SwitchTelnet(WebportalParam.sw1IPaddress, false);
        String result2 = switchTelnet
                .sendCLICommand("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, sw1port[0]), null);
        System.out.println(result2);
        if (result2.contains("pvid 200")) {
            micResult = false;
            assertTrue(micResult, "----Check Point 2 Fail:show running-config interface g4, 1 cli is:" + result2);
        } else {
            micResult = true;
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
