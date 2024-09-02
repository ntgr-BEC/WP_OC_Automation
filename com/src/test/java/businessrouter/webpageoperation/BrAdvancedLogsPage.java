package businessrouter.webpageoperation;

import java.util.logging.Logger;

import com.codeborne.selenide.Selenide;

import businessrouter.webelements.BrAdvancedLogsElements;
import businessrouter.webelements.BrAllMenueElements;
//import webportal.weboperation.MyCommonAPIs;
import util.MyCommonAPIs;

public class BrAdvancedLogsPage extends BrAdvancedLogsElements {
    final static Logger logger = Logger.getLogger("BrAdvancedLogsPage");

    public void OpenLogsPage() {
        BrAllMenueElements BrAllMenueElements = new BrAllMenueElements();
        logger.info("Open Logs page");
        BrAllMenueElements.advanced.click();
        MyCommonAPIs.sleepi(5);
        BrAllMenueElements.Logs.click();
    }

    public void clearLogoutput() {
        logger.info("Clear logs.");
        logclearbutton.click();
        if (logdialogtext.exists()) {
            while (true) {
                if (logdialogtext.getText().equals("Clear Log failed.")) {
                    // OpenLogsPage();
                    MyCommonAPIs.sleepi(3);
                    logclearbutton.click();
                } else {
                    break;
                }
            }
        }
        MyCommonAPIs.sleepi(3);
        // Selenide.refresh();
    }

    public void refreshLogoutput() {
        logger.info("Refresh logs.");
        logrefreshbutton.click();
        MyCommonAPIs.sleepi(3);
    }

    public void selectcheckbox(int options) {
        logger.info("Select checkbox.");
        Selenide.refresh();
        int[] indexs = {
                1, 2, 3, 4, 5, 6, 7
        };
        for (int i = 0; i < 7; i++) {
            if (options == indexs[i]) {
                continue;
            } else {
                logcheckboxnotcheck(indexs[i]);
            }
            MyCommonAPIs.sleepi(3);
        }
        logapplybutton.click();
        logger.info("Select checkbox successful.");
    }

    public void disablecheckbox(int options) {
        logger.info("Disable checkbox start.");
        Selenide.refresh();
        logcheckboxnotcheck(options);
        logapplybutton.click();
        logger.info("Select checkbox successful.");
    }

    public void selectallheckbox() {
        logger.info("Select all checkbox.");
        Selenide.refresh();
        logcheckboxallcheck();
        logapplybutton.click();
        logger.info("Select all checkbox successful.");
    }

    public boolean checkLogoutput(String log) {
        boolean result;
        logger.info("Check logs.");
        if (logcontents.exists()) {
            String logs = logcontents.innerText();
            System.out.println(logs);
            if (logs.indexOf(log) != -1) {
                logger.info("Display logs output.");
                result = true;
            } else {
                logger.info("Don't display logs output.");
                result = false;
            }
        } else {
            logger.info("Don't display logs output.");
            result = false;
        }
        return result;
    }
}
