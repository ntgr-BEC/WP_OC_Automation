package webportal.MDNS.prouser.PRJCBUGEN_T31116;

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
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.MDNSPage;
import webportal.weboperation.OrganizationPage;
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
    @Story("PRJCBUGEN_T31116") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify if SSIDs with different securities can be selected during the mDNS policy creation") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T31116") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        new MDNSPage(false).disableMDNS();
        new WirelessQuickViewPage().deleteALLSSID();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
        new HamburgerMenuPage(false).closeLockedDialog();
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);

        handle.gotoLoction();
        // new DevicesDashPage().checkDeviceInAdminAccount();
    }

    @Step("Test Step 2: check MDNS")
    public void step2() {

        ArrayList<String> list = new ArrayList<String>();
        Random r = new Random();
         
        int i;
        for(i=0;i<=6;i++) {
        int num = r.nextInt(10000);
        String mailname = "ssid" + String.valueOf(num);
        list.add(mailname);
        }
        System.out.println(list);
                 
        Map<String, String> locationInfo = new HashMap<String, String>();
        locationInfo.put("SSID", list.get(0));
        System.out.println(list.get(0));
        locationInfo.put("Band", "Both");
        locationInfo.put("Security", "Open");
        new WirelessQuickViewPage().addSsid1(locationInfo);
        
        Map<String, String> locationInfo1 = new HashMap<String, String>();
        locationInfo1.put("SSID", list.get(1));
        System.out.println(list.get(1));
        locationInfo1.put("Band", "Both");
        locationInfo1.put("Security", "WPA3 Personal Mixed (WPA2 + WPA3)");
        locationInfo1.put("Password", "123456798");
        new WirelessQuickViewPage(false).addSsid1(locationInfo1);
        
        Map<String, String> locationInfo2 = new HashMap<String, String>();
        locationInfo2.put("SSID", list.get(2));
        System.out.println(list.get(2));
        locationInfo2.put("Band", "Both");
        locationInfo2.put("Security", "WPA3 Personal");
        locationInfo2.put("Password", "123456798");
        new WirelessQuickViewPage(false).addSsid1(locationInfo2);
        
        Map<String, String> locationInfo3 = new HashMap<String, String>();
        locationInfo3.put("SSID", list.get(3));
        System.out.println(list.get(3));
        locationInfo3.put("Band", "Both");
        locationInfo3.put("Security", "WPA2 Personal Mixed");
        locationInfo3.put("Password", "123456798");
        new WirelessQuickViewPage(false).addSsid1(locationInfo3);
        
        Map<String, String> locationInfo4 = new HashMap<String, String>();
        locationInfo4.put("SSID", list.get(4));
        System.out.println(list.get(4));
        locationInfo4.put("Band", "Both");
        locationInfo4.put("Security", "WPA2 Personal");
        locationInfo4.put("Password", "123456798");
        new WirelessQuickViewPage(false).addSsid1(locationInfo4);
        
        new MDNSPage().enableMDNS();
        
        assertTrue(!new MDNSPage().MDNScheck(),"is  disabled ");
        
    
    }
    
}