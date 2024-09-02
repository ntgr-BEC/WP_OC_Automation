package orbi.restapioperation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Logger;

import com.alibaba.fastjson.JSONReader;

import orbi.param.OrbiGlobalConfig;
import util.MyCommonAPIs;
import util.RunCommand;
import webportal.param.WebportalParam;

public class OrbiRestAPIOperation extends MyCommonAPIs {
    final static Logger logger     = Logger.getLogger("OrbiRestAPIOperation");
    JSONReader          jsonReader = null;
    String              sPath      = null;
    String              sContent   = "";
    RunCommand          rc         = new RunCommand();

    public static String api_device_info             = "device_info";
    public static String api_device_name             = "device_name";
    public static String api_device_ip_settings      = "device_ip_settings";
    public static String api_lan_ip_settings         = "lan_ip_settings";
    public static String api_device_time             = "device_time";
    public static String api_led_control             = "led_control";
    public static String api_wan_port_stats          = "wan_port_stats";
    public static String api_lan_port_stats          = "lan_port_stats";
    public static String api_lan_subnet_stats        = "lan_subnet_stats";
    public static String api_vpn_data_stats          = "vpn_data_stats";
    public static String api_fw_update               = "fw_update";
    public static String api_device_fw_update        = "device_fw_update";
    public static String api_fw_update_status        = "fw_update_status";
    public static String api_device_fw_update_status = "device_fw_update_status";
    public static String api_ipsec_policy            = "ipsec_policy";

    // for orbi
    public static String api_change_upgrade_url       = "change_upgrade_url";
    public static String api_check_download_fw        = "check_download_fw";
    public static String api_check_download_fw_status = "check_download_fw_status";
    public static String api_fw_upgrade_all           = "fw_upgrade_all";

