package webportal.ProfileVlan.APAdvance.Pro.PRJCBUGEN_T14479;

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
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    
    Random random = new Random();
    int randomNumber = random.nextInt(1000000);
    String SSID    = "SSID" + String.valueOf(randomNumber);

    @Feature("AdvanceAPFunctionally.AdvanceAPFunctionally") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14479") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify rate limit functionally as non pro admin") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14479") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
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
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccount();
    }

    @Step("Test Step 2: Add WIFI ssid and configure rate limit;")
    public void step2() {
        Map<String, String> ssidInfo = new HashMap<String, String>();
        ssidInfo.put("SSID", SSID);
        ssidInfo.put("Security", "WPA2 Personal");
        ssidInfo.put("Password", "12345678");
        new WirelessQuickViewPage().addSsid(ssidInfo);

        new WirelessQuickViewPage().editRateLimit(ssidInfo.get("SSID"), 20.0000, 40.0000); // need modify

        int sum = 0;
        while (true) {
            MyCommonAPIs.sleepi(10);
            if (new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID "+SSID+"")
                    .indexOf("true") != -1) {
                break;
            } else if (sum > 30) {
                assertTrue(false, "Client cannot connected.");
                break;
            }
            sum += 1;
        }

        MyCommonAPIs.sleepi(20);

        boolean result1 = true;
        if (!new Javasocket()
                .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect "+SSID+" 12345678 WPA2PSK aes")
                .equals("true")) {
            result1 = false;
            MyCommonAPIs.sleepi(20);
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect "+SSID+" 12345678 WPA2PSK aes")
                    .equals("true")) {
                result1 = true;
            }
        }

        assertTrue(result1, "Client cannot connected.");

    }

    @Step("Test Step 3: Check speedtest result;")
    public void step3() {
        String str = new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport,
                "WAFruncaptive PRJCBUGEN-T14479.py www.speedtest.net test test");
        System.out.println("Printing All Speed Test Result From Client");
        System.out.println(str);

        if (str.indexOf("ERROR:") == -1 && !str.contains("finalresult: 0")) {

            // Define the pattern to match the download speed and upload speed
            Pattern pattern = Pattern.compile("'download-speed:', u'(\\d+\\.\\d+)'\\)\\('upload-speed:', u'(\\d+\\.\\d+)'\\)");

            // Match the pattern against the input string
            Matcher matcher = pattern.matcher(str);

            // Check if the pattern is found
            if (matcher.find()) {
                // Extract the download speed and upload speed from the matched groups
                String downloadSpeed = matcher.group(1);
                String uploadSpeed = matcher.group(2);

                // Print the extracted speeds
                System.out.println("Download Speed: " + downloadSpeed);
                System.out.println("Upload Speed: " + uploadSpeed);

                assertTrue(Float.parseFloat(downloadSpeed) < 42 && Float.parseFloat(downloadSpeed) > 0 && Float.parseFloat(uploadSpeed) < 22
                        && Float.parseFloat(uploadSpeed) > 0, "Speedtest result is error.");
            } else {
                System.out.println("No match found.");
            }

        } else {
            assertTrue(false, "Cannot run speedtest.");
        }

        new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
    }

}
