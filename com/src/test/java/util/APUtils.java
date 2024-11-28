package util;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;

import webportal.param.WebportalParam;
import webportal.webelements.DevicesDashPageElements;
import webportal.weboperation.DevicesDashPage;

public class APUtils extends MyCommonAPIs {
    final static Logger logger              = Logger.getLogger("APUtils");
    RunCommand          rc                  = new RunCommand();
    public String       apIp;
    public int          default_timeout_ssh = 30;
    public int          checkcycle          = 20;

    public PlinkCmd plink;

    public APUtils(String Ip) {
        apIp = Ip;
        plink = new PlinkCmd(Ip);
    }

    public String getLineField(String line, int pos) {
        String r = line.split(":")[pos];
        logger.info(r);
        return r;
    }

    /**
     * @param line
     * @param toMatch
     * @return vlanProfile1
     */
    public String getLineField(String line, String toMatch) {
        for (String s : line.split(":")) {
            if (s.contains(toMatch)) {
                logger.info(s);
                return s;
            }
        }
        return "error";
    }

    public String waitConfigReady(String expStr, boolean notContain) {
        logger.info(String.format("<%s>-<%s>", expStr, notContain));
        for (int i = 0; i <= checkcycle; i++) {
            String result = getConfig(expStr);
            if ((notContain && (false == result.contains(expStr))) || (!notContain && (true == result.contains(expStr)))) {
                logger.info("matched");
                return result;
            }
            MyCommonAPIs.sleepi(5);
        }
        logger.info("timeout");
        return "error";
    }

    public void setDeviceName(String name) {
    }

    public String getIpStatus() {
        String status = "";
        String output = plink.getOutput("grep 'basicSettings:ipAddr' /var/config", default_timeout_ssh);
        if (!output.equals("") && !output.contains("Connection timed out") && !output.contains("Connection refused")) {
            status = output;
        }
        return status;
    }

    public String getSsidStatus(String SSID, String Model) {
        String status = "";
        if (Model.equals("WAC505") || Model.equals("WAC510") || Model.equals("WAC540") || Model.equals("WAC564") || Model.startsWith("WAC")) {
            logger.info("entered WAC model");        
        if (plink.getOutput("grep \"wlan0:vap7:ssid \" /var/config", default_timeout_ssh).indexOf(SSID) != -1) {
            status = plink.getOutput("grep \"wlan0:vap7:vapProfileStatus\" /var/config", default_timeout_ssh);
        }
        }
        if (Model.equals("WAX610") || Model.equals("WAX610y") || Model.equals("WAX630") || Model.equals("WAX620") ||  Model.equals("WAX630E") ||  Model.equals("WAX615") ||  Model.startsWith("WAX") || Model.startsWith("WBE")) {
            logger.info("entered WAX model");
            
            if (plink.getOutput("grep \"wlan0:vap7:ssid \" /sysconfig/config", default_timeout_ssh).indexOf(SSID) != -1) {
                System.out.println("entered ");
                status = plink.getOutput("grep \"wlan0:vap7:vapProfileStatus\" /sysconfig/config", default_timeout_ssh);
            }   
            System.out.println("Exited ");
        }
        System.out.println("returnd");
        return status;
    }

    public boolean getArpProxy(String Model) {
        boolean result = false;
        String status = "";
        if (Model.equals("WAC505") || Model.equals("WAC510") || Model.equals("WAC540") || Model.equals("WAC564") || Model.startsWith("WAC")) {
            logger.info("entered WAC model");
            status = plink.getOutput("cat /var/config | grep -i proxy", default_timeout_ssh);
            System.out.println(status);
            if (!status.equals("") && status.contains("gwlanSettings:arpProxy 1")) {
                result = true;
            }
        }

        if (Model.equals("WAX610") || Model.equals("WAX610y") || Model.equals("WAX630") || Model.equals("WAX620") ||  Model.equals("WAX630E") ||   Model.startsWith("WAX")|| Model.startsWith("WBE")) {
            logger.info("entered WAX model");
            status = plink.getOutput("cat /sysconfig/config | grep -i proxy", default_timeout_ssh);
            System.out.println(status);
            if (!status.equals("") && status.contains("gwlanSettings:arpProxy 1")) {
                result = true;
            }
        }
        return result;
    }

    public boolean getB2UCdisableStatus(String Model) {
        boolean result = false;
        String status = "";
        if (Model.equals("WAC505") || Model.equals("WAC510") || Model.equals("WAC540") || Model.equals("WAC564") || Model.startsWith("WAC")) {
            logger.info("entered WAC model");
            status = plink.getOutput("cat /var/config | grep Enha", default_timeout_ssh);
            System.out.println(status);
            if (!status.equals("") && status.contains("bcastEnhance 0")) {
                result = true;
            }
        }

        if (Model.equals("WAX610") ||  Model.equals("WAX615") ||  Model.startsWith("WAX") || Model.startsWith("WBE")) {
            logger.info("entered WAX model");
            status = plink.getOutput("cat /sysconfig/config | grep Enha", default_timeout_ssh);
            System.out.println(status);
            if (!status.equals("") && status.contains("bcastEnhance 0")) {
                result = true;
            }
        }
        return result;
    }

    public boolean getB2UCEnableStatus(String Model) {
        boolean result = false;
        String status = "";
        if (Model.startsWith("WAC")) {
            logger.info("entered WAC model");
            status = plink.getOutput("cat /var/config | grep Enha", default_timeout_ssh);
            System.out.println(status);
            if (!status.equals("") && status.contains("bcastEnhance 1")) {
                result = true;
            }
        }

        if (Model.startsWith("WAX") || Model.startsWith("WBE")) {
            logger.info("entered WAX model");
            status = plink.getOutput("cat /sysconfig/config | grep Enha", default_timeout_ssh);
            System.out.println(status);
            if (!status.equals("") && status.contains("bcastEnhance 1")) {
                result = true;
            }
        }
        return result;
    }

    public boolean getIGMPdisableStatus(String Model) {
        boolean result = false;
        String status = "";
        if (Model.equals("WAC505") || Model.equals("WAC510") || Model.equals("WAC540") || Model.equals("WAC564") || Model.startsWith("WAC")) {
            logger.info("entered WAC model");
            status = plink.getOutput("cat /var/config | grep igmp", default_timeout_ssh);
            System.out.println(status);
            if (!status.equals("") && status.contains("igmpSnooping 0")) {
                result = true;
            }
        }

        if (Model.equals("WAX610")  ||  Model.equals("WAX615") ||  Model.startsWith("WAX") || Model.startsWith("WBE")) {
            logger.info("entered WAX model");
            status = plink.getOutput("cat /sysconfig/config | grep igmp", default_timeout_ssh);
            System.out.println(status);
            if (!status.equals("") && status.contains("igmpSnooping 0")) {
                result = true;
            }
        }
        return result;
    }

    public boolean getIGMPEnableStatus(String Model) {
        boolean result = false;
        String status = "";
        if (Model.equals("WAC505") || Model.equals("WAC510") || Model.equals("WAC540") || Model.equals("WAC564") || Model.startsWith("WAC")) {
            logger.info("entered WAC model");
            status = plink.getOutput("cat /var/config | grep igmp", default_timeout_ssh);
            System.out.println(status);
            if (!status.equals("") && status.contains("igmpSnooping 1")) {
                result = true;
            }
        }

        if (Model.equals("WAX610") ||  Model.equals("WAX615") ||  Model.startsWith("WAX") || Model.startsWith("WBE")) {
            logger.info("entered WAX model");
            status = plink.getOutput("cat /sysconfig/config | grep igmp", default_timeout_ssh);
            System.out.println(status);
            if (!status.equals("") && status.contains("igmpSnooping 1")) {
                result = true;
            }
        }
        return result;
    }
    
    
    public String get80211status(String cmd) {
        String result = "";
        result = plink.getOutput(cmd, default_timeout_ssh);
        System.out.println(result);
        return result;
    }   
    

    public void deleteMessages() {
        logger.info(plink.getOutput("cat /tmp/log/messages", default_timeout_ssh));
        plink.getOutput("rm /tmp/log/messages*", default_timeout_ssh);
    }

