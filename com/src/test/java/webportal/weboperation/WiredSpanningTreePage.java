package webportal.weboperation;

import java.util.logging.Logger;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import util.RunCommand;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.webelements.WiredSpanningTreeElement;

public class WiredSpanningTreePage extends WiredSpanningTreeElement {
    Logger logger = Logger.getLogger("WiredSpanningTreePage");

    public WiredSpanningTreePage() {
        logger.info("init...");
    }

    public void gotoPage() {
        open(URLParam.hrefSpanningTree, true);
    }

    /**
     * @return 0 - disable, 1 - stp, 2 - rstp
     */
    public int getSTPMode() {
        int iRet = 0;
        if (cbEnable.exists()) {
            if (!isEnable())
                return 0;
            else {
                if (lbSTPMode.getSelectedText().toLowerCase().equals("stp"))
                    return 1;
                else
                    return 2;
            }
        } else {
            for (SelenideElement se : rbSpanTreeMode) {
                if (se.is(Condition.checked))
                    return iRet;
                iRet++;
            }
        }
        return iRet;
    }
    
    public void enableRSTP() {
        setSTPMode(2, false, false);
    }

    /**
     * @param mode
     *                   0 - disable, 1 - stp, 2 - rstp
     * @param selectPort
     *                   true to select port 3
     * @param selectLag
     *                   true to select first lag (port 4)
     */
    public void setSTPMode(int mode, boolean selectPort, boolean selectLag) {
        int iRet = 0;
        logger.info("mode: " + mode + " selectPort: " + selectPort + " selectLag: " + selectLag);
        if (cbEnable.exists()) {
            if (mode == 0) {
                setSelected(cbEnable, false, true);
            } else if (mode > 0) {
                setSelected(cbEnable, true, true);
                clearAllPorts();
                clearAllLags();
                lbSTPMode.selectOption(mode - 1);
                waitReady();
                if (selectPort) {
                    clearAllPorts();
                    clickPort(3, 0);
                    clickPort(3, 1);
                }
                if (selectLag) {
                    for (SelenideElement se : cbLags) {
                        se.click();
                    }
                }
            }
        } else { // old page
            for (SelenideElement se : rbSpanTreeMode) {
                if (iRet == mode) {
                    se.click();
                    break;
                }
                iRet++;
            }
        }

        clickButton(0);
        if (btnNewOK.exists()) {
            click(btnNewOK);
        } else if (btnErrorOK.exists()) {
            click(btnErrorOK);
        } else {
            click(btnOK);
        }

        sleep(10, "wait stp deloy");
        if (WebportalParam.isRltkSW1 || WebportalParam.isRltkSW2) {
            sleep(30, "wait stp deloy for gs switch");
        }
        RunCommand.waitSWAlive(WebportalParam.sw1IPaddress);
        refresh();
    }
}
