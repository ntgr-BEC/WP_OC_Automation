package webportal.ProManagedSwitch.LAG.PRJCBUGEN_T41047;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.SelenideElement;

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
 * @author Sumanta
 */
public class Testcase extends TestCaseBase {
    String tclname = getClass().getName();

    String lagName    = "testlag1047";
    String vlanName   = "testvlan";
    String vlanId     = "844";
    String lagId      = "1";
    int    portIndex  = 4;
    int    portIndex1 = 5;

    String ipaclName = "test";
    String ipaclIp   = "11.1.1.1";

    @Feature("Switch.LAG") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T41047") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("008-Change port speed which belong to lag") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T41047") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p1", enabled = true) // Use p1/p2/p3 to high/normal/low on priority
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
    @Step("Test Step 1: DUT1 port 1 conenct to DUT2 port 1")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        WiredGroupPortConfigPage wgpcp = new WiredGroupPortConfigPage();
        wgpcp.enableLagPort();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 2: Connect two DUT with two ports")
    public void step2() {
        wlp.gotoLagPage();
        wlp.bEnableStatic = false;
        wlp.addLag(lagName, true, false);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 3: ​Change port 4 speed mode")
    public void step3() {
        WiredGroupPortConfigPage wgp = new WiredGroupPortConfigPage();
        List<SelenideElement> ecs = handle.getPorts(WebportalParam.getSwitchLagIndex(false, false));
        assertTrue(ecs.size() > 1, "There must be 2 ports for 4");
        ecs.get(0).click();
        ecs.get(1).click();
        wgp.portSpeedSelect.selectOption(2); // ms510 does not support 10M
        wgp.saveButton.click();
        wgp.clickBoxLastButton();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 4: pop-up message “The physical mode for Port Channel members.Changing the speed of Port Channel members requires removal from the Port Channel”")
    public void step4() {
        String s = handle.getPageErrorMsg();
        assertTrue(s.length() > 5, "error for 'Select a port.'");
    }
}