    /**
     * TODO add more ap mode
     *
     * @param devList
     *            all device list
     */
    public static void deleteMessagesAll(HashMap<String, HashMap<String, String>> devList) {
        for (String sNo : devList.keySet()) {
            String sModel = devList.get(sNo).get("Model");
            if (sModel.equals("WAC564") || sModel.equals("WAC540") || sModel.equals("WAC505") || sModel.equals("WAC510") || sModel.startsWith("WAC")) {
                new APUtils(devList.get(sNo).get("Ip")).deleteMessages();
            }
        }
    }

    /**
     * Note:call {@code deleteMessages} before check this API to make result reasonable
     *
     * @param toWait
     *            true to wait some time(1m) for that msg
     * @return
     *         true for profile is set, false to not
     */
    public boolean checkInstantWifiProfile(boolean toWait) {
        boolean toRet = false;
        for (int i = 0; i < 6; i++) {
            String output = plink.getOutput("grep 'Rf' /tmp/log/messages", default_timeout_ssh);
            if (output.contains("wlanSettings") && output.contains("cloudAgent")) {
                toRet = true;
                break;
            }
            if (!toWait) {
                break;
            }
            MyCommonAPIs.sleepi(10);
        }
        return toRet;
    }
    public String checkInstantWifiProfilecheck() {
        String output = "";
        for (int i = 0; i < 6; i++) {
            output = plink.getOutput("grep 'Rf' /tmp/log/messages", default_timeout_ssh);
            if (output.contains("wlanSettings") && output.contains("cloudAgent")) {
                break;
            }
           
            MyCommonAPIs.sleepi(10);
        }
        return output;
    }

    /**
     * TODO add more ap mode
     *
     * @param devList
     *            all device list
     * @return
     */
    public static boolean checkInstantWifiProfileAll(HashMap<String, HashMap<String, String>> devList) {
        boolean okay = false;
        for (String sNo : devList.keySet()) {
            String sModel = devList.get(sNo).get("Model");
            if (sModel.equals("WAC564") || sModel.equals("WAC540") || sModel.equals("WAC505") || sModel.equals("WAC510") || sModel.startsWith("WAC") ) {
                okay = true;
                if (!new APUtils(devList.get(sNo).get("Ip")).checkInstantWifiProfile(true))
                    return false;
            }
        }
        return okay;
    }

    public String getMacaclStatus(String mac) {
        String output = "";
        mac = mac.replace(":", "-");
        String cmd = plink.getOutput("cat /var/config | grep wlanAccessControlLocalTable:group0:" + mac.toUpperCase(), default_timeout_ssh);
        if (!cmd.contains("No such file or directory")) {
            output = cmd;
        }
        return output;
    }

    public HashMap<String, String> getChannelStatus(String domain, String Model) {
        HashMap<String, String> channelStatus = new HashMap<String, String>();
        if (Model.equals("WAC505") || Model.equals("WAC510") || Model.equals("WAC540") || Model.equals("WAC564") || Model.startsWith("WAC") ) {
       
        if (domain.contains("2.4GHz ")) {
            channelStatus.put("2.4GHz channel", plink.getOutput("grep \"wlanSettingTable:wlan0:channel \" /var/config", default_timeout_ssh));
            channelStatus.put("2.4GHz output power", plink.getOutput("grep \"wlanSettingTable:wlan0:txPower \" /var/config", default_timeout_ssh));
            channelStatus.put("2.4GHz channel width",
                    plink.getOutput("grep \"wlanSettingTable:wlan0:channelWidth \" /var/config", default_timeout_ssh));
        }
        if (domain.contains("5GHz ")) {
            channelStatus.put("5GHz channel", plink.getOutput("grep \"wlanSettingTable:wlan1:channel \" /var/config", default_timeout_ssh));
            channelStatus.put("5GHz output power", plink.getOutput("grep \"wlanSettingTable:wlan1:txPower \" /var/config", default_timeout_ssh));
            channelStatus.put("5GHz channel width",
                    plink.getOutput("grep \"wlanSettingTable:wlan1:channelWidth \" /var/config", default_timeout_ssh));
        }
        if (domain.contains("5GHz high")) {
            channelStatus.put("5GHz high channel", plink.getOutput("grep \"wlanSettingTable:wlan2:channel \" /var/config", default_timeout_ssh));
            channelStatus.put("5GHz high output power",
                    plink.getOutput("grep \"wlanSettingTable:wlan2:txPower \" /var/config", default_timeout_ssh));
            channelStatus.put("5GHz high channel width",
                    plink.getOutput("grep \"wlanSettingTable:wlan2:channelWidth \" /var/config", default_timeout_ssh));
        }
        
        }
        if (Model.equals("WAX610")  ||  Model.startsWith("WAX") || Model.startsWith("WBE")) {
            
            if (domain.contains("2.4GHz ")) {
                channelStatus.put("2.4GHz channel", plink.getOutput("grep \"wlanSettingTable:wlan0:channel \" /sysconfig/config", default_timeout_ssh));
                channelStatus.put("2.4GHz output power", plink.getOutput("grep \"wlanSettingTable:wlan0:txPower \" /sysconfig/config", default_timeout_ssh));
                channelStatus.put("2.4GHz channel width",
                        plink.getOutput("grep \"wlanSettingTable:wlan0:channelWidth \" /sysconfig/config", default_timeout_ssh));
            }
            if (domain.contains("5GHz ")) {
                channelStatus.put("5GHz channel", plink.getOutput("grep \"wlanSettingTable:wlan1:channel \" /sysconfig/config", default_timeout_ssh));
                channelStatus.put("5GHz output power", plink.getOutput("grep \"wlanSettingTable:wlan1:txPower \" /sysconfig/config", default_timeout_ssh));
                channelStatus.put("5GHz channel width",
                        plink.getOutput("grep \"wlanSettingTable:wlan1:channelWidth \" /sysconfig/config", default_timeout_ssh));
            }
            if (domain.contains("5GHz high")) {
                channelStatus.put("5GHz high channel", plink.getOutput("grep \"wlanSettingTable:wlan2:channel \" /sysconfig/config", default_timeout_ssh));
                channelStatus.put("5GHz high output power",
                        plink.getOutput("grep \"wlanSettingTable:wlan2:txPower \" /sysconfig/config", default_timeout_ssh));
                channelStatus.put("5GHz high channel width",
                        plink.getOutput("grep \"wlanSettingTable:wlan2:channelWidth \" /sysconfig/config", default_timeout_ssh));
            }
            
        }
              
        return channelStatus;
    }

    public String getRegion() {
        String region = "";
        region = plink.getOutput("grep \"sysCountryRegion\" /var/config", default_timeout_ssh);
        return region;
    }

    public String getMobilityId() {
        String id = "";
        id = plink.getOutput("grep \"mobilityId\" /var/config", default_timeout_ssh);
        return id;
    }

    public String getSsidExist() {
        String ssid = plink.getOutput("grep \"wlan0:vap0:ssid \" /var/config", default_timeout_ssh);
        logger.info("Device ssid:" + ssid);
        return ssid;
    }

    public String getDeviceName() {
        String SystemName = plink.getOutput("hostname", default_timeout_ssh);
        logger.info("Device hostname:" + SystemName);
        return SystemName;
    }

    public String getConfig(String expStr) {
        String sRet = plink.getOutput(String.format("grep '%s' /var/config", expStr), default_timeout_ssh);
        logger.info(String.format("fetch %d text", sRet.length()));
        return sRet;
    }

    public boolean isAP502() {
        if (WebportalParam.ap1Firmware.contains("502"))
            return true;
        else
            return false;
    }

    public String getVersion(boolean isAlt) {
        String ver;
        rc.enableSSH4AP();
        if (isAP502()) {
            ver = plink.getAPVersion();
        } else {
            if (isAlt) {
                ver = plink.getOutput("cat /var/alt_version", default_timeout_ssh);
            } else {
                ver = plink.getOutput("cat /etc/version", default_timeout_ssh);
            }
            Pattern p = Pattern.compile(".*(\\d{2,}\\.\\d+\\.\\d+\\.\\d+).*");
            Matcher m = p.matcher(ver.trim());
            if (m.matches()) {
                ver = m.group(1);
            }
        }
        logger.info(ver);
        return ver;
    }

