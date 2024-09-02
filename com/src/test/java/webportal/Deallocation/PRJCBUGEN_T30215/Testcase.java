package webportal.Deallocation.PRJCBUGEN_T30215;

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
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    
    OrganizationPage OrganizationPage = new OrganizationPage();
    String organizationName1 = "PRJCBUGEN_T30215";
    HashMap<String, String> locationInfo     = new HashMap<String, String>();
    String                  locationName     = "PRJCBUGEN_T30215";
    Map<String, String>     devInfo          = new HashMap<String, String>();

    @Feature("Deallocation") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T30215") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that  user should able to delete location along with added device from org.") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T30215") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new OrganizationPage().deleteOrganizationNew(organizationName1);
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
    }

    @Step("Test Step 2: create Organization;")
    public void step2() {  

        
        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName1);

       
        OrganizationPage.addOrganization(organizationInfo);

        OrganizationPage.addAllocateCredits(organizationName1, "1", "0", "0");
       
    }
    
    
    @Step("Test Step 3: allocate credit")
    public void step3() {  

    OrganizationPage.openOrg(organizationName1);

    locationInfo.put("Location Name", locationName);
    locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
    locationInfo.put("Zip Code", "12345");
    locationInfo.put("Country", "China");
    new AccountPage(false).addNetwork(locationInfo);

    new AccountPage(false).enterLocation(locationInfo.get("Location Name"));

  
}
    
    @Step("Test Step 4: allocate credit")
    public void step4() {  
        
     
        devInfo.put("Serial Number1", WebportalParam.ap5serialNo);
        devInfo.put("MAC Address1", WebportalParam.ap5macaddress);
        
     
        new DevicesDashPage(false).addNewdummyDevice(devInfo);
        
        HashMap<String, String> creditsInfo = new HamburgerMenuPage().getCreditAllocationStatus(organizationName1);
        assertTrue(
                creditsInfo.get("Used Devices Credits").equals("1") && creditsInfo.get("Unused Devices Credits").equals("0")
                        && creditsInfo.get("Used ICP Credits").equals("0") && creditsInfo.get("Unused ICP Credits").equals("0"),
                "Allocate credits error.");
        
    }
}
