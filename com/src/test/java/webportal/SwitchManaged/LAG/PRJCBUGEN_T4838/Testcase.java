package webportal.SwitchManaged.LAG.PRJCBUGEN_T4838;

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
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredGroupPortConfigPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String tclname = getClass().getName();

    String lagName   = "testlag4838";
    String vlanName  = "testvlan";
    String vlanId    = "838";
    String lagId     = "1";
    int    portIndex = 4;

    String ipaclName = "test";
    String ipaclIp   = "11.1.1.1";

    @Feature("Switch.LAG") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4838") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("012-Create lag with disabled port") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4838") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        portIndex = Integer.parseInt(WebportalParam.sw1LagPort1);
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        try {
            handle.refresh();
            WiredGroupPortConfigPage wgp = new WiredGroupPortConfigPage();
            MyCommonAPIs.waitReady();
            wgp.enablePorts(true);
            handle.clickButton(0);
            handle.clickYesNo(true);
            MyCommonAPIs.sleep(30, "wait command to devices");
        } catch (Throwable e) {
            e.printStackTrace();
        }
        wlp.gotoLagPage();
        wlp.deleteLag();
        wlp.deleteLagCli();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: DUT1 port 1 conenct to DUT2 port 1")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: Disable port 1 from web portal")
    public void step2() {
        WiredGroupPortConfigPage wgp = new WiredGroupPortConfigPage();
        MyCommonAPIs.waitReady();
        wgp.enablePorts(false);
        handle.clickButton(0);
        handle.clickYesNo(true);
        MyCommonAPIs.sleep(60, "wait port to be disabled.");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: ​Create a lag with port1")
    public void step3() {
        wlp.gotoLagPage();
        wlp.addLag(lagName, false, false);

        assertTrue(wlp.isPortsInactive(WebportalParam.getSwitchLagIndex(false, false)), "port is inactive");
        handle.clickButton(1);
    }

}
