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

import webportal.param.CommonDataType;
//import webportal.weboperation.WirelessQuickViewPage;
import webportal.param.WebportalParam;
import webportal.weboperation.ApiRequest;

import static io.restassured.RestAssured.*;


public class Api_AddLogPort{
    WebportalParam webportalParam = new WebportalParam();
    
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
        Map<String, String> endPointUrl = new HashMap<String, String>();
        endPointUrl = new ApiRequest().ENDPOINT_URL;
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("token",WebportalParam.token);
        headers.put("apikey",WebportalParam.apikey);
        headers.put("accountId",WebportalParam.accountId); 
        headers.put("networkId",WebportalParam.networkId);
        Map<String, String> pathParamsadd = new HashMap<String, String>();
        pathParamsadd.put("accountId",WebportalParam.accountId);
        Map<String, String> pathParamsupdate = new HashMap<String, String>();
        pathParamsupdate.put("networkId",WebportalParam.networkId);
        String requestBody1="{\"networkInfo\":[{\"name\":\"San Jose\",\"adminPassword\":\"Test@1234\",\"timeSettings\":{\"timeZone\":\"262\"},\"street\":\"\",\"city\":\"\",\"state\":\"\",\"postCode\":\"\",\"isoCountry\":\"US\"}]}";       
        String requestBody2="{\"networkInfo\":{\"name\":\"office12\"}}";
        

        
        
        //TO ADD NETWORK AND RETRIEVE NETWORK ID
        Response getResponse1 = ApiRequest.sendPostRequest(endPointUrl.get("Add_Network"), requestBody1, headers, pathParamsadd, null); 
        getResponse1.then().body("response.status", equalTo(true));
   
        Response getResponse2 = ApiRequest.sendPutRequest(endPointUrl.get("Network_Sanity"), requestBody2, headers, pathParamsupdate, null); 
        getResponse2.then().body("response.status", equalTo(true));
        
    }

}
