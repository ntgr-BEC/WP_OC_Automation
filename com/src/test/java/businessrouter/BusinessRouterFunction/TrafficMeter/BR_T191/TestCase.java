package businessrouter.BusinessRouterFunction.TrafficMeter.BR_T191;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

import businessrouter.BusinessRouterFunction.Administration.BR_T211.Config;
import businessrouter.testbase.TestCaseBase;
import businessrouter.webpageoperation.BrAdvancedTrafficMeterPage;
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

    public String sTestStr = "BR_T191";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.TrafficMeter") // 必须要加，对应目录名
    @Story("BR_T191") // 对应testrunkey
    @Description("008-Traffic Meter - Verify monthly limit warning message behavior") // 对应用例名字
    @TmsLink("1455194") // 对应用例详情页的URL最后几位数字

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

    @Step("Test Step 2: Config traffic meter,check warning message.")
    public void step2() {
        BrAdvancedTrafficMeterPage BrAdvancedTrafficMeterPage = new BrAdvancedTrafficMeterPage();
        BrAdvancedTrafficMeterPage.OpenTrafficMeterPage();
        MyCommonAPIs.sleepi(5);
        BrAdvancedTrafficMeterPage.EnableTrafficMeter();
        BrAdvancedTrafficMeterPage.ClickTrafficRestartButton();
        BrAdvancedTrafficMeterPage.ConfigTrafficVolumeControl("Both directions", "30");
        BrAdvancedTrafficMeterPage.ConfigWarningMessageVolume("10");
        MyCommonAPIs.sleepi(10);
        BrAdvancedTrafficMeterPage.downloadFile("/Projects/BR500/image/18.41.1.3/", "BR500-V18.41.1.3.img", "C:/");
        MyCommonAPIs.sleepi(40);
        Selenide.open("http://192.168.1.1/index.htm#/trafficMeter?_k=mejw42", "", "admin", WebportalParam.loginPassword);
        MyCommonAPIs.sleepi(10);
        if (BrAdvancedTrafficMeterPage.CheckWarningPage()) {
            brlogin.defaultLogin();
            BrAdvancedTrafficMeterPage.OpenTrafficMeterPage();
            MyCommonAPIs.sleepi(5);
            BrAdvancedTrafficMeterPage.ClickTrafficRestartButton();
            BrAdvancedTrafficMeterPage.ConfigTrafficVolumeControl("No limit", "0");
            BrAdvancedTrafficMeterPage.ConfigWarningMessageVolume("0");
            MyCommonAPIs.sleepi(10);
            BrAdvancedTrafficMeterPage.downloadFile("/Projects/BR500/image/18.41.1.3/", "BR500-V18.41.1.3.img", "C:/");
            MyCommonAPIs.sleepi(40);
            Selenide.open("http://192.168.1.1/index.htm#/trafficMeter?_k=mejw42", "", "admin", WebportalParam.loginPassword);
            MyCommonAPIs.sleepi(10);
            if (!BrAdvancedTrafficMeterPage.CheckWarningPage()) {
                micResult = true;
                assertTrue(micResult, "Pass:Warning message not pop up.");
            } else {
                micResult = false;
                assertTrue(micResult, "Failed:Warning message pop up.");
            }
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Warning message not pop up.");
        }
    }

    @Step("Test Step 3: Return CaseResult")
    public void step3() {
        CaseResult = true;
    }

}
