package webportal.CreateLocation.WP.PRJCBUGEN_T13873;


import static com.codeborne.selenide.Selenide.$$;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
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
import webportal.weboperation.WebportalLoginPage;

public class Testcase extends TestCaseBase {
    final static Logger logger = Logger.getLogger("PRJCBUGEN_T13873");
    public String   Locdiv      =          "#locationDivsContainer";
    
    ArrayList<String> lsLocationNetworks = new ArrayList<String>();
    
    @Feature("CreateLocation.WP") 
    @Story("PRJCBUGEN_T13873") 
    @Description("Verify that the user can create a location with image")
    @TmsLink("PRJCBUGEN_T13873") 

    @Test(alwaysRun = true, groups = "p1") 
    public void test() throws Exception {
        runTest(this);
    }
   
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        AccountPage AccountPage =new AccountPage();
        MyCommonAPIs.waitReady();
        if (AccountPage.locationName(WebportalParam.location1).exists()) {
            AccountPage.deleteLocation(WebportalParam.location1);
        }    
    
    }

    @Step("Test Step 1: Log in to a premium account;")
    public void step1() {      
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        Selenide.sleep(3000);
        // if Location is  already exits 
        AccountPage AccountPage = new AccountPage();
        MyCommonAPIs.waitReady();
      //  Selenide.sleep(10000);      
        if (AccountPage.locationName(WebportalParam.location1).exists()) {
            AccountPage.deleteLocation(WebportalParam.location1);
        }       
    }

    @Step("Test Step 2:Click on Add location icon")
    public void step2() throws IOException {
        HashMap<String, String> locationInfo = new HashMap<String, String>();      
        locationInfo.put("Location Name", WebportalParam.location1);
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "China");
        locationInfo.put("image", "C:\\WebportalAutomation\\com\\src\\test\\java\\webportal\\CreateLocation\\WP\\PRJCBUGEN_T13873\\\\orgImg.png");
        new AccountPage().addNetwork(locationInfo);
        MyCommonAPIs.waitReady();
    }
    
    @Step("Test Step 3:location with the same name should be created")
    public void step3() {      
        try {                             
            boolean located = false;
            MyCommonAPIs.sleepi(20);
            ElementsCollection    Image_elems  = $$(Selectors.byXpath("//*[@class='location-pic']/a/img")); 
           // MyCommonAPIs.waitElement(Image_elems);
            for(SelenideElement elm : Image_elems) {
                if(!elm.getAttribute("src").contains("network_default.png")) {
                    logger.info("Able to get the elem"+elm.getAttribute("src"));
                    
                    located = true;
                    assertTrue(located,"user is able to get image");                     
                    break;
                }                  
            }
            if(located==false) {
                assertTrue(located,"user is unable to get image"); 
                logger.info("Unable to get the image");             
            }
        }    
        catch(Exception e) {
            e.printStackTrace();
            logger.info("Unable to get webelement");
        }
    }
}
