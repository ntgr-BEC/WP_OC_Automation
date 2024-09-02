package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import util.MyCommonAPIs;
import util.RunCommand;
import util.SwitchCLIUtils;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.publicstep.WebCheck;
import webportal.webelements.ButtonElements;
import webportal.webelements.DevicesDashPageElements;
import webportal.webelements.PRDashPageElements;
import webportal.webelements.WirelessQuickViewElement;

/**
 * @author Tejeshwini K V
 */
public class PRDashPage extends PRDashPageElements {
    final static Logger logger = Logger.getLogger("RoutersPage");
   
    public PRDashPage() {
        logger.info("init...");
    }
    
    
    public void gotoPage() {
        refresh();
        WebCheck.checkUrl(URLParam.hrefRouters);
        waitReady();
        MyCommonAPIs.sleepi(5);
    }
    
    
    public int getApUptime(String SLNo) {
        WebCheck.checkUrl(URLParam.hrefDevices);
        int upTime = 0;
        String text = getText(String.format(uptime, SLNo));
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(text);
        upTime = Integer.parseInt(m.replaceAll("").trim());
        logger.info("Uptime number:" + String.valueOf(upTime));
        return upTime;
    }
    
    public void enterDeviceYes(String serialNumber) {
        WebCheck.checkUrl(URLParam.hrefRouters);
        for (int i = 0; i < 2; i++) {
            if (checkApIsExist(serialNumber)) {
                logger.info("Enter device.");
                executeJavaScript("arguments[0].removeAttribute('class')", editModule(serialNumber));
                MyCommonAPIs.sleep(3000);
                enterDevice(serialNumber).waitUntil(Condition.visible, 60 * 1000).click();
                MyCommonAPIs.sleep(5 * 1000);
                break;
            }
            refresh();
        }
    }
    
    
    public static void checkHrefIcon(String expectUrl) {
        logger.info(expectUrl);
        System.out.println(expectUrl);
       SelenideElement icon = $("[href=\"" + expectUrl + "\"]");
        if (icon.exists()) {
            try {
                icon.click();
                MyCommonAPIs.waitReady();
            } catch (Throwable e) {
                takess();
                e.printStackTrace();
                checkUrl(expectUrl);
                Selenide.refresh();
            }
        } else {
            checkUrl(expectUrl);
        }
        
        MyCommonAPIs.sleep(5 * 1000);
    }
    
    public static void checkUrl(String expectUrl) {
        logger.info("start to check: " + expectUrl);
        String url = WebportalParam.serverUrl + expectUrl;
        MyCommonAPIs.waitReady();
        String currentUrl = getCurrentUrl();
        
        if (currentUrl.contains("#/locked") || currentUrl.toLowerCase().contains("about:blank")) {
            try {
                new UserManage().logout();
            } catch (Throwable e) {
                e.printStackTrace();
            }
            
            new WebportalLoginPage(true).defaultLogin();
            MyCommonAPIs.waitReady();
        }
        
        for (int i = 0; i < 2; i++) {
            currentUrl = getCurrentUrl();
            if (!currentUrl.equals(url)) {
                logger.info("lets open url directly");
                Selenide.open(url);
            } else {
                logger.info("lets refresh url again");
                Selenide.refresh();
            }

            MyCommonAPIs.waitReady();
            currentUrl = getCurrentUrl();
            if (currentUrl.contains(url)) {
                break;
            } else {
                logger.info("not opened, try again!");
                Selenide.refresh();
            }
        }
        MyCommonAPIs.waitReady();
    }
    
    public String getPageSource() {
        return WebDriverRunner.source();
    }
    
    public boolean checkApIsExist(String serialNumber) {
        waitReady();
        boolean result = false;
        String sElement = String.format("//td[text()='%s']", serialNumber);
        logger.info("on element: " + sElement);
        if ($x(sElement).exists()) {
            result = true;
            logger.info("Ap SN:" + serialNumber + " is existed.");
        }
        return result;
    }
    
