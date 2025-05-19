package webportal.weboperation;
import java.awt.Robot;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DeviceBulkOnboardingElement;
import webportal.webelements.SwitchGUIElements;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.*;
import static java.lang.System.*;
import java.lang.*;
import org.openqa.selenium.Keys;



public class SwitchGUIPage extends SwitchGUIElements {

    WebDriver driver = WebDriverRunner.getWebDriver();
    public SwitchGUIPage() {
        logger.info("init...");
    }
    
    public void openLocalGUI(String ip, String passwd, String Model) {
        String url;
        if(Model.contains("M4250")) {
         url  = "http://"+ ip+":49151";
        }else {
           url = "http://"+ ip;
        }
        ((JavascriptExecutor) driver).executeScript("window.open('" + url + "', '_blank');");
        Selenide.switchTo().window(1);
        String currentURL = new MyCommonAPIs().getCurrentUrl();
        MyCommonAPIs.sleepi(8);
        
        if(Model.contains("M4")) {
            PRXFirmwarePage.Switchname.sendKeys("admin");
            PRXFirmwarePage.Switchpassword.sendKeys(passwd);
            MyCommonAPIs.sleepi(3);
            PRXFirmwarePage.SwitchLogin.click();
        }else {            
            SkipRegistration.click();
            MyCommonAPIs.sleepi(2);
            DeviceAdminPassword.click();
            DeviceAdminPassword.sendKeys(passwd);
            locallogin.click();
        }       
    }

    public void openResetLocalGUI(String ip, String passwd, String Model) {
        String url;
        if(Model.contains("M4250")) {
            url  = "http://"+ ip+":49151";
        }else {
            url = "http://"+ ip;
        }
        ((JavascriptExecutor) driver).executeScript("window.open('" + url + "', '_blank');");
        Selenide.switchTo().window(1);
        String currentURL = new MyCommonAPIs().getCurrentUrl();
        MyCommonAPIs.sleepi(8);
        if(Model.contains("M4")) {
            MainUILogin.click();
            PRXFirmwarePage.Switchname.sendKeys("admin");
            MyCommonAPIs.sleepi(3);
            PRXFirmwarePage.SwitchLogin.click();
            MyCommonAPIs.sleepi(3);
            Newpassword.sendKeys(passwd);
            Confirmpassword.sendKeys(passwd);
            Apply.click();
            MyCommonAPIs.sleepi(3);            
            

        }else {
            SkipRegistration.click();
            MyCommonAPIs.sleepi(5);
            DeviceAdminPassword.click();
            DeviceAdminPassword.sendKeys("password");
            locallogin.click();
            newPassword.sendKeys(passwd);
            cfmPassword.sendKeys(passwd);
            locallogin.click();
        }
        MyCommonAPIs.sleepi(10);
        driver.close();
        Selenide.switchTo().window(0);

        if(Model.contains("M4250")) {
            url  = "http://"+ ip+":49151";
        }else {
            url = "http://"+ ip;
        }
        ((JavascriptExecutor) driver).executeScript("window.open('" + url + "', '_blank');");
        Selenide.switchTo().window(1);
        currentURL = new MyCommonAPIs().getCurrentUrl();
        MyCommonAPIs.sleepi(8);

        if(Model.contains("M4")) {
            PRXFirmwarePage.Switchname.sendKeys("admin");
            PRXFirmwarePage.Switchpassword.sendKeys(passwd);
            MyCommonAPIs.sleepi(3);
            PRXFirmwarePage.SwitchLogin.click();
        }else {
            SkipRegistration.click();
            MyCommonAPIs.sleepi(2);
            DeviceAdminPassword.click();
            DeviceAdminPassword.sendKeys(passwd);
            locallogin.click();
        }
        Selenide.switchTo().window(0);
    }
    
    public void gotoswitchportconfig(String Model, String Port, String descrip) {       
        MyCommonAPIs.sleepi(5);
        if(Model.contains("M4")) {
        Switching.click();
        Ports.click();      
        driver.switchTo().frame("maincontent");  // Since it has an id and name
        SelectPort(Port).click();
        DescriptionText.sendKeys(descrip);
        driver.switchTo().defaultContent();
        Apply.click();
        }else {
        new GenericMethods().checkVisibleElements(SwitchingSM);
        new GenericMethods().checkVisibleElements(PortsSM);
        PortConfiguration.click();
        MyCommonAPIs.sleepi(5);
        SelectPortSM(Port).click();
        Edit.click();
        PortDescriptionSM.click();
        PortDescriptionSM.sendKeys(descrip);
        Save.click();
        }
        Selenide.switchTo().window(0);
    }
   
