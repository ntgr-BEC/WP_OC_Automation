package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static org.testng.Assert.assertTrue;
import java.time.format.DateTimeFormatter;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import com.codeborne.selenide.SelenideElement;
import javax.print.DocFlavor.URL;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.sis.util.Static;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import com.codeborne.selenide.ElementsCollection;
import com.google.inject.spi.Element;
import com.mysql.cj.fabric.xmlrpc.base.Array;
import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.publicstep.WebCheck;
import webportal.webelements.HamburgerMenuElement;
import webportal.webelements.InsightServicesPageElement;
import java.io.File;
import java.time.Duration;
import java.nio.file.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.Configuration;
import static com.codeborne.selenide.Selenide.*;
import javax.net.ssl.*;
import java.security.cert.X509Certificate;

/**
 * @author Netgear
 */

public class HamburgerMenuPage extends HamburgerMenuElement {
    private static final String actOnDate   = null;
    /**
    *
    */
    Logger                      logger;
    String                      pairMessage = "one-time security pair code is";
    String                      authMessage = "one-time security auth code is";
    int                         num;
    int                         num1;
    String premiumStartDateSubs = "";
    String premiumEndDate       = "";
    String proStartDateSubs = "";
    String proEndDate       = "";
    static String expectedFileName = "licenseDetails.csv";
    static String expectedFileName1 = "licenseDetails (1).csv";
    StringBuilder concatenatedFileNames = new StringBuilder();
    static Path filePath           = Paths.get("C:\\Users\\DELL\\Downloads", expectedFileName);
    String fileNamesString         = "";

