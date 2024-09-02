/**
 *
 */
package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;

import java.util.logging.Logger;

import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DevicesOrbiDeviceModePageElement;

/**
 * @author lavi
 */
public class DevicesOrbiDeviceModePage extends DevicesOrbiDeviceModePageElement {
    Logger            logger;
    /**
     * 0 - ap, 1 - router
     */
    public static int deviceMode = 99;
    
    /**
     *
     */
    public DevicesOrbiDeviceModePage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon(URLParam.hrefOrbiDeviceMode);
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }
    
    public DevicesOrbiDeviceModePage(boolean nopage) {
        // TODO Auto-generated constructor stub
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }
    
    public void gotoPage() {
        WebCheck.checkHrefIcon(URLParam.hrefOrbiDeviceMode);
    }
    
    public void initDeviceMode(boolean toAP) {
        if (WebportalParam.enableDebug)
            return;
        logger.info("initDeviceMode " + (toAP ? "ap" : "router"));
        if ((deviceMode == 99) || (toAP && (deviceMode == 1)) || (!toAP && (deviceMode == 0))) {
            gotoPage();
            if (setDeviceMode(toAP)) {
                sleep(300, "wait ob reonline");
            }
            if (toAP) {
                deviceMode = 0;
            } else {
                deviceMode = 1;
            }
        } else
            return;
        
    }
    
    /**
     * @param  toAP
     *              true to ap, false to router
     * @return      true if touched
     */
    public boolean setDeviceMode(boolean toAP) {
        boolean touched = false;
        String suffix = "+i+p";
        if (toAP && isRouterMode()) {
            deviceMode = 1;
            $(cbAPMode + suffix).click();
            touched = true;
        } else if (!toAP && !isRouterMode()) {
            $(cbRouterMode + suffix).click();
            deviceMode = 0;
            touched = true;
        } else
            return touched;
        
        waitReady();
        if ($(sPopButtonCss).exists()) {
            clickBoxLastButton();
        }
        sleepsync();
        return touched;
    }
    
    public boolean isRouterMode() {
        return $(cbRouterMode).isSelected();
    }
}
