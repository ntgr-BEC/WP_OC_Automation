package webportal.ApiTest.Devices;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import testbase.TestCaseBaseApi;
import webportal.ApiTest.Location.PositiveTestcases.Api_AddNetwork;
//import webportal.weboperation.WirelessQuickViewPage;
import webportal.param.WebportalParam;
import webportal.weboperation.ApiRequest;

import static io.restassured.RestAssured.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Api_AddDevice extends TestCaseBaseApi{

    String networkId;
    Map<String, String> headers = new HashMap<String, String>();
    Map<String, String> endPointUrl = new HashMap<String, String>();
    Map<String, String> pathParams = new HashMap<String, String>();
    Map<String, String> deviceInfo = new HashMap<String, String>();
    
    @Feature("Api_DeleteAPStatistics") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("This test will add device for the Netgear APIs based on specific Network ID") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
    }
    
    @BeforeMethod(alwaysRun=true)
    public void teardown()
    { 
        Response add = new Api_DeleteDevice().step1();     //delete the device first and then add the device
    }
  
    @Step("Send get request to {url}")
    public Response step1()
    {      
        endPointUrl = new ApiRequest().ENDPOINT_URL; 
        headers.put("token",WebportalParam.token);
        headers.put("apikey",WebportalParam.apikey);
        headers.put("accountId",WebportalParam.accountId);     
        
        pathParams.put("networkId",WebportalParam.networkId);
         
        String requestBody = "{\"deviceInfo\":{\"deviceType\":\"NA\",\"deviceName\":\"6AC2882982991\",\"serialNo\":\"6AC2882982991\",\"model\":\"\",\"macAddress\":\"87:17:87:18:37:91\",\"devicePlateform\":\"WEB\",\"abGroupId\":\"\",\"abDeviceMode\":\"1\"}}";
       
        requestBody = requestBody.replace("\"serialNo\":\"6AC2882982991\"", "\"serialNo\":\"" + WebportalParam.ap1deveiceName + "\"");
        requestBody = requestBody.replace("\"macAddress\":\"87:17:87:18:37:91\"", "\"macAddress\":\"" + WebportalParam.ap1macaddress + "\"");
        requestBody = requestBody.replace("\"deviceType\":\"NA\"", "\"deviceType\":\"AP\"");

        //TO PERFORM ANY REQUEST
        Response getResponse = ApiRequest.sendPostRequest(endPointUrl.get("Add_Device"), requestBody, headers, pathParams, null); 
        getResponse.then().body("response.status", equalTo(true));
        getResponse.then().body("response.message", equalTo("Device added."));
        
        return getResponse;
                          
    }

}