    public HamburgerMenuPage() {
        // TODO Auto-generated constructor stub
        String url = MyCommonAPIs.getCurrentUrl();
        MyCommonAPIs.sleepi(10);
        closeLockedDialog();
        if (!notificationicon.exists()) {
            WebCheck.checkHrefIcon(URLParam.hrefDevices);
            MyCommonAPIs.sleepi(10);
            waitElement(notificationicon);
        }
        if (hamburgermenu.exists()) {
            hamburgermenu.click();
            if (hamburgermenu.getAttribute("aria-expanded").equals("false")) {
                hamburgermenu.click();
            }
        } else if (hamburgermenunew.exists()) {
            hamburgermenunew.click();
            if (hamburgermenunew.getAttribute("aria-expanded").equals("false")) {
                hamburgermenunew.click();
            }
        }
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    public HamburgerMenuPage(boolean noPage) {
        // TODO Auto-generated constructor stub
        // String url = MyCommonAPIs.getCurrentUrl();
        // if (url.contains("/#/locked")) {
        MyCommonAPIs.sleepi(3);
        closeLockedDialog();
        if (noPage) {
            if (hamburgermenu.exists()) {
                hamburgermenu.click();
                if (hamburgermenu.getAttribute("aria-expanded").equals("false")) {
                    hamburgermenu.click();
                }
            } else if (hamburgermenunew.exists()) {
                hamburgermenunew.click();
                if (hamburgermenunew.getAttribute("aria-expanded").equals("false")) {
                    hamburgermenunew.click();
                }
            }
            accountmanager.click();
            waitReady();
            closeLockedDialog();
            subscriptions.click();
            waitReady();
            MyCommonAPIs.sleepi(10);
            if (closedevicecredits.exists()) {
                closedevicecredits.click();
            }
            String url = MyCommonAPIs.getCurrentUrl();
            if (!url.contains("subScriptionsPro")) {
                waitElement(subDiv);
            }
        }
        MyCommonAPIs.sleepi(2);
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    public void closeLockedDialog() {
        MyCommonAPIs.sleepi(15);
        if (closeLockedWindow.exists() && closeLockedWindow.isDisplayed()) {
            closeLockedWindow.click();
        }
        MyCommonAPIs.sleepi(5);
    }

    public void backToDashboardPage() {
        WebCheck.checkHrefIcon(URLParam.hrefDevices);
        waitReady();
    }

    public void backToLogin() {
        WebCheck.checkHrefIcon(URLParam.hrefDevices);
        MyCommonAPIs.sleepi(10);
        if (errorokbutton.exists()) {
            errorokbutton.click();
            logger.info("Error dialog displayed.");
        }
        waitReady();
    }

    public void clickAddInsightIncludedDevices() {
        closeLockedDialog();
        MyCommonAPIs.sleepi(3);
        addInsightDevices.click();
        waitReady();
    }

    public void inputAcccountEmail(Map<String, String> map) {
        if (createaccount.exists()) {
            createaccount.click();
        }
        if (createaccountold.exists()) {
            createaccountold.click();
        }
        // createaccount.doubleClick();
        String url = MyCommonAPIs.getCurrentUrl();
        open(url.replace("login", "signup"));
        if(createfirstname.isDisplayed()) {
        waitElement(createfirstname);
        }else {
            waitElement(createfirstname1);
        }
        if(createemailaddress.isDisplayed()) {
        createemailaddress.sendKeys(map.get("Email Address"));
        }else
            createemailaddress1.sendKeys(map.get("Email Address"));
        System.out.println("email visible");
        if(confirmemail.isDisplayed()) {
        confirmemail.sendKeys(map.get("Confirm Email"));
        }else
        confirmemail1.sendKeys(map.get("Confirm Email"));
    }

    public void inputAccountOtherInfo(Map<String, String> map) {
        if(createfirstname.isDisplayed()) {
        createfirstname.sendKeys(map.get("First Name"));
        }else
         createfirstname1.sendKeys(map.get("First Name"));
        MyCommonAPIs.sleepi(1);
        if(createlastname.isDisplayed()) {
        createlastname.sendKeys(map.get("Last Name"));
        }else
            createlastname1.sendKeys(map.get("Last Name"));
        MyCommonAPIs.sleepi(1);
        if(createpassword.isDisplayed()) {
        createpassword.sendKeys(map.get("Password"));
        }else
            createpassword1.sendKeys(map.get("Password"));
        MyCommonAPIs.sleepi(1);
        if(confirmpassword.isDisplayed()) {
        confirmpassword.sendKeys(map.get("Confirm Password"));
        }else
            confirmpassword1.sendKeys(map.get("Confirm Password"));
        MyCommonAPIs.sleepi(5);
        if(selectcountry.isDisplayed()) {
        selectcountry.selectOption(map.get("Country"));
        }else {
            selectcountry1.selectOption(map.get("Country"));
        }
        MyCommonAPIs.sleepi(2);
    }

    public void inputAccountInfo(Map<String, String> map) {
        logger.info("Create account:" + map.get("Email Address"));
        inputAcccountEmail(map);
        inputAccountOtherInfo(map);
        if (policyText.exists()) {
            ElementsCollection eles = $$(acceptPolicy);
            if (eles.last().isDisplayed()) {
                eles.last().click();
            }
        }else {   
            MyCommonAPIs.sleepi(10);
            acceptPolicy1.click();       
        }
        if(continuebutton.isDisplayed()) {    
        if (continuebutton.isEnabled()) {
            continuebutton.click();
        }
        }else {
            if (continuebutton1.isEnabled()) {
                continuebutton1.click();
        }
        }
        
    }

    public void createAccount(Map<String, String> map) {
       
        MyCommonAPIs.sleepi(10);
        long t= System.currentTimeMillis();
        long end = t+10;
        System.out.println(end);
        System.out.println("going inside while");
        while(System.currentTimeMillis() < end) {
            Selenide.refresh();
            System.out.println("inside while");
            if(loginNowButton.exists()) {
              loginNowButton.click();
              break;
            }
            MyCommonAPIs.sleepi(10);
        }         
        if (loginButtonLandingPage.exists()) {
            loginButtonLandingPage.click();
            MyCommonAPIs.sleepi(10);
            inputAccountInfo(map);
            MyCommonAPIs.sleepi(15);
            
            if(loginPwdNewcognito.isDisplayed()) {
            loginPwdNewcognito.sendKeys(map.get("Password"));
            SigninbuttonCognito.click();
            }
            MyCommonAPIs.sleepi(15);
            
            if (NoThankYou.isDisplayed()) {
                NoThankYou.click();
            }
            waitElement(finishPage);
            if (finishCreate.isDisplayed()) {
                finishCreate.click();
            }
            if (finishbutton.isDisplayed()) {
                finishbutton.click();
            }
            MyCommonAPIs.sleepi(15);
            String url = MyCommonAPIs.getCurrentUrl();
            if (!url.contains("billing")) {
                waitElement(notificationicon);

            }

            closeLockedWindow.click();
        } else {
            inputAccountInfo(map);
            MyCommonAPIs.sleepi(15);
            
            if(loginPwdNewcognito.isDisplayed()) {
            loginPwdNewcognito.sendKeys(map.get("Password"));
            SigninbuttonCognito.click();
            }
            MyCommonAPIs.sleepi(15);

            if (NoThankYou.isDisplayed()) {
                NoThankYou.click();
            }
//            waitElement(finishPage);
            if (finishCreate.isDisplayed()) {
                finishCreate.click();
            }
            if (finishbutton.isDisplayed()) {
                finishbutton.click();
            }
            MyCommonAPIs.sleepi(20);
            String url = MyCommonAPIs.getCurrentUrl();
            if (!url.contains("billing")) {
                waitElement(notificationicon);

            }

            closeLockedWindow.click();

        }
    }

    public void MigrateAccount(Map<String, String> map) {
        waitElement(createaccount);
        // createaccount.doubleClick();
        String url = MyCommonAPIs.getCurrentUrl();
        logger.info("Create account:" + map.get("Email Address"));
        loginPwd.sendKeys(map.get("Password"));
        MyCommonAPIs.sleepi(5);
        loginButton.click();
        MyCommonAPIs.sleepi(30);
    }

    public boolean checkTwoFaPage(Map<String, String> map, boolean verification, String phonenum, String Country) {
        boolean result = false;
        long t= System.currentTimeMillis();
        long end = t+12000;
        System.out.println("going inside while");
        while(System.currentTimeMillis() < end) {
            System.out.println("inside while");
            Selenide.refresh();
            if(loginNowButton.exists()) {
              loginNowButton.click();
              break;
            }
            MyCommonAPIs.sleepi(10);
        }
        inputAccountInfo(map);
        waitElement(yesenbalenew);
        yesenbalenew.click();
        // if (yesenbale.exists()) {
        // yesenbale.click();
        // } else if (yesenbalenew.exists()) {
        // yesenbalenew.click();
        // }
        // if ($x("//div[@ng-show='selectTsv']").getAttribute("class").equals("Verfiecol"))
        // MyCommonAPIs.sleepi(5);
        // if ($x("//div[@class='Verfiecol']//div[text()='SMS Text Message']").exists()) {
        // logger.info("In the 2fa page now.");
        // result = true;
        // if (verification && result == true) {
        // if($x("(//button[text()='Yes, Enable'])[2]").exists()) {
        // $x("(//button[text()='Yes, Enable'])[2]").click();
        // }
        // if($x("(//button[text()='Yes enable'])[2]").exists()) {
        // $x("(//button[text()='Yes enable'])[2]").click();
        // }
        //
        // MyCommonAPIs.sleepi(10);
        // waitElement(inputphone);
        // if (inputphone.exists()) {
        // logger.info("In the \"Add SMS Verification\" page now.");
        // result = true;
        // } else {
        // result = false;
        // }
        // if (result && !phonenum.equals("")) {
        // finishSignup(phonenum);
        // MyCommonAPIs.sleepi(10);
        // if ($x("//div[@ng-show='logging' and @class='wrapper']//p[text()='You have successfully created a NETGEAR
        // account.']")
        // .exists()) {
        // result = true;
        // logger.info("Sign up successful.");
        // finishbutton.click();
        // MyCommonAPIs.sleepi(60);
        // String url = MyCommonAPIs.getCurrentUrl();
        // if (url.contains("/#/locked")) {
        // closeLockedDialog();
        // }
        // if (closeLockedWindow.exists()) {
        // closeLockedWindow.click();
        // }
        // if (notificationicon.exists()) {
        // result = true;
        // logger.info("Login successful.");
        // } else {
        // refresh();
        // MyCommonAPIs.sleepi(60);
        // if (notificationicon.exists()) {
        // result = true;
        // logger.info("Login successful.");
        // }
        // }
        // } else {
        // logger.info("Sign up unsuccessful.");
        // result = false;
        // Selenide.back();
        // }
        // }
        // }
        // }
        // code by tejeshwini
        if ($x("//h2[contains(text(),'Enter phone number')]").exists()) {

            if (inputphone.exists()) {
                logger.info("In the \"Add SMS Verification\" page now.");
                result = true;
            } else {
                result = false;
            }
            if (result && !phonenum.equals("")) {
                finishSignup(phonenum, Country);
                MyCommonAPIs.sleepi(10);
                dontTrust.click();
                MyCommonAPIs.sleepi(10);
                if ($x("//div[@ng-show='logging' and @class='wrapper']//p[text()='You successfully created a NETGEAR account.']").exists()) {
                    result = true;
                    logger.info("Sign up successful.");
                    finishCreate.click();
                    MyCommonAPIs.sleepi(60);
                    closeLockedDialog();
                    if (closeLockedWindow.exists()) {
                        closeLockedWindow.click();
                    }
                    if (notificationicon.exists()) {
                        result = true;
                        logger.info("Login successful.");
                    } else {
                        refresh();
                        MyCommonAPIs.sleepi(60);
                        if (notificationicon.exists()) {
                            result = true;
                            logger.info("Login successful.");
                        }
                    }
                } else {
                    logger.info("Sign up unsuccessful.");
                    result = false;
                    Selenide.back();
                }
            }
        }
        MyCommonAPIs.sleepi(8);
        Selenide.back();
        return result;

    }

    public void finishSignup(String phonenum, String Country) {
        MyCommonAPIs.sleepi(5);
        if($x("//div[@class='flag-container']").isDisplayed()) {
        waitElement($x("//div[@class='flag-container']"));
        MyCommonAPIs.sleepi(8);
        $x("//div[@class='flag-container']").click();
        }else {
            $x("//*[text()=\"SMS Text Message\"]/../../span").click();
            $x("//*[text() ='Continue']").click();
         } 
        MyCommonAPIs.sleepi(3);
        if($x("//li[@data-country-code='us']").isDisplayed()) {
        $x("//li[@data-country-code='us']").click();
        }else {
            $x("//*[@id=\"scroll-style\"]/div[2]/div/form/div[2]/div/ngx-intl-tel-input/div/div/div[1]/div[3]").click();
            $x("//*[@id=\"iti-0__item-au\"]/span[1]").hover();
            MyCommonAPIs.sleepi(3);
            $x("//*[@id=\"iti-0__item-au\"]/span[1]").click();
        }
        MyCommonAPIs.sleepi(3);
        logger.info("Input phone number is:" + phonenum);
        if (inputphone.exists()) {
            inputphone.sendKeys(phonenum);
        } else if ($x("//input[@id='phone']").exists()) {
            $x("//input[@id='phone']").sendKeys(phonenum);
        }
        MyCommonAPIs.sleepi(5);
        if (addphone.exists()) {
            addphone.click();
        }
        if (Next.exists()) {
            Next.click();
        }

        waitElement(verifybutton);
        String code = getAuthCode(phonenum, pairMessage, Country);
        logger.info("Confirmation code is:" + code);
        
        Selenide.switchTo().window(0);
        String currentUrl1=new MyCommonAPIs().getCurrentUrl();
        System.out.print(currentUrl1);
        MyCommonAPIs.sleepi(10);
        int i =1;
        for (int id = 0; id < 6; id++) {
//            $x("//*[@id='" + String.valueOf(id) + "']").sendKeys(code.substring(id, id + 1));
            // $("#" + String.valueOf(id)).sendKeys(code.substring(id, id + 1));
//            OTPbox(String.valueOf(id)).sendKeys(code.substring(id, id + 1));                       
            $x("(//*[@autocomplete=\"one-time-code\"])["+String.valueOf(i)+"]").sendKeys(code.substring(id, id + 1));
            i++;
            
        }
        verifybutton.click();
        MyCommonAPIs.sleepi(30);
        if(SelectNumber.isDisplayed()){
            SelectNumber.click();
            MyCommonAPIs.sleepi(2);
            SaveNumber.click();
        }
       
        MyCommonAPIs.sleepi(2);
        
        
    }

    public String getAuthCode(String phonenum, String needMsg, String Country)  {
        String code = null;
        
        
     // Open a new tab using JavaScript
        WebDriver driver = WebDriverRunner.getWebDriver();
        String url = "https://quackr.io/temporary-numbers/" +Country + phonenum;
        ((JavascriptExecutor) driver).executeScript("window.open('" + url + "', '_blank');");
        Selenide.switchTo().window(1);
        String currentUrl=new MyCommonAPIs().getCurrentUrl();
        System.out.print(currentUrl);
        MyCommonAPIs.sleepi(30);

        List<SelenideElement> elements = $$x("//*[contains(text(),'NETGEAR ')]");
        
        for (SelenideElement element : elements) {
            String text = element.getText();
            System.out.println("text is"+ text);
            if (text.contains("NETGEAR")) {
                Pattern pattern = Pattern.compile("\\b\\d{6}\\b");
                Matcher matcher = pattern.matcher(text);
                if (matcher.find()) {
                    String otp = matcher.group();
                    logger.info("OTP found: " + otp);
                    back();
                    return otp; // Return the OTP if found
                }
            }
        }

        
       
        
        
        logger.info("No OTP found.");
        return null; // Return null if no OTP is found
        
        
//        boolean get = false;
//        
//        try {
//            // Fetch the page
//            Document doc = Jsoup.connect("https://quackr.io/temporary-numbers/australia/61" + phonenum)
//                                 .timeout(10000) // 10 seconds
//                                 .get();
//            
//            System.out.println(doc.html());
//
//            // Select the elements
//            Elements rows = doc.select(".rounded.shadow.dark div, .rounded.shadow.dark p");
//            System.out.println("Number of elements found: " + rows.size());
//
//            for (org.jsoup.nodes.Element row : rows) {
//                String message = row.text(); // Get the full text of the element
//                System.out.println("Message: " + message);
//
//                if (message.contains(needMsg)) {
//                    // Extract the OTP
//                    int startIndex = message.indexOf(needMsg) + needMsg.length() + 1;
//                    code = message.substring(startIndex, startIndex + 6).trim(); // Adjust based on your OTP format
//                    if (!code.isEmpty()) {
//                        get = true;
//                        break;
//                    }
//                }
//            }
//
//        } catch (HttpStatusException e) {
//            System.err.println("HTTP error fetching URL. Status=" + e.getStatusCode() + ", URL=" + e.getUrl());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        
      

        }
    
    
    
    
    public String GetOtpForInsight(String mobileNumber) {
        
        logger.info("Checking OTP for country: " + "australia" + ", mobile number: " + mobileNumber);
        
        // Open the Quackr page for the specific country and mobile number
       
        open("https://quackr.io/temporary-numbers/" + "australia" + "/" + "61" + mobileNumber);
        MyCommonAPIs.sleepi(5);

        // Find elements containing "Insight" in their text
        List<SelenideElement> elements = $$x("//*[contains(text(),'NETGEAR')]");
        
        for (SelenideElement element : elements) {
            String text = element.getText();
            if (text.contains("NETGEAR")) {
                Pattern pattern = Pattern.compile("\\b\\d{6}\\b");
                Matcher matcher = pattern.matcher(text);
                if (matcher.find()) {
                    String otp = matcher.group();
                    logger.info("OTP found: " + otp);
                    back();
                    return otp; // Return the OTP if found
                }
            }
        }

        logger.info("No OTP found.");
        return null; // Return null if no OTP is found
    }

    public void addTwoFAPhonenum(String newphonenum, String Country) {
        updateprofile.click();
        MyCommonAPIs.sleepi(10);
        if (cancelbutton.exists()) {
            cancelbutton.click();
        } else {
            MyCommonAPIs.sleepi(10);
            if (cancelbutton.exists()) {
                cancelbutton.click();
            }
        }
        loginsettings.click();
        MyCommonAPIs.sleepi(8);
        twostepverification.click();
        MyCommonAPIs.sleepi(5);
        if ($x("//span[text()='Enable']").exists()) {
            if ($x("//span[text()='Enable']/../md-switch").getAttribute("aria-checked").equals("true")) {
                waitElement(addsmsverification);
            } else {
                $x("(//span[text()='Enable']/..//div)[4]").click();
                waitElement(addsmsverification);
            }
        }
        SelenideElement phonelist = $x("//div[text()='SMS Verification']/..");
        // if (!(phonelist.getText().substring(phonelist.getText().indexOf("*", 5) + 2, phonelist.getText().indexOf("*", 5) + 6)
        // .contains(newphonenum.substring(newphonenum.length() - 4, newphonenum.length())))) {
        if (addsmsverification.exists() && !phonelist.getText().contains(newphonenum.substring(newphonenum.length() - 4, newphonenum.length()))) {
            logger.info("Add phone number is:" + newphonenum);
            addsmsverification.click();
            finishSignup(newphonenum, Country);
            MyCommonAPIs.sleepi(20);
            if (addsmsverification.exists()
                    && !phonelist.getText().contains(newphonenum.substring(newphonenum.length() - 4, newphonenum.length()))) {
                logger.info("Add phone number failed once, then add again.");
                addsmsverification.click();
                finishSignup(newphonenum, Country);
                MyCommonAPIs.sleepi(10);
            }
        } else {
            logger.info("Phone number is existed");
        }
        Selenide.back();
    }

    public boolean checkPhonenumMaxLimit(String phonenum) {
        boolean result = false;
        updateprofile.click();
        MyCommonAPIs.sleepi(10);
        if (cancelbutton.exists()) {
            cancelbutton.click();
        } else {
            MyCommonAPIs.sleepi(10);
            if (cancelbutton.exists()) {
                cancelbutton.click();
            }
        }
        loginsettings.click();
        MyCommonAPIs.sleepi(8);
        twostepverification.click();
        MyCommonAPIs.sleepi(8);
        SelenideElement phonelist = $x("//span[text()='Primary']/..");
        if (!(phonelist.getText().substring(phonelist.getText().indexOf("*", 5) + 2, phonelist.getText().indexOf("*", 5) + 6)
                .equals(phonenum.substring(phonenum.length() - 4, phonenum.length())))) {
            addsmsverification.click();
            waitElement($x("//div[@class='flag-container']"));
            MyCommonAPIs.sleepi(8);
            $x("//div[@class='flag-container']").click();
            logger.info("Input phone number is:" + phonenum);
            $x("//li[@data-country-code='us']").click();
            if (inputphone.exists()) {
                inputphone.sendKeys(phonenum);
            }
            MyCommonAPIs.sleepi(5);
            addphone.click();
            int count = 0;
            while (true) {
                MyCommonAPIs.sleepi(10);
                if ($x("//p[text()='Maximum limit reached, unable to add more']").exists()) {
                    result = true;
                    logger.info("Phone number is maximum limit reached.");
                    $x("//button[text()='OK']").click();
                    break;
                } else if (count == 5) {
                    logger.info("Phone number is not maximum limit reached.");
                    break;
                }
                count += 1;
            }
            MyCommonAPIs.sleepi(10);
        }
        Selenide.back();
        return result;
    }

    public void deleteAllTwoFAPhonenumWithoutPrimary() {
        updateprofile.click();
        MyCommonAPIs.sleepi(10);
        if (cancelbutton.exists()) {
            cancelbutton.click();
        } else {
            MyCommonAPIs.sleepi(10);
            if (cancelbutton.exists()) {
                cancelbutton.click();
            }
        }
        loginsettings.click();
        MyCommonAPIs.sleepi(8);
        twostepverification.click();
        MyCommonAPIs.sleepi(10);
        ElementsCollection phonelist = $$x("//div[text()='SMS Verification']/../div");
        for (int i = phonelist.size(); i > 0; i--) {
            if (phonelist.get(i - 1).getText().contains("SMS Verification")) {
                continue;
            } else if (phonelist.get(i - 1).getText().contains("Primary")) {
                continue;
            }
            // phonelist.get(i - 1).$x("//div[text()='Remove']").click();
            $x("(//div[text()='Remove'])[" + String.valueOf(i - 1) + "]").click();
            MyCommonAPIs.sleepi(5);
            removenumbutton.click();
            logger.info("Delete phonenum successful.");
            MyCommonAPIs.sleepi(10);
        }
        WebCheck.checkHrefIcon(URLParam.hrefDevices);
    }

    public void deleteTwoFAPhonenum(String phonenum) {
        updateprofile.click();
        MyCommonAPIs.sleepi(10);
        if (cancelbutton.exists()) {
            cancelbutton.click();
        } else {
            MyCommonAPIs.sleepi(10);
            if (cancelbutton.exists()) {
                cancelbutton.click();
            }
        }
        loginsettings.click();
        MyCommonAPIs.sleepi(8);
        twostepverification.click();
        MyCommonAPIs.sleepi(10);
        ElementsCollection phonelist = $$x("//div[text()='SMS Verification']/../div");
        for (int i = 0; i < phonelist.size(); i++) {
            if (phonelist.get(i).getText().equals("SMS Verification")) {
                continue;
            } else if (phonelist.get(i).getText().indexOf("Primary") != -1) {
                continue;
            }
            if (phonelist.get(i).getText().contains(phonenum.substring(phonenum.length() - 4, phonenum.length()))) {
                $x("(//div[text()='Remove'])[" + String.valueOf(i) + "]").click();
                MyCommonAPIs.sleepi(5);
                removenumbutton.click();
                logger.info("Delete " + phonenum + " successful.");
                MyCommonAPIs.sleepi(10);
                Selenide.back();
                break;
            }
        }
    }

    public void addTwoFAPhonenumAndChangePrimary(String oldphonenum, String newphonenum, String Country) {
        updateprofile.click();
        MyCommonAPIs.sleepi(10);
        if (cancelbutton.exists()) {
            cancelbutton.click();
        } else {
            MyCommonAPIs.sleepi(10);
            if (cancelbutton.exists()) {
                cancelbutton.click();
            }
        }
        loginsettings.click();
        MyCommonAPIs.sleepi(8);
        twostepverification.click();
        MyCommonAPIs.sleepi(8);
        SelenideElement phonelist = $x("//span[text()='Primary']/..");
        if (!(phonelist.getText().substring(phonelist.getText().indexOf("*", 5) + 2, phonelist.getText().indexOf("*", 5) + 6)
                .equals(newphonenum.substring(newphonenum.length() - 4, newphonenum.length())))) {
            addsmsverification.click();
            finishSignup(newphonenum, Country);
            MyCommonAPIs.sleepi(10);
            deleteprimarynum.click();
            MyCommonAPIs.sleepi(5);
            removenumbutton.click();
            logger.info("Old primary number is:" + oldphonenum + ", new primary number is:" + newphonenum);
            MyCommonAPIs.sleepi(5);
            if ($x("//button[@class='md-no-style md-button ng-scope md-ink-ripple']").exists()) {
                $x("//button[@class='md-no-style md-button ng-scope md-ink-ripple']").click();
                MyCommonAPIs.sleepi(5);
                savenum.click();
            }
        }
        MyCommonAPIs.sleepi(10);
        Selenide.back();
    }

    public void enableTwoFA(String phonenum, String Country) {
        updateprofile.click();
        MyCommonAPIs.sleepi(10);
        if (cancelbutton.exists()) {
            cancelbutton.click();
        } else {
            MyCommonAPIs.sleepi(10);
            if (cancelbutton.exists()) {
                cancelbutton.click();
            }
        }
        loginsettings.click();
        MyCommonAPIs.sleepi(10);
        twostepverification.click();
        MyCommonAPIs.sleepi(10);        
        SelenideElement phonelist = $x("//div[@class='FactorNmae ng-binding']");
        
        if($x("//span[text()='Enable']/../md-switch").isDisplayed()) {

        if (phonelist.exists() && !phonelist.getText().equals("")) {
            System.out.println("inside if");
            if (phonelist.getText().contains(phonenum.substring(phonenum.length() - 4, phonenum.length()))) {
                System.out.println("inside phonelist");
                if ($x("//span[text()='Enable']").exists()
                        &&  $x("//span[text()='Enable']/../mat-slide-toggle/div/button").getAttribute("aria-checked").equals("false")) {
                    System.out.println("inside click");
                    $x("(//span[text()='Enable']/..//div)[4]").click();
                }
            }
        } else {
            System.out.println("inside else");
            if ($x("//span[text()='Enable']").exists() && ($x("//span[text()='Enable']/../md-switch").getAttribute("aria-checked").equals("false") )) {
                System.out.println("inside enable");
                $x("(//span[text()='Enable']/..//div)[4]").click();
            }
            finishSignup(phonenum, Country);
        }
        }else {
            
            if (phonelist.exists() && !phonelist.getText().equals("")) {
                System.out.println("inside if");
                if (phonelist.getText().contains(phonenum.substring(phonenum.length() - 4, phonenum.length()))) {
                    System.out.println("inside phonelist");
                    if ($x("//span[text()='Enable']").exists()
                            &&  $x("//span[text()='Enable']/../mat-slide-toggle/div/button").getAttribute("aria-checked").equals("false")) {
                        System.out.println("inside click");
                        $x("(//span[text()='Enable']/..//div)[4]").click();
                    }
                }
            } else {
                System.out.println("inside else");
                if ($x("//span[text()='Enable']").exists() &&  $x("//span[text()='Enable']/../mat-slide-toggle/div/button").getAttribute("aria-checked").equals("false")) {
                    System.out.println("inside enable");
                    $x("(//span[text()='Enable']/..//div)[4]").click();
                }
                finishSignup(phonenum, Country);
            }
        }
        MyCommonAPIs.sleepi(10);
        Selenide.back();
    }

    public void resendMessageByTwoFA(String phonenum, String Country) {
        SelenideElement continuebutton = $x("//span[text()='Resend Message']/../../..//span[text()='Continue']");
        waitElement($x("//input[@id='0']"));
        $x("//span[text()='Resend Message']/..").click();
        MyCommonAPIs.sleepi(20);
        String code = getAuthCode(phonenum, authMessage, Country);
        logger.info("Confirmation code is:" + code);
        MyCommonAPIs.sleepi(10);
        Selenide.switchTo().window(0);
        String currentUrl1=new MyCommonAPIs().getCurrentUrl();
        System.out.print(currentUrl1);
        MyCommonAPIs.sleepi(10);
        for (int id = 0; id < 6; id++) {
            $x("//*[@id='" + String.valueOf(id) + "']").sendKeys(code.substring(id, id + 1));
            // $("#" + String.valueOf(id)).sendKeys(code.substring(id, id + 1));
        }
        continuebutton.click();
        MyCommonAPIs.sleepi(10);
        dontTrust.click();
        int ls = 0;
        while (ls < 5) {
            MyCommonAPIs.sleepi(50);
            if (!notificationicon.exists()) {
                refresh();
            } else {
                break;
            }
            ls += 1;
        }
    }

    public boolean checkTwoFAUseSameCodeLogin(String phonenum, String Country) {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.inputLogin(WebportalParam.loginName, WebportalParam.loginPassword);
        boolean result = false;
        SelenideElement continuebutton = $x("//span[text()='Resend Message']/../../..//span[text()='Continue']");
        waitElement($x("//input[@id='0']"));
        String code = getAuthCode(phonenum, authMessage, Country);
        logger.info("Confirmation code is:" + code);
        MyCommonAPIs.sleepi(10);
        Selenide.switchTo().window(0);
        String currentUrl1=new MyCommonAPIs().getCurrentUrl();
        System.out.print(currentUrl1);
        for (int id = 0; id < 6; id++) {
            $x("//*[@id='" + String.valueOf(id) + "']").sendKeys(code.substring(id, id + 1));
            // $("#" + String.valueOf(id)).sendKeys(code.substring(id, id + 1));
        }
        continuebutton.click();
        MyCommonAPIs.sleepi(10);
        dontTrust.click();
        // waitElement($x("//div[@data-tooltip='Notifications']"));
        int ls = 0;
        while (ls < 5) {
            MyCommonAPIs.sleepi(50);
            if (notificationicon.exists()) {
                result = true;
                break;
            } else {
                refresh();
            }
            ls += 1;
        }
        UserManage userManage = new UserManage();
        userManage.logout();
        webportalLoginPage.inputLogin(WebportalParam.loginName, WebportalParam.loginPassword);
        waitElement($x("//input[@id='0']"));
        MyCommonAPIs.sleepi(10);
        for (int id = 0; id < 6; id++) {
            $x("//*[@id='" + String.valueOf(id) + "']").sendKeys(code.substring(id, id + 1));
        }
        continuebutton.click();
        MyCommonAPIs.sleepi(10);
        if ($x("//input[@id='0']").exists()) {
            result = true;
        }
        return result;
    }

    public boolean checkTwoFAIsCorrect(String phonenum, boolean verification, String Country) {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.inputLogin(WebportalParam.loginName, WebportalParam.loginPassword);
        boolean result = false;
        SelenideElement continuebutton = $x("//span[text()='Resend Message']/../../..//span[text()='Continue']");
        // waitElement($x("//input[@id='0']"));
        if (verification) {
            String code = getAuthCode(phonenum, authMessage, Country);
            MyCommonAPIs.sleepi(10);
            Selenide.switchTo().window(0);
            String currentUrl1=new MyCommonAPIs().getCurrentUrl();
            System.out.print(currentUrl1);
            logger.info("Confirmation code is:" + code);
            for (int id = 0; id < 6; id++) {
                $x("//*[@id='" + String.valueOf(id) + "']").sendKeys(code.substring(id, id + 1));
                // $("#" + String.valueOf(id)).sendKeys(code.substring(id, id + 1));
            }
            continuebutton.click();
            MyCommonAPIs.sleepi(10);
            dontTrust.click();
            int ls = 0;
            while (ls < 5) {
                MyCommonAPIs.sleepi(50);
                if (notificationicon.exists()) {
                    result = true;
                    break;
                } else {
                    refresh();
                }
                ls += 1;
            }
        } else {
            String code = getAuthCode(phonenum, authMessage, Country);
            MyCommonAPIs.sleepi(10);
            Selenide.switchTo().window(0);
            String currentUrl1=new MyCommonAPIs().getCurrentUrl();
            System.out.print(currentUrl1);
            logger.info("Confirmation code is:" + code);
            MyCommonAPIs.sleepi(10);
            for (int id = 0; id < 6; id++) {
                if (id == 3) {
                    $x("//*[@id='" + String.valueOf(id) + "']").sendKeys("2");
                    continue;
                }
                $x("//*[@id='" + String.valueOf(id) + "']").sendKeys(code.substring(id, id + 1));
                // $("#" + String.valueOf(id)).sendKeys(code.substring(id, id + 1));
            }
            continuebutton.click();
            MyCommonAPIs.sleepi(10);
            if ($x("//input[@id='0']").exists()) {
                result = true;
            }
        }
        return result;
    }

    public void disableTwoFA() {
        // if($x("//div[@ng-show='twoStep_verification' and @aria-hidden='false']").exists()) {
        //
        // }
        updateprofile.click();
        int count = 0;
        while (count < 5) {
            MyCommonAPIs.sleepi(10);
            if (cancelbutton.exists()) {
                cancelbutton.click();
                break;
            }
            count += 1;
        }
        // try {
        // waitElement(cancelbutton);
        // cancelbutton.click();
        // } catch (Exception e) {
        // logger.info("Not find cancelbutton.");
        // }
        loginsettings.click();
        MyCommonAPIs.sleepi(8);
        twostepverification.click();
        MyCommonAPIs.sleepi(8);
        SelenideElement phonelist = $x("//div[@class='FactorNmae ng-binding']");
        if ($x("//span[text()='Enable']").exists() && $x("//span[text()='Enable']/../md-switch").getAttribute("aria-checked").equals("true")) {
            $x("(//span[text()='Enable']/..//div)[4]").click();
            MyCommonAPIs.sleepi(5);
            if ($x("//span[text()='Disable']/..").exists()) {
                $x("//span[text()='Disable']/..").click();
                MyCommonAPIs.sleepi(5);
                logger.info("Disable 2fa.");
            }
        }
        Selenide.back();
    }

    public void changeEmail(String newEmail, String confirmEmail, String password) {
        updateprofile.click();
        MyCommonAPIs.sleepi(10);
        if (cancelbuttonCognito.exists()) {
            cancelbuttonCognito.click();
        } else if (cancelbutton.exists()) {
            cancelbutton.click();
        } else {
            refresh();
            MyCommonAPIs.sleepi(10);
            if (cancelbutton.exists()) {
                cancelbutton.click();
            }
        }
        loginsettings.click();
        MyCommonAPIs.sleepi(5);
        logger.info("Start change email...");
        changeemail.click();
        MyCommonAPIs.sleepi(10);
        if (newemailCognito.exists()) {
            newemailCognito.sendKeys(newEmail);
        } else {
            waitElement(newemail);
            newemail.sendKeys(newEmail);
        }
        MyCommonAPIs.sleep(10);
        if (confirmmailCognito.exists()) {
            confirmmailCognito.sendKeys(confirmEmail);
        } else {
            waitElement(confirmmail);
            confirmmail.sendKeys(confirmEmail);
        }
        MyCommonAPIs.sleep(10);
        if (currentpasswordCognito.exists()) {
            currentpasswordCognito.sendKeys(password);
        } else {
            waitElement(currentpassword);
            currentpassword.sendKeys(password);
        }
        MyCommonAPIs.sleep(10);
        if (submitchangeemailCognito.exists()) {
            submitchangeemailCognito.click();
        } else {
            waitElement(submitchangeemail);
            submitchangeemail.click();
        }
        MyCommonAPIs.sleepi(10);
        String otp = "";
        if (verifyOtpScreenCognito.exists()) {
            String originalTab = WebDriverRunner.getWebDriver().getWindowHandle();
            executeJavaScript("window.open('https://www.yopmail.com', '_blank');");
            for (String tab : WebDriverRunner.getWebDriver().getWindowHandles()) {
                if (!tab.equals(originalTab)) {
                    WebDriverRunner.getWebDriver().switchTo().window(tab);
                    break;
                }
            }
            MyCommonAPIs.sleepi(5);
            String inputElement = "//input[@id='login']";
            $x(inputElement).clear();
            $x(inputElement).sendKeys(confirmEmail);
            $x("//button[@title='Check Inbox @yopmail.com']").click();
            MyCommonAPIs.sleepi(10);
            SelenideElement frame = $("iframe[name='ifinbox']");
            Selenide.switchTo().frame(frame);
            MyCommonAPIs.sleepi(10);
            System.out.println(checkemailtitle.getText());
            if (checkemailtitle.getText().contains("Change Your Account Email Address")) {
                logger.info("Received Device Online Notification email.");
                switchTo().defaultContent();
                SelenideElement mailFrame = $("[name='ifmail']");
                MyCommonAPIs.sleepi(10);
                mailFrame.shouldBe(Condition.visible);// Adjust timeout as needed
                switchTo().frame(mailFrame);
                MyCommonAPIs.sleepi(10);
                confirmEmailOtpYopmail.scrollIntoView(true);
                String otpText = confirmEmailOtpYopmail.getText();
                System.out.println("OTP is: " + otpText);
                MyCommonAPIs.sleepi(1);
                switchTo().defaultContent();
                WebDriverRunner.getWebDriver().close();
                MyCommonAPIs.sleepi(2);
                WebDriverRunner.getWebDriver().switchTo().window(originalTab);
                MyCommonAPIs.sleepi(2);
                System.out.println(otpText);
                enterChangeemailOTP.click();
                MyCommonAPIs.sleepi(2);
                enterOTP.setValue(otpText);
                //executeJavaScript("arguments[0].value = arguments[1];", enterChangeemailOTP.getWrappedElement(), otpText);
                MyCommonAPIs.sleep(10);
                submitchangeemailCognito.click();
                MyCommonAPIs.sleepi(10);
            }
        } else {
            MyCommonAPIs.sleepi(5);
            logger.info("Cognito servcer is not present");
            WebCheck.checkHrefIcon(URLParam.hrefDevices);
            if (okconformation.exists()) {
                okconformation.click();
            } else if (Invalidsession.isDisplayed()) {
                Invalidsession.click();
            } else {
                logger.info("Done");
            }
        }
    }

    public void editProfile(Map<String, String> map) {
        updateprofile.click();
        MyCommonAPIs.sleepi(10);
        if (cancelbuttonCognito.exists()) {
            cancelbuttonCognito.click();
        } else if (cancelbutton.exists()) {
            cancelbutton.click();
        } else {
            refresh();
            MyCommonAPIs.sleepi(10);
            if (cancelbutton.exists()) {
                cancelbutton.click();
            }
        }
        editprofile.click();
        MyCommonAPIs.sleepi(10);
        logger.info("Start edit profile...");
        if (map.containsKey("First Name")) {
            firstNameCognito.shouldBe(Condition.visible);
            MyCommonAPIs.sleep(1);
            firstNameCognito.scrollIntoView(true);
            MyCommonAPIs.sleep(1);
             firstNameCognito.setValue(map.get("First Name"));
        } else if (map.containsKey("First Name") && firstname.exists()) {
            // firstname.clear();
            // MyCommonAPIs.sleepi(3);
            firstname.setValue(map.get("First Name"));
        }
        MyCommonAPIs.sleepi(1);
        if (map.containsKey("Last Name")) {
            lastNameCognito.shouldBe(Condition.visible);
            MyCommonAPIs.sleep(1);
            lastNameCognito.scrollIntoView(true);
            MyCommonAPIs.sleep(1);
             lastNameCognito.setValue(map.get("Last Name"));
        } else if (map.containsKey("Last Name") && lastname.exists()) {
            // lastname.clear();
            // MyCommonAPIs.sleepi(3);
            lastname.setValue(map.get("Last Name"));
        }
        MyCommonAPIs.sleepi(1);
        if (map.containsKey("State")) {
            stateCognito.shouldBe(Condition.visible);
            MyCommonAPIs.sleep(1);
            stateCognito.scrollIntoView(true);
            MyCommonAPIs.sleep(1);
            stateCognito.setValue(map.get("State"));
        } else if (map.containsKey("State") && state.exists()) {
            // state.clear();
            // MyCommonAPIs.sleepi(3);
            state.setValue(map.get("State"));
        }
        MyCommonAPIs.sleepi(1);
        if (map.containsKey("City")) {
            cityCognito.shouldBe(Condition.visible);
            MyCommonAPIs.sleep(1);
            cityCognito.scrollIntoView(true);
            MyCommonAPIs.sleep(1);
            cityCognito.setValue(map.get("City"));
        } else if (map.containsKey("City") && city.exists()) {
            // city.clear();
            // MyCommonAPIs.sleepi(3);
            city.setValue(map.get("City"));
        }
        MyCommonAPIs.sleepi(1);
        if (map.containsKey("Street Address")) {
            streetAddCognito.shouldBe(Condition.visible);
            MyCommonAPIs.sleep(1);
            streetAddCognito.scrollIntoView(true);
            MyCommonAPIs.sleep(1);
            streetAddCognito.setValue(map.get("Street Address"));
        } else if (map.containsKey("Street Address") && streetaddress.exists()) {
            // streetaddress.clear();
            // MyCommonAPIs.sleepi(3);
            streetaddress.setValue(map.get("Street Address"));
        }
        MyCommonAPIs.sleepi(1);
        if (map.containsKey("Apartment or Suite")) {
            apartmentCognito.shouldBe(Condition.visible);
            MyCommonAPIs.sleep(1);
            apartmentCognito.scrollIntoView(true);
            MyCommonAPIs.sleep(1);
            apartmentCognito.setValue(map.get("Apartment or Suite"));
        } else if (map.containsKey("Apartment or Suite") && apartmentorsuite.exists()) {
            // apartmentorsuite.clear();
            // MyCommonAPIs.sleepi(3);
            apartmentorsuite.setValue(map.get("Apartment or Suite"));
        }
        MyCommonAPIs.sleepi(1);
        if (map.containsKey("Postal/ZIP Code")) {
            zipcodeCognito.shouldBe(Condition.visible);
            MyCommonAPIs.sleep(1);
            zipcodeCognito.scrollIntoView(true);
            MyCommonAPIs.sleep(1);
            zipcodeCognito.setValue(map.get("Postal/ZIP Code"));
        } else if (map.containsKey("Postal/ZIP Code") && postalzipcode.exists()) {
            // postalzipcode.clear();
            // MyCommonAPIs.sleepi(3);
            postalzipcode.setValue(map.get("Postal/ZIP Code"));
        }
        MyCommonAPIs.sleepi(15);
        savebutton.click();
        MyCommonAPIs.sleepi(15);
        if (cancelbuttonCognito.exists()) {
            cancelbuttonCognito.click();
        } else if (cancelbutton.exists()) {
            cancelbutton.click();
        }
//        MyCommonAPIs.sleepi(10);
//        $x("//div[@ng-show='acntMgnt' and @aria-hidden='false']//span[@ng-show='thirdParty']/a").click();
        logger.info("Finish edit profile...");
    }

    public Map<String, String> getProfile() {
        updateprofile.click();
        MyCommonAPIs.sleepi(10);
        if (cancelbuttonCognito.exists()) {
            cancelbuttonCognito.click();
        } else if (cancelbutton.exists()) {
            cancelbutton.click();
        } else {
            MyCommonAPIs.sleepi(10);
            if (cancelbutton.exists()) {
                cancelbutton.click();
            }
        }
        editprofile.click();
        MyCommonAPIs.sleepi(15);
        if (firstNameCognito.exists()) {
            logger.info("Get user profile...");
            Map<String, String> profileInfo = new HashMap<String, String>();
            profileInfo.put("First Name", firstNameCognito.getValue());
            profileInfo.put("Last Name", lastNameCognito.getValue());
            //profileInfo.put("Choose Country", choosecountry.getText());
            profileInfo.put("State", stateCognito.getValue());
            profileInfo.put("City", cityCognito.getValue());
            profileInfo.put("Street Address", streetAddCognito.getValue());
            profileInfo.put("Apartment or Suite", apartmentCognito.getValue());
            profileInfo.put("Postal/ZIP Code", zipcodeCognito.getValue());  
            WebCheck.checkHrefIcon(URLParam.hrefDevices);
            return profileInfo;
        } else {
            logger.info("Get user profile...");
            Map<String, String> profileInfo = new HashMap<String, String>();
            profileInfo.put("First Name", firstname.getValue());
            profileInfo.put("Last Name", lastname.getValue());
            profileInfo.put("Choose Country", choosecountry.getText());
            profileInfo.put("State", state.getValue());
            profileInfo.put("City", city.getValue());
            profileInfo.put("Street Address", streetaddress.getValue());
            profileInfo.put("Apartment or Suite", apartmentorsuite.getValue());
            profileInfo.put("Postal/ZIP Code", postalzipcode.getValue());
            WebCheck.checkHrefIcon(URLParam.hrefDevices);
            return profileInfo;
        }
    }

    public boolean checkLogout() {
        boolean result = false;
        logger.info("Logout account...");
        logout.click();
        MyCommonAPIs.sleepi(5);
        if ($x("//button/*[text()=\"NETGEAR Sign In\"]").exists()) {
            result = true;
            logger.info("Logout successful...");
        }

        if ($x("//button/*[text()=\"Login\"]").exists()) {
            result = true;
            logger.info("Logout successful...");
        }
        return result;
    }

    public boolean checkAccountTryTrial() {
        boolean result = false;
        // accountmanager.click();
        // MyCommonAPIs.sleepi(10);
        waitReady();
        // String url = MyCommonAPIs.getCurrentUrl();
        // if (url.contains("#/accountManagement/purchaseHistory")) {
        // if(closeLockedWindow.exists()) {
        // closeLockedWindow.click();
        // }}
        closeLockedDialog();
        // subscriptions.click();
        // MyCommonAPIs.sleepi(10);
        waitReady();
        if (closedevicecredits.exists()) {
            closedevicecredits.click();
        }
        String url = MyCommonAPIs.getCurrentUrl();
        if (accountmanager.isDisplayed()) {
            accountmanager.click();
            MyCommonAPIs.sleepi(5);
            closeLockedDialog();
            subscriptions.click();
            MyCommonAPIs.sleepi(10);
            if (closedevicecredits.exists()) {
                closedevicecredits.click();
            }
            // waitElement(trytrialbutton);

        }
        logger.info("Check account try trial...");
        // trytrialbutton.click();
        // MyCommonAPIs.sleepi(10);
        // clickBoxLastButton();
        // MyCommonAPIs.sleepi(10);
        // clickBoxLastButton();
        //
        // MyCommonAPIs.sleepsync();

        if (currentsubscription.exists()) {
            if (getText(currentsubscription).equals("Insight Premium Trial Subscription")) {
                result = true;
            }
        } else if (currentsubscriptionpremium.exists()) {
            if (getText(currentsubscriptionpremium).contains("Insight Premium Trial")) {
                result = true;
            }

        }
        return result;
    }

    public boolean checkAccountEmail(String email) {
        boolean result = false;
        accountmanager.click();
        MyCommonAPIs.sleepi(5);
        closeLockedDialog();
        subscriptions.click();
        waitReady();
        waitElement(subDiv);
        logger.info("Check email in subscriptions page...");
        if (accountemail.exists()) {
            if (accountemail.getText().contains(email)) {
                logger.info("Account emmail:" + accountemail.getText());
                result = true;
            }
        }
        return result;
    }

    public boolean checkSubscriptions() {
        boolean result = false;
        accountmanager.click();
        MyCommonAPIs.sleepi(5);
        closeLockedDialog();
        subscriptions.click();
        waitElement(currentsubscriptionpremium);
        logger.info("Check subscriptions...");
        if (currentsubscriptionpremium.exists() && currentsubscriptionpremium.getText().equals("Insight Premium Trial")
                || currentsubscriptionpremium.getText().equals("Insight Premium")) {
            if (endon.exists()) {
                result = true;
            }
        }
        return result;
    }

    public boolean PurchaseOrderHistoryScreen() {
        boolean result = false;
        accountmanager.click();
        // MyCommonAPIs.sleepi(5);
        // purchaseorderhistory.click();
        MyCommonAPIs.sleepi(5);
        purchaseorderhistory.click();
        MyCommonAPIs.sleepi(5);
        waitElement(iconsearch);
        logger.info("Check purchage order history...");
        if (iconsearch.exists()) {
            result = true;
        }
        return result;
    }

    public boolean PurchaseOrderHistorySubscriptionScreen() {
        boolean result = false;
        accountmanager.click();
        MyCommonAPIs.sleepi(5);
         purchaseorderhistory.click();
         MyCommonAPIs.sleepi(5);
        waitElement(iconsearch);
//         iconsearch.click();

        expandinsigtdivcredits.click();
        expandcaptiveportal.click();
        expandprovpn.click();
        logger.info("Click on all subscriptions in purchase history.");
        MyCommonAPIs.sleepi(5);
        if (expiredate.exists() && activatedate.exists() && subscriptionkeytext.exists()) {
            result = true;
        }

        return result;
    }

    public boolean ManagerPurchaseOrderHistoryScreen() {
        boolean result = false;
        accountmanager.click();
        // MyCommonAPIs.sleepi(5);
        // purchaseorderhistory.click();
        waitElement(iconsearch);
        logger.info("Check purchage order history...");
        if (!purchaseorderhistory.exists()) {
            result = true;
        }
        return result;
    }

    public boolean SearchProVpn() {
        boolean result = false;
        accountmanager.click();
        MyCommonAPIs.sleepi(5);
        // purchaseorderhistory.click();
        // waitElement(iconsearch);
        iconsearch.click();
        inputtextiniconsearch.click();
        inputtextiniconsearch.sendKeys("Pro VPN");
        iconsearch.click();
        logger.info("Check VPN subscriptions within given filters...");
        if (prouservpnlicense.exists() && !instantcaptiveportal.exists() && !prouserinsightlicense.exists()) {
            result = true;
        }
        return result;
    }

    public boolean CancelSearch() {
        boolean result = false;
        accountmanager.click();
        MyCommonAPIs.sleepi(5);
         purchaseorderhistory.click();
         waitElement(iconsearch);
        iconsearch.click();
        inputtextiniconsearch.click();
        inputtextiniconsearch.sendKeys("Pro VPN");
        canceliconsearch.click();
        logger.info("Cancel the search pop up.");
        if (!inputtextiniconsearch.exists()) {
            result = true;
        }
        return result;
    }

    public boolean ExpandCollapseSubscription() {
        boolean result = false;

        accountmanager.click();
        MyCommonAPIs.sleepi(5);
        purchaseorderhistory.click();
        MyCommonAPIs.sleepi(5);
         purchaseorderhistory.click();
         waitElement(iconsearch);
        iconsearch.click();

        expandinsigtdivcredits.click();
        MyCommonAPIs.sleepi(3);
        expandcaptiveportal.click();
        MyCommonAPIs.sleepi(3);
        expandprovpn.click();
        MyCommonAPIs.sleepi(3);
        logger.info("Check expand for all subscriptions in purchase history.");
        if (subscriptionkeytext.exists() || subscriptionkeytext.exists()) {
            result = true;
            System.out.println("result is true");
        }

        MyCommonAPIs.sleepi(3);
        // collapseinsigtdivcredits.click();
        // MyCommonAPIs.sleepi(3);
        // collapseprovpn.click();
        // MyCommonAPIs.sleepi(3);
        // collapsecaptiveportal.click();
        logger.info("Check collapse for all subscriptions in purchase history.");

        return result;
    }

    public boolean SearchText() {
        boolean result1 = false;
        boolean result2 = false;
        boolean result = false;

        accountmanager.click();
        MyCommonAPIs.sleepi(5);
         purchaseorderhistory.click();
        waitElement(iconsearch);
        iconsearch.click();
        inputtextiniconsearch.click();
        inputtextiniconsearch.sendKeys("Pro VPN");
        iconsearch.click();
        MyCommonAPIs.sleepi(5);
        logger.info("Check VPN subscriptions within given filters...");
        if (prouservpnlicense.exists() && !instantcaptiveportal.exists() && !prouserinsightlicense.exists()) {
            result1 = true;
        }
        iconsearch.click();
        inputtextiniconsearch.click();
        inputtextiniconsearch.clear();
        inputtextiniconsearch.sendKeys("Captive Portal");
        iconsearch.click();
        MyCommonAPIs.sleepi(5);
        if (!prouservpnlicense.exists() && instantcaptiveportal.exists() && !prouserinsightlicense.exists()) {
            result2 = true;
        }

        if ((result1 == true) && (result2 == true)) {
            result = true;
        }
        return result;
    }

    public boolean CheckDefaultTimeFilter() {
        boolean result = false;
        accountmanager.click();
         MyCommonAPIs.sleepi(5);
         purchaseorderhistory.click();
         MyCommonAPIs.sleepi(5);
        waitElement(defaultfilter);
        logger.info("check default Time filter is Last Year...");
        if (defaultfilter.exists()) {
            result = true;
        }
        return result;
    }

    public void LastQuarterTimeFilter() {
        accountmanager.click();
         MyCommonAPIs.sleepi(5);
         purchaseorderhistory.click();
        waitElement(defaultfilter);
        logger.info("Select Time filter is 90days...");
        lastquarterfilter.click();
    }

    public boolean CheckDefaultCategoryFilter() {
        boolean result = false;
        accountmanager.click();
         MyCommonAPIs.sleepi(5);
         purchaseorderhistory.click();
         MyCommonAPIs.sleepi(5);
        waitElement(defaultcategoryfilter);
        logger.info("check default Category filter as All...");
        if (defaultcategoryfilter.exists()) {
            result = true;
        }
        return result;
    }

    public boolean CheckAllFiltersPremium() {
        boolean result = false;
        accountmanager.click();
        // MyCommonAPIs.sleepi(5);
        // purchaseorderhistory.click();
        waitElement(defaultcategoryfilter);
        logger.info("check all Category of filters present...");
        defaultcategoryfilter.click();
        if (allcategoryfilter.exists() && insightvpnfilter.exists() && devicesupportcontracts.exists() && insightsubscription.exists()
                && insightcaptivefilter.exists() && insightpremiumsubscription.exists()) {
            result = true;
        }
        return result;
    }

    public boolean CheckAllFiltersPro() {
        boolean result1 = false;
        boolean result2 = false;
        boolean result3 = false;
        boolean result4 = false;
        boolean result5 = false;
        boolean result = false;
        accountmanager.click();
         MyCommonAPIs.sleepi(5);
         purchaseorderhistory.click();
         MyCommonAPIs.sleepi(5);
        waitElement(defaultcategoryfilter);
        logger.info("check all Category of filters present...");
        defaultcategoryfilter.click();
        if (provpnsubscription.exists() && devicesupportcontracts.exists()) {
            result1 = true;
            logger.info("result1 is pass");
        }
        if (allcategoryfilter.exists() && insightsubscription.exists()) {
            result2 = true;
            logger.info("result2 is pass");
        }
        if (insightvpnfilter.exists() && insightcaptivefilter.exists()) {
            result3 = true;
            logger.info("result3 is pass");
        }
        if (insightpremiumsubscription.exists() && instantcaptivefilter.exists()) {
            result4 = true;
            logger.info("result4 is pass");
        }
        if (insightprofilter.exists() && provpnsubscription.exists()) {
            result5 = true;
            logger.info("result5 is pass");
        }
        if ((result1 == true) && (result2 == true) && (result3 == true) && (result4 == true) && (result5 == true)) {
            result = true;
        }
        return result;
    }

    public boolean ProVPNSubscriptionCategoryFilter() {
        boolean result = false;
        accountmanager.click();
         MyCommonAPIs.sleepi(5);
         purchaseorderhistory.click();
         MyCommonAPIs.sleepi(5);
        waitElement(defaultcategoryfilter);
        logger.info("check available provpn credits..");
        defaultcategoryfilter.click();
        provpnsubscription.click();
        collapse.click();
        creditoption.click();
        if (availableprovpncredits.exists()) {
            result = true;
        }
        return result;
    }

    public boolean InsightProSubscriptionCategory() {
        boolean result = false;
        accountmanager.click();
         MyCommonAPIs.sleepi(5);
         purchaseorderhistory.click();
        waitElement(defaultcategoryfilter);
        logger.info("check available insight pro credits..");
        defaultcategoryfilter.click();
        if (insightprosubscription.exists()) {
            insightprosubscription.click();
        }
        if (insightprosubscription1.exists()) {
            insightprosubscription1.click();
        }
        collapse.click();
        creditoption.click();
        if (availableinsightprocredit.exists()) {
            result = true;
        }
        return result;
    }

    public boolean ZeroScreenPurchaseHistory() {
        boolean result = false;
        accountmanager.click();
        // MyCommonAPIs.sleepi(5);
        // upgradebutton.click();
        purchaseorderhistory.click();
        waitElement(defaultcategoryfilter);
        expandinsigtdivcredits.click();
        logger.info("check available provpn credits..");

        if (nopurchasemessage.exists()) {
            result = true;
        }
        return result;
    }

    public boolean FilterBasisOnCategory() {
        boolean result1 = false;
        boolean result2 = false;
        boolean result3 = false;
        boolean result = false;
        accountmanager.click();
        MyCommonAPIs.sleepi(5);
        purchaseorderhistory.click();
        MyCommonAPIs.sleepi(5);
        waitElement(defaultcategoryfilter);
        logger.info("check filters basis on category.");
        defaultcategoryfilter.click();
        provpnsubscription.click();
        defaultcategoryfilter.click();
        MyCommonAPIs.sleepi(7);
        if (prouservpnlicense.exists()) {
            result1 = true;
            logger.info("vpn exits");
        }
        MyCommonAPIs.sleepi(5);
        defaultcategoryfilter.click();
        instantcaptivefilter.click();
        defaultcategoryfilter.click();
        MyCommonAPIs.sleepi(7);
        if (instantcaptiveportal.exists()) {
            result2 = true;
            logger.info("ICP exits");
        }
        MyCommonAPIs.sleepi(5);
        defaultcategoryfilter.click();
        provpnsubscription.click();
        defaultcategoryfilter.click();
        MyCommonAPIs.sleepi(7);
        if (prouservpnlicense.exists()) {
            result3 = true;
            logger.info("Pro exits");
        }
        // && (result3 == true)
        if ((result1 == true) && (result2 == true) && (result3 == true)) {
            result = true;
        }
        return result;
    }

    public boolean FilterBasisOnTime() {
        boolean result = false;
        boolean result1 = false;
        boolean result2 = false;
        boolean result3 = false;
        accountmanager.click();
        MyCommonAPIs.sleepi(5);
        purchaseorderhistory.click();
        MyCommonAPIs.sleepi(5);
        waitElement(defaultcategoryfilter);
        logger.info("check filters basis on time.");
        defaultfilter.click();
        lastquarterfilter.click();
        MyCommonAPIs.sleepi(5);
        if (activatedate.exists()) {
            result1 = true;
        }
        defaultfilter.click();
        lastyearfilter.click();
        MyCommonAPIs.sleepi(5);
        if (activatedate.exists()) {
            result2 = true;
        }
        defaultfilter.click();
        MyCommonAPIs.sleepi(5);
        if (activatedate.exists()) {
            result3 = true;
        }
        if ((result1 == true) && (result2 == true) && (result3 == true)) {
            result = true;
        }
        return result;
    }

    public boolean DeviceSupportContractsCategoryFilter() {
        boolean result = false;
        accountmanager.click();
        purchaseorderhistory.click();
        MyCommonAPIs.sleepi(5);
        waitElement(defaultcategoryfilter);
        logger.info("check available provpn credits..");
        defaultcategoryfilter.click();
        devicesupportcontracts.click();
        MyCommonAPIs.sleepi(5);
        if (nopurchasemessage1.exists()) {
            result = true;
        }
        return result;
    }

    public boolean QuantityalongsideCategory() {
        boolean result = false;
        accountmanager.click();
        MyCommonAPIs.sleepi(5);
        purchaseorderhistory.click();
        waitElement(defaultcategoryfilter);
        logger.info("check Quantity alongside Category...");
        MyCommonAPIs.sleepi(5);
        if (prouserinsightlicensecount.exists() && prouservpnlicensecount.exists() && instantcaptiveportalcount.exists()) {
            result = true;
        }
        return result;
    }

    public boolean DeviceCreditPurchaseHistory() {
        boolean result = false;
        accountmanager.click();
        purchaseorderhistory.click();
        MyCommonAPIs.sleepi(5);
        waitElement(defaultcategoryfilter);
        logger.info("check Quantity alongside Category...");
        System.out.println("1");
        MyCommonAPIs.sleepi(5);
        // prouserinsightlicense.exists()
        if (prouserinsightlicensecount.exists() || premiumuserinsightlicense.exists()) {
            result = true;
        }
        return result;
    }

    public boolean ReleventPurchasesList() {
        boolean result = false;
        accountmanager.click();
        MyCommonAPIs.sleepi(5);
        purchaseorderhistory.click();
        MyCommonAPIs.sleepi(5);
        waitElement(defaultcategoryfilter);
        logger.info("check Quantity alongside Category...");
        MyCommonAPIs.sleepi(5);
        if (!prouserinsightlicensecount.exists() && !prouservpnlicensecount.exists() && !instantcaptiveportal.exists()) {
            result = true;
        }
        return result;
    }

    public boolean CaptiveportalSubscriptionPurchase() {
        boolean result = false;
        accountmanager.click();
         MyCommonAPIs.sleepi(5);
         purchaseorderhistory.click();
         MyCommonAPIs.sleepi(5);
        waitElement(defaultcategoryfilter);
        icpcollapse.click();
        MyCommonAPIs.sleepi(10);
        logger.info("check Captive Portal Subscription present under purchase history...");
        if (instantcaptiveportalcount.exists() && expiredate.exists() && activatedate.exists() && subscriptionkeytext.exists()) {
            result = true;
        }
        return result;
    }

    public boolean checkLoginSuccessful() {
        boolean result = false;
        if (hamburgermenu.exists()) {
            result = true;
            logger.info("Login successful...");
            hamburgermenu.click();
        } else if (hamburgermenunew.exists()) {
            result = true;
            logger.info("Login successful...");
            hamburgermenunew.click();
        }
        return result;
    }

    public boolean checkDefaultNotificationsStatus() {
        boolean result = false;
        accountmanager.click();
        MyCommonAPIs.sleepi(10);
        logger.info("Check default notifications status...");
        if (managenotificationsnew.exists()) {
            result = true;
            logger.info("Webportal version greater than 5.7.");
        }
        return result;
    }

    public void disableOrEnableEmailAndPushAlertsNotifications(String option) {
        accountmanager.click();
        MyCommonAPIs.sleepi(10);
        logger.info(option + " email notifications...");
        if (managenotifications.exists()) {
            managenotifications.click();
            MyCommonAPIs.sleepi(3);
            emailnotifications.click();
            MyCommonAPIs.sleepi(3);
            System.out.println("456");
            if (emailaddress.getAttribute("class").equals("OnOffSetting visible") && option.equals("disable")) {
                enableemailnotifications.click();
                savenoticications.click();
            } else if (!emailaddress.getAttribute("class").equals("OnOffSetting visible") && option.equals("enable")) {
                enableemailnotifications.click();
                savenoticications.click();
            }
        } else if (managenotificationsnew.exists()) {
            managenotificationsnew.click();
            MyCommonAPIs.sleepi(5);
            // accounttab.click();
            MyCommonAPIs.sleepi(5);
            if (option.equals("enable")) {
                if ($x(emailnotificationsnew).getAttribute("value").equals("") || $x(emailnotificationsnew).getAttribute("value").equals("0")) {
                    $x(emailnotificationsnew + "/../span").click();
                    MyCommonAPIs.sleepi(3);
                }
                if ($x(pushalertsnotificationsnew).getAttribute("value").equals("")
                        || $x(pushalertsnotificationsnew).getAttribute("value").equals("0")) {
                    $x(pushalertsnotificationsnew + "/../span").click();
                    MyCommonAPIs.sleepi(3);
                }
                savenoticicationsnew.click();
            } else if (option.equals("disable")) {
                if ($x(emailnotificationsnew).getAttribute("value").equals("1")) {
                    $x(emailnotificationsnew + "/../span").click();
                    MyCommonAPIs.sleepi(3);
                }
                if ($x(pushalertsnotificationsnew).getAttribute("value").equals("1")) {
                    $x(pushalertsnotificationsnew + "/../span").click();
                    MyCommonAPIs.sleepi(3);
                }
                savenoticicationsnew.click();
            }
        }
        waitReady();
    }

    public boolean checkEmailNotifications() {
        boolean result = false;
        accountmanager.click();
        MyCommonAPIs.sleepi(10);
        logger.info("Check email notifications...");
        if (managenotifications.exists()) {
            System.out.println("enterd 1st");
            managenotifications.click();
            MyCommonAPIs.sleepi(5);
            emailSwitch.click();
            MyCommonAPIs.sleepi(1);
            pushSwitch.click();
            MyCommonAPIs.sleepi(1);
            if ($x(emailnotificationsnew).getAttribute("value").equals("1") && $x(pushalertsnotificationsnew).getAttribute("value").equals("1")) {
                result = true;
                logger.info("Notifications enabled.");
            }
        } else if (managenotificationsnew.exists()) {
            System.out.println("enterd 2nd");
            managenotificationsnew.click();
            MyCommonAPIs.sleepi(5);
            emailSwitch.click();
            MyCommonAPIs.sleepi(1);
            pushSwitch.click();
            MyCommonAPIs.sleepi(1);
            if ($x(emailnotificationsnew).getAttribute("value").equals("1") && $x(pushalertsnotificationsnew).getAttribute("value").equals("1")) {
                result = true;
                logger.info("Notifications enabled.");
            }
        }
        return result;
    }

    public void disableAndEnableAutoRenewal(String option) {
        accountmanager.click();
        MyCommonAPIs.sleepi(10);
        closeLockedDialog();
        subscriptions.click();
        waitReady();
        if (billingandpaymentNew.exists()) {
            billingandpaymentNew.click();
        } else if (billingandpayment.exists()) {
            billingandpayment.click();
        }
        waitElement(editsubscription);
        MyCommonAPIs.sleepi(10);
        editsubscription.click();
        MyCommonAPIs.sleepi(5);
        if (option.equals("disable") && !disableautorenewal.isSelected()) {
            disableautorenewal.selectRadio("6");
        } else if (option.equals("enable") && disableautorenewal.isSelected()) {
            enableautorenewal.selectRadio("3");
        }
        $x("//button[text()='Save']").click();
        waitReady();
        MyCommonAPIs.sleepi(5);
        gotosubscriptionnow.click();
        waitReady();
    }

    public boolean checkDevicesBilling(String num, String option, String country) {
        boolean result = false;
        accountmanager.click();
        MyCommonAPIs.sleepi(10);
        closeLockedDialog();
        subscriptions.click();
        MyCommonAPIs.sleepi(10);
        if (closedevicecredits.exists()) {
            closedevicecredits.click();
        }
        waitReady();
        double amount = 0;
        if (country.equals("US")) {
            if (option.equals("monthly")) {
                amount = 0.99 * Double.valueOf(num);
            } else if (option.equals("yearly")) {
                amount = 9.99 * Double.valueOf(num);
            } else if (option.equals("basic")) {
                amount = 4.99 * Double.valueOf(num);
            }
        } else if (country.equals("AU")) {
            if (option.equals("monthly")) {
                amount = 1.5 * Double.valueOf(num);
            } else if (option.equals("yearly")) {
                amount = 14.5 * Double.valueOf(num);
            } else if (option.equals("basic")) {
                amount = 7.25 * Double.valueOf(num);
            }
        } else if (country.equals("CA")) {
            if (option.equals("monthly")) {
                amount = 1.3 * Double.valueOf(num);
            } else if (option.equals("yearly")) {
                amount = 12.75 * Double.valueOf(num);
            } else if (option.equals("basic")) {
                amount = 6.5 * Double.valueOf(num);
            }
        } else if (country.equals("JP")) {
            if (option.equals("monthly")) {
                amount = 120 * Double.valueOf(num);
            } else if (option.equals("yearly")) {
                amount = 1125 * Double.valueOf(num);
            } else if (option.equals("basic")) {
                amount = 560 * Double.valueOf(num);
            }
        } else if (country.equals("UK")) {
            if (option.equals("monthly")) {
                amount = 0.89 * Double.valueOf(num);
            } else if (option.equals("yearly")) {
                amount = 8.95 * Double.valueOf(num);
            } else if (option.equals("basic")) {
                amount = 4.47 * Double.valueOf(num);
            }
        }
        if (currencydevsubscriptionNew.exists()) {
            if (getText(currencydevsubscriptionNew).indexOf(String.valueOf(amount)) != -1) {
                result = true;
                logger.info("Devices billing is correct.");
            }
        } else if (currencydevsubscription.exists()) {
            if (getText(currencydevsubscription).indexOf(String.valueOf(amount)) != -1) {
                result = true;
                logger.info("Devices billing is correct.");
            }
        }
        return result;
    }

    public void clickPlaceOrderSupportDevice() {
        if (placeyourordernew.exists()) {
            placeyourordernew.click();
        } else if (placeYourOrder.exists()) {
            placeYourOrder.click();
        } else {
            placeyourorder.click();
        }
    }

    public void clickPlaceOrder() {
        if (placeyourordernew.exists()) {
            placeyourordernew.click();
        }

        int count = 0;
        while (count < 10) {
            MyCommonAPIs.sleepi(50);
            // Selenide.switchTo().frame("redirectTo3ds1Frame");
            if (gotosubscriptionnow.exists()) {
                gotosubscriptionnow.click();
                System.out.println("before emulater go to dash board");
                break;
            } else if(new InsightServicesPageElement().captivePortalServices.exists()){
                break;
                }
                else {
                System.out.println("in emulator ");
                MyCommonAPIs.sleepi(20);
                Selenide.switchTo().frame("redirectTo3ds1Frame");
                MyCommonAPIs.sleepi(10);
                if (Submitpayment.isDisplayed()) {
                    System.out.println("Submit button exist");
                    MyCommonAPIs.sleepi(5);
                    try {
                        Submitpayment.click();
                    } catch (Throwable e) {
                        System.out.println("Skip here");
                    }
                    // Submitpayment.waitUntil(Condition.appear, 10000, 1000);
                    System.out.println("submit button clicked ");
                }
                System.out.println("exit first loop");
            }
            System.out.println("exit sceond loop");
            MyCommonAPIs.sleepi(30);
            Selenide.switchTo().defaultContent();
            System.out.println("wait for check gotosubscriptionnow");
            System.out.println("check gotosubscriptionnow");
            if (gotosubscriptionnow.exists()) {
                System.out.println("after emulater go to dash board");
                gotosubscriptionnow.click();
                break;
            } else if (placeyourordernew.exists()) {
                placeyourordernew.click();
            } else if (placeyourorder.exists()) {
                placeyourorder.click();
            } else if (placeYourOrder.exists()) {
                placeYourOrder.click();
            }
            count += 1;
        }
        MyCommonAPIs.sleepi(20);
        waitReady();
    }

    public boolean checkVATNumberIsExist(Map<String, String> map) {
        boolean result = false;
        waitElement(upgrade);
        upgrade.click();
        waitElement(checkoutbutton);
        selectPremiumTime(map);

        setDevNum(map);
        // setDevNumAndInputBillingInfo(map);
        // savenum.click();
        MyCommonAPIs.sleepi(10);
        // inputCardInfo(map);
        // checkAutoRenew(map);
        if (!$x("//div[@hidden]/input[@id='orderSumary-vatregis-input']").exists()) {
            billingvatnum.setValue(map.get("VAT Registration Number"));
            sleepi(1);
            $x("//button[@id='add-vat']").click();
            waitReady();
            result = true;
            logger.info("VAT number input is existed.");
        }
        // paymentsumbit.click();
        // MyCommonAPIs.sleepi(30);
        // checkAutoRenew(map);
        // MyCommonAPIs.sleepi(10);
        MyCommonAPIs.sleepi(10);
        click(Termsandcondition, true);
        MyCommonAPIs.sleepi(5);
        clickPlaceOrder();
        // $x("//button[text()='Cancel']").click();
        // MyCommonAPIs.sleepi(3);
        // $x("//a[text()='Cancel']").click();
        return result;
    }

    public boolean checkCurrencySymbolAndCode(String symbol, String code, String country) {
        boolean result = false;
        accountmanager.click();
        MyCommonAPIs.sleepi(10);
        closeLockedDialog();
        subscriptions.click();
        waitReady();
        if (billingandpaymentNew.exists()) {
            billingandpaymentNew.click();
        } else if (billingandpayment.exists()) {
            billingandpayment.click();
        }
        waitElement(editsubscription);
        MyCommonAPIs.sleepi(10);
        editbillinginfomation.click();
        // $x("(//button[text()='Cancel'])[2]").click();
        if ((getText($x("(//span[text()='Billing Amount:'])[1]/..")).indexOf(symbol) != -1) && (billingzip.getValue().indexOf(code) != -1)
                && (billingcountry.getValue().indexOf(country) != -1)) {
            result = true;
            logger.info("Currency symbol and code displayed correct.");
        }
        MyCommonAPIs.sleepi(5);
        $x("//a[@class='btn cancelBtn']").click();
        return result;
    }

    public boolean checkAutoRenewal() {
        boolean result = false;
        accountmanager.click();
        MyCommonAPIs.sleepi(10);
        closeLockedDialog();
        subscriptions.click();
        waitReady();
        if (billingandpaymentNew.exists()) {
            billingandpaymentNew.click();
        } else if (billingandpayment.exists()) {
            billingandpayment.click();
        }
        waitElement(editsubscription);
        MyCommonAPIs.sleepi(10);
        if ($x("//h5[text()='Auto Renewal: No']").exists()) {
            // editsubscription.click();
            // if ($x("//span[text()='Expiry Date: ']").exists()) {
            result = true;
            logger.info("Auto renewal no.");
            // }
            // $x("//button[text()='Save']").click();
            // MyCommonAPIs.sleepi(8);
        }
        $x("//a[text()='Cancel']").click();
        waitReady();
        return result;
    }

    public void setDevNum(Map<String, String> map) {
        MyCommonAPIs.sleepi(10);
        waitElement(devicenumplus);
        if (map.containsKey("Number of Device Credits")) {
            int count = 1;
            while (count < Integer.valueOf(map.get("Number of Device Credits"))) {
                devicenumplus.click();
                count += 1;
            }
        }
        savenum.click();
        MyCommonAPIs.sleepi(10);
    }

    public void setDevNumAndInputBillingInfo(Map<String, String> map) {
        setDevNum(map);
        // Billingdropdown.click();
        MyCommonAPIs.sleepi(3);
        billingfirstname.clear();
        billingfirstname.sendKeys(map.get("First Name"));
        billinglastname.clear();
        billinglastname.sendKeys(map.get("Last Name"));
        // billingemail.sendKeys(map.get("Email"));
        // if(map.containsKey("Company Name")) {
        // billingcompanyname.sendKeys(map.get("Company Name"));
        // }
        billingstreetaddress.clear();
        billingstreetaddress.sendKeys(map.get("Street Address"));
        billingcity.clear();
        billingcity.sendKeys(map.get("City"));
        billingzip.clear();
        billingzip.sendKeys(map.get("Zip"));
        billingcountry.selectOption(map.get("Country"));
        if (billingstate1.isDisplayed()) {
            billingstate1.selectOption(map.get("State"));
        } else {
            billingstate2.sendKeys(map.get("State"));
        }
        MyCommonAPIs.sleepi(3);
        billingicon.click();

    }

    public void setDevNumAndInputBillingInfo1(Map<String, String> map) {

        Billingdropdown.click();
        MyCommonAPIs.sleepi(3);
        billingfirstname.clear();
        billingfirstname.sendKeys(map.get("First Name"));
        billinglastname.clear();
        billinglastname.sendKeys(map.get("Last Name"));
        // billingemail.sendKeys(map.get("Email"));
        // if(map.containsKey("Company Name")) {
        // billingcompanyname.sendKeys(map.get("Company Name"));
        // }
        billingstreetaddress.clear();
        billingstreetaddress.sendKeys(map.get("Street Address"));
        billingcity.clear();
        billingcity.sendKeys(map.get("City"));
        billingzip.clear();
        billingzip.sendKeys(map.get("Zip"));
        billingcountry.selectOption(map.get("Country"));
        if (billingstate1.isDisplayed()) {
            billingstate1.selectOption(map.get("State"));
        } else {
            billingstate2.sendKeys(map.get("State"));
        }
        MyCommonAPIs.sleepi(3);
        billingicon.click();

    }

    public boolean setDevNumAndInputBillingInfowithInvalidZipcode(Map<String, String> map) {
        boolean result = false;
        setDevNum(map);
        Billingdropdown.click();
        MyCommonAPIs.sleepi(3);
        billingfirstname.clear();
        billingfirstname.sendKeys(map.get("First Name"));
        billinglastname.clear();
        billinglastname.sendKeys(map.get("Last Name"));
        // billingemail.sendKeys(map.get("Email"));
        // if(map.containsKey("Company Name")) {
        // billingcompanyname.sendKeys(map.get("Company Name"));
        // }
        billingstreetaddress.clear();
        billingstreetaddress.sendKeys(map.get("Street Address"));
        billingcity.clear();
        billingcity.sendKeys(map.get("City"));
        billingzip.clear();
        billingzip.sendKeys(map.get("Zip"));
        MyCommonAPIs.waitElement(billingzipErrorPath);
        String ErrorMessage = billingzipErrorPath.getText();
        System.out.println("This is Error Message" + ErrorMessage);
        System.out.println("This is Error Message by Value" + billingzipErrorPath.getValue());
        if (ErrorMessage.equals("Zipcode is invalid.")) {
            result = true;
            System.out.println("Zipcode validation Succesfull");

        }
        return result;
    }

    public void inputCardInfo(Map<String, String> map) {
        MyCommonAPIs.sleepi(3);
        if (paymentcardnumber.isDisplayed()) {
            System.out.println("paymentcardnumber is existed");
        } else {
            Carddropdown.click();
        }
        paymentcardnumber.clear();
        System.out.println(map.get("Card Number"));
        System.out.println(map.get("CVV Number"));
        paymentcardnumber.sendKeys(map.get("Card Number"));
        MyCommonAPIs.sleepi(2);
        paymentcvvnumber.clear();
        paymentcvvnumber.sendKeys(map.get("CVV Number"));
        MyCommonAPIs.sleepi(2);
        paymentexpirationmonth.selectOption(map.get("Expiration Month"));
        paymentexpirationyear.selectOption(map.get("Expiration Year"));
        MyCommonAPIs.sleepi(2);
    }

    public void buyDeviceCredits(Map<String, String> map) {
        waitElement(upgrade);
        accountmanager.click();
        MyCommonAPIs.sleepi(10);
        String url = MyCommonAPIs.getCurrentUrl();
        if (url.contains("#/accountManagement/purchaseHistory")) {
            if (closeLockedWindow.exists()) {
                closeLockedWindow.click();
            }
        }
        closeLockedDialog();
        subscriptions.click();
        MyCommonAPIs.sleepi(10);
        waitElement(buydevicecredits);
        buydevicecredits.click();
        setDevNumAndInputBillingInfo(map);
        // savenum.click();
        MyCommonAPIs.sleepi(10);
        inputCardInfo(map);
        // checkAutoRenew(map);
        // paymentsumbit.click();
        MyCommonAPIs.sleepi(10);
        // checkAutoRenew(map);
        click(Termsandcondition, true);
        MyCommonAPIs.sleepi(10);
        clickPlaceOrder();
    }

    public void cancelSubscription() {

        new MyCommonAPIs().open(URLParam.hrefPaymentSubscription, true);

        // accountmanager.click();
        // MyCommonAPIs.sleepi(10);
        // closeLockedDialog();
        // subscriptions.click();

        waitReady();
        if (cancelSubscriptionNew.exists()) {
            cancelSubscriptionNew.click();
        } else if (cancelsubscription.exists()) {
            cancelsubscription.click();
        } else if (cancelBasicSub.exists()) {
            cancelBasicSub.click();
        }
        MyCommonAPIs.sleepi(3);
        $x("//p[text()='Are you sure that you want to cancel your subscription plan?']//following::button[contains(text(),'OK')][1]").click();
        waitReady();
        MyCommonAPIs.sleepi(10);
        if (closedevicecredits.exists()) {
            closedevicecredits.click();
        }
    }

    public void changePlanToPremium(Map<String, String> map) {
        waitReady();
        if (changesubscription.exists()) {
            changesubscription.click();
        } else if (changeSubNew.exists()) {
            changeSubNew.click();
        } else if (subscriptionupgrade.exists()) {
            subscriptionupgrade.click();
        }
        MyCommonAPIs.sleepi(10);
        waitElement(checkoutbutton);
        if (map.get("Country") == "US" || map.get("Country") == "Canada") {
            logger.info("entered US region");
            if (map.containsKey("Subscription Time")) {
                if (map.get("Subscription Time").equals("Monthly")) {
                    monthlyUS.selectRadio(monthlyUS.getAttribute("value"));
                } else if (map.get("Subscription Time").equals("Yearly")) {
                    yearlyUS.selectRadio(yearlyUS.getAttribute("value"));
                }
            }
        } else {
            logger.info("entered other region");
            if (map.containsKey("Subscription Time")) {
                if (map.get("Subscription Time").equals("Monthly")) {
                    monthly.selectRadio(monthly.getAttribute("value"));
                } else if (map.get("Subscription Time").equals("Yearly")) {
                    yearly.selectRadio(yearly.getAttribute("value"));
                }
            }
        }
        checkoutbutton.click();
        MyCommonAPIs.sleepi(20);
        // setDevNum(map);
        if (savenum.isDisplayed()) {
            savenum.click();
            MyCommonAPIs.sleepi(10);
        }
        waitReady();
        // setDevNumAndInputBillingInfo1(map);
        // inputCardInfo(map);

        click(Termsandcondition, true);
        MyCommonAPIs.sleepi(10);
        clickPlaceOrder();

    }

    public boolean checkMoreNeeded(String number) {
        boolean result = false;
        accountmanager.click();
        MyCommonAPIs.sleepi(10);
        closeLockedDialog();
        subscriptions.click();
        MyCommonAPIs.sleepi(10);
        if (closedevicecredits.exists()) {
            closedevicecredits.click();
        }
        String num = String.valueOf(Integer.valueOf(number) - 2);
        MyCommonAPIs.sleepi(5);
        if (moreneeded.exists()) {
            if (getText(moreneeded).contains(num + " More Needed")) {
                result = true;
                logger.info("Found devices more needed string.");
            }
        }
        return result;
    }

    public boolean inputPormoCode(Map<String, String> map) {
        boolean result = false;
        if (map.containsKey("Promo Code")) {
            addpromocode.click();
            MyCommonAPIs.sleepi(2);
            enterpromocode.setValue("test123");
            // donebutton.click();
            MyCommonAPIs.sleepi(5);

            ElementsCollection checkpoint = $$x("//div[@hidden]//a");
            if (checkpoint.size() == 1) {
                enterpromocode.setValue(map.get("Promo Code"));
                // donebutton.click();
                conformpromocode.click();
                MyCommonAPIs.waitReady();
                MyCommonAPIs.sleepi(3);
                if (totalprice.getText().contains("$0.00 USD")) {
                    result = true;
                    logger.info("Input promo code success.");
                }
            }
        }
        return result;
    }

    public boolean checkPromoCode(Map<String, String> map) {
        boolean result = false;
        new MyCommonAPIs().open(URLParam.hrefPaymentSubscription, true);
        logger.info("Start upgrade subsctiption...");
        if (ChangebuttonMoToYr.exists()) {
            ChangebuttonMoToYr.click();
        }
        if (Changebutton.exists()) {
            Changebutton.click();
        } else {
            waitElement(upgrade);
            upgrade.click();
        }
        // MyCommonAPIs.sleepi(30);
        waitElement(checkoutbutton);
        if (map.get("Country") == "US" || map.get("Country") == "Canada") {
            logger.info("entered US region");
            if (map.containsKey("Subscription Time")) {
                if (map.get("Subscription Time").equals("Monthly")) {
                    monthlyUS.selectRadio(monthlyUS.getAttribute("value"));
                } else if (map.get("Subscription Time").equals("Yearly")) {
                    yearlyUS.selectRadio(yearlyUS.getAttribute("value"));
                }
            }
        } else {
            logger.info("entered other region");
            if (map.containsKey("Subscription Time")) {
                if (map.get("Subscription Time").equals("Monthly")) {
                    monthly.selectRadio(monthly.getAttribute("value"));
                } else if (map.get("Subscription Time").equals("Yearly")) {
                    yearly.selectRadio(yearly.getAttribute("value"));
                }
            }

        }
        checkoutbutton.click();
        setDevNumAndInputBillingInfo(map);
        if (map.containsKey("VAT Registration Number")) {
            billingvatnum.setValue(map.get("VAT Registration Number"));
        }
        // savenum.click();
        MyCommonAPIs.sleepi(30);
        inputCardInfo(map);
        // checkAutoRenew(map);
        // paymentsumbit.click();
        // MyCommonAPIs.sleepi(30);
        // checkAutoRenew(map);
        MyCommonAPIs.sleepi(10);
        result = inputPormoCode(map);
        MyCommonAPIs.sleepi(5);
        click(Termsandcondition, true);
        MyCommonAPIs.sleepi(5);
        clickPlaceOrder();
        logger.info("Finish upgrade subsctiption...");
        return result;
    }

    // public boolean checkPromoCode100percent(Map<String, String> map, int Device) {
    // boolean result = false;
    // for (int i=0; i<=Device ; i++)
    // {
    // new MyCommonAPIs().open(URLParam.hrefPaymentSubscription, true);
    // logger.info("Start upgrade subsctiption...");
    // if (ChangebuttonMoToYr.exists() & i == 0) {
    // ChangebuttonMoToYr.click();
    // }
    // if (Changebutton.exists()) {
    // Changebutton.click();
    // } else {
    // waitElement(upgrade);
    // upgrade.click();
    // }
    // // MyCommonAPIs.sleepi(30);
    // if (i == 0)
    // waitElement(checkoutbutton);
    // if (map.get("Country") == "US" || map.get("Country") == "Canada") {
    // logger.info("entered US region");
    // if (map.containsKey("Subscription Time")) {
    // if (map.get("Subscription Time").equals("Monthly")) {
    // monthlyUS.selectRadio(monthlyUS.getAttribute("value"));
    // } else if (map.get("Subscription Time").equals("Yearly")) {
    // yearlyUS.selectRadio(yearlyUS.getAttribute("value"));
    // }
    // }
    // } else {
    // logger.info("entered other region");
    // if (map.containsKey("Subscription Time")) {
    // if (map.get("Subscription Time").equals("Monthly")) {
    // monthly.selectRadio(monthly.getAttribute("value"));
    // } else if (map.get("Subscription Time").equals("Yearly")) {
    // yearly.selectRadio(yearly.getAttribute("value"));
    // }
    // }
    //
    // }
    // if (i == 0)
    // checkoutbutton.click();
    // setDevNum(map);
    // // setDevNumAndInputBillingInfo(map);
    // // if (map.containsKey("VAT Registration Number")) {
    // // billingvatnum.setValue(map.get("VAT Registration Number"));
    // // }
    // // savenum.click();
    // MyCommonAPIs.sleepi(5);
    // // inputCardInfo(map);
    // // checkAutoRenew(map);
    // // paymentsumbit.click();
    // // MyCommonAPIs.sleepi(30);
    // // checkAutoRenew(map);
    // // MyCommonAPIs.sleepi(10);
    // result = inputPormoCode(map);
    // MyCommonAPIs.sleepi(5);
    // click(Termsandcondition, true);
    // MyCommonAPIs.sleepi(5);
    // clickPlaceOrder();
    // MyCommonAPIs.waitElement(DevieCreditsCount);
    // String Dcc = DevieCreditsCount.getText();
    // String Acc = AvailableCreditsCount.getText();
    // System.out.println(Dcc+Acc);
    // System.out.println(Dcc);
    // String count = Integer.toString(i+1);
    // System.out.println(ActivationTime.isDisplayed());
    // System.out.println(ExpiryTime.isDisplayed());
    // System.out.println(Acc.contentEquals(count));
    // System.out.println(Dcc.contentEquals(count));
    //
    // if (Dcc.contentEquals(count) & Acc.contentEquals(count))
    // {
    // if (ActivationTime.isDisplayed() & ExpiryTime.isDisplayed())
    // {
    // logger.info("Able to Add Subscription and Device is Visible on UI");
    // result = true;
    // }
    // else
    // logger.info("Able to Add Subscription But Divice is Not Visible on UI");
    // result = false;
    // }
    // else
    // logger.info("Divices are Not Visible on UI");
    //
    // logger.info("Finish upgrade subsctiption...");
    // }
    // return result;
    // }
    public boolean VerifyForPremiumTrial() {
        // TODO Auto-generated method stub
        boolean result = false;
        new MyCommonAPIs().open(URLParam.hrefPaymentSubscription, true);
        logger.info("Checking for Premium Trial on Subscription Page...");
        MyCommonAPIs.waitElement(PremiumTrialTextField);
        String status = PremiumTrialTextField.getText();
        if (status.equals("Insight Premium Trial")) {
            result = true;
            logger.info("This Account is of Premium Trial");
        } else
            logger.info("This is not an Premium Account");
        return result;

    }

    public boolean checkPromoCode100percent(Map<String, String> map) {
        boolean result = false;
        new MyCommonAPIs().open(URLParam.hrefPaymentSubscription, true);
        logger.info("Start upgrade subsctiption...");
        if (ChangebuttonMoToYr.exists()) {
            ChangebuttonMoToYr.click();
        }
        if (Changebutton.exists()) {
            Changebutton.click();
        } else {
            waitElement(upgrade);
            upgrade.click();
        }
        // MyCommonAPIs.sleepi(30);
        waitElement(checkoutbutton);
        if (map.get("Country") == "US" || map.get("Country") == "Canada") {
            logger.info("entered US region");
            if (map.containsKey("Subscription Time")) {
                if (map.get("Subscription Time").equals("Monthly")) {
                    monthlyUS.selectRadio(monthlyUS.getAttribute("value"));
                } else if (map.get("Subscription Time").equals("Yearly")) {
                    yearlyUS.selectRadio(yearlyUS.getAttribute("value"));
                }
            }
        } else {
            logger.info("entered other region");
            if (map.containsKey("Subscription Time")) {
                if (map.get("Subscription Time").equals("Monthly")) {
                    monthly.selectRadio(monthly.getAttribute("value"));
                } else if (map.get("Subscription Time").equals("Yearly")) {
                    yearly.selectRadio(yearly.getAttribute("value"));
                }
            }

        }
        checkoutbutton.click();
        setDevNum(map);
        // setDevNumAndInputBillingInfo(map);
        // if (map.containsKey("VAT Registration Number")) {
        // billingvatnum.setValue(map.get("VAT Registration Number"));
        // }
        // savenum.click();
        // inputCardInfo(map);
        // checkAutoRenew(map);
        // paymentsumbit.click();
        // MyCommonAPIs.sleepi(30);
        // checkAutoRenew(map);
        MyCommonAPIs.sleepi(10);
        result = inputPormoCode(map);
        MyCommonAPIs.sleepi(5);
        click(Termsandcondition, true);
        MyCommonAPIs.sleepi(5);
        clickPlaceOrder();
        MyCommonAPIs.waitElement(DevieCreditsCount);
        String Dcc = DevieCreditsCount.getText();
        String Acc = AvailableCreditsCount.getText();
        System.out.println(Dcc + Acc);
        System.out.println(Dcc);
        String count = "1";
        System.out.println(ActivationTime.isDisplayed());
        System.out.println(ExpiryTime.isDisplayed());
        System.out.println(Acc.contentEquals(count));
        System.out.println(Dcc.contentEquals(count));
        if (Dcc.contentEquals(count) & Acc.contentEquals(count)) {
            if (ActivationTime.isDisplayed() & ExpiryTime.isDisplayed()) {
                logger.info("Able to Add Subscription and Device is Visible on UI");
                result = true;
            } else {
                logger.info("Able to Add Subscription But Divice is Not Visible on UI");
                result = false;
            }

        } else
            logger.info("Divices are Not Visible on UI");

        return result;
    }

    public boolean CheckforZipCodeValidation(Map<String, String> map) {
        boolean result = false;
        new MyCommonAPIs().open(URLParam.hrefPaymentSubscription, true);
        logger.info("Start upgrade subsctiption...");
        if (ChangebuttonMoToYr.exists()) {
            ChangebuttonMoToYr.click();
        }
        if (Changebutton.exists()) {
            Changebutton.click();
        } else {
            waitElement(upgrade);
            upgrade.click();
        }
        // MyCommonAPIs.sleepi(30);
        waitElement(checkoutbutton);
        if (map.get("Country") == "US" || map.get("Country") == "Canada") {
            logger.info("entered US region");
            if (map.containsKey("Subscription Time")) {
                if (map.get("Subscription Time").equals("Monthly")) {
                    monthlyUS.selectRadio(monthlyUS.getAttribute("value"));
                } else if (map.get("Subscription Time").equals("Yearly")) {
                    yearlyUS.selectRadio(yearlyUS.getAttribute("value"));
                }
            }
        } else {
            logger.info("entered other region");
            if (map.containsKey("Subscription Time")) {
                if (map.get("Subscription Time").equals("Monthly")) {
                    monthly.selectRadio(monthly.getAttribute("value"));
                } else if (map.get("Subscription Time").equals("Yearly")) {
                    yearly.selectRadio(yearly.getAttribute("value"));
                }
            }

        }
        checkoutbutton.click();
        result = setDevNumAndInputBillingInfowithInvalidZipcode(map);
        return result;
    }

    public boolean BuyMultipleDevices(Map<String, String> map, int DeviceCount) {
        boolean result = false;
        for (int i = 1; i <= DeviceCount; i++) {
            MyCommonAPIs.waitElement(BuyDeviceCreditsButton);
            System.out.println(BuyDeviceCreditsButton.isDisplayed());
            if (BuyDeviceCreditsButton.isDisplayed()) {
                BuyDeviceCreditsButton.click();
                MyCommonAPIs.sleepi(5);
                setDevNum(map);
                ;
                MyCommonAPIs.sleepi(10);
                result = inputPormoCode(map);
                MyCommonAPIs.sleepi(5);
                click(Termsandcondition, true);
                MyCommonAPIs.sleepi(5);
                clickPlaceOrder();
                MyCommonAPIs.waitElement(DevieCreditsCount);
                String Dcc = DevieCreditsCount.getText();
                String Acc = AvailableCreditsCount.getText();
                System.out.println(Dcc + Acc);
                System.out.println(Dcc);
                String count = Integer.toString(i);
                System.out.println(ActivationTime.isDisplayed());
                System.out.println(ExpiryTime.isDisplayed());
                System.out.println(Acc.contentEquals(count));
                System.out.println(Dcc.contentEquals(count));

                if (Dcc.contentEquals(count) & Acc.contentEquals(count)) {
                    if (ActivationTime.isDisplayed() & ExpiryTime.isDisplayed()) {
                        logger.info("Able to Add Subscription and Device is Visible on UI");
                        result = true;
                    } else {
                        logger.info("Able to Add Subscription But Divice is Not Visible on UI");
                        result = false;
                    }

                }
            }
        }

        return result;
    }

    public void checkAutoRenew(Map<String, String> map) {
        if (map.containsKey("AutoRenew")) {
            if ($x(paymentautorenewstatus).exists()) {
                if (map.get("AutoRenew").contains("Off") && $x(paymentautorenewstatus).isSelected()) {
                    $x(paymentautorenewstatus).click();
                } else if (map.get("AutoRenew").contains("On") && !$x(paymentautorenewstatus).isSelected()) {
                    $x(paymentautorenewstatus).click();
                }
            }
        } else {
            if ($x(paymentautorenewstatus).exists()) {
                if (!$x(paymentautorenewstatus).isSelected()) {
                    $x(paymentautorenewstatus).click();
                }
            }
        }
    }

    public void selectPremiumTime(Map<String, String> map) {
        if (map.get("Country") == "US" || map.get("Country") == "Canada") {
            logger.info("entered US region");
            MyCommonAPIs.sleepi(5);
            if (map.containsKey("Subscription Time")) {
                if (map.get("Subscription Time").equals("Monthly")) {
                    MyCommonAPIs.sleepi(5);
                    monthlyUS.selectRadio(monthlyUS.getAttribute("value"));
                } else if (map.get("Subscription Time").equals("Yearly")) {
                    yearlyUS.selectRadio(yearlyUS.getAttribute("value"));
                }
            }
        } else {
            logger.info("entered other region");
            MyCommonAPIs.sleepi(5);
            if (map.containsKey("Subscription Time")) {
                MyCommonAPIs.sleepi(5);
                if (map.get("Subscription Time").equals("Monthly")) {
                    monthly.selectRadio(monthly.getAttribute("value"));
                } else if (map.get("Subscription Time").equals("Yearly")) {
                    yearly.selectRadio(yearly.getAttribute("value"));
                }
            }

        }

        checkoutbutton.click();
    }

    public void inputPaymentPage(Map<String, String> map) {

        logger.info("Start upgrade subsctiption...");
        if (ChangebuttonMoToYr.exists()) {
            ChangebuttonMoToYr.click();
        }
        if (Changebutton.exists()) {
            Changebutton.click();
        } else {
            waitElement(upgrade);
            upgrade.click();
        }
        // MyCommonAPIs.sleepi(30);
        waitElement(checkoutbutton);

        selectPremiumTime(map);
        setDevNum(map);

    }

    public void inputPaymentPageInfo(Map<String, String> map) {
        logger.info("Start upgrade subsctiption...");
        if (ChangebuttonMoToYr.exists()) {
            ChangebuttonMoToYr.click();
        }
        if (Changebutton.exists()) {
            Changebutton.click();
        } else {
            waitElement(upgrade);
            upgrade.click();
        }
        // MyCommonAPIs.sleepi(30);
        waitElement(checkoutbutton);

        selectPremiumTime(map);

        setDevNumAndInputBillingInfo(map);
        if (map.containsKey("VAT Registration Number")) {
            billingvatnum.setValue(map.get("VAT Registration Number"));
        }
        // savenum.click();
        MyCommonAPIs.sleepi(10);
        inputCardInfo(map);
        // checkAutoRenew(map);
        // paymentsumbit.click();
        MyCommonAPIs.sleepi(10);
        // checkAutoRenew(map);
        click(Termsandcondition, true);
        MyCommonAPIs.sleepi(5);
        clickPlaceOrder();
    }

    public void upgradeSubscription(Map<String, String> map) {
        new MyCommonAPIs().open(URLParam.hrefPaymentSubscription, true);
        inputPaymentPageInfo(map);
        if (map.containsKey("Promo Code")) {
            addpromocode.click();
            MyCommonAPIs.sleepi(2);
            enterpromocode.setValue(map.get("Promo Code"));
            donebutton.click();
            MyCommonAPIs.sleepi(5);
        }

        logger.info("Finish upgrade subsctiption...");
    }

    public void BuydeviceCredit(Map<String, String> map) {
        logger.info("Start upgrade subsctiption...");
        if (Changebutton.exists()) {
            Changebutton.click();
        }

        else if (upgrade.exists()) {
            upgrade.click();
        }
        MyCommonAPIs.sleepi(10);
        if (map.get("Country") == "US" || map.get("Country") == "Canada") {
            logger.info("entered US region");
            MyCommonAPIs.sleepi(10);
            if (map.containsKey("Subscription Time")) {
                if (map.get("Subscription Time").equals("Monthly")) {
                    monthlyUS.selectRadio(monthlyUS.getAttribute("value"));
                } else if (map.get("Subscription Time").equals("Yearly")) {
                    yearlyUS.selectRadio(yearlyUS.getAttribute("value"));
                }
            }
        } else {
            logger.info("entered other region");
            MyCommonAPIs.sleepi(10);
            if (map.containsKey("Subscription Time")) {
                if (map.get("Subscription Time").equals("Monthly")) {
                    monthly.selectRadio(monthly.getAttribute("value"));
                } else if (map.get("Subscription Time").equals("Yearly")) {
                    yearly.selectRadio(yearly.getAttribute("value"));
                }
            }

        }

        checkoutbutton.click();
        setDevNum(map);
        click(Termsandcondition, true);
        MyCommonAPIs.sleepi(5);
        clickPlaceOrder();
        logger.info("Finish upgrade subsctiption...");
    }

    public void changeMoToYr(Map<String, String> map) {
        logger.info("Start upgrade subsctiption...");
        if (ChangebuttonMoToYr.exists()) {
            ChangebuttonMoToYr.click();
        }
        MyCommonAPIs.sleepi(10);
        if (map.get("Country") == "US" || map.get("Country") == "Canada") {
            logger.info("entered US region");
            if (map.containsKey("Subscription Time")) {
                if (map.get("Subscription Time").equals("Monthly")) {
                    monthlyUS.selectRadio(monthlyUS.getAttribute("value"));
                } else if (map.get("Subscription Time").equals("Yearly")) {
                    yearlyUS.selectRadio(yearlyUS.getAttribute("value"));
                }
            }
        } else {
            logger.info("entered other region");
            if (map.containsKey("Subscription Time")) {
                if (map.get("Subscription Time").equals("Monthly")) {
                    monthly.selectRadio(monthly.getAttribute("value"));
                } else if (map.get("Subscription Time").equals("Yearly")) {
                    yearly.selectRadio(yearly.getAttribute("value"));
                }
            }

        }

        checkoutbutton.click();
        setDevNum(map);
        click(Termsandcondition, true);
        MyCommonAPIs.sleepi(5);
        clickPlaceOrder();
        logger.info("Finish upgrade subsctiption...");
    }

    public boolean checkCancelSubscription() {
        boolean result = false;
        String sub = "";
        if (currentSubscriptionNew.exists()) {
            sub = getText(currentSubscriptionNew);
        } else if (currentsubscription.exists()) {
            sub = getText(currentsubscription);
        }
        if (sub.equals("Insight Premium Trial")) {
            result = true;
            logger.info("Cancel subscription successful.");
        }
        return result;
    }

    public boolean checkSubscriptionScreenForBasic() {
        boolean result = false;
        logger.info("Check the basic Subscription");
        if (currentsubscription.exists()) {
            if (getText(currentsubscription).equals("Insight Basic Subscription")) {
                result = true;
                logger.info("Subsctiption screen for basic displayed correct.");
            }
        }
        return result;
    }

    public boolean checkSubscriptionScreenForPremium() {
        boolean result = false;
        logger.info("Check the premium Subscription");
        if (currentsubscriptionpremium.exists()) {

            if (getText(currentsubscriptionpremium).equals("Insight Premium")) {
                result = true;
                logger.info("Subsctiption screen for premium displayed correct.");
            }
        }
        return result;

        // String SubscriptionScreenForPremium = "Insight Premium" ;
        //
        // if (currentsubscription.exists())
        // {
        // result = true;
        // logger.info("Subsctiption screen for premium displayed correct.");
        // }
        // return result;
    }

    public boolean checkMonthlySubscriptionScreen(String devicenum) {
        boolean result = false;
        if (((getText(currencydevsubscriptionNew).indexOf("/Mo") != -1) && checkSubscriptionScreenForPremium()
                && (getText(currencysubscriptionNew).indexOf("/Mo") != -1) && deviceredsubscriptionNew.getText().equals(devicenum))) {
            result = true;
            logger.info("Monthly subsctiption screen displayed correct.");
        }
        return result;
    }

    public boolean checkMonthlyCurency(String currency, String Amount) {
        boolean result = false;
        String BasicCurrency = getText(currencysubscriptionNew);

        if (BasicCurrency.contains(currency) && BasicCurrency.contains(Amount)) {
            logger.info("Displayed correct currency and amount");
            result = true;
        }

        return result;
    }

    public boolean checkSubscriptionScreen(String devicenum) {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if (currencydevsubscriptionNew.exists()) {
            logger.info("Enterd New loop");
            String currencyDevSubscription = getText(currencydevsubscriptionNew);
            String currencySubscription = getText(currencysubscriptionNew);
            boolean flag = currencyDevSubscription.indexOf("/Yr") != -1 && currencySubscription.indexOf("/Yr") != -1
                    && getText(deviceredsubscriptionNew).equals(devicenum);
            if ((checkSubscriptionScreenForPremium() == true && flag) || (checkSubscriptionScreenForBasic() && flag)) {
                logger.info("Subsctiption screen displayed correct.");
                result = true;
            }
        } else if (currencydevsubscription.exists()) {
            logger.info("Enterd old loop");
            String currencyDevSubscription = getText(currencydevsubscription);
            String currencySubscription = getText(currencysubscription);
            boolean flag = currencyDevSubscription.indexOf("/Yr") != -1 && currencySubscription.indexOf("/Yr") != -1
                    && getText(deviceredsubscription).equals(devicenum);
            if ((checkSubscriptionScreenForPremium() && flag) || (checkSubscriptionScreenForBasic() && flag)) {
                logger.info("Subsctiption screen displayed correct.");
                result = true;
            }
        }
        return result;
    }

    public boolean checkBasicAnuallyCurrency(String Currency) {
        boolean result = false;
        String currencySubscription = getText(currencysubscription);

        String firstThreeChars = currencySubscription.substring(0, 3);

        if (firstThreeChars.equals(Currency)) {
            logger.info("Displayed currency is right");
            logger.info(String.format("the currency dispalayed is %s", firstThreeChars));
            result = true;
        }

        return result;

    }

    public boolean checkPremiumAnuallyCurrency(String Currency) {
        boolean result = false;
        String currencysubscription = getText(currencysubscriptionNew);
        String currencysubscriptionBilling = getText(currencydevsubscriptionNew);

        String firstThreeChars = currencysubscription.substring(0, 3);
        String ThreeChars = currencysubscription.substring(0, 3);

        if (firstThreeChars.equals(Currency) && ThreeChars.equals(Currency)) {
            logger.info("Displayed currency is right");
            logger.info(String.format("the currency dispalayed is %s", firstThreeChars));
            result = true;
        }

        return result;

    }

    public boolean checkICPCurrency(String Currency) {
        boolean result = false;
        hamburgermenunew.click();
        MyCommonAPIs.sleepi(5);
        accountmanager.click();
        waitElement(defaultcategoryfilter);
        MyCommonAPIs.sleepi(5);
        icpcollapse.click();
        MyCommonAPIs.sleepi(5);
        String ICPcurrencysubscription = getText(icpcurrency);
        logger.info(ICPcurrencysubscription);

        if (ICPcurrencysubscription.contains(Currency)) {
            logger.info("Displayed currency is right");
            result = true;
        }

        return result;
    }

    public boolean checkVPNCurrency(String Currency) {
        boolean result = false;
        hamburgermenunew.click();
        MyCommonAPIs.sleepi(5);
        accountmanager.click();
        waitElement(defaultcategoryfilter);
        MyCommonAPIs.sleepi(5);
        collapseprovpn.click();
        MyCommonAPIs.sleepi(5);
        String VPNcurrencysubscription = getText(VPNcurrency);
        logger.info(VPNcurrencysubscription);

        if (VPNcurrencysubscription.contains(Currency)) {
            logger.info("Displayed currency is right");
            result = true;
        }

        return result;
    }

    public boolean checkMultipackCurrency(String Currency) {
        boolean result = false;
        hamburgermenunew.click();
        MyCommonAPIs.sleepi(5);
        accountmanager.click();
        waitElement(defaultcategoryfilter);
        MyCommonAPIs.sleepi(5);
        collapseprovpn.click();
        MyCommonAPIs.sleepi(5);
        String VPNcurrencysubscription = getText(Multipackcurrency);
        logger.info(VPNcurrencysubscription);

        if (VPNcurrencysubscription.contains(Currency)) {
            logger.info("Displayed currency is right");
            result = true;
        }

        return result;
    }

    public String checkUnsupportedCountry() {
        String result = "";
        // waitElement(upgrade);
        // upgrade.click();
        // MyCommonAPIs.sleepi(20);
        // if ($x("//div[@class=\"modal fade countryRedirectError in\"]").exists() && okbutton.exists()) {
        // logger.info("This is a account of unsupported country.");
        // result = "Unsupported";
        // okbutton.click();
        // } else if ($x("//div[@class=\"modal fade LearnMore subs in\"]").exists() && checkoutbutton.exists()) {
        // logger.info("This is a account of supported country.");
        // result = "Supported";
        // $x("//h4[text()=\"Upgrade Subscription\"]/../button").click();
        // }
        accountmanager.click();
        MyCommonAPIs.sleepi(5);
        closeLockedDialog();
        subscriptions.click();
        MyCommonAPIs.sleepi(20);
        // waitElement(Changebutton);
        if (Changebutton.exists()) {
            Changebutton.click();
        } else
            logger.info("There is no subscription Added");

        MyCommonAPIs.sleepi(20);

        if ($x("//div[@class=\"modal fade LearnMore subs\"]").exists() && checkoutbutton.exists()) {
            logger.info("This is a account of supported country.");
            result = "Supported";
        } else if ($x("(//h3[text()='N/A'])[1]").exists() || $x("(//*[@id=\"myModal\"]/div/div/div[1]/h4)[2]").exists()) {
            logger.info("This is a account of unsupported country.");
            result = "Unsupported";
            if (okbutton.isDisplayed()) {
                okbutton.click();
            }

        }
        return result;
    }

    public boolean checkVersion() {
        boolean result1 = false;
        boolean result2 = false;
        about.click();
        logger.info("Check webportal version...");
        MyCommonAPIs.sleepi(3);
        String webportalver = webportalversion.getText();
        String cloudver = cloudversion.getText();
        if ((webportalver.indexOf("Web Portal Version : ") != -1) && (cloudver.indexOf("Cloud Version :") != -1)) {
            webportalver = webportalver.trim();
            cloudver = cloudver.trim();
            for (int i = 0; i < webportalver.length(); i++) {
                if ((webportalver.charAt(i) >= 48) && (webportalver.charAt(i) <= 57)) {
                    result1 = true;
                    logger.info("Webportal version:" + webportalver);
                    break;
                }
            }
            for (int i = 0; i < cloudver.length(); i++) {
                if ((cloudver.charAt(i) >= 48) && (cloudver.charAt(i) <= 57)) {
                    result2 = true;
                    logger.info("Cloud version:" + cloudver);
                    break;
                }
            }
        }
        closebutton.click();
        return result1 && result2;
    }

    public void changePassowrd(String oldPassword, String newPassword) {
        updateprofile.click();
        MyCommonAPIs.sleepi(10);
        if (cancelbutton.exists()) {
            cancelbutton.click();
        } else {
            refresh();
            MyCommonAPIs.sleepi(10);
            if (cancelbutton.exists()) {
                cancelbutton.click();
            }
        }
        loginsettings.click();
        MyCommonAPIs.sleepi(5);
        logger.info("Change password...");
        changepassword.click();
        MyCommonAPIs.sleepi(3);
        oldpassword.sendKeys(oldPassword);
        newpassword.sendKeys(newPassword);
        confirmnewpassword.sendKeys(newPassword);
        MyCommonAPIs.sleepi(3);
        submitbutton.click();
        logger.info("Change password successful.");
        WebCheck.checkHrefIcon(URLParam.hrefDevices);
    }

    public void createOwnerAccount(Map<String, String> map) {
        // owneremail.click();
        checkemailtitle.click();
        MyCommonAPIs.sleepi(3);
        // Selenide.switchTo().frame("emailframe");
        open(getowneraccounturl.getAttribute("href"));
        waitElement(ownerconfirmemail);
        ownerconfirmemail.sendKeys(map.get("Confirm Email"));
        ownerpassword.sendKeys(map.get("Password"));
        ownerconfirmpwd.sendKeys(map.get("Confirm Password"));
        ownercountrycode.selectOption(map.get("Country"));
        ownerphonenum.sendKeys(map.get("Phone Number"));
        ownerterms.click();
        MyCommonAPIs.sleepi(5);
        ownersignup.click();
        logger.info("Create owner account successful.");
        waitReady();
    }

    public void createManagerAccount(Map<String, String> map) {
        // invitemanager.click();
        checkemailtitle.click();
        MyCommonAPIs.sleepi(3);
        if (getowneraccounturlnew.exists()) {
            open(getowneraccounturlnew.getAttribute("href"));
        } else {
            open(getowneraccounturl.getAttribute("href"));
        }
        System.out.println("clear cookies");
        Selenide.clearBrowserCookies();
        System.out.println("clear browse storage");
        Selenide.clearBrowserLocalStorage();
        System.out.println("refresh");
        Selenide.refresh();
        waitElement(ownerconfirmemail);
        ownerconfirmemail.sendKeys(map.get("Confirm Email"));
        ownerpassword.sendKeys(map.get("Password"));
        ownerconfirmpwd.sendKeys(map.get("Confirm Password"));
        ownercountrycode.selectOption(map.get("Country"));
        ownerphonenum.sendKeys(map.get("Phone Number"));
        ownerterms.click();
        MyCommonAPIs.sleepi(5);
        ownersignup.click();
        logger.info("Create manager account successful.");
        waitReady();
    }

    public void CreateProAccount(Map<String, String> map) {
        waitElement(ownerconfirmemail);
        ownerconfirmemail.sendKeys(map.get("Confirm Email"));
        ownerpassword.sendKeys(map.get("Password"));
        ownerconfirmpwd.sendKeys(map.get("Confirm Password"));
        ownercountrycode.selectOption(map.get("Country"));
        ownerphonenum.sendKeys(map.get("Phone Number"));
        MyCommonAPIs.sleepi(1);
        createpronextbutton.click();
        MyCommonAPIs.sleepi(1);
        if (policyText1.exists()) {
            policyText1.click();
        }

        MyCommonAPIs.sleepi(1);
        if (policyText2.exists()) {
            policyText2.click();
        }

        MyCommonAPIs.sleepi(1);
        if (proaccountContinue.exists()) {
            proaccountContinue.click();
        }

    }

    public boolean checkInputBusinessInfoError(Map<String, String> map) {
        boolean result = false;
        agreeterm.click();
        MyCommonAPIs.sleepi(3);
        clickBusinessInfoPageButton();
        if (getText(businessInfoError).contains("Please enter business name.")) {
            businessname.setValue(map.get("Business Name"));
            MyCommonAPIs.sleepi(1);
            clickBusinessInfoPageButton();
            if (getText(businessInfoError).contains("Please enter business address.")) {
                businessprimaryaddress.setValue(map.get("Primary Address of Business"));
                MyCommonAPIs.sleepi(1);
                clickBusinessInfoPageButton();
                if (getText(businessInfoError).contains("Please enter city.")) {
                    businesscity.setValue(map.get("City"));
                    MyCommonAPIs.sleepi(1);
                    clickBusinessInfoPageButton();
                    if (getText(businessInfoError).contains("Please enter state.")) {
                        businessstate.setValue(map.get("State"));
                        MyCommonAPIs.sleepi(1);
                        clickBusinessInfoPageButton();
                        if (getText(businessInfoError).contains("Zip Code required")) {
                            businesszip.setValue(map.get("Zip Code"));
                            MyCommonAPIs.sleepi(1);
                            clickBusinessInfoPageButton();
                            if (getText(businessInfoError).contains("You must specify a country")) {
                                businesscountry.selectOption(map.get("Country"));
                                MyCommonAPIs.sleepi(1);
                                clickBusinessInfoPageButton();
                                if (getText(businessInfoError).contains("Please enter Phone No.")) {
                                    result = true;
                                    businessphone.setValue(map.get("Business Phone Number"));
                                    MyCommonAPIs.sleepi(1);
                                    clickBusinessInfoPageButton();
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    public void clickBusinessInfoPageButton() {
        MyCommonAPIs.sleepi(1);
        if (businessDone.exists()) {
            businessDone.click();
        } else {
            MyCommonAPIs.sleepi(1);
            businesssignup.click();
        }
    }

    public void inputBusinessInfo(Map<String, String> map) {
        MyCommonAPIs.sleepi(5);
        waitElement(selectmanagerservice);
        selectmanagerservice.click();
        MyCommonAPIs.sleepi(1);
        businessname.setValue(map.get("Business Name"));
        MyCommonAPIs.sleepi(1);
        businessprimaryaddress.setValue(map.get("Primary Address of Business"));
        MyCommonAPIs.sleepi(1);
        businesscity.setValue(map.get("City"));
        MyCommonAPIs.sleepi(1);
        businessstate.setValue(map.get("State"));
        MyCommonAPIs.sleepi(1);
        businesszip.setValue(map.get("Zip Code"));
        MyCommonAPIs.sleepi(1);
        businesscountry.selectOption(map.get("Country"));
        MyCommonAPIs.sleepi(1);
        businessphone.setValue(map.get("Business Phone Number"));
        MyCommonAPIs.sleepi(5);
        // agreeterm.click();
        MyCommonAPIs.sleepi(3);
    }

    public void inputBusinessInfoDirectPurchase(Map<String, String> map) {
        waitElement(selectmanagerservice);
        selectmanagerservice.click();
        businessname.setValue(map.get("Business Name"));
        businessprimaryaddress.setValue(map.get("Primary Address of Business"));
        businesscity.setValue(map.get("City"));
        businessstate.setValue(map.get("State"));
        businesszip.setValue(map.get("Zip Code"));
        businesscountry.selectOption(map.get("Country"));
        businessphone.setValue(map.get("Business Phone Number"));
        MyCommonAPIs.sleepi(5);
        agreetermDirectPurchase.click();
        MyCommonAPIs.sleepi(3);
    }

    public void PremiumTrailAndFinishSignin(Map<String, String> map) {
        if (StartTrialNow.isDisplayed()) {
            MyCommonAPIs.sleepi(5);
            StartTrialNow.click();
        }
        inputBusinessInfo(map);
        clickBusinessInfoPageButton();
        logger.info("Sign up success.");
        waitReady();
        MyCommonAPIs.sleepi(20);
    }

    public void HardBundleSignin(Map<String, String> map) {
        if (HardBundleStartTrialNow.isDisplayed()) {
            MyCommonAPIs.sleepi(5);
            HardBundleStartTrialNow.click();
        }
        inputBusinessInfo(map);
        clickBusinessInfoPageButton();
        logger.info("Sign up success.");
        waitReady();
        MyCommonAPIs.sleepsync();
    }

    public void inputLicenceAndFinishSignin(Map<String, String> map) {
        MyCommonAPIs.sleepi(15);
//        waitElement(inputproaccountkey);
        if (inputproaccountkey.isDisplayed()) {
            inputproaccountkey.setValue(map.get("Licence Key"));
            System.out.println(map.get("Licence Key"));
            logger.info("Input licence:" + map.get("Licence Key"));
            MyCommonAPIs.sleepi(5);
            inputproaccountkeynext.click();
        }
        inputBusinessInfo(map);
        clickBusinessInfoPageButton();
        logger.info("Sign up success.");
        waitReady();
        MyCommonAPIs.sleepsync();
    }

    public void inputLicenceAndFinishUpgrade(Map<String, String> map) {
        inputproaccountkey.setValue(map.get("Licence Key"));
        logger.info("Input licence:" + map.get("Licence Key"));
        MyCommonAPIs.sleepi(5);
        inputproaccountkeynext.click();
        waitElement(selectmanagerservice);
        selectmanagerservice.click();
        businessname.setValue(map.get("Business Name"));
        businessprimaryaddress.setValue(map.get("Primary Address of Business"));
        businesscity.setValue(map.get("City"));
        businessstate.setValue(map.get("State"));
        businesszip.setValue(map.get("Zip Code"));
        businesscountry.selectOption(map.get("Country"));
        businessphone.setValue(map.get("Business Phone Number"));
        MyCommonAPIs.sleepi(5);
        agreeterm.click();
        businessupgrade.click();
        logger.info("Upgrade success.");
        waitReady();
        MyCommonAPIs.sleepsync();
    }

    public boolean checkProaccountInfPage(String licence) {
        boolean result = false;
        logger.info("Licence key:" + licence);
        inputproaccountkey.setValue(licence);
        MyCommonAPIs.sleepi(5);
        inputproaccountkeynext.click();
        waitElement(selectmanagerservice);
        if (selectmanagerservice.exists()) {
            result = true;
            logger.info("Business Information page was displayed.");
        }
        return result;
    }

    public String getRandomWord() {
        String word = "";
        for (int i = 0; i < 4; i++) {
            int c = 'a' + (int) (Math.random() * 26);
            word += String.valueOf((char) c);
        }
        return word;
    }

    public boolean checkEmailMessageByMailcatch(String mailname) {
        boolean result = false;
        String url = "http://www.mailcatch.com/en/temporary-inbox?box=" + mailname;
        open(url);
        if (owneremail.exists()) {
            result = true;
            logger.info("Received invite owner email.");
        } else if (trytrialemail.exists()) {
            result = true;
            logger.info("Received insight premium free trial email.");
        } else if (invitemanager.exists()) {
            result = true;
            logger.info("Received invite manager email.");
        }
        return result;
    }

    // Edited by vivek Added mail notification conditions
    public boolean checkEmailMessage(String mailname) {
        boolean result = false;
        logger.info("Check email address is:" + mailname);
        open("https://www.guerrillamail.com/inbox");
        String basicElement = "//span[@id='inbox-id']";
        $x(basicElement).click();
        MyCommonAPIs.sleepi(2);
        $x(basicElement + "/input").setValue(mailname);
        MyCommonAPIs.sleepi(2);
        $x(basicElement + "/button[1]").click();
        MyCommonAPIs.sleepsync();
        System.out.println("1111111.");
        System.out.println(checkemailtitle.getText());
        if (checkemailtitle.getText().contains("Invite owner email")) {
            result = true;
            logger.info("Received invite owner email.");
        } else if (checkemailtitle.getText().contains("You have enabled Insight Pro Monthly Usage Billing")) {
            result = true;
            logger.info("Received insight premium free trial email.");
        } else if (checkemailtitle.getText().contains("Insight Pro Monthly Usage Billing disabled starting next month")) {
            result = true;
            logger.info("Received insight premium free trial email.");
        } else if (checkemailtitle.getText().contains("NETGEAR Insight Premium Free Trial")) {
            result = true;
            logger.info("Received insight premium free trial email.");
        } else if (checkemailtitle.getText().contains("Invite manager email")) {
            result = true;
            logger.info("Invite manager email.");
        } else if (checkemailtitle.getText().contains("Verify your email address on MyNETGEAR")) {
            result = true;
            logger.info("Received verify email.");
        } else if (checkemailtitle.getText().contains("Voucher Manager Invitation Email.")) {
            result = true;
            logger.info("Received voucher manager invitation email.");
        } else if (checkemailtitle.getText().contains("Invite voucher manager email")) {
            result = true;
            logger.info("Received voucher manager invitation email.");
        } else if (checkemailtitle.getText().contains("Invite Secondary Admin Email")) {
            result = true;
            logger.info("Received secondary manager invitation email.");
        } else if (checkemailtitle.getText().contains("Device Online")) {
            result = true;
            logger.info("Received Device Online Notification email.");
        } else if (checkemailtitle.getText().contains("Device Reboot")) {
            result = true;
            logger.info("Received Device Reboot Notification email.");
        }
        return result;
    }

    public String getVpnTotalGroup() {
        String groupCount = "";
        accountmanager.click();
        waitReady();
        if (vpnservices.exists()) {
            vpnservices.click();
            waitReady();
            MyCommonAPIs.sleepi(10);
            groupCount = getText(vpntotalgroup);
        }
        return groupCount;
    }

    public boolean checkVpnServicesSubscription(String GroupNum, String UserCredits) {
        boolean result = false;
        accountmanager.click();
        waitReady();
        if (vpnservices.exists()) {
            vpnservices.click();
            MyCommonAPIs.sleepi(5);
            if (InstantVPN.exists()) {
                InstantVPN.click();
            }
            waitReady();
            MyCommonAPIs.sleepi(10);
            if (vpntotalgroup.exists()) {
                if (totalvpncredits.getText().equals(String.valueOf(1 + Integer.parseInt(GroupNum)))
                        && $x(String.format(availablecredits,
                                String.valueOf((1 + Integer.parseInt(GroupNum)) - Integer.parseInt(getText(vpntotalgroup))))).exists()
                        && vpnusercredits.getText().equals(String.valueOf(1 + (Integer.parseInt(UserCredits) * 30)))
                        && $x(String.format(availablecredits, String.valueOf(1 + (Integer.parseInt(UserCredits) * 30)))).exists()) {
                    result = true;
                    logger.info("Vpn services display correct.");
                }
            } else if (vpntotalgroupnew.exists()) {
                if (totalvpncredits.getText().equals(String.valueOf(1 + Integer.parseInt(GroupNum)))
                        && $x(String.format(availablecredits,
                                String.valueOf((1 + Integer.parseInt(GroupNum)) - Integer.parseInt(getText(vpntotalgroupnew))))).exists()
                        && vpnusercredits.getText().equals(String.valueOf(1 + (Integer.parseInt(UserCredits) * 30)))
                        && $x(String.format(availablecredits, String.valueOf(1 + (Integer.parseInt(UserCredits) * 30)))).exists()) {
                    result = true;
                    logger.info("Vpn services display correct.");
                }
            }
        }
        return result;
    }

    public boolean checkbusinessVpnServicesSubscription(String Subscriptiontype, String GroupNum, String UserCredits) {
        boolean result = false;
        accountmanager.click();
        waitReady();
        if (vpnservices.exists()) {
            vpnservices.click();
            MyCommonAPIs.sleepi(5);
            // if (InstantVPN.exists()) {
            // InstantVPN.click();
            // }
            waitReady();
            MyCommonAPIs.sleepi(10);
            if (Subscriptiontype.equals("Employee Home Site")) {
                if (Totalremotesite.exists()) {
                    if (Totalremotesite.getText().equals(String.valueOf(Integer.parseInt(GroupNum)))
                            && MaxxonnectedClients.getText().equals(String.valueOf((5)))) {
                        result = true;
                        logger.info("Business Vpn services display correct.");
                    }
                }
            }
            if (Subscriptiontype.equals("Micro Office - 9 User")) {
                if (Totalremotesite.exists()) {
                    if (Totalremotesite.getText().equals(String.valueOf(Integer.parseInt(GroupNum)))
                            && MaxxonnectedClients.getText().equals(String.valueOf((45)))) {
                        result = true;
                        logger.info("Business Vpn services display correct.");
                    }
                }
            }
            if (Subscriptiontype.equals("Micro Office - 15 User")) {
                if (Totalremotesite.exists()) {
                    if (Totalremotesite.getText().equals(String.valueOf(Integer.parseInt(GroupNum)))
                            && MaxxonnectedClients.getText().equals(String.valueOf((75)))) {
                        result = true;
                        logger.info("Business Vpn services display correct.");
                    }
                }
            }

            if (Subscriptiontype.equals("Small Office - 25 User")) {
                if (Totalremotesite.exists()) {
                    if (Totalremotesite.getText().equals(String.valueOf(Integer.parseInt(GroupNum)))
                            && MaxxonnectedClients.getText().equals(String.valueOf((125)))) {
                        result = true;
                        logger.info("Business Vpn services display correct.");
                    }
                }
            }
            if (Subscriptiontype.equals("Small Office - 50 User")) {
                if (Totalremotesite.exists()) {
                    if (Totalremotesite.getText().equals(String.valueOf(Integer.parseInt(GroupNum)))
                            && MaxxonnectedClients.getText().equals(String.valueOf((250)))) {
                        result = true;
                        logger.info("Business Vpn services display correct.");
                    }
                }
            }
        }
        return result;
    }

    public boolean checkDisplayCancelVpnServices() {
        boolean result = false;
        accountmanager.click();
        waitReady();
        if (vpnservices.exists()) {
            vpnservices.click();
            MyCommonAPIs.sleepi(5);
            if (InstantVPN.exists()) {
                InstantVPN.click();
            }
            waitReady();
            MyCommonAPIs.sleepi(10);
            if (!deletecurrentservices.getText().contains("No Data Available")) {
                deletecurrentservices.hover();
                MyCommonAPIs.sleep(3000);
                if (cancelservices.exists()) {
                    result = true;
                    cancelservices.waitUntil(Condition.visible, 60 * 1000).click();
                    MyCommonAPIs.sleep(8 * 1000);
                    cancelservicesyes.click();
                    logger.info("Cancel services success.");
                    waitReady();
                }
            }
        }
        return result;
    }

    public boolean checkCancelICPServicesWindwow() {
        boolean result = false;
        gotoIcpServicesPage();
        if (!deleteicpservices.getText().contains("No Data Available")) {
            deleteicpservices.hover();
            MyCommonAPIs.sleep(5000);
            cancelservices.waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleep(8 * 1000);
            if (cancelservicesyesnew.exists()) {
                if (getText($("div.in p.m-b-20")).contains("Canceling this service will prevent you from using Instant Captive Portal Services.")) {
                    result = true;
                    cancelservicesyesnew.click();
                    logger.info("Cancel services success.");
                }
            }
        }
        waitReady();
        return result;
    }

    public boolean checkIcpGracePeriod() {
        boolean result = false;
        gotoIcpServicesPage();
        if (gracePeriodWarning.exists()) {
            if (getText(gracePeriodWarning).contains(
                    "One or more of your Captive Portal Credits have expired. To continue using Captive Portal Credits, purchase Captive Portal Credits from Insight Services before 7days grace period ends on")
                    && gracePeriodWarningBuyBtn.exists()) {
                result = true;
            }
        }
        return result;
    }

    public void cancelICPServices() {
        gotoIcpServicesPage();
        if (!deleteicpservices.getText().contains("No Data Available")) {
            if ($$x(delicpservicesmany).size() > 1) {
                int serviceNum = $$x(delicpservicesmany).size();
                for (int i = 0; i < serviceNum; i++) {
                    deleteicpservices.hover();
                    MyCommonAPIs.sleep(5000);
                    cancelservices.waitUntil(Condition.visible, 60 * 1000).click();
                    MyCommonAPIs.sleep(8 * 1000);
                    cancelservicesyesnew.click();
                    waitReady();
                    refresh();
                    MyCommonAPIs.sleepi(10);
                }
                logger.info("Cancel services success.");
            } else {
                deleteicpservices.hover();
                MyCommonAPIs.sleep(5000);
                cancelservices.waitUntil(Condition.visible, 60 * 1000).click();
                MyCommonAPIs.sleep(8 * 1000);
                cancelservicesyesnew.click();
                logger.info("Cancel services success.");
            }
        }
        waitReady();
    }

    public boolean cancelVpnServices() {
        boolean result = false;
        accountmanager.click();
        waitReady();
        if (vpnservices.exists()) {
            vpnservices.click();
            waitReady();
            MyCommonAPIs.sleepi(5);
            if (InstantVPN.exists()) {
                InstantVPN.click();
            }
            MyCommonAPIs.sleepi(10);
            if (!deletecurrentservices.getText().contains("No Data Available")) {
                if ($$x(deletecurrentservicesmany).size() > 1) {
                    int serviceNum = $$x(deletecurrentservicesmany).size();
                    for (int i = 0; i < serviceNum; i++) {
                        deletecurrentservices.hover();
                        MyCommonAPIs.sleep(5000);
                        cancelservices.waitUntil(Condition.visible, 60 * 1000).click();
                        MyCommonAPIs.sleep(8 * 1000);
                        if (getText(cancelvpnpopuptext).contains(
                                "Cancelling this service will prevent you from using some VPN functions. Are you sure you want to cancel?")) {
                            result = true;
                        }
                        cancelservicesyes.click();
                        waitReady();
                        refresh();
                        MyCommonAPIs.sleepi(10);
                    }
                } else {
                    deletecurrentservices.hover();
                    MyCommonAPIs.sleep(5000);
                    cancelservices.waitUntil(Condition.visible, 60 * 1000).click();
                    MyCommonAPIs.sleep(8 * 1000);
                    if (getText(cancelvpnpopuptext)
                            .contains("Cancelling this service will prevent you from using some VPN functions. Are you sure you want to cancel?")) {
                        result = true;
                    }
                    cancelservicesyes.click();
                    logger.info("Cancel services success.");
                }
            }
            waitReady();
        }
        return result;
    }

    public boolean checkVpnWarningMessage() {
        boolean result = false;
        accountmanager.click();
        waitReady();
        if (vpnservices.exists()) {
            vpnservices.click();
            MyCommonAPIs.sleepi(10);
            if (InstantVPN.exists()) {
                InstantVPN.click();
            }
            MyCommonAPIs.sleepi(10);
            if (vpnwarning.exists() && vpnwarning.getText().contains("Your NETGEAR Insight Subscription comes with 1 FREE VPN Group Credit")) {
                result = true;
                logger.info("Warning found.");
            }
        }
        return result;
    }

    public void gotoVpnResult() {
        accountmanager.click();
        waitReady();
        if (!openedorderhis.exists()) {
            openorderhis.click();
        }
        MyCommonAPIs.sleepi(10);
    }

    public void gotoBusinessVpnResult() {
        accountmanager.click();
        waitReady();
        if (!openedorderhis.exists()) {
            openbusinessorderhis.click();
        }
        MyCommonAPIs.sleepi(10);
    }

    public boolean checkBuyVpnResult(String year, String number) {
        boolean result = false;
        gotoVpnResult();
        // String yearelement = String.format(orderhistoryyr, year);
        ElementsCollection eles = $$x(vpnOrderTable);
        String actOnDate = "";
        String expOnDate = "";
        String orderQty = "";
        if (orderhistoryqty.exists()) {
            for (SelenideElement ele : eles) {
                System.out.println(ele);
                if (!ele.findElement(By.xpath("td[3]/p[1]")).getText().contains("Cancelled")) {
                    String actOnDateText = ele.findElement(By.xpath("td[3]/p[2]")).getText();
                    String expOnDateText = ele.findElement(By.xpath("td[4]")).getText();
                    actOnDate = actOnDateText.substring(actOnDateText.lastIndexOf(",") + 2, actOnDateText.length());
                    expOnDate = expOnDateText.substring(expOnDateText.lastIndexOf(",") + 2, expOnDateText.length());
                    System.out.println(actOnDate);
                    System.out.println(expOnDate);
                    orderQty = ele.findElement(By.xpath("td[2]/span")).getText();
                    System.out.println(orderQty);
                    break;
                }
            }
            if (((Integer.valueOf(expOnDate) - Integer.valueOf(actOnDate)) == Integer.valueOf(year)) && orderQty.equals(number)) {
                result = true;
                logger.info("Order history display correct.");
            }
        }
        return result;
    }

    public boolean checkBusinessVpnResult(String year, String number) {
        boolean result = false;
        gotoBusinessVpnResult();
        // String yearelement = String.format(orderhistoryyr, year);
        ElementsCollection eles = $$x(businessvpnOrderTable);
        String actOnDate = "";
        String expOnDate = "";
        String orderQty = "";
        if (businessorderhistoryqty.exists()) {
            for (SelenideElement ele : eles) {
                System.out.println(ele);
                if (!ele.findElement(By.xpath("td[3]/p[1]")).getText().contains("Cancelled")) {
                    String actOnDateText = ele.findElement(By.xpath("td[3]/p[2]")).getText();
                    String expOnDateText = ele.findElement(By.xpath("td[4]")).getText();
                    actOnDate = actOnDateText.substring(actOnDateText.lastIndexOf(",") + 2, actOnDateText.length());
                    expOnDate = expOnDateText.substring(expOnDateText.lastIndexOf(",") + 2, expOnDateText.length());
                    System.out.println(actOnDate);
                    System.out.println(expOnDate);
                    orderQty = ele.findElement(By.xpath("td[2]/span")).getText();
                    System.out.println(orderQty);
                    break;
                }
            }
            if (((Integer.valueOf(expOnDate) - Integer.valueOf(actOnDate)) == Integer.valueOf(year)) && orderQty.equals(number)) {
                result = true;
                logger.info("Order history display correct.");
            }
        }
        return result;
    }

    public boolean checkBuySomeVpnResult(String year, String number) {
        boolean result = false;
        accountmanager.click();
        waitReady();
        if (!openedorderhis.exists()) {
            openorderhis.click();
        }
        MyCommonAPIs.sleepi(5);
        if (orderhistoryqty.exists()) {
            // if (ordersubsort.getAttribute("aria-label").contains("descending")) {
            // ordersubsort.click();
            // MyCommonAPIs.sleepi(5);
            // }
            orderhisactsort.click();
            MyCommonAPIs.sleepi(5);
            if (!orderhisactsort.getAttribute("aria-label").contains("descending")) {
                orderhisactsort.click();
                MyCommonAPIs.sleepi(5);
            }
            for (int i = 1; i < 4; i++) {
                String yearelement = String.format(someorderhistory, String.valueOf(i), year);
                if ($x(yearelement).exists() && getText($x(String.format(someorderhistoryqty, String.valueOf(i)))).equals(number)) {
                    if (!getText($x(String.format(someorderstatus, String.valueOf(i)))).contains("Cancelled")) {
                        result = true;
                        logger.info("Order history display correct.");
                        break;
                    }
                }
            }
        }
        return result;
    }

    public String getOrderStatus() {
        String status = "";
        status = getText(orderstatus);
        return status;
    }

    public int getActivatedOnYear() {
        int year = 0;
        String date = getText(orderActivatedOn);
        year = Integer.parseInt(date.substring(date.lastIndexOf(",") + 2, date.length()));
        logger.info("Activated on " + date);
        return year;
    }

    public int getExpiresOnYear() {
        int year = 0;
        String date = getText(orderExpriesOn);
        year = Integer.parseInt(date.substring(date.lastIndexOf(",") + 2, date.length()));
        logger.info("Expires on " + date);
        return year;
    }

    public boolean checkAddVpnKey() {
        boolean result = false;
        accountmanager.click();
        waitReady();
        if (vpnservices.exists()) {
            vpnservices.click();
            MyCommonAPIs.sleepi(10);
            if (addvpnkey.exists()) {
                addvpnkey.click();
                MyCommonAPIs.sleepi(3);
                if (enterkey.exists()) {
                    result = true;
                    closeenterkey.click();
                    logger.info("Pop up 'Add VPN Service Key' dialog.");
                }
            }
        }
        return result;
    }

    public void openCreateProAccountUrl() {
        System.out.println("opening register URL");
        String url = WebportalParam.serverUrlLogin.substring(0, WebportalParam.serverUrlLogin.indexOf(".")) + ".insight.netgear.com/#/register";
        // String url = "insight.netgear.com/#/register";
        // String url = "https://pri-qa.insight.netgear.com/#/register";
        System.out.println(url);
        open(url);
        System.out.println("clear cookies");
        Selenide.clearBrowserCookies();
        System.out.println("clear browse storage");
        Selenide.clearBrowserLocalStorage();
        System.out.println("refresh");
        Selenide.refresh();

    }

    public void openNewURL() {
        // String url1 = "www.rediffmail.com";
        // open(url1);
        MyCommonAPIs.sleepi(10);
        String url = WebportalParam.serverUrlLogin.substring(0, WebportalParam.serverUrlLogin.indexOf(".")) + ".insight.netgear.com";
        open(url);
    }

    public void inputCreateProAccountEmail(String mailname) {
        createproemailinput.sendKeys(mailname);
        MyCommonAPIs.sleepi(3);
        createpronextbutton.click();
        logger.info("Input " + mailname + " and click next button.");
    }

    public boolean checkPurchaseHistory() {
        boolean result = false;
        accountmanager.click();
        waitReady();
        if (insightcaptiveportalhistory.exists()) {
            result = true;
            logger.info("Account already purchased captive portal services.");
        }
        return result;
    }

    public boolean checkDeleteIcpWarnIcon() {
        boolean result = false;
        if (deleteIcpServiceWarningIcon.exists()) {
            if (getText(deleteIcpServiceWarningIcon).contains(
                    "We are sorry to see you go. The selected service has been canceled successfully. A full refund will be made to your bank or card within 12 to 14 days.")) {
                result = true;
            }
        }
        return result;
    }

    public void gotoIcpServicesPage() {
        accountmanager.click();
        waitReady();
        if (captiveportalservices.exists()) {
            captiveportalservices.click();
            MyCommonAPIs.sleepi(10);
        }
    }

    public String getIcpCreditsNum() {
        gotoIcpServicesPage();
        return getText(icpCreditsNum);
    }

    public boolean checkIcpCreditsDate() {
        boolean result = false;
        if (checkCaptivePortalServicesCredits()) {
            SimpleDateFormat df = new SimpleDateFormat("dd, yyyy");
            SimpleDateFormat dfNextYear = new SimpleDateFormat("yyyy");
            SimpleDateFormat dfDate = new SimpleDateFormat("dd");
            String startDate = getText(icpSubscriptionTdTwo);
            String endDate = getText(icpSubscriptionTdThree);

            logger.info("Icp credits existed.");

            if (startDate.contains(df.format(new Date()).toString())
                    && endDate.contains(String.valueOf(Integer.valueOf(dfDate.format(new Date()).toString()) - 1) + ", "
                            + String.valueOf(Integer.valueOf(dfNextYear.format(new Date())) + 1))
                    && getText(icpCreditsNum).equals(getText(icpSubscriptionTdFour))) {
                result = true;
            }
        }
        return result;
    }

    public boolean checkProUserIcpCreditsAndTable() {
        boolean result = false;
        gotoIcpServicesPage();
        String icpNum = getText(icpCreditsNum);
        if (!icpNum.equals("0")) {
            logger.info("Total credits not equals zero.");
            int num = 0;
            ElementsCollection eles = $$x(icpSubscriptionTable + "td[4]");
            for (int i = 0; i < eles.size(); i++) {
                num += Integer.valueOf(getText(eles.get(i)));
            }
            if (icpNum.equals(String.valueOf(num))) {
                result = true;
                logger.info("Table total credits equals total credits.");
            }
        }
        return result;
    }

    public boolean checkCaptivePortalServicesCredits() {
        boolean result = true;
        gotoIcpServicesPage();
        if (captiveportalavailablecredits.exists()) {
            String check = getText(captiveportalavailablecredits);
            System.out.println(check);
            if (getText(captiveportalavailablecredits).equals("0")) {
                result = false;
                logger.info("Captive portal services available credits is 1st loop 0.");
            } else if (captiveportalavailablecreditsnew.exists()) {
                if (getText(captiveportalavailablecreditsnew).equals("0")) {
                    result = false;
                    logger.info("Captive portal services available credits is 2nd loop 0.");
                }
            }
        } else if (captiveportalavailablecreditsmanager.exists()) {
            if (getText(captiveportalavailablecreditsmanager).equals("0")) {
                result = false;
                logger.info("Captive portal services available credits is 3rd loop 0.");
            }
        }
        return result;
    }

    public boolean checkCreateProAccountPage(String option) {
        boolean result = false;
        if (!option.equals("checkKey") && !option.equals("checkManager")) {
            openCreateProAccountUrl();
        }
        if (createproemailinput.exists() && option.equals("checkUrl")) {
            if (createproemailinput.getAttribute("name").equals("signupEmail")) {
                result = true;
                logger.info("Open create pro account page success.");
            }
        } else if (createpronextbutton.exists() && option.contains("checkNext")) {
            String emailaddress = option.substring(option.indexOf(":") + 1, option.length());
            inputCreateProAccountEmail(emailaddress);
            MyCommonAPIs.sleepi(10);
            if (!createproemailinput.exists() && proaccountsignuptitle.exists()) {
                result = true;
                logger.info("Email address is correct and its in sign up page.");
            }
        } else if (option.contains("checkKey")) {
            if (inputproaccountkey.exists()) {
                result = true;
                logger.info("Its in enter pro key page.");
            }
        } else if (option.contains("checkManager")) {
            if (checkemailtitle.exists()) {
                checkemailtitle.click();
                MyCommonAPIs.sleepi(3);
                // Selenide.switchTo().frame("emailframe");
                open(getowneraccounturl.getAttribute("href"));
                waitElement(ownerconfirmemail);
                String url = MyCommonAPIs.getCurrentUrl();
                if (ownerconfirmemail.exists() && url.contains("#/register")) {
                    result = true;
                    logger.info("Its in manager account Sign-Up page.");
                }
            }
        }
        return result;
    }

    public String readLicenceKeyByTxt(String option) {
        String useLicence = "";
        String licence = "";
        String pathname = System.getProperty("user.dir") + "/src/test/resources/licence.txt";
        try (FileReader reader = new FileReader(pathname); BufferedReader br = new BufferedReader(reader)) {
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                if (line.equals("")) {
                    i += 1;
                    continue;
                }
                if (i == 0) {
                    useLicence = line;
                    i += 1;
                    continue;
                } else if (i == 1) {
                    licence += line;
                    i += 1;
                    continue;
                }
                licence += "\n" + line;
                i += 1;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (option.equals("Write")) {
            logger.info("Write licence:" + licence);
            try {
                PrintWriter pw = new PrintWriter(pathname);
                pw.write(licence);
                pw.flush();
                pw.close();
            } catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
        return useLicence;

    }

    public boolean checkCancelSubscriptionforpremium() {

        boolean result = false;

        if (currentsubscriptionpremium.exists() && getText(currentsubscriptionpremium).equals("Insight Premium Trial")) {
            result = true;
            logger.info("Subscription is back to Premium Trail\n");
        }

        // if(currentsubscriptionold.exists() && getText(currentsubscriptionold).equals("Insight Basic Subscription"))
        // {
        // result = true;
        // logger.info("Subscription is back to basic\n");
        //
        // }

        return result;
    }

    public boolean CancelSubscriptionformpremiumanually() {
        boolean result = false;
        if (currentsubscriptionold.exists() && getText(currentsubscriptionold).equals("Insight Basic Subscription")
                && getText(deviceredsubscription).equals("2")) {
            result = true;
            logger.info("Subscription is back to basic\n");

        }

        else if (CancelSubscriptionformpremiumanually.exists() && getText(CancelSubscriptionformpremiumanually).equals("Insight Premium Trial")
                && Unlimited.exists()) {
            result = true;
            logger.info("Subscription is back to Premium Trail\n");
        }
        return result;
    }

    public boolean checkCurrentSubscription() {

        boolean result = false;

        if (getText(deviceredsubscriptioncancel).equals("0") && getText(subscriptioncancel).equals("Cancelled")) {
            logger.info("Subscription cancel");
            result = true;
        }

        return result;
    }

    public void gotoInsightPremiumSubscriptions() {
        accountmanager.click();
        MyCommonAPIs.sleepi(5);
        closeLockedDialog();
        subscriptions.click();
        MyCommonAPIs.sleepi(10);
        if (closedevicecredits.exists()) {
            closedevicecredits.click();
        }
        waitReady();
//        $$(subButton).last().click();
        subButton1.click();
        waitReady();
    }

    public void cancelDeviceCredits() {
        accountmanager.click();
        MyCommonAPIs.sleepi(5);
        closeLockedDialog();
        subscriptions.click();
        MyCommonAPIs.sleepi(10);
        if (closedevicecredits.exists()) {
            closedevicecredits.click();
        }
        waitReady();
        SelenideElement ele = $x(deletecurrentdevicecredit);
        if (!getText(ele).contains("Use this table to view all current device credit packs for your Insight Premium account.")) {
            // ele.click();
            ele.hover();
            MyCommonAPIs.sleepi(1);
            if (cancelSubscriptionButton.exists()) {
                cancelSubscriptionButton.click();
            } else {
                subButton.click();
            }
            MyCommonAPIs.sleep(8 * 1000);
            clickYesNo(false);
            logger.info("Cancel device credits success.");
        }
        waitReady();
        refresh();
    }

    public boolean checkDeviceCredits(String number) {
        boolean result = false;
        SelenideElement ele = $x(deletecurrentdevicecredit);
        if (!getText(ele).contains("Use this table to view all current device credit packs for your Insight Premium account.")) {
            if (getText(deviceCreditsNum).equals(number) && getText(deviceCreditsStatus).equals("Active")) {
                result = true;
            }
        }
        return result;
    }

    public boolean checkSubscriptionsPage(String type, String number) {
        boolean result = false;
        if (checkDeviceCredits(number)) {
            result = true;
        }

        return result;
    }

    public void gotoCreditsAllocationPage() {
        logger.info("enter the creditallocationpage");
        accountmanager.click();
        logger.info("accountmangement click");
        MyCommonAPIs.sleepi(10);
        closeLockedDialog();
        logger.info("close click");
        creditsAllocation.click();
        logger.info("creditallocation click");
        waitReady();
        MyCommonAPIs.sleepi(10);
    }

    public HashMap<String, String> getCreditsAllocationCount() {
        HashMap<String, String> allocationStatus = new HashMap<String, String>();
        if (devCreditsAllocate.isDisplayed()) {
            devCreditsAllocate.click();
        }
        allocationStatus.put("Total Devices Credits", getText($x(String.format(totalCredits, "1"))));
        allocationStatus.put("Used Devices Credits", getText($x(String.format(usedCredits, "1"))));
        allocationStatus.put("Unused Devices Credits", getText($x(String.format(unusedCredits, "1"))));
        allocationStatus.put("Allocate Devices Count", getText($x(String.format(creditsCount, "1"))));
        icpCreditsAllocate.click();
        allocationStatus.put("Total ICP Credits", getText($x(String.format(totalCredits, "2"))));
        allocationStatus.put("Used ICP Credits", getText($x(String.format(usedCredits, "2"))));
        allocationStatus.put("Unused ICP Credits", getText($x(String.format(unusedCredits, "2"))));
        allocationStatus.put("Allocate ICP Count", getText($x(String.format(creditsCount, "2"))));
        vpnCreditsAllocate.click();
        allocationStatus.put("Total VPN Credits", getText($x(String.format(totalCredits, "3"))));
        allocationStatus.put("Used VPN Credits", getText($x(String.format(usedCredits, "3"))));
        allocationStatus.put("Unused VPN Credits", getText($x(String.format(unusedCredits, "3"))));
        allocationStatus.put("Allocate VPN Count", getText($x(String.format(creditsCount, "3"))));

        return allocationStatus;
    }

    public HashMap<String, String> getCreditAllocationStatus(String name) {
        HashMap<String, String> allocationStatus = new HashMap<String, String>();
        gotoCreditsAllocationPage();
        // SelenideElement btnEle = $x(String.format(allocateBtn, name));
        // btnEle.click();

        executeJavaScript("arguments[0].removeAttribute('class')", $x(String.format(showallocate, name)));
        MyCommonAPIs.sleep(3000);
        $x(String.format(clickallocate, name)).waitUntil(Condition.visible, 60 * 1000).click();
        MyCommonAPIs.sleep(8 * 1000);
        waitReady();
        allocationStatus = getCreditsAllocationCount();
        return allocationStatus;
    }

    public HashMap<String, String> getCreditAllocationTableInfo(String name) {
        HashMap<String, String> allocationStatus = new HashMap<String, String>();
        gotoCreditsAllocationPage();
        System.out.println(name);
        waitReady();
        allocationStatus.put("Devices Credits", getText($x(String.format(allocatedCredits, name, "2"))));
        allocationStatus.put("VPN Credits", getText($x(String.format(allocatedCredits, name, "4"))));
        allocationStatus.put("ICP Credits", getText($x(String.format(allocatedCredits, name, "3"))));
        return allocationStatus;
    }

    public void clickCreditsPlus(int devNum, int vpnNum, int icpNum) {
//      if (devCreditsAllocate.isDisplayed()) {
//          devCreditsAllocate.click();
//      }
      for (int i = 0; i < devNum; i++) {
          $x(String.format(creditsPlusBtn, "1")).click();
          MyCommonAPIs.sleepi(1);
      }
      vpnCreditsAllocate.click();
      for (int i = 0; i < vpnNum; i++) {
          $x(String.format(creditsPlusBtn, "3")).click();
          MyCommonAPIs.sleepi(1);
      }
      icpCreditsAllocate.click();
      for (int i = 0; i < icpNum; i++) {
          $x(String.format(creditsPlusBtn, "2")).click();
          MyCommonAPIs.sleepi(1);
      }
  }

    public void configCreditAllocation(String name, int devNum, int vpnNum, int icpNum) {
        logger.info("enter the allocation");
        gotoCreditsAllocationPage();
        logger.info("came out from creditallocation");

        executeJavaScript("arguments[0].removeAttribute('class')", $x(String.format(showallocate, name)));
        MyCommonAPIs.sleep(3000);
        $x(String.format(clickallocate, name)).waitUntil(Condition.visible, 60 * 1000).click();
        MyCommonAPIs.sleep(8 * 1000);

        // SelenideElement btnEle = $x(String.format(allocateBtn, name));
        // btnEle.click();
        // logger.info("ble click");
        // waitReady();
        clickCreditsPlus(devNum, vpnNum, icpNum);
        MyCommonAPIs.sleepi(5);
        logger.info("Plus click");
        allocateButton.click();
    }

    public HashMap<String, String> getDeallocatePageInfo() {
        HashMap<String, String> deallocateInfo = new HashMap<String, String>();
        HashMap<String, String> creditsInfo = new HamburgerMenuPage().getCreditAllocationStatus(WebportalParam.Organizations);
        cancelCreditsAllcation();
        waitReady();
        deallocate.click();
        waitReady();
        deallocateInfo.put("Deallocate DevNum",
                String.valueOf(Integer.valueOf(getText(deallocateDevCredits)) - Integer.valueOf(creditsInfo.get("Unused Devices Credits"))));
        if (deallocateIcpCredits.isDisplayed()) {
            deallocateInfo.put("Deallocate IcpNum",
                    String.valueOf(Integer.valueOf(getText(deallocateIcpCredits)) - Integer.valueOf(creditsInfo.get("Unused ICP Credits"))));
        } else {
            deallocateInfo.put("Deallocate IcpNum", "");
        }
        return deallocateInfo;
    }

    public void deallocateCredit(String name, String devNum, String icpNum) {
        gotoCreditsAllocationPage();
        deallocate.click();
        waitReady();
        if (!devNum.equals("0")) {
            selectDeallocateDevCredits.click();
            MyCommonAPIs.sleepi(3);
            clickButton(3);
            $x(String.format(selectOrgDeallocate, name)).click();
            MyCommonAPIs.sleepi(3);
            $x(String.format(inputDeallocateCredits, name)).setValue(devNum);
            MyCommonAPIs.sleepi(3);
            allocateButton.click();
            waitReady();
        }
        if (!icpNum.equals("0")) {
            selectDeallocateIcpCredits.click();
            MyCommonAPIs.sleepi(3);
            clickButton(3);
            $x(String.format(selectOrgDeallocate, name)).click();
            MyCommonAPIs.sleepi(3);
            $x(String.format(inputDeallocateCredits, name)).setValue(icpNum);
            MyCommonAPIs.sleepi(3);
            allocateButton.click();
            waitReady();
        }
    }

    public boolean checkDeallocateOrgExist(String name) {
        boolean result = false;
        String url = MyCommonAPIs.getCurrentUrl();
        if (!url.contains("/deallocateCredits")) {
            gotoCreditsAllocationPage();
            deallocate.click();
            waitReady();
            selectDeallocateDevCredits.click();
            MyCommonAPIs.sleepi(1);
            if (selectDeallocateIcpCredits.isDisplayed()) {
                selectDeallocateIcpCredits.click();
                MyCommonAPIs.sleepi(3);
            }
            System.out.println("before");
            clickButton(3);
            System.out.println("after");
        }
        MyCommonAPIs.sleepi(5);

        if ($x(String.format(selectOrgDeallocate, name)).isDisplayed()) {
            result = true;
        }
        MyCommonAPIs.sleepi(3);
        
        return result;
    }

    public boolean checkResetButton(String name, int devNum, int vpnNum, int icpNum) {
        boolean result = false;
        gotoCreditsAllocationPage();
        // SelenideElement btnEle = $x(String.format(allocateBtn, name));
        // btnEle.click();
        executeJavaScript("arguments[0].removeAttribute('class')", $x(String.format(showallocate, name)));
        MyCommonAPIs.sleep(3000);
        $x(String.format(clickallocate, name)).waitUntil(Condition.visible, 60 * 1000).click();
        MyCommonAPIs.sleep(8 * 1000);
        waitReady();
        clickCreditsPlus(devNum, vpnNum, icpNum);
        HashMap<String, String> allocationStatus = new HashMap<String, String>();
        allocationStatus = getCreditsAllocationCount();
        if (allocationStatus.get("Allocate ICP Count").equals(String.valueOf(icpNum))
                && allocationStatus.get("Allocate Devices Count").equals(String.valueOf(devNum))) {
            resetCreditsAllcation();
            allocationStatus = getCreditsAllocationCount();
            if (allocationStatus.get("Allocate ICP Count").equals("0") && allocationStatus.get("Allocate Devices Count").equals("0")) {
                result = true;
                logger.info("Reset credits count success.");
            }
        }
        return result;
    }

    public boolean checkCreditsAllocationErrorMsg(String name, int devNum, int vpnNum, int icpNum) {
        boolean result = false;
        gotoCreditsAllocationPage();
        // SelenideElement btnEle = $x(String.format(allocateBtn, name));
        // btnEle.click();

        executeJavaScript("arguments[0].removeAttribute('class')", $x(String.format(showallocate, name)));
        MyCommonAPIs.sleep(3000);
        $x(String.format(clickallocate, name)).waitUntil(Condition.visible, 60 * 1000).click();
        MyCommonAPIs.sleep(8 * 1000);
        waitReady();
        clickCreditsPlus(devNum, vpnNum, icpNum);
        if (devCreditsAllocate.isDisplayed()) {
            devCreditsAllocate.click();
        }
        for (int i = 0; i < 1; i++) {
            $x(String.format(creditsPlusBtn, "1")).click();
            MyCommonAPIs.sleepi(1);
        }
        if (errorMsg.exists()) {
            MyCommonAPIs.sleepi(5);
            clickCreditsPlus(0, 0, 1);
            if (errorMsg.exists()) {
                result = true;
                logger.info("All your device credits are allocated.");
            }
        }
        return result;
    }

    public boolean checkCreditsAllocationSuccessMsg() {
        boolean result = false;
        MyCommonAPIs.sleepi(3);
        if (successMsg.exists()) {
            result = true;
            logger.info("The credits have been allocated successfully");
        }
        return result;
    }

    public String getTotalDevicesCredits() {
        return getText(totalDevicesCredits);
    }

    public String getTotalIcpCredits() {
        return getText(totalIcpCredits);
    }

    public void cancelCreditsAllcation() {
        $$("button.ipbtn").get(0).click();
        MyCommonAPIs.sleepi(1);
    }

    public void resetCreditsAllcation() {
        $$("button.ipbtn").get(1).click();
        MyCommonAPIs.sleepi(1);
    }

    public void searchOrg(String name) {
        gotoCreditsAllocationPage();
        searchOrgName.click();
        MyCommonAPIs.sleepi(1);
        inputOrgName.setValue(name);
        MyCommonAPIs.sleepi(1);
        searchBtn.click();
        waitReady();
        MyCommonAPIs.sleepi(10);
    }

    public void endAccessSupportUser(String name) {
        waitReady();
        clickdown.click();
        sleepi(3);
        search.click();
        sleepi(3);
        sendemail.sendKeys(name);
        sleepi(3);
        searchbutton.click();
        sleepi(10);
        $x(String.format(supportRequestEndAccess, name)).click();
        sleepi(3);
        clickBoxLastButton();
        waitReady();
    }

    public void logInAsThisUser(String name) {
        waitReady();
        // supportRequestLogInUser(name).click();
        clickdown.click();
        sleepi(3);
        search.click();
        sleepi(3);
        sendemail.sendKeys(name);
        sleepi(3);
        searchbutton.click();
        sleepi(10);
        $x(String.format(supportRequestLogInUser, name)).click();
        sleepi(3);
        // clickBoxLastButton();
        Logon.click();
        waitReady();
    }

    public void grantAccessToSupport() {
        if(!endSupportAccess.isDisplayed()){
        grantAccessToSupport.click();
        sleepi(3);
        startacess.click();
        // clickBoxLastButton();
        waitReady();
        }
    }

    public void gotoMyInsightAccount() {
        gotoMyAccount.click();
        sleepi(3);
        clickBoxLastButton();
        waitReady();
    }

    public void endTechnicalSupportAccess() {
        endSupportAccess.click();
        sleepi(3);
        clickBoxLastButton();
        waitReady();
    }

    public void openInsightSubscriptionPlanPage() {
        // if (WebportalParam.serverUrl.contains("beta")) {
        // Selenide.open("https://test.netgear.com/insight/subscription.aspx#");
        // } else if (WebportalParam.serverUrl.contains("qa")) {
        // Selenide.open("https://test-us.netgear.com/insight/subscription.aspx#");
        // } else {
        // assertTrue(false, "Not support other server.");
        // }
        // waitReady();
        // if (closeSignUp.isDisplayed()) {
        // closeSignUp.click();
        // }

        Selenide.open(
                "https://accounts-stg2.netgear.com/login?redirectUrl=https:%2F%2Fbilling-stg-temp.netgear.com%2Finsight-pro&GotoDashboard=https:%2F%2Fmaint-beta.insight.netgear.com&category_id=19");

    }

    // public void clickLoginAndSubscribe(String type) {
    // String url = "";
    // if (type.equals("premium")) {
    // url = $$x(loginAndSubscribe).first().getAttribute("href");
    // } else if (type.equals("pro")) {
    // url = $$x(loginAndSubscribe).last().getAttribute("href");
    // }
    // MyCommonAPIs.sleepi(3);
    // Selenide.open(url);
    // waitReady();
    // }

    public void clickLoginAndSubscribe(String type) {
        String url = "";
        if (type.equals("premium")) {
            Selenide.open(
                    "https://accounts-stg2.netgear.com/login?redirectUrl=https:%2F%2Fbilling-stg-temp.netgear.com%2Finsight-pro&GotoDashboard=https:%2F%2Fmaint-beta.insight.netgear.com&category_id=19");

        } else if (type.equals("pro")) {
            Selenide.open(
                    "https://accounts-stg2.netgear.com/login?redirectUrl=https:%2F%2Fbilling-stg-temp.netgear.com%2Finsight-pro&GotoDashboard=https:%2F%2Fmaint-beta.insight.netgear.com&category_id=18");

        }
        MyCommonAPIs.sleepi(3);
        waitReady();
    }

    public String getActivationDate(String SerialNo) {
        ;
        MyCommonAPIs.sleepi(3);
        String activationdate = getText(activationDate(SerialNo));
        return activationdate;

    }

    public String getExpiryDate(String SerialNo) {

        MyCommonAPIs.sleepi(3);
        String getExpiryDate = getText(ExpiryDate(SerialNo));
        return getExpiryDate;

    }

    public boolean checkCreateAccountPage() {
        boolean result = false;
        if (createfirstname.exists() && createlastname.exists() && createemailaddress.exists() && confirmemail.exists() && createpassword.exists()
                && confirmpassword.exists() && selectcountry.exists()) {
            result = true;
            logger.info("Create account page display correct.");
        }
        return result;
    }

    public boolean createAccountExist() {
        boolean result = false;
        if ($x("//p[contains(text(),'The Email you entered already exists with our NETGEAR product.')]").exists()) {
            result = true;
            logger.info("Create account exist.");
        }
        return result;
    }

    public boolean checkCreateAccountError() {
        boolean result = false;
        if (emailerror.exists() && firstnameerror.exists() && confirmpwderror.exists()) {
            result = true;
            logger.info("Create account error displayed.");
        }
        return result;
    }

    public boolean checkDirectPurchase() {
        boolean result = false;
        if (directPurchaseTable.exists()) {
            result = true;
            logger.info("Direct purchase history existed.");
        }
        return result;
    }

    // public boolean checkDirectPurchaseTableInfo() {
    // boolean result = false;
    // SimpleDateFormat df = new SimpleDateFormat("dd, yyyy");
    // SimpleDateFormat dfNextYear = new SimpleDateFormat("yyyy");
    // SimpleDateFormat dfDate = new SimpleDateFormat("dd");
    // String expiresOn = getText(directPurchaseTdThree);
    // if (directPurchaseTable.exists()) {
    // logger.info("Direct purchase history existed.");
    // if (getText(directPurchaseTdTwo).contains(df.format(new Date()).toString())
    // && getText(directPurchaseTdThree).contains(
    // dfDate.format(new Date()).toString() + ", " + String.valueOf(Integer.valueOf(dfNextYear.format(new Date())) + 1))
    // && getText(directPurchaseTdFour).contains("1")) {
    // result = true;
    // }
    // }
    // return result;
    // }

    public boolean checkDirectPurchaseTableInfo() {
        boolean result = false;
        SimpleDateFormat df = new SimpleDateFormat("dd, yyyy");
        SimpleDateFormat dfNextYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat dfDate = new SimpleDateFormat("dd");
        String expiresOn = getText(directPurchaseTdThree);
        if (directPurchaseTable.exists()) {
            logger.info("Direct purchase history existed.");
            if (getText(directPurchaseTdTwo).contains("2021") && getText(directPurchaseTdThree).contains("2022")
                    && getText(directPurchaseTdFour).contains("1")) {
                result = true;
            }
        }
        return result;
    }

    public boolean checkdropDownPremiunDirectPurchase() {
        boolean result = false;
        accountmanager.click();
        waitElement(iconsearch);
        MyCommonAPIs.sleepi(5);
        logger.info("Check purchage order history...");
        if (dropDownPremiunDirectPurchase.exists() && premiumuserinsightlicense.exists()) {
            result = true;
        }
        return result;
    }

    public boolean checkdropDownProDirectPurchase() {
        boolean result = false;
        accountmanager.click();
        waitElement(iconsearch);
        MyCommonAPIs.sleepi(5);
        logger.info("Check purchage order history...");
        if (dropDownProDirectPurchase.exists()) {
            result = true;
        }
        return result;
    }

    public boolean checkPremiunDirectPurchaseCorrect() {
        boolean result = false;
        SimpleDateFormat df = new SimpleDateFormat("dd, yyyy");
        SimpleDateFormat dfNextYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat dfDate = new SimpleDateFormat("dd");
        accountmanager.click();
        waitElement(iconsearch);
        MyCommonAPIs.sleepi(5);
        if (dropDownPremiunDirectPurchase.exists()) {
            dropDownPremiunDirectPurchase.click();
            logger.info("Drop down direct purchase tab.");
            MyCommonAPIs.sleepi(3);
            String activatedOn = getText(directPurchaseHistoryTdThree);
            String expiresOn = getText(directPurchaseHistoryTdFour);
            if (getText(directPurchaseHistoryTdOne).contains("Insight Premium Subscription") && getText(directPurchaseHistoryTdTwo).contains("1")
                    && activatedOn.contains(df.format(new Date()).toString()) && expiresOn.contains(
                            dfDate.format(new Date()).toString() + ", " + String.valueOf(Integer.valueOf(dfNextYear.format(new Date())) + 1))) {
                result = true;
            }
        } else if (dropDownProDirectPurchase.exists()) {
            dropDownProDirectPurchase.click();
            logger.info("Drop down direct purchase tab.");
            MyCommonAPIs.sleepi(3);
            String activatedOn = getText(directPurchaseProHistoryTdThree);
            String expiresOn = getText(directPurchaseProHistoryTdFour);
            if (getText(directPurchaseProHistoryTdFive).equals("1") && getText(directPurchaseProHistoryTdOne).contains("Insight Pro 1 Single")
                    && getText(directPurchaseProHistoryTdTwo).contains("1") && activatedOn.contains(df.format(new Date()).toString())
                    && !activatedOn.equals("") && expiresOn.contains(
                            dfDate.format(new Date()).toString() + ", " + String.valueOf(Integer.valueOf(dfNextYear.format(new Date())) + 1))) {
                result = true;
            }
        }
        return result;
    }

    public boolean checkProAccountCurrentSubscription() {
        boolean result = false;
        if (proCurrentSubscription.exists()) {
            result = true;
            logger.info("It's a pro account.");
        }
        return result;
    }

    public boolean checkCancelBanner() {
        boolean result = false;
        if (proSubCancelBanner.exists()) {
            result = true;
            logger.info("Banner displayed.");
        }
        return result;
    }

    public boolean checkDisplayCancelDirectPurchase(String type) {
        boolean result = false;
        SelenideElement element = $x("");
        if (type.equals("premium")) {
            element = directPurchaseTdOne;
        } else {
            element = proSubscriptionTdOne;
        }
        if (element.exists()) {
            element.hover();
            MyCommonAPIs.sleep(3000);
            if (cancelSubscription.isDisplayed()) {
                result = true;
                cancelSubscription.waitUntil(Condition.visible, 60 * 1000).click();
                MyCommonAPIs.sleep(8 * 1000);
                cancelservicesyesnew.click();
                logger.info("Cancel direct purchase success.");
                waitReady();
            }
        }
        return result;
    }

    public void clickBusinessDetailsToComplete() {
        if (getPageErrorMsg().contains("go to business details to complete all required fields")) {
            clickBoxLastButton();
            waitReady();
        }
    }

    public boolean checkProAccountSubscriptionExist() {
        boolean result = false;
        if ($x(proSubscriptionTable).exists()) {
            if (!getText($x(proSubscriptionTable)).contains("No key available")) {
                result = true;
            }
        }
        return result;
    }

    public boolean checkProAccountSubscriptionInfo() {
        boolean result = false;
        if (checkProAccountSubscriptionExist()) {
            SimpleDateFormat df = new SimpleDateFormat("dd, yyyy");
            SimpleDateFormat dfNextYear = new SimpleDateFormat("yyyy");
            SimpleDateFormat dfDate = new SimpleDateFormat("dd");
            String expiresOn = getText(proSubscriptionTdThree);
            logger.info("Direct purchase history existed.");
            if (getText(proSubscriptionTdTwo).contains(df.format(new Date()).toString())
                    && expiresOn.contains(
                            dfDate.format(new Date()).toString() + ", " + String.valueOf(Integer.valueOf(dfNextYear.format(new Date())) + 1))
                    && getText(proSubscriptionTdFour).contains("1")) {
                result = true;
            }
        }
        return result;
    }

    public boolean MUBexits() {
        boolean result = false;

        hamburgermenunew.click();
        MyCommonAPIs.sleep(3000);
        accountmanager.click();
        MyCommonAPIs.sleepi(30);
        if (MUB.exists()) {
            logger.info("Monthly bassed billing is present");
            result = true;
        }
        return result;
    }

    public void GooMUB() {
        hamburgermenunew.click();
        MyCommonAPIs.sleepi(5);
        accountmanager.click();
        MyCommonAPIs.sleepi(20);
        MUB.click();
    }

    public void EnableMUB() {
        setSelected($x("//*[text() = 'Monthly Usage Billing']/../label/span"), true);

    }

    public void DisableMUB() {
        setSelected($x("//*[text() = 'Monthly Usage Billing']/../label/span"), true);

    }

    public boolean EnableCreditAllocation() {
        accountmanager.click();
        MyCommonAPIs.sleep(3000);
        creditsAllocation.click();
        MyCommonAPIs.sleep(3000);
        boolean result = false;
        setSelected($("#enableBlackList"), false);
        MyCommonAPIs.sleep(3000);
        if (CreditAllocationerror.isDisplayed()) {
            result = true;
            logger.info("message is displayed");
        }
        return result;
    }

    public boolean LMSkeycheck() {

        boolean result = false;
        accountmanager.click();
        MyCommonAPIs.sleep(3000);
        subscriptions.click();
        MyCommonAPIs.sleep(3000);
        // AddpurchaseKey.click();
        // MyCommonAPIs.sleep(3000);
        // String write = new HamburgerMenuPage(false).readLicenceKeyByTxt("Write");
        // WriteLMSKey.setValue(write);
        // clickonAddLMSKey.click();
        MyCommonAPIs.sleep(3000);
        String LMSActivationdate = getText(LMSActivation);
        String LMSExpirationdate = getText(LMSExpiration);
        System.out.println(LMSActivationdate);
        System.out.println(LMSExpirationdate);
        LocalDate currentDate = LocalDate.now();
        Month currentMonth = currentDate.getMonth();
        System.out.println(currentDate);
        System.out.println(currentMonth);
        int temp = currentMonth.getValue();
        System.out.println(temp);
        // temp= temp+1;
        // System.out.println(temp);
        String Month = currentMonth.of(temp).toString();
        System.out.println("Need ---" + Month);

        int currentYear = currentDate.getYear();
        currentYear = currentYear + 1;
        System.out.println("Year is --->" + currentYear);

        String NewMonth = Month.substring(0, 3);
        System.out.println(NewMonth);

        String firstLetter = NewMonth.substring(0, 1);
        String remainingLetters = NewMonth.substring(1, NewMonth.length());

        remainingLetters = remainingLetters.toLowerCase();

        String name = firstLetter + remainingLetters;
        System.out.println("Current year is : " + name);

        if (!LMSActivationdate.equals("") && !LMSExpirationdate.equals("")) {
            result = true;
            logger.info("got a correct date");
        }

        return result;
    }

    public boolean Othercountries() {
        boolean result = false;
        MyCommonAPIs.sleep(3000);
        if (otherCountries.exists()) {
            logger.info("MUB option is only applicable for the North American countries");
            result = true;
        }

        return result;
    }

    public boolean isMUBdisabled() {

        boolean result = false;

        if (MUBdisablemessage.isDisplayed()) {
            result = true;
            logger.info("cacelation message is displayed");
        }

        return result;

    }

    public boolean OtherUser() {
        boolean result = false;
        if (MUBEnableling.exists()) {
            result = true;
            logger.info("MUB enabling option exits");
        }
        System.out.println(result);
        return result;
    }

    public boolean UsageHistory() {
        boolean result = false;
        MyCommonAPIs.sleep(3000);
        MUBhistory.click();
        waitReady();
        if (MUBhistorytext.exists()) {
            logger.info("correct text displayed as in day zero MUB histry");
            System.out.println(
                    "View your Monthly Usage Billing history here, including details about each organization in your account. Information on the current month becomes available on the first day of the next month.");
            result = true;
        }
        return result;

    }

    public boolean CancelMUB() {
        boolean result = false;

        MUBcancelation.click();

        Selenide.switchTo().window(1);
        String url = MyCommonAPIs.getCurrentUrl();
        if (url.contains("kb.netgear.com") && cancelationKB.exists()) {
            logger.info("forwarded to KB artile");
            result = true;
        }
        Selenide.switchTo().window(0);
        return result;
    }

    public void EditManagePaymentMethods(Map<String, String> map) {
        ManagePaymentMethods.click();
        MyCommonAPIs.sleepi(10);
        TermaandconditionCheckbox.click();
        TermaandconditionAccept.click();
        MyCommonAPIs.sleep(6000);
        EditBilliningINfo.click();
        MyCommonAPIs.sleep(3000);
        billingfirstname.clear();
        billingfirstname.sendKeys(map.get("First Name"));
        billinglastname.clear();
        billinglastname.sendKeys(map.get("Last Name"));
        billingstreetaddress.clear();
        billingstreetaddress.sendKeys(map.get("Street Address"));
        billingcity.clear();
        billingcity.sendKeys(map.get("City"));
        billingzip.clear();
        billingzip.sendKeys(map.get("Zip"));
       
        MyCommonAPIs.sleep(3000);
        inputCardInfo(map);
        Submit.click();
        MyCommonAPIs.sleepi(20);
        if (gotosubscriptionnow.exists()) {
            gotosubscriptionnow.click();
        }
        MyCommonAPIs.sleepi(30);
//        if (cancel.isDisplayed()) {
//            cancel.click();
//        }
    }

    public boolean VerifyTermsandCondition() {

        boolean result = false;
        boolean result1 = false;
        boolean result2 = false;

        ManagePaymentMethods.click();
        MyCommonAPIs.sleep(3000);
        if (Termaandcondition.exists()) {
            logger.info("Terms and confition Exits");
            result1 = true;
        }
        TermaandconditionCheckbox.click();
        TermaandconditionAccept.click();
        waitReady();
        MyCommonAPIs.sleepi(30);
        if (BillingInfo.exists()) {
            logger.info("Billing info exits");
            result2 = true;
        }

        if ((result1 == true) && (result2 == true)) {

            result = true;
        }

        return result;

    }

    public Map<String, String> getProfileMUB() {
        ManagePaymentMethods.click();
        MyCommonAPIs.sleep(3000);
        if(TermaandconditionCheckbox.exists()) {
        TermaandconditionCheckbox.click();
        }
        if (TermaandconditionAccept.exists()) {
        TermaandconditionAccept.click();
        }
        MyCommonAPIs.sleep(6000);
        if (EditBilliningINfo.exists()) {
            EditBilliningINfo.click();
        }
        MyCommonAPIs.sleep(3000);
        if (EditPaymentINfo.exists()) {
            EditPaymentINfo.click();
        }
        MyCommonAPIs.sleep(3000);
        waitElement(billingfirstname);
        logger.info("Get user profile...");
        Map<String, String> profileInfo = new HashMap<String, String>();
        profileInfo.put("First Name", billingfirstname.getValue());
        profileInfo.put("Last Name", billinglastname.getValue());
        profileInfo.put("Street Address", billingstreetaddress.getText());
        profileInfo.put("City", billingcity.getValue());
        profileInfo.put("Zip", billingzip.getValue());
        profileInfo.put("Country", billingcountry.getValue());
        profileInfo.put("State", billingstate1.getValue());
        MyCommonAPIs.sleep(3000);
        if (gotosubscriptionnow.exists()) {
            gotosubscriptionnow.click();
        }
        if (cancel.exists()) {
            cancel.click();
        }
        return profileInfo;

    }

    public void FillPaymentMethods(Map<String, String> map) {
        ManagePaymentMethods.click();
        MyCommonAPIs.sleep(3000);
        TermaandconditionCheckbox.click();
        TermaandconditionAccept.click();
        MyCommonAPIs.sleep(6000);
        // GoToBillingInfo.click();
        MyCommonAPIs.sleep(3000);
        billingfirstname.sendKeys(map.get("First Name"));
        billinglastname.sendKeys(map.get("Last Name"));
        billingstreetaddress.sendKeys(map.get("Street Address"));
        billingcity.sendKeys(map.get("City"));
        billingzip.sendKeys(map.get("Zip"));
        billingcountry.selectOption(map.get("Country"));
        if (billingstate1.isDisplayed()) {
            billingstate1.selectOption(map.get("State"));
        } else {
            billingstate2.sendKeys(map.get("State"));
        }
        GoToPaymentinfo.click();
        MyCommonAPIs.sleepi(3);
        paymentcardnumber.clear();
        paymentcardnumber.setValue(map.get("Card Number"));
        MyCommonAPIs.sleepi(2);
        paymentcvvnumber.clear();
        paymentcvvnumber.setValue(map.get("CVV Number"));
        MyCommonAPIs.sleepi(2);
        paymentexpirationmonth.selectOption(map.get("Expiration Month"));
        paymentexpirationyear.selectOption(map.get("Expiration Year"));
        MyCommonAPIs.sleepi(2);
        billingstreetaddress.click();
        billingstreetaddress.clear();    
        billingstreetaddress.sendKeys(map.get("Street Address"));
    }

    public boolean CheckFreeTrailmessage() {
        MyCommonAPIs.sleepi(10);
        boolean result = false;
        if (textcheckpro.exists()) {
            result = true;
            logger.info("sucessfull message");
        }

        return result;
    }

    public boolean CheckFreeTrailPro() {
        boolean result = false;
        String actOnDate = "";
        String expOnDate = "";
        String orderQty = "";
        ElementsCollection tablerow = $$x("//*[@id=\"proCurrLicenseTable\"]/tbody/tr");

        for (SelenideElement ele : tablerow) {
            System.out.println(ele);

            String actOnDateText = ele.findElement(By.xpath("td[3]")).getText();
            String expOnDateText = ele.findElement(By.xpath("td[4]")).getText();
            actOnDate = actOnDateText.substring(0, 3);
            expOnDate = expOnDateText.substring(0, 3);
            // expOnDate = expOnDateText.substring(expOnDateText.lastIndexOf(",") + 2, expOnDateText.length());
            System.out.println(actOnDate);
            System.out.println(expOnDate);
            break;
        }

        String ACtgetMonth = MonthtoNumber(actOnDate);
        String expgetMonth = MonthtoNumber(expOnDate);
        int ACtgetMonthi = Integer.parseInt(ACtgetMonth);
        int expgetMonthi = Integer.parseInt(expgetMonth);
        int Month = expgetMonthi - ACtgetMonthi;
        System.out.println(Month);
        int Month1 = Month + 12;
        System.out.println(Month);
        
        if (Month1 == 3 || Month == 3) {
            result = true;
            logger.info("Order history display correct.");
        }

        return result;
    }

    String MonthtoNumber(String month) {

        String Result = "";
        String Num = month.toUpperCase();
        System.out.println("Month converted to upper" + Num);

        switch (Num) {
        case "JAN":
            Result = "1";
            System.out.print("1");
            break;
        case "FEB":
            Result = "2";
            System.out.print("2");
            break;
        case "MAR":
            Result = "3";
            System.out.print("3");
            break;
        case "APR":
            Result = "4";
            System.out.println("4");
            break;
        case "MAY":
            Result = "5";
            System.out.print("5");
            break;
        case "JUN":
            Result = "6";
            System.out.print("6");
            break;
        case "JUL":
            Result = "7";
            System.out.println("7");
            break;
        case "AUG":
            Result = "8";
            System.out.print("8");
            break;
        case "SEP":
            Result = "9";
            System.out.print("9");
            break;
        case "OCT":
            Result = "10";
            System.out.print("10");
            break;
        case "NOV":
            Result = "11";
            System.out.print("11");
            break;
        case "DEC":
            Result = "12";
            System.out.print("12");
            break;

        }

        return Result;

    }

    public boolean StartFreeTrail() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        ProTrailStart.click();
        MyCommonAPIs.sleepi(2);
        ProTrailActive.click();

        return result;
    }

    public boolean PurchaseHistoryProTrail() {

        boolean result = false;
        accountmanager.click();
        MyCommonAPIs.sleepi(20);
        PurchaseOrderHistory.click();
        MyCommonAPIs.sleepi(15);
        ProTrailPurchasehistoryDropDown.click();
        MyCommonAPIs.sleepi(2);

        String actOnDate = "";
        String expOnDate = "";
        String orderQty = "";

        String actOnDateText = actDate.getText();
        String expOnDateText = expDate.getText();
        System.out.println(actOnDateText);
        System.out.println(expOnDateText);
        actOnDate = actOnDateText.substring(0, 3);
        expOnDate = expOnDateText.substring(0, 3);
        System.out.println(actOnDate);
        System.out.println(expOnDate);

        String ACtgetMonth = MonthtoNumber(actOnDate);
        String expgetMonth = MonthtoNumber(expOnDate);
        System.out.println(ACtgetMonth);
        System.out.println(expgetMonth);

        int ACtgetMonthi = Integer.parseInt(ACtgetMonth);
        int expgetMonthi = Integer.parseInt(expgetMonth);
        int Month = expgetMonthi - ACtgetMonthi;
        System.out.println(Month);
        int Month1 = Month + 12;
        System.out.println(Month1);
        
        if (Month1 == 3 || Month == 3) {
            result = true;
            logger.info("Order history display correct.");
        }

        return result;
    }

    public void AddKeyAndVerify(String LicenceKey) {

        MyCommonAPIs.sleepi(5);
        AddProKey.click();
        MyCommonAPIs.sleepi(5);
        AddProLicense.sendKeys(LicenceKey);
        MyCommonAPIs.sleepi(5);
        ClickAdd.click();
        MyCommonAPIs.sleepi(5);
        ClickAddok.click();
        System.out.println("license added sucessfully");

    }

    public boolean verify(String lic) {
        MyCommonAPIs.sleepi(10);
        boolean result = false;
        String actOnDate = "";
        String expOnDate = "";
        String orderQty = "";
        ElementsCollection tablerow = $$x("//span[contains(text(), '" + lic + "')]/../..");
        MyCommonAPIs.sleepi(10);
        System.out.println("clollection of an element");
        for (SelenideElement ele : tablerow) {
            System.out.println(ele);
            System.out.println("Print the element");
            String actOnDateText = ele.findElement(By.xpath("td[3]")).getText();
            String expOnDateText = ele.findElement(By.xpath("td[4]")).getText();
            actOnDate = actOnDateText.substring(actOnDateText.lastIndexOf(",") + 2, actOnDateText.length());
            expOnDate = expOnDateText.substring(expOnDateText.lastIndexOf(",") + 2, expOnDateText.length());
            System.out.println(actOnDate);
            System.out.println(expOnDate);

            break;
        }
       
        if (((Integer.valueOf(expOnDate) - Integer.valueOf(actOnDate)) == 5)) {
            result = true;
            logger.info("Order history display correct.");
        }

        return result;
    }
    
    public boolean verifyLMS(String lic) {
        MyCommonAPIs.sleepi(10);
        boolean result = false;
        String actOnDate = "";
        String expOnDate = "";
        String orderQty = "";
        ElementsCollection tablerow = $$x("//span[contains(text(), '" + lic + "')]/../..");
        MyCommonAPIs.sleepi(10);
        System.out.println("clollection of an element");
        for (SelenideElement ele : tablerow) {
            System.out.println(ele);
            System.out.println("Print the element");
            String actOnDateText = ele.findElement(By.xpath("td[3]")).getText();
            String expOnDateText = ele.findElement(By.xpath("td[4]")).getText();
            actOnDate = actOnDateText.substring(actOnDateText.lastIndexOf(",") + 2, actOnDateText.length());
            expOnDate = expOnDateText.substring(expOnDateText.lastIndexOf(",") + 2, expOnDateText.length());
            System.out.println(actOnDate);
            System.out.println(expOnDate);

            break;
        }
       
        if (((Integer.valueOf(expOnDate) - Integer.valueOf(actOnDate)) == 5)) {
            result = true;
            logger.info("Order history display correct.");
        }

        return result;
    }
    
    public boolean verifyOrgImd(String lic) {
        MyCommonAPIs.sleepi(20);
        Orgimmediet.click();
        System.out.println(lic+"name");
        MyCommonAPIs.sleepi(40);
        boolean result = false;
        String actOnDate = "";
        String expOnDate = "";
        String orderQty = "";
        ElementsCollection tablerow = $$x("//span[contains(text(), '" + lic + "')]/../..");
        System.out.println(tablerow);
        System.out.println("clollection of an element");
        for (SelenideElement ele : tablerow) {
            System.out.println(ele);
            System.out.println("Print the element");
            String actOnDateText = ele.findElement(By.xpath("td[4]")).getText();
            String expOnDateText = ele.findElement(By.xpath("td[5]")).getText();
            actOnDate = actOnDateText.substring(actOnDateText.lastIndexOf(",") + 2, actOnDateText.length());
            expOnDate = expOnDateText.substring(expOnDateText.lastIndexOf(",") + 2, expOnDateText.length());
            System.out.println(actOnDate);
            System.out.println(expOnDate);

            break;
        }

        if (((Integer.valueOf(expOnDate) - Integer.valueOf(actOnDate)) == 5)) {

            result = true;
            logger.info("Order history display correct.");
        }

        return result;
    }
    
    
    public boolean verifyICP(String lic) {
        System.out.println(lic+ "name");
        MyCommonAPIs.sleepi(10);
        captiveportalservices.click();
        MyCommonAPIs.sleepi(40);
        boolean result = false;
        String actOnDate = "";
        String expOnDate = "";
        String orderQty = "";
        ElementsCollection tablerow = $$x("//span[contains(text(), '" + lic + "')]/../..");
        System.out.println("clollection of an element");
        System.out.println(lic+"name");
        for (SelenideElement ele : tablerow) {
            System.out.println(ele);
            System.out.println("Print the element");
            String actOnDateText = ele.findElement(By.xpath("td[2]")).getText();
            String expOnDateText = ele.findElement(By.xpath("td[3]")).getText();
            System.out.println(actOnDateText);
            System.out.println(expOnDateText);
            actOnDate = actOnDateText.substring(actOnDateText.lastIndexOf(",") + 2, actOnDateText.length());
            expOnDate = expOnDateText.substring(expOnDateText.lastIndexOf(",") + 2, expOnDateText.length());
            System.out.println(actOnDate);
            System.out.println(expOnDate);

            break;
        }

        if (((Integer.valueOf(expOnDate) - Integer.valueOf(actOnDate)) == 1)) {
            result = true;
            logger.info("Order history display correct.");
        }

        return result;
    }

    public boolean insightPritoinsightPro() {
        MyCommonAPIs.sleepi(2);
        boolean result = false;
        // humbergerMenuforPremtoPro.click();
        MyCommonAPIs.sleepi(1);
        waitElement(accountManagement);
        if (accountManagement.isDisplayed()) {
            System.out.println(accountManagement.getText());
            result = true;
            MyCommonAPIs.sleepi(1);
            accountManagement.click();
        } else {
            result = false;
        }
        MyCommonAPIs.sleepi(10);
        waitElement(upgradeToPro);
        if (upgradeToPro.isDisplayed()) {
            System.out.println(upgradeToPro.getText());
            result = true;
            MyCommonAPIs.sleepi(1);
            upgradeToPro.click();
        } else {
            result = false;
        }
        MyCommonAPIs.sleepi(15);
        waitElement(upgradeToProBtn);
        if (upgradeToProBtn.isDisplayed()) {
            System.out.println(upgradeToProBtn.getText());
            result = true;
            MyCommonAPIs.sleepi(1);
            upgradeToProBtn.click();
        } else {
            result = false;
        }
        return result;

    }

    public void netgearUpgradetoProPage(Map<String, String> map) {

        MyCommonAPIs.sleepi(15);

        // if (upgradetoProText.isDisplayed()) {
        // System.out.println(upgradetoProText.getText());
        // //System.out.println("Step3: Fill The Upgrade to pro account form.");
        // }
        // MyCommonAPIs.sleepi(2);

        System.out.println("before manageservice");

        managedServiceProvider.click();

        System.out.println("after  manageservice");

        MyCommonAPIs.sleepi(2);
        if (map.containsKey("Buisness Name")) {
            businessName.sendKeys(map.get("Buisness Name"));
        }
        MyCommonAPIs.sleepi(2);
        if (map.containsKey("Primary Address of Business")) {
            businessAddressName.sendKeys(map.get("Primary Address of Business"));
        }
        MyCommonAPIs.sleepi(2);
        if (map.containsKey("City")) {
            businessCity.sendKeys(map.get("City"));
        }
        MyCommonAPIs.sleepi(2);
        if (map.containsKey("State")) {
            businessState.sendKeys(map.get("State"));
        }
        MyCommonAPIs.sleepi(2);
        businesscityZipCode.sendKeys(map.get("Zip Code"));
        MyCommonAPIs.sleepi(2);
        businessCountry.selectOption(map.get("Country"));
        MyCommonAPIs.sleepi(2);
        if (map.containsKey("Business Phone Number")) {
            businessPhoneNumber.sendKeys(map.get("Business Phone Number"));
        }
        MyCommonAPIs.sleepi(10);
        businessUpgradeBtn.click();

        MyCommonAPIs.sleepi(10);

        /*
         * if (businessAccSuccessMsg.isDisplayed()) {
         * System.out.println("Premium to Pro Account Upgrade successful");
         * }
         */
    }

    public boolean verifyOrg(String lic, String OrgName) {
        MyCommonAPIs.sleepi(30);
        Dropdownorg.click();
        boolean result = false;
        String actOnDate = "";
        String expOnDate = "";
        String orderQty = "";
        String orgNameRecived = "";
        ElementsCollection tablerow = $$x("//span[contains(text(), '" + lic + "')]/../..");
        System.out.println("clollection of an element");
        for (SelenideElement ele : tablerow) {
            System.out.println(ele);
            System.out.println("Print the element");
            orgNameRecived = ele.findElement(By.xpath("td[1]")).getText();
            String actOnDateText = ele.findElement(By.xpath("td[4]")).getText();
            String expOnDateText = ele.findElement(By.xpath("td[5]")).getText();
            actOnDate = actOnDateText.substring(actOnDateText.lastIndexOf(",") + 2, actOnDateText.length());
            expOnDate = expOnDateText.substring(expOnDateText.lastIndexOf(",") + 2, expOnDateText.length());
            System.out.println(actOnDate);
            System.out.println(expOnDate);

            break;
        }

        if (((Integer.valueOf(expOnDate) - Integer.valueOf(actOnDate)) == 5) && orgNameRecived.contains(orgNameRecived)) {
            result = true;
            logger.info("Order history display correct.");
        }

        return result;
    }

    public void AddKeyAndVerify1(String LicenceKey, String typeofOrg, int value) {

        if (accountmanager.exists()) {
            accountmanager.click();
        }
        waitReady();
        subscriptions.click();
        MyCommonAPIs.sleepi(5);
        AddProKey.click();
        MyCommonAPIs.sleepi(10);
        if (typeofOrg.equals("Account")) {
            System.out.println("account is alredy clicked");
        } else {
            System.out.println("org is alredy clicked");
            orglicense.click();

        }      
        if (value==0) {
            System.out.println("Immediate is alredy clicked");
        } else {
            System.out.println("schedule is alredy clicked");
            schedule.click();
            System.out.println(java.time.LocalDate.now());       
            
            LocalDate Localday = java.time.LocalDate.now();       
            String strDate = Localday.toString();
            String todayDate =strDate.substring(8);
            System.out.println(todayDate);
            String check = todayDate.substring(0,1);
            System.out.println(check);
            if(check.equals("0")) {          
            todayDate = todayDate.substring(1);
            System.out.println(todayDate);
            }
            
            selectDate.click();
            selcetDate(todayDate).click();
            MyCommonAPIs.sleepi(3);
            selectTime.click();
            increseTime.doubleClick();
            increseTimeok.click();
//            addSchedule.click();
        }
        AddProLicense.sendKeys(LicenceKey);
        MyCommonAPIs.sleepi(10);
        ClickAdd.click();
        MyCommonAPIs.sleepi(10);
        if(ClickAddok.isDisplayed()) {
        ClickAddok.click();
        }
        System.out.println("license added sucessfully");

    }
    
    
    public void AddKeyAndVerify1Sch(String LicenceKey, String typeofOrg, int value) {

        if (accountmanager.exists()) {
            accountmanager.click();
        }
        waitReady();
        subscriptions.click();
        MyCommonAPIs.sleepi(5);
        AddProKey.click();
        MyCommonAPIs.sleepi(10);
        if (typeofOrg.equals("Account")) {
            System.out.println("account is alredy clicked");
        } else {
            System.out.println("org is alredy clicked");
            orglicense.click();

        }      
        if (value==0) {
            System.out.println("Immediate is alredy clicked");
        } else {
            System.out.println("schedule is alredy clicked");
            schedule.click();
            System.out.println(java.time.LocalDate.now());       
            
            LocalDate Localday = java.time.LocalDate.now();       
            String strDate = Localday.toString();
            String todayDate =strDate.substring(8);
            System.out.println(todayDate);
            String check = todayDate.substring(0,1);
            System.out.println(check);
            if(check.equals("0")) {          
            todayDate = todayDate.substring(1);
            System.out.println(todayDate);
            }
            
            selectDate.click();
            selcetDate(todayDate).click();
            MyCommonAPIs.sleepi(5);
            selectTime.click();
            MyCommonAPIs.sleepi(5);
            increseTime.doubleClick();
            MyCommonAPIs.sleepi(5);
            increseTimeok.click();
            
//            addSchedule.click();
        }
        AddProLicense.sendKeys(LicenceKey);
        MyCommonAPIs.sleepi(5);
        ClickAdd.click();
        MyCommonAPIs.sleepi(5);
        if(ClickAddok.isDisplayed()) {
        ClickAddok.click();
        }
        System.out.println("license added sucessfully");
        MyCommonAPIs.sleepi(180);

    }
    

    public void AddKeyAndVerify1(String LicenceKey, String typeofOrg, String orgName) {

        if (accountmanager.exists()) {
            accountmanager.click();
        }
        waitReady();
        subscriptions.click();
        MyCommonAPIs.sleepi(5);
        AddProKey.click();
        MyCommonAPIs.sleepi(20);
        if (typeofOrg.equals("Account")) {
            System.out.println("account is alredy clicked");
        } else {
            System.out.println("org is alredy clicked");
            orglicense.click();
            SelectOrg.selectOptionContainingText(orgName);

        }
        AddProLicense.sendKeys(LicenceKey);
        MyCommonAPIs.sleepi(5);
        ClickAdd.click();
        MyCommonAPIs.sleepi(5);
        ClickAddok.click();
        System.out.println("license added sucessfully");

    }

    public boolean verifyAllColoumnAccout() {

        boolean result = false;

        if (PrchaseconformationkeyAccout.isDisplayed() && SKUAccout.isDisplayed() && ActivationAccount.isDisplayed()
                && ExpirationAccount.isDisplayed() && CreditAccount.isDisplayed()) {

            System.out.println("allelements are present");
            result = true;
        }

        return result;

    }

    public boolean verifyAllColoumnOrg() {

        boolean result = false;

        if (PrchaseconformationkeyOrg.isDisplayed() && SKUOrg.isDisplayed() && ActivationOrg.isDisplayed() && ExpirationOrg.isDisplayed()
                && CreditOrg.isDisplayed()) {

            System.out.println("allelements are present");
            result = true;
        }

        return result;

    }

    public void closeTheWindow() {
        MyCommonAPIs.sleepi(10);
        closeTheWindow.click();
        MyCommonAPIs.sleepi(10);
    }

    String mailId;

    public void accountInfo() {
        MyCommonAPIs.sleepi(2);
        accountManagement1.click();
        MyCommonAPIs.sleepi(5);
        mailId = accountMailId.getText();
        System.out.println(mailId);
        MyCommonAPIs.sleepi(5);
    }

    /*
     * public boolean verifyMailinatorMail () {
     * boolean result = false;
     * String mailnitorWebPage = mailinatorSearchBoxText.getAttribute("placeholder");
     * MyCommonAPIs.sleepi(5);
     * System.out.println(mailnitorWebPage);
     * MyCommonAPIs.sleepi(2);
     * maillinatorEnterEmail.sendKeys(mailId);
     * MyCommonAPIs.sleepi(1);
     * mailinatorGoButton.click();
     * MyCommonAPIs.sleepi(10);
     * clickonMail1.click();
     * MyCommonAPIs.sleepi(5);
     * String mailBody = mailMemoCnfm.getText();
     * if (mailBody.
     * equals(" Thank you for choosing NETGEAR. We're glad to have you on board. Please confirm your email address ")) {
     * result = true;
     * }
     * System.out.println(mailBody);
     * MyCommonAPIs.sleepi(5);
     * 
     * return result;
     * }
     */

    public boolean secondarymanager(String option) {
        boolean result = false;

        if (option.contains("checkManager")) {
            if (checkemailtitle.exists()) {
                checkemailtitle.click();
                MyCommonAPIs.sleepi(3);
                open(getowneraccounturl.getAttribute("href"));
                waitElement(ownerconfirmemail);
            }
        }
        return result;

    }

    public boolean checkMUBpayment() {

        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if (MonthlyPaymentMethod.isDisplayed()) {
            result = true;
            System.out.println("it is not enabled ");
        }

        return result;
    }

    public void GooMUBcheck() {
        MyCommonAPIs.sleepi(5);
        accountmanager.click();
        MyCommonAPIs.sleepi(20);
        MUB.hover();
        MyCommonAPIs.sleepi(1);
        MUB.click();
        MyCommonAPIs.sleepi(10);
    }

    public String checkUserLoggedInn() {
        String str = "";

        MyCommonAPIs.sleepi(5);
        str = AccountType.getText();
        System.out.println(str);
        return str;
    }

    // Code Added By Pratik

    public boolean freeTrail(Map<String, String> map) {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        String name = btnFreeTrail.getText();
        if (btnFreeTrail.exists()) {
            result = true;
        }
        System.out.println(name);
        MyCommonAPIs.sleepi(2);
        btnFreeTrail.click();
        MyCommonAPIs.sleepi(5);
        inputBusinessInfo(map);
        clickBusinessInfoPageButton();
        logger.info("Sign up success.");
        waitReady();
        MyCommonAPIs.sleepsync();
        return result;
    }

    public boolean addingOneYearInsightDevice(Map<String, String> map) {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        String name = btnAddingOneYearInsightDevice.getText();
        if (btnAddingOneYearInsightDevice.exists()) {
            result = true;
        }
        System.out.println(name);
        MyCommonAPIs.sleepi(2);
        btnAddingOneYearInsightDevice.click();
        MyCommonAPIs.sleepi(5);
        inputBusinessInfo(map);
        clickBusinessInfoPageButton();
        logger.info("Sign up success.");
        waitReady();
        MyCommonAPIs.sleepsync();
        return result;
    }

    public void AddKeyAndVerifyICP(String LicenceKey) {

        if (accountmanager.exists()) {
            accountmanager.click();
        }
        waitReady();
        captiveportalservices.click();
        MyCommonAPIs.sleepi(5);
        AddICPKey.click();
        MyCommonAPIs.sleepi(20);
        AddICPLicenseKey.sendKeys(LicenceKey);
        MyCommonAPIs.sleepi(10);
        ClickAddICP.click();
        MyCommonAPIs.sleepi(5);
        if (ClickAddok.exists()) {
            ClickAddok.click();
        }
        System.out.println("license added sucessfully");
    } 

    public String checkUnsupportedCountry1() {
        String result = "";

        accountmanager.click();
        MyCommonAPIs.sleepi(5);
        closeLockedDialog();
        subscriptions.click();
        MyCommonAPIs.sleepi(20);
        if ($x("(//h3[text()='N/A'])[1]").exists() || $x("(//*[@id=\"myModal\"]/div/div/div[1]/h4)[2]").exists()) {
            logger.info("This is a account of unsupported country.");
            result = "Unsupported";
            if (okbutton.isDisplayed()) {
                okbutton.click();
            }

        }
        return result;
    }

    public boolean deleteNotificationsfromProAcount() {

        boolean result = false;
        MyCommonAPIs.sleepi(40);
        String noofnotificationsProAcc = "";

        if (proAccNoOfNotification.exists()) {
            noofnotificationsProAcc = proAccNoOfNotification.getText();
            System.out.println("Number of Pro Account Notifications are " + noofnotificationsProAcc);
            proAccNotificationIcon.click();
            MyCommonAPIs.sleepi(2);
        }

        seeAllBtn.click();
        MyCommonAPIs.sleepi(10);

        int number = Integer.parseInt(noofnotificationsProAcc);
        if (number >= 1) {
            num = number;
        }
        System.out.println(num);

//        for (int i = num; i > 0; i--) {
//            firstNotification.click();
//            MyCommonAPIs.sleepi(2);
//            deleteNotificationBtn.click();
//            // MyCommonAPIs.sleepi(1);
//            // if(msgNotificationDeleted.exists()) {
//            MyCommonAPIs.sleepi(5);
//            dashBoardPageProAcc.click();
//            MyCommonAPIs.sleepi(10);
//            proAccNotificationIcon.click();
//            MyCommonAPIs.sleepi(5);
//            seeAllBtn.click();
//            MyCommonAPIs.sleepi(20);
//            System.out.println(i);
//            // }
//        }
        
        selectAllNoti.click();
        MyCommonAPIs.sleepi(5);
        selectCheckBox.click();
        MyCommonAPIs.sleepi(6);
        deleteNoti.click();
        MyCommonAPIs.sleepi(5);

        String finalMsg = "";
        if (msgDontHaveAnyNotification.exists()) {
            finalMsg = msgDontHaveAnyNotification.getText();
            System.out.println(finalMsg);
            result = true;
            MyCommonAPIs.sleepi(5);
        }

        return result;

    }

    public void proAccLogout() {
        MyCommonAPIs.sleepi(10);
        proAccLogout.click();
        MyCommonAPIs.sleepi(5);
    }

    public boolean deleteNotificationsfromPremiumAcount() {

        boolean result = false;
        MyCommonAPIs.sleepi(60);
        String noofnotificationsProAcc = "";

        if (premiumAccNoOfNotification.exists()) {
            noofnotificationsProAcc = premiumAccNoOfNotification.getText();
            System.out.println("Number of Pro Account Notifications are " + noofnotificationsProAcc);
            premiumAccNotificationIcon.click();
            MyCommonAPIs.sleepi(2);
        }

        seeAllBtn.click();
        MyCommonAPIs.sleepi(10);

        int number = Integer.parseInt(noofnotificationsProAcc);
        if (number >= 1) {
            num1 = number;
        }
        System.out.println(num1);

//        for (int i = num1; i > 0; i--) {
//
//            firstNotification.click();
//            MyCommonAPIs.sleepi(2);
//            deleteNotificationBtn.click();
//            // MyCommonAPIs.sleepi(1);
//            // if(msgNotificationDeleted.exists()) {
//            MyCommonAPIs.sleepi(5);
//            locationDashboardPremiumAcc.click();
//            MyCommonAPIs.sleepi(10);
//            premiumAccNotificationIcon.click();
//            MyCommonAPIs.sleepi(5);
//            seeAllBtn.click();
//            MyCommonAPIs.sleepi(20);
//            System.out.println(i);
//            // }
//        }
        
        selectAllNoti.click();
        MyCommonAPIs.sleepi(5);
        selectCheckBox.click();
        MyCommonAPIs.sleepi(5);
        deleteNoti.click();
        MyCommonAPIs.sleepi(5);
        
        

        MyCommonAPIs.sleepi(5);
        String finalMsg = "";
        if (msgDontHaveAnyNotification.exists()) {
            finalMsg = msgDontHaveAnyNotification.getText();
            System.out.println(finalMsg);
            result = true;
            MyCommonAPIs.sleepi(5);
        }

        return result;

    }

    

    public void deallocateICPCredit(String name, String icpNum) {
        gotoCreditsAllocationPage();
        deallocate.click();
        waitReady();
        if (!icpNum.equals("0")) {
            selectDeallocateIcpCredits.click();
            MyCommonAPIs.sleepi(3);
            clickButton(3);
            $x(String.format(selectOrgDeallocate, name)).click();
            MyCommonAPIs.sleepi(3);
            $x(String.format(inputDeallocateCredits, name)).setValue(icpNum);
            MyCommonAPIs.sleepi(3);
            allocateButton.click();
            waitReady();
        }
    }

   


    // public boolean checkdownlaodInvoice() {
    // boolean result = false;
    // waitReady();
    // closeLockedDialog();
    // accountmanager.click();
    // MyCommonAPIs.sleepi(10);
    // subscriptions.click();
    // MyCommonAPIs.sleepi(10);
    // if(downlaodinvoice.isDisplayed())
    // {
    // result = true;
    // }
    // return result;
    // }

    // public void downlaodInvoice() {
    // waitReady();
    // accountmanager.click();
    // MyCommonAPIs.sleepi(10);
    // subscriptions.click();
    // MyCommonAPIs.sleepi(10);
    // downlaodinvoice.click();
    // MyCommonAPIs.sleepi(20);
    // SelectYear.selectOption(1);
    // Selectinvoice.selectOption(1);
    // MyCommonAPIs.sleepi(20);
    // downlaodinvoiceok.click();
    // MyCommonAPIs.sleepi(50);
    //
    // }

    
//    public boolean checkdownlaodInvoice() {
//        boolean result = false;      
//        waitReady();
//        closeLockedDialog();
//        accountmanager.click();
//        MyCommonAPIs.sleepi(10);
//        subscriptions.click();
//        MyCommonAPIs.sleepi(10);
//        if(downlaodinvoice.isDisplayed())
//        {
    
    //addedbyPratik
    
    public boolean addLocationsToOrg(String organizationName) {
        boolean result = false;
        MyCommonAPIs.sleepi(20);
        if (verifyLocafterMigration.exists() || verifyLocafterMigration1.exists()) {
            addlocationtoNewOrgCheckbox.click();
            MyCommonAPIs.sleepi(2);
            btnAssigntoOrg.click();
            MyCommonAPIs.sleepi(10);
            inputNewOrgName.setValue(organizationName);
            MyCommonAPIs.sleepi(2);
            submitButton.click();
            MyCommonAPIs.sleepi(20);
            if (locationAddedSuccessMsg.exists()) {
                okayButton.click();
                System.out.println(locationAddedSuccessMsg);
                logger.info("Location Successfully added to new created orgnizqation");
                result = true;
            }
        } 
        return result;
    }
    
    public boolean verifyCancelledDeviceCreditSubscriptionsinProAcc() {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        waitElement(insightpremiumSubTitle);
        if (insightpremiumSubTitle.exists()) {
            pluIcon1PurchaseHistory.click();
            MyCommonAPIs.sleepi(2);
            waitElement(insightPremiumAnnualSubscription);
            String temp = insightPremiumAnnualSubscription.getText();
            System.out.println(temp);
            if (insightPremiumAnnualSubscription.exists() && cancelledSubscription1.exists()) {
                pluIcon2PurchaseHistory.click();
                MyCommonAPIs.sleepi(2);
                waitElement(insightPremiumMultipackSub);
                String temp1 = insightPremiumMultipackSub.getText();
                System.out.println(temp1);
                if (insightPremiumMultipackSub.exists() && cancelledSubscription2.exists()) {
                    result = true;
                    logger.info("Premium Subscriptions are cancelled in Pro Account Automatically");
                }
            }
        } else {
            logger.info("Premium Subscriptions are not cancelled in Pro Account Automatically");
        }
        
        return result;
    }
    
    public boolean verifyUpgradeToproInPurhaseHstory() {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        waitElement(upgradeToPro);
        if (upgradeToPro.isDisplayed()) {
            System.out.println(upgradeToPro.getText());
            //OrganizationSettings.click();
            result = true;
            MyCommonAPIs.sleepi(1);
        } else {
            result = false;
        }
        return result;
    }

    

    
//    public void downlaodInvoice() {   
//        waitReady();
//        accountmanager.click();
//        MyCommonAPIs.sleepi(10);
//        subscriptions.click();
//        MyCommonAPIs.sleepi(10);
//        downlaodinvoice.click();
//        MyCommonAPIs.sleepi(20);
//        SelectYear.selectOption(1);
//        Selectinvoice.selectOption(1);
//        MyCommonAPIs.sleepi(20);
//        downlaodinvoiceok.click();
//        MyCommonAPIs.sleepi(50);
//        
//    }
    public void premiumVPNServicesStartDateEndDate() {
        MyCommonAPIs.sleepi(10);
        premiumStartDateSubs = startDateVPNSub.getText();
        premiumEndDate       = endDateVPNSub.getText();
        System.out.println(premiumStartDateSubs);
        System.out.println(premiumEndDate);
        logger.info("Premium Account: Start Date and End Date Stored");
    }
    
    public boolean proVPNServicesStartDateEndDate() {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        proStartDateSubs = startDateVPNSub.getText();
        proEndDate       = endDateVPNSub.getText();
        System.out.println(proStartDateSubs);
        System.out.println(proEndDate);
        logger.info("Premium Account: Start Date and End Date Stored");
        if ((premiumStartDateSubs==proStartDateSubs) && (premiumEndDate==proEndDate)) {
            result = true;
            logger.info(premiumStartDateSubs+"="+proStartDateSubs+"==== Starting Dates are same");
            logger.info(premiumEndDate+"="+proEndDate+"==== End Dates are same");
            System.out.println(premiumStartDateSubs+"="+proStartDateSubs+"==== Starting Dates are same");
            System.out.println(premiumEndDate+"="+proEndDate+"==== End Dates are same");
        } else {
            logger.info("Both Dates are not same.");
        }
        
        return result;
    }
    
    public boolean verifyUpgradeToproMessgae() {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        waitElement(upgradeToPro);
        if (upgradeToPro.isDisplayed()) {
            System.out.println(upgradeToPro.getText());
            upgradeToPro.click();
            MyCommonAPIs.sleepi(10);
            if (upgradeToProMessgae.exists() && upgradeToProMessgae1.exists()) {
                logger.info("Upgrade To Pro Subscription Message is shwon....");
                result = true;
            }
        } else {
            result = false;
        }
        return result;
    }
    
    public boolean verifyLicenceKeyPageLink() {
        boolean result = false;
        waitElement(inputproaccountkey);
        if (inputproaccountkey.isDisplayed()) {
            if (licensePageLink.exists()) {
                result = true;
                logger.info("Where can I buy a Pro key? Link is present on license page.");
            } else {
                logger.info("Where can I buy a Pro key? Link is not present on license page.");
            }
        }
        return result;
    }
    
    public boolean verifyWherecanIbuyaProkeyURL() {
        WebDriver driver;
        List<String> tabs;
        boolean result = false;
        waitElement(inputproaccountkey);
        
        if (inputproaccountkey.isDisplayed()) {
            
            if (licensePageLink.exists()) {
                logger.info("Where can I buy a Pro key? Link is present on license page.");
                licensePageLink.click();
                MyCommonAPIs.sleepi(10);
                
                driver = WebDriverRunner.getWebDriver();
                tabs = new ArrayList<>(driver.getWindowHandles());
                driver.switchTo().window(tabs.get(1));
                MyCommonAPIs.sleepi(10);
                //waitElement(cookiesClose);
                //cookiesClose.click();
                System.out.println(cookiesClose);
                
                if (whereToBuYHeaderonnewURL.exists() && verifyParagraphonlandedURL.exists()) {
                    
                    System.out.println(whereToBuYHeaderonnewURL.getText());
                    System.out.println(verifyParagraphonlandedURL.getText());
                    String expectedURL = "https://www.netgear.com/business/where-to-buy/";
                    String currentUrl = WebDriverRunner.url();
                    System.out.println(expectedURL);
                    System.out.println(currentUrl);
                    MyCommonAPIs.sleepi(5);
                    
                    if (expectedURL.equals(currentUrl)) {
                        
                        result = true;
                        MyCommonAPIs.sleepi(5);
                        logger.info("Where can I buy a Pro key URL is launched");
                        
                    } 
                } 
                driver.switchTo().window(tabs.get(0));
                MyCommonAPIs.sleepi(2);
                WebDriverRunner.getWebDriver().navigate().back();
                MyCommonAPIs.sleepi(2);
            } 
        }  else {
            logger.info("Existed in outer loop: Where can I buy a Pro key? Link is not present on license page.");
        }
        
        return result;
    }
    
    public boolean verifyAndDochangeEmail(Map<String, String> map) {
        boolean result = false;
        updateprofile.click();
        MyCommonAPIs.sleepi(10);
        if (cancelbutton.exists()) {
            cancelbutton.click();
        } else {
            refresh();
            MyCommonAPIs.sleepi(10);
            if (cancelbutton.exists()) {
                cancelbutton.click();
            }
        }
        loginsettings.click();
        MyCommonAPIs.sleepi(5);
        logger.info("Start change email...");
        changeemail.click();
        waitElement(newemail);
        newemail.sendKeys(map.get("New Email Name"));
        confirmmail.sendKeys(map.get("New Email Name"));
        currentpassword.sendKeys(map.get("Password"));
        MyCommonAPIs.sleepi(2);
        submitchangeemail.click();
        MyCommonAPIs.sleepi(20);
//        WebCheck.checkHrefIcon(URLParam.hrefDevices);
//        if (okconformation.exists()) {
//
//            okconformation.click();
//        }
//        if (Invalidsession.exists()) {
//            Invalidsession.click();
//        }
         waitElement($x("//span[text()='Login']"));
         MyCommonAPIs.sleepi(20);
         String url = MyCommonAPIs.getCurrentUrl();
         if (url.contains("/account?") || !url.equals(WebportalParam.serverUrlLogin)) {
         result = true;
         logger.info("Change email has been done successfully.");
         } else {
         waitElement($x("//span[text()='Login']"));
         }
         return result;
    }
   

    
    public void downlaodInvoice() {   
        waitReady();
        accountmanager.click();
        MyCommonAPIs.sleepi(10);
        subscriptions.click();
        MyCommonAPIs.sleepi(10);
        downlaodinvoice.click();
        MyCommonAPIs.sleepi(20);
        SelectYear.selectOption(1);
        Selectinvoice.selectOption(1);
        MyCommonAPIs.sleepi(20);
        downlaodinvoiceok.click();
        MyCommonAPIs.sleepi(50);
    }


    
    public boolean checkdownlaodInvoice() {
        boolean result = false;      
        waitReady();
        closeLockedDialog();
        accountmanager.click();
        MyCommonAPIs.sleepi(10);
        subscriptions.click();
        MyCommonAPIs.sleepi(10);
        if(downlaodinvoice.isDisplayed())
        {
            result = true;
        }
        return result;
    }
 
    public boolean checkDeviceCount(String LocName,String OrgName) {
        OrganizationPage OrganizationPage = new OrganizationPage();
        boolean result =false;
        String res="";
        String res1="";
        OrganizationPage.gotoPage();
        res1 = devicecount1(OrgName).getText();
        MyCommonAPIs.sleepi(30);
        OrganizationPage.openOrg(OrgName); 
        res = devicecount(LocName).getText();
        MyCommonAPIs.sleepi(2);
        System.out.println(res+"/t"+res1);
        if (!res.equals("") && res.contains("6") && !res1.equals("") && res1.contains("6")) {
            result = true;
        }
        return result;
    }
    
    public void setNewOrg(String orgName) {
        MyCommonAPIs.sleepi(5);
        newOrgName.sendKeys(orgName);
        MyCommonAPIs.sleepi(1);
        SubmitByText.click();
        MyCommonAPIs.sleepi(2);
        OKassignToOrg.click();
    }
    
 // added by vivek
    public void openAccountMgmt() {
        MyCommonAPIs.sleepi(2);
        accountmanager.click();
        MyCommonAPIs.sleepi(5);
    }

    // added by vivek
    public void EnterlmsKey() {
        MyCommonAPIs.sleepi(2);
        subscriptions.click();
        MyCommonAPIs.sleepi(3);
        AddPurchaseConfirmationKey.click();
        String write = new HamburgerMenuPage(false).readLicenceKeyByTxt("Write");
        WriteLMSKey.setValue(write);
        MyCommonAPIs.sleepi(5);
        accountOpt.click();
        MyCommonAPIs.sleepi(5);
        clickonAddLMSKey.click();
        MyCommonAPIs.sleepi(5);
        LicenseOkButton.click();
        MyCommonAPIs.sleepi(5);
    }

    // added by vivek
    public void OpenCreditAllocationPage() {
        CreditAllocation.click();
        MyCommonAPIs.sleepi(5);
        CreditAllocateIcon.click();
        MyCommonAPIs.sleepi(3);
    }

    // added by vivek
    public void clickOnAllocateButtonforsave() {
        MyCommonAPIs.sleepi(2);
        allocateButton.click();
        MyCommonAPIs.sleepi(5);

    }

    // added by vivek
    public void openHashRigisterUrlAndSetEmailId(String Email, String pwd) {
        new MyCommonAPIs().open(URLParam.registerPro, true);
        MyCommonAPIs.sleepi(3);
        AccountSugnUpEmail.sendKeys(Email);
        MyCommonAPIs.sleepi(1);
        ClickOnnext.click();
        MyCommonAPIs.sleepi(5);
        loginPwdNew.sendKeys(pwd);
        loginButton.click();
    }

    // added by vivek
    public void enterProPurchaseConfirmationKey(String licenseKey) {
        MyCommonAPIs.sleepi(1);
        inputlicenseKey.sendKeys(licenseKey);
        MyCommonAPIs.sleepi(1);
        NextBtnByText.click();
        MyCommonAPIs.sleepi(3);
    }
    
 // added by Vivek
    public boolean VerifyOrganizationSettingsisNotPresent() {
        boolean result = true;
        MyCommonAPIs.sleepi(2);
        if (LocationSettings.exists())
            ;
        {
            result = false;
        }
        return result;
    }

    // added by Vivek
    public ArrayList<String> VerifyAllOrgisPresentinOrganizationSettings() {
        ArrayList<String> OrgList = new ArrayList<String>();
        for (SelenideElement org : listOfOrg) {
            OrgList.add(org.text());
        }

        return OrgList;
    }

    // added by Vivek
    public ArrayList<String> VerifyAllLocationPresentinOrganizationSettings() {
        ArrayList<String> LocationList = new ArrayList<String>();
        for (SelenideElement ntwrk : listOfntwrk) {
            LocationList.add(ntwrk.text());
        }

        return LocationList;
    }

    // added by Vivek
    public void clickingOnPlusIcon() {
        MyCommonAPIs.sleepi(2);
        for (SelenideElement plusIcon : listOfplusIcon) {
            MyCommonAPIs.sleepi(1);
            plusIcon.click();
        }
    }

    // added by Vivek
    public void clickingOnFirstCheckBox() {
        MyCommonAPIs.sleepi(5);
        firstcheckBox.click();
        MyCommonAPIs.sleepi(3);
        SaveOrgSetting.click();
        MyCommonAPIs.sleepi(3);
    }

    // added by Vivek
    public boolean VerifyChangesAfterSave() {
        boolean result = false;
        MyCommonAPIs.sleepi(1);
        System.out.println(firstcheckBoxValue.getValue());
        if (firstcheckBoxValue.getValue().contains("0"));{
            result = true;
        }
        
        System.out.println("Varifying CheckBoxes Are Unchecked");
        return result;
    }

    // added by Vivek
    public void clickingOnAllCheckBox() {
        MyCommonAPIs.sleepi(5);
        for (SelenideElement checkbox : listOfcheckbox) {
            MyCommonAPIs.sleepi(1);
            checkbox.click();
        }
        MyCommonAPIs.sleepi(3);
        SaveOrgSetting.click();
        MyCommonAPIs.sleepi(3);
    }

    // added by Vivek
    public boolean VerifyAllChekBoxAreUnchecked() {
        boolean result = false;
        for (SelenideElement checkBoxvalue : listOfcheckboxValue) {
            MyCommonAPIs.sleepi(1);
            System.out.println(checkBoxvalue.getValue());
            if (checkBoxvalue.getValue().contains("0"))
                ;
            {
                result = true;
            }
        }
        System.out.println("Varifying CheckBoxes Are Unchecked");
        return result;
    }

 // Added by vivek
    public boolean checkEmailMessageForDeviceReboot(String mailname) {
        boolean result = false;
        logger.info("Check email address is:" + mailname);
        open("https://yopmail.com/");
        MyCommonAPIs.sleepi(5);
        String inputElement = "//input[@id='login']";
        $x(inputElement).clear();
        $x(inputElement).sendKeys(mailname);
        $x("//button[@title='Check Inbox @yopmail.com']").click();
        SelenideElement frame = $("iframe[name='ifinbox']");
        Selenide.switchTo().frame(frame);
        MyCommonAPIs.sleepsync();
        System.out.println(checkemailtitle.getText());
        if (checkemailtitle.getText().contains("Device Reboot")) {
            result = true;
            logger.info("Received Device Reboot Notification email.");
        }
        return result;
    }

    // Added by vivek
    public boolean checkEmailMessageForDeviceOnline(String mailname) {
        boolean result = false;
        logger.info("Check email address is:" + mailname);
        open("https://yopmail.com/");
        MyCommonAPIs.sleepi(5);
        String inputElement = "//input[@id='login']";
        $x(inputElement).clear();
        $x(inputElement).sendKeys(mailname);
        $x("//button[@title='Check Inbox @yopmail.com']").click();
        SelenideElement frame = $("iframe[name='ifinbox']");
        Selenide.switchTo().frame(frame);
        MyCommonAPIs.sleepsync();
        System.out.println(checkemailtitle.getText());
        if (checkemailtitle.getText().contains("Device Online")) {
            result = true;
            logger.info("Received Device Online Notification email.");
        }
        return result;

    }
    
 // added by Vivek
    public boolean VerifyLocationSectionShouldNotDisplayed() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        if (locationText.exists()) {
            result = true;
        }
        return result;
    }
    
 // added by Vivek
    public void OpenManageNotificationPreferences() {
        MyCommonAPIs.sleepi(2);
        accountManagement1.click();
        MyCommonAPIs.sleepi(2);
        managenotificationsnew.click();
        MyCommonAPIs.sleepi(5);
    }

    // added by Vivek
    public boolean VerifyOrganizationSettingsisPresent() {
        boolean result = false;
        waitElement(OrganizationSettings);
        if (getText(OrganizationSettings).contains("Organization Settings"))
           ;
        {
            result = true;
            OrganizationSettings.click();
        }
        return result;
    }
    
    // added by vivek
    public void openHashRigisterUrlAndOpenProInfoForm(String Email) {
        new MyCommonAPIs().open(URLParam.registerPro, true);
        MyCommonAPIs.sleepi(3);
        new MyCommonAPIs().open(URLParam.registerPro, true);
        MyCommonAPIs.sleepi(3);
        AccountSugnUpEmail.sendKeys(Email);
        MyCommonAPIs.sleepi(1);
        ClickOnnext.click();
        MyCommonAPIs.sleepi(5);
    }

    // added by vivek
    public void selectLocationAndSetNewOrg() {
        MyCommonAPIs.sleepi(5);
        locationSelectCheckBox.click();
        MyCommonAPIs.sleepi(2);
        AssigntoOrganization.click();
        MyCommonAPIs.sleepi(5);
    }
    
    // added by Anusha H
    public void AddKeyAndVerifyVPN(String LicenceKey) {

        if (accountmanager.exists()) {
            accountmanager.click();
        }
        waitReady();
        vpnservices.click();
        MyCommonAPIs.sleepi(5);
        AddVPNKey.click();
        MyCommonAPIs.sleepi(15);
        AddICPLicenseKey.sendKeys(LicenceKey);
        MyCommonAPIs.sleepi(10);
        ClickAddICP.click();
        MyCommonAPIs.sleepi(5);
        if (ClickAddok.exists()) {
            ClickAddok.click();
        }
        System.out.println("license added sucessfully");
    }
    
    public boolean verifyVPN(String lic) {
        MyCommonAPIs.sleepi(10);
        vpnservicespage.click();
        MyCommonAPIs.sleepi(10);
        boolean result = false;
        String actOnDate = "";
        String expOnDate = "";
        String orderQty = "";
        ElementsCollection tablerow = $$x("//span[contains(text(), '" + lic + "')]/../..");
        System.out.println("clollection of an element");
        for (SelenideElement ele : tablerow) {
            System.out.println(ele);
            System.out.println("Print the element");
            String actOnDateText = ele.findElement(By.xpath("td[2]")).getText();
            String expOnDateText = ele.findElement(By.xpath("td[3]")).getText();
            System.out.println(actOnDateText);
            System.out.println(expOnDateText);
            actOnDate = actOnDateText.substring(actOnDateText.lastIndexOf(",") + 2, actOnDateText.length());
            expOnDate = expOnDateText.substring(expOnDateText.lastIndexOf(",") + 2, expOnDateText.length());
            System.out.println(actOnDate);
            System.out.println(expOnDate);

            break;
        }

        if (((Integer.valueOf(expOnDate) - Integer.valueOf(actOnDate)) == 5)) {
            result = true;
            logger.info("Order history display correct.");
        }

        return result;
    }
    public boolean GotoProAccount(String Email) {
        boolean result=true;
        String wpLoginURL   = WebportalParam.serverUrlLogin;
        open(wpLoginURL);
        long t= System.currentTimeMillis();
        long end = t+12000;
        System.out.println("going inside while");
        while(System.currentTimeMillis() < end) {
            System.out.println("inside while");
            Selenide.refresh();
            if(loginNowButton.exists()) {
              loginNowButton.click();
              break;
            }
            MyCommonAPIs.sleepi(10);
        }         
        MyCommonAPIs.sleepi(5);
        if (createaccount.exists()) {
            createaccount.click();
        }
        else
        {
            createaccountcognito.click();
        }
        
        MyCommonAPIs.sleepi(10);
        if(SignUpPro.exists()){
            SignUpPro.click();
            result=true;
        }
        else {
            SignUpProCognito.click();
            result=true;
            
        }
        MyCommonAPIs.sleepi(10);
        addemailprohardbundle.sendKeys(Email);
        MyCommonAPIs.sleepi(4);
        nextBtnPro.click();
        return result;
    }
    
 // added by vivek
    public void gotoInsightSubscriptionsForPro() {
        MyCommonAPIs.sleepi(2);
        accountmanager.click();
        MyCommonAPIs.sleepi(2);
        subscriptions.click();
        MyCommonAPIs.sleepi(3);
    }
    
    // added by vivek
    public void clickOnCreditCountUnderPurchaseKeys() {
        MyCommonAPIs.sleepi(2);
        creditCountpro.click();
        MyCommonAPIs.sleepi(2);
        
    }
    
    // added by Vivek
    public boolean VerifyPopUpScreenisOpeing() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        if (creditDetailsText.isDisplayed());
        {
            result = true;
        }
        return result;
    }
    
    // added by Vivek
    public boolean VerifyCreditAllocationTxtVisible() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        if (CrditAllocationText.isDisplayed());
        {
            result = true;
        }
        return result;
    }
    
    // added by Vivek
    public boolean VerifyCreditAllocationAllDataAreVisible() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        if (OrgTextCredit.isDisplayed() &  ManagerTextCredit.isDisplayed() &  CrditTextunderPopUp.isDisplayed());
        {
            result = true;
        }
        return result;
    }
    
    // added by Vivek
    public boolean VerifyTheErrorMsgWhileCreateEnterpriseSSID() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        if (ErrorTextForenterprise.isDisplayed());
        {
            result = true;
        }
        return result;
    }
    
    // added by Vivek
    public boolean VerifyTheErrorMsgHaveOneLink() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        if (ErrorTextForenterprise.exists());
        {
            result = true;
        }
        return result;
    }
    
    // added by vivek
    public void expandinsigtdivCreditsSection() {
        expandinsigtdivcredits.click();
        MyCommonAPIs.sleepi(2);
    }
    
 // added by Vivek
    public boolean VerifyTheSearcIconIsThere() {
        boolean result = false;
        MyCommonAPIs.sleepi(1);
        if (iconsearch.exists());
        {
            result = true;
        }
        return result;
    }
    
    // added by Vivek
    public boolean VerifyTheInsightPremiumTrialSubsText() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String subsText = DivCreditSubsText.text();
        if (subsText.contains("Insight Premium Trial subscription for unlimited device credits"));
        {
            result = true;
        }
        return result;
    }
    
    // added by Vivek
    public boolean VerifyTheInsightSubsQuantity() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String subsText = DivCreditSubsQuantity.text();
        if (subsText.contains("Unlimited"));
        {
            result = true;
        }
        return result;
    }
    
 // added by Vivek
    public boolean VerifyTheActivationDateisTodayDate() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String ActDate = DivCreditSubsActivationDate.text();
        MyCommonAPIs.sleepi(1);
        String expiryDate = DivCreditSubsExpiryDate.text();
        System.out.println("Activation on ---");
        System.out.println(ActDate);
        System.out.println("Expires on ---");
        System.out.println(expiryDate);
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
        String date = currentDate.format(formatter); 
        
        if (ActDate.contains(date));
        {
            result = true;
        }
        return result;
    }
    
 // added by vivek
    public void expandHardBundalCreditsSection() {
        MyCommonAPIs.sleepi(2);
        ExpaindHardbundal.click();
        MyCommonAPIs.sleepi(2);
    }
    
    // added by Vivek
    public boolean VerifyHBBInsightPreOneYearSubsText() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String subsText = HardBdlDivCreditSubsText.text();
        if (subsText.contains("Insight Premium 1-year subscription with 1 device bundle credit"));
        {
            result = true;
        }
        return result;
    }
    
 // added by Vivek
    public boolean VerifyTheHBBSubsLocationName(String Location) {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String subsText = HbbLocInfo.text();
        if (subsText.contains(Location));
        {
            result = true;
        }
        return result;
    }
    
 // added by Vivek
    public boolean VerifyTheHBBDivActivationDateisTodayDate() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String ActDate = HBBDivCreditActivationDate.text();
        MyCommonAPIs.sleepi(1);
        String expiryDate = HBBDivCreditExpiryDate.text();
        System.out.println("Activation on ---");
        System.out.println(ActDate);
        System.out.println("Expires on ---");
        System.out.println(expiryDate);
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
        String date = currentDate.format(formatter); 
        
        if (ActDate.contains(date));
        {
            result = true;
        }
        return result;
    }
    
