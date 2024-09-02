package businessrouter.webpageoperation;
import static com.codeborne.selenide.Selenide.$x;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;

import businessrouter.webelements.BrAllMenueElements;
import businessrouter.webelements.BrBasicLanSetupElements;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
public class BrBasicLanSetupPage extends BrBasicLanSetupElements {
    final static Logger logger = Logger.getLogger("BrBasicLanSetupPage");
    public void OpenBasicLanSetupPage() {
        //open(LoginURL);
        BrAllMenueElements BrAllMenueElements = new BrAllMenueElements();
        logger.info("Open Basic LAN Setup  Page");
        //Selenide.open(BrURLParam.LANSetup);
        BrAllMenueElements.basic.click();
        if (WebportalParam.DUTType.contentEquals("BR500")) {
            BrAllMenueElements.Setup.click();
        }else if(WebportalParam.DUTType.contentEquals("BR100")) {
            System.out.println("df788888888888888888888888888");
            BrAllMenueElements.SetupBr100.click();                       
        }
        
        BrAllMenueElements.LANSetup.click();
    }
    public  void AddIPReservation (Map<String, String> ipreservation) {
        BrAllMenueElements BrAllMenueElements = new BrAllMenueElements();
        //logger.info("Open Basic LAN Setup  Page");
        //Selenide.open(BrURLParam.LANSetup);
        //BrAllMenueElements.basic.click();
        //BrAllMenueElements.Setup.click();
        //BrAllMenueElements.LANSetup.click();
        //MyCommonAPIs.sleep(10);
        lanipreservationaddbutton.click();
        logger.info("Add IP Reservation address start");
        logger.info(ipreservation.get("IP Address"));
        if (ipreservation.get("IP Address") != null) {
            String  []ipreservationlist = ipreservation.get("IP Address").split("\\.");  
            logger.info(ipreservationlist[0]); 
            Selenide.sleep(2000);
            lanreservationip1.setValue(ipreservationlist[0]);
            lanreservationip2.setValue(ipreservationlist[1]);
            lanreservationip3.setValue(ipreservationlist[2]);
            lanreservationip4.setValue(ipreservationlist[3]); 
        } else {
            logger.info("Reservation IP Address is null");
            
        }
        logger.info(ipreservation.get("MAC Address"));
        if (ipreservation.get("MAC Address") != null) {
            logger.info(ipreservation.get("MAC Address"));
            lanreservationmac.setValue(ipreservation.get("MAC Address"));
        } else {
            logger.info("MAC address is null");
            
        }
        if (ipreservation.get("Device Name") != null) {
            lanreservationdevicename.setValue(ipreservation.get("Device Name"));
        } else {
            logger.info("Device Name is null");
            
        }
        lanreservationapplybutton.click();
        logger.info("Add IP Reservation address end");
    }
    public boolean IPReservationExistYesOrNot(Map<String, String> ipreservation) {
        String ipreservationtable = "//tr[@class ='ant-table-row  ant-table-row-level-0']/td[@class ='ant-table-column-has-filters'][3]";
        boolean Result = false;
        logger.info(String.format("Get IP Reservation..."));
        MyCommonAPIs.waitReady();
        List<String> lsIPReservation= MyCommonAPIs.getTexts(ipreservationtable);
        int i = 1;
        for (String name : lsIPReservation) {
         
            i = i + 1;
            System.out.print("name1:"  + name);
            System.out.print("name2:"  + ipreservation.get("MAC Address"));
            //String []IPReservationElemList = name.split(" ");
            if( name.toUpperCase().contentEquals(ipreservation.get("MAC Address")) ) {
                Result = true;   
            }
           
        }
        return Result;
    }
    
    public void DeleteIPReservation(String  ListName) {
        String ipreservationtable = "//tr[@class ='ant-table-row  ant-table-row-level-0']/td[@class ='ant-table-column-has-filters'][2]";
        String Checkboxnumber = "(//input[@type =\"radio\"])";
        logger.info(String.format("Get IP Reservation..."));
        MyCommonAPIs.waitReady();
        List<String> lsIPReservation= MyCommonAPIs.getTexts(ipreservationtable);
        int i = 0;
        for (String name : lsIPReservation) {
         
            i = i + 1;
            System.out.print("name:"  + name);
            //String []IPReservationElemList = name.split(" ");
            if( name.contentEquals(ListName) ) {
                Checkboxnumber = Checkboxnumber +"[" + String.valueOf(i)+"]"; 
                System.out.print(Checkboxnumber);
                lanipreservationselectone = $x(Checkboxnumber);
                MyCommonAPIs.sleep(4000); 
                logger.info(Checkboxnumber);
                logger.info(String.format(": %s", name));
                lanipreservationselectone.selectRadio("1"); 
            }
           
        }
        lanipreservationdeletebutton.click();
        landeletecheckyesbutton.click();
       
    }
    
