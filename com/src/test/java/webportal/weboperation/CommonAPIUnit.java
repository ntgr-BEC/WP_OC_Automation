
package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import util.MyCommonAPIs;
import util.Pause;
import util.RunCommand;
import util.SwitchTelnet;
import webportal.param.WebportalParam;

public class CommonAPIUnit {
    public static int   timeout_element = 30 * 1000;
    static String       loader          = "[class='loaderContainer']";
    private static int  sw1Index        = 0;
    private static int  sw2Index        = 0;
    final static Logger logger          = Logger.getLogger("CommonAPIUnit");
    
    public CommonAPIUnit() {
        logger.info("init...");
    }
    
    public void sleep(int secs) {
        logger.info(String.format("sleep for: %d(s)", secs));
        new Pause().seconds(secs);
    }
    
    public void sleep(int secs, String coms) {
        logger.info(String.format("sleep for %s: %d(s)", coms, secs));
        new Pause().seconds(secs);
    }
    
    public String pageSource() {
        return WebDriverRunner.getWebDriver().getPageSource();
    }
    
    public boolean matches(String line, String regex) {
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
    
    private String convertToNormalSelector(String e) {
        if (e.startsWith("By."))
            return e.split(": ")[1];
        return e;
    }
    
    public SelenideElement getSE(String el) {
        el = convertToNormalSelector(el);
        if (el.startsWith("//") || el.startsWith("(//"))
            return $x(el);
        else
            return $(el);
    }
    
    public SelenideElement getSE(SelenideElement el) {
        return el;
    }
    
    public ElementsCollection getSEs(String el) {
        el = convertToNormalSelector(el);
        if (el.startsWith("//") || el.startsWith("(//"))
            return $$x(el);
        else
            return $$(el);
    }
    
    public void waitReady() {
        logger.info(String.format("waitReady..."));
        if ($(loader).isDisplayed()) {
            $(loader).waitWhile(Condition.visible, timeout_element);
        } else {
            sleep(2);
        }
    }
    
    public void waitElement(String el) {
        logger.info(String.format("waitElement: %s.", el));
        getSE(el).waitUntil(Condition.exist, timeout_element);
    }
    
    public void waitElementNot(String el) {
        logger.info(String.format("waitElementNot: %s.", el));
        getSE(el).waitWhile(Condition.exist, timeout_element);
    }
    
    public String getCmdOutput(String cmd) {
        logger.info(String.format("getCmdOutput: %s", cmd));
        SwitchTelnet st = new SwitchTelnet(WebportalParam.sw1IPaddress, WebportalParam.loginDevicePassword);
        String sRet = st.getCLICommand(cmd);
        return sRet;
    }
    
    public String getCmdOutputShowRunningConfig() {
        SwitchTelnet st = new SwitchTelnet(WebportalParam.sw1IPaddress, WebportalParam.loginDevicePassword);
        String sRet = st.readFull("show running-config");
        logger.info(String.format("getCmdOutputShowRunningConfig: %s", sRet));
        return sRet;
    }
    
    /**
     * @param  cmd
     *                    the cli to telnet
     * @param  notContain
     *                    false to search text, true not.
     * @return
     */
    public String waitCmdReady(String cmd, boolean notContain) {
        logger.info(String.format("waitCmdReady: %s-%s", cmd, notContain));
        SwitchTelnet st = new SwitchTelnet(WebportalParam.sw1IPaddress, WebportalParam.loginDevicePassword);
        String sRet;
        if (notContain) {
            sRet = st.checkExample(cmd, true);
        } else {
            sRet = st.checkExample(cmd);
        }
        
        sleep(4, "wait cli command to complete");
        return sRet;
    }
    
    /**
     * @param cmdIndex
     *                 1 = reboot,
     */
    public void doSwitchCommand(int cmdIndex) {
        logger.info(String.format("doSwitchCommand: %s", cmdIndex));
        SwitchTelnet st = new SwitchTelnet(WebportalParam.sw1IPaddress, WebportalParam.loginDevicePassword);
        if (cmdIndex == 1) {
            System.out.println("try to reboot sw");
            st.switchReboot();
            System.out.println("sleep for 30s");
            new Pause().seconds(30);
            RunCommand.waitSWAlive(WebportalParam.sw1IPaddress);
        }
    }
    
    public void setText(String el, String text) {
        logger.info(String.format("setText: %s-%s", el, text));
        SelenideElement ec = getElement(el);
        ec.clear();
        ec.setValue(text);
    }
    
    public String getText(String el) {
        logger.info(String.format("getText: %s", el));
        SelenideElement ec = getElement(el);
        String text = ec.text();
        logger.info(String.format("getText: %s", text));
        return text;
    }
    
    public List<String> getTexts(String el) {
        List<String> lsRet = new ArrayList<String>();
        ElementsCollection ecs = getElements(el);
        lsRet = ecs.texts();
        logger.info(String.format("getTexts: %s", lsRet.toString()));
        return lsRet;
    }
    
    public SelenideElement getElement(String el) {
        ElementsCollection ecs = getElements(el);
        logger.info(String.format("getElement: %s", ecs.size()));
        return ecs.first();
    }
    
    public SelenideElement getLastElement(String el, boolean isLast) {
        ElementsCollection ecs = getElements(el);
        logger.info(String.format("getElement: %s", ecs.size()));
        return ecs.last();
    }
    
    public ElementsCollection getElements(String el) {
        waitElement(el);
        logger.info(String.format("getElements: %s", el));
        return getSEs(el);
    }
    
    public String getPageErrorMsg() {
        String code = getText("#showNotification div");
        logger.info(String.format("getPageErrorMsg: %s", code));
        return code;
    }
    
    /**
     * @param sTable
     *                     The ID string of table
     * @param sTableColumn
     *                     The Column of table to match for item
     * @param sItem
     *                     The text for match
     * @param sAction
     *                     the string to find edit/del etc.
     */
    public void lineDoAction(String sTable, String sTableColumn, String sItem, String sAction) {
        int linePos = 0;
        ElementsCollection ecs;
        
        logger.info(String.format("lineDoAction: %s-%s-%s-%s\n", sTable, sTableColumn, sItem, sAction));
        // $("thead th:last-child").click();
        MyCommonAPIs.sleep(2000);
        // get line that contains sLine
        for (SelenideElement el : getElements(sTableColumn)) {
            String txt;
            txt = el.getText();
            logger.info(String.format("lineDoAction: check for '%s'", txt));
            if (txt.contains(sItem)) {
                break;
            }
            linePos += 1;
        }
        logger.info(String.format("lineDoAction: linePos:'%d'", linePos));
        
        // make sure last column is shown for clicking
        
        getElements(sTable).get(linePos).hover();
        // FIXME if first item does not have such el like remove, all later elements will be based on -1, so let's move
        // to previous one
        SelenideElement seTo;
        ecs = getElements(sAction);
        seTo = ecs.get(linePos);
        if (!seTo.isDisplayed() && (linePos > 0)) {
            seTo = ecs.get(--linePos);
        }
        seTo.click();
        sleep(4);
        waitReady();
    }
    
    public void editLine(String sTable, String sTableColumn, String sItem, String sAction) {
        lineDoAction(sTable, sTableColumn, sItem, sAction);
    }
    
    /**
     * @param el
     *                the css string for checkbox, checkbox will be next "i"/"span"
     * @param checked
     *                to checked or unchecked
     * @param isspan
     *                is i or span
     */
    public void setSelected(SelenideElement el, boolean checked, boolean isspan) {
        boolean sta = el.is(Condition.checked);
        logger.info(String.format("setSelected: %s-%s-%s", el, sta, checked));
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
                $x(saNew).click();
            } else {
                $(saNew).click();
            }
        }
    }
    
    public void open(String uri) {
        System.out.printf("starting: open %s\n", uri);
        waitReady();
        Selenide.open(uri);
        waitReady();
    }
    
    public void open(String uri, boolean needPrefix) {
        System.out.printf("starting: open %s\n", uri);
        waitReady();
        if (needPrefix) {
            uri = WebportalParam.serverUrl + uri;
        }
        Selenide.open(uri);
        waitReady();
    }
    
    /**
     * @param  swIndex
     *                   sw0 - 1, sw1 - 2
     * @param  pageIndex
     *                   1 - vlan page
     * @return
     */
    public int getNetworkIndex(int swIndex, int pageIndex) {
        String sEle;
        int swPos;
        logger.info(String.format("getNetworkIndex: %s-%s", swIndex, pageIndex));
        if (sw1Index == 0) {
            System.out.println("getNetworkIndex: init");
            if (pageIndex == 1) {
                swPos = 0;
                sEle = ".titleCol h5";
                for (String se : getTexts(sEle)) {
                    swPos += 1;
                    if (se.equals(WebportalParam.sw1deveiceName)) {
                        sw1Index = swPos;
                        continue;
                    }
                    if (WebportalParam.enaTwoSwitchMode && se.equals(WebportalParam.sw2deveiceName)) {
                        sw2Index = swPos;
                        continue;
                    }
                }
            }
        }
        System.out.printf("sw 1 index: %d\n", sw1Index);
        System.out.printf("sw 2 index: %d\n", sw2Index);
        if (swIndex == 1)
            return sw1Index;
        else
            return sw2Index;
    }
    
    public void gotoMyDevices() {
        open(WebportalParam.serverUrl + "#/dashboard/myDevices");
    }
    
    public void gotoLocationWireSettings() {
        open(WebportalParam.serverUrl + "#/wired/VLAN");
    }
    
    public ArrayList<String> getLocationMyLocations(String opt) {
        System.out.println("starting: getLocationMyLocations");
        ArrayList<String> networks = new ArrayList<String>();
        String network;
        String el = "//DIV[@id=\"gridView\"]//p[contains(@class, \"font\")]/span[string-length( text()) > 0]";
        SelenideElement tc = null;
        
        waitElement(el);
        ElementsCollection esc = $$x(el);
        for (SelenideElement i : esc) {
            network = i.getText();
            networks.add(network);
            System.out.println(network);
            if (((opt == null) && (tc == null)) || ((opt instanceof String) && opt.equals(network))) {
                tc = i;
            }
        }
        
        if (tc != null) {
            tc.click();
        }
        
        return networks;
    }
    
    public ArrayList<String> getLocationMyDevices(String opt) {
        System.out.println("starting: getLocationMyDevices");
        ArrayList<String> networks = new ArrayList<String>();
        String network;
        String el = "//*[@id='divIpFilterMyDevices']//h3[contains(@id, 'hNwDevMyDevices')]";
        SelenideElement tc = null;
        
        waitElement(el);
        ElementsCollection esc = $$x(el);
        for (SelenideElement i : esc) {
            network = i.getText();
            networks.add(network);
            System.out.println(network);
            if (((opt == null) && (tc == null)) || ((opt instanceof String) && opt.equals(network))) {
                tc = i;
            }
        }
        
        if (tc != null) {
            tc.click();
        }
        
        return networks;
    }
    
    public void gotoLoction() {
        getLocationMyLocations(null);
    }
    
    public void gotoLoction(String loc) {
        getLocationMyLocations(loc);
    }
}
