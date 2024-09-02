package webportal.MDNS.PRJCBUGEN_T31117;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.sun.xml.bind.v2.schemagen.xmlschema.List;

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
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.MDNSPage;
import webportal.webelements.MDNSElement;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;
import webportal.webelements.WirelessQuickViewElement;

/**
 *
 * @author  Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    ArrayList<String> MDNS=new ArrayList<String>(); 

    @Feature("MDNS") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T31117") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify if the mDNS Policy can be edited ( VLAN, Service, SSID's selections)") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T31117") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        MyCommonAPIs.sleepi(10);
        new MDNSPage(false).deletePolicy("MDNS");
        MyCommonAPIs.sleepi(5);
        new MDNSPage(false).disableMDNS();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccount();
    }

    @Step("Test Step 2: check MDNS")
    public void step2() {

        new MDNSPage(false).checkaddSsid();
        new MDNSPage().addPolicy();       
        
        MyCommonAPIs.sleepi(60);
      
        String before = new APUtils(WebportalParam.ap1IPaddress).MDNS(WebportalParam.ap1Model);
        
        assertTrue(before.contains("MDNS"), "policy is not added propely");
        
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("MDNSName", "MDNS");
        locationInfo.put("NewMDNSName", "NewMDNS");
        locationInfo.put("sharedService", "Telnet_Remote_Terminal");
        
        new MDNSPage(false).editMDNSpolicy(locationInfo);  
        
        MyCommonAPIs.sleepi(60);
        String after = new APUtils(WebportalParam.ap1IPaddress).MDNS(WebportalParam.ap1Model);
        
        assertTrue(after.contains(locationInfo.get("MDNSName") ), "policy is not added propely");
        assertTrue(after.contains(locationInfo.get("sharedService") ), "policy is not added propely");
        

    }
    
}