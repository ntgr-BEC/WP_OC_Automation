package webportal.HardBundle.PRJCBUGEN_T21466;

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
import webportal.weboperation.HardBundlePage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;


/**
 *
 * @author Tejeshwini
 *
 */
public class Testcase extends TestCaseBase {
    
    String                  organizationName = "OnBoardingTest";
    String                  locationName     = "OnBoardingTest";

    @Feature("Hardbundle") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T21466") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Pro Admin: Test to verify the  admin should be able to delete hard bundle devices") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T21466") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new OrganizationPage().deleteOrganizationNew(organizationName);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        
    }

    @Step("Test Step 2: Create Organization and Location ")
    public void step2() {
        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName);

        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.addOrganization(organizationInfo);
        OrganizationPage.openOrg(organizationName);
        HashMap<String, String> locationInfo     = new HashMap<String, String>();
        locationInfo.put("Location Name", locationName);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "China");
        new AccountPage(false).addNetwork(locationInfo);
    }
    
    @Step("Test Step 3:  Add hardbundle device")
    public void step3() {
        
        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.openOrg(organizationName);
        new AccountPage(false).enterLocation(locationName);
        
        Map<String, String> firststdevInfo = new HashMap<String, String>();
        
        firststdevInfo.put("Serial Number1", WebportalParam.getDeviceSerialNoCSV("ap1"));
        firststdevInfo.put("MAC Address1", WebportalParam.getDeviceMacCSV("ap1"));
        
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
     
    }
    
    @Step("Test Step 4:  Delete Hardbundle device")
    public void step4() {
        
        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.openOrg(organizationName);
        new AccountPage(false).enterLocation(locationName);
        new WirelessQuickViewPage().deleteDeviceYes(WebportalParam.getDeviceSerialNoCSV("ap1"));
        assertTrue(!new WirelessQuickViewPage().checkApIsExist(WebportalParam.ap1serialNo),"Device delete unsuccessful"); 
       
     
    }

}
