package webportal.SwitchManaged.LAG.PRJCBUGEN_T4842;

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
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String tclname = getClass().getName();

    String lagName    = "testlag4842";
    String vlanName   = "testvlan";
    String vlanId     = "842";
    String lagId      = "1";
    int    portIndex  = 4;
    int    portIndex1 = 5;

    String ipaclName = "test";
    String ipaclIp   = "11.1.1.1";

    @Feature("Switch.LAG") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4842") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("030- LAG selection inside port membership") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4842") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        portIndex = Integer.parseInt(WebportalParam.sw1LagPort1);
        portIndex1 = Integer.parseInt(WebportalParam.sw1LagPort2);
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
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wvp.gotoPage();
        wvp.newVlan(vlanName, vlanId, 0);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: Add LAG into new VLAN with tag")
    public void step2() {
        wlp.gotoLagPage();
        wlp.addLag(lagName, false, false);

        wvp.gotoPage();
        wvp.openVlan(vlanName, vlanId, 0);
        wvp.addLagPortToVlan(true);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: Add LAG into new VLAN with untag")
    public void step3() {
        wlp.gotoLagPage();
        wlp.addLag(lagName + "un", false, true);

        wvp.gotoPage();
        wvp.openVlan(vlanName, vlanId, 0);
        wvp.addLagPortToVlan(true, false);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 4: lag group id,lan menber, lag admin mode are right")
    public void step4() {
        handle.waitCmdReady(lagName, false);
        String tmpStr1, tmpStr2;

        tmpStr1 = MyCommonAPIs.getCmdOutput("show running-config interface " + WebportalParam.getSwitchLag(false, false), false);
        tmpStr2 = MyCommonAPIs.getCmdOutput("show running-config interface " + WebportalParam.getSwitchLag(false, true), false);
        tmpStr1 = tmpStr1.toLowerCase();
        tmpStr2 = tmpStr2.toLowerCase();
        assertTrue(SwitchCLIUtils.isTagPort(WebportalParam.getSwitchLag(false, false), vlanId), "port g3 is Tagged");
        assertTrue(tmpStr1.contains(vlanId), "port g3 is in vlan: " + vlanId);
        assertTrue(!SwitchCLIUtils.isTagPort(WebportalParam.getSwitchLag(false, true), vlanId), "port g4 is UnTagged");
        assertTrue(tmpStr2.contains(vlanId), "port g4 is in vlan: " + vlanId);
    }

    @Step("Test Step 5: Add successfully on web gui")
    public void step5() {
        wvp.gotoPage();
        wvp.openVlan(vlanName, vlanId, 0);
        wvp.showPortMember();

        assertTrue(wvp.isPortsAccess(WebportalParam.getSwitchLagIndex(false, true)), "port 4 is lag with access mode");
        assertTrue(wvp.isPortsTrunk(WebportalParam.getSwitchLagIndex(false, false)), "port 3 is lag with trunk mode");
    }
}
