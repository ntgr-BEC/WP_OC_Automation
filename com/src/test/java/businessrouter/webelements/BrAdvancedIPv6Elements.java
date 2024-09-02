package businessrouter.webelements;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;
public class BrAdvancedIPv6Elements {
    public SelenideElement internetconnectiontype   = $x("//div[@class='ant-select-selection__rendered']");
    public SelenideElement connectiontypeautodetect  =$x("//li[text()='Auto Detect']");
    public SelenideElement connectiontypeautoconfig  =$x("//li[text()='Auto Config']");
    public SelenideElement connectiontypedhcp  =$x("//li[text()='DHCP']");
    public SelenideElement connectiontypefixed  =$x("//li[text()='Fixed']");
    public SelenideElement connectiontypepassthrough  =$x("//li[text()='Pass Through']");
    public SelenideElement connectiontypedisabled  =$x("//li[text()='Disabled']");
    public SelenideElement ipv6cancelbutton   = $("#ipv6_cancel_btn");
    public SelenideElement ipv6applybutton  = $("#ipv6_apply_btn");
    public SelenideElement ipv6refreshbutton  = $("#ipv6_refresh_btn");
    public SelenideElement ipv6connetiontype  = $x("//*[@id=\"root\"]/div/div/div[2]/div/div[2]/div/div/div/div/div/div/div/div[3]/form[1]/div/div[1]");
    public SelenideElement ipv6wanip  = $x("//*[@id=\"root\"]/div/div/div[2]/div/div[2]/div/div/div/div/div/div/div/div[3]/form[1]/div/div[2]/p/p");
    public SelenideElement ipv6autoconfigwanip  = $x("//*[@id=\"root\"]/div/div/div[2]/div/div[2]/div/div/div/div/div/div/div/div[3]/form[1]/div[2]/div/p/p");
    public SelenideElement ipv6autoconfiglanip  = $x("//*[@id=\"root\"]/div/div/div[2]/div/div[2]/div/div/div/div/div/div/div/div[3]/form[3]/div[1]/p/p");
                                            
    public SelenideElement ipv6lanip  = $x("//*[@id=\"root\"]/div/div/div[2]/div/div[2]/div/div/div/div/div/div/div/div[3]/form[2]/div[1]/p/p");
    public SelenideElement ipv6primarydns1 = $("#ipv6_DNSAddress_Primary_0");
    
    public SelenideElement ipv6staticprimarydns1 = $("#IPv6_PD_addr0");
    public SelenideElement ipv6staticprimarydns2 = $("#IPv6_PD_addr1");
    public SelenideElement ipv6staticprimarydns3 = $("#IPv6_PD_addr2");
    public SelenideElement ipv6staticprimarydns4 = $("#IPv6_PD_addr3");
    public SelenideElement ipv6staticprimarydns5 = $("#IPv6_PD_addr4");
    public SelenideElement ipv6staticprimarydns6 = $("#IPv6_PD_addr5");
    public SelenideElement ipv6staticprimarydns7 = $("#IPv6_PD_addr6");
    public SelenideElement ipv6staticprimarydns8 = $("#IPv6_PD_addr7");

 
    public SelenideElement ipv6staticwan1 = $("#IPv6_wan_addr0");
    public SelenideElement ipv6staticwan2 = $("#IPv6_wan_addr1");
    public SelenideElement ipv6staticwan3 = $("#IPv6_wan_addr2");
    public SelenideElement ipv6staticwan4 = $("#IPv6_wan_addr3");
    public SelenideElement ipv6staticwan5 = $("#IPv6_wan_addr4");
    public SelenideElement ipv6staticwan6 = $("#IPv6_wan_addr5");
    public SelenideElement ipv6staticwan7 = $("#IPv6_wan_addr6");
    public SelenideElement ipv6staticwan8 = $("#IPv6_wan_addr7");
    public SelenideElement ipv6staticwanlen = $("#Profix_wan_length");
    public SelenideElement ipv6wandefaultgateway1 =  $("#IPv6_gat_eway0");
    public SelenideElement ipv6wandefaultgateway2 =  $("#IPv6_gat_eway1");
    public SelenideElement ipv6wandefaultgateway3 =  $("#IPv6_gat_eway2");
    public SelenideElement ipv6wandefaultgateway4 =  $("#IPv6_gat_eway3");
    public SelenideElement ipv6wandefaultgateway5 =  $("#IPv6_gat_eway4");
    public SelenideElement ipv6wandefaultgateway6 =  $("#IPv6_gat_eway5");
    public SelenideElement ipv6wandefaultgateway7 =  $("#IPv6_gat_eway6");
    public SelenideElement ipv6wandefaultgateway8 =  $("#IPv6_gat_eway7");
    public SelenideElement ipv6staticlan1 = $("#ipv6_lanSetup_Address_0");
    public SelenideElement ipv6staticlan2 = $("#ipv6_lanSetup_Address_1");
    public SelenideElement ipv6staticlan3 = $("#ipv6_lanSetup_Address_2");
    public SelenideElement ipv6staticlan4 = $("#ipv6_lanSetup_Address_3");
    public SelenideElement ipv6staticlan5 = $("#ipv6_lanSetup_Address_4");
    public SelenideElement ipv6staticlan6 = $("#ipv6_lanSetup_Address_5");
    public SelenideElement ipv6staticlan7 = $("#ipv6_lanSetup_Address_6");
    public SelenideElement ipv6staticlan8 = $("#ipv6_lanSetup_Address_7");
    public SelenideElement ipv6staticlanlen = $("#ipv6_lanSetup_AddressNumber");
    
    public SelenideElement ipv6landhcpserver = $("#ipv6_lanSetup_dhcp");
    public SelenideElement ipv6lanautoconfig = $("#ipv6_lanSetup_auto");
    public SelenideElement ipv6enableuseinterfaceid = $("#useInterfaceId");
    public SelenideElement ipv6laninterfaceid1 = $("#IPv6_interface_id1");
    public SelenideElement ipv6laninterfaceid2 = $("#IPv6_interface_id2");
    public SelenideElement ipv6laninterfaceid3 = $("#IPv6_interface_id3");
    public SelenideElement ipv6laninterfaceid4 = $("#IPv6_interface_id4");
    public SelenideElement ipv6autoconfiglanip2  = $x("//*[@id=\"root\"]/div/div/div[2]/div/div[2]/div/div/div/div/div/div/div/div[3]/form[3]/div[1]/p/p[1]");
   
    public SelenideElement ipv6filtersecured = $("#ipv6_filtering_Secured");
    public SelenideElement ipv6filteropen = $("#ipv6_filtering_Open");
  
}
