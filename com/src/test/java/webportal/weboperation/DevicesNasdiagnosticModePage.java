/**
 * 
 */
package webportal.weboperation;

import java.util.logging.Logger;

import com.codeborne.selenide.Selenide;

import util.MyCommonAPIs;
import webportal.publicstep.WebCheck;
import webportal.webelements.DevicesNasdiagnosticModeElement;

/**
 * @author zheli
 *
 */
public class DevicesNasdiagnosticModePage extends DevicesNasdiagnosticModeElement {
    Logger logger;

    /**
     * 
     */
    public DevicesNasdiagnosticModePage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon("#/devices/nas/diagnosticMode");
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
    }

    public String confirmAttr() {
        securediganosticsmode.click();
        Selenide.sleep(3 * 1000);
        okbutton.click();
        return portNumber.attr("style");
    }

    public void defaultSetting() {
        securediganosticsmode.click();
    }

    public void share() {
        shareButton.click();
        emailAddressInput.setValue(emailaddress);
        sendButton.click();
        MyCommonAPIs.waitReady();
    }
}
