/**
 *
 */
package webportal.publicstep;

import util.MyCommonAPIs;
import webportal.webelements.ButtonElements;

/**
 * @author zheli
 */
public class PublicButton extends ButtonElements {

    /**
     *
     */
    public PublicButton() {
        // TODO Auto-generated constructor stub
    }

    public void rebootDevice() {
        dotsicon.click();
        MyCommonAPIs.sleepi(10);
        reBootIcon.click();
        waitReady();

        clickBoxLastButton();
        MyCommonAPIs.sleep(200 * 1000);
    }

    /**
     * suggest not use reload ,because reload=factory default
     */
    public void reloadDevice() {
        reLoadIcon.click();
        waitReady();

        clickBoxLastButton();
        MyCommonAPIs.sleep(200 * 1000);
    }

    public void removeDevice() {
        deleteButton.click();
        waitReady();

        clickBoxLastButton();
        MyCommonAPIs.sleepi(10);
    }

    public void ClickSave() {
        ButtonElements.SAVEBUTTON.click();
    }

    public void ClickNext() {
        nextButton.click();
    }

    public void ShareDiagnostics(String email) {
        if (obShareButton.exists()) {
            obShareButton.click();
        } else {
            shareButton.click();
        }
        waitReady();
        shareEmailAddress.setValue(email);
        waitReady();
        sendButton.click();
    }

    /**
     * @return return true if result is ok
     */
    public boolean CheckTestVPNServer() {
        waitElement(testVPNButton);
        testVPNButton.click();
        waitReady();
        clickBoxLastButton();

        waitReady();
        try {
            waitElement(sPopButtonCss);
        } catch (Throwable e) {
            
        }
        String result = getPageErrorMsg();
        clickBoxLastButton();
        if (result.contains("good"))
            return true;
        return false;
    }
}
