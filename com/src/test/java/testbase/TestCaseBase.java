package testbase;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.testng.ScreenShooter;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import util.LoggerPrintStream;
import util.MyCommonAPIs;
import util.RunCommand;
import util.SwitchTelnet;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.weboperation.CustomReportPage;
import webportal.weboperation.DeviceBRClientPage;
import webportal.weboperation.DeviceBRDHCPServersPage;
import webportal.weboperation.DeviceBRIPSecVpnPage;
import webportal.weboperation.DeviceBRStatisticsPage;
import webportal.weboperation.DeviceBRSummaryPage;
import webportal.weboperation.DeviceBRVPNGroupsPage;
import webportal.weboperation.DeviceBRVPNUsersPage;
import webportal.weboperation.DeviceBRVlansPage;
import webportal.weboperation.DeviceBRWanIPPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.DevicesDashPageMNG;
import webportal.weboperation.DevicesSwitchStaticRoutingPage;
import webportal.weboperation.EventPage;
import webportal.weboperation.FirmwarePage;
import webportal.weboperation.FirmwarePolicyPage;
import webportal.weboperation.LocationRoutingPage;
import webportal.weboperation.NetworkSetupPage;
import webportal.weboperation.RadiusConfigurationPage;
import webportal.weboperation.RadiusPage;
import webportal.weboperation.RoutersPage;
import webportal.weboperation.SnmpPage;
import webportal.weboperation.SyslogPage;
import webportal.weboperation.WiredGroupPortConfigPage;
import webportal.weboperation.WiredLAGPage;
import webportal.weboperation.WiredPoESchedulesPage;
import webportal.weboperation.WiredQuickViewPage;
import webportal.weboperation.WiredSpanningTreePage;
import webportal.weboperation.WiredVLANPage;
import webportal.weboperation.WirelessQuickViewPage;
import webportal.weboperation.WiredDhcpRelayPage;
import webportal.weboperation.WiredDhcpSnoopingPage;

@Listeners({ ScreenShooter.class })
public class TestCaseBase {
    public boolean                               bIsTCFail;
    public Logger                                logger         = Logger.getLogger("root");
    public boolean                               micResult      = false;
    public static boolean                        toRestart      = false;
    public static MyCommonAPIs                   handle         = new MyCommonAPIs();
    public static WebportalParam                 webportalParam = new WebportalParam();
    public static int                            iFailCount     = 0;
    public static int                            iTotalCount    = 0;
    public static String                         testcaseName   = "";
    public static String                         testcaseId     = "";
    public static WiredGroupPortConfigPage       wgpcp          = new WiredGroupPortConfigPage(true);
    public static WiredLAGPage                   wlp            = new WiredLAGPage(true);
    public static WiredVLANPage                  wvp            = new WiredVLANPage();
    public static WiredQuickViewPage             wqvp           = new WiredQuickViewPage(true);
    public static WiredPoESchedulesPage          wpsp           = new WiredPoESchedulesPage(true);
    public static WiredSpanningTreePage          wstp           = new WiredSpanningTreePage();
    public static EventPage                      evtp           = new EventPage();
    public static LocationRoutingPage            rtp            = new LocationRoutingPage();
    public static DevicesSwitchStaticRoutingPage dssrp          = new DevicesSwitchStaticRoutingPage();
    public static RadiusPage                     rdp            = new RadiusPage();
    public static RadiusConfigurationPage        rcp            = new RadiusConfigurationPage();
    public static FirmwarePolicyPage             fmpp           = new FirmwarePolicyPage();
    public static FirmwarePage                   fmp            = new FirmwarePage();
    public static NetworkSetupPage               netsp          = new NetworkSetupPage();
    public static SnmpPage                       snmpp          = new SnmpPage();
    public static SyslogPage                     sysp           = new SyslogPage();
    public static DeviceBRSummaryPage            brdsp          = new DeviceBRSummaryPage();
    public static RoutersPage                    brrp           = new RoutersPage();
    public static DeviceBRClientPage             brdcp          = new DeviceBRClientPage();
    public static DeviceBRIPSecVpnPage           bripsvp        = new DeviceBRIPSecVpnPage();
    public static DeviceBRDHCPServersPage        brddchps       = new DeviceBRDHCPServersPage();
    public static DeviceBRStatisticsPage         brdstp         = new DeviceBRStatisticsPage();
    public static DeviceBRVPNGroupsPage          brdvgp         = new DeviceBRVPNGroupsPage();
    public static DeviceBRVPNUsersPage           brdvup         = new DeviceBRVPNUsersPage();
    public static DeviceBRWanIPPage              brdwip         = new DeviceBRWanIPPage();
    public static DeviceBRVlansPage              brdvp          = new DeviceBRVlansPage();
    public static DevicesDashPage                ddp            = new DevicesDashPage(true);
    public static DevicesDashPageMNG             ddpmg          = new DevicesDashPageMNG(true);
    public static WirelessQuickViewPage          wlqvp          = new WirelessQuickViewPage(true);
    public static CustomReportPage               crPage         = new CustomReportPage();
    public static WiredDhcpRelayPage             wdrp           = new WiredDhcpRelayPage();
    public static WiredDhcpSnoopingPage          wdsp           = new WiredDhcpSnoopingPage();

