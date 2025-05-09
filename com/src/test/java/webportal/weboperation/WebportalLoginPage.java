
/**
 *
 */
package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import java.util.Map;
import java.util.logging.Logger;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.webelements.WebportalLoginPageElement;

/**
 * @author zheli
 */
public class WebportalLoginPage extends WebportalLoginPageElement {
    private String       emailAddress  = null;
    private String       emailAddress1 = null;
    private String       passWord      = WebportalParam.loginPassword;
    private String       passWord1     = WebportalParam.loginPassword1;
    final static Logger  logger        = Logger.getLogger("WebportalLoginPage");
    public static String wpLoginURL    = WebportalParam.serverUrlLogin;

    static boolean bCheckWhatsNew = false;

    /**
    *
    */
    public WebportalLoginPage() {
        // TODO Auto-generated constructor stub
        String url = getCurrentUrl();
        if (WebportalParam.enableDebug) {
            if (url.contains(WebportalParam.serverUrl) && !isInLoginPage())
                return;
        }

        if (!isInLoginPage() && !$(sMyDevicesLink).exists()) {
            open(wpLoginURL);
        }
        MyCommonAPIs.sleepi(5);
        MyCommonAPIs.waitReady();
        logger.info("init...");
    }

    /**
     * @param noPage
     *            check whether we are logged-in already for debug
     */
    public WebportalLoginPage(boolean noPage) {
        // check url and "All Locations" obj
        if (isInLoginPage())
            return;

        if (!$(sMyDevicesLink).exists()) {
            open(wpLoginURL);
            MyCommonAPIs.waitReady();
        }
        logger.info("initex...");
    }

