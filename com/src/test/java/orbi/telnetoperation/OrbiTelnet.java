package orbi.telnetoperation;

import java.util.logging.Logger;

import orbi.param.DNIOrbiTelnetCommand;
import orbi.param.FoxconnOrbiTelnetCommand;
import orbi.param.OrbiGlobalConfig;
import util.MyCommonAPIs;
import util.TelnetOperator;
import webportal.param.WebportalParam;



/**
 * use to orbi telnet send command
 *
 * @author bingke.xue
 *
 */
public class OrbiTelnet {
    public static String  orbiIp = WebportalParam.ob2IPaddress;
    static TelnetOperator telnet;
    final static Logger   logger   = Logger.getLogger("OrbiTelnet");
    public String orbiTelnetPrompt = "root@" + WebportalParam.ob2Model +":/#";
//    public String orbiTelnetPrompt = "#";
    public int telenetPort = 23;
    public String orbiTelnetUserPrompt = "account:";
    public String orbiTelnetPasswordPrompt = "password";

    public OrbiTelnet(String ip) {
        orbiIp = ip;
        if (telnet != null) {
            telnet.distinct();
        }
        telnet = orbilogin(orbiIp);
    }


    public OrbiTelnet(String ip, String passwd) {
        orbiIp = ip;
        if (telnet != null) {
            telnet.distinct();
        }
        telnet = orbilogin(orbiIp, passwd);
    }



    public TelnetOperator orbilogin(String ip) {
        TelnetOperator telnet = new TelnetOperator("ANSI", orbiTelnetPrompt); // Windows,用VT220,否则会乱码
        telnet.disTimeout();
        telnet.loginorbi(ip, telenetPort, WebportalParam.ob2UserName, WebportalParam.loginDevicePassword);
        return telnet;
    }

    public TelnetOperator orbilogin(String ip, String passwd) {
        TelnetOperator telnet = new TelnetOperator("ANSI", orbiTelnetPrompt); // Windows,用VT220,否则会乱码
        telnet.disTimeout();
        telnet.loginorbi(ip, telenetPort, WebportalParam.ob2UserName, passwd);
        return telnet;
    }

    public String orbiTelnetSendCmd(String cmd) {
        String OutputStr = telnet.sendCommandsOrbi(cmd);
        logger.info("Output is :"+ OutputStr);
        return OutputStr;       
    }
    
    public void orbiReboot() {
        String telnetCMD = new OrbiGlobalConfig().orbiSupplier.equals("DNI")?new DNIOrbiTelnetCommand().DNIOrbiTelnetCommandMap.get("Orbi_reboot"):new FoxconnOrbiTelnetCommand().FoxconnOrbiTelnetCommandMap.get("Orbi_reboot"); 
        telnet.write(telnetCMD);
        MyCommonAPIs.sleepi(120);
    }

    public void orbiFactoryDefault() {
        String telnetCMD = new OrbiGlobalConfig().orbiSupplier.equals("DNI")?new DNIOrbiTelnetCommand().DNIOrbiTelnetCommandMap.get("Orbi_factory_default"):new FoxconnOrbiTelnetCommand().FoxconnOrbiTelnetCommandMap.get("Orbi_factory_default"); 
        telnet.write(telnetCMD);
        MyCommonAPIs.sleepi(180);
    }
    public String orbiGetRegion() {
        String telnetCMD = new OrbiGlobalConfig().orbiSupplier.equals("DNI")?new DNIOrbiTelnetCommand().DNIOrbiTelnetCommandMap.get("Orbi_get_region"):new FoxconnOrbiTelnetCommand().FoxconnOrbiTelnetCommandMap.get("Orbi_get_region"); 
        String regionOutputStr = telnet.sendCommandsOrbi(telnetCMD);
        logger.info("Region is :"+ regionOutputStr);
        String regionOutputStrList[] = regionOutputStr.replaceAll(orbiTelnetPrompt,"").trim().split("REGION:");
        return regionOutputStrList[1].trim();
       
    }
    public void orbiSetRegion(String region) {
        String telnetCMD = new OrbiGlobalConfig().orbiSupplier.equals("DNI")?new DNIOrbiTelnetCommand().DNIOrbiSetRegion(region):new FoxconnOrbiTelnetCommand().FoxconnOrbiSetRegion(region); 
        MyCommonAPIs.sleepi(10);
        String regionStr = telnet.sendCommandsOrbi(telnetCMD);
        logger.info("Region is :"+ regionStr);
        String nowRegion = this.orbiGetRegion();
        logger.info("now Region is :"+ nowRegion);
      
    }
    
    public void orbiEnableRemoteManagement() {
        String telnetCMD = new OrbiGlobalConfig().orbiSupplier.equals("DNI")?new DNIOrbiTelnetCommand().DNIOrbiTelnetCommandMap.get("Orbi_enable_remote_management"):new FoxconnOrbiTelnetCommand().FoxconnOrbiTelnetCommandMap.get("Orbi_enable_remote_management"); 
        telnet.write(telnetCMD);
        MyCommonAPIs.sleepi(60);
    }

    public static void disconnect() {
        telnet.distinct();
    }

 
}
