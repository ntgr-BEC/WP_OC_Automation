package webportal.ApiTest.Location.NegativeTestcases;
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
//import webportal.weboperation.WirelessQuickViewPage;
import webportal.param.WebportalParam;
import webportal.weboperation.ApiRequest;

import static io.restassured.RestAssured.*;


public class Api_AddNetworkWithEmptyApiKey extends TestCaseBaseApi{

    Map<String, String> endPointUrl = new HashMap<String, String>();
    Map<String, String> headers = new HashMap<String, String>();
    String networkId;
    
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
       
            endPointUrl = new ApiRequest().ENDPOINT_URL;          
            headers.put("token",WebportalParam.token);
            headers.put("apikey","");
                                   
            Map<String, String> pathParams = new HashMap<String, String>();
            pathParams.put("accountId",WebportalParam.accountId);
            String requestBody1="{\"networkInfo\":[{\"name\":\"San Jose\",\"adminPassword\":\"Test@1234\",\"timeSettings\":{\"timeZone\":\"262\"},\"street\":\"\",\"city\":\"\",\"state\":\"\",\"postCode\":\"\",\"isoCountry\":\"US\"}]}";       
            
            
            //TO ADD NETWORK AND RETRIEVE NETWORK ID
            Response getResponse = ApiRequest.sendPostRequest(endPointUrl.get("Add_Network"), requestBody1, headers, pathParams, null, 404); 
                         
        }
                
    }



