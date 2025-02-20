package webportal.CreateLocation.WP.PRJCBUGEN_T13874;


import static com.codeborne.selenide.Selenide.$$;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.WebportalLoginPage;
/**
 * @author Rajni
 */

public class Testcase extends TestCaseBase {
    final static Logger logger = Logger.getLogger("PRJCBUGEN_T13874");
    
    ArrayList<String> lsLocationNetworks = new ArrayList<String>();
    String  sOrganizationLocationElement = "#gridView .location-name"; 

    @Feature("CreateLocation.WP") 
    @Story("PRJCBUGEN_T13874") 
    @Description("Verify that location password should be 8 characters long, include upper case, lower case") 
    @TmsLink("PRJCBUGEN_T13874") 

    @Test(alwaysRun = true, groups = "p1") 
    public void test() throws Exception {
        runTest(this);
    }
   
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        AccountPage AccountPage =new AccountPage();
        MyCommonAPIs.waitReady();
        if (AccountPage.locationName(WebportalParam.location3).exists()) {
            AccountPage.deleteLocation(WebportalParam.location3);
        }    
        
    }

    @Step("Test Step 1: Log in to a premium account;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
       }

    @Step("Test Step 2:Click on Add location icon")
    public void step2() {
        AccountPage  AccountPage = new AccountPage();
        MyCommonAPIs.sleep(10000);      
        if (AccountPage.addNetWorkButton.exists()) {
            AccountPage.addNetWorkButton.click();
        } 
        AccountPage.timeZone.waitUntil(Condition.matchText("UTC"), 10 * 1000);
        MyCommonAPIs.sleep(10000);
        AccountPage.addNetLocationName.sendKeys(WebportalParam.location3);
        MyCommonAPIs.sleep(1000);
        AccountPage.addNetPassword.sendKeys("passowrd");
        AccountPage.addNetZipcode.sendKeys("12345");
        AccountPage.netCountryList.selectOption("China");
        AccountPage.savebutton.click();
        MyCommonAPIs.sleepi(10);
        boolean result = false;
       if(AccountPage.MessageAlert.getText().contains("Invalid password. Password must be 8-20 characters, with at least one uppercase and one lowercase letter, and one numeral. Allowed special characters are: !@#$%^&()*")) {
           result = true;
           assertTrue(result, "Alert is there");          
       }
       else {
           assertTrue(result, "Unable to get Alert");
                    
       }  
      
       AccountPage.addNetPassword.clear();
       AccountPage.addNetPassword.sendKeys("Password@1");
       AccountPage.savebutton.click();
       
}
   
    @Step("Test Step 3:location with the same name should be created after giving valid password")
    public void step3() {      
        try {                  
            AccountPage AccountPage = new AccountPage();
            MyCommonAPIs.waitElement(sOrganizationLocationElement);
            ElementsCollection esc = $$(sOrganizationLocationElement);
            String network;
            boolean located = false;
            for (SelenideElement locelem : esc) {                 
                if(located==false && locelem.getText().contains(WebportalParam.location3)) {
                    located =true;
                    break;
                 }                        
            }
            if(located) {
                assertTrue(located, "Invalid password. Password must be 8-20 characters, with at least one uppercase and one lowercase letter, and one numeral. Allowed special characters are: !@#$%^&()");
                logger.info("Verified that location password should be 8 characters long, include upper case, lower case");
            }
            else {
                assertTrue(located, "Failed : Verified that location password should be 8 characters long, include upper case, lower case");
                logger.info("the user is unable to create a single location with valid password");
            }
            
            }
        catch(Exception e) {
            e.printStackTrace();
            logger.info("Unable to get "+sOrganizationLocationElement);
        }
    }
 }
