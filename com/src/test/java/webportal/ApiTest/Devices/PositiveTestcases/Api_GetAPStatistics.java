package webportal.ApiTest.Devices.PositiveTestcases;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.testng.Assert.assertTrue;


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


public class Api_GetAPStatistics extends TestCaseBaseApi{

    String networkId;
    Map<String, String> headers = new HashMap<String, String>();
    Map<String, String> endPointUrl = new HashMap<String, String>();
    Map<String, String> ssidInfo = new HashMap<String, String>();
    String id= "";
    
    @Feature("Api_GetAPStatistics") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("This test will get AP statistics for the Netgear APIs based on specific Network ID") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
    }
    
    @Step("Send get request to {url}")
    public void step1()
    {
        endPointUrl = new ApiRequest().ENDPOINT_URL; 
        headers.put("token",WebportalParam.token);
        headers.put("apikey",WebportalParam.apikey);
        headers.put("accountId",WebportalParam.accountId);     
        headers.put("networkId",WebportalParam.networkId);
        
        Map<String, String> pathParams = new HashMap<String, String>(); 
        pathParams.put("networkId",WebportalParam.networkId);
        String requestBody="{\"wirelessNetwork\":{\"mloStatus\":\"0\",\"ssid\":\"SSID_TESTITI\",\"vlanId\":\"1\",\"vlanType\":1,\"enable\":\"1\",\"radioBand\":\"8\",\"redirectStatus\":\"0\",\"broadcastStatus\":\"0\",\"bandSteeringSt\":\"0\",\"rrmSt\":\"0\",\"clientIsoSt\":\"0\",\"allowAccessToCIList\":\"0\",\"ciAllowedList\":[],\"securitySt\":\"0\",\"security\":{\"authentication\":\"32\",\"password\":\"Pass@123\",\"oweMode\":\"0\"},\"rateLimit\":{\"enableRateLimit\":\"0\"},\"captivePortal\":{\"enableCaptivePortal\":\"0\"},\"accessToApSt\":\"0\",\"dynamicVlanSt\":\"0\",\"fastRoamingSt\":\"0\",\"kvrStatus\":\"1\",\"arsStatus\":\"0\",\"encryption\":\"6\",\"natMode\":{\"status\":\"0\",\"networkAddress\":\"\",\"subnet\":\"255.255.252.0\",\"dns\":\"8.8.8.8\",\"leaseTime\":\"1440\"},\"iotRadiusServer\":\"0\",\"iotRadiusServerId\":\"\",\"iotRadiusPolicyId\":\"\",\"mduStatus\":\"0\",\"mpskList\":[],\"isMPSKEnabled\":\"0\",\"custProfileEnable\":\"0\",\"custProfileId\":\"\"}}";       
        //TO PERFORM ANY REQUEST

        Response getResponse = ApiRequest.sendPostRequest(endPointUrl.get("Add_Ssid"), requestBody, headers, pathParams, null); 
        getResponse.then().body("response.status", equalTo(true));
        id=getResponse.jsonPath().getString("wirelessNetworkInfo.wirelessNetworkId");
        
        MyCommonAPIs.sleepi(200);
        ssidInfo.put("SSID", "SSID_TESTITI");
        ssidInfo.put("Security", "WPA2 Personal");
        ssidInfo.put("Password", "Pass@123");
        assertTrue(new ApiRequest().connectClient(ssidInfo)); 
        MyCommonAPIs.sleepi(300);
        
    }

    @Step("Send get request to {url}")
    public void step2()
    {      
        endPointUrl = new ApiRequest().ENDPOINT_URL;

        headers.put("token",WebportalParam.token);
        headers.put("apikey",WebportalParam.apikey);
        headers.put("accountId",WebportalParam.accountId);     
        headers.put("networkId",WebportalParam.networkId);
        
        Map<String, String> pathParams = new HashMap<String, String>(); 
        pathParams.put("serialNo",WebportalParam.ap1deveiceName);
         
        //TO PERFORM ANY REQUEST
        Response getResponse = ApiRequest.sendGetRequest(endPointUrl.get("AP_Statistics"), headers, pathParams, null);
        getResponse.then().body("response.status", equalTo(true));
        getResponse.then().body("response.message", equalTo("Success in getting AP device statistics"));
                     
    }

}