    public OrbiRestAPIOperation() {
        sPath = rc.execcURLOutput;
        File f = new File(sPath);
        if (f.exists()) {
            sContent = RunCommand.read(sPath);
            try {
                jsonReader = new JSONReader(new FileReader(f));
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * @param api
     * @param devType
     *            0-br, 1-ob, 2-sw
     */
    public OrbiRestAPIOperation(String api, int devType) {
        sPath = rc.execcURLOutput;
        devType = 1;
        getRest(api, null, devType);
    }

    /**
     * @param api
     * @param data
     * @param devType
     *            0-br, 1-ob, 2-sw
     */
    public OrbiRestAPIOperation(String api, String data, int devType) {
        sPath = rc.execcURLOutput;
        devType = 1;
        getRest(api, data, devType);
    }

    /**
     * @param api
     * @param data
     * @param devType
     *            0-br, 1-ob, 2-sw
     * @return
     */
    public String getRest(String api, String data, int devType) {
        String devIp =OrbiGlobalConfig.orbiLANIp ;
        devType = 1;
        if (data == null) {
            sContent = rc.getBRRESTResult(devIp, api);
        } else {
            sContent = rc.getBRRESTResult(devIp, api, data);
        }

        File f = new File(sPath);
        try {
            jsonReader = new JSONReader(new FileReader(f));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sContent;
    }

    public String Dump() {
        return sContent;
    }

    public String getField(String keyName) {
        return MyCommonAPIs.getJsonKeyValue(keyName, jsonReader);
    }

    public void setDeviceName(String name, int devType) {
        devType = 1;
        String data = String.format("{'deviceName':{'name':'%s'}}", name);
        getRest(api_device_name, data, devType);
    }


    /**
     * @param url
     *            it's a link from http server
     * @param devType
     *            0-br, 1-ob, 2-sw
     */
    public void updateSystemFirmware(String url, int devType) {
        String devIp = WebportalParam.br1IPaddress;
        String data = String.format("{'deviceFirmwareUpdate':{'fwUrl':'%s','fwDownloadTimeout':600}}", url);
        if (devType == 1) {
            devIp = WebportalParam.ob1IPaddress;
            data = String.format("{'changeUpgradeUrl':{'newHTTPUrl':'%s','newFTPUrl':''}}", url);
        }
        if (devType == 2) {
            devIp = WebportalParam.sw1IPaddress;
            data = String.format("{'deviceFirmwareUpdate':{'rebootNow':true,'fwUrl':'%s','fwDownloadTimeout':600}}", url);
        }

        getRest(api_device_info, null, devType);
        String fw; // "firmwareVersion": "V2.2.2.310", "swVer": "19.9.22.2"
        String fwver;
        if (devType == 2) {
            fw = getField("swVer");
            fwver = fw.substring(0);
        } else {
            fw = getField("firmwareVersion");
            fwver = fw.substring(1);
        }
        if (!url.contains(fwver)) {
            System.out.println("to update system: " + url);
            if (devType == 0) {
                getRest(api_fw_update, data, devType);
                try { // br may in rebooting
                    for (int i = 0; i < 20; i++) {
                        String tocheck = getRest(api_fw_update_status, null, devType);
                        if (tocheck.contains("downloadProgress")) {
                            MyCommonAPIs.sleepi(5);
                            continue;
                        }
                        if (tocheck.contains("Firmware Update shmget")) {
                            break;
                        }
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            } else if (devType == 2) {
                getRest(api_device_fw_update, data, devType);
                try { // sw may in rebooting
                    waitRestReady(api_device_fw_update_status, "Upgrade Success", false, devType);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            } else {
                getRest(api_change_upgrade_url, data, devType);
                getRest(api_check_download_fw, "", devType);
                if (getRest(api_check_download_fw_status, null, devType).contains("checkAndDownloadFirmwareStatus")) {
                    waitRestReady(api_check_download_fw_status, "Succesfull", false, devType);
                }
                getRest(api_fw_upgrade_all, "", devType);
            }

            if (devType == 0) {
                MyCommonAPIs.sleepi(60);
            } else {
                MyCommonAPIs.sleepi(120);
            }
        } else {
            System.out.println("fw is already in this version: " + fw);
        }
    }

    /**
     * @param devType
     *            0-br, 1-ob, 2-sw
     */
    public void updateSystemFirmware(int devType) {
        String devFirmware = WebportalParam.br1Firmware;
        String devIp = WebportalParam.br1IPaddress;
        String data = String.format("{'deviceFirmwareUpdate':{'fwUrl':'%s','fwDownloadTimeout':600}}", devFirmware);
        if (devType == 1) {
            devFirmware = WebportalParam.ob1Firmware;
            devIp = WebportalParam.ob1IPaddress;
            data = String.format("{'changeUpgradeUrl':{'newHTTPUrl':'%s','newFTPUrl':''}}", devFirmware);
        }
        if (devType == 2) {
            devFirmware = WebportalParam.sw1Firmware;
            devIp = WebportalParam.sw1IPaddress;
            data = String.format("{'deviceFirmwareUpdate':{'rebootNow':true,'fwUrl':'%s','fwDownloadTimeout':600}}", devFirmware);
        }

        getRest(api_device_info, null, devType);
        String fw; // "firmwareVersion": "V2.2.2.310", "swVer": "19.9.22.2"
        String fwver;
        if (devType == 2) {
            fw = getField("swVer");
            fwver = fw.substring(0);
        } else {
            fw = getField("firmwareVersion");
            fwver = fw.substring(1);
        }
        if (!devFirmware.contains(fwver)) {
            System.out.println("to update system: " + devFirmware);
            if (devType == 0) {
                getRest(api_fw_update, data, devType);
                try { // br may in rebooting
                    for (int i = 0; i < 20; i++) {
                        String tocheck = getRest(api_fw_update_status, null, devType);
                        if (tocheck.contains("downloadProgress")) {
                            MyCommonAPIs.sleepi(5);
                            continue;
                        }
                        if (tocheck.contains("Firmware Update shmget")) {
                            break;
                        }
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            } else if (devType == 2) {
                getRest(api_device_fw_update, data, devType);
                try { // sw may in rebooting
                    waitRestReady(api_device_fw_update_status, "Upgrade Success", false, devType);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            } else {
                getRest(api_change_upgrade_url, data, devType);
                getRest(api_check_download_fw, "", devType);
                if (getRest(api_check_download_fw_status, null, devType).contains("checkAndDownloadFirmwareStatus")) {
                    waitRestReady(api_check_download_fw_status, "Succesfull", false, devType);
                }
                getRest(api_fw_upgrade_all, "", devType);
            }

            if (devType == 0) {
                MyCommonAPIs.sleepi(120);
            } else {
                MyCommonAPIs.sleepi(240);
            }
            RunCommand.waitSWAlive(devIp);
        } else {
            System.out.println("fw is already in this version: " + fw);
        }
    }



}
