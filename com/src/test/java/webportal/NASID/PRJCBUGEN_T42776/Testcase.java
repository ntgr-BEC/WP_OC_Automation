package webportal.NASID.PRJCBUGEN_T42776;

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

import java.util.Random;

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
    String NASID = "1a";

    
    @Feature("NASID") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T42776") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Configure the Enterprise SSID with minimum length custom NAS identifier") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T42776") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new DeviceGroupPage().disable();
        new WirelessQuickViewPage().deleteSsidYes("apwp14270");
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
        
        new DeviceGroupPage().GoToeditRadious(WebportalParam.location1);
        assertTrue(new DeviceGroupPage().AddNASIDexc(NASID),"NAS is accepting more than 253");
    }  
    
  
     
  }    

      
     