package orbi.weboperation;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.logging.Logger;

import com.codeborne.selenide.Selenide;

import webportal.param.WebportalParam;
import orbi.webelements.OrbiAdministrationFirmwareUpdateElement;
import orbi.webelements.OrbiAllMenueElements;
//import webportal.weboperation.MyCommonAPIs;
import util.MyCommonAPIs;

public class OrbiAdministrationFirmwareUpdatePage extends OrbiAdministrationFirmwareUpdateElement {
    final static Logger logger = Logger.getLogger("OrbiAdministrationFirmwareUpdatePage");

    public void OpenFirmwareUpdatePage() {
        logger.info("Open Firmware Update Page"); 
        if (!WebportalParam.arch.equals("new")) {
            Selenide.switchTo().frame("topframe");
        }
        OrbiAllMenueElements OrbiAllMenueElements = new OrbiAllMenueElements();
        OrbiAllMenueElements.advanced.click();
        MyCommonAPIs.sleepi(5);
        if (!WebportalParam.arch.equals("new")) {
            Selenide.switchTo().parentFrame();
        }
        if (!OrbiAllMenueElements.FirmwareUpdate.isDisplayed()) {
            OrbiAllMenueElements.Administration.click();
            MyCommonAPIs.sleepi(3);
        }
        OrbiAllMenueElements.FirmwareUpdate.click();
        MyCommonAPIs.sleepi(5);
        logger.info("Open Firmware Update Page Done"); 
    }

    public boolean FirmwareUpdate(String filepath) {
        boolean result = true;
        Robot robot;
        if (!WebportalParam.arch.equals("new")) {
            Selenide.switchTo().frame("formframe");
        }
        logger.info("Start Firmware Update");
        manualupdate.click();
        MyCommonAPIs.sleepi(1);
        firmwarefilename.sendKeys(filepath);
        
        MyCommonAPIs.sleepi(5);
        firmwareuploadbutton.click();
        MyCommonAPIs.sleepi(10);
//        if(WebportalParam.arch.equals("new")) {
           try{
               Selenide.switchTo().alert().accept();
               MyCommonAPIs.sleepi(3);
           } catch(Throwable e) {
               System.out.println("Failed to click alert in new arch");
           }
//        }
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
//        MyCommonAPIs.sleepi(200);
        if (!WebportalParam.arch.equals("new")) {
            Selenide.switchTo().parentFrame();
        }
        return result;
    }
    public boolean FirmwareDowngrade(String filepath) {
        boolean result = true;
        Robot robot;
        if (!WebportalParam.arch.equals("new")) {
            Selenide.switchTo().frame("formframe");
        }
        logger.info("Start Firmware Update");
        manualupdate.click();
        firmwarefilename.sendKeys(filepath);

        MyCommonAPIs.sleepi(5);
        firmwareuploadbutton.click();
        MyCommonAPIs.sleepi(10);
        Selenide.confirm();
        MyCommonAPIs.sleepi(10);
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
        MyCommonAPIs.sleepi(150);
        if (!WebportalParam.arch.equals("new")) {
            Selenide.switchTo().parentFrame();
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
    public boolean FirmwareUpdateToLowimage(String filepath) {
        boolean result = true;
        Robot robot;
        if (!WebportalParam.arch.equals("new")){
            Selenide.switchTo().frame("formframe");
        }
        logger.info("FirmwareUpdateToLowimage start");
        manualupdate.click();
        MyCommonAPIs.sleepi(10);
        firmwarefilename.sendKeys(filepath);
        MyCommonAPIs.sleepi(5);
        firmwareuploadbutton.click();
        MyCommonAPIs.sleepi(10);
        Selenide.confirm();
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
        MyCommonAPIs.sleepi(180);
        if (!WebportalParam.arch.equals("new")) {
            Selenide.switchTo().parentFrame();
        }
        return result;
    }
    public boolean FirmwareUpdate2(String filepath) {
        boolean result = true;
        Robot robot;
        if (!WebportalParam.arch.equals("new")) {
            Selenide.switchTo().frame("formframe");
        }
        logger.info("Start Firmware Update");
        manualupdate.click();
        //firmwarefilename.sendKeys(filepath);
        Selenide.executeJavaScript("document.getElementById(\"browse\").click()");
        //firmwarebrowsebutton.s
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
       // logger.info("Select the upgrade file success.");
        MyCommonAPIs.sleepi(5);
        firmwareuploadbutton.click();
        MyCommonAPIs.sleepi(10);
        Selenide.confirm();
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
        MyCommonAPIs.sleepi(180);
        if (!WebportalParam.arch.equals("new")) {
            Selenide.switchTo().parentFrame();
        }
        return result;
    }

}
