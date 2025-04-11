package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.google.common.io.Files;

import util.APUtils;
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.CommonDataType;
import webportal.param.CommonDataType.SSIDData;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.WirelessQuickViewElement;
import java.util.Random;
import org.openqa.selenium.interactions.Actions;
import util.*;


public class PostManPage  extends MyCommonAPIs{

    public PostManPage() {       
        logger.info("into Postman...");
    }

    public void Deregister(String SLNo) {    
        
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Create the HTTP POST request
            HttpPost postRequest = new HttpPost("https://ocapi-qa.netgear.com/api/v2/ocProductRegisterDeactivate/?accessToken=eyJraWQiOiJvc2FqOVNyemR1SWgrUTFRa3NqcTZpZU9EU25PYXFyQnY5ekFVV3pkT2lNPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiIwNDA4ODRhOC0zMGQxLTcwMmUtNzFmYy1kOWE4OTI4NjY0YzQiLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtZWFzdC0xLmFtYXpvbmF3cy5jb21cL3VzLWVhc3QtMV9KYUdVeVB1Y3MiLCJjbGllbnRfaWQiOiI3amI5MmNuZm9iMTFpOTVvdDQ4bmdqZXNlOCIsIm9yaWdpbl9qdGkiOiI4Njc4YmE4My1iOTY2LTQ1NjctYjM5My1jMGU3NjQzNTk0MjgiLCJldmVudF9pZCI6IjFhMTMwM2MyLWFmODItNGU0OS1hMDFlLTQzZWI0MzYzNWM3ZSIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoiYXdzLmNvZ25pdG8uc2lnbmluLnVzZXIuYWRtaW4iLCJhdXRoX3RpbWUiOjE3NDQzNTI0NTUsImV4cCI6MTc0NDM1MzkwMiwiaWF0IjoxNzQ0MzUzMDAyLCJqdGkiOiI4OGM5MzUzOC1iZDk1LTQyMDEtOGMwZi01YjQwY2ViNWY4ZmUiLCJ1c2VybmFtZSI6IjA0MDg4NGE4LTMwZDEtNzAyZS03MWZjLWQ5YTg5Mjg2NjRjNCJ9.kUnL55YbTeDbtXg_4dFlBRyYgT4jmw0U6J3aMvurHRWf_dI58TVF3Ekqu2CO_g3LCCsLseOsU0x2DBa1aqgtwolaMYXSKEI8Q4MNOR1bFebclnYyBoNfGWJtVlLo_Y22R69EHJlzY7CaKLeOGIJJMJSOhl3Q6BL5-8Xm20MoxP1GtsrkOHil_2RWstOGK7t23bYAA8Wjy9IFNeojz8Wpc6zmfoLqaXMOpvieh-DHQE2krSv6kalzbrTNtu4A8G6uC7YNjqg1Fcaz1AH7htQaEcMrRP_bNuTcZH9pVbW_xDoTHvPk-ydYZp6r-0JdHCEom_QRRPiAsC570R4dVMegbg");

            // Add headers
            postRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
            postRequest.setHeader("x-dreamfactory-api-key", "175208f843081dc7d9addad1e372c51c04c4c3dd54834a500d85955f71742a34");

            // Create form parameters
            List<NameValuePair> urlParameters = new ArrayList<>();
            urlParameters.add(new BasicNameValuePair("serialNumber", SLNo));

            // Set the request entity
            postRequest.setEntity(new UrlEncodedFormEntity(urlParameters));
           
            // Execute the request
            try (org.apache.hc.client5.http.impl.classic.CloseableHttpResponse response = httpClient.execute(postRequest)) {
                // Print the response status
                System.out.println("Response Status: " + response.getCode());

                // Print the response body
                String responseBody ="";
                try {
                    responseBody = EntityUtils.toString(response.getEntity());
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("Response Body: " + responseBody);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    
    }

     