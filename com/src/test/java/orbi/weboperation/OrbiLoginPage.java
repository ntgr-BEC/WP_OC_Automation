/**
 *
 */
package orbi.weboperation;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

import java.util.logging.Logger;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import orbi.param.OrbiGlobalConfig;
import orbi.webelements.DNIOrbiCommanElement;
import orbi.webelements.DNIOrbiSetupWizardElement;
import orbi.webelements.OrbiAllMenueElements;
import util.MyCommonAPIs;
import util.Pause;
import webportal.param.WebportalParam;
import orbi.webelements.OrbiLoginPageElements;


/**
 * @author bingke.xue
 *
 */
public class OrbiLoginPage extends OrbiLoginPageElements{
    final static Logger  logger     = Logger.getLogger("OrbiLoginPage");
    public String deviceIpAddress = "";
    public static String httpsPort = "8443";
    public String OrbiloginURLWAN = "https://admin:" + WebportalParam.loginDevicePassword + "@"+OrbiGlobalConfig.orbiLANIp+":"+httpsPort;
    public static String orbiloginURLLAN = "https://admin:" + WebportalParam.loginDevicePassword + "@"+OrbiGlobalConfig.orbiLANIp;
    public String OrbiloginURLAP = "https://admin:" + WebportalParam.loginDevicePassword + "@"+WebportalParam.ob1IPaddress;
    public String OrbiDebugloginURLAP = "https://admin:" + WebportalParam.loginDevicePassword + "@"+WebportalParam.ob1IPaddress+"/debug.htm";
    public String OrbiDebugloginURLRouter = "https://admin:" + WebportalParam.loginDevicePassword + "@"+ OrbiGlobalConfig.orbiLANIp+"/debug.htm";
    
    public static String orbiloginURLLANsxk50 = "https://" + OrbiGlobalConfig.orbiLANIp;
    public String OrbiloginURLAPsxk50 = "https://" + WebportalParam.ob1IPaddress;
    
    private String      username = "admin";
    private String      passWord = WebportalParam.loginDevicePassword;
    private String      LoginURL = "https://" + WebportalParam.ob2IPaddress;
    
    /**
    *
    */
    public OrbiLoginPage() {
        logger.info("Login to Orbi admin UI:");
        String loginURL = OrbiGlobalConfig.orbiLANWANMode.equals("LAN")?orbiloginURLLAN:OrbiloginURLWAN;
        open(loginURL);
        if (DNIOrbiSetupWizardElement.setupContinueBtn.exists()) {
            DNIOrbiSetupWizardElement.setupContinueBtn.click();
        }
        MyCommonAPIs.waitReady();

    }
    
    public OrbiLoginPage(boolean nothing) {
        logger.info("new class");
    }
    
    public OrbiLoginPage(String orbiDeviceMode) {
        logger.info("Login to Orbi admin UI:");
        if (WebportalParam.arch.contains("old")) {
            String loginURL = orbiDeviceMode.equals("Router") ? orbiloginURLLAN : OrbiloginURLAP;
            System.out.println("orbi GUI URL is:" + loginURL);
            open(loginURL);
            if (DNIOrbiSetupWizardElement.setupContinueBtn.exists()) {
                DNIOrbiSetupWizardElement.setupContinueBtn.click();
            }
            MyCommonAPIs.waitReady();
        }else {
            String loginURL = orbiDeviceMode.equals("Router") ? orbiloginURLLANsxk50 : OrbiloginURLAPsxk50;
            open(loginURL);
            Selenide.switchTo().frame(DNIOrbiCommanElement.newarchcontentIframe);
            DNIOrbiCommanElement.newarchloginusername.setValue("admin");
            DNIOrbiCommanElement.newarchloginpassword.setValue(WebportalParam.loginDevicePassword);
            DNIOrbiCommanElement.newarchloginbtn.click();
            MyCommonAPIs.sleepi(10);
        }
    }
    

    public OrbiLoginPage(String user, String passwd) {
        deviceIpAddress = new OrbiAdvancedInternetSetupPage().getWANIpFromRouter();
        logger.info("Login to Orbi admin UI with specified user and passwd");
        String loginURL = OrbiGlobalConfig.orbiLANWANMode.equals("LAN")?orbiloginURLLAN:OrbiloginURLWAN;
        open(loginURL);
        if (DNIOrbiSetupWizardElement.setupContinueBtn.exists()) {
            DNIOrbiSetupWizardElement.setupContinueBtn.click();
        }
        open("https://"+ user +":" + passwd + "@"+deviceIpAddress+":"+httpsPort);
        MyCommonAPIs.waitReady();
    }
    
    public OrbiLoginPage(String user, String passwd, String ip) {
        logger.info("Login to Orbi admin UI with specified user and passwd");
        if (!WebportalParam.arch.equals("new")) {
            open("https://" + user + ":" + passwd + "@" + ip);
        } else {
            String url = "https://" + ip;
            open(url);
            MyCommonAPIs.sleepi(5);
            Selenide.switchTo().frame(DNIOrbiCommanElement.newarchcontentIframe);
            DNIOrbiCommanElement.newarchloginusername.setValue(user);
            DNIOrbiCommanElement.newarchloginpassword.setValue(passwd);
            DNIOrbiCommanElement.newarchloginbtn.click();
        }
        MyCommonAPIs.sleep(10000);
    }
    
