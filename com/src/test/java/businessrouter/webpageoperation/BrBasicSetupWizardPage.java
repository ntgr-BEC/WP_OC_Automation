package businessrouter.webpageoperation;
import java.util.logging.Logger;

import com.codeborne.selenide.Selenide;

import businessrouter.webelements.BrAllMenueElements;
import businessrouter.webelements.BrBasicSetupWizardElements;
public class BrBasicSetupWizardPage extends BrBasicSetupWizardElements{
    final static Logger logger = Logger.getLogger("BrBasicSetupWizardPage");
    public BrBasicSetupWizardPage() {
        // TODO Auto-generated constructor stub
    }
    public void OpenBasicSetupWizardPage() {
        logger.info("Open Basic SetupWizard Page");   
        BrAllMenueElements BrAllMenueElements = new BrAllMenueElements();
        BrAllMenueElements.basic.click();
        BrAllMenueElements.SetupWizard.click();
        logger.info("OpenBasicSetupWizardPage Done"); 
    }
    public boolean  CheckNoUseSetupWizard() {
        logger.info("CheckNoUseSetupWizard start");
        boolean Result = false;
        if (!nowizardbutton.isSelected()) {
            nowizardbutton.selectRadio("MyDetc");
        }
        nextbutton.click();
        Selenide.sleep(3000);
        if (wanpageflag.exists()) {
            Result = true;
        }
        
        logger.info("CheckNoUseSetupWizard Done"); 
        return Result;
    }
}
