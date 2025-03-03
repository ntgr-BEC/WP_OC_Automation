package webportal.CFD.CFD_7_6.ManagedUnmanaged.PRJCBUGEN_T38157;

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
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author RaviShankar S
 *
 */
public class Testcase extends TestCaseBase {

    String message = "To add Insight-manageable devices to the organization, you need to allocate more device credits. See Account Management";

    @Feature("ManagedUnmanaged") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T38157") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("[PRO]  Test to verify the user will not be able to one more device to the location having only one credit") // It's a
    @TmsLink("PRJCBUGEN_T38157") // It's a testcase id/link from Jira Test Case.
    

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
            
        System.out.println("start to do tearDown");
        new OrganizationPage(true).deleteOrganization("Netgearnew");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
    }
    
    @Step("Test Step2: Create a Organization and Location ")
    public void step2()
    {
        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", "Netgearnew");

        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.addOrganization(organizationInfo);
        OrganizationPage.openOrg("Netgearnew");
                     
        HashMap<String, String> locationInfo = new HashMap<String, String>();      
        locationInfo.put("Location Name", "officenew");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "32003");
        locationInfo.put("Country", "United States of America");
        new AccountPage(false).addNetwork(locationInfo);              
        MyCommonAPIs.sleepi(15);
        new HamburgerMenuPage().configCreditAllocation("Netgearnew", 1, 0, 0);
        
        OrganizationPage.openOrg("Netgearnew");
        MyCommonAPIs.waitReady();
        new AccountPage(false).enterLocation(locationInfo.get("officenew"));
                
        Map<String, String> firststdevInfo = new HashMap<String, String>();
               
        firststdevInfo.put("Serial Number1", WebportalParam.ap5serialNo);
        firststdevInfo.put("MAC Address1", WebportalParam.ap5macaddress);
        
        System.out.println(firststdevInfo);
                 
        new DevicesDashPage().addNewdummyDevice(firststdevInfo);
                        
    }

 

    @Step("Test Step 4:Onboard dummy AP and Verify unused credits;")
    public void step4() {
        new OrganizationPage(false).openOrg("Netgearnew");
        new AccountPage(false).enterLocation("officenew");
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ap6serialNo);
        devInfo.put("Device Name", WebportalParam.ap6deveiceName);
        devInfo.put("MAC Address", WebportalParam.ap6macaddress);
        String validate = new DevicesDashPage().validateOnboardingError(devInfo);
        assertTrue(validate.contains(message));
        
       
    }
}
