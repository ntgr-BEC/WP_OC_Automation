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
import webportal.weboperation.WirelessQuickViewPage;


/**
 *
 * @author Pratik
 *
 */
public class Testcase extends TestCaseBase {
    

    String AP1 =new DevicesDashPage(false).GenaraterandomSerial ("4XT");
    

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
        
        new DevicesDashPage().deleteDevice1(WebportalParam.ap5serialNo);         
        System.out.println("start to do tearDown");
        
    }
    
    @Step("Test Step 1: Login to premium account success.")
    public void step1() {
        
        

        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();  
               
        new MyCommonAPIs().gotoLoction(WebportalParam.location1);
             
        
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", AP1);
        devInfo.put("Device Name", AP1);
        devInfo.put("MAC Address", "aa:bb:cc:dd:ee:ff");
        new DevicesDashPage().addNewDevice(devInfo);
           
    }
    
    @Step("Test Step 2: Verify One Year Insight Included with Hardware premium on admin account")
    public void step2() {
        
        new  HardBundlePage().gotoOneYearInsightIncludedwithHardwarePRO();
        MyCommonAPIs.sleepi(5);
        assertTrue(new  HardBundlePage().srNounderOneYearInsightIncludedwithHardwarePRO(AP1).exists(),"We are not getting entry for Onboarded Hardbindle AP under the Insight Included with Hardware ");
        
    }
    
}
