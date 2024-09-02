package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$x;

import java.time.LocalDate;
import java.util.logging.Logger;

import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.GlobalNotificationElement;

public class GlobalNotificationPage extends GlobalNotificationElement {
    Logger logger = Logger.getLogger("GlobalNotificationPage");
    boolean result = false;
    public GlobalNotificationPage() {
        logger.info("init...");
    }
    
    public void clickNetworkconfiguration() {
        Networkconfigurationbutton.click();
        MyCommonAPIs.sleep(6000);
        
    }
   
    public void TroubleshootPage(String devicename) {
        Troubleshot.click();
        MyCommonAPIs.sleep(3000);
        
        TroubleshotDevice(devicename).click();  
        
        MyCommonAPIs.sleep(6000);
        
           }
    
    public boolean checkDeviceEnterDashBoardPage(String devicename) {
        
        MyCommonAPIs.sleep(3000);
        System.out.print("Entered loop\n");
        if(devicedashboard(devicename).exists()){
            
            if(sharediagnosticsap.exists() | sharediagnosticsapFourSwitch.exists()) {
             result = true;
             System.out.print("Entered device dash board\n\n");
            }
         }
         return result;
        
    }
    
public boolean checkOrbiDeviceEnterDashBoardPage(String devicename) {
        
        if(devicedashboard(devicename).exists()){
            
            if(deviceMode.exists()) {
             result = true;
             System.out.print("Entered device dash board\n\n");
            }
         }
         return result;
        
    }
    
    public boolean checkNetworkconfigurationpage(){
      
        String url = MyCommonAPIs.getCurrentUrl();
        boolean result = false;
        if((url.contains("/#/wireless/wirelessSettings") | url.contains("/#/wireless/wirelessOrbiSettings") ) && Quickviewexits.exists()){
            result = true;
            logger.info("Its not Ap settings page");
            System.out.print("Entered Wireless setting page/n");
        }
  
           return result;
           
         
    }

public boolean checkNetworkconfigurationpageBR500(){
    
    String url = MyCommonAPIs.getCurrentUrl();
    boolean result = false;
    if(url.contains("#/routers/settings/VPNGroups")){
        result = true;
        logger.info("Its not router settings page");
        System.out.print("Entered Router setting page\n");
    }
    MyCommonAPIs.sleepi(10);
       if (BRQuickviewexits.exists()) {
        result = true;
       System.out.print("Quickviewexits element is present\n");
       }
       return result;
       
     
}

    public void EnterWirelessSettingpage(){
        if(Wiredpage1.isDisplayed()) {
        Wiredpage1.click();}else {
            Wiredpage.click();
        }
        System.out.println("eneterd wire");
        MyCommonAPIs.sleep(3000);
        WiredSettings.click();
        MyCommonAPIs.sleep(3000);  
    
    }
    
    public void addssidViaWired(){
        
        addVLANWied.click();
        MyCommonAPIs.sleep(3000);
        continueWired.click();
        MyCommonAPIs.sleep(3000);  
        EnterNetworkName.sendKeys("chvk");
        EnterVlanName.sendKeys("54");
        EnterVlanID.sendKeys("434");
        MyCommonAPIs.sleepi(5);  
        Next1.click();  
        MyCommonAPIs.sleep(3000);  
        Next1.click();  
    }
    
    public void gotospanningTree(String devicename){
        
        SpanningTree.click();
        MyCommonAPIs.sleep(3000);
//        EnableSpanningTree.click();
//        MyCommonAPIs.sleep(3000);
        deviceconfigurationinvlan(devicename).click();
        MyCommonAPIs.sleep(3000);
                 
    }
   
    
    
    public void checkPOEScheduler(String devicename){
       System.out.println(java.time.LocalDate.now());       
        
        LocalDate Localday = java.time.LocalDate.now();       
        String strDate = Localday.toString();
        String todayDate =strDate.substring(8);
        System.out.println(todayDate);
        String check = todayDate.substring(0,1);
        System.out.println(check);
        if(check.equals("0")) {          
        todayDate = todayDate.substring(1);
        System.out.println(todayDate);
        }
        
        
        POESchedules.click();
        MyCommonAPIs.sleep(3000);
        AddScheduler.click();
        MyCommonAPIs.sleep(3000);
        MyCommonAPIs.sleep(3000);
        SchedulerName.sendKeys("New");
        MyCommonAPIs.sleep(3000);
        EnableAllDay.click();
        MyCommonAPIs.sleep(3000);
        RecurrenceType.click();
        MyCommonAPIs.sleep(1000);
        RecurrenceValue.click();
        MyCommonAPIs.sleep(1000);
        clickStartdate.click();
        MyCommonAPIs.sleep(3000);
        selectdate1(todayDate).click();
        MyCommonAPIs.sleep(3000);
        clickenddate.click();
        MyCommonAPIs.sleep(3000);
        selectdate1(todayDate).click();
        MyCommonAPIs.sleep(3000);
        SaveselectPort.click();
        MyCommonAPIs.sleep(3000);
        deviceconfigurationinvlan(devicename).click();
        MyCommonAPIs.sleep(3000);
        
    }

    
    public void GotoRadiusConfiguration(String devicename){
        
        Radiusconf.click();
        MyCommonAPIs.sleep(3000);
        deviceconfigurationinvlan(devicename).click();
        MyCommonAPIs.sleep(3000); 
    }
    
    

