package webportal.WAX650ESpecificCases.PRJCBUGEN_T27098;

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
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;
/**
 * @author Tejeshiwni K V
 */

public class Testcase extends TestCaseBase {

    @Feature("WAX650ESpecificCases") 
    @Story("PRJCBUGEN_T27098") 
    @Description("Verify when changing the band from non 6 Ghz band to 6 Ghz, security gets auto configured to WPA3 Mixed Personal") 
    @TmsLink("PRJCBUGEN_T27098") 

    @Test(alwaysRun = true, groups = "p2") 
    public void test() throws Exception {
        runTest(this);
    }
   
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        AccountPage AccountPage =new AccountPage();
        MyCommonAPIs.waitReady();
        new WirelessQuickViewPage().deleteSsidYes("apwp23809");
    //    new AccountPage().deleteSsidVlan(WebportalParam.location1, "VLAN16598");
        System.out.println("start to do tearDown");
    }

    @Step("Test Step 1: Log in to a premium account;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
        handle.gotoLoction();
    }

    
    @Step("Test Step 2:Enable functionality for 802.11w feature")
    public void step2(){
        
        Map<String, String> ssidInfo = new HashMap<String, String>();
        ssidInfo.put("SSID", "apwp23809");
        ssidInfo.put("Security", "WPA2 Personal");
        ssidInfo.put("Password", "12345678");
        new WirelessQuickViewPage().addSsid(ssidInfo);
        new WirelessQuickViewPage().EditSSID(ssidInfo);
        assertTrue(new WirelessQuickViewPage(false).changeWifiBand(ssidInfo));                         
    }
    
}


