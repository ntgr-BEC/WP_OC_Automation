package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Logger;

import com.alibaba.fastjson.JSONReader;

import orbi.telnetoperation.OrbiTelnet;
import orbi.weboperation.OrbiDebugSettingsPage;
import webportal.param.WebportalParam;

public class BRUtils extends MyCommonAPIs {
    final static Logger logger        = Logger.getLogger("BRUtils");
    JSONReader          jsonReader    = null;
    String              sPath         = null;
    String              sContent      = "";
    RunCommand          rc            = new RunCommand();
    String              sBRGateway    = "python \"" + rc.execPy + "/br_make_gateway.py\"";
    static boolean      resetBRRouter = false;
    public int          brmode;
    
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
    public static String api_satellites_details       = "satellites_details";
    public static String api_wifi_details_24g         = "wifi_details?wifi_band=2.4G";
    public static String api_wifi_details_5g          = "wifi_details?wifi_band=5G";
    public static String api_guest_wifi_details       = "guest_wifi_details";
    public static String api_attached_devices         = "attached_devices";
    public static String api_guest_cp_details         = "guest_cp_details";

    public static String api_device_wlan_info              = "device_wlan_info"; // first wifi network
    public static String api_device_guest_wlan_info        = "device_guest_wlan_info";
    public static String api_device_iot_wlan_info          = "device_iot_wlan_info";
    public static String api_device_guest_portal_wlan_info = "device_guest_portal_wlan_info";
    public static String api_vlan_network_infos            = "vlan_network_infos"; // network setup
    public static String api_lan_subnet                    = "lan_subnet"; // dhcp servers
    
    void init() {
        if (WebportalParam.br1model.contains("500") || WebportalParam.br1model.contains("200")) {
            brmode = 0;
        } else {
            brmode = 1;
        }
        if (MyCommonAPIs.testcaseName.contains("BRVPNRemoteUser2Site") || (MyCommonAPIs.testcaseName.contains(".BR") && !resetBRRouter)) {
            resetBRRouter = true;
            rc.exeCmd(sBRGateway + " " + WebportalParam.br1IPaddress, rc.execPy);
        }
    }
    