    public  void AddNewLan (Map<String, String> LanInfo) {
    
        lanaddsubnetbutton.click();
        String VlanIdLocation= addnewlanvlanidnum.getSearchCriteria();
        int t = VlanIdLocation.indexOf("'");
        StringBuilder sb = new StringBuilder(VlanIdLocation);
        sb.insert(t+1, LanInfo.get("VLAN ID"));
        VlanIdLocation = sb.toString();
        VlanIdLocation = VlanIdLocation.substring(9);
        logger.info("VlanIdLocation:" +VlanIdLocation);
        addnewlanvlanidnum = $x(VlanIdLocation);
        logger.info("Add New LAN");
        if (LanInfo.get("VLAN ID") != null) {
            ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", addnewlanapplybutton);
            addnewlanvlanid.click();
            addnewlanvlanidnum.click();
            Selenide.sleep(4000);
        }
        String Lan2Client = WebportalParam.brcleintport2;
        String  []Lan2ClientList = Lan2Client.split("\\.");
        if (addnewlanip1.getValue().contentEquals(Lan2ClientList[0]) && addnewlanip2.getValue().contentEquals(Lan2ClientList[1])&&addnewlanip3.getValue().contentEquals(Lan2ClientList[2]) ) {
            logger.info("lan client 2 and New LAN are in the same subnet."); 
        } 
        addnewlanapplybutton.click();
    }
    
    public void DeleteNewLAN(String NewLanName) {
        //open(LoginURL);
        BrAllMenueElements BrAllMenueElements = new BrAllMenueElements();
        logger.info("Delete New LAN");
        if (NewLanName.contentEquals("LAN2")) {
            newlan2name.click();
        } else if (NewLanName.contentEquals("LAN3")) {
            newlan3name.click();
        } else if (NewLanName.contentEquals("LAN4")) {
            newlan4name.click(); 
        }
        if (WebportalParam.DUTType.contentEquals("BR500")) {
            ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", landeletebutton);
            landeletebutton.click();
        } else if(WebportalParam.DUTType.contentEquals("BR100")) {
            ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", landeletebuttonbr100);
            landeletebuttonbr100.click();
            
            
        }
          
        
        landeletecheckyesbutton.click();
    }
    
    public  boolean GetWaringForAddingIPReservation (Map<String, String> ipreservation ) {
        boolean Result = false;
        BrAllMenueElements BrAllMenueElements = new BrAllMenueElements();
  
        lanipreservationaddbutton.click();
        logger.info("GetWaringForAddingIPReservation start");
        logger.info(ipreservation.get("IP Address"));
        if (ipreservation.get("IP Address") != null) {
            String  []ipreservationlist = ipreservation.get("IP Address").split("\\.");  
            logger.info(ipreservationlist[0]); 
            Selenide.sleep(2000);
            lanreservationip1.setValue(ipreservationlist[0]);
            lanreservationip2.setValue(ipreservationlist[1]);
            lanreservationip3.setValue(ipreservationlist[2]);
            lanreservationip4.setValue(ipreservationlist[3]); 
            if( warninginvalidipreservation.exists()) {
                String Warning =  warninginvalidipreservation.getText(); 
                if (Warning.contentEquals(ipreservation.get("Warning"))) {
                    Result = true;
                }
            } 
        } else {
            logger.info("Reservation IP Address is null");
            
        }
        return Result;
    }
       
