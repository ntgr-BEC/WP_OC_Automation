/**
 *
 */
package orbi.weboperation;

import java.util.logging.Logger;

import com.codeborne.selenide.Selenide;

import orbi.param.OrbiGlobalConfig;
import orbi.webelements.DNIOrbiAdvancedMenuElement;
import orbi.webelements.DNIOrbiAdvancedRouterApModePageElement;
import orbi.webelements.DNIOrbiCommanElement;
import util.MyCommonAPIs;


/**
 * @author bingke.xue
 *
 */
public class OrbiAdvancedRouterAPModePage extends DNIOrbiAdvancedRouterApModePageElement {
    Logger logger;
    public String orbiDeviceMode = "Router";

    /**
     *
     */
    public OrbiAdvancedRouterAPModePage() {
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init OrbiAdvancedRouterAPModePage...");
    }
    
    public void setDeviceModeVariable (String deviceMode) {
        this.orbiDeviceMode = deviceMode;
    }
    
    public void goToOrbiAdvancedRouterAPModePage () {
        logger.info("orbi device mode is"+this.orbiDeviceMode);
        new OrbiLoginPage(this.orbiDeviceMode);
        if (new OrbiGlobalConfig().orbiSupplier == "DNI"){
            Selenide.switchTo().frame(new DNIOrbiCommanElement().topIframe);
            DNIOrbiAdvancedMenuElement orbiAdvancedMenu = new DNIOrbiAdvancedMenuElement();
            orbiAdvancedMenu.advancedLabel.click();
//            Selenide.switchTo().parentFrame();
            MyCommonAPIs.sleepi(3);
            orbiAdvancedMenu.advSetupLabel.click();
            MyCommonAPIs.sleepi(3);
            orbiAdvancedMenu.SubMenuRouter_AP_Mode.click();            
        }
        MyCommonAPIs.waitReady();
        logger.info("In OrbiAdvancedRouterAPModePage");
        
        
    }
    
    public void setDeviceMode(String mode) {
        logger.info("Set orbi base device mode");
        
        this.goToOrbiAdvancedRouterAPModePage();
        if (new OrbiGlobalConfig().orbiSupplier == "DNI"){
            Selenide.switchTo().frame(new DNIOrbiCommanElement().formIframe);
            if (mode.equals("Router")) {
                logger.info("set router mode");
                DNIOrbiAdvancedRouterApModePageElement.radioNameRouterAPMode.selectRadio("0");
                DNIOrbiCommanElement.applyBtn.click();
            } else if (mode.equals("AP")) {
                logger.info("set AP mode");
                DNIOrbiAdvancedRouterApModePageElement.radioNameRouterAPMode.selectRadio("1");
                DNIOrbiCommanElement.applyBtn.click();
                DNIOrbiCommanElement.alertOKBtn.click();
                
            }
            MyCommonAPIs.sleepi(120);
            
            
        }
        
    }

}
