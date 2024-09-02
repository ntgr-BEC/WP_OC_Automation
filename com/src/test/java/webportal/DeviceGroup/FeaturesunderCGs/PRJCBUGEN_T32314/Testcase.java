package webportal.DeviceGroup.FeaturesunderCGs.PRJCBUGEN_T32314;

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
    String organizationName = "PRJCBUGEN_T13880";

    @Feature("DeviceGroup FeaturesunderCGs") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T32314") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, on deleting organization all CG and devices with CG gets deleted") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T32314") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
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
        
    }
    
    @Step("Test Step 2: Create Organization;")
    public void step2() {
        OrganizationPage page = new OrganizationPage();
        page.gotoPage();
        
        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName);
        page.addOrganization(organizationInfo);
        OrganizationPage.openOrg(organizationName);

    }
    
    
    @Step("Test Step 3: Create Location;")
    public void step3() {
        


        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "OnBoardingTest");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "4560");
        locationInfo.put("Country", "Australia");
        new AccountPage(false).addNetwork(locationInfo);
        
    }
    
    @Step("Test Step 4: Check CG name and description shown;")
    public void step4() {
        OrganizationPage.openOrg(organizationName);
        
        new DeviceGroupPage().GoToDeviceGroup("OnBoardingTest");
        MyCommonAPIs.sleepi(5);
        new DeviceGroupPage().CreateDGGroup("Automation1", "Check Grop creation");
        MyCommonAPIs.sleepi(5);
        
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ap1serialNo);
        devInfo.put("MAC Address1", WebportalParam.ap1macaddress);

        assertTrue(new DeviceGroupPage().addNewDevicedropdown(devInfo, "Automation1"),"dropdown to select CG step is shown");
        
    }
    
    @Step("Test Step 5: Delete Organization;")
    public void step5() {
        OrganizationPage page = new OrganizationPage();
        page.deleteOrganizationNew(organizationName);
    }
    
    
}
