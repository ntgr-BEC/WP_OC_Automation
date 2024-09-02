package webportal.weboperation;

import java.util.logging.Logger;

import webportal.param.URLParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DevicesApPortConfiqSummaryPageElement;

public class DevicesApPortConfiqSummaryPage extends DevicesApPortConfiqSummaryPageElement {
    final static Logger logger = Logger.getLogger("DevicesApPortConfiqSummaryPage");
    
    /**
     *
     */
    public DevicesApPortConfiqSummaryPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon(URLParam.hrefDevicesApPortConfiqSummary);
        logger.info("init...");
    }
    
    public String getPVID() {
        return getText(txtPVID);
    }
}