    public void PORTAdmin(String Model, String Port, String Enable) {       
        MyCommonAPIs.sleepi(5);
        if(Model.contains("M4")) {
        Switching.click();
        Ports.click();      
        driver.switchTo().frame("maincontent");  // Since it has an id and name
        java.lang.System.out.println(Port);
        SelectPort(Port).click();
        adminMG.selectOption(Enable);
        driver.switchTo().defaultContent();
        Apply.click();
        }else {
        new GenericMethods().clickVisibleElements(SwitchingSM);
        new GenericMethods().clickVisibleElements(PortsSM);
        PortConfiguration.click();
        MyCommonAPIs.sleepi(10);
        SelectPortSM(Port).click();
        Edit.click();
        MyCommonAPIs.sleepi(10);
        String sdminstatus = admin.getText();
        
        java.lang.System.out.println("sdminstatus"+sdminstatus);
        
        if(Enable.contains("Enable")) {
            admin.selectOption("Enable");
        }else if(Enable.contains("Disable")) {
            admin.selectOption("Disable");
        }
       Save.click();
       
    }
        MyCommonAPIs.sleepi(30);
        Selenide.switchTo().window(0);
    }
    
    public void DHCPsnoopingmode(String Model, boolean ststus) {       
        if(Model.contains("M4")) {
            MyCommonAPIs.sleepi(3);
            Services.click();
            MyCommonAPIs.sleepi(3);
            driver.switchTo().frame("maincontent"); 
           
            if(ststus) {
                if(EnableAdmin.isSelected()) {
                    java.lang.System.out.println("Toggle is already enabled. No action needed.");
                }else {
                    EnableAdmin.click();
                }
            }else {
                if(DisbaleAdmin.isSelected()) {
                    java.lang.System.out.println("Toggle is already disabled. No action needed.");
                }else {                    
                    DisbaleAdmin.click();
                }
            }
            
            driver.switchTo().defaultContent();
            Apply.click();
            
        }else {
            SystemSM.click();
            MyCommonAPIs.sleepi(3);
            DHCPServicesSM.click();
            MyCommonAPIs.sleepi(3);
            DHCPSnooping.click();
            MyCommonAPIs.sleepi(3);
            GlobalNavigation.click();
            MyCommonAPIs.sleepi(3);
            java.lang.System.out.println("ststus"+ SnoopingModeStstus.isSelected());
            if(ststus) {
            if (SnoopingModeStstus.isSelected()) {
                java.lang.System.out.println("Toggle is already enabled. No action needed.");
            } else {
                java.lang.System.out.println("Toggle is disabled. Enabling it now...");
                ESnoopingMode.click();
            }
            }else {
                
                if (SnoopingModeStstus.isSelected()) {
                    java.lang.System.out.println("Toggle is  enabled. action needed.");
                    ESnoopingMode.click();
                } else {
                    java.lang.System.out.println("Toggle is disabled. No action needed...");
                    
                }
            }
                
            Selenide.executeJavaScript("arguments[0].click();", Apply);
      
        }
        MyCommonAPIs.sleepi(30);
        Selenide.switchTo().window(0);
    }
    
    
    public void MACAddressValidation(String Model, boolean ststus) {       
        if(Model.contains("M4")) {
            MyCommonAPIs.sleepi(3);
            Services.click();
            MyCommonAPIs.sleepi(3);
            driver.switchTo().frame("maincontent"); 
           
            if(ststus) {
                if(EnableAdmin.isSelected()) {
                    java.lang.System.out.println("Toggle is already enabled. No action needed.");
                }else {
                    EnableAdmin.click();
                }
            }else {
                if(DisbaleAdmin.isSelected()) {
                    java.lang.System.out.println("Toggle is already disabled. No action needed.");
                }else {                    
                    DisbaleAdmin.click();
                }
            }
            
            driver.switchTo().defaultContent();
            Apply.click();
            
        }else {
            SystemSM.click();
            MyCommonAPIs.sleepi(3);
            DHCPServicesSM.click();
            MyCommonAPIs.sleepi(3);
            DHCPSnooping.click();
            MyCommonAPIs.sleepi(3);
            GlobalNavigation.click();
            MyCommonAPIs.sleepi(3);
            java.lang.System.out.println("ststus"+ MACAddress.isSelected());
            if(ststus) {
            if (MACAddressStstus.isSelected()) {
                java.lang.System.out.println("Toggle is already enabled. No action needed.");
            } else {
                java.lang.System.out.println("Toggle is disabled. Enabling it now...");
                MACAddress.click();
            }
            }else {
                
                if (MACAddressStstus.isSelected()) {
                    java.lang.System.out.println("Toggle is  enabled. action needed.");
                    MACAddress.click();
                } else {
                    java.lang.System.out.println("Toggle is disabled. No action needed...");
                    
                }
            }
                
            Selenide.executeJavaScript("arguments[0].click();", Apply);
      
        }
        MyCommonAPIs.sleepi(30);
        Selenide.switchTo().window(0);
    }
    
    
    public void AddVLAN(String Model, String VLANID, String VLANNAME) {  
        
        if(Model.contains("M4")) {
            Switching.click();
            MyCommonAPIs.sleepi(3);
            driver.switchTo().frame("maincontent"); 
            MyCommonAPIs.sleepi(3);
            VLANIDMN.sendKeys(VLANID);
            VLANName.sendKeys(VLANNAME);
            driver.switchTo().defaultContent();
            MyCommonAPIs.sleepi(3);
            new GenericMethods().clickVisibleElements(VLANAdd);            
        }else {
            MyCommonAPIs.sleepi(5);
            new GenericMethods().clickVisibleElements(SwitchingSM);
            VLANSM.click();
            AddVLANSM.click();
            VLANIDSM.sendKeys(VLANID);        
            VLANNameSM.sendKeys(VLANNAME);
            SaveSM.click();
        }
        
        MyCommonAPIs.sleepi(30);
        
    }
    
    
public void EditVLAN(String Model, String VLANold, String VLANNAMENew) {          
        if(Model.contains("M4")) {
            Switching.click();
            MyCommonAPIs.sleepi(3);
            driver.switchTo().frame("maincontent"); 
            MyCommonAPIs.sleepi(3);
            selectVLANM(VLANold).click();                       
            VLANName.click();
            VLANName.clear();
            VLANName.sendKeys(VLANNAMENew);
            driver.switchTo().defaultContent();
            MyCommonAPIs.sleepi(3);
            new GenericMethods().clickVisibleElements(Apply1);        
        }else {
            MyCommonAPIs.sleepi(5);
            selectVLAN(VLANold).click();
            EditVLAN.click();
            MyCommonAPIs.sleepi(3);
            VLANNameSM.click();
            VLANNameSM.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            VLANNameSM.sendKeys(Keys.DELETE);
            MyCommonAPIs.sleepi(10);
            VLANNameSM.setValue(VLANNAMENew);
            MyCommonAPIs.sleepi(3);
            SaveSM.click();
        }
        
        MyCommonAPIs.sleepi(30);
        Selenide.switchTo().window(0);
    }

