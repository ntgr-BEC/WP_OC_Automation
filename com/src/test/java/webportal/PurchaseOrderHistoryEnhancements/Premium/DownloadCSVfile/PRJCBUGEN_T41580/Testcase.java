package webportal.PurchaseOrderHistoryEnhancements.Premium.DownloadCSVfile.PRJCBUGEN_T41580;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;
import com.opencsv.exceptions.CsvValidationException;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.APUtils;
import util.MyCommonAPIs;
import util.RunCommand;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
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


    Random r = new Random();
    String organizationName = "Netgear1";
    String locationName     = "office";
    Map<String, String> locationInfo = new HashMap<String, String>();

    @Feature("PurchaseOrderHistoryEnhancements") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T41574") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify Insight Subscriptions CSV Download") // It's                                                                                                                                                     // Jira
    // Test
    // Case.
    @TmsLink("PRJCBUGEN_T41574") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to pro account success.")
    public void step1() {

        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();  
           
    }
        
      @Step("Test Step 2: Go to Credit Allocation and verify download csv file")

      public void step2() throws InterruptedException, CsvValidationException {
          
          new  HardBundlePage().gotoOneYearInsightIncludedwithHardwarePRO();
          MyCommonAPIs.sleepi(5);
          assertTrue(new HamburgerMenuPage().verifyInsightSubscriptionCSVFileDownload(),"CSV File is not downloaded properly");
          assertTrue(new HamburgerMenuPage().testCSVContent(),"CSV File is not verified properly");
      }
      
}
