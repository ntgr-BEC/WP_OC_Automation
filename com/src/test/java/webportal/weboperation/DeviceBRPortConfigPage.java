package webportal.weboperation;

import java.util.logging.Logger;

import webportal.param.URLParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DeviceBRPortConfigElement;

public class DeviceBRPortConfigPage extends DeviceBRPortConfigElement {
    Logger logger = Logger.getLogger("DeviceBRPortConfigPage");

    public DeviceBRPortConfigPage() {
        logger.info("init...");
    }

    public void gotoPage() {
        WebCheck.checkUrl(URLParam.hrefBRPortConfigSettings);
    }

    public void setPVID(String vlanName) {
        lbPVID.selectOptionContainingText(vlanName);
        clickButton(0);
    }
}
