package businessrouter.webpageoperation;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.openqa.selenium.JavascriptExecutor;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;

import businessrouter.webelements.BrAllMenueElements;
import businessrouter.webelements.BrFirewallTrafficRulesElements;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
public class BrFirewallTrafficRulesPage extends BrFirewallTrafficRulesElements {
    final static Logger logger = Logger.getLogger("BrFirewallTrafficRulesPage");
    boolean result = false;
    public void OpenFirewallTrafficRulesPage() {
        //open(LoginURL);
        logger.info("Open Firewall Traffic Rules Page");
        //Selenide.open(BrURLParam.FirewallTrafficRules);
        //MyCommonAPIs.sleep(10000); 
        BrAllMenueElements BrAllMenueElements = new BrAllMenueElements();
        BrAllMenueElements.advanced.click();
        BrAllMenueElements.Firewall.click();
        if (WebportalParam.DUTType.contentEquals("BR500")) {
            BrAllMenueElements.TrafficRules.click();
        } else if(WebportalParam.DUTType.contentEquals("BR100")) {
            logger.info("99999999999999999999999999"); 
            BrAllMenueElements.TrafficRulesbr100.click();
            
            
        }
       
    }
   
    public  boolean AddLANWANTrafficRule (Map<String, String> lanwantrafficrule) {
        //open(LoginURL)
       
        trafficrulesaddbutton.click();
        if (lanwantrafficrule.get("Rule Name") != null) {
            trafficrulesname.setValue(lanwantrafficrule.get("Rule Name"));
        } else {
            logger.info("Rule Name is null");
            
        }
        if (lanwantrafficrule.get("Protocol") != null) {
            ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", trafficrulesourceaddressend4);
            trafficrulesprotocol.click();
            if (lanwantrafficrule.get("Protocol").equalsIgnoreCase("All")) {
                trafficrulesprotocolall.click();
            }else if(lanwantrafficrule.get("Protocol").equalsIgnoreCase("ICMP")){
                trafficrulesprotocolicmp.click();               
            }else if(lanwantrafficrule.get("Protocol").equalsIgnoreCase("UDP")){
                trafficrulesprotocoludp.click();
            }else if(lanwantrafficrule.get("Protocol").equalsIgnoreCase("TCP")){
                trafficrulesprotocoltcp.click();
            }else if(lanwantrafficrule.get("Protocol").equalsIgnoreCase("TCP+UDP")){
                trafficrulesprotocoludptcp.click();
            }  
            Selenide.sleep(4000);
        } 
        if (lanwantrafficrule.get("Source Zone") != null) {
            logger.info("Source Zone");
           if(lanwantrafficrule.get("Source Zone").contentEquals("LAN")) {
               logger.info("111111111111111"); 
               //trafficrulessourcelan.selectRadio("LAN"); 
               if( lanwantrafficrule.get("Source LAN") != null) {
                   if( lanwantrafficrule.get("Source LAN").contentEquals("LAN2")) {
                       trafficrulessourcelannumber2.click();
                   }
               }
           } else {
               logger.info("Source Zone must be LAN");
           }          
        }
        if (lanwantrafficrule.get("Source address") != null) {
            String  []SourceAddrList = lanwantrafficrule.get("Source address").split("\\.");
            trafficrulessourceaddress1.setValue(SourceAddrList[0]);
            trafficrulessourceaddress2.setValue(SourceAddrList[1]);
            trafficrulessourceaddress3.setValue(SourceAddrList[2]);
            trafficrulessourceaddress4.setValue(SourceAddrList[3]); 
               
        } 
        if (lanwantrafficrule.get("Source address range") != null) {
            trafficrulesourcerange.selectRadio("range_addr");  
        }
         
        if (lanwantrafficrule.get("Source address start") != null) {
            String  []SourceAddrList = lanwantrafficrule.get("Source address start").split("\\.");
            trafficrulesourceaddressstart1.setValue(SourceAddrList[0]);
            trafficrulesourceaddressstart2.setValue(SourceAddrList[1]);
            trafficrulesourceaddressstart3.setValue(SourceAddrList[2]);
            trafficrulesourceaddressstart4.setValue(SourceAddrList[3]); 
               
        } 
        if (lanwantrafficrule.get("Source address end") != null) {
            String  []SourceAddrList = lanwantrafficrule.get("Source address end").split("\\.");
            trafficrulesourceaddressend1.setValue(SourceAddrList[0]);
            trafficrulesourceaddressend2.setValue(SourceAddrList[1]);
            trafficrulesourceaddressend3.setValue(SourceAddrList[2]);
            trafficrulesourceaddressend4.setValue(SourceAddrList[3]); 
               
        } 
        
        if (lanwantrafficrule.get("Source port") != null) {
            trafficrulessourceport.setValue(lanwantrafficrule.get("Source port"));            
        } 
       
        if (lanwantrafficrule.get("Source MAC address") != null) {
            
            trafficrulessourcemac.setValue(lanwantrafficrule.get("Source MAC address")); 
   
        }
        Selenide.sleep(3000);
       if (lanwantrafficrule.get("Destination Zone") != null) {
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", trafficruledestnationaddressstart4);
           logger.info("Destination Zone");
            if (lanwantrafficrule.get("Destination Zone") == "LAN") {
                trafficrulesdeslan.selectRadio("LAN"); 
            } else if (lanwantrafficrule.get("Destination Zone") == "WAN") {
                trafficrulesdeswan.selectRadio("WAN");                
            } else if (lanwantrafficrule.get("Destination Zone") == "Device") {
                trafficrulesdesdevice.selectRadio("Device");                
            }            
        }
       if (lanwantrafficrule.get("Destnation Address") != null) {
          
           String  []DesAddrList = lanwantrafficrule.get("Destnation Address") .split("\\.");
           trafficrulesdesaddress1.setValue(DesAddrList[0]);
           trafficrulesdesaddress2.setValue(DesAddrList[1]);
           trafficrulesdesaddress3.setValue(DesAddrList[2]);
           trafficrulesdesaddress4.setValue(DesAddrList[3]); 
           //trafficrulesdesaddress.setValue(lanwantrafficrule.get("Destnation Address"));
           
       } 
       if (lanwantrafficrule.get("Destnation address range") != null) {
           trafficrulesdestnationrange.selectRadio("range_addr");  
       }
        
       if (lanwantrafficrule.get("Destnation address start") != null) {
           String  []DestnationAddrList = lanwantrafficrule.get("Destnation address start").split("\\.");
           trafficruledestnationaddressstart1.setValue(DestnationAddrList[0]);
           trafficruledestnationaddressstart2.setValue(DestnationAddrList[1]);
           trafficruledestnationaddressstart3.setValue(DestnationAddrList[2]);
           trafficruledestnationaddressstart4.setValue(DestnationAddrList[3]); 
              
       } 
       if (lanwantrafficrule.get("Destnation address end") != null) {
           String  []DestinationAddrList = lanwantrafficrule.get("Destnation address end").split("\\.");
           trafficruledestnationaddressend1.setValue(DestinationAddrList[0]);
           trafficruledestnationaddressend2.setValue(DestinationAddrList[1]);
           trafficruledestnationaddressend3.setValue(DestinationAddrList[2]);
           trafficruledestnationaddressend4.setValue(DestinationAddrList[3]); 
              
       } 
       logger.info("uuuuuuuuuuuuuuuuuuuuuuuuuuu");
       logger.info(lanwantrafficrule.get("Destination Port"));
       if (lanwantrafficrule.get("Destination Port") != null) {
           logger.info("uuuuuuuuuuuuuuuuuuuuuuuuuuu");
           trafficrulesdesport.setValue(lanwantrafficrule.get("Destination Port"));            
       } 
       ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", trafficrulesapplybutton);
       if (lanwantrafficrule.get("Action") != null) {          
           if(lanwantrafficrule.get("Action").contentEquals("ACCEPT")) {
               logger.info("Action1");
               Selenide.sleep(20000);
               trafficrulesaction.click();
               logger.info("Action1");
               trafficrulesactionaccept.click();
           }else if(lanwantrafficrule.get("Action").contentEquals("DROP")) {
               logger.info("Action2");
             
               trafficrulesaction.click();          
               trafficrulesactiondrop.click();
           }
       }
       logger.info("Apply");
       trafficrulesapplybutton.click();
       String firewalllistname = "//span[@class ='text-bold']"; 
       logger.info(String.format("Check the rule exist or not"));
       MyCommonAPIs.waitReady();
       List<String> lsFirewallrules= MyCommonAPIs.getTexts(firewalllistname);
       for (String name : lsFirewallrules) {
         int existornot =  name.compareTo(lanwantrafficrule.get("Rule Name"));
         if(existornot == 0){
             result = true;  
         }else {
             result = false;   
         }
       }
       return result;
    }
    public void DeleteFirewall(String firewallname) {
      String firewalllistname = "//span[@class ='text-bold']";
      String Checkboxnumber = "(//input[@type =\"checkbox\"])";
     
        logger.info(String.format("getFirewallRules..."));
        MyCommonAPIs.waitReady();
        List<String> lsFirewallrules= MyCommonAPIs.getTexts(firewalllistname);
        int i = 1;
        for (String name : lsFirewallrules) {
         
            i = i + 1;
            System.out.print(name.indexOf(firewallname));
             if( name.indexOf(firewallname) != -1 ) {
                 Checkboxnumber = Checkboxnumber +"[" + String.valueOf(i)+"]"; 
                 trafficruleselectone = $x(Checkboxnumber);
                 MyCommonAPIs.sleep(4000); 
                 logger.info(Checkboxnumber);
                 logger.info(String.format(": %s", name));
                 trafficruleselectone.selectRadio("on");
                 
             }
        }
        trafficrulesdeletebutton.click();
        trafficrulesokbutton.click();
    }
    public  void DeleteallTrafficRules () {
        if ( trafficrulesselectall.exists()) {                     
            trafficrulesselectall.selectRadio("on");
            trafficrulesdeletebutton.click();
            trafficrulesokbutton.click();
        }
        
    }
    public  boolean EditLANWANTrafficRuleByName (Map<String, String> lanwantrafficrule) {
        //open(LoginURL)
        String firewalllistname = "//span[@class ='text-bold']";
        //String trafficruleedit = trafficruleeditbutton.getSearchCriteria();
        String trafficruleedit = "#traffic_rules_1_";
        logger.info(trafficruleedit);
          logger.info(String.format("getFirewallRules..."));
          MyCommonAPIs.waitReady();
          List<String> lsFirewallrules= MyCommonAPIs.getTexts(firewalllistname);
          int i = 0;
          for (String name : lsFirewallrules) {
              logger.info(name);
              i = i + 1;
              System.out.print(name.indexOf(lanwantrafficrule.get("Rule Name")));
              if( name.indexOf(lanwantrafficrule.get("Rule Name")) != -1 ) {
                  trafficruleedit = trafficruleedit + String.valueOf(i); 
                  trafficruleeditbutton = $(trafficruleedit);
                  MyCommonAPIs.sleep(4000); 
                  logger.info(trafficruleedit);
                  logger.info(String.format(": %s", name));
                  trafficruleeditbutton.click();
                  
              }
         }

        if (lanwantrafficrule.get("Rule Name") != null) {
            trafficrulesname.setValue(lanwantrafficrule.get("Rule Name"));
        }  
        Selenide.sleep(4000);
        if (lanwantrafficrule.get("Protocol") != null) {
            trafficrulesprotocol.click();
            if (lanwantrafficrule.get("Protocol").equalsIgnoreCase("All")) {
                trafficrulesprotocolall.click();
            }else if(lanwantrafficrule.get("Protocol").equalsIgnoreCase("ICMP")){
                trafficrulesprotocolicmp.click();               
            }else if(lanwantrafficrule.get("Protocol").equalsIgnoreCase("UDP")){
                trafficrulesprotocoludp.click();
            }else if(lanwantrafficrule.get("Protocol").equalsIgnoreCase("TCP")){
                trafficrulesprotocoltcp.click();
            }else if(lanwantrafficrule.get("Protocol").equalsIgnoreCase("TCP+UDP")){
                trafficrulesprotocoludptcp.click();
            }  
            Selenide.sleep(4000);
        } 
        if (lanwantrafficrule.get("Source Zone") != null) {
            logger.info("Source Zone");
           if(lanwantrafficrule.get("Source Zone").contentEquals("LAN")) {
               logger.info("111111111111111"); 
               
           } else {
               logger.info("Source Zone must be LAN");
           }          
        }
        if (lanwantrafficrule.get("Source address") != null) {
            String  []SourceAddrList = lanwantrafficrule.get("Source address").split("\\.");
            trafficrulessourceaddress1.setValue(SourceAddrList[0]);
            trafficrulessourceaddress2.setValue(SourceAddrList[1]);
            trafficrulessourceaddress3.setValue(SourceAddrList[2]);
            trafficrulessourceaddress4.setValue(SourceAddrList[3]); 
            //trafficrulessourceaddress.setValue(lanwantrafficrule.get("Source address"));   
        } 
        if (lanwantrafficrule.get("Source address range") != null) {
            trafficrulesourcerange.selectRadio("range_addr");  
        }
         
        if (lanwantrafficrule.get("Source address start") != null) {
            String  []SourceAddrList = lanwantrafficrule.get("Source address start").split("\\.");
            trafficrulesourceaddressstart1.setValue(SourceAddrList[0]);
            trafficrulesourceaddressstart2.setValue(SourceAddrList[1]);
            trafficrulesourceaddressstart3.setValue(SourceAddrList[2]);
            trafficrulesourceaddressstart4.setValue(SourceAddrList[3]); 
               
        } 
        if (lanwantrafficrule.get("Source address end") != null) {
            String  []SourceAddrList = lanwantrafficrule.get("Source address end").split("\\.");
            trafficrulesourceaddressend1.setValue(SourceAddrList[0]);
            trafficrulesourceaddressend2.setValue(SourceAddrList[1]);
            trafficrulesourceaddressend3.setValue(SourceAddrList[2]);
            trafficrulesourceaddressend4.setValue(SourceAddrList[3]); 
               
        }
        
        if (lanwantrafficrule.get("Source port") != null) {
            trafficrulessourceport.setValue(lanwantrafficrule.get("Source port"));            
        } 
        if (lanwantrafficrule.get("Source MAC address") != null) {
            trafficrulessourcemac.setValue(lanwantrafficrule.get("Source MAC address"));            
        }
        
       if (lanwantrafficrule.get("Destination Zone") != null) {
           logger.info("Destination Zone");
            if (lanwantrafficrule.get("Destination Zone") == "LAN") {
                trafficrulesdeslan.selectRadio("LAN"); 
            } else if (lanwantrafficrule.get("Destination Zone") == "WAN") {
                trafficrulesdeswan.selectRadio("WAN");                
            } else if (lanwantrafficrule.get("Destination Zone") == "Device") {
                trafficrulesdesdevice.selectRadio("Device");                
            }            
        }
       if (lanwantrafficrule.get("Destnation Address") != null) {
           String  []DesAddrList = lanwantrafficrule.get("Destnation Address") .split("\\.");
           trafficrulesdesaddress1.setValue(DesAddrList[0]);
           trafficrulesdesaddress2.setValue(DesAddrList[1]);
           trafficrulesdesaddress3.setValue(DesAddrList[2]);
           trafficrulesdesaddress4.setValue(DesAddrList[3]); 
           //trafficrulesdesaddress.setValue(lanwantrafficrule.get("Destnation Address"));
           
       } 
       if (lanwantrafficrule.get("Destnation address range") != null) {
           trafficrulesdestnationrange.selectRadio("range_addr");  
       }
        
       if (lanwantrafficrule.get("Destnation address start") != null) {
           String  []DestnationAddrList = lanwantrafficrule.get("Destnation address start").split("\\.");
           trafficruledestnationaddressstart1.setValue(DestnationAddrList[0]);
           trafficruledestnationaddressstart2.setValue(DestnationAddrList[1]);
           trafficruledestnationaddressstart3.setValue(DestnationAddrList[2]);
           trafficruledestnationaddressstart4.setValue(DestnationAddrList[3]); 
              
       } 
       if (lanwantrafficrule.get("Destnation address end") != null) {
           String  []DestinationAddrList = lanwantrafficrule.get("Destnation address end").split("\\.");
           trafficruledestnationaddressend1.setValue(DestinationAddrList[0]);
           trafficruledestnationaddressend2.setValue(DestinationAddrList[1]);
           trafficruledestnationaddressend3.setValue(DestinationAddrList[2]);
           trafficruledestnationaddressend4.setValue(DestinationAddrList[3]); 
              
       } 
       
       if (lanwantrafficrule.get("Destination Port") != null) {
           trafficrulesdesport.setValue(lanwantrafficrule.get("Destination Port"));            
       } 
       ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", trafficrulesapplybutton);
       if (lanwantrafficrule.get("Action") != null) {          
           if(lanwantrafficrule.get("Action").contentEquals("ACCEPT")) {
               logger.info("Action1");
               Selenide.sleep(20000);
               trafficrulesaction.click();
               logger.info("Action1");
               trafficrulesactionaccept.click();
           }else if(lanwantrafficrule.get("Action").contentEquals("DROP")) {
               logger.info("Action2");
             
               trafficrulesaction.click();          
               trafficrulesactiondrop.click();
           }
       }
       logger.info("Apply");
       trafficrulesapplybutton.click();
       //String firewalllistname = "//span[@class ='text-bold']"; 
       logger.info(String.format("Check the rule exist or not"));
       MyCommonAPIs.waitReady();
       List<String> lsFirewallrules1= MyCommonAPIs.getTexts(firewalllistname);
       for (String name : lsFirewallrules1) {
         int existornot =  name.compareTo(lanwantrafficrule.get("Rule Name"));
         if(existornot == 0){
             result = true;  
         }else {
             result = false;   
         }
       }
       return result;
    }
    
