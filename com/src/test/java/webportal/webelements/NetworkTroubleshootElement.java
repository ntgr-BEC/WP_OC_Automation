/**
 *
 */
package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.openqa.selenium.JavascriptExecutor;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;

/**
 * @author xuchen
 *
 */
public class NetworkTroubleshootElement extends MyCommonAPIs {
    Logger logger;

    public NetworkTroubleshootElement() {
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
    }

    public static SelenideElement selectAllDevice = $x("//*[@name='selectAllDevice']/../i");

    public SelenideElement selectOneDevice(String devicseName) {
        SelenideElement Device = $x("//*[text()='" + devicseName + "']/../../..//i");
        return Device;
    }

    public SelenideElement selectOneDeviceTestResult(String devicseName) {
        SelenideElement Device = $x("(//*[text()='" + devicseName + "']/../../../../../td[3]//span)[last()]");
        return Device;
    }

    public SelenideElement selectOneDeviceTestTimestamp(String devicseName) {
        SelenideElement Device = $x("(//*[text()='" + devicseName + "']/../../../../../td[2]//p)[last()]");
        return Device;
    }

    public SelenideElement selectOneinput(String devicseName) {
        SelenideElement Device = $x("//*[text()='" + devicseName + "']/../../..//input");
        return Device;
    }

    public static SelenideElement                    troubleshootBtn        = $x("//a[text()='" + WebportalParam.getLocText("Troubleshoot") + "']");
    public static SelenideElement                    btnRunTest             = $(".btn.saveBtn");
    public static SelenideElement                    btnCancel              = $(".btn.cancelBtn");
    public static SelenideElement                    DNSLookup              = $x("//p[text()='DNS Lookup']");
    public static SelenideElement                    ooklaSpeedtest         = $x("//*[@value='ooklaRadio']");
    public static SelenideElement                    testNow                = $x(
            "(//button[text()='" + WebportalParam.getLocText("Test Now") + "'])[last()]");
    
    
    public static SelenideElement                    RunTest1                = $x("//button[text()='Run Test']");
    
    public static SelenideElement                    domainName             = $("#dnsLookUpTest input");
    public static SelenideElement                    domainName1             = $("#pingAndTraceTest input");
    public static SelenideElement                    testAgain              = $x(
            "//button[text()='" + WebportalParam.getLocText("Test Again") + "']");
    public static SelenideElement     runtestBtn                            = $x("//button[text()='Run Test' and @class='btn saveBtn']");
    public static SelenideElement     selectAllRadioBtn                     = $x("//*[@id=\"DNSTable\"]/thead/tr/th[1]/span/label/i");
    public static String                             testAllResultsElements = "//*[text()='Model']/../../../../../td[3]";
    public static String                             testAllResults         = "//*[text()='Model']/../../../../../td[3]//span[2]";
    public static SelenideElement                    erroralert             = $("#showErrorDiv div");
    public static SelenideElement                    troubleshootlink       = $x("//a[@href='/#/Troubleshoot/Troubleshoot']");
    public static SelenideElement                    dnslookupclose         = $x(
            "//div[contains(@class,'dnsLookupSuccessful in')]//button[@class='close']");
    public static SelenideElement                    testinProcess          = $x(
            "//p[text()='" + WebportalParam.getLocText("Test in Progress...") + "']");
    public static SelenideElement                    filter                 = $("[data-tooltip='Filter Devices']");
    public static SelenideElement                    labelap                = $x("//*[text()='Access Point']");
    public static SelenideElement                    labelswitch            = $x("//*[text()='Switch']");
    public static SelenideElement                    apply                  = $x(
            "(//button[text()='" + WebportalParam.getLocText("Apply") + "'])[last()]");
    public final static Map<String, SelenideElement> filters                = new HashMap<String, SelenideElement>() {
                                                                                {
                                                                                    put("ap", labelap);
                                                                                    put("switch", labelswitch);
                                                                                }
                                                                            };
    public static SelenideElement                    search                 = $(".icon-search");
    public static SelenideElement                    searchfield            = $x("//input[@id='troubleshootSearchField']");
    public static SelenideElement                    searchclose            = $x("//*[@id=\"content\"]/div[2]/div/div[2]/ul/li/div/div/span");
    public static String                             devices                = "//p[contains(text(),'Serial Number')]";
    public static SelenideElement                    option                 = $(".icon-overflow");
    public static SelenideElement                    optiondnslookup        = $x(
            "//a[contains(@class,'displayOptions') and text()='" + WebportalParam.getLocText("DNS Lookup") + "']");
    public static SelenideElement                    optionapply            = $x(
            "(//button[text()='" + WebportalParam.getLocText("Apply") + "'])[1]");
    public static SelenideElement                    lablednslookup         = $x("//th[text()='" + WebportalParam.getLocText("DNS Lookup") + "']");

