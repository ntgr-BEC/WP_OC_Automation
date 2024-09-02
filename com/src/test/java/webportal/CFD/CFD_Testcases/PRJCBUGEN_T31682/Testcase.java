package webportal.CFD.CFD_Testcases.PRJCBUGEN_T31682;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import webportal.weboperation.DevicesApSummaryPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Anusha H
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("CFD.CFD_Testcases") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T31682") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("rate limit funcationally after reboot") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T31682") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().deleteSsidYes("apwp14479");
        new DevicesDashPage().deleteDeviceYes(WebportalParam.ap1serialNo);
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();

    }
    
    @Step("Test Step 2: Onboard a AP;")
    public void step2() {
        new MyCommonAPIs().gotoLoction(WebportalParam.location1);
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ap1serialNo);
        devInfo.put("MAC Address", WebportalParam.ap1macaddress);
        new DevicesDashPage().addNewDevice(devInfo);
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
    }

    @Step("Test Step 3: Add WIFI ssid and configure rate limit;")
    public void step3() {
        Map<String, String> ssidInfo = new HashMap<String, String>();
        ssidInfo.put("SSID", "apwp14479");
        ssidInfo.put("Security", "WPA2 Personal");
        ssidInfo.put("Password", "12345678");
        new WirelessQuickViewPage().addSsid(ssidInfo);

        new WirelessQuickViewPage().editRateLimit(ssidInfo.get("SSID"), 10.0000, 10.0000); // need modify

        int sum = 0;
        while (true) {
            MyCommonAPIs.sleepi(10);
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID apwp14479")
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
                .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp14479 12345678 WPA2PSK aes")
                .equals("true")) {
            result1 = false;
            MyCommonAPIs.sleepi(10);
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp14479 12345678 WPA2PSK aes")
                    .equals("true")) {
                result1 = true;
            }
        }

        assertTrue(result1, "Client cannot connected.");

    }

    @Step("Test Step 4: Check speedtest result;")
    public void step4() {
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

                assertTrue(Float.parseFloat(downloadSpeed) < 12 && Float.parseFloat(downloadSpeed) > 0 && Float.parseFloat(uploadSpeed) < 12
                        && Float.parseFloat(uploadSpeed) > 0, "Speedtest result is error.");
        } else {
            assertTrue(false, "Cannot run speedtest.");
        }
        new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
    }
    }
    
    @Step("Test Step 5: click on reboot on AP under device,AP should be going to reboot;")
    public void step5() {
        int upTimeBefore = new WirelessQuickViewPage().getApUptime();

        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap1serialNo);

        new DevicesApSummaryPage().clickReboot();

        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);

        int upTimeAfter = new WirelessQuickViewPage().getApUptime();

        // assertTrue(new WirelessQuickViewPage(false).checkApIsRebooting(WebportalParam.ap1serialNo));

        assertTrue(upTimeAfter < upTimeBefore, "Reboot AP device failed.");
        
        int sum = 0;
        while (true) {
            MyCommonAPIs.sleepi(10);
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID apwp14479")
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
                .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp14479 12345678 WPA2PSK aes")
                .equals("true")) {
            result1 = false;
            MyCommonAPIs.sleepi(10);
            if (new Javasocket()
                    .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect apwp14479 12345678 WPA2PSK aes")
                    .equals("true")) {
                result1 = true;
            }
        }

        assertTrue(result1, "Client cannot connected.");

    }
    
    @Step("Test Step 6: Check speedtest result after rebooting of the ap;")
    public void step6() {
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

                assertTrue(Float.parseFloat(downloadSpeed) < 12 && Float.parseFloat(downloadSpeed) > 0 && Float.parseFloat(uploadSpeed) < 12
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

