package businessrouter.webpageoperation;
import static com.codeborne.selenide.Selenide.$x;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.openqa.selenium.Keys;

import com.codeborne.selenide.Selenide;

import businessrouter.webelements.BrAllMenueElements;
import businessrouter.webelements.BrFirewallPortForwardingTriggeringElements;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;

public class BrFirewallPortForwardingTriggeringPage extends BrFirewallPortForwardingTriggeringElements {
    final static Logger logger = Logger.getLogger("BrFirewallPortForwardingTriggeringPage");
    boolean result = false;
    public void OpenFirewallPortForwardingTriggeringPage() {
        //open(LoginURL);
        logger.info("Open Firewall Port Forwarding and Triggering Page");
        BrAllMenueElements BrAllMenueElements = new BrAllMenueElements();
        BrAllMenueElements.advanced.click();
        BrAllMenueElements.Firewall.click();
        if (WebportalParam.DUTType.contentEquals("BR500")) {
            BrAllMenueElements.PortForwardingandTriggering.click();
        } else if(WebportalParam.DUTType.contentEquals("BR100")) {
            logger.info("99999999999999999999999999"); 
            BrAllMenueElements.PortForwardingandTriggeringbr100.click();
            
        }
        
    }
    public void EnableFirewallPortForwardingOrPortTriggering(int flag) {
        //open(LoginURL);
        //String TT = protforwardingenable.getSelectedValue();
        //logger.info(TT);
        System.out.print(flag);
        if (flag == 0) {
            logger.info("Enable Firewall Port Forwarding ");
            protforwardingenable.selectRadio("0");
           
            
        }else if(flag == 1) {
            logger.info("Enable Firewall Port Triggering"); 
            //prottriggeringenable.attr("value");
            //prottriggeringenable.getAttribute("value");
            //prottriggeringenable.getSelectedText();
            //System.out.println(prottriggeringenable.attr("value"));
            //System.out.println(prottriggeringenable.getAttribute("value"));
           // System.out.print(prottriggeringenable.getSelectedText());
            //System.out.print(prottriggeringenable.attr("disabled"));
            //if porttriggeringaddbutton.()
            //System.out.print(porttriggeringaddbutton.exists());
            //System.out.print(prottriggeringenable.isEnabled());
            
           // System.out.print(prottriggeringenable.isDisplayed());
           
             
            // if(prottriggeringenable.isEnabled()) {
            //prottriggeringenable.sendKeys(Keys.SPACE);
            //Selenide.sleep(10000);
                 prottriggeringenable.selectRadio("1");
                 //prottriggeringenable.attr("value");
                 
             //}

                 Selenide.sleep(10000);
                 porttriggeringapplybutton.click();  
                 
          
                logger.info("Enable Firewall Port Triggering");
               
        }else {
            logger.info(" Port Forwarding and Triggering flag is wrong");  
        }
        logger.info(" Port Forwarding and Triggering successfully");
        
    }
    
    public void AddcustomerPortForwardingRule(Map<String, String> portforwardingrule) {
        logger.info("Add customer Portforwarding rule");      
        portforwardingcustomadd.click();
        if (!portforwardingcustomservicename.exists()) {
            portforwardingcustomadd.click();  
        }
        Selenide.sleep(2000);
        portforwardingcustomprotocol   = $x("(//div[@class ='ant-select-selection__rendered'])[3]");
        if (portforwardingcustomprotocol.exists()) {
            portforwardingcustomprotocol   = $x("(//div[@class ='ant-select-selection__rendered'])[3]");
            
        } else {
            portforwardingcustomprotocol   = $x("(//div[@class ='ant-select-selection__rendered'])[2]");
            
        }
        if (portforwardingrule.get("Rule Name") != null) {
            portforwardingcustomservicename.setValue(portforwardingrule.get("Rule Name"));
        } else {
            logger.info("Rule Name is null");
            
        }
        Selenide.sleep(5000);
        if (portforwardingrule.get("Protocol") != null) {
            portforwardingcustomprotocol.click();
            if (portforwardingrule.get("Protocol").equalsIgnoreCase("TCP/UDP")) {
                portforwardingcustomprotocoltcpudp.click();               
            }else if(portforwardingrule.get("Protocol").equalsIgnoreCase("TCP")){
                portforwardingcustomprotocoltcp.click();
            }else if(portforwardingrule.get("Protocol").equalsIgnoreCase("UDP")){
                portforwardingcustomprotocoludp.click();
            }  
            Selenide.sleep(4000);
        } else {
            logger.info("Default Protocol is used ");
            
        }
        if (portforwardingrule.get("ExternalPort") != null) {
            portforwardingexternalport.setValue(portforwardingrule.get("ExternalPort"));
         } else {
            logger.info("ExternalPort is null");
         } 
        if (portforwardingrule.get("InternalPort") != null) {
            Selenide.sleep(2000);
            logger.info("Default Protocol is used 1111111111111111111111");
            //logger.info(portforwardinginternalportcheckbox.attr("href"));
            portforwardinginternalportcheckbox.sendKeys(Keys.SPACE);
            portforwardinginternalport.setValue(portforwardingrule.get("InternalPort"));
         } else {
            logger.info("InternalPort is null");
         } 
        if (portforwardingrule.get("Internal IP Address") != null) {
            String  []ipreservationlist = portforwardingrule.get("Internal IP Address").split("\\.");   
            portforwardingintelipaddr1.setValue(ipreservationlist[0]);
            portforwardingintelipaddr2.setValue(ipreservationlist[1]);
            portforwardingintelipaddr3.setValue(ipreservationlist[2]);
            portforwardingintelipaddr4.setValue(ipreservationlist[3]); 
        } else {
            logger.info("Reservation IP Address is null");
            
        }
        portforwardingapply.click();
    }
    public boolean AddPortForwardingruleYesOrNo() {
       
        String tt11 =  portforwardingcustomadd.attr("disabled");
        logger.info(tt11);
        if (tt11 == null) {
            //logger.info("1111111111111111111111");
            logger.info("Can add port forwarding rules");
            result = true; 
            
        }else if (tt11.contentEquals("true")) {
            logger.info("Can't add port forwarding rules");
            result = false;
        } 
        return result;
    
    }
    
