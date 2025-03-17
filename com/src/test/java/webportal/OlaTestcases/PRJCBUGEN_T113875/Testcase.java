package webportal.OlaTestcases.PRJCBUGEN_T113875;

import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$$x;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author Sumanta
 */
public class Testcase extends TestCaseBase {
    
    final static Logger logger = Logger.getLogger("PRJCBUGEN_T113875");  
    ArrayList<String> lsLocationNetworks = new ArrayList<String>();
    String  sOrganizationLocationElement = "//*[@col-id='locations']/..//*[@class='linkUnderlin']";
    
    String organizationName = "PRJCBUGEN_T113875";
    
    @Feature("OlaTestcases") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T113875") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that the user is able to Create multiple locations under the newly created organization") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T113875") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        OrganizationPage page = new OrganizationPage();
        page.deleteOrganizationNew(organizationName);
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to Insight Webportal")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
    }
    
    @Step("Test Step 2: Verify that the user is able to create an organization without an owner")
    public void step2() {
        OrganizationPage page = new OrganizationPage();
        page.gotoPage();
        
        Map<String, String> organizationInfo = new HashMap<String, String>();
        organizationInfo.put("Name", organizationName);
        
        page.addOrganization(organizationInfo);
        assertTrue(page.checkOrganizationIsExist(organizationName), "check user is able to create an organization");

    }
    
    @Step("Test Step 3:  Go to an organization and then Click on Add location icon. Adding Multiple locations")
    public void step3() {
        new OrganizationPage(false).openOrg(organizationName);
        HashMap<String, String> locationInfo = new HashMap<String, String>();        
        locationInfo.put("Number of Locations","2");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "United States of America");      
        new AccountPage(false).addMultipleNetwork(locationInfo);
        new OrganizationPage(false).openOrg(organizationName);
     }   
    @Step("Test Step 4:locations with the same name should be created")
    public void step4() {       
        try {                     
            ElementsCollection esc = $$x(sOrganizationLocationElement);
            esc.get(0).waitUntil(Condition.visible, 10000);
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
