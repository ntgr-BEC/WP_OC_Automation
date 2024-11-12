package webportal.ApiTest.MSP.PositiveTestcases;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

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
import java.util.Random;


public class Api_GetManagers extends TestCaseBaseApi{

    Map<String, String> endPointUrl = new HashMap<String, String>();
    Map<String, String> pathParams = new HashMap<String, String>();
    Map<String, String> headers = new HashMap<String, String>();
    String managerId;
    String orgId;
    
    
    @Feature("Api_GetManagers") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("This test retrieves managers details of a pro account") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
    }
    
    @AfterMethod(alwaysRun=true)
    public void teardown()
    { 
        Map<String, String> pathParams1 = new HashMap<String, String>(); 
        pathParams1.put("managerId",managerId);
       
        
        Response getResponse1 = ApiRequest.sendDeleteRequest(endPointUrl.get("Manager_Sanity"), headers, pathParams1, null); 
        getResponse1.then().body("response.status", equalTo(true));
        
        Map<String, String> pathParams2 = new HashMap<String, String>();
        pathParams2.put("accountId",WebportalParam.accountId);
        pathParams2.put("orgId",orgId);

         Response getResponse2 = ApiRequest.sendDeleteRequest(endPointUrl.get("Delete_Organization"), headers, pathParams2, null); 
         getResponse2.then().body("response.status", equalTo(true));
        
    }

  
    @Step("Send get request to {url}")
    public List<Response> step1()
    { 

        
       Response response = new Api_AddManagers().step1();
       orgId=response.jsonPath().getString("orgInfo.orgId");

        endPointUrl = new ApiRequest().ENDPOINT_URL;   
        
        headers.put("token",WebportalParam.token);
        headers.put("apikey",WebportalParam.apikey);    
        headers.put("accountId",WebportalParam.accountId);
                
        pathParams.put("orgId",orgId);
        pathParams.put("startFrom","1");
        

        //TO PERFORM ANY REQUEST 
        Response getResponse = ApiRequest.sendGetRequest(endPointUrl.get("Get_Manager"), headers, pathParams, null); 
        getResponse.then().body("response.status", equalTo(true));
        managerId=getResponse.jsonPath().getString("details[0]._id");
        System.out.println(managerId);

        
        return Arrays.asList(response,getResponse);

    }
                  
    }