    // for orbi
    public static int orbiMode = 99; // 0 - router, 1 - ap
    
    static DriverLog                 eventLog           = new DriverLog();
    static EventFiringWebDriver      eventWebDriver     = null;
    static boolean                   eventListenerAdded = false;
    static boolean                   eventListenerReged = false;
    public static Comparator<Method> compareStep        = new Comparator<Method>() {
                                                            @Override
                                                            public int compare(Method m1, Method m2) {
                                                                int i1 = Integer.parseInt(m1.getName().replace("step", ""));
                                                                int i2 = Integer.parseInt(m2.getName().replace("step", ""));
                                                                if (i1 > i2)
                                                                    return 1;
                                                                else
                                                                    return -1;
                                                            }
                                                        };
    public static Comparator<Method> compareSanityStep  = new Comparator<Method>() {
                                                            @Override
                                                            public int compare(Method m1, Method m2) {
                                                                String sm1 = m1.getName();
                                                                String sm2 = m2.getName();
                                                                int i1 = Integer.parseInt(sm1.substring(sm1.length() - 1));
                                                                int i2 = Integer.parseInt(sm2.substring(sm2.length() - 1));
                                                                if (i1 > i2)
                                                                    return 1;
                                                                else
                                                                    return -1;
                                                            }
                                                        };
    
    public void initdata(Object obj) {
        handle.webportalParam = webportalParam;
        try {
            testcaseId = testcaseName.substring(testcaseName.indexOf("_T") + 2);
            testcaseId = testcaseId.substring(0, testcaseId.indexOf("."));
        } catch (Throwable e) {
            testcaseId = RandomStringUtils.randomNumeric(5);
        }
        handle.setCheckPointStep(0);
        wvp.initTestData();
        wpsp.initTestData();
        fmpp.initTestData();
        netsp.initTestData();
        bripsvp.initTestData();
        wlqvp.initTestData();
        crPage.initTestData();
    }
    
    /**
     * @param  obj
     * @throws Exception
     *                   Used for 2 test cases have same steps
     */
    public void runParentTest(Object obj) throws Exception {
        runTest(obj.getClass().getSuperclass().newInstance());
    }
    
    public void runStepTest(Object obj) {
        initdata(obj);
        ArrayList<Method> ms = new ArrayList<Method>();
        for (Method m : obj.getClass().getDeclaredMethods()) {
            if (Pattern.matches("test_.+", m.getName())) {
                ms.add(m);
            }
        }
        try {
            String stepName = ms.get(0).getName();
            Integer.parseInt(stepName.substring(stepName.length() - 1));
            Collections.sort(ms, compareSanityStep);
        } catch (Throwable e) {
            // no sort
        }
        for (Method m : ms) {
            System.out.println(String.format("@@@@@@@@@@@@@@@@@@@@ Step Info(%s) @@@@@@@@@@@@@@@@@@@@", m.getName()));
            try {
                m.invoke(this);
                WebportalParam.noStepPass++;
                System.out.println(String.format("==========> %s: %s <==========", WebportalParam.resultPass, m.getName()));
            } catch (Exception e) {
                WebportalParam.noStepFail++;
                System.out.println(String.format("==========> %s: %s <==========", WebportalParam.resultFail, m.getName()));
                Allure.addAttachment(m.getName(), new ByteArrayInputStream(WebportalParam.takeScreenShot()));
                e.printStackTrace();
            }
        }
    }
    
