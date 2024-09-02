package webportal.DeviceContract.PRJCBUGEN_T14270;

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
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("DeviceContract") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14270") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Purchase device contract for WAC540 Model- US") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14270") // It's a testcase id/link from Jira Test Case.

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
        webportalLoginPage.loginByUserPassword("devicecontractus@yopmail.com", "Netgear1@");

        handle.gotoLoction();
    }

    @Step("Test Step 2: Go to device contract Page")
    public void step2() {
        Map<String, String> paymentInfo = new HashMap<String, String>();
        paymentInfo.put("Device Category", "Category 1, 1-Year");
        paymentInfo.put("Email", "@mailinator.com");
        paymentInfo.put("Street Address", "Springs Rd");
        paymentInfo.put("City", "Red Bank");
        paymentInfo.put("Zip", "32003");
        paymentInfo.put("Country", "US");
        paymentInfo.put("State", "Florida");
        paymentInfo.put("Card Number", "5193911111111112");
        paymentInfo.put("CVV Number", "123");
        paymentInfo.put("Expiration Month", "May");
        paymentInfo.put("Expiration Year", "2030");
        
        new InsightServicesPage().buyDeviceContract(paymentInfo, WebportalParam.ap4serialNo);

    }

    @Step("Test Step 3: Check whether connected connect is shown in client list;")
    public void step3() {
        
        assertTrue(new InsightServicesPage(false).DeviceContractVerification(WebportalParam.ap4serialNo),"");
      
    }

}
