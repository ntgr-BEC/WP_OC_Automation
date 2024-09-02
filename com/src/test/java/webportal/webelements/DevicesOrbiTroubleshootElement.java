/**
 *
 */
package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import java.util.logging.Logger;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

/**
 * @author lavi
 *
 */
public class DevicesOrbiTroubleshootElement extends MyCommonAPIs {
    Logger logger;

    public DevicesOrbiTroubleshootElement() {
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
    }

    public static SelenideElement linkDNSLookup = $(".leftMenuItems a[href*=DNSLookup]");
    public static SelenideElement linkSpeedTest = $(".leftMenuItems a[href*=OoklaSpeedTest]");
    
    /////////////  new elements below  /////////////
    public static SelenideElement btnRun          = $x("//button[text()='Run Test' or text()='Test Now']");
    public static SelenideElement liPingTest      = $x("//li[text()='Ping Test']");
    public static SelenideElement inputHostName   = $("input[name*=hostName]");
    public static SelenideElement inputDNSLookupHost   = $x("//*[@id='dnsLookUpTest']/input");
    public static SelenideElement textSuccess = $x("//span[text()='Success']");
    public static SelenideElement textFail    = $x("//span[text()='Failed']");
    
    public static SelenideElement liDNSLookup     = $x("//li[text()='DNS Lookup']");
    
    public static SelenideElement liSpeedTest     = $x("//li[text()='Ookla Speedtest']");
    
    public static SelenideElement liTraceRoute    = $x("//li[text()='Traceroute']");
    

    /**
     * @param pageIndex
     *            0 - ping test 
     *            1 - dns lookup
     *            2 - ookla speedtest
     *            3 - traceroute
     */
    public void gotoPage(int pageIndex) {
        /*
        if (0 == pageIndex) {
            linkDNSLookup.click();
        } else {
            linkSpeedTest.click();
        }
        */
        if (0 == pageIndex) {
            liPingTest.click();
        } else if (1 == pageIndex) {
            liDNSLookup.click();
        } else if (2 == pageIndex) {
            liSpeedTest.click();
        } else if (3 == pageIndex){
            liTraceRoute.click();
        }
        waitReady();
    }

    public static SelenideElement btnRunTest         =  $x("//*[text()='Run Test']"); //$("#divCOnSecLed button");
    public static String          txtResultTimestamp = ".ooklaTestResult h6";
    public static String          txtResultValue     = ".ooklaTestResult tbody td";

    // speedtest
    public void runTest() {
        btnRunTest.click();
        waitReady();
        sleep(20, "wait testing result");
        for (int i = 0; i < 30; i++) {
            if (getTexts(txtResultTimestamp).get(1).length() > 0) {
                break;
            }
            sleepi(4);
        }
    }
    
}
