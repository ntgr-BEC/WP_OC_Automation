package webportal.APBasic.Enterprise.PRO.PRJCBUGEN_T12361;

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
import util.APUtils;
import util.Javasocket;
import util.MyCommonAPIs;
import util.RunCommand;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Sumanta
 *
 */
public class Testcase extends TestCaseBase {
    
    Map<String, String> locationInfo = new HashMap<String, String>();

    @Feature("APBasic.Enterpries.PRO") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T12361") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("verify creation of ORG WPA2 Enterprise Security as pro admin and connect to a clinet ") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T12361") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        new WirelessQuickViewPage().deleteSsidYes("apwp12361");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);

        handle.gotoLoction();
//        new DevicesDashPage().checkDeviceInNormalAccount("admin"); //must input admin or manager
    }

    @Step("Test Step 2: Add organizationWide SSID")
    public void step2() {
        
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        new OrganizationPage(false).goToOrgSsid(WebportalParam.Organizations);
        locationInfo.put("SSID", "apwp12361");
        locationInfo.put("Security", "WPA2 Enterprise");
        new OrganizationPage(false).CreateOrgSSId(locationInfo);
        
        new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "sudo -S nmcli --terse connection show | grep wireless | cut -d : -f 2 |   while read name; do sudo nmcli connection delete $name; done");
        MyCommonAPIs.sleepi(60);
        new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "sudo nmcli device wifi rescan");
        MyCommonAPIs.sleepi(20);
        new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "sudo nmcli device wifi list | grep -F \"apwp12361\"");
        MyCommonAPIs.sleepi(20);
        new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "sudo -S nmcli c add type wifi con-name \"apwp12361\" ifname wlp4s0 ssid \"apwp12361\"");      
        MyCommonAPIs.sleepi(20);
        new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "sudo -S nmcli connection modify \"apwp12361\" wifi-sec.key-mgmt wpa-eap 802-1x.eap peap 802-1x.phase2-auth mschapv2 802-1x.identity \"teju\" 802-1x.password \"teju\"");
        MyCommonAPIs.sleepi(20);
        new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "sudo -S nmcli connection up \"apwp12361\"");
    
    }
    
    @Step("Test Step 3: check organizationWide SSID Present on UI")
    public void step3() {
    
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        new MyCommonAPIs().gotoLoction(WebportalParam.location1);
        new OrganizationPage(false).goWirelessSetting();
        assertTrue(new OrganizationPage(false).checkOrgSsidIsExist("apwp12361"),"ssid does not exits in Loc 1");
        
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        new MyCommonAPIs().gotoLoction(WebportalParam.location2);
        new OrganizationPage(false).goWirelessSetting();
        assertTrue(new OrganizationPage(false).checkOrgSsidIsExist("apwp12361"),"ssid does not exits in Loc 2");
        
    }
    
    @Step("Test Step 4: enable SHH and check command")
    public void step4() {
        
        new RunCommand().enableSSH4APALL(WebportalParam.ap1IPaddress);
       
        
        assertTrue(new APUtils(WebportalParam.ap1IPaddress).getSsidStatus1("apwp12361", WebportalParam.ap1Model),"ssid is not pushed to AP");
       // assertTrue(new APUtils(WebportalParam.ap2IPaddress).getSsidStatus1("apwp12361", WebportalParam.ap2Model),"ssid is not pushed to AP");
                
    }

    @Step("Test Step 5: Check whether connected connect is shown in client list;")
    public void step5() {
        new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo);
        assertTrue(new WirelessQuickViewPage().checkClientConnect(WebportalParam.clientwlanmac), "Client cannot connected.");
        new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "netsh wlan disconnect");
    }

}
