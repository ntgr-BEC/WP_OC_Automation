package businessrouter.webpageoperation;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import businessrouter.webelements.BrAllMenueElements;
import businessrouter.webelements.BrIPSecVpnElements;
import util.MyCommonAPIs;

public class BrIPSecVpnPage extends BrIPSecVpnElements {
    final static Logger logger = Logger.getLogger("BrIPSecVpnPage");

    public BrIPSecVpnPage() {
        // TODO Auto-generated constructor stub
    }
    boolean result = false;
    public void OpenIPSecVPNPage() {
        //open(LoginURL);
        logger.info("Open IPSec VPN Page");
        BrAllMenueElements BrAllMenueElements = new BrAllMenueElements();
        BrAllMenueElements.advanced.click();
        BrAllMenueElements.IPSecVPN.click();
    }
    public boolean ConfigIPSecVPNPolicy(Map<String, String> IPSecVpnPolicy) {
        //open(LoginURL);
        logger.info("Add customer IPSec VPN rule");
        addbutton.click();
        Selenide.sleep(2000);
        if (IPSecVpnPolicy.get("Rule Status") != null) {
            System.out.println(IPSecVpnPolicy.get("Rule Status"));
           if (IPSecVpnPolicy.get("Rule Status").compareTo("enable") == 0){
               if(!enableordisable.isEnabled()) {
                enableordisable.selectRadio("on");
               }
            } else {
                System.out.println("dd234343245sewrey4567653");
                enableordisable.sendKeys(Keys.SPACE);
            }
        
        } else {
            logger.info("Rule Status is null");
        }  
      if (IPSecVpnPolicy.get("Policy Name") != null) {
            
          policyname.setValue(IPSecVpnPolicy.get("Policy Name"));
      } else {
            logger.info("Policy Name is null");
      } 
      if (IPSecVpnPolicy.get("Remote Gateway") != null) {
          
          remotegateway.setValue(IPSecVpnPolicy.get("Remote Gateway"));
      } else {
            logger.info("Remote Gateway is null");
      } 
      if (IPSecVpnPolicy.get("Remote Subnet") != null) {
          String  []RemoteSubnetList = IPSecVpnPolicy.get("Remote Subnet").split("\\.");
          remotesubnet1.setValue(RemoteSubnetList[0]);
          remotesubnet2.setValue(RemoteSubnetList[1]);
          remotesubnet3.setValue(RemoteSubnetList[2]);
          remotesubnet4.setValue(RemoteSubnetList[3]); 
             
      } 
      if (IPSecVpnPolicy.get("Remote Mask") != null) {
          String  []RemoteMaskList = IPSecVpnPolicy.get("Remote Mask").split("\\.");
          remotemask1.setValue(RemoteMaskList[0]);
          remotemask2.setValue(RemoteMaskList[1]);
          remotemask3.setValue(RemoteMaskList[2]);
          remotemask4.setValue(RemoteMaskList[3]); 
             
      } 
      if (IPSecVpnPolicy.get("Local Subnet") != null) {
          String  []LocalSubnetList = IPSecVpnPolicy.get("Local Subnet").split("\\.");
          localsubnet1.setValue(LocalSubnetList[0]);
          localsubnet2.setValue(LocalSubnetList[1]);
          localsubnet3.setValue(LocalSubnetList[2]);
          localsubnet4.setValue(LocalSubnetList[3]);            
      } 
      if (IPSecVpnPolicy.get("Local Mask") != null) {
          String  []LocalMaskList = IPSecVpnPolicy.get("Local Mask").split("\\.");
          localmask1.setValue(LocalMaskList[0]);
          localmask2.setValue(LocalMaskList[1]);
          localmask3.setValue(LocalMaskList[2]);
          localmask4.setValue(LocalMaskList[3]);        
      } 
      logger.info(IPSecVpnPolicy.get("PreShared Key"));
      if (IPSecVpnPolicy.get("PreShared Key") != null) {
          
          presharedkey.setValue(IPSecVpnPolicy.get("PreShared Key"));
      } else {
            logger.info("PreShared Key is null");
      }
      if (IPSecVpnPolicy.get("IKE Mode") != null) {
          if (IPSecVpnPolicy.get("IKE Mode").equalsIgnoreCase("IKEv1")){
              System.out.print(ikev1.isSelected());
              if(!ikev1.isSelected()) {
                  ikev1.selectRadio("ikev1");
              }
              
          } else {
              System.out.print(ikev1.isSelected());
              if(!ikev2.isSelected()) {
                  ikev2.selectRadio("ikev2");
              }
          }
      } else {
          logger.info("IKE Mode is null");
      }
      advancedsettinglink.click();
      
      if (IPSecVpnPolicy.get("proposal1") != null) {
          ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(true);", advancedsettinglink);
           ph1proposal1eidt.click();
           Selenide.sleep(5000);
              System.out.println(IPSecVpnPolicy.get("proposal1"));
              if (IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("md5-3des-dh1")) {                             
                  ph1proposal11.click();
              }else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("md5-3des-dh2")){


                  ph1proposal12.click();
              }else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("md5-3des-dh5")){
                  ph1proposal13.click();
              }else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("md5-aes128-dh1")){

                  ph1proposal14.click();
              }else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("md5-aes128-dh2")){

                  ph1proposal15.click();              
              }else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("md5-aes128-dh5")){

                  ph1proposal16.click();              
              }else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("md5-aes192-dh1")){

                  ph1proposal17.click();              
              }else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("md5-aes192-dh2")){

                  ph1proposal18.click();              
              }else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("md5-aes192-dh5")){
                  ph1proposal19.click();              
              }else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("md5-aes256-dh1")){
                  ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal110);
                  Selenide.sleep(5000);
                  ph1proposal110.click();              
              }else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("md5-aes256-dh2")){
                  ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal111);
                  Selenide.sleep(5000);
                  ph1proposal111.click();              
              }else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("md5-aes256-dh5")){
                  ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal112);
                  Selenide.sleep(5000);
                  ph1proposal112.click();              
              }else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("sha1-3des-dh1")){
                  ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal113);
                  Selenide.sleep(5000);
                  ph1proposal113.click();              
              }else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("sha1-3des-dh2")){
                  ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal114);
                  Selenide.sleep(5000);
                  ph1proposal114.click();              
              }else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("sha1-3des-dh5")){
                  ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal115);
                  Selenide.sleep(5000);
                  ph1proposal115.click();              
              }
              else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("sha1-aes128-dh1")){
                  ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal116);
                  Selenide.sleep(5000);
                  ph1proposal116.click();              
              }
              else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("sha1-aes128-dh2")){
                  ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal117);
                  Selenide.sleep(5000);
                  ph1proposal117.click();              
              }
              else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("sha1-aes128-dh5")){
                  ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal118);
                  Selenide.sleep(5000);
                  ph1proposal118.click();              
              }
              else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("sha1-aes192-dh1")){
                  ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal119);
                  Selenide.sleep(5000);
                  ph1proposal119.click();              
              }
              else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("sha1-aes192-dh2")){
                  ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal120);
                  Selenide.sleep(5000);
                  ph1proposal120.click();              
              }
              else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("sha1-aes192-dh5")){
                  ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal121);
                  Selenide.sleep(5000);
                  ph1proposal121.click();              
              }
              else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("sha1-aes256-dh5")){
                  ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal122);
                  Selenide.sleep(5000);
                  ph1proposal122.click();              
              }
              else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("sha1-aes256-dh5")){
                  ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal123);
                  Selenide.sleep(5000);
                  ph1proposal123.click();              
              }
              else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("sha1-aes256-dh5")){
                  ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal124);
                  Selenide.sleep(5000);
                  ph1proposal124.click();              
              }
              
         
      }
      if (IPSecVpnPolicy.get("proposal2") != null) {
          ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(true);", advancedsettinglink);
          
          System.out.println(IPSecVpnPolicy.get("proposal2"));
          ph1proposal2eidt.click();
          Selenide.sleep(5000);
          if (IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("md5-3des-dh1")) {              
        
              ph1proposal21.click();
          }else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("md5-3des-dh2")){
       
              ph1proposal22.click();
          }else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("md5-3des-dh5")){
        
              ph1proposal23.click();
          }else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("md5-aes128-dh1")){
       
              ph1proposal24.click();
          }else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("md5-aes128-dh2")){
       
              ph1proposal25.click();              
          }else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("md5-aes128-dh5")){
        
              ph1proposal26.click();              
          }else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("md5-aes192-dh1")){
          
              ph1proposal27.click();              
          }else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("md5-aes192-dh2")){
     
              ph1proposal28.click();              
          }else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("md5-aes192-dh5")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal29);
              Selenide.sleep(10000);
              ph1proposal29.click();              
          }else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("md5-aes256-dh1")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal210);
              Selenide.sleep(10000);
              ph1proposal210.click();              
          }else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("md5-aes256-dh2")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal211);
              Selenide.sleep(10000);
              ph1proposal211.click();              
          }else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("md5-aes256-dh5")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal212);
              Selenide.sleep(10000);
              ph1proposal212.click();              
          }else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("sha1-3des-dh1")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal213);
              Selenide.sleep(10000);
              ph1proposal213.click();              
          }else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("sha1-3des-dh2")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal214);
              Selenide.sleep(10000);
              ph1proposal214.click();              
          }else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("sha1-3des-dh5")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal215);
              Selenide.sleep(10000);
              ph1proposal215.click();              
          }
          else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("sha1-aes128-dh1")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal216);
              Selenide.sleep(10000);
              ph1proposal216.click();              
          }
          else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("sha1-aes128-dh2")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal218);
              Selenide.sleep(10000);
              ph1proposal217.click();              
          }
          else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("sha1-aes128-dh5")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal124);
              Selenide.sleep(10000);
              ph1proposal218.click();              
          }
          else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("sha1-aes192-dh1")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal219);
              Selenide.sleep(10000);
              ph1proposal219.click();              
          }
          else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("sha1-aes192-dh2")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal220);
              Selenide.sleep(10000);
              ph1proposal220.click();              
          }
          else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("sha1-aes192-dh5")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal221);
              Selenide.sleep(10000);
              ph1proposal221.click();              
          }
          else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("sha1-aes256-dh1")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal222);
              Selenide.sleep(10000);
              ph1proposal222.click();              
          }
          else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("sha1-aes256-dh2")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal223);
              Selenide.sleep(10000);
              ph1proposal223.click();              
          }
          else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("sha1-aes256-dh5")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal224);
              Selenide.sleep(10000);
              ph1proposal224.click();              
          }
      }
      if (IPSecVpnPolicy.get("proposal3") != null) {
        ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(true);", ph1proposal1eidt);
          
          System.out.println(IPSecVpnPolicy.get("proposal3"));
          ph1proposal3eidt.click();
          Selenide.sleep(5000);
          if (IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("md5-3des-dh1")) {              
             
              ph1proposal31.click();
          }else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("md5-3des-dh2")){
            
              ph1proposal32.click();
          }else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("md5-3des-dh5")){
        
              ph1proposal33.click();
          }else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("md5-aes128-dh1")){
        
              ph1proposal34.click();
          }else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("md5-aes128-dh2")){
             System.out.print("asdfasdfadsfasdfasdfafds");
              ph1proposal35.click();              
          }else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("md5-aes128-dh5")){
       
              ph1proposal36.click();              
          }else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("md5-aes192-dh1")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal37);
              Selenide.sleep(10000);
              ph1proposal37.click();              
          }else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("md5-aes192-dh2")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal38);
              Selenide.sleep(10000);
              ph1proposal38.click();              
          }else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("md5-aes192-dh5")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal39);
              Selenide.sleep(10000);
              ph1proposal39.click();              
          }else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("md5-aes256-dh1")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal310);
              Selenide.sleep(10000);
              ph1proposal310.click();              
          }else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("md5-aes256-dh2")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal311);
              Selenide.sleep(10000);
              ph1proposal311.click();              
          }else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("md5-aes256-dh5")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal312);
              Selenide.sleep(10000);
              ph1proposal312.click();              
          }else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("sha1-3des-dh1")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal313);
              Selenide.sleep(10000);
              ph1proposal313.click();              
          }else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("sha1-3des-dh2")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal314);
              Selenide.sleep(10000);
              ph1proposal314.click();              
          }else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("sha1-3des-dh5")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal315);
              Selenide.sleep(10000);
              ph1proposal315.click();              
          }
          else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("sha1-aes128-dh1")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal316);
              Selenide.sleep(10000);
              ph1proposal316.click();              
          }
          else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("sha1-aes128-dh2")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal317);
              Selenide.sleep(10000);
              ph1proposal317.click();              
          }
          else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("sha1-aes128-dh5")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal318);
              Selenide.sleep(10000);
              ph1proposal318.click();              
          }
          else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("sha1-aes192-dh1")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal319);
              Selenide.sleep(10000);
              ph1proposal319.click();              
          }
          else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("sha1-aes192-dh2")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal320);
              Selenide.sleep(10000);
              ph1proposal320.click();              
          }
          else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("sha1-aes192-dh5")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal321);
              Selenide.sleep(10000);
              ph1proposal321.click();              
          }
          else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("sha1-aes256-dh5")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal322);
              Selenide.sleep(10000);
              ph1proposal322.click();              
          }
          else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("sha1-aes256-dh5")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal323);
              Selenide.sleep(10000);
              ph1proposal323.click();              
          }
          else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("sha1-aes256-dh5")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal324);
              Selenide.sleep(10000);
              ph1proposal324.click();
           }
      }
      if (IPSecVpnPolicy.get("proposal4") != null) {
          ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(true);", ph1proposal2eidt);
          ph1proposal4eidt.click();
          Selenide.sleep(5000);
          System.out.println(IPSecVpnPolicy.get("proposal4"));
          if (IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("md5-3des-dh1")) {              
              
              ph1proposal41.click();
          }else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("md5-3des-dh2")){
             
              ph1proposal42.click();
          }else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("md5-3des-dh5")){
            
              ph1proposal43.click();
          }else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("md5-aes128-dh1")){
            
              ph1proposal44.click();
          }else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("md5-aes128-dh2")){
         
              ph1proposal45.click();              
          }else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("md5-aes128-dh5")){
           
              ph1proposal46.click();              
          }else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("md5-aes192-dh1")){
         
              ph1proposal47.click();              
          }else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("md5-aes192-dh2")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal48);
              Selenide.sleep(10000);
              ph1proposal48.click();              
          }else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("md5-aes192-dh5")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal49);
              Selenide.sleep(10000);
              ph1proposal49.click();              
          }else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("md5-aes256-dh1")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal410);
              Selenide.sleep(10000);
              ph1proposal410.click();              
          }else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("md5-aes256-dh2")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal411);
              Selenide.sleep(10000);
              ph1proposal411.click();              
          }else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("md5-aes256-dh5")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal412);
              Selenide.sleep(10000);
              ph1proposal412.click();              
          }else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("sha1-3des-dh1")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal413);
              Selenide.sleep(10000);
              ph1proposal413.click();              
          }else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("sha1-3des-dh2")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal414);
              Selenide.sleep(10000);
              ph1proposal414.click();              
          }else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("sha1-3des-dh5")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal415);
              Selenide.sleep(10000);
              ph1proposal415.click();              
          }
          else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("sha1-aes128-dh1")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal416);
              Selenide.sleep(10000);
              ph1proposal416.click();              
          }
          else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("sha1-aes128-dh2")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal417);
              Selenide.sleep(10000);
              ph1proposal417.click();              
          }
          else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("sha1-aes128-dh5")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal418);
              Selenide.sleep(10000);
              ph1proposal418.click();              
          }
          else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("sha1-aes192-dh1")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal419);
              Selenide.sleep(10000);
              ph1proposal419.click();              
          }
          else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("sha1-aes192-dh2")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal420);
              Selenide.sleep(10000);
              ph1proposal420.click();              
          }
          else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("sha1-aes192-dh5")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal421);
              Selenide.sleep(10000);
              ph1proposal421.click();              
          }
          else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("sha1-aes256-dh5")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal422);
              Selenide.sleep(10000);
              ph1proposal422.click();              
          }
          else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("sha1-aes256-dh5")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal423);
              Selenide.sleep(10000);
              ph1proposal423.click();              
          }
          else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("sha1-aes256-dh5")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal424);
              Selenide.sleep(10000);
              ph1proposal424.click();              
          }
      }
      
      if (IPSecVpnPolicy.get("Exchange Mode") != null) {
          ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(true);", ph1proposal3eidt);
          System.out.println(IPSecVpnPolicy.get("Exchange Mode"));
          if (IPSecVpnPolicy.get("Exchange Mode").equalsIgnoreCase("Main Mode")){
              if(!mainmode.isSelected()) {
                  mainmode.selectRadio("1");
              }
              
          } else {
              System.out.print(aggressivemode.isSelected());
              if(!aggressivemode.isSelected()) {
                  aggressivemode.selectRadio("2");
              }
          }
      } else {
          logger.info("Negotiation  Mode is null");
      }
      
      if (IPSecVpnPolicy.get("Negotiation  Mode") != null) {
          ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(true);", ph1proposal3eidt);
          if (IPSecVpnPolicy.get("Negotiation  Mode").equalsIgnoreCase("Initiator/Responder Mode")){
              if(!rsbothmode.isSelected()) {
                  rsbothmode.selectRadio("1");
              }
              
          } else {
              if(!respondemode.isSelected()) {
                  respondemode.selectRadio("2");
              }
          }
      } else {
          logger.info("Negotiation Mode is null");
      }
     if (IPSecVpnPolicy.get("SA Lifetime") != null) {
          
         salifetime.setValue(IPSecVpnPolicy.get("SA Lifetime "));
      } else {
            logger.info("SA Lifetime is null");
      } 
     if (IPSecVpnPolicy.get("DPD") != null) {
         if (IPSecVpnPolicy.get("DPD").equalsIgnoreCase("enable")){
             if(!dpdyesorno.isSelected()) {
                 dpdyesorno.selectRadio("on");
             }
             
         } else if(IPSecVpnPolicy.get("DPD").equalsIgnoreCase("disable")) {
             if(dpdyesorno.isSelected()) {
                 dpdyesorno.sendKeys(Keys.SPACE);
             }
         }
     } else {
         logger.info("DPD is null");
     }
     if (IPSecVpnPolicy.get("DPD Interval") != null) {
         dpdinterval.clear();
         dpdinterval.setValue(IPSecVpnPolicy.get("DPD Interval"));
      } else {
            logger.info("DPD Interval is null");
      }
     if (IPSecVpnPolicy.get("ph2proposal1") != null) {
         System.out.println("dddddddddddddd23333333333333333333333333333");
         
         ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(true);", dpdinterval);
         ph2proposal1eidt.click();
         Selenide.sleep(5000);
         if ( IPSecVpnPolicy.get("ph2proposal1").equalsIgnoreCase("esp-md5-3des")) {
             ph2proposal11.click();
         }else if (IPSecVpnPolicy.get("ph2proposal1").equalsIgnoreCase("esp-md5-aes128")){
             ph2proposal12.click();
         }else if(IPSecVpnPolicy.get("ph2proposal1").equalsIgnoreCase("esp-md5-aes192")){
             ph2proposal13.click();
          }else if(IPSecVpnPolicy.get("ph2proposal1").equalsIgnoreCase("esp-md5-aes256")){
              ph2proposal14.click();
          }else if(IPSecVpnPolicy.get("ph2proposal1").equalsIgnoreCase("esp-sha1-3des")){
              ph2proposal15.click();
          }else if(IPSecVpnPolicy.get("ph2proposal1").equalsIgnoreCase("esp-sha1-aes128")){
              ph2proposal16.click();
          } else if(IPSecVpnPolicy.get("ph2proposal1").equalsIgnoreCase("esp-sha1-aes192")){
              ph2proposal17.click();  
          } else if(IPSecVpnPolicy.get("ph2proposal1").equalsIgnoreCase("esp-sha1-aes256")){
              ph2proposal18.click();
           
         } 

         Selenide.sleep(3000);
     } 
     if (IPSecVpnPolicy.get("ph2proposal2") != null) {
       System.out.println("dddddddddddddd23333333333333333333333333333231111");
         
         ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(true);", dpdinterval);
         ph2proposal2eidt.click();
         Selenide.sleep(5000);
         if ( IPSecVpnPolicy.get("ph2proposal2").equalsIgnoreCase("esp-md5-3des")) {
             ph2proposal21.click();
         }else if (IPSecVpnPolicy.get("ph2proposal2").equalsIgnoreCase("esp-md5-aes128")){
             ph2proposal22.click();
         }else if(IPSecVpnPolicy.get("ph2proposal2").equalsIgnoreCase("esp-md5-aes192")){
             ph2proposal23.click();
          }else if(IPSecVpnPolicy.get("ph2proposal2").equalsIgnoreCase("esp-md5-aes256")){
              ph2proposal24.click();
          }else if(IPSecVpnPolicy.get("ph2proposal2").equalsIgnoreCase("esp-sha1-3des")){
              ph2proposal25.click();
          }else if(IPSecVpnPolicy.get("ph2proposal2").equalsIgnoreCase("esp-sha1-aes128")){
              ph2proposal26.click();
          } else if(IPSecVpnPolicy.get("ph2proposal2").equalsIgnoreCase("esp-sha1-aes192")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph2proposal27);
              ph2proposal27.click();  
          } else if(IPSecVpnPolicy.get("ph2proposal2").equalsIgnoreCase("esp-sha1-aes256")){
              ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph2proposal28);
              ph2proposal28.click();
           
         } 
     }
     if (IPSecVpnPolicy.get("ph2proposal3") != null) {
 System.out.println("dddddddddddddd2333333333333333333333333333323111145");
         
         ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(true);", ph2proposal1eidt);
         ph2proposal3eidt.click();
         Selenide.sleep(5000);
         if ( IPSecVpnPolicy.get("ph2proposal3").equalsIgnoreCase("esp-md5-3des")) {
             ph2proposal31.click();
         }else if (IPSecVpnPolicy.get("ph2proposal3").equalsIgnoreCase("esp-md5-aes128")){
             ph2proposal32.click();
         }else if(IPSecVpnPolicy.get("ph2proposal3").equalsIgnoreCase("esp-md5-aes192")){
             ph2proposal33.click();
          }else if(IPSecVpnPolicy.get("ph2proposal3").equalsIgnoreCase("esp-md5-aes256")){
              ph2proposal34.click();
          }else if(IPSecVpnPolicy.get("ph2proposal3").equalsIgnoreCase("esp-sha1-3des")){
              ph2proposal35.click();
          }else if(IPSecVpnPolicy.get("ph2proposal3").equalsIgnoreCase("esp-sha1-aes128")){
              ph2proposal36.click();
          } else if(IPSecVpnPolicy.get("ph2proposal3").equalsIgnoreCase("esp-sha1-aes192")){
              ph2proposal37.click();  
          } else if(IPSecVpnPolicy.get("ph2proposal3").equalsIgnoreCase("esp-sha1-aes256")){
              ph2proposal38.click();
           
         } 
     }
     if (IPSecVpnPolicy.get("ph2proposal4") != null) {
System.out.println("dddddddddddddd233333333333333333333333333332311133316");
         
         ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(true);", ph2proposal1eidt);
         ph2proposal4eidt.click();
         Selenide.sleep(5000);
         if ( IPSecVpnPolicy.get("ph2proposal4").equalsIgnoreCase("esp-md5-3des")) {
             ph2proposal41.click();
         }else if (IPSecVpnPolicy.get("ph2proposal4").equalsIgnoreCase("esp-md5-aes128")){
             ph2proposal42.click();
         }else if(IPSecVpnPolicy.get("ph2proposal4").equalsIgnoreCase("esp-md5-aes192")){
             ph2proposal43.click();
          }else if(IPSecVpnPolicy.get("ph2proposal4").equalsIgnoreCase("esp-md5-aes256")){
              ph2proposal44.click();
          }else if(IPSecVpnPolicy.get("ph2proposal4").equalsIgnoreCase("esp-sha1-3des")){
              ph2proposal45.click();
          }else if(IPSecVpnPolicy.get("ph2proposal4").equalsIgnoreCase("esp-sha1-aes128")){
              ph2proposal46.click();
          } else if(IPSecVpnPolicy.get("ph2proposal4").equalsIgnoreCase("esp-sha1-aes192")){
              ph2proposal47.click();  
          } else if(IPSecVpnPolicy.get("ph2proposal4").equalsIgnoreCase("esp-sha1-aes256")){
              ph2proposal48.click();
           
         } 
     }
     if (IPSecVpnPolicy.get("PFS") != null) {
         ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(true);", ph2proposal2eidt);
         pfs.click();
         if (IPSecVpnPolicy.get("PFS").equalsIgnoreCase("no")) {
             pfsno.click();               
         }else if(IPSecVpnPolicy.get("PFS").equalsIgnoreCase("dh1")){
             pfsdh1.click();
         }else if(IPSecVpnPolicy.get("PFS").equalsIgnoreCase("dh2")){
             pfsdh2.click();
         }else if(IPSecVpnPolicy.get("PFS").equalsIgnoreCase("dh5")){
             pfsdh5.click();
         }else if(IPSecVpnPolicy.get("PFS").equalsIgnoreCase("dh14")){
             pfsdh14.click();
         }else if(IPSecVpnPolicy.get("PFS").equalsIgnoreCase("dh15")){
             pfsdh15.click();
         }else if(IPSecVpnPolicy.get("PFS").equalsIgnoreCase("dh16")){
             pfsdh16.click();
         }else if(IPSecVpnPolicy.get("PFS").equalsIgnoreCase("dh17")){
             pfsdh17.click();
         }else if(IPSecVpnPolicy.get("PFS").equalsIgnoreCase("dh18")){
             pfsdh18.click();
         }else if(IPSecVpnPolicy.get("PFS").equalsIgnoreCase("dh19")){
             pfsdh19.click();
         }else if(IPSecVpnPolicy.get("PFS").equalsIgnoreCase("dh20")){
             pfsdh20.click();
         }else if(IPSecVpnPolicy.get("PFS").equalsIgnoreCase("dh21")){
             pfsdh21.click();
         }else if(IPSecVpnPolicy.get("PFS").equalsIgnoreCase("dh22")){
             pfsdh22.click();
         }else if(IPSecVpnPolicy.get("PFS").equalsIgnoreCase("dh23")){
             pfsdh23.click();
         } else if(IPSecVpnPolicy.get("PFS").equalsIgnoreCase("dh24")){
             ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", pfsdh24);
             pfsdh24.click();
         }    
             
         Selenide.sleep(4000);
     } else {
         logger.info("Default PFS is used ");
         
     }
     if (IPSecVpnPolicy.get("Ph2 SA Lifetime") != null) {
         
         ph2salifetime.setValue(IPSecVpnPolicy.get("Ph2 SA Lifetime "));
      } else {
            logger.info("Ph2 SA Lifetime is null");
      }
     ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", applybutton);
     applybutton.click();
     logger.info(String.format("Check the rule exist or not"));
     String IPsevpolicylistname = "//tr[@class = 'ant-table-row  ant-table-row-level-0']/td[4]"; 
     logger.info(String.format("Check the rule exist or not"));
     MyCommonAPIs.waitReady();
     List<String> lspolicyrules= MyCommonAPIs.getTexts(IPsevpolicylistname);
     for (String name : lspolicyrules) {
       int existornot =  name.compareTo(IPSecVpnPolicy.get("Policy Name"));
       if(existornot == 0){
           result = true;  
       }else {
           result = false;   
       }
     }
     return result;
    }
    
