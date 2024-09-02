package businessrouter.webpageoperation;
import static com.codeborne.selenide.Selenide.$x;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.openqa.selenium.Keys;

import com.codeborne.selenide.Selenide;

import businessrouter.webelements.BrAllMenueElements;
import businessrouter.webelements.BrFirewallAccessControlElements;
import util.MyCommonAPIs;
public class BrFirewallAccessControlPage extends BrFirewallAccessControlElements{
    final static Logger logger = Logger.getLogger("BrFirewallAccessControlPage");
    public void OpenFirewallAccessControlPage() {
        logger.info("Open Firewall Access Control Page");   
        BrAllMenueElements BrAllMenueElements = new BrAllMenueElements();
        BrAllMenueElements.advanced.click();
        BrAllMenueElements.Firewall.click();
        BrAllMenueElements.AccessControl.click();
        logger.info("OpenFirewallAccessControlPage Done"); 
    }
    public void AllowOrBlockNewDevice(String AllowOrBlock) {
        logger.info("AllowOrBlockNewDevice Start"); 
        System.out.print(accesscontrolonoryes.isSelected());
        if (!accesscontrolonoryes.isSelected()){
            accesscontrolonoryes.selectRadio("on");
        }
        if (AllowOrBlock.contentEquals("Allow")){
            System.out.print(accesscontrolallow.isSelected());
            if (accesscontrolallow.isSelected()) {
                
            } else {
                logger.info("ddddddddddddddddddddd"); 
                accesscontrolallow.selectRadio("allow_all");
                logger.info("ddddddddddddddddddddd77777");
            }
            
        } else if(AllowOrBlock.contentEquals("Block")){
            System.out.print(accesscontrolblock.isSelected());
            if (accesscontrolblock.isSelected()) {
                
            } else {
                accesscontrolblock.selectRadio("block_all");
            }
            
        }
        accesscontrolapplybutton.click();
        logger.info("AllowOrBlockNewDevicee Done"); 
    }
    public boolean CheckConnectedDeviceInfo(Map<String, String> DeviceInfo) {
        boolean Result = false;
        logger.info("CheckConnectedDeviceInfo Start"); 
        String  accesscontrolconnecteddevxp= "//tr[@class ='ant-table-row  ant-table-row-level-0']"; 
        logger.info("accesscontrolconnecteddevxp:" +accesscontrolconnecteddevxp);
        List<String> LsAccessControlConnectedDev= MyCommonAPIs.getTexts(accesscontrolconnecteddevxp);
       
        for (String name : LsAccessControlConnectedDev) {
         
            //System.out.print(name);
            logger.info("name:" + name); 
            String []DeviceElemList = name.split(" ");
            if (DeviceElemList[0].contentEquals(DeviceInfo.get("Status"))) {
                logger.info("DeviceElemList[1]:" + DeviceElemList[1]); 
                logger.info("DeviceElemList[2]:" + DeviceElemList[2]); 
                logger.info("DeviceElemList[3]:" + DeviceElemList[3]);
                logger.info("DeviceElemList[1]1:" + DeviceInfo.get("Device Name")); 
                logger.info("DeviceElemList[2]2:" + DeviceInfo.get("IP")); 
                logger.info("DeviceElemList[3]3:" + DeviceInfo.get("MAC")); 
                
                if (DeviceElemList[1].contentEquals(DeviceInfo.get("Device Name"))&& DeviceElemList[2].contentEquals(DeviceInfo.get("IP"))&&DeviceElemList[3].contentEquals(DeviceInfo.get("MAC"))){
                    
                    Result = true; 
                }
                
            }
         }
        logger.info("CheckConnectedDeviceInfo Done"); 
         return Result;    
    }
    public void TunoffAccessControl() {
        logger.info("TunoffAccessControl Start"); 
        System.out.print(accesscontrolonoryes.isSelected());
        if (accesscontrolonoryes.isSelected()){
            accesscontrolonoryes.sendKeys(Keys.SPACE);
        }
        accesscontrolapplybutton.click();
        logger.info("TunoffAccessControl Done"); 
    }
    public void DeletenotConnectedAllowedDevice(Map<String, String> DeviceInfo) {
        //boolean Result = false;
        
        logger.info("DeletenotConnectedAllowedDevice Start"); 
        if( !accesscontrolallownotconnectremovebutton.exists()) {
            accesscontrollistallownotconnect.click();
        }
        String  accesscontrolconnecteddevxp= "//tr[@class ='ant-table-row  ant-table-row-level-0']"; 
        String Checkboxnumber = "(//input[@ class='ant-checkbox-input'])";
        logger.info("accesscontrolconnecteddevxp:" +accesscontrolconnecteddevxp);
        List<String> LsAccessControlConnectedDev= MyCommonAPIs.getTexts(accesscontrolconnecteddevxp);
       int tt =  LsAccessControlConnectedDev.size();
       logger.info("tt:" + String.valueOf(tt)); 
        int  i = 3;
       
        for (String name : LsAccessControlConnectedDev) {
            i = i + 1;
            String []DeviceElemList = name.split(" ");
            logger.info("DeviceElemList[1]:" + DeviceElemList[1]); 
            logger.info("DeviceInfo.get(\"MAC\"):" + DeviceInfo.get("MAC"));
            if (DeviceElemList[1].contentEquals(DeviceInfo.get("MAC"))) {
                Checkboxnumber = Checkboxnumber +"[" + String.valueOf(i)+"]"; 
                accesscontrolselectone = $x(Checkboxnumber);
                accesscontrolselectone.selectRadio("on"); 
                MyCommonAPIs.sleep(2000); 
                logger.info(Checkboxnumber);
            }
        }
        accesscontrolallownotconnectremovebutton.click();
        accesscontrolallownotconnectokbutton.click();
        logger.info("DeletenotConnectedAllowedDevice Done"); 
         //return Result;    
    }
    
