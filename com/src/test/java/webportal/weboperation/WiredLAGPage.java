/**
 *
 */
package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertTrue;

import java.util.Map;
import java.util.logging.Logger;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import util.SwitchTelnet;
import webportal.param.CommonDataType.EditLagData;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.WiredLAGPageElement;

/**
 * @author Netgear
 */
public class WiredLAGPage extends WiredLAGPageElement {
    /**
     *
     */
    Logger         logger;
    static boolean cleanLagCli_flag = false;

    public WiredLAGPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon(URLParam.hrefWiredLag);
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    public WiredLAGPage(boolean noPage) {
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("initex...");
    }

    public void gotoLagPage() {
        open(URLParam.hrefWiredLag, true);
        MyCommonAPIs.waitElement(addLagIcon);
    }

    public void gotoLagEditPage() {
        click(btnEdit);
        sleep(10, "gotoLagEditPage");
    }

    public void gotoLagEditPage(String lagName) {
        logger.info(String.format("open lag with: %s", lagName));
        $x(String.format(sBtnEdit, lagName)).click();
        MyCommonAPIs.waitReady();
    }

    public void editLag(EditLagData eldData) {
        logger.info(String.format("edit lag with: %s", eldData.name));
        eldData.Dump();

        gotoLagEditPage(eldData.name);
        // editLagName.setValue(eldData.name);
        setSelected(editLagEnable, eldData.bEnable, true);
        setSelected(editLagStatic, eldData.bStatic, true);
        for (SelenideElement se : getPorts(Integer.parseInt(WebportalParam.sw1LagPort1))) {
            if ((eldData.bPortLag1 && !se.getAttribute("class").contains("Blue"))
                    || (!eldData.bPortLag1 && se.getAttribute("class").contains("Blue"))) {
                se.click();
            }
        }

        for (SelenideElement se : getPorts(Integer.parseInt(WebportalParam.sw1LagPort2))) {
            if ((eldData.bPortLag2 && !se.getAttribute("class").contains("Blue"))
                    || (!eldData.bPortLag2 && se.getAttribute("class").contains("Blue"))) {
                se.click();
            }
        }

        click(saveLagButton);
        getPageErrorMsg();
    }

    /**
     * @param lagName
     * @param isSelect2Ports
     *                        true to select lag1 and lag2 ports, false to select only one of them based on isSelect2ndport
     * @param isSelect2ndport
     *                        true to select lag2, false to select lag1
     */
    public void addLag(String lagName, boolean isSelect2Ports, boolean isSelect2ndport) {
        addLagIcon.click();
        MyCommonAPIs.waitReady();
        MyCommonAPIs.sleepi(20);
        waitElement(".common-useplanblk");
        ElementsCollection ecs = switchesChoice;
        assertTrue(ecs.size() > 1, "There must be 2 switches");
        selectSwitchsOnCreateLag();

        clickButton(3);
        addLAGName.setValue(lagName);
        waitReady();
        setSelected(cbEnableAdmin, bEnableAdmin, true);
        setSelected(cbEnableStatic, bEnableStatic, true);
        click(saveLagName);

        if (isSelect2Ports) {
            clickPort(Integer.parseInt(WebportalParam.sw1LagPort1), 0);
            clickPort(Integer.parseInt(WebportalParam.sw2LagPort1), 1);

            clickPort(Integer.parseInt(WebportalParam.sw1LagPort2), 0);
            clickPort(Integer.parseInt(WebportalParam.sw2LagPort2), 1);
        } else {
            if (isSelect2ndport) {
                clickPort(Integer.parseInt(WebportalParam.sw1LagPort2), 0);
                clickPort(Integer.parseInt(WebportalParam.sw2LagPort2), 1);
            } else {
                clickPort(Integer.parseInt(WebportalParam.sw1LagPort1), 0);
                clickPort(Integer.parseInt(WebportalParam.sw2LagPort1), 1);
            }
        }

        takess("check which ports are selected for adding lag.");
        clickButton(0);

        if (!$(sPopButtonCss).isDisplayed()) {
            // lag id will be refreshed
            if (getPageErrorMsg().length() == 0) {
                if (!addLagIcon.isDisplayed()) {
                    refresh();
                }
            }
            sleepsync();
        }
    }

