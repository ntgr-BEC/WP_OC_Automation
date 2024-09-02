package webportal.InstantWiFiForMode.PRJCBUGEN_T36393;

import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesApSummaryPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.FileHandling;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    
    Map<String, String> locationInfo = new HashMap<String, String>();
    
    @Feature("InstantWiFiForMode") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T36393") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify instant WIFI, when AP mode is selected in 11ACmode under 5 Ghz") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T36393") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
   
        System.out.println("start to do tearDown");
        new WirelessQuickViewPage().deleteSsidYes(locationInfo.get("SSID"));
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInNormalAccount("admin"); //must input admin or manager
    }

    @Step("Test Step 2: Create SSID;")
    public void step2() {
        
        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap1serialNo);            //reboot is required as RF data will not generated if we run again and again in regression

        new DevicesApSummaryPage().clickReboot();

        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo); 
       
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("SSID", "apwp36388");
        locationInfo.put("Security", "WPA2 Personal Mixed");
        locationInfo.put("Password", "Netgear1@");
        new WirelessQuickViewPage().addSsid1(locationInfo);
    }  
    
    @Step("Test Step 3: change radio mode;")
    public void step3() {     
             
        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap1serialNo);
        new WirelessQuickViewPage(false).RadioAndChannels.click();
        MyCommonAPIs.sleepi(10);  
        String mode = "11ac";
        new WirelessQuickViewPage(false).DropDown5GhzLow.click(); 
        new WirelessQuickViewPage(false).Radiomode5low.selectOption(mode);
        new WirelessQuickViewPage(false).SaveDevicelevel.click();
        MyCommonAPIs.sleepi(60);
    }
    
    @Step("Test Step 4: Click instant WiFI and check RF data;")
    public void step4() { 
        new APUtils(WebportalParam.ap1IPaddress).deleteMessages();
        new WirelessQuickViewPage().optimizeInstantWifi(false);       
        int count = 0;
        while (true) {
            MyCommonAPIs.sleepsync();
            if (new APUtils(WebportalParam.ap1IPaddress).checkInstantWifiProfile(false)) {
                assertTrue(true);
                break;
            } else if (count == 10) {
                assertTrue(false, "Instant wifi logs not output.");
                break;
            }
            count += 1;
        }
        
        
    }
   
     }    

      
     