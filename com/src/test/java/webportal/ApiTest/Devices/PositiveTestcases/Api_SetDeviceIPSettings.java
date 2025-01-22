package webportal.ApiTest.Devices.PositiveTestcases;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

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
import webportal.ApiTest.Location.PositiveTestcases.Api_AddNetwork;
//import webportal.weboperation.WirelessQuickViewPage;
import webportal.param.WebportalParam;
import webportal.weboperation.ApiRequest;

import static io.restassured.RestAssured.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Api_SetDeviceIPSettings extends TestCaseBaseApi{

    String networkId;
    Map<String, String> headers = new HashMap<String, String>();
    Map<String, String> endPointUrl = new HashMap<String, String>();
 
    
    @Feature("Api_GetDeviceIPSettings") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("This test sets device IP settings for the Netgear APIs based on specific Network ID") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case.
    
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
        
        Map<String, String> pathParams = new HashMap<String, String>();
        pathParams.put("deviceType","AP");
        pathParams.put("serialNo",WebportalParam.ap1deveiceName); 
        pathParams.put("commandType","ipSetting");   
        
//        The command type value according to the device. The enumerations are provided below.
//        PR : 1006.
//        AP : 3.
//        Switch : 5.
//        BR : 5(WAN IP setting), 6(LAN IP setting).
//        ORBI : 2(WAN IP setting), 10(LAN IP setting).

       
        String modifiedIp =ApiRequest.changeLastPartOfIp(WebportalParam.ap1IPaddress,224);
        String modifiedGateway =ApiRequest.changeLastPartOfIp(WebportalParam.ap1IPaddress,1);
           
            
        String requestBody = "{\"deviceInfo\":{\"command\":{\"type\":3,\"system\":{\"basicSettings\":{\"dhcpClientStatus\":\"0\",\"ipAddr\":\""+modifiedIp+"\",\"netmaskAddr\":\"255.255.255.0\",\"gatewayAddr\":\""+modifiedGateway+"\",\"priDnsAddr\":\"8.8.8.8\",\"sndDnsAddr\":\"4.2.2.1\",\"networkIntegralityCheck\":\"0\"}}}}}";
//        String requestBody = "{\"deviceInfo\":{\"command\":{\"type\":3,\"system\":{\"basicSettings\":{\"dhcpClientStatus\":\"0\",\"ipAddr\":\""+modifiedIp+"\",\"netmaskAddr\":\"255.255.240.0\",\"gatewayAddr\":\""+modifiedGateway+"\",\"priDnsAddr\":\"8.8.8.8\",\"sndDnsAddr\":\"4.2.2.1\",\"networkIntegralityCheck\":\"0\"}}}}}"

        
        //TO PERFORM ANY REQUEST
        Response getResponse = ApiRequest.sendPostRequest(endPointUrl.get("IP_Statistics"), requestBody, headers, pathParams, null); 
        getResponse.then().body("response.status", equalTo(true));
                           
    }

}