 // added by Vivek
    public boolean VerifyHBBSecondDivInsightPreOneYearSubsText() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String subsText = HardBdlSecDivCreditSubsText.text();
        if (subsText.contains("Insight Premium 1-year subscription with 1 device bundle credit."));
        {
            result = true;
        }
        return result;
    }
    
 // added by Vivek
    public boolean VerifyTheHBSecondBDivSubsLocationName(String Location) {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String subsText = HbbSecondLocInfo.text();
        if (subsText.contains(Location));
        {
            result = true;
        }
        return result;
    }
    
 // added by Vivek
    public boolean VerifyTheHBBSecondDivActivationDateisTodayDate() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String ActDate = HBBSecDivCreditActivationDate.text();
        MyCommonAPIs.sleepi(1);
        String expiryDate = HBBSecDivCreditExpiryDate.text();
        System.out.println("Activation on ---");
        System.out.println(ActDate);
        System.out.println("Expires on ---");
        System.out.println(expiryDate);
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
        String date = currentDate.format(formatter); 
        
        if (ActDate.contains(date));
        {
            result = true;
        }
        return result;
    }
    
 // added by Vivek
    public boolean VerifyHBBThirdDivInsightPreOneYearSubsText() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String subsText = HardBdlThirdDivCreditSubsText.text();
        if (subsText.contains("Insight Premium 1-year subscription with 1 device bundle credit."));
        {
            result = true;
        }
        return result;
    }
    
 // added by Vivek
    public boolean VerifyTheHBThirdBDivSubsLocationName(String Location) {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String subsText = HbbThirdLocInfo.text();
        if (subsText.contains(Location));
        {
            result = true;
        }
        return result;
    }
    
 // added by Vivek
    public boolean VerifyTheHBBThirdDivActivationDateisTodayDate() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String ActDate = HBBThirdDivCreditActivationDate.text();
        MyCommonAPIs.sleepi(1);
        String expiryDate = HBBThirdDivCreditExpiryDate.text();
        System.out.println("Activation on ---");
        System.out.println(ActDate);
        System.out.println("Expires on ---");
        System.out.println(expiryDate);
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
        String date = currentDate.format(formatter); 
        
        if (ActDate.contains(date));
        {
            result = true;
        }
        return result;
    }
    
    // added by vivek
    public void expandICPCreditsSection() {
        MyCommonAPIs.sleepi(1);
        ExpaindICPCreditSection.click();
        MyCommonAPIs.sleepi(2);
    }
    
 // added by Vivek
    public boolean VerifyTheICPSubsText() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String subsText = ICPDivCreditSubsText.text();
        if (subsText.contains("Captive Portal, Pack of 1, 1-yr-USD 12 Months subscription with 1 device credits"));
        {
            result = true;
        }
        return result;
    }
    
    // added by Vivek
    public boolean VerifyICPSubsCreditQuantity() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String subsText = ICPDivCreditSubsQuantity.text();
        if (subsText.contains("1"));
        {
            result = true;
        }
        return result;
    }
    
 // added by Vivek
    public boolean VerifyTheICPActivationAndExpiryDates() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String ActDate = ICPDivCreditSubsActivationDate.text();
        MyCommonAPIs.sleepi(1);
        String expiryDate = ICPDivCreditSubsExpiryDate.text();
        System.out.println("ICP Activation on ---");
        System.out.println(ActDate);
        System.out.println("ICP Expires on ---");
        System.out.println(expiryDate);
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
        String date = currentDate.format(formatter); 
        
        if (ActDate.contains(date));
        {
            result = true;
        }
        return result;
    }
    
 // added by Vivek
    public boolean VerifyTheICPforThreeYearSubsText() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String subsText = ICPDivCreditSubsText.text();
        if (subsText.contains("Captive Portal, Pack of 1, 3-yr-USD 36 Months subscription with 1 device credits"));
        {
            result = true;
        }
        return result;
    }
    
    // added by Vivek
    public boolean VerifyICPforThreeYearCreditQuantity() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String subsText = ICPDivCreditSubsQuantity.text();
        if (subsText.contains("1"));
        {
            result = true;
        }
        return result;
    }
    
 // added by Vivek
    public boolean VerifyICPActivationAndExpiryDats() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String ActDate = ICPDivCreditSubsActivationDate.text();
        MyCommonAPIs.sleepi(1);
        String expiryDate = ICPDivCreditSubsExpiryDate.text();
        System.out.println("ICP Activation on ---");
        System.out.println(ActDate);
        System.out.println("ICP Expires on ---");
        System.out.println(expiryDate);
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
        String date = currentDate.format(formatter); 
        
        if (ActDate.contains(date));
        {
            result = true;
        }
        return result;
    }
    
    // added by Vivek
    public boolean VerifyTheICPforThreeAPOneYearSubsText() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String subsText = ICPDivCreditSubsText.text();
        if (subsText.contains("Captive Portal, Pack of 3, 1-yr-USD 12 Months subscription with 1 device credits"));
        {
            result = true;
        }
        return result;
    }
    
 // added by Vivek
    public boolean VerifyTheICPforThreeAPThreeeYearSubsText() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String subsText = ICPDivCreditSubsText.text();
        if (subsText.contains("Captive Portal, Pack of 3, 3-yr-USD 36 Months subscription with 1 device credits"));
        {
            result = true;
        }
        return result;
    }
    
 // added by Vivek
    public boolean VerifyTheICPforTenAPOneYearSubsText() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String subsText = ICPDivCreditSubsText.text();
        if (subsText.contains("Captive Portal, Pack of 10, 1-yr-USD 12 Months subscription with 1 device credits"));
        {
            result = true;
        }
        return result;
    }
    
 // added by Vivek
    public boolean VerifyTheICPforTenAPThreeYearSubsText() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String subsText = ICPDivCreditSubsText.text();
        if (subsText.contains("Captive Portal, Pack of 10, 3-yr-USD 36 Months subscription with 1 device credits"));
        {
            result = true;
        }
        return result;
    }
    
    
    // added by Vivek
       public boolean VerifyTheICPforFortyAPOneYearSubsText() {
           boolean result = false;
           MyCommonAPIs.sleepi(2);
           String subsText = ICPDivCreditSubsText.text();
           if (subsText.contains("Captive Portal, Pack of 40, 1-yr-USD 12 Months subscription with 1 device credits"));
           {
               result = true;
           }
           return result;
       }
    
 // added by Vivek
    public boolean VerifyTheICPforFortyAPThreeYearSubsText() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String subsText = ICPDivCreditSubsText.text();
        if (subsText.contains("Captive Portal, Pack of 40, 3-yr-USD 36 Months subscription with 1 device credits"));
        {
            result = true;
        }
        return result;
    }
    
 // added by vivek
    public void expandVPNCreditsSection() {
        MyCommonAPIs.sleepi(1);
        ExpaindVPNCreditSection.click();
        MyCommonAPIs.sleepi(2);
    }
    
    
 // added by Vivek
    public boolean VerifyTheVPNforThreeYearSubsText() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String subsText = VPNDivCreditSubsText.text();
        if (subsText.contains("Insight Instant VPN - 1 Year 12 Months subscription with 1 group credits"));
        {
            result = true;
        }
        return result;
    }
    
    // added by Vivek
    public boolean VerifyVPNCreditQuantity() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String subsText = VPNDivCreditSubsQuantity.text();
        if (subsText.contains("1"));
        {
            result = true;
        }
        return result;
    }
    
 // added by Vivek
    public boolean VerifyVPNActivationAndExpiryDats() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String ActDate = VPNDivCreditSubsActivationDate.text();
        MyCommonAPIs.sleepi(1);
        String expiryDate = VPNDivCreditSubsExpiryDate.text();
        System.out.println("ICP Activation on ---");
        System.out.println(ActDate);
        System.out.println("ICP Expires on ---");
        System.out.println(expiryDate);
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
        String date = currentDate.format(formatter); 
        
        if (ActDate.contains(date));
        {
            result = true;
        }
        return result;
    }
    
    // added by Vivek
    public boolean VerifyTheVPNforThreeAPOneYearSubsText() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String subsText = VPNDivCreditSubsText.text();
        if (subsText.contains("Insight Instant VPN - 1 Year 12 Months subscription with 3 group credits"));
        {
            result = true;
        }
        return result;
    }
    
 // added by Vivek
    public boolean VerifyVPNCreditQuantityforThreeDevice() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String subsText = VPNDivCreditSubsQuantity.text();
        if (subsText.contains("3"));
        {
            result = true;
        }
        return result;
    }
    
 // added by Vivek
    public boolean VerifyTheVPNforTenDevicesSubsText() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String subsText = VPNDivCreditSubsText.text();
        if (subsText.contains("Insight Instant VPN - 1 Year 12 Months subscription with 10 group credits"));
        {
            result = true;
        }
        return result;
    }
    
 // added by Vivek
    public boolean VerifyVPNCreditQuantityforTenDevices() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String subsText = VPNDivCreditSubsQuantity.text();
        if (subsText.contains("10"));
        {
            result = true;
        }
        return result;
    }
    
 // added by Vivek
    public boolean VerifyThePreTrialToPreMonthlySubsText() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String subsText = PreMonthlyDivCreditSubsText.text();
        if (subsText.contains("Insight Premium Monthly/Annual Subscription with 2 device credits."));
        {
            result = true;
        }
        return result;
    }
    
    // added by Vivek
    public boolean VerifyPreTrialToPreMonthlySubsQuantity() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String subsText = PreMonthlyDivCreditSubsQuantity.text();
        if (subsText.contains("2"));
        {
            result = true;
        }
        return result;
    }
    
 // added by Vivek
    public boolean VerifyThePreTrialActivationAndExpiryDates() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String ActDate = PreMonthlyDivCreditSubsActiDate.text();
        MyCommonAPIs.sleepi(1);
        String expiryDate = PreMonthlyDivCreditSubsExpDate.text();
        System.out.println("Activation on ---");
        System.out.println(ActDate);
        System.out.println("Expires on ---");
        System.out.println(expiryDate);
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
        String date = currentDate.format(formatter); 
        
        if (ActDate.contains(date));
        {
            result = true;
        }
        return result;
    }
    
    // added by Vivek
    public boolean VerifyThePreTrialToPreYearlySubsText() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String subsText = PreMonthlyDivCreditSubsText.text();
        if (subsText.contains("Insight Premium Monthly/Annual Subscription with 2 device credits."));
        {
            result = true;
        }
        return result;
    }
    
    // added by vivek
    public void expandInsightPremiumSubscriptionsSection() {
        ExpaindinsightPreSubsSection.click();
        MyCommonAPIs.sleepi(2);
    }
    
 // added by Vivek
    public boolean VerifyTheInsightPremiumSubsText() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String subsText = insightPreSubsCreditText.text();
        if (subsText.contains("Insight Premium 5 pack Sub, 1Yr subscription"));
        {
            result = true;
        }
        return result;
    }
    
    // added by Vivek
    public boolean VerifyTheInsightPremiumSubsQuantity() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String subsText = insightPreSubsCreditQuantity.text();
        if (subsText.contains("1"));
        {
            result = true;
        }
        return result;
    }
    
 // added by Vivek
    public boolean VerifyTheInsightPreSubsActivationAndExpiryDates() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String ActDate = insightPreSubsCreditActivationDate.text();
        MyCommonAPIs.sleepi(1);
        String expiryDate = insightPreSubsCreditExpiryDate.text();
        System.out.println("Activation on ---");
        System.out.println(ActDate);
        System.out.println("Expires on ---");
        System.out.println(expiryDate);
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
        String date = currentDate.format(formatter); 
        
        if (ActDate.contains(date));
        {
            result = true;
        }
        return result;
    }
    
    // added by Vivek
    public boolean VerifyMultiPackforFiveDivThreeYearsSubsText() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String subsText = insightPreSubsCreditText.text();
        if (subsText.contains("Insight Premium 5 pack Sub, 3Yr subscription"));
        {
            result = true;
        }
        return result;
    }
    
 // added by Vivek
    public boolean VerifyMultiPackforFiveDivFiveYearsSubsText() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String subsText = insightPreSubsCreditText.text();
        if (subsText.contains("Insight Premium 5 pack Sub, 5Yr subscription"));
        {
            result = true;
        }
        return result;
    }
    
 // added by Vivek
    public boolean VerifyMultiPackforTenDivOneYearsSubsText() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String subsText = insightPreSubsCreditText.text();
        if (subsText.contains("Insight Premium 10 pack Sub, 1Yr subscription"));
        {
            result = true;
        }
        return result;
    }
    
 // added by Vivek
    public boolean VerifyMultiPackforTenDivThreeYearsSubsText() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String subsText = insightPreSubsCreditText.text();
        if (subsText.contains("Insight Premium 10 pack Sub, 3Yr subscription"));
        {
            result = true;
        }
        return result;
    }
    
 // added by Vivek
    public boolean VerifyMultiPackforTenDivFiveYearsSubsText() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        String subsText = insightPreSubsCreditText.text();
        if (subsText.contains("Insight Premium 10 pack Sub, 5Yr subscription"));
        {
            result = true;
        }
        return result;
    }
    
    public void upgradeSubscriptionNorway(Map<String, String> map) {
        new MyCommonAPIs().open(URLParam.hrefPaymentSubscription, true);
        inputPaymentPageInfoNorway(map);
        if (map.containsKey("Promo Code")) {
            addpromocode.click();
            MyCommonAPIs.sleepi(2);
            enterpromocode.setValue(map.get("Promo Code"));
            donebutton.click();
            MyCommonAPIs.sleepi(5);
        }

        logger.info("Finish upgrade subsctiption...");
    }
    
    public void inputPaymentPageInfoNorway(Map<String, String> map) {
        logger.info("Start upgrade subsctiption...");
        if (ChangebuttonMoToYr.exists()) {
            ChangebuttonMoToYr.click();
        }
        if (Changebutton.exists()) {
            Changebutton.click();
        } else {
            waitElement(upgrade);
            upgrade.click();
        }
        // MyCommonAPIs.sleepi(30);
        waitElement(checkoutbutton);

    }
  //AddedByPratik    
    public void DownloadTest () {
        System.out.println("Step4: Passed");
        String downloadFilepath = "C:\\Users\\DELL\\Downloads";
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("download.default_directory", downloadFilepath);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        System.out.println("Step5: Passed");
    }
  //AddedByPratik
    private static boolean waitForFileDownload(Path filePath, Duration timeout) throws InterruptedException {
        long endTime = System.currentTimeMillis() + timeout.toMillis();
        while (System.currentTimeMillis() < endTime) {
            if (Files.exists(filePath)) {
                return true;
            }
            Thread.sleep(500);
        }
        return false;
    }