    public  boolean ChangLANDHCPRange (Map<String, String> iprange) {
        boolean Result = false;
        boolean Result1 = true;
        boolean Result2 = true;
        logger.info("ChangLANDHCPRange start");
        logger.info(iprange.get("Start IP"));
        if (iprange.get("Start IP") != null) {      
            Selenide.sleep(2000);         
            landhcpstartip4.setValue(iprange.get("Start IP")); 
            
        } else {
            logger.info("Start IP is null");
            
        }
        if (iprange.get("End IP") != null) {     
            Selenide.sleep(4000);        
            landhcpendip4.setValue(iprange.get("End IP")); 
        } else {
            logger.info("End IP is null");
            
        }
        lanapplybutton.click();
        Selenide.sleep(30000);
        if (iprange.get("Start IP") != null) {  
           String NewStartIP = landhcpstartip4.getValue();
           logger.info("NewStartIP:" + NewStartIP);
           logger.info("NewStartIP2:" + iprange.get("Start IP"));
           if(NewStartIP.contentEquals(iprange.get("Start IP"))) {
               Result1 = true;
           }else {
               Result1 = false;
           }
        }
        if (iprange.get("End IP") != null) {  
            Selenide.sleep(2000);
            String NewEndIP = landhcpendip4.getValue();
            logger.info("NewEndIP:" + NewEndIP);
            logger.info("NewEndIP2:" + iprange.get("End IP"));
            if(NewEndIP.contentEquals(iprange.get("End IP"))) {
                Result2 = true;
            }else {
                Result2 = false;
            }
         }
        System.out.print(Result1);
        System.out.print(Result2);
        Result = Result1 && Result2;
        System.out.print(Result);
        logger.info("ChangLANDHCPRange end");
        return Result;
    } 
    
