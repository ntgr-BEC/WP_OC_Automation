package webportal.weboperation;

import java.util.logging.Logger;

import util.MyCommonAPIs;
import webportal.webelements.TroublesshotElement;

public class TroubleshootPage extends TroublesshotElement {
    Logger logger = Logger.getLogger("TroubleshootPage");

    public TroubleshootPage() {
        logger.info("init...");
        Troubleshot.click();
        MyCommonAPIs.sleep(3000);
    }

    public void clickTroubleshot() {
        Troubleshot.click();
    }
    
    public void clickTriubleshotDeviceconfiguration() {
        TroubleshotDeviceConfigurationAP.click();
        MyCommonAPIs.sleep(3000);
        
    }
    public void clickNetworkconfiguration() {
        Networkconfigurationbutton.click();
        MyCommonAPIs.sleep(3000);
        
    }
    public boolean checkDeviceconfigurationpage(){
        
        boolean result = false;
        String url = MyCommonAPIs.getCurrentUrl();
        if(url.contains("/#/devices/accessPoint/summary")){
            result = true;
            logger.info("Its not Ap settings page");
            System.out.print("Entered Wireless setting page/n");
        }
        if(NetworkConfiguration.exists()){
            result=true;
            System.out.print("AP Dash board screen is present");
            return result;
        }
          
        return result;
    }
    
}

