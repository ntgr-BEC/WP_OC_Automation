package webportal.Israel5ghz.PRJCBUGEN_T28499;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
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
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    
    String cmd = "iwconfig wifi1vap0 | grep Access";

    @Feature("Israel5ghz") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN-T28499") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Instant Wifi  for  Israel  for wax630") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T28499") // It's a testcase id/link from Jira Test Case.

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
    
    @Step("Test Step 2: Add Another AP in to Israel")
    public void step2() {
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ap2serialNo);
        devInfo.put("Device Name", WebportalParam.ap2deveiceName);
        devInfo.put("MAC Address", WebportalParam.ap2macaddress);

        new DevicesDashPage(false).addNewDevice(devInfo);
    }

    @Step("Test Step 3: Create SSid ")
    public void step3() {
              
       // new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap1serialNo);
        new WirelessQuickViewPage().instantWifiIsrael5ghz();
        
        
      
    }
    

}
