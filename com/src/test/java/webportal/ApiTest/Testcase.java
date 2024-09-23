package webportal.ApiTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import testbase.TestCaseBase;
import webportal.weboperation.WirelessQuickViewPage;

import static io.restassured.RestAssured.*;


public class Testcase  {
    
    @Feature("Testing-API") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T007") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Testing the particular API for proper response") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN_T007") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        step1();
    }
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        
        System.out.println("start to do tearDown");
    }
    
    @Step("Test Step 1: Login IM WP success;")
    public void step1()
    {
        System.out.print("started----------------------");
        RestAssured.baseURI="https://pri-qa-api.insight.netgear.com/";
        String id1="64578dba6be50855bf7fbc63";
        String id2="6698c0e04008812c2eb7168d";
        System.out.println("Request URL: " + RestAssured.baseURI + "insightappcom/network/v1/locationDetails/" + id1 + "/" + id2);
        
        //TO PERFORM ANY REQUEST
        Response response = given()
                .log().all()
                .pathParam("Linkid1",id1)
                .pathParam("Linkid2",id2)
                .header("token","2_5PtQREdX5xTl6K1wPcnLWxfuawNAUVLJ4tCyt5Vlg9CuurXDs4Ckqi102y4PNYQBvtljCzD3Lkzjb39zAC8S0BpSRYWm937qzVb4w-EqJ-sRLPIelO8VGKLg7J5w-tMcaBzeT68oZ1kt75AsrEytz4Ug0-a0XMqZ96-FbUev00Tk")
                .header("x-xsrf-token","kdCRLErv5bgWxLloV6HzzSoqEbpxmeXWnUrT")
                .header("apikey","pP3dO6k6lf2N83UEWUjH480VUXhtqCNqa0g8ONeM")
                .header("accountId","64578dba6be50855bf7fbc64")
            .when()
                  .get("insightappcom/network/v1/locationDetails/{Linkid1}/{Linkid2}")
            .then()
                  .log().all()
                  .statusCode(200)
                  .header("Content-Type", "application/json")
                  .extract().response();
        
        response.prettyPrint();
        System.out.println("Request URL: " + RestAssured.baseURI + "/insightappcom/network/v1/locationDetails/" + id1 + "/" + id2);
                
    }

}
