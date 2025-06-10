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
    Random random = new Random();
    int n = random.nextInt(7);
    int count=n;
    String onlinedevices="3";
    String offlinedevices="4";
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
        new OrganizationPage(false).deleteOrganization("Netgearnew");

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
        new HamburgerMenuPage().configCreditAllocation("Netgearnew", 10, 0, 0);
        OrganizationPage.openOrg("Netgearnew");
        MyCommonAPIs.waitReady();
        new AccountPage(false).enterLocation(locationInfo.get("Location Name"));
        
       
                        
    }
    
    @Step("Test Step 3: Onboard Ap's")
    public void step3() {              
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
    
    @Step("Test Step4: Assert the count")
    public void step4() {
        assertTrue(new OrganizationPage().VerifyDeviceCountOnHomeScreen("Netgearnew",count,onlinedevices,offlinedevices));
    }

    private String replaceSerial(StringBuilder serialparam , String replacer) {
        // TODO Auto-generated method stub
        System.out.print("converted number is "+serialparam.replace(11,13,replacer));
        return serialparam.replace(11,13,replacer).toString();
    }

}
