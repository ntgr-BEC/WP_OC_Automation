package testbase;

import org.testng.annotations.BeforeSuite;

import webportal.param.WebportalParam;

public class TestCaseBaseApi {
    protected static WebportalParam webportalParam;
    
    @BeforeSuite
    public void setupWebPortalParam() {
        if(webportalParam == null)
        {
            webportalParam = new WebportalParam();
            System.out.print("XML Initialized");
        }
    }
    
  
}