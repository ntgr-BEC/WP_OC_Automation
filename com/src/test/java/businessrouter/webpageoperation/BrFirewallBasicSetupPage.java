package businessrouter.webpageoperation;
import java.util.Map;
import java.util.logging.Logger;

import org.openqa.selenium.Keys;

import com.codeborne.selenide.Selenide;

import businessrouter.webelements.BrAllMenueElements;
import businessrouter.webelements.BrFirewallBasicSetupElements;
public class BrFirewallBasicSetupPage extends BrFirewallBasicSetupElements {
    final static Logger logger = Logger.getLogger("BrFirewallBasicSetupPage");
    public BrFirewallBasicSetupPage() {
        // TODO Auto-generated constructor stub
    }
    public void OpenFirewallBasicSetupPage() {
        logger.info("Open Firewall Basic Setup Page");   
        BrAllMenueElements BrAllMenueElements = new BrAllMenueElements();
        BrAllMenueElements.advanced.click();
        BrAllMenueElements.Firewall.click();
        BrAllMenueElements.BasicSetup.click();
        logger.info("OpenFirewallBasicSetupPage Done"); 
    }
    public boolean EnableOrDisableDMZ(Map<String, String> DMZInfo) {
        boolean Result = false;
        logger.info("EnableOrDisableDMZ start");   
        if(DMZInfo.get("DMZ Status").contentEquals("Enable")) {
            if(!firewallbasicdefaultdmz.isSelected()) {
                firewallbasicdefaultdmz.selectRadio("on");
                if(DMZInfo.get("DMZ IP") != null) {
                    String[] LsDmzIp = DMZInfo.get("DMZ IP").split("\\.");
                    firewallbasicdefaultdmzip1.setValue(LsDmzIp[0]);
                    firewallbasicdefaultdmzip2.setValue(LsDmzIp[1]);
                    firewallbasicdefaultdmzip3.setValue(LsDmzIp[2]);
                    firewallbasicdefaultdmzip4.setValue(LsDmzIp[3]);
                    
                }
            } else {
                if(DMZInfo.get("DMZ IP") != null) {
                    String[] LsDmzIp2 = DMZInfo.get("DMZ IP").split("\\.");
                    firewallbasicdefaultdmzip1.setValue(LsDmzIp2[0]);
                    firewallbasicdefaultdmzip2.setValue(LsDmzIp2[1]);
                    firewallbasicdefaultdmzip3.setValue(LsDmzIp2[2]);
                    firewallbasicdefaultdmzip4.setValue(LsDmzIp2[3]);
                    
                }
                
           }
        } else if(DMZInfo.get("DMZ Status").contentEquals("Disable")) {
            logger.info("22222222222222222222222221212"); 
            System.out.print(firewallbasicdefaultdmz.isSelected()); 
            if(firewallbasicdefaultdmz.isSelected()) {
                firewallbasicdefaultdmz.sendKeys(Keys.SPACE);                 
             }
             
        }
        firewallbasicapply.click();
        Selenide.sleep(5000);
        if(DMZInfo.get("DMZ Status").contentEquals("Enable")) {
            if(firewallbasicdefaultdmz.isSelected()) {
                Result = true;
            }
            
        }else if(DMZInfo.get("DMZ Status").contentEquals("Disable")) {
            if(!firewallbasicdefaultdmz.isSelected()) {
                Result = true;
            }
            
        }
        logger.info("EnableOrDisableDMZ end"); 
        return Result;
    }
    public boolean EnableOrDisableRespondtoPing (String EnableOrDisable) {
        boolean Result = false;
        logger.info("EnableOrDisableRespondtoPing start");   
        if(EnableOrDisable.contentEquals("Enable")) {
            if(!firewallbasicrespingintel.isSelected()) {
                firewallbasicrespingintel.selectRadio("on");
                
            } 
           
        } else if(EnableOrDisable.contentEquals("Disable")) {
            logger.info("22222222222222222222222221212"); 
            System.out.print(firewallbasicrespingintel.isSelected()); 
            if(firewallbasicrespingintel.isSelected()) {
                firewallbasicrespingintel.sendKeys(Keys.SPACE);                 
             }
             
        }
        firewallbasicapply.click();
        Selenide.sleep(5000);
        if(EnableOrDisable.contentEquals("Enable")) {
            if(firewallbasicrespingintel.isSelected()) {
                Result = true;
            }
            
        }else if(EnableOrDisable.contentEquals("Disable")) {
            if(!firewallbasicrespingintel.isSelected()) {
                Result = true;
            }
            
        }
        logger.info("EnableOrDisableRespondtoPing end"); 
        return Result;
    }
    public boolean ChangeNatFilterMode (String mode) {
        boolean Result = false;
        logger.info("ChangeNatFilterMode start");   
        if (mode.equalsIgnoreCase("Secured")) {
            if (!firewallbasicnatfiltersecured.isSelected()) {
                firewallbasicnatfiltersecured.selectRadio("0");
            }
        }else if(mode.equalsIgnoreCase("Open")){
            logger.info("6666666666666666666666666666");  
           if (!firewallbasicnatfilteropen.isSelected()) {
               logger.info("6666666666666666666666666666777777777");  
               firewallbasicnatfilteropen.selectRadio("1");
               
           }               
        }
        logger.info("6666666666666666666666666666777777777888");  
        firewallbasicapply.click();
        Selenide.sleep(5000);
        if (mode.equalsIgnoreCase("Secured")) {
            if (firewallbasicnatfiltersecured.isSelected()) {
                Result = true;
            }
        }else if(mode.equalsIgnoreCase("Open")){
           if (firewallbasicnatfilteropen.isSelected()) {
               Result = true;
               
           }               
        }
        logger.info("ChangeNatFilterMode end"); 
       
        return Result;
    }
    public boolean ChangeMUTSize (String Size) {
        boolean Result = false;
        logger.info("ChangeMUTSize start");   
        if (Size != null) {
            firewallbasicmtusize.setValue(Size);
            }
        
        firewallbasicapply.click();
        Selenide.sleep(5000);
        
            if (firewallbasicmtusize.getValue().contentEquals(Size)) {
                Result = true;
            }
        
        logger.info("ChangeMUTSize end"); 
       
        return Result;
    }
    public boolean ChangeUnormalMUTSize (String UnormalSize) {
        boolean Result = false;
        logger.info("ChangeUnormalMUTSize start");   
        if (UnormalSize != null) {
            logger.info(UnormalSize);  
            logger.info("5555555555555555555555556");   
            firewallbasicmtusize.setValue(UnormalSize);
         }
        if (firewallmtuwarning.getText().contentEquals("Invalid MTU value. The valid range is 616 to 1500")) {
            logger.info("555555555555555555555555667");  
            Result = true;
        }
        
        logger.info("ChangeUnormalMUTSize end"); 
       
        return Result;
    }

}