    /**
     * @param firmwareURL
     *            it's a link from tftp/http server
     */
    public boolean updateFirmwareOld(String firmwareURL) {
        boolean bUpdated = false;
        String fwver = getVersion(false);
        String fwurl = WebportalParam.ap1Firmware;
        if (firmwareURL != null) {
            fwurl = firmwareURL;
        }
        if (fwurl.contains(fwver)) {
            logger.info("device is already in this version: " + fwurl);
        } else {
            bUpdated = true;
            logger.info("to update system: " + fwurl);
            if (fwurl.contains("http://")) {
                String fwName = fwurl.substring(fwurl.lastIndexOf("/") + 1);
                plink.getOutput(String.format("cd /tmp;/usr/bin/curl -LO %s", fwurl), 60);
                plink.getOutput(String.format("/usr/local/bin/firmware-upgrade-file /tmp/%s reboot check", fwName), 120);

            } else {
                if (isAP502()) {
                    plink.UpgradeAP502(fwurl);
                } else {
                    plink.getOutput(String.format("/usr/local/bin/firmware-upgrade-tftp %s %s reboot check", fwurl, WebportalParam.TftpSvr), 240);
                }
            }

            MyCommonAPIs.sleepi(30);
            RunCommand.waitSWAlive(WebportalParam.ap1IPaddress);
            MyCommonAPIs.sleepi(30);
        }
        return bUpdated;
    }

    public boolean get11REnableStatus(String Model) {
        boolean result = false;
        String status = "";
        if (Model.equals("WAC505") || Model.equals("WAC510") || Model.equals("WAC540") || Model.equals("WAC564") || Model.startsWith("WAC")) {
            logger.info("entered WAC model");
            status = plink.getOutput("cat /sysconfig/config |grep 11r", default_timeout_ssh);
            System.out.println(status);
            if (!status.equals("") && status.contains("igmpSnooping 1")) {
                result = true;
            }
        }

        if (Model.equals("WAX610")   ||  Model.startsWith("WAX") || Model.startsWith("WBE")) {
            logger.info("entered WAX model");
            status = plink.getOutput("cat /sysconfig/config |grep 11r", default_timeout_ssh);
            System.out.println(status);
            if (!status.equals("") && status.contains("igmpSnooping 1")) {
                result = true;
            }
        }
        return result;
    }
    
   

    public String getLoadBalancingStatus(String type, String Model) {
        String result = "";
        String cliMsg = "";
        System.out.println(type);
        switch (type) {
        case "Radio 2.4 GHz":
            cliMsg = "wlan0:maxClientLoadBalancingStatus";
            break;
        case "Radio 5 GHz":
            cliMsg = "wlan1:maxClientLoadBalancingStatus";
            break;
        case "Radio 5 GHz High":
            cliMsg = "wlan2:maxClientLoadBalancingStatus";
            break;
        case "Client 2.4 GHz":
            cliMsg = "wlan0:receiveRSSILoadBalancing";
            break;
        case "Client 5 GHz":
            cliMsg = "wlan1:receiveRSSILoadBalancing";
            break;
        case "Client 5 GHz High":
            cliMsg = "wlan2:receiveRSSILoadBalancing";
            break;
        case "Channel 2.4 GHz":
            cliMsg = "wlan0:channelUtilisationLoadBalancingStatus";
            break;
        case "Channel 5 GHz":
            cliMsg = "wlan1:channelUtilisationLoadBalancingStatus";
            break;
        case "Channel 5 GHz High":
            cliMsg = "wlan2:channelUtilisationLoadBalancingStatus";
            break;
        }
       System.out.println(cliMsg);
       System.out.println(default_timeout_ssh);
       MyCommonAPIs.sleepi(20);
       
       if (Model.equals("WAC505") || Model.equals("WAC510") || Model.equals("WAC540") || Model.equals("WAC564") || Model.startsWith("WAC")) {
        result = plink.getOutput("cat /var/config | grep " + cliMsg, default_timeout_ssh); 
       }
       
       if (Model.equals("WAX610")  ||  Model.equals("WAX615") ||  Model.startsWith("WAX") || Model.startsWith("WBE")) {
           
           result = plink.getOutput("cat /sysconfig/config | grep " + cliMsg, default_timeout_ssh); 
       }
        return result;
    }
    
    public String clientIsolation(String Model) {
        String result = "";
        if (Model.equals("WAC505") || Model.equals("WAC510") || Model.equals("WAC540") || Model.equals("WAC564") || Model.startsWith("WAC")) {
            logger.info("entered WAC model");
            result = plink.getOutput("cat /var/config | grep -i clientSeparation", default_timeout_ssh);
            System.out.println(result);
            
        }

        if (Model.equals("WAX610")  ||  Model.equals("WAX615") ||  Model.startsWith("WAX") || Model.startsWith("WBE")) {
            logger.info("entered WAX model");
            result = plink.getOutput("cat /sysconfig/config | grep -i clientSeparation", default_timeout_ssh);
            System.out.println(result);
            
        }
        return result;
    }
    
    public String getAdvancedRateSelectionConfig24(String Model) {
        String result = "";
        if (Model.equals("WAC505") || Model.equals("WAC510") || Model.equals("WAC540") || Model.equals("WAC564") || Model.startsWith("WAC")) {
            logger.info("entered WAC model");
            result = plink.getOutput("cat /var/config | grep wlan0:vap7:densityLevel", default_timeout_ssh);
            System.out.println(result);
            
        }

        if (Model.equals("WAX610")   ||  Model.startsWith("WAX") || Model.startsWith("WBE")) {
            logger.info("entered WAX model");
            result = plink.getOutput("cat /sysconfig/config | grep wlan0:vap7:densityLevel", default_timeout_ssh);
            System.out.println(result);
            
        }
        return result;
    }
    
    public String getAdvancedRateSelectionConfig50(String Model) {
        String result = "";
        String status = "";
        if (Model.equals("WAC505") || Model.equals("WAC510") || Model.equals("WAC540") || Model.equals("WAC564") || Model.startsWith("WAC")) {
            logger.info("entered WAC model");
            result = plink.getOutput("cat /var/config | grep wlan1:vap7:densityLevel", default_timeout_ssh);
            System.out.println(result);
            
        }

        if (Model.equals("WAX610")  ||  Model.equals("WAX615") ||  Model.startsWith("WAX") || Model.startsWith("WBE")) {
            logger.info("entered WAX model");
            result = plink.getOutput("cat /sysconfig/config | grep wlan1:vap7:densityLevel", default_timeout_ssh);
            System.out.println(result);
            
        }
        return result;
    }
    
    public String getDate() {
        String result = "";
      
        logger.info("entered WAC model");
        result = plink.getOutput("date", default_timeout_ssh);
        System.out.println(result);
            
        return result;
    }
    
    public String getARSMulticast24(String Model) {
        String result = "";
        String status = "";
        if (Model.equals("WAC505") || Model.equals("WAC510") || Model.equals("WAC540") || Model.equals("WAC564") || Model.startsWith("WAC") || Model.startsWith("WBE")) {
            logger.info("entered WAC model");
            result = plink.getOutput("cat /var/config | grep wlan0:vap0:fixedMulticastRate", default_timeout_ssh);
            System.out.println(result);
            
        }

        if (Model.equals("WAX610")  ||  Model.equals("WAX615") ||  Model.startsWith("WAX") || Model.startsWith("WBE")) {
            logger.info("entered WAX model");
            result = plink.getOutput("cat /sysconfig/config | grep wlan0:vap0:fixedMulticastRate", default_timeout_ssh);
            System.out.println(result);
            
        }
        return result;
    }
    
    public String getARSMulticast50(String Model) {
        String result = "";
        String status = "";
        if (Model.equals("WAC505") || Model.equals("WAC510") || Model.equals("WAC540") || Model.equals("WAC564") || Model.startsWith("WAC")) {
            logger.info("entered WAC model");
            result = plink.getOutput("cat /var/config | grep wlan1:vap0:fixedMulticastRate", default_timeout_ssh);
            System.out.println(result);
            
        }

        if (Model.equals("WAX610") ||  Model.startsWith("WAX") || Model.startsWith("WBE")) {
            logger.info("entered WAX model");
            result = plink.getOutput("cat /sysconfig/config | grep wlan1:vap0:fixedMulticastRate", default_timeout_ssh);
            System.out.println(result);
            
        }
        return result;
    }
    
