package businessrouter.BusinessRouterFunction.Administration.BR_T220;

import static org.testng.Assert.assertTrue;

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
import util.XMLManagerForTest;

public class TestCase extends TestCaseBase {
    String tmpStr;

    public String sTestStr = "BR_T220";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.Administration") // 必须要加，对应目录名
    @Story("BR_T220") // 对应testrunkey
    @Description("019-Cancel button can work on firmware update page") // 对应用例名字
    @TmsLink("1455223") // 对应用例详情页的URL最后几位数字

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
    }

    @Step("Tese Step2: Cancel button can work on firmware update page")
    public void step2() {
        XMLManagerForTest xmlManager = new XMLManagerForTest();
        String filename = xmlManager.getValueFromPortXml("//Config/afterimage");
        String filepath = "C:\\tftpd32\\" + filename;

        BrAdvancedFirmwareUpdatePage BrAdvancedFirmwareUpdatePage = new BrAdvancedFirmwareUpdatePage();
        BrAdvancedFirmwareUpdatePage.OpenFirmwareUpdatePage();
        MyCommonAPIs.sleepi(10);
        if (BrAdvancedFirmwareUpdatePage.CancelButtonCheck(filepath)) {
            micResult = true;
            assertTrue(micResult, "Pass:Cancel button can work.");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Cancel button can't work.");
        }
    }
    
    @Step("Test Step 3: Return CaseResult")
    public void step3() {
        CaseResult = true;
    }
}
