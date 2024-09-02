package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;

import java.util.logging.Logger;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

public class LocationRoutingElement extends MyCommonAPIs {
    Logger logger = Logger.getLogger("LocationRoutingElement");
    
    public String vlanlistTable = ".DataTableBlock table tbody";
    
    public SelenideElement seMask    = $("#vlanSubmask");
//    public SelenideElement btnDelete = $("div[style*=block] button[class*=default]");
    public SelenideElement btnDelete = $x("//button[@class='btn btn-default ']");
    public SelenideElement btnClose  = $("div[style*=block] button[class*=close]");
    
    public String allInput = ".in input[data-type*=ipAddress]";
    
    public String getIpAddressXpath(String devName) {
        return String.format("//h5[contains(text(), '%s')]/../input", devName);
    }
    
    public void setIpAddress(String devName, String ipAddr) {
        setText(getIpAddressXpath(devName), ipAddr);
    }
    
    /**
     * Note call this api after sws have been set
     *
     * @param ip
     */
    public void setOtherIpAddress(String ip) {
        String nextip = nextIP(ip, 4);
        int countSw = $$(allInput).size();
        logger.info("found switch number: " + countSw);
        for (SelenideElement se : $$(allInput)) {
            if (se.getValue().length() == 0) {
                logger.info("set switch ip to: " + nextip);
                se.setValue(nextip);
                nextip = nextIP(nextip);
            }
        }
    }
    
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
