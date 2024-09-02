package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import util.MailHandler;
import webportal.param.URLParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DeviceBRVPNUsersElement;

public class DeviceBRVPNUsersPage extends DeviceBRVPNUsersElement {
    Logger logger = Logger.getLogger("DeviceBRVPNUsersPage");
    
    public DeviceBRVPNUsersPage() {
        logger.info("init...");
    }
    
    public void gotoPage() {
        WebCheck.checkUrl(URLParam.hrefBRvpnUsers);
    }
    
    public List<String> getUsers() {
        List<String> sRet = new ArrayList<String>();
        if ($(txtEmail).exists())
            return getTexts(txtEmail);
        return sRet;
    }
    
    public boolean isUserActive(String sUser) {
        if ($("tbody tr").exists()) {
            if (getTextTable("tbody", sUser, 3).equals("Active"))
                return true;
            else
                return false;
        }
        return false;
    }
    
    public void reInviteUser(String sUser) {
        editLine(sUserTabl, 2, sUser, 1);
        clickYesNo(true);
    }
    
    public void addUser(String sUser) {
        if (!getUsers().contains(sUser)) {
            btnAdd.click();
            setText(txtEmailAdd, sUser);
            btnInvite.click();
            waitReady();
        }
    }
    
    public void deleteUser(String sUser) {
        try {
            editLine(sUserTabl, 2, sUser, 2);
        } catch (Throwable e) {
            takess();
            e.printStackTrace();
            editLine(sUserTabl, 2, sUser, 1);
        }
        btnRemove.click();
        waitReady();
    }
    
    public void deleteAllUser() {
        takess("delete All data");
        for (String sUser : getUsers()) {
            deleteUser(sUser);
        }
    }
    
    /**
     * @param sUser
     *              open line that contains email address(sUser)
     */
    public void openUser(String sUser) {
        int iPos = getUsers().indexOf(sUser);
        $$(txtName).get(iPos).click();
        waitReady();
    }
    
    public List<String> getLoginHistory() {
        return getTexts(sUserLogin);
    }
    
    public boolean acceptInviteFromMail(String mailAddr) {
        return new MailHandler(mailAddr).acceptInvitation();
    }
}
