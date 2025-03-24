package webportal.weboperation;

import java.util.Map;
import java.util.logging.Logger;

import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DevicesApRadioAndChannelsPageElement;

public class DevicesApRadioAndChannelsPage extends DevicesApRadioAndChannelsPageElement {
    final static Logger logger = Logger.getLogger("DevicesApRadioAndChannelsPage");

    /**
     *
     */
    public DevicesApRadioAndChannelsPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon(URLParam.hrefDevicesApRadioAndChannelsPage);
        logger.info("init...");
    }

    public DevicesApRadioAndChannelsPage(boolean noPage) {
        // TODO Auto-generated constructor stub
        logger.info("init...");
    }

    public void checkFiveGHzTireDisplay() {
        if (!fiveGHzChannel.isDisplayed()) {
            openFiveGHzTier.click();
            MyCommonAPIs.sleepi(3);
        }
    }

    public void checkFiveGHzHighTireDisplay() {
        if (!fiveGHzHighChannel.isDisplayed()) {
            if(openFiveGHzHighTier1.exists())
            {
            openFiveGHzHighTier1.click();
            MyCommonAPIs.sleepi(3);
            }
            else
            {
                openFiveGHzHighTier2.click();
                MyCommonAPIs.sleepi(3);
            }
        }
    }

    public void configRadioAndChannel(Map<String, String> map) {
        if (map.containsKey("2.4GHz channel")) {
            twoPointFourGHzChannel.selectOption(map.get("2.4GHz channel"));
            MyCommonAPIs.sleepi(1);
        }
        if (map.containsKey("2.4GHz channel width")) {
            twoPointFourGHzChannelWidth.selectOption(map.get("2.4GHz channel width"));
            MyCommonAPIs.sleepi(1);
        }
        if (map.containsKey("2.4GHz output power")) {
            twoPointFourGHzOutputPower.selectOption(map.get("2.4GHz output power"));
            MyCommonAPIs.sleepi(1);
        }
        if (map.containsKey("5GHz channel")) {
            checkFiveGHzTireDisplay();
            fiveGHzChannel.selectOption(map.get("5GHz channel"));
            MyCommonAPIs.sleepi(1);
        }
        if (map.containsKey("5GHz channel width")) {
            checkFiveGHzTireDisplay();
            fiveGHzChannelWidth.selectOption(map.get("5GHz channel width"));
            MyCommonAPIs.sleepi(1);
        }
        if (map.containsKey("5GHz output power")) {
            checkFiveGHzTireDisplay();
            fiveGHzOutputPower.selectOption(map.get("5GHz output power"));
            MyCommonAPIs.sleepi(1);
        }
        if (openFiveGHzHighTier1.exists() || openFiveGHzHighTier2.exists()) {
            if (map.containsKey("5GHz high channel")) {
                checkFiveGHzHighTireDisplay();
                fiveGHzHighChannel.selectOption(map.get("5GHz high channel"));
                MyCommonAPIs.sleepi(1);
            }
            if (map.containsKey("5GHz high channel width")) {
                checkFiveGHzHighTireDisplay();
                fiveGHzHighChannelWidth.selectOption(map.get("5GHz high channel width"));
                MyCommonAPIs.sleepi(1);
            }
            if (map.containsKey("5GHz high output power")) {
                checkFiveGHzHighTireDisplay();
                fiveGHzHighOutputPower.selectOption(map.get("5GHz high output power"));
                MyCommonAPIs.sleepi(1);
            }
        }
        if (yesRunBtn.isDisplayed()) {
            yesRunBtn.click();
            MyCommonAPIs.sleepi(5);
        }
        saveBtn.click();
        MyCommonAPIs.waitReady();
    }

}
