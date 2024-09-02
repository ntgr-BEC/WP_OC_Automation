package webportal.OoklaSpeedtest.PRJCBUGEN_T16507;
import static org.testng.Assert.assertTrue;

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
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesOoklaSpeedtestPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
public class TestCase extends TestCaseBase{

    boolean micResult = false;
    @Feature("Ookla Speedtest") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T16507") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify whether we are getting results for the download speed") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T16507") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    
    public void test() throws Exception {
        runTest(this);
    }

 
    @AfterMethod(alwaysRun = true)
    public void tearDown() {

    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
        //handle.gotoLoction();
        //new DevicesDashPage().checkDeviceInNormalAccount("admin"); //must input admin or manager
    }
    @Step("Test Step 2: Create new location and add device in this location;")
    public void step2() {
       
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("Location Name", "Ookla Speedtest");
        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
        locationInfo.put("Zip Code", "12345");
        locationInfo.put("Country", "Canada");
        new AccountPage().addNetwork(locationInfo);

        new AccountPage().enterLocation("Ookla Speedtest");
//        Map<String, String> devInfo = new HashMap<String, String>();
//        devInfo.put("Serial Number", WebportalParam.ap2serialNo);
//        devInfo.put("Device Name", WebportalParam.ap2deveiceName);
//        devInfo.put("MAC Address1", WebportalParam.ap2macaddress);
//        new DevicesDashPage(false).addNewDevice(devInfo);
//        
//        DevicesDashPage DevicesDashPage= new DevicesDashPage();
//        boolean Result = false;
//        Result = DevicesDashPage.waitDevicesReConnected(WebportalParam.ap2serialNo);
//        if (Result == true) {
//            micResult =  true;
//            assertTrue(micResult,"Pass:Connect Device successfully !");  
//        }else {
//            micResult = false;
//            assertTrue(micResult,"Failed:Connect Device unsuccessfully!"); 
//        } 
    }
    @Step("Test Step 3: Now once the device comes online, go to the device dashboard and go to Troubleshoot -> Ookla Speedtest page to get download info")
    public void step3() {
        boolean Result = false;
        DevicesDashPage DevicesDashPage= new DevicesDashPage();
        DevicesDashPage.enterDevice( WebportalParam.ap2serialNo);
        DevicesOoklaSpeedtestPage DeviceOoklaSpeedtestPage = new DevicesOoklaSpeedtestPage();
        DeviceOoklaSpeedtestPage.gotoAPOoklaSpeedtestpage();
        DeviceOoklaSpeedtestPage.ClickRuntestButton();
        String DownloadInfo = DeviceOoklaSpeedtestPage.GetDownloadInfo();
        if (!DownloadInfo.equals("-")) {
            Result = true;
        }
        if (Result == true) {
            micResult = true;
            assertTrue(micResult,"Pass:Get the network upload speed with appropriate unit successfully !");  
        }else {
            micResult = false;
            assertTrue(micResult,"Failed:Get the network upload speed with appropriate unit unsuccessfully!"); 
        } 
        
    }


}
