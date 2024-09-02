package webportal.weboperation;

import java.util.logging.Logger;

import webportal.param.URLParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DeviceBRWanIPElement;

public class DeviceBRWanIPPage extends DeviceBRWanIPElement {
    Logger logger = Logger.getLogger("DeviceBRWanIPPage");
    
    public DeviceBRWanIPPage() {
        logger.info("init...");
    }
    
    public void gotoPage() {
        WebCheck.checkUrl(URLParam.hrefBRWanIP);
    }
    
    public void saveIt() {
        clickButton(0);
        sleepsync();
    }
    
    /**
     * @param enable
     *               true to enable dhcp, false to static
     */
    public void setDHCP(boolean enable) {
        setSelected(cbDhcp, enable, true);
        if (enable) {
            sleepi(30);
        }
    }
    
    public void setStatic(String ip, String mask, String gw) {
        setSelected(cbDhcp, false, true);
        setText(txtIp, ip);
        if (mask != null) {
            setText(txtMask, mask);
        }
        if (gw != null) {
            setText(txtGateway, gw);
        }
    }
    
    public void setStatic(String dns1, String dns2) {
        setSelected(cbDhcp, false, true);
        setText(txtDNS1, dns1);
        if (dns2 != null) {
            setText(txtDNS2, dns2);
        }
    }
    
}
