package util;

import java.util.logging.Logger;

import webportal.param.WebportalParam;

public class PlinkCmd {
    String              devip;
    String              cmd1;
    String              cmd2;
    String              appwSuffixNew  ="czdaeq#!"; 
    String              appwSuffixOld  ="xzsawq@!";
    static boolean      tryNewPasswd   = true;
    String              plinkName      = "plink.exe";
    RunCommand          rc             = new RunCommand();
    final static Logger logger         = Logger.getLogger("PlinkCmd");
    String              sWACFirmwarePy = "python \"" + rc.execPy + "/wac502fw.py\"";
    
    public PlinkCmd(String ip) {
        devip = ip;
        reinit(tryNewPasswd);
    }

    public String getAPPaswdSuffix(boolean useNew) {
        if (useNew)
            return appwSuffixNew;
        else
            return appwSuffixOld;
    }
    
    public void reinit(boolean useNew) {
        String apPass = getAPPaswdSuffix(useNew);
        cmd1 = String.format("cmd.exe /c echo y | \"%s\" -ssh -l root -pw \"%s%s\" %s", rc.execPlink, WebportalParam.loginDevicePassword, apPass,
                devip);
        cmd2 = String.format("cmd.exe /c echo y | \"%s\" -ssh -l root -pw %s %s", rc.execPlink, WebportalParam.loginDevicePassword, devip);
    }
    
    public String getOutputEx(String cmdStr, int timeout, boolean iscmd2) {
        String result = null;
        for (int i = 0; i < 2; i++) { // fix plink waring bug
            String format_cmdStr = "";
            if (iscmd2) {
                format_cmdStr = String.format("%s %s", cmd2, cmdStr);
            } else {
                format_cmdStr = String.format("%s %s", cmd1, cmdStr);
            }
            result = rc.getCmdOutput(format_cmdStr, plinkName, timeout);
            if (result.contains("Access denied") || result.contains("closed network connection")) {
                logger.info("wrong passwd, need reboot ap if failed again");
                MyCommonAPIs.sleepi(5 * 60);
                reinit(!tryNewPasswd);
                continue;
            }
            if (result.contains("Software caused connection abort")) {
                logger.info("device is refusing our request, try to reboot or wait 5m");
                rc.enableSSH4AP();
                continue;
            }
            if (!result.contains("POTENTIAL SECURITY BREACH")) {
                break;
            }
        }
        logger.info(result);
        return result;
    }
    
    public String getAPName() {
        String name = getOutputEx(String.format("\"conf_get system:basicSettings:apName\""), 20, false);
        name = name.trim();
        return name.substring(name.length() - 13);
    }
    
    public String getAPVersion() {
        String format_cmdStr = String.format("%s %s \"%s\"", sWACFirmwarePy, devip, rc.txtGetAPVerOutput);
        rc.getCmdOutput(format_cmdStr, "python.exe", 30);
        MyCommonAPIs.sleepi(5);
        for (String line : RunCommand.read(rc.txtGetAPVerOutput).split("\n")) {
            if (line.contains("Firmware Version"))
                return line.split("- v")[1].trim();
        }
        return "not found";
    }
    
    public String getNASName() {
        String name = getOutputEx(String.format("hostname"), 20, true);
        name = name.trim();
        return name;
    }
    
    public String getOutput(String cmd, int timeout) {
        String output = getOutputEx(String.format("\"%s\"", cmd), timeout, false);
        return output;
    }
    
    /**
     * @param devType
     *                0 - ap, 1 - br
     */
    public void doReboot(int devType) {
        
    }
    
    /**
     * @param fwName
     *               a file name which download from tftp
     */
    public void Build502Cmd(String fwName) {
        String cmds = String.format("mgmt\r\n\r\n\r\n\r\nfwgrade\r\n\r\n\r\n\r\nfwup tftp://%s/%s\r\n\r\n", devip, fwName);
        rc.write(rc.txtUpgradeAPFW, false, cmds);
    }
    
    public void UpgradeAP502(String fwName) {
        // Build502Cmd(fwName);
        // String format_cmdStr = String.format("cmd.exe /c type \"%s\" | \"%s\" -ssh -batch -l admin -pw %s %s hah", rc.txtUpgradeAPFW, rc.execPlink,
        // WebportalParam.loginDevicePassword, devip);
        String format_cmdStr = String.format("%s %s %s %s \"%s\"", sWACFirmwarePy, devip, WebportalParam.TftpSvr, fwName, rc.txtSetAPVerOutput);
        rc.getCmdOutput(format_cmdStr, "python.exe", 120);
        MyCommonAPIs.sleep(180, "502 takes 3m to reboot");
    }
}
