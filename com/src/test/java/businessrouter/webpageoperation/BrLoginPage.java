package businessrouter.webpageoperation;

import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;

import businessrouter.webelements.BrAllMenueElements;
import businessrouter.webelements.BrLoginPageElements;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;

/**
 * @author Sujuan.Li
 *
 */

public class BrLoginPage extends BrLoginPageElements {
    private String username = WebportalParam.loginName;
    private String passWord = WebportalParam.loginPassword;
    // final static Logger logger = Logger.getLogger("WebportalLoginPage");
    private String      LoginURL = WebportalParam.serverUrl;
    final static Logger logger   = Logger.getLogger("BrLoginPage");

    public void BrLoginPage() {

    }

    // Edit by Dallas Zhao at 9/12/2018 start
    public void checkChangePasswordDialog() {
        if (modifypasswordokbutton2.exists()) {
            modifypasswordokbutton2.click();
            if (modifypasswordokbutton1.exists()) {
                modifypasswordokbutton1.click();
            }
        } else {
            if (modifypasswordokbutton.exists()) {
                modifypasswordokbutton.click();
            }
        }
    }// Edit by Dallas Zhao at 9/12/2018 end

    public void defaultLogin() {
        // open(LoginURL);
        logger.info(WebportalParam.DUTType); 
        if (WebportalParam.DUTType.contentEquals("BR500")) {
            Selenide.open("https://192.168.1.1/unauth.cgi");
            Selenide.open(LoginURL, "", "admin", "Test@123");
        } else if(WebportalParam.DUTType.contentEquals("BR100")) {
            logger.info("99999999999999999999999999"); 
            Selenide.open(LoginURL);
            logger.info("99999999999999999999999999");
            loginUsernamebr100.setValue(username);
            loginPasswordbr100.setValue(passWord);
            loginButton100.click();
            
            
        }

       // MyCommonAPIs.sleepi(15);
        //if (passWord.equals("password")) {
         //   checkChangePasswordDialog();
        //}
    }
    

    public void BrLogout() {
        WebDriver webDriver = null;
        // Close the DUT GUI(LoginURL);
        // Edit by Dallas Zhao at 9/12/2018 start
        Selenide.refresh();
        BrAllMenueElements BrAllMenueElements = new BrAllMenueElements();
        BrAllMenueElements.basic.click();
        MyCommonAPIs.sleepi(20);
        checkChangePasswordDialog();
        // Edit by Dallas Zhao at 9/12/2018 end
        if (logoutButton.exists()) {
            logoutButton.click();
        } else {
            logger.info("DUT is logout");
            System.out.print("DUT is logout");
        }
        Selenide.close();
    }

    // Edit by Dallas Zhao at 9/12/2018 start
    public boolean CheckLoginSuccess() {
        boolean result;
        if (WebportalParam.DUTType.contentEquals("BR500")) {
            Selenide.open("https://192.168.1.1/unauth.cgi");
            Selenide.open(LoginURL, "", "admin", "Test@123");
        } else if(WebportalParam.DUTType.contentEquals("BR100")) {         
            Selenide.open(LoginURL);
            loginUsernamebr100.setValue(username);
            loginPasswordbr100.setValue("password");
            loginButton100.click();
            
            
        }
        //Selenide.open("https://192.168.1.1/unauth.cgi");
        //Selenide.open(LoginURL, "", "admin", "password");
        MyCommonAPIs.sleepi(15);

        //if (passWord.equals("password")) {
            checkChangePasswordDialog();
        //}
        logger.info("Check login status.");
        if (logoutButton.exists()) {
            logger.info("Check login success.");
            result = true;
        } else {
            logger.info("Check login fail.");
            result = false;
        }
        return result;
    }

    public boolean CheckNewPasswordLoginSuccess(String NewPassword) {
        boolean result;
        if (WebportalParam.DUTType.contentEquals("BR500")) {
            Selenide.open("https://192.168.1.1/unauth.cgi");
            Selenide.open(LoginURL, "", "admin", NewPassword);
        }else if(WebportalParam.DUTType.contentEquals("BR100")) {
            Selenide.open(LoginURL);
            loginUsernamebr100.setValue(username);
            loginPasswordbr100.setValue(NewPassword);
            loginButton100.click();                       
        }
        MyCommonAPIs.sleepi(15);
        checkChangePasswordDialog();
        logger.info("Check login status.");
        if (logoutButton.exists()) {
            logger.info("Check login success.");
            result = true;
        } else {
            logger.info("Check login fail.");
            result = false;
        }
        return result;
    }

    public void LoginRefresh() {
        Selenide.open(LoginURL);

        MyCommonAPIs.sleepi(15);
        checkChangePasswordDialog();
    }

    public boolean CheckGuiUrlIsHttps() {
        boolean result;
        String url = WebDriverRunner.url();
        if (url.substring(0, 5).equals("https")) {
            result = true;
        } else {
            result = false;
        }
        return result;
    } // Edit by Dallas Zhao at 9/12/2018 end
    public void LoginWithNewLanIP(String NewIP) {
        // open(LoginURL);
       // if (WebportalParam.DUTType.contentEquals("BR500")) {
        if (WebportalParam.DUTType.contentEquals("BR500")) {
            String NewURL = "https://" +NewIP +"/unauth.cgi";
            String NewURL2 = "https://" +NewIP ;
            Selenide.open(NewURL);
            Selenide.open(NewURL2, "", "admin", "Test@123");
        }else if(WebportalParam.DUTType.contentEquals("BR100")) {
            String NewURL2 = "https://" +NewIP ;
            Selenide.open(NewURL2);
            loginUsernamebr100.setValue(username);
            loginPasswordbr100.setValue(passWord);
            loginButton100.click();                       
        }
            
        

       // MyCommonAPIs.sleepi(15);
        //if (passWord.equals("password")) {
         //   checkChangePasswordDialog();
        //}
    }
    public boolean LoginWithNewLanIPv6(String NewIP) {
        logger.info("LoginWithNewLanIPv6 start.");
        boolean Result = false;
        String NewURL = "https://[" +NewIP +"]/unauth.cgi";
        String NewURL2 = "https://[" +NewIP +"]";
        Selenide.open(NewURL);
        Selenide.open(NewURL2, "", "admin", "Test@123");
        logger.info("Check login status.");
        if (logoutButton.exists()) {
            logger.info("Check login success.");
            Result = true;
        } else {
            logger.info("Check login fail.");
            Result = false;
        }
        return Result;
    }
}
