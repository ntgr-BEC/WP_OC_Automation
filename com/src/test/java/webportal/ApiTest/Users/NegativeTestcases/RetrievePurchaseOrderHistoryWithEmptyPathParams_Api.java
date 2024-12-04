package webportal.ApiTest.Users.NegativeTestcases;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.uwyn.jhighlight.fastutil.Arrays;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import testbase.TestCaseBaseApi;
import webportal.param.WebportalParam;
import webportal.weboperation.ApiRequest;

import static io.restassured.RestAssured.*;


public class RetrievePurchaseOrderHistoryWithEmptyPathParams_Api extends TestCaseBaseApi{

    Map<String, String> endPointUrl = new HashMap<String, String>();
    Map<String, String> headers = new HashMap<String, String>();
  
    
    @Feature("RetrievePurchaseOrderHistoryWithEmptyPathParams_Api") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Retrive the purchase order history for an pro account with empty pathparams.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
    }

    @Step("Send get request to {url}")
    public void step1()
    { 
        
        endPointUrl = new ApiRequest().ENDPOINT_URL; 
        
        headers.put("token",WebportalParam.tokenPro);
        headers.put("apikey",WebportalParam.apikey);
        headers.put("accountId",WebportalParam.accountIdPro);
    
        Map<String, String> pathParams = new HashMap<String, String>();
        pathParams.put("timePeriod","all");   //The enumerations are all, 365 Days, 90 Days, 30 Days.
        pathParams.put("categoryId"," ");          //Empty pathparmas
        
        Response getResponse = ApiRequest.sendGetRequest(endPointUrl.get("Retrieve_PurchaseOrderHistory"), headers, pathParams, null); 
        getResponse.then().body("response.status", equalTo(false))
        .body("response.message", equalTo("Category not found with Id "));
               
        }
}


