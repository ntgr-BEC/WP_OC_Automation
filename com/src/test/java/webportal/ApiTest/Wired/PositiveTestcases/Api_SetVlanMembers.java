package webportal.ApiTest.Wired.PositiveTestcases;
import static org.hamcrest.CoreMatchers.equalTo;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import testbase.TestCaseBaseApi;
import webportal.ApiTest.Devices.Api_GetDevices;
import webportal.ApiTest.Location.PositiveTestcases.Api_AddNetwork;
//import webportal.weboperation.WirelessQuickViewPage;
import webportal.param.WebportalParam;
import webportal.weboperation.ApiRequest;

import static io.restassured.RestAssured.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Api_SetVlanMembers extends TestCaseBaseApi{

    String networkId;
    String deviceId;
    Map<String, String> headers = new HashMap<String, String>();
    Map<String, String> endPointUrl = new HashMap<String, String>();
    Map<String, String> pathParams = new HashMap<String, String>();
    

    
    @Feature("Api_SetVlanMembers") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("This test Sets VLAN Mac Acl") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
        step2();
    }
    
    @AfterMethod(alwaysRun=true)
    public void teardown()
    {  

       Response getResponse = ApiRequest.sendDeleteRequest(endPointUrl.get("Vlan_Info"), headers, pathParams, null); 
       getResponse.then().body("response.status", equalTo(true));
    }
    

   @Step("Create a Vlan in the existing network")
   public void step1()
   {
       endPointUrl = new ApiRequest().ENDPOINT_URL;
       
       Map<String, String> pathParams1 = new HashMap<String, String>();
       pathParams1.put("networkId",WebportalParam.networkId);
       
       
       headers.put("token",WebportalParam.token);
       headers.put("apikey",WebportalParam.apikey);
       headers.put("accountId",WebportalParam.accountId);     
       
       
       String requestBody = "{\"vlan\":{\"name\":\"VLAN_250\",\"vlanId\":\"250\",\"trafficClass\":\"0\",\"voipOptimization\":\"0\",\"igmpSnooping\":\"0\",\"overrideTrafficPriority\":\"0\",\"qosConfig\":\"Data\",\"vlanType\":\"6\",\"vlanNwName\":\"VLAN_250\",\"vlanNwDesc\":\"VLAN_250\"}}";

       Response getResponse = ApiRequest.sendPostRequest(endPointUrl.get("Vlan"), requestBody, headers, pathParams1, null); 
       getResponse.then().body("response.status", equalTo(true));
   }
    
    
    
    @Step("Send get request to {url}")
    public void step2()
    {
        endPointUrl = new ApiRequest().ENDPOINT_URL;
        Response response1=new Api_GetDevices().step1();
        deviceId=response1.jsonPath().getString("deviceInfo[0].deviceId");
        
        String vlanId="250";

        
        pathParams.put("networkId",WebportalParam.networkId);
        pathParams.put("vlanId",vlanId);
        
        String body="{\"vlanMembers\":{\"memberWirelessNetwork\":[],\"apPortMembers\":[],\"portMembers\":[{\"deviceId\":\""+deviceId+"\",\"taggedPorts\":[],\"untaggedPorts\":[\"15\"]}],\"lanPortMembers\":[],\"wanPortMembers\":[],\"publicMembers\":[],\"employeeMembers\":[],\"guestMembers\":[],\"lagMembers\":{\"tagged\":[],\"untagged\":[],\"portGrp\":[]}}}";
        

        //TO PERFORM ANY REQUEST
     
        Response getResponse = ApiRequest.sendPostRequest(endPointUrl.get("Set_VlanMembers"),body, headers, pathParams, null); 
        getResponse.then().body("response.status", equalTo(true))
                          .body("response.message", equalTo("Success"));
                               
    }

}
