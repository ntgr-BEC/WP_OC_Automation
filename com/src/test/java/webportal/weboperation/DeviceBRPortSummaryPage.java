package webportal.weboperation;

import java.util.logging.Logger;

import webportal.param.URLParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DeviceBRPortSummaryElement;

public class DeviceBRPortSummaryPage extends DeviceBRPortSummaryElement {
    Logger logger = Logger.getLogger("DeviceBRPortSummaryPage");

    public DeviceBRPortSummaryPage() {
        logger.info("init...");
    }

    public void gotoPage() {
        WebCheck.checkUrl(URLParam.hrefBRPortConfigSummary);
    }

    public String getPVID() {
        return getText(PVID);
    }

    public String getVlanID() {
        return getText(vlanID);
    }
}
