/**
 *
 */
package webportal.weboperation;

import java.util.logging.Logger;

import com.codeborne.selenide.Condition;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DevicesSwitchPortMirrorElement;

/**
 * @author xuchen
 */
public class DevicesSwitchPortMirrorPage extends DevicesSwitchPortMirrorElement {
    public Logger        logger;
    public CommonAPIUnit commonApi = new CommonAPIUnit();

    /**
     *
     */
    public DevicesSwitchPortMirrorPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon("#/devices/switch/portMirroring");
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    public void configPortMirrorMode(boolean mode) {
        portMirrorMode.waitUntil(Condition.exist, 10000);
        boolean ischecked = portMirrorMode.is(Condition.checked);
        logger.info(String.format("portMirrorMode: %s-%s-%s", portMirrorMode.getSearchCriteria(), ischecked, mode));
        if (ischecked != mode) {
            portMirrorOnOff.click();
            MyCommonAPIs.sleep(3000);
        }
    }

    public void reloadDevice() {
        reloadIcon.click();
        yesReload.click();
    }

    public void clickSrcPorts(String[] ports) {
        if (ports != null) {
            for (int i = 0; i < ports.length; i++) {
                char lastChar = ports[i].charAt(ports[i].length() - 1);
                String lastChar1 = Character.toString(lastChar);
                System.out.println(lastChar1);
                srcportChoice(lastChar1).click();
            }
        }
    }

    public void clickDestPorts(String[] ports) {
        if (ports != null) {
            for (int i = 0; i < ports.length; i++) {
                char lastChar = ports[i].charAt(ports[i].length() - 1);
                String lastChar1 = Character.toString(lastChar);
                System.out.println(lastChar1);
                destportChoice(lastChar1).click();
            }
        }
    }

    public void clickapplyButton() {
        takess("check mirror setting");
        applyButton.click();
        sleepsync();
       
    }

    public void configPortMirrorPorts(boolean mode, String[] srcports, String[] dstports) {
        configPortMirrorMode(mode);
        if (mode) {
            clickSrcPorts(srcports);
            clickDestPorts(dstports);
        }
        clickapplyButton();
    }

    @Override
    public String getPageErrorMsg() {
        logger.info(String.format("portMirrorAlertString:%s", portMirrorAlertString));
        String code = WebportalParam.getNLocText(commonApi.getText(portMirrorAlertString));
        logger.info(String.format("getPageErrorMsg:%s", code));
        return code;
    }

}