    public boolean getMACACLstatusList(String Model) {
        boolean result = false;
        String status = "";
        if (Model.equals("WAC505") || Model.equals("WAC510") || Model.equals("WAC540") || Model.equals("WAC564") || Model.startsWith("WAC")) {
            logger.info("entered WAC model");
            status = plink.getOutput("cat /var/config | grep wlanAccessControlLocalTable", default_timeout_ssh);
            System.out.println(status);
            if (!status.equals("") && status.contains("gwlanSettings:arpProxy 1")) {
                result = true;
            }
        }

        if (Model.equals("WAX610")  ||  Model.equals("WAX615") ||  Model.startsWith("WAX") || Model.startsWith("WBE")) {
            logger.info("entered WAX model");
            status = plink.getOutput("cat /sysconfig/config | grep wlanAccessControlLocalTable", default_timeout_ssh);
            System.out.println(status);
            if (!status.equals("") && status.contains("gwlanSettings:arpProxy 1")) {
                result = true;
            }
        }
        return result;
    }

    public String MacAcl(String Model) {
       
        String status = "";
        if (Model.equals("WAC505") || Model.equals("WAC510") || Model.equals("WAC540") || Model.equals("WAC564")|| Model.startsWith("WAC")) {
            logger.info("entered WAC model");
            status = plink.getOutput("cat /var/config |grep -i accessControlSettings:wlanAccessControlLocalTable:group0", default_timeout_ssh);
            System.out.println(status);
            
        }

        if (Model.equals("WAX610") ||  Model.startsWith("WAX") || Model.startsWith("WBE")) {
            logger.info("entered WAX model");
            status = plink.getOutput("cat /sysconfig/config |grep -i accessControlSettings:wlanAccessControlLocalTable:group0", default_timeout_ssh);
            System.out.println(status);
            
        }
        return status;
    }
    
    public boolean getEEMEnableStatus(String Model) {
        boolean result = false;
        String status = "";
        if ( Model.startsWith("WAC")) {
            logger.info("entered WAC model");
            status = plink.getOutput("cat /var/config | grep -i energy", default_timeout_ssh);
            System.out.println(status);
            if (!status.equals("") && status.contains("energyEfficiencyMode 1")) {
                result = true;
            }
        }

        if (  Model.startsWith("WAX") || Model.startsWith("WBE")) {
            logger.info("entered WAX model");
            status = plink.getOutput("cat /sysconfig/config | grep -i energy", default_timeout_ssh);
            System.out.println(status);
            if (!status.equals("") && status.contains("energyEfficiencyMode 1")) {
                result = true;
            }
        }
        return result;
    }
    public boolean getEEMdisableStatus(String Model) {
        boolean result = false;
        String status = "";
        if ( Model.startsWith("WAC")) {
            logger.info("entered WAC model");
            status = plink.getOutput("cat /var/config | grep -i energy", default_timeout_ssh);
            System.out.println(status);
            if (!status.equals("") && status.contains("energyEfficiencyMode 0")) {
                result = true;
            }
        }

        if (  Model.startsWith("WAX") || Model.startsWith("WBE")) {
            logger.info("entered WAX model");
            status = plink.getOutput("cat /sysconfig/config | grep -i energy", default_timeout_ssh);
            System.out.println(status);
            if (!status.equals("") && status.contains("energyEfficiencyMode 0")) {
                result = true;
            }
        }
        return result;
    }
    
    public boolean getpushedOrganizationWideSSIDStatus(String Model) {
        boolean result = false;
        String status = "";
        if (Model.startsWith("WAC")) {
            logger.info("entered WAC model");
            System.out.println("entered WAC model");
            status = plink.getOutput("iwconfig | grep ESSID", default_timeout_ssh);
            MyCommonAPIs.sleepi(20);
            System.out.println(status);
            MyCommonAPIs.sleepi(10);
            if (status.contains("apwp14270")) {
                result = true;
                System.out.println("SSID is pushed to ap1");
            }
        }

        if (Model.startsWith("WAX") || Model.startsWith("WBE")) {
            logger.info("entered WAX model");
            System.out.println("entered WAC model");
            status = plink.getOutput("iwconfig | grep ESSID", default_timeout_ssh);
            MyCommonAPIs.sleepi(20);
            System.out.println(status);
            MyCommonAPIs.sleepi(10);
            if (status.contains("apwp14270")) {
                result = true;
                System.out.println("SSID is pushed to ap1");
            }
        }
        return result;
    }

    

    
    public String SysLogEnableStatus(String Model) {
        boolean result = false;
        String status = "";
        if ( Model.startsWith("WAC")) {
            logger.info("entered WAC model");
            MyCommonAPIs.sleepi(5);
            status = plink.getOutput("cat /var/config | grep syslog", default_timeout_ssh);
            System.out.println(status);
        }

        if (Model.startsWith("WAX") || Model.startsWith("WBE")) {
            logger.info("entered WAX model");
            MyCommonAPIs.sleepi(5);
            status = plink.getOutput("cat /sysconfig/config | grep syslog", default_timeout_ssh);
            System.out.println(status);
        }
        return status;
    }
    public String LEDEnableStatus(String Model) {
        boolean result = false;
        String status = "";
        if ( Model.startsWith("WAC")) {
            logger.info("entered WAC model");
            MyCommonAPIs.sleepi(5);
            status = plink.getOutput("cat /var/config | grep -i ledcontrol", default_timeout_ssh);
            System.out.println(status);
        }

        if (Model.startsWith("WAX") || Model.startsWith("WBE")) {
            logger.info("entered WAX model");
            MyCommonAPIs.sleepi(5);
            status = plink.getOutput("cat /sysconfig/config | grep -i ledcontrol", default_timeout_ssh);
            System.out.println(status);
        }
        return status;
    }
    public String SNMPEnableStatus(String Model) {
        boolean result = false;
        String status = "";
        if ( Model.startsWith("WAC")) {
            logger.info("entered WAC model");
            MyCommonAPIs.sleepi(5);
            status = plink.getOutput("cat /var/config | grep -i snmp", default_timeout_ssh);
            System.out.println(status);
        }

        if (Model.startsWith("WAX") || Model.startsWith("WBE")) {
            logger.info("entered WAX model");
            MyCommonAPIs.sleepi(5);
            status = plink.getOutput("cat /sysconfig/config | grep -i snmp", default_timeout_ssh);
            System.out.println(status);
        }
        return status;
    }
    
    
    public String MPSK(String Model) {
        boolean result = false;
        String status = "";
        if (Model.startsWith("WAX")) {
            logger.info("entered WAX model");
            status = plink.getOutput("cat /sysconfig/config |grep mpsk", default_timeout_ssh);
            System.out.println(status);
        }
        return status;
    }
    
    
    public boolean getSsidStatus1(String SSID, String Model) {
        MyCommonAPIs.sleepi(50);
        boolean result = false;
        String status = "";
        if (Model.equals("WAC505") || Model.equals("WAC510") || Model.equals("WAC540") || Model.equals("WAC564") || Model.startsWith("WAC")) {
            logger.info("entered WAC model"); 
            status = plink.getOutput("iwconfig | grep ESSID", default_timeout_ssh);
        if (status.contains(SSID)) {
            System.out.println("entered WAC");
            result=true;
        }
        } else if (Model.equals("WAX610")   ||  Model.startsWith("WAX") || Model.startsWith("WBE")) {
            logger.info("entered WAX model");
            status = plink.getOutput("iwconfig | grep ESSID", default_timeout_ssh);
            if (status.contains(SSID)) {
                System.out.println("entered WAX");
                result=true;
            }   
            System.out.println("Exited WAX");
        }
        System.out.println("returnd");
        return result;
    }
    
    public boolean getAdvancedRateSelectionConfig24boolean(String Model) {
        String result = "";
        boolean result1 = false;
        if (Model.equals("WAC505") || Model.equals("WAC510") || Model.equals("WAC540") || Model.equals("WAC564") || Model.startsWith("WAC")) {
            logger.info("entered WAC model");
            result = plink.getOutput("cat /var/config | grep wlan0:vap7:densityLevel", default_timeout_ssh);
            System.out.println(result);
            if (result.contains("1")) {
                result1 = true; 
            }
            
        }

        if (Model.equals("WAX610")  ||  Model.equals("WAX615") ||  Model.startsWith("WAX") || Model.startsWith("WBE")) {
            logger.info("entered WAX model");
            result = plink.getOutput("cat /sysconfig/config | grep wlan0:vap7:densityLevel", default_timeout_ssh);
            System.out.println(result);
            if (result.contains("1")) {
                result1 = true; 
            }
        }
        return result1;
    }
    
