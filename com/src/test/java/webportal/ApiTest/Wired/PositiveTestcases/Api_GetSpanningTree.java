package webportal.ApiTest.Wired.PositiveTestcases;
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
import webportal.ApiTest.Location.PositiveTestcases.Api_AddNetwork;
//import webportal.weboperation.WirelessQuickViewPage;
import webportal.param.WebportalParam;
import webportal.weboperation.ApiRequest;

import static io.restassured.RestAssured.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Api_GetSpanningTree extends TestCaseBaseApi{

    String networkId;
    Map<String, String> headers = new HashMap<String, String>();
    Map<String, String> endPointUrl = new HashMap<String, String>();
    Map<String, String> pathParams = new HashMap<String, String>();
    
    @Feature("Api_GetSpanningTree") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("This test get spanning tree data") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
    }
    
    @AfterMethod(alwaysRun=true)
    public void teardown()
    {  
     //Enabling the spanning tree -- default configuration
     String body="{\"stpRstpConfig\":{\"status\":\"1\",\"stpMode\":\"2\",\"switchInfo\":[{\"serialNo\":\""+ WebportalParam.sw1serialNo +"\",\"port\":[1,2,3,4,5,6,7,8],\"lagId\":[1]}]}}";
        
    //TO PERFORM ANY REQUEST
     Response getResponse = ApiRequest.sendPostRequest(endPointUrl.get("Set_SpanningTree"),body, headers, pathParams, null); 
     getResponse.then().body("response.status", equalTo(true))
                       .body("response.message", equalTo("success"))
                       .body("networkInfo[0].deviceName", equalTo(WebportalParam.sw1serialNo))
                       .body("networkInfo[0].message", equalTo("Your configuration has been applied. It may take some time to reflect"));  
    }
  
    @Step("Send get request to {url}")
    public Response step1()
    {
        endPointUrl = new ApiRequest().ENDPOINT_URL;
        Response add = new Api_SetSpanningTree().step1();     //Set the spanning tree --disable
        headers.put("token",WebportalParam.token);
        headers.put("apikey",WebportalParam.apikey);
        headers.put("accountId",WebportalParam.accountId);           
   
        pathParams.put("networkId",WebportalParam.networkIdSw);

        //TO PERFORM ANY REQUEST
     
        Response getResponse = ApiRequest.sendGetRequest(endPointUrl.get("Get_SpanningTree"), headers, pathParams, null); 
        getResponse.then().body("response.status", equalTo(true))
                          .body("response.message", equalTo("success"))
                          .body("networkInfo.switchInfo[0].serialNo", equalTo(WebportalParam.sw1serialNo))
                          .body("networkInfo.status", equalTo("0"));       //verify the spanning tree --disable
        
        return getResponse; 
        
                
    }

}
