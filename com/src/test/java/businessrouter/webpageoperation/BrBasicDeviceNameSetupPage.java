package businessrouter.webpageoperation;
import java.util.logging.Logger;

import businessrouter.webelements.BrAllMenueElements;
import businessrouter.webelements.BrBasicDeviceNameSetupElements;
//import webportal.weboperation.MyCommonAPIs;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
public class BrBasicDeviceNameSetupPage extends BrBasicDeviceNameSetupElements {
    final static Logger logger = Logger.getLogger("BrBasicDeviceNameSetupPage"); 
    public void OpenDeviceNameSetupPage() {
        BrAllMenueElements BrAllMenueElements = new BrAllMenueElements();              
        logger.info("Open Device Name Setup page");
        BrAllMenueElements.basic.click();
        if (WebportalParam.DUTType.contentEquals("BR500")) {
            BrAllMenueElements.Setup.click();
        } else if(WebportalParam.DUTType.contentEquals("BR100")) {
            BrAllMenueElements.SetupBr100.click();
        }
        
        BrAllMenueElements.DeviceNameSetup.click();

    }
    public  String GetDevicename () {
        //open(LoginURL)
       String Devicename = devicename.getValue();
       return Devicename;
    }
    public boolean Editdevicename (String newdivicename) {
        //open(LoginURL)
        logger.info("Edit Device Name start!");
        boolean result;
       String OldDevicename = devicename.getValue();
       devicename.setValue(newdivicename);
       logger.info("111111111111111111111");
       MyCommonAPIs.sleepi(5);
       devicenameapplybutton.click();
       MyCommonAPIs.sleepi(50);
       logger.info("1111111111111111111112");
       String NewDevicename = devicename.getValue();
       int devicenamecompareresult = OldDevicename.compareTo(NewDevicename); 
       if (devicenameeditwarning.exists()) {
           
           result = false; 
       } else {
           if(devicenamecompareresult !=  0) {
               result = true; 
           } else {
               result = false; 
           }
       }
       
       return result;
    }
    public  boolean ComparedefaultDevicename (String Defaultname) {
        //open(LoginURL)
       boolean results = false;
       String Devicename = devicename.getValue();
       logger.info(Devicename);
       if(Devicename.compareTo(Defaultname) == 0) {
           logger.info("compare result");
         results = true;  
       } else {
          results = false;  
       }
       return results;
    }
    
    public boolean Editdevicenamewithspecialchar (String newdivicename) {
        boolean result = false;
       devicename.setValue(newdivicename);
       MyCommonAPIs.sleepi(5);
       String DeviceNameWarning = devicenameeditwarning.text();
       logger.info("devicewarning:" + DeviceNameWarning);
       if (DeviceNameWarning.contentEquals("Device name validation failed.")) {
           
           result = true; 
       } else {
           result = false;
           
       }
       
       return result;
    }
    
    public boolean Editdevicenamewithlongermax(String newdivicename, int maxlegth) {
        boolean result;
        String OldDevicename = devicename.getValue();
        devicename.setValue(newdivicename);
        //devicenameapplybutton.click();
        String NewDevicename = devicename.getValue();
        logger.info("devicename:" + NewDevicename);
        int devicenamecompareresult = OldDevicename.compareTo(NewDevicename); 
        logger.info(String.valueOf(NewDevicename.length()));
        if (NewDevicename.length() == 15 ) {
            if(devicenamecompareresult ==  0) {
                result = false; 
            } else {
                result = true; 
            }            
            
        } else {
            result = false;            
        }
        
        return result;
    }
    
}
