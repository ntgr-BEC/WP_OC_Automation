package webportal.RF_WLAN_Profile.Premium.PRJCBUGEN_T39776;

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
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author  Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    

    Map<String, String> RFdata = new HashMap<String, String>();
    Map<String, String> RFdata1 = new HashMap<String, String>();
    
    @Feature("RF_WLAN_Profile.Premium") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T39776") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("After devices are onboarded, user can assign default RF profile(Open ofiice, Outdoor) to the APs or user can create new RF profile and assign it to APs. So that RF profile properties will be pushed to group of APs in a network.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T39776") // It's a testcase id/link from Jira Test Case.

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
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccount();
       
    }


    
    @Step("Test Step 2: Delete device and enable IGMP")
    public void step2() {
        
        new WirelessQuickViewPage();
        String RFName = "Open Office";
        String RFName1 = "Outdoor";
        new DevicesDashPage().AssignRF(WebportalParam.ap1serialNo, RFName); 
        new DevicesDashPage().AssignRF(WebportalParam.ap2serialNo, RFName1); 
        String RF = new DevicesDashPage().devicesRF(WebportalParam.ap1serialNo).getText();
        String RF1 = new DevicesDashPage().devicesRF(WebportalParam.ap2serialNo).getText();
        assertTrue(RF.contains(RFName),"RF is not set properly");
        assertTrue(RF1.contains(RFName1),"RF is not set properly");
        new DevicesDashPage().UNAssignRF(WebportalParam.ap1serialNo); 
        new DevicesDashPage().UNAssignRF(WebportalParam.ap2serialNo); 
        
    }
    
    
    
    
  
       

}
