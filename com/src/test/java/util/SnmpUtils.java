package util;

import java.io.File;
import java.util.logging.Logger;

import org.apache.commons.lang.RandomStringUtils;

import webportal.param.WebportalParam;

public class SnmpUtils extends MyCommonAPIs {
    final static Logger logger  = Logger.getLogger("SnmpUtils");
    RunCommand          rc      = new RunCommand();
    public String       sysDesc = ".1.3.6.1.2.1.1.1.0";
    public String       sVer    = "2c";
    public String       sIp;
    public String       sCommunity;
    
    public SnmpUtils(String Ip, String Pw) {
        sIp = Ip;
        sCommunity = Pw;
    }
    
    public String getSnmp(String oid) {
        String cmd = String.format("%s -v %s -c %s %s %s", rc.execSnmpget, sVer, sCommunity, sIp, oid);
        logger.info(cmd);
        cmd = rc.getCmdOutput(cmd, "snmpget.exe", 30);
        logger.info(cmd);
        return cmd;
    }
    
    /**
     * @return STRING: GC752X Insight 52-Port Gigabit Ethernet
     */
    public String getSysDesc() {
        return getSnmp(sysDesc);
    }
    
    public void killTrap(boolean delTrapLog) {
        if (WebportalParam.enableDebug)
            return;
        rc.killProcess("snmptrapd.exe");
        new Pause().seconds(3);
        if (delTrapLog) {
            new File(rc.execSnmptrapdOutput).renameTo(new File(rc.execSnmptrapdOutput + RandomStringUtils.randomNumeric(4)));
        }
    }
    
    public void launchTrap() {
        if (WebportalParam.enableDebug)
            return;
        killTrap(false);
        String cmd = String.format("%s -fdc %s -Lf %s", rc.execSnmptrapd, rc.execSnmptrapdConf, rc.execSnmptrapdOutput);
        System.out.printf("*******cmd=%s",cmd);
        rc.exeCmd(cmd, rc.execSnmpPath);
    }
    
    public String getTrap() {
        return RunCommand.read(rc.execSnmptrapdOutput);
    }
}
