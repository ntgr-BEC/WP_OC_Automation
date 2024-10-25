/**
 *
 */
package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.executeJavaScript;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.MDNSElement;

/**
 * @author zheli
 *
 */
public class MDNSPage extends MDNSElement {
    final static Logger logger = Logger.getLogger("DevicesAPIpSettingsPage");
    
    
    
    public MDNSPage() {
        if (getCurrentUrl().contains(URLParam.hreforganization)) {
            new OrganizationPage().openOrg(WebportalParam.Organizations);
            gotoLoction(WebportalParam.location1);
        }
        editNetwork.click();
    }
    
    public MDNSPage(boolean noPage) {
        logger.info("no edit page");
    }

    public boolean MDNScheck() {     
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        MDNSGateway.click();
        MyCommonAPIs.sleepi(5);
        selectGateway.selectOption("AP");
        MyCommonAPIs.sleepi(4);     
        boolean sta = ($x("//*[@id=\"enableBlackList\"]").isSelected());
        System.out.println(sta);
        if(sta==false) {
            result = true;
        }
        return result;    
    }
    
    public boolean MDNPolicyScheck() {     
        boolean result = false;
        MyCommonAPIs.sleepi(4);
        MDNSGateway.click();
        MyCommonAPIs.sleepi(5);
        selectGateway.selectOption("AP");
        MyCommonAPIs.sleepi(4);      
        setSelected($x("//*[@id=\"enableBlackList\"]"), true);
        MyCommonAPIs.sleepi(4);
        if(OkGotIt.isDisplayed()) {
            OkGotIt.click();
        }
        MyCommonAPIs.sleepi(4);
        AddPolicy.click();
        MyCommonAPIs.sleepi(10);
        if(policyName.isDisplayed()) {
            result = true;
        }
        closepopup.click();
        return result;    
    }
  
    
    public void disableMDNS() {          
        MyCommonAPIs.sleepi(5);
        setSelected($x("//*[@id=\"enableBlackList\"]"), false);
    }
    
    public boolean DiscoveredServicesCheck() {     
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        MDNSGateway.click();
        MyCommonAPIs.sleepi(5);
        selectGateway.selectOption("AP");
        MyCommonAPIs.sleepi(4);     
        setSelected($x("//*[@id=\"enableBlackList\"]"), true);
        MyCommonAPIs.sleepi(4);
        if(OkGotIt.isDisplayed()) {
            OkGotIt.click();
        }
        MyCommonAPIs.sleepi(4);
        clickDiscoveredServices.click();
        MyCommonAPIs.sleepi(4);
        if(Refresh.isDisplayed()) {
            result = true;
        }
//        if(okbutton.exists()) {
//            okbutton.click();
//        }
        return result;    
    }
    public boolean WarrningWithoutSSID() {     
        boolean result = false;
        String warningMessage = "";
        MyCommonAPIs.sleepi(5);
        MDNSGateway.click();
        MyCommonAPIs.sleepi(5);
        selectGateway.selectOption("AP");
        MyCommonAPIs.sleepi(8);     
        $x("(//button[text()='OK. Got it'])[2]").click();
        MyCommonAPIs.sleepi(4);
        MyCommonAPIs.sleepi(4);     
        setSelected($x("//*[@id=\"enableBlackList\"]"), true);
        
        if (Warrning.exists()) {
            warningMessage = Warrning.getText();
        } else {
            warningMessage = Warrning1.getText();
        }
        
        System.out.println(warningMessage);    
        if(warningMessage.contains("it requires the Location to have at least 1 SSID meeting")) {
            result = true;
        }
        
        if (okbutton.exists()) {
            okbutton.click();
        } else {
            okbutton1.click();
        }
      
        return result;    
    }
    
    public void addSsid() {
        WebCheck.checkHrefIcon(URLParam.hrefWireless);
        waitElement(settingsorquickview);
        
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("SSID", "MDNS");
        locationInfo.put("Security", "WPA2 Personal Mixed");
        locationInfo.put("Password", "123456798");
        
        System.out.println("addning ssid");
        if(settingsorquickview.exists()) {
            settingsorquickview.click();
            }
            waitReady();
            waitElement(addssid);          
            new WirelessQuickViewPage().addSsidStepMDNS(locationInfo);
    }
    
    public boolean checkaddSsid() {
        boolean result = false;
        
        WebCheck.checkHrefIcon(URLParam.hrefWireless);
        waitElement(settingsorquickview);
        if(settingsorquickview.exists()) {
            settingsorquickview.click();
            }
        System.out.println("we are in wifi page");
        MyCommonAPIs.sleepi(10);
        
        if (ssidlistone.exists()) 
        {
            System.out.println("ssid is alredy added ");
            result = true;
        }
        if(!ssidlistone.exists())
        {
            System.out.println("ssid is not  added ");
            addSsid();
        }  
        System.out.println("ssid validation completed ");
        return result;
    }
    
    
    public void addPolicy() {
        MyCommonAPIs.sleepi(5);
        MDNSGateway.click();
        MyCommonAPIs.sleepi(5);
        selectGateway.selectOption("AP");
        MyCommonAPIs.sleepi(4);     
        setSelected($x("//*[@id=\"enableBlackList\"]"), true);
        MyCommonAPIs.sleepi(4);
        if(OkGotIt.isDisplayed()) {
        OkGotIt.click();
        }
        if (!checkMDNSIsExist("MDNS")) {
        MyCommonAPIs.sleepi(4);
        AddPolicy.click();
        MyCommonAPIs.sleepi(10);
        policyName.sendKeys("MDNS");
        MyCommonAPIs.sleepi(1);
        sharedService.selectOption("SSH_Remote_Terminal");
        MyCommonAPIs.sleepi(1);
        IPAddress.sendKeys(WebportalParam.ap1IPaddress);
        MyCommonAPIs.sleepi(1);
        ServiceVLAN.click();
        MyCommonAPIs.sleepi(1);
        VLAN1.click();
        MyCommonAPIs.sleepi(1);
        AllWirelessNetwork.click();
        MyCommonAPIs.sleepi(1);
        Add.click();
        MyCommonAPIs.sleepi(10);
        okpolicy.click();
        }else
        {
            System.out.println("alredy exits");
        }
    }
    
    
    public boolean checkMDNSIsExist(String MDNS) {
        System.out.println(MDNS);
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        waitReady();
        // ($("#wirelessTable"));
        String sElement = String.format("//td[text()='%s']", MDNS);
        logger.info("on element:" + sElement);
        if ($x(sElement).exists()) {
            result = true;
            logger.info("MDNS:" + MDNS + " is existed.");
        }
        return result;
    }
    
