package webportal.PR60X.Sanity.prouser.PRJCBUGEN_T34409;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.DevicesOoklaSpeedtestPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.NetworkTroubleshootPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.PRDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredVLANPage;
import webportal.weboperation.WiredVLANPageForVLANPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Anusha H
 *
 */
public class Testcase extends TestCaseBase {

    
    @Feature("PR60X.Sanity") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T34409") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to delete the VLAN.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T34409") // It's a testcase id/link from Jira Test Case.

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
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
        new HamburgerMenuPage(false).closeLockedDialog();
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);

        handle.gotoLoction();
      
        // new DevicesDashPage().checkDeviceInAdminAccountGen(WebportalParam.pr1serialNo, WebportalParam.pr1macaddress, WebportalParam.pr1deveiceName);
        
        Map<String, String> deviceinfo = new HashMap<String, String>();
        deviceinfo.put("Serial Number", WebportalParam.pr1serialNo);
        deviceinfo.put("Device Name", WebportalParam.pr1deveiceName );
        deviceinfo.put("MAC Address1", WebportalParam.pr1macaddress);
        new DevicesDashPage().checkDeviceInNormalAccountGEN( "admin", deviceinfo);
        
    }

    @Step("Test Step 2: Create a VLAN;")
    public void step2() {
        
        new WirelessQuickViewPage().gotonetworksetup();
        
}
    
    @Step("Test Step 3: Delete a VLAN;")
    public void step3() {
        
        new WiredVLANPage(true).deleteAllVlan();
}
}

