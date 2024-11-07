package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;

public class ManagerPageElement extends MyCommonAPIs {

    public SelenideElement managerdropdown = $x("//img[contains(@src,'icon-setting')]");

    public SelenideElement        addmanager       = $x("//div[@class='addIcon PsitionRight']//span");
    public SelenideElement        managername      = $("#managerName");
    public SelenideElement        manageremail     = $x("//input[@name='managerEmail']");
    public SelenideElement        managertype      = $x("//select[@name='managerType']");
    public SelenideElement        manageraccess    = $x("//select[@name='accessType']");
    public SelenideElement        selectAllOrg     = $x("//p[text()='Select All']/..");
    public SelenideElement        grantaccessall   = $x("//input[@id='isApplyCheckBox']/../i");
    public SelenideElement        invitebutton     = $("#saveOrgBtn");
    public SelenideElement        invitebuttonsentok = $x("//*[@id=\"successModalInviteManager\"]");
    public SelenideElement        successdialogmsg = $x("//p[text()='Your invitation has been sent.']"); //("//p[text()='" + WebportalParam.getLocText("Your invitation has been sent.") + "']");
    public SelenideElement        invitesuccessok  = $("#successModalInviteManager");
    public static SelenideElement removemanager    = $x("//button[text()='" + WebportalParam.getLocText("Remove") + "']");
//    public SelenideElement       OrgCountMgrPage   = $x("//select[@name='managerType']");
    public SelenideElement       OrgCountMgrPage   = $x("(//td[@class='managerOrgListToolTip'])[1]");
    public SelenideElement       verifymgrText   = $x("//h1");
    public SelenideElement       KBLink            = $x("//div[@class='TableHeader']/h3//span[@class='QuestionCol']");
    public SelenideElement       OrgpopUpdata      = $x("//div[@class=\"AccountTooltip overHidden text-left\"]/div[@class=\"toolTipBody OrgNotifaicationBlock\"]");

    public SelenideElement managerlistorganizations(String name) {
        SelenideElement element = $x("//td[text()='" + name + "']/../td[3]"); 
        return element;
    }

    public SelenideElement managerlistaccesspolicy(String name) {
        SelenideElement element = $x("//td[text()='" + name + "']/../td[4]");
        return element;
    }

    public SelenideElement manageremailexist(String name) {
        SelenideElement element = $x("//td[text()='" + name + "']");
        return element;
    }

    public SelenideElement selectorganizationcheck(String name) {
        SelenideElement element = $x("//p[text()='" + name + "']/../input");
        return element;
    }

    public SelenideElement selectorganization(String name) {
        SelenideElement element = $x("//p[text()='" + name + "']/../i");
        return element;
    }

    public SelenideElement editmanager(String text) {
        SelenideElement manager = $x("//td[text()='" + text + "']/..//td[7]/p");
        return manager;
    }

    public SelenideElement editvoucheradmin(String text) {
        SelenideElement manager = $x("//td[text()='" + text + "']/../td[4]/p");
        return manager;
    }

    public SelenideElement editmanagerinside(String text) {
        SelenideElement manager = $x("(//td[text()='" + text + "']/..//td[7]//i)[2]");
        ElementsCollection managers = $$x("(//td[text()='" + text + "']/..//td[7]//i)");
        if (managers.size() == 2) {
            manager = $x("(//td[text()='" + text + "']/..//td[7]//i)[1]");
        }
        return manager;
    }

    public SelenideElement deletevoucheradmin(String text) {
        SelenideElement manager = $x("(//td[text()='" + text + "']/../td[4]//i)[1]");
        ElementsCollection managers = $$x("(//td[text()='" + text + "']/../td[4]//i)");
        if (managers.size() == 2) {
            manager = $x("(//td[text()='" + text + "']/../td[4]//i)[2]");
        }
        return manager;
    }

    public SelenideElement deletemanager(String text) {
        SelenideElement manager = $x("(//td[text()='" + text + "']/..//td[7]//i)[3]");
        ElementsCollection managers = $$x("(//td[text()='" + text + "']/..//td[7]//i)");
        if (managers.size() == 2) {
            manager = $x("(//td[text()='" + text + "']/..//td[7]//i)[2]");
        }
        return manager;
    }
    
    
    public SelenideElement editSecondaryAdmin(String text) {
        SelenideElement manager = $x("//td[text()='" + text + "']/..//td[4]/p");
        return manager;
    }
    
