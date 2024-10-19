package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.executeJavaScript;

import java.util.Map;
import java.util.logging.Logger;
import org.openqa.selenium.Keys;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.ButtonElements;
import webportal.webelements.DeviceGroupElement;
import java.io.*;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeviceGroupPage extends DeviceGroupElement {
    Logger logger = Logger.getLogger("DeviceGroupPage");
    boolean result = false;
    public DeviceGroupPage() {
        logger.info("init...");
    }
    
    
    public void GoToDeviceGroup(String locationName) {

        if (locationName(locationName).exists()) {
            editNetwork(locationName);
            MyCommonAPIs.sleepi(3);
        }
        DeviceGroup.click();
        MyCommonAPIs.sleepi(5);
     }
    
    public void GoToSysLog(String locationName) {
        boolean result = false;
        if (locationName(locationName).exists()) {
            editNetwork(locationName);
            MyCommonAPIs.sleepi(3);
        }
        MyCommonAPIs.sleepi(5);
        SysLog.click();

     }
    
    public void GoToLED(String locationName) {
        boolean result = false;
        if (locationName(locationName).exists()) {
            editNetwork(locationName);
            MyCommonAPIs.sleepi(3);
        }
        LedSettings.click();
        MyCommonAPIs.sleepi(5);
     }
    
    public void GoToSNMP(String locationName) {
        boolean result = false;
        if (locationName(locationName).exists()) {
            editNetwork(locationName);
            MyCommonAPIs.sleepi(3);
        }
        SNMP.click();
        MyCommonAPIs.sleepi(5);
     }
    public void EnableSysLog(String IP, String Port) {
        enbleSysLog.click();
        MyCommonAPIs.sleepi(3);
        SysLogIP.clear();
        SysLogIP.sendKeys(IP);
        MyCommonAPIs.sleepi(3);
        SysLogPort.clear();
        SysLogPort.sendKeys(Port);
        MyCommonAPIs.sleepi(3);
        Apply.click();
        MyCommonAPIs.sleepi(3);
        okSys.click();

     }
    
    public void EnableSNMP(String IP, String CString) {
        MyCommonAPIs.sleepi(5);
        enbleSNMP.click();
        MyCommonAPIs.sleepi(5);
        SNMPIP.clear();
        SNMPIP.sendKeys(IP);
        SNMPCString.clear();
        SNMPCString.sendKeys(CString);
        SaveSNMP.click();
        MyCommonAPIs.sleepi(5);
        OKSNMP.click();
        MyCommonAPIs.sleepi(5);
     }
    
    public void SetLED(int value) {
        MyCommonAPIs.sleepi(5);
        String current_value = SetLed.getText();
        logger.info("current value---" + current_value);
        SetLed.click();
        MyCommonAPIs.sleepi(5);
        if(value == 0) {           
            SetDefault.click();
        }else if(value == 1){
            String off = SetOff.getText();
            logger.info(off);
            SetOff.click();
            if(!off.equals(current_value))
            {
                MyCommonAPIs.sleepi(5);
                Apply.click();
                MyCommonAPIs.sleepi(5);
                Ok.click();
                MyCommonAPIs.sleepi(5);
            }
        }else if(value == 1){
            String textdefault = SetDefault.getText();
            logger.info(textdefault);
            SetDefault.click();
            if(!textdefault.equals(current_value))
            {
                MyCommonAPIs.sleepi(5);
                Apply.click();
                MyCommonAPIs.sleepi(5);
                Ok.click();
                MyCommonAPIs.sleepi(5);
            }
        }else if(value == 2){
           String textexcept = SetExcept.getText();
           logger.info("Except value ---> " + textexcept);
           SetExcept.click();
           if(!textexcept.equals(current_value))
           {
               logger.info("except Selected");
               MyCommonAPIs.sleepi(5);
               Apply.click();
               MyCommonAPIs.sleepi(5);
               Ok.click();
               MyCommonAPIs.sleepi(5);
           }
        
        }
     }
    public void disableSysLog() {
        MyCommonAPIs.sleepi(5);
        enbleSysLog.click();     
        MyCommonAPIs.sleepi(5);
        Apply.click();
     }
    
    public void disableSNMP() {
        MyCommonAPIs.sleepi(5);
        ClearSNMP.click();     
        MyCommonAPIs.sleepi(5);
        ClearSNMPagain.click();
        MyCommonAPIs.sleepi(5);
        OKSNMP.click();
        MyCommonAPIs.sleepi(5);
     }
    
    public boolean DeviceGroupExits(String locationName) {
        boolean result = false;
        if (locationName(locationName).exists()) {
            editNetwork(locationName);
            MyCommonAPIs.sleepi(3);
        }
        if(!DeviceGroup.isDisplayed()) {
            result = true;
        }
        return result;
     }
    
    public boolean DeviceGroupNameisAsked() {
        boolean result = false;
        AddDeviceGroup.click();
        waitElement(AddDeviceGroupName);
        if(AddDeviceGroupName.isDisplayed() && AddDeviceGroupDescription.isDisplayed()) {
            result = true;
        }
        MyCommonAPIs.sleepi(3);
        closeDeviceGroup.click();
        return result;
     }
    
    public boolean DeviceGroupButtons() {
        boolean result = false;
        AddDeviceGroup.click();
        waitElement(AddDeviceGroupName);
        MyCommonAPIs.sleepi(3);
        if(CreateDeviceGroupbutton.exists() && CancelDeviceGroupbutton.exists()) {
            result = true;
        }
        MyCommonAPIs.sleepi(3);
        closeDeviceGroup.click();
        return result;
     }
    
    public boolean DeviceGroupcross() {
        boolean result = false;
        AddDeviceGroup.click();
        waitElement(AddDeviceGroupName);
        MyCommonAPIs.sleepi(3);
        if(closeDeviceGroup.exists()) {
            result = true;
        }
        MyCommonAPIs.sleepi(3);
        closeDeviceGroup.click();
        return result;
     }
    
    public String DeviceGroupNameValidation(String Name, String Des) {
        String result= "";
        AddDeviceGroup.click();
        waitElement(AddDeviceGroupName);
        AddDeviceGroupName.clear();
        AddDeviceGroupName.sendKeys(Name);
        MyCommonAPIs.sleepi(2);
        AddDeviceGroupDescription.clear();
        AddDeviceGroupDescription.sendKeys(Des);
        CreateDeviceGroupbutton.click();
        MyCommonAPIs.sleepi(2);
        if(errorText.exists()) {
        result = errorText.getText();
        }else {
            result = Sucessmessage.getText();
        }
        if(closeDeviceGroup.isDisplayed()) {
            closeDeviceGroup.click();
        }
        return result;
     }
    
    public String CreateDGGroup(String Name, String Des) {
        String result= "";
        AddDeviceGroup.click();
        waitElement(AddDeviceGroupName);
        AddDeviceGroupName.clear();
        AddDeviceGroupName.sendKeys(Name);
        MyCommonAPIs.sleepi(2);
        AddDeviceGroupDescription.clear();
        AddDeviceGroupDescription.sendKeys(Des);
        CreateDeviceGroupbutton.click();
        MyCommonAPIs.sleepi(2);
        if(errorText.exists()) {
        result = errorText.getText();
        }else {
            result = Sucessmessage.getText();
        }
        if(closeDeviceGroup.isDisplayed()) {
            closeDeviceGroup.click();
        }
        return result;
        
     }
    
    public boolean CGclounm() {
        boolean result = false;
        if(CGDescription.isDisplayed() && CGDevices.isDisplayed() && CGOnline.isDisplayed() && CGonboarded.isDisplayed()) {
            result = true;          
        }
        return result;
     }
    
    
    public void deleteDG(String CGName) {
      if (checkCGexits(CGName)) {
          logger.info("Delete CG.");
          executeJavaScript("arguments[0].removeAttribute('class')",editCG(CGName));
          MyCommonAPIs.sleep(3000);
          deleteCG(CGName).waitUntil(Condition.visible, 60 * 1000).click();
          MyCommonAPIs.sleep(6000);
          deletessidyes.click();
          MyCommonAPIs.sleep(5 * 1000);
      }
  }
    
    public boolean checkCGexits(String CGName) {
        boolean result = false;
        String sElement = String.format("//td[text()='%s']", CGName);
        logger.info("on element:" + sElement);
        if ($x(sElement).exists()) {
            result = true;
            logger.info("Ssid:" + CGName + " is existed.");
        }
        return result;
    }
    
    
    
    public void deleteCGGroupALL() {
        waitReady();
        MyCommonAPIs.sleepi(5);
        while (CGexits.exists()) {
            logger.info("entered delete loop");
            String CGnamerecord = grabCGName.getText();
            logger.info("no ssid to delete");
            deleteDG(CGnamerecord);
            waitReady();
        }
    }
    
    public boolean deleteDGPopup(String CGName) {
        boolean result = false;
        if (checkCGexits(CGName)) {
            logger.info("Delete CG.");
            executeJavaScript("arguments[0].removeAttribute('class')",editCG(CGName));
            MyCommonAPIs.sleep(3000);
            deleteCG(CGName).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleep(6000);
            
            if(deletessidyes.isDisplayed()) {
                deletessidyes.click();
                result = true;
            }
         
            MyCommonAPIs.sleep(5 * 1000);
        }
        return result;
    }
    
    
    public void addNewDevice(Map<String, String> map, String GroupName) {
        WebCheck.checkHrefIcon(URLParam.hrefDevices);
        if (devicesName(map.get("Serial Number")).exists())
            return;
        checkAddDevice(map,  GroupName);
    }
    
    public boolean addNewDevicedropdown(Map<String, String> map, String GroupName) {
        boolean result = false;
        WebCheck.checkHrefIcon(URLParam.hrefDevices);
        if (devicesName(map.get("Serial Number")).exists())   
            logger.info("entering device");
        result = checkAddDevicedropdown(map,  GroupName);
        return result;
    }
    
    public void clickAddDevice() {
        MyCommonAPIs.sleepi(10);
        logger.info("checking  add device");
        if (addDevice1.exists()) {
            addDevice1.click();
        } 
        
        if (addDevice2.exists()) {
            addDevice3.click();
        }
        else {
            addDevice2.click();
        }
            MyCommonAPIs.sleepi(10);
            if (addSingleDevice.exists()) {
                logger.info("checking  single device");
                addSingleDevice.click();
            }
        
    }
    
    public void checkAddDevice(Map<String, String> map, String GroupName) {
        boolean result = true;
        for (String ss : map.keySet()) {
            logger.info(ss + ": " + map.get(ss));
        }
        
        clickAddDevice();
        waitElement(goDeviceBtn);
        serialNo.sendKeys(map.get("Serial Number"));
        MyCommonAPIs.sleepi(3);
        goDeviceBtn.click();
        MyCommonAPIs.sleepi(3);
        DGGroup.selectOption(GroupName);
        if (map.get("MAC Address1")!=null) {
            macAddress.sendKeys(map.get("MAC Address1"));
        }else {
            macAddress.sendKeys(map.get("MAC Address"));
        }
        waitElementNot(goDeviceBtn);
    
        clickBoxLastButton();  
        if (viewDevices.exists()) {
            viewDevices.click();
        }
    }
    
    
    public boolean checkAddDevicedropdown(Map<String, String> map, String GroupName) {
        boolean result = true;
        for (String ss : map.keySet()) {
            logger.info(ss + ": " + map.get(ss));
        }
        logger.info("entering add device");
        clickAddDevice();
        waitElement(goDeviceBtn);
        serialNo.sendKeys(map.get("Serial Number"));
        MyCommonAPIs.sleepi(3);
        goDeviceBtn.click();
        MyCommonAPIs.sleepi(3);
        if(DGGroup.isDisplayed()) {
             result = true;
        }
        DGGroup.selectOption(GroupName);
        if (map.get("MAC Address1")!=null) {
            macAddress.sendKeys(map.get("MAC Address1"));
        }else {
            macAddress.sendKeys(map.get("MAC Address"));
        }
//        waitElementNot(goDeviceBtn);
        MyCommonAPIs.sleepi(10);
        next.click();
    
//       clickBoxLastButton();  
//        if (viewDevices.exists()) {
//            viewDevices.click();
//        }
        
        return result;
    }
    
    public void goinsideDG(String CGName) {
        if (checkCGexits(CGName)) {
            logger.info("Delete CG.");
            executeJavaScript("arguments[0].removeAttribute('class')",editCG(CGName));
            MyCommonAPIs.sleep(3000);
            enterCG(CGName).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleep(6000);
        }
    }
    
    public void DeleteDeviceDG(String Serial) {
        MyCommonAPIs.sleepi(10);
        if (checkCGexits(Serial)) {
            logger.info("Delete device CG.");
            executeJavaScript("arguments[0].removeAttribute('class')",editDeviceTabCG(Serial));
            MyCommonAPIs.sleep(3000);
            DeleteDeviceCG(Serial).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleep(6000);
            if(deletessidyes.isDisplayed()) {
                deletessidyes.click();
                result = true;
            }
        }
    }
    
    public boolean DeleteDeviceDGpopUpCheck(String Serial) {
        
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if (checkCGexits(Serial)) {
            logger.info("Delete device CG.");
            executeJavaScript("arguments[0].removeAttribute('class')",editDeviceTabCG(Serial));
            MyCommonAPIs.sleep(3000);
            DeleteDeviceCG(Serial).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleep(6000);
            if(deletessidyes.isDisplayed()) {
                result = true;
                deletessidyes.click();
            }
        }
        
        return result;
    }
    
    
    public boolean editdisDG(String CGName) {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        EditDGGeneral.clear();
        EditDGGeneral.sendKeys(CGName);
        SaveSNMP.click();
        MyCommonAPIs.sleepi(5);
        String text = EditDGGeneral.getText();
//        System.out.println(text);
        if(text.equals(CGName)) {
            result= true;
//            System.out.println("text edited");
            
        }
        
        return result;
        
    }
    
    
    public boolean editNameDG() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        if(EditDGGeneralName.getAttribute("readOnly").equals("true")) {
            result= true;
//            System.out.println("text  not edited ");
            
        }
      
        return result;
        
    }
    
    public boolean DGMenu() {
        boolean result = false;
        
        DGDevicelist.click();
        MyCommonAPIs.sleepi(2);
        DGWiFi.click();
        MyCommonAPIs.sleepi(2);
        DGGeneral.click();
        MyCommonAPIs.sleepi(2);
        DGProperties.click();
        MyCommonAPIs.sleepi(2);
        DGWiFiSchedules.click();
        MyCommonAPIs.sleepi(2);
        DGFastRoaming.click();
        MyCommonAPIs.sleepi(2);
        DGLoadBalancing.click();
        MyCommonAPIs.sleepi(2);
        DGVLANNetworkSetup.click();
        MyCommonAPIs.sleepi(2);
        DGAdvanced.click();
        MyCommonAPIs.sleepi(2);
        if(DGDevicelist.isDisplayed() & DGWiFi.isDisplayed()& DGGeneral.isDisplayed() & DGGeneral.isDisplayed() & DGProperties.isDisplayed() & DGWiFiSchedules.isDisplayed() & DGFastRoaming.isDisplayed() 
                & DGLoadBalancing.isDisplayed() & DGVLANNetworkSetup.isDisplayed() & DGAdvanced.isDisplayed() ) {
            result= true;
//            System.out.println("text  not edited ");
            
        }
      
        return result;
        
    }
    
    public boolean veifySummary() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        String Notnull = TotalAP.getText();
