package webportal.PR60X.Sanity.PRJCBUGEN_T34416;

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
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.DevicesOoklaSpeedtestPage;
import webportal.weboperation.PRDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Anusha H
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("PR60X.Sanity") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T34416") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to perform a speed test.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T34416") // It's a testcase id/link from Jira Test Case.

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
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccountGen(WebportalParam.pr1serialNo, WebportalParam.pr1macaddress, WebportalParam.pr1deveiceName);
        
    }

    @Step("Test Step 2: Enter PRX dashboard;")
    public void step2() {
           
        new PRDashPage().enterDeviceYes(WebportalParam.pr1serialNo);
        new PRDashPage().troubleshootpage();
       
        boolean Result = false;
        System.out.println("tessssssssssssss");
        DevicesOoklaSpeedtestPage DeviceOoklaSpeedtestPage = new DevicesOoklaSpeedtestPage();
        DeviceOoklaSpeedtestPage.gotoAPOoklaSpeedtestpage();
        System.out.println("tessssssssssssss1");
        DeviceOoklaSpeedtestPage.ClickRuntestButton();
        System.out.println("tessssssssssssss2");
        String UploadInfo = DeviceOoklaSpeedtestPage.GetUploadInfo();
        System.out.println(UploadInfo);
        if (!UploadInfo.equals("-")) {
            Result = true;
        }
        if (Result == true) {
            micResult = true;
            assertTrue(micResult,"Pass:Get the network upload speed with appropriate unit successfully !");  
        }else {
            micResult = false;
            assertTrue(micResult,"Failed:Get the network upload speed with appropriate unit unsuccessfully!"); 
        } 
    }

}