//AddedByPratik
    private static boolean verifyFile() {
        try {
            File file = filePath.toFile();
            if (!file.exists()) {
                return false;
            }
            if (!file.getName().equals(expectedFileName)) {
                return false;
            }
            if (file.length() <= 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
//AddedByPratik
    private static void deleteFile() {
        try {
            Files.deleteIfExists(filePath);
            System.out.println("File deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to delete the file.");
        }
    }
 //AddedByPratik   
    public boolean verifYOptionisNotThereforCSVFileDownload() {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        gotoCreditsAllocationPage();
        MyCommonAPIs.sleepi(10);
        if (csvFileDownloadButton.exists()) {
            System.out.println(" CSV File download button is visible on credit allocation page");
            result = false;
        } else {
            result = true;
        }     
        return result;
    }
   //addedBypratik
    public void getFileNamesandVerify() {        
        String downloadFolderPath = System.getProperty("user.home") + "\\Downloads";
        File downloadFolder = new File(downloadFolderPath);

        if (!downloadFolder.exists() || !downloadFolder.isDirectory()) {
            System.out.println("The specified download folder does not exist or is not a directory.");
            return;
        }

        File[] files = downloadFolder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    concatenatedFileNames.append(file.getName()).append(", ");
                }
            }
        }
        System.out.println(concatenatedFileNames.length());
        if (concatenatedFileNames.length() > 0) {
            concatenatedFileNames.setLength(concatenatedFileNames.length() - 2);
        }
                String fileNamesString = concatenatedFileNames.toString();
        System.out.println("Concatenated File Names: " + fileNamesString); 
}
//AddedByPratik 
    public boolean verifYCSVFileDownload() throws InterruptedException {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        gotoCreditsAllocationPage();
        MyCommonAPIs.sleepi(10);
        waitElement(csvFileDownloadButton);
        System.out.println("Step1: Passed");
        if (csvFileDownloadButton.exists()) {
            System.out.println(" CSV File download button is visible on credit allocation page");
            csvFileDownloadButton.click();
            MyCommonAPIs.sleepi(5);
            getFileNamesandVerify();
            MyCommonAPIs.sleepi(5);
            System.out.println("Step2: Passed");
            String concatenatedString = concatenatedFileNames.toString();
            String[] stringArray = concatenatedString.split(", ");
            for (int i = 0; i<=1000; i++ ) {
                System.out.println("Step4: Passed"+stringArray[i]);
                if (stringArray[i].equals(expectedFileName)) {
                    result = true;
                    System.out.println("Step4: Passed"+stringArray[i]);
                    break;
                }
            }
            
        }
        return result;
    }
    
    public boolean verifYCSVFileDownload1() throws InterruptedException {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        gotoCreditsAllocationPage();
        MyCommonAPIs.sleepi(10);
        waitElement(csvFileDownloadButton);
        System.out.println("Step1: Passed");
        if (csvFileDownloadButton.exists()) {
            System.out.println(" CSV File download button is visible on credit allocation page");
            csvFileDownloadButton.click();
            MyCommonAPIs.sleepi(5);
            getFileNamesandVerify();
            MyCommonAPIs.sleepi(5);
            System.out.println("Step2: Passed");
            String concatenatedString = concatenatedFileNames.toString();
            String[] stringArray = concatenatedString.split(", ");
            for (int i = 0; i<=1000; i++ ) {
                System.out.println("Step4: Passed"+stringArray[i]);
                if (stringArray[i].equals(expectedFileName1)) {
                    result = true;
                    System.out.println("Step4: Passed"+stringArray[i]);
                    break;
                }
            }
            
        }
        return result;
    }
    
    //addedByPratik
    
    public boolean verifyAllocatedDeviceCreditsSideTray () {
        
        boolean result = false;   
        MyCommonAPIs.sleepi(10);
        gotoCreditsAllocationPage();
        MyCommonAPIs.sleepi(15);
        if (allocatedDeviceCredits.exists() && allocatedCaptiveCredits.exists()) {
            
            allocatedDeviceCredits.click();
            MyCommonAPIs.sleepi(10);
            System.out.println("Clicked on allocated device credit number:");
            
            SelenideElement v1 = sideTrayofDeviceCredits.shouldBe(Condition.visible);
            SelenideElement v2 = netgearHeader.shouldBe(Condition.visible);
            SelenideElement v3 = sideTrayDeviceAllocatedCredits.shouldBe(Condition.visible);
            SelenideElement v4 = sideTraNoOfyDeviceAllocatedCredits.shouldBe(Condition.visible);
            SelenideElement v5 = sideTrayCloseIcon.shouldBe(Condition.visible);
            
            v1.shouldHave(Condition.attribute("class", "clientDetailBlock deviceCreditSideBar dataDetailShow"));
            v2.shouldHave(Condition.text("Netgear"));
            v3.shouldHave(Condition.text("Allocated Device Credits"));
            v4.shouldHave(Condition.text("2"));
            
            logger.info(" Alloated Device Credit Tray is opened now and verified ");
            System.out.println(" Alloated Device Credit Tray is opened now and verified ");
            if (v1.isDisplayed() && v2.isDisplayed() && v3.isDisplayed() && v4.isDisplayed() && v5.isDisplayed()) {
                result = true;
                sideTrayCloseIcon.click();
                MyCommonAPIs.sleepi(5);
            }   
        }
        return result;
    }
    
    //AddedByPratik
    
    public boolean verifyAllocatedDeviceCreditsSideTrayCloseIcon () {
        
        boolean result = false;   
        MyCommonAPIs.sleepi(10);
        gotoCreditsAllocationPage();
        MyCommonAPIs.sleepi(15);
        if (allocatedDeviceCredits.exists() && allocatedCaptiveCredits.exists()) {
            
            allocatedDeviceCredits.click();
            MyCommonAPIs.sleepi(10);
            System.out.println("Clicked on allocated device credit number:");
            SelenideElement v5 = sideTrayCloseIcon.shouldBe(Condition.visible);   
            v5.shouldHave(Condition.attribute("class", "closeIconBlock"));
            logger.info(" Alloated Device Credit Tray is opened now and close icon is verified ");
            System.out.println(" Alloated Device Credit Tray is opened now and close icon is verified ");
            if (v5.isDisplayed()) {
                result = true;
                sideTrayCloseIcon.click();
                MyCommonAPIs.sleepi(5);
            }   
        }
        return result;
    }
    
    //addedByPratik
    
    public boolean verifyAllocatedDeviceCreditsSideTrayLicenseKey () {
        
        boolean result = false;   
        MyCommonAPIs.sleepi(10);
        gotoCreditsAllocationPage();
        MyCommonAPIs.sleepi(15);
        System.out.println("Step1:");
        
        if (allocatedDeviceCredits.exists() && allocatedCaptiveCredits.exists()) {
            System.out.println("Step2:");
            
            allocatedDeviceCredits.click();
            MyCommonAPIs.sleepi(30);
            System.out.println("Clicked on allocated device credit number:");
            System.out.println("Step3:");
            
            SelenideElement v1 = sideTrayofDeviceCredits.shouldBe(Condition.visible);
            waitElement(v1);
            SelenideElement v2 = netgearHeader.shouldBe(Condition.visible);
            SelenideElement v3 = sideTrayDeviceAllocatedCredits.shouldBe(Condition.visible);
            SelenideElement v4 = sideTraNoOfyDeviceAllocatedCredits.shouldBe(Condition.visible);
            v1.shouldHave(Condition.attribute("class", "clientDetailBlock deviceCreditSideBar dataDetailShow"));
            v2.shouldHave(Condition.text("Netgear"));
            v3.shouldHave(Condition.text("Allocated Device Credits"));
            v4.shouldHave(Condition.text("2"));
            System.out.println("Step4:");
            
            SelenideElement v5 = deviceCreditTrayLicenseKey.shouldBe(Condition.visible);
            v5.shouldHave(Condition.attribute("class", "ClientHistoryEllipses no-margin"));
            deviceCreditTrayLicenseKey.hover();
            System.out.println("Step5:");
            MyCommonAPIs.sleepi(1);
            //deviceCreditTrayLicenseKeyLong.hover();
            //MyCommonAPIs.sleepi(1);
            SelenideElement v6 = deviceCreditTrayLicenseKeyLong.shouldBe(Condition.visible);
            SelenideElement v7 = deviceCreditTrayLicenseKeyCloneText.shouldBe(Condition.visible);
            v6.shouldHave(Condition.attribute("class", "AccountTooltip"));
            v7.shouldHave(Condition.text("Clone"));
            System.out.println("Step6:");
            if (v6.isDisplayed() && v7.isDisplayed()) {
                MyCommonAPIs.sleepi(1);
                sideTrayCloseIcon.hover();
                MyCommonAPIs.sleepi(1);
                SelenideElement v8 = sideTrayCloseIcon.shouldBe(Condition.visible); 
                System.out.println("Step7:");
                logger.info(" Alloated Device Credit Tray is opened now and license keys are verified ");
                System.out.println(" Alloated Device Credit Tray is opened now and license keys are verified ");
                if (v1.isDisplayed() && v2.isDisplayed() && v3.isDisplayed() && v4.isDisplayed() && v5.isDisplayed()
                        && v8.isDisplayed()) {
                    result = true;
                    sideTrayCloseIcon.click();
                    System.out.println("Step8:");
                    
                    MyCommonAPIs.sleepi(5);
                } 
            }
              
        }
        return result;
    }
    
 //addedByPratik
    
    public boolean verifyAllocatedDeviceCreditsSideTrayExpiaryDate () {
        
        boolean result = false;   
        MyCommonAPIs.sleepi(10);
        gotoCreditsAllocationPage();
        MyCommonAPIs.sleepi(15);
        System.out.println("Step1:");
        
        if (allocatedDeviceCredits.exists() && allocatedCaptiveCredits.exists()) {
            System.out.println("Step2:");
            
            allocatedDeviceCredits.click();
            MyCommonAPIs.sleepi(30);
            System.out.println("Clicked on allocated device credit number:");
            System.out.println("Step3:");
            
            SelenideElement v1 = sideTrayofDeviceCredits.shouldBe(Condition.visible);
            waitElement(v1);
            SelenideElement v2 = netgearHeader.shouldBe(Condition.visible);
            SelenideElement v3 = sideTrayDeviceAllocatedCredits.shouldBe(Condition.visible);
            SelenideElement v4 = sideTraNoOfyDeviceAllocatedCredits.shouldBe(Condition.visible);
            v1.shouldHave(Condition.attribute("class", "clientDetailBlock deviceCreditSideBar dataDetailShow"));
            v2.shouldHave(Condition.text("Netgear"));
            v3.shouldHave(Condition.text("Allocated Device Credits"));
            v4.shouldHave(Condition.text("2"));
            System.out.println("Step4:");
            
            SelenideElement v5 = deviceCreditTrayLicenseKey.shouldBe(Condition.visible);
            v5.shouldHave(Condition.attribute("class", "ClientHistoryEllipses no-margin"));
            deviceCreditTrayLicenseKey.hover();
            System.out.println("Step5:");
            
            MyCommonAPIs.sleepi(1);
            //deviceCreditTrayLicenseKeyLong.hover();
            //MyCommonAPIs.sleepi(1);
            SelenideElement v6 = deviceCreditTrayLicenseKeyLong.shouldBe(Condition.visible);
            SelenideElement v7 = deviceCreditTrayLicenseKeyCloneText.shouldBe(Condition.visible);
            v6.shouldHave(Condition.attribute("class", "AccountTooltip"));
            v7.shouldHave(Condition.text("Clone"));
            System.out.println("Step6:");
            
            if (v6.isDisplayed() && v7.isDisplayed()) {
                MyCommonAPIs.sleepi(1);
                expriryDateShownOnTray.hover();
                MyCommonAPIs.sleepi(1);
                SelenideElement v10 = expriryDateShownOnTray.shouldBe(Condition.visible);
                SelenideElement v8 = sideTrayCloseIcon.shouldBe(Condition.visible); 
                System.out.println("Step7:");
                
                logger.info(" Alloated Device Credit Tray is opened now and expiary date is verified ");
                System.out.println(" Alloated Device Credit Tray is opened now and expiary date is verified ");
                if (v1.isDisplayed() && v2.isDisplayed() && v3.isDisplayed() && v4.isDisplayed() && v5.isDisplayed()
                        && v8.isDisplayed() && v10.isDisplayed()) {
                    result = true;
                    sideTrayCloseIcon.click();
                    System.out.println("Step8:");
                
                    MyCommonAPIs.sleepi(5);
                } 
            }
              
        }
        return result;
    }
    