    public boolean EditConnectedDeviceInfo(Map<String, String> DeviceInfo) {
        boolean Result = false;
        logger.info("EditConnectedDeviceInfo Start"); 
       // String  accesscontrolconnecteddevxp= "//tr[@class ='ant-table-row  ant-table-row-level-0']"; 
        String Checkboxnumber = "(//input[@ class='ant-checkbox-input'])";
        String  accesscontrolconnecteddevxp= "//tr[@class ='ant-table-row  ant-table-row-level-0']"; 
        logger.info("accesscontrolconnecteddevxp:" +accesscontrolconnecteddevxp);
        List<String> LsAccessControlConnectedDev= MyCommonAPIs.getTexts(accesscontrolconnecteddevxp);
        int i = 2;
        for (String name : LsAccessControlConnectedDev) {
         
            i = i + 1;
            logger.info("name:" + name); 
            String []DeviceElemList = name.split(" ");
            logger.info("DeviceElemList[3]:" + DeviceElemList[3]); 
            logger.info("DeviceElemList[0]0:" + DeviceInfo.get("MAC")); 
            if (DeviceElemList[3].contentEquals(DeviceInfo.get("MAC"))) {
                logger.info("DeviceElemList[1]:" + DeviceElemList[0]); 
                logger.info("DeviceElemList[2]:" + DeviceElemList[1]); 
                logger.info("DeviceElemList[3]:" + DeviceElemList[2]);
                logger.info("DeviceElemList[1]1:" + DeviceInfo.get("Device Name")); 
                logger.info("DeviceElemList[2]2:" + DeviceInfo.get("IP")); 
                logger.info("DeviceElemList[0]0:" + DeviceInfo.get("Status")); 
                Checkboxnumber = Checkboxnumber +"[" + String.valueOf(i)+"]"; 
                accesscontrolselectone = $x(Checkboxnumber);
                accesscontrolselectone.selectRadio("on");
                MyCommonAPIs.sleep(2000); 
                logger.info(Checkboxnumber);
                accesscontroleditbutton.click();
                if (DeviceInfo.get("Status") != null) {
                    accesscontrolconnectstatus.click();
                    if(DeviceInfo.get("Status").equalsIgnoreCase("Allow")){
                        accesscontrolconnectstatusallow.click();               
                    }else if(DeviceInfo.get("Status").equalsIgnoreCase("Block")){
                        accesscontrolconnectstatusblock.click();
                    }
                    Selenide.sleep(4000);
                    
                } 
                
                if (DeviceInfo.get("Device Name") != null) {
                    accesscontrolconnecteditdevicename.setValue( DeviceInfo.get("Device Name"));
                
                }
            }
            
         }
        accesscontrolconnecteditapplybutton.click();
        
        logger.info("EditConnectedDeviceInfo Done"); 
         return Result;    
    }
    
