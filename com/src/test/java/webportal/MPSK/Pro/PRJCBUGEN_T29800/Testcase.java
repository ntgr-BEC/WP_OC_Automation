package webportal.MPSK.Pro.PRJCBUGEN_T29800;

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
import util.APUtils;
import util.Javasocket;
import util.MyCommonAPIs;
import util.RunCommand;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author  Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    private static final String OnBoardingTest = null;

    @Feature("MPSK") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T29800") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, MPSK is a part of copy configuration") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T29800") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new AccountPage().deleteOneLocation("OnBoardingTest");
        new WirelessQuickViewPage().deleteAllMPSKKey();        
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInNormalAccount("admin");
    }

    @Step("Test Step 2: Add MPSK Key")
    public void step2() {
        assertTrue(new WirelessQuickViewPage().addMPSKKey1(), "MPSK key is not added successfully");
        
                          
    }

    @Step("Test Step 3: Create Location and create copy")
    public void step3() {
        
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "OnBoardingTest");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "4560");
        locationInfo.put("Country", "Australia");
        new AccountPage().addNetwork(locationInfo);
       
        handle.gotoLoction("OnBoardingTest");
        
        assertTrue(new WirelessQuickViewPage().VerifyMPSK(), "MPSK key is not added successfully");
    }

}
