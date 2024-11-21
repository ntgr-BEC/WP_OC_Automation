package webportal.ApiTest.Users;
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


public class Api_GetSecondaryAdmins extends TestCaseBaseApi{

    Map<String, String> endPointUrl = new HashMap<String, String>();
    Map<String, String> headers = new HashMap<String, String>();
  
    Random r        = new Random();
    int    num      = r.nextInt(10000000);
    String mailname = "apwptest" + String.valueOf(num) ;
    String email = mailname + "@yopmail.com";
    String secadminId="";
    
    @Feature("Api_InviteSecondaryAdmins") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Get secondary admin for an pro account.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
    }
    
    
    @AfterMethod(alwaysRun=true)
    public void teardown()
    { 
        Map<String, String> pathParams = new HashMap<String, String>();
        pathParams.put("secadminId",secadminId);
        Response getResponse = ApiRequest.sendDeleteRequest(endPointUrl.get("Delete_SecAdmin"), headers, pathParams, null); 
        getResponse.then().body("response.status", equalTo(true))
        .body("response.message", equalTo("Secondary admin deleted successfully"));
        
    }  
    
  
    @Step("Send get request to {url}")
    public Response step1()
    { 
        
        endPointUrl = new ApiRequest().ENDPOINT_URL; 
        new Api_InviteSecondaryAdmins().step1();
        headers.put("token",WebportalParam.token);
        headers.put("apikey",WebportalParam.apikey);
        headers.put("accountId",WebportalParam.accountId);
        
        Map<String, String> pathParams = new HashMap<String, String>();
        pathParams.put("startFrom","0");
        
        Response getResponse = ApiRequest.sendGetRequest(endPointUrl.get("Get_SecAdmin"), headers, pathParams, null); 
        getResponse.then().body("response.status", equalTo(true))
        .body("response.message", equalTo("Secondary admin list found."));
        secadminId= getResponse.jsonPath().getString("details.adminsList[0]._id");  
        
        return getResponse;
        }
                
    }



