/**
 *
 */
package orbi.weboperation;

import java.util.logging.Logger;

import com.codeborne.selenide.Selenide;

import orbi.param.OrbiGlobalConfig;
import orbi.webelements.DNIOrbiAdvancedBackupSettingsPageElement;
import orbi.webelements.DNIOrbiAdvancedMenuElement;
import orbi.webelements.DNIOrbiCommanElement;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;

/**
 * @author bingke.xue
 *
 */
public class OrbiAdvancedBackupSettingsPage extends DNIOrbiAdvancedBackupSettingsPageElement {
    Logger logger;
    public String orbiDeviceMode = "Router";

    /**
     *
     */
    public OrbiAdvancedBackupSettingsPage() {
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init OrbiAdvancedBackupSettingsPage...");
    }
    
    public void setDeviceModeVariable (String deviceMode) {
        this.orbiDeviceMode = deviceMode;
    }
    
    public void goToOrbiAdvancedBackupSettingsPage () {
        new OrbiLoginPage(this.orbiDeviceMode);
        if (WebportalParam.arch.contains("old")) {
            MyCommonAPIs.sleepi(3);
            if (new OrbiGlobalConfig().orbiSupplier == "DNI") {
                Selenide.switchTo().frame(new DNIOrbiCommanElement().topIframe);
                DNIOrbiAdvancedMenuElement orbiAdvancedMenu = new DNIOrbiAdvancedMenuElement();
                orbiAdvancedMenu.advancedLabel.click();
                MyCommonAPIs.waitReady();
                Selenide.switchTo().parentFrame(); // add by ann
                orbiAdvancedMenu.advAdministratorBtn.click();
                orbiAdvancedMenu.SubMenuBackupsettings.click();
            }
            MyCommonAPIs.waitReady();
        }else {
            MyCommonAPIs.sleepi(3);
            DNIOrbiAdvancedMenuElement orbiAdvancedMenu = new DNIOrbiAdvancedMenuElement();
            orbiAdvancedMenu.advancedLabel.click();
            MyCommonAPIs.sleepi(5);
            orbiAdvancedMenu.advAdministratorBtn.click();
            MyCommonAPIs.sleepi(3);
            orbiAdvancedMenu.SubMenuBackupsettings.click();
            MyCommonAPIs.sleepi(5);
        }
        logger.info("In OrbiAdvancedBackupSettingsPage");
        
        
    }
    
    public void factoryDefaultDevice() {
        logger.info("Begin to erase device");
        if (WebportalParam.arch.contains("old")) {
            if (new OrbiGlobalConfig().orbiSupplier == "DNI") {
                Selenide.switchTo().frame(new DNIOrbiCommanElement().formIframe);
                new DNIOrbiAdvancedBackupSettingsPageElement().eraseBtn.click();
                new DNIOrbiCommanElement().saveBtn.click();
            } 
        }else {
            new DNIOrbiAdvancedBackupSettingsPageElement().eraseBtn.click();
            MyCommonAPIs.sleepi(1);
            new DNIOrbiCommanElement().saveBtn.click();
        }
        logger.info("wait device to be online again");
        MyCommonAPIs.sleepi(120);       
    }


}
