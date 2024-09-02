package businessrouter.webpageoperation;

import java.awt.Robot;
import java.util.logging.Logger;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;

import businessrouter.webelements.BrAdvancedQosSetupElements;
import businessrouter.webelements.BrAllMenueElements;
import util.MyCommonAPIs;

public class BrAdvancedQosSetupPage extends BrAdvancedQosSetupElements {

    public BrAdvancedQosSetupPage() {
        // TODO Auto-generated constructor stub
    }

    final static Logger logger = Logger.getLogger("BrAdvancedQosSetupPage");
    boolean             result = false;

    public void OpenAdvancedQosSetupPage() {
        // open(LoginURL);
        logger.info("Open  Qos Setup Page");
        BrAllMenueElements BrAllMenueElements = new BrAllMenueElements();
        BrAllMenueElements.advanced.click();
        BrAllMenueElements.QoSSetup.click();
    }

    public void MouseWheel(int num) {
        logger.info("Mouse wheel start.");
        MyCommonAPIs.sleepi(10);
        try {
            Robot robot = new Robot();
            robot.mouseWheel(num);
        } catch (Exception e) {
            logger.info("");
        }
        logger.info("Mouse wheel done");
    }

    public void EnableOrDisableQoS(String EnableOrDisable) {
        logger.info("EnableOrDisableQoS Start");
       // MouseWheel(-100);
        ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", downloadspeedvalue);
        if (EnableOrDisable.contentEquals("Enable")) {
            if (!dynaminqosenable.isSelected()) {
                dynaminqosenable.selectRadio("on");
            }
        } else if (EnableOrDisable.contentEquals("Disable")) {
            if (dynaminqosenable.isSelected()) {
                dynaminqosenable.sendKeys(Keys.SPACE);
            }
        }
        MyCommonAPIs.sleepi(2);
        qosdynamicapplybutton.click();
        MyCommonAPIs.sleepi(2);
        if (dynaminqosenabledialogok.exists()) {
            dynaminqosenabledialogok.click();
        }
        logger.info("EnableOrDisableQoS Done");
    }

    public void DefineInternetBandwidth(String downloadvalue, String uploadvalue) {
        logger.info("DefineInternetBandwidth start");
        dynaminqosenable.scrollTo();
        if (!defineinternetbandwith.isSelected()) {
            defineinternetbandwith.selectRadio("2");
            defineinternetbandwidthdialogok.click();
        }
        downloadspeedvalue.clear();
        downloadspeedvalue.sendKeys(downloadvalue);
        uploadspeedvalue.clear();
        uploadspeedvalue.sendKeys(uploadvalue);
        Selenide.sleep(2000);
        qosdynamicapplybutton.click();
        logger.info("DefineInternetBandwidth done");
    }

    public void SelectSpeedtest() {
        logger.info("SelectSpeedtest start");
        dynaminqosenable.scrollTo();
        if (!detectinternetbandwith.isSelected()) {
            detectinternetbandwith.selectRadio("1");
        }
        qosdynamicapplybutton.click();
        logger.info("SelectSpeedtest done");
    }

    public boolean CheckDynamicQoS() {
        boolean result;
        logger.info("CheckDynamicQoS start");
        MouseWheel(-10);
        if (dynaminqosenable.isSelected()) {
            result = true;
        } else {
            result = false;
        }
        logger.info("CheckDynamicQoS done");
        return result;
    }

    public boolean CheckSpeedtest() {
        boolean result;
        logger.info("CheckSpeedtest start");
        MouseWheel(-10);
        if (detectinternetbandwith.isEnabled()) {
            result = true;
        } else {
            result = false;
        }
        logger.info("CheckSpeedtest done");
        return result;
    }

    public boolean CheckDefineInternetBandwidth() {
        boolean result;
        logger.info("CheckDefineInternetBandwidth start");
        MouseWheel(-10);
        if (defineinternetbandwith.isEnabled()) {
            result = true;
        } else {
            result = false;
        }
        logger.info("CheckDefineInternetBandwidth done");
        return result;
    }

    public boolean CheckDefineInternetBandwidthValue(String downloadvalue, String uploadvalue) {
        boolean result;
        if (Double.valueOf(downloadspeedvalue.getValue()).intValue() == Integer.valueOf(downloadvalue)
                && Double.valueOf(uploadspeedvalue.getValue()).intValue() == Integer.valueOf(uploadvalue)) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    public boolean CheckErrorDialog() {
        boolean result;
        MyCommonAPIs.sleepi(2);
        if (speedvalueerrordialogtext.exists()) {
            if (speedvalueerrordialogtext.getText().equals("You must enter the download and upload speed before enabling Quality of Service.")) {
                result = true;
            } else if (speedvalueerrordialogtext.getText().equals(
                    "The value you entered in the Download Speed (Mbps) field or Upload Speed (Mbps) field is out of range. Enter a value between 0.10 and 1000.00.")) {
                result = true;
            } else {
                result = false;
            }
        } else {
            result = false;
        }
        return result;
    }
}
