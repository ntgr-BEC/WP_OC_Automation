package webportal.CFD.Links.PRJCBUGEN_T37786;

import testbase.TestCaseBase;

import java.util.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.APUtils;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.*;

import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertTrue;

/*author
 RAVISHANKAR*/
public class Testcase extends TestCaseBase{
    String link;
    public SelenideElement        netgear               = $x("//*[@id=\"desktop-head\"]/div[1]/div/div[1]/a[2]");
    public SelenideElement        supportlink            = $x("//*[@id=\"smartadmin-root\"]/div/div[3]/div/div/div[1]/ul/li[2]/a");

    
    
    @Feature("LINKS")
    @Story("PRJCBUGEN_T37786")
    @Description("Test to verify when the user clicks on the Support link on organization page , then open the new valid page.")
    @TmsLink("PRJCBUGEN_T37786")
    
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        Selenide.switchTo().window(0); 
    }
    
    @Step("Test Step 1: Login to Insight Webportal")
    public void step1()
    {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.loginName, WebportalParam.loginPassword);
    }
    @Step("Test Step 2: Click on about and check for existance")
    public void step2()
    {
        link=supportlink.getAttribute("href");
        System.out.println(link);
        if(supportlink.exists())
        {
            supportlink.click();
            MyCommonAPIs.sleepi(10);
            Selenide.switchTo().window(1);
        
        }
        else
        {
            assertTrue(false,"supportlink is not visible");
        }
    }
    
    @Step("Test Step3: Verify Url check and supportlink page")
    public void step3()
    {   boolean result=false;
        String currentUrl=new MyCommonAPIs().getCurrentUrl();
        System.out.print(currentUrl);
        
        if(currentUrl.equalsIgnoreCase(link) && netgear.exists())
        { 
                result=true;
        }
        assertTrue(result,"check link and supportlink page");
    }
        
    }



