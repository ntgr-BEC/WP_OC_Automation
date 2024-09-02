/**
 *
 */
package orbi.weboperation;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import java.util.logging.Logger;

import org.openqa.selenium.Keys;

import com.codeborne.selenide.Selenide;

import orbi.param.OrbiGlobalConfig;
import orbi.webelements.DNIOrbiAdvancedBackupSettingsPageElement;
import orbi.webelements.DNIOrbiAdvancedInternetSetupPageElement;
import orbi.webelements.DNIOrbiAdvancedMenuElement;
import orbi.webelements.DNIOrbiCommanElement;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;

/**
 * @author bingke.xue
 *
 */
public class OrbiAdvancedInternetSetupPage extends DNIOrbiAdvancedBackupSettingsPageElement {
    Logger logger;
    public String orbiDeviceMode = "Router";

    /**
     *
     */
    public OrbiAdvancedInternetSetupPage() {
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init OrbiAdvancedInternetSetupPage...");
    }
    
    public void setDeviceModeVariable (String deviceMode) {
        this.orbiDeviceMode = deviceMode;
    }
    
    public void goToOrbiAdvancedInternetSetupPage () {
        new OrbiLoginPage(this.orbiDeviceMode);
        if (new OrbiGlobalConfig().orbiSupplier == "DNI"){
            Selenide.switchTo().frame(new DNIOrbiCommanElement().topIframe);
            DNIOrbiAdvancedMenuElement orbiAdvancedMenu = new DNIOrbiAdvancedMenuElement();
            orbiAdvancedMenu.advancedLabel.click();
            Selenide.switchTo().parentFrame();
            MyCommonAPIs.sleepi(5);
            orbiAdvancedMenu.advSetupBtn.click();
            orbiAdvancedMenu.SubMenuInternetSetup.click();            
        }
        MyCommonAPIs.waitReady();
        logger.info("In OrbiAdvancedInternetSetupPage");
        
        
    }
    
    public String getWANIp() {
        logger.info("Get orbi base WAN ip");
        String WANIp = "";
        this.goToOrbiAdvancedInternetSetupPage();
        if (new OrbiGlobalConfig().orbiSupplier == "DNI"){
            Selenide.switchTo().frame(new DNIOrbiCommanElement().formIframe);
            String firstNum = DNIOrbiAdvancedInternetSetupPageElement.firstNumWANIp.getValue();
            String secondNum = DNIOrbiAdvancedInternetSetupPageElement.secondNumWANIp.getValue();
            String thirdNum = DNIOrbiAdvancedInternetSetupPageElement.thirdNumWANIp.getValue();
            String forthNum = DNIOrbiAdvancedInternetSetupPageElement.forthNumWANIp.getValue();
            WANIp = firstNum+"."+secondNum+"."+thirdNum+"."+forthNum;
            
        }
        return WANIp;
    }
    public String getWANIpFromRouter(){
        String WANIP_1 = "";
        try {
            Selenide.open("http://admin:password@192.168.2.1");
        
            Selenide.actions().sendKeys("admin");
            Selenide.actions().sendKeys(Keys.TAB);
            Selenide.actions().sendKeys("passowrd");
            Selenide.actions().sendKeys(Keys.ENTER);
        
            logger.info("open router R6400 URL Done");
            $("#basic-atd").click();
            logger.info("basic_atd click Done");
            Selenide.switchTo().frame("page");
            WANIP_1 = $x("//span[@name='rule_mac'][text()='"+WebportalParam.ob2MAC_Address.toUpperCase().replace('-', ':')+"' or text()='"+WebportalParam.ob2MAC_Address2.toUpperCase().replace('-', ':')+"']/../..//span[@name='rule_ip']").getText();
            System.out.print("WAN IP is this:"+WANIP_1+"\n");
        }catch(Throwable e) {
            System.out.println("failed to get wan ip");
        }
        return WANIP_1;
    }

}
