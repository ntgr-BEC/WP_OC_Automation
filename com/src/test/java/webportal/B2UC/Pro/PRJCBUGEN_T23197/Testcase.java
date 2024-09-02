package webportal.B2UC.Pro.PRJCBUGEN_T23197;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.APUtils;
import util.Javasocket;
import util.MyCommonAPIs;
import util.RunCommand;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author  Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("B2UC_Pro") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T23197") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify the default state of the feature Broadcast to unicast ") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T23197") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInNormalAccount("admin");
    }

    @Step("Test Step 2: Check default IGMP status in Insight;")
    public void step2() {
        assertTrue(new WirelessQuickViewPage().B2UCInsight(), "B2UC enable by default");
        
    }

    @Step("Test Step 3: Check default B2UC status in AP console")
    public void step3() {
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        new RunCommand().enableSSH4AP(WebportalParam.loginPassword, WebportalParam.ap1serialNo);
        assertTrue(new APUtils(WebportalParam.ap1IPaddress).getB2UCdisableStatus(WebportalParam.ap1Model), "B2UC enable bu default in AP console");
        
    }

}