    public boolean AddBlockNotConnectDev(Map<String, String> DeviceInfo) {
        boolean Result =false;
        logger.info("AddBlockNotConnectDev Start"); 
        if( !accesscontrolblockremovedbutton.exists()) {
            accesscontrollistblocknotconnect.click();
        }
        accesscontrolblockaddbutton.click();
        if (DeviceInfo.get("Device Name")!= null) {
            accesscontrolblockdevicename.setValue(DeviceInfo.get("Device Name"));
        }
        if (DeviceInfo.get("MAC")!= null) {
            accesscontrolblockdevicemac.setValue(DeviceInfo.get("MAC"));
        }
        
        accesscontrolconnecteditapplybutton.click();
        Selenide.sleep(5000);
        String  accesscontrolconnecteddevxp= "//tr[@class ='ant-table-row  ant-table-row-level-0']"; 
        logger.info("accesscontrolconnecteddevxp:" +accesscontrolconnecteddevxp);
        List<String> LsAccessControlConnectedDev= MyCommonAPIs.getTexts(accesscontrolconnecteddevxp);
     
        for (String name : LsAccessControlConnectedDev) {
                  
            logger.info("name:" + name); 
            String []DeviceElemList = name.split(" ");
            logger.info("DeviceElemList[1]:" + DeviceElemList[1]); 
            logger.info("DeviceInfo-MAC:" + DeviceInfo.get("MAC")); 
            if (DeviceElemList[1].contentEquals(DeviceInfo.get("MAC"))) {
                Result = true; 
              }
                
        }
        
        logger.info("AddBlockNotConnectDev Done"); 
        return Result;
        
    }
    
    public boolean DeletenotConnectedBlockedDevice(Map<String, String> DeviceInfo) {
        boolean Result = false;
        logger.info("DeletenotConnectedBlockedDevice Start"); 
        if( !accesscontrolblockremovedbutton.exists()) {
            accesscontrollistblocknotconnect.click();
        }
        String  accesscontrolconnecteddevxp= "//tr[@class ='ant-table-row  ant-table-row-level-0']"; 
        String Checkboxnumber = "(//input[@ class='ant-checkbox-input'])";
        logger.info("accesscontrolconnecteddevxp:" +accesscontrolconnecteddevxp);
        List<String> LsAccessControlConnectedDev= MyCommonAPIs.getTexts(accesscontrolconnecteddevxp);
       int tt =  LsAccessControlConnectedDev.size();
       logger.info("tt:" + String.valueOf(tt)); 
        int  i = 3;
        for (String name : LsAccessControlConnectedDev) {
            i = i + 1;
            String []DeviceElemList = name.split(" ");
            logger.info("DeviceElemList[1]:" + DeviceElemList[1]); 
            logger.info("DeviceInfo.get(\"MAC\"):" + DeviceInfo.get("MAC")); 
            if (DeviceElemList[1].contentEquals(DeviceInfo.get("MAC"))) {
               Checkboxnumber = Checkboxnumber +"[" + String.valueOf(i)+"]"; 
               accesscontrolselectone = $x(Checkboxnumber);
               accesscontrolselectone.selectRadio("on"); 
                MyCommonAPIs.sleep(2000); 
               logger.info(Checkboxnumber);
            }           
        }
        accesscontrolblockremovedbutton.click();
        accesscontrolallownotconnectokbutton.click();
        logger.info("DeletenotConnectedBlockedDevice Done"); 
         return Result;    
    }
    
