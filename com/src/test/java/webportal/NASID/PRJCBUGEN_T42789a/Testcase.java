package webportal.NASID.PRJCBUGEN_T42789a;

import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
import webportal.weboperation.DeviceGroupPage;
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
    Map<String, String> ECPInfo = new HashMap<String, String>();
//    String NASID = "abcde";
    int length = 254;
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    Random random = new Random();
    StringBuilder sb = new StringBuilder(length);
    int randomNumber = random.nextInt(1000000);
    String SSID    = "SSID" + String.valueOf(randomNumber);
    String NASID;
    
    @Feature("NASID") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T42789") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Configure the External Captive Portal SSID with maximum length custom NAS identifier") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T42789") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().deleteSsidYes(SSID);
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success and go to radious;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();
    }

    @Step("Test Step 2: Add numbers NASIS;")
    public void step2() {
        
        
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }

        NASID = sb.toString();
        System.out.println("Random Alphabet String of 553 characters:\n" + NASID);
        
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("SSID", SSID);
        locationInfo.put("Security", "WPA2 Personal Mixed");
        locationInfo.put("Password", "123456798");
        new WirelessQuickViewPage().addSsid1(locationInfo);
        ECPInfo.put("ECP Type", "GoZone Wi-Fi");
        ECPInfo.put("Walled Garden", "*.smartwifiplatform.com");
        ECPInfo.put("Walled Garden1", "*.4wifi-e2.net");
        ECPInfo.put("Splash Page URL", "https://splash.4wifi-e2.net/hotspotlogin.php");
        ECPInfo.put("Captive Portal Authentication Type", "Radius");
        ECPInfo.put("Primary Address", "3.132.31.3");
        ECPInfo.put("Secondary Address", "3.18.137.68");
        ECPInfo.put("PasswordGoZone", "Bijdragl");
        ECPInfo.put("NASID", NASID);
        
        
        new WirelessQuickViewPage().enableECP(locationInfo.get("SSID"), ECPInfo);  
        
        
    }  
    
    @Step("Test Step 3: check SSH;")
    public void step3() {
        String VapIndex = new APUtils(WebportalParam.ap1IPaddress).Addeditssid();
        Map<String, String> value = new WirelessQuickViewPage().findSSID(VapIndex, locationInfo.get("SSID"));
        System.out.println("value is "+value);
        
        new RunCommand().enableSSH4AP( WebportalParam.ap1IPaddress, WebportalParam.loginPassword);
        
        String SSHoutput = new APUtils(WebportalParam.ap1IPaddress).getNASIDofssid(WebportalParam.ap1Model, value.get("VAP"));
        assertTrue(SSHoutput.contains(NASID), "NASID is not Pushed");
    }  
     
  }    

      
     