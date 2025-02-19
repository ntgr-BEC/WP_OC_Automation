package webportal.CFD.CFD_7_6.DeviceCount.PRJCBUGEN_T33318;

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
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author RaviShankar S
 *
 */
public class Testcase extends TestCaseBase {
    Random random = new Random();
    int n = random.nextInt(7);
    int temper=n;
    Map<String, String> firststdevInfo = new HashMap<String, String>();
    StringBuilder temp =new StringBuilder(WebportalParam.ap5serialNo);


    @Feature("Totaldevices_Count") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T33318") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the total number of the devices count on Organization Tab ") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T33318") // It's a testcase id/link from Jira Test Case.

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
        new DevicesDashPage(false).deleteAllDevices();
        
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
        System.out.println(n);    
        
        while(n>0){                    
        System.out.println(firststdevInfo);                
        new DevicesDashPage(false).addNewdummyDevice(firststdevInfo);
        String replacer = String.valueOf(random.ints(10,99).findFirst().getAsInt());
        System.out.print(replacer);
        String serialnew = replaceSerial(temp,replacer);
        firststdevInfo.put("Serial Number1",serialnew);     
        n--;
        }
                       
    }
    
    @Step("Test Step3: Assert the count")
    public void step3() {
        assertTrue(new OrganizationPage().VerifyDeviceCountOnHomeScreen(WebportalParam.Organizations,temper));
    }

    private String replaceSerial(StringBuilder serialparam , String replacer) {
        // TODO Auto-generated method stub
        System.out.print("converted number is "+serialparam.replace(11,13,replacer));
        return serialparam.replace(11,13,replacer).toString();
    }

}
