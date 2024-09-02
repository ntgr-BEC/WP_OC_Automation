/**
 *
 */
package webportal.weboperation;

import java.util.logging.Logger;

import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DevicesOrbiFirmwarePageElement;

/**
 * @author lavi
 *
 */
public class DevicesOrbiFirmwarePage extends DevicesOrbiFirmwarePageElement {
    Logger logger;

    /**
     *
     */
    public DevicesOrbiFirmwarePage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon(URLParam.hrefOrbiFirmware);
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    public DevicesOrbiFirmwarePage(boolean nopage) {
        // TODO Auto-generated constructor stub
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    public String getVersion() {
        return getTextTable(sOutdoorTable, WebportalParam.ob2deveiceName, 3);
    }

    public boolean isUpgradable() {
        return btnUpgrade.exists();
    }

    public void doUpgrade() {
        if (btnUpgrade.exists()) {
            btnUpgrade.click();
            timerStart(25 * 60);
            while (timerRunning()) {
                waitReady();
                sleep(30, "wait for auto-refresh");
                if (!isUpgradable()) {
                    break;
                }
            }
        }
    }
}
