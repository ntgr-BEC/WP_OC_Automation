
package util;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.InetAddresses;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import testbase.TestCaseBase;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.OrganizationPage;
import webportal.weboperation.WebportalLoginPage;

public class MyCommonAPIs {
    public static int             timeout_element  = 130 * 1000;
    public static String          loader           = "[class='loaderContainer']";
    protected final static Logger logger           = Logger.getLogger("MyCommonAPIs");
    public WebportalParam         webportalParam   = null;
    public static boolean         isCurlStart      = false;
    public static boolean         isVpnClientStart = false;
    public static boolean         isCmdStart       = false;
    public static int             iMonitorCycly    = 100;
    public static Long            timerStart       = 0L;
    public static int             timerDuration    = 0;
    public static String          testcaseName     = null;
    
    // some commone elemnt
    // public String sMyDevicesLink = ".MobileDevice a[href*=evices]";
    public String          sMyDevicesLink = ".MobileDevice";
    public SelenideElement loginUserType  = $(".userNameBlock .fontSize12");
    
    public MyCommonAPIs() {
        // logger.info("init...");
    }
    
    public static void sleepi(int secs) {
        new Pause().seconds(secs);
    }
    
    public static void sleepsync() {
        if (!WebportalParam.enableBatch) {
            if (WebportalParam.sw1Model.contains("GS752")) {
                new Pause().seconds(60, "wait cloud to sync cmd for devices");
            } else {
                new Pause().seconds(60, "wait cloud to sync cmd for devices");
            }
        }
    }

    public static void sleepsyncorbi() {
        new Pause().seconds(100, "wait cloud to sync cmd for devices");
    }
    
    public static void sleep(int secs, String coms) {
        new Pause().seconds(secs, coms);
    }
    
    public static void sleep(long milliseconds) {
        logger.info(String.format("to: %ss/(%s)ms", milliseconds / 1000, milliseconds));
        Selenide.sleep(milliseconds);
    }
    