//        System.out.println(Notnull);
        if(Notnull!= "null" && Properties.isDisplayed() && SystemStatus.isDisplayed()) {
            result= true;
//            System.out.println("text  not edited ");
            
        }
      
        return result;
        
    }
    public boolean DeviceListCG(String SerialNo) {
        boolean result = false;
        MyCommonAPIs.sleepi(5);

        if(devicesList(SerialNo).isDisplayed()) {
            result= true;
//            System.out.println("Device exits ");
            
        }
      
        return result;
        
    }
    
    public boolean GenaralTab() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);

        if(EditDGGeneral.isDisplayed() && EditDGGeneralName.isDisplayed()) {
            result= true;
//            System.out.println("Genaral  exits ");
            
        }
      
        return result;
        
    }
    
    
    public boolean OpenGUI(String IPaddess) {
        boolean result = false;
        String oldTitle = Selenide.title();
//        System.out.println(oldTitle);
//        System.out.println("old was printed");
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
        open("https:" + IPaddess +" ");
        String NewTitle = Selenide.title();
//        System.out.println(NewTitle);
        MyCommonAPIs.sleepi(10);
        UserName.sendKeys("admin");
        Password.sendKeys("Netgear1@");
        Login.click();
        MyCommonAPIs.sleepi(10);
        Management.click();
        MyCommonAPIs.sleepi(5);
        Managementok.click();
        MyCommonAPIs.sleepi(5);
        Monitaring.click();
        MyCommonAPIs.sleepi(5);
        System.click();
        String antena = Antenna.getText();
//        System.out.println(antena);
        if(antena.equals("1x1")) {
            result = true;
//            System.out.println("antena is 1 cross 1");

        }
        return result;
    }
        
        public void taredown() {
            String oldTitle = Selenide.title();
//            System.out.println(oldTitle);
//            System.out.println("old was printed");
            Selenide.clearBrowserCookies();
            Selenide.clearBrowserLocalStorage();
            open(WebportalParam.serverUrlLogin);
            String NewTitle = Selenide.title();           
    }
        
        public boolean CheckNotification(String Name) {
            boolean result= false;
            NotificationBell.click();
            String bell  = NotificationMes.getText();
            if(bell.contains("User New has created a device group named Netgear1")) {
                result = true;
            }
            return result;
            
         }
        
        public boolean ShareNotification(String Name) {
            boolean result= false;
            NotificationBell.click();
            MyCommonAPIs.sleepi(3);
            SelectAll.click();
            MyCommonAPIs.sleepi(5);
            SelectEnable.click();
            MyCommonAPIs.sleepi(2);
            SelectOe.click();
            MyCommonAPIs.sleepi(1);
            Share.click();
            MyCommonAPIs.sleepi(5);
            Email.sendKeys("tvishwanath@netgear.com");
            Send.click();
            
            MyCommonAPIs.sleepi(3);
            if(SucessEmail.isDisplayed()) {
                result = true;
            }
            return result;
            
            
            
         }
        
        public boolean DelNotification(String Name) {
            boolean result= false;
            NotificationBell.click();
            MyCommonAPIs.sleepi(3);
            SelectAll.click();
            MyCommonAPIs.sleepi(5);
            SelectEnable.click();
            MyCommonAPIs.sleepi(2);
            SelectOe.click();
            MyCommonAPIs.sleepi(1);
            Deletebutton.click();
            String Mes = SucessNoti.getText();
           logger.info(Mes);
            MyCommonAPIs.sleepi(3);
            if(Mes.contains("Notification deleted.")) {
                result = true;
            }
            return result;
            
         }
        
        public boolean addNewDeviceOrg(Map<String, String> map, String GroupName, String LocName) {
            boolean result = false;   
                logger.info("entering device");
            result = checkAddDeviceOrg(map,  GroupName, LocName);
            return result;
        }
        
        public boolean checkAddDeviceOrg(Map<String, String> map, String GroupName, String Loc) {
            boolean result = true;
            for (String ss : map.keySet()) {
                logger.info(ss + ": " + map.get(ss));
            }
            logger.info("entering add device");
            clickAddDevice();
            logger.info("exit from  single device");
            MyCommonAPIs.sleepi(3);
            LocName.selectOption(Loc);
            waitElement(goDeviceBtn);
            serialNo.sendKeys(map.get("Serial Number"));
            MyCommonAPIs.sleepi(3);
            goDeviceBtn.click();
            MyCommonAPIs.sleepi(3);
            if(DGGroup.isDisplayed()) {
                 result = true;
            }
            DGGroup.selectOption(GroupName);
            if (map.get("MAC Address1")!=null) {
                macAddress.sendKeys(map.get("MAC Address1"));
            }else {
                macAddress.sendKeys(map.get("MAC Address"));
            }
            waitElementNot(goDeviceBtn);
        
            clickBoxLastButton();  
            if (viewDevices.exists()) {
                viewDevices.click();
            }
            
            return result;
        }
        
        public boolean AddDeviceNonDGDevices(Map<String, String> map, String GroupName) {
            boolean result = true;
            for (String ss : map.keySet()) {
                logger.info(ss + ": " + map.get(ss));
            }
            logger.info("entering add device");
            clickAddDevice();
            waitElement(goDeviceBtn);
            serialNo.sendKeys(map.get("Serial Number"));
            MyCommonAPIs.sleepi(3);
            goDeviceBtn.click();
            MyCommonAPIs.sleepi(3);
            if(!DGGroup.isDisplayed()) {
                 result = true;
            }
           
            
            return result;
        }
        
        public boolean NoCGGroup(Map<String, String> map, String GroupName) {
            boolean result = false;
            WebCheck.checkHrefIcon(URLParam.hrefDevices);
            if (devicesName(map.get("Serial Number")).exists())   
                logger.info("entering device");
            logger.info("entering add device");
            clickAddDevice();
            waitElement(goDeviceBtn);
            serialNo.sendKeys(map.get("Serial Number"));
            MyCommonAPIs.sleepi(3);
            goDeviceBtn.click();
            MyCommonAPIs.sleepi(3);
            
            if(!DGGroup.isDisplayed()) {
                result = true;
           }
           
            close.click();
            return result;
        }
        
        public boolean DeviceFilter( String GroupName, String SerialNo) {
            boolean result = false;
            MyCommonAPIs.sleepi(3);
            WirelessFilter.click();
            MyCommonAPIs.sleepi(3);
            Fileter(GroupName).click();
            
            if(ClientWidges(SerialNo).isDisplayed() && TrafficWidges(SerialNo).isDisplayed() && SidedateClient.isDisplayed() && Sidedatetraffic.isDisplayed())
            {
                result = true;
            }
            
            
            return result;
        }
        
        
        public boolean checkssid() {
            boolean result = false;
            while (ssidlistone.exists()) {
                logger.info("entered delete loop");
                result = true;
            }
                        
            return result;
        }
}
