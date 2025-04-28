package webportal.ApiTest.Organizations.PositiveTestcases;
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


public class Api_AllocateDeviceCredits extends TestCaseBaseApi{

    Map<String, String> endPointUrl = new HashMap<String, String>();
    Map<String, String> headers = new HashMap<String, String>();
    String OrgID;
    int DC= 2;
    String Licence;
    Response response1;
    
    

    
    @Feature("Api_AllocateDeviceCredits") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Allocate device credits to organization") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
        step2(DC, OrgID);
    }
    
    @AfterMethod(alwaysRun=true)
    public void teardown()
    { 
        
        Map<String, String> pathParams = new HashMap<String, String>();
        pathParams.put("orgId",OrgID);
        pathParams.put("accountId",WebportalParam.accountIdPro);
        
        Response getResponse1 = ApiRequest.sendDeleteRequest(endPointUrl.get("Delete_Organization"), headers, pathParams, null); 
        getResponse1.then().body("response.status", equalTo(true));
       
    }  
    
    @Step("Send get request to {url}")
    public void step1()
    {
        response1 = new Api_AddOrganization().step1();
        OrgID = response1.jsonPath().getString("orgInfo.orgId");
        Licence = new ApiRequest().readLicenceKeyByTxt("Write");

       
    }
  
    @Step("Send get request to {url}")
    public void step2(int DC, String OrgID)
    {     
        

        endPointUrl = new ApiRequest().ENDPOINT_URL;        
        System.out.println(OrgID);
        headers.put("token",WebportalParam.tokenPro);
        headers.put("apikey",WebportalParam.apikey);
        headers.put("accountId",WebportalParam.accountIdPro);
//        headers.put("OrgId",OrgID);
        
        Map<String, String> pathParams = new HashMap<String, String>();
        pathParams.put("orgId", OrgID);
        
        
         String requestBody1="{\"licenseInfo\":{\"licKey\":\""+Licence+"\"}}";
        
        Response getResponse1 = ApiRequest.sendPostRequest(endPointUrl.get("Add_Purchase_Confirmation"),requestBody1, headers, null, null); 
        getResponse1.then().body("response.status", equalTo(true));
        
        String requestBody2="{\"bsVpnCredInfo\":{\"creditCount\":0,\"unusedLicKeys\":[{\"credits\":0,\"key\":\"string\"}]},\"cfCredInfo\":{\"creditCount\":0,\"unusedLicKeys\":[{\"credits\":0,\"key\":\"string\"}]},\"cfTopUpCredInfo\":{\"creditCount\":0,\"unusedLicKeys\":[{\"credits\":0,\"key\":\"string\"}]},\"devCredInfo\":{\"creditCount\":2,\"unusedLicKeys\":[{\"credits\":0,\"key\":\""+Licence+"\"}]},\"instantCpCredInfo\":{\"creditCount\":0,\"unusedLicKeys\":[{\"credits\":0,\"key\":\"string\"}]},\"vpnCredInfo\":{\"creditCount\":0,\"unusedLicKeys\":[{\"credits\":0,\"key\":\"string\"}]}}";
        
        Response getResponse2 = ApiRequest.sendPostRequest(endPointUrl.get("AllocateCredits"), requestBody2, headers, pathParams, null); 
        getResponse2.then().body("response.status", equalTo(true));
        getResponse2.then().body("response.message", equalTo("Success in setting organization allocation details."));
        getResponse2.then().body("devAllocResp.message", equalTo("Allocation of device credit success in organization."));
//        getResponse.then().body("instantCpAllocResp.message", equalTo("Allocation of instant captive portal credit success in organization."));     
       
    }



}