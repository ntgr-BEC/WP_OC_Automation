package webportal.webelements;

import java.util.logging.Logger;

import util.MyCommonAPIs;

public class DeviceBRClientElement extends MyCommonAPIs {
    Logger logger = Logger.getLogger("DeviceBRClientElement");

    public String sExpand     = "#divPageHtmlConnNeigh h3";
    public String sClientName = sExpand + " span:first-child";
    public String sClientMac  = "#divPageHtmlConnNeigh .row div div:nth-child(1) p";
    public String sClientIp   = "#divPageHtmlConnNeigh .row div div:nth-child(2) p";
}
