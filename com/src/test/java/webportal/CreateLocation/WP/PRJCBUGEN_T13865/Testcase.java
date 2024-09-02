package webportal.CreateLocation.WP.PRJCBUGEN_T13865;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.testng.Assert.assertTrue;

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
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;;

/**
 * @author Rajni
 */

public class Testcase extends TestCaseBase {
    String  sOrganizationLocationElement = "#gridView .location-name"; 
    final static Logger logger = Logger.getLogger("PRJCBUGEN_T13865");
    
    @Feature("CreateLocation.WP") 
    @Story("PRJCBUGEN_T13865") 
    @Description("Verify that the user is able to create a single location in a pro account (Manager)") 
    @TmsLink("PRJCBUGEN_T13865") 

    @Test(alwaysRun = true, groups = "p2") 
    public void test() throws Exception {
        runTest(this);
    }
   
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
       
    }
    
    @Step("Test Step 1: Log in to a pro account admin for creating org ")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        //Adding Organization
        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.addOrg(WebportalParam.Organizations);
        OrganizationPage.refresh();
        Selenide.sleep(10000);
        //logout from admin
        String sCheck = "[alt=\"User Image\"]";
        System.out.println("try to do logout");
        $(sCheck).click();
        $(Selectors.byCssSelector(".open ul li:last-child a")).click();
        System.out.println("user is logout");
        MyCommonAPIs.waitReady();
       
    }
    @Step("Test Step 2: Log in to a pro account Manger")
    public void step2() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.managerName, WebportalParam.managerPassword);;  
        if (new HamburgerMenuPage(false).closedevicecredits.exists()) {
            new HamburgerMenuPage(false).closedevicecredits.click();
        }
    }

    @Step("Test Step 3:Click on Add location icon")
    public void step3() {
        HashMap<String, String> locationInfo = new HashMap<String, String>();      
        locationInfo.put("Location Name", WebportalParam.location1);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "China");
        new AccountPage().addNetwork(locationInfo);
        MyCommonAPIs.waitReady();
     
    }   
    @Step("Test Step 4:location with the same name should be created")
    public void step4() {
        
        try {                     
            MyCommonAPIs.waitElement(sOrganizationLocationElement);
            ElementsCollection esc = $$(sOrganizationLocationElement);
            String network;
            boolean located = false;
            for (SelenideElement locelem : esc) {  
              
                if(locelem.getText().contains(WebportalParam.location1)) {
                   located =true;               
                }             
            }
            if(located ==true) {
                assertTrue(located, "Verify that the user is able to create a single location in a pro account (Manager)");
                logger.info(" Verify that the user is able to create a single location in a pro account (Manager)");
            }
            else {
                assertTrue(located, "Verify that the user is unable to create a single location in a pro account (Manager)");             
                logger.info("Manager is unable to create a single location");
            }
            
            }
        catch(Exception e) {
            logger.info("Unable to get "+sOrganizationLocationElement);
        }
    
    }
   

@Step("Test Step 5:Deleting location from managers")
public void step5() {    
            AccountPage AccountPage =new AccountPage();
            // Going to delete all Locations created from this testcase       
            MyCommonAPIs.waitElement(sOrganizationLocationElement);
            ElementsCollection esc = $$(sOrganizationLocationElement);
            int location = 0;
            for (SelenideElement locelem : esc) { 
                System.out.println(locelem.getText());
                if(location ==0 &&locelem.getText().contains(WebportalParam.location1)) { 
                    location =1;
                    System.out.println(locelem.getText()+"is deleted");
                    AccountPage.deleteLocation(locelem.getText());
                }
                }
}
@Step("Test Step 6:Logining out from manager")
public void step6() { 
                //logout from manager
                String sCheck = "[alt=\"User Image\"]";
                System.out.println("try to do logout");
                $(sCheck).click();
                $(Selectors.byCssSelector(".open ul li:last-child a")).click();
                System.out.println("user is logout");
                MyCommonAPIs.waitReady();
}

@Step("Test Step 7:Going to delete Organzation")
public void step7() { 
    WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
    webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
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
}
