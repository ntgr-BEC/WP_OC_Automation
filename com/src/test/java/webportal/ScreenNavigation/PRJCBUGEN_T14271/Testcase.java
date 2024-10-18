package webportal.ScreenNavigation.PRJCBUGEN_T14271;

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
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.DeviceBulkOnboardingPage;
import webportal.weboperation.DeviceScreenNavigationPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("ScreenNavigation") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14271") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("wireless navigation of All Setting Tabs ") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14271") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }
  

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
    }

    @Step("Test Step 2: Verify Wireless page;")
    public void step2() {
       
        assertTrue(new  DeviceScreenNavigationPage().verifyWireless(),"wireless Screen is not complete");
        MyCommonAPIs.sleepi(15);
        
    }
    
    @Step("Test Step 3: Verify WifiandCapitivePortal Tab  page;")
    public void step3() {
       
        assertTrue(new  DeviceScreenNavigationPage().WifiandCapitivePortalTab(),"WifiandCapitivePortal Tab Screens are not complete");
        MyCommonAPIs.sleepi(3);
    }
    
    @Step("Test Step 4: WifiSchedulesTab page;")
    public void step4() {
       
        assertTrue(new  DeviceScreenNavigationPage().WifiSchedulesTab(),"WifiSchedulesTab Screens are not complete");
        MyCommonAPIs.sleepi(3);
    }
    
    @Step("Test Step 5: URLfilteringTab page;")
    public void step5() {
        assertTrue(new  DeviceScreenNavigationPage().URLfilteringTab(),"URLfilteringTab Screens are not complete");
        MyCommonAPIs.sleepi(3);
    }
    @Step("Test Step 6: InstantWifiTab page;")
    public void step6() {
        assertTrue(new  DeviceScreenNavigationPage().InstantWifiTab(),"InstantWifiTab Screens are not complete");
        MyCommonAPIs.sleepi(3);
    }
    @Step("Test Step 7: FastRoamingTab page;")
    public void step7() {
        assertTrue(new  DeviceScreenNavigationPage().FastRoamingTab(),"FastRoamingTab Screens are not complete");
        MyCommonAPIs.sleepi(3);
    }
    @Step("Test Step 8: AirBridegGroupsTab page;")
    public void step8() {
        assertTrue(new  DeviceScreenNavigationPage().AirBridegGroupsTab(),"AirBridegGroupsTab Screens are not complete");
        MyCommonAPIs.sleepi(3);
    }
    @Step("Test Step 9: LoadBalancingTab page;")
    public void step9() {
        assertTrue(new  DeviceScreenNavigationPage().LoadBalancingTab(),"LoadBalancingTab Screens are not complete");        
        MyCommonAPIs.sleepi(3);
    }
    @Step("Test Step 10: AdvancedWirelessSettingTab page;")
    public void step10() {
        assertTrue(new  DeviceScreenNavigationPage().AdvancedWirelessSettingTab(),"AdvancedWirelessSettingTab Screens are not complete");
        MyCommonAPIs.sleepi(3);
    }
    @Step("Test Step 11: AdvancedNetworkSettingTab page;")
    public void step11() {
        assertTrue(new  DeviceScreenNavigationPage().AdvancedNetworkSettingTab(),"AdvancedNetworkSettingTab Screens are not complete");
        MyCommonAPIs.sleepi(3);
    }
    @Step("Test Step 12: MeshSettingTab page;")
    public void step12() {
        assertTrue(new  DeviceScreenNavigationPage().MeshSettingTab(),"MeshSettingTab Screens are not complete");
        MyCommonAPIs.sleepi(3);
    }
    @Step("Test Step 13: ManagementLanSettingTab page;")
    public void step13() {
        assertTrue(new  DeviceScreenNavigationPage().ManagementLanSetting(),"ManagementLanSettingTab Screens are not complete");
        MyCommonAPIs.sleepi(3);
    }
}
