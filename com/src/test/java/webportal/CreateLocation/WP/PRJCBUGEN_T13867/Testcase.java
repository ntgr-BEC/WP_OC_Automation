package webportal.CreateLocation.WP.PRJCBUGEN_T13867;


import static com.codeborne.selenide.Selenide.$$;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

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
    final static Logger logger = Logger.getLogger("PRJCBUGEN_T13867");
    
    ArrayList<String> lsLocationNetworks = new ArrayList<String>();
    String  sOrganizationLocationElement = "#gridView .location-name"; 

    @Feature("CreateLocation.WP") 
    @Story("PRJCBUGEN_T13867") 
    @Description("Verify that the user is able to delete location") 
    @TmsLink("PRJCBUGEN_T13867") 

    @Test(alwaysRun = true, groups = "p1") 
    public void test() throws Exception {
        runTest(this);
    }
   
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
           }

    @Step("Test Step 1: Log in to a premium account;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
      
    }

    @Step("Test Step 2:Click on Add location icon")
    public void step2() {
        HashMap<String, String> locationInfo = new HashMap<String, String>();      
        locationInfo.put("Location Name", WebportalParam.location1);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "China");
        new AccountPage().addNetwork(locationInfo);
        MyCommonAPIs.waitReady();
    }
    
    @Step("Test Step 3:location with the same name should be created")
    public void step3() {      
        try {      
            AccountPage AccountPage = new AccountPage();
            MyCommonAPIs.waitElement(sOrganizationLocationElement);
            ElementsCollection esc = $$(sOrganizationLocationElement);
            String network;
            int located = 0;
            for (SelenideElement locelem : esc) {                 
                if(located==0 && locelem.getText().contains(WebportalParam.location1)) {
                   located =1;               
                }             
            }
            if(located ==1) {
                
                logger.info("Verified that the user is able to create a single location");
            }
            else {
                logger.info("the user is unable to create a single location");
            }
            
            }
        catch(Exception e) {
            logger.info("Unable to get "+sOrganizationLocationElement);
        }
    
    }
    @Step("Test Step 4:user is able to delete location")
    public void step4() { 
        AccountPage AccountPage =new AccountPage();   
        boolean    result      = true;
        MyCommonAPIs.waitReady();
        try {
        if (AccountPage.locationName(WebportalParam.location1).exists()) {           
            AccountPage.deleteLocation(WebportalParam.location1) ;           
        }
        MyCommonAPIs.waitReady();
        ElementsCollection esc = $$(sOrganizationLocationElement);
        String network;
        int located = 0;
        for (SelenideElement locelem : esc) {                 
            if(located==0 && locelem.getText().contains(WebportalParam.location1)) {
                result=false;               
            }             
        }
        if(result) {        
            assertTrue(result,"user is able to delete location");       
        }
        else {
            assertTrue(result,"user is unable to delete location");           
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            
            logger.info("Unable to get delete location");
        }
        }
    
}
