/**
 *
 */
package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$x;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

import util.MyCommonAPIs;
import webportal.publicstep.WebCheck;
import webportal.webelements.DevicesSwitchSummaryPageElement;

/**
 * @author zheli
 */
public class DevicesSwitchSummaryPage extends DevicesSwitchSummaryPageElement {
    Logger logger;

    /**
     *
     */
    public DevicesSwitchSummaryPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon("#/devices/switch/summary");
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    public DevicesSwitchSummaryPage(boolean nopage) {
        // TODO Auto-generated constructor stub
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    /**
     * @param devtype
     *                0 - switch, 1 - br, 2 - nas, 3 - ob, 4 - ap
     */
    public DevicesSwitchSummaryPage(int devtype) {
        // TODO Auto-generated constructor stub
        if (devtype == 1) {
            WebCheck.checkHrefIcon("#/devices/br/summary");
        } else if (devtype == 2) {
            WebCheck.checkHrefIcon("#/devices/nas/summary");
        } else if (devtype == 3) {
            WebCheck.checkHrefIcon("#/devices/orbi/summary");
        } else if (devtype == 4) {
            WebCheck.checkHrefIcon("#/devices/accessPoint/summary");
        } else {
            WebCheck.checkHrefIcon("#/devices/switch/summary");
        }
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
    }

    public DevicesSwitchConnectedNeighboursPortConfiqSummaryPage enterPortConfigSummary(String port) {
        portChoice(port).click();
        return new DevicesSwitchConnectedNeighboursPortConfiqSummaryPage();
    }
    

    public Date getTime() {
        Date date = null;
        String time = dateAndTime.getText();
        // Nov 26 2018 10:34:17 UTC+8
        // Dec 24 2018 Mon 22:45:50 UTC+8
        logger.info(time);
        time = time.substring(0, 24);
        SimpleDateFormat myFmt = new SimpleDateFormat("MMM dd yyyy EEE HH:mm:ss", Locale.ENGLISH);
        // SimpleDateFormat myFmt = new SimpleDateFormat("MMM dd yyyy HH:mm:ss", Locale.ENGLISH);
        try {
            date = myFmt.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        logger.info(date.toString());
        return date;
    }

    public boolean checkDeviceDetails(Map<String, String> DevicesInfo) {
        for (String ss : DevicesInfo.keySet()) {
            logger.info(ss + ": " + DevicesInfo.get(ss));
        }
        boolean result = true;
        if (DevicesInfo.get("Name") != null) {
            if (DevicesInfo.get("Name").equals(name.getText())) {
            } else {
                logger.info("failed 1");
                result = false;
            }
        }
        if (DevicesInfo.get("Serial_Number") != null) {
            if (DevicesInfo.get("Serial_Number").equals(serialNumber.getText())) {
            } else {
                logger.info("failed 2");
                result = false;
            }
        }
        if (DevicesInfo.get("Model") != null) {
            if (DevicesInfo.get("Model").equals(model.getText())) {
            } else {
                logger.info("failed 3");
                result = false;
            }
        }
        if (DevicesInfo.get("Base_MAC_Address") != null) {
            if (DevicesInfo.get("Base_MAC_Address").equalsIgnoreCase(baseMACAddress.getText())) {
            } else {
                logger.info("failed 4");
                result = false;
            }
        }
        if (DevicesInfo.get("Uptime") != null) {
            if (DevicesInfo.get("Uptime") != null) {
            } else {
                logger.info("failed 5");
                result = false;
            }
        }
        if (DevicesInfo.get("VLANs_in_use") != null) {
            if (DevicesInfo.get("VLANs_in_use").equals(vlanInUse.getText())) {
            } else {
                logger.info("failed 6");
                result = false;
            }
        }
        if (DevicesInfo.get("Traffic") != null) {
            if (DevicesInfo.get("Traffic").equals(traffic.getText())) {
            } else {
                logger.info("failed 7");
                result = false;
            }
        }
        if (DevicesInfo.get("IP_Address") != null) {
            if (DevicesInfo.get("IP_Address").equals(iPAddress.getText())) {
            } else {
                logger.info("failed 8");
                result = false;
            }
        }
        if (DevicesInfo.get("Firmware_Version") != null) {
            if (DevicesInfo.get("Firmware_Version") != null) {
            } else {
                logger.info("failed 9");
                result = false;
            }
        }
        if (DevicesInfo.get("Bootcode_Version") != null) {
            if (DevicesInfo.get("Bootcode_Version") != null) {
            } else {
                logger.info("failed 10");
                result = false;
            }
        }
        if (DevicesInfo.get("Date_&_Time") != null) {
            if (DevicesInfo.get("Date_&_Time") != null) {
            } else {
                logger.info("failed 11");
                result = false;
            }
        }
        return result;
    }

    public void reloadDevice() {
        logger.info("start");
        DoDeviceActive(2);
        waitReady();
        clickBoxLastButton();
        waitReady();
        sleepi(30);
    }

    /**
     * @param index
     *              0-"delete", "reboot", "reset", "share"
     */
    public void DoDeviceActive(int index) {
        btnActions(index).click();
    }

    public void clickReboot() {
        logger.info("Click reboot button.");
        reboot.click();
        MyCommonAPIs.sleepi(5);
        rebootconfirm.click();
        MyCommonAPIs.sleepi(5);
        getPageErrorMsg();
        if ($x("//div[text()='Waiting for additional details from the Device. Refresh to see the device details.']").exists()) {
            refresh();
            MyCommonAPIs.sleepsync();
            reboot.click();
            MyCommonAPIs.sleepi(3);
            rebootconfirm.click();
            MyCommonAPIs.sleepi(5);
        }
        // MyCommonAPIs.sleepsync();
    }
}
