package webportal.ClientIsolationWithDLAN.PRJCBUGEN_T21429;

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
    
    Map<String, String> locationInfo = new HashMap<String, String>();
    

    @Feature("ClientIsolationWithDLAN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T21429") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify if the Copy Configuration copies the Client Isolation to the Target location.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T21429") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new AccountPage().deleteOneLocation("OnBoardingTest");
        new WirelessQuickViewPage().deleteSsidYes("apwp21421");

    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);

        handle.gotoLoction();
//        new DevicesDashPage().checkDeviceInNormalAccount("admin"); //must input admin or manager
    }

    @Step("Test Step 2: Create SSID with Client Isolation enabled")
    public void step2() {
        
        locationInfo.put("SSID", "apwp21421");
        locationInfo.put("Security", "WPA2 Personal Mixed");
        locationInfo.put("Password", "123456798");
        new WirelessQuickViewPage().addSsid1(locationInfo);
        
        new WirelessQuickViewPage().enableclientIsolation(locationInfo.get("SSID"));
    }
    
 
//    @Step("Test Step 3: Verify client enabled")
//    public void step3() {
//        
//        MyCommonAPIs.sleepi(60);
//        String msg = new APUtils(WebportalParam.ap1IPaddress).clientIsolation( WebportalParam.ap1Model);        
//        assertTrue(msg.contains("clientSeparation 1"),"default  value for 2.4 Ghz is not right");  
//        
//    }
    
    @Step("Test Step 4: Create Location and create copy")
    public void step4() {
        
        Map<String, String> locationInfo1 = new HashMap<String, String>();
        locationInfo1.put("Location Name", "OnBoardingTest");
        locationInfo1.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo1.put("Zip Code", "4560");
        locationInfo1.put("Country", "Australia");
        new AccountPage().addNetwork(locationInfo1);
        
       
        new WirelessQuickViewPage().CopyConfig(WebportalParam.Organizations, WebportalParam.location1, "OnBoardingTest" );
        handle.gotoLoction("OnBoardingTest");    
        assertTrue (new WirelessQuickViewPage().isEnabledClientIsolation(locationInfo.get("SSID")), "after copy client isolation is not enabled");
        
        

}
}