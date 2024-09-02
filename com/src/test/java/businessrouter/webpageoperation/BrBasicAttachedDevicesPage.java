package businessrouter.webpageoperation;
import static com.codeborne.selenide.Selenide.$x;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.codeborne.selenide.Selenide;

import businessrouter.webelements.BrAllMenueElements;
import businessrouter.webelements.BrBasicAttachedDevicesElements;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
public class BrBasicAttachedDevicesPage extends BrBasicAttachedDevicesElements{

    public BrBasicAttachedDevicesPage() {
        // TODO Auto-generated constructor stub
    }
    final static Logger logger = Logger.getLogger("BrBasicAttachedDevicesPage");
    boolean result = false;
    public void OpenAttachedDevicesPage() {
        //open(LoginURL);
        logger.info("Open Attached Device Page");
        //Selenide.open(BrURLParam.FirewallTrafficRules);
        //MyCommonAPIs.sleep(10000); 
        BrAllMenueElements BrAllMenueElements = new BrAllMenueElements();
        BrAllMenueElements.basic.click();
        if (WebportalParam.DUTType.contentEquals("BR500")) {
            BrAllMenueElements.AttachedDevices.click();
        } else if(WebportalParam.DUTType.contentEquals("BR100")) {
            BrAllMenueElements.AttachedDevicesbr100.click();
     
        }
       
    }
    public String GetAccessControlStatus() {
        String AccessControlStatus = accesscontrolstatus.getText();
        logger.info(AccessControlStatus);
        String  []LsAccessControlStatus = AccessControlStatus.split(":");
        logger.info(LsAccessControlStatus[1]);
        return LsAccessControlStatus[1].trim();
    }
    public String GetAccessControlGeneralRule() {
        String AccessControlGeneralRule = accesscontrolgenerlrule.getText();
        logger.info(AccessControlGeneralRule);
        String  []LsAccessControlGeneralRule = AccessControlGeneralRule.split(":");
        logger.info(LsAccessControlGeneralRule[1]);
        return LsAccessControlGeneralRule[1].trim();
    }
    public boolean CheckConnectedDutDeviceInfo(Map<String, String> DeviceInfo) {
        boolean Result = false;
        logger.info("CheckConnectedDutDeviceInfo Start"); 
        String  DeviceListObj= "//div[@id ='attachedDevices_listdevice_list1Item1']"; 
        logger.info("DeviceListObj:" +DeviceListObj);
        List<String> LsAccessControlConnectedDev= MyCommonAPIs.getTexts(DeviceListObj);
       
        for (String name : LsAccessControlConnectedDev) {
         
            //System.out.print(name);
            logger.info("222222222222222222222222222222222222222");
            logger.info("name:" + name); 
            String []DeviceElemList = name.split("\n|\r");
            System.out.print(DeviceElemList.length);
                logger.info("DeviceElemList[1]:" + DeviceElemList[0]);
                logger.info("DeviceElemList[2]:" + DeviceElemList[1]); 
                logger.info("DeviceElemList[3]:" + DeviceElemList[2]);
               // logger.info("DeviceElemList[4]:" + DeviceElemList[3]);
                logger.info("DeviceElemList[1]1:" + DeviceInfo.get("Device Name")); 
                logger.info("DeviceElemList[2]2:" + DeviceInfo.get("IP")); 
                logger.info("DeviceElemList[3]3:" + DeviceInfo.get("MAC")); 
                logger.info("DeviceElemList[4]4:" + DeviceInfo.get("Status"));
                System.out.println(DeviceInfo.get("MAC").toLowerCase());
                if(DeviceElemList.length == 3) {
                    if (DeviceElemList[0].contentEquals(DeviceInfo.get("Device Name"))&& DeviceElemList[1].contentEquals(DeviceInfo.get("IP"))&&DeviceElemList[2].contentEquals(DeviceInfo.get("MAC"))){
                        System.out.println("d9999999999999999999999999999999999999");
                        Result = true; 
                    }
                    
                } else if (DeviceElemList.length == 4) {
                   if (DeviceElemList[0].contentEquals(DeviceInfo.get("Device Name"))&& DeviceElemList[1].contentEquals(DeviceInfo.get("IP"))&&DeviceElemList[2].contentEquals(DeviceInfo.get("MAC"))&&DeviceElemList[3].contentEquals(DeviceInfo.get("Status"))){
                       Result = true; 
                   }
                        
                } else if (DeviceElemList.length == 5) {
                    if (DeviceElemList[0].contentEquals(DeviceInfo.get("Device Name"))&& DeviceElemList[1].contentEquals(DeviceInfo.get("IP"))&&DeviceElemList[2].contentEquals(DeviceInfo.get("MAC"))&&DeviceElemList[3].contentEquals(DeviceInfo.get("Status"))&&DeviceElemList[4].contentEquals(DeviceInfo.get("Status1"))){
                        Result = true; 
                    }  
                } else if (DeviceElemList.length == 6) {
                    if (DeviceElemList[0].contentEquals(DeviceInfo.get("Device Name"))&& DeviceElemList[1].contentEquals(DeviceInfo.get("IP"))&&DeviceElemList[2].contentEquals(DeviceInfo.get("MAC"))&&DeviceElemList[3].contentEquals(DeviceInfo.get("Status"))&&DeviceElemList[4].contentEquals(DeviceInfo.get("Status1"))&&DeviceElemList[5].contentEquals(DeviceInfo.get("Status2"))){
                        Result = true; 
                    } 
                }
   
                System.out.print(Result); 
          if( Result ) {
              break;
              
          }     
         }
        System.out.print(Result);
        logger.info("CheckConnectedDutDeviceInfo Done"); 
         return Result;    
    }
    