    public BRUtils() {
        init();
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
     *                0-br, 1-ob, 2-sw, 3-ob8, 4-orbi6-ap_mode
     */
    public BRUtils(String api, int devType) {
        init();
        sPath = rc.execcURLOutput;
        getRest(api, null, devType);
    }
    
    /**
     * @param api
     * @param data
     * @param devType
     *                0-br, 1-ob, 2-sw
     */
    public BRUtils(String api, String data, int devType) {
        sPath = rc.execcURLOutput;
        getRest(api, data, devType);
    }
    
    /**
     * @param  api
     * @param  data
     * @param  devType
     *                 0-br, 1/3-ob router mode, 2-sw, 4-ob ap mode
     * @return
     */
    public String getRest(String api, String data, int devType) {
        String devIp = WebportalParam.br1IPaddress;
        if ((devType == 1) || (devType == 3)) {
            devIp = "192.168.1.1";
            enableOrbiRestapi(devIp, WebportalParam.loginDevicePassword);
        }
        if (devType == 2) {
            devIp = WebportalParam.sw1IPaddress;
        }
        if (devType == 2) {
            if (data == null) {
                sContent = rc.getSWRESTResult(devIp, api);
            } else {
                sContent = rc.getSWRESTResult(devIp, api, data);
                
            }
        } else if (devType == 3) {
            if (data == null) {
                sContent = rc.getOBRESTResult(devIp, api);
            } else {
                sContent = rc.getOBRESTResult(devIp, api, data);
                
            }
        } else if (devType == 4) {
            devIp = WebportalParam.ob1IPaddress;
            enableOrbiRestapi(devIp, WebportalParam.loginDevicePassword);
            if (data == null) {
                sContent = rc.getBRRESTResult_ap(devIp, api);
            } else {
                sContent = rc.getBRRESTResult_ap(devIp, api, data);
            }
        } else {
            if (data == null) {
                sContent = rc.getBRRESTResult(devIp, api);
            } else {
                sContent = rc.getBRRESTResult(devIp, api, data);
                
            }
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

    public String getFieldOrbi(String keyName) {
        return MyCommonAPIs.getJsonKeyValue(keyName, rc.execcURLOutput);
    }
    
    public String getFieldsOrbi(String keyName) {
        return MyCommonAPIs.getJsonKeyValues(keyName, rc.execcURLOutput);
    }

    public String getFieldOrbi(String dictKey, String keyItem, String keyVal, String keyReturn) {
        return MyCommonAPIs.getJsonKeyValue(dictKey, keyItem, keyVal, keyReturn, rc.execcURLOutput);
    }
    
    public void setDeviceName(String name, int devType) {
        String data = String.format("{'deviceName':{'name':'%s'}}", name);
        getRest(api_device_name, data, devType);
    }
    
    public void setLANIp(String ip, boolean dhcp) {
        int status = 0;
        if (dhcp) {
            status = 1;
        }
        String startIp = nextIP(ip, 10);
        String endIp = nextIP(ip, 50);
        // String data = String.format(
        // "{'lanIpSettings': {'lanIp': '%s','lanSubnet': '255.255.255.0','dhcpEnabled': %d,'startIp': '%s','endIp': '%s'}}",
        // ip, status,
        // startIp, endIp);
        // getRest(api_, data);
        String spy = String.format("python.exe %s/br_lan_set.py -p %s -i %s -j %s -k %s -t %d", rc.execPy, WebportalParam.loginDevicePassword,
                startIp, endIp, WebportalParam.br1IPaddress, brmode);
        rc.exeCmd(spy, rc.execPy);
    }
    
    /**
     * @param url
     *                it's a link from http server
     * @param devType
     *                0-br, 1-ob, 2-sw
     */
    public boolean updateSystemFirmware(String url, int devType) {
        boolean bUpdated = false;
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
        String fw; // "firmwareVersion": "V20.30.42.4", "swVer": "19.9.22.2"
        String fwver;
        if (devType == 2) {
            fw = getField("swVer");
            fwver = fw.substring(0);
        } else {
            fw = getField("firmwareVersion");
            fwver = fw.substring(3); // BR200-V99.27.42.4
        }
        if (!url.contains(fwver)) {
            bUpdated = true;
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
        return bUpdated;
    }
    
    /**
     * @param devType
     *                0-br, 1-ob, 2-sw
     */
    public boolean updateSystemFirmware(int devType) {
        boolean bUpdated = false;
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
        String fw; // "firmwareVersion": "V2.2.2.310", "swVer": "19.9.22.2", 'firmwareVersion', "V20.03.22.1"
        String fwver;
        if (devType == 2) {
            fw = getField("swVer");
            fwver = fw.substring(0);
        } else {
            fw = getField("firmwareVersion");
            fwver = fw.substring(3); // We make BR to like V99.36.22.4
        }
        if (!devFirmware.contains(fwver)) {
            bUpdated = true;
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
        return bUpdated;
    }
    
    public void createDHCP(String vlanId, String dhcpIp) {
        String spy = String.format("python.exe %s/br_vlan_create.py -p %s -i %s -j %s -n %s -t %d", rc.execPy, WebportalParam.loginDevicePassword,
                WebportalParam.br1IPaddress, dhcpIp, vlanId, brmode);
        rc.exeCmd(spy, rc.execPy);
    }
    
    public void remoteIpSec() {
        String data = "{'policy_list':[{'id':1},{'id':2},{'id':3},{'id':4}]}";
        RunCommand.bcURLDelete = true;
        try {
            getRest(api_ipsec_policy, data, 0);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        RunCommand.bcURLDelete = false;
    }

    public String getOrbiVlanInfo() {
        return "";
    }
    
    public void enableOrbiRestapi(String ip, String pw) {
        String cmd = "";
        String output = "";
        // if telnet not work, enable it
        if(WebportalParam.arch.equals("old")) {
            try{
                cmd = "pwd";
                output = new OrbiTelnet(ip , pw).orbiTelnetSendCmd(cmd);
            }catch(Throwable e) {
                OrbiDebugSettingsPage page = new OrbiDebugSettingsPage();
                page.OrbibaseEnableTelenet(ip, pw);
            }
        }else { //sxk50 doesn't have telnet timeout, so directly open debug page to enable telnet
            try {
                OrbiDebugSettingsPage page = new OrbiDebugSettingsPage();
                page.OrbibaseEnableTelenet(ip, pw);
            }catch(Throwable e) {
                System.out.println("second time try open debug page failed");
            }
        }
        
        // enable orbi restapi
        cmd = "/etc/init.d/insight-configd stop";
        output = new OrbiTelnet(ip , pw).orbiTelnetSendCmd(cmd);
        MyCommonAPIs.sleepi(5);
        cmd = "sed -i -e 's/\"-l 2 -r -q\"/\"-l 2 -r -q -t\"/g' /etc/init.d/insight-configd";
        System.out.println(cmd);
        output = new OrbiTelnet(ip , pw).orbiTelnetSendCmd(cmd);
        cmd = "/etc/init.d/insight-configd start";
        output = new OrbiTelnet(ip , pw).orbiTelnetSendCmd(cmd);
        MyCommonAPIs.sleepi(5);
    }
    
}
