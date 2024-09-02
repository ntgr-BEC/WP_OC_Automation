package orbi.weboperation;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.codeborne.selenide.Selenide;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;

import webportal.param.WebportalParam;
import orbi.webelements.OrbiAllMenueElements;
import orbi.webelements.OrbiAdvancedmDNSElements;
import util.MyCommonAPIs;

public class OrbiAdvancedmDNSPage extends OrbiAdvancedmDNSElements {

    final static Logger logger = Logger.getLogger("OrbiAdvancedmDNSPage");

    public void OpenAdvancedmDNSPage() {
        logger.info("Open Advanced mDNS Page");
        if (!WebportalParam.arch.equals("new")) {
            Selenide.switchTo().frame("topframe");
        }
        OrbiAllMenueElements OrbiAllMenueElements = new OrbiAllMenueElements();
        OrbiAllMenueElements.advanced.click();
        MyCommonAPIs.sleepi(5);
        if (!WebportalParam.arch.equals("new")) {
            Selenide.switchTo().parentFrame();
        }
        if (!OrbiAllMenueElements.mDNS.isDisplayed()) {
            OrbiAllMenueElements.AdvancedSetup.click();
            MyCommonAPIs.sleepi(3);
        }
        OrbiAllMenueElements.mDNS.click();
        MyCommonAPIs.sleepi(5);
        logger.info("Open Advanced mDNS Page Done"); 
    }
    
    public void EnablemDNS() {
        logger.info("Enable mDNS Start");
        if (!WebportalParam.arch.equals("new")) {
            Selenide.switchTo().frame("formframe");
        }
        if (enablemDNS.getAttribute("checked") != "checked") {
            MyCommonAPIs.clickElementIdViaJs(enablemDNSid); 

        }
        if (!WebportalParam.arch.equals("new")) {
            Selenide.switchTo().parentFrame();
        }
        logger.info("Enable mDNS Done");
    }
    
    public void AddPolicy(String name, String shared, String src, String dst) {
        logger.info("Add policy Start");
        if (!WebportalParam.arch.equals("new")) {
            Selenide.switchTo().frame("formframe");
            addbtn.click();
            MyCommonAPIs.sleepi(1);
            policynameinput.sendKeys(name);
            sharedservicesselect.selectOptionByValue(shared);
            srcvlanselect.selectOptionContainingText(src);
            dstvlanselect.selectOptionContainingText(dst);
            MyCommonAPIs.sleepi(1);
            Selenide.switchTo().parentFrame();
        }else {
            addbtn.click();
            MyCommonAPIs.sleepi(1);
            policynameinputsxk50.sendKeys(name);
            sharedservicesselectsxk50.selectOptionByValue(shared);
            srcvlanselectsxk50.selectOptionContainingText(src);
            dstvlanselectsxk50.selectOptionContainingText(dst);
            MyCommonAPIs.sleepi(1);
        }
        logger.info("Add policy Done");
    }

    public void ClickApply() {
        logger.info("Click apply Start");
        if (!WebportalParam.arch.equals("new")) {
            Selenide.switchTo().frame("formframe");
        }
        applybtn.click();
        MyCommonAPIs.sleepi(10);
        if (!WebportalParam.arch.equals("new")) {
            Selenide.switchTo().parentFrame();
        }
        logger.info("Click Apply Done");
    }
    
}
