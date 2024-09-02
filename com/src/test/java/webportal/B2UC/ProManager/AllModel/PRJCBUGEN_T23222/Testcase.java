package webportal.B2UC.ProManager.AllModel.PRJCBUGEN_T23222;

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
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author  Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("B2UC_ProManager") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T23222") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify if the feature B2UC gets configured across all the 610Y AP") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T23222") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new WirelessQuickViewPage().B2UCDisable();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
       
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
        assertTrue((WebportalParam.ap5Model == "WAX610Y"),"test case should be tested only for WAX610Y AP");
        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInNormalAccount("manager");
    }

    @Step("Test Step 2: Add All types of device;")
    public void step2() {
        
        
        new WirelessQuickViewPage().B2UCEnable();
     
    }
    
    @Step("Test Step 3: Add WIFI ssid and now connect client to this ssid;")
    public void step3() {
        assertTrue(new APUtils(WebportalParam.ap5IPaddress).getB2UCEnableStatus(WebportalParam.ap5Model), "IGMP is disabled after restoring");
        }

}