    /**
     * add a lcap mode lag with port lag1 on it, for postsanity, select port 3
     *
     * @param lagName
     * @param devName1
     * @param devName2
     * @param portId
     *                 TODO
     */
    public void addLag(String lagName, String devName1, String devName2, String portId) {
        click(addLagIcon);
        waitElement(sSwitchesChoice);
        waitReady();
        selectSwitchsOnCreateLag(devName1, devName2);
        
        clickButton(3);
        addLAGName.setValue(lagName);
        setSelected(cbEnableStatic, false, true);
        click(saveLagName);
        if (WebportalParam.enaTwoSwitchMode) {
            clickPortOnSwitch(devName1, WebportalParam.sw1LagPort1);
            clickPortOnSwitch(devName2, WebportalParam.sw2LagPort1);
        } else {
            clickPortOnSwitch(devName1, portId);
            clickPortOnSwitch(devName2, portId);
        }
        clickButton(0);
    }
    
    public void addLag(String lagName, Map<String, String[]> portArray) {
        for (String ss : portArray.keySet()) {
            for (String sss : portArray.get(ss)) {
                System.out.println(ss + ": " + sss);
            }
        }
        click(addLagIcon);
        boolean bFoundSW = false;
        waitElement(sSwitchesChoice);
        waitReady();
        selectSwitchsOnCreateLag();

        clickButton(3);
        addLAGName.setValue(lagName);
        click(saveLagName);
        for (String key : portArray.keySet()) {
            String deviceName = key;
            String[] portName = portArray.get(key);
            for (int i = 0; i < portName.length; i++) {
                clickPortOnSwitch(deviceName, portName[i]);
                MyCommonAPIs.sleep(2 * 1000);
            }
        }
        MyCommonAPIs.sleep(3000);
        clickButton(0);
        MyCommonAPIs.sleepi(30);
    }

    /**
     * @param  lagName
     * @param  portIndex
     * @param  adminMode
     * @param  lcapType
     *                   true to Dyn, false to Stat
     * @return
     */
    public boolean checkLagCli(String lagName, int portIndex, boolean adminMode, boolean lcapType) {
        boolean bRet = true;
        logger.info(String.format(": <%s>-<%s>-<%s>-%s>", lagName, portIndex, adminMode, lcapType));
        if (WebportalParam.isRltkSW1) {
            String lines;
            SwitchTelnet st = new SwitchTelnet(WebportalParam.sw1IPaddress, WebportalParam.loginDevicePassword, true);
            st.setEnable();
            lines = st.sendCLICommandClear("show running-config interfaces LAG 1-2", lagName);
            if (!lines.contains(lagName)) {
                bRet = false;
                logger.info("match error: lag name not found");
            }
            
            String portNo;
            if(WebportalParam.sw1Model.contains("MS")){
                if(portIndex < 5)
                    portNo = String.format("mg%s", portIndex);
                else if(portIndex > 4 && portIndex < 9)
                    portNo = String.format("xmg%s", portIndex);
                else
                    portNo = String.format("xg%s", portIndex);
            }
            else{
                portNo = String.format("g%s", portIndex);}
            String lagNo = "1";
            lines = st.sendCLICommandClear("show running-config interfaces " + portNo, null);
            if (lines.contains("lag")) {
                String[] lsLine = lines.split("\n");
                lagNo = lsLine[2].split("\\s+")[2];            
            }
            if (!lines.contains(portNo)) {
                bRet = false;
                logger.info("match error: port not found");
            }
            if (lcapType) {
                if (!lines.contains("static")) {
                    bRet = false;
                    logger.info("match error: lcap mode check error1");
                }
            } else {
                if (lines.contains("static")) {
                    bRet = false;
                    logger.info("match error: lcap mode check error2");
                }
            }

            lines = st.sendCLICommandClear("show running-config interfaces LAG " + lagNo, null);
            if (adminMode && lines.contains("shutdown")) {
                bRet = false;
                logger.info("match error: admin mode check error1");
            } else if (!adminMode && !lines.contains("shutdown")) {
                bRet = false;
                logger.info("match error: admin mode check error2");
            }
        } else {
            String[] sRet = getCmdOutputLines("show port-channel all", false);
            for (String str : sRet) {
                if (str.contains(lagName)) {
                    if(WebportalParam.sw1Model.contains("M4")){
                        if (!str.contains(String.format("1/0/%s", portIndex))) {
                            bRet = false;
                            logger.info("match error: 001");
                        }
                        
                    }else {
                    if (!str.contains(String.format("g%s", portIndex))) {
                        bRet = false;
                        logger.info("match error: 001");
                    }
                    }
                    if (adminMode) {
                        if (!str.contains("En.")) {
                            bRet = false;
                            logger.info("match error: 002");
                        }
                    } else {
                        if (!str.contains("Dis.")) {
                            bRet = false;
                            logger.info("match error: 003");
                        }
                    }
                    if (lcapType) {
                        if (!str.contains("Stat")) {
                            bRet = false;
                            logger.info("match error: 004");
                        }
                    } else {
                        if (!str.contains("Dyn")) {
                            bRet = false;
                            logger.info("match error: 005");
                        }
                    }
                    break;
                }
            }
        }
        return bRet;
    }