//addedByPratik
    
    public boolean verifyAllocatedDeviceCreditsSideTrayCloneLicenseKey () {
        
        boolean result = false;   
        MyCommonAPIs.sleepi(10);
        gotoCreditsAllocationPage();
        MyCommonAPIs.sleepi(15);
        System.out.println("Step1:");
        
        if (allocatedDeviceCredits.exists() && allocatedCaptiveCredits.exists()) {
            System.out.println("Step2:");
            
            allocatedDeviceCredits.click();
            MyCommonAPIs.sleepi(30);
            System.out.println("Clicked on allocated device credit number:");
            System.out.println("Step3:");
            
            SelenideElement v1 = sideTrayofDeviceCredits.shouldBe(Condition.visible);
            waitElement(v1);
            SelenideElement v2 = netgearHeader.shouldBe(Condition.visible);
            SelenideElement v3 = sideTrayDeviceAllocatedCredits.shouldBe(Condition.visible);
            SelenideElement v4 = sideTraNoOfyDeviceAllocatedCredits.shouldBe(Condition.visible);
            v1.shouldHave(Condition.attribute("class", "clientDetailBlock deviceCreditSideBar dataDetailShow"));
            v2.shouldHave(Condition.text("Netgear"));
            v3.shouldHave(Condition.text("Allocated Device Credits"));
            v4.shouldHave(Condition.text("2"));
            System.out.println("Step4:");
            
            SelenideElement v5 = deviceCreditTrayLicenseKey.shouldBe(Condition.visible);
            v5.shouldHave(Condition.attribute("class", "ClientHistoryEllipses no-margin"));
            deviceCreditTrayLicenseKey.hover();
            System.out.println("Step5:");
            
            MyCommonAPIs.sleepi(1);
            SelenideElement v6 = deviceCreditTrayLicenseKeyLong.shouldBe(Condition.visible);
            SelenideElement v7 = deviceCreditTrayLicenseKeyCloneText.shouldBe(Condition.visible);
            v6.shouldHave(Condition.attribute("class", "AccountTooltip"));
            v7.shouldHave(Condition.text("Clone"));
            System.out.println("Step6:");
            
            if (v6.isDisplayed() && v7.isDisplayed()) {
                MyCommonAPIs.sleepi(3);
                checkboxCloneLicenseKeyTray.hover();
                checkboxCloneLicenseKeyTray.click();
                System.out.println("Step7:");
                MyCommonAPIs.sleep(5);
                copiedLicenseKeyonDeviceCreditTray.hover();
                System.out.println("Step8:");
                SelenideElement v8 = copiedLicenseKeyonDeviceCreditTray.shouldBe(Condition.visible);
                v8.shouldHave(Condition.text("Copied"));
                System.out.println("Step9:");
                if (v8.isDisplayed()) {
                    logger.info(" Alloated Device Credit Tray is opened now and verified copy license key ");
                    System.out.println(" Alloated Device Credit Tray is opened now and verified copy license key ");
                        result = true;
                        sideTrayCloseIcon.hover();
                        MyCommonAPIs.sleepi(1);
                        sideTrayCloseIcon.click();
                        System.out.println("Step8:");
                        MyCommonAPIs.sleepi(5); 
                }

            }
              
        }
        return result;
    }
