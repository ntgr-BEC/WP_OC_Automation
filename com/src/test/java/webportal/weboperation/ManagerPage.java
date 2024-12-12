package webportal.weboperation;

import static com.codeborne.selenide.Selenide.executeJavaScript;

import java.util.Map;
import java.util.logging.Logger;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.ManagerPageElement;

/**
 * @author Netgear
 *
 */

public class ManagerPage extends ManagerPageElement {
    /**
    *
    */
    Logger logger;

    public ManagerPage() {
        // TODO Auto-generated constructor stub
        MyCommonAPIs.sleepi(5);  
        waitElement(managerdropdown);
        MyCommonAPIs.sleepi(1); 
        if (managerdropdown.exists()) {
            managerdropdown.click();
        }
        WebCheck.checkHrefIcon(URLParam.hrefManagerList);
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
        refresh();
    }

    public ManagerPage(boolean noPage) {
        // TODO Auto-generated constructor stub
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    public void addManager(Map<String, String> map) {
        MyCommonAPIs.sleepi(5);  
        waitElement(addmanager);
        MyCommonAPIs.sleepi(1);
        if (!checkManagerIsExist(map.get("Email Address"))) {
            addmanager.click();
            waitElement(managername);
            managername.sendKeys(map.get("Name"));
            manageremail.sendKeys(map.get("Email Address"));
            if (map.containsKey("Manager Type")) {
                managertype.selectOption(map.get("Manager Type"));
            }
            if (map.containsKey("Access Policy")) {
                manageraccess.selectOption(map.get("Access Policy"));
            }
            if (map.containsKey("Organization Name")) {
                if (!selectorganizationcheck(map.get("Organization Name")).isSelected()) {
                    selectorganization(map.get("Organization Name")).click();
                }
            }
            if (map.containsKey("Grant Access All")) {
                grantaccessall.click();
            }
            
            if (map.containsKey("Select All")) {
                selectAllOrg.click();
            }
            MyCommonAPIs.sleepi(5);
            invitebutton.click();
            MyCommonAPIs.sleepi(5);
            logger.info("Finish add manager.");
            invitebuttonsentok.click();
            MyCommonAPIs.sleepi(5);
        }
    }

    public void editManager(Map<String, String> map) {
        if (checkManagerIsExist(map.get("Old Email Address"))) {
            executeJavaScript("arguments[0].removeAttribute('class')", editmanager(map.get("Old Email Address")));
            MyCommonAPIs.sleep(3000);
            editmanagerinside(map.get("Old Email Address")).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleep(8 * 1000);
            waitElement(managername);
            if (map.containsKey("Name")) {
                managername.clear();
                MyCommonAPIs.sleepi(3);
                managername.sendKeys(map.get("Name"));
            }
            if (map.containsKey("Email Address")) {
                manageremail.clear();
                MyCommonAPIs.sleepi(3);
                manageremail.sendKeys(map.get("Email Address"));
            }
            if (map.containsKey("Manager Type")) {
                managertype.selectOption(map.get("Manager Type"));
            }
            if (map.containsKey("Access Policy")) {
                manageraccess.selectOption(map.get("Access Policy"));
            }
            if (map.containsKey("Organization Name")) {
                if (selectorganizationcheck(map.get("Organization Name")).isSelected()) {
                    selectorganization(map.get("Organization Name")).click();
                }
            }
            if (map.containsKey("Grant Access All")) {
                grantaccessall.click();
            }
            MyCommonAPIs.sleepi(10);
            invitebutton.click();
            MyCommonAPIs.sleepi(3);
            invitesuccessok.click();
            logger.info("Finish edit manager.");
        }
    }

    public void deleteManager(String email) {
        if (checkManagerIsExist(email)) {
            executeJavaScript("arguments[0].removeAttribute('class')", editmanager(email));
            MyCommonAPIs.sleep(3000);
            deletemanager(email).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleepi(5);
            if(removemanager.isDisplayed())
            {
            removemanager.click();
            }
            else
            {
                removemanagernew.click();
            }
            MyCommonAPIs.sleep(8 * 1000);
            if (!checkManagerIsExist(email)) {
                logger.info("Delete " + email + " success.");
            }
        }
    }

    public void deleteVoucherAdmin(String email) {
        if (checkManagerIsExist(email)) {
            executeJavaScript("arguments[0].removeAttribute('class')", editvoucheradmin(email));
            MyCommonAPIs.sleep(3000);
            deletevoucheradmin(email).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleepi(5);
            removemanager.click();
            MyCommonAPIs.sleep(8 * 1000);
            if (!checkManagerIsExist(email)) {
                logger.info("Delete " + email + " success.");
            }
        }
    }

    public boolean checkEditResult(String email, String policy, String orgnum) {
        boolean result = false;
        System.out.println(managerlistorganizations(email).getText() +" === "+orgnum);
        System.out.println(managerlistaccesspolicy(email).getText() +" === "+policy );
        
        if (managerlistorganizations(email).getText().equals(orgnum) && managerlistaccesspolicy(email).getText().equals(policy)) {
            result = true;
            logger.info("Edited manager information success.");
        }
        System.out.println("checkSuccessDialog Point 2" + result);
        return result;
    }

    public boolean checkSuccessDialog() {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        waitElement(successdialogmsg);
        MyCommonAPIs.sleepi(1);
        if (successdialogmsg.exists()) {
            result = true;
            logger.info("Your invitation has been sent.");
            MyCommonAPIs.sleepi(5);
            System.out.println(successdialogmsg.getText());
            invitesuccessok.click();
            waitReady();
        }
        System.out.println("checkSuccessDialog Point 1" + result);
        return result;
    }

    public boolean checkManagerIsExist(String email) {
        boolean result = false;
        if (manageremailexist(email).exists()) {
            result = true;
            logger.info(email + " is existed.");
        }
        System.out.println("checkManagerIsExist" + result);
        return result;
    }

    public boolean addVoucherAdmin(Map<String, String> map) {
        boolean result = false;
        if (!checkManagerIsExist(map.get("Email Address"))) {
            addmanager.click();
            waitElement(managername);
            if (managertype.exists()) {
                managertype.selectOption("Voucher Admin");
            }
            managername.setValue(map.get("Name"));
            manageremail.setValue(map.get("Email Address"));
            if (selectorganization(map.get("Organization Name")).exists()) {
                if (!selectorganizationcheck(map.get("Organization Name")).isSelected()) {
                    selectorganization(map.get("Organization Name")).click();
                }
            }
            MyCommonAPIs.sleepi(5);
            invitebutton.click();
            MyCommonAPIs.sleepi(3);
            if (invitesuccessok.exists()) {
                invitesuccessok.click();
            }
            logger.info("Finish add voucher admin.");
        } else {
            result = true;
            logger.info("Voucher admin existed.");
        }
        return result;
    }
    
    public void UnassignOrganization(Map<String, String> map) {
        if (checkManagerIsExist(map.get("Old Email Address"))) {
            executeJavaScript("arguments[0].removeAttribute('class')", editmanager(map.get("Old Email Address")));
            MyCommonAPIs.sleep(3000);
            editmanagerinside(map.get("Old Email Address")).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleep(8 * 1000);
            waitElement(managername);
            selectorganization(map.get("Organization Name")).click();
            MyCommonAPIs.sleepi(10);
            invitebutton.click();
            MyCommonAPIs.sleepi(3);
            invitesuccessok.click();
            logger.info("Finish edit manager.");
            
        }
    }
    
    public boolean checkmanagerName(Map<String, String> map) {
        MyCommonAPIs.sleepi(10);
        boolean result = false;
       
           if(!checkManagerIsExist(map.get("Old Email Address"))){
               result = true;
               logger.info("manager is not assigned to organozation is not reflecting in owner account");
           }
        
        return result;
    }
    
    public boolean checkorgAssignedToWriteManager(Map<String, String> map) {
        
        boolean result = false;
       
           if(checkOrgAssociatedWithWritemanager(map.get("Old Email Address")).getText().equals("1")){
               result = true;
               logger.info("manager is not assigned to organozation is not reflecting in owner account");
           }
        
        return result;
    }
    
 // Added by vivek
    public boolean VerifyOrgCountOnMgrPage() {
        boolean result = false;
        MyCommonAPIs.sleepi(3);
        logger.info("verifying Total Org Count is Showing Correct");
        if(OrgCountMgrPage.isDisplayed()) {
            String count = OrgCountMgrPage.text();
            logger.info(count + "Org Found");
            result = true;

        }
        return result;
    }
    
    // Added by vivek
    public boolean hoverOnAddedOrgCount(String mail_id) {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        logger.info("Verify that user should be able see the PopUp and data");
        hoverOverTheOrgCountOfAddedMgr(mail_id);
        result = true;
        return result;
    }
    
 // Added by vivek
    public void hoverOverTheOrgCountOfAddedMgr(String mail_id) {
        MyCommonAPIs.sleepi(5);
        if (MgrOrgCount(mail_id).exists())
            System.out.println(MgrOrgCount(mail_id));
            MgrOrgCount(mail_id).hover();
            MgrOrgCount(mail_id).hover();
            logger.info("Hover over the organization count of added manager Done");
            MyCommonAPIs.sleepi(5);
        
    }
    
 // Added by vivek
    public boolean VerifyFiveOrgisVisibleOnPopUp(String email_id) {
        boolean result = false;
        int count = 0;
        MyCommonAPIs.sleepi(3);
        for (SelenideElement elem : listOfMgrOrg(email_id)) {
            count = count + 1;
            logger.info("PopUp Data " + count + " ===> " + elem.text());
        }
        if(count == 5) {
            logger.info(count + "Org Found");
            result = true;
        }
        return result;
    }
    
    // Added by vivek
    public void clickOnViewAllButton(String mail_id) {
        MyCommonAPIs.sleepi(2);
        if (viewAllButton(mail_id).exists())
            viewAllButton(mail_id).hover();
            viewAllButton(mail_id).click();
            logger.info("Clicking on View All Button");
            MyCommonAPIs.sleepi(10);
        
    }
    
    // Added by vivek
    public boolean VerifyOrgIsPresentOnViewAllSection(String mail_id) {
        boolean result = false;
        MyCommonAPIs.sleepi(3);
        logger.info("verifying Total Org Count is Showing Correct");
        if(viewAllOrgName(mail_id).isDisplayed()) {
            result = true;
        }
        return result;
    }
    
    
    // Added by vivek
    public boolean clickOnKbIconLinkAndVerifyNewOPenedPage() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        logger.info("Clicking on KB Link");
        KBLink.click();
        MyCommonAPIs.sleepi(2);
        Selenide.switchTo().window(1);
        logger.info("Going to New opened Windows and Verify opend page is Correct");
        if (verifymgrText.text().contains("How do I add a manager to my Insight")) {
            result = true;
            Selenide.close();
            MyCommonAPIs.sleepi(1);
            Selenide.switchTo().window(0);
            MyCommonAPIs.sleepi(2);
            
        }
        return result = true;
    
    }
    
