package orbi.webelements;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;
public class OrbiBasicWirelessElements {

    public SelenideElement wireless1   = $x("//*[@id='tab1' or @id='tab-0']"); // Change for compatibility
    public SelenideElement wireless2   = $x("//*[@id='tab2' or @id='tab-1']"); // Change for compatibility
    public SelenideElement wireless3   = $x("//*[@id='tab3' or @id='tab-2']"); // Change for compatibility
    public SelenideElement wireless1cancelbutton  = $("#cancel");
    public SelenideElement wireless1applybutton   = $("#apply");
    public SelenideElement wireless1ssidname   = $("#ssid");
    public SelenideElement wireless124gchannel = $("#wireless_channel");
    public SelenideElement wireless15gchannel  = $("#wireless_channel_an");
    
    public String          wireless1securitynoneid = "security_disable";
    public String          wireless1wpa2aesid = "security_wpa2";
    public String          wireless1tkipandaesid = "security_auto";
    public String          wireless1wpaenterpriseid = "security_wpae";
    public String          wireless1wpa3enterpriseid = "security_wpa3e";
    public String          wireless1wpa2wpa3id = "security_wpa2ewpa3";
    public SelenideElement wireless1securitynone   = $("#security_disable");
    public SelenideElement wireless1wpa2aes = $("#security_wpa2");
    public SelenideElement wireless1tkipandaes  = $("#security_auto");
    public SelenideElement wireless1wpaenterprise  = $x("//input[@value='WPAE']"); //$("#security_wpae");
    public SelenideElement wireless1wpa3sae       = $x("//input[@value='WPA3']");
    public SelenideElement wireless1wpa3enterprise  = $("#security_wpa3e");
    public SelenideElement wireless1wpa2wpa3  = $("#security_wpa2wpa3");
    
    public SelenideElement wireless1password  = $("#passphrase");
    
    public SelenideElement wireless2cancelbutton  = $("#cancel");
    public SelenideElement wireless2applybutton   = $("#apply");
    public SelenideElement wireless2enablessid = $("#enable_ssid_broadcast");
    public SelenideElement wireless2ssidname   = $("#ssid");
    public SelenideElement wireless224gchannel = $("#wireless_channel");
    public SelenideElement wireless25gchannel  = $("#wireless_channel_an");
    
    public String          wireless2securitynoneid = "security_disable";
    public String          wireless2wpa2aesid = "security_wpa2";
    public String          wireless2tkipandaesid = "security_auto";
    public String          wireless2wpaenterpriseid = "security_wpae";
    public SelenideElement wireless2securitynone   = $("#security_disable");
    public SelenideElement wireless2wpa2aes = $("#security_wpa2");
    public SelenideElement wireless2tkipandaes  = $("#security_auto");
    public SelenideElement wireless2wpaenterprise  = $("#security_wpae");
    
    
    public SelenideElement wireless2password  = $x("//*[@id='passphrase' or @id='wpa2_passphrase']"); // Change for compatibility
    public SelenideElement wireless2passsword5 = $x("//*[@id='passphrase_an' or @id='wpa2_passphrase_an']"); // Change for compatibility
    
    public SelenideElement wireless2enable   =$("#enable_byod");
    public String          wireless2enableid = "enable_byod";
    public SelenideElement wireless2ssidseperation =$("#enable_separate");
    public String          wireless2ssidseperationid = "enable_separate";
    public SelenideElement wireless2ssid24g =$("#ssid");
    public SelenideElement wireless2ssi5g =$("#ssid_an");
    public SelenideElement wireless2vlan =$x("//*[@id='enable_vlan_profile']"); // Change for compatibility
    public String          wireless2vlanId = "enable_vlan_profile";
    public SelenideElement wireless2vlanid =$("#wlan_vlan_profile_list");
    
    public String          wireless2securitynone5gid = "security_disable_an";
    public String          wireless2wpa2aes5gid = "security_wpa2_an";
    public String          wireless2tkipandaes5gid = "security_auto_an";
    public String          wireless2wpaenterprise5gid = "security_wpae_an";
    public SelenideElement wireless2securitynone5g   = $("#security_disable_an");
    public SelenideElement wireless2wpa2aes5g = $("#security_wpa2_an");
    public SelenideElement wireless2tkipandaes5g  = $("#security_auto_an");
    public SelenideElement wireless2wpaenterprise5g  = $("#security_wpae_an");
    
