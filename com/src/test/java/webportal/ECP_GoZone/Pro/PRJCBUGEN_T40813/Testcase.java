package webportal.ECP_GoZone.Pro.PRJCBUGEN_T40813;

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
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Ravishankar S
 *
 */
public class Testcase extends TestCaseBase {
    
    Map<String, String> ssidInfo = new HashMap<String, String>();
    Map<String, String> ECPInfo = new HashMap<String, String>();

    @Feature("ECP_GoZone") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T40813") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("remove wall garden functionally.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T40813") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().deleteALLSSID();
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
        handle.gotoLoction();

    }

    @Step("Test Step 2: Add WIFI ssid with WPA2 Enterprise security and enable ECP;")
    public void step2() {    
        ssidInfo.put("SSID", "apwp40813");
        ssidInfo.put("Security", "Open");
//        ssidInfo.put("Password", "123456798");
        new WirelessQuickViewPage().addSsid1(ssidInfo);
        ECPInfo.put("Walled Garden", "*.smartwifiplatform.com");
        ECPInfo.put("Walled Garden1", "*.4wifi-e2.net");
        ECPInfo.put("Splash Page URL", "https://splash.4wifi-e2.net/hotspotlogin.php");
        ECPInfo.put("Captive Portal Authentication Type", "Radius");
        new WirelessQuickViewPage().enableECP(ssidInfo.get("SSID"), ECPInfo);

    }
    
    @Step("Test Step 3: Validate Walled Garden;")
    public void step3() {
        String CMD = "WalledGarden" ;
        String Result = new APUtils(WebportalParam.ap1IPaddress).getECPWalledGarden(WebportalParam.ap1Model,  CMD);
        System.out.println(Result);
        assertTrue(Result.contains(ECPInfo.get("Walled Garden")), "ECP walled Garden is not applied");
        assertTrue(Result.contains(ECPInfo.get("Walled Garden1")), "ECP walled Garden is not applied");
                
    }
    
    @Step("Test Step 4: Remove ECP;")
    public void step4() {
        Map<String, String> ECPInfo = new HashMap<String, String>();
        ECPInfo.put("Walled Garden Remove", "Select All");
        new WirelessQuickViewPage().disableECP(ssidInfo.get("SSID"), ECPInfo);
        
    }
    @Step("Test Step4: Validate Config")
    public void step5() {
        
        String CMD = "WalledGarden" ;
        String Result = new APUtils(WebportalParam.ap1IPaddress).getECPWalledGarden(WebportalParam.ap1Model,  CMD);
        System.out.println(Result);
        assertFalse((Result.contains(ECPInfo.get("Walled Garden"))) && (Result.contains(ECPInfo.get("Walled Garden1"))), "ECP walled Garden is not applied");
             
    }
    

 
}
