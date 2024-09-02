package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;

import java.util.List;
import java.util.logging.Logger;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;

public class DeviceOrbiWifiNetworkElement extends MyCommonAPIs {
    Logger logger = Logger.getLogger("DeviceOrbiWifiNetworkElement");

    public static SelenideElement enableSwitchOn         = $x(
            "//h5[(text()='" + WebportalParam.getLocText("Enable") + "']/../label[contains(@class,'disableAccordian')]");
    public static SelenideElement enableSwitchOff        = $x(
            "//h5[(text()='" + WebportalParam.getLocText("Enable") + "']/../label[not(contains(@class,'disableAccordian'))]");
    public static SelenideElement enableSwitch           = $x("//h5[(text()='" + WebportalParam.getLocText("Enable") + "']/../label");
    public SelenideElement        inputSSIDName          = $x("//input[@data-type='orbiSsid']");
    public static SelenideElement broadCastSSIDSwitchOn  = $x(
            "//h5[(text()='" + WebportalParam.getLocText("Broadcast SSID") + "']/../label[contains(@class,'disableAccordian')]");
    public static SelenideElement broadCastSSIDSwitchOff = $x(
            "//h5[(text()='" + WebportalParam.getLocText("Broadcast SSID") + "']/../label[not(contains(@class,'disableAccordian'))]");
    public static SelenideElement broadCastSSIDSwitch    = $x(
            "//h5[(text()='" + WebportalParam.getLocText("Broadcast SSID") + "']/../label[not(contains(@class,'disableAccordian'))]");
    public static SelenideElement securitySelect         = $x("//h5[text()='" + WebportalParam.getLocText("Security") + "']/../select");
    public SelenideElement        SSIDPwd                = $("[data-type='ssidOrbiPassword']");
    // public SelenideElement SSIDPwd = $x("//input[@name='password']");
    public SelenideElement        btnCancel            = $(".btn.ipbtn.cancelBtn");
    public SelenideElement        btnSave              = $(".btn.saveBtn");
    public static SelenideElement expirationSelect     = $x("//h5[text()='" + WebportalParam.getLocText("Expiration") + "']/../select");
    public static SelenideElement authenticationSelect = $x("//h5[text()='" + WebportalParam.getLocText("Authentication Option") + "']/../select");

    public String wifiListTable = "tbody";
    public String staEnable     = "#setOrbiWifiDetailsSecurityOrbi label input";

    public static SelenideElement inputsepaenable = $x("//h5[text()='SSID Separation']/../label/input");
    public static SelenideElement inputvlanenable = $x("//input[@id='vlanEnable']");
    public static SelenideElement inputwifiscenable = $x("//input[@id='wifiSchedule']");
    public static SelenideElement inputipaddr     = $x("//input[@id='secondaryAdd']");
    public static SelenideElement inputportnum    = $x("//h5[text()='Port Number']/../input");
    public static SelenideElement inputsecretkey  = $x("//input[@id='secondarySecret']");

    public static SelenideElement spanvlanenable = $x("//h5[text()='VLAN Profile']/../label/span");
    public static SelenideElement spanwifiscenable = $x("//h5[text()='WiFi Schedules']/../label/span");
    public static SelenideElement spansepaenable = $x("//h5[text()='SSID Separation']/../label/span");

    public void setOptEnabled(boolean enable, boolean broadcast) {
        ElementsCollection ecs = $$(staEnable);
        if (ecs.get(0).is(Condition.checked) != enable) {
            $$(staEnable + "+span").get(0).click();
        }
        if (ecs.get(1).is(Condition.checked) != broadcast) {
            $$(staEnable + "+span").get(1).click();
        }
    }

    public boolean WifiIsEnabled() { // the checkbox is checked
        ElementsCollection ecs = $$(staEnable);
        if (ecs.get(0).is(Condition.checked) == true) { return true; }
        return false;
    }

    public boolean WifiIsBlocked() { // can not change the attribute of the element
        ElementsCollection ecs = $$(staEnable);
        if (ecs.get(0).is(Condition.disabled) == true) { return true; }
        return false;
    }

    public void setSSIDSeparation(boolean sta) {
        /*
         * ElementsCollection ecs = $$(staEnable);
         * if (ecs.get(2).is(Condition.checked) != sta) {
         * $$(staEnable + "+span").get(2).click();
         * }
         */
        if (inputsepaenable.is(Condition.checked) != sta) {
            spansepaenable.click();
            // $$(staEnable + "+span").get(3).click();
        }

    }

    public void setSSIDVLANProfile(boolean sta) {
        /*
         * ElementsCollection ecs = $$(staEnable);
         * if (ecs.get(3).is(Condition.checked) != sta) {
         * $$(staEnable + "+span").get(3).click();
         * }
         */
        if (inputvlanenable.is(Condition.checked) != sta) {
            spanvlanenable.click();
            // $$(staEnable + "+span").get(3).click();
        }

    }
    
    public void setWifiSchedule(boolean sta) {
        if (inputwifiscenable.is(Condition.checked) != sta) {
            spanwifiscenable.click();
            // $$(staEnable + "+span").get(3).click();
        }

    }

