package webportal.PR60X.Sanity.PRJCBUGEN_T34411;

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
import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.DevicesOoklaSpeedtestPage;
import webportal.weboperation.NetworkTroubleshootPage;
import webportal.weboperation.PRDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredVLANPage;
import webportal.weboperation.WiredVLANPageForVLANPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    
    @Feature("PR60X.Sanity") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T34411") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the basic WAN settings.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4411") // It's a testcase id/link from Jira Test Case.

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
    @Step("Test Step 2: change ro dynamic and static ;")
    public void step2() {
        String connectiontypeDHCP = "DHCP";
        new PRDashPage().enterDeviceYes(WebportalParam.pr1serialNo);
        new PRDashPage().WANStatic(connectiontypeDHCP);
        MyCommonAPIs.sleepi(180); 
        new PRDashPage().enterDeviceYes(WebportalParam.pr1serialNo);
        WebCheck.checkHrefIcon(URLParam.hrefDevicesPRWanIp);
        MyCommonAPIs.sleepi(5); 
        assertTrue( new PRDashPage().DHCP.isDisplayed()  , "DHCP is not selected");
        new PRDashPage().enterDeviceYes(WebportalParam.pr1serialNo);
        String connectiontypeStatic = "Static";
        new PRDashPage().WANStatic(connectiontypeStatic);
        MyCommonAPIs.sleepi(180); 
        new PRDashPage().enterDeviceYes(WebportalParam.pr1serialNo);
        WebCheck.checkHrefIcon(URLParam.hrefDevicesPRWanIp);
        MyCommonAPIs.sleepi(5); 
        assertTrue( !new PRDashPage().DHCP.isDisplayed()  , "Static is not selected");
        
    }      
   
}

