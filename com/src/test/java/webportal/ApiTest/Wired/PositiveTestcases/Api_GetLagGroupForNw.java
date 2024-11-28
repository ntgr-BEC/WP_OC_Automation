package webportal.ApiTest.Wired.PositiveTestcases;
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
import webportal.ApiTest.Devices.PositiveTestcases.Api_GetDevices;
import webportal.ApiTest.Location.PositiveTestcases.Api_AddNetwork;
import webportal.param.CommonDataType;
//import webportal.weboperation.WirelessQuickViewPage;
import webportal.param.WebportalParam;
import webportal.weboperation.ApiRequest;

import static io.restassured.RestAssured.*;


public class Api_GetLagGroupForNw extends TestCaseBaseApi{
    String networkId;
    String lagGroupId;
    String deviceId;
    Map<String, String> headers = new HashMap<String, String>();
    Map<String, String> endPointUrl = new HashMap<String, String>();
   
    
    @Feature("Api_GetLagPort") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Retrieves_Lagport_data") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
    }
    
    @AfterMethod
    public void teardown()
    {
        Map<String, String> pathParams = new HashMap<String, String>();
        pathParams.put("networkId",WebportalParam.networkIdSw);
        pathParams.put("lagGroupId",lagGroupId);
        String requestBody="{\"lagConfig\":{\"adminMode\":1,\"lagMembers\":[{\"deviceId\":\""+deviceId+"\",\"lagId\":\"0\",\"ports\":[] }],\"name\":\"TestLAGG\",\"type\":1}}";
        Response getResponse = ApiRequest.sendDeleteRequest(endPointUrl.get("LagGroupId_Sanity"), requestBody, headers, pathParams, null); 
        getResponse.then().body("response.status", equalTo(true));
      
    }
  
    @Step("Send get request to {url}")
    public Response step1()
    { 
        Response response=new Api_AddLagGroup().step1();
             
        endPointUrl = new ApiRequest().ENDPOINT_URL;

        headers.put("token",WebportalParam.token);
        headers.put("apikey",WebportalParam.apikey);
        headers.put("accountId",WebportalParam.accountId);     
        Map<String, String> pathParams = new HashMap<String, String>();
        pathParams.put("networkId",WebportalParam.networkIdSw);
           
        //TO PERFORM ANY REQUEST    
        Response getResponse = ApiRequest.sendGetRequest(endPointUrl.get("LagGroup_Sanity"), headers, pathParams, null); 
        getResponse.then().body("response.status", equalTo(true))
                          .body("response.message", equalTo("Success"));
        
        lagGroupId = getResponse.jsonPath().getString("lagSettings[0].id");
        deviceId = getResponse.jsonPath().getString("lagSettings[0].lagMembers[0].deviceId");
        System.out.println("---------------------"+deviceId);

        
        return getResponse;
        
        
        
    }

}
