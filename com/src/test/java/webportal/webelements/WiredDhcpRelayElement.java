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

public class WiredDhcpRelayElement extends MyCommonAPIs {
    
    final static Logger          logger         = Logger.getLogger("WiredDhcpRelayElement");
    
    public static SelenideElement checkDhcpL2Relay = $x("//div[@id='divSideBarSecEditVlan']/..//a[text()='DHCP L2 Relay']");
    
    // DhcpRelay Global Config page
    public static SelenideElement dhcpL2RelayGlobalConfig = $x("//a[text()='DHCP L2 Relay']/..//a[text()='Global Configuration']");
    public static SelenideElement enableGlobalConfigAdminMode    = $x("//h5[text()='Admin Mode']/..//span");
    public static SelenideElement saveGlobalConfiguration = $x("//button[@id='saveEditVlan']");
    public static SelenideElement dhcpRelayGlobalConfigUserVlanEnableButton = $x("//td[text()='data vlan']/..//span");
    
    // DhcpRelay port Coniguration Page 
    public static SelenideElement checkDhcpL2RelayPortConfig = $x("//div[@id='button_header']/..//div[text()='Port Configuration']");
    public static SelenideElement dhcpL2RelayPortConfig   = $x("//a[text()='DHCP L2 Relay']/..//a[text()='Port Configuration']");
    public static SelenideElement dhcpRelayPortConfigAdminMode = $x("//button[text()='Admin Mode']");
    public static SelenideElement dhcpRelayPortConfigTrustMode = $x("//button[text()='82 Option Trust Mode']");
    public static SelenideElement dhcpRelayPortConfigSaveButton = $x("//button[@id='saveEditVlan']");
    public static SelenideElement deletePortConfig = $x("//button[text()='Delete']");
    public static SelenideElement selectAllButton = $x("//button[text()='Select All']");
    public static SelenideElement deselectAllButton = $x("//button[text()='Deselect All']");
    
    
    public static ElementsCollection allSelectChecks              = $$x("//span[@class='ethernet-count']/../../../li[@class = 'portSelected']");
    public static ElementsCollection allPorts              = $$x("//span[@class='ethernet-count']/../i[@class='icon icon-check']");
    public static ElementsCollection allAdminModes         = $$x("//span[@class='icon-L1 Sright']");
    
    public static SelenideElement checkSelected = $x("//i[@class='icon icon-check']");
    
    
    /**
     * 
     * @param portNo: please provide the port number from 0 to n-1
     * @return  It will take the given port number Xpath
     */
    public static SelenideElement txtPortSelection(String portNo) {
        System.out.println("port number is"+portNo);
        return $x(String.format("//li[@id='device_0_port_%s']", portNo));
    }
       
    public static SelenideElement txtPortSelectionnext(String portNo) {
        return $x(String.format("(//div[@id='divSwitchPortsGroupPrt%s'])[2]", portNo));
    }
    
    public static SelenideElement txtPortAdminModeCheck(String portNo) {
        return $x(String.format("(//li[@id='device_0_port_%s']//span[@id='spnEtherNetPortsjuhiii9GroupPrt0'])[1]", portNo, portNo));
    }
    
    public static SelenideElement txtPortTrustModeCheck(String portNo) {
        return $x(String.format("(//li[@id='device_0_port_%s']//span[@id='spnEtherNetPortsjuhiii9GroupPrt1'])[2]", portNo, portNo));
    }
    
    public static boolean checkPortSelectedOrNot(String portNo) {
        return $x(String.format("//div[@id='divSwitchPortsGroupPrt%s']/../i[@class='icon icon-check']", portNo)).isDisplayed();
        
    }
    public static boolean checkPortSelectedOrNotnext(String portNo) {
        return $x(String.format("(//div[@id='divSwitchPortsGroupPrt%s']/../i[@class='icon icon-check'])[2]", portNo)).isDisplayed();
        
    }
    
    
    public boolean isEnable(SelenideElement el) {
        return el.isSelected();
    }
    /**
     * 
     * @param vlanId: Vlan Id which we want to select from the page
     * @return path for the required vlanId
     */
    public static SelenideElement selectUserVlan(String vlanId) {
        return $x(String.format("//td[text()='%s']/..//span", vlanId));
        
    }
    
  
}