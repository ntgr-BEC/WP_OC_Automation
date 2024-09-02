package orbi.weboperation;

import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Logger;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.conditions.*;

import webportal.param.WebportalParam;
import orbi.webelements.OrbiAdvancedHomeElements;
import orbi.webelements.OrbiAllMenueElements;
import util.MyCommonAPIs;

public class OrbiAdvancedHomePage extends OrbiAdvancedHomeElements {

    final static Logger logger = Logger.getLogger("OrbiAdministrationNtpSettingsPage");

    public void OpenAdvancedHomePage() {
        OrbiAllMenueElements OrbiAllMenueElements = new OrbiAllMenueElements();
        logger.info("Open Advanced Home Page!");
        if (!WebportalParam.arch.equals("new")) {
            Selenide.switchTo().frame("topframe");
        }else if (!OrbiAllMenueElements.advanced.exists()) {
            Selenide.switchTo().frame("contentframe");
        }
        OrbiAllMenueElements.advanced.click();
        MyCommonAPIs.sleepi(5);
        if (!WebportalParam.arch.equals("new")) {
            Selenide.switchTo().parentFrame();
        }
        OrbiAllMenueElements.AdvancedHome.click();
        logger.info("OpenAdvancedHomePage Done!");
        MyCommonAPIs.sleepi(3);

    }

    public void rebootOrbiPro() {
        logger.info("Reboot Orbi Pro system.");
        if (!WebportalParam.arch.equals("new")) {
            Selenide.switchTo().frame("formframe");
        }
        rebootbutton.click();
        yesbutton.click();
        if (!WebportalParam.arch.equals("new")) {
            Selenide.switchTo().parentFrame();
        }
        MyCommonAPIs.sleepi(160);
    }

    
}
