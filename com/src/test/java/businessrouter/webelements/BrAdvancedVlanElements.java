package businessrouter.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;

public class BrAdvancedVlanElements {
    public SelenideElement vlanaddbutton           = $("#vlan_list_1");
    public SelenideElement vlandeletebutton        = $("#vlan_list_2");
    public SelenideElement vlanrefreshbutton       = $("#vlan_list_3");
    public SelenideElement vlanid                  = $("#addVlan_PanelView_1");
    public SelenideElement vlanname                = $("#addVlan_PanelView_2");
    public SelenideElement vlanlanport1            = $("#addVlan_PanelView_6_checkbox_0");
    public SelenideElement vlanlanport2            = $("#addVlan_PanelView_6_checkbox_1");
    public SelenideElement vlanlanport3            = $("#addVlan_PanelView_6_checkbox_2");
    public SelenideElement vlanlanport4            = $("#addVlan_PanelView_6_checkbox_3");
    public SelenideElement vlanwan                 = $("#addVlan_PanelView_6_checkbox_4");
    public SelenideElement vlanlanporttagvalue     = $x("//div[@class='ant-select-dropdown ant-select-dropdown--single ant-select-dropdown-placement-bottomLeft']//li[text()='TAG']");
    public SelenideElement vlanlanportuntagvalue   = $x("//div[@class='ant-select-dropdown ant-select-dropdown--single ant-select-dropdown-placement-bottomLeft']//li[text()='UNTAG']");
    public SelenideElement vlanlanporttaguntag1    = $x("(//div[@class ='ant-select-selection__rendered'])[1]");
    public SelenideElement vlanlanport1tagvalue    = $x("(//li[text()='TAG'])[1]");
    public SelenideElement vlanlanport1untagvalue  = $x("(//li[text()='UNTAG'])[1]");
    public SelenideElement vlanlanporttaguntag2    = $x("(//div[@class ='ant-select-selection__rendered'])[2]");
    public SelenideElement vlanlanport2tagvalue    = $x("//li[text()='TAG'])[2]");
    public SelenideElement vlanlanport2untagvalue  = $x("(//li[text()='UNTAG'])[2]");
    public SelenideElement vlanlanporttaguntag3    = $x("(//div[@class ='ant-select-selection__rendered'])[3]");
    public SelenideElement vlanlanport3tagvalue    = $x("(//li[text()='TAG'])[3]");
    public SelenideElement vlanlanport3untagvalue  = $x("(//li[text()='UNTAG'])[3]");
    public SelenideElement vlanlanporttaguntag4    = $x("(//div[@class ='ant-select-selection__rendered'])[4]");
    public SelenideElement vlanlanport4tagvalue    = $x("(//li[text()='TAG'])[4]");
    public SelenideElement vlanlanport4untagvalue  = $x("(//li[text()='UNTAG'])[4]");
    public SelenideElement vlandescription         = $("#addVlan_PanelView_3");
    public SelenideElement vlanaddvlancancelbutton = $("#addVlan_PanelView_4");
    public SelenideElement vlanaddvlanapplybutton  = $("#addVlan_PanelView_5");
    public SelenideElement vlanselectall           = $x("(//input[@type =\"checkbox\"])[1]");
    public SelenideElement vlanselectone           = $x("//input[@type =\"checkbox\"][#ex#]");
    public SelenideElement vlaneditbutton          = $("#vlan_list_4_");
    public SelenideElement vlandeletebutton2       = $x("//*[@id=\"vlan_list_4_#ex#_3\"]");
    public SelenideElement vlanport                = $x(
            "//*[@id=\"root\"]/div/div/div[2]/div/div[2]/div/div/div/div/div/div/div/div/div[1]/div/div/div/div/div[3]");
    public SelenideElement vlanportpvid            = $x("//*[@id=\"vlan_ports_1\"]/div/div/table/tbody/tr[#ex#]/td[2]/div/div/div]");
    public SelenideElement vlanportcancelbutton    = $("#vlan_ports_2");
    public SelenideElement vlanportapplybutton     = $("#vlan_ports_3");
    public SelenideElement vlantablevlanid         = $x(
            "//tr[@class ='ant-table-row  ant-table-row-level-0']/td[@class ='ant-table-column-has-filters'][2]");
    public SelenideElement vlantablelist           = $x("//tr[@class =\"ant-table-row  ant-table-row-level-0\"]");
    public SelenideElement vlanokbutton            = $x("//*[@id=\"vlan_list_2_popDelete\"]/div/div/div[2]/button[2]");
    public SelenideElement Checkboxlocation        = $x("(//input[@type ='checkbox'])");
    
    public SelenideElement br100vlanlanport1            = $("#addVlan_PanelView_6_checkbox_3");
    public SelenideElement br100vlanlanport2            = $("#addVlan_PanelView_6_checkbox_4");

}
