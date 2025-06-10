package webportal.CFD.CFD_7_6.DeviceCount.PRJCBUGEN_T33317;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
   
    String onlinedevices="3";
    String offlinedevices="4";
    Map<String, String> firststdevInfo = new HashMap<String, String>();
    StringBuilder temp =new StringBuilder(WebportalParam.ap5serialNo);


    @Feature("Totaldevices_Count") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T33317") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description(" Test to verified currently online and offline device count at location level") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T33317") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        new MyCommonAPIs().gotoLoction(WebportalParam.location1);
        new DevicesDashPage(false).GoToDevicesDashPage();
        new DevicesDashPage(false).deleteDeviceNo(WebportalParam.ap5macaddress);
        
    }
              
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
    }
    
    
    @Step("Test Step 2: Onboard Ap's")
    public void step2() {
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        new MyCommonAPIs().gotoLoction(WebportalParam.location1);
               
        firststdevInfo.put("MAC Address1", WebportalParam.ap5macaddress);  
        firststdevInfo.put("Serial Number1", WebportalParam.ap5serialNo);  
        new DevicesDashPage().addNewdummyDevice(firststdevInfo);

    }
    
    @Step("Test Step3: Assert the count")
    public void step3() {
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        assertTrue(new AccountPage(false).VerifyDeviceCountOnHomeScreen(WebportalParam.location1,2,onlinedevices,offlinedevices));
    }

  

}
