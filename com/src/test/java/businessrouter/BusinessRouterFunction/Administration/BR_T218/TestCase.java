package businessrouter.BusinessRouterFunction.Administration.BR_T218;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import businessrouter.testbase.TestCaseBase;
import businessrouter.webpageoperation.BrAdvancedFirmwareUpdatePage;
import businessrouter.webpageoperation.BrLoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import util.MyCommonAPIs;

public class TestCase extends TestCaseBase {
    String tmpStr;

    public String sTestStr = "BR_T218";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.Administration") // 必须要加，对应目录名
    @Story("BR_T218") // 对应testrunkey
    @Description("017-Import wrong image to upgrade") // 对应用例名字
    @TmsLink("1455221") // 对应用例详情页的URL最后几位数字

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
        MyCommonAPIs.sleepi(10);
    }

    @Step("Test Step2: Import wrong image to upgrade")
    public void step2() {
        String filepath = "C:\\tftpd32\\BR500-wrong.img";
        File file = new File(filepath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //BrLoginPage BrLoginPage = new BrLoginPage();
        //BrLoginPage.LoginRefresh();

        BrAdvancedFirmwareUpdatePage BrAdvancedFirmwareUpdatePage = new BrAdvancedFirmwareUpdatePage();
        BrAdvancedFirmwareUpdatePage.OpenFirmwareUpdatePage();
        MyCommonAPIs.sleepi(10);
        if (!BrAdvancedFirmwareUpdatePage.FirmwareUpdate(filepath)) {
            micResult = true;
            assertTrue(micResult, "Pass:Import wrong image to upgrade fail.");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Import wrong image to upgrade success.");
        }
    }
    
    @Step("Test Step 3: Return CaseResult")
    public void step3() {
        CaseResult = true;
    }
}
