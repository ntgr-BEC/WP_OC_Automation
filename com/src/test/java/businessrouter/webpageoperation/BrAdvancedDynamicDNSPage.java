package businessrouter.webpageoperation;
import java.util.Map;
import java.util.logging.Logger;

import org.openqa.selenium.Keys;

import com.codeborne.selenide.Selenide;

import businessrouter.webelements.BrAdvancedDynamicDNSElements;
import businessrouter.webelements.BrAllMenueElements;


public class BrAdvancedDynamicDNSPage extends BrAdvancedDynamicDNSElements {
    final static Logger logger = Logger.getLogger("BrAdvancedDynamicDNSPage");
    //boolean result = false;
    public BrAdvancedDynamicDNSPage() {
        // TODO Auto-generated constructor stub
    }
    
    public void OpenDynamicDNSPage() {
        //open(LoginURL);
        logger.info("Open Dynamic DNS Page");
        BrAllMenueElements BrAllMenueElements = new BrAllMenueElements();
        BrAllMenueElements.advanced.click();
        BrAllMenueElements.DynamicDNS.click();    
    }
    public boolean RigesterMyNetgearAccount(Map<String, String> mynetgearaccountinfo) {
        //open(LoginURL);
        logger.info("RigesterMyNetgearAccount start");
        boolean Result = false;
        if (!ddnsenable.isSelected()) {
            ddnsenable.selectRadio("on");
        }
        Selenide.sleep(5000);
        if (mynetgearaccountinfo.get("Host Name") != null) {
            //int x=(int)(Math.random()*100);
            //String NewHostName = mynetgearaccountinfo.get("Host Name") +  String.valueOf(x);
            //logger.info(NewHostName);
            ddnsaccounthostname.setValue(mynetgearaccountinfo.get("Host Name"));
         } else {
            logger.info("NewHostName is null");
         } 
        if (mynetgearaccountinfo.get("Email") != null) {
            
            ddnsaccountemail.setValue(mynetgearaccountinfo.get("Email"));
         } else {
            logger.info("Email is null");
         } 
        if (mynetgearaccountinfo.get("Password") != null) {
            
            ddnsaccountpassword.setValue(mynetgearaccountinfo.get("Password"));
         } else {
            logger.info("Password is null");
         } 
        Selenide.sleep(8000);
        ddnsregisterbutton.click();
        //ddnsregisterbutton.click();
         Selenide.sleep(60000);
         if (ddensresetbutton.exists()) {
             Result = true;
         }
         logger.info("RigesterMyNetgearAccount end");
         return Result;
    }
    public void ResetDdnsConfiguration() {
        
        logger.info("ResetDdnsConfiguration start");
 
        if (ddensresetbutton.exists()) {
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@!!!!!!!!!");
            ddensresetbutton.click();
        }
        Selenide.sleep(5000);
        ddnsapplybutton.click();
        logger.info("ResetDdnsConfiguration end");
    }
    
