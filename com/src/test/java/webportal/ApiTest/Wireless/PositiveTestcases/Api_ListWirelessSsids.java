package webportal.ApiTest.Wireless.PositiveTestcases;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

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


public class Api_ListWirelessSsids extends TestCaseBaseApi{

    Map<String, String> endPointUrl = new HashMap<String, String>();
    Map<String, String> pathParams = new HashMap<String, String>();
    Map<String, String> headers = new HashMap<String, String>();
    String networkId;
    
    @Feature("Api_ListWirelessSsids") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T004") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("This test gets list of SSIDs configuration from the particular API") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T004") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
   }
    
    @AfterMethod(alwaysRun=true)
    public void teardown()
    { 
        Map<String, String> pathParams = new HashMap<String, String>();
        pathParams.put("networkId",networkId);
        
        Response getResponse1 = ApiRequest.sendDeleteRequest(endPointUrl.get("Network_Sanity"), headers, pathParams, null); 
        getResponse1.then().body("response.status", equalTo(true));
    }
  
    @Step("Send get request to {url}")
    public void step1()
    { 
        
        endPointUrl = new ApiRequest().ENDPOINT_URL;
        
        List<Response> response = new Api_AddSsid().step1();
        Response add=response.get(0);
        Response ssidid=response.get(1);
        String id=ssidid.jsonPath().getString("wirelessNetworkInfo.wirelessNetworkId");
        networkId=add.jsonPath().getString("networkInfo[0].networkId");
      
        
        headers.put("token",WebportalParam.token);
        headers.put("apikey",WebportalParam.apikey);
        headers.put("accountId",WebportalParam.accountId);        
          
        pathParams.put("networkId",networkId);
     
        //TO PERFORM ANY REQUEST

        Response getResponse = ApiRequest.sendGetRequest(endPointUrl.get("List_Wireless_Networks"), headers, pathParams, null); 
        getResponse.then().body("response.status", equalTo(true));
       
        //ADDED NORMAL SSID DETAILS
        getResponse.then().statusCode(200)
        .body("mpskList", hasSize(0)) 
        .body("rateEnabledSSID.isRateLimitExceeded", equalTo(false))
        .body("ciAllowedListLen", equalTo(0)); 
        
    }
                  
    }