    public void checkGroupPortConfig(String devicename){
    

        GroupPortSettings.click();
        MyCommonAPIs.sleep(3000);
        deviceconfigurationinvlan(devicename).click();
        MyCommonAPIs.sleep(3000);
        
    
    }

   public void  ClickonVLAN(String devicename){
       
       Wiredpage.click();
       MyCommonAPIs.sleep(3000);
       if(WiredSettings.exists()) {
           WiredSettings.click();
       }
       MyCommonAPIs.sleep(3000);
       
       String vlanName   = "Management VLAN";
       String vlanId     = "1";
       wvp.openVlan(vlanName, vlanId, 0);
       MyCommonAPIs.sleep(3000);
//       PortMember.click();
       MyCommonAPIs.sleep(3000);
       
       deviceconfigurationinvlan(devicename).click();
       MyCommonAPIs.sleep(3000);

   }
   
   
    public boolean checkswitchsettingpage(){
    
    String url = MyCommonAPIs.getCurrentUrl();
    boolean result = false;
    if(url.contains("#/wired/VLAN")){
        result = true;
        logger.info("Its not switch settings page");
        System.out.print("Entered switch setting page\n");
    }
    
       if (Quickviewexits.exists())
        result = true;
       System.out.print("Quickviewexits element is present\n");
       
       return result;
}

public void gotonetworksetup(String devicename){
    
    editnetwork.click();
    MyCommonAPIs.sleep(3000);
    
    gotoNetworksetup.click();
    MyCommonAPIs.sleep(3000);
    
    enterVLAN.click();
    MyCommonAPIs.sleep(3000);
    
    if(VLANOK.exists()) {
        VLANOK.click();
    }
    
    NEXT.click();
    MyCommonAPIs.sleep(3000);
    
    deviceinfonetworkloc(devicename).click();
    
    MyCommonAPIs.sleep(3000);
    
}
	
public boolean gotonetworksetupBR(String devicename){
    
    editnetwork.click();
    MyCommonAPIs.sleep(3000);
    
    gotoNetworksetup.click();
    MyCommonAPIs.sleep(3000);
    
    enterVLAN.click();
    MyCommonAPIs.sleep(3000);
    
    if(VLANOK.exists()) {
        VLANOK.click();
    }
    
    NEXT.click();
    MyCommonAPIs.sleep(3000);
    
    deviceinfonetworkloc(devicename).click();
    
    MyCommonAPIs.sleep(3000);
      
   if(devicedashboard(devicename).exists() && TestVPNServer.exists()){
        result = true;
        System.out.print("Entered device dash board\n\n");
        
    }
    return result;
}
    
public void instantwifipage(String APname){
    
    
    Wireless.click();
    MyCommonAPIs.sleep(3000);
    Settings.click();
    MyCommonAPIs.sleep(3000);
    InstantWiFi.click();
    MyCommonAPIs.sleep(3000);
    if(deviceconfigurationinvlan(APname).exists()){      
        deviceconfigurationinvlan(APname).click();
        MyCommonAPIs.sleep(3000);
        
    }

}


public void enterDeviceYes(String devicename){
    
    enterdevicespage.click();
    MyCommonAPIs.sleep(3000);
    clickdevice(devicename).doubleClick();

}


public boolean DeleteSwitch(String devicename){
    Dropdownforswitch.click();
    MyCommonAPIs.sleepi(5);
    boolean result = false;
    deleteswitch.click();
    MyCommonAPIs.sleep(3000);
    YesDeleteButton.click();
    MyCommonAPIs.sleep(3000);
    if (new DevicesDashPage().getDeviceName(devicename).equals(""))
    {
        result = true;
    }
    
    return result;
    }

public boolean ConformORbideleted(String devicename){
    
    boolean result = false;
    
    if (new DevicesDashPage().getDeviceName(devicename).equals(""))
    {
        result = true;
    }
    
    return result;
}

public boolean checkswitchNameIsExist(String serialNumber, String Name) {
    WebCheck.checkHrefIcon(URLParam.hrefDevices);
    boolean result = false;
    if ($x("//td[text()='" + serialNumber + "']/../td[1]").getText().equals(Name)) {
        result = true;
        logger.info("switch name:" + Name + " is existed.");
    }
    return result;
}

public boolean checkApIsExist(String serialNumber) {
    waitReady();
    boolean result = false;
    String sElement = String.format("//td[text()='%s']", serialNumber);
    logger.info("on element: " + sElement);
    if ($x(sElement).exists()) {
        result = true;
        logger.info("Ap SN:" + serialNumber + " is existed.");
    }
    return result;
}

public void editswitchName(String serialNumber, String newname) {
    WebCheck.checkHrefIcon(URLParam.hrefDevices);
    if (checkApIsExist(serialNumber)) {
        editName(serialNumber).click();
        MyCommonAPIs.sleepi(3);
        inputName(serialNumber).clear();
        MyCommonAPIs.sleepi(3);
        inputName(serialNumber).sendKeys(newname);
        inputnameyes(serialNumber).click();
    } else {
        logger.info("device isn't existed: " + serialNumber);
    }
}

public void RebootSwitch(String devicename){
    MyCommonAPIs.sleepi(5);
    Dropdownforswitch.click();
    MyCommonAPIs.sleepi(5);
    rebootswitch.click();
    boolean result = false;
//    Rebootswitch.click();
    MyCommonAPIs.sleep(3000);
    YesRebootButton.click();
    MyCommonAPIs.sleep(3000);
    
}

public void RebootOrbi(String devicename){
    Dropdown.click();
    MyCommonAPIs.sleepi(5);
    reset.click();
    MyCommonAPIs.sleepi(5);
    rebootconfirm.click();
    MyCommonAPIs.sleepi(5);
    boolean result = false;
//    Rebootorbi.click();
//    MyCommonAPIs.sleep(3000);
//    ContinueRebootButton.click();
    MyCommonAPIs.sleep(3000);
    
}

public void RebootBR(String devicename){
    
    boolean result = false;
    Dropdownforswitch.click();
    MyCommonAPIs.sleepi(5);
    rebootswitch.click();
    MyCommonAPIs.sleep(3000);
    YesRebootButton.click();
    MyCommonAPIs.sleep(3000);
    
}

public void ShareDiognastic(String email){
    
    boolean result = false;
    ShareDiognastic.click();
    MyCommonAPIs.sleep(3000);
    MyCommonAPIs.sleepi(3);
    shareemail.sendKeys(email);
    send.click();
    MyCommonAPIs.sleepi(3);
    
}

public boolean checkAlertIsExist() {
    logger.info("Check alert is exist.");
    boolean result = false;
    if (alerttext.exists()) {
        if (alerttext.getText().equals("Your configuration has been applied. It may take some time to reflect")) {
            logger.info("Alert is existed.");
            result = true;
        } else if (alerttext.getText().equals("Diagnostic logs shared successfully")) {
            logger.info("Alert is existed.");
            result = true;
        }
    }
    return result;
}
    
public boolean checkAlertIsExistBR() {
    logger.info("Check alert is exist.");
    boolean result = false;
    System.out.println("Success Message --->"+alerttext.getText());
    if (alerttext.exists()) {
        if (alerttext.getText().equals("Your configuration has been applied. It may take some time to reflect")) {
            logger.info("Alert is existed.");
            result = true;
        } else if (alerttext.getText().equals("Diagnostics logs shared.")) {
            logger.info("Alert is existed.");
            result = true;
        }
    }
    return result;
}
   public void ShareDiognasticOrbi(String email){
        
        boolean result = false;
        ShareDiognasticOrbi.click();
        MyCommonAPIs.sleep(3000);
        MyCommonAPIs.sleepi(3);
        shareemail.sendKeys(email);
        send.click();
        MyCommonAPIs.sleepi(3);
    
    
}
   public void ShareDiognasticBR(String email){
       
       boolean result = false;
       ShareDiognasticBR.click();
       MyCommonAPIs.sleep(3000);
       MyCommonAPIs.sleepi(3);
       shareemail.sendKeys(email);
       send.click();
       MyCommonAPIs.sleepi(3);
   
   
}
//public int getUptimeswitch(String devicename){
//    
//    
//////    int text = getText(String.format(uptimeswitch, devicename));
////    
////    return text;
//}

}
