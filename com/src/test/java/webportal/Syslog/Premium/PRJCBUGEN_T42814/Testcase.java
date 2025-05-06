package webportal.Syslog.Premium.PRJCBUGEN_T42814;

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
import webportal.webelements.DeviceGroupElement;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DeviceGroupPage;
import webportal.weboperation.DevicesApSummaryPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author  RaviShankar 
 *
 */
public class Testcase extends TestCaseBase {
    
    
    boolean status;

    @Feature("Syslog") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T42814") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify Default status of Syslog") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T42814") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        MyCommonAPIs.sleepi(2);
        if(status)
        {
            new DeviceGroupPage().disableSysLog();
        }
         
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
    }

    @Step("Test Step 2: Go to Syslog and enable")
    public void step2() {
        new DeviceGroupPage().GoToSysLog(WebportalParam.location1);
        status=MyCommonAPIs.checkSelected(new DeviceGroupElement().enbleSysLog);
        assertTrue(!status,"Syslog is Enabled by default");
       
    }
}