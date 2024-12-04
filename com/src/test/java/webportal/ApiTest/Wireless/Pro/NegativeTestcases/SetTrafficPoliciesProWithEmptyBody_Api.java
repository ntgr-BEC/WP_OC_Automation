package webportal.ApiTest.Wireless.Pro.NegativeTestcases;
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
import webportal.ApiTest.Wireless.Pro.PositiveTestcases.Api_AddSsidPro;
//import webportal.weboperation.WirelessQuickViewPage;
import webportal.param.WebportalParam;
import webportal.weboperation.ApiRequest;

import static io.restassured.RestAssured.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class SetTrafficPoliciesProWithEmptyBody_Api extends TestCaseBaseApi{

    Map<String, String> endPointUrl = new HashMap<String, String>();
    Map<String, String> pathParams = new HashMap<String, String>();
    Map<String, String> headers = new HashMap<String, String>();
    String wirelessOrgId;
 
    
    @Feature("SetTrafficPoliciesProWithEmptyBody_Api") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T004") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Set traffic policies (DHCP offer as unicast) for organization SSID with empty Json.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T004") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
      
   }
    
    @AfterMethod(alwaysRun=true)
    public void teardown()
    { 
     
        pathParams.put("orgId",WebportalParam.orgId);
        pathParams.put("wirelessOrgId",wirelessOrgId);
        
        Response getResponse3 = ApiRequest.sendDeleteRequest(endPointUrl.get("Delete_Ssid_Pro"),headers, pathParams, null); 
        getResponse3.then().body("response.status", equalTo(true));

    }
   

    @Step("Send get request to {url}")
    public void step1()
    { 
        endPointUrl = new ApiRequest().ENDPOINT_URL;
        Response add = new Api_AddSsidPro().step1();           //Created the SSID and default DHCPOffer will be enabled
        
        wirelessOrgId= add.jsonPath().getString("wirelessOrgInfo.wirelessOrgId");
        
        headers.put("token",WebportalParam.token);
        headers.put("apikey",WebportalParam.apikey);    
        headers.put("accountId",WebportalParam.accountId);
       
    
        pathParams.put("orgId",WebportalParam.orgId);
        pathParams.put("wirelessOrgId",wirelessOrgId);
        
        String requestBody="{}";     //Disable the DHCPOffer
        //TO PERFORM ANY REQUEST
    
        Response getResponse = ApiRequest.sendPutRequest(endPointUrl.get("Set_TrafficPolicies"), requestBody, headers, pathParams, null); 
        getResponse.then().body("response.status", equalTo(false))
                          .body("response.message", equalTo("Required fields are missing."));
      
        
        
    }
    
    }

