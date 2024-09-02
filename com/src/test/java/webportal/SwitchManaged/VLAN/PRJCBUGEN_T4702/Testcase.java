package webportal.SwitchManaged.VLAN.PRJCBUGEN_T4702;

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
import util.SwitchCLIUtilsMNG;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredGroupPortConfigPage;
import webportal.weboperation.WiredQuickViewPage;
import webportal.weboperation.WiredVLANPage;
import webportal.weboperation.WiredVLANPageForVLANPage;

/**
 * @author xuchen
 */
public class Testcase extends TestCaseBase implements Config {
    public boolean Result = true;
    String         vlanId = "600";

    @Feature("Switch.VLAN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4702") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("048-Create a vlan via the template of Data Network Configuration/Custom Network and change it default settings") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4702") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p3")
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

    @Step("Test Step 2: use template of Data Network Configuration to create vlan 600, name data traffic vlan")
    public void step2() {
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPage wiredVLANPage = new WiredVLANPage(false);
        WiredGroupPortConfigPage wiredGroupPortConfigPage = new WiredGroupPortConfigPage();
        wiredGroupPortConfigPage.multiSetting(SWITCH1_PORTARRAY, BATTCHSETTING1);
        MyCommonAPIs.sleep(8000);

        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.addDataVlanWithPorts("data traffic vlan", "600", dut1Name, sw1port, "tag", null, null, null, "true");
        handle.waitCmdReady("vlan 600", false);
    }

    @Step("Test Step 3: Check configuration on webportal")
    public void step3() {
        // check sw1 on webportal
        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        List<String> vlans = vlanPage.getVlans();
        MyCommonAPIs.sleep(3000);

        System.out.println("vlan is:" + vlans);
        if (vlans.contains("data traffic vlan")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "------Check Point 1:vlanId is:" + vlans);
        }
    }

    @Step("Test Step 4: Check configuration on CLI")
    public void step4() {
        // check on dut CLI
        for (int i = 0; i < sw1port.length; i++) {
            String result1 = MyCommonAPIs
                    .getCmdOutput("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, sw1port[i]), false)
                    .toLowerCase();
            if (WebportalParam.sw1Model.contains("M4250")) {
            if (SwitchCLIUtilsMNG.isTagPort("0/" + sw1port[i], vlanId) && result1.contains("600")) {
                micResult = true;
            } else {
                micResult = false;
                assertTrue(micResult, "----Check Point 1 Fail:check vlan 600 fail, cli is:" + result1);
            }
        }else if (WebportalParam.sw1Model.contains("M4350")) {
            if (SwitchCLIUtilsMNG.isTagPort("1/0/" + sw1port[i], vlanId) && result1.contains("600")) {
                micResult = true;
            } else {
                micResult = false;
                assertTrue(micResult, "----Check Point 1 Fail:check vlan 600 fail, cli is:" + result1);
            }
        }
     }

//        String result2 = SwitchCLIUtilsMNG.getIGMPVlan("600");
//        System.out.println(result2);
//        if (result2.toLowerCase().contains("enabled")) {
//            micResult = true;
//        } else {
//            micResult = false;
//            assertTrue(micResult, "----Check Point 2 Fail:show igmpsnooping on dut1, 1 cli is:" + result2);
//        }
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
        WiredVLANPageForVLANPage vlanPage = new WiredVLANPageForVLANPage();
        vlanPage.deleteAllVlan();
    }

}
