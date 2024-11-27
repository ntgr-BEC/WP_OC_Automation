package webportal.ApiTest.Licenses.PositiveTestcases;
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
import webportal.weboperation.HamburgerMenuPage;

import static io.restassured.RestAssured.*;


public class Api_GetLicensekeyInformation extends TestCaseBaseApi{

    Map<String, String> endPointUrl = new HashMap<String, String>();
    Map<String, String> headers = new HashMap<String, String>();
    String Licence;

    
    @Feature("Api_GetLicensekeyInformation") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Get licensekey information.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
        step2();
    }
      
    @Step("Send get request to {url}")
    public void step1()
    {
        
        Licence = new HamburgerMenuPage(false).readLicenceKeyByTxt("Write");
        
    }
    
    @Step("Send get request to {url}")
    public void step2()
    {
      
        endPointUrl = new ApiRequest().ENDPOINT_URL; 
        
        headers.put("token",WebportalParam.tokenPro);
        headers.put("accountId",WebportalParam.accountIdPro);
        headers.put("apikey",WebportalParam.apikey);
        
        Map<String, String> pathParams = new HashMap<String, String>();
        pathParams.put("type","0");
        
        String requestBody1="{\"licKey\":\""+Licence+"\"}";
        
        Response getResponse = ApiRequest.sendPostRequest(endPointUrl.get("Get_Licensekey_Information"),requestBody1, headers, pathParams, null); 
        getResponse.then().body("response.status", equalTo(true));

        
    }

}