package businessrouter.testbase;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.testng.ScreenShooter;

import businessrouter.webelements.BrAllMenueElements;
import businessrouter.webpageoperation.BrAdvancedBackupSettingsPage;
import businessrouter.webpageoperation.BrAdvancedDynamicDNSPage;
import businessrouter.webpageoperation.BrAdvancedIPv6Page;
import businessrouter.webpageoperation.BrAdvancedQosSetupPage;
import businessrouter.webpageoperation.BrAdvancedStaticRoutesPage;
import businessrouter.webpageoperation.BrAdvancedVlanPage;
import businessrouter.webpageoperation.BrBasicAttachedDevicesPage;
import businessrouter.webpageoperation.BrBasicDeviceNameSetupPage;
import businessrouter.webpageoperation.BrBasicLanSetupPage;
import businessrouter.webpageoperation.BrBasicSetupWizardPage;
import businessrouter.webpageoperation.BrBasicWanSetupPage;
import businessrouter.webpageoperation.BrDashboardPage;
import businessrouter.webpageoperation.BrFirewallAccessControlPage;
import businessrouter.webpageoperation.BrFirewallBasicSetupPage;
import businessrouter.webpageoperation.BrFirewallPortForwardingTriggeringPage;
import businessrouter.webpageoperation.BrFirewallTrafficRulesPage;
import businessrouter.webpageoperation.BrIPSecVpnPage;
import businessrouter.webpageoperation.BrIPv6DhcpServerPage;
import businessrouter.webpageoperation.BrLoginPage;
import businessrouter.webpageoperation.BrPptpL2tpServerPage;
import businessrouter.webpageoperation.BrTMSPage;
import io.qameta.allure.Attachment;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import util.LoggerPrintStream;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;

@Listeners({
    ScreenShooter.class
})
public class TestCaseBase {
    public boolean                      micResult      = false;
    public boolean                      CaseResult      = false;
    public static boolean               toRestart      = false;
    public static URL                   sRemoteURL     = null;
    public static Capabilities          sRemoteCap     = null;
    public static MyCommonAPIs          handle         = new MyCommonAPIs();
    public static WebportalParam        webportalParam = new WebportalParam();
    public static int                   iFailCount     = 0;   
    public static BrAllMenueElements    BrAllMenue     = new BrAllMenueElements(); 
    public static BrLoginPage           brlogin        =  new BrLoginPage();
    public static BrBasicDeviceNameSetupPage brdevname =  new BrBasicDeviceNameSetupPage();
    public static BrAdvancedBackupSettingsPage           brbackup       = new BrAdvancedBackupSettingsPage();
    public static BrFirewallTrafficRulesPage brfirewall= new BrFirewallTrafficRulesPage();
    public static BrFirewallPortForwardingTriggeringPage brporttrigger = new BrFirewallPortForwardingTriggeringPage();
    public static BrTMSPage                   Brtmspage = new BrTMSPage();
    public static BrAdvancedVlanPage         brvlanpage = new BrAdvancedVlanPage();
    public static BrBasicLanSetupPage        brlansetuppage  = new BrBasicLanSetupPage();
    public static BrDashboardPage            brdashboard  = new BrDashboardPage();
    public static BrFirewallAccessControlPage    braccesscontrol  = new BrFirewallAccessControlPage();
    public static BrBasicAttachedDevicesPage   brattachdevice = new BrBasicAttachedDevicesPage();
    public static BrBasicWanSetupPage          brwanpage =  new BrBasicWanSetupPage();
    public static BrPptpL2tpServerPage         brpptpl2tpserver=  new BrPptpL2tpServerPage();
    public static BrAdvancedQosSetupPage       brqospage = new BrAdvancedQosSetupPage();
    public static BrAdvancedStaticRoutesPage brstaticroutepage = new BrAdvancedStaticRoutesPage();
    public static BrAdvancedDynamicDNSPage   brddnspage = new BrAdvancedDynamicDNSPage();
    public static BrIPv6DhcpServerPage       bripv6dhcpserverpage = new BrIPv6DhcpServerPage();
    public static BrAdvancedIPv6Page         bripv6page = new BrAdvancedIPv6Page();
    public static BrFirewallBasicSetupPage   brfirewallbasicpage = new BrFirewallBasicSetupPage();
    public static BrBasicSetupWizardPage     brsetupwizardpage = new BrBasicSetupWizardPage();
    public static BrIPSecVpnPage             bripsecvpnpage = new BrIPSecVpnPage();
    
