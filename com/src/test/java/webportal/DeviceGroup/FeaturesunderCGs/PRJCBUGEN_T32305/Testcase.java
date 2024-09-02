package webportal.DeviceGroup.FeaturesunderCGs.PRJCBUGEN_T32305;

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

    @Feature("DeviceGroup FeaturesunderCGs") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T32305") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, only AP/Switch/Router on-boarding with SN will have new pop up provided CG is existing at Org-level/loc level/device list page") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T32305") // It's a testcase id/link from Jira Test Case.

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

    @Step("Test Step 2: Check CG name and description shown for AP;")
    public void step2() {
        
        new DeviceGroupPage().GoToDeviceGroup(WebportalParam.location1);
        MyCommonAPIs.sleepi(5);
        new DeviceGroupPage().CreateDGGroup("Automation1", "Check Grop creation");
        MyCommonAPIs.sleepi(5);
        
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", "4XT3764726423");
        devInfo.put("MAC Address1", "94:a6:7e:a8:8d:5f");

        assertTrue(new DeviceGroupPage().addNewDevicedropdown(devInfo, "Automation1"),"dropdown to select CG step is shown");
                  
    }
    
    @Step("Test Step 3: Check CG name and description shown for orbi;")
    public void step3() {       
        
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", "52K58A5G0148F");
        devInfo.put("MAC Address1", "94:a6:7e:a8:8d:5f");

        assertTrue(new DeviceGroupPage().addNewDevicedropdown(devInfo, "Automation1"),"dropdown to select CG step is shown");           
       
    }
    
    @Step("Test Step 4: Check CG name and description shown for switch;")
    public void step4() {
        
        
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", "5V41947X80007");
        devInfo.put("MAC Address1", "94:a6:7e:a8:8d:5f");

        assertTrue(new DeviceGroupPage().addNewDevicedropdown(devInfo, "Automation1"),"dropdown to select CG step is shown");
            
       
    }
    
    @Step("Test Step 5: Check CG name and description shown for MHS;")
    public void step5() {
        
        
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", "6V476ASDFA78D");

        assertTrue(new DeviceGroupPage().NoCGGroup(devInfo, "Automation1"),"dropdown to select CG step is shown");
            
       
    }
    
    @Step("Test Step 6: Check CG name and description shown for NAS;")
    public void step6() {
        
        
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", "3HF2834728432");

        assertTrue(new DeviceGroupPage().NoCGGroup(devInfo, "Automation1"),"dropdown to select CG step is shown");
            
       
    }
    
    @Step("Test Step 7: Check CG name and description shown for airbridge;")
    public void step7() {
        
        
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", "662897F98ASDF");

        assertTrue(new DeviceGroupPage().NoCGGroup(devInfo, "Automation1"),"dropdown to select CG step is shown");
            
       
    }
    
    @Step("Test Step 8: Check CG name and description shown for BR;")
    public void step8() {
        
        
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", "5JR897F98ASDF");

        assertTrue(new DeviceGroupPage().NoCGGroup(devInfo, "Automation1"),"dropdown to select CG step is shown");
            
       
    }

}
