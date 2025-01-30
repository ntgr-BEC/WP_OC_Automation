package webportal.CreateLocation.WP.PRJCBUGEN_T13861;

import static com.codeborne.selenide.Selenide.$$;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
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
    
    final static Logger logger = Logger.getLogger("PRJCBUGEN_T13861");  
    ArrayList<String> lsLocationNetworks = new ArrayList<String>();
    String  sOrganizationLocationElement = "#gridView .location-name";
          
  
    @Feature("CreateLocation.WP") 
    @Story("PRJCBUGEN_T13861") 
    @Description("Verify that the user is able to create multiple locations in a pro account (Admin)") 
    @TmsLink("PRJCBUGEN_T13861") 

    @Test(alwaysRun = true, groups = "p2") 
    public void test() throws Exception {
        runTest(this);
    }
   
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");       
        // deleting Organization
        OrganizationPage OrganizationPage = new OrganizationPage();
        Selenide.sleep(10000);
        ElementsCollection esc1 = $$(sOrganizationLocationElement);
        for (SelenideElement locelem : esc1) { 
            System.out.println(locelem.getText());
            if(locelem.getText().contains(WebportalParam.Organizations)) { 
                System.out.println(locelem.getText()+"is deleted");
                OrganizationPage.deleteOrganization(locelem.getText());
            }
            }   
         }

    @Step("Test Step 1: Log in to a pro account  ")
    public void step1() {
   
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
       // Adding Organization
        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.addOrg(WebportalParam.Organizations);
       
    }
    
    @Step("Test Step 2:  Go to an organization and then Click on Add location icon. Adding Multiple locations")
    public void step2() {
        HashMap<String, String> locationInfo = new HashMap<String, String>();        
        locationInfo.put("Number of Locations","2");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "United States of America");      
        new AccountPage().addMultipleNetwork(locationInfo);
     }   
    @Step("Test Step 3:locations with the same name should be created")
    public void step3() {       
        try {                     
            AccountPage AccountPage =new AccountPage() ;
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
    @Step("Test Step 4:Deleting locations ")
    public void step4() {  
        // Going to delete all Locations created from this testcase       
//       MyCommonAPIs.waitElement(sOrganizationLocationElement);
//        ElementsCollection esc = $$(sOrganizationLocationElement);
//        System.out.println(esc);
//        for (SelenideElement locelem : esc) { 
//            System.out.println(locelem.getText());
//            if(locelem.getText().contains("Location_")) { 
//                System.out.println(locelem.getText()+"is deleteds");
//                AccountPage.deleteLocation(locelem.getText());
//                MyCommonAPIs.sleepi(10);
//    }
       for(int i=1;i<=2;i++) {
           new AccountPage().deleteOneLocation("Location_"+i);
        System.out.println("Location_"+i +"is deleted");    
            }
    }
}