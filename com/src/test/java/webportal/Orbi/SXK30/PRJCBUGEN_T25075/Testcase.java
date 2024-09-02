package webportal.Orbi.SXK30.PRJCBUGEN_T25075;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.weboperation.WebportalLoginPage;
import webportal.param.WebportalParam;
import webportal.weboperation.TopologyPage;



/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("SXK30") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25075") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Topology with sxk 30 in abstract view") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25075") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown TestcaseStub");
    }

    // Each step covers the operation in one page
    @Step("Test Step 1: Login to WP / Go to Location 1")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
    }

    @Step("Test Step 2: Go to topology page / Select abstract view and check elements")
    public void step2() {
        boolean router = false, client = false, isp = false;
        TopologyPage page = new TopologyPage();
        page.gotoPage();
        page.arrow.click();
        MyCommonAPIs.waitReady();
        page.icontopology.click();
        MyCommonAPIs.waitReady();
        page.abstractview.click();
        MyCommonAPIs.waitReady();
//        page.arrow.click();
        MyCommonAPIs.waitReady();
        
        if(page.topology.exists()) {
            String nodetext = page.topology.getText();
            
                if(nodetext.contains("ISP")) {
                    isp = true;
                }
                if(nodetext.contains(WebportalParam.ob2deveiceName)) {
                    router = true;
                }
                if(nodetext.contains(WebportalParam.client1name)) {
                    client = true;
                }
            
        }
        assertTrue(isp,"checkpoint 1 : ISP exists in the topology");
        assertTrue(router,"checkpoint 2 : router exists in the topology");
        assertTrue(client,"checkpoint 3 : client exists in the topology");
    }

    
}