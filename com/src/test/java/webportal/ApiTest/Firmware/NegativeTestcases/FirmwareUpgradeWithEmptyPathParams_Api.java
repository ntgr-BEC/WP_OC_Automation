package webportal.ApiTest.Firmware.NegativeTestcases;
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
import webportal.param.CommonDataType;
//import webportal.weboperation.WirelessQuickViewPage;
import webportal.param.WebportalParam;
import webportal.weboperation.ApiRequest;

import static io.restassured.RestAssured.*;


public class FirmwareUpgradeWithEmptyPathParams_Api extends TestCaseBaseApi{

    String networkId;
    Map<String, String> endPointUrl = new HashMap<String, String>();
    Map<String, String> headers = new HashMap<String, String>();
    Map<String, String> pathParamsupdate = new HashMap<String, String>();
    
    @Feature("FirmwareUpgradeWithEmptyPathParams_Api") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T008") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("UPDATE Device Firmware with empty path params") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T008") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
    }

  
    @Step("Send get request to {url}")
    public void step1()
    {

        endPointUrl = new ApiRequest().ENDPOINT_URL;        
        headers.put("token",WebportalParam.token);
        headers.put("apikey",WebportalParam.apikey);
        headers.put("accountId",WebportalParam.accountId); 

        pathParamsupdate.put("networkId",WebportalParam.networkId);
        pathParamsupdate.put("deviceCount"," ");         //With empty device count pathparam

        String requestBody="{\"fwInfo\":{\"fwUpgrade\":\"firmwareUpgrade\",\"serialNo\":\""+WebportalParam.ap1serialNo+"\"}}";
        
        Response getResponse = ApiRequest.sendPutRequest(endPointUrl.get("Firmware_Upgrade"), requestBody, headers, pathParamsupdate, null,400); 
        getResponse.then().body("response.status", equalTo(false));
}
}
