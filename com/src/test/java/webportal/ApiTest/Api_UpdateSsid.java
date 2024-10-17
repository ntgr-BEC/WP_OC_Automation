package webportal.ApiTest;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.HashMap;
import java.util.Map;

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
import webportal.param.CommonDataType;
//import webportal.weboperation.WirelessQuickViewPage;
import webportal.param.WebportalParam;
import webportal.weboperation.ApiRequest;

import static io.restassured.RestAssured.*;


public class Api_UpdateSsid extends TestCaseBaseApi{

    
    @Feature("VLAN Listing") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("This test retrieves VLAN details feom the Netgear APIs based on specific Network ID") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
    }
  
    @Step("Send get request to {url}")
    public void step1()
    { 
//        new Api_AddSsid().step1();
        Map<String, String> endPointUrl = new HashMap<String, String>();
        endPointUrl = new ApiRequest().ENDPOINT_URL;
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("token",WebportalParam.token);
        headers.put("apikey",WebportalParam.apikey);
        headers.put("accountId",WebportalParam.accountId);        
        Map<String, String> pathParams = new HashMap<String, String>();
        pathParams.put("networkId",WebportalParam.networkId);
        pathParams.put("id","66fba254abee9135d7bbe478");
//        pathParams.put("ssidId",WebportalParam.ssidId);
        
        String requestBody="{\"wirelessNetwork\":{\"mloStatus\":\"0\",\"enable\":\"1\",\"ssid\":\"SSIDTest002\",\"vlanId\":\"1\",\"vlanType\":1,\"radioBand\":\"8\",\"broadcastStatus\":\"0\",\"bandSteeringSt\":\"0\",\"clientIsoSt\":\"0\",\"allowAccessToCIList\":\"0\",\"ciAllowedList\":[],\"securitySt\":\"0\",\"rrmSt\":\"0\",\"security\":{\"authentication\":\"32\",\"password\":\"Pass@123\",\"oweMode\":\"0\"},\"rateLimit\":{\"enableRateLimit\":0,\"uploadRateLimit\":\"65536\",\"downloadRateLimit\":\"65536\"},\"captivePortal\":{\"enableCaptivePortal\":\"0\"},\"fbCpStatus\":\"0\",\"accessToApSt\":\"0\",\"dynamicVlanSt\":\"0\",\"fastRoamingSt\":\"0\",\"kvrStatus\":\"1\",\"arsStatus\":\"0\",\"encryption\":\"4\",\"bandChanged\":\"1\",\"vlanChanged\":\"0\",\"nameChanged\":\"0\",\"natMode\":{\"status\":\"0\",\"networkAddress\":\"\",\"subnet\":\"255.255.252.0\",\"dns\":\"8.8.8.8\",\"leaseTime\":1440},\"iotRadiusServer\":\"0\",\"iotRadiusPolicyId\":\"\",\"iotRadiusServerId\":\"\",\"mduStatus\":\"0\",\"isMdnsEnabled\":false,\"ars\":{\"wlan0\":{\"status\":\"0\",\"densityLevel\":\"0\",\"multicastRate\":\"11\"},\"wlan1\":{\"status\":\"0\",\"densityLevel\":\"0\",\"multicastRate\":\"24\"}},\"mpskList\":[],\"isMPSKEnabled\":\"0\",\"custProfileEnable\":\"0\",\"custProfileId\":\"\"}}";
        
        //TO PERFORM ANY REQUEST
   
        Response getResponse = ApiRequest.sendPutRequest(endPointUrl.get("Update_Ssid"), requestBody, headers, pathParams, null); 
        getResponse.then().body("response.status", equalTo(true));
  
        
    }

}
