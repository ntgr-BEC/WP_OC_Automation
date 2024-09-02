package webportal.RF_WLAN_Profile.PRO.PRJCBUGEN_T39766;

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
    
    String RFName = "Netgear";
    String RFDescription = "BEC Automation Team";
    String RFNameedit = "Insight";
    String RFDescriptionedit = "BEC Insight Automation Team";

    @Feature("RF_WLAN_Profile.PRO") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T39766") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that when RF profile is selected, we should display the checkbox of \"Copy Configurations of a RF profile with preferred settings\" and \"Select Preferred RF profile\" drop-down") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T39766") // It's a testcase id/link from Jira Test Case.

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
        new DevicesDashPage().checkDeviceInNormalAccount("admin"); //must input admin or manager
       
    }

    @Step("Test Step 2: Delete device and enable IGMP")
    public void step2() {
       
        new WirelessQuickViewPage().GotoRF();
        Map<String, String> RFdata = new HashMap<String, String>();
        RFdata.put("RFName", "Netgear");
        RFdata.put("RFDescription", "BEC Automation Team");
        new WirelessQuickViewPage().CreateRFProfile(RFdata);        
        assertTrue(new WirelessQuickViewPage().checkRFExist(RFName),"RF Not created");
       
    }
    
    @Step("Test Step 3: Create Location and create copy")
    public void step3() {
    
    Map<String, String> locationInfo = new HashMap<String, String>();
    locationInfo.put("Location Name", "OnBoardingTest");
    locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
    locationInfo.put("Zip Code", "4560");
    locationInfo.put("Country", "Australia");
    new AccountPage().addNetwork(locationInfo);
    
   
    new WirelessQuickViewPage().CopyConfig(WebportalParam.Organizations, WebportalParam.location1, "OnBoardingTest" );
    handle.gotoLoction("OnBoardingTest");
    
}
    
    @Step("Test Step 4: Create Location and create copy")
    public void step4() {
        handle.gotoLoction("OnBoardingTest");
        new WirelessQuickViewPage().GotoRF();
        assertTrue(new WirelessQuickViewPage().checkRFExist(RFName),"RF Not created");       

}
  
       

}
