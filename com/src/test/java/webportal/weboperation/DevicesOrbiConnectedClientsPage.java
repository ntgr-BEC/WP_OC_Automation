/**
 *
 */
package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DevicesOrbiConnectedClientsPageElement;

/**
 * @author lavi
 *
 */
public class DevicesOrbiConnectedClientsPage extends DevicesOrbiConnectedClientsPageElement {
    Logger logger;

    /**
     *
     */
    public DevicesOrbiConnectedClientsPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon(URLParam.hrefOrbiConnectedClients);
        MyCommonAPIs.sleepi(2);
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    public DevicesOrbiConnectedClientsPage(boolean nopage) {
        // TODO Auto-generated constructor stub
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    public List<String> getAllConnectedClients() {
        List<String> lsName = new ArrayList<String>();
        if (isTableHasData())
            return getTextsTable(sConnectedClientTable, 1);
        return lsName;
    }

    public void gotoConnectedClients() {
        if (!$(sConnectedClientTable).isDisplayed()) {
            tabConnected.click();
            MyCommonAPIs.sleepi(3);
        }
    }

    public void gotoDisconnectedClients() {
        if (!$(sDisconnectedClientTable).isDisplayed()) {
            tabDisconnected.click();
            MyCommonAPIs.sleepi(3);
        }
    }

    /**
     * @param connected
     *            true - connected clients, false - disconnected clients
     * 
     */
    public boolean clientExists(String devicename, boolean connected) {
        if (connected) {
            gotoConnectedClients();
            System.out.println(getTextsTable(sConnectedClientTable, 1));
            if (getTextsTable(sConnectedClientTable, 1).contains(devicename)) { return true; }
        } else {
            gotoDisconnectedClients();
            System.out.println(getTextsTable(sDisconnectedClientTable, 1));
            if (getTextsTable(sDisconnectedClientTable, 1).contains(devicename)) { return true; }
        }
        return false;
    }

    /**
     * @param connected
     *            true - connected clients, false - disconnected clients
     * 
     */
    public boolean isWiredClient(String devicename, boolean connected) {
        if (connected) {
            gotoConnectedClients();
            if (getTextsTable(sConnectedClientTable, 1).contains(devicename) && isWired(devicename)) { return true; }
        } else {
            gotoDisconnectedClients();
            if (getTextsTable(sDisconnectedClientTable, 1).contains(devicename) && isWired(devicename)) { return true; }
        }
        return false;
    }

    /**
     * @param connected
     *            true - connected clients, false - disconnected clients
     * 
     */
    public boolean isWirelessClient(String devicename, boolean connected) {
        if (connected) {
            gotoConnectedClients();
            if (getTextsTable(sConnectedClientTable, 1).contains(devicename) && isWireless(devicename)) { return true; }
        } else {
            gotoDisconnectedClients();
            if (getTextsTable(sDisconnectedClientTable, 1).contains(devicename) && isWireless(devicename)) { return true; }
        }
        return false;
    }

    public String getSSID(String devicename, boolean connected) {
        if (connected) {
            gotoConnectedClients();
            return connectedSSID(devicename);
        } else {
            gotoDisconnectedClients();
            return connectedSSID(devicename);
        }
    }

    public String getMAC(String devicename, boolean connected) {
        if (connected) {
            gotoConnectedClients();
            return macAddr(devicename).trim();
        } else {
            gotoDisconnectedClients();
            return macAddr(devicename).trim();
        }
    }

    public String getIP(String devicename, boolean connected) {
        if (connected) {
            gotoConnectedClients();
            return ipAddr(devicename);
        } else {
            gotoDisconnectedClients();
            return ipAddr(devicename);
        }
    }

    public boolean search(String devicename, String keyword, boolean connected) {
        if (connected) {
            gotoConnectedClients();
        } else {
            gotoDisconnectedClients();
        }
        searchicon.click();
        MyCommonAPIs.sleepi(2);
        // searchinput.clear();
        // searchinput.sendKeys(keyword);
        searchinput.setValue(keyword);
        MyCommonAPIs.sleepi(2);
        searchbtn.click();
        MyCommonAPIs.sleepi(3);
        if (clientExists(devicename, connected)) { return true; }
        return false;

    }

    public void pauseClient(String devicename) {
        if(pauseresumecheckbox(devicename).isSelected()) {
            pauseresumeslide(devicename).click();
            MyCommonAPIs.sleepi(120);
        }
    }
    
    public void resumeClient(String devicename) {
        if(!pauseresumecheckbox(devicename).isSelected()) {
            pauseresumeslide(devicename).click();
            MyCommonAPIs.sleepi(120);
        }
    }

}
