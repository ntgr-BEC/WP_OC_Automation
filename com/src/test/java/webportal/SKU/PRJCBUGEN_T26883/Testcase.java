package webportal.SKU.PRJCBUGEN_T26883;

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
import webportal.weboperation.WebportalLoginPage;


/**
 *
 * @author Tejeshwini
 *
 */
public class Testcase extends TestCaseBase {
    
   
    
    
    String                  locationName     = "OnBoardingTest";

    @Feature("SKU") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T26883") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Add  US in USA for WAX620  ") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T26883") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new AccountPage().deleteOneLocation("OnBoardingTest");
        
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to account")
    public void step1() {
        
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.loginName, WebportalParam.loginPassword);
    }

    @Step("Test Step 2: Create Location ")
    public void step2() {
        
        
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", locationName);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "United States of America");
        new AccountPage().addNetwork(locationInfo);
        
    }
    
    
    @Step("Test Step 3:  Add hardbundle device and check whether it allows to add without credit allocation")
    public void step3() {
        
        new AccountPage().enterLocation("OnBoardingTest");
        
        Map<String, String> firststdevInfo = new HashMap<String, String>();
        
        firststdevInfo.put("Serial Number1", WebportalParam.getDeviceSerialNoSKU("AP28"));
        firststdevInfo.put("MAC Address1", WebportalParam.getDeviceMacCSVSKU("AP28"));
       
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
        
        assertTrue(new DevicesDashPage().checkNumberOfDevices().equals("Onedevice"), "More device exits");
       
    }

}
