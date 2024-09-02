package businessrouter.webelements;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;

public class BrFirewallAccessControlElements {
    public SelenideElement accesscontrolonoryes   = $("#access_control_show_1");
    public SelenideElement accesscontrolallow   = $("#access_control_show_3");
    public SelenideElement accesscontrolblock   = $("#access_control_show_4");
    public SelenideElement accesscontrolallowbutton  = $("#access_control_show_5");
    public SelenideElement accesscontrolblockbutton   = $("#access_control_show_6");
    public SelenideElement accesscontroleditbutton   = $("#access_control_show_7");
    
    public SelenideElement accesscontrolalloworblock = $("/html/body/div[2]/div/div[2]/div/div[1]/div[1]/div[2]/div/span/div/div/div");
    public SelenideElement accesscontrolrefreshbutton   = $("#access_control_show_8");
    public SelenideElement accesscontrolcancelbutton   = $("#access_control_show_21");
    public SelenideElement accesscontrolapplybutton   = $("#access_control_show_22");
    public SelenideElement accesscontrolselectall   = $x("//*[@id=\"access_control_show_9\"]/div/div/table/thead/tr/th[1]/span/div/label/span/input");
    public SelenideElement accesscontrolselectone   = $x("//input[@ class='ant-checkbox-input']");
    public SelenideElement accesscontrolalloweddevice = $x("//*[@id=\"access_control_show_11\"]/div[1]");
    public SelenideElement accesscontrolallowedremovedbutton = $("#access_control_show_12");
    public SelenideElement accesscontrolallowedaddbutton = $("#access_control_show_13");
    public SelenideElement accesscontrolallowededitbutton = $("#access_control_show_14");
    
    public SelenideElement accesscontrolblockdevice = $x("//*[@id=\"access_control_show_16\"]/div[1]");
    public SelenideElement accesscontrolblockremovedbutton = $("#access_control_show_17");
    public SelenideElement accesscontrolblockaddbutton = $("#access_control_show_18");
    public SelenideElement accesscontrolblockeditbutton = $("#access_control_show_19");
    public SelenideElement accesscontrolblockdevicename = $("#dev_name");
    public SelenideElement accesscontrolblockdevicemac = $("#mac_addr");
    
    
    public SelenideElement accesscontrolconnecteddevlist = $x("//tr[@class ='ant-table-row  ant-table-row-level-0']");
    public SelenideElement accesscontrolallownotconnectremovebutton = $("#access_control_show_12");
    public SelenideElement accesscontrolallownotconnectokbutton = $x("//button[@class = 'ant-btn ant-btn-primary ant-btn-sm']");
    
    public SelenideElement accesscontrolconnectstatus  = $x("//div[@class = 'ant-select-selection__rendered']");
    public SelenideElement accesscontrolconnectstatusallow  = $x("//li[text()='Allow']");
    public SelenideElement accesscontrolconnectstatusblock  = $x("//li[text()='Block']");
    public SelenideElement accesscontrolconnecteditdevicename  = $("#dev_name");
    public SelenideElement accesscontrolconnecteditapplybutton = $(" #operator_apply");
    
    public SelenideElement accesscontrollistallownotconnect = $x("(//div[@class = 'ant-collapse-header'])[1]");
    
    public SelenideElement accesscontrollistblocknotconnect = $x("(//div[@class = 'ant-collapse-header'])[2]");
    
    public SelenideElement accesscontrollokbutton = $x("(//button[@class = 'ant-btn ant-btn-primary'])[2]");
    
    
    
  
}


