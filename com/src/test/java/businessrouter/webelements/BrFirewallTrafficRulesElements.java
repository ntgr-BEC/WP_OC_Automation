package businessrouter.webelements;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;

public class BrFirewallTrafficRulesElements {
    public SelenideElement trafficrulesaddbutton   = $("#traffic_rules_1");
    public SelenideElement trafficrulesrefreshbutton   = $("#traffic_rules_2");
    public SelenideElement trafficrulesdeletebutton   = $("#traffic_rules_3");
    public SelenideElement trafficruleeditbutton   = $("#traffic_rules_1_");
    public SelenideElement trafficrulesname   = $("#name");
    public SelenideElement trafficrulesprotocol   = $x("(//div[@class='ant-select-selection__rendered'])[1]");
    public SelenideElement trafficrulesprotocolall   = $x("//li[text()='ALL']");
    public SelenideElement trafficrulesprotocolicmp   = $x("//li[text()='ICMP']");
    public SelenideElement trafficrulesprotocoludp   = $x("//li[text()='UDP']");
    public SelenideElement trafficrulesprotocoltcp   = $x("//li[text()='TCP']");
    public SelenideElement trafficrulesprotocoludptcp   = $x("//li[text()='TCP + UDP']");
    public SelenideElement trafficrulessourcelan = $("#editFirewall_PanelView_8");    
    public SelenideElement trafficrulessourcewan   = $("#editFirewall_PanelView_10");
    public SelenideElement trafficrulessourceaddress1   = $("#sourceAddressGroup_1");
    public SelenideElement trafficrulessourceaddress2   = $("#sourceAddressGroup_2");
    public SelenideElement trafficrulessourceaddress3   = $("#sourceAddressGroup_3");
    public SelenideElement trafficrulessourceaddress4   = $("#sourceAddressGroup_4");
    public SelenideElement trafficrulessourceport   = $("#src_port");
    public SelenideElement trafficrulessourcemac   = $("#src_mac");
    public SelenideElement trafficrulesdesdevice =$("#editFirewall_PanelView_15");
    public SelenideElement trafficrulesdeslan  = $("#editFirewall_PanelView_16");
    public SelenideElement trafficrulesdeswan  = $("#editFirewall_PanelView_18");
    public SelenideElement trafficrulesdesaddress1  = $("#destAddressGroup_1");
    public SelenideElement trafficrulesdesaddress2  = $("#destAddressGroup_2");
    public SelenideElement trafficrulesdesaddress3  = $("#destAddressGroup_3");
    public SelenideElement trafficrulesdesaddress4  = $("#destAddressGroup_4");
    public SelenideElement trafficrulesdesport  = $("#dest_port");
    public SelenideElement trafficrulesaction  = $x("(//div[@class='ant-select-selection__rendered'])[2]");
    public SelenideElement trafficrulesactionaccept  = $x("//li[text()='ACCEPT']");
    public SelenideElement trafficrulesactiondrop  = $x("//li[text()='DROP']");
    public SelenideElement trafficrulesextraarg  = $("#extra");
    public SelenideElement trafficrulescancelbutton  = $("#editFirewall_PanelView_25");
    public SelenideElement trafficrulesapplybutton  = $("#editFirewall_PanelView_26");
    public SelenideElement trafficrulesselectall  = $x("//*[@id=\"traffic_rules_3\"]/div/div/table/thead/tr/th[1]/span/div/label/span/input");
    public SelenideElement trafficrulesokbutton  =$x("//*[@id=\"traffic_rules_3_popDelete\"]/div/div/div[2]/button[2]");
    public SelenideElement trafficrulestable = $x("//*[@id=\"traffic_rules_3\"]/div/div/table/tbody");
    public SelenideElement trafficruleselectone = $x("(//label[@class = 'ant-checkbox-wrapper'])");
    
    public SelenideElement trafficrulesourceaddressstart1  = $("#sourceAddressGroup_iprange_startip1");
    public SelenideElement trafficrulesourceaddressstart2 =  $("#sourceAddressGroup_iprange_startip2");
    public SelenideElement trafficrulesourceaddressstart3  = $("#sourceAddressGroup_iprange_startip3");
    public SelenideElement trafficrulesourceaddressstart4 =  $("#sourceAddressGroup_iprange_startip4");
    
    public SelenideElement trafficrulesourceaddressend1  = $("#sourceAddressGroup_iprange_endip1");
    public SelenideElement trafficrulesourceaddressend2  = $("#sourceAddressGroup_iprange_endip2");
    public SelenideElement trafficrulesourceaddressend3  = $("#sourceAddressGroup_iprange_endip3");
    public SelenideElement trafficrulesourceaddressend4  = $("#sourceAddressGroup_iprange_endip4");
    public SelenideElement trafficrulesourcerange       = $("#sourceAddressGroup_iprange");
    
    
    public SelenideElement trafficruledestnationaddressstart1  = $("#destAddressGroup_iprange_startip1");
    public SelenideElement trafficruledestnationaddressstart2 =  $("#destAddressGroup_iprange_startip2");
    public SelenideElement trafficruledestnationaddressstart3  = $("#destAddressGroup_iprange_startip3");
    public SelenideElement trafficruledestnationaddressstart4 =  $("#destAddressGroup_iprange_startip4");
    
    public SelenideElement trafficruledestnationaddressend1  = $("#destAddressGroup_iprange_endip1");
    public SelenideElement trafficruledestnationaddressend2  = $("#destAddressGroup_iprange_endip2");
    public SelenideElement trafficruledestnationaddressend3  = $("#destAddressGroup_iprange_endip3");
    public SelenideElement trafficruledestnationaddressend4  = $("#destAddressGroup_iprange_endip4");
    public SelenideElement trafficrulesdestnationrange       = $("#destAddressGroup_iprange");
    public SelenideElement trafficruleswarning               = $x("//div[@class = 'ant-form-explain']");
    public SelenideElement trafficrulessourcelannumber2      = $x("(//span[text()='LAN2'])[1]");
  

}