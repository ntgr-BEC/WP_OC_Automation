package webportal.CFD.AP.PRO.PRJCBUGEN_T27400;

import static com.codeborne.selenide.Selenide.$$;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
import util.APUtils;
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.CommonDataType;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.AccountPage;
import webportal.weboperation.CopyConfigurationPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.InsightServicesPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini 
 *
 */
public class Testcase extends TestCaseBase {

    Map<String, String> ssidInfo                 = new HashMap<String, String>();
    Map<String, String> organizationInfo         = new HashMap<String, String>();
    String organizationName1 = "PRJCBUGEN_T138811";
    String  sOrganizationLocationElement = "#gridView .location-name";

    @Feature("AP") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T27400") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to create a new  location through \" Add multiple location\" for new organization.")
    @TmsLink("PRJCBUGEN-T27400") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        OrganizationPage page = new OrganizationPage();
        page.deleteOrganizationNew(organizationName1);
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);

       
    }
        
        @Step("Test Step 2: Add Organization;")
        public void step2() {
            
            Map<String, String> organizationInfo = new HashMap<String, String>();
            organizationInfo.put("Name", organizationName1);

            OrganizationPage OrganizationPage = new OrganizationPage();
            OrganizationPage.addOrganization(organizationInfo);
                   
    }
        @Step("Test Step 3:  Go to an organization and then Click on Add location icon. Adding Multiple locations")
        public void step3() {
            HashMap<String, String> locationInfo = new HashMap<String, String>();        
            locationInfo.put("Number of Locations","2");
            locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
            locationInfo.put("Zip Code", "12345");
            locationInfo.put("Country", "United States of America");    
            new AccountPage().addMultipleNetwork(locationInfo);
         }   
        @Step("Test Step 4:locations with the same name should be created")
        public void step4() {       
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

     }
