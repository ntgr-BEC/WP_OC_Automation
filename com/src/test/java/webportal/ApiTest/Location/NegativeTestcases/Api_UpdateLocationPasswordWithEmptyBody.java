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
import util.MyCommonAPIs;
import webportal.ApiTest.Location.PositiveTestcases.Api_AddNetwork;
import webportal.param.CommonDataType;
//import webportal.weboperation.WirelessQuickViewPage;
import webportal.param.WebportalParam;
import webportal.weboperation.ApiRequest;

import static io.restassured.RestAssured.*;


public class Api_UpdateLocationPasswordWithEmptyBody extends TestCaseBaseApi{
    Map<String, String> endPointUrl = new HashMap<String, String>();
    Map<String, String> headers = new HashMap<String, String>();
    Map<String, String> pathParamsupdate = new HashMap<String, String>();
    String networkId;


    
    @Feature("Api_UpdateLocationPassword") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T007") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Updating the locations password with Empty Json") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T007") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
    }
    
    @AfterMethod(alwaysRun=true)
    public void teardown()
    {  
        
        Response getResponse1 = ApiRequest.sendDeleteRequest(endPointUrl.get("Network_Sanity"), headers, pathParamsupdate, null); 
        getResponse1.then().body("response.status", equalTo(true));
    }
  
    @Step("Send get request to {url}")
    public void step1()
    {
         Response add = new Api_AddNetwork().step1();
          networkId=add.jsonPath().getString("networkInfo[0].networkId"); 
 
        endPointUrl = new ApiRequest().ENDPOINT_URL;        
        headers.put("token",WebportalParam.token);
        headers.put("apikey",WebportalParam.apikey);
        headers.put("accountId",WebportalParam.accountId); 

        pathParamsupdate.put("networkId",networkId);

        String requestBody="{}";
        
        Response getResponse = ApiRequest.sendPutRequest(endPointUrl.get("Update_Location_Password"), requestBody, headers, pathParamsupdate, null); 
        getResponse.then().body("response.status", equalTo(false))
                          .body("response.status", equalTo("A network error has occurred. Try again in a few minutes."));
 
        
    }
}
