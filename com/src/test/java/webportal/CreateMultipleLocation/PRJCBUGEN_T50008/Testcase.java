package webportal.CreateMultipleLocation.PRJCBUGEN_T50008;

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
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author dallas
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("Create Multiple Locations Pro") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T50008") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Add Multiple MAC ACL allow") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T50008") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
//        new WirelessQuickViewPage().deleteSsidYes("apwp14476");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
//        new DevicesDashPage().checkDeviceInAdminAccount();
    }

    @Step("Test Step 2: Enable ACL Policy popup is shown because  no device is added;")
    public void step2() {
        Map<String, String> ssidInfo = new HashMap<String, String>();
        ssidInfo.put("SSID", "apwp14476");
        ssidInfo.put("Security", "WPA2 Personal");
        ssidInfo.put("Password", "12345678");
        ssidInfo.put("type","Local ACL");
        ssidInfo.put("policy","Allow");
        new WirelessQuickViewPage().addSsid(ssidInfo);
        int MaxMacAclNum=12;
        //assertTrue(new WirelessQuickViewPage().AddMultipleMacAcl(ssidInfo.get("SSID"), 512,ssidInfo.get("policy")),"Unable to find Created Total MAC's");
        new WirelessQuickViewPage().AddMultipleMacAcl(ssidInfo.get("SSID"), MaxMacAclNum,ssidInfo.get("policy"));
//        new WirelessQuickViewPage().changeMacaclPolicyAndType(ssidInfo.get("SSID"),ssidInfo.get("type"),ssidInfo.get("policy"));
       // new WirelessQuickViewPage().enableMacAcl(ssidInfo.get("SSID"));
    }

        // lack radius type


}
