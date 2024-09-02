package businessrouter.BusinessRouterFunction.VLAN.BR_T516;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import businessrouter.testbase.TestCaseBase;
import businessrouter.webpageoperation.BrAdvancedBackupSettingsPage;
import businessrouter.webpageoperation.BrAdvancedVlanPage;
import businessrouter.webpageoperation.BrLoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;

public class TestCase extends TestCaseBase implements Config {
    String tclname = getClass().getName();
    String tmpStr;

    public String sTestStr    = "BR_T516";
    public String sCurrentValue;
    public String sExpectedtValue;
    BrLoginPage   BrLoginPage = new BrLoginPage();
    // public String sOldSW = WebportalParam.sw1IPaddress;

    @Feature("Business Router.VLAN") // 必须要加，对应目录名
    @Story("BR_T516") // 对应testrunkey
    @Description("029-Create max lan and vlan") // 对应用例名字
    @TmsLink("1455412") // 对应用例详情页的URL最后几位数字

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

    @Step("Test Step 2: Add new VLANs")
    public void step2() {
        BrAdvancedVlanPage BrAdvancedVlanPage = new BrAdvancedVlanPage();
        BrAdvancedVlanPage.OpenAdvancedVlanPage();
        MyCommonAPIs.sleepi(5);
        for (int i = 0; i < 20; i++) {
            String tt = String.valueOf(i);
            String VLANname = "test" + tt;
            VLAN.replace("Name", VLANname);
            int VLANNum = i + 3;
            String SVLANNum = String.valueOf(VLANNum);

            VLAN.replace("VLAN ID", SVLANNum);

            BrAdvancedVlanPage.AddVlan(VLAN);
            util.MyCommonAPIs.sleep(20000);
        }

    }

    @Step("Test Step 3: Delete all Vlans ")
    public void step3() {
        BrAdvancedVlanPage BrAdvancedVlanPage = new BrAdvancedVlanPage();
        BrAdvancedVlanPage.OpenAdvancedVlanPage();
        // BrAdvancedVlanPage.DeleteVLAN("10");
        for (int i = 0; i < 2; i++) {
            System.out.println("delete rules");
            BrAdvancedVlanPage.DeleteallVlans();
            util.MyCommonAPIs.sleep(20000);
        }
        BrAdvancedBackupSettingsPage BrAdvancedBackupSettingsPage = new BrAdvancedBackupSettingsPage();
        BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
        MyCommonAPIs.sleepi(10);
        if (WebportalParam.DUTType.contentEquals("BR500")) {
            BrAdvancedBackupSettingsPage.RestoreSetting("C:\\tftpd32\\NETGEAR_BR500_default.cfg");
        } else if(WebportalParam.DUTType.contentEquals("BR100")) {
            BrAdvancedBackupSettingsPage.RestoreSetting("C:\\tftpd32\\NETGEAR_BR100_default.cfg");
            
        }
        MyCommonAPIs.sleepi(180);
        //BrLoginPage.BrLogout();
        CaseResult = true;

    }

}
