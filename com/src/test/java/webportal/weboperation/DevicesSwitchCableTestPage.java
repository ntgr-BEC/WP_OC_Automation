/**
 *
 */
package webportal.weboperation;

import java.util.logging.Logger;

import util.MyCommonAPIs;
import webportal.publicstep.WebCheck;
import webportal.webelements.DevicesSwitchCableTestElement;

/**
 * @author xuchen
 */
public class DevicesSwitchCableTestPage extends DevicesSwitchCableTestElement {
    public Logger        logger;
    public CommonAPIUnit commonApi = new CommonAPIUnit();
    
    /**
     *
     */
    public DevicesSwitchCableTestPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon("#/devices/switch/cabelTest");
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }
    
    public void reloadDevice() {
        reloadIcon.click();
        yesReload.click();
    }
    
    public void clickPorts(String[] ports) {
        if (ports != null) {
            for (int i = 0; i < ports.length; i++) {
                portChoice(ports[i]).click();
            }
        }
    }
    
    public void clicktestButton() {
        testModCabeltest.click();
        MyCommonAPIs.sleepi(4);
        yesTestTheCable.click();
        MyCommonAPIs.sleep(20000);
    }
    
    public void testCableTest(String[] ports) {
        clickPorts(ports);
        clicktestButton();
        takess();
    }
    
    public String getCableTestResult(String port) {
        String code;
        if (txtNoResult.exists()) {
            code = getText(txtNoResult);
            return code;
        } else {
            String elementString = testResult(port).getSearchCriteria();
            logger.info(String.format("get testResult elementString:%s", elementString));
            code = commonApi.getText(elementString);
            logger.info(String.format("get result:%s", code));
        }
        return code;
    }
    
    @Override
    public String getPageErrorMsg() {
        logger.info(String.format("cableTestAlertString:%s", cabletestAlertString));
        String code = commonApi.getText(cabletestAlertString);
        logger.info(String.format("getPageErrorMsg:%s", code));
        return code;
    }
    
}
