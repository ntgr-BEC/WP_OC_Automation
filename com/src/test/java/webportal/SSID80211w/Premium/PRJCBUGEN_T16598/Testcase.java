package webportal.SSID80211w.Premium.PRJCBUGEN_T16598;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;
/**
 * @author Pragya
 */

public class Testcase extends TestCaseBase {
    final static Logger logger = Logger.getLogger("PRJCBUGEN_T16598");
    
    ArrayList<String> lsLocationNetworks = new ArrayList<String>();
    String  sOrganizationLocationElement = "#gridView .location-name"; 

    @Feature("SSID80211w.Premium") 
    @Story("PRJCBUGEN_T16598") 
    @Description("Test to verify the enable functionality for 802.11w feature while editing secured SSID using network flow.Premium") 
    @TmsLink("PRJCBUGEN-T16598") 

    @Test(alwaysRun = true, groups = "p2") 
    public void test() throws Exception {
        runTest(this);
    }
   
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        AccountPage AccountPage =new AccountPage();
        MyCommonAPIs.waitReady();
        new WirelessQuickViewPage().deleteSsidYes("apwp16598");
    //    new AccountPage().deleteSsidVlan(WebportalParam.location1, "VLAN16598");
        System.out.println("start to do tearDown");
    }

    @Step("Test Step 1: Log in to a premium account;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
               
    }

   @Step("Test Step 2:Click on Add location icon")
    public void step2() throws IOException {
        HashMap<String, String> locationInfo = new HashMap<String, String>();      
        locationInfo.put("Location Name", WebportalParam.location1);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "China");
        new AccountPage().addNetwork(locationInfo);
       
    }
    
    @Step("Test Step 3:Enable functionality for 802.11w feature")
    public void step3(){
        AccountPage AccountPage = new AccountPage();
        HashMap<String, String> locationInfo = new HashMap<String, String>();
        Map<String, String> ssidInfo = new HashMap<String, String>();
        locationInfo.put("Network Name","VLAN16598");
        locationInfo.put("VLAN Name","VLAN16598");
        locationInfo.put("VLAN ID", "147");
        locationInfo.put("SSID", "apwp16598");
        locationInfo.put("Security", "WPA2 Personal");
        locationInfo.put("Password", "123456798");
        ssidInfo.put("SSID", "apwp16598");
        MyCommonAPIs.sleep(10000);       
        AccountPage.editSSID80211wLocation(WebportalParam.location1,locationInfo);
        new WirelessQuickViewPage().enabletoggleSSID80211wsettinglocation(ssidInfo);
                    
        }
}