    public boolean getAdvancedRateSelectionConfig50boolean(String Model) {
        String result = "";
        boolean result1 = false;
        if (Model.equals("WAC505") || Model.equals("WAC510") || Model.equals("WAC540") || Model.equals("WAC564") || Model.startsWith("WAC")) {
            logger.info("entered WAC model");
            result = plink.getOutput("cat /var/config | grep wlan1:vap7:densityLevel", default_timeout_ssh);
            System.out.println(result);
            if (result.contains("4")) {
                result1 = true; 
            }
        }

        if (Model.equals("WAX610")  ||  Model.equals("WAX615") ||  Model.startsWith("WAX") || Model.startsWith("WBE")) {
            logger.info("entered WAX model");
            result = plink.getOutput("cat /sysconfig/config | grep wlan1:vap7:densityLevel", default_timeout_ssh);
            System.out.println(result);
            if (result.contains("4")) {
                result1 = true; 
            }
        }
        return result1;
    }
    
    public boolean getNatStatus(String Model) {
        boolean result = false;
        String status = "";
        if (Model.equals("WAC505") || Model.equals("WAC510") || Model.equals("WAC540") || Model.equals("WAC564") || Model.startsWith("WAC")) {
            logger.info("entered WAC model");
            status = plink.getOutput(" cat /var/config |grep -i nat | grep vap7 |grep natMode", default_timeout_ssh);
            System.out.println(status);
            if (!status.equals("") && status.contains("natMode 1")) {
                result = true;
            }
        }

        if (Model.equals("WAX610")  ||  Model.equals("WAX615") ||  Model.startsWith("WAX") || Model.startsWith("WBE")) {
            logger.info("entered WAX model");
            status = plink.getOutput(" cat /sysconfig/config |grep -i nat | grep vap7 |grep natMode", default_timeout_ssh);
            System.out.println(status);
            if (!status.equals("") && status.contains("natMode 1")) {
                result = true;
            }
        }
        return result;
    
    }
    public boolean getSsidStat(String Model) {
        boolean result = false;
        String status = "";
        if (Model.equals("WAC505") || Model.equals("WAC510") || Model.equals("WAC540") || Model.equals("WAC564") || Model.startsWith("WAC")) {
            logger.info("entered WAC model");
            status = plink.getOutput("cat /var/config| grep -i profilestatus |grep vap7", default_timeout_ssh);
            System.out.println(status);
            if (!status.equals("") && status.contains("ProfileStatus 1")) {
                result = true;
            }
        }

        if (Model.equals("WAX610")  ||  Model.equals("WAX615") ||  Model.startsWith("WAX") || Model.startsWith("WBE")) {
            logger.info("entered WAX model");
            status = plink.getOutput("cat /sysconfig/config| grep -i profilestatus |grep vap7", default_timeout_ssh);
            System.out.println(status);
            if (!status.equals("") && status.contains("ProfileStatus 1")) {
                result = true;
            }
        }
        return result;
    }
    
    
    public boolean checkclientIsolation(String Model) {
        String result = "";
        if (Model.equals("WAC505") || Model.equals("WAC510") || Model.equals("WAC540") || Model.equals("WAC564") || Model.startsWith("WAC")) {
            logger.info("entered WAC model");
            result = plink.getOutput("cat /var/config | grep -i clientSeparation", default_timeout_ssh);
            if(!result.equals("")&&result.contains("clientSeparation 1"))
            {
                return true;
            }        
            
            
                return false;
            

            
        }

        if (Model.equals("WAX610")  ||  Model.equals("WAX615") ||  Model.startsWith("WAX") || Model.startsWith("WBE")) {
            logger.info("entered WAX model");
            result = plink.getOutput("cat /sysconfig/config | grep -i clientSeparation", default_timeout_ssh);
        }
        if(!result.equals("") && result.contains("clientSeparation 1"))
        {
            return true;
        }
           return false;
      
    }
    
    public String getMACList(String Model) {
//        boolean result = false;
        String status = "";
        if (Model.equals("WAC505") || Model.equals("WAC510") || Model.equals("WAC540") || Model.equals("WAC564") || Model.startsWith("WAC")) {
            logger.info("entered WAC model");
            status = plink.getOutput("cat /var/config | grep wlanAccessControlLocalTable|grep group7", default_timeout_ssh); 
            System.out.println(status);
        }

        if (Model.equals("WAX610")  ||  Model.equals("WAX615") ||  Model.startsWith("WAX") || Model.startsWith("WBE")) {
            logger.info("entered WAX model");
            status = plink.getOutput(" cat /sysconfig/config | grep wlanAccessControlLocalTable|grep group7", default_timeout_ssh);
            System.out.println(status);
        }
        return status;
    }  
    
    public boolean Mpskstatus(String Model) {
        boolean result = false;
        String status = "";
        if (Model.equals("WAC505") || Model.equals("WAC510") || Model.equals("WAC540") || Model.equals("WAC564") || Model.startsWith("WAC")) {
            logger.info("entered WAC model"); 
            status = plink.getOutput("cat /var/config | grep -i mpsk", default_timeout_ssh);
            if(!status.equals("") && status.contains("mpskStatus 1"));
            result=true;
      
        } else if (Model.equals("WAX610")  ||  Model.equals("WAX615") ||  Model.startsWith("WAX") || Model.startsWith("WBE")) {
            logger.info("entered WAX model");
            status = plink.getOutput("cat /sysconfig/config | grep -i mpsk", default_timeout_ssh);
            if(!status.equals("") &&status.contains("mpskStatus 1"))
            result=true;
        }
    
        return result;
    }
    
