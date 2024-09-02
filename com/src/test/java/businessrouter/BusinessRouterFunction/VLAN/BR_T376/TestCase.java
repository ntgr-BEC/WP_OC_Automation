package businessrouter.BusinessRouterFunction.VLAN.BR_T376;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import businessrouter.testbase.TestCaseBase;
import businessrouter.webpageoperation.BrAdvancedVlanPage;
import businessrouter.webpageoperation.BrLoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import util.MyCommonAPIs;

public class TestCase extends TestCaseBase implements Config {
    String tclname = getClass().getName();
    String tmpStr;

    public String sTestStr    = "BR_T376";
    public String sCurrentValue;
    public String sExpectedtValue;
    BrLoginPage   BrLoginPage = new BrLoginPage();
    // public String sOldSW = WebportalParam.sw1IPaddress;

    @Feature("Business Router.VLAN") // 必须要加，对应目录名
    @Story("BR_T376") // 对应testrunkey
    @Description("010-Delete many VLANs from a port") // 对应用例名字
    @TmsLink("1455395") // 对应用例详情页的URL最后几位数字

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
        MyCommonAPIs.sleepi(15);
    }

    @Step("Test Step 2: Add serveral vlan and add port 1,2 to these vlan and Remove port2 from each VLAN")
    public void step2() {
        BrAdvancedVlanPage BrAdvancedVlanPage = new BrAdvancedVlanPage();
        BrAdvancedVlanPage.OpenAdvancedVlanPage();
        MyCommonAPIs.sleepi(5);
        boolean result1 = false;
        for (int i = 3; i < 11; i++) {
            VLAN.put("VLAN ID", String.valueOf(i));
            VLAN.put("Name", "vlan" + String.valueOf(i));
            BrAdvancedVlanPage.AddVlan(VLAN);
            MyCommonAPIs.sleepi(3);
        }
        BrAdvancedVlanPage.OpenAdvancedVlanPage();
        MyCommonAPIs.sleepi(5);
        for (int i = 3; i < 8; i++) {
            VLAN.put("VLAN ID", String.valueOf(i));
            VLAN.put("Name", "vlan" + String.valueOf(i));
            result1 = BrAdvancedVlanPage.CheckVlanConfig(VLAN) && BrAdvancedVlanPage.CheckVlanMember(VLAN);
            if (!result1) {
                break;
            }
        }
        if (result1) {
            BrAdvancedVlanPage.OpenAdvancedVlanPage();
            MyCommonAPIs.sleepi(5);
            boolean result2 = false;
            for (int i = 3; i < 11; i++) {
                VLAN.put("VLAN ID", String.valueOf(i));
                VLAN.put("Name", "vlan" + String.valueOf(i));
                VLAN.put("Ports", "2:TAG");
                BrAdvancedVlanPage.DeleteVlanMember(VLAN);
                MyCommonAPIs.sleepi(5);
            }
            BrAdvancedVlanPage.OpenAdvancedVlanPage();
            MyCommonAPIs.sleepi(5);
            for (int i = 3; i < 8; i++) {
                VLAN.put("VLAN ID", String.valueOf(i));
                VLAN.put("Name", "vlan" + String.valueOf(i));
                VLAN.put("Ports", "1:TAG");
                result2 = BrAdvancedVlanPage.CheckVlanConfig(VLAN) && BrAdvancedVlanPage.CheckVlanMember(VLAN);
                if (!result2) {
                    break;
                }
            }
            if (result2) {
                micResult = true;
                assertTrue(micResult, "Pass:VLAN config is correct.");
            } else {
                micResult = false;
                assertTrue(micResult, "Failed:VLAN config is incorrect.");
            }

            if (micResult) {
                BrAdvancedVlanPage.OpenAdvancedVlanPage();
                MyCommonAPIs.sleepi(5);
                BrAdvancedVlanPage.DeleteallVlans();
                MyCommonAPIs.sleepi(15);
            }
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:VLAN config is incorrect.");
        }
    }

    @Step("Test Step 3: Return CaseResult")
    public void step3() {
        CaseResult = true;
    }

}
