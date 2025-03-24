package webportal.PR60X.Qos.prouser.PRJCBUGEN_T34410;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.APUtils;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.DevicesOoklaSpeedtestPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.NetworkTroubleshootPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.PRDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {


    @Feature("PR60X.Sanity_Qos") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T344010") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify user able to manually define download and upload speed for WAN1.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T34410") // It's a testcase id/link from Jira Test Case.

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
        new HamburgerMenuPage(false).closeLockedDialog();
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);

        handle.gotoLoction();
      
        // new DevicesDashPage().checkDeviceInAdminAccountGen(WebportalParam.pr1serialNo, WebportalParam.pr1macaddress, WebportalParam.pr1deveiceName);
        
    }

    @Step("Test Step 2: Enable the dual WAN ;")
    public void step2() {
        
        new PRDashPage().enterDeviceYes(WebportalParam.pr1serialNo);
        new PRDashPage().Qos();
        String check = new APUtils(WebportalParam.pr1IPaddress).getQosStatus();
        
        
    }      
}
