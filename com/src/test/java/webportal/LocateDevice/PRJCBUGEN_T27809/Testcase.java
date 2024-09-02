package webportal.LocateDevice.PRJCBUGEN_T27809;

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
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesApSummaryPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("Locate Device") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T27809") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, Locate device functionality is for all AP models 505,510,540,610,610Y,620,630") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T27809") // It's a testcase id/link from Jira Test Case.

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

    @Step("Test Step 2: click on reset on AP under device,AP should go to restore configure and come online with cloud configure;")
    public void step2() {
        
        new DevicesDashPage().enterDeviceViadeviceSceen(WebportalParam.ap1serialNo);
        MyCommonAPIs.sleepi(10);
        
        for(int i =0; i<5; i++) {
            if(!new WirelessQuickViewPage(false).LocatedeviceClick.exists()) {
               System.out.println("locate device  exit");
               break;
            }
            MyCommonAPIs.sleepi(60);
            System.out.println("locate device does not exit");
        }
     
        if(WebportalParam.ap1Model.contains("WAC505") || WebportalParam.ap1Model.contains("WAC510") ||
           WebportalParam.ap1Model.contains("WAC540") || WebportalParam.ap1Model.contains("WAC564") ||
           WebportalParam.ap1Model.contains("WAX610") || WebportalParam.ap1Model.contains("WAX610Y") ||
           WebportalParam.ap1Model.contains("WAX620") || WebportalParam.ap1Model.contains("WAX630") ||
           WebportalParam.ap1Model.contains("WAX628") || WebportalParam.ap1Model.contains("WAX608Y"));
        
        
         assertTrue(new WirelessQuickViewPage(false).Locatedevice.exists(),"Locate device boutton not exits");    
        
    }

}