    public SelenideElement wireless3cancelbutton  = $("#cancel");
    public SelenideElement wireless3applybutton   = $("#apply");
    public SelenideElement wireless3enablessid = $("#enable_ssid_broadcast");
    public SelenideElement wireless3ssidname   = $("#ssid");
    public SelenideElement wireless324gchannel = $("#wireless_channel");
    public SelenideElement wireless35gchannel  = $("#wireless_channel_an");
    
    public String          wireless3securitynoneid = "security_disable";
    public String          wireless3wpa2aesid = "security_wpa2";
    public String          wireless3tkipandaesid = "security_auto";
    public String          wireless3wpaenterpriseid = "security_wpae";
    public SelenideElement wireless3securitynone   = $("#security_disable");
    public SelenideElement wireless3wpa2aes = $("#security_wpa2");
    public SelenideElement wireless3tkipandaes  = $("#security_auto");
    public SelenideElement wireless3wpaenterprise  = $("#security_wpae");
    
    public SelenideElement wireless3passsword5 = $x("//*[@id='passphrase_an' or @id='wpa2_passphrase_an']"); // Change for compatibility
    public SelenideElement wireless3password  = $x("//*[@id='passphrase' or @id='wpa2_passphrase']"); // Change for compatibility
    public SelenideElement wireless3enable  = $("#enable_iot");
    public String          wireless3enableid = "enable_iot";
    public SelenideElement wireless3ssidseperation =$("#enable_separate");
    public String          wireless3ssidseperationid = "enable_separate";
    public SelenideElement wireless3ssid24g =$("#ssid");
    public SelenideElement wireless3ssi5g =$("#ssid_an");
    
    //public SelenideElement channel24gcontent = $("#auto");
    public SelenideElement channel24gcontent = $("#wireless_channel"); // Change for compatibility
    public SelenideElement channel5gcontent  =$("#wireless_channel_an");
    public SelenideElement channel52gcontent  =$x("//*[@id='wireless_channel_an_2']");
    
    public String          wireless3securitynone5gid = "security_disable_an";
    public String          wireless3wpa2aes5gid = "security_wpa2_an";
    public String          wireless3tkipandaes5gid = "security_auto_an";
    public String          wireless3wpaenterprise5gid = "security_wpae_an";
    public SelenideElement wireless3securitynone5g   = $("#security_disable_an");
    public SelenideElement wireless3wpa2aes5g = $("#security_wpa2_an");
    public SelenideElement wireless3tkipandaes5g  = $("#security_auto_an");
    public SelenideElement wireless3wpaenterprise5g  = $("#security_wpae_an");
    
    public SelenideElement wirelessapplypercentage  = $x("//*[text()='Please wait a moment...']"); // Change for compatibility
    
    public SelenideElement radiusIp1  = $x("//*[@id='radiusSerIP1']");
    public SelenideElement radiusIp2  = $x("//*[@id='radiusSerIP2']");
    public SelenideElement radiusIp3  = $x("//*[@id='radiusSerIP3']");
    public SelenideElement radiusIp4  = $x("//*[@id='radiusSerIP4']");
    
    public SelenideElement radiusSecret  = $x("//*[@id='radiusSerSecret']");
    
    //Wifi Schedule
    public SelenideElement wireless_schedule = $x("//*[@id='wireless_schedule']");
    public SelenideElement mon_time  = $x("//*[@id='mon_time']");
    public SelenideElement tue_time  = $x("//*[@id='tue_time']");
    public SelenideElement wed_time  = $x("//*[@id='wed_time']");
    public SelenideElement thu_time  = $x("//*[@id='thu_time']");
    public SelenideElement fri_time  = $x("//*[@id='fri_time']");
    public SelenideElement sat_time  = $x("//*[@id='sat_time']");
    public SelenideElement sun_time  = $x("//*[@id='sun_time']");
    
    public SelenideElement mon_enable  = $x("//*[@id='mon-enabled']");
    public SelenideElement tue_enable  = $x("//*[@id='tue-enabled']");
    public SelenideElement wed_enable  = $x("//*[@id='wed-enabled']");
    public SelenideElement thu_enable  = $x("//*[@id='thu-enabled']");
    public SelenideElement fri_enable  = $x("//*[@id='fri-enabled']");
    public SelenideElement sat_enable  = $x("//*[@id='sat-enabled']");
    public SelenideElement sun_enable  = $x("//*[@id='sun-enabled']");
}
