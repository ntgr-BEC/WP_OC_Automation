package webportal.SwitchManaged.Routing.PRJCBUGEN_T6885;

import static org.testng.Assert.assertTrue;

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
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String vlanName = "testvlan";
    String vlanId   = "25";
    int    iMax     = 15;
    String checkId  = vlanId + iMax;
    String mask     = "255.255.255.0";
    String ip1      = "11.22.33.44";
    String ip2      = "11.22.33.45";

    @Issue("PRJCBUGEN-11348")
    @Feature("Switch.Routing") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T6885") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("033-Create max number of routing vlan") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T6885") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p5") // "p3"
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        wvp.gotoPage();
        wvp.deleteAllVlan();

        rtp.gotoPage();
        rtp.deleteAllVlanRoute();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open page")
    
    public void step1() {
//        if (!WebportalParam.skipIssueCheck)
//            throw new RuntimeException("PRJCBUGEN-11348");
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wvp.gotoPage();
        wvp.deleteAllVlan();

        rtp.gotoPage();
        rtp.deleteAllVlanRoute();
    }

    @Step("Test Step 2: Add 16 vlan.")
    public void step2() {
        for (int i = 1; i <= (iMax + 1); i++) {
            String name = String.format(vlanName + "%d", i);
            String id = String.format(vlanId + "%d", i);
            wvp.gotoPage();
            wvp.newVlan(name, id, 0);
        }
    }

    @Step("Test Step 3: Add ip address for vlan1-15")
    public void step3() {
        new AccountPage().enterEditNetworkPage();
        rtp.gotoPage();
        for (int i = 1; i <= iMax; i++) {
            String name = String.format(vlanName + "%d", i);
            String ip = String.format(vlanId + ".1.%d.", i);
            rtp.addIpToVlan(name, mask, ip + "1", ip + "2");
        }
    }

    @Step("Test Step 4: Check configuration from dut and app")
    public void step4() {
        handle.waitCmdReady(checkId, false);
        String tmpStr = MyCommonAPIs.getCmdOutput("show running-config interface vlan " + vlanId + iMax, false);
        assertTrue(tmpStr.contains("routing"), "check routing option");
        assertTrue(tmpStr.contains("ip address"), "check ip option");
    }

    @Step("Test Step 5: Add ip address for vlan 16")
    public void step5() {
        rtp.addIpToVlan(vlanName + (iMax + 1), mask, ip1, ip2);
        String sRet = handle.getPageErrorMsg();
        assertTrue(sRet.contains("limit exceeded"));
    }

}