    static DriverLog                 eventLog       = new DriverLog();;
    static EventFiringWebDriver      eventWebDriver = null;
    public static Map<String, String> TmsRebootCommands = new HashMap<String, String>() {
        {
          
            put("Interface Type", "Backend");
            put("Dut IP", WebportalParam.brlanconnectip);
            put("Host IP", WebportalParam.brlanconnectip);
            put("Protocol", "reboot");
   
            
        }
    };
    public static Comparator<Method> compareStep    = new Comparator<Method>() {
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

    public void initdata() {
      //  wvp.init();
     //   wpsp.init();
    //    fpp.init();
    }
    public void runTest(Object obj) throws Exception {
        initdata();
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
                m.invoke(this);
            } catch (Exception e) {
                if (WebportalParam.enableDebug) {
                    e.printStackTrace();
                } else
                    throw e;
            }
        }

    }

    /**
     * We must use remote driver already
     *
     * @return
     */
    public WebDriver startBrowser() {
        return startBrowser(sRemoteURL, sRemoteCap);
    }

    public WebDriver startBrowser(URL url, Capabilities capabilities) {
        WebDriver webDriver;
        boolean isRestarted = false;
        String sCurURL = "faked";
        boolean bOpenLogin = false;
        WebDriverRunner.addListener(eventLog);
        try {
            webDriver = new RemoteWebDriver(url, capabilities);
            try {
                sCurURL = webDriver.getCurrentUrl();
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
                System.out.println("we have restarted browser");
            }
        } catch (Throwable e) {
            System.out.println("try one more time to start...");
            e.printStackTrace();
            webDriver = new RemoteWebDriver(url, capabilities);
            bOpenLogin = true;
        }
        if (bOpenLogin) {
            System.out.println("let we go to login page");
            webDriver.get(WebportalParam.serverUrlLogin);
        }
        return webDriver;
    }
        public DesiredCapabilities setDownloadsPath() {
        // String downloadsPath = "C:\\tftpd32";
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        // chromePrefs.put("download.default_directory", downloadsPath);
        // chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.prompt_for_download", true);
        String locale = getlocale();
        ChromeOptions options = new ChromeOptions();
        options.addArguments(locale);
        options.setExperimentalOption("prefs", chromePrefs);
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(ChromeOptions.CAPABILITY, options);
        return caps;
    }

    public String getlocale() {
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
        System.out.println("#################### Run TestCase: " + this.getClass().getName());
        try {
            beforeClass();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    void beforeClass() throws MalformedURLException {
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

        Configuration.startMaximized = true;
        if (WebportalParam.enableRemote) {
            DesiredCapabilities capabilities;
            WebDriver webDriver;
            String locale = getlocale();
            URL sURL;
            String sRemoteHost = "http://127.0.0.1";
            if (WebportalParam.BrowserType.equalsIgnoreCase("chrome")) {
                sURL = new URL(sRemoteHost + ":4444/wd/hub");
            } else {
                sURL = new URL(sRemoteHost + ":4445/wd/hub");
            }

            if (WebportalParam.BrowserType.toLowerCase().contains("chrome")) {
                ChromeOptions options = new ChromeOptions();
                options.addArguments(locale);
                options.addArguments("disable-infobars");
                capabilities = DesiredCapabilities.chrome();
                capabilities.setBrowserName("chrome");
                capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            } else {
                FirefoxOptions options = new FirefoxOptions();
                options.addArguments(locale);
                capabilities = DesiredCapabilities.firefox();
                capabilities.setBrowserName("firefox");
                capabilities.setCapability("marionette", false);
                capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);
            }

            webDriver = startBrowser(sURL, capabilities);
            if (WebportalParam.enableDebug) {
                Configuration.timeout = 10000;
                webDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            } else {
                webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            }

            webDriver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
            webDriver.manage().timeouts().setScriptTimeout(120, TimeUnit.SECONDS);
            try {
                webDriver.manage().window().maximize();
            } catch (Throwable e) {
                System.out.println("error to max");
            }

            eventWebDriver = new EventFiringWebDriver(webDriver);
            eventWebDriver.register(eventLog);
            WebDriverRunner.setWebDriver(eventWebDriver);
            System.out.printf("Current URL: %s\n", webDriver.getCurrentUrl());
        } else {
            if (WebportalParam.BrowserType.equalsIgnoreCase("chrome")) {
                Configuration.browser = "chrome";
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\chromedriver.exe");
                // DesiredCapabilities cap = setDownloadsPath();
                HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
                chromePrefs.put("download.prompt_for_download", true);
                String locale = getlocale();
                ChromeOptions options = new ChromeOptions();
                options.addArguments(locale);
                 options.setExperimentalOption("prefs", chromePrefs);
                DesiredCapabilities caps = new DesiredCapabilities();
                caps.setCapability(ChromeOptions.CAPABILITY, options);
                WebDriver webDriver = new ChromeDriver(options);
                webDriver.manage().window().maximize();
                WebDriverRunner.setWebDriver(webDriver);
            } else if (WebportalParam.BrowserType.equalsIgnoreCase("firefox")) {
                Configuration.browser = "gecko";
                System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\geckodriver.exe");
                String locale = getlocale();
                FirefoxOptions options = new FirefoxOptions();
                options.addArguments(locale);
                WebDriver webDriver = new FirefoxDriver(options);
                webDriver.manage().window().maximize();
                WebDriverRunner.setWebDriver(webDriver);

            } else if (WebportalParam.BrowserType.equalsIgnoreCase("iexplore")) {
                Configuration.browser = "ie";
                System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\IEDriverServer.exe");
                DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
                capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                WebDriver webDriver = new InternetExplorerDriver(capabilities);
                webDriver.manage().window().maximize();
                WebDriverRunner.setWebDriver(webDriver);
            }
        }

    }

    @Attachment(type = "text/uri-list")
    public String addConsoleLog() {
        String sName = this.getClass().getName();
        String sTo = "SwitchFunctionality.";
        String sEnd = ".Testcase";
        int iStart = sName.indexOf(sTo);
        int iEnd = sName.indexOf(sEnd);
        String sTcName;
        if (iEnd > iStart && iStart > 0) {
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

    @AfterClass(alwaysRun = true)
    public void afterClassWrap(ITestContext context) {
        System.out.println("#################### Stop TestCase: " + this.getClass().getName());
        try {
            afterClass(context);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    void afterClass(ITestContext context) {
        int iFail = context.getFailedTests().size();
        System.out.println("Failed testcase count: " + iFail);
        System.out.println("iFailCount: " + iFailCount);
        System.out.print(WebportalParam.enableDebug);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@!!!!!!");
        if (!WebportalParam.enableDebug) {
            try {  
                // Edit by Sujuan Li at 8/20/2018 start
                System.out.println("BRNAME:" + WebportalParam.br1deveiceName);
                if (WebportalParam.br1deveiceName.contentEquals("BR500")) {  
                     // Edit by Dallas Zhao at 9/11/2018 start
                    //System.out.print("micResult:");
                    System.out.print(CaseResult);
                    if (!CaseResult) {
                        Brtmspage.CloseTMSWindows();
                        //String ClientUrl = "window.open(\"https://" + WebportalParam.brlangateway + "\");";
                        //logger.info("ClientUrl:" + ClientUrl);
                        //String js = "window.open(\"http://192.168.1.8/tmshtml/login.html\");";
                        //Selenide.executeJavaScript(ClientUrl);
                        //handle.sleepi(100);
                        brlogin.LoginWithNewLanIP(WebportalParam.bripsecoppositelangateway);
                        // Selenide.open(WebportalParam.bripsecoppositelangateway, "", "admin", "Test@123");
                         handle.sleepi(10);
                         brbackup.OpenBackupSettingsPage();
                         MyCommonAPIs.sleepi(10);
                         brbackup.RestoreSetting("C:\\tftpd32\\NETGEAR_BR500_2_default.cfg");
                         MyCommonAPIs.sleepi(100);
                         Selenide.close();
                        Selenide.open(WebportalParam.serverUrl, "", "admin", "Test@123");
                        handle.sleepi(10);
                        brbackup.OpenBackupSettingsPage();
                        MyCommonAPIs.sleepi(10);
                        brbackup.RestoreSetting("C:\\tftpd32\\NETGEAR_BR500_default.cfg");
                        MyCommonAPIs.sleepi(100);
                        Selenide.close();
                        //brlogin.defaultLogin();
                        //MyCommonAPIs.sleepi(10);
                        
                        String TmsPageHandle= Brtmspage.LoginTMS(WebportalParam.brlanconnectip);
                        TmsRebootCommands.replace("Dut IP", WebportalParam.brlanconnectip);
                        TmsRebootCommands.replace("Host IP", WebportalParam.brlanconnectip);
                        boolean Result1 = Brtmspage.RuncmdByTMS(TmsRebootCommands);
                        String TmsPageHandle2= Brtmspage.LoginTMS(WebportalParam.brwanconnectip);
                        TmsRebootCommands.replace("Dut IP", WebportalParam.brwanconnectip);
                        TmsRebootCommands.replace("Host IP", WebportalParam.brwanconnectip);
                        boolean Result2 = Brtmspage.RuncmdByTMS(TmsRebootCommands); 
                        MyCommonAPIs.sleepi(120);
                       
                    } // Edit by Dallas Zhao at 9/11/2018 end                 
                    //brlogin.BrLogout();  
                } else {                         // Edit by Sujuan Li at 8/20/2018 end
                    //UserManage userManage = new UserManage();
                    //userManage.logout();
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
         // Edit by Sujuan Li at 8/20/2018 start
            if (!WebportalParam.br1deveiceName.contentEquals("BR500")) {
                if (iFail != iFailCount) {
                    iFailCount = iFail;
                    if (iFail > 0) {
                        try {
                           // handle.getCmdOutputShowRunningConfig(false);
                       } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                 }
            }
            // Edit by Sujuan Li at 8/20/2018 end
                

            if (!WebportalParam.enableRemote) {
                WebDriverRunner.getWebDriver().quit();
            } else {
                eventWebDriver.unregister(eventLog);
            }
        }
        addConsoleLog();
    }

}
