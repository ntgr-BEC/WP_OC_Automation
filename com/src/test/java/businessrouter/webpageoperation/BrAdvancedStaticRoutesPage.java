package businessrouter.webpageoperation;
import static com.codeborne.selenide.Selenide.$x;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.codeborne.selenide.Selenide;

import businessrouter.webelements.BrAdvancedStaticRoutesElements;
import businessrouter.webelements.BrAllMenueElements;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;

public class BrAdvancedStaticRoutesPage extends BrAdvancedStaticRoutesElements {
    final static Logger logger = Logger.getLogger("BrAdvancedStaticRoutesPage");
    public void OpenStaticRoutePage() {
        //open(LoginURL);
        BrAllMenueElements BrAllMenueElements = new BrAllMenueElements();
                
        logger.info("Open StaticRoutes Page");
        BrAllMenueElements.advanced.click();
        if (WebportalParam.DUTType.contentEquals("BR500")) {
            BrAllMenueElements.StaticRoutes.click();
        } else if(WebportalParam.DUTType.contentEquals("BR100")) {
            logger.info("99999999999999999999999999"); 
            BrAllMenueElements.StaticRoutesbr100.click();
            
        }
        
        
        //logger.info(BrURLParam.StaticRoutes);
        //Selenide.open(BrURLParam.StaticRoutes);
    }
    public  void AddStaticRule(Map<String, String> staticroute) {
        //open(LoginURL)
        //OpenStaticRoutePage();
        //MyCommonAPIs.sleep(10);
        
        //BrAllMenueElements BrAllMenueElements = new BrAllMenueElements();
        logger.info("Add static route");
       // BrAllMenueElements.advanced.click();
       // BrAllMenueElements.StaticRoutes.click();
        staticrouteaddbutton.click();
        Selenide.sleep(5000);
        if (staticroute.get("Route Name") != null) {
            staticroutename.setValue(staticroute.get("Route Name"));
        } else {
            logger.info("Route Name is null");
            
        }
        logger.info(staticroute.get("Destination IP Address"));
        if (staticroute.get("Destination IP Address") != null) {
            String deip = staticroute.get("Destination IP Address");
            String splitchar = "\\.";
            logger.info(deip);
            String  []diplist =deip.split(splitchar);
            logger.info(diplist[0]);
            System.out.println(diplist[0]);
            System.out.println(diplist[1]);
            System.out.println(diplist[2]);
            System.out.println(diplist[3]);
            staticroutedesipv41.setValue(diplist[0]);
            staticroutedesipv42.setValue(diplist[1]);
            staticroutedesipv43.setValue(diplist[2]);
            staticroutedesipv44.setValue(diplist[3]);   
        } else {
            logger.info("Destination IP Address is null");
            
        }
        
        if (staticroute.get("IP Subnet Mask") != null) {
            String  []ipmasklist = staticroute.get("IP Subnet Mask").split("\\.");   
            staticroutedesipmask1.setValue(ipmasklist[0]);
            staticroutedesipmask2.setValue(ipmasklist[1]);
            staticroutedesipmask3.setValue(ipmasklist[2]);
            staticroutedesipmask4.setValue(ipmasklist[3]);  
        } else {
            logger.info("IP Subnet Mask is null");
            
        }
        if (staticroute.get("Gateway IP Address") != null) {
            String  []ipgatewaylist = staticroute.get("Gateway IP Address").split("\\.");   
            staticroutedesgateway1.setValue(ipgatewaylist[0]);
            staticroutedesgateway2.setValue(ipgatewaylist[1]);
            staticroutedesgateway3.setValue(ipgatewaylist[2]);
            staticroutedesgateway4.setValue(ipgatewaylist[3]);  
        } else {
            logger.info("Gateway IP Address is null");
            
        }
        if (staticroute.get("Metric") != null) {
            staticroutemetric.setValue(staticroute.get("Metric"));
        }
        else {
            logger.info("Metric  is null");
            
        }
        staticrouteapplybutton.click();
    
    }
    
