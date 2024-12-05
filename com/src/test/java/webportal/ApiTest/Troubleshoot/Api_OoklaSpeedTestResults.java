package webportal.ApiTest.Troubleshoot;
import static org.hamcrest.CoreMatchers.equalTo;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import testbase.TestCaseBaseApi;
import util.MyCommonAPIs;
import webportal.ApiTest.Location.PositiveTestcases.Api_AddNetwork;
//import webportal.weboperation.WirelessQuickViewPage;
import webportal.param.WebportalParam;
import webportal.weboperation.ApiRequest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Api_OoklaSpeedTestResults extends TestCaseBaseApi{

    Map<String, String> endPointUrl = new HashMap<String, String>();
    Map<String, String> pathParams = new HashMap<String, String>();
    Map<String, String> headers = new HashMap<String, String>();
    String networkId;
    
    @Feature("Api_OoklaSpeedTestResults") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("This test will Ookal speed tets lookup results from the particular account") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
    }
    
    @Step("Send get request to {url}")
    public void step1()
    { 
        endPointUrl = new ApiRequest().ENDPOINT_URL;  
        Response add = new Api_OoklaSpeedTest().step1();    //perform ookla spedd test test and then retrive the result
        headers.put("token",WebportalParam.token);
        headers.put("apikey",WebportalParam.apikey);    
        headers.put("accountId",WebportalParam.accountId);
      
        pathParams.put("networkId",WebportalParam.networkId);
        pathParams.put("serialNo",WebportalParam.ap1deveiceName);
        MyCommonAPIs.sleepi(20);
        
         //TO PERFORM ANY REQUEST 
        Response getResponse = ApiRequest.sendGetRequest(endPointUrl.get("SpeedTest_results"), headers, pathParams, null);
        getResponse.then().body("response.status", equalTo(true))
        .body("details.upLinkSpeed", greaterThan("0"))
        .body("details.downLinkSpeed", greaterThan("0"))
        .body("details.serialNumber", equalTo(WebportalParam.ap1deveiceName));
        
    }
}
