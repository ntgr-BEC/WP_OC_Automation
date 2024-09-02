package webportal.CFD.AP.PRJCBUGEN_T27350;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
import webportal.param.CommonDataType;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.AccountPage;
import webportal.weboperation.CopyConfigurationPage;
import webportal.weboperation.DeviceBulkOnboardingPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini 
 *
 */
public class Testcase extends TestCaseBase {


    @Feature("AP") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T27350") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("upload large image")
    @TmsLink("PRJCBUGEN-T27350") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
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
        webportalLoginPage.loginByUserPassword(WebportalParam.loginName,WebportalParam.loginPassword);

        handle.gotoLoction();
    }
   
    
    
    @Step("Test Step 2: Upload big file")
    public void step2() {
        Map<String, String> CaptivePortalPaymentInfo = new CommonDataType().PAYMENT_INFO;

        if (!new HamburgerMenuPage().checkCaptivePortalServicesCredits()) {
            CaptivePortalPaymentInfo.put("First Name", "New");
            CaptivePortalPaymentInfo.put("Last Name", "T16635");
            CaptivePortalPaymentInfo.put("Email", WebportalParam.loginName);
            CaptivePortalPaymentInfo.put("Quantity", "3"); // can input 1 , 3 , 10 , 40
            CaptivePortalPaymentInfo.put("Duration", "1"); // can input 1 , 3
            new InsightServicesPage().buyCaptivePortalProducts(CaptivePortalPaymentInfo);
        }
                
        Map<String, String> ssidInfo = new HashMap<String, String>();
        ssidInfo.put("SSID", "apwp14477");
        ssidInfo.put("Security", "WPA2 Personal");
        ssidInfo.put("Password", "12345678");
        new WirelessQuickViewPage().addSsid(ssidInfo);

        new WirelessQuickViewPage().editCaptivePortal(ssidInfo.get("SSID"), "http://www.rediff.com", "sri office", "welcome to sri office");
        
        String filePath = new WirelessQuickViewPage().GetcurrentPath();
        filePath = filePath + "PRJCBUGEN_T27350\\img1.png";
        System.out.println(filePath);
        
        new WirelessQuickViewPage().UploadImage(ssidInfo.get("SSID"), filePath);
    }
        
}