//AddedByPratik    
public boolean verifyDeviceAndUsedCreditsShownOnTrayPage () {
        
        boolean result = false;   
        MyCommonAPIs.sleepi(10);
        gotoCreditsAllocationPage();
        MyCommonAPIs.sleepi(15);
        System.out.println("Step1:");
        
        if (allocatedDeviceCredits.exists() && allocatedCaptiveCredits.exists()) {
            System.out.println("Step2:");
            
            allocatedDeviceCredits.click();
            MyCommonAPIs.sleepi(30);
            System.out.println("Clicked on allocated device credit number:");
            System.out.println("Step3:");
            
            SelenideElement v1 = sideTrayofDeviceCredits.shouldBe(Condition.visible);
            waitElement(v1);
            SelenideElement v2 = netgearHeader.shouldBe(Condition.visible);
            SelenideElement v3 = sideTrayDeviceAllocatedCredits.shouldBe(Condition.visible);
            SelenideElement v4 = sideTraNoOfyDeviceAllocatedCredits.shouldBe(Condition.visible);
            SelenideElement v5 = deviceCreditsonTray.shouldBe(Condition.visible);
            SelenideElement v6 = usedCreditsShwononTray.shouldBe(Condition.visible);
            v1.shouldHave(Condition.attribute("class", "clientDetailBlock deviceCreditSideBar dataDetailShow"));
            v2.shouldHave(Condition.text("Netgear"));
            v3.shouldHave(Condition.text("Allocated Device Credits"));
            v4.shouldHave(Condition.text("2"));
            v5.shouldHave(Condition.text("2"));
            v6.shouldHave(Condition.text("0"));
            System.out.println("Step4:");
            if (v1.isDisplayed() && v2.isDisplayed() && v3.isDisplayed() && v4.isDisplayed() && v5.isDisplayed() && v6.isDisplayed()) {
                    logger.info(" Alloated Device Credits and used credits are showing corrrectly on device credits tray ");
                    System.out.println(" Alloated Device Credits and used credits are showing corrrectly on device credits tray");
                    result = true;
                    MyCommonAPIs.sleepi(1);
                    sideTrayCloseIcon.click();
                    System.out.println("Step8:");
                    MyCommonAPIs.sleepi(5); 
            }
              
        }
        return result;
    }