    /**
     * sleep if 10m will be next day to avoid time issue
     */
    public void Sleep4Midnight() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        if ((hour >= 23) && (minute > 50)) {
            sleep(((60 - minute) + 1) * 60, "midnight...");
        }
    }

    public String getFakeDeviceNo(String devType) {
        String devNo = "not_support";
        if (devType.equalsIgnoreCase("WAC564")) {
            devNo = "6081905YF" + RandomStringUtils.randomNumeric(4);
        }
        if (devType.equalsIgnoreCase("WAC505")) {
            devNo = "4XT172EK" + RandomStringUtils.randomNumeric(5);
        }
        if (devType.equalsIgnoreCase("WAC510")) {
            devNo = "4W837451" + RandomStringUtils.randomNumeric(5);
        }
        if (devType.equalsIgnoreCase("WAC502")) {
            devNo = "6621902B" + RandomStringUtils.randomNumeric(5);
        }
        if (devType.equalsIgnoreCase("GC752XP")) {
            devNo = "51517478" + RandomStringUtils.randomNumeric(5);
        }
        if (devType.equalsIgnoreCase("GS728TPv2")) {
            devNo = "5WW193DW" + RandomStringUtils.randomNumeric(5);
        }
        logger.info("Build a device " + devType + ": " + devNo);
        return devNo;
    }
    
    /**
     * @param timeout
     *                in secs
     */
    public static void timerStart(int timeout) {
        logger.info("Build a timer: " + timeout);
        if ((System.currentTimeMillis() - timerStart) < timerDuration) {
            logger.warning("FIXME: previous timer is found -> " + (timerDuration / 1000));
        }

        timerStart = System.currentTimeMillis();
        timerDuration = timeout * 1000;
    }
    
    public static boolean timerRunning() {
        if ((System.currentTimeMillis() - timerStart) > timerDuration)
            return false;
        else
            return true;
    }
    
    /**
     * scroll page on window
     *
     * @param isTop
     *              True to top, false to bottom
     */
    public void scrollTo(boolean isTop) {
        if (isTop) {
            Selenide.executeJavaScript("var q=document.body.scrollTop=0");
            Selenide.executeJavaScript("var q=document.documentElement.scrollTop=0");
        } else {
            Selenide.executeJavaScript("var q=document.body.scrollTop=100000");
            Selenide.executeJavaScript("var q=document.documentElement.scrollTop=100000");
        }
        sleepi(2);
    }
    
    public static String takess(String comment) {
        logger.info("==========> " + comment);
        return takess();
    }
    
    public static String takess() {
        Date date = new Date();
        long times = date.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String dateString = formatter.format(date);
        String fileName = String.format("%s/%s/%s.png", System.getProperty("user.dir"), Configuration.reportsFolder, dateString);
        try {
            FileUtils.forceMkdirParent(new File(fileName));
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        System.out.printf("Task Screenshot to: %s\n", fileName);
        try {
            Screenshot fpScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
                    .takeScreenshot(WebportalParam.curWebDriver);
            ImageIO.write(fpScreenshot.getImage(), "PNG", new File(fileName));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Selenide.screenshot(fileName);
        } catch (Throwable e) {
            e.printStackTrace();
            WebportalParam.browserIsFailed = true;
            TestCaseBase.startBrowser();
        }
          System.out.println("file name inside"+fileName);
        return fileName;
    }
    
    public boolean isInLoginPage() {
        String url = getCurrentUrl();
        logger.info("checking page: " + url);
        if (url.endsWith("#/login") || url.contains("/login?") || url.equals(WebportalParam.serverUrlLogin))
            return true;
        else
            return false;
    }
    
    /**
     * @param  index
     *               0-refresh, 1-geturl, 2-pagesource, 3-openurl
     * @param  argv
     *               if any
     * @return
     */
    public static String checkSystemCall(int index, String argv) {
        String toreturn = "";
        String[] indexStr = { "refresh page", "get url", "get PageSource", "open url" };
        logger.info("call: " + indexStr[index] + "/argv: " + argv);
        boolean stillfailed = false;
        while (true) {
            try {
                if (0 == index) {
                    WebportalParam.curWebDriver.navigate().refresh();
                } else if (1 == index) {
                    toreturn = WebportalParam.curWebDriver.getCurrentUrl();
                    logger.info(toreturn);
                } else if (2 == index) {
                    toreturn = WebportalParam.curWebDriver.getPageSource();
                    logger.info("length of html page: " + toreturn.length());
                } else if (3 == index) {
                    Selenide.open(argv);
                }
                stillfailed = false;
                break;
            } catch (Throwable e) {
                if (stillfailed) {
                    break;
                }
                
                e.printStackTrace();
                stillfailed = true;
                sleep(300, "error on inline call, try once for call: " + indexStr[index]);
                WebportalParam.curWebDriver = WebDriverRunner.getWebDriver();
                continue;
            }
        }
        
        if (stillfailed) {
            logger.info("FIXME: Browser failed for system call, try to restart...");
            WebportalParam.browserIsFailed = true;
            TestCaseBase.startBrowser();
        }
        return toreturn;
    }
    
    /**
     * refresh and check whether there was a page error
     */
    public void refresh() {
        logger.info("do our refresh");
        checkSystemCall(0, null);
        waitReady();
        int trytimes = 15;
        while (trytimes > 0) {
            trytimes -= 1;
            if (checkPageError()) {
                sleep(60, "try again after sleep: " + trytimes);
                checkSystemCall(0, null);
                waitReady();
            } else {
                break;
            }
        }
        
        if (isInLoginPage()) {
            logger.info("session timeout?");
            new WebportalLoginPage().defaultLogin();
            gotoLoction();
        }
    }
    
    public String pageSource() {
        return checkSystemCall(2, null);
    }
    
    /**
     * @return return true if found error
     */
    public boolean checkPageError() {
        String sPG = pageSource();
        List<String> lsError = new ArrayList<String>();
        lsError.add("A network error has occurred. Try again in a few minutes.");
        lsError.add("modal fade internetError in");
        lsError.add("Access token is invalid");
        for (String s : lsError) {
            if (sPG.contains(s)) {
                logger.info("Found error: " + s);
                takess();
                // restorePage();
                return true;
            }
        }
        return false;
    }
    
    /**
     * @param uri
     *            open url directly, if page already was opened, refresh it.
     */
    public void open(String uri) {
        getCurrentUrl();
        System.out.printf("starting: open %s\n", uri);
        waitReady();
        if (!getCurrentUrl().equals(uri)) {
            checkSystemCall(3, uri);
            for (int i = 0; i < 2; i++) {
                waitReady();
                if (checkPageError()) {
                    sleep(60, "try again after sleep: " + i);
                    checkSystemCall(0, null);
                    waitReady();
                } else {
                    break;
                }
            }
        } else {
            refresh();
        }
    }
    
    /**
     * @param uri
     *                   open url
     * @param needPrefix
     *                   whether append @WebportalParam.serverUrl to uri
     */
    public void open(String uri, boolean needPrefix) {
        if (needPrefix) {
            uri = WebportalParam.serverUrl + uri;
        }
        open(uri);
    }
    
    public void restorePage() {
        String sUrl = getCurrentUrl();
        logger.info("will restore: " + sUrl);
        Selenide.actions().sendKeys(Keys.ESCAPE).build().perform();
        waitReady();
        try {
            gotoAllLocation();
            gotoLoction();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        waitReady();
        try {
            Selenide.open(sUrl);
        } catch (Throwable e) {
            logger.info("error on open: " + sUrl);
        }
        waitReady();
        logger.info("restored: " + sUrl);
    }
    
    public static String getCurrentUrl() {
        return checkSystemCall(1, null);
    }
    
    /**
     * @param  line
     * @param  regex
     * @return       return true for finding regex in line or false for not.
     */
    public static boolean matches(String line, String regex) {
        logger.info(String.format("matches for: <%s><%s>", line, regex));
        if (regex.substring(0, 1) != ".*") {
            regex = ".*" + regex;
        }
        int sLen = regex.length();
        if (regex.substring(sLen - 2, sLen - 1) != ".*") {
            regex += ".*";
        }
        
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(line);
        
        return m.matches();
    }
    
    /**
     * @param  ip
     *            get next ip like 192.168.1.2 from 192.168.1.1
     * @return
     */
    public String nextIP(String ip) {
        InetAddress a = InetAddresses.forString(ip);
        int i = InetAddresses.coerceToInteger(a);
        InetAddress b = InetAddresses.fromInteger(i + 1);
        return InetAddresses.toAddrString(b);
    }
    
    public static String nextIP(String ip, int hop) {
        InetAddress a = InetAddresses.forString(ip);
        int i = InetAddresses.coerceToInteger(a);
        InetAddress b = InetAddresses.fromInteger(i + hop);
        return InetAddresses.toAddrString(b);
    }

    public String getRandomIp() {
        Random rand = new Random();
        return String.format("10.%s.%s.%s", rand.nextInt(250) + 1, rand.nextInt(250) + 1, rand.nextInt(250) + 1);
    }
    
    public static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    /**
     * @param  ip
     *            the last segnet after +50
     * @return    the static ip should same as None in vlan ip page
     */
    public String getVlan1StaticIp(String ip) {
        return nextIP(ip, 50);
    }

    /**
     * @param  is2ndSW
     *                 false to sw1, true to sw2
     * @return
     */
    public String getVlan1StaticIp(boolean is2ndSW) {
        if (is2ndSW)
            return nextIP(WebportalParam.bk_sw2IPaddress, 50);
        else
            return nextIP(WebportalParam.bk_sw1IPaddress, 50);
    }
    
    /**
     * @param  ip
     *            return ip.1 for gateway, CAUSTION: some lab may use different gw
     * @return
     */
    public String getIpGateway(String ip) {
        int lp = ip.lastIndexOf('.');
        return ip.substring(0, lp) + ".1";
    }
    
    private static String convertToNormalSelector(Object e) {
        String _e = (String) e;
        if (_e.startsWith("By."))
            return _e.split(": ")[1];
        return _e;
    }
    
    public static SelenideElement getSE(Object el) {
        if (el instanceof String) {
            String _el = convertToNormalSelector(el);
            if (_el.startsWith("//") || _el.startsWith("(//"))
                return $x(_el);
            else
                return $(_el);
        } else
            return (SelenideElement) el;
    }
    
    public static ElementsCollection getSEs(String el) {
        el = convertToNormalSelector(el);
        if (el.startsWith("//") || el.startsWith("(//"))
            return $$x(el);
        else
            return $$(el);
    }
    
    public static void waitReady() {
        logger.info(String.format("start..."));
        if ($(loader).isDisplayed()) {
            try {
                $(loader).waitWhile(Condition.visible, timeout_element);
            } catch (Throwable e) {
                takess();
                Selenide.actions().sendKeys(Keys.ESCAPE).build().perform();
                ((JavascriptExecutor) WebportalParam.curWebDriver).executeScript("window.stop();");
                Selenide.refresh();
            }
        } else {
            sleepi(2);
        }
    }
    
    public static void waitElement(Object el) {
        if (!el.toString().contains("NoSuchElementException")) {
            logger.info(String.format("<%s>", el.toString()));
        } else {
            logger.info(String.format("<%s>", ((SelenideElement) el).getSearchCriteria()));
        }
        getSE(el).waitUntil(Condition.exist, timeout_element);
    }
    
    public static void waitElementNot(Object el) {
        if (!el.toString().contains("NoSuchElementException")) {
            logger.info(String.format("<%s>", el.toString()));
        } else {
            logger.info(String.format("<%s>", ((SelenideElement) el).getSearchCriteria()));
        }
        getSE(el).waitWhile(Condition.exist, timeout_element);
    }
    
    public static String getShadowRootElementinnerText(String jselement) {
        
            JavascriptExecutor js = (JavascriptExecutor) WebportalParam.curWebDriver;
            String clearData = (String) js.executeScript("return "+jselement+".innerText");
            return clearData;
    }
    
    
    public static void clickElementIdViaJs(String id) {
        Selenide.executeJavaScript("document.getElementById('" + id + "').click()"); 
        sleepi(1);
    }
    
    /**
     * @param el
     *           the element to click directly
     */
    public void click(Object el) {
        logger.info(String.format("<%s>", el.toString()));
        try {
            getSE(el).click();
        } catch (Throwable e) {
            takess("error on selenium click, try js again");
            waitReady();
            ((JavascriptExecutor) WebportalParam.curWebDriver).executeScript("return arguments[0].click();", getSE(el));
        }
        waitReady();
    }
    
    /**
     * @param el
     * @param isJS
     *             true to use js, false to click by selenium
     */
    public void click(Object el, boolean isJS) {
        logger.info(String.format("<%s>", el.toString()));
        if (isJS) {
            ((JavascriptExecutor) WebportalParam.curWebDriver).executeScript("return arguments[0].click();", getSE(el));
        } else {
            getSE(el).click();
        }
        waitReady();
    }
    
    public String   sPopButtonCss  = "div.in button";
    public String   sPopButtonCssCancel  = "div.in button[2]";
    /**
     * 0-save, 1-cancel, 2-delete, 3-next, 4-edit
     */
    public String[] btnIndexString = { "save", "cancle", "delete", "next", "edit", "ok" };
    
    /**
     * @param btnIndex
     *                 see {@link #btnIndexString} from 0 ~ n
     */
    public void clickButton(int btnIndex) {
        boolean bFound = false;
        String sBtn, sRealBtn, sNoLoc;
        
        takess("Screenshot page on clickButton for button: " + btnIndexString[btnIndex]);
        sNoLoc = btnIndexString[btnIndex].substring(0, 1).toUpperCase() + btnIndexString[btnIndex].substring(1);
        sBtn = WebportalParam.getLocText("im5.6Keys", btnIndexString[btnIndex]);
        sRealBtn = String.format("//button[text()='%s' or text()='%s']", sNoLoc, sBtn);
        for (SelenideElement se : $$x(sRealBtn)) {
            if (se.isDisplayed()) {
                bFound = true;
                logger.info(String.format("<%s>-<%s>", sBtn, se));
                click(se);
                waitReady();
                break;
            }
        }
        
        if (!bFound && !WebportalParam.BrowserLanguage.equalsIgnoreCase("en")) {
            sBtn = WebportalParam.getNLocText("common", btnIndexString[btnIndex]);
            sRealBtn = String.format("//button[text()='%s']", sBtn);
            for (SelenideElement se : $$x(sRealBtn)) {
                if (se.isDisplayed()) {
                    bFound = true;
                    logger.info(String.format("<%s>-<%s>", sBtn, se));
                    click(se);
                    waitReady();
                    break;
                }
            }
        }
        
        assertTrue(bFound, "Button was not found");
        getPageErrorMsg();
    }
    
    /**
     * @param isYes
     *              true to Yes
     */
    public void clickYesNo(boolean isYes) {
        waitReady();
        if (isYes) {
            $$(sPopButtonCss).get(2).click();
        } else {
            $$(sPopButtonCss).get(1).click();
        }
        waitReady();
        getPageErrorMsg();
    }
    
    /**
     * @param isYes
     *              true to Yes
     */
    public void clickYesNo(int buttonIndex) {
        waitReady();
        $$(sPopButtonCss).get(buttonIndex).click();
        waitReady();
        getPageErrorMsg();
    }
    
    public void clickBoxFirstButton() {
        try {
            $(sPopButtonCss).waitUntil(Condition.appear, 10 * 1000);
            $$(sPopButtonCss).first().click();
            waitReady();
        } catch (Throwable e) {
            e.printStackTrace();
            takess();
        }
        getPageErrorMsg();
    }
    
    public void clickBoxLastButton() {
        try {
            $(sPopButtonCss).waitUntil(Condition.appear, 10 * 1000);
            $$(sPopButtonCss).last().click();
//            $$(sPopButtonCss).filter(Condition.appear).last().click();
            waitReady();
        } catch (Throwable e) {
            e.printStackTrace();
            takess();
        }
        getPageErrorMsg();
    }
    
    public void clickBoxBeforeLastButton() {
        try {
            $(sPopButtonCssCancel).waitUntil(Condition.appear, 10 * 1000);
//            $$(sPopButtonCss).last().click();
            $$(sPopButtonCssCancel).last().click();
            waitReady();
        } catch (Throwable e) {
            e.printStackTrace();
            takess();
        }
        getPageErrorMsg();
    }
    
    
    /**
     * @param  sLines
     * @param  regex
     * @return
     */
    public boolean isContain(String sLines, String regex) {
        for (String sLine : sLines.split("\n")) {
            if (sLine.trim().matches(regex))
                return true;
        }
        return false;
    }
    
    /**
     * @param  sLines
     * @param  regex
     * @param  sSplit
     * @return
     */
    public boolean isContain(String sLines, String regex, String sSplit) {
        for (String sLine : sLines.split(sSplit)) {
            if (sLine.matches(regex))
                return true;
        }
        return false;
    }
    
    /**
     * @param  cmd
     *                 Send cmd directly to telnet, split ";" as multiple commands to SW
     * @param  is2ndsw
     *                 true for sw2, false for sw1
     * @return
     */
    public static String getCmdOutput(String cmd, boolean is2ndsw) {
        String swIp = WebportalParam.sw1IPaddress;
        if (is2ndsw) {
            swIp = WebportalParam.sw2IPaddress;
        }
        logger.info(String.format("<%s>-<%s>", swIp, cmd));
        SwitchTelnet st;
        try {
            st = new SwitchTelnet(swIp, WebportalParam.loginDevicePassword, true);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        
        st.setEnable();
        String sRet = "";
        if (cmd.contains(";")) {
            for (String s : cmd.split(";")) {
                if (s.equalsIgnoreCase("config")) {
                    SwitchTelnet.telnet.setPrompt(st.isRltkSW, true);
                }
                sRet = st.getCLICommand(s);
            }
        } else {
            sRet = st.getCLICommand(cmd);
        }
        return sRet;
    }
    
    /**
     * @param  cmd
     *                 Send cmd directly to telnet, split ";" as multiple commands to SW
     * @param  is2ndsw
     *                 true for sw2, false for sw1
     * @return
     */
    public String[] getCmdOutputLines(String cmd, boolean is2ndsw) {
        String swIp = WebportalParam.sw1IPaddress;
        if (is2ndsw) {
            swIp = WebportalParam.sw2IPaddress;
        }
        logger.info(String.format("<%s-%s>", swIp, cmd));
        SwitchTelnet st;
        try {
            st = new SwitchTelnet(swIp, WebportalParam.loginDevicePassword, true);
        } catch (Exception e) {
            e.printStackTrace();
            return new String[] { "error" };
        }
        
        st.setEnable();
        String sRet = "";
        if (cmd.contains(";")) {
            for (String s : cmd.split(";")) {
                sRet = st.getCLICommand(s);
            }
        } else {
            sRet = st.getCLICommand(cmd);
        }
        
        return sRet.split("\n");
    }
    
    /**
     * @param  is2ndsw
     *                 true for sw2, false for sw1
     * @return         get all output of "show running-config"
     */
    public String getCmdOutputShowRunningConfig(boolean is2ndsw) {
        String swIp = WebportalParam.sw1IPaddress;
        if (is2ndsw) {
            swIp = WebportalParam.sw2IPaddress;
        }
        logger.info(String.format("<%s>", swIp));
        SwitchTelnet st;
        try {
            st = new SwitchTelnet(swIp, WebportalParam.loginDevicePassword, true);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        
        st.setEnable();
        String sRet = st.sendCLICommandClear("show running-config", null);
        //String sRet = st.readFull("show running-config");
        logger.info(String.format("<%s>", sRet));
        return sRet;
    }
    
    /**
     * @param  is2ndsw
     *                 true for sw2, false for sw1
     * @return         get all output of "show logging buffered"
     */
    public String getCmdOutputShowLogging(boolean is2ndsw) {
        String swIp = WebportalParam.sw1IPaddress;
        if (is2ndsw) {
            swIp = WebportalParam.sw2IPaddress;
        }
        logger.info(String.format("<%s>", swIp));
        SwitchTelnet st;
        try {
            st = new SwitchTelnet(swIp, WebportalParam.loginDevicePassword, true);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        
        st.setEnable();
        String sRet = st.readFull("show logging buffered");
        logger.info(String.format("<%s>", sRet));
        return sRet;
    }
    
    /**
     * @param  expStr
     *                    the expect string of running-config in telnet
     * @param  notContain
     *                    false to search text, true not.
     * @return
     */
    public String waitCmdReady(String expStr, boolean notContain) {
        logger.info(String.format("<%s>-<%s>", expStr, notContain));
        SwitchTelnet st;
        try {
            st = new SwitchTelnet(WebportalParam.sw1IPaddress, WebportalParam.loginDevicePassword, true);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        String sRet;
        sRet = st.checkExample(expStr, notContain);
        
        sleep(15, "wait all others cli command to complete");
        return sRet;
    }
    
    static String g_sTimeout = "TIMEOUT";
    
    /**
     * @param  sCmd
     *              cmd issued to telnet
     * @param  sExp
     *              string to contains
     * @param  bNot
     *              false to check exist, true to non-existed
     * @return      All output of last cmd (40s timeout)
     */
    
    public static String waitCliReady(String sCmd, String sExp, boolean bNot) {
        logger.info(String.format("<%s>-<%s>-<%s>-<%s>", WebportalParam.sw1IPaddress, sCmd, sExp, bNot));
        SwitchTelnet st = new SwitchTelnet(WebportalParam.sw1IPaddress, WebportalParam.loginDevicePassword, true);
        st.setEnable();
        String result = "";
        boolean bTimeout = true;
        for (int i = 0; i <= SwitchTelnet.checkcycle; i++) {
            result = st.getCLICommand(sCmd);
            boolean bb = result.contains(sExp);
            if ((bNot && !bb) || (!bNot && bb)) {
                bTimeout = false;
                logger.info("break-for-waitcli");
                break;
            }
            MyCommonAPIs.sleepi(10);
        }
        if (bTimeout) {
            logger.info("timeout-for-waitcli");
            return g_sTimeout;
        }
        return result;
    }
    
    /**
     * @param  sAPI
     *                 like BRUtils.api_device_info
     * @param  sExp
     *                 string to match
     * @param  bNot
     * @param  devType
     *                 0-br, 1/3-ob, 2-sw
     * @return         the full output string
     */
    public String waitRestReady(String sAPI, String sExp, boolean bNot, int devType) {
        String devIp = WebportalParam.br1IPaddress;
        if ((devType == 1) || (devType == 3)) {
            devIp = WebportalParam.ob1IPaddress;
        }
        if (devType == 2) {
            devIp = WebportalParam.sw1IPaddress;
        }
        logger.info(String.format("<%s>-<%s>-<%s>-<%s>", devIp, sAPI, sExp, bNot));
        boolean bTimeout = true;
        String result = "";
        int cycle = 15;
        if (devType == 1) {
            cycle = 10;
        }
        if (devType == 3) {
            cycle = 30;
        }
        BRUtils handle = new BRUtils(sAPI, devType);
        for (int i = 0; i <= cycle; i++) {
            result = handle.Dump();
            logger.info(result);
            // if (result.equals("")) {
            // break;
            // }
            boolean bb = result.contains(sExp);
            if ((bNot && !bb) || (!bNot && bb)) {
                bTimeout = false;
                logger.info("break-for-waitrest");
                break;
            }
            MyCommonAPIs.sleepi(10);
            handle.getRest(sAPI, null, devType);
        }
        if (bTimeout) {
            logger.info("timeout-for-waitrest");
            return g_sTimeout;
        }
        return result;
    }
    
    /**
     * it uses switch CLI to check, this can be used when switch in cloud mode (10m)
     */
    public void waitDeviceOnline() {
        RunCommand.waitSWAlive(WebportalParam.sw1IPaddress);
        boolean bTimeout = true;
        if (!WebportalParam.isRltkSW1) {   
            for (int i = 0; i < 12; i++) {
                if (!waitCliReady("show application status appmgr", "Connected", false).equals(g_sTimeout)) {
                    bTimeout = false;
                    break;
                }
            }
        } else {
            bTimeout = false;
            sleep(240, "wait cloud online");
        }
        if (bTimeout) {
            logger.info("timeout-for-waitDeviceOnline");
        }
    }
    
    /**
     * it uses switch CLI to check, must be used when switch do reload & default (10m)
     */
    public static void waitDeviceOnlineReload() {
        boolean bTimeout = true;
        RunCommand.waitSWAlive(WebportalParam.sw1IPaddress);
        for (int i = 0; i < 15; i++) {
            if (!waitCliReady("show logging buffered", "Registration Successfull", false).equals(g_sTimeout)) {
                bTimeout = false;
                break;
            }
        }
        if (bTimeout) {
            logger.info("timeout-for-waitDeviceOnlineReload");
        }
        
        getCmdOutput("clear logging buffered", false);
    }
    
    /**
     * @param cmdIndex
     *                 1 = reboot,2 = default
     */
    public void doSwitchCommand(int cmdIndex) {
        logger.info(String.format("<%s>", cmdIndex));
        SwitchTelnet st = new SwitchTelnet(WebportalParam.sw1IPaddress, WebportalParam.loginDevicePassword, true);
        if (cmdIndex == 1) {
            logger.info("try to reboot sw");
            st.switchReboot();
            new Pause().seconds(120, "sleep for switch reboot");
            MyCommonAPIs.sleepi(180);
//            waitDeviceOnline();
        } else if (cmdIndex == 2) {
            st.switchDefault();
            new Pause().seconds(120, "sleep for switch reboot");
            MyCommonAPIs.sleepi(180);
//            waitDeviceOnline();
        }
    }
    
    public static void setText(Object el, String text) {
        logger.info(String.format("<%s-%s>", el, text));
        SelenideElement ec = getSE(el);
        ec.clear();
        ec.sendKeys(text);
    }
    
    public static void setText(SelenideElement se, int val) {
        se.clear();
        for (int i = 0; i < 10; i++) {
            if (getText(se).isEmpty() && getValue(se).isEmpty()) {
                break;
            }
            se.sendKeys(Keys.BACK_SPACE);
        }
        se.sendKeys(String.format("%d", val));
    }
    
    public static String getText(Object el) {
        logger.info(String.format("<%s>", el));
        SelenideElement ec = getSE(el);
        String text = ec.text();
        if (text.length() == 0) {
            text = ec.getAttribute("innerText");
        }
        logger.info(text);
        return text;
    }
    
    public static String getValue(Object el) {
        logger.info(String.format("<%s>", el));
        SelenideElement ec = getSE(el);
        String text = ec.getValue();
        logger.info(text);
        return text;
    }
    
    public static List<String> getTexts(String el) {
        List<String> lsRet = new ArrayList<String>();
        ElementsCollection ecs = getElements(el);
        lsRet = ecs.texts();
        logger.info(String.format("<%s>", lsRet.toString()));
        return lsRet;
    }
    
    public static SelenideElement getElement(String el) {
        ElementsCollection ecs = getElements(el);
        logger.info(String.format("<%s>", ecs.size()));
        return ecs.first();
    }
    
    public static SelenideElement getElement(String el, boolean isLast) {
        ElementsCollection ecs = getElements(el);
        logger.info(String.format("<%s>", ecs.size()));
        return ecs.last();
    }
    
    public static ElementsCollection getElements(String el) {
        waitElement(el);
        logger.info(String.format("<%s>", el));
        return getSEs(el);
    }
    
    /**
     * @return save screenshot if there was error, translate to English if found
     */
    public String getPageErrorMsg() {
        String txtId = "//a[@class='customeErrorClose']/..";
        if ($x(txtId).exists()) {
            String sRet = "";
            for (String code : getTexts(txtId)) {
                logger.info(String.format("<%s>", code));
                if (code.length() > 0) {
                    sRet += WebportalParam.getNLocText(code).toLowerCase();
                }
            }
            if (sRet.length() > 0) {
                takess();
                return sRet;
            }
        }
        String errId = "div[style*=block] .modal-body p";
        if ($(errId).exists()) {
            String code = getText(errId);
            if (code.length() > 0) {
                takess();
                return WebportalParam.getNLocText(code).toLowerCase();
            }
        }
        return "";
    }
    
    String              sSWBox          = ".box-scroller";
    String              sDeviceBox      = "//*[contains(text(),'%s')]/../../..";
    public String       portIdXpath     = "//li[contains(@id,'port_')]";
    public String       sPortBlockCss   = ".PortMemberBlock";
    public String       sPortBlockXpath = "(//*[@class='PortMemberBlock'])[%s]";
    /**
     * group page / stp page vlan page / add lag / add poe sch radius config lag view network page
     */
    public List<String> sXpathDeviceBox = Arrays.asList("//span[contains(text(),'%s')]/../../..", "//h5[contains(text(),'%s')]/../../..",
            "//h5[contains(text(),'%s')]/../../../..", "//span[contains(text(),'%s')]/../../../..",
            "//h5[contains(text(),'%s')]/../../../../../..");
    
    /**
     * @param  devType
     *                 0-sw1, 1-sw2, 2-br, 3-ap
     * @return
     */
    public String getDeviceName(int devType) {
        String sDevName = WebportalParam.sw1deveiceName;
        if (devType == 1) {
            sDevName = WebportalParam.sw2deveiceName;
        } else if (devType == 2) {
            sDevName = WebportalParam.br1deveiceName;
        } else if (devType == 3) {
            sDevName = WebportalParam.ap1deveiceName;
        }
        return sDevName;
    }
    
    /**
     * @param  devType
     *                 0-sw1, 1-sw2, 2-br, 3-ap
     * @return
     */
    public String getDeviceXpath(int devType) {
        return getDeviceXpath(getDeviceName(devType));
    }
    
    /**
     * @param  devName
     * @return
     */
    public String getDeviceXpath(String devName) {
        String sDev = String.format(sDeviceBox, devName);
        waitElement(sDev);
        for (String dev : sXpathDeviceBox) {
            sDev = String.format(dev, devName);
            String tocheck = sDev + portIdXpath;
            if ($x(tocheck).exists())
                return sDev;
        }
        return sDev;
    }
    
    public String getDevicePortIdXpath(String devName) {

        return String.format("%s%s", getDeviceXpath(devName), portIdXpath);
    }
    
    public String getDevicePortIdXpath(int devType) {
        return getDevicePortIdXpath(getDeviceName(devType));
    }
    
    /**
     * @param  devXpath
     *                  like {@code sXpathDeviceBox}
     * @param  portId
     *                  1~n
     * @return          return port SelenideElement for device
     */
    public SelenideElement getDevicePort(String devXpath, String portId) {
        String portUntag = String.format("li[substring(@id,string-length(@id)-string-length('port_%s')+1)='port_%s']", portId, portId);
        String portTag = String.format("li[substring(@id,string-length(@id)-string-length('port_%sT')+1)='port_%sT']", portId, portId);
        String devPortUntag = String.format("%s//%s", devXpath, portUntag);
        String devPortTag = String.format("%s//%s", devXpath, portTag);
        SelenideElement seUntag = $x(devPortUntag);
        if (seUntag.exists())
            return seUntag;
        SelenideElement seTag = $x(devPortTag);
        if (seTag.exists())
            return seTag;
        takess();
        throw new RuntimeException("not able to select port");
    }
    
    /**
     * @param  devType
     *                 0-sw1, 1-sw2, 2-br, 3-ap
     * @return
     */

    public static int sw1PortCount = 99;
    public static int sw2PortCount = 99;
    public static int br1PortCount = 99;
    public static int ap1PortCount = 99;
    
    public int getPortNo() {
        return getPortNo(0);
    }
    
    public int getPortNo(int swIndex) {
        return getPortNo(getDeviceName(swIndex));
    }
    
    public int getPortNo(String devName) {
        int portNo = 0;
        if (devName.equals(WebportalParam.sw1deveiceName)) {
            if (sw1PortCount == 99) {
            } else {
                portNo = sw1PortCount;
            }
        }
        if (devName.equals(WebportalParam.sw2deveiceName)) {
            if (sw2PortCount == 99) {
            } else {
                portNo = sw2PortCount;
            }
        }
        if (devName.equals(WebportalParam.br1deveiceName)) {
            if (br1PortCount == 99) {
            } else {
                portNo = br1PortCount;
            }
        }
        if (devName.equals(WebportalParam.ap1deveiceName)) {
            if (ap1PortCount == 99) {
            } else {
                portNo = ap1PortCount;
            }
        }
        
        if (portNo == 0) {
            String xpath = getDevicePortIdXpath(devName);
            portNo = $$x(xpath).size();
            System.out.println("!!!Check portNo");
            System.out.println(portNo);
            if ($x(String.format(sPortBlockXpath, 1)).exists()) { // vlan network has 2
                portNo = portNo / 2;
                System.out.println("!!!Check portNo2");
                System.out.println(portNo);
            }
            if (devName.equals(WebportalParam.sw1deveiceName)) {
                sw1PortCount = portNo;
            }
            if (devName.equals(WebportalParam.sw2deveiceName)) {
                sw2PortCount = portNo;
            }
            if (devName.equals(WebportalParam.br1deveiceName)) {
                br1PortCount = portNo;
            }
            if (devName.equals(WebportalParam.ap1deveiceName)) {
                ap1PortCount = portNo;
            }
        }
        
        logger.info(String.format("total ports: <%s> for device name: <%s>", portNo, devName));
        return portNo;
    }
    
    /**
     * Return the index of port for one or two row (from 0 to n) return 7 for port 8 on one row switch return 20 for port 16 on two rows switch
     *
     * @param  portIndex
     *                   which port to click, 1 to n
     * @param  portNo
     *                   total ports SW has
     * @return
     */
    public int getPortIndex(int portIndex, int portNo) {
        logger.info(String.format("portIndex: <%s>, portNo: <%s>", portIndex, portNo));
        int iRet;
        if (portNo <= 10 || portNo == 12) {
            // CAUTION: if device is BR, remember to wan is first port
            iRet = portIndex - 1;
        } else { // two row
            int halfPort = portNo / 2;
            if ((portNo == 26) || (portNo == 18)) {// for gs724 / gs716
                halfPort--;
            }
            int halfPortIndex = portIndex / 2;
            int upPort = portIndex % 2;
            if (upPort == 1) {
                iRet = halfPortIndex;
            } else {
                iRet = (halfPort + halfPortIndex) - 1;
            }
        }
        logger.info(String.format("<portIndex: %s>-<totalPorts: %s>-<portRealIndex: %s>", portIndex, portNo, iRet));
        return iRet;
    }
    
    public int getIndexSwitchPoE() {
        int swIndex = 0;
        if (WebportalParam.sw1Model.toLowerCase().contains("p")) {
            swIndex = 0;
        } else if (WebportalParam.sw2Model.toLowerCase().contains("p")) {
            swIndex = 1;
        } else {
            assertTrue(false, "No PoE device found, must have one");
        }
        return swIndex;
    }
    
    /**
     * @return return list of device name in page
     */
    public List<String> getDevicesName() {
        String tocheck1 = "h5[class*=nameOverFlow]"; // network/vlan/lag
        String tocheck2 = ".titleCol>h5"; // add lag
        String tocheck3 = ".nsaccord-head.open span:first-child"; // stp/group
        if ($(tocheck1).exists())
            return getTexts(tocheck1);
        else if ($(tocheck2).exists())
            return getTexts(tocheck2);
        else {
            if ($(tocheck3).exists() && ($$(tocheck3).size() > 1)) {
                List<String> lsret = new ArrayList<String>();
                for (String ret : getTexts(tocheck3)) {
                    if (ret.contains(" -")) { // on group page
                        ret = ret.substring(0, ret.indexOf(" -"));
                    }
                    if (ret.contains("- ")) { // on stp page
                        ret = ret.substring(0, ret.indexOf("- "));
                    }
                    lsret.add(ret);
                }
                return lsret;
            } else
                throw new RuntimeException("not able to find elements for device name");
        }
    }
    
    /**
     * @param  devName
     * @return         return 0-sw1, 1-sw2, 2-br, 3-ap
     */
    public int getDeviceType(String devName) {
        if (devName.equals(WebportalParam.sw1deveiceName))
            return 0;
        if (devName.equals(WebportalParam.sw2deveiceName))
            return 1;
        if (devName.equals(WebportalParam.br1deveiceName))
            return 2;
        if (devName.equals(WebportalParam.ap1deveiceName))
            return 3;
        else
            throw new RuntimeException("TODO: Plz add new device type to all apis.");
    }
    
    /**
     * @param portIndex
     *                  the first PoE port(if sw1 is not poe) at index, from 1 to n
     */
    public void clickPortPoE(int portIndex) {
        logger.info(String.format("<%s>", portIndex));
        int swIndex = getIndexSwitchPoE();
        clickPort(portIndex, swIndex);
    }
    
    /**
     * To support device without name FIXME: If there are more than 1 switches, select all of them?
     *
     * @param portIndex
     * @param is2nd
     *                  false to click first switch in page
     */
    public void clickPort(int portIndex, boolean isAll) {
        logger.info(String.format("<%s>-<%s>", portIndex, isAll));
        String pId = String.format("#port_%s", portIndex);
        if (!isAll) {
            $(pId).click();
        } else {
            for (SelenideElement se : $$(pId)) {
                se.click();
            }
        }
        waitReady();
    }
    
    /**
     * @param portIndex
     *                  click port on sw1 (sw1 may not be the first switch in page)
     */
    public void clickPort(int portIndex) {
        clickPort(portIndex, 0);
    }
    
    /**
     * @param portIndex
     *                  from 1 to n
     * @param swIndex
     *                  from 0-sw1, 1-sw2, 2-br1, 3-ap
     */
    public void clickPort(int portIndex, int swIndex) {
        logger.info(String.format("<portIndex: %s>-<swIndex: %s>", portIndex, swIndex));
        String xpath = getDevicePortIdXpath(swIndex);
        $$x(xpath).get(getPortIndex(portIndex, getPortNo(swIndex))).click();
        waitReady();
    }
    
    /**
     * @param portIndex
     *                  from 1 to n
     * @param devName
     *                  string to device name
     */
    public void clickPort(int portIndex, String devName) {
        logger.info(String.format("<portIndex: %s>-<devName: %s>", portIndex, devName));
        int devIndex = getDeviceType(devName);
        clickPort(portIndex, devIndex);
    }
    
    /**
     * @param portIndex
     *                  from 1 to n
     * @param swIndex
     *                  from 0-sw1, 1-sw2, 2-br1, 3-ap
     */
    public void clickPort(String portIndex, int swIndex) {
        logger.info(String.format("<portIndex-str: %s>-<swIndex: %s>", portIndex, swIndex));
        clickPort(Integer.parseInt(portIndex), swIndex);
    }
    
    /**
     * @param portIndex
     *                  from 1 to n
     * @param swIndex
     *                  from 0-sw1, 1-sw2, 2-br1, 3-ap
     */
    public SelenideElement getPort(int portIndex, int swIndex) {
        logger.info(String.format("<%s>-<%s>", portIndex, swIndex));
        String xpath = getDevicePortIdXpath(swIndex);
        return $$x(xpath).get(getPortIndex(portIndex, getPortNo(swIndex)));
    }
    
    /**
     * @param portIndex
     *                  from 1 to n return 2 switch port index
     */
    public List<SelenideElement> getPorts(int portIndex) {
        logger.info(String.format("<%s>", portIndex));
        String xpath1 = getDevicePortIdXpath(0);
        String xpath2 = getDevicePortIdXpath(1);
        waitElement(xpath1);
        List<SelenideElement> lsSe = new ArrayList<SelenideElement>();
        lsSe.add($$x(xpath1).get(getPortIndex(portIndex, getPortNo(0))));
        lsSe.add($$x(xpath2).get(getPortIndex(portIndex, getPortNo(1))));
        return lsSe;
    }
    
    /**
     * click all ports
     */
    public void clickAllPort() {
        logger.info(String.format("clickAllPort"));
        clickAllPort(0);
    }
    
    /**
     * @param swIndex
     *                click port on switch, from 0-sw1, 1-sw2, 2-br1, 3-ap
     */
    public void clickAllPort(int swIndex) {
        logger.info(String.format("<%s>", swIndex));
        int iPorts = getPortNo(swIndex);
        String xpath = getDevicePortIdXpath(0);
        for (SelenideElement se : $$x(xpath)) {
            se.click();
        }
    }
    
    /**
     * Get port class for all pages
     *
     * @param  portIndex
     *                   from 1 to n
     * @return           like colorGreen
     */
    public String getPortCls(int portIndex) {
        return getPortCls(portIndex, 0);
    }
    
    /**
     * Get port class for all pages
     *
     * @param  portIndex
     *                   from 1 to n
     * @param  swIndex
     *                   from 0-sw1, 1-sw2, 2-br1, 3-ap
     * @return           like colorGreen or ("active trunkPort", "active accessPort", "" in vlan)
     */
    public String getPortCls(int portIndex, int swIndex) {
        String xpath = getDevicePortIdXpath(swIndex);
        String sRet = $$x(xpath).get(getPortIndex(portIndex, getPortNo(swIndex))).getAttribute("class");
        logger.info(String.format("<%s>-<%s>", portIndex, sRet));
        return sRet;
    }
    
    /**
     * @param sTable
     *                     The ID string of table tbody with tr
     * @param sTableColumn
     *                     The Column of table to match for item
     * @param sItem
     *                     The text for match
     * @param sAction
     *                     the string to find edit/del etc.
     */
    public void editLine(String sTable, String sTableColumn, String sItem, String sAction) {
        int linePos = 0;
        ElementsCollection ecs;
        boolean bFound = false;
        
        // FIXME: sometime vlan render needs more time, hover wont work
        waitReady();
        logger.info(String.format(": <%s-%s-%s-%s>", sTable, sTableColumn, sItem, sAction));
        
        // get line that contains sLine
        for (SelenideElement el : getElements(sTableColumn)) {
            String txt;
            txt = getText(el);
            logger.info(String.format(": check for '%s'", txt));
            if (txt.contains(sItem) || sItem.equals("")) {
                bFound = true;
                break;
            }
            linePos += 1;
        }
        
        assertTrue(bFound, "line was not found");
        getElements(sTableColumn).get(linePos).hover();
        for (SelenideElement se : getElements(sAction)) {
            if (se.isDisplayed()) {
                se.click();
                break;
            }
        }
        sleepi(4);
        waitReady();
    }
    
    /**
     * @param sTable
     *                table tbody element
     * @param iColumn
     *                which column 1~n
     * @param sMatch
     *                text to be match in row
     * @param iImg
     *                which img 0~n
     */
    public void editLine(String sTable, int iColumn, String sMatch, int iImg) {
        int iPos = 1;
        boolean bFound = false;
        waitReady();
        for (String sText : getTextsTable(sTable, iColumn)) {
            if (sText.equals(sMatch)) {
                bFound = true;
                break;
            }
            iPos++;
        }
        
        assertTrue(bFound, "editLine: line must be existed: -> " + sMatch);
        String sRow = String.format("%s tr:nth-of-type(%d)", sTable, iPos);
        String sCell = String.format("%s td:last-child", sRow);
        for (int i = 0; i < 2; i++) {
            $(sCell).hover();
            int j = $(sRow).$$("img").size();
            if ((j > 0) && (iImg < j)) {
                $(sRow).$$("img").get(iImg).click();
                break;
            }
            waitReady();
        }
        sleepi(4);
        waitReady();
    }
    
    /**
     * @param sTable
     *               table tbody
     * @param iRow
     *               which row from 1~n
     * @param iImg
     *               which img from 0~n
     */
    public void editLine(String sTable, int iRow, int iImg) {
        String sRow = String.format("%s tr:nth-of-type(%d)", sTable, iRow);
        String sCell = String.format("%s td:last-child", sRow);
        $(sCell).hover();
        $(sRow).$$("img").get(iImg).click();
        sleepi(4);
        waitReady();
    }
    
    /**
     * @param  sTable
     *                The string to "table tbody"
     * @param  row
     *                from 1~n
     * @param  col
     *                from 1~n
     * @return        The full text of cell
     */
    public String getTextTable(String sTable, int row, int col) {
        return getText(String.format("%s tr:nth-of-type(%d) td:nth-of-type(%d)", sTable, row, col));
    }
    
    /**
     * @param  sTable
     *                string that point to page table "table tbody"
     * @param  sFind
     *                string to match on each line
     * @param  col
     *                which column to return value 1~n
     * @return        The full text of cell where one of cell in a row contains sFind
     */
    public String getTextTable(String sTable, String sFind, int col) {
        int iPos = 1;
        waitElement(sTable);
        for (String sLine : getTexts(sTable + " tr")) {
            if (sLine.contains(sFind))
                return getText(String.format("%s tr:nth-of-type(%d) td:nth-of-type(%d)", sTable, iPos, col));
            iPos++;
        }
        return "";
    }
    
    /**
     * @param  sTable
     *                The string to "table tbody"
     * @param  col
     *                from 1~n
     * @return        list of string for this col
     */
    public List<String> getTextsTable(String sTable, int col) {
        return getTexts(String.format("%s tr td:nth-of-type(%d)", sTable, col));
    }
    
    public List<String> getTextsTable(String sTable) {
        return getTexts(String.format("%s tr", sTable));
    }
    
    /**
     * @param el
     *                the css string for checkbox, to click selector will be next "i"/"span"
     * @param checked
     *                to checked or unchecked
     * @param isspan
     *                is i(false) or span (true)
     */
    public void setSelected(SelenideElement el, boolean checked, boolean isspan) {
        boolean sta = el.is(Condition.checked);
        logger.info(String.format("<%s>-<%s>-<%s>", el, sta, checked));
        String sa, saNew;
        
        if (checked != sta) {
            boolean isxpath = false;
            sa = el.getSearchCriteria();
            if (sa.contains("By.xpath")) {
                sa = sa.split(":")[1].trim();
                isxpath = true;
            }
            if (isspan) {
                if (isxpath) {
                    saNew = sa + "/following-sibling::span";
                } else {
                    saNew = sa + "+span";
                }
            } else {
                if (isxpath) {
                    saNew = sa + "/following-sibling::i";
                } else {
                    saNew = sa + "+i";
                }
            }
            if (isxpath) {
                click($x(saNew));
            } else {
                click($(saNew));
            }
        }
    }
    
    /**
     * @param el
     *                the checkbox to check via js click
     * @param checked
     *                to checked or unchecked
     */
    public void setSelected(SelenideElement el, boolean checked) {
        boolean sta = el.is(Condition.checked);
        if (checked != sta) {
            click(el, true);
        }
    }
    
    /**
     * @param id
     *                 The id (class with open or not) to lookup to expand page
     * @param nextSpan
     *                 whether to lookup next span
     */
    public void setExpand(String id, boolean nextSpan) {
        if (!$(id).getAttribute("class").toLowerCase().contains(" open")) {
            logger.info("click plus to expand");
            if (nextSpan) {
                $(id + " span:first-child").click();
            } else {
                $(id).click();
            }
        }
    }
    
    /**
     * @param id
     *                 The id (class with open or not) to lookup to expand page
     * @param nextSpan
     *                 whether to lookup next span
     * @param eIndex
     *                 which element to click 0 ~ more.
     */
    public void setExpand(String id, boolean nextSpan, int eIndex) {
        SelenideElement se = $$(id).get(eIndex);
        if (!se.getAttribute("class").toLowerCase().contains(" open")) {
            logger.info("click plus to expand");
            if (nextSpan) {
                se.find(By.cssSelector("i:last-child")).click();
            } else {
                se.click();
            }
        }
    }
    
    /**
     * @param checkId
     *                the element has open in class
     * @param clickId
     *                the element to click for open
     */
    public void setExpand(String checkId, String clickId) {
        if (!$(checkId).getAttribute("class").toLowerCase().contains(" open")) {
            logger.info("click plus to expand");
            $(clickId).click();
        }
        
    }
    
    /**
     * @param se
     *             SelenideElement to click
     * @param bMax
     *             true to set max, false to set min
     */
    public void setSlider(SelenideElement se, boolean bMax) {
        se.click();
        Actions actions = new Actions(WebportalParam.curWebDriver);
        for (int i = 0; i < 10; i++) {
            if (bMax) {
                actions.sendKeys(Keys.PAGE_UP).perform();
            } else {
                actions.sendKeys(Keys.PAGE_DOWN).perform();
            }
            sleep(300);
        }
    }
    
    /**
     * @param id
     *              The string to lookup element
     * @param value
     *              to value to set
     */
    public void setSlider(String id, String value) {
        Actions actions = new Actions(WebportalParam.curWebDriver);
        String sVal = String.format("%s .tooltip-inner", id);
        $(sVal).click();
        int toVal = Integer.parseInt(value);
        int iCheck = -1;
        while (!$(sVal).getText().equals(value)) {
            int iCurVal;
            sleep(300);
            try {
                iCurVal = Integer.parseInt($(sVal).getText());
            } catch (NumberFormatException e) {
                iCurVal = (int) Float.parseFloat($(sVal).getText());
            }
            logger.info(String.format("current value: %s, to value: %s", iCurVal, toVal));
            if (iCheck == iCurVal) {
                break;
            } else {
                iCheck = iCurVal;
            }
            if (iCurVal > toVal) {
                actions.sendKeys(Keys.ARROW_LEFT).perform();
            } else {
                actions.sendKeys(Keys.ARROW_RIGHT).perform();
            }
        }
    }
    
    /**
     * @param  id
     * @param  value
     *                for cmp
     * @param  times
     *                how many events to send
     * @param  upDown
     *                true to page up/down, false to arrow up/down
     * @return        the final value
     */
    public String setSlider(String id, String value, int times, boolean pageUpDown) {
        Actions actions = new Actions(WebportalParam.curWebDriver);
        String sVal = String.format("%s .tooltip-inner", id);
        $(sVal).click();
        int toVal = Integer.parseInt(value);
        int iCurVal;
        while (times > 0) {
            times -= 1;
            sleep(200);
            try {
                iCurVal = Integer.parseInt($(sVal).getText());
            } catch (NumberFormatException e) {
                iCurVal = (int) Float.parseFloat($(sVal).getText());
            }
            logger.info(String.format("current value: %s, to value: %s", iCurVal, toVal));
            if (iCurVal < toVal) {
                if (pageUpDown) {
                    actions.sendKeys(Keys.PAGE_UP).perform();
                } else {
                    actions.sendKeys(Keys.ARROW_UP).perform();
                }
            } else {
                if (pageUpDown) {
                    actions.sendKeys(Keys.PAGE_DOWN).perform();
                } else {
                    actions.sendKeys(Keys.ARROW_DOWN).perform();
                }
            }
        }
        
        try {
            iCurVal = Integer.parseInt($(sVal).getText());
        } catch (NumberFormatException e) {
            iCurVal = (int) Float.parseFloat($(sVal).getText());
        }
        logger.info("get value: " + iCurVal);
        
        return String.format("%s", iCurVal);
    }
    
    public void gotoAllLocations() {
        if (!getCurrentUrl().contains(URLParam.hrefaccount)) {
            open(WebportalParam.serverUrl + URLParam.hrefaccount);
        }
    }
    
    public void gotoMyDevices() {
        // TODO to support PRO
        if (!getCurrentUrl().contains("/#/dashboard/myDevices")) {
            open(WebportalParam.serverUrl + "#/dashboard/myDevices");
        }
    }
    
    public void gotoLocationWireSettings() {
        if (!getCurrentUrl().contains("/#/wired/")) {
            open(WebportalParam.serverUrl + URLParam.hrefWiredSetting);
        }
    }
    
    public String     sOrganizationFlag               = "#orgLocationDropdown";
    public String     sCurLocationElement             = "#headerLocName";
    public String     sCurOrganizationElement         = "#locationDropdown .display-inline";
    public String     sOrganizationLocationElement    = "#gridView .location-name";
    public String     sOrganizationLocationElementNew = "#OrgList .underline";
    public String     sOrganizationLocationElement1   = "#content.location-grid";
    ArrayList<String> lsLocationNetworks              = new ArrayList<String>();
    
    /**
     * @param  local
     *               if not found, open last location, open first location if null
     * @return
     */
    SelenideElement getLocationElement(String local) {
        String network;
        SelenideElement tc = null;
        
        waitReady();
        logger.info("wait for: " + local);
        try {
            waitElement(sOrganizationLocationElement);
        } catch (NoSuchElementException e) {
            logger.info("try refresh again");
            takess();
            refresh();
            waitElement(sOrganizationLocationElement);
        }
        
        // location page now has refresh func
        for (int tryi = 0; tryi < 2; tryi++) {
            ElementsCollection esc = $$(sOrganizationLocationElement);
            for (SelenideElement i : esc) {
                network = getText(i);
                if (network.equals("")) {
                    lsLocationNetworks.clear();
                    takess();
                    waitReady();
                    break;
                }
                lsLocationNetworks.add(getText(i));
                logger.info("Found and add network: " + network);
                if (((local == null) && (tc == null)) || ((local instanceof String) && local.equalsIgnoreCase(network))) {
                    logger.info("location matched");
                    tc = i;
                    break;
                }
            }
            if (tc != null) {
                break;
            }
        }
        
        if (tc == null) {
            logger.info("return first network");
            tc = $$(sOrganizationLocationElement).first();
        }
        return tc;
    }
    
    /**
     * @param  opt
     *             null to first location
     * @return     return all of locations
     */
    public ArrayList<String> getLocationCurrentLocation(String opt) {
        logger.info("start to open location: " + opt);
        SelenideElement tc = null;
        
        waitReady();
        String sURLCheck = "#/organization/details";
        // if we cannot see url like location or organization means we were already in location
        if (!(getCurrentUrl().contains(URLParam.hrefaccount) || getCurrentUrl().contains(sURLCheck)))
            return lsLocationNetworks;
            
        // if (!$(sLocationElement).exists()) {
        // open(sURLCheck, true);
        // }
        
        tc = getLocationElement(opt);
        if (tc != null) {
            try {
                click(tc, true);
            } catch (Throwable e) {
                logger.warning("not able to click, check page load");
                click(tc, false);
            }
            waitReady();
        }
        
        return lsLocationNetworks;
    }
    
    public ArrayList<String> getLocationMyDevices(String opt) {
        logger.info("starting...");
        ArrayList<String> networks = new ArrayList<String>();
        String network;
        String el = "//*[@id='divIpFilterMyDevices']//h3[contains(@id, 'hNwDevMyDevices')]";
        SelenideElement tc = null;
        
        waitElement(el);
        ElementsCollection esc = $$x(el);
        for (SelenideElement i : esc) {
            network = getText(i);
            networks.add(network);
            logger.info(network);
            if (((opt == null) && (tc == null)) || ((opt instanceof String) && opt.equals(network))) {
                tc = i;
            }
        }
        
        if (tc != null) {
            tc.click();
        }
        
        return networks;
    }
    
    /**
     * @param sNo
     *            a switch device
     */
    public void openADevice(String sNo) {
        DevicesDashPage ddp = new DevicesDashPage();
        ddp.enterDevicesSwitchSummary(sNo);
    }
    
    public void openOneBRDevice(boolean bySerialNo) {
        DevicesDashPage ddp = new DevicesDashPage();
        ddp.openBRDevice(bySerialNo);
    }
    
    public void openOBDevice() {
        DevicesDashPage ddp = new DevicesDashPage();
        ddp.openOB1();
    }
    
    public void gotoOrganization() {
        if (!getCurrentUrl().contains(URLParam.hreforganization)) {
            new OrganizationPage().gotoPage();
        }
    }
    
    public void gotoOrganizationLoction(String org, String loc) {
        WebCheck.checkUrl(URLParam.hreforganization);
        if (getCurrentUrl().contains(URLParam.hreforganization)) {
            new OrganizationPage().openOrg(org);
        }
        SelenideElement loc_ele = $x(String.format("//span[text()='%s']", loc));
        loc_ele.click();
        waitReady();
    }

    public void gotoLoction() {
        if (getCurrentUrl().contains(URLParam.hreforganization)) {
            new OrganizationPage().openOrg(WebportalParam.Organizations);
        }
        gotoLoction(WebportalParam.location1);
    }
    
    public void gotoLoctionOrbi() {
        if (getCurrentUrl().contains(URLParam.hreforganization)) {
            new OrganizationPage().openOrg(WebportalParam.Organizations);
        }
        gotoLoction(WebportalParam.location2);
    }
    public void gotoLoctionOrbi1() {
        if (getCurrentUrl().contains(URLParam.hreforganization)) {
            new OrganizationPage().openOrg(WebportalParam.Organizations);
        }
        gotoLoction(WebportalParam.location1);
    }
    
    public void gotoLoctionBR() {
        if (getCurrentUrl().contains(URLParam.hreforganization)) {
            new OrganizationPage().openOrg(WebportalParam.Organizations);
        }
        gotoLoction(WebportalParam.location1);
    }
    
    public void gotoLoction(String loc) {
        if (!$(sCurLocationElement).exists() || !getText($(sCurLocationElement)).equalsIgnoreCase(loc)) {
            if (!WebportalParam.enableDebug) {
                gotoAllLocation();
            }
            getLocationCurrentLocation(loc);
        }
    }
    
    public void gotoAllLocation() {
        if ($(sOrganizationFlag).exists()) {
            open(URLParam.hreforganization, true);
            new OrganizationPage().openOrg(WebportalParam.Organizations);
        } else {
            if (!getCurrentUrl().contains(URLParam.hrefaccount)) {
                open(URLParam.hrefaccount, true);
            } else {
                waitReady();
            }
        }
    }
    
    public void gotoLoctionvoucher(String loc) {
        SelenideElement organization = $(sOrganizationLocationElement);
        if(organization.exists()) {
            organization.click();
            waitReady();
        }
        SelenideElement location = $(By.xpath(String.format("//span[text()='%s']", loc)));
        location.click();
        waitReady();
    }
    
    public static String sInterfacePrefix;
    
    public static void routeBRCall(String className, String... args) {
        sInterfacePrefix = "firewall.webelements." + WebportalParam.DUTType;
        try {
            StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
            StackTraceElement e = stacktrace[2];
            String name = e.getMethodName();
            Object obj = Class.forName(sInterfacePrefix + className).newInstance();
            Class<?> cls = obj.getClass();
            Method m;
            
            if (args.length == 0) {
                m = cls.getDeclaredMethod(name);
                m.invoke(obj);
            } else if (args.length == 1) {
                m = cls.getDeclaredMethod(name, String.class);
                m.invoke(obj, args[0]);
            } else if (args.length == 2) {
                m = cls.getDeclaredMethod(name, String.class, String.class);
                m.invoke(obj, args[0], args[1]);
            } else if (args.length == 3) {
                m = cls.getDeclaredMethod(name, String.class, String.class, String.class);
                m.invoke(obj, args[0], args[1], args[2]);
            } else if (args.length == 4) {
                m = cls.getDeclaredMethod(name, String.class, String.class, String.class, String.class);
                m.invoke(obj, args[0], args[1], args[2], args[3]);
            } else
                throw new RuntimeException("FIXME");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * @param  keyName
     *                 Only search for top layer on keyName
     * @param  jsonObj
     *                 A handle from JSONReader
     * @return         return found value or full json if not
     */
    public static String getJsonKeyValue(String keyName, JSONReader jsonObj) {
        logger.info("lookup for: " + keyName);
        String key, keyVal = "", fulljson = "";
        Object val;
        if (jsonObj == null) {
            logger.warning("null obj");
            return "EMPTYFILE";
        }
        jsonObj.startObject();
        while (true) {
            key = jsonObj.readString();
            val = jsonObj.readObject();
            fulljson += val.toString();
            if (key.equals(keyName)) {
                keyVal = val.toString();
                break;
            }
            
            if (val instanceof JSONObject) {
                for (Object k : ((JSONObject) val).keySet()) {
                    if (((JSONObject) val).get(k) instanceof JSONObject) {
                        logger.info("TODO:look for next obj");
                        // jsonObj.readObject(((JSONObject) val).get(k));
                        // val = jsonObj.readObject();
                        break;
                    }
                    if (k.toString().equals(keyName)) {
                        keyVal = ((JSONObject) val).getString((String) k);
                        break;
                    }
                }
            } else if (val instanceof JSONArray) {
                Iterator<Object> it = ((JSONArray) val).iterator();
                while (it.hasNext()) {
                    String kk = it.next().toString();
                    if (kk.contains(keyName)) {
                        keyVal = kk;
                        break;
                    }
                }
            }
            
            if (keyVal != "") {
                System.out.println("objKey: " + keyName + ", objVal: " + keyVal);
                break;
            }
            
            if (!jsonObj.hasNext()) {
                logger.info("no next");
                break;
            }
        }
        
        // jsonObj.endObject();
        // jsonObj.close();
        if (keyVal == "") {
            logger.warning("return full text");
            return fulljson;
        }
        
        return keyVal;
    }

    /**
     * @param  jsonKey
     * @param  filePath
     * @return          find first entry with same key and return its value
     */
    public static String getJsonKeyValue(String jsonKey, String filePath) {
        String ret = "NOTFOUND";
        try {
            String json = readFile(filePath, StandardCharsets.US_ASCII);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(json);
            if (rootNode.findValue(jsonKey) != null)
                return rootNode.findValue(jsonKey).toString();
            else {
                logger.info("Error: not find key for: " + jsonKey);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ret;
    }
    
    public static String getJsonKeyValues(String jsonKey, String filePath) {
        String ret = "NOTFOUND";
        try {
            String json = readFile(filePath, StandardCharsets.US_ASCII);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(json);
            if (rootNode.findValue(jsonKey) != null)
                return rootNode.findValues(jsonKey).toString();
            else {
                logger.info("Error: not find key for: " + jsonKey);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * @param  dictKey
     *                   like lanSubnet
     * @param  keyItem
     *                   vlanID
     * @param  keyVal
     *                   11
     * @param  keyReturn
     *                   lanIP
     * @param  filePath
     * @return           in json file, return the value matched to keyReturn which keyVal & keyItem are same and under dictKey
     */
    public static String getJsonKeyValue(String dictKey, String keyItem, String keyVal, String keyReturn, String filePath) {
        String ret = "NOTFOUND";
        try {
            String json = readFile(filePath, StandardCharsets.US_ASCII);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(json);
            JsonNode keyNode = rootNode.findValue(dictKey);
            if (keyNode != null) {
                for (int i = 0; i < keyNode.size(); i++) {
                    if ((keyNode.get(i) != null) && keyNode.get(i).findValue(keyItem).toString().contains(keyVal))
                        return keyNode.get(i).findValue(keyReturn).toString();
                }
            } else {
                logger.info("Error: not find key for: " + dictKey);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ret;
    }

    public static Runnable myTelentMonitor = new Runnable() {
        @Override
        public void run() {
            logger.info("Start to monitoring telnet");
            SwitchTelnet.isTelnetStart = true;
            timerStart(SwitchTelnet.cliWaitTimeout);
            while (timerRunning()) {
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (!SwitchTelnet.isTelnetStart) {
                    break;
                }
            }
            if (SwitchTelnet.isTelnetStart) {
                logger.info("shutdownxx");
                if (!WebportalParam.enableDebug) {
                    new RunCommand().shutdownTelnet();
                }
            }
        }
    };
    
    public static Runnable myCurlMonitor = new Runnable() {
        @Override
        public void run() {
            logger.info("Start to monitoring curl");
            isCurlStart = true;
            timerStart(130);
            while (timerRunning()) {
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (!isCurlStart) {
                    break;
                }
            }
            if (isCurlStart) {
                logger.info("shutdownxx");
                if (!WebportalParam.enableDebug) {
                    new RunCommand().killProcess("curl.exe");
                }
            }
        }
    };
    
    public static Runnable myVPNClientMonitor = new Runnable() {
        @Override
        public void run() {
            logger.info("Start to monitoring vpn client");
            timerStart(20);
            isVpnClientStart = true;
            while (timerRunning()) {
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (!isVpnClientStart) {
                    break;
                }
            }
            if (isVpnClientStart) {
                logger.info("shutdownxx");
                if (!WebportalParam.enableDebug) {
                    new RunCommand().killProcess("InsightVPNClient.exe");
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    new RunCommand().killProcess("werfault.exe");
                }
            }
        }
    };
    
    static void myCMDMonitor(String exec, int timeout) {
        class OneShotTask implements Runnable {
            String exec;
            int    timeout;
            
            OneShotTask(String s, int t) {
                exec = s;
                timeout = t;
            }
            
            @Override
            public void run() {
                logger.info("Start to monitoring " + exec);
                isCmdStart = true;
                timerStart(timeout);
                while (timerRunning()) {
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    if (!isCmdStart) {
                        break;
                    }
                }
                if (isCmdStart) {
                    logger.info("shutdownxx");
                    if (!WebportalParam.enableDebug) {
                        new RunCommand().killProcess(exec);
                    }
                }
            }
        }
        
        Thread t = new Thread(new OneShotTask(exec, timeout));
        t.start();
    }
    
    static int     iGCheckPoint = 0;
    static boolean bGCheckPoint = false;
    
    /**
     * @param iVal
     *             make sure in each suites iVal is unique
     */
    public void setCheckPointStep(int iVal) {
        bGCheckPoint = false;
        iGCheckPoint = iVal;
        logger.info("set: " + iVal);
    }

    /**
     * @return current test case check point
     */
    public int getCheckPointStep() {
        logger.info("get: " + iGCheckPoint);
        return iGCheckPoint;
    }

    /**
     * @param bVal
     *             true for pass, false for fail
     */
    public void setCheckPointResult(boolean bVal) {
        logger.info("set: " + bVal);
        bGCheckPoint = bVal;
    }
    
    /**
     * @return true for pass, false for fail
     */
    public boolean getCheckPointResult() {
        iGCheckPoint = 0;
        return bGCheckPoint;
    }
    
    
    public void doSwitchCommandforDeviceDisconnect(int cmdIndex) {
        logger.info("Will generate a critical notification");
        SwitchTelnet st = new SwitchTelnet(WebportalParam.sw1IPaddress, WebportalParam.loginDevicePassword, true);
        if (cmdIndex == 1) {
            logger.info("try to disconncet sw");
            st.switchDisconnect();
            new Pause().seconds(60, "sleep for switch disconnect");
        } else if (cmdIndex == 2) {
            logger.info("try to connect sw");
            st.switchConnect();
            new Pause().seconds(60, "sleep for switch connect");
            waitDeviceOnline();
        }
        logger.info("try to disconnect the switch via command");
    }
}
