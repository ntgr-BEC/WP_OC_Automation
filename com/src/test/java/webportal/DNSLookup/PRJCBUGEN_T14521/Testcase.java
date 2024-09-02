package webportal.DNSLookup.PRJCBUGEN_T14521;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
import webportal.param.WebportalParam;
import webportal.weboperation.NetworkTroubleshootPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author xuchen
 *
 */
public class Testcase extends TestCaseBase {
    String domain = "www.bing.com";
    String tmpStr;
    public static NetworkTroubleshootPage troubleshoot = new NetworkTroubleshootPage(false);
    

    @Feature("DNSLookup") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14521") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify whether the network timezone is getting reflected in the DNS lookup results timestamps") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14521") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Insight discover and manage one ap")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new NetworkTroubleshootPage();
    }

    @Step("Test Step 2: Insight go to troubleshoot to run test;")
    public void step2() {
        tmpStr = troubleshoot.runOneTest(WebportalParam.ap1serialNo,domain);
        assertFalse(tmpStr.contains("error"), "dns lookup result is: " + tmpStr);
        assertFalse(tmpStr.contains("fail"), "dns lookup result is: " + tmpStr);
        SelenideElement testresult = troubleshoot.selectOneDeviceTestResult(WebportalParam.ap1serialNo);
        testresult.click();
        MyCommonAPIs.sleepi(6);
        int     year    = Calendar.getInstance().get(Calendar.YEAR);
        String  month   = String.format("%d", Calendar.getInstance().get(Calendar.MONTH) + 1);
        int     day     = Calendar.getInstance().get(Calendar.DATE);
        Calendar cale = Calendar.getInstance();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy",Locale.ENGLISH);
        System.out.println(sdf.format(date));
        System.out.println(sdf.format(cale.getTime()));
        
        tmpStr = MyCommonAPIs.checkSystemCall(2,"");

        assertTrue(tmpStr.contains(sdf.format(date)), "dns lookup result is not contain the time. ");
        if (troubleshoot.dnslookupclose.exists()) {
            troubleshoot.dnslookupclose.click();
        }
    }



}
