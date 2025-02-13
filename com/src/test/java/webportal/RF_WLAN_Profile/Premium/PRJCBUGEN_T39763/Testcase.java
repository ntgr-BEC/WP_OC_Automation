package webportal.RF_WLAN_Profile.Premium.PRJCBUGEN_T39763;

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

    String RFNameedit = "Insight";
    String RFDescriptionedit = "BEC Insight Automation Team";

    @Feature("RF_WLAN_Profile.Premium") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T39763") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that in Manage Profiles, we should provide an option to create, view, edit and delete RF profiles in Premium User → Location → Wireless → Settings → Profiles") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T39763") // It's a testcase id/link from Jira Test Case.

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
       
        new WirelessQuickViewPage().GotoRF();
        Map<String, String> RFdata = new HashMap<String, String>();
        RFdata.put("RFName", "Netgear");
        RFdata.put("RFDescription", "BEC Automation Team");
        new WirelessQuickViewPage().CreateRFProfile(RFdata);
        assertTrue(new WirelessQuickViewPage().checkRFExist(RFdata.get("RFName")),"RF Not created");
       
    }
    
    
    @Step("Test Step 3: Delete device and enable IGMP")
    public void step3() {
       
        new WirelessQuickViewPage().GotoRF();
        new WirelessQuickViewPage(false).clickEditRF(RFNameedit,RFDescriptionedit);
        new WirelessQuickViewPage().GotoRF();
        assertTrue(new WirelessQuickViewPage(false).checkRFExist(RFNameedit),"RF Not created");
       
    }
    
    @Step("Test Step 4: Delete device and enable IGMP")
    public void step4() {
       
        new WirelessQuickViewPage().GotoRF();
        new WirelessQuickViewPage(false).deleteRF(RFNameedit);
       
       
    }
    
  
       

}
