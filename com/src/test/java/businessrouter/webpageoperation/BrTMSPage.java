package businessrouter.webpageoperation;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;

import businessrouter.webelements.BrTMSElements;
public class BrTMSPage extends BrTMSElements  {
    final static Logger logger = Logger.getLogger("BrTMSPage");
    public String  LoginTMS(String ClientIP) {
        String HistoryHandle = "";;
        String ClientUrl = "window.open(\"http://" + ClientIP + "/tmshtml/login.html\");";
        logger.info("ClientUrl:" + ClientUrl);
        String js = "window.open(\"http://192.168.1.8/tmshtml/login.html\");";
        Selenide.executeJavaScript(ClientUrl);
        WebDriverRunner WebDriverRunner= new WebDriverRunner(); 
        HistoryHandle = WebDriverRunner.getWebDriver().getWindowHandle();
        logger.info(HistoryHandle);
        Set<String> list=new HashSet<String>();
        list = WebDriverRunner.getWebDriver().getWindowHandles();
        System.out.print(list.size());
        for (String handle : list) {  
           logger.info(handle);
           logger.info(HistoryHandle);
           if(!handle.contentEquals(HistoryHandle)) {
               logger.info("23333333333333333333333333");          
               WebDriverRunner.getWebDriver().switchTo().window(handle);
           } else {
               
               logger.info("Don't need to switch browser window.");   
           }  
        }
        Selenide.sleep(2000);
        username.setValue("hwang");
        password.setValue("123456");
        loginbutton.click();
        commoninterface.click();
        return HistoryHandle;
    }
    
    public String GetBrowserHandles() {
        WebDriverRunner WebDriverRunner= new WebDriverRunner(); 
        String CurrentHandle = WebDriverRunner.getWebDriver().getWindowHandle();
        logger.info(CurrentHandle);
        return CurrentHandle;
      
     
    }
    public void ChangeBrowserHandles(String Histroyhanle) {
        WebDriverRunner WebDriverRunner= new WebDriverRunner(); 
        Set<String> list=new HashSet<String>();
        list = WebDriverRunner.getWebDriver().getWindowHandles();
        for (String handle : list) {  
            logger.info(handle);
            logger.info(Histroyhanle);
            if(!handle.contentEquals(Histroyhanle)) {
                WebDriverRunner.getWebDriver().switchTo().window(Histroyhanle);
                
            } else {
                logger.info("Don't need to switch browser window."); 
            }  
        }
        
    }

