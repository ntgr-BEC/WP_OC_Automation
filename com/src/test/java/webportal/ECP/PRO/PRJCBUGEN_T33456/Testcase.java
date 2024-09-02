package webportal.ECP.PRO.PRJCBUGEN_T33456;

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
import webportal.param.WebportalParam;
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
    
    
    Map<String, String> locationInfo = new HashMap<String, String>();

    @Feature("ECP") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T33456") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Error message is shown or not When ECP to enabled and trying to change that ssid security to WPA2/3 enterprise.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T33456") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        new OrganizationPage(false).goToOrgSsid(WebportalParam.Organizations);
        new WirelessQuickViewPage(false).deleteOrgSsid("apwp33456");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);

        handle.gotoLoction();
//         new DevicesDashPage().checkDeviceInNormalAccount("admin"); //must input admin or manager
    }

    @Step("Test Step 2: Add WIFI ssid with WPA2 Enterprise security and enable ECP;")
    public void step2() {
        
        new WirelessQuickViewPage().deleteALLORGSSID();
        new WirelessQuickViewPage().deleteALLSSID();
        
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        new OrganizationPage(false).goToOrgSsid(WebportalParam.Organizations);
        locationInfo.put("SSID", "apwp33456");
        locationInfo.put("Security", "WPA2 Personal");
        locationInfo.put("Password", "123456789");
        locationInfo.put("ECP", "true");
        new WirelessQuickViewPage(false).CreateECP(locationInfo);       

    }
    
    @Step("Test Step 3: edit ECP;")
    public void step3() {
        
        Map<String, String> EcpInfo = new HashMap<String, String>();
        EcpInfo.put("Security", "WPA2 Enterprise");       
        assertTrue(new WirelessQuickViewPage(false).editECP(locationInfo.get("SSID"), EcpInfo), "ECP did ot through error ");
        
    }
    

 
}
