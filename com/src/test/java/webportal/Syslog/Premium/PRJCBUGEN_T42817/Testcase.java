package webportal.Syslog.Premium.PRJCBUGEN_T42817;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.SelenideElement;

import bsh.ParseException;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.APUtils;
import util.Javasocket;
import util.MyCommonAPIs;
import util.RunCommand;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.AccountPageElement;
import webportal.webelements.DeviceGroupElement;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DeviceGroupPage;
import webportal.weboperation.DevicesApSummaryPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.FileHandling;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author  RaviShankar 
 *
 */
public class Testcase extends TestCaseBase {
    
    
    String path="C:\\Syslog\\LOG.txt";
    String Global;
    Random random = new Random();
    int randomNumber = random.nextInt(10000);
    String ssid    = "SSID" + String.valueOf(randomNumber);
    
    @Feature("Syslog") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T42817") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify Default status of Syslog") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T42817") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new DeviceGroupPage().GoToSysLog(WebportalParam.location1);
        if(MyCommonAPIs.checkSelected(new DeviceGroupElement().enbleSysLog))
        {
            new DeviceGroupPage().disableSysLog();
        }
        
    }
    
  

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
    }

    @Step("Test Step 2: Go to Syslog and enable")
    public void step2() {
        try {
        InetAddress inetAddress = InetAddress.getLocalHost();
        Global=inetAddress.getHostAddress();
        System.out.print(inetAddress.getHostAddress());
        }
        catch(UnknownHostException e){
            e.printStackTrace();
        }
        new AccountPageElement().editNetwork(WebportalParam.location1);
        MyCommonAPIs.sleepi(5);
        new DeviceGroupPage().enableSysLogAndVerifySuccessMsg(Global, "514");
    
    }
    
    @Step("Test Step 3: Add WIFI ssid and now connect client to this ssid;")
    public void step3() {
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("SSID", ssid);
        locationInfo.put("Band", "Both");
        locationInfo.put("Security", "WPA2 Personal");
        locationInfo.put("Password", "123456798");
        new WirelessQuickViewPage().addSsid1(locationInfo);
        MyCommonAPIs.sleepi(30);
        new WirelessQuickViewPage().connectClient(locationInfo);
        
    }
    
    
    @Step("Test Step 4: Add WIFI ssid and now connect client to this ssid;")
    public void step4() throws ParseException, IOException, java.text.ParseException {              
        assertTrue(new FileHandling().assertLogAfterTime(path, ssid),"NOT FOUND SSID");        
    }
    
    @Step("Test Step5:check for config push")
    public void step5(){
        boolean status=false;
        MyCommonAPIs.sleepi(120);
        String result = new APUtils(WebportalParam.ap1IPaddress).SysLogEnableStatus(WebportalParam.ap1Model);
        if(result.contains("logSettings:syslogStatus 1") && result.contains("logSettings:syslogSrvIp "+Global) && result.contains("logSettings:syslogSrvPort 514")&& result.contains("logSettings:syslogSrvPort 514")) {
            status = true;
        }
        
        assertTrue(status == true , "syslog  is disabled after enabling");
        
    }

    
    
}