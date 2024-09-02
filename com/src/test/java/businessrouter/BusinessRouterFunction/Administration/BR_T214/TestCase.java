package businessrouter.BusinessRouterFunction.Administration.BR_T214;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

import businessrouter.testbase.TestCaseBase;
import businessrouter.webpageoperation.BrAdvancedBackupSettingsPage;
import businessrouter.webpageoperation.BrAdvancedVlanPage;
import businessrouter.webpageoperation.BrBasicDeviceNameSetupPage;
import businessrouter.webpageoperation.BrLoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;

public class TestCase extends TestCaseBase implements Config {
    String tmpStr;

    public String sTestStr = "BR_T214";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.Administration") // 必须要加，对应目录名
    @Story("BR_T214") // 对应testrunkey
    @Description("013-Restore default configuration file") // 对应用例名字
    @TmsLink("1455217") // 对应用例详情页的URL最后几位数字

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
        MyCommonAPIs.sleepi(5);
    }

    @Step("Test Step2: Default Router,backup it")
    public void step2() {
        BrAdvancedBackupSettingsPage BrAdvancedBackupSettingsPage = new BrAdvancedBackupSettingsPage();
        BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
        //MyCommonAPIs.sleepi(10);
       // BrAdvancedBackupSettingsPage.DefaultDevice();
        //Selenide.refresh();
        //BrLoginPage BrLoginPage = new BrLoginPage();
        //BrLoginPage.CheckNewPasswordLoginSuccess("password");

        //BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
        MyCommonAPIs.sleepi(5);
        if (BrAdvancedBackupSettingsPage.SaveSetting(DefaultFilepath)) {
            micResult = true;
            assertTrue(micResult, "Pass:Backup configuration success.");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Backup configuration Failed.");
        }
    }

    @Step("Test Step3: Config Rourer on every page,save the configuration file")
    public void step3() {

        BrAdvancedVlanPage BrAdvancedVlanPage = new BrAdvancedVlanPage();
        BrAdvancedVlanPage.OpenAdvancedVlanPage();
        MyCommonAPIs.sleepi(3);
        BrAdvancedVlanPage.AddVlan(VLAN);
        MyCommonAPIs.sleepi(3);
        brdevname.OpenDeviceNameSetupPage();
        MyCommonAPIs.sleepi(5);
        brdevname.Editdevicename(ChangeName);

        BrAdvancedBackupSettingsPage BrAdvancedBackupSettingsPage = new BrAdvancedBackupSettingsPage();
        BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
        MyCommonAPIs.sleepi(20);
        if (BrAdvancedBackupSettingsPage.SaveSetting(ChangeFilepath)) {
            micResult = true;
            assertTrue(micResult, "Pass:Backup configuration success.");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Backup configuration Failed.");
        }
    }

    @Step("Test Step4: Restore default configuration file,check configuration")
    public void step4() {
        BrAdvancedBackupSettingsPage BrAdvancedBackupSettingsPage = new BrAdvancedBackupSettingsPage();
        BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
        MyCommonAPIs.sleepi(10);
        BrAdvancedBackupSettingsPage.RestoreSetting(DefaultFilepath);
        //if (BrAdvancedBackupSettingsPage.CheckRestoreSuccess()) {
            MyCommonAPIs.sleepi(100);
            Selenide.close();
            //Selenide.refresh();
            System.out.println("dddddfeeeeeeeeeeeeer3333341324132666");
            BrLoginPage BrLoginPage = new BrLoginPage();
            BrLoginPage.defaultLogin();
            MyCommonAPIs.sleepi(20);
            boolean CompareResult = false;

            BrBasicDeviceNameSetupPage BrBasicDeviceNameSetupPage = new BrBasicDeviceNameSetupPage();
            BrBasicDeviceNameSetupPage.OpenDeviceNameSetupPage();
            MyCommonAPIs.sleepi(15);
            String DefaultDevicename = "BR500";
            if (WebportalParam.DUTType.contentEquals("BR500")) {
                DefaultDevicename = "BR500";
            }else if(WebportalParam.DUTType.contentEquals("BR100")) {
                DefaultDevicename = "BR100";
            }
            CompareResult = BrBasicDeviceNameSetupPage.ComparedefaultDevicename(DefaultDevicename);

            if (CompareResult) {
                micResult = true;
                assertTrue(micResult, "Pass:Restore default configuration success");
            } else {
                micResult = false;
                assertTrue(micResult, "Failed:Restore default configuration fail");
            }
        //} else {
        //    micResult = false;
        //    assertTrue(micResult, "Failed:Restore configuration Failed.");
        //}
    }

    @Step("Test Step5: Restore configuration file that saved use step2,check configuration")
    public void step5() {
        BrAdvancedVlanPage BrAdvancedVlanPage = new BrAdvancedVlanPage();
        BrAdvancedBackupSettingsPage BrAdvancedBackupSettingsPage = new BrAdvancedBackupSettingsPage();
        BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
        MyCommonAPIs.sleepi(10);
        BrAdvancedBackupSettingsPage.RestoreSetting(ChangeFilepath);
        MyCommonAPIs.sleepi(100);
        //if (BrAdvancedBackupSettingsPage.CheckRestoreSuccess()) {
            System.out.println("dddddfeeeeeeeeeeeeer333334132413245");
            Selenide.close();
            BrLoginPage BrLoginPage = new BrLoginPage();
            BrLoginPage.defaultLogin();
            MyCommonAPIs.sleepi(20);
            boolean CompareResult = false;

            BrBasicDeviceNameSetupPage BrBasicDeviceNameSetupPage = new BrBasicDeviceNameSetupPage();
            BrBasicDeviceNameSetupPage.OpenDeviceNameSetupPage();
            MyCommonAPIs.sleepi(15);
            CompareResult = BrBasicDeviceNameSetupPage.ComparedefaultDevicename(ChangeName);

            BrAdvancedVlanPage.OpenAdvancedVlanPage();
            MyCommonAPIs.sleepi(5);
            boolean CompareResult2 = BrAdvancedVlanPage.CheckVlanConfig(VLAN);

            if (CompareResult && CompareResult2) {
                micResult = true;
                assertTrue(micResult, "Pass:Restore step2 configuration success");
            } else {
                micResult = false;
                assertTrue(micResult, "Failed:Restore step2 configuration fail");
            }
       // } else {
       //     micResult = false;
       //     assertTrue(micResult, "Failed:Restore configuration fail.");
       // }

        if (micResult) {
            brdevname.OpenDeviceNameSetupPage();
            MyCommonAPIs.sleepi(5);
            if (WebportalParam.DUTType.contentEquals("BR500")) {
                brdevname.Editdevicename("BR500");
            }else if(WebportalParam.DUTType.contentEquals("BR100")) {
                brdevname.Editdevicename("BR100");
            }
            
            BrAdvancedVlanPage.OpenAdvancedVlanPage();
            BrAdvancedVlanPage.OpenAdvancedVlanPage();
            MyCommonAPIs.sleepi(3);
            BrAdvancedVlanPage.DeleteVLAN(VLAN.get("VLAN ID"));
            MyCommonAPIs.sleepi(3);
        }
    }

    @Step("Test Step 6: Return CaseResult")
    public void step6() {
        CaseResult = true;
    }
}