    public boolean checkLag(String lagId, int portIndex, boolean adminMode, boolean lcapType) {
        boolean bRet = true;
        String m, s;

        boolean bsw = false;
        if (WebportalParam.isRltkSW1 || WebportalParam.isRltkSW2) {
            // will be check in checkLagCli
        } else {
            for (int i = 0; i < 2; i++) {
                logger.info(String.format("%d: %s-%s-%s-%s-%s", i, bsw, lagId, portIndex, adminMode, lcapType));
                s = getCmdOutput(String.format("show port-channel %s", lagId), bsw);
                if (adminMode) {
                    if (MyCommonAPIs.matches(s, String.format("Admin Mode\\\\.+ +Disabled"))) {
                        bRet = false;
                        logger.info("match error: 001");
                    }
                } else {
                    if (MyCommonAPIs.matches(s, String.format("Admin Mode\\\\.+ +Enabled"))) {
                        bRet = false;
                        logger.info("match error: 002");
                    }
                }

                if (lcapType) {
                    if (MyCommonAPIs.matches(s, String.format("Type\\\\.+ +Static"))) {
                        bRet = false;
                        logger.info("match error: 003");
                    }
                } else {
                    if (MyCommonAPIs.matches(s, String.format("Type\\\\.+ +Dynamic"))) {
                        bRet = false;
                        logger.info("match error: 004");
                    }
                }

                if (!s.contains(String.format("g%s", portIndex))) {
                    bRet = false;
                    logger.info("match error: 005");
                }
                bsw = !bsw;
            }
        }
        return bRet;
    }

    /**
     *
     */
    public void deleteLag() {
        // TODO Auto-generated method stub
        logger.info("removing lag start...");
        waitReady();
        try {
            while (firstEditButton.exists()) {
                getLagList();
                click(firstEditButton);
                click(deleteLagButton);
                MyCommonAPIs.sleep(2 * 1000);
                click(yesContinueButton);
                logger.info("removed lag");
                waitReady();
            }
        } catch (Throwable e) {
            e.printStackTrace();
            takess();
        }
        if (!WebportalParam.enableBatch) {
            sleep(30, "wait lag to be available");
        }
        takess("check all lag should be removed");
    }

