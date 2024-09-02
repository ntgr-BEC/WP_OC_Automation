package webportal.B2UC.Pro.PRJCBUGEN_T23090;

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

    @Feature("B2UC_Pro") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T23090") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify if the feature Broadcast to unicast gets copied when cloning of the configuration is done") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T23090") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new AccountPage().deleteOneLocation("OnBoardingTest");
        new WirelessQuickViewPage().B2UCDisable();
        
        
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInNormalAccount("admin");
    }

    @Step("Test Step 2: Enable B2UC;")
    public void step2() {
        
        new WirelessQuickViewPage().B2UCEnable();
        MyCommonAPIs.sleepi(120);       
        new RunCommand().enableSSH4AP(WebportalParam.loginPassword, WebportalParam.ap1serialNo);
        assertTrue(new APUtils(WebportalParam.ap1IPaddress).getB2UCEnableStatus(WebportalParam.ap1Model), "B2UC is disabled");
                          
    }

    @Step("Test Step 3: Create Location and create copy")
    public void step3() {
        
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "OnBoardingTest");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "4560");
        locationInfo.put("Country", "Australia");
        new AccountPage().addNetwork(locationInfo);
        
       
        new WirelessQuickViewPage().CopyConfig(WebportalParam.Organizations, WebportalParam.location1, "OnBoardingTest" );
        handle.gotoLoction("OnBoardingTest");
        
        assertTrue(new WirelessQuickViewPage().IsB2UCenabled(),"B2UC is not enabled");
        
    }

}
