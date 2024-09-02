/**
 *
 */
package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import java.util.Map;
import java.util.logging.Logger;

import com.codeborne.selenide.Condition;

import util.BRUtils;
import util.MyCommonAPIs;
import webportal.param.CommonDataType;
import webportal.param.URLParam;
import webportal.param.CommonDataType.SSIDData;
import webportal.publicstep.WebCheck;
import webportal.webelements.DeviceOrbiWifiNetworkElement;

/**
 * @author bingke.xue
 */
public class DevicesOrbiWifiNetworkPage extends DeviceOrbiWifiNetworkElement {
    Logger          logger;
    public String   sGuestWifiName   = "";
    public String   sGuestCPWifiName = "";
    public SSIDData ssidData         = null;
    
    public void initTestData() {
        ssidData = new CommonDataType().dataSSID;
    }

    /**
     *
     */
    public DevicesOrbiWifiNetworkPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon(URLParam.hrefOrbiWifiNetworks);
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
        initTestData();
        $(wifiListTable).scrollIntoView(true);
    }
    
    public DevicesOrbiWifiNetworkPage(boolean nopage) {
        // TODO Auto-generated constructor stub
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
        initTestData();
    }
    
    public void gotoPage() {
        WebCheck.checkUrl(URLParam.hrefOrbiWifiNetworks);
        $(wifiListTable).scrollIntoView(true);
    }

    public String getWifiGuest() {
        if (sGuestWifiName.isEmpty()) {
            sGuestWifiName = new BRUtils(BRUtils.api_guest_wifi_details, 1).getField("SSID");
            sGuestCPWifiName = new BRUtils(BRUtils.api_guest_cp_details, 1).getField("SSID");
        }
        logger.info(sGuestWifiName);
        return sGuestWifiName;
    }

    public String getWifiGuestCP() {
        if (sGuestCPWifiName.isEmpty()) {
            sGuestWifiName = new BRUtils(BRUtils.api_guest_wifi_details, 1).getField("SSID");
            sGuestCPWifiName = new BRUtils(BRUtils.api_guest_cp_details, 1).getField("SSID");
        }
        logger.info(sGuestCPWifiName);
        return sGuestCPWifiName;
    }

    public int getNumberSSID() {
        return $$("#deviceTable1 tbody tr").size();
    }
    
    public boolean CheckSSIDSummary(Map<String, String> SSIDInfo) {
        for (String ss : SSIDInfo.keySet()) {
            logger.info(ss + ": " + SSIDInfo.get(ss));
        }
        boolean result = true;
        String SSIDName = SSIDInfo.get("SSIDName");
        
        DeviceOrbiWifiNetworkElement deviceorbiwifinetworkelement = new DeviceOrbiWifiNetworkElement();
        if ((SSIDInfo.get("SSIDName") != null) && !deviceorbiwifinetworkelement.SSIDNameItem(SSIDName).exists()) {
            logger.info("SSIDName check don't exist");
            result = false;
        }
        if ((SSIDInfo.get("SSIDStatus") != null)
                && !deviceorbiwifinetworkelement.SSIDStatus(SSIDName).shouldHave(Condition.text(SSIDInfo.get("SSIDStatus"))).exists()) {
            logger.info("SSIDStatus check failed");
            result = false;
        }
        if ((SSIDInfo.get("SSIDSecurity") != null)
                && !deviceorbiwifinetworkelement.SSIDSecurity(SSIDName).shouldHave(Condition.text(SSIDInfo.get("SSIDSecurity"))).exists()) {
            logger.info("SSIDSecurity check failed");
            result = false;
        }
        if ((SSIDInfo.get("SSIDPassword") != null)
                && !deviceorbiwifinetworkelement.SSIDPassword(SSIDName).shouldHave(Condition.exactText("......")).exists()) {
            logger.info("SSIDPassword check failed");
            result = false;
        }
        if ((SSIDInfo.get("BroadcastStatus") != null) && !deviceorbiwifinetworkelement.SSIDBroadcastStatus(SSIDName)
                .shouldHave(Condition.text(SSIDInfo.get("BroadcastStatus"))).exists()) {
            logger.info("BroadcastStatus check failed");
            result = false;
        }
        logger.info("result is :" + result);
        return result;
        
    }
    
    /**
     * @param iPos
     *                0-3
     * @param newName
     */
    public void editSSIDName(int iPos, String newName) {
        selectSsid(iPos);
        txtSsidName.setValue(newName);
        clickSsidSave();
        waitReady();
    }

    /**
     * @param iPos
     *             0-3
     */
    public void editSsidSetting(int iPos) {
        waitReady();
        editSsid(iPos);
        setOptEnabled(ssidData.enableSsid, ssidData.Broadcast);
        setSSIDSeparation(ssidData.enaSsidSeparation);
        setSsidName(ssidData.ssid);
        if(iPos==3) { // for guest wifi
            authenticationSelect.selectOption(1);
            SSIDPwd.sendKeys(ssidData.Password);
        }
        else {
            setSsidSecurity(ssidData.ssidSecurity, ssidData.Password);
        }
        
        setSSIDVLANProfile(ssidData.enaSsidVlan);
        if (ssidData.enaSsidVlan) {
            setSsidVlanProfile(ssidData.ssidVlan);
        }
        if (ssidData.enableCP) {
            WebCheck.checkHrefIcon(URLParam.hrefOrbicaptivePortalOrbi);
            setSelected(cbRedirectURL, ssidData.enableRedirectURL, true);
            if (!ssidData.RedirectURL.isEmpty()) {
                txtRedirectURL.clear();
                txtRedirectURL.setValue(ssidData.RedirectURL);
            }
            lbSessionTimeout.selectOption(ssidData.sessionTimeout);
            if (!ssidData.CPLogoFilename.isEmpty()) {
                btnUploadLogo.sendKeys(ssidData.CPLogoFilename);
            }
        }
        clickButton(0);
        MyCommonAPIs.sleepi(120); // wait config apply
    }
    
    public String[] getSsidSetting(int iPos) {
        String [] ssid_password = {"",""};
        waitReady();
        editSsid(iPos);
        ssid_password[0] = inputSSIDName.getValue();
        ssid_password[1] = SSIDPwd.getValue();
        
        return ssid_password;
    }
    
}
