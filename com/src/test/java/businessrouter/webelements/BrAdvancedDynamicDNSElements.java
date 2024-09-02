package businessrouter.webelements;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;

public class BrAdvancedDynamicDNSElements {
    public SelenideElement ddnsenable   = $("#DNS_ddns_use");
    public SelenideElement ddnsserviceprovide   = $x("//*[@id=\"root\"]/div/div/div[2]/div/div[2]/div/div/div/div/div/div/div/div/div[2]/div/div/div/div");
    public SelenideElement ddnsaccountenable   = $("#DNS_ddns_yes");
    public SelenideElement ddnsaccountdisable  = $("#DNS_ddns_no");
    public SelenideElement ddnsaccounthostname  = $("#DNS_ddns_hostName");
    public SelenideElement ddnsaccountemail  = $("#DNS_ddns_email");
    public SelenideElement ddnsaccountpassword  = $("#DNS_ddns_password");
    public SelenideElement ddnsregisterbutton  = $("#DNS_ddns_register");
    public SelenideElement ddnsshowstatusbutton  = $("#DNS_ddns_showStatus");
    public SelenideElement ddnscancelbutton  = $("#DNS_ddns_cancel");
    public SelenideElement ddnsapplybutton  = $("#DNS_ddns_apply");
    public SelenideElement ddensresetbutton = $("#DNS_ddns_reset");
    public SelenideElement ddnsstatus = $x("//div[@class ='ant-card-body']");
}