    public boolean inputLogin(String user, String passwd) {
        logger.info("login with:" + user);
        MyCommonAPIs.waitReady();
        try {
            String ps = WebportalParam.curWebDriver.getPageSource();
            if (ps.length() < 250)
                return false;
            if (loginEmailNew.exists()) {
                loginEmailNew.clear();
                loginEmailNew.sendKeys(user);
            } else if (loginEmailCognito.exists()) {
                loginEmailCognito.clear();
                loginEmailCognito.sendKeys(user);
            }else {
                loginEmailNew1.clear();
                loginEmailNew1.sendKeys(user);
            }
            
            sleepi(1);
            if (loginPwdNew.exists()) {
                loginPwdNew.clear();
                loginPwdNew.sendKeys(passwd);
            } else if (loginPwdCognito.exists()) {
                loginPwdCognito.clear();
                loginPwdCognito.sendKeys(passwd);
            } else {
                loginPwdNew1.clear();
                loginPwdNew1.sendKeys(passwd);
            }

            sleepi(5);
            if (loginButtonNew.exists()) {
                loginButtonNew.click();
            }else {
                loginButtonCognito.click();
           }
            sleepi(10);
            if (NoThankYou.isDisplayed()) {
                NoThankYou.click();
            }
            MyCommonAPIs.sleepi(10);
            if (loginButtonNew.exists())
                return false;
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
    }

    public void sendLoginInfomation(Map<String, String> infoMap) {
        inputLogin(infoMap.get("emailAdress"), infoMap.get("password"));
    }

    public void defaultLogin() {
        MyCommonAPIs.sleepi(10);
        long t = System.currentTimeMillis();
        long end = t + 20;
        System.out.println(end);
        System.out.println("going inside while");
        while (System.currentTimeMillis() < end) {
            System.out.println("inside while");
            Selenide.refresh();
            if (loginNowButton.exists()) {
                loginNowButton.click();
                break;
            }
            MyCommonAPIs.sleepi(10);
        }
        if (emailAddress == null) {
            if (WebportalParam.defaultUser.equals("1")) {
                emailAddress = WebportalParam.adminName;
            } else if (WebportalParam.defaultUser.equals("2")) {
                emailAddress = WebportalParam.managerName;
            } else if (WebportalParam.defaultUser.equals("3")) {
                emailAddress = WebportalParam.ownerName;
            } else if (WebportalParam.defaultUser.equals("4")) {
                emailAddress = WebportalParam.readManagerName;
            } else {
                emailAddress = WebportalParam.loginName;
            }
        }
        loginByUserPassword(emailAddress, passWord);
    }

    public void loginByUserPassword(String emailAddress, String passWord) {
        boolean isfailed = true;
        long t = System.currentTimeMillis();
        long end = t + 12000;
        //System.out.println("going inside while");
        //while (System.currentTimeMillis() < end) {
            //System.out.println("inside while");
            Selenide.refresh();
            MyCommonAPIs.sleepi(5);
//            waitElement(loginNowButton);
            MyCommonAPIs.sleepi(1);
            if (loginNowButton.exists()) {
                loginNowButton.click();
                //break;
            }
            MyCommonAPIs.sleepi(5);
        //}
        if (isInLoginPage()) {
            System.out.printf("try to login with user: %s\n", emailAddress);
            for (int i = 0; i < 5; i++) {
                logger.info("checking to input credit...");
                if (inputLogin(emailAddress, passWord)) {
                    isfailed = false;
                    break;
                }
                takess();
                open(wpLoginURL);
                //System.out.println("going inside while");
                //while (System.currentTimeMillis() < end) {
                    //System.out.println("inside while");
                    Selenide.refresh();
                    MyCommonAPIs.sleepi(5);
                    waitElement(loginNowButton);
                    MyCommonAPIs.sleepi(1);
                    if (loginNowButton.exists()) {
                        loginNowButton.click();
                        //break;
                    }
                    MyCommonAPIs.sleepi(5);
                //}
                MyCommonAPIs.sleepi(10);
                if ($(sMyDevicesLink).exists() || $(sCurLocationElement).exists()) {
                    isfailed = false;
                    break;
                }
            }
            if (isfailed)
                throw new RuntimeException("Cannot open login page");
        }

        isfailed = true;
        for (int i = 0; i < 2; i++) {
            logger.info("checking to load page...");
            timerStart(3 * 60);
            while (timerRunning()) {
                if (getCurrentUrl().contains("CamSdk") || isInLoginPage()) {
                    MyCommonAPIs.sleepi(5);
                } else {
                    if (loginUserType.exists()) {
                        isfailed = false;
                        break;
                    } else {
                        MyCommonAPIs.sleepi(5);
                    }
                }
            }

            if (isfailed) {
                takess();
            }
            if (isInLoginPage()) {
                isfailed = true;
                open(wpLoginURL);
                inputLogin(emailAddress, passWord);
                continue;
            }
            if (!isfailed) {
                break;
            } else {
                Selenide.refresh();
            }
        }
        if (isfailed)
            throw new RuntimeException("Cannot login to webportal");
        else {
            System.out.printf("user is logined: %s/%s\n", emailAddress, loginUserType.getText());
        }
        MyCommonAPIs.waitReady();

        if (!bCheckWhatsNew) {
            bCheckWhatsNew = true;
            if (!WebportalParam.enableDebug) {
                MyCommonAPIs.sleepi(10);
                if (CheckWhatsNew.exists()) {
                    CheckWhatsNew.click();
                }
            }
        }

        if (WebportalParam.ThemeStyle.equals("dark")) {
            settingsButton.click();
            if (!darkthemeInput.isSelected()) {
                darkthemeSpan.click();
                MyCommonAPIs.sleepi(1);
            }
            sidebarCloseIcon.click();
            MyCommonAPIs.sleepi(1);
        }
        
    }

    public boolean checkLoginErrorMsg(String account, String password) {
        boolean result = false;
        int i = 0;
        loginEmailNew1.sendKeys(account);
        loginPwdNew.sendKeys(password);
        sleepi(1);

        if (loginButtonNew.exists()) {
            loginButtonNew.click();
        }
        while (i < 60) {
            i += 1;
            if (loginProgressBar.getAttribute("aria-hidden").equals("false")) {
                MyCommonAPIs.sleepi(1);
                continue;
            }
            if (loginErrorMsg.getAttribute("aria-hidden").equals("false")) {
                result = true;
                logger.info("Error message: " + getText(loginErrorMsg));
                break;
            }
        }
        return result;
    }

    public void defaultLogin1() {
        if (emailAddress1 == null) {
            if (WebportalParam.defaultUser.equals("1")) {
                emailAddress1 = WebportalParam.adminName;
            } else if (WebportalParam.defaultUser.equals("2")) {
                emailAddress1 = WebportalParam.managerName;
            } else if (WebportalParam.defaultUser.equals("3")) {
                emailAddress1 = WebportalParam.ownerName;
            } else if (WebportalParam.defaultUser.equals("4")) {
                emailAddress1 = WebportalParam.readManagerName;
            } else {
                emailAddress1 = WebportalParam.loginName1;
            }
        }
        loginByUserPassword(emailAddress1, passWord1);
    }

    // Added by Vivek
    public void clickOnGoToMyInsightAccount() {
        MyCommonAPIs.sleepi(8);
        if (goToInsightAcc.exists()) {
            logger.info("Clicking on Go to my Insight Account Link");
            goToInsightAcc.click();
            MyCommonAPIs.sleepi(3);
            YesButton.click();
        }
    }

    public boolean loginToAdminaccount(String emailAddress1, String passWord1) {
        boolean result = false;
        long t = System.currentTimeMillis();
        long end = t + 12000;
        System.out.println("going inside while");
        while (System.currentTimeMillis() < end) {
            System.out.println("inside while");
            Selenide.refresh();
            MyCommonAPIs.sleepi(5);
            if (loginNowButton.exists()) {
                loginNowButton.click();
                break;
            }
            MyCommonAPIs.sleepi(10);
        }
        if (loginEmailNew.exists()) {
            loginEmailNew.clear();
            loginEmailNew.sendKeys(emailAddress1);
        } else {
            loginEmailNew1.clear();
            loginEmailNew1.sendKeys(emailAddress1);
        }
        MyCommonAPIs.sleepi(1);
        if (loginPwdNew.exists()) {
            loginPwdNew.clear();
            loginPwdNew.sendKeys(passWord1);
        } else {
            loginPwdNew1.clear();
            loginPwdNew1.sendKeys(passWord1);
        }

        MyCommonAPIs.sleepi(5);
        if (loginButtonNew.exists()) {
            loginButtonNew.click();
            result = true;
        }
        return result;
    }
    
    public void twoFaEmailLogin(String emailAddress, String passWord) {
        boolean isfailed = true;
        long t = System.currentTimeMillis();
        long end = t + 12000;
        System.out.println("going inside while");
        while (System.currentTimeMillis() < end) {
            System.out.println("inside while");
            Selenide.refresh();
            if (loginNowButton.exists()) {
                loginNowButton.click();
                break;
            }
            MyCommonAPIs.sleepi(2);
        }
        if (isInLoginPage()) {
            System.out.printf("try to login with user: %s\n", emailAddress);
            for (int i = 0; i < 5; i++) {
                logger.info("checking to input credit...");
                if (inputLogin(emailAddress, passWord)) {
                    isfailed = false;
                    break;
                }
            }
        }
        String OTP = email2FAOTP(emailAddress);
        logger.info("Confirmation code is:" + OTP);
        MyCommonAPIs.sleepi(10);
        Selenide.switchTo().window(0);
        String currentUrl1=new MyCommonAPIs().getCurrentUrl();
        System.out.print(currentUrl1);
        MyCommonAPIs.sleepi(10);
        int i =0;
        for (int id = 0; id < 6; id++) {
            $x("(//*[@autocomplete=\"one-time-code\"])["+String.valueOf(i)+"]").sendKeys(OTP.substring(id, id + 1));
            i++;  
        }
        continuebutton.click();
        MyCommonAPIs.sleepi(10);
        dontTrust.click();
        int ls = 0;
        while (ls < 5) {
            MyCommonAPIs.sleepi(20);
            if (!notificationicon.exists()) {
                refresh();
            } else {
                break;
            }
            ls += 1;
        }
    }
        
    
        
    public String email2FAOTP(String emailaddress) {
        
        WebDriver driver = WebDriverRunner.getWebDriver();
        String url = "https://yopmail.com";
        ((JavascriptExecutor) driver).executeScript("window.open('" + url + "', '_blank');");
        Selenide.switchTo().window(1);
        MyCommonAPIs.sleepi(5);
        String inputElement = "//input[@id='login']";
        $x(inputElement).clear();
        $x(inputElement).sendKeys(emailaddress);
        $x("//button[@title='Check Inbox @yopmail.com']").click();
        SelenideElement frame = $x("//*[@id=\"ifmail\"]");
        Selenide.switchTo().frame(frame);
        MyCommonAPIs.sleepsync();
        String OTP = OTPInYopmail.getText();
        System.out.println(OTP);
        return OTP;
            
        }
    
    //AddedByPratik
    public boolean verifyLoginPage() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        if (loginButtonNew.exists() || loginButtonCognito.exists()) {
            logger.info("Invite manager link is working fine landed on login page");
            result = true;
       }
        return result;
    }
    