    public static SelenideElement                    Ping                    = $x("//input[@value='pingRadio']/..");
    public static SelenideElement                    Traceroute              = $x("//input[@value='traceRadio']/..");
    public static SelenideElement                    pingdomainName          = $("#pingAndTraceTest input");
    public static SelenideElement                    TraceRoutedomainName    = $("#pingAndTraceTest input");
    public static SelenideElement                    RunTest                 = $x("//*[@id=\"main\"]/div/div[4]/div/div/div[3]/button[2]");
    public static SelenideElement                    DomainNameCheck         = $x("//*[@id=\"pingAndTraceTest\"]/div/div[3]/div/div[1]/div[1]");
    public static SelenideElement                    PacketTransmittedcheck  = $x("//*[@id=\"pingAndTraceTest\"]/div/div[3]/div/div[2]/div/p[9]");
    public static SelenideElement                    CompleteResult          = $x("//*[@id=\"pingAndTraceTest\"]/div/div[3]/div/div[2]");
    
    public static SelenideElement                    CompleteResult1          = $x("(//*[@class=\"OrganizationPro\"])[1]");
    public static SelenideElement                    CloseResult             = $x("//*[@id=\"main\"]/div/div[4]/div/div/div[1]/button/img");
    public static SelenideElement                    CompleteResultTraceRoute = $x("//*[@id=\"pingAndTraceTest\"]/div/div[3]/div/div[2]");
    public static SelenideElement                    Pinglookupclose         = $x( "//*[@id=\"main\"]/div/div[3]/div/div/div[1]/button/img");
    public static SelenideElement                    DeviceTroubleshoot      = $x("(//*[contains(text(), 'Troubleshoot')])[2]");
    public static SelenideElement                    DevicePing              = $("#divPgBdyStatics ul > li:nth-child(1)");
    public static SelenideElement                    DeviceTraceroute        = $x("#divPgBdyStatics ul > li:nth-child(4)");
    public static SelenideElement                    DeviceTraceroute2        = $x("//*[text()=\"Traceroute\"]");
    
    public static SelenideElement                    RunTestDevicelevel      = $x("//*[@id='divDPingTraceRoute']//button[text()='Run Test']");
    public static SelenideElement                    selectResultDevicelevel = $x("//*[@id=\"divDPingTraceRoute\"]/div[4]/div/div[3]/p/span/span[2]");
    public static SelenideElement                    AdvanceSettings            = $x("//*[@id=\"pingAndTraceTest\"]/div/div[2]/p/span[2]");
    public static SelenideElement                    AdvanceSettingsDevicelevel = $x("//*[@id=\"pingAndTraceTest\"]/div[2]/div/div/p/span[2]");
    public static SelenideElement                    ViewPreviosResult          = $x("//*[@id=\"divDPingTraceRoute\"]/div[4]/div[1]/div[1]/p[2]/span");
    public static SelenideElement                    devicelevelresult          = $x("//*[@id=\"divDPingTraceRoute\"]/div[4]/div/div[3]/div/div[2]");
    public static SelenideElement                    OtherScreen          = $x("//*[@id=\"spnTitDevSwitchChnlUit\"]");
    
