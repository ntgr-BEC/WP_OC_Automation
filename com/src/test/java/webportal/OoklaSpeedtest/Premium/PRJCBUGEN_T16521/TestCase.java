package webportal.OoklaSpeedtest.Premium.PRJCBUGEN_T16521;

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
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesOoklaSpeedtestPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author Sujuan
 *
 */
public class TestCase extends TestCaseBase {
    boolean micResult = false;
    @Feature("Ookla Speedtest Premium") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T16521") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify whether we get a proper time stamp for all tests done in the past") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T16521") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    
    public void test() throws Exception {
        runTest(this);
    }

 
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
//        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccount();
    }
    @Step("Test Step 2: Create new location and add device in this location;")
    public void step2() {
     
//        Map<String, String> locationInfo = new HashMap<String, String>();
//        locationInfo.put("Location Name", "Ookla Speedtest");
//        locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
//        locationInfo.put("Zip Code", "12345");
//        locationInfo.put("Country", "Canada");
//        new AccountPage().addNetwork(locationInfo);
        MyCommonAPIs.sleepi(10);
        new AccountPage().enterLocation("Ookla Speedtest");
//        Map<String, String> devInfo = new HashMap<String, String>();
//        devInfo.put("Serial Number", WebportalParam.ap1serialNo);
//        devInfo.put("Device Name", WebportalParam.ap1deveiceName);
//        devInfo.put("MAC Address", WebportalParam.ap1macaddress);
//        new DevicesDashPage(false).addNewDevice(devInfo);
//        
//        
//        DevicesDashPage DevicesDashPage= new DevicesDashPage();
//        boolean Result = false;
//        Result = DevicesDashPage.waitDevicesReConnected(WebportalParam.ap1serialNo);
//       
//        if ((Result == true)) {
//            micResult =  true;
//            assertTrue(micResult,"Pass:Connect Device successfully !");  
//        }else {
//            micResult = false;
//            assertTrue(micResult,"Failed:Connect Device unsuccessfully!"); 
//        } 
    }
    @Step("Test Step 3: Now once the device comes online, go to the device dashboard,upload and download speeds with appropriate unit")
    public void step3() {
        boolean Result = false;
        DevicesDashPage DevicesDashPage= new DevicesDashPage();
        DevicesDashPage.enterDevice( WebportalParam.ap1serialNo);
        DevicesOoklaSpeedtestPage DeviceOoklaSpeedtestPage = new DevicesOoklaSpeedtestPage();
        DeviceOoklaSpeedtestPage.gotoAPOoklaSpeedtestpage();
        DeviceOoklaSpeedtestPage.ClickRuntestButton();
        String UploadInfo = DeviceOoklaSpeedtestPage.GetUploadInfo();
        String DownloadInfo = DeviceOoklaSpeedtestPage.GetDownloadInfo();
        String latencyInfo = DeviceOoklaSpeedtestPage.GetLatencyInfo();
        System.out.println(UploadInfo);
        System.out.println(DownloadInfo);
        System.out.println(latencyInfo);
        if (!UploadInfo.equals("-") && !DownloadInfo.equals("-") && !latencyInfo.equals("-")) {
            Result = true;
        }
        if (Result == true) {
            micResult = true;
            assertTrue(micResult,"Pass:Get the network upload , download speed and latency with appropriate unit successfully !");  
        }else {
            micResult = false;
            assertTrue(micResult,"Failed:Get the network upload ,download speed and latency with appropriate unit unsuccessfully!"); 
        }
        System.out.println("000011111111111111112333333333333333333");
        Result = false;
        String webportaTime  = DeviceOoklaSpeedtestPage.getWebpotalTime();
        System.out.println(webportaTime);
       // DevicesDashPage.enterDevice( WebportalParam.ap1serialNo);
        String systemTime = DeviceOoklaSpeedtestPage.getSystemDate();
        System.out.println(systemTime);
        
    
        if (webportaTime.contains(systemTime)) {
            Result = true;
        }
        if (Result == true) {
            micResult = true;
            assertTrue(micResult,"Pass:Along with the test results with the corresponding network timezone!");  
        }else {
            micResult = false;
            assertTrue(micResult,"Failed:Along with the test results with the uncorresponding network timezone!"); 
        } 
        
    }
}
