package webportal.AP.InstantWifiNew.PRJCBUGEN_T18191;

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
 * @author Pragya
 *
 */
public class Testcase extends TestCaseBase {

    String              region       = "";
    Map<String, String> locationInfo = new HashMap<String, String>();
    Random              r           = new Random();
    int                 num         = r.nextInt(10000000);
    String              mailname    = "apwptest" + String.valueOf(num);


    @Feature("AdvanceAPFunctionally.non_pro_admin") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T18191") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify instant wifi functionally as non pro user for country Luxemourg") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T18191") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new AccountPage().deleteOneLocation("OnBoardingTest");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Create IM WP account success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);

        Map<String, String> accountInfo = new HashMap<String, String>();
        accountInfo.put("First Name", mailname);
        accountInfo.put("Last Name", "T10218");
        accountInfo.put("Email Address", mailname + "@mailinator.com");
        accountInfo.put("Confirm Email", mailname + "@mailinator.com");
        accountInfo.put("Password", "Netgear#123");
        accountInfo.put("Confirm Password", "Netgear#123");
        accountInfo.put("Country", "Luxembourg");

        new HamburgerMenuPage(false).createAccount(accountInfo);
    }

    @Step("Test Step 2: create a location;")
    public void step2() {
        new HamburgerMenuPage(false).closeLockedDialog();
        HashMap<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "OnBoardingTest");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "1466");
        locationInfo.put("Country", "Luxembourg");
        new AccountPage().addNetwork(locationInfo);
        new AccountPage(false).enterLocation("OnBoardingTest");
        
    }
    
    
    @Step("Test Step 3: onboard AP;")
    public void step3() {
       WebCheck.checkHrefIcon(URLParam.hrefDevices);
        
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ap1serialNo);
        devInfo.put("MAC Address1", WebportalParam.ap1macaddress);

        new DevicesDashPage(false).addNewDevice(devInfo);

        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        new RunCommand().enableSSH4AP(WebportalParam.loginPassword, WebportalParam.ap1IPaddress);
    }
    
    @Step("Test Step 3: Change location wireless region to japan and click optimize now button, then check ap log;")
    public void step5() {

        Map<String, String> ssidInfo = new HashMap<String, String>();
        ssidInfo.put("SSID", "apwp18178");
        ssidInfo.put("Security", "WPA2 Personal Mixed");
        ssidInfo.put("Password", "123456798");
        new WirelessQuickViewPage().addSsid(ssidInfo);

        new APUtils(WebportalParam.ap1IPaddress).deleteMessages();
        if (!new WirelessQuickViewPage().optimizeInstantWifi(false)) {
            int i = 0;
            while (i < 2) {
                MyCommonAPIs.sleepi(10 * 60);
                new APUtils(WebportalParam.ap1IPaddress).deleteMessages();
                if (new WirelessQuickViewPage().optimizeInstantWifi(false)) {
                    break;
                }
            }
        }

        int count = 0;
        while (true) {
            MyCommonAPIs.sleepsync();
            if (new APUtils(WebportalParam.ap1IPaddress).checkInstantWifiProfile(false)) {
                assertTrue(true);
                break;
            } else if (count == 10) {
                assertTrue(false, "Instant wifi logs not output.");
                break;
            }
            count += 1;
        }

//        new APUtils(WebportalParam.ap1IPaddress).deleteMessages();
//        if (!new WirelessQuickViewPage().optimizeInstantWifi(false)) {
//            MyCommonAPIs.sleepi(20 * 60);
//
//            count = 0;
//            while (true) {
//                MyCommonAPIs.sleepsync();
//                if (new APUtils(WebportalParam.ap1IPaddress).checkInstantWifiProfile(false)) {
//                    assertTrue(true);
//                    break;
//                } else if (count == 10) {
//                    assertTrue(false, "Instant wifi logs not output.");
//                    break;
//                }
//                count += 1;
//            }
//        } else {
//            assertTrue(false, "Instant wifi not optimize.");
//        }

    }
}

