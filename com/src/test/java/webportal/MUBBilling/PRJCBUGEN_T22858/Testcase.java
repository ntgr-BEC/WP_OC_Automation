package webportal.MUBBilling.PRJCBUGEN_T22858;

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
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("MUBBilling") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T22858") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify if user is able to successfully save their CC details") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T22858") // It's a testcase id/link from Jira Test Case.

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

       
    }
    

    @Step("Test Step 3: verify functionality of Cancel button")
    public void step3() {  
        
        new HamburgerMenuPage(false).GooMUB();
        
        Map<String, String> paymentInfo = new HashMap<String, String>();
        paymentInfo.put("First Name", "MUBI");
        paymentInfo.put("Last Name", "T1752");
        paymentInfo.put("Street Address", "Street");
        paymentInfo.put("City", "INVERNESS");
        paymentInfo.put("Zip", "34451");
        paymentInfo.put("Country", "US");
        paymentInfo.put("State", "Florida");
        paymentInfo.put("Card Number", "4142621111111112");
        paymentInfo.put("CVV Number", "123");
        paymentInfo.put("Expiration Month", "May");
        paymentInfo.put("Expiration Year", "2030");
        new HamburgerMenuPage(false).FillPaymentMethods(paymentInfo);
        
        assertTrue (new HamburgerMenuPage(false).Submit.isEnabled(), "CC details not fillied properly");
        logger.info("1st one is pass");
        new HamburgerMenuPage(false).Submit.click();
        MyCommonAPIs.sleepi(30);
        assertTrue(new HamburgerMenuPage(false).gotosubscriptionnow.exists(), "go to subscription doesnot exits");
        logger.info("2st one is pass");
        MyCommonAPIs.sleepi(3);
        new HamburgerMenuPage(false).gotosubscriptionnow.click();
        MyCommonAPIs.sleepi(30);
        assertTrue(new HamburgerMenuPage(false).MUBEnableling.exists(), "Mub enable button does not exits");
        logger.info("3st one is pass");
    }


}
