package businessrouter.webpageoperation;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;

import businessrouter.webelements.BrPptpL2tpServerElemnets;
import util.MyCommonAPIs;
public class BrPptpL2tpServerPage extends BrPptpL2tpServerElemnets {
    final static Logger logger = Logger.getLogger("BrPptpL2tpServerPage");
    public BrPptpL2tpServerPage() {
        // TODO Auto-generated constructor stub
    }
    public String  LoginPptpL2tpServer(String ClientIP) {
        String HistoryHandle = "";
        String ClientUrl = "window.open(\"https://" + ClientIP + "\");";
        logger.info("ClientUrl:" + ClientUrl);
        //String js = "window.open(\"http://192.168.1.8/tmshtml/login.html\");";
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
        username.setValue("admin");
        password.setValue("password");
        loginbutton.click();
        if(continuebutton.exists()) {
            continuebutton.click();
        }
        return HistoryHandle;
    }
    public boolean  AddNewPptpl2tpUser(Map<String, String> NewUserInfo) {
        boolean Result = false;
        userlink.click();
        usersecondlink.click();
        Selenide.sleep(2000);
        useraddbutton.click();
        Selenide.sleep(2000);
        if (NewUserInfo.get("User Name") != null) {
            pptpl2tpusername.setValue(NewUserInfo.get("User Name"));
        } else {
            logger.info("User Name is null");
            
        }
        if (NewUserInfo.get("User Type") != null) {
            pptpl2tpusertype.selectOptionByValue(NewUserInfo.get("User Type"));
        } else {
            logger.info("User Type is null");
            
        }
        if (NewUserInfo.get("Password") != null) {
            pptpl2tppassword.setValue(NewUserInfo.get("Password"));
        } else {
            logger.info("Password is null");
            
        }
        if (NewUserInfo.get("Confirm Password") != null) {
            pptpl2tpconfirmpassword.setValue(NewUserInfo.get("Confirm Password"));
        } else {
            logger.info("Confirm Password is null");
            
        }
        userapplybutton.click();
        Selenide.sleep(5000);
        String newuserlistname = "//*[@id='tblUsers']/tbody/tr/td[2]"; 
        logger.info(String.format("Check the new user add successfully or not"));
        MyCommonAPIs.waitReady();
        List<String> lsUser= MyCommonAPIs.getTexts(newuserlistname);
        for (String name : lsUser) {
            logger.info(name);
            logger.info(NewUserInfo.get("User Name"));
          int existornot =  name.compareTo(NewUserInfo.get("User Name"));
          System.out.print(existornot);
          if(existornot == 0){
              Result = true;  
          }
        }
        return Result;
    }
    public void DeleteallUser() {
        boolean Result = true;
        userlink.click();
        usersecondlink.click();
        userselectallbutton.click();
        Selenide.sleep(5000);
        userdeletebutton.click();    
        
    }
    public boolean  AddNewPptpServer(Map<String, String> NewPptpInfo) {
        boolean Result = false;
        logger.info("AddNewPptpServer Start");
        vpnlink.click();
        pptplink.click();
        Selenide.sleep(2000);
        //useraddbutton.click();
        if(!pptpenable.isSelected()) {
            logger.info("23423322332233223223");
            pptpenable.click();
        }
        Selenide.sleep(2000);
        if (NewPptpInfo.get("Start IP") != null) {
            String  []StartAddrList = NewPptpInfo.get("Start IP").split("\\.");
            pptpstartip1.setValue(StartAddrList[0]);
            pptpstartip2.setValue(StartAddrList[1]);
            pptpstartip3.setValue(StartAddrList[2]);
            pptpstartip4.setValue(StartAddrList[3]);
        } else {
            logger.info("Start IP is null");
            
        }
        if (NewPptpInfo.get("End IP") != null) {
            String  []EndAddrList = NewPptpInfo.get("End IP").split("\\.");
            pptpendip1.setValue(EndAddrList[0]);
            pptpendip2.setValue(EndAddrList[1]);
            pptpendip3.setValue(EndAddrList[2]);
            pptpendip4.setValue(EndAddrList[3]);
        } else {
            logger.info("End IP is null");
            
        }
        if(!pptpauthpap.isSelected()) {
            pptpauthpap.click();  
        }
        if(!pptpauthchap.isSelected()) {
            pptpauthchap.click();  
        }
        if(!pptpauthmschap.isSelected()) {
            pptpauthmschap.click();  
        }
        if(!pptpauthmschap2.isSelected()) {
            pptpauthmschap2.click();  
        }
        pptpapplybutton.click();
        Selenide.sleep(5000);
        if (pptpenable.isSelected()) {
            Result = true;
        }
        logger.info("AddNewPptpServer Start");
        return Result;
    }
    
    public boolean  AddNewL2tpServer(Map<String, String> NewL2tpInfo) {
        boolean Result = false;
        vpnlink.click();
        l2tplink.click();
        Selenide.sleep(2000);
        if(!l2tpenable.isSelected()) {
            l2tpenable.click();
        }
        Selenide.sleep(2000);
        if (NewL2tpInfo.get("Start IP") != null) {
            String  []StartAddrList = NewL2tpInfo.get("Start IP").split("\\.");
            l2tpstartip1.setValue(StartAddrList[0]);
            l2tpstartip2.setValue(StartAddrList[1]);
            l2tpstartip3.setValue(StartAddrList[2]);
            l2tpstartip4.setValue(StartAddrList[3]);
        } else {
            logger.info("Start IP is null");
            
        }
        if (NewL2tpInfo.get("End IP") != null) {
            String  []EndAddrList = NewL2tpInfo.get("End IP").split("\\.");
            l2tpendip1.setValue(EndAddrList[0]);
            l2tpendip2.setValue(EndAddrList[1]);
            l2tpendip3.setValue(EndAddrList[2]);
            l2tpendip4.setValue(EndAddrList[3]);
        } else {
            logger.info("End IP is null");
            
        }
        if(!l2tpauthpap.isSelected()) {
            l2tpauthpap.click();  
        }
        if(!l2tpauthchap.isSelected()) {
            l2tpauthchap.click();  
        }
        if(!l2tpauthmschap.isSelected()) {
            l2tpauthmschap.click();  
        }
        if(!l2tpauthmschap2.isSelected()) {
            l2tpauthmschap2.click();  
        }
       
        l2tpapplybutton.click();
        Selenide.sleep(5000);
        if (l2tpenable.isSelected()) {
            Result = true;
        }
        return Result;
    }

}
