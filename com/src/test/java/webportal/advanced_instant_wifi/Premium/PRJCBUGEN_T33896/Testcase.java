package webportal.advanced_instant_wifi.Premium.PRJCBUGEN_T33896;

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
import util.RunCommand;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Vivek Singh
 *
 */
public class Testcase extends TestCaseBase {

    String              region       = "";
    Map<String, String> locationInfo = new HashMap<String, String>();
    Random              r           = new Random();
    int                 num         = r.nextInt(10000000);
    String              mailname    = "apwptest" + String.valueOf(num);

    @Feature("Advance Instant WIFI 6.10") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T32896") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify save button is enable, when we open radio settings by clicking on plus icon") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T33896") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
    }

    @Step("Test Step 2: Verify Account have one Location and one AP in connected state ")
    public void step2() {
        handle.gotoLoction();
        assertTrue(new DevicesDashPage().isDeviceConnected(WebportalParam.ap1serialNo));

    }

    @Step("Test Step 3: Enable SSH and Create one SSID")
    public void step3() {
        new RunCommand().enableSSH4APALL(WebportalParam.ap1IPaddress);
        new WirelessQuickViewPage().deleteALLSSID();
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("SSID", "ap@33862");
        locationInfo.put("Band", "Both");
        locationInfo.put("Security", "WPA2 Personal");
        locationInfo.put("Password", "Netgear1@");
        new WirelessQuickViewPage().addSsid1(locationInfo);

    }

    @Step("Test Step 4: Login Tera Term and Go to instant wifi page")
    public void step4() {
        new APUtils(WebportalParam.ap1IPaddress);
        new WirelessQuickViewPage();
        new WirelessQuickViewPage(false).open_instant_wifi_page();
    }
    
    @Step("Test Step 5: Verify RadioSetting Save Button is working ")
    public void step5() {
        
        new WirelessQuickViewPage(false).clickOnPlusIconForOpen_RadioSetting();
        assertTrue(new WirelessQuickViewPage(false).clickingOnSaveButtonAndVerifySuccessMsg());
        
        
    }
}