package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import webportal.param.WebportalParam;

public class VPNClient {
    RunCommand          rc     = new RunCommand();
    final static Logger logger = Logger.getLogger("VPNClient");

    String sInsightVPNClient = "C:\\Program Files (x86)\\InsightVPNClient\\bin\\InsightVPNClient.exe";
    String sVpnClient        = "python \"" + rc.execPy + "/vpnclient.py\"";
    String vpnUser           = null;
    String vpnPasswd         = WebportalParam.loginPassword;

    public VPNClient() {
    }

    public VPNClient(String user) {
        vpnUser = user;
    }

    public boolean isInstalled() {
        if (new File(sInsightVPNClient).exists())
            return true;
        return false;
    }

    /**
     * @param sCMD
     * @return 0 means pass, 1 means fail
     */
    public int getReturnValue(String sCMD) {
        Process p = rc.exeCmd(sCMD, rc.execPy);
        final BufferedReader out = new BufferedReader(new InputStreamReader(p.getInputStream())),
                err = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        try {
            if (!p.waitFor(240, TimeUnit.SECONDS)) {
                System.out.println("FIXME: proc is still running[timeout increase here or reduce in py], try 1m more");
                p.waitFor(60, TimeUnit.SECONDS);
            }
            p.destroyForcibly();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        new Pause().seconds(10, "10s more to wait");

        System.out.println("check output, it will output stderr if proc was exists");
        int iMaxLine = 100, i = 0;
        Thread t = new Thread(MyCommonAPIs.myVPNClientMonitor);
        t.start();
        String allLine = "";
        String line1 = null;
        String line2 = null;
        try {
            do {
                i++;
                line1 = out.readLine();
                if (line1 == null) {
                    break;
                }
                line2 = err.readLine();
                if (line2 == null) {
                    break;
                }
                allLine += line1 + line2;
                System.out.println("1: " + line1 + "2: " + line2);
                if (allLine.contains("tcis")) {
                    break;
                }
            } while (true);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        MyCommonAPIs.isVpnClientStart = false;
        if (allLine.contains("tcispass"))
            return 0;

        return p.exitValue();
    }

    public boolean checkTermPolicy() {
        String toRun = String.format("%s 3", sVpnClient);

        if (getReturnValue(toRun) == 0)
            return true;
        else
            return false;
    }

    public boolean checkCreateAccount() {
        String toRun = String.format("%s 6", sVpnClient);

        if (getReturnValue(toRun) == 0)
            return true;
        else
            return false;
    }

    public boolean checkForgotAccount() {
        String toRun = String.format("%s 7", sVpnClient);

        if (getReturnValue(toRun) == 0)
            return true;
        else
            return false;
    }

    public boolean checkCopyright(String sUser, String sPasswd) {
        String toRun = String.format("%s 8 %s %s", sVpnClient, sUser, sPasswd);

        if (getReturnValue(toRun) == 0)
            return true;
        else
            return false;
    }

    public boolean LoginOut(String sUser, String sPasswd) {
        String toRun = String.format("%s 1 %s %s", sVpnClient, sUser, sPasswd);

        if (getReturnValue(toRun) == 0)
            return true;
        else
            return false;
    }

    public boolean ClientConnectToGroup(String sUser, String sPasswd, String groupName) {
        String toRun = String.format("%s 4 %s %s %s", sVpnClient, sUser, sPasswd, groupName);

        if (getReturnValue(toRun) == 0)
            return true;
        else
            return false;
    }

    public boolean VPNClientCheckLinkInfo(String sUser, String sPasswd, String groupName) {
        String toRun = String.format("%s 5 %s %s %s", sVpnClient, sUser, sPasswd, groupName);

        if (getReturnValue(toRun) == 0)
            return true;
        else
            return false;
    }

    public boolean callStumb(String funcName) {
        String toRun = String.format("%s %s %s %s", sVpnClient, funcName, vpnUser, vpnPasswd);

        if (getReturnValue(toRun) == 0)
            return true;
        else
            return false;
    }
}
