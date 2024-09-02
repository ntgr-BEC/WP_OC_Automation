/**
 *
 */
package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import webportal.param.URLParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DevicesSwitchPoEPageElement;

/**
 * @author Lavi
 *
 */
public class DevicesSwitchPoEPage extends DevicesSwitchPoEPageElement {
    final static Logger logger = Logger.getLogger("DevicesSwitchPoEPage");
    List<String>        lsRet  = new ArrayList<String>();

    /**
     *
     */
    public DevicesSwitchPoEPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon(URLParam.hrefDevicesSwitchPoE);
        logger.info("init...");
    }

    public List<String> getPorts() {
        if ($(sTable).exists())
            return getTextsTable(sTable, 1);
        else
            return lsRet;
    }

    public List<String> getPowerUsage() {
        if ($(sTable).exists())
            return getTextsTable(sTable, 2);
        else
            return lsRet;
    }

    public List<String> getIPAddr() {
        if ($(sTable).exists())
            return getTextsTable(sTable, 3);
        else
            return lsRet;
    }
}
