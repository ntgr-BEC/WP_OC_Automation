package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import java.util.logging.Logger;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

public class DeviceBRVPNGroupsElement extends MyCommonAPIs {
    Logger logger = Logger.getLogger("DeviceBRVPNGroupsElement");

    public SelenideElement btnCreate  = $(".AddVpnBlock button");
    public SelenideElement btnAddIcon = $(".addIcon.PsitionRight span");
    public SelenideElement btnOK      = $(".createVPNGroupWarningModal.in button");
    public SelenideElement txtName    = $("#smart-form-license input");
    public SelenideElement btnSave    = $("#divRowDSwitchIpSett .CreateVpnGroup.in button:last-child");

    public void clickAdd() {
        for (int i = 0; i < 2; i++) {
            if (btnCreate.exists()) {
                btnCreate.click();
            } else {
                btnAddIcon.click();
            }
            waitReady();
            // skip license issue
            if (!txtName.exists()) {
                clickBoxFirstButton();
            } else {
                break;
            }
        }
    }

    public String txtGroupCircel   = "//div[text()='%s']/..";
    public String txtGroupCircel1   = "//div[text()='%s']";
    public String txtGroupName     = ".CircleGroupName";
    public String txtDeviceList    = txtGroupCircel + "//li//p";
//    public String btnAddDevice     = txtGroupCircel + "//img[contains(@src, 'plus')]";
    public String btnAddDevice     = txtGroupCircel1 + "//following::img[contains(@src, 'assets/img/commonIcons/icon-plus-circle.png')]";
    public String btnDeleteGroup   = txtGroupCircel + "//img[contains(@src, 'delete')]";
    public String btnDeviceIcon    = txtGroupCircel + "//img[contains(@src, 'router')]";
    public String menuDeviceDetail = txtGroupCircel + "//a[contains(@class, 'colorGray')]";
    public String menuDeviceDelete = txtGroupCircel + "//a[contains(@class, 'colorRed')]";

    public SelenideElement devicecount = $x("//div[@class=\"iconAdd\"][1]/p/div[1]");
    
    /**
     * @param groupName
     * @param menu
     *                  0 - detail, 1 - delete
     */
    public void selectGroupDeviceMenu(String groupName, int menu) {
        String sElement;
        if (menu == 0) {
            sElement = String.format(menuDeviceDetail, groupName);
        } else {
            sElement = String.format(menuDeviceDelete, groupName);
        }
        for (SelenideElement se : $$x(sElement)) {
            if (se.isDisplayed()) {
                se.click();
                break;
            }
        }
    }

    public String          cbDeviceList      = ".addVPNDeviceColumn label";
    public String          txtDeviceListName = ".addVPNDeviceColumn p span";
    public SelenideElement btnSaveDevice     = $(".MACaddDevics .cstmModFooter button:last-child");

    public String staHealthy   = txtGroupCircel1 + "//following::*[contains(@class,'HealthyLines-Bottom')]";
    public String staBroken    = txtGroupCircel + "//*[contains(@class,'Broken')]";
    public String staInPorcess = txtGroupCircel + "//*[contains(@class,'Progress')]";

    public String sLinkGroupName  = ".LevelTitle.GoBack";
    public String sLinkVPNName    = ".networksetting h3 span:first-child";
    public String sLinkDeviceName = ".ListVPN H4";
    public String sLinkStatus     = "tbody td";

    // tunnel
    public SelenideElement showTunnelName = $(".networksetting h3 span:first-child");
    public SelenideElement btnTunnelName  = $(".networksetting h3 span:last-child");
    public SelenideElement showTunnelDesc = $(".TunnelBlock p");
    public SelenideElement txtTunnelName  = $("#tunnelNameInput");
    public SelenideElement txtTunnelDesc  = $("#tunnelDescInput");
    
}
