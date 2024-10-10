package webportal.ApiTest;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;

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
//import webportal.weboperation.WirelessQuickViewPage;
import webportal.param.WebportalParam;
import webportal.weboperation.ApiRequest;

import static io.restassured.RestAssured.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Api_GetRadiusServerConfig extends TestCaseBaseApi{

    Map<String, String> endPointUrl = new HashMap<String, String>();
    Map<String, String> pathParams = new HashMap<String, String>();
    Map<String, String> headers = new HashMap<String, String>();
    String networkId;
    
    
    @Feature("Api_GetRadiusServerConfig") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("This test gets radius server configuration from the particular API") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
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
        headers.put("networkId",WebportalParam.networkId);
      
        //TO PERFORM ANY REQUEST 
        Response getResponse = ApiRequest.sendGetRequest(endPointUrl.get("RadiusServerConfig_Sanity"), headers, pathParams, null); 
        getResponse.then().body("response.status", equalTo(true));
        
        //DEFAULT NAT INFO 
//        getResponse.then().statusCode(200)
//        .body("details.networkAddress.ssid1", equalTo("172.31.0.1"))
//        .body("details.networkAddress.ssid2", equalTo("172.31.4.1"))
//        .body("details.networkAddress.ssid3", equalTo("172.31.8.1"))
//        .body("details.networkAddress.ssid4", equalTo("172.31.12.1"))
//        .body("details.networkAddress.ssid5", equalTo("172.31.16.1"))
//        .body("details.networkAddress.ssid6", equalTo("172.31.20.1"))
//        .body("details.networkAddress.ssid7", equalTo("172.31.24.1"))
//        .body("details.networkAddress.ssid8", equalTo("172.31.28.1"))
//        .body("details.subnet", hasItems(
//            "255.255.252.0",
//            "255.255.254.0",
//            "255.255.255.0",
//            "255.255.255.128",
//            "255.255.255.192",
//            "255.255.255.224",
//            "255.255.255.240",
//            "255.255.255.248",
//            "255.255.255.252"
//        ));
    }
                  
    }

