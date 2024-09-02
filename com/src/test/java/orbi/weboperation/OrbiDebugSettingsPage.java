/**
 *
 */
package orbi.weboperation;

import static com.codeborne.selenide.Selenide.$x;

import java.util.logging.Logger;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import orbi.param.OrbiGlobalConfig;
import orbi.webelements.DNIOrbiCommanElement;
import orbi.webelements.DNIOrbiDebugSettingsPageElement;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;

/**
 * @author bingke.xue
 *
 */
public class OrbiDebugSettingsPage extends DNIOrbiDebugSettingsPageElement {
    Logger               logger;
    public static String orbiloginURLLAN = "https://admin:" + WebportalParam.loginDevicePassword + "@" + OrbiGlobalConfig.orbiLANIp;
    public static String orbiloginURLWAN = "https://admin:" + WebportalParam.loginDevicePassword + "@" + WebportalParam.ob2IPaddress;

    /**
     *
     */
    public OrbiDebugSettingsPage() {
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("go to OrbiDebugSettingsPage");
    }

    public void OrbibaseEnableTelenet(String orbiDeviceMode) {
        logger.info("Begin to enable orbi base telnet");
        if (new OrbiGlobalConfig().orbiSupplier == "DNI") {
            new OrbiLoginPage(orbiDeviceMode).OrbiLoginDebugPage(orbiDeviceMode);
            Selenide.switchTo().frame(DNIOrbiCommanElement.formIframe);
            // Selenide.switchTo().frame("formframe");
            System.out.println("telnet is checked or not:" + DNIOrbiDebugSettingsPageElement.enableTelnetCheckBox.isSelected());
            if (!DNIOrbiDebugSettingsPageElement.enableTelnetCheckBox.isSelected()) {
                System.out.println("try to enable orbi telnet");
                // DNIOrbiDebugSettingsPageElement.enableTelnetCheckBox.setSelected(true);
                DNIOrbiDebugSettingsPageElement.enableTelnetCheckBox.hover();
                DNIOrbiDebugSettingsPageElement.enableTelnetCheckBox.setSelected(true);
                // DNIOrbiDebugSettingsPageElement.enableTelnetCheckBox.click();
                System.out.println("deviceMode" + orbiDeviceMode);
                System.out.println("after click enable telenet");
                MyCommonAPIs.sleepi(10);
            }

        }

    }

    public boolean OrbibaseEnableTelenet(String baseip, String password) {
        logger.info("Begin to enable orbi base telnet");
        new OrbiLoginPage(false).OrbiLoginDebugPage(baseip, password);
        try {
            if (!WebportalParam.arch.equals("new")) {
                Selenide.switchTo().frame(DNIOrbiCommanElement.formIframe);
            }else {
                DNIOrbiDebugSettingsPageElement.newarchTelnetCheckBox.hover();
            }
        } catch (Throwable e) {
            return false;
        }

        if (!WebportalParam.arch.equals("new")) {
            System.out.println("telnet is checked or not:" + DNIOrbiDebugSettingsPageElement.enableTelnetCheckBox.isSelected());
            if (!DNIOrbiDebugSettingsPageElement.enableTelnetCheckBox.isSelected()) {
                System.out.println("try to enable orbi telnet");
                DNIOrbiDebugSettingsPageElement.enableTelnetCheckBox.hover();
                DNIOrbiDebugSettingsPageElement.enableTelnetCheckBox.setSelected(true);
                System.out.println("after click enable telenet");
            } else {
                DNIOrbiDebugSettingsPageElement.enableTelnetCheckBox.hover();
                DNIOrbiDebugSettingsPageElement.enableTelnetCheckBox.setSelected(false);
                MyCommonAPIs.sleepi(10);
                DNIOrbiDebugSettingsPageElement.enableTelnetCheckBox.hover();
                DNIOrbiDebugSettingsPageElement.enableTelnetCheckBox.setSelected(true);
            } 
        }else {
            if(!DNIOrbiDebugSettingsPageElement.newarchTelnetInput.isSelected()) {
                DNIOrbiDebugSettingsPageElement.newarchTelnetCheckBox.click();
            }else {
                DNIOrbiDebugSettingsPageElement.newarchTelnetCheckBox.click();
                MyCommonAPIs.sleepi(10);
                DNIOrbiDebugSettingsPageElement.newarchTelnetCheckBox.click();
            }
        }
        
        MyCommonAPIs.sleepi(10);
        System.out.println("telnet enabled");
        return true;
    }

    public void OrbibaseForceChangePassword(String baseip, String old_password, String new_password) {
        logger.info("Begin to force change password");

        new OrbiLoginPage(false).OrbiLoginForceChangePasswordPage(baseip, old_password);

        SelenideElement newpw = $x("//input[@name='sysNewPasswd']");
        SelenideElement confirmpw = $x("//input[@name='sysConfirmPasswd']");
        SelenideElement changepw = $x("//input[@name='change_pwd']");

        newpw.setValue(new_password);
        confirmpw.setValue(new_password);
        changepw.click();
        MyCommonAPIs.waitReady();

    }

}
