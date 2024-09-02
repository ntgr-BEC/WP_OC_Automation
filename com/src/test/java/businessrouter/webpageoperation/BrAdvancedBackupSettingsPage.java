package businessrouter.webpageoperation;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.logging.Logger;

import com.codeborne.selenide.Selenide;

import businessrouter.webelements.BrAdvancedBackupSettingsElements;
import businessrouter.webelements.BrAllMenueElements;
//import webportal.weboperation.MyCommonAPIs;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;

public class BrAdvancedBackupSettingsPage extends BrAdvancedBackupSettingsElements {
    final static Logger logger = Logger.getLogger("BrAdvancedBackupSettingsPage");

    public void OpenBackupSettingsPage() {
        BrAllMenueElements BrAllMenueElements = new BrAllMenueElements();
        logger.info("Open Backup Settings page");
        BrAllMenueElements.advanced.click();
        MyCommonAPIs.sleepi(5);
        if (WebportalParam.DUTType.contentEquals("BR500")) {
            BrAllMenueElements.BackupSettings.click();
        }else if(WebportalParam.DUTType.contentEquals("BR100")) {
            System.out.println("dddddddddd324234211111111111111111");
            BrAllMenueElements.BackupSettingsbr100.click();
        }
        
    }

    public boolean SaveSetting(String filepath) {
        boolean result;
        result = true;
        Robot robot;
        logger.info("Backup configuration.");
        backupbutton.click();
        try {
            StringSelection stringSelection = new StringSelection(filepath);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
            MyCommonAPIs.sleepi(2);
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            MyCommonAPIs.sleepi(2);
            robot.keyPress(KeyEvent.VK_ENTER);
            MyCommonAPIs.sleepi(2);
            robot.keyPress(KeyEvent.VK_LEFT);
            MyCommonAPIs.sleepi(2);
            robot.keyPress(KeyEvent.VK_ENTER);
        } catch (AWTException e) {
            logger.info("Backup configuration fail.");
            result = false;
        }
        logger.info("Backup configuration success.");
        return result;
    }

    public void DefaultDevice() {
        logger.info("Start default device.");
        Selenide.refresh();
        factorydefaultbutton.click();
        MyCommonAPIs.sleepi(3);
        if (factoryapplybutton.exists()) {
            factoryapplybutton.click();
            MyCommonAPIs.sleepi(100);
        } else {
            logger.info("Default device fail.");
        }
        Selenide.close();
    }

    public void RestoreSetting(String filepath) {
        Robot robot;
        logger.info("Restore configuration.");
        while (true) {
            MyCommonAPIs.sleepi(10);
            restorebrowsebutton.click();
            try {
                StringSelection stringSelection = new StringSelection(filepath);
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
                MyCommonAPIs.sleepi(2);
                robot = new Robot();
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_V);
                MyCommonAPIs.sleepi(5);
                robot.keyPress(KeyEvent.VK_ENTER);
            } catch (AWTException e) {
                logger.info("Restore configuration fail.");
            }
            MyCommonAPIs.sleepi(5);
            restorbutton.click();
            MyCommonAPIs.sleepi(5);
            if (dialogyesbutton.exists()) {
                dialogyesbutton.click();
            }
            MyCommonAPIs.sleepi(10);
            if (restorbutton.exists()) {
                Selenide.refresh();
            } else {
                break;
            }
        }
    }

    public boolean CheckRestoreSuccess() {
        boolean result;

        MyCommonAPIs.sleepi(2);
        if (!backupbutton.exists()) {
            MyCommonAPIs.sleepi(10);
            if (backupbutton.exists()) {
                logger.info("Restore empty file fail.");
                result = true;
            } else if (!backupbutton.exists()) {
                logger.info("Restore current settings success.");
                result = true;
            } else {
                logger.info("Restore current settings fail.");
                result = false;
            }
        } else {
            logger.info("Restore current settings fail.");
            result = false;
        }
        return result;
    }

    public boolean CheckRestorWrongMessage() {
        boolean result;

        System.out.println(restorewrongmessage.exists());
        MyCommonAPIs.sleepi(10);
        if (backupbutton.exists()) {
            logger.info("Restore wrong settings fail.");
            result = true;
        } else {
            logger.info("Restore wrong settings success.");
            result = false;
        }
        return result;
    }
    
    public void RestoreSetting1(String filepath) {
        Robot robot;
        logger.info("Restore configuration.");
       
            MyCommonAPIs.sleepi(10);
            restorebrowsebutton.click();
            try {
                StringSelection stringSelection = new StringSelection(filepath);
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
                MyCommonAPIs.sleepi(2);
                robot = new Robot();
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_V);
                MyCommonAPIs.sleepi(5);
                robot.keyPress(KeyEvent.VK_ENTER);
            } catch (AWTException e) {
                logger.info("Restore configuration fail.");
            }
            MyCommonAPIs.sleepi(5);
            restorbutton.click();
 
        
    } 
    
    public void RestoreEmptySetting(String filepath) {
        Robot robot;
        logger.info("Restore configuration.");
     
            MyCommonAPIs.sleepi(10);
            restorebrowsebutton.click();
            try {
                StringSelection stringSelection = new StringSelection(filepath);
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
                MyCommonAPIs.sleepi(2);
                robot = new Robot();
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_V);
                MyCommonAPIs.sleepi(5);
                robot.keyPress(KeyEvent.VK_ENTER);
            } catch (AWTException e) {
                logger.info("Restore configuration fail.");
            }
            MyCommonAPIs.sleepi(5);
            restorbutton.click();
            MyCommonAPIs.sleepi(5);
            if (dialogyesbutton.exists()) {
                dialogyesbutton.click();
            }
           
    }

}
