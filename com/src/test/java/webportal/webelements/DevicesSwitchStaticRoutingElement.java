package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;

import java.util.logging.Logger;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

public class DevicesSwitchStaticRoutingElement extends MyCommonAPIs {
    Logger logger = Logger.getLogger("DevicesSwitchStaticRoutingElement");

    public String vlanlistTable = ".DataTableBlock table tbody";

    public SelenideElement seMask    = $("#vlanSubmask");
    public SelenideElement btnDelete = $("div[style*=block] button[class*=default]");
    public SelenideElement btnClose  = $("div[style*=block] button[class*=close]");

    public String lsSectionItem = "#_divColMdThree h3";

    public String sGateWay = "#gatewayipAddress";

    public String          rbSwitch  = ".OnOffSetting label";
    public SelenideElement btnAdd    = $(".StaticRoutingTableBlock .MoreToggle");
    public SelenideElement cbDefault = $("#defaultstate");
    public String          txtField  = ".AddNewSwitchBlock h5+input";

    public SelenideElement btnYes = $(".modal.fade.DefaultRuote.in button:last-child");

    public String routeTable     = ".StaticRoutingTableBlock table";
    public String routeTableIp   = String.format("%s tr td:nth-of-type(1)", routeTable);
    public String routeTableMask = String.format("%s tr td:nth-of-type(2)", routeTable);
    public String routeTableGw   = String.format("%s tr td:nth-of-type(3)", routeTable);
    public String routeTableEdit = String.format("%s img", routeTable);
}
