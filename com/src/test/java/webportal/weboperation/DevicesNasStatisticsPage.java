/**
 * 
 */
package webportal.weboperation;

import java.util.logging.Logger;

import webportal.publicstep.WebCheck;
import webportal.webelements.DevicesNasStaticsPageElement;

/**
 * @author zheli
 *
 */
public class DevicesNasStatisticsPage extends DevicesNasStaticsPageElement {
    Logger logger;

    /**
     * 
     */
    public DevicesNasStatisticsPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon("#/devices/nas/statistics");
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
    }

    public String getdiskTemp() {
        return diskTempature.getText();
    }

    public String getcpuTemp() {
        return cpuTempature.getText();
    }

    public String getfanSpeed() {
        return fanSpeed.getText();
    }
}
