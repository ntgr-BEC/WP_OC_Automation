package webportal.DeviceGroup.CustomGroupNPI.PRJCBUGEN_T32421;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.APUtils;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DeviceGroupPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;
import webportal.webelements.DeviceGroupElement;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {
    
    OrganizationPage OrganizationPage = new OrganizationPage();
    Map<String, String> locationInfo = new HashMap<String, String>();
    int check = 1;

    @Feature("DeviceGroup CustomGroupNPI") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T32421") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify, device on-boarded to CG will have location config ( pushed Radius/syslog/SNMP/LED/RRM/VLANs/Routing/FW upgrade)") // It's a test case title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T32421") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        OrganizationPage.openOrg(WebportalParam.Organizations);
        new DeviceGroupPage().GoToDeviceGroup(WebportalParam.location1);
        MyCommonAPIs.sleepi(5);
        new DeviceGroupPage().deleteCGGroupALL();
        new DeviceGroupPage().disableSysLog();
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        
        OrganizationPage.openOrg(WebportalParam.Organizations);
    }
    
    
    @Step("Test Step 2: Go to Syslog and enable")
    public void step2() {
        new DeviceGroupPage().GoToSysLog(WebportalParam.location1);
        new DeviceGroupPage().EnableSysLog("1.1.1.1", "514");

    }
    
    @Step("Test Step 3: Go to LED and set")
    public void step3() {
        new DeviceGroupPage().GoToLED(WebportalParam.location1);
        new DeviceGroupPage().SetLED(check);

    }
    
    @Step("Test Step 4: Go to SNMP and enable")
    public void step4() {
        new DeviceGroupPage().GoToSNMP(WebportalParam.location1);
        new DeviceGroupPage().EnableSNMP("1.1.1.1", "Automation1@");
        
    }

    @Step("Test Step 5: Add device to DG;")
    public void step5() {
        
        new DeviceGroupPage().GoToDeviceGroup(WebportalParam.location1);
        MyCommonAPIs.sleepi(5);
        new DeviceGroupPage().CreateDGGroup("Automation1", "Check Grop creation");
        MyCommonAPIs.sleepi(5);
        new DeviceGroupPage().goinsideDG("Automation1");
        MyCommonAPIs.sleepi(5);
        new DeviceGroupPage().DGWiFi.click();
         
       
    }

  
    
   
    
    @Step("Test Step 6: oboard device and wait for config Push;")
    public void step6() {
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.ap1serialNo);
        devInfo.put("MAC Address1", WebportalParam.ap1macaddress);

        new DeviceGroupPage().addNewDevice(devInfo, "Automation1");
        MyCommonAPIs.sleepi(60); 
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        MyCommonAPIs.sleepi(60); 

    }
        
    @Step("Test Step 7: Disable 5ghz")
    public void step7() {
        boolean status = false;
        boolean status1 = false;
        boolean status2 = false;
        
        String result = new APUtils(WebportalParam.ap1IPaddress).SysLogEnableStatus(WebportalParam.ap1Model);
        if(result.contains("logSettings:syslogStatus 1") && result.contains("logSettings:syslogSrvIp 1.1.1.1") && result.contains("logSettings:syslogSrvPort 514")) {
            status = true;
        }
        
        if(result.contains("ledControl 1")) {
            status1 = true;
        }
        
        if(result.contains("snmpStatus 1") ) {
            status2 = true;
        }
        assertTrue(status == true && status1 == true && status2 == true, "syslog  is disabled after enabling");
    }
        
     
}