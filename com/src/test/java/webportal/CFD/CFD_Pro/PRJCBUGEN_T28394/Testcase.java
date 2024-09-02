package webportal.CFD.CFD_Pro.PRJCBUGEN_T28394;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
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
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;;

/**
 * @author Rajni
 */

public class Testcase extends TestCaseBase {
    
    final static Logger logger = Logger.getLogger("PRJCBUGEN_T13861");  
    ArrayList<String> lsLocationNetworks = new ArrayList<String>();
    String  sOrganizationLocationElement = "#gridView .location-name";
    OrganizationPage OrganizationPage = new OrganizationPage();
    String organizationName = "New28394";
    String locationName     = "office";
          
  
    @Feature("CreateLocation.WP") 
    @Story("PRJCBUGEN_T28394") 
    @Description("All location  are shown  for  a manager  with Grant access to all future organizations") 
    @TmsLink("PRJCBUGEN_T28394") 

    @Test(alwaysRun = true, groups = "p2") 
    public void test() throws Exception {
        runTest(this);
    }
   
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");       
        String sCheck = "[alt=\"User Image\"]";
        System.out.println("try to do logout");
        $(sCheck).click();
        $(Selectors.byCssSelector(".open ul li:last-child a")).click();
        System.out.println("user is logout");
        MyCommonAPIs.waitReady();
        
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        
        new OrganizationPage().deleteOrganizationNew(organizationName);
         }

    @Step("Test Step 1: Log in to a pro account  ")
    public void step1() {
   
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
//        Adding Organization
        OrganizationPage.addOrg(organizationName);
       
    }
    
    @Step("Test Step 2:  Go to an organization and then Click on Add location icon. Adding Multiple locations")
    public void step2() {
        HashMap<String, String> locationInfo = new HashMap<String, String>();        
        locationInfo.put("Number of Locations","2");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "United States of America");
        locationInfo.put("Time Zone", "UTC-07:00");   
        OrganizationPage.openOrg(organizationName);
        new AccountPage(false).addMultipleNetwork(locationInfo);
     }   
    @Step("Test Step 3:locations with the same name should be created")
    public void step3() {       
        try {                     
            AccountPage AccountPage =new AccountPage() ;
            OrganizationPage.openOrg(organizationName);
            MyCommonAPIs.waitElement(sOrganizationLocationElement);
            ElementsCollection esc = $$(sOrganizationLocationElement);
            String network;
            int located = 0;
            for (SelenideElement locelem : esc) {  
                if(locelem.getText().contains("Location_")) {
                   located +=1;               
                }             
            }
            if(located ==2) {
                logger.info("Verified  that the user is able to create multiple locations in a pro account (Admin)");
            }
            else {
                assertTrue(false, "Verified  that the user is able to create multiple locations in a pro account (Admin)");                   
            }
            
            }
        catch(Exception e) {
            assertTrue(false, "sOrganizationLocationElement is not found");                   
        
            logger.info("Unable to get "+sOrganizationLocationElement);
        }
      
}
    
    @Step("Test Step 4: logout and login to manager")
    public void step4() {
        
           String sCheck = "[alt=\"User Image\"]";
            System.out.println("try to do logout");
            $(sCheck).click();
            $(Selectors.byCssSelector(".open ul li:last-child a")).click();
            System.out.println("user is logout");
            MyCommonAPIs.waitReady();
            
            WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
            webportalLoginPage.loginByUserPassword(WebportalParam.managerName, WebportalParam.managerPassword);
            
    }
    
    @Step("Test Step 5:locations with the same name should be created")
    public void step5() {       
        try {                     
            AccountPage AccountPage =new AccountPage() ;
            OrganizationPage.openOrg(organizationName);
            MyCommonAPIs.waitElement(sOrganizationLocationElement);
            ElementsCollection esc = $$(sOrganizationLocationElement);
            String network;
            int located = 0;
            for (SelenideElement locelem : esc) {  
                if(locelem.getText().contains("Location_")) {
                   located +=1;               
                }             
            }
            if(located ==2) {
                logger.info("Verified  that the user is able to create multiple locations in a pro account (Admin)");
            }
            else {
                assertTrue(false, "Verified  that the user is able to create multiple locations in a pro account (Admin)");                   
            }
            
            }
        catch(Exception e) {
            assertTrue(false, "sOrganizationLocationElement is not found");                   
        
            logger.info("Unable to get "+sOrganizationLocationElement);
        }
      
}
 
}