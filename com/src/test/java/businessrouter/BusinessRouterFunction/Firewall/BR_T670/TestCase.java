package businessrouter.BusinessRouterFunction.Firewall.BR_T670;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import businessrouter.testbase.TestCaseBase;
import businessrouter.webpageoperation.BrFirewallTrafficRulesPage;
import businessrouter.webpageoperation.BrLoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;

public class TestCase extends TestCaseBase implements Config {
    String tclname = getClass().getName();
    String tmpStr;

    public String sTestStr = "BR_T670";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;

    @Feature("Business Router.Firewall") // 必须要加，对应目录名
    @Story("BR_T670") // 对应testrunkey
    @Description("116-TrafficRules-Create max lan to wan rules") // 对应用例名字
    @TmsLink("1461346") // 对应用例详情页的URL最后几位数字

    @Test(alwaysRun = true, groups = "p2") // 标记测试用例
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    @Step("Test Step 1: Open Device")
    public void step1() {
       
        BrLoginPage BrLoginPage = new BrLoginPage();
        BrLoginPage.defaultLogin();
        handle.sleepi(15);   
    }

    @Step("Test Step 2: Add max Firewall rules")
    public void step2() {
        BrFirewallTrafficRulesPage BrFirewallTrafficRulesPage = new BrFirewallTrafficRulesPage();
        BrFirewallTrafficRulesPage.OpenFirewallTrafficRulesPage();
        for (int i = 0; i < 128; i++) {
            // BrFirewallTrafficRulesPage.OpenFirewallTrafficRulesPage();
            String tt = String.valueOf(i);
            String VLANname = "test" + tt;
            //int  DportNum = 1000 + i;
            //String Dport = String.valueOf(DportNum);
            String NewSource = Brtmspage.GetNewIPAddress( Firewallrule.get("Source address"));
            Firewallrule.replace("Rule Name", VLANname);
            Firewallrule.replace("Source address", NewSource);
            BrFirewallTrafficRulesPage.AddLANWANTrafficRule(Firewallrule);
            util.MyCommonAPIs.sleep(5000);
            //BrFirewallTrafficRulesPage.OpenFirewallTrafficRulesPage();
        }

    }

    @Step("Test Step 3: Delete max Firewall rules")
    public void step3() {
        BrLoginPage BrLoginPage = new BrLoginPage();
        BrFirewallTrafficRulesPage BrFirewallTrafficRulesPage = new BrFirewallTrafficRulesPage();
        //BrFirewallTrafficRulesPage.OpenFirewallTrafficRulesPage();
        for (int i = 0; i < 11; i++) {
            System.out.println("delete rules");
            BrFirewallTrafficRulesPage.DeleteallTrafficRules();
        }
        
        BrLoginPage.BrLogout();
        CaseResult = true;

    }

}
