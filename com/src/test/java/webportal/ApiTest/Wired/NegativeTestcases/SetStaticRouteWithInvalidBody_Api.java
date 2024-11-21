package webportal.ApiTest.Wired.NegativeTestcases;
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
import webportal.ApiTest.Devices.PositiveTestcases.Api_GetDevices;
import webportal.ApiTest.Location.PositiveTestcases.Api_AddNetwork;
//import webportal.weboperation.WirelessQuickViewPage;
import webportal.param.WebportalParam;
import webportal.weboperation.ApiRequest;

import static io.restassured.RestAssured.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SetStaticRouteWithInvalidBody_Api extends TestCaseBaseApi{

    String networkId;
    Map<String, String> headers = new HashMap<String, String>();
    Map<String, String> endPointUrl = new HashMap<String, String>();
    Map<String, String> pathParams = new HashMap<String, String>();
    
    @Feature("SetStaticRouteWithInvalidBody_Api") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("This test sets static route with Invalid body using Netgear APIs") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
    }
    
  
    @Step("Send get request to {url}")
    public void step1()
    {
           
        Response response=new Api_GetDevices().step1();
        String deviceId=response.jsonPath().getString("deviceInfo[0].deviceId");
        
        endPointUrl = new ApiRequest().ENDPOINT_URL;

        headers.put("token",WebportalParam.token);
        headers.put("apikey",WebportalParam.apikey);
        headers.put("accountId",WebportalParam.accountId);     
        
        pathParams.put("networkId",WebportalParam.networkId);
        
        String InvaliddeviceId = deviceId+"dfadsfdgdg";
        String requestBody = "{\"staticRouteInfo\":[{\"addrFamily\":\"1\",\"nextHopIp\":\"196.1.1.1\",\"netAddr\":\"0.0.0.0\",\"ipMask\":\"0.0.0.0\",\"routePref\":\"1\",\"type\":\"1\",\"deviceId\":\""+InvaliddeviceId+"\",\"previousNextHopIp\":\"0.0.0.0\",\"previousNetAddr\":\"0.0.0.0\",\"previousIpMask\":\"0.0.0.0\"}]}";
      
        //TO PERFORM ANY REQUEST
     
        Response getResponse = ApiRequest.sendPutRequest(endPointUrl.get("Set_StaticRoute"), requestBody, headers, pathParams, null,400); 
        getResponse.then().body("response.message", equalTo("An error occurred while processing the request. Try again."))
        .body("response.status", equalTo(false));
        
                    
    }

}