    public void runTest(Object obj) throws Exception {
        initdata(obj);
        ArrayList<Method> ms = new ArrayList<Method>();
        for (Method m : obj.getClass().getDeclaredMethods()) {
            if (Pattern.matches("step\\d+", m.getName())) {
                ms.add(m);
            }
        }
        Collections.sort(ms, compareStep);
        for (Method m : ms) {
            String sInfo = "-";
            try {
                Step ss = m.getAnnotation(Step.class);
                if (ss != null) {
                    sInfo = ss.value();
                }
            } catch (Throwable e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            System.out.println(String.format("@@@@@@@@@@@@@@@@@@@@ Step(%s) Info(%s) @@@@@@@@@@@@@@@@@@@@", m.getName(), sInfo));
            try {
                bIsTCFail = false;
                m.invoke(this);
            } catch (Exception e) {
                iFailCount += 1;
                bIsTCFail = true;
                System.out.println(String.format("@@@@@@@@@@@@@@@@@@@@ Steps Done(Fail): %s-%s @@@@@@@@@@@@@@@@@@@@", m.getName(), sInfo));
                e.printStackTrace();
                if (!WebportalParam.enableBatch)
                    throw e;
            }
        }
        System.out.println("@@@@@@@@@@@@@@@@@@@@ Steps Done(Pass), Go to tearDown & Stop @@@@@@@@@@@@@@@@@@@@");
    }
    
    static WebDriver startRemoteBrowser(URL url, Capabilities capabilities) {
        WebDriver webDriver;
        boolean isRestarted = false;
        String sCurURL = "faked";
        boolean bOpenLogin = false;
        try {
            if (WebportalParam.curWebDriver != null)
                return WebportalParam.curWebDriver;
            webDriver = new RemoteWebDriver(url, capabilities);
            try {
                sCurURL = webDriver.getCurrentUrl();
                if (sCurURL.contains("chrome not reachable"))
                    throw new RuntimeException(sCurURL);
            } catch (Throwable e) {
                e.printStackTrace();
                isRestarted = true;
                System.out.println("dead sesssion found");
                try {
                    webDriver.quit();
                } catch (Throwable ee) {
                    ee.printStackTrace();
                    System.out.println("dead sesssion should be killed");
                }
                webDriver = new RemoteWebDriver(url, capabilities);
                bOpenLogin = true;
            }
            
            if (sCurURL.endsWith("locked") || (!isRestarted && toRestart && WebportalParam.enableRemote && !WebportalParam.enableDebug)) {
                toRestart = false;
                webDriver.quit();
                webDriver = new RemoteWebDriver(url, capabilities);
                bOpenLogin = true;
                System.out.println("we have restarted browser again");
            } else {
                System.out.println("we have an opening browser now");
            }
        } catch (Throwable e) {
            System.out.println("try one more time to start...");
            e.printStackTrace();
            webDriver = new RemoteWebDriver(url, capabilities);
            bOpenLogin = true;
        }
        if (bOpenLogin) {
            System.out.println("let we go to login page in more than 300s:" + WebportalParam.serverUrlLogin);
            boolean isPassed = true;
            for (int i = 0; i < 5; i++) {
                try {
                    webDriver.get(WebportalParam.serverUrlLogin);
                    break;
                } catch (Throwable e) {
                    isPassed = false;
                    System.out.println("failed to load login page, ignore once");
                }
            }
            assert (isPassed);
        }
        return webDriver;
    }
    
    public static String getlocale() {
        String locale = "--lang=en_US";
        if (WebportalParam.BrowserLanguage.equalsIgnoreCase("jp")) {
            locale = "--lang=ja_JP";
        }
        if (WebportalParam.BrowserLanguage.equalsIgnoreCase("de")) {
            locale = "--lang=de_DE";
        }
        if (WebportalParam.BrowserLanguage.equalsIgnoreCase("fr")) {
            locale = "--lang=fr-FR";
        }
        if (WebportalParam.BrowserLanguage.equalsIgnoreCase("es")) {
            locale = "--lang=es-ES";
        }
        if (WebportalParam.BrowserLanguage.equalsIgnoreCase("it")) {
            locale = "--lang=it-IT";
        }
        if (WebportalParam.BrowserLanguage.equalsIgnoreCase("cn")) {
            locale = "--lang=zh-CN";
        }
        return locale;
    }
    
    /**
     * kill driver process and set browser
     */
    @BeforeClass(alwaysRun = true)
    public void beforeClassWrap() throws MalformedURLException {
        // if (GlobalParam.BrowserType.equalsIgnoreCase("chrome")) {
        // Command command = new Command();
        // command.killProcess("chromedriver.exe");
        // } else if (GlobalParam.BrowserType.equalsIgnoreCase("firefox")) {
        // Command command = new Command();
        // command.killProcess("geckodriver.exe");
        // } else if (GlobalParam.BrowserType.equalsIgnoreCase("iexplore")) {
        // Command command = new Command();
        // command.killProcess("IEDriverServer.exe");
        // }
        
        System.setOut(new LoggerPrintStream(System.out));
        Configuration.reopenBrowserOnFail = true;
        Configuration.timeout = 35000;
        Configuration.pollingInterval = 2000;
        testcaseName = this.getClass().getName();
        MyCommonAPIs.testcaseName = testcaseName;
        System.out.println("#################### Run TestCase: " + testcaseName);
        try {
            beforeClass();
        } catch (Throwable e) {
            System.out.println("ERROR: setUp for testcase is failed, check env first.");
            e.printStackTrace();
            beforeClass();
        }
    }
    
    static void stopBrowserForce(boolean tokill) {
        if (tokill) {
            System.out.println("DEBUG: stopping browser");
            WebportalParam.curWebDriver.quit();
        }
        WebportalParam.curWebDriver = null;
        eventListenerAdded = false;
        eventListenerReged = false;
    }
    
    static void stopBrowser() {
        try {
            WebportalParam.curWebDriver.getCurrentUrl();
        } catch (Throwable e) {
            System.out.println("DEBUG: Browser was invalid or non-existed");
            stopBrowserForce(false);
            return;
        }
        if (WebportalParam.browserIsFailed || (!WebportalParam.enableDebug && !WebportalParam.enaReuseBrowser)) {
            if (WebportalParam.curWebDriver != null) {
                stopBrowserForce(true);
            } else {
                System.out.println("browser was stopped");
            }
        } else {
            System.out.println("browser is kept");
        }
    }
    
    public static void startBrowser() {
        stopBrowser();
        if (!eventListenerAdded) {
            eventListenerAdded = true;
            WebDriverRunner.addListener(eventLog);
        }
        if (WebportalParam.enableRemote) {
            DesiredCapabilities capabilities;
            String locale = getlocale();
            URL sURL;
            try {
                sURL = new URL("http://" + WebportalParam.SeleniumSvr.trim() + "/wd/hub");
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                System.out.printf("Check url: <%s>\n", WebportalParam.SeleniumSvr);
                return;
            }
            if (WebportalParam.BrowserType.toLowerCase().contains("chrome")) {             
                ChromeOptions options = new ChromeOptions();
                HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
                chromePrefs.put("credentials_enable_service", false);
                chromePrefs.put("password_manager_enabled", false);
                chromePrefs.put("profile.default_content_settings.popups", 0);
                chromePrefs.put("credentials_enable_service", false);
                chromePrefs.put("profile.password_manager_enabled", false);
                            
               
                options.setExperimentalOption("credentials_enable_service", false);
                options.setExperimentalOption("profile.password_manager_enabled", false);
                options.addArguments(locale);
                options.addArguments("start-maximized");
                options.addArguments("--disable-default-apps");
                options.addArguments("--ignore-certificate-errors");
                options.addArguments("−−lang=fr");
                options.setExperimentalOption("prefs", chromePrefs);
                options.setExperimentalOption("w3c", false);
                options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
                
                
                if ((WebportalParam.BrowserPath.length() > 10) && new File(WebportalParam.BrowserPath).exists()) {
                    options.setBinary(WebportalParam.BrowserPath);
                }
                if (WebportalParam.enableHeadless) {
                    options.addArguments("--window-size=1400x900");
                    options.setHeadless(WebportalParam.enableHeadless);
                }
                if (WebportalParam.enableProxy) {
                    options.addArguments("--proxy-server=" + WebportalParam.ProxySvr);
                }
                capabilities = DesiredCapabilities.chrome();
                capabilities.setBrowserName("chrome");
                capabilities.setCapability(ChromeOptions.CAPABILITY, options);
                capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                capabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
            } else {
                FirefoxOptions options = new FirefoxOptions();
                options.addArguments(locale);
                capabilities = DesiredCapabilities.firefox();
                capabilities.setBrowserName("firefox");
                capabilities.setCapability("marionette", false);
                capabilities.setCapability(CapabilityType.PROXY, new Proxy().setHttpProxy(WebportalParam.ProxySvr.replace("http://", "")));
                
                capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);
            }
            
            WebportalParam.curWebDriver = startRemoteBrowser(sURL, capabilities);
            if (WebportalParam.enableDebug) {
                Configuration.timeout = 10000;
                WebportalParam.curWebDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            } else {
                WebportalParam.curWebDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            }
            
            WebportalParam.curWebDriver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
            WebportalParam.curWebDriver.manage().timeouts().setScriptTimeout(300, TimeUnit.SECONDS);
        } else {
            if (WebportalParam.curWebDriver == null) {
                if (WebportalParam.BrowserType.equalsIgnoreCase("chrome")) {
                    Configuration.browser = "chrome";
                    System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\chromedriver.exe");
                    String locale = getlocale();
                    ChromeOptions options = new ChromeOptions();
                    HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
//                    chromePrefs.put("download.default_directory", "D:\\downTeju");
                    options.setExperimentalOption("prefs", chromePrefs);
                    options.addArguments(locale);
                    if ((WebportalParam.BrowserPath.length() > 10) && new File(WebportalParam.BrowserPath).exists()) {
                        options.setBinary(WebportalParam.BrowserPath);
                    }
                    options.addArguments("start-maximized");
                    options.addArguments("--disable-infobars");
                    options.addArguments("--ignore-certificate-errors");
//                    options.setExperimentalOption("excludeSwitches", "disable-popup-blocking");
                    options.setHeadless(WebportalParam.enableHeadless);
                    if (WebportalParam.enableProxy) {
                        options.addArguments("--proxy-server=" + WebportalParam.ProxySvr);
                    }
                    WebportalParam.curWebDriver = new ChromeDriver(options);
                } else if (WebportalParam.BrowserType.equalsIgnoreCase("firefox")) {
                    Configuration.browser = "gecko";
                    System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\geckodriver.exe");
                    String locale = getlocale();
                    //FirefoxOptions options = new FirefoxOptions();
                    //options.addArguments(locale);
                    //FirefoxProfile ffProfile = options.getProfile();
                    //ffProfile.setAcceptUntrustedCertificates(true);
                    //ffProfile.setAssumeUntrustedCertificateIssuer(false);
                    FirefoxProfile profile = new FirefoxProfile();
                    profile.setAssumeUntrustedCertificateIssuer(true);
                    profile.setAcceptUntrustedCertificates(false);

                    FirefoxOptions options = new FirefoxOptions();
                    //options.setProfile(profile);
                    options.addArguments(locale);
                    WebportalParam.curWebDriver = new FirefoxDriver(options);
                } else if (WebportalParam.BrowserType.equalsIgnoreCase("iexplore")) {
                    Configuration.browser = "ie";
                    System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\IEDriverServer.exe");
                    InternetExplorerOptions explorerOptions = new InternetExplorerOptions();
                    explorerOptions.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                    explorerOptions.setCapability("ignoreZoomSetting", true);
                    explorerOptions.setCapability("requireWindowFocus", true);
                    WebportalParam.curWebDriver = new InternetExplorerDriver(explorerOptions);
                }
            }
        }
        
        if (!eventListenerReged) {
            eventListenerReged = true;
            eventWebDriver = new EventFiringWebDriver(WebportalParam.curWebDriver);
            eventWebDriver.register(eventLog);
            WebDriverRunner.setWebDriver(eventWebDriver);
        }
        WebportalParam.curWebDriver = WebDriverRunner.getWebDriver();
        System.out.printf("Current URL: %s\n", WebportalParam.curWebDriver.getCurrentUrl());
        WebportalParam.browserIsFailed = false;
    }
    