//AddedByPratik    
public boolean verifyDeallocatedDeviceAndUsedCreditsShownOnTrayPage () {
      
      boolean result = false;   
      MyCommonAPIs.sleepi(10);
      gotoCreditsAllocationPage();
      MyCommonAPIs.sleepi(15);
      System.out.println("Step1:");
      
      if (allocatedDeviceCreditsAfterDeallocate.exists() && allocatedCaptiveCredits.exists()) {
          System.out.println("Step2:");
          
          allocatedDeviceCreditsAfterDeallocate.click();
          MyCommonAPIs.sleepi(30);
          System.out.println("Clicked on allocated device credit number:");
          System.out.println("Step3:");
          
          SelenideElement v1 = sideTrayofDeviceCredits.shouldBe(Condition.visible);
          waitElement(v1);
          SelenideElement v2 = netgearHeader.shouldBe(Condition.visible);
          SelenideElement v3 = sideTrayDeviceAllocatedCredits.shouldBe(Condition.visible);
          SelenideElement v4 = sideTraNoOfyDeviceAllocatedCredits1.shouldBe(Condition.visible);
          SelenideElement v5 = deviceCreditsonTray1.shouldBe(Condition.visible);
          SelenideElement v6 = usedCreditsShwononTray.shouldBe(Condition.visible);
          v1.shouldHave(Condition.attribute("class", "clientDetailBlock deviceCreditSideBar dataDetailShow"));
          v2.shouldHave(Condition.text("Netgear"));
          v3.shouldHave(Condition.text("Allocated Device Credits"));
          v4.shouldHave(Condition.text("1"));
          v5.shouldHave(Condition.text("1"));
          v6.shouldHave(Condition.text("0"));
          System.out.println("Step4:");
          if (v1.isDisplayed() && v2.isDisplayed() && v3.isDisplayed() && v4.isDisplayed() && v5.isDisplayed() && v6.isDisplayed()) {
                  logger.info(" Dealloated Device Credits and used credits are showing corrrectly on device credits tray ");
                  System.out.println(" Dealloated Device Credits and used credits are showing corrrectly on device credits tray");
                  result = true;
                  MyCommonAPIs.sleepi(1);
                  sideTrayCloseIcon.click();
                  System.out.println("Step8:");
                  MyCommonAPIs.sleepi(5); 
          }
            
      }
      return result;
  }

