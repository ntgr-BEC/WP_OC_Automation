package util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import webportal.param.WebportalParam;

public class VPNClientToMac {
    public Socket       clientSocket = null;
    public String       vpnUserName  = "sqa005@mailinator.com";
    public String       vpnPassword  = "Netgear1@";
    public String       vpnGroupName = "VPN1";
    public Pause        aSleep       = new Pause();
    final static Logger logger       = Logger.getLogger("VPNClientToMac");
    DataOutputStream    outToServer;
    BufferedReader      inFromServer;

    public VPNClientToMac() {
        // TODO Auto-generated constructor stub
    }

    public String sendCmd(String cmd) {
        String s = "fail";
        logger.info("sending :" + cmd);
        for (int i = 0; i < 2; i++) {
            try {
                clientSocket = new Socket(WebportalParam.macVPNClientIp, 54321);
                outToServer = new DataOutputStream(clientSocket.getOutputStream());
                inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                break;
            } catch (UnknownHostException e) {
                aSleep.seconds(2);
            } catch (IOException e) {
                aSleep.seconds(2);
            }
            logger.info("try again");
        }

        if (clientSocket == null)
            return "";
        try {
            outToServer.writeBytes(cmd + "\n");
            s = inFromServer.readLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            outToServer.close();
            inFromServer.close();
            clientSocket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        logger.info(s);
        return s;
    }

    public void clientLogout() {
        sendCmd("VPNLogout");
    }

    public void clientLogin() {
        sendCmd(String.format("VPNLogin %s,%s", vpnUserName, vpnPassword));
    }

    public boolean clientGroupOpen() {
        String s = sendCmd(String.format("VPNConnectGroup %s", vpnGroupName));
        if (s.toLowerCase().contains("true"))
            return true;
        else
            return false;
    }

    public int clientPing(String ip) {
        return Integer.parseInt(sendCmd(String.format("VPNPing %s %s", ip, 120)));
    }

}
