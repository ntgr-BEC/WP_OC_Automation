package webportal.weboperation;

import java.util.List;
import java.util.logging.Logger;

import webportal.param.URLParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DeviceOrbiDHCPServersElement;

public class DevicesOrbiDHCPServersPage extends DeviceOrbiDHCPServersElement {
    Logger logger = Logger.getLogger("DeviceBRDHCPServersPage");
    
    public DevicesOrbiDHCPServersPage() {
        logger.info("init...");
    }
    
    public void gotoPage() {
        WebCheck.checkUrl(URLParam.hrefOrbidhcpServerOrbi);
        dumpInfo();
    }
    
    public List<String> getListIP() {
        return getTextsTable(sDhcpTable, 1);
    }
    
    /**
     * @param vlanName
     * @param ip
     *                 add a dhcp subnet on vlan
     */
    public void addOne(String vlanName, String ip) {
        btnAdd.click();
        lbVlan.selectOption(vlanName);
        setText(txtIp, ip);
        setSelected(cbDhcp, true);
        clickButton(0);
        sleepsync();
    }
    
    public void openOne(String vlanID) {
        editLine(sDhcpTable, 3, vlanID, 0);
        waitReady();
    }
    
    public void deleteOne(String vlanID) {
        editLine(sDhcpTable, 3, vlanID, 1);
        clickBoxLastButton();
        sleepsync();
    }
    
    public void setVlan(String vlanId) {
        lbVlan.selectOption(vlanId);
    }
    
    /**
     * @param vlanID
     * @param ip
     *               range will be 2 - 200 based on this ip
     * @param enable
     */
    public void setDHCP(String vlanID, String ip, boolean enable) {
        openOne(vlanID);
        txtIp.setValue(ip);
        enableDHCP(enable);
        txtIpStart.setValue("02");
        txtIpEnd.setValue("0200");
        clickButton(0);
    }
    
    public void setDHCP(String vlanID, boolean enable) {
        openOne(vlanID);
        enableDHCP(enable);
        clickButton(0);
    }
    
    /**
     * @param enable
     *               true to enable
     */
    public void enableDHCP(boolean enable) {
        setSelected(cbDhcp, enable, true);
    }
}