    public boolean GetWarningWithWrongConfig(Map<String, String> lanwantrafficrule) {
        
        logger.info("GetWarningWithWrongConfig");
        boolean Result = false;
        trafficrulesaddbutton.click();
        if (lanwantrafficrule.get("Rule Name") != null) {
            trafficrulesname.setValue(lanwantrafficrule.get("Rule Name"));    
            Selenide.sleep(4000);
            if ( trafficruleswarning.exists()) {
                String WarningContent = trafficruleswarning.getText();
                logger.info("ddd222222223444444444444455666666333");
                logger.info("Source port:"+WarningContent);
                if  (WarningContent.contentEquals("The traffic rule name already exists.")) {
                    logger.info("ddd2222222234444444444444556666663335555555");
                    Result = true; 
                } else {
                    Result = false;
                }
            }else {
                Result = false;
            }
        } else {
            logger.info("Rule Name is null");
            
        }
        logger.info(lanwantrafficrule.get("Protocol"));
        if (lanwantrafficrule.get("Protocol") != null) {
            trafficrulesprotocol.click();
            if (lanwantrafficrule.get("Protocol").equalsIgnoreCase("All")) {
                logger.info("********************");
                trafficrulesprotocolall.click();
            }else if(lanwantrafficrule.get("Protocol").equalsIgnoreCase("ICMP")){
                trafficrulesprotocolicmp.click();               
            }else if(lanwantrafficrule.get("Protocol").equalsIgnoreCase("UDP")){
                trafficrulesprotocoludp.click();
            }else if(lanwantrafficrule.get("Protocol").equalsIgnoreCase("TCP")){
                trafficrulesprotocoltcp.click();
            }else if(lanwantrafficrule.get("Protocol").equalsIgnoreCase("TCP+UDP")){
                trafficrulesprotocoludptcp.click();
            }  
            Selenide.sleep(4000);
        } 
        if (lanwantrafficrule.get("Source Zone") != null) {
            logger.info("Source Zone");
           if(lanwantrafficrule.get("Source Zone").contentEquals("LAN")) {
               logger.info("111111111111111"); ; 
           } else {
               logger.info("Source Zone must be LAN");
           }          
        }
        if (lanwantrafficrule.get("Source address") != null) {
            String  []SourceAddrList = lanwantrafficrule.get("Source address").split("\\.");
            trafficrulessourceaddress1.setValue(SourceAddrList[0]);
            trafficrulessourceaddress2.setValue(SourceAddrList[1]);
            trafficrulessourceaddress3.setValue(SourceAddrList[2]);
            trafficrulessourceaddress4.setValue(SourceAddrList[3]); 
            if ( trafficruleswarning.exists()) {
                String WarningContent = trafficruleswarning.getText();
                logger.info("Source address Warning:"+WarningContent);
                if  (WarningContent.contentEquals("Invalid IP address. Please enter it again.")) {
                    Result = true; 
                } else {
                    Result = false;
                }
            } else {
                Result = false;
            }
               
        } 
        if (lanwantrafficrule.get("Source address range") != null) {
            trafficrulesourcerange.selectRadio("range_addr");  
        }
         
        if (lanwantrafficrule.get("Source address start") != null) {
            String  []SourceAddrList = lanwantrafficrule.get("Source address start").split("\\.");
            trafficrulesourceaddressstart1.setValue(SourceAddrList[0]);
            trafficrulesourceaddressstart2.setValue(SourceAddrList[1]);
            trafficrulesourceaddressstart3.setValue(SourceAddrList[2]);
            trafficrulesourceaddressstart4.setValue(SourceAddrList[3]);
            if ( trafficruleswarning.exists()) {
                String WarningContent = trafficruleswarning.getText();
                logger.info("Source address  start  Warning:"+WarningContent);
                if  (WarningContent.contentEquals("Invalid IP address. Please enter it again.")) {
                    Result = true; 
                } else {
                    Result = false;
                }
               
              } else {
                  Result = false;
              }
        }
            
        if (lanwantrafficrule.get("Source address end") != null) {
            String  []SourceAddrList1 = lanwantrafficrule.get("Source address end").split("\\.");
            trafficrulesourceaddressend1.setValue(SourceAddrList1[0]);
            trafficrulesourceaddressend2.setValue(SourceAddrList1[1]);
            trafficrulesourceaddressend3.setValue(SourceAddrList1[2]);
            trafficrulesourceaddressend4.setValue(SourceAddrList1[3]); 
               
            if ( trafficruleswarning.exists()) {
                String WarningContent = trafficruleswarning.getText();
                logger.info("Source address  end Warning:"+WarningContent);
                if  (WarningContent.contentEquals("Invalid IP address. Please enter it again.")) {
                    Result = true; 
                } else {
                    Result = false;
                }
            }else {
                Result = false;
            }
        } 
        
        if (lanwantrafficrule.get("Source port") != null) {
            trafficrulessourceport.setValue(lanwantrafficrule.get("Source port"));
            logger.info("ddd222222223444444444444455666666");
            Selenide.sleep(3000);
            System.out.print(trafficruleswarning.exists());
            
            if ( trafficruleswarning.exists()) {
                String WarningContent = trafficruleswarning.getText();
                logger.info("ddd222222223444444444444455666666333");
                logger.info("Source port:"+WarningContent);
                if  (WarningContent.contentEquals("Enter a source port from 1 to 65535.")) {
                    logger.info("ddd2222222234444444444444556666663335555555");
                    Result = true; 
                } else {
                    Result = false;
                }
            }else {
                Result = false;
            }
        } 
       
        if (lanwantrafficrule.get("Source MAC address") != null) {
            
            trafficrulessourcemac.setValue(lanwantrafficrule.get("Source MAC address")); 
            if ( trafficruleswarning.exists()) {
                String WarningContent = trafficruleswarning.getText();
                logger.info("Source MAC address:"+WarningContent);
                if  (WarningContent.contentEquals("Invalid source MAC address.")) {
                    Result = true; 
                } else {
                    Result = false;
                }
            }else {
                Result = false;
            }
   
        }
        
       if (lanwantrafficrule.get("Destination Zone") != null) {
           logger.info("Destination Zone");
            if (lanwantrafficrule.get("Destination Zone") == "LAN") {
                trafficrulesdeslan.selectRadio("LAN"); 
            } else if (lanwantrafficrule.get("Destination Zone") == "WAN") {
                trafficrulesdeswan.selectRadio("WAN");                
            } else if (lanwantrafficrule.get("Destination Zone") == "Device") {
                trafficrulesdesdevice.selectRadio("Device");                
            }            
        }
       if (lanwantrafficrule.get("Destnation Address") != null) {
           String  []DesAddrList = lanwantrafficrule.get("Destnation Address") .split("\\.");
           trafficrulesdesaddress1.setValue(DesAddrList[0]);
           trafficrulesdesaddress2.setValue(DesAddrList[1]);
           trafficrulesdesaddress3.setValue(DesAddrList[2]);
           trafficrulesdesaddress4.setValue(DesAddrList[3]); 
           if ( trafficruleswarning.exists()) {
               String WarningContent = trafficruleswarning.getText();
               logger.info("Destnation address Warning:"+WarningContent);
               if  (WarningContent.contentEquals("Invalid IP address. Please enter it again.")) {
                   Result = true; 
               } else {
                   Result = false;
               }
           }else {
               Result = false;
           }
           
       } 
       if (lanwantrafficrule.get("Destnation address range") != null) {
           trafficrulesdestnationrange.selectRadio("range_addr");  
       }
        
       if (lanwantrafficrule.get("Destnation address start") != null) {
           String  []DestnationAddrList = lanwantrafficrule.get("Destnation address start").split("\\.");
           trafficruledestnationaddressstart1.setValue(DestnationAddrList[0]);
           trafficruledestnationaddressstart2.setValue(DestnationAddrList[1]);
           trafficruledestnationaddressstart3.setValue(DestnationAddrList[2]);
           trafficruledestnationaddressstart4.setValue(DestnationAddrList[3]); 
           if ( trafficruleswarning.exists()) {
               String WarningContent = trafficruleswarning.getText();
               logger.info("Destnation address start Warning:"+WarningContent);
               if  (WarningContent.contentEquals("Invalid IP address. Please enter it again.")) {
                   Result = true; 
               } else {
                   Result = false;
               }
           }else {
               Result = false;
           }
       }
              
       
       if (lanwantrafficrule.get("Destnation address end") != null) {
           String  []DestinationAddrList = lanwantrafficrule.get("Destnation address end").split("\\.");
           trafficruledestnationaddressend1.setValue(DestinationAddrList[0]);
           trafficruledestnationaddressend2.setValue(DestinationAddrList[1]);
           trafficruledestnationaddressend3.setValue(DestinationAddrList[2]);
           trafficruledestnationaddressend4.setValue(DestinationAddrList[3]); 
           if ( trafficruleswarning.exists()) {
               String WarningContent = trafficruleswarning.getText();
               logger.info("Destnation address start end:"+WarningContent);
               if  (WarningContent.contentEquals("Invalid IP address. Please enter it again.")) {
                   Result = true; 
               } else {
                   Result = false;
               }
           }
       }
       
       logger.info(lanwantrafficrule.get("Destination Port"));
       if (lanwantrafficrule.get("Destination Port") != null) {
           
           trafficrulesdesport.setValue(lanwantrafficrule.get("Destination Port")); 
           Selenide.sleep(3000);
           System.out.print(trafficruleswarning.exists());
           if ( trafficruleswarning.exists()) {
               String WarningContent = trafficruleswarning.getText();
               logger.info("Destination port:"+WarningContent);
               if  (WarningContent.contentEquals("Enter a destination port from 1 to 65535.")) {
                   Result = true; 
               } else {
                   Result = false;
               }
           }else {
               Result = false;
           }
       } 
    
         return Result;
      }
           
    public String GetOjectText(String ObjectName, String Setvalue) {
        //open(LoginURL);
        logger.info("Open Firewall Traffic Rules Page");
        String Result="";
        trafficrulesaddbutton.click();
        if (ObjectName.contentEquals("Rule Name")){
            trafficrulesname.setValue(Setvalue);
            Result =  trafficrulesname.getValue();  
        }
       return Result;
    }
}
  
