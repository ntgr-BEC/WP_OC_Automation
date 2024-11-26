package webportal.ApiTest.Wireless.PositiveTestcases;
import static org.hamcrest.CoreMatchers.equalTo;

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


public class Api_AddMacAcl extends TestCaseBaseApi{

    Map<String, String> endPointUrl = new HashMap<String, String>();
    
    Map<String, String> headers = new HashMap<String, String>();
    String id;
    
    @Feature("Api_AddMacAcl") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("This test Adds MAC ACL config from the particular network ID") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
    }
    
    @AfterMethod(alwaysRun=true)
    public void teardown()
    {    
        Map<String, String> pathParams = new HashMap<String, String>();
        pathParams.put("networkId",WebportalParam.networkId);
        pathParams.put("id",id);  
        
        Response getResponse = ApiRequest.sendDeleteRequest(endPointUrl.get("Ssid_Sanity"),headers, pathParams, null); 
        getResponse.then().body("response.status", equalTo(true));
    }

  
    @Step("Send get request to {url}")
    public Response step1()
    { 
        endPointUrl = new ApiRequest().ENDPOINT_URL;        

        headers.put("token",WebportalParam.token);
        headers.put("apikey",WebportalParam.apikey);    
        headers.put("accountId",WebportalParam.accountId);
        
        Map<String, String> pathParams1 = new HashMap<String, String>();
        pathParams1.put("networkId",WebportalParam.networkId);
        
        String requestBody1="{\"wirelessNetwork\":{\"mloStatus\":\"0\",\"ssid\":\"SSID_TEST_MAC\",\"vlanId\":\"1\",\"vlanType\":1,\"enable\":\"1\",\"radioBand\":\"8\",\"redirectStatus\":\"0\",\"broadcastStatus\":\"0\",\"bandSteeringSt\":\"0\",\"rrmSt\":\"0\",\"clientIsoSt\":\"0\",\"allowAccessToCIList\":\"0\",\"ciAllowedList\":[],\"securitySt\":\"0\",\"security\":{\"authentication\":\"32\",\"password\":\"Pass@123\",\"oweMode\":\"0\"},\"rateLimit\":{\"enableRateLimit\":\"0\"},\"captivePortal\":{\"enableCaptivePortal\":\"0\"},\"accessToApSt\":\"0\",\"dynamicVlanSt\":\"0\",\"fastRoamingSt\":\"0\",\"kvrStatus\":\"1\",\"arsStatus\":\"0\",\"encryption\":\"6\",\"natMode\":{\"status\":\"0\",\"networkAddress\":\"\",\"subnet\":\"255.255.252.0\",\"dns\":\"8.8.8.8\",\"leaseTime\":\"1440\"},\"iotRadiusServer\":\"0\",\"iotRadiusServerId\":\"\",\"iotRadiusPolicyId\":\"\",\"mduStatus\":\"0\",\"mpskList\":[],\"isMPSKEnabled\":\"0\",\"custProfileEnable\":\"0\",\"custProfileId\":\"\"}}";       
   
        Response getResponse1 = ApiRequest.sendPostRequest(endPointUrl.get("Add_Ssid"), requestBody1, headers, pathParams1, null); 
        getResponse1.then().body("response.status", equalTo(true));
        
        id=getResponse1.jsonPath().getString("wirelessNetworkInfo.wirelessNetworkId");
        

       
        Map<String, String> pathParams2 = new HashMap<String, String>();
        pathParams2.put("networkId",WebportalParam.networkId);
        pathParams2.put("wirelessNetworkId",id);  
        
        String requestBody2 = "{\"macAclConfigInfo\":{\"macAuth\":\"1\",\"type\":\"0\",\"policy\":\"0\",\"macList\":[{\"deviceName\":\"TEScgeck\",\"mac\":\"11:33:11:22:34:77\"}]}}";
                                             
        //TO PERFORM ANY REQUEST 
        Response getResponse2 = ApiRequest.sendPostRequest(endPointUrl.get("Add_Wireless_MacAcl"), requestBody2, headers, pathParams2, null); 
        getResponse2.then().body("response.status", equalTo(true));
        
        return getResponse1;
        
    }
                  
    }

