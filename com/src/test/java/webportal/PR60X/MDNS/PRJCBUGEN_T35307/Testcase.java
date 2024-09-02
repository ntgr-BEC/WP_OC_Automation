package webportal.PR60X.MDNS.PRJCBUGEN_T35307;

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
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.MDNSPage;
import webportal.weboperation.PRDashPage;
import webportal.webelements.MDNSElement;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;
import webportal.webelements.WirelessQuickViewElement;

/**
 *
 * @author  Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    
    HashMap<String, String> BackupInfo = new HashMap<String, String>();

    @Feature("PR60X.MDNS") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T35307") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify, create New Location with APs and PR and enable mDNS with PR as gateway") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T35307") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        MyCommonAPIs.sleepi(10);
        new MDNSPage().deletePolicy("MDNS");
        MyCommonAPIs.sleepi(5);
        new MDNSPage(false).disableMDNS();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccountGen(WebportalParam.pr1serialNo, WebportalParam.pr1macaddress, WebportalParam.pr1deveiceName);
    }

    @Step("Test Step 2: check MDNS")
    public void step2() {
        new MDNSPage(false).checkaddSsid();
        new MDNSPage().addPolicy();
        MyCommonAPIs.sleepi(5);
        assertTrue(new MDNSPage(false).Policyexits.isDisplayed(),"Policy is added properly");
        MyCommonAPIs.sleepi(60);
        String before = new APUtils(WebportalParam.ap1IPaddress).MDNS(WebportalParam.ap1Model);        
        assertTrue(before.contains("mDNSGateway 1"), "policy is not added propely");
        new MDNSPage(false).disableMDNS();
        new PRDashPage().addPolicyPR(WebportalParam.pr1deveiceName); 
        
        
        
        MyCommonAPIs.sleepi(60);
        String before1 = new APUtils(WebportalParam.ap1IPaddress).MDNS(WebportalParam.ap1Model);        
        assertTrue(!before1.contains("mDNSGateway 0"), "policy is not added propely");
    }
    
  
       

}