    public SelenideElement deleteSecondaryAdmin(String text) {      
        SelenideElement manager;
        ElementsCollection managers;
        if($x("(//td[text()='" + text + "']/..//td[4]//i)[2]").isDisplayed()) {
        manager = $x("(//td[text()='" + text + "']/..//td[4]//i)[2]");
        managers = $$x("(//td[text()='" + text + "']/..//td[4]//i)[2]");
        if (managers.size() == 2) {
            manager = $x("(//td[text()='" + text + "']/..//td[4]//i)[2]");
        }
        }else
        {
            manager = $x("(//td[text()='" + text + "']/..//td[4]//i)");
            managers = $$x("(//td[text()='" + text + "']/..//td[4]//i)");
            if (managers.size() == 2) {
                manager = $x("(//td[text()='" + text + "']/..//td[4]//i)");
            }
        }
        
        return manager;
    }
    
    public SelenideElement editSecondaryAdminPost(String text) {
        SelenideElement manager = $x("//td[text()='" + text + "']/..//td[4]/p");
        return manager;
    }
    
    public SelenideElement deleteSecondaryAdminPost(String text) {
        SelenideElement manager = $x("//td[text()='" + text + "']/..//td[4]//i");
//        ElementsCollection managers = $$x("(//td[text()='" + text + "']/..//td[4]//i)");
//        if (managers.size() == 2) {
//            manager = $x("(//td[text()='" + text + "']/..//td[4]//i)[2]");
//        }
        return manager;
    }
    
    public SelenideElement Resend(String text) {
        SelenideElement manager = $x("//td[text()='" + text + "']/..//td[4]//a//i");
        return manager;
    }
    //pratik
    public SelenideElement successOkayButton = $x("//button[@id='successModalInviteManager']");
    
    //vivek
    public SelenideElement MgrOrgCount(String text) {
        SelenideElement emt = $x("//td[text()='" + text + "']/../td//div[@class='orgcount']");
        return emt;
    }
    //vivek
    public ElementsCollection listOfMgrOrg(String text) {
        ElementsCollection listElmts = $$x("//td[text()='" + text + "']/..//div[@class='toolTipBody OrgNotifaicationBlock']//span[@data-testid='orgName']");
        return listElmts;   
}
    
    //vivek
    public SelenideElement viewAllButton(String text) {
        SelenideElement emt = $x("//td[text()='" + text + "']/..//div/a[text()='View All']");
        return emt;
    }
    
    //vivek
    public SelenideElement viewAllOrgName(String Org_Name) {
        SelenideElement emt = $x("//td[@class='sorting_1']//span[text()='" + Org_Name + "']");
        return emt;
    }
    
    public static SelenideElement removemanagernew    = $x("//button[text()='Remove']");

    public SelenideElement checkOrgAssociatedWithWritemanager(String name) {
        SelenideElement element = $x("//td[text()='" + name + "']/../td[3]");
        return element;
    }
    
    //AddedBypratik 
    public SelenideElement verifyExistingManger(String name) {
        SelenideElement verifyExistingManger    = $x("//td[text()='"+ name +"']/..//td[text()='Active']");
        return verifyExistingManger;
    }
    
    //AddedBypratik 
    public SelenideElement verifySecondaryAdmin(String name) {
        SelenideElement verifySecondaryAdmin    = $x("//td[text()='"+ name +"']");
        return verifySecondaryAdmin;
    }
    
    //AddedBypratik 
    public SelenideElement deleteSecAdmin(String name) {
        SelenideElement deleteSecAdmin    = $x("//td[text()='"+ name +"']/..//img[@class='deleteDeviceIcon']");
        return deleteSecAdmin;
    }
    
    //AddedByPratik
    public SelenideElement secondaryAdminOpt         = $x("//a[@href=\"#/organization/secondaryAdmin\"]");
    public SelenideElement addSecAdmin               = $x("//div[@id='_divMoreIconOrg']");
    public SelenideElement secondaryadminname        = $x("//input[@id='secondaryAdminName']");
    public SelenideElement secondaryAdminEmail       = $x("//input[@id='secondaryAdminEmail']");
    public SelenideElement addSecondaryAdminButton   = $x("//button[@id='saveOrgBtn']");
    public SelenideElement successMsg1               = $x("//div[text()='Your invitation has been sent successfully. Please ']");
    public SelenideElement successMsg2               = $x("//*[text()='to see invited secondary admins.']");
    public SelenideElement successMsg3               = $x("//a[text()='click here']");
    public SelenideElement pendingStatus             = $x("//*[text()='Pending']");
}


