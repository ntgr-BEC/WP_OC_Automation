package businessrouter.webelements;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;
public class BrBasicWanSetupElements {
    public SelenideElement internetconnectionreqyes   = $("#loginreq_radio_pppoe");
    public SelenideElement internetconnectionreqno  = $("#loginreq_radio_dhcp");
    public SelenideElement devicenameeditbutton   = $("#editDeviceName_btn");
    public SelenideElement devicename   = $("#deviceName");
    public SelenideElement devicenamewarning =$x("//div[@class ='ant-form-explain']");
    public SelenideElement devicenameeditapplybutton   = $("#editDeviceName_apply_btn");
    public SelenideElement devicenameeditcancelbutton   = $("#editDeviceName_cancel_btn");
    public SelenideElement domainname   = $("#domain_name");
    public SelenideElement ipautomatic   = $("#WANAssign_radio_dhcp");
    public SelenideElement ipstatic      = $("#WANAssign_radio_static");
    public SelenideElement wanipaddress1   = $("#WPethr1");
    public SelenideElement wanipaddress2   = $("#WPethr2");
    public SelenideElement wanipaddress3   = $("#WPethr3");
    public SelenideElement wanipaddress4   = $("#WPethr4");
    public SelenideElement wanipsubmask1   = $("#WMask1");
    public SelenideElement wanipsubmask2   = $("#WMask2");
    public SelenideElement wanipsubmask3   = $("#WMask3");
    public SelenideElement wanipsubmask4   = $("#WMask4");
    public SelenideElement wanipgateway1   = $("#WGateway1");
    public SelenideElement wanipgateway2   = $("#WGateway2");
    public SelenideElement wanipgateway3   = $("#WGateway3");
    public SelenideElement wanipgateway4   = $("#WGateway4");
    public SelenideElement dnsautomatic    = $("#DNSAssign_radio_0");
    public SelenideElement dnsstatic       = $("#DNSAssign_radio_1");
    public SelenideElement wanprimarydnsaddress1   = $("#DAddr1");
    public SelenideElement wanprimarydnsaddress2   = $("#DAddr2");
    public SelenideElement wanprimarydnsaddress3   = $("#DAddr3");
    public SelenideElement wanprimarydnsaddress4   = $("#DAddr4");
    public SelenideElement wansecondarydnsaddress1   = $("#PDAddr1");
    public SelenideElement wansecondarydnsaddress2   = $("#PDAddr2");
    public SelenideElement wansecondarydnsaddress3   = $("#PDAddr3");
    public SelenideElement wansecondarydnsaddress4   = $("#PDAddr4");
    public SelenideElement wanthirddnsaddress1   = $("#TDAddr1");
    public SelenideElement wanthirddnsaddress2   = $("#TDAddr2");
    public SelenideElement wanthirddnsaddress3   = $("#TDAddr3");
    public SelenideElement wanthirddnsaddress4   = $("#TDAddr4");
    public SelenideElement wanusedefaultmac   = $x("//*[@id=\"MACAssign_radio\"]/div/div[1]/label/span[1]/input");
    public SelenideElement wanusecomputermac   = $x("//*[@id=\"MACAssign_radio\"]/div/div[2]/label/span[1]/input");
    public SelenideElement wanusecustomerdefinedmac   = $x("//*[@id=\"MACAssign_radio\"]/div/div[3]/label/span[1]/input");
    public SelenideElement wancustomerdefinedmac   = $("#Spoofmac");
    public SelenideElement wantestbutton   = $("#test_btn");
    public SelenideElement wancancelbutton   = $("#cancel_btn");
    public SelenideElement wanapplybutton   = $("#apply_btn");
    public SelenideElement connectionmode = $x("(//div [@role ='combobox'])[2]");
    public SelenideElement connectionmodealwayson = $x("//li[text() ='Always On']");
    public SelenideElement connectionmodedialondemand = $x("//li[text()='Dial on Demand']");
    public SelenideElement connectionmodemanualconnect = $x(" //li[text() ='Manually Connect']");
    public SelenideElement idletimeout = $("#pppoe_idletime");
    public SelenideElement interservicetype = $x("//div[@class ='ant-select-selection__rendered']");
    public SelenideElement interservicetypepppoe = $x("//li[text() ='PPPoE']");
    public SelenideElement interservicetypepptp = $x("//li[text() ='PPTP']");
    public SelenideElement interservicetypel2tp = $x("//li[text()='L2TP']");
    public SelenideElement interpppoeusername = $("#pppoe_username");
    public SelenideElement interpptpusername = $("#pptp_username");
    public SelenideElement interl2tpusername = $("#l2tp_username");
    public SelenideElement interpppoepassword = $("#pppoe_passwd");
    public SelenideElement interpptppassword = $("#pptp_passwd");
    public SelenideElement interl2tppassword = $("#l2tp_passwd");
    public SelenideElement pptpidletime  = $("#pptp_idletime");
    public SelenideElement l2tppidletime  = $("#l2tp_idletime");
    public SelenideElement intermyip1 = $("#myip_1");
    public SelenideElement intermyip2 = $("#myip_2");
    public SelenideElement intermyip3 = $("#myip_3");
    public SelenideElement intermyip4 = $("#myip_4");
    
    public SelenideElement interserverip1 = $("#servip_1");
    public SelenideElement interserverip2 = $("#servip_2");
    public SelenideElement interserverip3 = $("#servip_3");
    public SelenideElement interserverip4 = $("#servip_4");
    
    public SelenideElement intermyipmask1 = $("#mymask_1");
    public SelenideElement intermyipmask2 = $("#mymask_2");
    public SelenideElement intermyipmask3 = $("#mymask_3");
    public SelenideElement intermyipmask4 = $("#mymask_4");
    
    public SelenideElement intermyipgateway1 = $("#mygw_1");
    public SelenideElement intermyipgateway2 = $("#mygw_2");
    public SelenideElement intermyipgateway3 = $("#mygw_3");
    public SelenideElement intermyipgateway4 = $("#mygw_4");
    
    public SelenideElement interisdnsmode  = $("#DNSAssign_radio_0");
    
    

    
}