    public boolean EditLanDeviceName (Map<String, String> NewLanDeviceName) {
        //open(LoginURL)
        logger.info("EditLanDeviceName start!");
        boolean Result = false;
        String  DeviceListObj= "//div[@id ='attachedDevices_listdevice_list1Item1']"; 
        String  LanDeviceObj= "(//*[@id='attachedDevices_listdevice_list1Item1Meta1']/div[1]/a/img)"; 
        logger.info("DeviceListObj:" +DeviceListObj);
        List<String> LsAccessControlConnectedDev0= MyCommonAPIs.getTexts(DeviceListObj);
        int i  = 0;
        for (String name : LsAccessControlConnectedDev0) {
         
            logger.info("name:" + name); 
            String []DeviceElemList = name.split("\n|\r");
            i = i +1;
                logger.info("DeviceElemList[1]:" + DeviceElemList[0]);
                logger.info("DeviceElemList[2]:" + DeviceElemList[1]); 
                logger.info("DeviceElemList[3]:" + DeviceElemList[2]);
                logger.info("DeviceElemList[1]1:" + NewLanDeviceName.get("Device Name")); 
                logger.info("DeviceElemList[2]2:" + NewLanDeviceName.get("IP")); 
                logger.info("DeviceElemList[3]3:" + NewLanDeviceName.get("MAC")); 
                
                if (DeviceElemList[1].contentEquals(NewLanDeviceName.get("IP"))&&DeviceElemList[2].contentEquals(NewLanDeviceName.get("MAC"))){
                    String NewDeviceListObj = LanDeviceObj +"[" + String.valueOf(i)+"]"; 
                    logger.info("NewDeviceListObj:" + NewDeviceListObj); 
                    devicelogger  = $x(NewDeviceListObj);
            }
         }
        Selenide.sleep(20000);
        devicelogger.doubleClick();
        Selenide.sleep(6000);
        if (NewLanDeviceName.get("Device Name") != null) {
            landevicename.setValue(NewLanDeviceName.get("Device Name"));
        }
        if (NewLanDeviceName.get("Device Type") != null) {
            //trafficrulesprotocol.click();
            if (NewLanDeviceName.get("Device Type").equalsIgnoreCase("All")) {
               // trafficrulesprotocolall.click();
            }else if(NewLanDeviceName.get("Device Type").equalsIgnoreCase("ICMP")){
                //trafficrulesprotocolicmp.click();               
            }else if(NewLanDeviceName.get("Device Type").equalsIgnoreCase("UDP")){
                //trafficrulesprotocoludp.click();
            }else if(NewLanDeviceName.get("Device Type").equalsIgnoreCase("TCP")){
               // trafficrulesprotocoltcp.click();
            }else if(NewLanDeviceName.get("Device Type").equalsIgnoreCase("TCP+UDP")){
                //trafficrulesprotocoludptcp.click();
            }  
            Selenide.sleep(4000);
        } 
       
       
        applybutton.click();
        Selenide.sleep(20000);
        List<String> LsAccessControlConnectedDev= MyCommonAPIs.getTexts(DeviceListObj);
       
        for (String name : LsAccessControlConnectedDev) {
         
            logger.info("name:" + name); 
            String []DeviceElemList = name.split("\n|\r");
            
                logger.info("DeviceElemList[1]:" + DeviceElemList[0]);
                logger.info("DeviceElemList[2]:" + DeviceElemList[1]); 
                logger.info("DeviceElemList[3]:" + DeviceElemList[2]);
                logger.info("DeviceElemList[1]1:" + NewLanDeviceName.get("Device Name")); 
                logger.info("DeviceElemList[2]2:" + NewLanDeviceName.get("IP")); 
                logger.info("DeviceElemList[3]3:" + NewLanDeviceName.get("MAC")); 
                
                if (DeviceElemList[0].contentEquals(NewLanDeviceName.get("Device Name"))&& DeviceElemList[1].contentEquals(NewLanDeviceName.get("IP"))&&DeviceElemList[2].contentEquals(NewLanDeviceName.get("MAC"))){
                    Result = true; 
                
                
            }
         }
        System.out.print(Result);
       logger.info("EditLanDeviceName stop!");
       return Result;
    }

    
    public boolean EditLanDeviceNameWithSpecialchar (Map<String, String> NewLanDeviceName) {
        logger.info("EditLanDeviceNameWithSpecialchar start!");
        boolean result = false;
        String WarningMessage = null;
        boolean Result = false;
        String  DeviceListObj= "//div[@id ='attachedDevices_listdevice_list1Item1']"; 
        String  LanDeviceObj= "(//*[@id='attachedDevices_listdevice_list1Item1Meta1']/div[1]/a/img)"; 
        logger.info("DeviceListObj:" +DeviceListObj);
        List<String> LsAccessControlConnectedDev0= MyCommonAPIs.getTexts(DeviceListObj);
        int i  = 0;
        for (String name : LsAccessControlConnectedDev0) {
         
            logger.info("name:" + name); 
            String []DeviceElemList = name.split("\n|\r");
            i = i +1;
                logger.info("DeviceElemList[1]:" + DeviceElemList[0]);
                logger.info("DeviceElemList[2]:" + DeviceElemList[1]); 
                logger.info("DeviceElemList[3]:" + DeviceElemList[2]);
                logger.info("DeviceElemList[1]1:" + NewLanDeviceName.get("Device Name")); 
                logger.info("DeviceElemList[2]2:" + NewLanDeviceName.get("IP")); 
                logger.info("DeviceElemList[3]3:" + NewLanDeviceName.get("MAC")); 
                
                if (DeviceElemList[1].contentEquals(NewLanDeviceName.get("IP"))&&DeviceElemList[2].contentEquals(NewLanDeviceName.get("MAC"))){
                    String NewDeviceListObj = LanDeviceObj +"[" + String.valueOf(i)+"]"; 
                    logger.info("NewDeviceListObj:" + NewDeviceListObj); 
                    devicelogger  = $x(NewDeviceListObj);
            }
         }
        Selenide.sleep(20000);
        devicelogger.doubleClick();
        Selenide.sleep(6000);
        landevicename.setValue(NewLanDeviceName.get("Device Name"));
        MyCommonAPIs.sleepi(5);
        if(invaliddevicenamewarning.exists()) {
            WarningMessage = invaliddevicenamewarning.getText();
            logger.info("WarningMessage:" + WarningMessage);
           
        }        
        if (WarningMessage.contentEquals("Enter a valid device name using letters, numbers, or an underline.") ) {
           
           result = true; 
        } else {
           result = false;
           
        }
       logger.info("EditLanDeviceNameWithSpecialchar start!");
       return result;
    }
}