public boolean GetVpnTunnelStatus(String PolicyName) {
   //open(LoginURL);
   logger.info("Get Vpn Tunnel Status");
   String TunnelStatus="";
   boolean Result = false;
   int i = 0;
   String IPsevpolicylistname = "//tr[@class = 'ant-table-row  ant-table-row-level-0']/td[4]"; 
   String IPsevpntunnelstatus = "//tr[@class = 'ant-table-row  ant-table-row-level-0']/td[3]"; 
   List<String> lspolicyrulenames= MyCommonAPIs.getTexts(IPsevpolicylistname);
   //List<String> lsvpntunnelstatuss= MyCommonAPIs.getTexts(IPsevpntunnelstatus);
   for (String name : lspolicyrulenames) {
       i = i + 1;
       System.out.println(name);
       int existornot =  name.compareTo(PolicyName);
       System.out.print(existornot);
       if(existornot == 0){
           IPsevpntunnelstatus = "//tr[@class = 'ant-table-row  ant-table-row-level-0'][" + String.valueOf(i)+ "]/td[3]/img";
           System.out.println(IPsevpntunnelstatus);
       }      
     }
   vnpstatus = $x(IPsevpntunnelstatus);
   TunnelStatus = vnpstatus.getAttribute("title");
   System.out.println(TunnelStatus);
   
   if(TunnelStatus.contentEquals("Connect")){
       result = true;  
   }else if(TunnelStatus.contentEquals("Disconnect")) {
       result = false;   
   }
   System.out.print(result);
   return result;
  } 
