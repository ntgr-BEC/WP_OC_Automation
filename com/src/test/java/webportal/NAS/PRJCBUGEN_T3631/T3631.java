package webportal.NAS.PRJCBUGEN_T3631;

import static com.codeborne.selenide.Selenide.sleep;

import org.testng.annotations.Test;

import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.weboperation.AccountPageNas;
import webportal.weboperation.WebportalLoginPage;

public class T3631 extends TestCaseBase implements webportal.NAS.PRJCBUGEN_T3631.Config {

    // public void test(){
    // test1();
    // test2();
    // test3();
    // }
    @Test(priority = 1)
    public void test1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();
        sleep(60 * 1000);
        AccountPageNas accountPage = new AccountPageNas();
        accountPage.addNetwork(LOCATION_INFO);
        MyCommonAPIs.waitReady();
    }
    // @Test(priority=2)
    // public void test2(){
    // //WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
    // //webportalLoginPage.defaultLogin();
    // //sleep(60*1000);
    // MyCommonAPIs.waitReady();
    // AccountPageNas accountPage = new AccountPageNas();
    // accountPage.enterEditNetworkPage2();
    //
    // }
    // @Test(priority=3)
    // public void test3(){
    //// WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
    //// webportalLoginPage.defaultLogin();
    //// sleep(30*1000);
    // MyCommonAPIs.waitReady();
    // AccountPageNas accountPage = new AccountPageNas();
    // accountPage.enterDeleteNetworkPage2();
    // //$("#test").shouldHave(condition)

}
