package webportal.DeviceGroup.CustomGroupNPI.WithAP.PRJCBUGEN_T32383;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
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
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DeviceGroupPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;
import webportal.webelements.DeviceGroupElement;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    
    OrganizationPage OrganizationPage = new OrganizationPage();
    Map<String, String> locationInfo = new HashMap<String, String>();

    @Feature("DeviceGroup CustomGroupNPI") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T32383") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify, advanced rate selection can be configured") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T32383") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        OrganizationPage.openOrg(WebportalParam.Organizations);
        new DeviceGroupPage().GoToDeviceGroup(WebportalParam.location1);
        MyCommonAPIs.sleepi(5);
        new DeviceGroupPage().goinsideDG("Automation1");
        MyCommonAPIs.sleepi(5);
        new DeviceGroupPage().DGWiFi.click();
        MyCommonAPIs.sleepi(5);
        new WirelessQuickViewPage(false).deleteSsidYes(locationInfo.get("SSID"));
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        
        OrganizationPage.openOrg(WebportalParam.Organizations);
    }

    @Step("Test Step 2: Check CG name and description shown;")
    public void step2() {
        
        new DeviceGroupPage().GoToDeviceGroup(WebportalParam.location1);
        MyCommonAPIs.sleepi(5);
        new DeviceGroupPage().goinsideDG("Automation1");
        MyCommonAPIs.sleepi(5);
        new DeviceGroupPage().DGWiFi.click();
       
    }
    
    @Step("Test Step 3: Add WIFI ssid and now connect client to this ssid;")
    public void step3() {
        
       
        locationInfo.put("SSID", "apwp22741");
        locationInfo.put("Security", "WPA2 Personal Mixed");
        locationInfo.put("Password", "123456798");
        new WirelessQuickViewPage(false).addSsidDG(locationInfo);
        
        int densitylevel = 3;
        new WirelessQuickViewPage(false).GoToAdvanceRateCntrol(locationInfo.get("SSID"));
        new WirelessQuickViewPage(false).enableSetMinimumRateControl();        
        new WirelessQuickViewPage(false).DragDensityTo(densitylevel);
        new WirelessQuickViewPage(false).GoToAdvanceRateCntrol(locationInfo.get("SSID"));
        assertTrue(new WirelessQuickViewPage(false).Density0Text2Ghz(densitylevel), "Advanceratelimit was enabled by default");
        
        String msg = new APUtils(WebportalParam.ap1IPaddress).getAdvancedRateSelectionConfig24( WebportalParam.ap1Model);
        
        assertTrue(msg.contains("vapSettingTable:wlan0:vap0:densityLevel 3"),"default  value for 2.4 Ghz is not right");
        
    }

}
