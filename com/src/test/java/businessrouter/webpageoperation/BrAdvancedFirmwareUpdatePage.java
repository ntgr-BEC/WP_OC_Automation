package businessrouter.webpageoperation;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.logging.Logger;

import com.codeborne.selenide.Selenide;

import businessrouter.webelements.BrAdvancedFirmwareUpdateElements;
import businessrouter.webelements.BrAllMenueElements;
//import webportal.weboperation.MyCommonAPIs;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;

public class BrAdvancedFirmwareUpdatePage extends BrAdvancedFirmwareUpdateElements {
    final static Logger logger = Logger.getLogger("BrAdvancedFirmwareUpdatePage");

    public void OpenFirmwareUpdatePage() {
        BrAllMenueElements BrAllMenueElements = new BrAllMenueElements();
        logger.info("Open Firmware Update page");
        BrAllMenueElements.advanced.click();
        MyCommonAPIs.sleepi(10);
        if (WebportalParam.DUTType.contentEquals("BR500")) {
            BrAllMenueElements.FirmwareUpdate.click();
        } else if(WebportalParam.DUTType.contentEquals("BR100")) {
            BrAllMenueElements.FirmwareUpdateBr100.click();           
        }
       
    }

    public boolean FirmwareUpdate(String filepath) {
        boolean result = true;
        Robot robot;
        logger.info("Start Firmware Update");
        firmwarebrowsebutton.click();
        try {
            StringSelection stringSelection = new StringSelection(filepath);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
            MyCommonAPIs.sleepi(2);
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            MyCommonAPIs.sleepi(2);
            robot.keyPress(KeyEvent.VK_ENTER);
        } catch (AWTException e) {
            logger.info("Select the upgrade file failed.");
            result = false;
        }
        logger.info("Select the upgrade file success.");
        MyCommonAPIs.sleepi(5);
        firmwareuploadbutton.click();
        MyCommonAPIs.sleepi(20);
        if (dialogyesbutton.exists() && result) {
            logger.info("Click yes button.");
            dialogyesbutton.click();
            if (firmwareuploadyesbutton.exists()) {
                MyCommonAPIs.sleepi(2);
                firmwareuploadyesbutton.click();
            }
            MyCommonAPIs.sleepi(10);
            if (!firmwareuploadyesbutton.exists()) {
                logger.info("Firmware Update success.");
            } else {
                logger.info("Firmware Update failed.");
                result = false;
            }
        } else if (firmwareuploadyesbutton.exists() && result) {
            logger.info("Click yes button.");
            if (firmwareuploadyesbutton.exists()) {
                MyCommonAPIs.sleepi(2);
                firmwareuploadyesbutton.click();
                MyCommonAPIs.sleepi(10);
                if (!firmwareuploadyesbutton.exists()) {
                    logger.info("Firmware Update success.");
                } else {
                    logger.info("Firmware Update failed.");
                    result = false;
                }
            } else {
                logger.info("Firmware Update failed.");
                result = false;
            }
        } else if (!firmwareuploadbutton.exists()) {
            logger.info("Firmware Update success.");
        } else {
            logger.info("Firmware Update failed.");
            result = false;
        }
        return result;
    }

    public void FirmwareDownload() {
        downloadfirmware.click();

        Selenide.switchTo().window(1);
        MyCommonAPIs.sleepi(20);
        downloadbutton.scrollTo();
        MyCommonAPIs.sleepi(5);
        downloadbutton.click();

        /* 需要补充页面下载部分 */

        MyCommonAPIs.sleepi(5);
        Selenide.switchTo().window(0);

    }

    public boolean FirmwareCheckNew(String filename) {
        boolean result = false;
        downloadfirmware.click();

        Selenide.switchTo().window(1);
        MyCommonAPIs.sleepi(20);
        downloadbutton.scrollTo();
        MyCommonAPIs.sleepi(5);
        downloadbutton.click();

        /* 需要补充页面下载部分 */

        MyCommonAPIs.sleepi(5);
        Selenide.switchTo().window(0);

        return result;
    }

    public boolean CancelButtonCheck(String filepath) {
        boolean result = true;
        Robot robot;
        logger.info("Start check cancel button");
        firmwarebrowsebutton.click();
        try {
            StringSelection stringSelection = new StringSelection(filepath);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
            MyCommonAPIs.sleepi(2);
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            MyCommonAPIs.sleepi(2);
            robot.keyPress(KeyEvent.VK_ENTER);
        } catch (AWTException e) {
            logger.info("Select the upgrade file failed.");
            result = false;
        }
        // MyCommonAPIs.sleepi(10);
        // String js = "document.getElementById(\"upgradeFile\").value";
        // Selenide.executeJavaScript(js,"upgradeFile");
        // System.out.println(Selenide.executeJavaScript(js));
        // MyCommonAPIs.sleepi(10);
        firmwarecancelbutton.click();
        MyCommonAPIs.sleepi(10);
        if (firmwarefilename.exists()) {
            logger.info("Cancel button can work.");
        } else {
            logger.info("Cancel button can't work.");
            result = false;
        }
        return result;
    }

    public void CheckFirmwareVersion(String afterimage, String filepath) {

        System.out.println(firmwareversion.text());
        out: if (firmwareversion.text().equals(afterimage)) {
            break out;
        } else {
            FirmwareUpdate(filepath);           
            MyCommonAPIs.sleepi(180);
        }
    }
}