    public void LogoutTMSAndCloseBrowser() {
        WebDriverRunner.getWebDriver().quit();
 
    }
    public boolean UdpTcpTrafficPassOrFailed(Map<String, String> TmsCommand) {
        boolean TmsResut= false;
        //if (commoninterface.exists()) {
        //    commoninterface.click();
        //}
        Selenide.sleep(2000);
        
        if (TmsCommand.get("Interface Type") != null) {
            interfacetype.selectOption(TmsCommand.get("Interface Type"));
        } else {
            logger.info("Interface Type is null");
            
        }
        if (TmsCommand.get("Command") != null) {
            command.selectOption(TmsCommand.get("Command"));
            
            }else{
                logger.info("Command is null");
            }  
        Selenide.sleep(2000);
        if (TmsCommand.get("Dut IP") != null) {
            dutip.setValue(TmsCommand.get("Dut IP"));
         } else {
            logger.info("Dut IP is null");
                
         }
        if (TmsCommand.get("LAN port") != null) {
            dutport.setValue(TmsCommand.get("LAN port"));
         } else {
            logger.info("LAN port is null");
                
         }
        if (TmsCommand.get("Host IP") != null && TmsCommand.get("WAN port") != null ) {
            String HostCommand = TmsCommand.get("Host IP") +":"+ TmsCommand.get("WAN port")+"," +TmsCommand.get("WAN connetc IP");
            logger.info(HostCommand);
            hostip.setValue(HostCommand);
         } else {
            logger.info("Host IP or WAN port is null");
                
         }
        if (TmsCommand.get("Protocol") != null) {
            parameter.setValue(TmsCommand.get("Protocol"));
         } else {
            logger.info("Protocol is null");
                
         }
        applybutton.click();
        Selenide.sleep(20000);
      
        if (result.exists()) {
            
           String Result =  result.getText();
           logger.info(Result);
           if (Result.contentEquals("RET_OK")) {
               TmsResut = true;  
           } else {
               TmsResut = false;   
           }
        } else {
            TmsResut = false;  
        }
        return TmsResut;
    }
  public void BackTMSConfigPafe() {
       if (backbutton.exists()) {
           backbutton.click();          
   }
       Selenide.sleep(1000);
       if (resetbutton.exists()) {
           resetbutton.click();
       }    
 
    }
    public boolean CheckPingPassOrFailed(Map<String, String> TmsCommand) {
        boolean TmsResut= false;
        //if (commoninterface.exists()) {
        //    commoninterface.click();
        //}
        Selenide.sleep(2000);
        
        if (TmsCommand.get("Interface Type") != null) {
            interfacetype.selectOption(TmsCommand.get("Interface Type"));
        } else {
            logger.info("Interface Type is null");
            
        }
        
        Selenide.sleep(2000);
        if (TmsCommand.get("Dut IP") != null) {
            dutip.setValue(TmsCommand.get("Dut IP"));
         } else {
            logger.info("Dut IP is null");
         }
        if (TmsCommand.get("Host IP") != null ) {
               
            hostip.setValue(TmsCommand.get("Host IP"));
         } else {
                logger.info("Host IP  port is null");
                    
         }       
        
        if (TmsCommand.get("Protocol") != null) {
            logger.info(TmsCommand.get("Protocol"));
            parameter.setValue(TmsCommand.get("Protocol"));
         } else {
            logger.info("Protocol is null");
                
         }
        applybutton.click();
        Selenide.sleep(20000);
       String CmdResult = cmdresult.getText();
       logger.info(CmdResult);
       
       int LostResult= 0;
       LostResult= CmdResult.indexOf("100% packet loss");
       
       int PassResult = 0;
       PassResult = CmdResult.indexOf("5 received");
       System.out.println("99999999999999999999999");
       logger.info(String.valueOf(LostResult));
       logger.info(String.valueOf(PassResult));
        if (LostResult >= 0 && PassResult== -1 ) {
            TmsResut = false;  
        } else if(LostResult >= -1 && PassResult >=0 ) {
            TmsResut = true;   
        } else {
            TmsResut = false;  
        }
        System.out.print(TmsResut);
        return TmsResut;
     }
   