    void beforeClass() {
        iTotalCount += 1;
        try {
            Method mm = this.getClass().getMethod("test");
            Description dd = mm.getAnnotation(Description.class);
            if (dd != null) {
                System.out.println("TestCase Summary: " + dd.value());
            }
        } catch (NoSuchMethodException | SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        System.setProperty("selenide.browser", "Chrome");
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\chromedriver.exe");
        Configuration.startMaximized = true;
        startBrowser();
    }
    
    @Attachment(type = "text/uri-list")
    public String addConsoleLog() {
        String sName = this.getClass().getName();
        String sTo = "Switch.";
        if (sName.contains("LongRunStability.")) {
            sTo = "LongRunStability.";
        }
        if (sName.contains("BR.")) {
            sTo = "BR.";
        }
        
        String sEnd = ".Testcase";
        int iStart = sName.indexOf(sTo);
        int iEnd = sName.indexOf(sEnd);
        String sTcName;
        if ((iEnd > iStart) && (iStart > 0)) {
            sTcName = sName.substring(iStart, iEnd);
            return String.format("../com$webortal/testReport/webportal.%s/Testcase/test/", sTcName);
        } else
            return "NOT SUPPORT";
    }
    
    @BeforeSuite(alwaysRun = true)
    public void BeforeSuite(ITestContext context) {
        toRestart = true;
        System.out.println("Start BeforeSuite");
    }
    
    @AfterSuite(alwaysRun = true)
    public void AfterSuite(ITestContext context) {
        System.out.println("Start AfterSuite");
        WebportalParam.enaReuseBrowser = false;
        stopBrowser();
        new RunCommand().SendPostSanityMail();
    }
    
    @AfterClass(alwaysRun = true)
    public void afterClassWrap(ITestContext context) {
        System.out.println(String.format("#################### Stop TestCase(%s): %s", bIsTCFail ? "Fail" : "Pass", this.getClass().getName()));
        try {
            afterClass(context);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    void afterClass(ITestContext context) {
        int iFail = context.getFailedTests().size();
        System.out.println(String.format("Testcase Count: %d(P)/%d(F)/%d(Total), CurFeature Fail: %d", iTotalCount - iFailCount, iFailCount,
                iTotalCount, iFail));
        if (!WebportalParam.enableDebug) {
            try {
                if (!WebportalParam.enaReuseBrowser) {
                    UserManage userManage = new UserManage();
                    userManage.logout();
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
            
            if (bIsTCFail) {
                try {
                    if (testcaseName.contains(".Switch.") && !testcaseName.contains("PRJCBUGEN_T4687.")) {
                        if (RunCommand.isHostAlive(WebportalParam.sw1IPaddress, 80)) {
                            handle.getCmdOutputShowLogging(false);
                            MyCommonAPIs.getCmdOutput("clear logging buffered", false);
                            handle.getCmdOutputShowRunningConfig(false);
                        }
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
            
            stopBrowser();
            if ((SwitchTelnet.thrMonitor != null) && (SwitchTelnet.telnet != null)) {
                try {
                    SwitchTelnet.isTelnetStart = false;
                    SwitchTelnet.thrMonitor.join();
                    SwitchTelnet.telnet.distinct();
                } catch (Throwable e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    SwitchTelnet.thrMonitor = null;
                }
            }
        }
        addConsoleLog();
    }
    
    //Code Added By Pratik for Premium to Pro Subscription
    
/*    WebDriver webDriver;
    public void mailinatorWebsiteEmailVerification () {
        webDriver.get("https://www.mailinator.com/");
        MyCommonAPIs.sleepi(5); 
    }*/
}
