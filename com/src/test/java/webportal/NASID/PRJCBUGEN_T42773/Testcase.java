package webportal.NASID.PRJCBUGEN_T42773;

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
    String NASID = "12345678";
    
    @Feature("NASID") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T42773") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Configure the Enterprise SSID with custom NAS identifier of all numbers") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T42773") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new DeviceGroupPage().disable();
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success and go to radious;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();
    }

    @Step("Test Step 2: Add numbers NASID;")
    public void step2() {
        
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("SSID", "apwp22345");
        locationInfo.put("Security", "WPA2 Enterprise");
        new WirelessQuickViewPage().addSsid1(locationInfo);
        
        
        new DeviceGroupPage().GoToeditRadious(WebportalParam.location1);
        new DeviceGroupPage().AddNASID(NASID);
    }  
    
    @Step("Test Step 3: check SSH;")
    public void step3() {
        new RunCommand().enableSSH4AP(WebportalParam.ap1IPaddress, WebportalParam.loginPassword);
        String SSHoutput = new APUtils(WebportalParam.ap1IPaddress).getNASID(WebportalParam.ap1Model);
        assertTrue(SSHoutput.contains(NASID), "NASID is not Pushed");
    }  
     
  }    

      
     