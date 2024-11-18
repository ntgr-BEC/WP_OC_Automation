package webportal.ApiTest.Devices.PositiveTestcases;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import testbase.TestCaseBaseApi;
import testbase.TestCaseBase;
import webportal.ApiTest.Location.PositiveTestcases.Api_AddNetwork;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.param.WebportalParam;
import webportal.weboperation.ApiRequest;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.DevicesSwitchCableTestPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

import static io.restassured.RestAssured.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.MyCommonAPIs;


public class Api_SwitchCableTest extends TestCaseBaseApi{

    String networkId;
    Map<String, String> headers = new HashMap<String, String>();
    Map<String, String> endPointUrl = new HashMap<String, String>();
    Map<String, String> pathParams = new HashMap<String, String>();
    TestCaseBase tcb = new TestCaseBase();
    
    @Feature("Api_SwitchCableTest") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Checks the cable test") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
        step2();
    }
    @BeforeMethod
    public void teardown()
    { 
        
        new TestCaseBase().startBrowser();
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();
        tcb.handle.gotoLoction("office2");
    }

    @Step("Perform cable test")
    public void step1()
    {
        DevicesDashPage devicesDashPage = new DevicesDashPage();
        devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
        
        DevicesSwitchCableTestPage cableTestPage = new DevicesSwitchCableTestPage();
                cableTestPage.logger.info("step3......");     
                cableTestPage.testCableTest(new String[]{"1","3"});
                MyCommonAPIs.sleepi(10);
                UserManage userManage = new UserManage();
                userManage.logout();
    }
    
    @Step("Send get request to {url}")
    public void step2()
    {
        endPointUrl = new ApiRequest().ENDPOINT_URL;

        headers.put("token",WebportalParam.token);
        headers.put("apikey",WebportalParam.apikey);
        headers.put("accountId",WebportalParam.accountId);     
        
        pathParams.put("serialNo",WebportalParam.sw1deveiceName);
         
        //TO PERFORM ANY REQUEST
        Response getResponse = ApiRequest.sendGetRequest(endPointUrl.get("Switch_CableTest"), headers, pathParams, null); 
        getResponse.then().body("response.status", equalTo(true));

        
                    
    }

}
