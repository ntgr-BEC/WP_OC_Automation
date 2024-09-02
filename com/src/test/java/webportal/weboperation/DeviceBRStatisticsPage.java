package webportal.weboperation;

import java.util.logging.Logger;

import webportal.param.URLParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DeviceBRStatisticsElement;

public class DeviceBRStatisticsPage extends DeviceBRStatisticsElement {
    Logger logger = Logger.getLogger("DeviceBRStatisticsPage");

    public DeviceBRStatisticsPage() {
        logger.info("init...");
    }

    public void gotoPage() {
        WebCheck.checkUrl(URLParam.hrefBRstatistics);
    }

    public boolean isAllFieldHasData() {
        if (getText(txtTemp).length() < 1)
            return false;
        if (getText(txtCpu).length() < 1)
            return false;
        if (getText(txtWanTX).length() < 1)
            return false;
        if (getText(txtWanRX).length() < 1)
            return false;
        if (getText(txtLanTX).length() < 1)
            return false;
        if (getText(txtLanRX).length() < 1)
            return false;
        return true;
    }
}