    public void DeleteForwardingRule(String forwardingname) {
        String ForwardingListName = "(//tr[@class='ant-table-row  ant-table-row-level-0'])";
        String Checkboxnumber = "(//input[@type = 'checkbox'])";
       
          logger.info(String.format("Delete Forwarding rule..."));
          MyCommonAPIs.waitReady();
          List<String> LsForwardingrules= MyCommonAPIs.getTexts(ForwardingListName);
          System.out.println(LsForwardingrules);
          int i = 1;
          for (String name : LsForwardingrules) {
           
              i = i+1;
              String []ForwardingElemList = name.split(" ");
               if( ForwardingElemList[1].contentEquals(forwardingname) ) {
                   Checkboxnumber = Checkboxnumber +"[" + String.valueOf(i)+"]"; 
                   System.out.print(Checkboxnumber);
                   portforwardingselectone = $x(Checkboxnumber);
                   MyCommonAPIs.sleep(4000); 
                   logger.info(Checkboxnumber);
                   logger.info(String.format(": %s", name));
                   //portforwardingselectone.selectRadio(ForwardingElemList[0]);
                   portforwardingselectone.selectRadio("on");
                   
               }
          }
          portforwardingcustomdelete.click();
          portforwardingokbutton.click();
      }
        
    public boolean ForwardingRuleExistOrNot(String forwardingname) {
        String ForwardingListName = "(//tr[@class='ant-table-row  ant-table-row-level-0'])";
          logger.info("Forwarding rule exist or not");
          MyCommonAPIs.waitReady();
          List<String> LsForwardingrules= MyCommonAPIs.getTexts(ForwardingListName);
          int i = 2;
          for (String name : LsForwardingrules) {
           
              i = i+1;
              String []ForwardingElemList = name.split(" ");
              logger.info(ForwardingElemList[1]);
               if( ForwardingElemList[1].contentEquals(forwardingname) ) {
                   result = true;
                   logger.info("Forwarding rule exist");
               } else {
                   result = false;
                   logger.info("Forwarding rule doesn't exist");
               }
          }
          return result;
      }
    public void AddcustomertriggeringRule(Map<String, String> triggeringrule) {
        logger.info("Add customer triggeringrule rule");      
        porttriggeringaddbutton.click();
        if (!porttriggeringruleservname.exists()) {
            porttriggeringaddbutton.click();  
        }
        Selenide.sleep(2000);
        if (triggeringrule.get("Rule Name") != null) {
            porttriggeringruleservname.setValue(triggeringrule.get("Rule Name"));
        } else {
            logger.info("Rule Name is null");
            
        }
        Selenide.sleep(5000);
        if (triggeringrule.get("Service User") != null) {
            porttriggeringruleserviceuser.click();
            if (triggeringrule.get("Service User").equalsIgnoreCase("Any")) {
                porttriggeringruleserviceuserany.click();               
            }else if(triggeringrule.get("Service User").equalsIgnoreCase("Single Address")){
                porttriggeringruleserviceusersingle.click();
                String  []ServiceIPList = triggeringrule.get("Service IP").split("\\.");   
                porttriggeringruleserviceip1.setValue(ServiceIPList[0]);
                porttriggeringruleserviceip2.setValue(ServiceIPList[1]);
                porttriggeringruleserviceip3.setValue(ServiceIPList[2]);
                porttriggeringruleserviceip4.setValue(ServiceIPList[3]);
            } 
            Selenide.sleep(4000);
        } else {
            logger.info("Default Service User is used ");
            
        }
        if (triggeringrule.get("Service Type") != null) {
            porttriggeringruleservicetype.click();
            if (triggeringrule.get("Service Type").equalsIgnoreCase("TCP")) {
                porttriggeringruleservicetypetcp.click();               
            }else if(triggeringrule.get("Service Type").equalsIgnoreCase("UDP")){
                porttriggeringruleservicetypeudp.click();
            }
         } else {
             logger.info("Default Service Type is used ");
         } 
        if (triggeringrule.get("Triggering Port") != null) {
            Selenide.sleep(2000);
            porttriggeringport.setValue(triggeringrule.get("Triggering Port"));
         } else {
            logger.info("Triggering Port is null");
         } 
        if (triggeringrule.get("Connection Type") != null) {
            porttriggeringconnectiontype.click();
            if (triggeringrule.get("Connection Type").equalsIgnoreCase("TCP/UDP")) {
                porttriggeringconnectiontypetcpudp.click();               
            }else if(triggeringrule.get("Connection Type").equalsIgnoreCase("TCP")){
                porttriggeringconnectiontypetcp.click();
            }else if (triggeringrule.get("Connection Type").equalsIgnoreCase("UDP")) {
                porttriggeringconnectiontypeudp.click();
            }
         } else {
             logger.info("Default Service Type is used ");
         } 
        if (triggeringrule.get("Starting Port") != null) {
            porttriggeringstartingport.setValue(triggeringrule.get("Starting Port"));
        
        } else {
            logger.info("Starting Port is null");
            
        }
        if (triggeringrule.get("Ending Port") != null) {
            porttriggeringendingport.setValue(triggeringrule.get("Ending Port"));
        
        } else {
            logger.info("Ending Port is null");
            
        }
        porttriggeringendingruleapply.click();
    }
    public void DeleteTriggeringRule(String triggeringname) {
        String TriggeringListName = "(//tr[@class='ant-table-row  ant-table-row-level-0'])";
        String Checkboxnumber = "(//input[@type = 'checkbox'])";
       
          logger.info("Delete Forwarding rule...");
          MyCommonAPIs.waitReady();
          Selenide.sleep(2000);
          List<String> LsTriggeringrules= MyCommonAPIs.getTexts(TriggeringListName);
          int i = 2;
          for (String name : LsTriggeringrules) {
              
             
              logger.info(name);
              i = i+1;              
              String []TriggerngElemList = name.split(" ");
              logger.info(TriggerngElemList[0]);
              String []TriggeringRuleEleList =TriggerngElemList[0].split("\n");
              logger.info(TriggeringRuleEleList[0]);       
              logger.info(TriggeringRuleEleList[1]);
             
              
               if( TriggeringRuleEleList[1].contentEquals(triggeringname) ) {
                   Checkboxnumber = Checkboxnumber +"[" + String.valueOf(i)+"]"; 
                   System.out.print(Checkboxnumber);
                   portforwardingselectone = $x(Checkboxnumber);
                   MyCommonAPIs.sleep(4000); 
                   logger.info(Checkboxnumber);
                   //logger.info(String.format(": %s", name));
                   portforwardingselectone.selectRadio("on");
                   
               }
          }
          porttriggeringdeletebutton.click();
          portforwardingokbutton.click();
      }
    public void EditcustomerPortForwardingRule(Map<String, String> portforwardingrule) {
        logger.info("Add customer Portforwarding rule");  
        String ForwardingListName = "(//tr[@class='ant-table-row  ant-table-row-level-0'])";
        String Checkboxnumber = "(//input[@type = 'checkbox'])";
       
          logger.info(String.format("Edit Forwarding rule..."));
          MyCommonAPIs.waitReady();
          List<String> LsForwardingrules= MyCommonAPIs.getTexts(ForwardingListName);
          int lsnumber =  LsForwardingrules.size();
          int i = 1;
          for (String name : LsForwardingrules) {
           
              i = i+1;
              String []ForwardingElemList = name.split(" ");
               if( ForwardingElemList[1].contentEquals(portforwardingrule.get("Rule Name")) ) {
                   Checkboxnumber = Checkboxnumber +"[" + String.valueOf(i)+"]"; 
                   System.out.print(Checkboxnumber);
                   portforwardingselectone = $x(Checkboxnumber);
                   MyCommonAPIs.sleep(4000); 
                   logger.info(Checkboxnumber);
                   logger.info(String.format(": %s", name));
                  // portforwardingselectone.selectRadio(ForwardingElemList[0]);
                   portforwardingselectone.selectRadio("on");
                   
               }
          }
        
        portforwardingcustomedit.click();
        if (!portforwardingcustomservicename.exists()) {
            portforwardingcustomedit.click();  
        }
        Selenide.sleep(2000);
        portforwardingcustomprotocol   = $x("(//div[@class ='ant-select-selection__rendered'])[3]");
        if (portforwardingcustomprotocol.exists()) {
            portforwardingcustomprotocol   = $x("(//div[@class ='ant-select-selection__rendered'])[3]");
            
        } else {
            portforwardingcustomprotocol   = $x("(//div[@class ='ant-select-selection__rendered'])[2]");
            
        }
        if (portforwardingrule.get("Rule Name") != null) {
            portforwardingcustomservicename.setValue(portforwardingrule.get("Rule Name"));
        } else {
            logger.info("Rule Name is null");
            
        }
        Selenide.sleep(5000);
        if (portforwardingrule.get("Protocol") != null) {
            portforwardingcustomprotocol.click();
            if (portforwardingrule.get("Protocol").equalsIgnoreCase("TCP/UDP")) {
                portforwardingcustomprotocoltcpudp.click();               
            }else if(portforwardingrule.get("Protocol").equalsIgnoreCase("TCP")){
                portforwardingcustomprotocoltcp.click();
            }else if(portforwardingrule.get("Protocol").equalsIgnoreCase("UDP")){
                portforwardingcustomprotocoludp.click();
            }  
            Selenide.sleep(4000);
        } else {
            logger.info("Default Protocol is used ");
            
        }
        if (portforwardingrule.get("ExternalPort") != null) {
            portforwardingexternalport.setValue(portforwardingrule.get("ExternalPort"));
         } else {
            logger.info("ExternalPort is null");
         } 
        if (portforwardingrule.get("InternalPort") != null) {
            Selenide.sleep(2000);
            logger.info("Default Protocol is used 1111111111111111111111");
            //logger.info(portforwardinginternalportcheckbox.attr("href"));
            portforwardinginternalportcheckbox.sendKeys(Keys.SPACE);
            portforwardinginternalport.setValue(portforwardingrule.get("InternalPort"));
         } else {
            logger.info("InternalPort is null");
         } 
        if (portforwardingrule.get("Internal IP Address") != null) {
            String  []ipreservationlist = portforwardingrule.get("Internal IP Address").split("\\.");   
            portforwardingintelipaddr1.setValue(ipreservationlist[0]);
            portforwardingintelipaddr2.setValue(ipreservationlist[1]);
            portforwardingintelipaddr3.setValue(ipreservationlist[2]);
            portforwardingintelipaddr4.setValue(ipreservationlist[3]); 
        } else {
            logger.info("Reservation IP Address is null");
            
        }
        if (portforwardingrule.get("attached devices") != null) {
           // String list = "(//span[@class = 'ant-table-row-indent indent-level-0'])";
            String Checkboxnumber1 = "(//input[@type ='radio'])";
            //int allnumber = LsForwardingrules.size();
            logger.info("Get attached devices...");
            MyCommonAPIs.waitReady();
            Selenide.sleep(2000);
            List<String> LsAlForwardingrules1= MyCommonAPIs.getTexts(ForwardingListName);
            
              
              int j =  2 ;
              int t = 0;
              for (String name : LsAlForwardingrules1) {
                  if (WebportalParam.DUTType.contentEquals("BR500")) {
                      t = t + 1;
                  }
                  j = j + 1;
                  Checkboxnumber1 = "(//input[@type ='radio'])";
                  String []AttachDeviceElemList = name.split(" ");
                  logger.info(AttachDeviceElemList[0]);
                  if (AttachDeviceElemList[0].contentEquals(portforwardingrule.get("attached devices"))){
                      Checkboxnumber1 = Checkboxnumber1 +"[" + String.valueOf(j)+"]"; 
                      System.out.print(Checkboxnumber1);
                      portforwardingselectone = $x(Checkboxnumber1);
                      MyCommonAPIs.sleep(4000);
                      logger.info("dddddddd2232@@@@@@@@@@@@@@@@@@");
                      logger.info(Checkboxnumber1);
                      //logger.info(String.format(": %s", name));
                      logger.info(String.valueOf(t));
                      portforwardingselectone.selectRadio(String.valueOf(t));
                   }
                  
              }
        }
        portforwardingapply.click();
    }
}
