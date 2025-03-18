package webportal.RF_WLAN_Profile.Premium.PRJCBUGEN_T39765;

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
    


    @Feature("RF_WLAN_Profile.Premium") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T39765") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that RF Profiles Decription field should contain only 1-512 Characters") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T39765") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().GotoRF();
        new WirelessQuickViewPage(false).deleteRF("Netgear");
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

    @Step("Test Step 2: CreateRFProfile")
    public void step2() {
        
        
        Map<String, String> RFdata = new HashMap<String, String>();
        RFdata.put("RFName", "Netgear");
        RFdata.put("RFDescription", "Netgear, Inc., is an American computer networking company based in San Jose, California, with offices in about 22 other countries. It produces networking hardware for consumers, businesses, and service providers. The company operates in three business segments: retail, commercial, and as a service provider\r\n" + 
                "Netgear's products cover a variety of widely used technologies such as wireless (Wi-Fi, LTE and 5G), Ethernet and powerline, with a focus on reliability and ease-of-use. \r\n" + 
                "Netgear markets network products for the business sector, most notably the ProSAFE switch range.");
        RFdata.put("Copy Configurations", "Open Office");
               
        
        new WirelessQuickViewPage().GotoRF();
        String Disvalid = new WirelessQuickViewPage(false).verifyDecription(RFdata);
        assertTrue(Disvalid.contains("Enter a description with up to 512 alphanumeric characters"),"discription accepting more than 512 charecter");

       
    }
    
    
    
  
       

}
