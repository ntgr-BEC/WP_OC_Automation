package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;

import java.util.logging.Logger;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

public class DeviceBRVPNUsersElement extends MyCommonAPIs {
    Logger logger = Logger.getLogger("DeviceBRVPNUsersElement");

    public String sUserTabl = "tbody";
    public String txtName   = ".sorting_1 span";
    public String txtEmail  = "#tdDevMac1AddrdevicesDash";
    public String txtStatus = "#tdDevMacAddrdevicesDash";

    public SelenideElement btnAdd      = $(".VPNGroupTop span");
    public SelenideElement txtEmailAdd = $("#vpnUserEmailInput");
    public SelenideElement btnInvite   = $(".in .cstmModFooter button");
    public SelenideElement btnRemove   = $(".in .modal-footer button:last-child");

    public String sUserLogin = "tbody tr td";
}
