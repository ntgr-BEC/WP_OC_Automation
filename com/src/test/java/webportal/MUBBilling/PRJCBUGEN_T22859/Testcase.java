package webportal.MUBBilling.PRJCBUGEN_T22859;

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
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("MUBBilling") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T22859") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify if user is able to edit the fields under the billing page") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T22859") // It's a testcase id/link from Jira Test Case.

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

    @Step("Test Step 2: verify functionality of Cancel button")
    public void step2() {  
        
        new HamburgerMenuPage(false).GooMUB();
        
        Map<String, String> oldProfileInfo = new HamburgerMenuPage().getProfileMUB();
        System.out.println(oldProfileInfo);
        
        Map<String, String> paymentInfo = new HashMap<String, String>();
        paymentInfo.put("First Name", "3rd attemt");
        paymentInfo.put("Last Name", "T1755");
        paymentInfo.put("Street Address", "Street 4568 James");
        paymentInfo.put("City", "INVERNESS");
        paymentInfo.put("Zip", "34453");
        paymentInfo.put("Country", "US");
        paymentInfo.put("State", "Florida");
        new HamburgerMenuPage(false).EditManagePaymentMethods(paymentInfo);
        
        System.out.println("out from new");
        
        Map<String, String> newProfileInfo = new HamburgerMenuPage().getProfileMUB();
        boolean result = true;
        
        System.out.println(newProfileInfo);
        
        new HamburgerMenuPage(false).GooMUB();
        
        
        String m1value = oldProfileInfo.get("Zip");
        String m2value = newProfileInfo.get("Zip");
        
        System.out.println("m1 value is " + m1value);
        System.out.println("m2 value is " +m2value);
        
        if (m1value.equals(m2value)) {
            result = false;
        }
        
//        for (Map.Entry<String, String> entry1 : paymentInfo.entrySet()) {
//            String m1value = entry1.getValue() == null ? "" : entry1.getValue();
//            System.out.println("m1 value is " + m1value);
//            
//            String m2value = oldProfileInfo.get(entry1.getKey()) == null ? "" : oldProfileInfo.get(entry1.getKey());
//            System.out.println("m2 value is " +m2value);
//            if (m1value.equals(m2value)) {
//                System.out.println(m2value);
//                result = false;
//                break;
//            }
//        }
//        
        new HamburgerMenuPage(false).EditManagePaymentMethods(oldProfileInfo);
        MyCommonAPIs.sleepi(10);
        assertTrue(result, "Edit profile unsuccessful.");
        
    }


}
