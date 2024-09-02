package businessrouter.webpageoperation;
import java.util.Map;
import java.util.logging.Logger;

import com.codeborne.selenide.Selenide;

import businessrouter.webelements.BrAllMenueElements;
import businessrouter.webelements.BrBasicWanSetupElements;
import webportal.param.WebportalParam;
public class BrBasicWanSetupPage extends BrBasicWanSetupElements{
    final static Logger logger = Logger.getLogger("BrBasicWanSetupPage");
    public BrBasicWanSetupPage() {
        // TODO Auto-generated constructor stub
    }
    public void OpenBasicWanSetupPage() {
        //open(LoginURL);
        BrAllMenueElements BrAllMenueElements = new BrAllMenueElements();
        logger.info("Open Basic WAN Setup  Page");
        logger.info("OpenBasicWanSetupPage  Start");
        //Selenide.open(BrURLParam.LANSetup);
        BrAllMenueElements.basic.click();
        if (WebportalParam.DUTType.contentEquals("BR500")) {
            BrAllMenueElements.Setup.click();
            BrAllMenueElements.WANSetup.click();
        }else if(WebportalParam.DUTType.contentEquals("BR100")) {
            System.out.println("df788888888888888888888888888");
            BrAllMenueElements.SetupBr100.click(); 
            BrAllMenueElements.WANSetup.click();
            BrAllMenueElements.wan1br100.click();
        }
        
        logger.info("OpenBasicWanSetupPage  End");
    }
    public void ChangeWanPortModeDhcpOrStatic(Map<String, String> WanPortModeInfo) {
        //open(LoginURL);
        BrAllMenueElements BrAllMenueElements = new BrAllMenueElements();
        logger.info("ChangeWanPortModeDhcpOrStatic Start");
        if (!internetconnectionreqno.isSelected()) {
            internetconnectionreqno.selectRadio("dhcp");
        }
        
        if (WanPortModeInfo.get("Mode").contentEquals("DHCP")) {
           System.out.println("00000000000000000000000000000");
            System.out.print(ipautomatic.isSelected());
            if(!ipautomatic.isSelected()) {
                logger.info("9999999999999999999999999999999999999"); 
                ipautomatic.selectRadio("dhcp");
            }
            
        } else if (WanPortModeInfo.get("Mode").contentEquals("Static")) {
            if(!ipstatic.isSelected()) {
                ipstatic.selectRadio("static");
            }
            if (WanPortModeInfo.get("WAN IP") != null) {
                String  []WanIpList = WanPortModeInfo.get("WAN IP").split("\\.");  
                logger.info(WanIpList[0]); 
                Selenide.sleep(2000);
                wanipaddress1.setValue(WanIpList[0]);
                wanipaddress2.setValue(WanIpList[1]);
                wanipaddress3.setValue(WanIpList[2]);
                wanipaddress4.setValue(WanIpList[3]); 
            } else {
                logger.info("WAN IP Address is null");
                
            }
            if (WanPortModeInfo.get("WAN Subnet Mask") != null) {
                String  []WanSubMaskList = WanPortModeInfo.get("WAN Subnet Mask").split("\\.");  
                logger.info(WanSubMaskList[0]); 
                Selenide.sleep(2000);
                wanipsubmask1.setValue(WanSubMaskList[0]);
                wanipsubmask2.setValue(WanSubMaskList[1]);
                wanipsubmask3.setValue(WanSubMaskList[2]);
                wanipsubmask4.setValue(WanSubMaskList[3]); 
            } else {
                logger.info("WAN Subnet Mask is null");
                
            }
            if (WanPortModeInfo.get("WAN Gateway") != null) {
                String  []WanGatewayList = WanPortModeInfo.get("WAN Gateway").split("\\.");  
                logger.info(WanGatewayList[0]); 
                Selenide.sleep(2000);
                wanipgateway1.setValue(WanGatewayList[0]);
                wanipgateway2.setValue(WanGatewayList[1]);
                wanipgateway3.setValue(WanGatewayList[2]);
                wanipgateway4.setValue(WanGatewayList[3]); 
            } else {
                logger.info("WAN Gateway is null");
                
            }           
       
        } else {
            logger.info("WAN Mode is null");  
           
        }
        if (WanPortModeInfo.get("DNS Mode").contentEquals("Automatic")) {
            if(!dnsautomatic.isSelected()) {
                dnsautomatic.selectRadio("0");
            }          
        } else if (WanPortModeInfo.get("DNS Mode").contentEquals("Static")) {
            //if(!dnsstatic.isSelected()) {
                dnsstatic.selectRadio("1");
            //}
            if (WanPortModeInfo.get("First DNS") != null) {
            String  []WanFirstDnsList = WanPortModeInfo.get("First DNS").split("\\.");  
            logger.info(WanFirstDnsList[0]); 
            Selenide.sleep(2000);
            wanprimarydnsaddress1.setValue(WanFirstDnsList[0]);
            wanprimarydnsaddress2.setValue(WanFirstDnsList[1]);
            wanprimarydnsaddress3.setValue(WanFirstDnsList[2]);
            wanprimarydnsaddress4.setValue(WanFirstDnsList[3]); 
            } else {
               logger.info("First DNS is null");
            
           }
           if (WanPortModeInfo.get("Second DNS") != null) {
            String  []WanSecondDnsList = WanPortModeInfo.get("Second DNS").split("\\.");  
            logger.info(WanSecondDnsList[0]); 
            Selenide.sleep(2000);
            wansecondarydnsaddress1.setValue(WanSecondDnsList[0]);
            wansecondarydnsaddress2.setValue(WanSecondDnsList[1]);
            wansecondarydnsaddress3.setValue(WanSecondDnsList[2]);
            wansecondarydnsaddress4.setValue(WanSecondDnsList[3]); 
           } else {
            logger.info("Second DNS is null");
            
           }
           if (WanPortModeInfo.get("Third DNS") != null) {
            String  []WanThirdDnsList = WanPortModeInfo.get("Third DNS").split("\\.");  
            logger.info(WanThirdDnsList[0]); 
            Selenide.sleep(2000);
            wanthirddnsaddress1.setValue(WanThirdDnsList[0]);
            wanthirddnsaddress2.setValue(WanThirdDnsList[1]);
            wanthirddnsaddress3.setValue(WanThirdDnsList[2]);
            wanthirddnsaddress4.setValue(WanThirdDnsList[3]); 
           } else {
            logger.info("Third DNS is null");
            
           }
            
     }
        
        if (wanapplybutton.isEnabled()) {
            wanapplybutton.click();
        }
        logger.info("OpenBasicWanSetupPage End");
    }
    public boolean CheckWanPortIpInfo(Map<String, String> WanPortInfo) {
        boolean Result1 = true;
        boolean Result2 = true;
        boolean Result3 = true;
        boolean Result4 = true;
        boolean Result = true;
        logger.info("CheckWanPortIpInfo Start");
        if (WanPortInfo.get("WAN IP") != null) {
            String  []WanIpList = WanPortInfo.get("WAN IP").split("\\.");  
            logger.info(WanIpList[0]); 
            Selenide.sleep(2000);
            if ( wanipaddress1.getValue().contentEquals((WanIpList[0])) && wanipaddress2.getValue().contentEquals((WanIpList[1])) && wanipaddress3.getValue().contentEquals((WanIpList[2]))) {
                Result1 = true;
            } else {
                Result1 = false;
            }
            
        } 
        if (WanPortInfo.get("WAN Mask") != null) {
            String  []WanSubMaskList = WanPortInfo.get("WAN Mask").split("\\.");  
            logger.info(WanSubMaskList[0]); 
            Selenide.sleep(2000);
      
            if (wanipsubmask1.getValue().contentEquals(WanSubMaskList[0]) && wanipsubmask2.getValue().contentEquals(WanSubMaskList[1]) && wanipsubmask3.getValue().contentEquals(WanSubMaskList[2]) && wanipsubmask4.getValue().contentEquals(WanSubMaskList[3])) {
                Result2 = true;  
            }else {
                Result1 = false;
            }
            
        } 
        if (WanPortInfo.get("WAN Gateway") != null) {
            String  []WanGatewayList = WanPortInfo.get("WAN Gateway").split("\\.");  
            logger.info(WanGatewayList[0]); 
            Selenide.sleep(2000);
           if( wanipgateway1.getValue().contentEquals(WanGatewayList[0]) && wanipgateway2.getValue().contentEquals(WanGatewayList[1]) && wanipgateway3.getValue().contentEquals(WanGatewayList[2])&&  wanipgateway4.getValue().contentEquals(WanGatewayList[3])) {
               Result3 = true;  
           }else {
               Result3 = false;
           }
        }
        if (WanPortInfo.get("First DNS") != null) {
            String  []WanFirstDnsList = WanPortInfo.get("First DNS").split("\\.");  
            logger.info(WanFirstDnsList[0]); 
            Selenide.sleep(2000);
            if( wanprimarydnsaddress1.getValue().contentEquals(WanFirstDnsList[0]) && wanprimarydnsaddress2.getValue().contentEquals(WanFirstDnsList[1]) && wanprimarydnsaddress3.getValue().contentEquals(WanFirstDnsList[2]) && wanprimarydnsaddress3.getValue().contentEquals(WanFirstDnsList[3])) {
                Result4 = true;  
            }else {
                Result4 = false;
            }
                    
        }
        Result = Result1 && Result2 && Result3 && Result4;
        logger.info("CheckWanPortIpInfo End");
        return Result; 
    }
    public boolean EditDeviceNameInfo(String NewDeviceName) {
        boolean Result = false;
        logger.info("EditDeviceNameInfo Start");
        devicenameeditbutton.click();
        devicename.setValue(NewDeviceName);
        if(devicenameeditapplybutton.isEnabled()) {
            devicenameeditapplybutton.click();
            devicenameeditbutton.click();
            String EdittedName = devicename.getValue();
            if (EdittedName.contentEquals(NewDeviceName)) {
                Result = true; 
            }
        }
        
        logger.info("EditDeviceNameInfo End");
        return Result;
    
    }
    public boolean EditDeviceNameInfoWithSpecialCha(Map<String, String> DeviceNameInfo) {
        boolean Result = false;
        logger.info("EditDeviceNameInfoWithSpecialCha Start");
        devicenameeditbutton.click();
        devicename.setValue(DeviceNameInfo.get("Device Name"));
  
        //if (devicenamewarning.exists()) {
            System.out.print("DDD");
          String Warning =  devicenamewarning.getText();
          System.out.print(devicenamewarning.getText());
          if (Warning.contentEquals(DeviceNameInfo.get("Warning"))) {
              Result = true;
          //}
        }
        logger.info("EditDeviceNameInfoWithSpecialCha End");
        return Result;
    }
    public boolean EditDomainNameInfo(String NewDomainName) {
        boolean Result = false;
        logger.info("EditDomainNameInfo Start");
        //devicenameeditbutton.click();
        if (NewDomainName!="") {
            domainname.setValue(NewDomainName);
            if(wanapplybutton.isEnabled()) {
                wanapplybutton.click();
                Selenide.sleep(20000);
                String EdittedName = domainname.getValue();
                if (EdittedName.contentEquals(NewDomainName)) {
                    Result = true; 
                }
            }
        } else {
            logger.info("1111111111111111dadsafdasd:");
            //domainname.clear();
            domainname.setValue(NewDomainName);
            Selenide.sleep(18000);
            ipstatic.selectRadio("static");
            ipautomatic.selectRadio("dhcp");
            Selenide.sleep(18000);
            
            //if(wanapplybutton.isEnabled()) {
           // wanapplybutton.attr("enabled");
                logger.info("dadsafdasd:");
                wanapplybutton.click();
                Selenide.sleep(20000);
               
                String EdittedName = domainname.getValue();
                logger.info("EdittedName:" +EdittedName);
                if (EdittedName == "") {
                    Result = true; 
                }
            //}
        }
        //devicenameeditbutton.click();
       
        logger.info("EditDomainNameInfo End");
        return Result;
    
    }
    public boolean Editidletime(String Newidletime) {
        boolean Result = false;
        logger.info("Editidletime Start");
        if (!internetconnectionreqyes.isSelected()) {
            internetconnectionreqyes.selectRadio("pppoe");
        }
        
        //connectionmode.click();
        //Selenide.sleep(4000);
        //connectionmodedialondemand.click();
        //Selenide.sleep(4000);
        idletimeout.setValue(Newidletime);
         
      
        if(wanapplybutton.isEnabled()) {
            wanapplybutton.click();
            Selenide.sleep(20000);
            String EditedIdleTime = idletimeout.getValue();
            if (EditedIdleTime.contentEquals(Newidletime)) {
                Result = true; 
            }
        }
        
        logger.info("Editidletime End");
        return Result;
    
    }
    public boolean SetPptpL2tpAndPppoe(Map<String, String> ISPAccountInfo) {
        boolean Result = false;
        logger.info("SetPptpL2tpAndPppoe Start");
        if (!internetconnectionreqyes.isSelected()) {
            internetconnectionreqyes.selectRadio("pppoe");
        }
        
        if (ISPAccountInfo.get("Internet Service") != null) {
            logger.info(ISPAccountInfo.get("Internet Service"));
            interservicetype.click();
            if (ISPAccountInfo.get("Internet Service").equalsIgnoreCase("PPPoE")) {
                interservicetypepppoe.click();
                interpppoeusername.setValue(ISPAccountInfo.get("User Name"));
                interpppoepassword.setValue(ISPAccountInfo.get("Passeword"));
            }else if(ISPAccountInfo.get("Internet Service").equalsIgnoreCase("PPTP")){
                interservicetypepptp.click();   
                interpptpusername.setValue(ISPAccountInfo.get("User Name"));
                interpptppassword.setValue(ISPAccountInfo.get("Passeword"));           
            }else if(ISPAccountInfo.get("Internet Service").equalsIgnoreCase("L2TP")){
                interservicetypel2tp.click();
                interl2tpusername.setValue(ISPAccountInfo.get("User Name"));
                interl2tppassword.setValue(ISPAccountInfo.get("Passeword"));
            }
            Selenide.sleep(4000);
        } 
        
        
       if (ISPAccountInfo.get("Connection Mode") != null) {
            
           connectionmode.click();
           Selenide.sleep(3000);
            if (ISPAccountInfo.get("Connection Mode").equalsIgnoreCase("Always On")) {
                connectionmodealwayson.click();
            }else if(ISPAccountInfo.get("Connection Mode").equalsIgnoreCase("Dial on Demand")){
                connectionmodedialondemand.click();    
                if (ISPAccountInfo.get("Idletime") != null) {
                    if (ISPAccountInfo.get("Internet Service").equalsIgnoreCase("PPPoE")) {
                        idletimeout.setValue(ISPAccountInfo.get("Idletime"));
                    }else if(ISPAccountInfo.get("Internet Service").equalsIgnoreCase("PPTP")){  
                        pptpidletime.setValue(ISPAccountInfo.get("Idletime"));                              
                    }else if(ISPAccountInfo.get("Internet Service").equalsIgnoreCase("L2TP")){
                        l2tppidletime.setValue(ISPAccountInfo.get("Idletime"));
                    }
                    
                }
            }else if(ISPAccountInfo.get("Connection Mode").equalsIgnoreCase("Manually Connect")){
                logger.info("1222222222222222222222222222222");
                connectionmodemanualconnect.click();
            }
            Selenide.sleep(4000);
        }
       if(ISPAccountInfo.get("Internet Service").equalsIgnoreCase("PPTP") ||  ISPAccountInfo.get("Internet Service").equalsIgnoreCase("L2TP")){
           if (ISPAccountInfo.get("My IP") != null) {
               BrTMSPage  brTmsPage = new  BrTMSPage();
               String NewMyIp = brTmsPage.GetNewIPAddress(ISPAccountInfo.get("My IP"));
               String  []NewMyIpList = NewMyIp.split("\\.");
               intermyip1.setValue(NewMyIpList[0]);
               intermyip2.setValue(NewMyIpList[1]);
               intermyip3.setValue(NewMyIpList[2]);
               intermyip4.setValue(NewMyIpList[3]); 
               
           }
           if (ISPAccountInfo.get("Server IP") != null) {
               String  []ServerIpList = ISPAccountInfo.get("Server IP").split("\\.");
               interserverip1.setValue(ServerIpList[0]);
               interserverip2.setValue(ServerIpList[1]);
               interserverip3.setValue(ServerIpList[2]);
               interserverip4.setValue(ServerIpList[3]); 
               
           }
           if (ISPAccountInfo.get("Sub Mask") != null) {
               String  []SubMaskList = ISPAccountInfo.get("Sub Mask").split("\\.");
               intermyipmask1.setValue(SubMaskList[0]);
               intermyipmask2.setValue(SubMaskList[1]);
               intermyipmask3.setValue(SubMaskList[2]);
               intermyipmask4.setValue(SubMaskList[3]); 
               
           }
           if (ISPAccountInfo.get("Gateway") != null) {
               String  []GatewayList = ISPAccountInfo.get("Gateway").split("\\.");
               intermyipgateway1.setValue(GatewayList[0]);
               intermyipgateway2.setValue(GatewayList[1]);
               intermyipgateway3.setValue(GatewayList[2]);
               intermyipgateway4.setValue(GatewayList[3]); 
               
           }
                  
       }
       System.out.print(interisdnsmode.isSelected());
      if(!interisdnsmode.isSelected()) {
          logger.info("566666666666666666666666666666666666666");
          interisdnsmode.selectRadio("0");
      }
         
      
        if(wanapplybutton.isEnabled()) {
            wanapplybutton.click();
            Selenide.sleep(60000);
        }
        String UserName1;
        if (ISPAccountInfo.get("Internet Service").equalsIgnoreCase("PPPoE")) {
             UserName1 = interpppoeusername.getValue();
             if (UserName1.contentEquals(ISPAccountInfo.get("User Name"))) {
                 Result = true; 
              }
                
         }else if(ISPAccountInfo.get("Internet Service").equalsIgnoreCase("PPTP")){
              UserName1 = interpptpusername.getValue();  
              logger.info("UserName1:" + UserName1);
              if (UserName1.contentEquals(ISPAccountInfo.get("User Name"))) {
                  Result = true; 
              }
                
         }else if(ISPAccountInfo.get("Internet Service").equalsIgnoreCase("L2TP")){
             UserName1 = interl2tpusername.getValue();
              if (UserName1.contentEquals(ISPAccountInfo.get("User Name"))) {
                  Result = true; 
                
              }
         }
         System.out.print(Result);
        
        logger.info("SetPptpL2tpAndPppoe End");
        return Result;
    
    }
    
}
