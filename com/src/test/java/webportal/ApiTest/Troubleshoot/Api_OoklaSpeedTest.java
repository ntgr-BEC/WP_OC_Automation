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


public class Api_OoklaSpeedTest extends TestCaseBaseApi{

    Map<String, String> endPointUrl = new HashMap<String, String>();
    Map<String, String> pathParams = new HashMap<String, String>();
    Map<String, String> headers = new HashMap<String, String>();
    String networkId;
    
    @Feature("Api_OoklaSpeedTest") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("This test will troubleshoot the device with ookla speed test from the particular account") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
    }
    
    @Step("Send get request to {url}")
    public Response step1()
    { 
        endPointUrl = new ApiRequest().ENDPOINT_URL;  
      
        headers.put("token",WebportalParam.token);
        headers.put("apikey",WebportalParam.apikey);    
        headers.put("accountId",WebportalParam.accountId);
      
        pathParams.put("networkId",WebportalParam.networkId);
      
        
        String requestBody = "{\"command\":[{\"commandParam\":\"netgear.com\",\"commandType\":\"2\",\"initTtl\":\"string\",\"interval\":\"string\",\"maxTtl\":\"string\",\"packetSize\":\"string\",\"packetsPerHop\":\"string\",\"pingCount\":\"string\",\"pingInterval\":\"string\",\"pingTimeout\":\"string\",\"port\":\"string\",\"size\":\"string\"}],\"serialNo\":[\""+WebportalParam.ap1serialNo+"\"]}";
      

        Response getResponse = ApiRequest.sendPostRequest(endPointUrl.get("SpeedTest"), requestBody, headers, pathParams, null); 
        getResponse.then().body("response.status", equalTo(true))
        .body("info[0].message", equalTo("Your configuration has been applied. It may take some time to reflect"))
        .body("info[0].serialNo", equalTo(WebportalParam.ap1deveiceName));
     
       return getResponse;
        
    }
}

   