package webportal.NightlyBuild.PRJCBUGEN_T34370;

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
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.DeviceBulkOnboardingPage;
import webportal.weboperation.DeviceScreenNavigationPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Anusha H
 *
 */
public class Testcase extends TestCaseBase {
    
    
   

    @Feature("NightlyBuild") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T34370") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Topology page Navigation in the pro account") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T34370") // It's a testcase id/link from Jira Test Case.

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
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInNormalAccount("admin");
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
      
    }

    @Step("Test Step 2: Verify Topology page")
    public void step2() {
        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap1serialNo);
        assertTrue(new  DeviceScreenNavigationPage().verifyDeviceToplology()," Topology Screen is not complete");
        MyCommonAPIs.sleepi(15); 
        }
        
       
    @Step("Test Step 3: Verify Topology page")
    public void step3() {
            new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap1serialNo);
            assertTrue(new  DeviceScreenNavigationPage().verifyDeviceToplologyOption()," Topology options is not complete");
            MyCommonAPIs.sleepi(15); 
        }
        }

