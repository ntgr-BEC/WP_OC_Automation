package businessrouter.BusinessRouterFunction.Administration.BR_T216;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

import businessrouter.testbase.TestCaseBase;
import businessrouter.webpageoperation.BrAdvancedFirmwareUpdatePage;
import businessrouter.webpageoperation.BrBasicDeviceNameSetupPage;
import businessrouter.webpageoperation.BrLoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import util.MyCommonAPIs;
import util.XMLManagerForTest;

public class TestCase extends TestCaseBase {
    String tmpStr;

    public String sTestStr = "BR_T216";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.Administration") // 必须要加，对应目录名
    @Story("BR_T216") // 对应testrunkey
    @Description("015-Upgrade image to new") // 对应用例名字
    @TmsLink("1455219") // 对应用例详情页的URL最后几位数字

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

    @Step("Test Step 2: Config Rourer,upgrade image to new")
    public void step2() {
        brdevname.OpenDeviceNameSetupPage();
        MyCommonAPIs.sleepi(5);
        brdevname.Editdevicename("testT216");

        MyCommonAPIs.sleepi(60);
        Selenide.close();
        BrLoginPage BrLoginPage = new BrLoginPage();
        //BrLoginPage.LoginRefresh();
        BrLoginPage.defaultLogin();
        MyCommonAPIs.sleepi(15);
        
        BrAdvancedFirmwareUpdatePage BrAdvancedFirmwareUpdatePage = new BrAdvancedFirmwareUpdatePage();
        BrAdvancedFirmwareUpdatePage.OpenFirmwareUpdatePage();
        MyCommonAPIs.sleepi(10);
        XMLManagerForTest xmlManager = new XMLManagerForTest();
        String filename = xmlManager.getValueFromPortXml("//Config/afterimage");
        String filepath = "C:\\tftpd32\\" + filename;
        if (BrAdvancedFirmwareUpdatePage.FirmwareUpdate(filepath)) {
            micResult = true;
            assertTrue(micResult, "Pass:Upgrade image to new success.");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Upgrade image to new fail.");
        }
    }

    @Step("Test Step 3: Check the configuration")
    public void step3() {
        BrLoginPage BrLoginPage = new BrLoginPage();
        MyCommonAPIs.sleepi(180);
        BrLoginPage.defaultLogin();

        boolean CompareResult = false;

        BrBasicDeviceNameSetupPage BrBasicDeviceNameSetupPage = new BrBasicDeviceNameSetupPage();
        BrBasicDeviceNameSetupPage.OpenDeviceNameSetupPage();
        MyCommonAPIs.sleepi(15);
        CompareResult = BrBasicDeviceNameSetupPage.ComparedefaultDevicename("testT216");

        if (CompareResult == true) {
            micResult = true;
            assertTrue(micResult, "Pass:Configuration file do not lose");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Configuration file lose");
        }
    }
    
    @Step("Test Step 4: Return CaseResult")
    public void step4() {
        CaseResult = true;
    }
}
