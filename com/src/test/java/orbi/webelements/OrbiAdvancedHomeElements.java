package orbi.webelements;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;
public class OrbiAdvancedHomeElements {

    public SelenideElement rebootbutton       = $("#reboot");
    //public SelenideElement yesbutton          = $x("//*[@id=\"main\"]/table/tbody/tr[3]/td/input[1]"); 
    public SelenideElement yesbutton          = $x("//*[@id='yes' or @name='yes']"); // Change for compatibility
    public SelenideElement routerinfortabal   = $("#router_info");
    public SelenideElement hardwareversion    = $x("//*[@id=\"router_info\"]//b[text()='Hardware Version']/../../td[2]"); // Change for compatibility
    public SelenideElement firmwareversion    = $x("//*[@id=\"router_info\"]//b[text()='Firmware Version']/../../td[2]"); // Change for compatibility
    public SelenideElement operationmode      = $x("//*[@id=\"router_info\"]//b[text()='Operation Mode']/../../td[2]"); // Change for compatibility
    public SelenideElement langateway         = $x("//*[@id=\"ap6\"]/td[2]");
    public SelenideElement internetwanip      = $x("//*[@id='internet_info']//b[text()='IP Address']/../../td[2]"); // Fit for old and new arch
    public SelenideElement g24channel         = $x("//*[@id='wireless_general']//b[text()='2.4G Channel']/../../td[2]"); // Change for compatibility
    public SelenideElement g5channel          = $x("//*[@id='wireless_general']//b[text()='5G Channel']/../../td[2]"); // Change for compatibility
    public SelenideElement g52channel         = $x("//*[@id='wireless_general']//b[text()='5G-2 Channel']/../../td[2]"); // Change for compatibility
    public SelenideElement routerinfomac      = $x("//*[@id='ap4']/td[2]");
    
    public SelenideElement wireless1ssid                  = $x("//*[@id='wireless' or @id='wireless_an']//b[text()='Name (SSID)']/../../td[2]/span"); // Change for compatibility
    public SelenideElement wireless1ssidsxk50             = $x("//*[@id='wireless_an']/tr/td[2]/span"); // 
    public SelenideElement wireless1broadcasename         = $x("//*[@id='wireless' or @id='wireless_an']//b[text()='Broadcast Name']/../../td[2]"); // Change for compatibility
    public SelenideElement wireless1ap                    = $x("//*[@id='wireless' or @id='wireless_an']//b[text()='Wireless AP']/../../td[2]"); // Change for compatibility
    
    public SelenideElement wireless2ssid24g                  = $x("(//*[@id=\"byod_wlanssid\"]/td[2])[1]");
    public SelenideElement wireless2ssid5g                   = $x("//*[@id='byod_network']//b[text()='Name(SSID 5GHz)']/../../td[2]"); // Change for compatibility
    public SelenideElement wireless2broadcasename         = $x("//*[@id='byod_network']//b[text()='Broadcast Name']/../../td[2]"); // Change for compatibility
   // public SelenideElement wireless2ap                    = $x("//*[@id=\"byod_wlanap\"]/td[2]");
    public SelenideElement wireless2ap                    = $x("//*[@id='byod_network']//b[text()='Wireless AP']/../../td[2]"); // Change for compatibility
    
    public SelenideElement wireless3ssid24g                  = $x("(//*[@id=\"iot_wlanssid\"]/td[2])[1]");
    public SelenideElement wireless3ssid5g                   = $x("//*[@id='iot_network']//b[text()='Name(SSID 5GHz)']/../../td[2]"); // Change for compatibility
    public SelenideElement wireless3broadcasename         = $x("//*[@id='iot_network']//b[text()='Broadcast Name']/../../td[2]"); // Change for compatibility
    public SelenideElement wireless3ap                    = $x("//*[@id='iot_network']//b[text()='Wireless AP']/../../td[2]"); // Change for compatibility
    
    public SelenideElement wirelessguestssid                  = $x("//*[@id='wlanssid' or @id='guest_wlanssid']/td[2]/span");
    public SelenideElement wirelessguestbroadcasename         = $x("//*[@id='guest_network']//*[text()='Broadcast Name']/../../td[2]"); // Fit for old and new arch
    public SelenideElement wirelessguestap                    = $x("//*[@id=\"wireless\"]/tbody/tr[6]/td[2]");
   
    public SelenideElement routerdata                   =$x("//*[@id='statistic_table']/tbody/tr[3]/td/table/tbody/tr[2]/td[3]/span");
    public SelenideElement routerdatanewsxk50           =$x("//*[@id='statistic_table']/tr[2]/td[3]/span");
    public SelenideElement showstatisticsbutton         =$("#show_statistics");
    public SelenideElement pollinterval                 =$("#timeset");
    public SelenideElement setintervalbutton            =$("#set_interval");
    public SelenideElement stopintervalbutton           =$("#stop");
    public SelenideElement portWAN                      =$x("//span[text()='WAN']");
    public SelenideElement portLAN1                     =$x("//span[contains(text(),'LAN') and contains(text(),'1')]"); // Change for compatibility
    public SelenideElement portLAN2                     =$x("//span[contains(text(),'LAN') and contains(text(),'2')]"); // Change for compatibility
    public SelenideElement portLAN3                     =$x("//span[contains(text(),'LAN') and contains(text(),'3')]"); // Change for compatibility
    public SelenideElement portLAN4                     =$x("//span[text()='LAN4']");
    public SelenideElement wlanbgn                      =$("#wlan_info");
    public SelenideElement wlananac                     =$x("//span[text()='WLAN a/n/ac']");
    public SelenideElement wlananax                     =$x("//span[text()='WLAN a/n/ax']");
    
    public SelenideElement wlanbackhaul                 =$("#backhaul_info");
    public SelenideElement lan1status                   =$x("//span[contains(text(),'LAN') and contains(text(),'1')]/../../td[2]/span");
    
    public SelenideElement connectstatusbutton           =$("#conn_status");
    public SelenideElement disconnectbutton              =$("#disconnect");
    public SelenideElement connectbutton                 =$("#connect");
    public SelenideElement wanip                         =$x("//*[@id='pptp_connect']//tr[2]/td[2]"); // Change for compatibility
    public SelenideElement closebutton                   =$("#close");
    
}
