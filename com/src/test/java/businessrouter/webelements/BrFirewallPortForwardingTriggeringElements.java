package businessrouter.webelements;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;

public class BrFirewallPortForwardingTriggeringElements {

    public SelenideElement protforwardingenable   = $("#portForwarding_2");
    public SelenideElement prottriggeringenable   = $("#portForwarding_3");
    public SelenideElement portfortriggerservicename   = $x("//*[@id=\"root\"]/div/div/div[2]/div/div[2]/div/div/div/div/div/div/div/form/div/div[1]/div/div[2]/div/span/div/div/div");
    
    public SelenideElement portforwardingservicip1  = $("#portForwarding_5");
    public SelenideElement portforwardingservicip2   = $("#portForwarding_6");
    public SelenideElement portforwardingservicip3   = $("#portForwarding_7");
    public SelenideElement portforwardingservicip4   = $("#portForwarding_8");
    public SelenideElement portforwardingaddbutton   = $("#portForwarding_9");
    
    public SelenideElement portforwardingcustomadd   = $("#portForwardingTables_4");
    public SelenideElement portforwardingcustomedit   = $("#portForwardingTables_1");
    public SelenideElement portforwardingcustomdelete   = $("#portForwardingTables_3");
    
    public SelenideElement portfortriggereditservice   = $("#portForwardingTables_1");
    public SelenideElement portfortriggerdeleteservice   = $("#portForwardingTables_3");
    public SelenideElement portfortriggeraddcustomservice   = $("#portForwardingTables_4");
    public SelenideElement portforwardingcustomservicename   = $("#operator_portforwarding_PanelView_1");
    public SelenideElement portforwardingcustomprotocol   = $x("(//div[@class ='ant-select-selection__rendered'])[2]");
    public SelenideElement portforwardingcustomprotocoltcpudp   = $x("//li[text()='TCP/UDP']");
    public SelenideElement portforwardingcustomprotocoltcp   = $x("//li[text()='TCP']");
    public SelenideElement portforwardingcustomprotocoludp   = $x("//li[text()='UDP']");
    public SelenideElement portforwardingexternalport   = $("#operator_portforwarding_PanelView_3");
    public SelenideElement portforwardinginternalport   = $("#operator_portforwarding_PanelView_5");
    public SelenideElement portforwardinginternalportcheckbox   = $("#operator_portforwarding_PanelView_4");
    public SelenideElement portforwardingintelipaddr1   = $("#operator_portforwarding_PanelView_6");
    public SelenideElement portforwardingintelipaddr2   = $("#operator_portforwarding_PanelView_7");
    public SelenideElement portforwardingintelipaddr3   = $("#operator_portforwarding_PanelView_8");
    public SelenideElement portforwardingintelipaddr4   = $("#operator_portforwarding_PanelView_9");
    public SelenideElement portforwardingapply    =$("#operator_portforwarding_PanelView_12");
    public SelenideElement portfortriggerrefresh   = $("#portForwardingTables_5");
    public SelenideElement portforwardinglist = $x("(//span[@style='font-weight: bold;'])[1]");
    public SelenideElement portforwardingselectone    =$x("(//input[@type ='radio'])");
    public SelenideElement portforwardingokbutton  =$x("//button[@class='ant-btn ant-btn-primary ant-btn-sm']");
    
    public SelenideElement porttriggeringdisable   = $("#portTriggering_4");
    public SelenideElement porttriggeringtimeout   = $("#portTriggering_5");
    public SelenideElement porttriggeringaddbutton   = $("#portTriggeringTables_1");
    public SelenideElement porttriggeringeditbutton   = $("#portTriggeringTables_2");
    public SelenideElement porttriggeringdeletebutton   = $("#portTriggeringTables_4");
    public SelenideElement porttriggeringapplybutton   = $("#portTriggering_7");
    public SelenideElement porttriggeringruleservname   = $("#operator_porttriggering_PanelView_1");
    public SelenideElement porttriggeringruleserviceuser   = $x("(//div [@class ='ant-select-selection__rendered'])[1]");
    public SelenideElement porttriggeringruleserviceuserany   = $x("//li[text()='Any']");
    public SelenideElement porttriggeringruleserviceusersingle   = $x("//li[text()='Single address']");
    public SelenideElement porttriggeringruleserviceip1   = $("#operator_porttriggering_PanelView_3");
    public SelenideElement porttriggeringruleserviceip2   = $("#operator_porttriggering_PanelView_4");
    public SelenideElement porttriggeringruleserviceip3   = $("#operator_porttriggering_PanelView_5");
    public SelenideElement porttriggeringruleserviceip4   = $("#operator_porttriggering_PanelView_6");
    public SelenideElement porttriggeringruleservicetype   = $x("(//div [@class ='ant-select-selection__rendered'])[2]");
    public SelenideElement porttriggeringruleservicetypetcp  = $x("//li[@class='ant-select-dropdown-menu-item ant-select-dropdown-menu-item-active ant-select-dropdown-menu-item-selected']");
    public SelenideElement porttriggeringruleservicetypeudp  = $x("//li[@class='ant-select-dropdown-menu-item ant-select-dropdown-menu-item-selected']");
    public SelenideElement porttriggeringport  = $("#operator_porttriggering_PanelView_8");
    public SelenideElement porttriggeringconnectiontype  = $x("(//div [@class ='ant-select-selection__rendered'])[3]");
    public SelenideElement porttriggeringconnectiontypetcpudp  = $x("(//li[@class='ant-select-dropdown-menu-item ant-select-dropdown-menu-item-active ant-select-dropdown-menu-item-selected'])[2]");
    public SelenideElement porttriggeringconnectiontypetcp  = $x("( //li[text()='TCP'])[2]");
    public SelenideElement porttriggeringconnectiontypeudp  = $x("( //li[text()='UDP'])[2]");
    public SelenideElement porttriggeringstartingport  = $("#operator_porttriggering_PanelView_10");
    public SelenideElement porttriggeringendingport  = $("#operator_porttriggering_PanelView_11");
    public SelenideElement porttriggeringendingruleapply  = $("#operator_porttriggering_PanelView_13");
    public SelenideElement portforwardingllrules  = $x("(//span[@class = 'ant-table-row-indent indent-level-0'])");
}