    public boolean getScheduleWifiStatus(String Model) {
        boolean result = false;
        String status = "";
        if (Model.equals("WAC505") || Model.equals("WAC510") || Model.equals("WAC540") || Model.equals("WAC564") || Model.startsWith("WAC")) {
            logger.info("entered WAC model");
            status = plink.getOutput(" cat /var/config |grep -i ssidsch |grep -i vap7", default_timeout_ssh);
            System.out.println(status);
            if (!status.equals("") && status.contains("ssidSch 1")) {
                result = true;
            }
        }

        if (Model.equals("WAX610") ||  Model.equals("WAX615") ||  Model.startsWith("WAX") || Model.startsWith("WBE")) {
            logger.info("entered WAX model");
            status = plink.getOutput(" cat /sysconfig/config |grep -i ssidsch |grep -i vap7", default_timeout_ssh);
            System.out.println(status);
            if (!status.equals("") && status.contains("ssidSch 1")) {
                result = true;
            }
        }
        return result;
    
    }
    public boolean getSecurityStatus(String Model, String security, Map<String,String> authentication) {
        boolean result = false;
        String status = "";
        String newstatus="";
        Map<String, String> map= new HashMap<String,String>();
        map.put("Open","authenticationType 0");
        map.put("WPA2 Personal","authenticationType 32");
        map.put("WPA2 Personal Mixed","authenticationType 48");
        map.put("WPA2 Enterprise","authenticationType 8");
        map.put("WPA3 Personal","authenticationType 80");
        map.put("WPA3 Personal Mixed (WPA2 + WPA3)","authenticationType 96");
        map.put("WPA3 Enterprise","authenticationType 112");
//        map.put("OWE", "oweMode 1");
        
        
        if (Model.equals("WAC505") || Model.equals("WAC510") || Model.equals("WAC540") || Model.equals("WAC564") || Model.startsWith("WAC")) {
            logger.info("entered WAC model");
            status = plink.getOutput("cat /var/config| grep vap7 |grep wlan0 |grep-i authentication", default_timeout_ssh);
            System.out.println(status);
            if( authentication.containsKey("OWE"))
            {
                newstatus = plink.getOutput("cat /var/config | grep vap7 |grep -i oweMode", default_timeout_ssh); 
                System.out.println(newstatus);
            if (!status.equals("") && status.contains(map.get(security))&& newstatus.contains("oweMode 1")) {
                result = true;
            }
            }
            if( authentication.containsKey("OWETM"))
            {
                newstatus = plink.getOutput("cat /var/config | grep vap7 |grep -i oweMode", default_timeout_ssh); 
                System.out.println(newstatus);
            if (!status.equals("") && status.contains(map.get(security))&& newstatus.contains("oweMode 2")) {
                result = true;
            }
        }
            else if(!status.equals("") && status.contains(map.get(security)))
            {
                result= true;
            }
        }
           

        if (Model.equals("WAX610") ||   Model.equals("WAX615") ||  Model.startsWith("WAX") || Model.startsWith("WBE")) {
            logger.info("entered WAX model");
            status = plink.getOutput("cat /sysconfig/config| grep vap7 |grep wlan0 |grep -i authentication", default_timeout_ssh);
            System.out.println(status);
            if( authentication.containsKey("OWE"))
            {
                newstatus = plink.getOutput("cat /sysconfig/config | grep vap7 |grep -i oweMode", default_timeout_ssh);      
                System.out.println(newstatus);
            if (!status.equals("") && status.contains(map.get(security))&& newstatus.contains("oweMode 1")) {
                result = true;
            }
        }
            if( authentication.containsKey("OWETM"))
            {
                newstatus = plink.getOutput("cat /sysconfig/config | grep vap7 |grep -i oweMode", default_timeout_ssh); 
                System.out.println(newstatus);
            if (!status.equals("") && status.contains(map.get(security))&& newstatus.contains("oweMode 2")) {
                result = true;
            }
        }
            else if(!status.equals("") && status.contains(map.get(security)))
            {
                result= true;
            }
        
    }
        return result;
        }
    public boolean getRateLimitStatus(String Model) {
        boolean result = false;
        String status = "";
        if (Model.equals("WAC505") || Model.equals("WAC510") || Model.equals("WAC540") || Model.equals("WAC564") || Model.startsWith("WAC")) {
            logger.info("entered WAC model");
            status = plink.getOutput(" cat /var/config |grep rateLimitStatus", default_timeout_ssh);
            System.out.println(status);
            if (!status.equals("") && status.contains("rateLimitStatus 1")) {
                result = true;
            }
        }

        if (Model.equals("WAX610") ||  Model.equals("WAX615") ||  Model.startsWith("WAX") || Model.startsWith("WBE")) {
            logger.info("entered WAX model");
            status = plink.getOutput(" cat /sysconfig/config |grep rateLimitStatus", default_timeout_ssh);
            System.out.println(status);
            if (!status.equals("") && status.contains("rateLimitStatus 1")) {
                result = true;
            }
        }
        return result;
    
    }
    public boolean getVlanCustomStatus(String Model) {
        boolean result = false;
        String status = "";
        if (Model.equals("WAC505") || Model.equals("WAC510") || Model.equals("WAC540") || Model.equals("WAC564") || Model.startsWith("WAC")) {
            logger.info("entered WAC model");
            status = plink.getOutput(" cat /var/config |grep vap7 | grep -i vlan", default_timeout_ssh);
            System.out.println(status);
            if (!status.equals("") && status.contains("vlanID 8")) {
                result = true;
            }
        }

        if (Model.equals("WAX610")  ||  Model.equals("WAX615") ||  Model.startsWith("WAX") || Model.startsWith("WBE")) {
            logger.info("entered WAX model");
            status = plink.getOutput(" cat /sysconfig/config |grep vap7 | grep -i vlan", default_timeout_ssh);
            System.out.println(status);
            if (!status.equals("") && status.contains("vlanID 8")) {
                result = true;
            }
        }
        return result;
    
    }
    public boolean get11REnableStatusnew(String Model) {
        boolean result = false;
        String status = "";
        MyCommonAPIs.sleepi(30);
        if (Model.equals("WAC505") || Model.equals("WAC510") || Model.equals("WAC540") || Model.equals("WAC564") || Model.startsWith("WAC")) {
            logger.info("entered WAC model");
            status = plink.getOutput("cat /var/config |grep -i 11rstatus |grep -i vap7", default_timeout_ssh);
            System.out.println(status);
            if (!status.equals("") && status.contains("11rStatus 1")) {
                result = true;
            }
        }

        if (Model.equals("WAX610")  ||  Model.equals("WAX615") ||  Model.startsWith("WAX") || Model.startsWith("WBE")) {
            logger.info("entered WAX model");
            status = plink.getOutput("cat /sysconfig/config |grep -i 11rstatus |grep -i vap7", default_timeout_ssh);
            System.out.println(status);
            if (!status.equals("") && status.contains("11rStatus 1")) {
                result = true;
            }
        }
        return result;
    }
    public String MDNS(String Model) {
        
        String status = "";
        if (Model.startsWith("WAC")) {

            System.out.println("MDNS not applicable for WAC devices");
            
        }

        if (Model.startsWith("WAX")||Model.startsWith("WBE")) {
            logger.info("entered WAX model");
            status = plink.getOutput("cat /sysconfig/config | grep -i mdns", default_timeout_ssh);
            System.out.println(status);
            
        }
        return status;
    }
    
    public String getSsidStatusClientIso(String SSID, String Model) {
        String status = "";
        if (Model.startsWith("WAC")) {
            logger.info("entered WAC model");  
			MyCommonAPIs.sleepi(60);
        if (plink.getOutput("grep \"wlan0:vap0:ssid \" /var/config", default_timeout_ssh).indexOf(SSID) != -1) {
            status = plink.getOutput("grep \"ciAllowedList0\" /var/config", default_timeout_ssh);
        }
        }
        if ( Model.startsWith("WAX")||Model.startsWith("WBE")) {
            logger.info("entered WAX model");
            
            if (plink.getOutput("grep \"wlan0:vap7:ssid \" /sysconfig/config", default_timeout_ssh).indexOf(SSID) != -1) {
                System.out.println("entered ");
                status = plink.getOutput("grep \"ciAllowedList0\" /sysconfig/config", default_timeout_ssh);
            }   
            System.out.println("Exited ");
        }
        System.out.println("returnd");
        return status;
    }
  
   
    public String Addeditssid() {

        MyCommonAPIs.sleepi(120);
        String status = "";
            logger.info("entered WAC model");
            status = plink.getOutput("iwconfig | grep -i essid", default_timeout_ssh);
            System.out.println(status);              
        return status;
    }

    public boolean Addeditssid1(String m, String n, String Model) {
            boolean result = false;
            int nu = Integer.parseInt(n);
            String status = "";
            if (Model.equals("WAC505") || Model.equals("WAC510") || Model.equals("WAC540") || Model.equals("WAC564") || Model.startsWith("WAC")) {
                logger.info("entered model");
                status = plink.getOutput(String.format("cat /var/config | grep -i wlan%s:vap%d | grep -i profilestatus",m,nu), default_timeout_ssh);
                System.out.println(status);
                if (!status.equals("") && status.contains("1")) {
                    result = true;
                }
            }

            if (Model.equals("WAX610")  ||  Model.equals("WAX615") ||  Model.startsWith("WAX") || Model.startsWith("WBE")) {
                logger.info("entered WAX model");
                status = plink.getOutput(String.format("cat /sysconfig/config | grep -i wlan%s:vap%d | grep -i profilestatus",m,nu), default_timeout_ssh);
                System.out.println(status);
                if (!status.equals("") && status.contains("1")) {
                    result = true;
                }
            }
            return result;
    }
    public String getECPWalledGarden(String Model, String cmd) {
        String result = "";
        
        boolean result1 = false;
        if (Model.equals("WAC505") || Model.equals("WAC510") || Model.equals("WAC540") || Model.equals("WAC564") || Model.startsWith("WAC")) {
            logger.info("entered WAC model");
            MyCommonAPIs.sleepi(180);
            result = plink.getOutput("cat /var/config | grep -i "+cmd+"", default_timeout_ssh);          
        }

        if (Model.equals("WAX610")  ||  Model.equals("WAX615") ||  Model.startsWith("WAX") || Model.startsWith("WBE")) {
            logger.info("entered WAX model");
			 MyCommonAPIs.sleepi(150);
            result = plink.getOutput("cat /sysconfig/config | grep -i "+cmd+"", default_timeout_ssh);
        
        }
        return result;
    }
    
//  Written by Vivek
  public boolean checkInstantWifiProfileRF_Setting() {
      boolean flag = false;
      for (int i = 0; i < 6; i++) {
          String output = plink.getOutput("grep 'Rf settings' /tmp/log/messages", default_timeout_ssh);
          MyCommonAPIs.sleepi(5);
          logger.info(output);
          System.out.println(output);
          if (output.contains("Rf settings")) {
              flag = true;
              break;
          }
          }
          MyCommonAPIs.sleepi(10);
      return flag;
  }
  
