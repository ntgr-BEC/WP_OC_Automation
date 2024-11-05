package webportal.ApiTest.Licenses.Positive;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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


public class Api_FetchCreditsInformation extends TestCaseBaseApi{

    Map<String, String> endPointUrl = new HashMap<String, String>();
    Map<String, String> headers = new HashMap<String, String>();
    String OrgID;
    int DC= 2;
    int ICPC= 2;
    Response response1;
    

    
    @Feature("API_Licenses_PositiveTestcases") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Fetch credits information for an account.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
    }
    
    @AfterMethod(alwaysRun=true)
    public void teardown()
    { 
        
        
       
    }  
    
    @Step("Send get request to {url}")
    public void step1()
    {
      
        endPointUrl = new ApiRequest().ENDPOINT_URL;  
        headers.put("token",WebportalParam.token);
        headers.put("accountId",WebportalParam.accountId);
        headers.put("apikey",WebportalParam.apikey);
        
        Response getResponse = ApiRequest.sendGetRequest(endPointUrl.get("Fetch_Credits"), headers, null, null); 
        getResponse.then().body("response.status", equalTo(true));
        getResponse.then().body("details.email", equalTo((WebportalParam.adminName).toLowerCase() ));
    }
  
  


}