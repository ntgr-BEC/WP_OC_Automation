package webportal.ApiTest.Wireless.NegativeTestcases;
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AddSsidWithInvalidBody_Api extends TestCaseBaseApi{

    Map<String, String> endPointUrl = new HashMap<String, String>();
    Map<String, String> pathParams = new HashMap<String, String>();
    Map<String, String> headers = new HashMap<String, String>();
    String networkId;
    
    @Feature("AddSsidWithInvalidBody_Api") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T004") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Addition of ssid with invalid body") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T004") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
   }
    
    @AfterMethod(alwaysRun=true)
    public void teardown()
    { 
 
        Response getResponse1 = ApiRequest.sendDeleteRequest(endPointUrl.get("Network_Sanity"), headers, pathParams, null); 
        getResponse1.then().body("response.status", equalTo(true));
    }
  
    @Step("Send get request to {url}")
    public void step1()
    { 
        endPointUrl = new ApiRequest().ENDPOINT_URL;
        Response add = new Api_AddNetwork().step1();
        networkId=add.jsonPath().getString("networkInfo[0].networkId");    
           
       
        headers.put("token",WebportalParam.token);
        headers.put("apikey",WebportalParam.apikey);    
        headers.put("accountId",WebportalParam.accountId);
       
        pathParams.put("networkId",networkId);
        
        String InvalidPassword = "Pa";
        String requestBody="{\"wirelessNetwork\":{\"mloStatus\":\"0\",\"ssid\":\"SSID_TEST\",\"vlanId\":\"1\",\"vlanType\":1,\"enable\":\"1\",\"radioBand\":\"8\",\"redirectStatus\":\"0\",\"broadcastStatus\":\"0\",\"bandSteeringSt\":\"0\",\"rrmSt\":\"0\",\"clientIsoSt\":\"0\",\"allowAccessToCIList\":\"0\",\"ciAllowedList\":[],\"securitySt\":\"0\",\"security\":{\"authentication\":\"32\",\"password\":\""+InvalidPassword+"\",\"oweMode\":\"0\"},\"rateLimit\":{\"enableRateLimit\":\"0\"},\"captivePortal\":{\"enableCaptivePortal\":\"0\"},\"accessToApSt\":\"0\",\"dynamicVlanSt\":\"0\",\"fastRoamingSt\":\"0\",\"kvrStatus\":\"1\",\"arsStatus\":\"0\",\"encryption\":\"6\",\"natMode\":{\"status\":\"0\",\"networkAddress\":\"\",\"subnet\":\"255.255.252.0\",\"dns\":\"8.8.8.8\",\"leaseTime\":\"1440\"},\"iotRadiusServer\":\"0\",\"iotRadiusServerId\":\"\",\"iotRadiusPolicyId\":\"\",\"mduStatus\":\"0\",\"mpskList\":[],\"isMPSKEnabled\":\"0\",\"custProfileEnable\":\"0\",\"custProfileId\":\"\"}}";       
        //TO PERFORM ANY REQUEST

        Response getResponse = ApiRequest.sendPostRequest(endPointUrl.get("Add_Ssid"), requestBody, headers, pathParams, null); 
        getResponse.then().body("response.status", equalTo(false))
        .body("response.message", equalTo("Incorrect password pattern"));
       
      
        
    }
                  
    }

