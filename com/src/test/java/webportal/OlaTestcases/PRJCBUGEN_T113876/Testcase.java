package webportal.OlaTestcases.PRJCBUGEN_T113876;

import static com.codeborne.selenide.Selenide.$$;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

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
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author Sumanta
 */
public class Testcase extends TestCaseBase {
    String LocName = "PRJCBUGEN_T113876";
    String  sOrganizationLocationElement = "#gridView .location-name"; 
    
    @Feature("OlaTestcases") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T113876") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify that user creates locations under existing ORG") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T113876") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        AccountPage AccountPage =new AccountPage();
        AccountPage.deleteLocation(LocName);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to Insight Webportal")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
    }
    
    @Step("Test Step 2:  to Create multiple locations under the organization")
    public void step2() {
        OrganizationPage page = new OrganizationPage();
        page.gotoPage();
        page.openOrg(WebportalParam.Organizations);

    }
    
    @Step("Test Step 3:Click on Add location icon")
    public void step3() {
        HashMap<String, String> locationInfo = new HashMap<String, String>();      
        locationInfo.put("Location Name", LocName);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "United States of America");
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
              
                if(locelem.getText().contains(LocName)) {
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

}