public  void DeleteallVpnPolicyRules () {
    if ( selectall.exists()) {                     
        selectall.selectRadio("on");
        deletebutton.click();
        vpnpolicyokbutton.click();
    }
    
}
public  void DeleteOneVpnPolicyRules (String PolicyName) {
    
    String IPsevpolicylistname = "//tr[@class = 'ant-table-row  ant-table-row-level-0']/td[4]";
    String vpnselectoneelement = "//*[@id='ipsce_policy_3']/div/div[2]/div/div/table/tbody/tr[1]/td[1]/span/label/span/input";
//    BrAllMenueElements BrAllMenueElements = new BrAllMenueElements();
//    BrAllMenueElements.advanced.click();
    //BrAllMenueElements.IPSecVPN.click();
   
      logger.info(String.format("Delete one vpn policy ..."));
      MyCommonAPIs.waitReady();
      List<String> lspolicyrulenames= MyCommonAPIs.getTexts(IPsevpolicylistname);
      int i = 0;
      for (String name : lspolicyrulenames) {
       
          i = i + 1;
          System.out.println(name);
          int existornot =  name.compareTo(PolicyName);
          System.out.print(existornot);
          if(existornot == 0){
            
              vpnselectoneelement ="//*[@id='ipsce_policy_3']/div/div/table/tbody/tr[" + String.valueOf(i)+"]/td[1]/span/label/span/input";
              //vpnselectoneelement ="//*[@id='ipsce_policy_3']/div/div[2]/div/div/table/tbody/tr[1]/td[" + String.valueOf(i)+"]/span/label/span/input"; 
              System.out.println(vpnselectoneelement);
              vnpselectone = $x(vpnselectoneelement);
              MyCommonAPIs.sleep(4000); 
              //logger.info(vpnselectoneelement);
              logger.info(String.format(": %s", name));
              vnpselectone.selectRadio("on");
              //vnpselectone.sendKeys("on");
              //vnpselectone.click();
              //vnpselectone.
          }  
      }
     
     
      deletebutton.click();
      vpnpolicyokbutton.click();
  }
 public  boolean VpnPolicyRulesExistOrNot (String PolicyName) {
    boolean Result = false;
    String IPsevpolicylistname = "//tr[@class = 'ant-table-row  ant-table-row-level-0']/td[4]";
    SelenideElement vpnpolicyname = $x(IPsevpolicylistname);
    if (vpnpolicyname.exists()) {
         logger.info(String.format("Check the policy exist or not..."));
         MyCommonAPIs.waitReady();
         List<String> lspolicyrulenames= MyCommonAPIs.getTexts(IPsevpolicylistname);
         int i = 0;
         for (String name : lspolicyrulenames) {
             System.out.println(name);
             int existornot =  name.compareTo(PolicyName);
             System.out.print(existornot);
             if(existornot == 0){
                Result = true;
             }  
        }
    } else {
        Result = false;
    }
      
      return Result;
  }
 public int  CheckPolicyName(String PolicyName) {
     //open(LoginURL);
     //logger.info("Open Firewall Traffic Rules Page");
     int  Result= 0;
     addbutton.click();
     if (PolicyName != null) {         
         policyname.setValue(PolicyName);
     } 
     String PolicyNameValue = policyname.getValue();
     System.out.println(PolicyNameValue);
     if (PolicyName.compareTo(PolicyNameValue) > 0 && PolicyNameValue.length() == 32 ) {
         Result = 1; 
     } else if (PolicyName.compareTo(PolicyNameValue) == 0) {
         Result = 2;
     } else {
         Result = 3;
     }
    return Result;
 }
 public int  CheckDomainName(String DomainName) {
     //open(LoginURL);
     //logger.info("Open Firewall Traffic Rules Page");
     int  Result= 0;
     addbutton.click();
     if (DomainName != null) {         
         remotegateway.setValue(DomainName);
     } 
     String DomainNameValue = remotegateway.getValue();
     System.out.println(DomainNameValue);
     if (DomainName.compareTo(DomainNameValue) > 0 && DomainNameValue.length() == 127 ) {
         Result = 1; 
     } else if (DomainName.compareTo(DomainNameValue) == 0) {
         Result = 2;
     } else {
         Result = 3;
     }
    return Result;
 }
 public int  CheckPreSharedKey(String PreSharedKey) {
     //open(LoginURL);
     //logger.info("Open Firewall Traffic Rules Page");
     int  Result= 0;
     addbutton.click();
     if (PreSharedKey != null) {         
         presharedkey.setValue(PreSharedKey);
     } 
     String presharedkeyValue = presharedkey.getValue();
     System.out.println(presharedkeyValue);
     if (PreSharedKey.compareTo(presharedkeyValue) > 0 && presharedkeyValue.length() == 128 ) {
         Result = 1; 
     } else if (PreSharedKey.compareTo(presharedkeyValue) == 0) {
         Result = 2;
     } else {
         Result = 3;
     }
    return Result;
 }
 public boolean  GetWarningWithWrongConfig(Map<String, String> vpnpolicyrule) {
     //open(LoginURL);
     logger.info("GetWarningWithWrongConfig");
     boolean Result = false;
     addbutton.click();
     if (vpnpolicyrule.get("Remote Subnet") != null && vpnpolicyrule.get("Local Subnet")!= null  && vpnpolicyrule.get("Remote Mask") != null && vpnpolicyrule.get("Local Mask")!= null) {
         if(vpnpolicyrule.get("Remote Mask").compareTo( vpnpolicyrule.get("Local Mask")) == 0) {
             if (vpnpolicyrule.get("Remote Subnet") != null) {
                 String  []RemoteSubnetList = vpnpolicyrule.get("Remote Subnet").split("\\.");
                 remotesubnet1.setValue(RemoteSubnetList[0]);
                 remotesubnet2.setValue(RemoteSubnetList[1]);
                 remotesubnet3.setValue(RemoteSubnetList[2]);
                 remotesubnet4.setValue(RemoteSubnetList[3]); 
                    
             } 
             if (vpnpolicyrule.get("Remote Mask") != null) {
                 String  []RemoteMaskList = vpnpolicyrule.get("Remote Mask").split("\\.");
                 remotemask1.setValue(RemoteMaskList[0]);
                 remotemask2.setValue(RemoteMaskList[1]);
                 remotemask3.setValue(RemoteMaskList[2]);
                 remotemask4.setValue(RemoteMaskList[3]); 
                    
             } 
             if (vpnpolicyrule.get("Local Subnet") != null) {
                 String  []LocalSubnetList = vpnpolicyrule.get("Local Subnet").split("\\.");
                 localsubnet1.setValue(LocalSubnetList[0]);
                 localsubnet2.setValue(LocalSubnetList[1]);
                 localsubnet3.setValue(LocalSubnetList[2]);
                 localsubnet4.setValue(LocalSubnetList[3]);            
             } 
             if (vpnpolicyrule.get("Local Mask") != null) {
                 String  []LocalMaskList = vpnpolicyrule.get("Local Mask").split("\\.");
                 localmask1.setValue(LocalMaskList[0]);
                 localmask2.setValue(LocalMaskList[1]);
                 localmask3.setValue(LocalMaskList[2]);
                 localmask4.setValue(LocalMaskList[3]);        
             }   
             
         }
    }
    Selenide.sleep(4000);
    if ( warningofsubnet.exists()) {
        String WarningContent = warningofsubnet.getText();
             logger.info("ddd222222223444444444444455666666333");
             logger.info("Source port:"+WarningContent);
             if  (WarningContent.contentEquals("The input local subnet already exists.")) {
                 logger.info("ddd2222222234444444444444556666663335555555");
                 Result = true; 
             } else {
                 Result = false;
             }
         }else {
             Result = false;
         } 
    return Result;
 }
 public boolean EditIPSecVPNPolicy(Map<String, String> IPSecVpnPolicy) {
     //open(LoginURL);
     logger.info("Edit customer IPSec VPN rule");
     System.out.println("asdffffffffffffffffffffffffffffffffffffff");
     String IPsevpolicylistname = "//tr[@class = 'ant-table-row  ant-table-row-level-0']/td[4]";
     String vpneditbuttonelement = "#ipsce_policy_";
    
       logger.info(String.format("Delete one vpn policy ..."));
       MyCommonAPIs.waitReady();
       List<String> lspolicyrulenames= MyCommonAPIs.getTexts(IPsevpolicylistname);
       int i = 0;
       for (String name : lspolicyrulenames) {
        
           i = i + 1;
           System.out.println(name);
           int existornot =  name.compareTo(IPSecVpnPolicy.get("Policy Name"));
           System.out.print(existornot);
           if(existornot == 0){
               vpneditbuttonelement =vpneditbuttonelement + String.valueOf(i)+"_1";
               System.out.println(vpneditbuttonelement);
               vpneditbutton= $(vpneditbuttonelement);
                
               System.out.println(vpneditbuttonelement);
              
               MyCommonAPIs.sleep(4000); 
               //logger.info(vpnselectoneelement);
               logger.info(String.format(": %s", name));
               vpneditbutton.click();
 
           }  
       }
     
     Selenide.sleep(2000);
     if (IPSecVpnPolicy.get("Rule Status") != null) {
        if (IPSecVpnPolicy.get("Rule Status").contentEquals("enable")){
            System.out.print(enableordisable.isSelected());
            if(!enableordisable.isSelected()) {
             enableordisable.selectRadio("on");
            }
         } else {
             enableordisable.sendKeys(Keys.SPACE);
         }
     
     } else {
         logger.info("Rule Status is null");
     }  
   if (IPSecVpnPolicy.get("Policy Name") != null) {
         
       policyname.setValue(IPSecVpnPolicy.get("Policy Name"));
   } else {
         logger.info("Policy Name is null");
   } 
   if (IPSecVpnPolicy.get("Remote Gateway") != null) {
       
       remotegateway.setValue(IPSecVpnPolicy.get("Remote Gateway"));
   } else {
         logger.info("Remote Gateway is null");
   } 
   if (IPSecVpnPolicy.get("Remote Subnet") != null) {
       String  []RemoteSubnetList = IPSecVpnPolicy.get("Remote Subnet").split("\\.");
       remotesubnet1.setValue(RemoteSubnetList[0]);
       remotesubnet2.setValue(RemoteSubnetList[1]);
       remotesubnet3.setValue(RemoteSubnetList[2]);
       remotesubnet4.setValue(RemoteSubnetList[3]); 
          
   } 
   if (IPSecVpnPolicy.get("Remote Mask") != null) {
       String  []RemoteMaskList = IPSecVpnPolicy.get("Remote Mask").split("\\.");
       remotemask1.setValue(RemoteMaskList[0]);
       remotemask2.setValue(RemoteMaskList[1]);
       remotemask3.setValue(RemoteMaskList[2]);
       remotemask4.setValue(RemoteMaskList[3]); 
          
   } 
   if (IPSecVpnPolicy.get("Local Subnet") != null) {
       String  []LocalSubnetList = IPSecVpnPolicy.get("Local Subnet").split("\\.");
       localsubnet1.setValue(LocalSubnetList[0]);
       localsubnet2.setValue(LocalSubnetList[1]);
       localsubnet3.setValue(LocalSubnetList[2]);
       localsubnet4.setValue(LocalSubnetList[3]);            
   } 
   if (IPSecVpnPolicy.get("Local Mask") != null) {
       String  []LocalMaskList = IPSecVpnPolicy.get("Local Mask").split("\\.");
       localmask1.setValue(LocalMaskList[0]);
       localmask2.setValue(LocalMaskList[1]);
       localmask3.setValue(LocalMaskList[2]);
       localmask4.setValue(LocalMaskList[3]);        
   } 
   logger.info(IPSecVpnPolicy.get("PreShared Key"));
   if (IPSecVpnPolicy.get("PreShared Key") != null) {
       
       presharedkey.setValue(IPSecVpnPolicy.get("PreShared Key"));
   } else {
         logger.info("PreShared Key is null");
   }
   if (IPSecVpnPolicy.get("IKE Mode") != null) {
       if (IPSecVpnPolicy.get("IKE Mode").equalsIgnoreCase("IKEv1")){
           if(!ikev1.isSelected()) {
               ikev1.selectRadio("on");
           }
           
       } else {
           if(!ikev2.isSelected()) {
               ikev2.selectRadio("on");
           }
       }
   } else {
       logger.info("IKE Mode is null");
   }
   advancedsettinglink.click();
   if (IPSecVpnPolicy.get("proposal1") != null) {
       ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(true);", advancedsettinglink);
        ph1proposal1eidt.click();
        Selenide.sleep(5000);
           System.out.println(IPSecVpnPolicy.get("proposal1"));
           if (IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("md5-3des-dh1")) {                             
               ph1proposal11.click();
           }else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("md5-3des-dh2")){


               ph1proposal12.click();
           }else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("md5-3des-dh5")){
               ph1proposal13.click();
           }else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("md5-aes128-dh1")){

               ph1proposal14.click();
           }else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("md5-aes128-dh2")){

               ph1proposal15.click();              
           }else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("md5-aes128-dh5")){

               ph1proposal16.click();              
           }else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("md5-aes192-dh1")){

               ph1proposal17.click();              
           }else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("md5-aes192-dh2")){

               ph1proposal18.click();              
           }else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("md5-aes192-dh5")){
               ph1proposal19.click();              
           }else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("md5-aes256-dh1")){
               ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal110);
               Selenide.sleep(5000);
               ph1proposal110.click();              
           }else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("md5-aes256-dh2")){
               ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal111);
               Selenide.sleep(5000);
               ph1proposal111.click();              
           }else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("md5-aes256-dh5")){
               ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal112);
               Selenide.sleep(5000);
               ph1proposal112.click();              
           }else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("sha1-3des-dh1")){
               ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal113);
               Selenide.sleep(5000);
               ph1proposal113.click();              
           }else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("sha1-3des-dh2")){
               ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal114);
               Selenide.sleep(5000);
               ph1proposal114.click();              
           }else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("sha1-3des-dh5")){
               ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal115);
               Selenide.sleep(5000);
               ph1proposal115.click();              
           }
           else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("sha1-aes128-dh1")){
               ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal116);
               Selenide.sleep(5000);
               ph1proposal116.click();              
           }
           else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("sha1-aes128-dh2")){
               ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal117);
               Selenide.sleep(5000);
               ph1proposal117.click();              
           }
           else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("sha1-aes128-dh5")){
               ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal118);
               Selenide.sleep(5000);
               ph1proposal118.click();              
           }
           else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("sha1-aes192-dh1")){
               ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal119);
               Selenide.sleep(5000);
               ph1proposal119.click();              
           }
           else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("sha1-aes192-dh2")){
               ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal120);
               Selenide.sleep(5000);
               ph1proposal120.click();              
           }
           else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("sha1-aes192-dh5")){
               ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal121);
               Selenide.sleep(5000);
               ph1proposal121.click();              
           }
           else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("sha1-aes256-dh5")){
               ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal122);
               Selenide.sleep(5000);
               ph1proposal122.click();              
           }
           else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("sha1-aes256-dh5")){
               ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal123);
               Selenide.sleep(5000);
               ph1proposal123.click();              
           }
           else if(IPSecVpnPolicy.get("proposal1").equalsIgnoreCase("sha1-aes256-dh5")){
               ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal124);
               Selenide.sleep(5000);
               ph1proposal124.click();              
           }
           
      
   }
   if (IPSecVpnPolicy.get("proposal2") != null) {
       ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(true);", advancedsettinglink);
       
       System.out.println(IPSecVpnPolicy.get("proposal2"));
       ph1proposal2eidt.click();
       Selenide.sleep(5000);
       if (IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("md5-3des-dh1")) {              
     
           ph1proposal21.click();
       }else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("md5-3des-dh2")){
    
           ph1proposal22.click();
       }else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("md5-3des-dh5")){
     
           ph1proposal23.click();
       }else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("md5-aes128-dh1")){
    
           ph1proposal24.click();
       }else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("md5-aes128-dh2")){
    
           ph1proposal25.click();              
       }else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("md5-aes128-dh5")){
     
           ph1proposal26.click();              
       }else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("md5-aes192-dh1")){
       
           ph1proposal27.click();              
       }else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("md5-aes192-dh2")){
  
           ph1proposal28.click();              
       }else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("md5-aes192-dh5")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal29);
           Selenide.sleep(10000);
           ph1proposal29.click();              
       }else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("md5-aes256-dh1")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal210);
           Selenide.sleep(10000);
           ph1proposal210.click();              
       }else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("md5-aes256-dh2")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal211);
           Selenide.sleep(10000);
           ph1proposal211.click();              
       }else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("md5-aes256-dh5")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal212);
           Selenide.sleep(10000);
           ph1proposal212.click();              
       }else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("sha1-3des-dh1")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal213);
           Selenide.sleep(10000);
           ph1proposal213.click();              
       }else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("sha1-3des-dh2")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal214);
           Selenide.sleep(10000);
           ph1proposal214.click();              
       }else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("sha1-3des-dh5")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal215);
           Selenide.sleep(10000);
           ph1proposal215.click();              
       }
       else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("sha1-aes128-dh1")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal216);
           Selenide.sleep(10000);
           ph1proposal216.click();              
       }
       else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("sha1-aes128-dh2")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal218);
           Selenide.sleep(10000);
           ph1proposal217.click();              
       }
       else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("sha1-aes128-dh5")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal124);
           Selenide.sleep(10000);
           ph1proposal218.click();              
       }
       else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("sha1-aes192-dh1")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal219);
           Selenide.sleep(10000);
           ph1proposal219.click();              
       }
       else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("sha1-aes192-dh2")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal220);
           Selenide.sleep(10000);
           ph1proposal220.click();              
       }
       else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("sha1-aes192-dh5")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal221);
           Selenide.sleep(10000);
           ph1proposal221.click();              
       }
       else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("sha1-aes256-dh1")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal222);
           Selenide.sleep(10000);
           ph1proposal222.click();              
       }
       else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("sha1-aes256-dh2")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal223);
           Selenide.sleep(10000);
           ph1proposal223.click();              
       }
       else if(IPSecVpnPolicy.get("proposal2").equalsIgnoreCase("sha1-aes256-dh5")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal224);
           Selenide.sleep(10000);
           ph1proposal224.click();              
       }
   }
   if (IPSecVpnPolicy.get("proposal3") != null) {
     ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(true);", ph1proposal1eidt);
       
       System.out.println(IPSecVpnPolicy.get("proposal3"));
       ph1proposal3eidt.click();
       Selenide.sleep(5000);
       if (IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("md5-3des-dh1")) {              
          
           ph1proposal31.click();
       }else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("md5-3des-dh2")){
         
           ph1proposal32.click();
       }else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("md5-3des-dh5")){
     
           ph1proposal33.click();
       }else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("md5-aes128-dh1")){
     
           ph1proposal34.click();
       }else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("md5-aes128-dh2")){
          System.out.print("asdfasdfadsfasdfasdfafds");
           ph1proposal35.click();              
       }else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("md5-aes128-dh5")){
    
           ph1proposal36.click();              
       }else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("md5-aes192-dh1")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal37);
           Selenide.sleep(10000);
           ph1proposal37.click();              
       }else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("md5-aes192-dh2")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal38);
           Selenide.sleep(10000);
           ph1proposal38.click();              
       }else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("md5-aes192-dh5")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal39);
           Selenide.sleep(10000);
           ph1proposal39.click();              
       }else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("md5-aes256-dh1")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal310);
           Selenide.sleep(10000);
           ph1proposal310.click();              
       }else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("md5-aes256-dh2")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal311);
           Selenide.sleep(10000);
           ph1proposal311.click();              
       }else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("md5-aes256-dh5")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal312);
           Selenide.sleep(10000);
           ph1proposal312.click();              
       }else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("sha1-3des-dh1")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal313);
           Selenide.sleep(10000);
           ph1proposal313.click();              
       }else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("sha1-3des-dh2")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal314);
           Selenide.sleep(10000);
           ph1proposal314.click();              
       }else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("sha1-3des-dh5")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal315);
           Selenide.sleep(10000);
           ph1proposal315.click();              
       }
       else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("sha1-aes128-dh1")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal316);
           Selenide.sleep(10000);
           ph1proposal316.click();              
       }
       else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("sha1-aes128-dh2")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal317);
           Selenide.sleep(10000);
           ph1proposal317.click();              
       }
       else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("sha1-aes128-dh5")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal318);
           Selenide.sleep(10000);
           ph1proposal318.click();              
       }
       else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("sha1-aes192-dh1")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal319);
           Selenide.sleep(10000);
           ph1proposal319.click();              
       }
       else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("sha1-aes192-dh2")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal320);
           Selenide.sleep(10000);
           ph1proposal320.click();              
       }
       else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("sha1-aes192-dh5")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal321);
           Selenide.sleep(10000);
           ph1proposal321.click();              
       }
       else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("sha1-aes256-dh5")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal322);
           Selenide.sleep(10000);
           ph1proposal322.click();              
       }
       else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("sha1-aes256-dh5")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal323);
           Selenide.sleep(10000);
           ph1proposal323.click();              
       }
       else if(IPSecVpnPolicy.get("proposal3").equalsIgnoreCase("sha1-aes256-dh5")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal324);
           Selenide.sleep(10000);
           ph1proposal324.click();
        }
   }
   if (IPSecVpnPolicy.get("proposal4") != null) {
       ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(true);", ph1proposal2eidt);
       ph1proposal4eidt.click();
       Selenide.sleep(5000);
       System.out.println(IPSecVpnPolicy.get("proposal4"));
       if (IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("md5-3des-dh1")) {              
           
           ph1proposal41.click();
       }else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("md5-3des-dh2")){
          
           ph1proposal42.click();
       }else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("md5-3des-dh5")){
         
           ph1proposal43.click();
       }else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("md5-aes128-dh1")){
         
           ph1proposal44.click();
       }else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("md5-aes128-dh2")){
      
           ph1proposal45.click();              
       }else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("md5-aes128-dh5")){
        
           ph1proposal46.click();              
       }else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("md5-aes192-dh1")){
      
           ph1proposal47.click();              
       }else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("md5-aes192-dh2")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal48);
           Selenide.sleep(10000);
           ph1proposal48.click();              
       }else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("md5-aes192-dh5")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal49);
           Selenide.sleep(10000);
           ph1proposal49.click();              
       }else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("md5-aes256-dh1")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal410);
           Selenide.sleep(10000);
           ph1proposal410.click();              
       }else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("md5-aes256-dh2")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal411);
           Selenide.sleep(10000);
           ph1proposal411.click();              
       }else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("md5-aes256-dh5")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal412);
           Selenide.sleep(10000);
           ph1proposal412.click();              
       }else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("sha1-3des-dh1")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal413);
           Selenide.sleep(10000);
           ph1proposal413.click();              
       }else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("sha1-3des-dh2")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal414);
           Selenide.sleep(10000);
           ph1proposal414.click();              
       }else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("sha1-3des-dh5")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal415);
           Selenide.sleep(10000);
           ph1proposal415.click();              
       }
       else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("sha1-aes128-dh1")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal416);
           Selenide.sleep(10000);
           ph1proposal416.click();              
       }
       else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("sha1-aes128-dh2")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal417);
           Selenide.sleep(10000);
           ph1proposal417.click();              
       }
       else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("sha1-aes128-dh5")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal418);
           Selenide.sleep(10000);
           ph1proposal418.click();              
       }
       else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("sha1-aes192-dh1")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal419);
           Selenide.sleep(10000);
           ph1proposal419.click();              
       }
       else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("sha1-aes192-dh2")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal420);
           Selenide.sleep(10000);
           ph1proposal420.click();              
       }
       else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("sha1-aes192-dh5")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal421);
           Selenide.sleep(10000);
           ph1proposal421.click();              
       }
       else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("sha1-aes256-dh5")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal422);
           Selenide.sleep(10000);
           ph1proposal422.click();              
       }
       else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("sha1-aes256-dh5")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal423);
           Selenide.sleep(10000);
           ph1proposal423.click();              
       }
       else if(IPSecVpnPolicy.get("proposal4").equalsIgnoreCase("sha1-aes256-dh5")){
           ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", ph1proposal424);
           Selenide.sleep(10000);
           ph1proposal424.click();              
       }
   }
   
   if (IPSecVpnPolicy.get("Exchange Mode") != null) {
       if (IPSecVpnPolicy.get("Exchange Mode").equalsIgnoreCase("Main Mode")){
           if(!mainmode.isSelected()) {
               mainmode.selectRadio("on");
           }
           
       } else {
           if(!aggressivemode.isSelected()) {
               aggressivemode.selectRadio("on");
           }
       }
   } else {
       logger.info("Negotiation  Mode is null");
   }
   
   if (IPSecVpnPolicy.get("Negotiation  Mode") != null) {
       if (IPSecVpnPolicy.get("Negotiation  Mode").equalsIgnoreCase("Initiator/Responder Mode")){
           if(!rsbothmode.isSelected()) {
               rsbothmode.selectRadio("on");
           }
           
       } else {
           if(!respondemode.isSelected()) {
               respondemode.selectRadio("on");
           }
       }
   } else {
       logger.info("Negotiation Mode is null");
   }
  if (IPSecVpnPolicy.get("SA Lifetime") != null) {
       
      salifetime.setValue(IPSecVpnPolicy.get("SA Lifetime "));
   } else {
         logger.info("SA Lifetime is null");
   } 
  if (IPSecVpnPolicy.get("DPD") != null) {
      if (IPSecVpnPolicy.get("DPD").equalsIgnoreCase("enable")){
          if(!dpdyesorno.isSelected()) {
              dpdyesorno.selectRadio("on");
          }
          
      } else if(IPSecVpnPolicy.get("DPD").equalsIgnoreCase("disable")) {
          if(dpdyesorno.isSelected()) {
              dpdyesorno.sendKeys(Keys.SPACE);
          }
      }
  } else {
      logger.info("DPD is null");
  }
  if (IPSecVpnPolicy.get("DPD Interval") != null) {
      dpdinterval.clear();
      dpdinterval.setValue(IPSecVpnPolicy.get("DPD Interval"));
   } else {
         logger.info("DPD Interval is null");
   }
  if (IPSecVpnPolicy.get("ph2proposal1") != null) {
      System.out.println("dddddddddddddd23333333333333333333333333333");
      
      ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(true);", tunnelmode);
      ph2proposal1eidt.click();
      Selenide.sleep(5000);
      if ( IPSecVpnPolicy.get("ph2proposal1").equalsIgnoreCase("esp-md5-3des")) {
          ph2proposal11.click();
      }else if (IPSecVpnPolicy.get("ph2proposal1").equalsIgnoreCase("esp-md5-aes128")){
          ph2proposal12.click();
      }else if(IPSecVpnPolicy.get("ph2proposal1").equalsIgnoreCase("esp-md5-aes192")){
          ph2proposal13.click();
       }else if(IPSecVpnPolicy.get("ph2proposal1").equalsIgnoreCase("esp-md5-aes256")){
           ph2proposal14.click();
       }else if(IPSecVpnPolicy.get("ph2proposal1").equalsIgnoreCase("esp-sha1-3des")){
           ph2proposal15.click();
       }else if(IPSecVpnPolicy.get("ph2proposal1").equalsIgnoreCase("esp-sha1-aes128")){
           ph2proposal16.click();
       } else if(IPSecVpnPolicy.get("ph2proposal1").equalsIgnoreCase("esp-sha1-aes192")){
           ph2proposal17.click();  
       } else if(IPSecVpnPolicy.get("ph2proposal1").equalsIgnoreCase("esp-sha1-aes256")){
           ph2proposal18.click();
        
      } 

      Selenide.sleep(3000);
  } 
  if (IPSecVpnPolicy.get("ph2proposal2") != null) {
    System.out.println("dddddddddddddd23333333333333333333333333333231111");
      
      ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(true);", ph2proposal1eidt);
      ph2proposal2eidt.click();
      Selenide.sleep(5000);
      if ( IPSecVpnPolicy.get("ph2proposal2").equalsIgnoreCase("esp-md5-3des")) {
          ph2proposal21.click();
      }else if (IPSecVpnPolicy.get("ph2proposal2").equalsIgnoreCase("esp-md5-aes128")){
          ph2proposal22.click();
      }else if(IPSecVpnPolicy.get("ph2proposal2").equalsIgnoreCase("esp-md5-aes192")){
          ph2proposal23.click();
       }else if(IPSecVpnPolicy.get("ph2proposal2").equalsIgnoreCase("esp-md5-aes256")){
           ph2proposal24.click();
       }else if(IPSecVpnPolicy.get("ph2proposal2").equalsIgnoreCase("esp-sha1-3des")){
           ph2proposal25.click();
       }else if(IPSecVpnPolicy.get("ph2proposal2").equalsIgnoreCase("esp-sha1-aes128")){
           ph2proposal26.click();
       } else if(IPSecVpnPolicy.get("ph2proposal2").equalsIgnoreCase("esp-sha1-aes192")){
           ph2proposal27.click();  
       } else if(IPSecVpnPolicy.get("ph2proposal2").equalsIgnoreCase("esp-sha1-aes256")){
           ph2proposal28.click();
        
      } 
  }
  if (IPSecVpnPolicy.get("ph2proposal3") != null) {
System.out.println("dddddddddddddd2333333333333333333333333333323111145");
      
      ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(true);", ph2proposal2eidt);
      ph2proposal3eidt.click();
      Selenide.sleep(5000);
      if ( IPSecVpnPolicy.get("ph2proposal3").equalsIgnoreCase("esp-md5-3des")) {
          ph2proposal31.click();
      }else if (IPSecVpnPolicy.get("ph2proposal3").equalsIgnoreCase("esp-md5-aes128")){
          ph2proposal32.click();
      }else if(IPSecVpnPolicy.get("ph2proposal3").equalsIgnoreCase("esp-md5-aes192")){
          ph2proposal33.click();
       }else if(IPSecVpnPolicy.get("ph2proposal3").equalsIgnoreCase("esp-md5-aes256")){
           ph2proposal34.click();
       }else if(IPSecVpnPolicy.get("ph2proposal3").equalsIgnoreCase("esp-sha1-3des")){
           ph2proposal35.click();
       }else if(IPSecVpnPolicy.get("ph2proposal3").equalsIgnoreCase("esp-sha1-aes128")){
           ph2proposal36.click();
       } else if(IPSecVpnPolicy.get("ph2proposal3").equalsIgnoreCase("esp-sha1-aes192")){
           ph2proposal37.click();  
       } else if(IPSecVpnPolicy.get("ph2proposal3").equalsIgnoreCase("esp-sha1-aes256")){
           ph2proposal38.click();
        
      } 
  }
  if (IPSecVpnPolicy.get("ph2proposal4") != null) {
System.out.println("dddddddddddddd233333333333333333333333333332311133316");
      
      ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(true);", ph2proposal3eidt);
      ph2proposal4eidt.click();
      Selenide.sleep(5000);
      if ( IPSecVpnPolicy.get("ph2proposal4").equalsIgnoreCase("esp-md5-3des")) {
          ph2proposal41.click();
      }else if (IPSecVpnPolicy.get("ph2proposal4").equalsIgnoreCase("esp-md5-aes128")){
          ph2proposal42.click();
      }else if(IPSecVpnPolicy.get("ph2proposal4").equalsIgnoreCase("esp-md5-aes192")){
          ph2proposal43.click();
       }else if(IPSecVpnPolicy.get("ph2proposal4").equalsIgnoreCase("esp-md5-aes256")){
           ph2proposal44.click();
       }else if(IPSecVpnPolicy.get("ph2proposal4").equalsIgnoreCase("esp-sha1-3des")){
           ph2proposal45.click();
       }else if(IPSecVpnPolicy.get("ph2proposal4").equalsIgnoreCase("esp-sha1-aes128")){
           ph2proposal46.click();
       } else if(IPSecVpnPolicy.get("ph2proposal4").equalsIgnoreCase("esp-sha1-aes192")){
           ph2proposal47.click();  
       } else if(IPSecVpnPolicy.get("ph2proposal4").equalsIgnoreCase("esp-sha1-aes256")){
           ph2proposal48.click();
        
      } 
  }
  if (IPSecVpnPolicy.get("PFS") != null) {
      pfs.click();
      if (IPSecVpnPolicy.get("PFS").equalsIgnoreCase("no")) {
          pfsno.click();               
      }else if(IPSecVpnPolicy.get("PFS").equalsIgnoreCase("dh1")){
          pfsdh1.click();
      }else if(IPSecVpnPolicy.get("PFS").equalsIgnoreCase("dh2")){
          pfsdh2.click();
      }else if(IPSecVpnPolicy.get("PFS").equalsIgnoreCase("dh5")){
          pfsdh5.click();
      }else if(IPSecVpnPolicy.get("PFS").equalsIgnoreCase("dh14")){
          pfsdh14.click();
      }else if(IPSecVpnPolicy.get("PFS").equalsIgnoreCase("dh15")){
          pfsdh15.click();
      }else if(IPSecVpnPolicy.get("PFS").equalsIgnoreCase("dh16")){
          pfsdh16.click();
      }else if(IPSecVpnPolicy.get("PFS").equalsIgnoreCase("dh17")){
          pfsdh17.click();
      }else if(IPSecVpnPolicy.get("PFS").equalsIgnoreCase("dh18")){
          pfsdh18.click();
      }else if(IPSecVpnPolicy.get("PFS").equalsIgnoreCase("dh19")){
          pfsdh19.click();
      }else if(IPSecVpnPolicy.get("PFS").equalsIgnoreCase("dh20")){
          pfsdh20.click();
      }else if(IPSecVpnPolicy.get("PFS").equalsIgnoreCase("dh21")){
          pfsdh21.click();
      }else if(IPSecVpnPolicy.get("PFS").equalsIgnoreCase("dh22")){
          pfsdh22.click();
      }else if(IPSecVpnPolicy.get("PFS").equalsIgnoreCase("dh23")){
          pfsdh23.click();
      }    
          
      Selenide.sleep(4000);
  } else {
      logger.info("Default PFS is used ");
      
  }
  if (IPSecVpnPolicy.get("Ph2 SA Lifetime") != null) {
      
      ph2salifetime.setValue(IPSecVpnPolicy.get("Ph2 SA Lifetime "));
   } else {
         logger.info("Ph2 SA Lifetime is null");
   }
  ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", applybutton);
  applybutton.click();
  logger.info(String.format("Check the rule exist or not"));
  //IPsevpolicylistname = "//tr[@class = 'ant-table-row  ant-table-row-level-0']/td[4]"; 
  logger.info(String.format("Check the rule exist or not"));
  MyCommonAPIs.waitReady();
  List<String> lspolicyrules= MyCommonAPIs.getTexts(IPsevpolicylistname);
  for (String name : lspolicyrules) {
    int existornot =  name.compareTo(IPSecVpnPolicy.get("Policy Name"));
    if(existornot == 0){
        result = true;  
    }else {
        result = false;   
    }
  }
  return result;
 }
 public void DisableOrEnableIPSecVPNPolicy(String IpSecPolicyName) {
     //open(LoginURL);
     logger.info("Edit customer IPSec VPN rule");
     System.out.println("asdffffffffffffffffffffffffffffffffffffff");
     String IPsevpolicylistname = "//tr[@class = 'ant-table-row  ant-table-row-level-0']/td[4]";
     String vpndisableorenablebuttonelement = "(//*[@id=\"ipsce_policy_";
    
       logger.info(String.format("Delete one vpn policy ..."));
       MyCommonAPIs.waitReady();
       List<String> lspolicyrulenames= MyCommonAPIs.getTexts(IPsevpolicylistname);
       int i = 0;
       for (String name : lspolicyrulenames) {
        
           i = i + 1;
           System.out.println(name);
           int existornot =  name.compareTo(IpSecPolicyName);
           System.out.print(existornot);
           if(existornot == 0){
               vpndisableorenablebuttonelement =vpndisableorenablebuttonelement + String.valueOf(i)+"_0\"])[2]";
               System.out.println(vpndisableorenablebuttonelement);
               SelenideElement vpndisableorenablebutton= $x(vpndisableorenablebuttonelement);
                
               System.out.println(vpndisableorenablebuttonelement);
              
               MyCommonAPIs.sleep(4000); 
               //logger.info(vpnselectoneelement);
               logger.info(String.format(": %s", name));
               vpndisableorenablebutton.click();
              String classname= vpndisableorenablebutton.getAttribute("class");
              System.out.println(classname);
           }  
       }
 }
 public boolean  AddIpSecVpnYesOrNot() {
     //open(LoginURL);
     //logger.info("Open Firewall Traffic Rules Page");
     boolean  Result= false;
     addbutton.click();
     SelenideElement warningofpolicy= $x("/html/body/div[2]/div/span/div/div/div/span");
     if ( warningofpolicy.exists()) {
         String WarningContent = warningofpolicy.getText();
              logger.info("ddd222222223444444444444455666666333");
              logger.info("Source port:"+WarningContent);
              if  (WarningContent.compareTo("Only 4 policies are supported.") == 0) {
                  logger.info("ddd2222222234444444444444556666663335555555");
                  Result = true; 
              } else {
                  Result = false;
              }
       }else {
              Result = false;
          } 
    return Result;
 }
 public boolean  AddDuplicatedVpnPolicy(Map<String, String> IPSecVpnPolicy) {
     //open(LoginURL);
     //logger.info("Open Firewall Traffic Rules Page");
     boolean  Result= false;
     addbutton.click();
     logger.info("ddd222222223444444444444455666666333");
     if (IPSecVpnPolicy.get("Policy Name") != null) {
         
         policyname.setValue(IPSecVpnPolicy.get("Policy Name"));
     } else {
           logger.info("Policy Name is null");
     } 
     SelenideElement warningofduplicatepolicy= $x("/html/body/div[2]/div/div[2]/div/div[1]/form/div[2]/div[2]/div/div");
     System.out.print(warningofduplicatepolicy.exists());
     if ( warningofduplicatepolicy.exists()) {
         String WarningContent = warningofduplicatepolicy.getText();
              logger.info("ddd222222223444444444444455666666333");
              logger.info("Source port:"+WarningContent);
              if  (WarningContent.compareTo("The input policy name already exists.") == 0) {
                  logger.info("ddd2222222234444444444444556666663335555555");
                  Result = true; 
              } else {
                  Result = false;
              }
       }else {
              Result = false;
          } 
    return Result;
 }
 public boolean  checkWarngOfInvalidPolicyName(Map<String, String> IPSecVpnPolicy) {
     //open(LoginURL);
     //logger.info("Open Firewall Traffic Rules Page");
     boolean  Result= false;
     addbutton.click();
     if (IPSecVpnPolicy.get("Policy Name") != null) {
         
         policyname.setValue(IPSecVpnPolicy.get("Policy Name"));
     } else {
           logger.info("Policy Name is null");
     } 
     Selenide.sleep(3000);
     SelenideElement warningofinvalidname=$x("/html/body/div[2]/div/div[2]/div/div[1]/form/div[2]/div[2]/div/div");
     System.out.print(warningofinvalidname.exists());
     System.out.println(warningofinvalidname.getText());
     if ( warningofinvalidname.exists()) {
         String WarningContent = warningofinvalidname.getText();
              logger.info("ddd222222223444444444444455666666333");
              logger.info("Source port:"+WarningContent);
              if  (WarningContent.compareTo("The input is not valid policy name.") == 0) {
                  logger.info("ddd2222222234444444444444556666663335555555");
                  Result = true; 
              } else {
                  Result = false;
              }
       }else {
              Result = false;
          } 
    return Result;
 }
 public int  CheckPhase1SALifetime(String Phase1SALifetime) {
     //open(LoginURL);
     //logger.info("Open Firewall Traffic Rules Page");
     int  Result= 0;
     addbutton.click();
     advancedsettinglink.click();
     ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(true);", ph2proposal4eidt);
     if (Phase1SALifetime != null) {         
         salifetime.setValue(Phase1SALifetime);
     } 
     String saLifetimeValue = salifetime.getValue();
     System.out.println(saLifetimeValue);
     SelenideElement warningofsalifetime=$x("//div[@class ='ant-form-explain']");
    
     if (Integer.valueOf(Phase1SALifetime).intValue() < 600 || Integer.valueOf(Phase1SALifetime).intValue() > 604800 ) {
         String WarningContent = warningofsalifetime.getText();
         if  (WarningContent.compareTo("The input is not valid SA Lifetime.") == 0) {
             logger.info("ddd2222222234444444444444556666663335555555");
             Result = 1; 
         } else {
             Result = 2;
         }
     } else  { 
         System.out.print(warningofsalifetime.exists());
         if (!warningofsalifetime.exists()) {
             Result = 3;  
         } else {
             Result = 4;
         }    
     } 
    return Result;
 }
 public int  CheckPhase2SALifetime(String Phase2SALifetime) {
     //open(LoginURL);
     //logger.info("Open Firewall Traffic Rules Page");
     int  Result= 0;
     addbutton.click();
     advancedsettinglink.click();
     ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", applybutton);
     if (Phase2SALifetime != null) {         
         ph2salifetime.setValue(Phase2SALifetime);
     } 
     String saLifetimeValue = ph2salifetime.getValue();
     Selenide.sleep(20000);
     System.out.println(saLifetimeValue);
     SelenideElement warningofsalifetime=$x("//div[@class ='ant-form-explain']");
     //System.out.println(warningofsalifetime.getText());
     if (Integer.valueOf(Phase2SALifetime).intValue() < 600 || Integer.valueOf(Phase2SALifetime).intValue() > 604800 ) {
         System.out.println(warningofsalifetime.getText());
         String WarningContent = warningofsalifetime.getText();
         if  (WarningContent.compareTo("The input is not valid SA Lifetime.") == 0) {
             logger.info("ddd2222222234444444444444556666663335555555");
             Result = 1; 
         } else {
             Result = 2;
         }
     } else  { 
         System.out.print(warningofsalifetime.exists());
         if (!warningofsalifetime.exists()) {
             Result = 3;  
         } else {
             Result = 4;
         }    
     } 
    return Result;
 }
 public int  CheckDpdinterval(String DpdInterval) {
     //open(LoginURL);
     //logger.info("Open Firewall Traffic Rules Page");
     int  Result= 0;
     addbutton.click();
     advancedsettinglink.click();
     ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(true);", salifetime);
     if (DpdInterval != null) {         
         dpdinterval.setValue(DpdInterval);
     } 
     String dpdIntervalValue = dpdinterval.getValue();
     Selenide.sleep(20000);
     System.out.println(dpdIntervalValue);
     SelenideElement warningofdpdinterval=$x("//div[@class ='ant-form-explain']");
    // System.out.println(warningofdpdinterval.getText());
     if (Integer.valueOf(DpdInterval).intValue() < 1 || Integer.valueOf(DpdInterval).intValue() > 300 ) {
         System.out.println(warningofdpdinterval.getText());
         String WarningContent = warningofdpdinterval.getText();
         if  (WarningContent.compareTo("This is not a valid DPD Interval.") == 0) {
             logger.info("ddd2222222234444444444444556666663335555555");
             Result = 1; 
         } else {
             Result = 2;
         }
     } else  { 
         System.out.print(warningofdpdinterval.exists());
         if (!warningofdpdinterval.exists()) {
             Result = 3;  
         } else {
             Result = 4;
         }    
     } 
    return Result;
 }

}
