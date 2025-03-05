package webportal.Syslog.Premium.PRJCBUGEN_T42810;

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
import util.Javasocket;
import util.MyCommonAPIs;
import util.RunCommand;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DeviceGroupPage;
import webportal.weboperation.DevicesApSummaryPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author  Pratik
 *
 */
public class Testcase extends TestCaseBase {
    
    
    OrganizationPage OrganizationPage = new OrganizationPage();

    @Feature("Syslog") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T42810") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify the server logs when syslog level error is selected") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T42810") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        MyCommonAPIs.sleepi(2);
        new DeviceGroupPage().disableSysLog();
         
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
    }
    
    @Step("Test Step 2: Go to Syslog and enable; verify success message")
    public void step2() {
        new DeviceGroupPage().GoToSysLog(WebportalParam.location1);
        assertTrue(new DeviceGroupPage().enableSysLogwithEmergencyAndVerifySuccessMsg("1.1.1.1", "514", "Error"),"Not Getting Success After entering valid input data for syslog server");
    }
    
    @Step("Test Step3:check for config push")
    public void step3(){
        boolean status=false;
        MyCommonAPIs.sleepi(120);
        String result = new APUtils(WebportalParam.ap1IPaddress).SysLogEnableStatus(WebportalParam.ap1Model);
        if(result.contains("logSettings:syslogStatus 1") && result.contains("logSettings:syslogSrvIp 1.1.1.1") && result.contains("logSettings:syslogSrvPort 514") && result.contains("system:logSettings:syslogLevel 4")) {
            status = true;
        }
        
        assertTrue(status == true , "syslog  is disabled after enabling");
        
    }

}
