package businessrouter.BusinessRouterFunction.Security_BlockSites.BR_T53;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

import businessrouter.BusinessRouterFunction.Administration.BR_T211.Config;
import businessrouter.testbase.TestCaseBase;
import businessrouter.webpageoperation.BrAdvancedSecurityBlockSitesPage;
import businessrouter.webpageoperation.BrLoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import util.MyCommonAPIs;

public class TestCase extends TestCaseBase implements Config {
    String tmpStr;

    public String sTestStr = "BR_T53";
    public String sCurrentValue;
    public String sExpectedtValue;

    @Feature("Business Router.Security_BlockSites") // 必须要加，对应目录名
    @Story("BR_T53") // 对应testrunkey
    @Description("012-Block Sites - Normal keywords") // 对应用例名字
    @TmsLink("1455056") // 对应用例详情页的URL最后几位数字

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

    @Step("Test Step 2: Add a keyword in the box and is up to 60 characters.")
    public void step2() {
        String[] urls = {
                "baidu", "renren", "qq"
        };
        char a = 33;
        String keyword = "";
        while (a < 80) {
            keyword += a;
            a++;
        }
        keyword += "BAIDUQQRENREN";
        System.out.println(keyword);
        BrAdvancedSecurityBlockSitesPage BrAdvancedSecurityBlockSitesPage = new BrAdvancedSecurityBlockSitesPage();
        BrAdvancedSecurityBlockSitesPage.OpenSecurityBlockSitesPage();
        MyCommonAPIs.sleepi(15);
        BrAdvancedSecurityBlockSitesPage.blocksiteselectalw(keyword);
        boolean result1 = BrAdvancedSecurityBlockSitesPage.checkdomainlist(keyword);
        if (result1) {
            boolean result2 = false;
            String[] titles = {
                    "百度一下，你就知道", "人人网，中国领先的实名制SNS社交网络。加入人人网，找到老同学，结识新朋友。", "腾讯首页"
            };
            for (int i = 0; i < urls.length; i++) {
                Selenide.open("http://www." + urls[i] + ".com");
                MyCommonAPIs.sleepi(5);
                if (Selenide.title().equals(titles[i]) && !Selenide.title().equals("")) {
                    result2 = true;
                } else {
                    result2 = false;
                    break;
                }
            }
            if (result2) {
                micResult = true;
                assertTrue(micResult, "Pass:It will be dropped");
            } else {
                micResult = false;
                assertTrue(micResult, "Failed:It will not be dropped");
            }
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:Add keyword fail");
        }
    }

    @Step("Test Step 3: Return CaseResult")
    public void step3() {
        CaseResult = true;
    }
}