    public boolean ConfigMyNetgearAccount(Map<String, String> mynetgearaccountinfo) {
        //open(LoginURL);
        logger.info("ConfigMyNetgearAccount start");
        boolean Result = false;
        if (mynetgearaccountinfo.get("DDNS Flag") != null) {
            if (mynetgearaccountinfo.get("DDNS Flag").contentEquals("Enable")) {
                if (!ddnsenable.isSelected()) {
                    ddnsenable.selectRadio("on");
                } 
            } else if (mynetgearaccountinfo.get("DDNS Flag").contentEquals("Disable")) {
                if (ddnsenable.isSelected()) {
                    ddnsenable.sendKeys(Keys.SPACE);
                } 
            }
        }
        
        Selenide.sleep(5000);
         if (mynetgearaccountinfo.get("Exist Account") != null) {
            if(mynetgearaccountinfo.get("Exist Account").contentEquals("Yes")) {
                if (!ddnsaccountenable.isSelected()) {
                    ddnsaccountenable.selectRadio("yes");
                }
            }else if(mynetgearaccountinfo.get("Exist Account").contentEquals("No")){
                if (!ddnsaccountdisable.isSelected()) {
                    ddnsaccountdisable.selectRadio("no");
                }
            }
            
         } 
        if (mynetgearaccountinfo.get("Host Name") != null) {
            
            ddnsaccounthostname.setValue(mynetgearaccountinfo.get("Host Name"));
         } else {
            logger.info("NewHostName is null");
         } 
        if (mynetgearaccountinfo.get("Email") != null) {
            
            ddnsaccountemail.setValue(mynetgearaccountinfo.get("Email"));
         } else {
            logger.info("Email is null");
         } 
        if (mynetgearaccountinfo.get("Password") != null) {
            
            ddnsaccountpassword.setValue(mynetgearaccountinfo.get("Password"));
         } else {
            logger.info("Password is null");
         } 
        Selenide.sleep(3000);
        ddnsapplybutton.click();
         Selenide.sleep(60000);
         if (ddensresetbutton.exists()) {
             //logger.info(ddnsdomainname.getText());
             //logger.info(ddnsdomainname.getValue());
             //logger.info(ddnsdomainname.getTagName());
             Result = true;
         }
         System.out.print(Result);
         logger.info("ConfigMyNetgearAccount end");
         return Result;
    }
    public boolean ShowDdnsStatusUpOrDown(String DownOrUp) {
        boolean Result = false;
        logger.info("ShowDdnsStatusUpOrDown start");
 
        if (ddnsshowstatusbutton.exists()) {
            ddnsshowstatusbutton.click();
        }
        Selenide.sleep(15000);
        if (DownOrUp.contentEquals("Yes")) {
           if( ddnsstatus.getText().indexOf("successfully") > 0) {
               Result = true;
           }
        } else if(DownOrUp.contentEquals("No")) {
            if( ddnsstatus.getText().indexOf("not reachable") > 0) {
                Result = true;
            }
        }  else if(DownOrUp.contentEquals("Wrong")) {
            if( ddnsstatus.getText().indexOf("Dynamic DNS service is not enabled") > 0) {
                Result = true;
            }
        } 
        
        logger.info("ShowDdnsStatusUpOrDown end");
        System.out.print(Result);
        return Result;
    }
    public boolean NetgearAccountMaxEmailAndMaxPassword(Map<String, String> mynetgearaccountinfo) {
        //open(LoginURL);
        logger.info("NetgearAccountMaxEmailAndMaxPassword start");
        boolean Result = false;
        if (mynetgearaccountinfo.get("DDNS Flag") != null) {
            if (mynetgearaccountinfo.get("DDNS Flag").contentEquals("Enable")) {
                if (!ddnsenable.isSelected()) {
                    ddnsenable.selectRadio("on");
                } 
            } else if (mynetgearaccountinfo.get("DDNS Flag").contentEquals("Disable")) {
                if (ddnsenable.isSelected()) {
                    ddnsenable.sendKeys(Keys.SPACE);
                } 
            }
        }
        
        Selenide.sleep(5000);
         if (mynetgearaccountinfo.get("Exist Account") != null) {
            if(mynetgearaccountinfo.get("Exist Account").contentEquals("Yes")) {
                if (!ddnsaccountenable.isSelected()) {
                    ddnsaccountenable.selectRadio("yes");
                }
            }else if(mynetgearaccountinfo.get("Exist Account").contentEquals("No")){
                if (!ddnsaccountdisable.isSelected()) {
                    ddnsaccountdisable.selectRadio("no");
                }
            }
            
         } 
        if (mynetgearaccountinfo.get("Host Name") != null) {
            
            ddnsaccounthostname.setValue(mynetgearaccountinfo.get("Host Name"));
           
         } else {
            logger.info("NewHostName is null");
         } 
        if (mynetgearaccountinfo.get("Email") != null) {
            
            ddnsaccountemail.setValue(mynetgearaccountinfo.get("Email"));
            
         } else {
            logger.info("Email is null");
         } 
        if (mynetgearaccountinfo.get("Password") != null) {
            
            ddnsaccountpassword.setValue(mynetgearaccountinfo.get("Password"));
         } else {
            logger.info("Password is null");
         } 
        String ddnsemail  =ddnsaccountemail.getValue();
        String ddnspassword = ddnsaccountpassword.getValue();
        if (ddnsemail.length() == 48 && ddnspassword.length() ==32 ) {
            Result = true;
        }
       
         logger.info("NetgearAccountMaxEmailAndMaxPassword end");
         return Result;
    }
    public boolean NetgearAccountMaxEmailAndMaxHosename(Map<String, String> mynetgearaccountinfo) {
        //open(LoginURL);
        logger.info("NetgearAccountMaxEmailAndMaxHosename start");
        boolean Result = false;
        if (mynetgearaccountinfo.get("DDNS Flag") != null) {
            if (mynetgearaccountinfo.get("DDNS Flag").contentEquals("Enable")) {
                if (!ddnsenable.isSelected()) {
                    ddnsenable.selectRadio("on");
                } 
            } else if (mynetgearaccountinfo.get("DDNS Flag").contentEquals("Disable")) {
                if (ddnsenable.isSelected()) {
                    ddnsenable.sendKeys(Keys.SPACE);
                } 
            }
        }
        
        Selenide.sleep(5000);
         if (mynetgearaccountinfo.get("Exist Account") != null) {
            if(mynetgearaccountinfo.get("Exist Account").contentEquals("Yes")) {
                if (!ddnsaccountenable.isSelected()) {
                    ddnsaccountenable.selectRadio("yes");
                }
            }else if(mynetgearaccountinfo.get("Exist Account").contentEquals("No")){
                if (!ddnsaccountdisable.isSelected()) {
                    ddnsaccountdisable.selectRadio("no");
                }
            }
            
         } 
        if (mynetgearaccountinfo.get("Host Name") != null) {
            
            ddnsaccounthostname.setValue(mynetgearaccountinfo.get("Host Name"));
           
         } else {
            logger.info("NewHostName is null");
         } 
        if (mynetgearaccountinfo.get("Email") != null) {
            
            ddnsaccountemail.setValue(mynetgearaccountinfo.get("Email"));
            
         } else {
            logger.info("Email is null");
         } 
        if (mynetgearaccountinfo.get("Password") != null) {
            
            ddnsaccountpassword.setValue(mynetgearaccountinfo.get("Password"));
         } else {
            logger.info("Password is null");
         } 
        String ddnshostname  =ddnsaccounthostname.getValue();
        
        if (ddnshostname.length() == 114 ) {
            Result = true;
        }
       
         logger.info("NetgearAccountMaxEmailAndMaxHosename end");
         return Result;
    }

}
