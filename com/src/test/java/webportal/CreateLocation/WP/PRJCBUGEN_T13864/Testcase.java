package webportal.CreateLocation.WP.PRJCBUGEN_T13864;

import static com.codeborne.selenide.Selenide.$$;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.logging.Logger;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.ElementsCollection;
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
    String  sOrganizationLocationElement = "#gridView .location-name"; 
    
    final static Logger logger = Logger.getLogger("PRJCBUGEN_T13864");
    
    
    @Feature("CreateLocation.WP") 
    @Story("PRJCBUGEN_T13864") 
    @Description("Verify that the user is able to create a single location in a pro account (Admin)") 
    @TmsLink("PRJCBUGEN_T13864") 

    @Test(alwaysRun = true, groups = "p2") 
    public void test() throws Exception {
        runTest(this);
    }
   
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        AccountPage AccountPage =new AccountPage();
        // Going to delete all Locations created from this testcase       
        MyCommonAPIs.waitElement(sOrganizationLocationElement);
        ElementsCollection esc = $$(sOrganizationLocationElement);
        for (SelenideElement locelem : esc) { 
            System.out.println(locelem.getText());
            if(locelem.getText().contains(WebportalParam.location1+"_Location_")) { 
                System.out.println(locelem.getText()+"is deleted");
                AccountPage.deleteLocation(locelem.getText());
            }
            }
        Selenide.sleep(10000);
        OrganizationPage OrganizationPage = new OrganizationPage();
        MyCommonAPIs.waitElement(sOrganizationLocationElement);
        ElementsCollection esc1 = $$(sOrganizationLocationElement);
        for (SelenideElement locelem : esc1) { 
            System.out.println(locelem.getText());
            if(locelem.getText().contains(WebportalParam.Organizations)) { 
                System.out.println(locelem.getText()+"is deleted");
                OrganizationPage.deleteOrganization(locelem.getText());
            }
        }
    }

    @Step("Test Step 1: Log in to a pro account;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);;
        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.addOrg(WebportalParam.Organizations); 
    
    }

    @Step("Test Step 2:Click on Add location icon")
    public void step2() {
        AccountPage AccountPage =new AccountPage() ;
        HashMap<String, String> locationInfo = new HashMap<String, String>();      
        locationInfo.put("Location Name", WebportalParam.location1);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "China");
        new AccountPage().addNetwork(locationInfo);
       
    }   
    @Step("Test Step 3:location with the same name should be created")
    public void step3() {       
        try {                
            AccountPage AccountPage =new AccountPage() ;
            MyCommonAPIs.waitElement(sOrganizationLocationElement);
            ElementsCollection esc = $$(sOrganizationLocationElement);
            String network;
            boolean located = false;
            for (SelenideElement locelem : esc) {  
                System.out.println(locelem.getText());
                if(locelem.getText().contains(WebportalParam.location1)) {
                   located =true;               
                }             
            }
            if(located ==true) {
                assertTrue(located, "Verified that the admin is able to create a single location");
                logger.info("Verified that the admin is able to create a single location");
            }
            else {
                assertTrue(located, "admin is unable to create a single location");
                logger.info("admin is unable to create a single location");
            }
            
            }
        catch(Exception e) {
            logger.info("Unable to get "+sOrganizationLocationElement);
        }
    
    }
   
}
