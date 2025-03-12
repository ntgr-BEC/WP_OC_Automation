package webportal.Seperation80211.prousER.PRJCBUGEN_T39886;

import static org.testng.Assert.assertFalse;
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
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.HardBundlePage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;


/**
 *
 * @author Ravishankar S
 *
 */
public class Testcase extends TestCaseBase {
    
    Map<String, String> ssidInfo = new HashMap<String, String>();
   
    

    @Feature("Seperation 802.11 and 802.11kv") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T39886") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify enabling band steering will also enable 802..1kv") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T39886") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().deleteSsidYes("apwp39886");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
        new HamburgerMenuPage(false).closeLockedDialog();
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        handle.gotoLoction();      
    }

  

        @Step("Test Step 2:  Add WIFI ssid with 80211kv disabled and check for config push;")
        public void step2() {
          
            ssidInfo.put("SSID", "apwp39886");
            ssidInfo.put("Security", "WPA2 Personal Mixed");
            ssidInfo.put("Password", "123456798");
            ssidInfo.put("802.11kv", "false");
            new WirelessQuickViewPage().addSsid(ssidInfo); 
            assertFalse(new APUtils(WebportalParam.ap1IPaddress).getKVREnableStatus(WebportalParam.ap1Model),"80211kv is enabled");       
        }
    
    @Step("Test Step 3: enable BandSteering and check for config push;")
    public void step3() {
        new WirelessQuickViewPage(false).bandSteering(ssidInfo.get("SSID"));
        assertTrue(new APUtils(WebportalParam.ap1IPaddress).getBandSteeringStatus(WebportalParam.ap1Model),"BandSteering  not enabled");
        assertTrue(new APUtils(WebportalParam.ap1IPaddress).getKVREnableStatus(WebportalParam.ap1Model),"80211kv is not enabled");       
     }

    }
    