    public boolean addDupPolicy() {
        boolean result = true;
        MyCommonAPIs.sleepi(5);
        AddPolicy.click();
        MyCommonAPIs.sleepi(10);
        policyName.sendKeys("MDNS");
        MyCommonAPIs.sleepi(1);
        sharedService.selectOption("SSH_Remote_Terminal");
        MyCommonAPIs.sleepi(1);
        IPAddress.sendKeys(WebportalParam.ap1IPaddress);
        MyCommonAPIs.sleepi(1);
        ServiceVLAN.click();
        MyCommonAPIs.sleepi(1);
        VLAN1.click();
        MyCommonAPIs.sleepi(1);
        AllWirelessNetwork.click();
        MyCommonAPIs.sleepi(1);
        Add.click();
        MyCommonAPIs.sleepi(5);
        closepopup.click();
        if(Policyexitserror.isDisplayed()) {
            result = true;
        }
       return result;
    }
    
    public void deletePolicy(String MDNSName) {
        MyCommonAPIs.sleepi(5);
        MDNSGateway.click();
        MyCommonAPIs.sleepi(5);     
        if(Policyexits.isDisplayed()) {
            System.out.println("Policy exits");
            
//            executeJavaScript("arguments[0].removeAttribute('class')", editMdNS(MDNSName));
//            MyCommonAPIs.sleep(3000);
//            deleteMDNS(MDNSName).waitUntil(Condition.visible, 60 * 1000).click();

            
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions a = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor)driver;
            js.executeScript("window.scrollBy(0, 250)", "");
            a.moveToElement(Policyexits).perform();
            a.moveToElement(deleteMDNS(MDNSName)).click().perform();
            
            MyCommonAPIs.sleep(6000);
            deletessidyes.click();
            MyCommonAPIs.sleep(5 * 1000);
        }
        
    }
    
    
    public void MultiaddPolicy(Map<String, String> map) {       
        MyCommonAPIs.sleepi(4);
        AddPolicy.click();
        MyCommonAPIs.sleepi(10);
        policyName.sendKeys(map.get("MDNSName"));
        MyCommonAPIs.sleepi(1);
        sharedService.selectOption("SSH_Remote_Terminal");
        MyCommonAPIs.sleepi(1);
        IPAddress.sendKeys(WebportalParam.ap1IPaddress);
        MyCommonAPIs.sleepi(1);
        ServiceVLAN.click();
        MyCommonAPIs.sleepi(1);
        VLAN1.click();
        MyCommonAPIs.sleepi(1);
        AllWirelessNetwork.click();
        MyCommonAPIs.sleepi(1);
        Add.click();
        MyCommonAPIs.sleepi(10);
        okpolicy.click();
    }
    
    public void enableMDNS() {
        MyCommonAPIs.sleepi(5);
        MDNSGateway.click();
        MyCommonAPIs.sleepi(5);
        selectGateway.selectOption("AP");
        MyCommonAPIs.sleepi(5);     
        setSelected($x("//*[@id=\"enableBlackList\"]"), true);
        MyCommonAPIs.sleepi(5);
        waitElement(OkGotIt);
        if(OkGotIt.exists()) {
        OkGotIt.click();
        }
        MyCommonAPIs.sleepi(4);
    
  }

    public void editMDNSpolicy(Map<String, String> map) {
        MyCommonAPIs.sleepi(5);
        MDNSGateway.click();
        MyCommonAPIs.sleepi(5);
        selectGateway.selectOption("AP");
        MyCommonAPIs.sleepi(4);     
        if(Policyexits.isDisplayed()) {
            System.out.println("Policy exits");
//            executeJavaScript("arguments[0].removeAttribute('class')", editMdNS(map.get("MDNSName")));
//            MyCommonAPIs.sleepi(5);
//            editMDNS(map.get("MDNSName")).waitUntil(Condition.visible, 60 * 1000).click();
//            MyCommonAPIs.sleep(6000);
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions a = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor)driver;
            a.moveToElement(Policyexits).perform();
            a.moveToElement(editMDNS(map.get("MDNSName"))).click().perform();
        }
        policyName.clear();
        policyName.sendKeys(map.get("NewMDNSName"));
        MyCommonAPIs.sleepi(1);
        sharedService.selectOption(map.get("sharedService"));
        MyCommonAPIs.sleepi(1);
        IPAddress.clear();
        IPAddress.sendKeys(WebportalParam.ap1IPaddress);
        MyCommonAPIs.sleepi(1);
        MyCommonAPIs.sleepi(1);
        Add.click();
        MyCommonAPIs.sleepi(10);
        if(okpolicy.exists());{
        okpolicy.click();
        }
        
    }

}


