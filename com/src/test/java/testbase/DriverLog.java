package testbase;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

import com.codeborne.selenide.Selenide;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;

public class DriverLog implements WebDriverEventListener {
    static MyCommonAPIs handle  = new MyCommonAPIs();
    String              sPrefix = "--> ";

    @Override
    public void beforeAlertAccept(WebDriver driver) {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterAlertAccept(WebDriver driver) {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterAlertDismiss(WebDriver driver) {
        // TODO Auto-generated method stub

    }

    @Override
    public void beforeAlertDismiss(WebDriver driver) {
        // TODO Auto-generated method stub

    }

    @Override
    public void beforeNavigateTo(String url, WebDriver driver) {
        // TODO Auto-generated method stub
        if (WebportalParam.enableRemote) {
            System.out.println(sPrefix + "Navigating to " + url);
        }
    }

    @Override
    public void afterNavigateTo(String url, WebDriver driver) {
        // TODO Auto-generated method stub
        if (handle.checkPageError()) {
            System.out.println(sPrefix + "See error while naviing to " + url);
            Selenide.refresh();
            Selenide.sleep(10 * 1000);
        }
    }

    @Override
    public void beforeNavigateBack(WebDriver driver) {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterNavigateBack(WebDriver driver) {
        // TODO Auto-generated method stub

    }

    @Override
    public void beforeNavigateForward(WebDriver driver) {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterNavigateForward(WebDriver driver) {
        // TODO Auto-generated method stub

    }

    @Override
    public void beforeNavigateRefresh(WebDriver driver) {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterNavigateRefresh(WebDriver driver) {
        // TODO Auto-generated method stub

    }

    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
        // TODO Auto-generated method stub
        if (WebportalParam.enableRemote) {
            System.out.println(sPrefix + "Search on " + by.toString());
        }
    }

    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {
        // TODO Auto-generated method stub
    }

    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {
        // TODO Auto-generated method stub
        if (WebportalParam.enableRemote) {
            System.out.println(sPrefix + "Click on " + element.toString());
        }
    }

    @Override
    public void afterClickOn(WebElement element, WebDriver driver) {
        // TODO Auto-generated method stub
    }

    @Override
    public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
        // TODO Auto-generated method stub

    }

    @Override
    public void beforeScript(String script, WebDriver driver) {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterScript(String script, WebDriver driver) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onException(Throwable throwable, WebDriver driver) {
        // TODO Auto-generated method stub
    }

    @Override
    public void beforeSwitchToWindow(String windowName, WebDriver driver) {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterSwitchToWindow(String windowName, WebDriver driver) {
        // TODO Auto-generated method stub

    }

    @Override
    public <X> void beforeGetScreenshotAs(OutputType<X> target) {
        // TODO Auto-generated method stub

    }

    @Override
    public <X> void afterGetScreenshotAs(OutputType<X> target, X screenshot) {
        // TODO Auto-generated method stub

    }

    @Override
    public void beforeGetText(WebElement element, WebDriver driver) {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterGetText(WebElement element, WebDriver driver, String text) {
        // TODO Auto-generated method stub

    }

}
