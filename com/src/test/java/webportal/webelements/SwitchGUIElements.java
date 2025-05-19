package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.$$x;

import java.util.logging.Logger;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

public class SwitchGUIElements extends MyCommonAPIs {
    protected Logger logger = Logger.getLogger("DeviceBRSummaryElement");
    
    //Managed Switch Elements
    
    //Main tabs
    
    public SelenideElement System          = $x("//*[@aid=\"tab_System\"]");
    public SelenideElement Switching       = $x("//*[@aid=\"tab_Switching\"]");
    public SelenideElement Routing         = $x("//*[@aid=\"tab_Routing\"]");
    public SelenideElement QoS             = $x("//*[@aid=\"tab_QoS\"]");
    public SelenideElement Security        = $x("//*[@aid=\"tab_Security\"]");
    public SelenideElement Monitoring      = $x("//*[@aid=\"tab_Monitoring\"]");
    public SelenideElement Maintenance     = $x("//*[@aid=\"tab_Maintenance\"]");
    public SelenideElement Help            = $x("//*[@aid=\"tab_Help\"]");
    public SelenideElement Index           = $x("//*[@aid=\"tab_Index\"]");
    
    //Sub under switching
    

    public SelenideElement Ports                    = $x("//*[@aid=\"Ports\"]");
    public SelenideElement PortDescription          = $x("//*[@aid=\"lvl1_PortDescription\"]");
    public SelenideElement DescriptionText          = $x("//*[@rownumber=\"0\"]/../../td[3]/input[2]");
    public SelenideElement Apply                    = $x("//*[text()=\"Apply\"]");
    public SelenideElement adminMG                  = $x("//*[@rownumber=\"0\"]/../../td[6]/select");
    public SelenideElement Services                 = $x("//*[@aid=\"Services\"]");
    public SelenideElement DisbaleAdmin             = $x("//td[text()='Admin Mode']/following-sibling::td//input[@type='radio' and @value='Disable']");
    public SelenideElement EnableAdmin              = $x("//td[text()='Admin Mode']/following-sibling::td//input[@type='radio' and @value='Enable']");    
    public SelenideElement VLANIDMN                 = $x("//*[text()=\"VLAN ID\"]/../../tr[2]/td[2]/input[2]");
    public SelenideElement VLANName                 = $x("//*[text()=\"VLAN ID\"]/../../tr[2]/td[3]/input[2]");
    public ElementsCollection VLANAdd               = $$x("//*[text()=\"Add\"]");    
    public SelenideElement MainUILogin              = $x("//*[text() = 'Main UI Login']");
    public SelenideElement Newpassword              = $x("//*[@name=\"newPassword\"]");
    public SelenideElement Confirmpassword              = $x("//*[@name=\"confirmNewPassword\"]");
    
    public SelenideElement SelectPort(String portNumber) {   
      return $x("//*[text() = '"+portNumber+"']/../td[1]/input");        
    }
    
    public SelenideElement selectVLANM(String VLANName) {        
        return $x("//*[text() = '"+VLANName+"']/../td/input");  
      }
    
      
    //SMart Switch Elements
    
    public SelenideElement SelectPortSM(String portNumber) {        
        return $x("//*[text() = '"+portNumber+"']/../td/label");  
      }
     public ElementsCollection SwitchingSM                = $$x("//*[text() =\"Switching\"]");     
     public ElementsCollection Apply1                     = $$x("//*[text()=\"Apply\"]");
     public ElementsCollection PortsSM                    = $$x("//*[text() =\"Ports\"]");
     public SelenideElement VLANSM                        = $x("//*[text()=\"VLAN\"]");
     public SelenideElement AddVLANSM                     = $x("//*[@id=\"btn-addNew\"]");
     public SelenideElement VLANIDSM                      = $x("//*[@id=\"vlan\"]");
     public SelenideElement VLANNameSM                    = $x("//*[@id=\"name\"]");
     public SelenideElement SaveSM                        = $x("//*[text()=\"Save\"]");
     
     
     public SelenideElement DHCPServicesSM                    = $x("//*[text() =\"DHCP Services\"]");
     public SelenideElement SystemSM                          = $x("//*[text() =\"System\"]");
     public SelenideElement DHCPSnooping                      = $x("//*[text() =\"DHCP Snooping\"]");
     public SelenideElement GlobalNavigation                  = $x("//*[text() =\"Global Configuration\"]");
     public SelenideElement ESnoopingMode                     = $x("//label[.//*[@id=\"toggle_state\" and @name=\"admin\"]]");
     public SelenideElement SnoopingModeStstus                = $x("//*[@id='toggle_state' and @name='admin']");
     public SelenideElement MACAddress                        = $x("//label[.//*[@id=\"toggle_state\" and @name=\"MacAddress\"]]");
     public SelenideElement MACAddressStstus                  = $x("//*[@id='toggle_state' and @name='MacAddress']");
     
     public SelenideElement Edit                   = $x("//*[text()=\"Edit\"]");     
     public SelenideElement PortConfiguration      = $x("//*[text()='Port Configuration']");      
     public SelenideElement SkipRegistration       = $x("//*[contains(text(), 'Skip Registration')]/ancestor::div[1]//following::img[contains(@class, 'menu-filter') or @id='skip']");
     public SelenideElement DeviceAdminPassword    = $x("//*[@id=\"password\"]");
     public SelenideElement locallogin             = $x("//*[@id=\"local_login\"]");
     public SelenideElement DeviceStatusInInsight  = $x("//*[text() ='Insight']/../span[2]");
     public SelenideElement PortDescriptionSM      = $x("//*[@id=\"descp\"]");
     public SelenideElement Save                   = $x("//*[text()=\"Save\"]");
     public SelenideElement admin                  = $x("//*[@id=\"admin\"]");
     public SelenideElement EditVLAN               = $x("//*[@id=\"btn-edit\"]");
     public SelenideElement newPassword            = $x("//*[@id=\"newPwdUI\"]");
     public SelenideElement cfmPassword            = $x("//*[@id=\"cfmPwdUI\"]");
     public SelenideElement search                 = $x("//*[@id=\"search\"]");
     public SelenideElement telnet                 = $x("//*[text()=\"Telnet\"]");
     public SelenideElement checktelnet            = $x("//*[@id=\"toggle_state\"]");
     public SelenideElement enabletelnet           = $x("//*[@id=\"span_slider\"]");
     public SelenideElement ApplySM                = $x("//*[@id=\"btn_Apply\"]");
     
     public SelenideElement selectVLAN(String VLANName) {        
         return $x("//*[text()='"+VLANName+"']/../td/label");  
       }
      
      
  }

