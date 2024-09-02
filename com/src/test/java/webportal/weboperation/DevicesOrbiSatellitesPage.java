/**
 *
 */
package webportal.weboperation;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import webportal.param.URLParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DevicesOrbiSatellitesPageElement;

/**
 * @author lavi
 *
 */
public class DevicesOrbiSatellitesPage extends DevicesOrbiSatellitesPageElement {
    Logger logger;

    /**
     *
     */
    public DevicesOrbiSatellitesPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon(URLParam.hrefOrbiSatellites);
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    public DevicesOrbiSatellitesPage(boolean nopage) {
        // TODO Auto-generated constructor stub
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    public List<String> getAllIndoorName() {
        List<String> lsName = new ArrayList<String>();
        if (isTableHasData(true))
            return getTextsTable(sIndoorTable, 1);
        return lsName;
    }

    /**
     * change first device to toName
     *
     * @param toName
     */
    public void changeIndoorName(String toName) {
        editLine(sIndoorTable, 1, 1);
        setName(toName);
        waitReady();
    }
    
    public String getFirstSatelliteIP() {
        return getTextsTable(sIndoorTable, 2).get(0);
    }
    
    public String getFirstSatelliteStatus() {
        return getTextsTable(sIndoorTable, 5).get(0);
    }
    
    public void deleteFirstSatellite() {
        firstsatellite.hover();
        deleteicon.click();
        deletebtn.click();
    }
}
