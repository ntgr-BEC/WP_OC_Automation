package webportal.weboperation;

import java.util.logging.Logger;

import org.openqa.selenium.Keys;

import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.RadiusElement;

public class RadiusPage extends RadiusElement {
    Logger logger = Logger.getLogger("RadiusPage");
    String ip1    = "10.1.1.1";
    String ip2    = "10.1.1.2";

    public RadiusPage() {
        logger.info("init...");
    }

    public void gotoPage() {
        if (!txtIp.exists()) {
            new AccountPage().enterEditNetworkPage();

        }
        WebCheck.checkUrl(URLParam.hrefRadius);
    }

    public void disableAuth() {
        setSelected(cbEnableAuth(), false, true);
        clickButton(0);
    }

    public void enableAuth(String ip1, String ip2) {
        if (getValue(txtIp).equals(ip1))
            return;
        setSelected(cbEnableAuth(), true, true);
        setText(txtIp, ip1);
        sleep(2000);
//        setText(txtIpSec, ip2);
        txtIpSec.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        txtIpSec.sendKeys(ip2);
        
        clickButton(0);
    }

    // Add by Dallas
    public boolean checkAuthStatus(String ip1) {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        if (txtIp.isEnabled() && txtIp.getAttribute("value").equals(ip1)) {
            result = true;
            logger.info("Auth status is enabled.");
        }
        return result;
    }
    
    public boolean enableRadiusserver() {
        boolean result = false;
        gotoPage();
        MyCommonAPIs.sleepi(10);
        waitElement(enableRadiusServer);
        enableRadiusServer.click();
        MyCommonAPIs.sleepi(10);
        waitElement(inputPrimaryIPAddress);
        inputPrimaryIPAddress.clear();
        MyCommonAPIs.sleepi(1);
        inputPrimaryIPAddress.sendKeys("172.16.27.12");
        MyCommonAPIs.sleepi(1);
        inputSecurityPassword.clear();
        MyCommonAPIs.sleepi(1);
        inputSecurityPassword.sendKeys("Netgear1@");
        MyCommonAPIs.sleepi(1);
        saveRadiusSettings.click();
        waitElement(radiusServerEnableSuccessMsg);
        String msg = radiusServerEnableSuccessMsg.getText();
        if (radiusServerEnableSuccessMsg.exists()) {
            System.out.println(msg);
            result = true;
        }
        return result;
    }
    
}
