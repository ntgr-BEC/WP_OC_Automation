package webportal.weboperation;

import java.util.logging.Logger;

import webportal.param.URLParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DevicesApPortConfiqSettingPageElement;

public class DevicesApPortConfiqSettingPage extends DevicesApPortConfiqSettingPageElement {
    final static Logger logger = Logger.getLogger("DevicesApPortConfiqSettingPage");
    
    /**
     *
     */
    public DevicesApPortConfiqSettingPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon(URLParam.hrefDevicesApPortConfiqSettings);
        logger.info("init...");
    }
    
    public void SetPortPvid(String vlanId) {
        lbPVID.selectOptionContainingText(vlanId);
        clickButton(0);
    }
}