    public void OrbiLoginDebugPage(String orbiDeviceMode) {
        logger.info("Login to Orbi debug admin UI:");
//        String loginURL = OrbiGlobalConfig.orbiLANWANMode.equals("LAN") && orbiDeviceMode.equals("Router")?orbiloginURLLAN:OrbiloginURLWAN;
        String loginURL = orbiDeviceMode.equals("Router")?OrbiDebugloginURLRouter:OrbiDebugloginURLAP;
        open(loginURL);
        if (DNIOrbiSetupWizardElement.setupContinueBtn.exists()) {
            DNIOrbiSetupWizardElement.setupContinueBtn.click();
        }
        MyCommonAPIs.waitReady();
    }
    
    public void OrbiLoginDebugPage(String baseip, String password) {
        logger.info("Login to Orbi debug admin UI:");
        if (!WebportalParam.arch.equals("new")) {
            String OrbiDebugloginURL = "https://admin:" + password + "@" + baseip + "/debug.htm";
            open(OrbiDebugloginURL);
        }else {
            String OrbiDebugloginURL = "https://" + baseip + "/debug.htm";
            open(OrbiDebugloginURL);
            MyCommonAPIs.sleepi(5);
            Selenide.switchTo().frame(DNIOrbiCommanElement.newarchcontentIframe);
            DNIOrbiCommanElement.newarchloginusername.setValue("admin");
            DNIOrbiCommanElement.newarchloginpassword.setValue(password);
            DNIOrbiCommanElement.newarchloginbtn.click();
        }
        new Pause().seconds(10);
    }
    
    public void OrbiLoginForceChangePasswordPage(String baseip, String password) {
        logger.info("Login to force change password page");
        System.out.println(baseip);
        System.out.println(password);
        //String OrbiChangePasswordloginURL = "https://admin:" + password + "@"+ baseip +"/force_change_password.html";
        String OrbiChangePasswordloginURL = "https://admin:" + password + "@"+ baseip;
        open(OrbiChangePasswordloginURL);
        MyCommonAPIs.waitReady();
    }
    
    public void OrbiClickHttps() {
        if (logindetailbutton.isDisplayed()) {
            logindetailbutton.click();
        }
        MyCommonAPIs.sleepi(1);
        if (loginproceeslink.exists()) {
            loginproceeslink.click();
        }
        MyCommonAPIs.sleepi(1);
        if (multiloginconfirmyes.exists()) {
            multiloginconfirmyes.click();
        }
        MyCommonAPIs.sleepi(3);
    }
    
    public void generalLogin(String url, String user, String pw) {
        if (!WebportalParam.arch.equals("new")) {
            Selenide.open(url, "", user, pw);
        }else {
            Selenide.open(url);
        }
        
        MyCommonAPIs.sleepi(1);
        OrbiClickHttps();
        MyCommonAPIs.sleepi(1);
        if (logindetailbutton.exists()) {
            logindetailbutton.click();
            MyCommonAPIs.sleepi(1);
        }
        if (loginproceeslink.exists()) {
            loginproceeslink.click();
            MyCommonAPIs.sleepi(1);
        }

        // Modify for new-arch start : needs to enter username password after alert msg pop out
        if (WebportalParam.arch.equals("new")) {
            MyCommonAPIs.sleepi(3);
            if (! (url.contains("hidden_info") )) {
                Selenide.switchTo().frame("contentframe");
            }
            System.out.println("after switch to alert");
            try {
                loginUsernamesxk50.waitUntil(Condition.enabled, 10000, 1000);
                if (loginUsernamesxk50.exists()) {
                    System.out.println("username exists");
                    loginUsernamesxk50.setValue(user);
                    loginPasswordsxk50.setValue(pw);
                    loginButtonsxk50.click();
                }
                MyCommonAPIs.sleepi(3);
            } catch (Throwable e) {
                System.out.println("login username not found");
            }
        }

        if (multiloginconfirmyes.exists()) {
            multiloginconfirmyes.click();
        }
        MyCommonAPIs.sleepi(12);

    }
    
    public void defaultLogin() {
        
        logger.info(WebportalParam.DUTType);
        if (!WebportalParam.arch.equals("new")) {
            Selenide.open(LoginURL, "", username, passWord);
        }else {
            Selenide.open(LoginURL);
        }
        MyCommonAPIs.sleepi(1);
        if (logindetailbutton.exists()) {
            logindetailbutton.click();
            MyCommonAPIs.sleepi(1);
        }
        if (loginproceeslink.exists()) {
            loginproceeslink.click();
            MyCommonAPIs.sleepi(1);
        }

        if (WebportalParam.arch.equals("new")) {
            MyCommonAPIs.sleepi(3);
            Selenide.switchTo().frame("contentframe");
            System.out.println("after switch to alert");
            try {
                loginUsernamesxk50.waitUntil(Condition.enabled, 10000, 1000);
                if (loginUsernamesxk50.exists()) {
                    System.out.println("username exists");
                    loginUsernamesxk50.setValue(username);
                    loginPasswordsxk50.setValue(passWord);
                    loginButtonsxk50.click();
                }
                MyCommonAPIs.sleepi(3);
            } catch (Throwable e) {
                System.out.println("login username not found");
            }
        }

        if (multiloginconfirmyes.exists()) {
            multiloginconfirmyes.click();
        }
        MyCommonAPIs.sleepi(12);
        
    }
    
    public void OrbiLogout() {
        
        Selenide.refresh();
        OrbiAllMenueElements OrbiAllMenueElements = new OrbiAllMenueElements();
        
        MyCommonAPIs.sleepi(12);
        
        if (WebportalParam.arch.equals("new") && !logoutButton.exists()) {
            Selenide.switchTo().frame("contentframe");
        }
        if (logoutButton.exists()) {
            logoutButton.click();
            MyCommonAPIs.sleepi(3);
        } else {
            logger.info("DUT is logout");
            System.out.print("DUT is logout");
        }
        
    }

}