    public void setSsidName(String ssid) {
        inputSSIDName.clear();
        inputSSIDName.sendKeys(ssid);
    }

    public void setSsidVlanProfile(String vlan) {
        lbSsidVlan.selectOption(vlan);
    }
    
    public void selectWifiSchedule(String name) {
        lbWifiSchedule.selectOption(name);
    }

    public void setSsidSecurity(int Security, String passwd) {
        if (Security == 0) {
            securitySelect.selectOption(0);
        } else if (Security == 1) { // aes
            securitySelect.selectOption(1);
            SSIDPwd.setValue(passwd);
        } else if (Security == 2) { // aes-tkip
            securitySelect.selectOption(2);
            SSIDPwd.setValue(passwd);
        } else if (Security == 3) { // wpae
            securitySelect.selectOption(3);
        } else if (Security == 4) { // sae
            securitySelect.selectOption(4);
            SSIDPwd.setValue(passwd);
        } else if (Security == 5) { // aes-sae
            securitySelect.selectOption(5);
            SSIDPwd.setValue(passwd);
        } else if (Security == 6) { // wpa3e
            securitySelect.selectOption(6);
        } else {
            logger.info("error: " + Security);
        } 

    }
    

    public void setRadiusServer(String ipaddr, String portnum, String secretkey) {
        inputipaddr.setValue(ipaddr);
        inputportnum.setValue(portnum);
        inputsecretkey.setValue(secretkey);
    }

    public void openWiFiSSID(String ssid) {
        editLine(wifiListTable, 1, ssid, 1);

    }

    /**
     * @param status
     * @param ssid
     * @param broadcast
     * @param expired
     *            "1, 2, or Never"
     * @param auth
     *            0 for None, 1 for pass
     * @param passwd
     */
    public void editGuestCPSSID(boolean status, String ssid, boolean broadcast, String expired, int auth, String passwd) {
        editSsid(3); // openWiFiSSID(ssid);
        setOptEnabled(status, broadcast);
        // setSelected($$(staEnable).first(), status, true);
        // setSelected($$(staEnable).last(), broadcast, true);
        setSsidName(ssid);
        // expirationSelect.selectOptionContainingText("1");
        authenticationSelect.selectOption(auth);
        if (auth == 1) {
            SSIDPwd.sendKeys(passwd);
        }
        clickButton(0);
    }

    public List<String> getWiFiList() {
        return getTexts("tbody td:first-child p");
    }
    
    /**
     * @param ssididx
     *            1 for wifi1, 2 for wifi2, 3 for wifi3, 4 for guest
     * @param colidx
     *            1 for ssid, 2 for status, 3 for type, 4 for security, 6 for vlan
     */
    public String getWiFiInfo(int ssididx, int colidx) {
        SelenideElement targetele = $x("//tbody/tr[" + ssididx + "]/td["+ colidx + "]");
        return targetele.getText();
    }

    public SelenideElement SSIDNameItem(String SSIDName) {
        return $x("//p[@title='" + SSIDName + "']");
    }

    public SelenideElement SSIDStatus(String SSIDName) {
        return $x("//p[@title='" + SSIDName + "']/../../td[2]");
    }

    public SelenideElement SSIDSecurity(String SSIDName) {
        return $x("//p[@title='" + SSIDName + "']/../../td[4]");
    }

    public SelenideElement SSIDPassword(String SSIDName) {
        return $x("//p[@title='" + SSIDName + "']/../../td[5]");
    }

    public SelenideElement SSIDBroadcastStatus(String SSIDName) {
        return $x("//p[@title='" + SSIDName + "']/../../td[6]");
    }

    public String          txtSsidRow     = "tr td:first-child p";
    public String          txtSsidEditRow = "tr td:last-child";
    public SelenideElement txtSsidName    = $("tr td input");
    public SelenideElement lbSsidSecurity = $("#setOrbiWifiDetailsSecurityOrbi select");
    // public SelenideElement lbSsidVlan = $("#HvlaneditWirNet+select");
    public SelenideElement lbSsidVlan   = $x("//*[@id='selectedVlanId']");
    public SelenideElement lbWifiSchedule   = $x("//h5[text()='WiFi Schedules']/../..//*[@id='selectedVlanId']");
    public String          btnSssidSave = "tr td span button img";

    public void selectSsid(int iPos) {
        SelenideElement row = $$(txtSsidRow).get(iPos);
        row.hover();
        row.find("img").click();
    }

    public void clickSsidSave() {
        $$(btnSssidSave).get(0).click();
    }
    
    public void clickSave() {
        btnSave.scrollIntoView(true);
        btnSave.click();
    }

    public void editSsid(int iPos) {
        SelenideElement row = $$(txtSsidEditRow).get(iPos);
        row.hover();
        row.find("img").click();
        waitReady();
    }

    // capital portal
    public SelenideElement cbRedirectURL    = $("#OnOffBtnURL");
    public SelenideElement txtRedirectURL   = $("#divREdUrCapPort input");
    public SelenideElement lbSessionTimeout = $("#divOnSesCapPort select");
    public SelenideElement btnUploadLogo    = $("#divFileCapPort input[type=file]");
}
