package webportal.Onboard_recursive.PRJCBUGEN_T14171;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.APUtils;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.weboperation.AccountPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WirelessQuickViewPage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Ravishankar S
 *
 */
public class Testcase extends TestCaseBase {
    
    private static final Logger logger = LoggerFactory.getLogger(Testcase.class);
    int dim;

    @Feature("DeviceOnBoarding from one premium account to one Premium account") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14012") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Add one device to premium account and delete and onboard to one more premium account") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14012") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        
        System.out.println("start to do tearDown");
    }

    // Each step is a single test step from Jira Test Case      
            @Step("Test Step 1: Login IM WP success;")
            public void step1() {
                int Iteration = 0, Fail = 0;
//                for (int i = 0; i < 100; i++) {
                    while(true) {
                    WebportalLoginPage webportalLoginPage = null;
                    try {
                        
                        System.out.printf("Status of Device onboard : Fail<%s>/Total<%s>\n", Fail, Iteration);   
                        webportalLoginPage = new WebportalLoginPage(true);
                        webportalLoginPage.loginByUserPassword(WebportalParam.loginName, WebportalParam.loginPassword);
                        dim=0;
                        handle.gotoLoction();
                        MyCommonAPIs.sleepi(5);

                      logger.info("Add device in the location and Verify device status");
                        new DevicesDashPage().AddNewDevice(WebportalParam.ap1serialNo, WebportalParam.ap1macaddress);
                        MyCommonAPIs.sleepi(60);
                        assertTrue(new WirelessQuickViewPage().checkApIsExist(WebportalParam.ap1serialNo),"Device delete unsuccessful");
                        assertTrue(new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo),"Connected Status Not visible");

                        logger.info("Remove the device and Logout of the existing");
                        new DevicesDashPage().deleteDeviceNo(WebportalParam.ap1serialNo);
                        MyCommonAPIs.sleepi(5);
                        assertFalse(new WirelessQuickViewPage().checkApIsExist(WebportalParam.ap1serialNo),"Device delete unsuccessful");
                        UserManage userManage = new UserManage();
                        userManage.logout();

                        logger.info("Add device to one more premium account");
                        webportalLoginPage.loginByUserPassword(WebportalParam.loginName1, WebportalParam.loginPassword1);
                        dim=1;
                        handle.gotoLoction();
                        MyCommonAPIs.sleepi(5);

                        logger.info("Add device in the location and Verify device status");
                        new DevicesDashPage().AddNewDevice(WebportalParam.ap1serialNo, WebportalParam.ap1macaddress);
                        MyCommonAPIs.sleepi(60);
                        assertTrue(new WirelessQuickViewPage().checkApIsExist(WebportalParam.ap1serialNo),"Device delete unsuccessful");
                        assertTrue(new DevicesDashPage().waitDevicesReConnected(WebportalParam.ap1serialNo));

                        logger.info("Remove the device and Logout of the existing");
                        new DevicesDashPage().deleteDeviceNo(WebportalParam.ap1serialNo);
                        MyCommonAPIs.sleepi(5);
                        assertFalse(new WirelessQuickViewPage().checkApIsExist(WebportalParam.ap1serialNo),"Device delete unsuccessful");

                        userManage.logout();
                        Iteration++;

                    } catch (Throwable e) {
                        Fail++;
                        logger.error("An error occurred during the onboarding or removal process", e);
                          
                            if(new WirelessQuickViewPage().checkApIsExist(WebportalParam.ap1serialNo)){
                               new WirelessQuickViewPage().deleteDeviceYes(WebportalParam.ap1serialNo);
                               }       
                                UserManage userManage = new UserManage();
                                userManage.logout();
                                if(dim==0)
                                {   System.out.print("Issue in account 2");
                                     System.out.print(dim);
                                    webportalLoginPage.loginByUserPassword(WebportalParam.loginName1, WebportalParam.loginPassword1);
                                    handle.gotoLoction();
                                    if(new WirelessQuickViewPage().checkApIsExist(WebportalParam.ap1serialNo)){
                                        new WirelessQuickViewPage().deleteDeviceYes(WebportalParam.ap1serialNo);
                                    }                                    
                                    userManage.logout();
                                 }
                                else
                                {
                                    System.out.print("Issue in account 2");
                                    System.out.print(dim);
                                    webportalLoginPage.loginByUserPassword(WebportalParam.loginName, WebportalParam.loginPassword);
                                    handle.gotoLoction();
                                    if(new WirelessQuickViewPage().checkApIsExist(WebportalParam.ap1serialNo)){
                                        new WirelessQuickViewPage().deleteDeviceYes(WebportalParam.ap1serialNo);
                                    }
                                    userManage.logout();
                                }
                                    
                                
                            } 
                        }
                    }
                }

            
        