    public  boolean EnableOrDisableDHCPServer (String EnableOrDisable) {
        boolean Result = false;
        boolean Result1 = true;
        boolean Result2 = true;
        logger.info("EnableOrDisableDHCPServer start");
       
        if (EnableOrDisable.contentEquals("Enable")) {      
            if (!dhcpserverenable.isSelected()){
                dhcpserverenable.selectRadio("on");
            }
         
        } else if (EnableOrDisable.contentEquals("Disable")) {
            if (dhcpserverenable.isSelected()){
                dhcpserverenable.sendKeys(Keys.SPACE);
            }
            
        }
        lanapplybutton.click();
        Selenide.sleep(30000);
        if (EnableOrDisable.contentEquals("Enable")) {      
            if (dhcpserverenable.isSelected()){
                Result = true;
            }
         
        } else if (EnableOrDisable.contentEquals("Disable")) {
            if (!dhcpserverenable.isSelected()){
                Result = true;
            }
            
        }
        return Result;
    } 
    public  void ChangeConnectedIPToIPReservation (Map<String, String> ipreservation) {
        lanipreservationaddbutton.click();
        logger.info("ChangeConnectedIPToIPReservation start");
        logger.info(ipreservation.get("MAC Address"));
        if (ipreservation.get("MAC Address") != null) {
            String connectpcmacobject = "//tr[@class = 'ant-table-row  ant-table-row-level-0']/td[@class='ant-table-column-has-filters'][3]";
            String Checkboxnumber = "(//input[@type =\"radio\"])";
            logger.info(String.format("Get Connected PC MAC list..."));
            MyCommonAPIs.waitReady();
            List<String> lsConnectedPcMac= MyCommonAPIs.getTexts(connectpcmacobject);
            int i = 0;
            
            
            for (String name : lsConnectedPcMac) {
             
                i = i + 1;
                logger.info("name:"  + name);
                logger.info("name2:"  + ipreservation.get("MAC Address"));
                //String []IPReservationElemList = name.split(" ");
                if( name.toUpperCase().contentEquals(ipreservation.get("MAC Address"))) {
                    Checkboxnumber = Checkboxnumber +"[" + String.valueOf(i)+"]"; 
                    System.out.print(Checkboxnumber);
                    lanipreservationselectone = $x(Checkboxnumber);
                    MyCommonAPIs.sleep(4000); 
                    logger.info(Checkboxnumber);
                    System.out.println("9999922333333333333333333333333");
                    //logger.info(String.format(": %s", name));
                    if (WebportalParam.DUTType.contentEquals("BR500")) {
                        if (ipreservation.get("Exist IP Reservation Number")!= null) {
                            int b = Integer.valueOf(ipreservation.get("Exist IP Reservation Number")).intValue();
                            i = i - b;
                            System.out.print(i);
                            
                        }
                    }else if(WebportalParam.DUTType.contentEquals("BR100")) {
                        if (ipreservation.get("Exist IP Reservation Number")!= null) {
                            int b = Integer.valueOf(ipreservation.get("Exist IP Reservation Number")).intValue();
                            i = i - b;
                            System.out.print(i);
                            
                        } else {
                         i = i -1;    
                        }
                         System.out.print(i);
                    }
                    
                    lanipreservationselectone.selectRadio(String.valueOf(i)); 
                    //lanipreservationeditbutton.click();
                    //lanipreservationselectone.click();
                }
               
            }
            if (ipreservation.get("IP Address") != null) {
                String  []ipreservationlist = ipreservation.get("IP Address").split("\\.");  
                logger.info(ipreservationlist[0]); 
                Selenide.sleep(2000);
                lanreservationip1.setValue(ipreservationlist[0]);
                lanreservationip2.setValue(ipreservationlist[1]);
                lanreservationip3.setValue(ipreservationlist[2]);
                lanreservationip4.setValue(ipreservationlist[3]); 
            } else {
                logger.info("Changed Reservation IP Address is null");
                
            }
            
            lanreservationapplybutton.click();
            logger.info("ChangeConnectedIPToIPReservation end");
        }
        
    }
    public  void ChangLanIpAndMask (Map<String, String> lanipinfo) {
       
        logger.info("ChangLanIpAndMask start");
        logger.info(lanipinfo.get("LAN IP"));
        if (lanipinfo.get("LAN IP") != null) {      
            Selenide.sleep(2000);         
            String  []laniplist = lanipinfo.get("LAN IP").split("\\.");  
            logger.info(laniplist[0]); 
            Selenide.sleep(2000);
            lanipaddress1.setValue(laniplist[0]);
            lanipaddress2.setValue(laniplist[1]);
            lanipaddress3.setValue(laniplist[2]);
            lanipaddress4.setValue(laniplist[3]);  
            
        } else {
            logger.info("LAN IP is null");
            
        }
        if (lanipinfo.get("Subnet Mask") != null) {     
            Selenide.sleep(4000);   
            String  []lanipmasklist = lanipinfo.get("Subnet Mask").split("\\.");
            
            lanipsubmask1.setValue(lanipmasklist[0]);
            lanipsubmask2.setValue(lanipmasklist[1]);
            lanipsubmask3.setValue(lanipmasklist[2]);
            lanipsubmask4.setValue(lanipmasklist[3]);  
        } else {
            logger.info("Subnet Mask is null");
            
        }
        lanapplybutton.click();
        logger.info("ChangLanIpAndMask end");
        Selenide.sleep(10000);
    }
    public boolean  CheckLanIpAndMask (Map<String, String> lanipinfo) {
        boolean Result = false;
        boolean Result1 = true;
        boolean Result2 = true;
        logger.info("CheckLanIpAndMask start");
        if (lanipinfo.get("LAN IP") != null) {  
           String NewLanIP1 = lanipaddress1.getValue();
           String NewLanIP2 = lanipaddress2.getValue();
           String NewLanIP3 = lanipaddress3.getValue();
           String NewLanIP4 = lanipaddress4.getValue();
           String NewLanIP = NewLanIP1 + "."+NewLanIP2+"."+NewLanIP3+"."+ NewLanIP4;
           logger.info("NewLanIP:" + NewLanIP);
           logger.info("NewLanIP2:" + lanipinfo.get("LAN IP"));
           if(NewLanIP.contentEquals(lanipinfo.get("LAN IP"))) {
               Result1 = true;
           }else {
               Result1 = false;
           }
        }
        if (lanipinfo.get("Subnet Mask") != null) {  
            Selenide.sleep(2000);
            String NewIPMask = lanipsubmask1.getValue() +"."+lanipsubmask2.getValue()+"." +lanipsubmask3.getValue()+"."+lanipsubmask4.getValue();
            logger.info("NewIPMask:" + NewIPMask);
            logger.info("NewIPMask2:" + lanipinfo.get("Subnet Mask"));
            if(NewIPMask.contentEquals(lanipinfo.get("Subnet Mask"))) {
                Result2 = true;
            }else {
                Result2 = false;
            }
         }
        System.out.print(Result1);
        System.out.print(Result2);
        Result = Result1 && Result2;
        System.out.print(Result);
        logger.info("CheckLanIpAndMask end");
        return Result;
    }
    public void ChangeLAN(String NewLanName) {
        //open(LoginURL);
        BrAllMenueElements BrAllMenueElements = new BrAllMenueElements();
        logger.info("Delete New LAN");
        if (NewLanName.contentEquals("LAN2")) {
            newlan2name.click();
        } else if (NewLanName.contentEquals("LAN3")) {
            newlan3name.click();
        } else if (NewLanName.contentEquals("LAN4")) {
            newlan4name.click(); 
        }
       
    }
    public  void AddIPReservationForDefinedLan (Map<String, String> ipreservation) {
        BrAllMenueElements BrAllMenueElements = new BrAllMenueElements();
        //logger.info("Open Basic LAN Setup  Page");
        //Selenide.open(BrURLParam.LANSetup);
        //BrAllMenueElements.basic.click();
        //BrAllMenueElements.Setup.click();
        //BrAllMenueElements.LANSetup.click();
        //MyCommonAPIs.sleep(10);
        if (ipreservation.get("LAN") != null){
            if (ipreservation.get("LAN").contentEquals("LAN2")) {
                lanipreservationaddbutton2.click();
            } else {
                lanipreservationaddbutton.click();
            }
            
        }
        
        logger.info("Add IP Reservation address start");
        logger.info(ipreservation.get("IP Address"));
        if (ipreservation.get("IP Address") != null) {
            String  []ipreservationlist = ipreservation.get("IP Address").split("\\.");  
            logger.info("dddddddddddddddddddddewwwwwr");
           // logger.info(ipreservationlist[0]); 
           // logger.info(ipreservationlist[1]);
           // logger.info(ipreservationlist[2]);
           // logger.info(ipreservationlist[3]);
            Selenide.sleep(2000);
            lan2reservationip1.setValue(ipreservationlist[0]);
            lan2reservationip2.setValue(ipreservationlist[1]);
            lan2reservationip3.setValue(ipreservationlist[2]);
            lan2reservationip4.setValue(ipreservationlist[3]); 
        } else {
            logger.info("Reservation IP Address is null");
            
        }
        logger.info(ipreservation.get("MAC Address"));
        if (ipreservation.get("MAC Address") != null) {
            logger.info(ipreservation.get("MAC Address"));
            lan2reservationmac.setValue(ipreservation.get("MAC Address"));
        } else {
            logger.info("MAC address is null");
            
        }
        if (ipreservation.get("Device Name") != null) {
            lan2reservationdevicename.setValue(ipreservation.get("Device Name"));
        } else {
            logger.info("Device Name is null");
            
        }
        lan2reservationapplybutton.click();
        logger.info("Add IP Reservation address end");
    }
    public  void AddNewLanWithDhcp (Map<String, String> LanInfo) {
        
        lanaddsubnetbutton.click();
        String VlanIdLocation= addnewlanvlanidnum.getSearchCriteria();
        int t = VlanIdLocation.indexOf("'");
        StringBuilder sb = new StringBuilder(VlanIdLocation);
        sb.insert(t+1, LanInfo.get("VLAN ID"));
        VlanIdLocation = sb.toString();
        VlanIdLocation = VlanIdLocation.substring(9);
        logger.info("VlanIdLocation:" +VlanIdLocation);
        addnewlanvlanidnum = $x(VlanIdLocation);
        logger.info("Add New LAN");
        if (LanInfo.get("VLAN ID") != null) {
            ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", addnewlanapplybutton);
            System.out.println("9999999999999999999999999111");
            addnewlanvlanid.click();
            System.out.println("9999999999999999999999999111");
            addnewlanvlanidnum.click();
            Selenide.sleep(4000);
        }
        System.out.println("9999999999999999999999999111");
        String Lan2Client = WebportalParam.brlanclientip2;
        String  []Lan2ClientList = Lan2Client.split("\\.");
        if (addnewlanip1.getValue().contentEquals(Lan2ClientList[0]) && addnewlanip2.getValue().contentEquals(Lan2ClientList[1])&&addnewlanip3.getValue().contentEquals(Lan2ClientList[2]) ) {
            logger.info("lan client 2 and New LAN are in the same subnet."); 
        }
        if (LanInfo.get("DHCP") != null) {      
            Selenide.sleep(2000); 
            if (WebportalParam.DUTType.contentEquals("BR500")) {
                if (LanInfo.get("DHCP").contentEquals("enable")) {
                    dhcpenable.selectRadio("on");
                } else {
                    
                }
            }else if(WebportalParam.DUTType.contentEquals("BR100")) {
                if (LanInfo.get("DHCP").contentEquals("enable")) {
                    dhcpenablebr100.selectRadio("on");
                } else {
                    
                }                
            }
           
            
            
        }
            
        
       // if (LanInfo.get("Start IP") != null) {      
      //      Selenide.sleep(2000);         
       //     landhcpstartip4.setValue(LanInfo.get("Start IP")); 
            
      //  } else {
       //     logger.info("Start IP is null");
            
       // }
       // if (LanInfo.get("End IP") != null) {     
       //     Selenide.sleep(4000);        
       //     landhcpendip4.setValue(LanInfo.get("End IP")); 
       // } else {
       //     logger.info("End IP is null");
            
      //  }
        addnewlanapplybutton.click();
    }
    public void DeleteLan2IPReservation(String  ListName) {
        String ipreservationtable = "//tr[@class ='ant-table-row  ant-table-row-level-0']/td[@class ='ant-table-column-has-filters'][2]";
        String Checkboxnumber = "(//input[@type =\"radio\"])";
        logger.info(String.format("Get IP Reservation..."));
        MyCommonAPIs.waitReady();
        List<String> lsIPReservation= MyCommonAPIs.getTexts(ipreservationtable);
        int i = 0;
        for (String name : lsIPReservation) {
         
            i = i + 1;
            System.out.print("name:"  + name);
            //String []IPReservationElemList = name.split(" ");
            if( name.contentEquals(ListName) ) {
                Checkboxnumber = Checkboxnumber +"[" + String.valueOf(i)+"]"; 
                System.out.print(Checkboxnumber);
                lanipreservationselectone = $x(Checkboxnumber);
                MyCommonAPIs.sleep(4000); 
                logger.info(Checkboxnumber);
                logger.info(String.format(": %s", name));
                lanipreservationselectone.selectRadio("1"); 
            }
           
        }
        lan2ipreservationdeletebutton.click();
        landeletecheckyesbutton.click();
       
    }
    public  void EditIPReservation (Map<String, String> ipreservation) {
        //lanipreservationaddbutton.click();
        logger.info("ChangeConnectedIPToIPReservation start");
        logger.info(ipreservation.get("MAC Address"));
        if (ipreservation.get("MAC Address") != null) {
            String connectpcmacobject = "//tr[@class = 'ant-table-row  ant-table-row-level-0']/td[@class='ant-table-column-has-filters'][3]";
            String Checkboxnumber = "(//input[@type =\"radio\"])";
            logger.info(String.format("Get Connected PC MAC list..."));
            MyCommonAPIs.waitReady();
            List<String> lsConnectedPcMac= MyCommonAPIs.getTexts(connectpcmacobject);
            int i = 0;
            
            
            for (String name : lsConnectedPcMac) {
             
                i = i + 1;
                logger.info("name:"  + name);
                logger.info("name2:"  + ipreservation.get("MAC Address"));
                //String []IPReservationElemList = name.split(" ");
                if( name.toUpperCase().contentEquals(ipreservation.get("MAC Address"))) {
                    Checkboxnumber = Checkboxnumber +"[" + String.valueOf(i)+"]"; 
                    System.out.print(Checkboxnumber);
                    lanipreservationselectone = $x(Checkboxnumber);
                    MyCommonAPIs.sleep(4000); 
                    logger.info(Checkboxnumber);
                    System.out.println("9999922333333333333333333333333");
                    //logger.info(String.format(": %s", name));
                    if (WebportalParam.DUTType.contentEquals("BR500")) {
                        if (ipreservation.get("Exist IP Reservation Number")!= null) {
                            int b = Integer.valueOf(ipreservation.get("Exist IP Reservation Number")).intValue();
                            i = i - b;
                            System.out.print(i);
                            
                        }
                    }else if(WebportalParam.DUTType.contentEquals("BR100")) {
                        if (ipreservation.get("Exist IP Reservation Number")!= null) {
                            int b = Integer.valueOf(ipreservation.get("Exist IP Reservation Number")).intValue();
                            i = i - b;
                            System.out.print(i);
                            
                        } else {
                         //i = i;    
                        }
                         System.out.print(i);
                    }
                    
                    lanipreservationselectone.selectRadio(String.valueOf(i)); 
                    lanipreservationeditbutton.click();
                    //lanipreservationselectone.click();
                }
               
            }
            if (ipreservation.get("IP Address") != null) {
                String  []ipreservationlist = ipreservation.get("IP Address").split("\\.");  
                logger.info(ipreservationlist[0]); 
                Selenide.sleep(2000);
                lanreservationip1.setValue(ipreservationlist[0]);
                lanreservationip2.setValue(ipreservationlist[1]);
                lanreservationip3.setValue(ipreservationlist[2]);
                lanreservationip4.setValue(ipreservationlist[3]); 
            } else {
                logger.info("Changed Reservation IP Address is null");
                
            }
            
            lanreservationapplybutton.click();
            logger.info("ChangeConnectedIPToIPReservation end");
        }
        
    }
}
