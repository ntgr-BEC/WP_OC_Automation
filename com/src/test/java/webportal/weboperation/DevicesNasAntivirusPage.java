/**
 * 
 */
package webportal.weboperation;

import java.util.logging.Logger;

import webportal.publicstep.WebCheck;
import webportal.webelements.DevicesNasAntivirusElement;

/**
 * @author zheli
 *
 */
public class DevicesNasAntivirusPage extends DevicesNasAntivirusElement {
    Logger logger;

    /**
     * 
     */
    public DevicesNasAntivirusPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon("#/devices/nas/antivirus");
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
    }

    public String getConfirmFw() {
        return curfirmware.getText();
    }

    public String isConfirmFw() {
        return upfirmware.getText();
    }

    public String getAntivirusStatus() {
        return antivirusStatus.getText();
    }
}
