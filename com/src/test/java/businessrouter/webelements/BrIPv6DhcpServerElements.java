package businessrouter.webelements;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;
public class BrIPv6DhcpServerElements {

    public SelenideElement networklink  =  $("#mm1");
    public SelenideElement lansettinglink  =  $("#sm5");
    public SelenideElement ipv6page = $x("//input[@class ='toggleRadio']");
    public SelenideElement laninterfaceipv6addr  =  $("#tblLanSetupIpAddr");
    public SelenideElement laninterfaceipv6addrlen  =  $("#txtPreLen");
    public SelenideElement ipv6dhcpstatus  =  $("#selDhcpStatus");
    public SelenideElement ipv6dhcpmode  =  $("#selDhcpMode");
    public SelenideElement ipv6dnsserver  =  $("#selDnsSvrs");
    public SelenideElement ipv6prifexdelegation  =  $("#chkbPrefixDeleGation");
    public SelenideElement ipv6applybutton  =  $x("//input[@name ='button.config.dhcpv6s.lanConfigIpv6']");
    public SelenideElement ipv6addprifexbutton  =  $x("( //input[@class ='submit submit3 bAdd'])[2]");
    public SelenideElement ipv6selectallprifexbutton  =  $x("(//input[@class = 'submit submit3 bSelectall'])[2]");
    public SelenideElement ipv6deleteprifexbutton  =  $x("(//input[@class = 'submit submit3 bDelete'])[2]");
    public SelenideElement ipv6delegationprifex  =  $("#txtPrefix");
    public SelenideElement ipv6delegationprifexlen  =  $("#txtIpv6PreLen");
    public SelenideElement ipv6delegationprifexapply  =  $x("//input[@name ='button.config.dhcpv6sLANPrefixPool.lanConfigIpv6.-1']");
    
    public SelenideElement radvdlink  =  $x("//div[@class ='floatRight tabRightLink']");
    public SelenideElement radvdstatus = $("#selRadvdStatus");
    public SelenideElement advertismode = $("#selAdvtMode");
    public SelenideElement raflag = $("#selRAflag");
    public SelenideElement routerprefernce = $("#routerPreference");
    public SelenideElement radvdapplybutton = $x("//input[@name = 'button.config.radvd.radvd_config']");
    public SelenideElement radvdprifexaddbutton = $("#add");
    public SelenideElement radvdprifexselectallbutton = $x(" //input[@class ='submit submit3 bSelectall']");
    public SelenideElement radvdprifexdeletebutton = $("#btDelete");
    
    public SelenideElement ipv6prefixtype = $("#selPrefixType");
    public SelenideElement ipv6prefix = $("#txtPrefix");
    public SelenideElement ipv6prefixlen = $("#txtIpv6PreLen");
    public SelenideElement ipv6prefixlifetime = $("#txtPrefLifeTime");
    
    public SelenideElement ipv6prefixapplybutton = $x("//input[@name ='button.config.radvdLANPrefixPool.radvd_config.-1']");
}
