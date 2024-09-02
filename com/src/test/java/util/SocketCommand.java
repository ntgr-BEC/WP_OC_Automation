package util;

import webportal.param.WebportalParam;

//import util.Javasocket;

public class SocketCommand extends Javasocket {
    
    public void DisableNIC(String client_ip, String client_port, String nic_name) {
        String cmd = "netsh interface set interface %s disable";
        new Javasocket().sendCommandToWinClient(client_ip, client_port, String.format(cmd, nic_name));
        MyCommonAPIs.sleepi(5);
    }
    
    public void EnableNIC(String client_ip, String client_port, String nic_name) {
        String cmd = "netsh interface set interface %s enable";
        new Javasocket().sendCommandToWinClient(client_ip, client_port, String.format(cmd, nic_name));
        MyCommonAPIs.sleepi(5);
    }
    
    public String getNICIP(String client_ip, String client_port, String nic_name) {
        String cmd = "netsh interface ip show address \"%s\" | findstr \"IP Address\"";
        String result = new Javasocket().sendCommandToWinClient(client_ip, client_port, String.format(cmd, nic_name));
        MyCommonAPIs.sleepi(5);
        System.out.println(result);
        String[] temp = result.trim().split(" ");
        result = temp[temp.length-1];
        System.out.println(result);
        return result;
    }
    
    public String getNICMAC(String client_ip, String client_port, String nic_name) {
        String cmd = "ipconfig /all | findstr \"%s Physical\"";
        String result = new Javasocket().sendCommandToWinClient(client_ip, client_port, String.format(cmd, nic_name));
        MyCommonAPIs.sleepi(5);
        
        String mac_str = "";
        String[] temp1 = result.split(nic_name);
        
        String[] temp = temp1[1].trim().split("Physical Address. . . . . . . . . : ");
        
        result = temp[1].trim();
        result = result.replace("-", ":");
        return result;
    }
    
    public String getWiFiIP(String client_ip, String client_port) {
        String cmd = "WAFgetip Wi-Fi";
        String result = new Javasocket().sendCommandToWinClient(client_ip, client_port, cmd);
        MyCommonAPIs.sleepi(5);
        String[] splited = result.split("\\s+");
        result = splited[1];
        System.out.println(result);
        return result;
    }
    
    public boolean checkNICSubnetPrefix(String client_ip, String client_port, String nic_name, String subnet) {
        String cmd = "netsh interface ip show address \"%s\" | findstr \"Subnet\"";
        String result = new Javasocket().sendCommandToWinClient(client_ip, client_port, String.format(cmd, nic_name));
        MyCommonAPIs.sleepi(5);
        System.out.println(result);
        if (result.contains(subnet)) {
            return true;
        }
        return false;
    }
    
    public boolean checkNICIP(String client_ip, String client_port, String nic_name, String routerip) {
        String hostip = getNICIP(client_ip, client_port, nic_name);
        String[] temp = hostip.trim().split("\\.");
        String subhostip = temp[0] + temp[1] + temp[2];
        temp = routerip.trim().split("\\.");
        String subrouterip = temp[0] + temp[1] + temp[2];
        System.out.println(subhostip);
        System.out.println(subrouterip);
        if(subhostip.equals(subrouterip)) {
            return true;
        }
        return false;
    }
    
    public void DisconnectWifi(String client_ip, String client_port) {
        new Javasocket().sendCommandToWinClient(client_ip, client_port, "WAFdisconnect");
        MyCommonAPIs.sleepi(7);
    }
    
    public void RestartWifi(String client_ip, String client_port, String nic_name) {
        new Javasocket().sendCommandToWinClient(client_ip, client_port, String.format("WAFwifiOff %s", nic_name));
        MyCommonAPIs.sleepi(7);
        new Javasocket().sendCommandToWinClient(client_ip, client_port, String.format("WAFwifiOn %s", nic_name));
        MyCommonAPIs.sleepi(7);
    }
    
    public void ReleaseAndRenewIP(String client_ip, String client_port, String nic_name) {
        new Javasocket().sendCommandToWinClient(client_ip, client_port, String.format("ipconfig /release \"%s\"", nic_name));
        MyCommonAPIs.sleepi(10);
        new Javasocket().sendCommandToWinClient(client_ip, client_port, String.format("ipconfig /renew \"%s\"", nic_name));
        MyCommonAPIs.sleepi(10);
    }
    
    public boolean BSSIDConnect(String client_ip, String client_port, String ssid, String password, String bssid) {
        boolean result = true;
        String cmd = String.format("WAFconnectBSSID %s %s WPA2PSK aes %s", ssid, password, bssid);
        
        if (!new Javasocket()
                .sendCommandToWinClient(client_ip, client_port, cmd)
                .equals("true")) {
            result = false;
        }
        
        MyCommonAPIs.sleepi(10);
        return result;
    }
    
    public boolean Pingtest(String src_ip, String target_ip, String client_ip, String client_port) {
        boolean result = false;
        String cmd = String.format("WAFping %s %s", target_ip, src_ip);
        String ping_result = new Javasocket().sendCommandToWinClient(client_ip, client_port, cmd);
        String s = "Reply from %s:";
        if (ping_result.contains(String.format(s, target_ip))) {
            result = true;
        } 
        return result;
    }
    
    
}