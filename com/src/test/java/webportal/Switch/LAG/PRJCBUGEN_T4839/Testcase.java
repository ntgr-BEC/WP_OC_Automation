package webportal.Switch.LAG.PRJCBUGEN_T4839;

import static org.testng.Assert.assertFalse;
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
import webportal.param.CommonDataType;
import webportal.param.CommonDataType.EditLagData;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredGroupPortConfigPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String tclname = getClass().getName();

    String             lagName  = "testlag4839";
    String             vlanName = "testvlan";
    String             vlanId   = "839";
    String             lagId    = "1";
    public EditLagData eldData;

    String ipaclName = "test";
    String ipaclIp   = "11.1.1.1";

    @Feature("Switch.LAG") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4839") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("014-Edit lag on dual switches") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4839") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        wlp.gotoLagPage();
        wlp.deleteLag();
        MyCommonAPIs.sleepi(4);
        wlp.deleteLagCli();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Manage switch by Insight, and create a VLAN, check if LAG selection inside port membership")
    public void step1() {
        eldData = new CommonDataType().new EditLagData();
        eldData.name = lagName;

        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        WiredGroupPortConfigPage wgpcp = new WiredGroupPortConfigPage();
        wgpcp.enableLagPort();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: Create lag 1 on dut 1 and dut2, and add port 4 to lag 1")
    public void step2() {
        wlp.gotoLagPage();
        wlp.addLag(lagName, false, false);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: On both switch lag 1 is created successfully with menber port 4")
    public void step3() {
        handle.waitCmdReady(lagName, false);
        MyCommonAPIs.sleepsync();
        String tmpStr;

        tmpStr = MyCommonAPIs.getCmdOutput("show running-config interface " + WebportalParam.getSwitchLag(false, false), false);
        assertTrue(tmpStr.contains(" lag "), "port g4 is added to lag 1 - 1");

        tmpStr = MyCommonAPIs.getCmdOutput("show running-config interface " + WebportalParam.getSwitchLag(true, false), true);
        assertTrue(tmpStr.contains(" lag "), "port g4 is added to lag 1 - 2");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 4: Edit lag 1 , and add port 5 to lag 1")
    public void step4() {
        wlp.gotoLagPage();
        wlp.editLag(eldData);
    }

    @Step("Test Step 5: On both switch lag 1 have menber port 5,4")
    public void step5() {
        assertTrue(wlp.isPortsEnable(WebportalParam.getSwitchLagIndex(false, false)), "port 4 is added to lag");
        assertTrue(wlp.isPortsEnable(WebportalParam.getSwitchLagIndex(false, true)), "port 5 is added to lag");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 6: Edit lag 1 , and delete port 5 from lag 1")
    public void step6() {
        wlp.gotoLagPage();
        eldData.bPortLag2 = false;
        wlp.editLag(eldData);
    }

    @Step("Test Step 7: On both switch lag 1 have menber port 4 only")
    public void step7() {
        assertTrue(wlp.isPortsEnable(WebportalParam.getSwitchLagIndex(false, false)), "port 4 is added to lag");
        assertFalse(wlp.isPortsEnable(WebportalParam.getSwitchLagIndex(false, true)), "port 5 is not added to lag");
    }

    @Step("Test Step 8: Delete lag 1 from app, and check result from web portal and web gui")
    public void step8() {
        wlp.gotoLagPage();
        wlp.deleteLag();
    }

    @Step("Test Step 9: Lag 1 is deleted successfully.")
    public void step9() {
        MyCommonAPIs.sleepsync();
        assertFalse(SwitchCLIUtils.isLagPort(WebportalParam.getSwitchLag(false, false)), "lag 1 is removed - 1");
    }

}
