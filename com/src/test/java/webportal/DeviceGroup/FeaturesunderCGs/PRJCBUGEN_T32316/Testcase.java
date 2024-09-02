package webportal.DeviceGroup.FeaturesunderCGs.PRJCBUGEN_T32316;

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
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DeviceGroupPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;
import webportal.webelements.DeviceGroupElement;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    
    OrganizationPage OrganizationPage = new OrganizationPage();
    Map<String, String> devInfo = new HashMap<String, String>();
    String organizationName = "PRJCBUGEN_T13880";

    @Feature("DeviceGroup FeaturesunderCGs") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T32316") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, on wireless page filter > group by 'Custom Group Name'") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T32316") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        OrganizationPage.openOrg(WebportalParam.Organizations);
        new DeviceGroupPage().GoToDeviceGroup(WebportalParam.location1);
        MyCommonAPIs.sleepi(5);
        new DeviceGroupPage().deleteDG("Automation1");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        
        OrganizationPage.openOrg(WebportalParam.Organizations);
    }
    

    @Step("Test Step 2: Check CG name and description shown;")
    public void step2() {
       
        
        new DeviceGroupPage().GoToDeviceGroup(WebportalParam.location1);
        MyCommonAPIs.sleepi(5);
        new DeviceGroupPage().CreateDGGroup("Automation1", "Check Grop creation");
        MyCommonAPIs.sleepi(5);
        
       
        devInfo.put("Serial Number", WebportalParam.ap1serialNo);
        devInfo.put("MAC Address1", WebportalParam.ap1macaddress);

        assertTrue(new DeviceGroupPage().addNewDevicedropdown(devInfo, "Automation1"),"dropdown to select CG step is shown");      
        
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        
    }
    

    @Step("Test Step 3: Go o wireless page;")
    public void step3() {
        new WirelessQuickViewPage();
        
        assertTrue(new DeviceGroupPage().DeviceFilter("Automation1",  WebportalParam.ap1serialNo ),"dropdown to select CG step is shown");    
    }
    
    
}