    public boolean AddAllowNotConnectDev(Map<String, String> DeviceInfo) {
        boolean Result =false;
        logger.info("AddAllowNotConnectDev Start"); 
        if( !accesscontrolallownotconnectremovebutton.exists()) {
            accesscontrollistallownotconnect.click();
        }
        accesscontrolallowedaddbutton.click();
        if (DeviceInfo.get("Device Name")!= null) {
            accesscontrolblockdevicename.setValue(DeviceInfo.get("Device Name"));
        }
        if (DeviceInfo.get("MAC")!= null) {
            accesscontrolblockdevicemac.setValue(DeviceInfo.get("MAC"));
        }
        
        accesscontrolconnecteditapplybutton.click();
        Selenide.sleep(5000);
        String  accesscontrolconnecteddevxp= "//tr[@class ='ant-table-row  ant-table-row-level-0']"; 
        logger.info("accesscontrolconnecteddevxp:" +accesscontrolconnecteddevxp);
        List<String> LsAccessControlConnectedDev= MyCommonAPIs.getTexts(accesscontrolconnecteddevxp);
     
        for (String name : LsAccessControlConnectedDev) {
                  
            logger.info("name:" + name); 
            String []DeviceElemList = name.split(" ");
            logger.info("DeviceElemList[1]:" + DeviceElemList[1]); 
            logger.info("DeviceElemList[0]0:" + DeviceInfo.get("MAC")); 
            if (DeviceElemList[1].contentEquals(DeviceInfo.get("MAC"))) {
                Result = true; 
              }
                
        }
        
        logger.info("AddAllowNotConnectDev Done"); 
        return Result;
        
    }
    public boolean BlockedOrAllowedConnectedDeviceInfo(Map<String, String> DeviceInfo) {
        boolean Result = false;
        logger.info("BlockedOrAllowedConnectedDeviceInfo Start"); 
      
        String Checkboxnumber = "(//input[@ class='ant-checkbox-input'])";
        String  accesscontrolconnecteddevxp= "//tr[@class ='ant-table-row  ant-table-row-level-0']"; 
        logger.info("accesscontrolconnecteddevxp:" +accesscontrolconnecteddevxp);
        List<String> LsAccessControlConnectedDev= MyCommonAPIs.getTexts(accesscontrolconnecteddevxp);
        int i = 2;
        for (String name : LsAccessControlConnectedDev) {
         
            i = i + 1;
            logger.info("name:" + name); 
            String []DeviceElemList = name.split(" ");
            logger.info("DeviceElemList[3]:" + DeviceElemList[3]); 
            logger.info("DeviceElemList[0]0:" + DeviceInfo.get("MAC")); 
            if (DeviceElemList[3].contentEquals(DeviceInfo.get("MAC"))) {
                logger.info("DeviceElemList[1]:" + DeviceElemList[0]); 
                logger.info("DeviceElemList[2]:" + DeviceElemList[1]); 
                logger.info("DeviceElemList[3]:" + DeviceElemList[2]);
                logger.info("DeviceElemList[1]1:" + DeviceInfo.get("Device Name")); 
                logger.info("DeviceElemList[2]2:" + DeviceInfo.get("IP")); 
                logger.info("DeviceElemList[0]0:" + DeviceInfo.get("Status")); 
                Checkboxnumber = Checkboxnumber +"[" + String.valueOf(i)+"]"; 
                accesscontrolselectone = $x(Checkboxnumber);
                accesscontrolselectone.selectRadio("on");
                MyCommonAPIs.sleep(2000); 
                logger.info(Checkboxnumber);
                if (DeviceInfo.get("Status").contentEquals("Blocked")) {
                    accesscontrolblockbutton.click();
                    if (accesscontrollokbutton.exists()) {
                        accesscontrollokbutton.click();
                    }
                } else if (DeviceInfo.get("Status").contentEquals("Allowed")) {
                    accesscontrolallowbutton.click();
                    if (accesscontrollokbutton.exists()) {
                        accesscontrollokbutton.click();
                    }
                }
                
            }
                
        }
        Selenide.sleep(5000);
        List<String> LsAccessControlConnectedDev1= MyCommonAPIs.getTexts(accesscontrolconnecteddevxp);
        
        for (String name : LsAccessControlConnectedDev1) {
         
            //System.out.print(name);
            logger.info("name:" + name); 
            String []DeviceElemList = name.split(" ");
            if (DeviceElemList[0].contentEquals(DeviceInfo.get("Status"))) {
                logger.info("DeviceElemList[1]:" + DeviceElemList[1]); 
                logger.info("DeviceElemList[2]:" + DeviceElemList[2]); 
                logger.info("DeviceElemList[3]:" + DeviceElemList[3]);
                logger.info("DeviceElemList[1]1:" + DeviceInfo.get("Device Name")); 
                logger.info("DeviceElemList[2]2:" + DeviceInfo.get("IP")); 
                logger.info("DeviceElemList[3]3:" + DeviceInfo.get("MAC")); 
                
                if (DeviceElemList[1].contentEquals(DeviceInfo.get("Device Name"))&& DeviceElemList[2].contentEquals(DeviceInfo.get("IP"))&&DeviceElemList[3].contentEquals(DeviceInfo.get("MAC"))){
                    Result = true; 
                }
                
            }
         }
        logger.info("BlockedOrAllowedConnectedDeviceInfo Done"); 
        return Result;    
    }
}