    /**
     * delete 2 lag from cli on port 4/5
     */
    public void deleteLagCli() {
        if (!cleanLagCli_flag) {
            cleanAllLagCli();
        } else {
            String cmd1 = String.format(
                    "config;interface %s;deleteport lag 1;deleteport lag 2;interface %s;deleteport lag 1;deleteport lag 2;exit;port-channel name lag 1 ch1;port-channel name lag 2 ch2",
                    WebportalParam.getSwitchLag(false, false), WebportalParam.getSwitchLag(false, true));
            String cmd2 = String.format(
                    "config;interface %s;no lag;exit;interface %s;no lag;exit;interface LAG 1;no name;exit;interface LAG 2;no name",
                    WebportalParam.getSwitchLag(false, false), WebportalParam.getSwitchLag(false, true));
            if (WebportalParam.isRltkSW1) {
                getCmdOutput(cmd2, false);
            } else {
                getCmdOutput(cmd1, false);
            }
            if (WebportalParam.enaTwoSwitchMode) {
                String cmd3 = String.format(
                        "config;interface %s;deleteport lag 1;deleteport lag 2;interface %s;deleteport lag 1;deleteport lag 2;exit;port-channel name lag 1 ch1;port-channel name lag 2 ch2",
                        WebportalParam.getSwitchLag(true, false), WebportalParam.getSwitchLag(true, true));
                String cmd4 = String.format(
                        "config;interface %s;no lag;exit;interface %s;no lag;exit;interface LAG 1;no name;exit;interface LAG 2;no name",
                        WebportalParam.getSwitchLag(true, false), WebportalParam.getSwitchLag(true, true));
                if (WebportalParam.isRltkSW2) {
                    getCmdOutput(cmd4, true);
                } else {
                    getCmdOutput(cmd3, true);
                }
            }
            logger.info("removed lag in cli");
        }
    }

    /**
     * clean lag1 and lag2 on all ports
     */
    public void cleanAllLagCli() {
        if (cleanLagCli_flag)
            return;
        String cmdsw = "config;";
        for (int i = 1; i <= WebportalParam.getSwitchPortNumber(WebportalParam.sw1Model); i++) {
            if (WebportalParam.isRltkSW1) {
                cmdsw += String.format("interface %s;no lag;", WebportalParam.getSwitchPort(WebportalParam.sw1Model, i));
            } else {
                cmdsw += String.format("interface %s;deleteport lag 1;deleteport lag 2;", WebportalParam.getSwitchPort(WebportalParam.sw1Model, i));
            }
        }
        if (WebportalParam.isRltkSW1) {
            cmdsw += "exit;interface LAG 1;no name;exit;interface LAG 2;no name";
        } else {
            cmdsw += "exit;port-channel name lag 1 ch1;port-channel name lag 2 ch2";
        }
        getCmdOutput(cmdsw, false);

        if (WebportalParam.enaTwoSwitchMode) {
            cmdsw = "config;";
            for (int i = 1; i <= WebportalParam.getSwitchPortNumber(WebportalParam.sw2Model); i++) {
                if (WebportalParam.isRltkSW2) {
                    cmdsw += String.format("interface %s;no lag;", WebportalParam.getSwitchPort(WebportalParam.sw2Model, i));
                } else {
                    cmdsw += String.format("interface %s;deleteport lag 1;deleteport lag 2;", i);
                }
            }
            if (WebportalParam.isRltkSW2) {
                cmdsw += "exit;interface LAG 1;no name;exit;interface LAG 2;no name";
            } else {
                cmdsw += "exit;port-channel name lag 1 ch1;port-channel name lag 2 ch2";
            }
            getCmdOutput(cmdsw, true);
        }
        cleanLagCli_flag = true;
        logger.info("cleal lag1/2 for all ports in cli");
    }

    // add by Dallas
    public void addLag(String lagName, String lagPort) {
        addLagIcon.click();
        MyCommonAPIs.waitReady();
        waitElement(".common-useplanblk");
        $x("//div[@class='planblk']/div/../../..").click();

        clickButton(3);
        addLAGName.setValue(lagName);
        waitReady();
        setSelected(cbEnableAdmin, bEnableAdmin, true);
        setSelected(cbEnableStatic, bEnableStatic, true);
        click(saveLagName);

        String ele = String.format("//li[@class=\"titleCol\"]/ancestor::div[@class=\"box-shadow margin-top-10 no-padding m-b-20\"]//span[text()='%s']", lagPort);
        $x(ele).click();

        takess("check which ports are selected for adding lag.");
        clickButton(0);

        if (!$(sPopButtonCss).isDisplayed()) {
            // lag id will be refreshed
            if (getPageErrorMsg().length() == 0) {
                if (!addLagIcon.isDisplayed()) {
                    refresh();
                }
            }
            sleepsync();
        }
    }

    public boolean checkLagIsExist(String lagName) {
        boolean result = false;
        String ele = String.format("//span[contains(text(),'%s')]", lagName);
        if ($x(ele).exists()) {
            result = true;
            logger.info(lagName + " existed.");
        }
        return result;
    }

}
