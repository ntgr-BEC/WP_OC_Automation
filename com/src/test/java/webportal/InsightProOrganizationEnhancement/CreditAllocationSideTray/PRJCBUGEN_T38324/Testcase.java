package webportal.InsightProOrganizationEnhancement.CreditAllocationSideTray.PRJCBUGEN_T38324
;

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

    @Feature("InsightProOrganizationEnhancement") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T38324") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify whether on side tray has the credits column that shows the number of the device count credits for a single LMS key.") // It's                                                                                                                                                     // Jira
    // Test
    // Case.
    @TmsLink("PRJCBUGEN_T38324") // It's a testcase id/link from Jira Test Case.

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

        //WebportalLoginPage webportalLoginPage = new WebportalLoginPage(false);
        new WebportalLoginPage(true).loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
           
    }
        
      @Step("Test Step 2: Go to Credit Allocation and verify allocated device credits side tray license key is visible or not")

      public void step2() throws InterruptedException {
          
          assertTrue(new HamburgerMenuPage().verifyAllocatedDeviceCreditsSideTrayLicenseKey()," Alloated Device Credit Tray is not opened now and license key is not verified ");

      }
      
}
