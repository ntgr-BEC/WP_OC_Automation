/**
 *
 */
package webportal.weboperation;

import java.util.Map;
import java.util.logging.Logger;

import webportal.param.URLParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DevicesOrbiSummaryPageElement;

/**
 * @author bingke.xue
 *
 */
public class DevicesOrbiSummaryPage extends DevicesOrbiSummaryPageElement {
    Logger logger;

    /**
     *
     */
    public DevicesOrbiSummaryPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon(URLParam.hrefOrbiSummary);
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    public DevicesOrbiSummaryPage(boolean nopage) {
        // TODO Auto-generated constructor stub
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    public boolean checkDeviceDetails(Map<String, String> DevicesInfo) {
        for (String ss : DevicesInfo.keySet()) {
            logger.info(ss + ": " + DevicesInfo.get(ss));
        }
        boolean result = true;

        if ((DevicesInfo.get("Connnected_status") == "Connected") && DevicesOrbiSummaryPageElement.connectStatus.getText().contains("Connected")
                && !DevicesOrbiSummaryPageElement.connectStatus.getText().contains("Disconnected")) {
            logger.info("this is connected status" + DevicesOrbiSummaryPageElement.connectStatus.getText());
            logger.info("check connected status success");
        } else {
            logger.info("failed check connected status");
            result = false;
        }
        if ((DevicesInfo.get("Device_Mode") == "Router") && DevicesOrbiSummaryPageElement.routerModeIcon.exists()) {
            logger.info("This is device mode class:" + DevicesOrbiSummaryPageElement.routerModeIcon.getAttribute("class"));
            logger.info("check device mode router success");
        } else if ((DevicesInfo.get("Device_Mode") == "AP") && DevicesOrbiSummaryPageElement.apModeIcon.exists()) {
            logger.info("This is device mode class:" + DevicesOrbiSummaryPageElement.apModeIcon.getAttribute("class"));
            logger.info("check device mode ap success");
        } else {
            logger.info("failed check device mode");
            result = false;
        }

        if (DevicesInfo.get("Name") != null) {
            logger.info("this is Name value" + DevicesOrbiSummaryPageElement.name.getText());
            if (DevicesInfo.get("Name").equals(DevicesOrbiSummaryPageElement.name.getText())) {
                logger.info("check Name success");
            } else {
                logger.info("failed check Name");
                result = false;
            }
        }
        if (DevicesInfo.get("Serial_Number") != null) {
            logger.info("this is SN:" + DevicesOrbiSummaryPageElement.serialNumber.getText());
            if (DevicesInfo.get("Serial_Number").equals(DevicesOrbiSummaryPageElement.serialNumber.getText())) {
                logger.info("check Serial Number success");
            } else {
                logger.info("failed check SN");
                result = false;
            }
        }
        if (DevicesInfo.get("Model") != null) {
            logger.info("This is model:" + DevicesOrbiSummaryPageElement.model.getText());
            if (DevicesInfo.get("Model").equals(DevicesOrbiSummaryPageElement.model.getText())) {
                logger.info("check Model success");

            } else {
                logger.info("failed check Model");
                result = false;
            }
        }
        if (DevicesInfo.get("Base_MAC_Address") != null) {
            logger.info("This is MAC_Address:" + DevicesOrbiSummaryPageElement.baseMACAddress.getText().toUpperCase().replace(":", "-"));
            if (DevicesInfo.get("Base_MAC_Address")
                    .equals(DevicesOrbiSummaryPageElement.baseMACAddress.getText().toUpperCase().replace(":", "-"))) {
                logger.info("check MAC_Address success");
            } else {
                logger.info("failed check MAC_Address");
                result = false;
            }
        }
        if (DevicesInfo.get("Uptime") != null) {
            logger.info("This is Uptime:" + DevicesOrbiSummaryPageElement.upTime.getText());
            if (DevicesOrbiSummaryPageElement.upTime.getText() != null) {
                logger.info("check Uptime not null success");

            } else {
                logger.info("failed check Uptime");
                result = false;
            }
        }
        if (DevicesInfo.get("Ip_Adress") != null) {
            logger.info("This is Ip_Address:" + DevicesOrbiSummaryPageElement.iPAddress.getText());
            if (DevicesInfo.get("Ip_Adress").equals(DevicesOrbiSummaryPageElement.iPAddress.getText())) {
                logger.info("check Ip_Address success");
            } else {
                logger.info("failed check Ip_Address");
                result = false;
            }
        }
        /*
         * if (DevicesInfo.get("Traffic") != null) {
         * if (DevicesInfo.get("Traffic").equals(traffic.getText())) {
         * } else {
         * logger.info("failed 7");
         * result = false;
         * }
         * }
         */
        if (DevicesInfo.get("Firmware_Version") != null) {
            logger.info("This is Firmware version:" + DevicesOrbiSummaryPageElement.firmware.getText());
            if (DevicesInfo.get("Firmware_Version").equals(DevicesOrbiSummaryPageElement.firmware.getText())) {
                logger.info("check firmware version success");
            } else {
                logger.info("failed check firmware version");
                result = false;
            }
        }
        logger.info("results is :" + result);
        return result;
    }

    public String getDeviceMode() {

        String deviceMode = "";
        if (DevicesOrbiSummaryPageElement.routerModeIcon.exists()) {
            deviceMode = "Router";
        } else if (DevicesOrbiSummaryPageElement.apModeIcon.exists()) {
            deviceMode = "AP";
        }
        logger.info("Current device mode is:" + deviceMode);
        return deviceMode;

    }
    
    public String getBaseMAC() {

        String BaseMAC = "";
        if (DevicesOrbiSummaryPageElement.baseMACAddress.exists()) {
            BaseMAC = DevicesOrbiSummaryPageElement.baseMACAddress.text();
        }
        logger.info("Current Base MAC Address is:" + BaseMAC);
        return BaseMAC;
    }
    
    public String getBaseIP() {

        String BaseIP = "";
        if (DevicesOrbiSummaryPageElement.iPAddress.exists()) {
            BaseIP = DevicesOrbiSummaryPageElement.iPAddress.text();
        }
        logger.info("Current Base MAC Address is:" + BaseIP);
        return BaseIP;
    }

    /**
     * @return true - orbi is in router mode, false to ap mode
     */
    public boolean isOrbiRouterMode() {
        if (getDeviceMode().equals("Router"))
            return true;
        else
            return false;
    }

}
