package webportal.ApiTest.Wireless.Pro.PositiveTestcases;
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
import webportal.ApiTest.Wireless.PositiveTestcases.Api_AddSsid;
//import webportal.weboperation.WirelessQuickViewPage;
import webportal.param.WebportalParam;
import webportal.weboperation.ApiRequest;

import static io.restassured.RestAssured.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class Api_SetTrafficPoliciesForLocSsidPro extends TestCaseBaseApi{

    Map<String, String> endPointUrl = new HashMap<String, String>();
  
    Map<String, String> headers = new HashMap<String, String>();
    String id;
 
    
    @Feature("Api_SetTrafficPoliciesForLocSsidPro") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T004") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Get traffic policies (DHCP offer as unicast) from Location SSSID.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T004") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
        step2();
   }
    
    @BeforeMethod(alwaysRun=true)
    public void teardown1()
    { 
        headers.put("token",WebportalParam.tokenPro);
        headers.put("apikey",WebportalParam.apikey);    
        headers.put("accountId",WebportalParam.accountIdPro);
        endPointUrl = new ApiRequest().ENDPOINT_URL;
        
        Map<String, String> pathParams = new HashMap<String, String>();
        pathParams.put("networkId",WebportalParam.networkIdPro);
        String requestBody="{\"wirelessNetwork\":{\"mloStatus\":\"0\",\"ssid\":\"SSID_TEST\",\"vlanId\":\"1\",\"vlanType\":1,\"enable\":\"1\",\"radioBand\":\"8\",\"redirectStatus\":\"0\",\"broadcastStatus\":\"0\",\"bandSteeringSt\":\"0\",\"rrmSt\":\"0\",\"clientIsoSt\":\"0\",\"allowAccessToCIList\":\"0\",\"ciAllowedList\":[],\"securitySt\":\"0\",\"security\":{\"authentication\":\"32\",\"password\":\"Pass@123\",\"oweMode\":\"0\"},\"rateLimit\":{\"enableRateLimit\":\"0\"},\"captivePortal\":{\"enableCaptivePortal\":\"0\"},\"accessToApSt\":\"0\",\"dynamicVlanSt\":\"0\",\"fastRoamingSt\":\"0\",\"kvrStatus\":\"1\",\"arsStatus\":\"0\",\"encryption\":\"6\",\"natMode\":{\"status\":\"0\",\"networkAddress\":\"\",\"subnet\":\"255.255.252.0\",\"dns\":\"8.8.8.8\",\"leaseTime\":\"1440\"},\"iotRadiusServer\":\"0\",\"iotRadiusServerId\":\"\",\"iotRadiusPolicyId\":\"\",\"mduStatus\":\"0\",\"mpskList\":[],\"isMPSKEnabled\":\"0\",\"custProfileEnable\":\"0\",\"custProfileId\":\"\"}}";       
        //TO PERFORM ANY REQUEST

        Response getResponse = ApiRequest.sendPostRequest(endPointUrl.get("Add_Ssid"), requestBody, headers, pathParams, null); 
        getResponse.then().body("response.status", equalTo(true));
        id=getResponse.jsonPath().getString("wirelessNetworkInfo.wirelessNetworkId");

    }
    
    @AfterMethod(alwaysRun=true)
    public void teardown()
    { 
      
        Map<String, String> pathParams = new HashMap<String, String>();
        pathParams.put("networkId",WebportalParam.networkIdPro);
        pathParams.put("id",id);  
        
        Response getResponse = ApiRequest.sendDeleteRequest(endPointUrl.get("Ssid_Sanity"),headers, pathParams, null); 
        getResponse.then().body("response.status", equalTo(true)); 

    }
   
    @Step("Send get request to {url}")
    public Response step1()
    { 
        endPointUrl = new ApiRequest().ENDPOINT_URL;
        headers.put("token",WebportalParam.tokenPro);
        headers.put("apikey",WebportalParam.apikey);    
        headers.put("accountId",WebportalParam.accountIdPro);
       
        Map<String, String> pathParams = new HashMap<String, String>();
        pathParams.put("orgId",WebportalParam.orgId);
        pathParams.put("networkId",WebportalParam.networkIdPro);
        pathParams.put("id",id);
        
        String requestBody = "{\"wirelessNetwork\":{\"dhcpOfferBcastToUcast\":\"0\"}}";
        
        //TO PERFORM ANY REQUEST
        Response getResponse = ApiRequest.sendPutRequest(endPointUrl.get("Set_TrafficPolicies_ForLocSsid"), requestBody, headers, pathParams, null); 
        getResponse.then().body("response.status", equalTo(true))
        .body("details[0].message", equalTo("Applying your configuration settings. This can take up to 3 minutes to display"));
        
        return getResponse;
    }
    
    @Step("Send get request to {url}")
    public void step2() {
        
        Map<String, String> pathParams = new HashMap<String, String>();
        pathParams.put("networkId",WebportalParam.networkIdPro);
        pathParams.put("id",id); 
        
        Response getResponse = ApiRequest.sendGetRequest(endPointUrl.get("TrafficPolices_Sanity"), headers, pathParams, null); 
        getResponse.then().body("response.status", equalTo(true))
        .body("details.ssid", equalTo("SSID_TEST"))
        .body("details.dhcpOfferBcastToUcast", equalTo("0"));
    }
                  
    }