    //AddedBypratik
    public void logintoProAccAfterCreatingacc(String emailAddress, String passWord) {
        boolean isfailed = true;
        MyCommonAPIs.sleepi(30);
        if (loginNowButton.exists()) {
            loginNowButton.click();
            MyCommonAPIs.sleepi(15);
            if (loginEmailNew.exists()) {
                loginEmailNew.clear();
                loginEmailNew.sendKeys(emailAddress);
            } else if (loginEmailCognito.exists()) {
                loginEmailCognito.clear();
                loginEmailCognito.sendKeys(emailAddress);
            }else {
                loginEmailNew1.clear();
                loginEmailNew1.sendKeys(emailAddress);
            }
            
            MyCommonAPIs.sleepi(1);
            if (loginPwdNew.exists()) {
                loginPwdNew.clear();
                loginPwdNew.sendKeys(passWord);
            } else if (loginPwdCognito.exists()) {
                loginPwdCognito.clear();
                loginPwdCognito.sendKeys(passWord);
            } else {
                loginPwdNew1.clear();
                loginPwdNew1.sendKeys(passWord);
            }

            MyCommonAPIs.sleepi(5);
            if (loginButtonNew.exists()) {
                loginButtonNew.click();
            }else {
                loginButtonCognito.click();
           }
            MyCommonAPIs.sleepi(10);
            if (NoThankYou.isDisplayed()) {
                NoThankYou.click();
            }
        } else {
            MyCommonAPIs.sleepi(10);
            if (loginEmailNew.exists()) {
                loginEmailNew.clear();
                loginEmailNew.sendKeys(emailAddress);
            } else if (loginEmailCognito.exists()) {
                loginEmailCognito.clear();
                loginEmailCognito.sendKeys(emailAddress);
            }else {
                loginEmailNew1.clear();
                loginEmailNew1.sendKeys(emailAddress);
            }
            
            MyCommonAPIs.sleepi(1);
            if (loginPwdNew.exists()) {
                loginPwdNew.clear();
                loginPwdNew.sendKeys(passWord);
            } else if (loginPwdCognito.exists()) {
                loginPwdCognito.clear();
                loginPwdCognito.sendKeys(passWord);
            } else {
                loginPwdNew1.clear();
                loginPwdNew1.sendKeys(passWord);
            }

            MyCommonAPIs.sleepi(5);
            if (loginButtonNew.exists()) {
                loginButtonNew.click();
            }else {
                loginButtonCognito.click();
           }
            MyCommonAPIs.sleepi(10);
            if (NoThankYou.isDisplayed()) {
                NoThankYou.click();
            }

        }
        
    }
    
  public String shareDiagnosticsEmail(String emailaddress) {
        
        WebDriver driver = WebDriverRunner.getWebDriver();
        String url = "https://yopmail.com";
        ((JavascriptExecutor) driver).executeScript("window.open('" + url + "', '_blank');");
        Selenide.switchTo().window(1);
        MyCommonAPIs.sleepi(5);
        String inputElement = "//input[@id='login']";
        $x(inputElement).clear();
        $x(inputElement).sendKeys(emailaddress);
        $x("//button[@title='Check Inbox @yopmail.com']").click();
        SelenideElement frame = $x("//*[@id=\"ifmail\"]");
        Selenide.switchTo().frame(frame);
        String mailBody = shareDiagnosticsMail.getText();
        System.out.println(MyCommonAPIs.getCurrentUrl());
        MyCommonAPIs.sleepi(20);      
        System.out.println(mailBody);
        return mailBody;
            
        }
       
    }
   
