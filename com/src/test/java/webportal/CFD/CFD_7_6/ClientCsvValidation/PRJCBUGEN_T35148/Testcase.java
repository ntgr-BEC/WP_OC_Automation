package webportal.CFD.CFD_7_6.ClientCsvValidation.PRJCBUGEN_T35148;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.APUtils;
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.WirelessQuickViewElement;
import webportal.weboperation.*;


/**
 *
 * @author RaviShankar S
 * PRECONDITION--INTESTCASE UNCOMMENT THE DOWNLOAD PATH
 * Need to have folder C:\\AutomationDownloads\\
 * give admin permission to folder
 *
 */
public class Testcase extends TestCaseBase {
    Map<String, String> locationInfo = new HashMap<String, String>();
    String Disconnect="NO";
    String csvFilePath="C:\\AutomationDownloads\\clientlist.csv";
        
    @Feature("Verify Client list") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T35148") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify wireless page--> client list when a user downloads the CSV then shows the correct data.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T35148") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().deleteALLSSID();
        System.out.println("start to do tearDown");
        File csvFile = new File(csvFilePath);
        if (csvFile.exists()) {
            boolean deleted = csvFile.delete();
            if (!deleted) {
                System.out.println("Warning: Failed to delete CSV file: " + csvFilePath);
            } else {
                System.out.println("Successfully deleted CSV file: " + csvFilePath);
            }
        }
    }



    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();
    }

  
    @Step("Test Step 2:  Add WIFI ssid and now connect client to this ssid;")
    public void step2() {                  
        locationInfo.put("SSID", "apssidT35148");
        locationInfo.put("Band", "Both");
        locationInfo.put("Security", "WPA2 Personal");
        locationInfo.put("Password", "123456798");
        new WirelessQuickViewPage().addSsid1(locationInfo);
        new WirelessQuickViewPage().connectClient(locationInfo);

       
    }
    
    
    @Step("Test Step 4: Check whether connected connect is shown in Wireless page ,client page and device dash board;")
    public void step4() {

        MyCommonAPIs.sleepi(180);
        assertTrue(new WirelessQuickViewPage().checkClientConnectClientPage(WebportalParam.clientwlanmac, Disconnect), "Client not listed on wireless page");
        //CLICK on Download CSV BUTTON
        MyCommonAPIs.sleepi(10);
        new WirelessQuickViewElement().downloadClientList.click();
        MyCommonAPIs.sleepi(20);

    }

    @Step("Test Step 5: Compare the downloaded CSV file with the connected client list;")
    public void step5() {
        File csvFile = new File(csvFilePath);
        assertTrue(csvFile.exists(), "CSV file was not downloaded");

        // Wait a bit to ensure file is fully written
        MyCommonAPIs.sleepi(5);

        // Validate client in CSV
        assertTrue(new FileHandling().validateCSVForClientListing(csvFilePath,
                        WebportalParam.client1name, WebportalParam.clientwlanmac),
                "Client not listed on CSV file");

    }
    
   
    
        
    }
    