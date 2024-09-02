package webportal.CreateLocation.WP.PRJCBUGEN_T13870;


import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

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
    final static Logger logger = Logger.getLogger("PRJCBUGEN_T13870");
    
    ArrayList<String> lsLocationNetworks = new ArrayList<String>();
    String  sOrganizationLocationElement = "#gridView .location-name"; 

    @Feature("CreateLocation.WP") 
    @Story("PRJCBUGEN_T13870") 
    @Description("verify that user can change location password, country, and zip code") 
    @TmsLink("PRJCBUGEN-T13870") 

    @Test(alwaysRun = true, groups = "p2") 
    public void test() throws Exception {
        runTest(this);
    }
   
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        AccountPage AccountPage =new AccountPage();
        MyCommonAPIs.waitReady();
        if (AccountPage.locationName("Loc2").exists()) {
            AccountPage.deleteLocation("Loc2");
        }        
    }

    @Step("Test Step 1: Log in to a premium account;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
               
    }

   @Step("Test Step 2:Click on Add location icon")
    public void step2() throws IOException {
        HashMap<String, String> locationInfo = new HashMap<String, String>();      
        locationInfo.put("Location Name", WebportalParam.location1);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "China");
        new AccountPage().addNetwork(locationInfo);
       
    }
    
    @Step("Test Step 3:location with the same name should be created")
    public void step3(){
        AccountPage AccountPage = new AccountPage();
        HashMap<String, String> locationInfo = new HashMap<String, String>();      
        locationInfo.put("Location Name","Loc2");
        locationInfo.put("Device Admin Password","Password@1");
        locationInfo.put("Zip Code", "145");
        locationInfo.put("Country", "Nigeria");  
        MyCommonAPIs.sleep(10000);       
        AccountPage.editLocation(WebportalParam.location1,locationInfo);
        MyCommonAPIs.sleepi(10);
        boolean located=false;
        if (AccountPage.locationName("Loc2").exists()) {
            located =true;
        }              
        assertTrue(located,"verified that user can change location password, country, and zip code");         
        }
}
