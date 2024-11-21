package webportal.ApiTest.Wired.NegativeTestcases;
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
import webportal.ApiTest.Wired.PositiveTestcases.Api_GetLagGroupForNw;
import webportal.param.CommonDataType;
//import webportal.weboperation.WirelessQuickViewPage;
import webportal.param.WebportalParam;
import webportal.weboperation.ApiRequest;

import static io.restassured.RestAssured.*;


public class ModifyLagGroupWithEmptyBody_Api extends TestCaseBaseApi{
    
    String networkId;
    String lagGroupId;
    String deviceId;
    Map<String, String> headers = new HashMap<String, String>();
    Map<String, String> endPointUrl = new HashMap<String, String>();
    Map<String, String> pathParams = new HashMap<String, String>();
    
    @Feature("ModifyLagGroupWithEmptyBody_Api") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Modifies_Lagport with Emty Json") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
    }
    
    @AfterMethod
    public void teardown()
    {
        Map<String, String> pathParams = new HashMap<String, String>();
        pathParams.put("networkId",WebportalParam.networkId);
        pathParams.put("lagGroupId",lagGroupId);
        String requestBody="{\"lagConfig\":{\"adminMode\":1,\"lagMembers\":[{\"deviceId\":\""+deviceId+"\",\"lagId\":\"0\",\"ports\":[] }],\"name\":\"TestLAGG\",\"type\":1}}";
        Response getResponse = ApiRequest.sendDeleteRequest(endPointUrl.get("LagGroupId_Sanity"), requestBody, headers, pathParams, null); 
        getResponse.then().body("response.status", equalTo(true));
      
    }
  
    @Step("Send get request to {url}")
    public void step1()
    { 
             
        endPointUrl = new ApiRequest().ENDPOINT_URL;

        headers.put("token",WebportalParam.token);
        headers.put("apikey",WebportalParam.apikey);
        headers.put("accountId",WebportalParam.accountId);     
        
        pathParams.put("networkId",WebportalParam.networkId);
        
        Response response=new Api_GetLagGroupForNw().step1();
        lagGroupId = response.jsonPath().getString("lagSettings[0].id");
        deviceId = response.jsonPath().getString("lagSettings[0].lagMembers[0].deviceId");
        
        String requestBody = "{}";
      
        //TO PERFORM ANY REQUEST
     
        Response getResponse1 = ApiRequest.sendPutRequest(endPointUrl.get("LagGroup_Sanity"), requestBody, headers, pathParams, null,400); 
        getResponse1.then().body("response.status", equalTo(false))
                          .body("response.message", equalTo("An error occurred while processing the request. Try again."));
        

    }

}
