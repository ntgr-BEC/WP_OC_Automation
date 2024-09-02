/**
 * 
 */
package webportal.weboperation;

import java.util.logging.Logger;

import webportal.publicstep.WebCheck;
import webportal.webelements.DevicesNasFirmwarePageElement;

/**
 * @author zheli
 *
 */
public class DevicesNasFirmwarePage extends DevicesNasFirmwarePageElement {
    Logger logger;

    /**
     * 
     */
    public DevicesNasFirmwarePage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon("#/devices/nas/firmware");
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
    }

    public String getConfirmFw() {
        return curfirmware.getText();
    }

    public String isConfirmFw() {
        return upfirmware.getText();
    }
}
