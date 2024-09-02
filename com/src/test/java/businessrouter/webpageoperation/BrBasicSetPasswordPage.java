package businessrouter.webpageoperation;

import java.util.logging.Logger;

import businessrouter.webelements.BrAllMenueElements;
import businessrouter.webelements.BrSetPasswordElements;
//import webportal.weboperation.MyCommonAPIs;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;

public class BrBasicSetPasswordPage extends BrSetPasswordElements {
    final static Logger logger = Logger.getLogger("BrBasicSetPasswordPage");

    public void OpenSetPasswordPage() {
        BrAllMenueElements BrAllMenueElements = new BrAllMenueElements();
        logger.info("Open Set Password page");
        BrAllMenueElements.basic.click();
        if (WebportalParam.DUTType.contentEquals("BR500")) {
            BrAllMenueElements.Setup.click();
        } else if(WebportalParam.DUTType.contentEquals("BR100")) {
            BrAllMenueElements.SetupBr100.click();
        }
        BrAllMenueElements.SetPassword.click();
    }

    public void ChangePassword(String OldPassword, String NewPassword) {
        logger.info("Start change password.");
        oldpassword.setValue(OldPassword);
        MyCommonAPIs.sleepi(2);
        newpassword.setValue(NewPassword);
        MyCommonAPIs.sleepi(2);
        confirmpassword.setValue(NewPassword);
        MyCommonAPIs.sleepi(2);
        passwordapplybutton.click();
    }

    public boolean CheckChangePasswordError(String Change) {
        boolean result = true;
        MyCommonAPIs.sleep(5);
        logger.info("Check change password error.");
        if (changepassworderror.exists()) {
            System.out.println(changepassworderror.getText());
            if (changepassworderror.getText().equals("The new password length is less than 8.")) {
                logger.info("The new password length is less than 8.");
            } else if (changepassworderror.getText().equals("The new password validation failed.")) {
                logger.info("The new password validation failed.");
            } else {
                logger.info("Other error.");
            }
        } else if (changepassworddialogtitle.getText().equals("Are you sure you want to change the admin password without recovery option?")
                && changepassworddialogcontent.getText().equals(
                        "If you do not enable password recovery and forget your new password, the only way to recover the password is to reset the device to the factory default settings.")) {
            logger.info(
                    "If you do not enable password recovery and forget your new password, the only way to recover the password is to reset the device to factory default.\"");
            if (Change.equals("change")) {
                MyCommonAPIs.sleep(5);
                changepassworddialogokbutton.click();
            }
        } else {
            logger.info("Change password don't find error.");
            result = false;
        }
        return result;
    }
}
