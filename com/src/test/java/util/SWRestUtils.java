package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.alibaba.fastjson.JSONReader;

import webportal.param.WebportalParam;

public class SWRestUtils extends MyCommonAPIs {
    JSONReader        jsonReader = null;
    String            sPath      = null;
    String            sContent   = "";
    RunCommand        rc         = new RunCommand();
    public static int cycle      = 20;              // 2s

    public static String api_device_info        = "device_info";
    public static String api_device_name        = "device_name";
    public static String api_device_ip_settings = "device_ip_settings";
    public static String api_lan_ip_settings    = "lan_ip_settings";
    public static String api_device_time        = "device_time";
    public static String api_led_control        = "led_control";
    public static String api_wan_port_stats     = "wan_port_stats";
    public static String api_lan_port_stats     = "lan_port_stats";
    public static String api_lan_subnet_stats   = "lan_subnet_stats";
    public static String api_vpn_data_stats     = "vpn_data_stats";
    public static String api_swsec_ipacl_list   = "swsec_ipacl_list";
    public static String api_swsec_macacl_list  = "swsec_macacl_list";

    public SWRestUtils() {
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
     * @return string of full rest api result
     */
    public String Dump() {
        return sContent;
    }

    /**
     * @param keyName
     *            return string of single field
     * @return
     */
    public String getField(String keyName) {
        return MyCommonAPIs.getJsonKeyValue(keyName, jsonReader);
    }

    /**
     * @param api
     * @param is2ndsw
     *            true for sw2, false for sw1
     */
    public String getRest(String api, boolean is2ndsw) {
        sPath = rc.execcURLOutput;
        String swIp = WebportalParam.sw1IPaddress;
        if (is2ndsw) {
            swIp = WebportalParam.sw2IPaddress;
        }
        logger.info(String.format("<%s>-<%s>", swIp, api));
        return getRest(swIp, api);
    }

    public String getRest(String host, String api) {
        sContent = rc.getSWRESTResult(host, api);
        File f = new File(sPath);
        try {
            jsonReader = new JSONReader(new FileReader(f));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sContent;
    }

}
