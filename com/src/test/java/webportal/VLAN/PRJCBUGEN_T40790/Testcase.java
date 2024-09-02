package webportal.VLAN.PRJCBUGEN_T40790;

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
import util.RunCommand;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredVLANPageForVLANPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author  Pratik
 *
 */
public class Testcase extends TestCaseBase {

    String vlan ="VLANPort5";
    String id ="5";
    Map<String, String> locationInfo = new HashMap<String, String>();

    @Feature("VLAN") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T40790") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Check  in Wired Setting  --> VLAN/ Network Setup existing VLAN on edit if user tries to move next page without assigning an Port mode to the selected ports we will get Warning message") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T40790") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        System.out.println("start to do tearDown");
        handle.gotoLoction();
//        new WiredVLANPageForVLANPage().deleteVLAN(vlan);

    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccountswitch();
        new DevicesDashPage().checkDeviceInAdminAccountswitch2();
    }

    @Step("Test Step 2: Create VLAN and edit same vlan and select ports")
    public void step2() {
        
//        new WiredVLANPageForVLANPage().CreateVLANToTestPort(vlan, id);
//        assertTrue(new WiredVLANPageForVLANPage().editExistingVLANSelectportsAndSave(vlan),"VLAN is not created successfully by selecting ports");
           
    }
    
}
