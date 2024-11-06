package webportal.ApiTest.Wired.PositiveTestcases;
import static org.hamcrest.CoreMatchers.equalTo;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Api_SetVlanIpAclSettings extends TestCaseBaseApi{

    String networkId;
    Map<String, String> headers = new HashMap<String, String>();
    Map<String, String> endPointUrl = new HashMap<String, String>();

    
    @Feature("Api_SetVlanIpAclSettings") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("This test creates sets Vlan IP ACL setting of the network") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T001") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
    }
    
//    @AfterMethod(alwaysRun=true)
//    public void teardown()
//    {  
//        Map<String, String> pathParams = new HashMap<String, String>();
//       pathParams.put("networkId",networkId);    
//        Response getResponse1 = ApiRequest.sendDeleteRequest(endPointUrl.get("Network_Sanity"), headers, pathParams, null); 
//        getResponse1.then().body("response.status", equalTo(true));
//    }
  
    @Step("Send get request to {url}")
    public List<Response> step1()
    {
        List <Response> response = new Api_VlanListing().step1();
        Response addNetwork=response.get(0);
        Response addVlan=response.get(1);

        networkId=addNetwork.jsonPath().getString("networkInfo[0].networkId"); 
        String vlanId=addVlan.jsonPath().getString("vlanConfig[-1].vlanId");
        System.out.print("---------------------"+vlanId);
        endPointUrl = new ApiRequest().ENDPOINT_URL;

        headers.put("token",WebportalParam.token);
        headers.put("apikey",WebportalParam.apikey);
        headers.put("accountId",WebportalParam.accountId);     
        
        Map<String, String> pathParams = new HashMap<String, String>();
        pathParams.put("networkId",networkId);
        pathParams.put("vlanId",vlanId);

        String requestBody="{\"ipAclList\":{\"mode\":\"1\",\"action\":\"0\",\"ipAclRule\":{\"manual\":[{\"deviceName\":\"Test123\",\"ipAddress\":\"192.17.2.1\",\"networkMask\":\"0.0.0.0\",\"accessDirection\":\"2\"}],\"custom\":[]}}}";
        //TO PERFORM ANY REQUEST
     
        Response getResponse = ApiRequest.sendPostRequest(endPointUrl.get("IpAcl_Sanity"), requestBody, headers, pathParams, null); 
        getResponse.then().body("response.status", equalTo(true));
        
        return response;
        
                
    }

}
