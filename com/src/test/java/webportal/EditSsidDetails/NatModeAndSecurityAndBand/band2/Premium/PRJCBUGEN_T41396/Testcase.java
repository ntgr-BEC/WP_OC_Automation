
package webportal.EditSsidDetails.NatModeAndSecurityAndBand.band2.Premium.PRJCBUGEN_T41396;

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
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.FileHandling;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Anusha H
 *
 */
public class Testcase extends TestCaseBase {

    Map<String, String> ssidInfo = new HashMap<String, String>();
    
    @Feature("EditSsidDetails.NatModeAndSecurityAndBand.2band") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T41396") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Edit a SSID from 2.4 and 5ghz to 5ghz with NAT mode ") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T41936") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().deleteSsidYes(ssidInfo.get("SSID"));
        try {
            new WirelessQuickViewPage().deleteFolder("C:\\Auto\\filename.txt");
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
//        new DevicesDashPage().checkDeviceInNormalAccount("admin"); //must input admin or manager
    }

    @Step("Test Step 2: Add a SSID with 2.4ghz and 5ghz;")
    public void step2() {
        ssidInfo.put("SSID", "PRJCBUGEN_T41396");
        ssidInfo.put("Security", "WPA2 Personal");
        ssidInfo.put("Password", "123456798");
        ssidInfo.put("Band", "Uncheck6 GHz");
        new WirelessQuickViewPage().addAndEdit(ssidInfo);
    }  
        
    @Step("Test Step 3 :Edit a SSID from 2.4 and 5ghz to 5 ghz with NAT mode ;")
    public void step3() {
        ssidInfo.put("Band", "Click 2.4ghz");
        new WirelessQuickViewPage().addAndEditSsid(ssidInfo.get("SSID"), ssidInfo); 
        new WirelessQuickViewPage().bridgeTonat(ssidInfo.get("SSID"));
           }
           
         
    @Step("Test Step 4: Write to a file;")
    public void step4() {
             
        MyCommonAPIs.sleepi(50);
        String VapIndex = new APUtils(WebportalParam.ap1IPaddress).Addeditssid();
                 
        new FileHandling().writeFile("C:\\Auto\\filename.txt",VapIndex);       
    }
                 
    @Step("Test Step 5: To verify the SSID ids broadcasted or not;")
    public void step5() throws IOException {
             
        String n1="";
        String m1="";
        String band1 = "5.0";
            
            
        String check1 = new FileHandling().ssidBroadcast("C:\\Auto\\filename.txt",ssidInfo.get("SSID"), band1);        
        System.out.println(check1);           
        n1=check1.substring(4,5);
        m1=check1.substring(8,9);   
        System.out.print(n1+" \t"+ m1+"\n");
            
        assertTrue(new APUtils(WebportalParam.ap1IPaddress).Addeditssid1(n1,m1,WebportalParam.ap1Model), "ssid(5) not broadcasted");
        assertTrue(new APUtils(WebportalParam.ap1IPaddress).getNatStatus1(n1,m1,WebportalParam.ap1Model), "CONFIG NOT PUSHED");
    }    
}         