  public boolean knownAP(String Ap2mac, String Model) {
      boolean result = false;
      if (Model.startsWith("WAC")) {
          logger.info("entered model");
         String status = plink.getOutput(String.format("cat /var/config | grep -i system:apList:knownApTable"), default_timeout_ssh);
          System.out.println(status);
          if (!status.equals("") && status.contains(Ap2mac)) {
              result = true;
          }
      }

      if (Model.startsWith("WAX")||Model.startsWith("WBE")) {
          logger.info("entered model");
         String status = plink.getOutput(String.format("cat /sysconfig/config | grep -i system:apList:knownApTable"), default_timeout_ssh);
          System.out.println(status);
          if (!status.equals("") && status.contains(Ap2mac)) {
              result = true;
          }
      }
      return result;
}
  
  public String getDtimStatus(String Model,String hz) {
     
      String status = "";
      if (Model.equals("WAC505") || Model.equals("WAC510") || Model.equals("WAC540") || Model.equals("WAC564") || Model.startsWith("WAC")) {
          logger.info("entered WAC model");
          status = plink.getOutput("cat /var/config| grep -i dtimInterval |grep wlan"+hz+"", default_timeout_ssh);
          System.out.println(status);
          
      }

      if (Model.equals("WAX610")  ||  Model.equals("WAX615") ||  Model.startsWith("WAX") || Model.startsWith("WBE")) {
          logger.info("entered WAX model");
          status = plink.getOutput("cat /sysconfig/config| grep -i dtimInterval |grep wlan"+hz+"", default_timeout_ssh);
          System.out.println(status);
      
          }
      
      return status;
  }
  public String getBeaconStatus(String Model,String hz) {
      
      String status = "";
      if (Model.equals("WAC505") || Model.equals("WAC510") || Model.equals("WAC540") || Model.equals("WAC564") || Model.startsWith("WAC")) {
          logger.info("entered WAC model");
          status = plink.getOutput("cat /var/config| grep -i beaconInterval |grep wlan"+hz+"", default_timeout_ssh);
          System.out.println(status);
          
      }

      if (Model.equals("WAX610")  ||  Model.equals("WAX615") ||  Model.startsWith("WAX") || Model.startsWith("WBE")) {
          logger.info("entered WAX model");
          status = plink.getOutput("cat /sysconfig/config| grep -i beaconInterval |grep wlan"+hz+"", default_timeout_ssh);
          System.out.println(status);
      
          }
      
      return status;
  }
public String getBroadcaststatus(String Model,String hz) {
      
      String status = "";
      if (Model.equals("WAC505") || Model.equals("WAC510") || Model.equals("WAC540") || Model.equals("WAC564") || Model.startsWith("WAC")) {
          logger.info("entered WAC model");
          status = plink.getOutput("cat /var/config| grep -i maxRateLimit |grep wlan"+hz+"", default_timeout_ssh);
          System.out.println(status);
          
      }

      if (Model.equals("WAX610")  ||  Model.equals("WAX615") ||  Model.startsWith("WAX") || Model.startsWith("WBE")) {
          logger.info("entered WAX model");
          status = plink.getOutput("cat /sysconfig/config| grep -i maxRateLimit |grep wlan"+hz+"", default_timeout_ssh);
          System.out.println(status);
      
          }
      
      return status;
  }


public String getQosStatus() {    
    String status = "";
        status = plink.getOutput("curl http://localhost:9988/api/v1/monitoring?xagent_id=QA123456-3710-203-138741094", default_timeout_ssh);
        System.out.println(status);
    return status;
}

public String getIpStatus1(String Model) {
    String status = "";
    if (Model.equals("WAC505") || Model.equals("WAC510") || Model.equals("WAC540") || Model.equals("WAC564") || Model.startsWith("WAC")) {
        logger.info("entered WAC model");
        String output = plink.getOutput("cat /var/config |grep -i ip", default_timeout_ssh);
        if (!output.equals("") && !output.contains("Connection timed out") && !output.contains("Connection refused")) {
            status = output;
        }
    } else {
        String output = plink.getOutput("cat /sysconfig/config |grep -i ip", default_timeout_ssh);
        if (!output.equals("") && !output.contains("Connection timed out") && !output.contains("Connection refused")) {
            status = output;
        }
    }
    return status;
}

public boolean getKVREnableStatus(String Model) {
    boolean result = false;
    String status = "";
    MyCommonAPIs.sleepi(30);
    if (Model.equals("WAC505") || Model.equals("WAC510") || Model.equals("WAC540") || Model.equals("WAC564") || Model.startsWith("WAC")) {
        logger.info("entered WAC model");
        status = plink.getOutput("cat /var/config |grep -i 11k|grep -i vap7", default_timeout_ssh);
        System.out.println(status);
        if (!status.equals("") && status.contains("11kvStatus 1")) {
            result = true;
        }
    }

    if (Model.equals("WAX610")  ||  Model.equals("WAX615") ||  Model.startsWith("WAX") || Model.startsWith("WBE")) {
        logger.info("entered WAX model");
        status = plink.getOutput("cat /sysconfig/config |grep -i 11k|grep -i vap7", default_timeout_ssh);
        System.out.println(status);
        if (!status.equals("") && status.contains("11kvStatus 1")) {
            result = true;
        }
    }
    return result;
}
    
public boolean switchTheFW() {
    boolean result = false;
    String status ="";
    logger.info("Switch the FW to backward firmware");
    status = plink.getOutput("/usr/local/bin/switch_alt_firmware && reboot", default_timeout_ssh);
    System.out.println("The command to switch the FW is executed");
    MyCommonAPIs.sleepi(30);
    RunCommand.waitSWAlive(WebportalParam.ap1IPaddress);
    MyCommonAPIs.sleepi(30);
    Selenide.refresh();
    new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
    if (status.equals("Swap Partition Suceess!!")){
        result = true;
    }
    return result;
    }

public boolean getBandSteeringStatus(String Model) {
    boolean result = false;
    String status = "";
    MyCommonAPIs.sleepi(30);
    if (Model.equals("WAC505") || Model.equals("WAC510") || Model.equals("WAC540") || Model.equals("WAC564") || Model.startsWith("WAC")) {
        logger.info("entered WAC model");
        status = plink.getOutput("cat /var/config |grep -i bandsteeringStatus", default_timeout_ssh);
        System.out.println(status);
        if (!status.equals("") && status.contains("bandSteeringStatus 1")) {
            result = true;
        }
    }

    if (Model.equals("WAX610")  ||  Model.equals("WAX615") ||  Model.startsWith("WAX") || Model.startsWith("WBE")) {
        logger.info("entered WAX model");
        status = plink.getOutput("cat /sysconfig/config |grep -i bandsteeringStatus", default_timeout_ssh);
        System.out.println(status);
        if (!status.equals("") && status.contains("bandSteeringStatus 1")) {
            result = true;
        }
    }
    return result;

}  


//AddedByPratik
public boolean getLoadBalancingDisassociateStickeyClients(String Model) {
    boolean result = false;
    String status = "";
    MyCommonAPIs.sleepi(60);
    if (Model.equals("WAC505") || Model.equals("WAC510") || Model.equals("WAC540") || Model.equals("WAC564") || Model.startsWith("WAC")) {
        logger.info("entered WAC model");
        status = plink.getOutput("cat /var/config | grep Sticky", default_timeout_ssh);
        System.out.println(status);
        if (!status.equals("") && status.contains("forceStickyClientDisassociationStatus 1")) {
            result = true;
        }
    }

    if (Model.equals("WAX610")  ||  Model.equals("WAX615") ||  Model.startsWith("WAX") || Model.startsWith("WBE")) {
        logger.info("entered WAX model");
        status = plink.getOutput("cat /sysconfig/config | grep Sticky", default_timeout_ssh);
        System.out.println(status);
        if (!status.equals("") && status.contains("forceStickyClientDisassociationStatus 1")) {
            result = true;
        }
    }
    return result;

}


//Added by Anusha H

public boolean getSecurityStatus1(String Model, String security, String m , String n,  Map<String,String> authentication) {
    boolean result = false;
    String status = "";
    String newstatus="";
    int nu = Integer.parseInt(n);
    Map<String, String> map= new HashMap<String,String>();
    map.put("Open","authenticationType 0");
    map.put("WPA2 Personal","authenticationType 32");
    map.put("WPA2 Personal Mixed","authenticationType 48");
    map.put("WPA2 Enterprise","authenticationType 8");
    map.put("WPA3 Personal","authenticationType 80");
    map.put("WPA3 Personal Mixed (WPA2 + WPA3)","authenticationType 96");
    map.put("WPA3 Enterprise","authenticationType 112");
    
    if (Model.startsWith("WAC")) {
        logger.info("entered WAC model");
        status = plink.getOutput(String.format("cat /var/config| grep -i wlan%s:vap%d |grep -i authentication",m,nu), default_timeout_ssh);
        System.out.println(status);
        if (!status.equals("") && status.contains(map.get(security))) {
            result = true;
        }
    }
    
    if (Model.startsWith("WAX") || Model.startsWith("WBE")) {
        logger.info("entered WAX model");
        status = plink.getOutput(String.format("cat /sysconfig/config| grep -i wlan%s:vap%d |grep -i authentication",m,nu), default_timeout_ssh);
        System.out.println(status);
        if (!status.equals("") && status.contains(map.get(security))) {
            result = true;
        }
}
    return result;
}

public boolean getBandSteeringStatus1(String m, String n, String Model) {
    boolean result = false;
    int nu = Integer.parseInt(n);
    String status = "";
    MyCommonAPIs.sleepi(30);
    if (Model.startsWith("WAC")){
        logger.info("entered WAC model");
        status = plink.getOutput(String.format("cat /var/config |grep -i bandSteeringStatus |grep -i wlan%s:vap%d ",m,nu), default_timeout_ssh);
        System.out.println(status);
        if (!status.equals("") && status.contains("bandSteeringStatus 1")) {
            result = true;
        }
    }

    if ( Model.startsWith("WAX") || Model.startsWith("WBE")) {
        logger.info("entered WAX model");
        status = plink.getOutput(String.format("cat /sysconfig/config |grep -i bandSteeringStatus |grep -i wlan%s:vap%d ",m,nu), default_timeout_ssh);
        System.out.println(status);
        if (!status.equals("") && status.contains("bandSteeringStatus 1")) {
            result = true;
        }
    }
    return result;
}

public boolean getBroadcastSSIDtogglebutton(String m, String n, String Model) {  //broadcast SSID toggle button is disabled then output is 1 
    boolean result = false;
    int nu = Integer.parseInt(n);
    String status = "";
    if (Model.startsWith("WAC")) {
        logger.info("entered model");
        status = plink.getOutput(String.format("cat /var/config |grep -i hiden |grep -i wlan%s:vap%d ",m,nu), default_timeout_ssh);
        System.out.println(status);
        if (!status.equals("") && status.contains("1")) {
            result = true;
        }
    }

    if (Model.startsWith("WAX") || Model.startsWith("WBE")) {
        logger.info("entered WAX model");
        status = plink.getOutput(String.format("cat /sysconfig/config |grep -i hiden |grep -i wlan%s:vap%d ",m,nu), default_timeout_ssh);
        System.out.println(status);
        if (!status.equals("") && status.contains("1")) {
            result = true;
        }
    }
    return result;
}

public boolean getNatStatus1(String m, String n, String Model) {
    boolean result = false;
    int nu = Integer.parseInt(n);
    String status = "";
    if (Model.startsWith("WAC")) {
        logger.info("entered model");
        status = plink.getOutput(String.format("cat /var/config | grep -i wlan%s:vap%d |grep -i nat |grep natMode",m,nu), default_timeout_ssh);
        System.out.println(status);
        if (!status.equals("") && status.contains("1")) {
            result = true;
        }
    }

    if (Model.startsWith("WAX") || Model.startsWith("WBE")) {
        logger.info("entered WAX model");
        status = plink.getOutput(String.format("cat /sysconfig/config | grep -i wlan%s:vap%d |grep -i nat |grep natMode",m,nu), default_timeout_ssh);
        System.out.println(status);
        if (!status.equals("") && status.contains("1")) {
            result = true;
        }
    }
    return result;
}

public String getSsidStatusClientIso1(String m, String n, String SSID, String Model) {
    int nu = Integer.parseInt(n);
    String status = "";
    if (Model.startsWith("WAC")) {
        logger.info("entered model");
        status = plink.getOutput(String.format("grep ciAllowedList0 /var/config  | grep -i wlan%s:vap%d ",m,nu), default_timeout_ssh);
        System.out.println(status);
    }

    if (Model.startsWith("WAX") || Model.startsWith("WBE")) {
        logger.info("entered WAX model");
        status = plink.getOutput(String.format("grep ciAllowedList0 /sysconfig/config  | grep -i wlan%s:vap%d ",m,nu), default_timeout_ssh);
        System.out.println(status);
    }
    return status;
}


public void changeAPtoLocal() {  
        logger.info("entered WAC model");
        plink.getOutput("conf_set system:basicSettings:cloudStatus 0", default_timeout_ssh);
        plink.getOutput("conf_set system:basicSettings:cloudConnectivityUI 0", default_timeout_ssh);
        plink.getOutput("conf_save", default_timeout_ssh);       
    
}


public void changeAPtoNetgear() {  
    logger.info("entered WAC model");
    plink.getOutput("conf_set system:basicSettings:cloudStatus 1", default_timeout_ssh);
    plink.getOutput("conf_set system:basicSettings:cloudConnectivityUI 1", default_timeout_ssh);      

}

public void upgrageFirmware(String fileName) {  
    plink.getOutput(String.format("/usr/local/bin/firmware-upgrade-tftp %s %s reboot check", fileName, WebportalParam.TftpSvr), 240);
}

public void Setserver(String APIP){  
    logger.info("entered WAC model");
//    String url = WebportalParam.serverUrlLogin.substring(0, WebportalParam.serverUrlLogin.indexOf("."));
//    System.out.println("URL is "+ url);
    WebDriver driver = WebDriverRunner.getWebDriver();
    String oprnURL = "http://"+APIP+":9999/insight_server";
    String username = "admin";
    String password = "Netgear1@";
    
    // Construct the URL with basic authentication
    String url = "http://" + username + ":" + password + "@172.16.19.41:9999/insight_server";

    ((JavascriptExecutor) driver).executeScript("window.open('" + url + "', '_blank');");
    Selenide.switchTo().window(1);
    String currentURL = new MyCommonAPIs().getCurrentUrl();
    System.out.println(currentURL);
    MyCommonAPIs.sleepi(5);     
    if(url.contains("maint-beta")) {
        System.out.println("inside Maint-beta");
        new DevicesDashPageElements().dropdownclick.selectOption("MAINT-BETA");
    }else if(url.contains("pri-qa")) {
        System.out.println("inside Pri-qa");
        new DevicesDashPageElements().dropdownclick.selectOption("PRI-QA");      
    }
    new DevicesDashPageElements().submit.click();
    
    MyCommonAPIs.sleepi(5);
    if(new DevicesDashPageElements().sucessmessage.exists()) {
    System.out.println(new DevicesDashPageElements().sucessmessage.getText());
    }
    MyCommonAPIs.sleepi(5);
    Selenide.switchTo().window(0);   

}

//AddedByPratik
public boolean getCustomerProfileStatus(String CustomerProfileOption, String Model) {
    MyCommonAPIs.sleepi(5);
    boolean result = false;
    String status = "";
    if (Model.startsWith("WBE")) {
        logger.info("entered WBE model");
        status = plink.getOutput("cat /sysconfig/config | grep customerProfile", default_timeout_ssh);
        if (status.contains(CustomerProfileOption)) {
            System.out.println("entered WBE");
            result=true;
        }   
        System.out.println("Exited WBE");
    }
    System.out.println("returnd");
    return result;
}

}