    //AddedByPratik
    public void addManager1(Map<String, String> map) {
        if (!checkManagerIsExist(map.get("Email Address"))) {
            addmanager.click();
            waitElement(managername);
            managername.sendKeys(map.get("Name"));
            manageremail.sendKeys(map.get("Email Address"));
            if (map.containsKey("Manager Type")) {
                managertype.selectOption(map.get("Manager Type"));
            }
            if (map.containsKey("Access Policy")) {
                manageraccess.selectOption(map.get("Access Policy"));
            }
            if (map.containsKey("Organization Name")) {
                if (!selectorganizationcheck(map.get("Organization Name")).isSelected()) {
                    selectorganization(map.get("Organization Name")).click();
                }
            }
            if (map.containsKey("Grant Access All")) {
                grantaccessall.click();
            }
            
            if (map.containsKey("Select All")) {
                selectAllOrg.click();
            }
            MyCommonAPIs.sleepi(5);
            invitebutton.click();
        }
    }
    
    //AddedByPratik
    public boolean verifyExistingManager(String name) {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        if (verifyExistingManger(name).exists()) {
            result = true;
            System.out.println(verifyExistingManger(name).getText());
            logger.info("Existing Manger is verifed active and exists");
        }
        return result;
    }
    
    //AddedByPratik
    public boolean addSecondaryAdmin(Map<String, String> map) {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        if (managerdropdown.exists()) {
            managerdropdown.click();
        }
        MyCommonAPIs.sleepi(2);
        waitElement(secondaryAdminOpt);
        secondaryAdminOpt.click();
        MyCommonAPIs.sleepi(5);
        waitElement(addSecAdmin);
        addSecAdmin.click();
        MyCommonAPIs.sleepi(5);
        waitElement(secondaryadminname);
        secondaryadminname.sendKeys(map.get("Name"));
        MyCommonAPIs.sleepi(1);
        waitElement(secondaryAdminEmail);
        secondaryAdminEmail.sendKeys(map.get("Email Address"));
        MyCommonAPIs.sleepi(1);
        waitElement(addSecondaryAdminButton);
        addSecondaryAdminButton.click();
        MyCommonAPIs.sleepi(2);
        waitElement(successMsg1);
        if (successMsg1.exists() && successMsg2.exists() && successMsg3.exists()) {
            successMsg3.click();
            MyCommonAPIs.sleepi(5);
            if (pendingStatus.exists() && verifySecondaryAdmin(map.get("Email Address")).exists()) {
                logger.info("Secondary Admin invited Successfully");
                result = true;
            }
        }
        return result;
    }
    
