package webportal.LogProbingClient.PRJCBUGEN_T38176;

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
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DeviceGroupPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author  Ravishankar SS
 *
 */
public class Testcase extends TestCaseBase {
    
    
    OrganizationPage OrganizationPage = new OrganizationPage();

    @Feature("Log probing client") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T38176") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that for owner able config syslog on WP.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T38176") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        MyCommonAPIs.sleepi(2);
        OrganizationPage.openOrg(WebportalParam.Organizations);
        new DeviceGroupPage().GoToSysLog(WebportalParam.location1);
        new DeviceGroupPage().disableSysLog();
        MyCommonAPIs.sleepi(3);   
        new WirelessQuickViewPage().goToNetworkSetting();
        MyCommonAPIs.sleepi(3);   
        new WirelessQuickViewPage(false).enableLogProbing(false);
         
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.ownerName, WebportalParam.ownerPassword);
        new OrganizationPage(false).clickonOkayGotit();
        handle.gotoLoction();

    }

    @Step("Test Step 2: Go to Syslog and enable")
    public void step2() {
        new OrganizationPage().openOrg(WebportalParam.Organizations);
        OrganizationPage.openOrg(WebportalParam.Organizations);
        new DeviceGroupPage().GoToSysLog(WebportalParam.location1);
        new DeviceGroupPage().EnableSysLog("1.1.1.1", "514");
    }
    
    @Step("Test Step 3: Enable log Probing;")
    public void step3() {
        
        new WirelessQuickViewPage().goToNetworkSetting();
        MyCommonAPIs.sleepi(3);   
        new WirelessQuickViewPage(false).enableLogProbing(true);

       
}
    
    @Step("Test Step4:check for config push")
    public void step4(){
        boolean status=false;
        String result = new APUtils(WebportalParam.ap1IPaddress).SysLogEnableStatus(WebportalParam.ap1Model);
        if(result.contains("logSettings:syslogStatus 1") && result.contains("logSettings:syslogSrvIp 1.1.1.1") && result.contains("logSettings:syslogSrvPort 514")) {
            status = true;
        }
        
        assertTrue(status == true , "syslog  is disabled after enabling");
        
    }
       

}