    public String GetNetworkCardMacAddress(Map<String, String> TmsCommand) {
        boolean TmsResut= false;
        
        Selenide.sleep(2000);
        
        if (TmsCommand.get("Interface Type") != null) {
            interfacetype.selectOption(TmsCommand.get("Interface Type"));
        } else {
            logger.info("Interface Type is null");
            
        }
        
        Selenide.sleep(2000);
        if (TmsCommand.get("Dut IP") != null) {
            dutip.setValue(TmsCommand.get("Dut IP"));
         } else {
            logger.info("Dut IP is null");
         }
        if (TmsCommand.get("Host IP") != null ) {
               
            hostip.setValue(TmsCommand.get("Host IP"));
         } else {
                logger.info("Host IP  port is null");
                    
         }       
        
        if (TmsCommand.get("Protocol") != null) {
            logger.info(TmsCommand.get("Protocol"));
            parameter.setValue(TmsCommand.get("Protocol"));
         } else {
            logger.info("Protocol is null");
                
         }
        applybutton.click();
        Selenide.sleep(20000);
        String CmdResult = cmdresult.getText();
       logger.info(CmdResult);
       int MacLocationindex= CmdResult.indexOf("HWaddr");
       logger.info(String.valueOf(MacLocationindex));
       String MacAddress = CmdResult.substring(MacLocationindex+7, MacLocationindex+24);
       logger.info(MacAddress);
       return MacAddress;
    }
    //Get New address which is more 1 than old address.
    public String GetNewIPAddress(String OldIPAddress) {
        String Newdeip = "";
        String SIPAddress = "";
        System.out.println(OldIPAddress);
        String []OldIpList = OldIPAddress.split("\\.");
       
        
        int a = Integer.valueOf(OldIpList[3]).intValue();
        if ( a < 254) {
            a = a + 1;
            SIPAddress = String.valueOf(a);
            Newdeip = OldIpList[0]+"." + OldIpList[1]+"."+ OldIpList[2]+"."+SIPAddress;
        } else {
            int b = Integer.valueOf(OldIpList[2]).intValue();
            if (b < 254) {
                b= b + 1;
                SIPAddress = String.valueOf(b);
                 Newdeip = OldIpList[0]+"." + OldIpList[1]+"."+ SIPAddress+"."+OldIpList[3];
            } else {
              int c = Integer.valueOf(OldIpList[1]).intValue();
              if (c < 254) {
                  c = c + 1;
                  SIPAddress = String.valueOf(c);
                  Newdeip = OldIpList[0]+"." + SIPAddress+"."+ OldIpList[2]+"."+OldIpList[3];
              } else {
                  int d = Integer.valueOf(OldIpList[0]).intValue(); 
                  if (d < 254) {
                      d = d + 1;
                      SIPAddress = String.valueOf(c);
                      Newdeip = SIPAddress+"." + OldIpList[1]+"."+ OldIpList[2]+"."+OldIpList[3];
                  }else {
                      logger.info("IPv4 address overflow");  
                  }
              }
            }
        }
        logger.info(Newdeip);
        return Newdeip;
      
     }
    public void CloseTMSWindows() {
        WebDriverRunner.getWebDriver().close();
 
    }
    
    public boolean RuncmdByTMS(Map<String, String> TmsCommand) {
        boolean TmsResut= false;
        //if (commoninterface.exists()) {
        //    commoninterface.click();
        //}
        Selenide.sleep(2000);
        
        if (TmsCommand.get("Interface Type") != null) {
            interfacetype.selectOption(TmsCommand.get("Interface Type"));
        } else {
            logger.info("Interface Type is null");
            
        }
        
        Selenide.sleep(2000);
        if (TmsCommand.get("Dut IP") != null) {
            dutip.setValue(TmsCommand.get("Dut IP"));
         } else {
            logger.info("Dut IP is null");
         }
        if (TmsCommand.get("Host IP") != null ) {
               
            hostip.setValue(TmsCommand.get("Host IP"));
         } else {
                logger.info("Host IP  port is null");
                    
         }       
        
        if (TmsCommand.get("Protocol") != null) {
            logger.info(TmsCommand.get("Protocol"));
            parameter.setValue(TmsCommand.get("Protocol"));
         } else {
            logger.info("Protocol is null");
                
         }
        applybutton.click();
        Selenide.sleep(12000);
        
        if (result.exists()) {
            
           String Result =  result.getText();
           logger.info(Result);
           if (Result.contentEquals("RET_OK")) {
               TmsResut = true;  
           } else {
               TmsResut = false;   
           }
        } else {
            TmsResut = false;  
        }
        return TmsResut;
    }
    