    public void troubleshootpage() {
        troubleshoot.click();
        MyCommonAPIs.waitReady();
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
    
    public void shareEmail(String email) {
        logger.info("Click share button.");
        WebCheck.checkHrefIcon(URLParam.hrefDevicesBRSummary);
        share.click();
        MyCommonAPIs.sleepi(20);
        shareemail.click();
        shareemail.sendKeys(email);
        send.click();
        MyCommonAPIs.sleepi(2);
    }
    
    public boolean checkAlertIsExist() {
        logger.info("Check alert is exist.");
        boolean result = false;
        if (alerttext.exists()) {
            if (alerttext.getText().equals("Your configuration has been applied. It may take some time to reflect")) {
                logger.info("Alert is existed.");
                result = true;
            } else if (alerttext.getText().equals("Diagnostic logs shared")) {
                logger.info("Alert is existed.");
                result = true;
            } else if (alerttext.getText().equals("Diagnostics logs shared.")) {
                logger.info("Alert is existed.");
                result = true;
            }
        }
        return result;
    }
    
   

    
    public boolean clientsParameters() {
        WebCheck.checkHrefIcon(URLParam.hrefDevicesBRClients);
        MyCommonAPIs.sleepi(5);
        boolean result = false;     
        
        if(connectedclient.exists() && disconnectedclient.exists() && staticclient.exists() ) {
            System.out.println(getText(VLANiDparameter));
            if( getText(clientparameter).contains("Client" ) && getText(portparameter).contains("Port")  && getText(MACparameter).contains("MAC Address" ) && getText(IPparameter).contains("IP Address")
                    && getText(VLANiDparameter).contains("VLAN ID" )) { 
                result = true;
             }   
         }
       return result;
   }
    
    public boolean getWLANStatus() {
        return WANToglleenable.is(Condition.checked);
    }
    
    public void WAN() {
        WebCheck.checkHrefIcon(URLParam.hrefDevicesPRWanIp);
        
        MyCommonAPIs.sleepi(5);
        WANToglleenable.click();
        MyCommonAPIs.sleepi(5);
        WANSvae.click();
        MyCommonAPIs.sleepi(10);  
        Selenide.refresh();
        MyCommonAPIs.sleepi(20);  
        
        
   }
    public boolean checkWAN() {
       
        boolean result = false; 
        WebCheck.checkHrefIcon(URLParam.hrefDevicesPRWanIp);
        MyCommonAPIs.sleepi(5);
         if(PrimaryWLAN.isDisplayed()) {            
            result = true;
        }
       
       return result;
        
    }
    
    public void WANStatic(String type) {
        WebCheck.checkHrefIcon(URLParam.hrefDevicesPRWanIp);
        MyCommonAPIs.sleepi(5);
        conectionType.selectOption(type);       
        MyCommonAPIs.sleepi(5);
        WANSvae.click();
        MyCommonAPIs.sleepi(10);  
        Selenide.refresh();
        MyCommonAPIs.sleepi(20);
   }
    
    public void Qos() {
        WebCheck.checkHrefIcon(URLParam.hrefDevicesPRQos);
        MyCommonAPIs.sleepi(5);
        enableQos.click();  
        MyCommonAPIs.sleepi(5);
        Qosdownload.click();
        Qosdownload.sendKeys("30");
        MyCommonAPIs.sleepi(10);  
        Qosupload.click();
        Qosupload.sendKeys("40");       
        MyCommonAPIs.sleepi(20);
        QosSave.click();
   }
    
    public void addPolicyPR(String PRName) {
       MyCommonAPIs.sleepi(5);
       selectGateway.selectOption(PRName);
       MyCommonAPIs.sleepi(4);     
       MyCommonAPIs.sleepi(4);     
       setSelected($x("//*[@id=\"enableBlackList\"]"), true);
       MyCommonAPIs.sleepi(4);
       if(OkGotIt.isDisplayed()) {
       OkGotIt.click();
       }       
    }
    
    public boolean checkDropdown(String PRName) {
        boolean result = false;
       
        
        MyCommonAPIs.sleepi(5);
        new MDNSPage().MDNSGateway.click();
        MyCommonAPIs.sleepi(5);
        
        List<SelenideElement> dd = selectGateway.getSelectedOptions();
    

        System.out.println(dd.size());
        for (int j = 0; j < dd.size(); j++) {
            System.out.println(dd.get(j).getText());

        }
        
        
        if(dd.contains("No Selection") && dd.contains("AP") && dd.contains(PRName))
        {
            result = true;
        }
        return result;       
     }
    
    public void clickReboot() {
        WebCheck.checkHrefIcon(URLParam.hrefDevicesBRSummary);
        logger.info("Click reboot button.");
        Dropdown.click();
        MyCommonAPIs.sleepi(10);
        reboot.click();
        MyCommonAPIs.sleepi(15);
        waitElement(rebootconfirm);
        rebootconfirm.click();
        MyCommonAPIs.sleepi(10);
        getPageErrorMsg();
        if ($x("//div[text()='Waiting for additional details from the Device. Refresh to see the device details.']").exists()) {
            refresh();
            MyCommonAPIs.sleepsync();
            reboot.click();
            MyCommonAPIs.sleepi(3);
            rebootconfirm.click();
            MyCommonAPIs.sleepi(5);
        }
        // MyCommonAPIs.sleepsync();
    }
  
}

    
