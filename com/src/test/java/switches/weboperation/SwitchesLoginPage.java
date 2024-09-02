package switches.weboperation;

import static com.codeborne.selenide.Selenide.open;

import switches.param.SwitchParam;
import switches.param.init.AppManageSwitchesInit;
import switches.param.init.NewGuiManagedSwitchesInit;
import switches.webelements.SwitchesLoginPageElements;
import util.MyCommonAPIs;

public class SwitchesLoginPage extends SwitchesLoginPageElements {
    public SwitchesLoginPage() {
    }

    /**
     * 打开网页
     *
     * @param url
     */
    public void openBrowser(String url) {
        open("http://" + url);
    }

    /**
     * 登陆操作
     *
     * @param swicthes
     */
    public void sendLoginInfomation() {
        openBrowser(SwitchParam.IPADDRESS);
        if (AppManageSwitchesInit.isAppManageSwitches(SwitchParam.MODEL)) {
            passWord.sendKeys(SwitchParam.PASSWORD);
            loginButtong.click();
        } else if (NewGuiManagedSwitchesInit.isNewGuiManagedSwitches(SwitchParam.MODEL)) {
            userName.sendKeys(SwitchParam.LOGINNAME);
            passWord.sendKeys(SwitchParam.PASSWORD);
            loginButtong.click();
        }
        MyCommonAPIs.sleep(5 * 1000);
        if (notice.isDisplayed()) {
            notice.click();
        }
    }
}
