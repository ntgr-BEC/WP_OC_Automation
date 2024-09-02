package webportal.PR60X.ProSanity.PRJCBUGEN_T34414;

import static org.testng.Assert.assertFalse;
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
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Anusha H
 *
 */
public class Testcase extends TestCaseBase {
    
    String domain = "www.bing.com";
    String tmpStr;
    public static NetworkTroubleshootPage troubleshoot = new NetworkTroubleshootPage(false);
    

    @Feature("PR60X.Sanity") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T34416") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to perform a DNS test.") // It's a testcase title from Jira Test Case.
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
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccountGen(WebportalParam.pr1serialNo, WebportalParam.pr1macaddress, WebportalParam.pr1deveiceName);
        
    }

    @Step("Test Step 2: Enter PRX dashboard;")
    public void step2() {
        
        new NetworkTroubleshootPage(); 
        tmpStr = troubleshoot.runOneTest(WebportalParam.pr1serialNo,domain);
        assertFalse(tmpStr.contains("error"), "dns lookup result is: " + tmpStr);
        assertFalse(tmpStr.contains("fail"), "dns lookup result is: " + tmpStr);
        SelenideElement testresult = troubleshoot.selectOneDeviceTestResult(WebportalParam.pr1serialNo);
        testresult.click();
        MyCommonAPIs.sleepi(5);
//        troubleshoot.CloseResult.click();
        MyCommonAPIs.sleepi(6);
//        troubleshoot.selectAllRadioBtn.click();
//        MyCommonAPIs.sleepi(6);
//        if(troubleshoot.runtestBtn.exists()) {
//            troubleshoot.runtestBtn.click();
//        }else {
            troubleshoot.testAgain.click();
//        }
       
        MyCommonAPIs.sleepi(6);
        tmpStr = MyCommonAPIs.checkSystemCall(2,"");
        assertTrue(tmpStr.contains("DNS Lookup Successful"), "dns lookup testAgain result is: " + tmpStr);

        if (troubleshoot.dnslookupclose.exists()) {
            troubleshoot.dnslookupclose.click();
        }
    }
    }