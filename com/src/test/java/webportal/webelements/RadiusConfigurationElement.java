package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$$x;

import java.util.logging.Logger;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

public class RadiusConfigurationElement extends MyCommonAPIs {
    Logger logger = Logger.getLogger("RadiusConfigurationElement");
    
    public SelenideElement    btnSave         = $(".in button:last-child");
    public String             optionMode      = ".in div label";
    public String             btnStatus       = "input[id*=radiuscheck]";
    public ElementsCollection btnAllSelectAll = $$("button[class=btn-default]");
    public ElementsCollection btnAllPortMode  = $$("button[data-target*=Mode]");

    /**
     * @param option
     *               from 0~2 (auto, auth, unauth)
     */
    public void selectMode(int option) {
        logger.info("option: " + option);
        waitElement(optionMode);
        click($$(optionMode).get(option));
    }
    
    public String getDeviceString(String devName) {
        return String.format("//h5[contains(text(),'%s')]/../../../..", devName);
    }
    
    /**
     * @param  devName
     * @param  portId
     *                 1~n
     * @return
     */
    public SelenideElement getPort(String devName, String portId) {
        return getDevicePort(getDeviceString(devName), portId);
    }
    
    /**
     * @param  devName
     * @param  port
     *                 0 - portmode, 1 - select all
     * @return
     */
    public SelenideElement getButton(String devName, int port) {
        return $$x(String.format("%s//button", getDeviceString(devName))).get(port);
    }

    public String getCheckBox(String devName) {
        return String.format("%s//input", getDeviceString(devName));
    }
    
}
