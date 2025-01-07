package webportal.PurchaseOrderHistoryEnhancements.Pro.HardBundleCategory.PRJCBUGEN_T41532;

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
    @Story("PRJCBUGEN_T41532") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify in the purchase order history we get the entry of the onboarded hard bundle device under the Insight Included with Hardware") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T41532") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }
    
    @Step("Test Step 1: Login to pro account success.")
    public void step1() {

        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);        
           
    }
    
    @Step("Test Step 2: Verify One Year Insight Included with Hardware PRO on admin account")
    public void step2() {
        
        new  HardBundlePage().gotoOneYearInsightIncludedwithHardwarePRO();
        MyCommonAPIs.sleepi(5);
        assertTrue(new  HardBundlePage().srNounderOneYearInsightIncludedwithHardwarePRO(WebportalParam.ap5serialNo).exists(),"We are not getting entry for Onboarded Hardbindle AP under the Insight Included with Hardware ");
        assertTrue(new  HardBundlePage().srNounderOneYearInsightIncludedwithHardwarePRO(WebportalParam.sw1serialNo).exists(),"We are not getting entry for Onboarded Hardbindle Switch under the Insight Included with Hardware ");
        assertTrue(new  HardBundlePage().srNounderOneYearInsightIncludedwithHardwarePRO(WebportalParam.br1serialNo).exists(),"We are not getting entry for Onboarded Hardbindle BR under the Insight Included with Hardware ");
        assertTrue(new  HardBundlePage().srNounderOneYearInsightIncludedwithHardwarePRO(WebportalParam.ob1serialNo).exists(),"We are not getting entry for Onboarded Hardbindle Orbi under the Insight Included with Hardware ");
        assertTrue(new  HardBundlePage().srNounderOneYearInsightIncludedwithHardwarePRO(WebportalParam.pr1serialNo).exists(),"We are not getting entry for Onboarded Hardbindle Orbi under the Insight Included with Hardware ");
        
    }
        
    @Step("Test Step 3: Verify One Year Insight Included with Hardware PRO on Secondary Admin account")
    public void step3() {
        
        String sCheck = "[alt=\"User Image\"]";
        System.out.println("try to do logout");
        $(sCheck).click();
        $(Selectors.byCssSelector(".open ul li:last-child a")).click();
        System.out.println("user is logout");
        MyCommonAPIs.waitReady();
        
        MyCommonAPIs.sleepi(10);
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.inputLogin(WebportalParam.SecondadminName,WebportalParam.SecondadminPassword);  
        new OrganizationPage(false).clickonOkayGotit();
        new  HardBundlePage().gotoOneYearInsightIncludedwithHardwarePRO();
        MyCommonAPIs.sleepi(5);
        assertTrue(new  HardBundlePage().srNounderOneYearInsightIncludedwithHardwarePRO(WebportalParam.ap5serialNo).exists(),"We are not getting entry for Onboarded Hardbindle AP under the Insight Included with Hardware ");
        assertTrue(new  HardBundlePage().srNounderOneYearInsightIncludedwithHardwarePRO(WebportalParam.sw1serialNo).exists(),"We are not getting entry for Onboarded Hardbindle Switch under the Insight Included with Hardware ");
        assertTrue(new  HardBundlePage().srNounderOneYearInsightIncludedwithHardwarePRO(WebportalParam.br1serialNo).exists(),"We are not getting entry for Onboarded Hardbindle BR under the Insight Included with Hardware ");
        assertTrue(new  HardBundlePage().srNounderOneYearInsightIncludedwithHardwarePRO(WebportalParam.ob1serialNo).exists(),"We are not getting entry for Onboarded Hardbindle Orbi under the Insight Included with Hardware ");
        assertTrue(new  HardBundlePage().srNounderOneYearInsightIncludedwithHardwarePRO(WebportalParam.pr1serialNo).exists(),"We are not getting entry for Onboarded Hardbindle Orbi under the Insight Included with Hardware ");
        
    }
    
}
