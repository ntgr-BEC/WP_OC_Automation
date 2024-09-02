package webportal.MacAcl512.PRJCBUGEN_T28460;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import webportal.param.WebportalParam;
import webportal.webelements.WirelessQuickViewElement;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

/**
 *
 * @author Tejeshwini K V
 *
 */
public class Testcase extends TestCaseBase {

    
    List<String> Orglist=new ArrayList<String>(); 
    
    @Feature("MacAcl512") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN-T28460") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Add 512  mac  acl  to deny in  mac  acl") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T28460") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
//        new WirelessQuickViewPage().deleteSsidYes("apwp14476");
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        new DevicesDashPage().checkDeviceInAdminAccount();
    }

    @Step("Test Step 2: Enable ACL Policy popup is shown because  no device is added;")
    public void step2() throws InterruptedException {
        Map<String, String> ssidInfo = new HashMap<String, String>();
        ssidInfo.put("SSID", "apwp28459");
        ssidInfo.put("Security", "WPA2 Personal");
        ssidInfo.put("Password", "12345678");
        ssidInfo.put("type","Local ACL");
        ssidInfo.put("policy","deny");
        new WirelessQuickViewPage().addSsid(ssidInfo);
        new WirelessQuickViewPage().editSsid(ssidInfo.get("SSID"));
        int MaxMacAclNum=512;
        Orglist = new WirelessQuickViewPage().AddMultipleMacAcl(ssidInfo.get("SSID"), MaxMacAclNum,ssidInfo.get("policy"));
        System.out.println(Orglist);
         new WirelessQuickViewElement().toggleBtn.click();
     }

     @Step("Test Step 3: check whether it is applied from ssh ")
     public void step3() {
         MyCommonAPIs.sleepi(70);
         String msg = new APUtils(WebportalParam.ap1IPaddress).MacAcl( WebportalParam.ap1Model);
         for(int i=0;i<Orglist.size(); i++) {
             System.out.println(Orglist.get(i));
         assertTrue(msg.contains(Orglist.get(i)),"default  value for 2.4 Ghz is not right");
         }
     }

 }
