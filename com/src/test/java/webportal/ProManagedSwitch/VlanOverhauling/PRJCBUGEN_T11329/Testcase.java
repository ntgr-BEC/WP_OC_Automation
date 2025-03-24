package webportal.ProManagedSwitch.VlanOverhauling.PRJCBUGEN_T11329;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.weboperation.WebportalLoginPage;
import webportal.param.WebportalParam;
/**
 *
 * @author sumanta
 *
 */
public class Testcase extends TestCaseBase {
    String vlanName = "testvlan";
    String vlanId   = "4089";

    @Feature("Switch.VlanOverhauling") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T11329") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("003-Create a network via the template of \"Video\"") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T11329") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open page and goto Vlan Page")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);

        handle.gotoLoction();
    }

    @Step("Test Step 2: Check to create it if there was not a video vlan")
    public void step2() {
        wvp.gotoPage();
        wvp.newVlan(vlanName, vlanId, 2);
    }

    @Step("Test Step 3: Insight create Video VLAN via the template of 'Video' with default config")
    public void step3() {
        handle.waitCmdReady(vlanId, false);
        String tmpStr = "";
        if (WebportalParam.isRltkSW1) {
            tmpStr = handle.getCmdOutput("show running-config ", false);
        } else {
            tmpStr = handle.getCmdOutput("show vlan " + vlanId, false);
        }
        
        assertTrue(tmpStr.contains(vlanId), "check video vlan is there");
    }

}
