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
            HttpPost postRequest = new HttpPost("https://ocapi-qa.netgear.com/api/v2/ocProductRegisterDeactivate/?accessToken=2_5ls7XaGJ2FxrO1IySnLuPvOr6-0-n1DnhL0zprIrYow6kt6K-IpcNVmRKRrfd6mLEOjkTDmYd9A4F5vtXPJlHj9YCHEvZru2PXOyBeZ5TF5Vn0k8GtsqSTGM9zKF25CO_UKE7jlG-KEsmJi5zbVXPE7r_5x5Rt7NI4Jpi9YyiX1E");

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

     