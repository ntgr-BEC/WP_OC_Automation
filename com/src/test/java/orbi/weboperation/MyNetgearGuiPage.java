/**
 *
 */
package orbi.weboperation;

import java.util.logging.Logger;

import com.codeborne.selenide.Selenide;

import orbi.param.OrbiGlobalConfig;
import orbi.webelements.MyNetgearPageElement;
import webportal.param.WebportalParam;

/**
 * @author bingke.xue
 *
 */
public class MyNetgearGuiPage extends MyNetgearPageElement {
    Logger logger;
    
    private String emailAddress = WebportalParam.loginName;
    private String       passWord   = WebportalParam.loginPassword;

    public MyNetgearGuiPage() {
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("go to MyNetgearGuiPage");
    }
    
    public void defaultLogin() {
        logger.info("Begin to login to mynetgearGui.");
        Selenide.open(OrbiGlobalConfig.myNetgearGuiURL);
        signInEmailInput.setValue(emailAddress);
        signInPwdInput.setValue(passWord);
        signInBtn.click();
    }
    public void loginByUserPassword(String user,String passwd) {
        logger.info("Begin to login to mynetgearGui.");
        Selenide.open(OrbiGlobalConfig.myNetgearGuiURL);
        signInEmailInput.setValue(user);
        signInPwdInput.setValue(passwd);
        signInBtn.click();
    }
    public boolean checkDeviceInList(String serial) {
        logger.info("Check device in list or not:");
        boolean result = false;
        if (new MyNetgearPageElement().DeviceInList(serial).exists()) {
            logger.info("Device is in mynetgear device list");
            result = true;
        }
        return result;
    }
    public void logoutGui() {
        logger.info("Try to logout GUI.");
        new MyNetgearPageElement().signOutBtn.click();
    }
}
