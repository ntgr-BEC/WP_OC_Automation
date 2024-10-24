package webportal.ApiTest;
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

import static io.restassured.RestAssured.*;


public class Api_AddOrganization extends TestCaseBaseApi{

    
    @Feature("Api_AddOrganization") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T003") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Adds Orgaization") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T003") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
    }
  
    @Step("Send get request to {url}")
    public void step1()
    {

        RestAssured.baseURI=WebportalParam.baseURI;
        String aid1=WebportalParam.accountId;
        String responsebody="{\"mailto:orginfo\":{\"orgname\":\"rest1103\",\"ownername\":\"\",\"owneremail\":\"sudheer.duggisetty@netgear.com\",\"persPhnNo\":\"\",\"busiPhnNo\":\"\",\"emailRecipient\":[\"1\",\"2\"],\"pushRecipient\":[\"1\",\"2\"],\"deviceOwnership\":\"1\",\"repRecipient\":[\"1\",\"2\"],\"isSchedule\":\"1\",\"frequency\":\"1\",\"applyToAllOrg\":\"0\"}}";
        System.out.println("Request URL: " + RestAssured.baseURI + "insightappcom/api/network/v1/" + aid1);
        //TO PERFORM ANY REQUEST
        Response response = given()
                .pathParam("accountId",aid1)
                .header("token",WebportalParam.token)
                .header("x-xsrf-token",WebportalParam.xsrfToken)
                .header("apikey",WebportalParam.apikey)
                .body(responsebody)
            
            .when()
                  .post("insightappcom/api/organization/v1/Organzation/{accountId}")
            .then()
                  .log().all()
                  .statusCode(200)
                  .header("Content-Type", "application/json")
                  .extract().response();
        System.out.print("----------------------------------------");
        System.out.print(response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(),200,"status code did not match");
                
    }

}