    public void EnableTelnet(String ip, String passwd, String Model) {
        String url;
        if(Model.contains("M4250")) {
         url  = "http://"+ ip+":49151";
        }else {
           url = "http://"+ ip;
        }
        ((JavascriptExecutor) driver).executeScript("window.open('" + url + "', '_blank');");
        Selenide.switchTo().window(1);
        String currentURL = new MyCommonAPIs().getCurrentUrl();
        MyCommonAPIs.sleepi(8);
        
        if(Model.contains("M4")) {
            java.lang.System.out.println("automatically enabled");
    }else {
        MyCommonAPIs.sleepi(8);
        SkipRegistration.click();
        MyCommonAPIs.sleepi(2);
        DeviceAdminPassword.click();
        DeviceAdminPassword.sendKeys(passwd);
        locallogin.click();
        MyCommonAPIs.sleepi(5);
        search.click();
        search.sendKeys("telnet");
        MyCommonAPIs.sleepi(2);
        telnet.click();
        MyCommonAPIs.sleepi(10);
        if(checktelnet.isEnabled()){
            java.lang.System.out.println("alredy enabled");
        }else {
            enabletelnet.click();
            ApplySM.click();
        }
        MyCommonAPIs.sleepi(10);
        
    }
        
        driver.close();
        Selenide.switchTo().window(0);
    }
}
