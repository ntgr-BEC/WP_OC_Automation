package webportal.CreateLocation.WP.PRJCBUGEN_T00010;


import static com.codeborne.selenide.Selenide.$$;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
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

public class Testcase extends TestCaseBase {
    final static Logger logger = Logger.getLogger("PRJCBUGEN_T00010");
    

    @Feature("ChangeLocationPassword.WP") 
    @Story("PRJCBUGEN_T00010") 
    @Description("Verify that the user is able to Change Location Password.") 
    @TmsLink("PRJCBUGEN_T00010") 

    @Test(alwaysRun = true, groups = "p2") 
    public void test() throws Exception {
        runTest(this);
    }
   
	@AfterMethod(alwaysRun = true)
    public void tearDown() {
	    new AccountPage().deleteOneLocation(WebportalParam.location1);
        System.out.println("start to do tearDown");       
    }

    @Step("Test Step 1: Log in to a premium account;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
        HashMap<String, String> locationInfo = new HashMap<String, String>();      
        locationInfo.put("Location Name", WebportalParam.location1);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "China");
        new AccountPage().addNetwork(locationInfo);
        MyCommonAPIs.waitReady();
      }

    @Step("Test Step 2: Edit Location and Change Password")
    public void step2() throws IOException {
        
        new AccountPage().editAndChangePasswordPriaccLocation();
        assertTrue(new AccountPage().verifyLocPassword(),"password is not updated successfully");
        
    }
    

}
