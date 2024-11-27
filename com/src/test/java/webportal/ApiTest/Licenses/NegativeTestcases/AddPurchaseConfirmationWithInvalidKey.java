package webportal.ApiTest.Licenses.NegativeTestcases;
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
import webportal.ApiTest.Organizations.PositiveTestcases.Api_AddOrganization;
//import webportal.weboperation.WirelessQuickViewPage;
import webportal.param.WebportalParam;
import webportal.weboperation.ApiRequest;
import webportal.weboperation.HamburgerMenuPage;

import static io.restassured.RestAssured.*;


public class AddPurchaseConfirmationWithInvalidKey extends TestCaseBaseApi{

    Map<String, String> endPointUrl = new HashMap<String, String>();
    Map<String, String> headers = new HashMap<String, String>();
    String OrgID;


    
    @Feature("AddPurchaseConfirmationWithInvalidKey") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Add purchase confirmation key at account and organization.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
       
        step1();
    }    
    @Step("Send get request to {url}")
    public void step1()
    {
      
        Response response1 = new Api_AddOrganization().step1();
        OrgID = response1.jsonPath().getString("orgInfo.orgId");
        endPointUrl = new ApiRequest().ENDPOINT_URL; 
        
        headers.put("token",WebportalParam.token);
        headers.put("accountId",WebportalParam.accountId);
        headers.put("apikey",WebportalParam.apikey);
        headers.put("OrgId",OrgID);
              

        String requestBody1="{\"licenseInfo\":{\"licKey\":\"NG4601-7E43-30D3-3B72-C83D-FD12-4E53-8288-3DC1\"}}";
        
        Response getResponse = ApiRequest.sendPostRequest(endPointUrl.get("Add_Purchase_Confirmation"),requestBody1, headers, null, null); 
        getResponse.then().body("response.status", equalTo(false))
                           .body("response.message", equalTo("Some error occured while registering the License key"));
        
        
        
    }
  
  


}