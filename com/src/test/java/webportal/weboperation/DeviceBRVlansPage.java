package webportal.weboperation;

import java.util.List;
import java.util.logging.Logger;

import webportal.param.URLParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DeviceBRVlanElement;

public class DeviceBRVlansPage extends DeviceBRVlanElement {
    Logger logger = Logger.getLogger("DeviceBRVlansPage");

    public DeviceBRVlansPage() {
        logger.info("init...");
    }

    public void gotoPage() {
        WebCheck.checkUrl(URLParam.hrefBRVlan);
    }

    public List<String> getVlans() {
        return getTextsTable(sVlanTable, 1);
    }

    public List<String> getVlanIds() {
        return getTextsTable(sVlanTable, 2);
    }
}
