package businessrouter.webelements;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;

public class BrFirewallBasicSetupElements {
    
    public SelenideElement firewallbasicportscandospro   = $("#firewall_setup_3");
    public SelenideElement firewallbasicdefaultdmz   = $("#firewall_setup_4");
    public SelenideElement firewallbasicdefaultdmzip1   = $("#firewall_setup_5");
    public SelenideElement firewallbasicdefaultdmzip2   = $("#firewall_setup_6");
    public SelenideElement firewallbasicdefaultdmzip3   = $("#firewall_setup_7");
    public SelenideElement firewallbasicdefaultdmzip4   = $("#firewall_setup_8");
    public SelenideElement firewallbasicrespingintel   = $("#firewall_setup_9");
    public SelenideElement firewallbasicigmpproxy   = $("#firewall_setup_1");
    public SelenideElement firewallbasicmtusize   = $("#firewall_setup_10");
    public SelenideElement firewallbasicnatfiltersecured  = $("#firewall_setup_12");
    public SelenideElement firewallbasicnatfilteropen  = $("#firewall_setup_13");
    public SelenideElement firewallbasicsipalg  = $("#firewall_setup_14");
    public SelenideElement firewallbasicapply = $("#firewall_setup_16");
    public SelenideElement firewallmtuwarning = $x("//*[@id=\"root\"]/div/div/div[2]/div/div[2]/div/div/div/div/div/div/div/form/div[5]/div/div/div[2]/div/div");

}
