/**
 *
 */
package webportal.weboperation;

import java.util.Map;
import java.util.logging.Logger;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.NetworkTroubleshootElement;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

/**
 * @author xuchen
 *
 */
public class NetworkTroubleshootPage extends NetworkTroubleshootElement {
    Logger logger;

    /**
     *
     */
    public NetworkTroubleshootPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon(URLParam.hrefNetworkTroubleShoot);
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    public NetworkTroubleshootPage(boolean nopage) {
        // TODO Auto-generated constructor stub
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    public void gotoAPDnsLookUp() {
        WebCheck.checkHrefIcon(URLParam.hrefAPTroubleShoot);
    }    
    
    public String runOneTest(String devicseName,String domain) {
        SelenideElement Device = selectOneDevice(devicseName);
        Device.click();
        String result = "";
        btnRunTest.click();
        DNSLookup.click();
        domainName.clear();
        domainName.setValue(domain);
        testNow.click();
        waitReady();
        sleep(20, "wait testing result");
        if (erroralert.exists() && erroralert.isDisplayed()) {
            result = erroralert.text();
        } else {
            for (int i = 0; i < 10; i++) {
                if (selectOneDeviceTestResult(devicseName).exists()) {
                    if(selectOneDeviceTestResult(devicseName).text()=="In Progress") {
                        sleepi(4);
                    } else {
                        break;
                    }
                } else {
                    result = "error when test";
                    break;
                }
            }
            result = getText(selectOneDeviceTestResult(devicseName));
        }
        logger.info("----test result:"+result);
        return result.toLowerCase();
    }


    public String runAllTest(String domain) {
        MyCommonAPIs.sleepi(10);
        selectAllDevice.click();
        String result = "";
        MyCommonAPIs.sleepi(3);
        btnRunTest.click();
        MyCommonAPIs.sleepi(10);
        DNSLookup.click();
        MyCommonAPIs.sleepi(3);
        domainName.clear();
        MyCommonAPIs.sleepi(3);
        domainName.setValue(domain);
        testNow.click();
        waitReady();
        sleep(30, "wait testing result");
        ElementsCollection resultelements = getElements(testAllResultsElements);
        ElementsCollection resultstrings = getElements(testAllResults);
        if (resultelements.size() == resultstrings.size()) {
            for(SelenideElement el : resultstrings) {
                result = result + " " + el.text();
            }
        } else {
            for(SelenideElement el : resultstrings) {
                result = result + " " + el.text();
            }
            result = result + "------ with error.";
        }
        logger.info("---test result:"+result);
        return result.toLowerCase();
    }    

    public String runOneTestOnDevice(String domain) {
        String result = "";
        domainName.clear();
        domainName.setValue(domain);
        testNow.click();
        waitReady();
        sleep(25, "wait testing result");
        if (erroralert.exists() && erroralert.isDisplayed()) {
            result = erroralert.text();
        } else {
            result = MyCommonAPIs.checkSystemCall(2,"");
        }
        result = MyCommonAPIs.checkSystemCall(2,"");
        return result.toLowerCase();
    }
    
    
    public String runOneTestOnDevicePR(String domain) {
        String result = "";
        domainName1.clear();
        domainName1.setValue(domain);
        RunTest1.click();
        waitReady();
        sleep(25, "wait testing result");
        if (erroralert.exists() && erroralert.isDisplayed()) {
            result = erroralert.text();
        } else {
            result = MyCommonAPIs.checkSystemCall(2,"");
        }
        result = MyCommonAPIs.checkSystemCall(2,"");
        return result.toLowerCase();
    }
    
    public String runPingTestOnDevice(Map<String, String> map, String domain, String SerialNo) {
        String result = "";
        btnRunTest.click();
        MyCommonAPIs.sleepi(5);
        Ping.click();
        testNow.click();
        MyCommonAPIs.sleepi(5);
        TraceRoutedomainName.clear();
        TraceRoutedomainName.setValue(domain);
        MyCommonAPIs.sleepi(5);
        if (map.containsKey("AdvanceSetting")) {
            AdvanceSettings.click();           
        }
        if (map.containsKey("Ping Count")) {
            PingCount.clear();
            PingCount.sendKeys(map.get("Ping Count"));            
        }
        if (map.containsKey("Packet Size")) {
            PacketSize.clear();
            PacketSize.sendKeys(map.get("Packet Size"));
        }
        if (map.containsKey("Ping Timeout")) {
            PingTimeout.clear();
            PingTimeout.sendKeys(map.get("Ping Timeout"));
        }
        if (map.containsKey("Ping Interval")) {
            PingInterval.clear();
            PingInterval.sendKeys(map.get("Ping Interval"));
        }
        MyCommonAPIs.sleepi(5);
        RunTest.click();       
        MyCommonAPIs.sleepi(30);
        result = getText(selectResult(SerialNo));
        System.out.println(result);
        return result.toLowerCase();        
    }
    
    public String runPingTestAgain(Map<String, String> map, String domain, String SerialNo) {
        String result = "";
        MyCommonAPIs.sleepi(3);
        waitElement(selectResult(SerialNo));
        selectResult(SerialNo).click();
        MyCommonAPIs.sleepi(5);
        RunTestAgain.click();
        MyCommonAPIs.sleepi(5);
        //TraceRoutedomainName.clear();
        TraceRoutedomainName.setValue(domain);
        MyCommonAPIs.sleepi(5);
        RunTest.click();       
        MyCommonAPIs.sleepi(30);
        result = getText(selectResult(SerialNo));
        System.out.println(result);
        return result.toLowerCase();        
    }
    
    public String runTraceRouteTestOnDevice(Map<String, String> map, String domain, String SerialNo) {
        String result = "";
        if(btnRunTest.exists()) {
        btnRunTest.click();
        }
        MyCommonAPIs.sleepi(5);
        Traceroute.click();
        testNow.click();
        MyCommonAPIs.sleepi(5);
        TraceRoutedomainName.clear();
        TraceRoutedomainName.setValue(domain);
        MyCommonAPIs.sleepi(5);
        if (map.containsKey("AdvanceSetting")) {
            AdvanceSettings.click();           
        }
        if (map.containsKey("packetsPerHop")) {
            packetsPerHop.clear();
            packetsPerHop.sendKeys(map.get("packetsPerHop"));            
        }
        if (map.containsKey("maxTtl")) {
            maxTtl.clear();
            maxTtl.sendKeys(map.get("maxTtl"));
        }
        if (map.containsKey("initTtl")) {
            initTtl.clear();
            initTtl.sendKeys(map.get("initTtl"));
        }
        if (map.containsKey("interval")) {
            interval.clear();
            interval.sendKeys(map.get("interval"));
        }
        if (map.containsKey("port")) {
            port.clear();
            port.sendKeys(map.get("port"));
        }
        if (map.containsKey("size")) {
            size.clear();
            size.sendKeys(map.get("size"));
        }
        MyCommonAPIs.sleepi(5);
        RunTest.click();       
        MyCommonAPIs.sleepi(50);
        result = getText(selectResulttraceRoute(SerialNo));
        System.out.println(result);
        return result.toLowerCase();        
    }
    
    public String runTraceRouteTestOnDevicePR(Map<String, String> map, String domain, String SerialNo) {
        String result = "";
        
        return result.toLowerCase(); 
    }
    
    public String runTraceRouteagain(Map<String, String> map, String domain, String SerialNo) {
        String result = "";
        Success.click();
        RunTestAgain.click();
        MyCommonAPIs.sleepi(5);
        TraceRoutedomainName.clear();
        TraceRoutedomainName.setValue(domain);
        MyCommonAPIs.sleepi(5);
        if (map.containsKey("AdvanceSetting")) {
            AdvanceSettings.click();           
        }
        if (map.containsKey("maxTtl")) {
            maxTtl.clear();
            maxTtl.sendKeys(map.get("maxTtl"));
        }
        MyCommonAPIs.sleepi(5);
        RunTest.click();       
        MyCommonAPIs.sleepi(50);
        result = getText(selectResulttraceRoute(SerialNo));
        System.out.println(result);
        return result.toLowerCase();        
    }
    
    public String checksucessreport(String SerialNo) {
        String result = "";
        result = getText(selectResult(SerialNo));
        System.out.println(result);
        return result.toLowerCase();        
    }
    public String checksucessreportTraceroute(String SerialNo) {
        String result = "";
        result = getText(selectResulttraceRoute(SerialNo));
        System.out.println(result);
        return result.toLowerCase();        
    }
    
    public String runPingTestOnDevicelevel(String domain) {
        String result = "";
        TraceRoutedomainName.clear();
        MyCommonAPIs.sleepi(1);
        TraceRoutedomainName.setValue(domain);
        MyCommonAPIs.sleepi(1);
        if(AdvanceSettingsDevicelevel.isDisplayed()) {
            AdvanceSettingsDevicelevel.click();
            MyCommonAPIs.sleepi(1);
        }
        if(maxTtl.isDisplayed()) {
            maxTtl.clear();
            maxTtl.sendKeys("3");
        }
        RunTestDevicelevel.click();       
        MyCommonAPIs.sleepi(40);
        result = getText(selectResultDevicelevel);
        System.out.println(result);
        return result.toLowerCase();
        
    }
    
    
    
    public void GotoDeviceDashboardTroubleShoot(String serial) {
        new WirelessQuickViewPage().enterDeviceYes(serial);
        MyCommonAPIs.sleepi(10);
        DeviceTroubleshoot.click();
        MyCommonAPIs.sleepi(10);
        
        //Selenide.open(WebportalParam.serverUrl+"#/devices/accessPoint/troubleshoot");
        JavascriptExecutor js = (JavascriptExecutor) WebDriverRunner.getWebDriver();
        js.executeScript("window.scrollBy(0, -250)");
        MyCommonAPIs.sleepi(10);
        if(DeviceTraceroute2.exists())
        {
            DeviceTraceroute2.click();
        }
        else
        {
            DeviceTraceroute.click();
        }
        MyCommonAPIs.sleepi(10);
    }
    
    public String CheckPingResult(String SerialNo) {      
        String result = "";
        MyCommonAPIs.sleepi(3);
        waitElement(selectResult(SerialNo));
        selectResult(SerialNo).click();
        MyCommonAPIs.sleepi(5);
        String CompleteResultcheck;
        if(CompleteResult1.isDisplayed()) {
        CompleteResultcheck =  getText(CompleteResult1);

        }else {
            CompleteResultcheck =  getText(CompleteResult1);            
        }
        result = CompleteResultcheck;
        System.out.println(result);
        MyCommonAPIs.sleepi(10);
        CloseResult.click();
        return result.toLowerCase();
    }
    
    public String CheckPingResultdevicelevel() {    
        String result = "";
        String CompleteResultcheck =  getText(devicelevelresult);
        System.out.println(CompleteResultcheck);
        return result.toLowerCase();
    }
    
    public String CheckTracerouteResult(String SerialNo) {      
        String result = "";
        if (selectResulttraceRoute1(SerialNo).exists() && selectResulttraceRoute(SerialNo).exists()) {
            selectResulttraceRoute1(SerialNo).click();
        } else {
            selectResulttraceRoute(SerialNo).click(); 
        }
        MyCommonAPIs.sleepi(5);
        String CompleteResultcheck =  getText(CompleteResultTraceRoute);
        result = CompleteResultcheck;
        System.out.println(result);
        if(CloseResult.isDisplayed()) {
            
            CloseResult.click();
        }
        return result.toLowerCase();
    }
    

    public void selectFilters(String[] types) {
        filter.click();
        for (int i = 0; i < types.length; i++) {
            filters.get(types[i]).click();
         }
        apply.click();
        MyCommonAPIs.sleepi(3);
    }

    public int setSearch(String value) {
        search.click();
        MyCommonAPIs.sleepi(1);
        searchfield.clear();
        MyCommonAPIs.sleepi(1);
        searchfield.setValue(value);
        MyCommonAPIs.sleepi(1);
        //searchclose.click();
        MyCommonAPIs.sleepi(3);
        ElementsCollection ecs = MyCommonAPIs.getElements(devices);
        logger.info("devices after search is:"+ecs.size());
        return ecs.size(); 
    }    
    
    public void closePopup() {
        searchclose.click();
    }
    
    public void clcikOption(String value) {
        option.click();
        if (value =="dnslookup") {
            optiondnslookup.click();
        }
        optionapply.click();
        MyCommonAPIs.sleepi(1);
        
    } 
    //AddedByPraik
    public boolean ookalaSpeedTestVerifySuccessPopup () {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        ookalaspeedTest.click();
        MyCommonAPIs.sleepi(2);
        runOkalaTest.click();
        MyCommonAPIs.sleepi(5);
        okalaspeedTestRadioBtn.click();
        MyCommonAPIs.sleepi(2);
        testNowBtn.click();
        MyCommonAPIs.sleepi(15);
        waitElement(successMsgLink);
        if (successMsgLink.exists()) {
            logger.info("Okala Speed test completed Successfully.");
            successMsgLink.click();
            MyCommonAPIs.sleepi(10);
            if (okalaSpeedtestLogoimg.exists() && resultMessage.exists() && tableHeader1.exists()
                && tableHeader2.exists() && tableHeader3.exists() && tableinfo1.exists() && tableinfo2.exists() && tableinfo3.exists()) {
                logger.info("Okala Speed test Success Popup Message is coming");
                result = true;
            } 
        }
        return result;
    }
}


