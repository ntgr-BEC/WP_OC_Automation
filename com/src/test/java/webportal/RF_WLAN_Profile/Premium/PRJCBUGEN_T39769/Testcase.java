package webportal.RF_WLAN_Profile.Premium.PRJCBUGEN_T39769;

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
    
    @Feature("RF_WLAN_Profile.Premium") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T39769") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that for each set of predefined RF Profiles should have the following columns with preferred values") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T39769") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().GotoRF();
        new WirelessQuickViewPage(false).deleteRF(RFdata.get("RFName"));
        System.out.println("start to do tearDown");

    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        //new DevicesDashPage().checkDeviceInAdminAccount();
       
    }

    @Step("Test Step 2: Create RF Profile and verify delete options")
    public void step2() {
        
        RFdata.put("RFName", "Netgear");
        RFdata.put("RFDescription", "BEC Automation Team");       
        
        new WirelessQuickViewPage().GotoRF();
        new WirelessQuickViewPage(false).CreateRFProfile(RFdata);
        
            
        assertTrue(!new WirelessQuickViewPage(false).deleteRFprofile("Open Office").isDisplayed(), "");
        assertTrue(!new WirelessQuickViewPage(false).deleteRFprofile("Outdoor").isDisplayed(), "");
        assertTrue(new WirelessQuickViewPage(false).deleteRFprofile(RFdata.get("RFName")).isDisplayed(), "");
                
       
    }
    
    
    
  
       

}
