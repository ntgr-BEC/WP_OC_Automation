package webportal.PurchaseOrderHistoryEnhancements.Premium.HardBundleCategory.PRJCBUGEN_T41555;

import static com.codeborne.selenide.Selenide.$;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selectors;

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
import webportal.weboperation.HardBundlePage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;


/**
 *
 * @author Pratik
 *
 */
public class Testcase extends TestCaseBase {
    
    String                  locationName     = "OnBoardingTest";
    
    Random r = new Random();
    int num = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num);
    

    @Feature("PurchaseOrderHistoryEnhancements") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T41555") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("[Premium]Test to verify when we onboard the hard bundle device in the account then in the purchase order history create the new tile with Insight Included with Hardware") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T41555") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        new MyCommonAPIs().gotoLoction(WebportalParam.location1);
        
        Map<String, String> devInfo1 = new HashMap<String, String>();
        devInfo1.put("Serial Number", WebportalParam.sw1serialNo);
        devInfo1.put("Device Name", WebportalParam.sw1deveiceName);
        devInfo1.put("MAC Address", WebportalParam.sw1MacAddress);
        new DevicesDashPage().addNewDevice(devInfo1);
        
        Map<String, String> devInfo2 = new HashMap<String, String>();
        devInfo2.put("Serial Number", WebportalParam.br1serialNo);
        devInfo2.put("Device Name", WebportalParam.br1deveiceName);
        devInfo2.put("MAC Address", WebportalParam.br1deveiceMac);
        new DevicesDashPage().addNewDevice(devInfo2);
        
        Map<String, String> devInfo3 = new HashMap<String, String>();
        devInfo3.put("Serial Number", WebportalParam.ob1serialNo);
        devInfo3.put("Device Name", WebportalParam.ob1deveiceName);
        devInfo3.put("MAC Address", WebportalParam.ob1deveiceMac);
        new DevicesDashPage().addNewDevice(devInfo3);
        
        Map<String, String> devInfo4 = new HashMap<String, String>();
        devInfo4.put("Serial Number", WebportalParam.pr1serialNo);
        devInfo4.put("Device Name", WebportalParam.pr1deveiceName);
        devInfo4.put("MAC Address", WebportalParam.pr1macaddress);
        new DevicesDashPage().addNewDevice(devInfo4);
        
        System.out.println("start to do tearDown");
        
    }
    
    @Step("Test Step 1: Login to premium account success.")
    public void step1() {

        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();  
        new MyCommonAPIs().gotoLoction(WebportalParam.location1);
        new DevicesDashPage().deleteDevice1(WebportalParam.ap5serialNo);
        new DevicesDashPage().deleteDevice1(WebportalParam.sw1serialNo);
        new DevicesDashPage().deleteDevice1(WebportalParam.br1serialNo);
        new DevicesDashPage().deleteDevice1(WebportalParam.ob1serialNo);
        new DevicesDashPage().deleteDevice1(WebportalParam.pr1serialNo);
        
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ap5serialNo);
        devInfo.put("Device Name", WebportalParam.ap5deveiceName);
        devInfo.put("MAC Address", WebportalParam.ap5macaddress);
        new DevicesDashPage().addNewDevice(devInfo);
           
    }
    
    @Step("Test Step 2: Verify One Year Insight Included with Hardware premium on admin account")
    public void step2() {
        
        new  HardBundlePage().gotoOneYearInsightIncludedwithHardwarePRO();
        MyCommonAPIs.sleepi(5);
        assertTrue(new  HardBundlePage().srNounderOneYearInsightIncludedwithHardwarePRO(WebportalParam.ap5serialNo).exists(),"We are not getting entry for Onboarded Hardbindle AP under the Insight Included with Hardware ");
        
    }
    
}