    //AddedBypratik
    public void deleteSecondaryAdminEmail(String email) {
        MyCommonAPIs.sleepi(5);
        if (managerdropdown.exists()) {
            managerdropdown.click();
        }
        MyCommonAPIs.sleepi(2);
        waitElement(secondaryAdminOpt);
        secondaryAdminOpt.click();
        MyCommonAPIs.sleepi(10);
        executeJavaScript("arguments[0].removeAttribute('class')", verifySecondaryAdmin(email));
        MyCommonAPIs.sleep(3000);
        verifySecondaryAdmin(email).hover();
        MyCommonAPIs.sleep(3);
        deleteSecAdmin(email).waitUntil(Condition.visible, 60 * 1000).hover().click();
        MyCommonAPIs.sleepi(5);
        if (removemanager.isDisplayed()) {
            removemanager.click();
        } else {
            removemanagernew.click();
        }
        MyCommonAPIs.sleep(8 * 1000);
    }
    
    //AddedByPratik
    public boolean verifyExistingSecondaryAdmin(String name) {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        if (managerdropdown.exists()) {
            managerdropdown.click();
        }
        MyCommonAPIs.sleepi(2);
        waitElement(secondaryAdminOpt);
        secondaryAdminOpt.click();
        MyCommonAPIs.sleepi(10);
        if (verifyExistingManger(name).exists()) {
            result = true;
            System.out.println(verifyExistingManger(name).getText());
            logger.info("Existing secondary Admin is verifed active and exists");
        }
        return result;
    }

    
}