    public boolean DeleteStaticRule(String statiroutename) {
        String StaticRouteName = "//tr[@class='ant-table-row  ant-table-row-level-0']/td[4]";
        String Checkboxnumber = "(//input[@class = 'ant-radio-input'])";
        boolean Result = true; 
          logger.info(String.format("DeleteStaticRule start"));
          MyCommonAPIs.waitReady();
          List<String> LsStaticRouteNames= MyCommonAPIs.getTexts(StaticRouteName);
          int i = 0;
          for (String name : LsStaticRouteNames) {
           
              i = i+1;
             
               if( name.contentEquals(statiroutename) ) {
                   Checkboxnumber = Checkboxnumber +"[" + String.valueOf(i)+"]"; 
                   System.out.print(Checkboxnumber);
                   staticroutecheckbox = $x(Checkboxnumber);
                   
                   logger.info(Checkboxnumber);
                   logger.info(String.format(": %s", name));
                   logger.info(String.valueOf(i));
                   staticroutecheckbox.selectRadio(String.valueOf(i));
                   logger.info(String.valueOf(i));
                   
               }
          }
          logger.info("66666666666666666666666666666666666666666666666");
          staticroutedeletebutton.click();
          staticrouteokbutton.click();
          Selenide.sleep(5000);
          staticroutecheckbox = $x("(//input[@class = 'ant-radio-input'])[1]");
          if (!staticroutecheckbox.exists()) {
              
          } else {
              List<String> LsStaticRouteNames1= MyCommonAPIs.getTexts(StaticRouteName);
              for (String name : LsStaticRouteNames1) {
                  logger.info(name);
                  logger.info(statiroutename);
                  if( name.contentEquals(statiroutename)) {
                      Result = false;                   
                  }
              }
          }
          System.out.print(Checkboxnumber);
          return Result;
      }
    public  boolean  AddInvalidStaticRule(Map<String, String> staticroute) {
   
        logger.info("AddInvalidStaticRule start");
        boolean Result = false;
        staticrouteaddbutton.click();
        Selenide.sleep(5000);
        if (staticroute.get("Route Name") != null) {
            staticroutename.setValue(staticroute.get("Route Name"));
        } else {
            logger.info("Route Name is null");
            
        }
        logger.info(staticroute.get("Destination IP Address"));
        if (staticroute.get("Destination IP Address") != null) {
            String deip = staticroute.get("Destination IP Address");
            String splitchar = "\\.";
            logger.info(deip);
            String  []diplist =deip.split(splitchar);
            logger.info(diplist[0]);
            System.out.println(diplist[0]);
            System.out.println(diplist[1]);
            System.out.println(diplist[2]);
            System.out.println(diplist[3]);
            staticroutedesipv41.setValue(diplist[0]);
            staticroutedesipv42.setValue(diplist[1]);
            staticroutedesipv43.setValue(diplist[2]);
            staticroutedesipv44.setValue(diplist[3]); 
            logger.info("633333333333333333333333333");
            Selenide.sleep(5000);
            if (invalidstaticroutewarning.exists()) {
                logger.info("6333333333333333333333333332222222222");
                logger.info(invalidstaticroutewarning.getText());
                if  (invalidstaticroutewarning.getText().contentEquals("Invalid Destination IP Address.")){
                    Result = true;
                }
            }
        } else {
            logger.info("Destination IP Address is null");
            
        }
        
        if (staticroute.get("IP Subnet Mask") != null) {
            String  []ipmasklist = staticroute.get("IP Subnet Mask").split("\\.");   
            staticroutedesipmask1.setValue(ipmasklist[0]);
            staticroutedesipmask2.setValue(ipmasklist[1]);
            staticroutedesipmask3.setValue(ipmasklist[2]);
            staticroutedesipmask4.setValue(ipmasklist[3]);  
        } else {
            logger.info("IP Subnet Mask is null");
            
        }
        if (staticroute.get("Gateway IP Address") != null) {
            String  []ipgatewaylist = staticroute.get("Gateway IP Address").split("\\.");   
            staticroutedesgateway1.setValue(ipgatewaylist[0]);
            staticroutedesgateway2.setValue(ipgatewaylist[1]);
            staticroutedesgateway3.setValue(ipgatewaylist[2]);
            staticroutedesgateway4.setValue(ipgatewaylist[3]);  
        } else {
            logger.info("Gateway IP Address is null");
            
        }
        if (staticroute.get("Metric") != null) {
            staticroutemetric.setValue(staticroute.get("Metric"));
        }
        else {
            logger.info("Metric  is null");
            
        }
        logger.info("AddInvalidStaticRule end");
        System.out.print(Result);
        return Result; 
      
    }
    public boolean CheckStaticRuleExistOrNot(String statiroutename) {
       
         boolean Result = false; 
         logger.info(String.format("CheckStaticRuleExistOrNot start"));
         String StaticRouteName = "//tr[@class='ant-table-row  ant-table-row-level-0']/td[4]";
         staticroutecheckbox = $x("(//input[@class = 'ant-radio-input'])[1]");
         if (!staticroutecheckbox.exists()) {
              
         } else {
              List<String> LsStaticRouteNames1= MyCommonAPIs.getTexts(StaticRouteName);
              for (String name : LsStaticRouteNames1) {
                  logger.info(name);
                  logger.info(statiroutename);
                  if( name.contentEquals(statiroutename)) {
                      Result = true;                   
                  }
              }
          }
         System.out.print(Result);
         logger.info("CheckStaticRuleExistOrNot  End");
         return Result;
      }

}
