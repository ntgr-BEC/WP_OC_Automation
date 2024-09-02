/**
 *
 */
package webportal.weboperation;

import java.util.logging.Logger;

import webportal.param.URLParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DevicesSwitchConnectedNeighborsPageElement;

/**
 * @author zheli
 *
 */
public class DevicesSwitchConnectedNeighborsPage extends DevicesSwitchConnectedNeighborsPageElement {
    final static Logger logger = Logger.getLogger("DevicesSwitchConnectedNeighborsPage");

    /**
     *
     */
    public DevicesSwitchConnectedNeighborsPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon(URLParam.hrefDevicesSwitchConnectedNeighbors);
        logger.info("init...");
    }

    public void checkNeighbor() {

    }

}
