package orbi.webelements;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;
public class OrbiAdvancedmDNSElements {

    public SelenideElement enablemDNS   = $x("//input[@id='enable_mdns']");
    public String          enablemDNSid = "enable_mdns";
    public SelenideElement addbtn   = $x("//*[@id='add_black' or @id='add_profile']");
    public SelenideElement applybtn   = $x("//*[@id='apply']");
  
    public SelenideElement policytable   = $x("//*[@id='vlan_profile_table']");
  
    public SelenideElement policynameinput   = $x("//*[@id='vlan_profile_table']//*[contains(@class,'port_table')][last()]//input[1]");
    public SelenideElement sharedservicesselect   = $x("//*[@id='vlan_profile_table']//*[contains(@class,'port_table')][last()]//select[contains(@id, 'service')]");
    public SelenideElement srcvlanselect   = $x("//*[@id='vlan_profile_table']//*[contains(@class,'port_table')][last()]//select[contains(@id, 'so')]");
    public SelenideElement dstvlanselect   = $x("//*[@id='vlan_profile_table']//*[contains(@class,'port_table')][last()]//select[contains(@id, 'des')]");
    
    
    
    public SelenideElement policynameinputsxk50   = $x("//*[@id='vlan_profile_table']//tr[last()]//input[1]");
    public SelenideElement sharedservicesselectsxk50   = $x("//*[@id='vlan_profile_table']//tr[last()]//select[contains(@id, 'service')]");
    public SelenideElement srcvlanselectsxk50   = $x("//*[@id='vlan_profile_table']//tr[last()]//select[contains(@id, 'so')]");
    public SelenideElement dstvlanselectsxk50   = $x("//*[@id='vlan_profile_table']//tr[last()]//select[contains(@id, 'des')]");
    
}
