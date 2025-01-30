package webportal.ProfileVlan.APAdvance.Premium.PRJCBUGEN_T14475;

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
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    
    Random random = new Random();
    int randomNumber = random.nextInt(1000000);
    String SSID    = "SSID" + String.valueOf(randomNumber);

    @Feature("ProfileVlan.AdvanceAPFunctionally") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14475") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify Fast Roaming enable as non pro admin") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14475") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().deleteSsidYes(SSID);
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.loginName,WebportalParam.loginPassword);

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccount();
    }

    @Step("Test Step 2: Add WIFI ssid and enable Fast Roaming, check ap gui config;")
    public void step2() {
        Map<String, String> ssidInfo = new HashMap<String, String>();
        ssidInfo.put("SSID", SSID);
        ssidInfo.put("Security", "WPA2 Personal");
        ssidInfo.put("Password", "12345678");
        ssidInfo.put("VLANID", "10(10)");
        new WirelessQuickViewPage().addSsid(ssidInfo);
//        String mobilityId = new WirelessQuickViewPage().enableFastRoamingAndGetMobilityid();
//        MyCommonAPIs.sleepi(20);
//
//        assertTrue(new APUtils(WebportalParam.ap1IPaddress).getMobilityId().indexOf(mobilityId) != -1, "Enable mobilityid failed");
        
        assertTrue(new WirelessQuickViewPage().enableFastRoamingAndGetMobilityid1(),"enable fast roming failed");
    }

}