//addedByPratik

public boolean verifyMultipleKeysDeviceCreditsSideTrayLicenseKey () {
    
    boolean result = false;   
    MyCommonAPIs.sleepi(10);
    gotoCreditsAllocationPage();
    MyCommonAPIs.sleepi(15);
    System.out.println("Step1:");
    
    if (allocatedDeviceCredits2.exists() && allocatedCaptiveCredits.exists()) {
        System.out.println("Step2:");
        
        allocatedDeviceCredits2.click();
        MyCommonAPIs.sleepi(30);
        System.out.println("Clicked on allocated device credit number:");
        System.out.println("Step3:");
        
        SelenideElement v1 = sideTrayofDeviceCredits.shouldBe(Condition.visible);
        waitElement(v1);
        SelenideElement v2 = netgearHeader.shouldBe(Condition.visible);
        SelenideElement v3 = sideTrayDeviceAllocatedCredits.shouldBe(Condition.visible);
        SelenideElement v4 = sideTraNoOfyDeviceAllocatedCredits2.shouldBe(Condition.visible);
        v1.shouldHave(Condition.attribute("class", "clientDetailBlock deviceCreditSideBar dataDetailShow"));
        v2.shouldHave(Condition.text("Netgear"));
        v3.shouldHave(Condition.text("Allocated Device Credits"));
        v4.shouldHave(Condition.text("500"));
        System.out.println("Step4:");
        
        SelenideElement v5 = deviceCreditTrayLicenseKey.shouldBe(Condition.visible);
        v5.shouldHave(Condition.attribute("class", "ClientHistoryEllipses no-margin"));
        deviceCreditTrayLicenseKey.hover();
        System.out.println("Step5:");
        MyCommonAPIs.sleepi(1);
        //deviceCreditTrayLicenseKeyLong.hover();
        //MyCommonAPIs.sleepi(1);
        SelenideElement v6 = multipleLicensekeys1.shouldBe(Condition.visible);
        SelenideElement v7 = multipleLicensekeys2.shouldBe(Condition.visible);
        SelenideElement v8 = multipleLicensekeys3.shouldBe(Condition.visible);
        SelenideElement v9 = multipleLicensekeys4.shouldBe(Condition.visible);
        System.out.println("Step6:");
        if (v6.isDisplayed() && v7.isDisplayed() && v8.isDisplayed() && v9.isDisplayed()) {
            MyCommonAPIs.sleepi(1);
            result = true;
            SelenideElement v10 = sideTrayCloseIcon.shouldBe(Condition.visible); 
            System.out.println("Step7:");
            logger.info(" Alloated Device Credit Tray is opened now and license keys are verified ");
            System.out.println(" Alloated Device Credit Tray is opened now and license keys are verified ");
                sideTrayCloseIcon.click();
                System.out.println("Step8:");
                MyCommonAPIs.sleepi(5);
        }
          
    }
    return result;
}
//NotAddedByPratik   
public void clickPlaceOrder1() {
    if (placeyourordernew.exists()) {
        placeyourordernew.click();
    }

    int count = 0;
    while (count < 10) {
        MyCommonAPIs.sleepi(50);
        // Selenide.switchTo().frame("redirectTo3ds1Frame");
        if (gotosubscriptionnow.exists()) {
            gotosubscriptionnow.click();
            System.out.println("before emulater go to dash board");
            break;
        } else{
        break;
       }
    }
    }


public boolean checkSubscriptionsFreeTrial() {
    boolean result = false;
    accountmanager.click();
    MyCommonAPIs.sleepi(5);
    closeLockedDialog();
    subscriptions.click();  
    MyCommonAPIs.sleepi(10);
    logger.info("Check subscriptions...");
    if (activateFreeTrial.exists()) {
        System.out.println(activateFreeTrial.getText());
        if(activateFreeTrial.getText().equalsIgnoreCase("Activate Free Trial"))    
            result = true;
        
    }
    return result;
}

//Added by Anusha H

public boolean checkEmailMessageForCustomReports(String mailname) {
    boolean result = false;
    logger.info("Check email address is:" + mailname);
    open("https://yopmail.com/");
    MyCommonAPIs.sleepi(5);
    String inputElement = "//input[@id='login']";
    $x(inputElement).clear();
    $x(inputElement).sendKeys(mailname);
    $x("//button[@title='Check Inbox @yopmail.com']").click();
    SelenideElement frame = $("iframe[name='ifinbox']");
    Selenide.switchTo().frame(frame);
    MyCommonAPIs.sleepsync();
   
    System.out.println(ReportInMail.getText());
    if (ReportInMail.getText().contains("Organization Report: Netgear")) {
        result = true;
        logger.info("Received Custom report email .");
    }
    return result;

}

public boolean errorMSG() {
    boolean result= false;
    if (errorMSG.getText().contains("Please enter a valid email ID.")) {
        result = true;
        logger.info("Error message displayed for invalied entered email address");
    }
    return result;
    
}

public boolean countORG(String org, int TotalOrgCount) {
    boolean result= false;
        int i = 1;
        while(true) {
         String orga = org+i;
         boolean count  = orgcount(orga).isDisplayed();
         if(count) {
            i++;
         }
           else
               break;
        }
        
        if (i==TotalOrgCount)
           {
            result= true;
           }
    return result;
}


public boolean verifyLicOnOrgSubscription(String lic) {
    MyCommonAPIs.sleepi(20);
    Orgimmediet.click();
    MyCommonAPIs.sleepi(4);
    System.out.println(lic+"name");
    boolean result = false;
    if (license(lic).isDisplayed()) {
        result = true; 
    }
    return result;
}

public boolean verifyLicOnAcctSubscription(String lic) {
    MyCommonAPIs.sleepi(20);
    System.out.println(lic+"name");
    boolean result = false;
    if (license(lic).isDisplayed()) {
        result = true; 
    }
    return result;
}

public boolean AccountLevelActiveRowElements() {
    new MyCommonAPIs().open(URLParam.hrefSubscriptionpage, true);
    AcctSubsActiveRow.click();
    MyCommonAPIs.sleepi(8);
    boolean result = false;
    if (AcctSubsActiveRowElemnets.getText().contains("Purchase Confirmation Key") && AcctSubsActiveRowElemnets.getText().contains("SKU") 
            && AcctSubsActiveRowElemnets.getText().contains("Activation")  && AcctSubsActiveRowElemnets.getText().contains("Expiration")  && AcctSubsActiveRowElemnets.getText().contains("Credits")) {
        result = true;
    }
  return result;
}

public boolean OrgLevelActiveRowElements() {
    new MyCommonAPIs().open(URLParam.hrefSubscriptionpage, true);
    MyCommonAPIs.sleepi(5);
    Orgimmediet.click();
    boolean result = false;
    
    if (ORGSubsActiveRowElemnets.getText().contains("Purchase Confirmation Key") && ORGSubsActiveRowElemnets.getText().contains("SKU") && ORGSubsActiveRowElemnets.getText().contains("Organization")
            && ORGSubsActiveRowElemnets.getText().contains("Activation")  && ORGSubsActiveRowElemnets.getText().contains("Expiration")  && ORGSubsActiveRowElemnets.getText().contains("Credits")) {
        result = true;
    }
  return result;
}

public boolean AccountLevelActiveRow() {
    new MyCommonAPIs().open(URLParam.hrefSubscriptionpage, true);
    boolean result = false;
    if(AcctSubsActiveRow.isDisplayed()){
        result=true;
    }
    return result;
}

public boolean OrgLevelActiveRow() {
    new MyCommonAPIs().open(URLParam.hrefSubscriptionpage, true);
    boolean result = false;
    if(OrgSubsActiveRow.isDisplayed()){
        result=true;
    }
    return result;
}

public boolean AcctLevelExpiryRow() {
    new MyCommonAPIs().open(URLParam.hrefSubscriptionpage, true);
    boolean result = false;
    if(AcctSubsExpiryRow.isDisplayed()){
        result=true;
    }
    return result;
}

public boolean OrgLevelExpiryRow() {
    new MyCommonAPIs().open(URLParam.hrefSubscriptionpage, true);
    boolean result = false;
    if(OrgSubsExpiryRow.isDisplayed()){
        result=true;
    }
    return result;
}

public void AddKeyToOrg(String LicenceKey, String selectOrg) {

    if (accountmanager.exists()) {
        accountmanager.click();
    }
    waitReady();
    subscriptions.click();
    MyCommonAPIs.sleepi(5);
    AddProKey.click();
    MyCommonAPIs.sleepi(10);
    orglicense.click(); 
    MyCommonAPIs.sleepi(6);
    SelectOrgInSubspage(selectOrg).click();
    System.out.println("Immediate is alredy clicked");
    AddProLicense.sendKeys(LicenceKey);
    MyCommonAPIs.sleepi(10);
    ClickAdd.click();
    MyCommonAPIs.sleepi(10);
    if(ClickAddok.isDisplayed()) {
    ClickAddok.click();
    }
    System.out.println("license added sucessfully");

}

public boolean AllocateAdditionalcreditfromkeys(String name, String LicenceKey) {
    boolean result = false;
    gotoCreditsAllocationPage();
    executeJavaScript("arguments[0].removeAttribute('class')", $x(String.format(showallocate, name)));
    MyCommonAPIs.sleep(3000);
    $x(String.format(clickallocate, name)).waitUntil(Condition.visible, 60 * 1000).click();
    MyCommonAPIs.sleep(8 * 1000);
    if (devCreditsAllocate.isDisplayed()) {
        devCreditsAllocate.click();
    }
    selectSubsKey.click();
    MyCommonAPIs.sleepi(5);
    SelectAll.click();
    MyCommonAPIs.sleepi(5);
    logger.info("Plus click");
    allocateButton.click();
    HashMap<String, String> allocationStatus = new HashMap<String, String>();
    allocationStatus.put("Devices Credits", getText($x(String.format(allocatedCredits, name, "2"))));
    if(allocationStatus.equals("25") || allocationStatus.equals("100")) {
        result = true;
}
 return result;   
}

public boolean disablingOfAllocateAdditionalcreditfromkeys(String name) {
    boolean result = false;
    gotoCreditsAllocationPage();
    executeJavaScript("arguments[0].removeAttribute('class')", $x(String.format(showallocate, name)));
    MyCommonAPIs.sleep(3000);
    $x(String.format(clickallocate, name)).waitUntil(Condition.visible, 60 * 1000).click();
    MyCommonAPIs.sleep(8 * 1000);
    if (devCreditsAllocate.isDisplayed()) {
        devCreditsAllocate.click();
    }
    $x(String.format(creditsPlusBtn, "1")).click();
    MyCommonAPIs.sleep(3000);
    if(!selectSubsKey.isDisplayed()) {
        result=true;
    }
    return result;
    
}

public boolean disablingOfAllocateAdditionalcreditfromAccount(String name) {
    boolean result = false;
    gotoCreditsAllocationPage();
    executeJavaScript("arguments[0].removeAttribute('class')", $x(String.format(showallocate, name)));
    MyCommonAPIs.sleep(3000);
    $x(String.format(clickallocate, name)).waitUntil(Condition.visible, 60 * 1000).click();
    MyCommonAPIs.sleep(8 * 1000);
    if (devCreditsAllocate.isDisplayed()) {
        devCreditsAllocate.click();
    }
    $x(String.format(creditsPlusBtn, "1")).click();
    MyCommonAPIs.sleep(3000);
    if(!selectSubsAcct.isDisplayed()) {
        result=true;
    }
    return result;
    
}

public boolean LMSkeyInAdditionalCreditsfromKeys(String key , String name) {
    boolean result = false;
    logger.info("enter the allocation");
    gotoCreditsAllocationPage();
    executeJavaScript("arguments[0].removeAttribute('class')", $x(String.format(showallocate, name)));
    MyCommonAPIs.sleep(3000);
    $x(String.format(clickallocate, name)).waitUntil(Condition.visible, 60 * 1000).click();
    MyCommonAPIs.sleep(8 * 1000);
    if (devCreditsAllocate.isDisplayed()) {
        devCreditsAllocate.click();
    }
    selectSubsKey.click();
    MyCommonAPIs.sleep(3000);
    if(keysDropdown.getText().contains(key)) {
        result=true;
    }
    return result;
}

public boolean AllocateLMSkeyThroughEditOrg(String organizationName) {
    boolean result = false;
    editORG.click();
    MyCommonAPIs.sleep(3000);
    AllocateCredits.click();
    waitReady();
    selectSubsKey.click();
    MyCommonAPIs.sleep(1000);
    SelectAll.click();
    MyCommonAPIs.sleep(2000);
    Allocatebutton2.click();
    HashMap<String, String> creditsInfo = new HamburgerMenuPage().getCreditAllocationTableInfo(organizationName);
    if(creditsInfo.get("Devices Credits").equals("25") || creditsInfo.get("Devices Credits").equals("100")) {
        result=true;
    }
    
    return result;
}
//AddedByPratik
public boolean verifyLocalGUIofAP() {
    boolean result = false;
    String ap1ip = WebportalParam.ap1IPaddress;
    open("https://"+ap1ip);
    MyCommonAPIs.sleepi(10);
    if (clickOnAdvanced.exists()) {
        clickOnAdvanced.click();
        MyCommonAPIs.sleepi(3);
        waitElement(clickOnLinktoOpenLocalGUI);
        clickOnLinktoOpenLocalGUI.click();
        MyCommonAPIs.sleepi(10);
        waitElement(apUsername);
        apUsername.setValue("admin");
        MyCommonAPIs.sleepi(1);
        waitElement(apPassword);
        apPassword.setValue("Netgear1@");
        MyCommonAPIs.sleepi(1);
        waitElement(loginButtonLocalGUIAP);
        loginButtonLocalGUIAP.click();
        MyCommonAPIs.sleepi(10);
        waitElement(verifyLoggedInSuccesswithAPModel);
        String localGUIAPText = verifyLoggedInSuccesswithAPModel.getText();
        System.out.println(localGUIAPText);
        if (localGUIAPText.contains(WebportalParam.ap1Model)) {
            System.out.println("AP1 is not went for reboot");
            logger.info("AP1 not went for reboot");
            result = true;
        }
    } else {
        MyCommonAPIs.sleepi(10);
        waitElement(apUsername);
        apUsername.setValue("admin");
        MyCommonAPIs.sleepi(1);
        waitElement(apPassword);
        apPassword.setValue("Netgear1@");
        MyCommonAPIs.sleepi(1);
        waitElement(loginButtonLocalGUIAP);
        loginButtonLocalGUIAP.click();
        MyCommonAPIs.sleepi(10);
        waitElement(verifyLoggedInSuccesswithAPModel);
        String localGUIAPText = verifyLoggedInSuccesswithAPModel.getText();
        System.out.println(localGUIAPText);
        if (localGUIAPText.contains(WebportalParam.ap1Model)) {
            System.out.println("AP1 is not went for reboot");
            logger.info("AP1 not went for reboot");
            result = true;
        }
    }
    
    return result;
}
//AddedByPratik
public boolean verifyLocalGUIofSwitch() {
  boolean result = false;
  String sw1ip = WebportalParam.sw1IPaddress;
  open("http://"+sw1ip);
  MyCommonAPIs.sleepi(10);
  waitElement(switchGUIPassword);
  switchGUIPassword.setValue("Netgear1@");
  MyCommonAPIs.sleepi(3);
  waitElement(loginButtonSwitch);
  loginButtonSwitch.click();
  MyCommonAPIs.sleepi(10);
  waitElement(okPopupBtn);
  okPopupBtn.click();
  MyCommonAPIs.sleepi(5);
  waitElement(switchModelverifyText);
  String localGUISWText = switchModelverifyText.getText();
  System.out.println(localGUISWText);
  if (localGUISWText.contains(WebportalParam.sw1Model)) {
      System.out.println("SW1 is not went for reboot");
      logger.info("SW1 not went for reboot");
      result = true;
  }
  
  return result;
}

//AddedByPratik
public boolean verifyInvoiceFileData() {
    boolean result = false;
    MyCommonAPIs.sleepi(10);
    downlaodInvoice();
    MyCommonAPIs.sleepi(5);
    String userHome = System.getProperty("user.home");
    File downloadsFolder = new File(userHome, "Downloads");
    if (!downloadsFolder.exists() || !downloadsFolder.isDirectory()) {
        System.out.println("Downloads folder does not exist or is not a directory.");
        return result;
    }

    File[] downloadedFiles = downloadsFolder.listFiles(file -> !file.isDirectory());
    if (downloadedFiles != null && downloadedFiles.length > 0) {
        File latestFile = Arrays.stream(downloadedFiles)
                                .max((f1, f2) -> Long.compare(f1.lastModified(), f2.lastModified()))
                                .orElse(null);

        if (latestFile != null) {
            System.out.println("Latest file found: " + latestFile.getName());
            String name = latestFile.getName();
            if (name.endsWith(".pdf")) {
                try (PDDocument document = PDDocument.load(latestFile)) {
                    PDFTextStripper pdfStripper = new PDFTextStripper();
                    String text = pdfStripper.getText(document);
                    System.out.println("PDF Content:");
                    System.out.println(text);
                    if (text.contains("Total Paid") && text.contains("$")) {
                        result = true;
                        System.out.println("Premium invoice file data verified.");
                    } else {
                        System.out.println("The PDF does not contain the expected text.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error reading the PDF file: " + e.getMessage());
                }
            } else {
                System.out.println("The latest file is not a PDF.");
            }
        } else {
            System.out.println("No files found in the Downloads folder.");
        }
    } else {
        System.out.println("No files found in the Downloads folder.");
    }

    return result;
}    
// added by Pratik
public void OpenCreditAllocationPageFor2ndorg() {
    CreditAllocation.click();
    MyCommonAPIs.sleepi(5);
    secOrgCreditAllocation.click();
    MyCommonAPIs.sleepi(3);
}


public class SSLUtil {
    public void disableCertificateValidation() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() { return null; }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) { }
                    public void checkServerTrusted(X509Certificate[] certs, String authType) { }
                }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public void enableTwoFAEmail() {
    updateprofile.click();
    MyCommonAPIs.sleepi(10);
    if (cancelbutton.exists()) {
        cancelbutton.click();
    } 
    loginsettings.click();
    MyCommonAPIs.sleepi(10);
    twostepverification.click();
    MyCommonAPIs.sleepi(10);        
    enable2FA.click();
    MyCommonAPIs.sleepi(10);  
    waitElement(email2FA);
    Continue.click();
    MyCommonAPIs.sleepi(5);  
    Selenide.back();
        
}
}




