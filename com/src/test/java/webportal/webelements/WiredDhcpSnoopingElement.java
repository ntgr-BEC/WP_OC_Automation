package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.collections4.ListUtils;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.CommonDataType;
import webportal.param.CommonDataType.CustomAccessManagment;
import webportal.param.WebportalParam;

public class WiredDhcpSnoopingElement extends MyCommonAPIs {

    final static Logger logger = Logger.getLogger("WiredDhcpSnoopingElement");

    // DhcpSnooping

    public static SelenideElement checkDhcpSnooping                = $x("//div[@id='divSideBarSecEditVlan']/..//a[text()='DHCP Snooping']");
    public static SelenideElement checkDhcpGlobalConfigPage        = $x("//div[@id= 'dhcpSnnopingGlobalConfig']");
    public static SelenideElement dhcpSnoopingGlobalConfig         = $x("//a[text()='DHCP Snooping']/..//a[text()='Global Configuration']");
    public static SelenideElement dhcpSnoopingPortConfig           = $x("//a[text()='DHCP Snooping']/..//a[text()='Port Configuration']");
    public static SelenideElement dhcpSnoopingMode                 = $x("//h5[text()='DHCP Snooping Mode']/..//span");
    public static SelenideElement dhcpSnoopingMACAddressValidation = $x("//h5[text()='MAC Address Validation']/..//span");
    public static SelenideElement saveGlobalConfiguration          = $x("//button[@id='saveEditVlan']");
    
    public static SelenideElement dhcpSnoopingModebutton          = $x("//h5[text()='DHCP Snooping Mode']/../label/input");
    public static SelenideElement selectUserVlanbutton(String vlanId) {
        return $x(String.format("//td[text()='%s']/../td[2]/label/input", vlanId));   
    }
    public static SelenideElement dhcpSnoopingMACAddressValidationbutton = $x("//h5[text()='MAC Address Validation']/../label/input");

    /**
     * 
     * @param vlanId:
     *            Vlan Id which we want to select from the page
     * @return path for the required vlanId
     */
    public static SelenideElement selectUserVlan(String vlanId) {
        return $x(String.format("//td[text()='%s']/..//span", vlanId));

    }

    public static ElementsCollection allPorts() {
        return $$x(String.format("//h3/span[text()='%s']/../..//i[@class='icon icon-ethernet']/../span[@class='ethernet-count']", WebportalParam.sw1deveiceName));
        }


    public static SelenideElement checkSelectedOrNot(String portNo) {
        return $x(String.format("//i[@id='iconChckSwitchPortsGroupPrt%s']/..//i[@class='icon icon-check']", portNo));
    }



    public static SelenideElement txtPortTrustModeCheck(String portNo) {
        return $x(String.format("//*[text()='%s' and @id='spnEtherNetPortsGroupPrt1']/../span[2]", portNo));
    }

    public static SelenideElement txtPortInvalidPacketsCheck(String portNo) {
        return $x(String.format("//*[text()='%s' and @id='spnEtherNetPortsGroupPrt1']/../span[3]", portNo));
    }

    public boolean isEnable(SelenideElement el) {
        return el.isSelected();
    }

    /**
     * 
     * @param button:
     *            The button which you want from the list
     *            String Inputs: "Trust Mode", "Invalid Packets", "Delete", "Select All", "Deselect All"
     * @return Button Xpath
     */

    public static SelenideElement selectButton(String button) {
        return $x(String.format("//button[text()='%s']", button));
    }

    public static String switchUIXpath = "//h3/span[text()='%s']";

    public static String getSwitchXpath(String deviceName) {
        return String.format(switchUIXpath, deviceName);
    }
    /**
     * 
     * @param portNo:
     *            please provide the port number from 0 to n-1
     * @return It will take the given port number Xpath from DUT-1
     */
    public static SelenideElement txtPortSelection(String deviceName, String portNo) {
        SelenideElement srcport = $x(
                String.format("%s/../..//ul[@id='ulSwitchModlGroupPrt0']/..//span[text()='%s']", getSwitchXpath(deviceName), portNo));
      
        return srcport;
    }
    
    public static SelenideElement buttonSelection(String deviceName, String text) {
        SelenideElement srcport = $x(

                String.format("%s/../..//following-sibling::div/div[2]/button[text()='%s']", getSwitchXpath(deviceName),text));
        return srcport;
    }

}
