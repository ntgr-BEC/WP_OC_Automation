package webportal.MultiAdmin.PRJCBUGEN_T30981;

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
import util.APUtils;
import util.Javasocket;
import util.MyCommonAPIs;
import util.RunCommand;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author  Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    Random r        = new Random();
    int    num      = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num) + "@sharklasers.com";
    String mailname1 = "apwptesta" + String.valueOf(num) + "@sharklasers.com";
    String Name     = "PRJCBUGEN_T30981";

    @Feature("MultiAdmin") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T30981") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Check whether User invited count numbers are getting updated") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T30981") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");  
        MyCommonAPIs.sleepi(20);
        new WirelessQuickViewPage(false).deleteSecondAdmin(mailname);
        new WirelessQuickViewPage(false).deleteSecondAdmin(mailname1);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
        
        
        new WirelessQuickViewPage(false).deleteSecondAdminALL();
    }
    
    @Step("Test Step 2: Invite secodary admin")
    public void step2() {
      
        String NumberofAdmin =  new WirelessQuickViewPage(false).checkcount(Name, mailname );       
        assertTrue(NumberofAdmin.equals("Invitation Sent:0/2"), "Admin number is not right");
        String NumberofAdmin1 =  new WirelessQuickViewPage(false).checkcount(Name, mailname1 );  
        assertTrue(NumberofAdmin1.equals("Invitation Sent:1/2"), "Admin number is not right");
    }


    
    

}