    public static SelenideElement                    PingCount               = $x("//input[@name='pingCount']");
    public static SelenideElement                    PacketSize              = $x("//input[@name='packetSize']");
    public static SelenideElement                    PingTimeout             = $x("//input[@name='pingTimeout']");
    public static SelenideElement                    PingInterval            = $x("//input[@name='pingInterval']");
    public static SelenideElement                    packetsPerHop           = $x("//input[@name='packetsPerHop']");
    public static SelenideElement                    maxTtl                  = $x("//input[@name='maxTtl']");
    public static SelenideElement                    initTtl                 = $x("//input[@name='initTtl']");
    public static SelenideElement                    interval                = $x("//input[@name='interval']");
    public static SelenideElement                    port                    = $x("//input[@name='port']");
    public static SelenideElement                    size                    = $x("//input[@name='size']");
    public static SelenideElement                    ErrorMessage            = $x("//*[@id=\"pingAndtraceModalError\"]/div");
    public static SelenideElement                    RunTestAgain            = $x("//*[@id=\"pingAndTraceTest\"]/div/div[1]/div[2]/button");
    public static SelenideElement                    Success            = $x("//*[@id=\"DNSTable\"]/tbody/tr/td[5]/div/span/p[1]/span/span[2]");
    
    
    public SelenideElement selectResult(String devicseName) {
        SelenideElement Device = $x("(//*[text()='" + devicseName + "']/../../../../..//span)[5]");
        return Device;
    }
    public SelenideElement selectResulttraceRoute(String devicseName) {
        //SelenideElement Device = $x("(//*[text()='" + devicseName + "']/../../../../..//span)[11]");
        //SelenideElement Device = $x("(//*[text()='" + devicseName + "']/../../../../..//span//span[2])[2]");  //use this for maint-qa
      //*[text()='6WP2285HA0624']/../../../../..//span//span[2]
        SelenideElement Device = $x("//*[text()='" + devicseName + "']/../../../../..//span//span[2]");    //updated for priqa
        return Device;
    }
    public SelenideElement selectResulttraceRouteStatus(String devicseName) {
     // use this for maint-beta SelenideElement Device = $x("(//*[text()='" + devicseName + "']/../../../../..//span)[12]");
        SelenideElement Device = $x("(//*[text()='" + devicseName + "']/../../../../..//span//span[2])");  //use this for maint-qa
        return Device;
    }
    
    //AddedByPratik
    public static SelenideElement                    ookalaspeedTest         = $x("//th[text()='Ookla Speedtest']");
    public static SelenideElement                    runOkalaTest            = $x("//button[text()='Run Test' and @class ='btn saveBtn']");
    public static SelenideElement                    okalaspeedTestRadioBtn  = $x("//p[text()='Ookla Speedtest']");
    public static SelenideElement                    testNowBtn              = $x("//button[text()='Test Now']");
    public static SelenideElement                    successMsgLink          = $x("//span[text()='Success']");
    public static SelenideElement                    okalaSpeedtestLogoimg   = $x("//img[@src='assets/img/commonIcons/speedtest-by-ookla.png']");
    public static SelenideElement                    resultMessage           = $x("//h6[text()='Ookla Speedtest Result']");
    public static SelenideElement                    tableHeader1            = $x("(//table/thead/tr)[3]/th[1]");
    public static SelenideElement                    tableHeader2            = $x("(//table/thead/tr)[3]/th[2]");
    public static SelenideElement                    tableHeader3            = $x("(//table/thead/tr)[3]/th[3]");
    public static SelenideElement                    tableinfo1              = $x("(//table/tbody/tr)[2]/td[1]");
    public static SelenideElement                    tableinfo2              = $x("(//table/tbody/tr)[2]/td[2]");
    public static SelenideElement                    tableinfo3              = $x("(//table/tbody/tr)[2]/td[3]");

public SelenideElement selectResulttraceRoute1(String devicseName) {
    //SelenideElement Device = $x("(//*[text()='" + devicseName + "']/../../../../..//span)[11]");
    SelenideElement Device = $x("(//*[text()='" + devicseName + "']/../../../../..//span//span[2])[2]");  //use this for maint-qa
    //SelenideElement Device = $x("//*[text()='" + devicseName + "']/../../../../..//span//span[2]");    //updated for priqa
    return Device;
}

}
