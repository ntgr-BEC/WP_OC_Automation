package orbi.weboperation;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.logging.Logger;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;

import orbi.webelements.OrbiSatelliteFirmwareUpdateElement;
import webportal.param.WebportalParam;
import orbi.webelements.OrbiSatelliteAllMenuElements;
//import webportal.weboperation.MyCommonAPIs;
import util.MyCommonAPIs;

public class OrbiSatellitenFirmwareUpdatePage extends OrbiSatelliteFirmwareUpdateElement {
    final static Logger logger = Logger.getLogger("OrbiAdministrationFirmwareUpdatePage");

    public void OpenFirmwareUpdatePage() {
        logger.info("Open Firmware Update Page");
        // Selenide.switchTo().frame("topframe");
        OrbiSatelliteAllMenuElements OrbiSatelliteAllMenuElements = new OrbiSatelliteAllMenuElements();
        OrbiSatelliteAllMenuElements.firmwareupdate.click();
        MyCommonAPIs.sleepi(5);
        // Selenide.switchTo().parentFrame();
        logger.info("Open Firmware Update Page Done");
    }

    public void FirmwareUpdate(String filepath) {
        boolean result = true;
        // Selenide.switchTo().frame("formframe");
        logger.info("Start Firmware Update");
        firmwarefilename.sendKeys(filepath);
        MyCommonAPIs.sleepi(1);
        firmwareuploadbutton.click();
        MyCommonAPIs.sleepi(15);
        if (!WebportalParam.arch.equals("new")) {
            if (firmwareuploadyesbutton.is(Condition.visible) && result) {
                logger.info("Click yes button.");
                MyCommonAPIs.sleepi(2);
                firmwareuploadyesbutton.click();
                MyCommonAPIs.sleepi(5);
            }
        } else {
            if (firmwareuploadyesbuttonsxk50.is(Condition.visible) && result) {
                logger.info("Click yes button.");
                MyCommonAPIs.sleepi(2);
                firmwareuploadyesbuttonsxk50.click();
                MyCommonAPIs.sleepi(5);
            }
            if (firmwareuploadyesbutton.is(Condition.visible) && result) {
                logger.info("Click yes button.");
                MyCommonAPIs.sleepi(2);
                firmwareuploadyesbutton.click();
                MyCommonAPIs.sleepi(5);
            }
        }
//        MyCommonAPIs.sleepi(240);
        // Selenide.switchTo().parentFrame();
        // return result;
    }

    public boolean Checkfirmwareversion(String filepath) {
        boolean result = true;
        logger.info("Start check Firmware version");
        MyCommonAPIs.sleepi(8);
        String UIfirmwareversion = "";
        if (!WebportalParam.arch.equals("new")) {
            UIfirmwareversion = firmwareversion.getText();
        }else {
            UIfirmwareversion = firmwareversionsxk50.getText();
        }
        System.out.println(UIfirmwareversion);
        // logger.info("Select the upgrade file success.");

        if (filepath.indexOf(UIfirmwareversion) == -1) {
            result = false;
        }
        return result;
    }

    public boolean FirmwareUpdateIncorrectImage(String filepath) {
        boolean result = true;
        logger.info("Start Firmware Update");
        firmwarefilename.sendKeys(filepath);
        MyCommonAPIs.sleepi(5);
        if (firmwareuploadbutton.isEnabled()) {
            // logger.info("Select the upgrade file success.");
            firmwareuploadbutton.click();
        }
        MyCommonAPIs.sleepi(5);
        String incorrectfirmwaremsgtext = "This firmware file is incorrect! Please get the firmware file again and make sure it is the correct firmware for this product.";
        String incorrecttext2 = "Please assign the correct file. The file format is *.img";
        String msg = "";
        if (!WebportalParam.arch.equals("new")) {
            result = incorrectfirmwaremsg.getText().contains(incorrectfirmwaremsgtext);
            if (result) {
                OKbutton.click();
            }
        } else {
            System.out.println(incorrectfirmwaremsgsxk50.getText());
            if (filepath.contains("img")) {
                result = incorrectfirmwaremsgsxk50.getText().contains(incorrectfirmwaremsgtext);
            } else {
                result = incorrectfirmwaremsgsxk50.getText().contains(incorrecttext2);
            }
            if (result) {
                OKbuttonsxk50.click();
            }
        }
        MyCommonAPIs.sleepi(3);
        result = firmwareuploadbutton.exists() && result;
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

}