    public String GetNetworkCardIpAddress(Map<String, String> TmsCommand) {
        boolean TmsResut= false;
        
        Selenide.sleep(2000);
        
        if (TmsCommand.get("Interface Type") != null) {
            interfacetype.selectOption(TmsCommand.get("Interface Type"));
        } else {
            logger.info("Interface Type is null");
            
        }
        
        Selenide.sleep(2000);
        if (TmsCommand.get("Dut IP") != null) {
            dutip.setValue(TmsCommand.get("Dut IP"));
         } else {
            logger.info("Dut IP is null");
         }
        if (TmsCommand.get("Host IP") != null ) {
               
            hostip.setValue(TmsCommand.get("Host IP"));
         } else {
                logger.info("Host IP  port is null");
                    
         }       
        
        if (TmsCommand.get("Protocol") != null) {
            logger.info(TmsCommand.get("Protocol"));
            parameter.setValue(TmsCommand.get("Protocol"));
         } else {
            logger.info("Protocol is null");
                
         }
        applybutton.click();
        Selenide.sleep(20000);
        String CmdResult = cmdresult.getText();
       logger.info(CmdResult);
       int IpLocationindex= CmdResult.indexOf("inet addr:");
       int BcastLocationindex= CmdResult.indexOf("Bcast:");
       int IpLength = BcastLocationindex - IpLocationindex -1;
       logger.info(String.valueOf(IpLocationindex));
       logger.info(String.valueOf(BcastLocationindex));
       String IpAddress = CmdResult.substring(IpLocationindex+10, IpLocationindex+IpLength);
       logger.info(IpAddress);
       return IpAddress.trim();
    }
    public String GetNetworkCardIpv6Address(Map<String, String> TmsCommand) {
        boolean TmsResut= false;
        
        Selenide.sleep(2000);
        
        if (TmsCommand.get("Interface Type") != null) {
            interfacetype.selectOption(TmsCommand.get("Interface Type"));
        } else {
            logger.info("Interface Type is null");
            
        }
        
        Selenide.sleep(2000);
        if (TmsCommand.get("Dut IP") != null) {
            dutip.setValue(TmsCommand.get("Dut IP"));
         } else {
            logger.info("Dut IP is null");
         }
        if (TmsCommand.get("Host IP") != null ) {
               
            hostip.setValue(TmsCommand.get("Host IP"));
         } else {
                logger.info("Host IP  port is null");
                    
         }       
        
        if (TmsCommand.get("Protocol") != null) {
            logger.info(TmsCommand.get("Protocol"));
            parameter.setValue(TmsCommand.get("Protocol"));
         } else {
            logger.info("Protocol is null");
                
         }
        applybutton.click();
        Selenide.sleep(20000);
        String CmdResult = cmdresult.getText();
       logger.info(CmdResult);
       int IpLocationindex= CmdResult.indexOf("inet addr6:");
       int BcastLocationindex= CmdResult.indexOf("Scope:Global");
       int IpLength = BcastLocationindex - IpLocationindex -1;
       logger.info(String.valueOf(IpLocationindex));
       logger.info(String.valueOf(BcastLocationindex));
       String Ipv6Address = CmdResult.substring(IpLocationindex+11, IpLocationindex+IpLength);
       logger.info(Ipv6Address);
       return Ipv6Address.trim();
    }
    
    public boolean CheckUrlPassOrFailed(Map<String, String> TmsCommand) {
        boolean TmsResut= false;
        //if (commoninterface.exists()) {
        //    commoninterface.click();
        //}
        Selenide.sleep(2000);
        
        if (TmsCommand.get("Interface Type") != null) {
            interfacetype.selectOption(TmsCommand.get("Interface Type"));
        } else {
            logger.info("Interface Type is null");
            
        }
        
        Selenide.sleep(2000);
        if (TmsCommand.get("Dut IP") != null) {
            dutip.setValue(TmsCommand.get("Dut IP"));
         } else {
            logger.info("Dut IP is null");
         }
        if (TmsCommand.get("Host IP") != null ) {
               
            hostip.setValue(TmsCommand.get("Host IP"));
         } else {
                logger.info("Host IP  port is null");
                    
         }       
        
        if (TmsCommand.get("Protocol") != null) {
            logger.info(TmsCommand.get("Protocol"));
            parameter.setValue(TmsCommand.get("Protocol"));
         } else {
            logger.info("Protocol is null");
                
         }
        applybutton.click();
        Selenide.sleep(20000);
       String CmdResult = cmdresult.getText();
       logger.info(CmdResult);
       
       int failelocation= -1;
       failelocation= CmdResult.indexOf("Web Site Blocked by NETGEAR Firewall");
       logger.info(String.valueOf(failelocation));
 
        if (failelocation >= 0 ) {
            TmsResut = false;  
        }else {
            TmsResut = true;  
        }
        System.out.print(TmsResut);
        return TmsResut;
     }
}
