/**
 *
 */
package webportal.weboperation;

import java.util.logging.Logger;

import com.codeborne.selenide.Condition;

import util.Pause;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.NetworkEditNetworkPageElement;

/**
 * @author Netgear
 */
public class NetworkEditNetworkPage extends NetworkEditNetworkPageElement {
    /**
     *
     */
    Logger logger;

    public NetworkEditNetworkPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon(URLParam.hrefNetworkEditNetwork);
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
        try {
            password.waitWhile(Condition.empty, 30 * 1000);
        } catch (Throwable e) {
            logger.warning("password is empty.");
            takess();
        }
        new Pause().seconds(5); // bug, page refresh?
    }

    public NetworkEditNetworkPage(boolean noPage) {
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
        password.waitWhile(Condition.empty, 60 * 1000);
    }

    public String getDeviceAdminPassword() {
        String sRet = password.getValue();
        logger.info("is: " + sRet);
        return sRet;
    }

    public void modifyDeviceAdminPassword(String newPassword) {
        showPasswd();
        String sGet = getDeviceAdminPassword();
        if (sGet.equals(newPassword)) {
            logger.info("old & new passwd are same: " + newPassword);
        }
        password.clear();
        logger.info("set to: " + newPassword);
        password.sendKeys(newPassword);
        save.click();
    }

    public void modifyLocationName(String newPassword) {
        password.setValue(newPassword);
        save.click();
    }

    public String getErrorMessage() {
        return WebportalParam.getNLocText(alert.getText());
    }

    /**
     * @param toDefault
     *                  true to set utc+8, otherwise utc-4;
     */
    public void setTimeZone(boolean toDefault) {
        if (toDefault) {
            seCountry.selectOption("China");
            seTZ.selectOptionContainingText("08");
        } else {
            seCountry.selectOption("Bolivia");
            seTZ.selectOptionContainingText("04");
        }
        save.click();
        waitReady();
        // BUG: tz need to be reload, otherwise, time cannot be set on firmware policy
        refresh();
    }
}
