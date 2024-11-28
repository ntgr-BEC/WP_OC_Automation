package webportal.SwitchManaged.System.PRJCBUGEN_T4663;

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
import util.SwitchCLIUtils;
import webportal.param.WebportalParam;
import webportal.publicstep.PublicButton;
import webportal.weboperation.DevicesDashPageMNG;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase implements Config {
    public String expectValue = "";

    String devName  = "deviceName4663";
    String vlanName = "testvlan";
    String vlanId   = "200";
    String lagName  = "lag4663";

    @Feature("Switch.System") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4663") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("040- Deploy \"Reload\" to Switch with VLAN and LAG level configuration") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4663") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p4") // p1
    public void test() throws Exception {
        runTest(this);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: enter setting page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();
    }

    @Step("Test Step 2: create VLAN 200, and tag port3 into vlan200, untag port4 into vlan200")
    public void step2() {
        wvp.gotoPage();
        wvp.openVlan(vlanName, vlanId, 0);
        wvp.showPortMember();
        wvp.selectPort(3, true, 0);
        wvp.selectPort(3, true, 1);
        wvp.selectPort(4, false, 0);
        wvp.selectPort(4, false, 1);
        handle.clickButton(0);
    }

    @Step("Test Step 3:  create LAG3, and add port4&5 into LAG3 (static)")
    public void step3() {
        wlp.gotoLagPage();
        wlp.addLag(lagName, true, false);
    }

    @Step("Test Step 4: modify device name (e.g. test123!)")
    public void step4() {
        DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
        devicesDashPage.editDeviceName(WebportalParam.sw1serialNo, devName);
    }

    @Step("Test Step 5: Deploy \"Factory Default\" command from Web Portal to Switch")
    public void step5() {
        new DevicesDashPageMNG().enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
        new PublicButton().rebootDevice();
//        MyCommonAPIs.waitDeviceOnlineReload();
    }

    @Step("Test Step 6: After switch reload, check switch info on Web Portal and device Web GUI")
    public void step6() {
        String tmpStr1, tmpStr2, tmpStr3, tmpStr4, tmpStr5, tmpStr6;
        tmpStr1 = MyCommonAPIs.getCmdOutput("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, 3), false);
        tmpStr2 = MyCommonAPIs.getCmdOutput("show running-config interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, 4), false);
        tmpStr3 = MyCommonAPIs.getCmdOutput("show running-config interface " + WebportalParam.getSwitchLag(false, false), false);
        tmpStr4 = MyCommonAPIs.getCmdOutput("show running-config interface " + WebportalParam.getSwitchLag(false, true), false);
        if (WebportalParam.isRltkSW1) {
            tmpStr5 = MyCommonAPIs.getCmdOutput("show info", false);
        } else {
            tmpStr5 = MyCommonAPIs.getCmdOutput("show sysinfo", false);
        }
        assertTrue(SwitchCLIUtils.isTagPort("g3", vlanId), "port1 is tagged");
        assertTrue(tmpStr1.contains(vlanId), "port 3 is in vlan 200");
        assertTrue(!SwitchCLIUtils.isTagPort("g4", vlanId), "port2 is untagged");
        assertTrue(tmpStr2.contains(vlanId), "port 4 is in vlan 200");

        assertTrue(SwitchCLIUtils.isLagPort(WebportalParam.getSwitchLag(false, false)), "addpmgort lag");
        assertTrue(SwitchCLIUtils.isLagPort(WebportalParam.getSwitchLag(false, true)), "addpmgort lag");
        assertTrue(tmpStr5.contains(devName), devName);
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        WebportalParam.updateSwitchOneOption(false, null);
        try {
            DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
            devicesDashPage.editDeviceName(devName, WebportalParam.sw1deveiceName);
            devicesDashPage.waitAllSwitchDevicesConnected();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        wlp.gotoLagPage();
        wlp.deleteLag();
        wlp.deleteLagCli();
        wvp.deleteAllVlan();
        wvp.deleteAllVlanCli();
    }
}
