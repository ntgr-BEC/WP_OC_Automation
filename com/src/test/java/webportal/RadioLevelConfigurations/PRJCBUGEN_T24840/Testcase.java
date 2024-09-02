package webportal.RadioLevelConfigurations.PRJCBUGEN_T24840;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
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
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.publicstep.WebCheck;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    private static final String OnBoardingTest = null;

    @Feature("RadioLevelConfigurations") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T24840") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify, network level radio channel config is a part of copy configuration") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T24840") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new AccountPage().deleteOneLocation("OnBoardingTest");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);

        handle.gotoLoction();
    }

    @Step("Test Step 2:  disable 2.4Ghz, 5 and 6 ghz")
    public void step2() {
    String Enable = "0";
    new WirelessQuickViewPage().GoToWirelessSettings();
    new WirelessQuickViewPage(false).Enable24(Enable);
    new WirelessQuickViewPage(false).DropDown5GhzLowWireless.click();
    new WirelessQuickViewPage(false).Enable5low(Enable);
    new WirelessQuickViewPage(false).DropDown5GhzHighWireless.click();
    new WirelessQuickViewPage(false).Enable5high(Enable);   
    
    }
    
    @Step("Test Step 3: Create Location and create copy")
    public void step3() {
        
        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.openOrg(WebportalParam.Organizations);
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "OnBoardingTest");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "4560");
        locationInfo.put("Country", "Australia");
        new AccountPage().addNetwork(locationInfo);
        
       
        new WirelessQuickViewPage().CopyConfig(WebportalParam.Organizations, WebportalParam.location1, "OnBoardingTest" );
        handle.gotoLoction("OnBoardingTest");
        
    }
    
    @Step("Test Step 4: Create Location and create copy")
    public void step5() {
        new WirelessQuickViewPage().GoToWirelessSettings();
        assertTrue(!new WirelessQuickViewPage(false).EnableorDisable24(),"2.4 is not enabled by default");
        assertTrue(!new WirelessQuickViewPage(false).EnableorDisable5High(),"5hgh is not enabled by default");
        assertTrue(!new WirelessQuickViewPage(false).EnableorDisable5Low(),"5 low is not enabled by default");
    }
    }
    
