package businessrouter.webpageoperation;

import java.util.Map;
import java.util.logging.Logger;

import com.codeborne.selenide.Selenide;

import businessrouter.webelements.BrAdvancedIPv6Elements;
import businessrouter.webelements.BrAllMenueElements;

public class BrAdvancedIPv6Page extends BrAdvancedIPv6Elements {
    final static Logger logger = Logger.getLogger("BrAdvancedIPv6Page");
    public BrAdvancedIPv6Page() {
        // TODO Auto-generated constructor stub
    }
    public void OpenAdvancedIPv6Page() {
        //open(LoginURL);
        BrAllMenueElements BrAllMenueElements = new BrAllMenueElements();
                
        logger.info("Open Advanced IPv6 Page");
        BrAllMenueElements.advanced.click();
        BrAllMenueElements.IPv6.click();

    }
    
    public void ChangeIPv6ConnectionType(String Ipv6ConnetionType) {
        logger.info("ChangeIPv6ConnectionType start");
        internetconnectiontype.click();
        if (Ipv6ConnetionType.equalsIgnoreCase("Auto Detect")) {
            connectiontypeautodetect.click();
        }else if(Ipv6ConnetionType.equalsIgnoreCase("Auto Config")){
            connectiontypeautoconfig.click();               
        }else if(Ipv6ConnetionType.equalsIgnoreCase("DHCP")){
            connectiontypedhcp.click();
        }else if(Ipv6ConnetionType.equalsIgnoreCase("Fixed")){
            connectiontypefixed.click();
        }else if(Ipv6ConnetionType.equalsIgnoreCase("Pass Through")){
            connectiontypepassthrough.click();
        }else if(Ipv6ConnetionType.equalsIgnoreCase("Disabled")){
            logger.info("22222222222222222222222222222222222222");
            connectiontypedisabled.click();
            logger.info("22222222222222222222222222222222222222333");
        }
        Selenide.sleep(5000);
        ipv6applybutton.click();
        logger.info("ChangeIPv6ConnectionType end"); 

    }
    public boolean CheckIPv6Info(Map<String, String> IPv6Info) {
        boolean Result = true;
        logger.info("CheckIPv6Info start");
        if (IPv6Info.get("Connection Type") != null) {
            String ConnetionType = ipv6connetiontype.getText();
            logger.info(ConnetionType);
            if(ConnetionType.indexOf(IPv6Info.get("Connection Type")) == -1) {
                Result = false;  
            }
        }
        if (IPv6Info.get("WAN IPv6") != null) {
            String WanIPv6 = ipv6wanip.getText();
            logger.info(WanIPv6);
            if(WanIPv6.indexOf(IPv6Info.get("WAN IPv6")) == -1) {
                Result = false;  
            }
        }
        if (IPv6Info.get("LAN IPv6") != null) {
            String LanIpv6 = ipv6lanip.getText();
            System.out.println(LanIpv6);
            logger.info(LanIpv6);
            if(LanIpv6.indexOf(IPv6Info.get("LAN IPv6")) == -1) {
                Result = false;  
            }
        }
        if (IPv6Info.get("WAN DNS") != null) {
            String WanDns1 = ipv6primarydns1.getValue();
            logger.info(WanDns1);
            if(WanDns1.indexOf(IPv6Info.get("WAN DNS")) == -1) {
                Result = false;  
            }
        }
        System.out.print(Result);
        logger.info("CheckIPv6Info end");
        return Result;
        

    }
    public boolean CheckIPv6AutoConfigInfo(Map<String, String> IPv6Info) {
        boolean Result = true;
        String LanIpv6;
        logger.info("CheckIPv6AutoConfigInfo start");
       
        if (IPv6Info.get("WAN IPv6") != null) {
            String WanIPv6 = ipv6autoconfigwanip.getText();
            logger.info(WanIPv6);
            if(WanIPv6.indexOf(IPv6Info.get("WAN IPv6")) == -1) {
                Result = false;  
            }
        }
        if (IPv6Info.get("LAN IPv6") != null) {
            logger.info("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
            if (IPv6Info.get("InterfaceID") != null) {
                logger.info("12DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
                LanIpv6 =ipv6autoconfiglanip2.getText();
            } else {
                LanIpv6 = ipv6autoconfiglanip.getText();
            }
            
           
            logger.info(LanIpv6);
            if(LanIpv6.indexOf(IPv6Info.get("LAN IPv6")) == -1) {
                Result = false;  
            }
        }
        if (IPv6Info.get("WAN DNS") != null) {
            String WanDns1 = ipv6primarydns1.getValue();
            logger.info(WanDns1);
            if(WanDns1.indexOf(IPv6Info.get("WAN DNS")) == -1) {
                Result = false;  
            }
        }
        System.out.print(Result);
        logger.info("CheckIPv6AutoConfigInfo end");
        return Result;
        

    }
    public boolean SetStaticIPv6ForWANPortAndLANPort(Map<String, String> StaticIpv6Info) {
        boolean Result = false;
        logger.info("SetStaticIPv6ForWANPortAndLANPort start");
        internetconnectiontype.click();
        connectiontypefixed.click();
        if (StaticIpv6Info.get("WAN IPv6") != null) {
            String[] LsIpv6Static1 = StaticIpv6Info.get("WAN IPv6").split("::");
            if (LsIpv6Static1.length == 1) {
                String[] LsIpv6Static2 = LsIpv6Static1[0].split(":");
                if(LsIpv6Static2.length == 8 ) {
                    ipv6staticwan1.setValue(LsIpv6Static2[0]);
                    ipv6staticwan2.setValue(LsIpv6Static2[1]);
                    ipv6staticwan3.setValue(LsIpv6Static2[2]);
                    ipv6staticwan4.setValue(LsIpv6Static2[3]);
                    ipv6staticwan5.setValue(LsIpv6Static2[4]);
                    ipv6staticwan6.setValue(LsIpv6Static2[5]);
                    ipv6staticwan7.setValue(LsIpv6Static2[6]);
                    ipv6staticwan8.setValue(LsIpv6Static2[7]);
                    
                } else {
                    logger.info("Static WAN IPv6 format is wrong."); 
                }
                
            } else if(LsIpv6Static1.length == 2) {
                String[] LsIpv6Static2 = LsIpv6Static1[0].split(":");
                String[] LsIpv6Static3 = LsIpv6Static1[1].split(":");
                String[] LsIpv6Static5 = new String[8];
                for (int i = 0; i < LsIpv6Static2.length; i++) {
                    logger.info(LsIpv6Static2[i]); 
                    LsIpv6Static5[i] = LsIpv6Static2[i];
                }
                int ZeroNum = 8 - LsIpv6Static2.length - LsIpv6Static3.length;
                for (int i = 0; i <= ZeroNum; i++) {
                    LsIpv6Static5[i+LsIpv6Static2.length] = "0";   
                }
                for (int i = 0; i < LsIpv6Static3.length; i++) {
                    LsIpv6Static5[i+LsIpv6Static2.length+ZeroNum] = LsIpv6Static3[i];   
                }
                logger.info(LsIpv6Static5[0]); 
                logger.info(LsIpv6Static5[1]);
                logger.info(LsIpv6Static5[2]);
                logger.info(LsIpv6Static5[3]);
                logger.info(LsIpv6Static5[4]);
                logger.info(LsIpv6Static5[5]);
                logger.info(LsIpv6Static5[6]);
                logger.info(LsIpv6Static5[7]);
                
                ipv6staticwan1.setValue(LsIpv6Static5[0]);
                ipv6staticwan2.setValue(LsIpv6Static5[1]);
                ipv6staticwan3.setValue(LsIpv6Static5[2]);
                ipv6staticwan4.setValue(LsIpv6Static5[3]);
                ipv6staticwan5.setValue(LsIpv6Static5[4]);
                ipv6staticwan6.setValue(LsIpv6Static5[5]);
                ipv6staticwan7.setValue(LsIpv6Static5[6]);
                ipv6staticwan8.setValue(LsIpv6Static5[7]);
            }
            
        }
        ipv6staticwanlen.setValue(StaticIpv6Info.get("WAN IPv6 Length"));

        if (StaticIpv6Info.get("Gateway") != null) {
            String[] LsIpv6StaticGateway1 = StaticIpv6Info.get("Gateway").split("::");
            if (LsIpv6StaticGateway1.length == 1) {
                String[] LsIpv6StaticGateway2 = LsIpv6StaticGateway1[0].split(":");
                if(LsIpv6StaticGateway2.length == 8 ) {
                    ipv6wandefaultgateway1.setValue(LsIpv6StaticGateway2[0]);
                    ipv6wandefaultgateway2.setValue(LsIpv6StaticGateway2[1]);
                    ipv6wandefaultgateway3.setValue(LsIpv6StaticGateway2[2]);
                    ipv6wandefaultgateway4.setValue(LsIpv6StaticGateway2[3]);
                    ipv6wandefaultgateway5.setValue(LsIpv6StaticGateway2[4]);
                    ipv6wandefaultgateway6.setValue(LsIpv6StaticGateway2[5]);
                    ipv6wandefaultgateway7.setValue(LsIpv6StaticGateway2[6]);
                    ipv6wandefaultgateway8.setValue(LsIpv6StaticGateway2[7]);
                    
                } else {
                    logger.info("Static IPv6 gateway format is wrong."); 
                }
                
            } else if(LsIpv6StaticGateway1.length == 2) {
                String[] LsIpv6StaticGateway2 = LsIpv6StaticGateway1[0].split(":");
                String[] LsIpv6StaticGateway3 = LsIpv6StaticGateway1[1].split(":");
                String[] LsIpv6StaticGateway5 = new String[8];
                for (int i = 0; i < LsIpv6StaticGateway2.length; i++) {
                    LsIpv6StaticGateway5[i] = LsIpv6StaticGateway2[i];
                }
                int ZeroNum = 8 - LsIpv6StaticGateway2.length - LsIpv6StaticGateway3.length;
                for (int i = 0; i <= ZeroNum; i++) {
                    LsIpv6StaticGateway5[i+LsIpv6StaticGateway2.length] = "0";   
                }
                for (int i = 0; i < LsIpv6StaticGateway3.length; i++) {
                    LsIpv6StaticGateway5[i+LsIpv6StaticGateway2.length+ZeroNum] = LsIpv6StaticGateway3[i];   
                }
                
                ipv6wandefaultgateway1.setValue(LsIpv6StaticGateway5[0]);
                ipv6wandefaultgateway2.setValue(LsIpv6StaticGateway5[1]);
                ipv6wandefaultgateway3.setValue(LsIpv6StaticGateway5[2]);
                ipv6wandefaultgateway4.setValue(LsIpv6StaticGateway5[3]);
                ipv6wandefaultgateway5.setValue(LsIpv6StaticGateway5[4]);
                ipv6wandefaultgateway6.setValue(LsIpv6StaticGateway5[5]);
                ipv6wandefaultgateway7.setValue(LsIpv6StaticGateway5[6]);
                ipv6wandefaultgateway8.setValue(LsIpv6StaticGateway5[7]);
            }
            
        }
        if (StaticIpv6Info.get("Primary DNS") != null) {
            String[] LsIpv6StaticPrimaryDns1 = StaticIpv6Info.get("Primary DNS").split("::");
            if (LsIpv6StaticPrimaryDns1.length == 1) {
                String[] LsIpv6StaticPrimaryDns2 = LsIpv6StaticPrimaryDns1[0].split(":");
                if(LsIpv6StaticPrimaryDns2.length == 8 ) {
                    ipv6staticprimarydns1.setValue(LsIpv6StaticPrimaryDns2[0]);
                    ipv6staticprimarydns2.setValue(LsIpv6StaticPrimaryDns2[1]);
                    ipv6staticprimarydns3.setValue(LsIpv6StaticPrimaryDns2[2]);
                    ipv6staticprimarydns4.setValue(LsIpv6StaticPrimaryDns2[3]);
                    ipv6staticprimarydns5.setValue(LsIpv6StaticPrimaryDns2[4]);
                    ipv6staticprimarydns6.setValue(LsIpv6StaticPrimaryDns2[5]);
                    ipv6staticprimarydns7.setValue(LsIpv6StaticPrimaryDns2[6]);
                    ipv6staticprimarydns8.setValue(LsIpv6StaticPrimaryDns2[7]);
                    
                } else {
                    logger.info("Static IPv6 Primary DNS format is wrong."); 
                }
                
            } else if(LsIpv6StaticPrimaryDns1.length == 2) {
                String[] LsIpv6StaticPrimaryDns2 = LsIpv6StaticPrimaryDns1[0].split(":");
                String[] LsIpv6StaticPrimaryDns3 = LsIpv6StaticPrimaryDns1[1].split(":");
                String[] LsIpv6StaticPrimaryDns5 = new String[8];
                for (int i = 0; i < LsIpv6StaticPrimaryDns2.length; i++) {
                    LsIpv6StaticPrimaryDns5[i] = LsIpv6StaticPrimaryDns2[i];
                }
                int ZeroNum = 8 - LsIpv6StaticPrimaryDns2.length - LsIpv6StaticPrimaryDns3.length;
                for (int i = 0; i <= ZeroNum; i++) {
                    LsIpv6StaticPrimaryDns5[i+LsIpv6StaticPrimaryDns2.length] = "0";   
                }
                for (int i = 0; i < LsIpv6StaticPrimaryDns3.length; i++) {
                    LsIpv6StaticPrimaryDns5[i+LsIpv6StaticPrimaryDns2.length+ZeroNum] = LsIpv6StaticPrimaryDns3[i];   
                }
                
                ipv6staticprimarydns1.setValue(LsIpv6StaticPrimaryDns5[0]);
                ipv6staticprimarydns2.setValue(LsIpv6StaticPrimaryDns5[1]);
                ipv6staticprimarydns3.setValue(LsIpv6StaticPrimaryDns5[2]);
                ipv6staticprimarydns4.setValue(LsIpv6StaticPrimaryDns5[3]);
                ipv6staticprimarydns5.setValue(LsIpv6StaticPrimaryDns5[4]);
                ipv6staticprimarydns6.setValue(LsIpv6StaticPrimaryDns5[5]);
                ipv6staticprimarydns7.setValue(LsIpv6StaticPrimaryDns5[6]);
                ipv6staticprimarydns8.setValue(LsIpv6StaticPrimaryDns5[7]);
            }
            
        }
        
        if (StaticIpv6Info.get("LAN IPv6") != null) {
            String[] LsIpv6StaticLan1 = StaticIpv6Info.get("LAN IPv6").split("::");
            if (LsIpv6StaticLan1.length == 1) {
                String[] LsIpv6StaticLan2 = LsIpv6StaticLan1[0].split(":");
                if(LsIpv6StaticLan2.length == 8 ) {
                    ipv6staticlan1.setValue(LsIpv6StaticLan2[0]);
                    ipv6staticlan2.setValue(LsIpv6StaticLan2[1]);
                    ipv6staticlan3.setValue(LsIpv6StaticLan2[2]);
                    ipv6staticlan4.setValue(LsIpv6StaticLan2[3]);
                    ipv6staticlan5.setValue(LsIpv6StaticLan2[4]);
                    ipv6staticlan6.setValue(LsIpv6StaticLan2[5]);
                    ipv6staticlan7.setValue(LsIpv6StaticLan2[6]);
                    ipv6staticlan8.setValue(LsIpv6StaticLan2[7]);
                    
                } else {
                    logger.info("Static IPv6 Primary DNS format is wrong."); 
                }
                
            } else if(LsIpv6StaticLan1.length == 2) {
                String[] LsIpv6StaticLan2 = LsIpv6StaticLan1[0].split(":");
                String[] LsIpv6StaticLan3 = LsIpv6StaticLan1[1].split(":");
                String[] LsIpv6StaticLan5 = new String[8];
                for (int i = 0; i < LsIpv6StaticLan2.length; i++) {
                    LsIpv6StaticLan5[i] = LsIpv6StaticLan2[i];
                }
                int ZeroNum = 8 - LsIpv6StaticLan2.length - LsIpv6StaticLan3.length;
                for (int i = 0; i <= ZeroNum; i++) {
                    LsIpv6StaticLan5[i+LsIpv6StaticLan2.length] = "0";   
                }
                for (int i = 0; i < LsIpv6StaticLan3.length; i++) {
                    LsIpv6StaticLan5[i+LsIpv6StaticLan2.length+ZeroNum] = LsIpv6StaticLan3[i];   
                }
                
                ipv6staticlan1.setValue(LsIpv6StaticLan5[0]);
                ipv6staticlan2.setValue(LsIpv6StaticLan5[1]);
                ipv6staticlan3.setValue(LsIpv6StaticLan5[2]);
                ipv6staticlan4.setValue(LsIpv6StaticLan5[3]);
                ipv6staticlan5.setValue(LsIpv6StaticLan5[4]);
                ipv6staticlan6.setValue(LsIpv6StaticLan5[5]);
                ipv6staticlan7.setValue(LsIpv6StaticLan5[6]);
                ipv6staticlan8.setValue(LsIpv6StaticLan5[7]);
            }
            
        }
        ipv6staticlanlen.setValue(StaticIpv6Info.get("LAN IPv6 Length"));
        Selenide.sleep(15000);
        ipv6applybutton.click();
        Selenide.sleep(5000);
        if(ipv6staticwanlen.getValue().contentEquals(StaticIpv6Info.get("WAN IPv6 Length"))){
            Result = true;
        }
        System.out.print(Result);
        
        logger.info("SetStaticIPv6ForWANPortAndLANPort end");
        return Result;
        

    }
    public String GeteLanInterfaceIPv6Address() {
        boolean Result = true;
        logger.info("GeteLanInterfaceIPv6Address start");
        String[] LanIpv6 = ipv6lanip.getText().split("/");
        logger.info(LanIpv6[0]);
        return LanIpv6[0];
           
    }
    public void SetIPv6ConnectionTypeAndInterfaceID(Map<String, String> Ipv6Info) {
        logger.info("SetIPv6ConnectionTypeAndInterfaceID start");
        internetconnectiontype.click();
        logger.info("2111111111111111111111111111000000000000000000000");
        if (Ipv6Info.get("Connect Type").equalsIgnoreCase("Auto Detect")) {
            connectiontypeautodetect.click();
        }else if(Ipv6Info.get("Connect Type").equalsIgnoreCase("Auto Config")){
            connectiontypeautoconfig.click();               
        }else if(Ipv6Info.get("Connect Type").equalsIgnoreCase("DHCP")){
            logger.info("2111111111111111111111111111");
            connectiontypedhcp.click();
            logger.info("2111111111111111111111111111");
        }else if(Ipv6Info.get("Connect Type").equalsIgnoreCase("Fixed")){
            connectiontypefixed.click();
        }else if(Ipv6Info.get("Connect Type").equalsIgnoreCase("Pass Through")){
            connectiontypepassthrough.click();
        }else if(Ipv6Info.get("Connect Type").equalsIgnoreCase("Disabled")){
            logger.info("22222222222222222222222222222222222222");
            connectiontypedisabled.click();
            logger.info("22222222222222222222222222222222222222333");
        }
        if (Ipv6Info.get("LAN Type") != null) {
            if (Ipv6Info.get("LAN Type").equalsIgnoreCase("DHCP")) {
                if (!ipv6landhcpserver.isSelected()) {
                    ipv6landhcpserver.selectRadio("dhcp");
                }
              
            }else if(Ipv6Info.get("LAN Type").equalsIgnoreCase("Auto Config")) {
                logger.info("211111111111111111111111111133333");
                if (!ipv6lanautoconfig.isSelected()) {
                    ipv6lanautoconfig.selectRadio("auto");
                }
            }
        }
        if (Ipv6Info.get("InterfaceID" ) != null) {
            logger.info("2111111111111111111111111111wwwwwwwww");
            if (!ipv6enableuseinterfaceid.isSelected()) {
                ipv6enableuseinterfaceid.selectRadio("on");
            }
            String[] LsInterfaceId = Ipv6Info.get("InterfaceID" ).split(":");
            logger.info(LsInterfaceId[0]);
            logger.info(LsInterfaceId[1]);
            logger.info(LsInterfaceId[2]);
            logger.info(LsInterfaceId[3]);
            ipv6laninterfaceid1.setValue(LsInterfaceId[0]);
            ipv6laninterfaceid2.setValue(LsInterfaceId[1]);
            ipv6laninterfaceid3.setValue(LsInterfaceId[2]);
            ipv6laninterfaceid4.setValue(LsInterfaceId[3]);
        }
        Selenide.sleep(5000);
       
        ipv6applybutton.click();
        logger.info("SetIPv6ConnectionTypeAndInterfaceID end"); 

    }
    public boolean ChangeIPv6FilterType(String FilterType) {
        logger.info("ChangeIPv6FilterType start");
        boolean Result = false;
        if (FilterType.equalsIgnoreCase("Secured")) {
            if (!ipv6filtersecured.isSelected()) {
                ipv6filtersecured.selectRadio("Secured");
            }
        }else if(FilterType.equalsIgnoreCase("Open")){
           if (!ipv6filteropen.isSelected()) {
               ipv6filteropen.selectRadio("Open");
               
           }               
        }
        ipv6applybutton.click();
        Selenide.sleep(5000);
        if (FilterType.equalsIgnoreCase("Secured")) {
            if (ipv6filtersecured.isSelected()) {
                Result = true;
            }
        }else if(FilterType.equalsIgnoreCase("Open")){
           if (ipv6filteropen.isSelected()) {
               Result = true;
               
           }               
        }
        logger.info("ChangeIPv6FilterType end"); 
        return Result;

    }
    public boolean CheckIPv6RefreshStatusButton(Map<String, String> IPv6Info) {
        boolean Result = true;
        String LanIpv6;
        logger.info("CheckIPv6RefreshStatusButton start");
        ipv6refreshbutton.click();
        Selenide.sleep(15000);
        if (IPv6Info.get("WAN IPv6") != null) {
            String WanIPv6 = ipv6autoconfigwanip.getText();
            logger.info(WanIPv6);
            if(WanIPv6.indexOf(IPv6Info.get("WAN IPv6")) == -1) {
                Result = false;  
            }
        }
        if (IPv6Info.get("LAN IPv6") != null) {
            logger.info("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
            if (IPv6Info.get("InterfaceID") != null) {
                LanIpv6 =ipv6autoconfiglanip2.getText();
            } else {
                LanIpv6 = ipv6autoconfiglanip.getText();
            }
            
           
            logger.info(LanIpv6);
            if(LanIpv6.indexOf(IPv6Info.get("LAN IPv6")) == -1) {
                Result = false;  
            }
        }
        if (IPv6Info.get("WAN DNS") != null) {
            String WanDns1 = ipv6primarydns1.getValue();
            logger.info(WanDns1);
            if(WanDns1.indexOf(IPv6Info.get("WAN DNS")) == -1) {
                Result = false;  
            }
        }
        System.out.print(Result);
        logger.info("CheckIPv6RefreshStatusButton end");
        System.out.print(Result);
        return Result;
        

    }
    public boolean IPv6RefreshStatusButtonExistOrNot() {
        boolean Result = false;
        String LanIpv6;
        logger.info("IPv6RefreshStatusButtonExistOrNot start");
        if (ipv6refreshbutton.exists()) {
            Result = true;
        }
        System.out.print(Result);
        logger.info("IPv6RefreshStatusButtonExistOrNot end");
        return Result;
        

    }
}
