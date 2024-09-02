/**
 *
 */
package webportal.weboperation;

import java.util.List;
import java.util.logging.Logger;

import webportal.publicstep.WebCheck;
import webportal.webelements.WiredVLANForVLANElement;

/**
 * @author xuchen
 *
 */
public class DevicesSwitchVLANsInUsePage extends WiredVLANForVLANElement {
    Logger               logger;
    public String        vlanlistTableCol = "#tbdySwitchVlanInUse td:nth-of-type(1) span";
    public CommonAPIUnit commonApi        = new CommonAPIUnit();

    /**
     *
     */
    public DevicesSwitchVLANsInUsePage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon("#/devices/switch/vlansInUse");
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    public List<String> getVlans() {
        logger.info(String.format("getVlans..."));
        return commonApi.getTexts(vlanlistTableCol);
    }

}
