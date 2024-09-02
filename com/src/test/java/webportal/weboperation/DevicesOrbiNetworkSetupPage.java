package webportal.weboperation;

import java.util.List;
import java.util.logging.Logger;

import webportal.param.URLParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DeviceOrbiNetworkSetupElement;

public class DevicesOrbiNetworkSetupPage extends DeviceOrbiNetworkSetupElement {
    Logger logger = Logger.getLogger("DevicesOrbiNetworkSetupPage");

    public DevicesOrbiNetworkSetupPage() {
        logger.info("init...");
        initTestData();
    }

    public void gotoPage() {
        WebCheck.checkUrl(URLParam.hrefOrbinetworkSetup);
    }

    public List<String> getNetworks() {
        return getTexts(sNetworksTable);
    }
    
    public void openNetwork(String name) {
        aNetwork(name).click();
        waitReady();
    }

    public void editNetwork(String netName, String newName, String newIp) {
        openNetwork(netName);
        setPage1Vlan(newName, null);
        clickNext();
        clickNext();
        setPage4DHCPServers(newIp, true, "", "");
        clickConfirm();
        clickViewNetwork();
        sleepsyncorbi();
    }

    public void editDefaultNetwork(String netName, boolean toAccess) {
        openNetwork("Default");
        setPage1Vlan(netName, null);
        if (toAccess) {
            setPage2PortMembers("3", 0);
        } else {
            setPage2PortMembers("3", 1);
        }
        clickNext();
        clickNext();
        clickConfirm();
        clickViewNetwork();
        sleepsyncorbi();
    }

    public void editNetwork(String netName) {
        openNetwork(netName);
        setPage1Vlan(networkData.vlanName, networkData.vlanId, networkData.ClientIsolation, networkData.NetworkIsolation);
        setPage2PortMembers(networkData.portList, networkData.portMode);
        setPage3Ssid();
        setPage4DHCPServers(networkData.DhcpIp, networkData.DhcpMode, networkData.DhcpIpStart, networkData.DhcpIpEnd);
        clickConfirm();
        clickViewNetwork();
        sleepsyncorbi();
    }

    public void createNetwork() {
        btnAdd.click();
        setPage1Vlan(networkData.vlanName, networkData.vlanId, networkData.ClientIsolation, networkData.NetworkIsolation);
        setPage2PortMembers(networkData.portList, networkData.portMode);
        setPage3Ssid();
        setPage4DHCPServers(networkData.DhcpIp, networkData.DhcpMode, networkData.DhcpIpStart, networkData.DhcpIpEnd);
        clickConfirm();
        clickViewNetwork();
        sleepsyncorbi();
    }

    public void createNetwork(String vlanName, String vlanId, String ip) {
        dumpInfo();
        btnAdd.click();
        setPage1Vlan(vlanName, vlanId);
        if (txtCurrentPage.exists()) {
            clickNext();
            clickNext();
            setPage4DHCPServers(ip, true, "", "");
        }
        clickConfirm();
        clickViewNetwork();
        sleepsyncorbi();
    }

    /**
     * @param vlanName
     * @param vlanId
     * @param portList
     *                 "1,2"
     * @param portMode
     *                 0-access, 1-trunk
     * @param ip
     */
    public void createNetworkWithPort(String vlanName, String vlanId, String portList, int portMode) {
        dumpInfo();
        btnAdd.click();
        setPage1Vlan(vlanName, vlanId);
        if (txtCurrentPage.exists()) {
            setPage2PortMembers(portList, portMode);
            clickNext();
            setPage4DHCPServers(getRandomIp(), false, "", "");
        }
        clickConfirm();
        clickViewNetwork();
        sleepsyncorbi();
    }

    public void deleteNetwork(String vlanName) {
            editLine(sNetworksTable, 1, vlanName, 1); 
            sleepi(10);
            clickBoxLastButton();
            if (getPageErrorMsg().length() < 4) {
                sleepsyncorbi();
            }
    }
}
