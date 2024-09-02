/**
 *
 */
package switches.weboperation;

import switches.webelements.SystemInfomationPageElements;
import util.MyCommonAPIs;

/**
 * @author zheli
 *
 */
public class SystemInfomationPage extends SystemInfomationPageElements {
    public SystemInfomationPage() {
        CatlogPage catlogPage = new CatlogPage();
        catlogPage.systemInformation();
    }

    public void changeToLocal() {
        localModel.click();
        CatlogPage catlogPage = new CatlogPage();
        oKButton.click();
        applyButton.click();
        MyCommonAPIs.sleep(5 * 1000);
    }

    public void changeToInsight() {
        cloudModel.click();
        CatlogPage catlogPage = new CatlogPage();
        oKButton.click();
        applyButton.click();
        MyCommonAPIs.sleep(5 * 1000);
    }
}
