package webportal.CFD.CFD_7_6.DevicePasswod.PRJCBUGEN_T33329;

import static org.junit.Assert.assertFalse;
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
import util.RunCommand;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.ManagerPage;
import webportal.weboperation.NetworkEditNetworkPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;
import webportal.weboperation.MDNSPage;

/**
 *
 * @author RaviShankar
 *
 */
public class Testcase extends TestCaseBase {
    
    Random random = new Random();
    int addition=random.nextInt(10);



    @Feature("Device_Password") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T33329") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify changing device admin password will lead to SSH disabling") // It's a testcase                                                                                                                           // Test Case.
    @TmsLink("PRJCBUGEN_T33329") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }
    
    
   

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new NetworkEditNetworkPage(false).modifyDeviceAdminPassword(WebportalParam.adminPassword);
    }

    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        new OrganizationPage().openOrg(WebportalParam.Organizations);
        handle.gotoLoction();

    }

    
    @Step("Check for Device password under Owner")
    public void step2() {
       new NetworkEditNetworkPage().modifyDeviceAdminPassword(WebportalParam.adminPassword+addition);
       MyCommonAPIs.sleepi(30);
       assertTrue(!new RunCommand().enableSSH4APResponse());
      
    }
    

}
