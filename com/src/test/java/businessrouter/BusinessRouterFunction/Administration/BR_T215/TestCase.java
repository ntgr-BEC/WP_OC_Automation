package businessrouter.BusinessRouterFunction.Administration.BR_T215;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

import businessrouter.testbase.TestCaseBase;
import businessrouter.webpageoperation.BrAdvancedBackupSettingsPage;
import businessrouter.webpageoperation.BrAdvancedFirmwareUpdatePage;
import businessrouter.webpageoperation.BrAdvancedVlanPage;
import businessrouter.webpageoperation.BrBasicDeviceNameSetupPage;
import businessrouter.webpageoperation.BrBasicSetPasswordPage;
import businessrouter.webpageoperation.BrLoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import util.MyCommonAPIs;
import util.XMLManagerForTest;

public class TestCase extends TestCaseBase implements Config {
    String tmpStr;

    public String sTestStr = "BR_T215";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.Administration") // 必须要加，对应目录名
    @Story("BR_T215") // 对应testrunkey
    @Description("014-Restore configuration that saved on differernt image") // 对应用例名字
    @TmsLink("1455218") // 对应用例详情页的URL最后几位数字

    @Test(alwaysRun = true, groups = "p2") // 标记测试用例
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (!micResult) {
            XMLManagerForTest xmlManager = new XMLManagerForTest();
            String vername = xmlManager.getValueFromPortXml("//Config/afterimage").substring(6, 16);
            String filename = xmlManager.getValueFromPortXml("//Config/afterimage");
            String filepath = "C:\\tftpd32\\" + filename;
            System.out.println(vername);

            BrLoginPage BrLoginPage = new BrLoginPage();
            BrLoginPage.LoginRefresh();
            MyCommonAPIs.sleepi(20);
            BrAdvancedFirmwareUpdatePage BrAdvancedFirmwareUpdatePage = new BrAdvancedFirmwareUpdatePage();
            BrAdvancedFirmwareUpdatePage.OpenFirmwareUpdatePage();
            MyCommonAPIs.sleepi(10);
            BrAdvancedFirmwareUpdatePage.CheckFirmwareVersion(vername, filepath);
            BrLoginPage.defaultLogin();
        }
        System.out.println("start to do tearDown");
    }

    @Step("Test Step 1: Open Device")
    public void step1() {
        BrLoginPage BrLoginPage = new BrLoginPage();
        BrLoginPage.defaultLogin();
        MyCommonAPIs.sleepi(15);
    }

    @Step("Test Step 2: Router run with old image,save the configuration file")
    public void step2() {
        BrLoginPage BrLoginPage = new BrLoginPage();
       // BrLoginPage.LoginRefresh();

        BrAdvancedFirmwareUpdatePage BrAdvancedFirmwareUpdatePage = new BrAdvancedFirmwareUpdatePage();
        BrAdvancedFirmwareUpdatePage.OpenFirmwareUpdatePage();
        MyCommonAPIs.sleepi(10);
        XMLManagerForTest xmlManager = new XMLManagerForTest();
        String filename = xmlManager.getValueFromPortXml("//Config/beforeimage");
        String filepath = "C:\\tftpd32\\" + filename;
        if (BrAdvancedFirmwareUpdatePage.FirmwareUpdate(filepath)) {
            micResult = true;
            assertTrue(micResult, "Pass:Upgrade image to old success.");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Upgrade image to old fail.");
        }

        MyCommonAPIs.sleepi(180);
        BrLoginPage.defaultLogin();
        MyCommonAPIs.sleepi(20);
        BrAdvancedVlanPage BrAdvancedVlanPage = new BrAdvancedVlanPage();
        BrAdvancedVlanPage.OpenAdvancedVlanPage();
        BrAdvancedVlanPage.OpenAdvancedVlanPage();
        MyCommonAPIs.sleepi(3);
        BrAdvancedVlanPage.AddVlan(VLAN);
        brdevname.OpenDeviceNameSetupPage();
        MyCommonAPIs.sleepi(5);
        brdevname.Editdevicename(ChangeName);

        BrAdvancedBackupSettingsPage BrAdvancedBackupSettingsPage = new BrAdvancedBackupSettingsPage();
        BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
        MyCommonAPIs.sleepi(10);
        if (BrAdvancedBackupSettingsPage.SaveSetting(ConfigFilepath)) {
            micResult = true;
            assertTrue(micResult, "Pass:Backup configuration success.");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Backup configuration fail.");
        }
    }

    @Step("Test Step 3: Upgrade image to new,and default Router")
    public void step3() {
        BrLoginPage BrLoginPage = new BrLoginPage();
        //BrLoginPage.LoginRefresh();
        BrLoginPage.defaultLogin();

        BrAdvancedFirmwareUpdatePage BrAdvancedFirmwareUpdatePage = new BrAdvancedFirmwareUpdatePage();
        BrAdvancedFirmwareUpdatePage.OpenFirmwareUpdatePage();
        MyCommonAPIs.sleepi(15);
        XMLManagerForTest xmlManager = new XMLManagerForTest();
        String filename = xmlManager.getValueFromPortXml("//Config/afterimage");
        String filepath = "C:\\tftpd32\\" + filename;
        if (BrAdvancedFirmwareUpdatePage.FirmwareUpdate(filepath)) {
            MyCommonAPIs.sleepi(180);
            BrLoginPage.defaultLogin();

            BrAdvancedBackupSettingsPage BrAdvancedBackupSettingsPage = new BrAdvancedBackupSettingsPage();
            BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
            MyCommonAPIs.sleepi(10);
            BrAdvancedBackupSettingsPage.DefaultDevice();

            if (BrLoginPage.CheckNewPasswordLoginSuccess("password")) {
                BrBasicSetPasswordPage BrBasicSetPasswordPage = new BrBasicSetPasswordPage();
                MyCommonAPIs.sleepi(10);
                BrBasicSetPasswordPage.ChangePassword("password", "Test@123");
                BrBasicSetPasswordPage.CheckChangePasswordError("change");
                micResult = true;
                assertTrue(micResult, "Pass:Upgrade image to new and default Router success.");
            } else {
                micResult = false;
                assertTrue(micResult, "Failed:Upgrade image to new and default Router fail.");
            }
            Selenide.close();
            BrLoginPage.defaultLogin();
            MyCommonAPIs.sleepi(15);

        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Upgrade image to new fail.");
        }
    }

    @Step("Test Step 4: Restore configuraion that saved via step2")
    public void step4() {
        BrAdvancedVlanPage BrAdvancedVlanPage = new BrAdvancedVlanPage();
        BrAdvancedBackupSettingsPage BrAdvancedBackupSettingsPage = new BrAdvancedBackupSettingsPage();
        BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
        MyCommonAPIs.sleepi(10);
        String filepath = "C:\\tftpd32\\NETGEAR_BR500_T215.cfg";
        BrAdvancedBackupSettingsPage.RestoreSetting(filepath);
        //if (BrAdvancedBackupSettingsPage.CheckRestoreSuccess()) {

            MyCommonAPIs.sleepi(180);
            Selenide.close();
            BrLoginPage BrLoginPage = new BrLoginPage();
            BrLoginPage.defaultLogin();

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
                assertTrue(micResult, "Pass:Restore default configuration success");
            } else {
                micResult = false;
                assertTrue(micResult, "Failed:Restore default configuration fail");
            }
        //} else {
       //     micResult = false;
        //    assertTrue(micResult, "Failed:Restore configuration fail.");
       // }
    }

    @Step("Test Step 5: Return CaseResult")
    public void step5() {
        CaseResult = true;
    }
}
