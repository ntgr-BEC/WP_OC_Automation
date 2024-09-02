/**
 *
 */
package util;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang.RandomStringUtils;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import webportal.param.WebportalParam;

/**
 * use to check sth in mailbox
 *
 * @author Lavi
 */
public class MailHandler extends MyCommonAPIs {
    String mailFullName     = null;
    String mailName         = null;
    String mailboxHander    = null;
    int    iTimeoutSeekMail = 15 * 60;

    public MailHandler() {

    }

    public MailHandler(int timeout) {
        iTimeoutSeekMail = timeout;
    }

    public MailHandler(String mail) {
        initMail(mail);
    }

    public void initMail(String mail) {
        mailFullName = mail;
        mailName = mail.split("@")[0];
        if (mail.contains("mailcatch.com")) {
            mailboxHander = "MailCatch_";
        } else if (mail.contains("mynetgear.com")) {
            mailboxHander = "MailAHem_";
        } else if (mail.contains("mailforspam.com")) {
            mailboxHander = "Mail4Spam_";
        } else if (mail.contains("mailinator")) {
            mailboxHander = "Mailinator_";
        } else
            throw new RuntimeException("unkown domain");
        System.out.println("MailHandler: " + mailboxHander + "/" + mailName);
    }

    public String getRandomAddress() {
        mailName = "njqa.wp" + RandomStringUtils.randomNumeric(6);
        mailFullName = mailName + WebportalParam.mailDomain;
        initMail(mailFullName);
        return mailFullName;
    }

    public Object handlerCaller(String method) {
        System.out.println("calling " + method);
        try {
            return this.getClass().getDeclaredMethod(mailboxHander + method).invoke(this);
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        throw new RuntimeException("err");
    }

    /**
     * open mailbox then open first mail
     */
    public void enterFirstEmail() {
        handlerCaller("openMailBox");
        takess("enterFirstEmail");
        handlerCaller("enterFirstEmail");
    }

    /**
     * open mailbox then open first mail then click accept for invitation
     *
     * @return true if found
     */
    public boolean acceptInvitation() {
        enterFirstEmail();
        takess("acceptInvitation");
        return (boolean) handlerCaller("acceptInvitation");
    }

    /**
     * open mailbox then open first mail then click accept for register
     *
     * @return true if found
     */
    public boolean confirmMailAddress() {
        enterFirstEmail();
        takess("confirmMailAddress");
        return (boolean) handlerCaller("confirmMailAddress");
    }

    public boolean isReportMail() {
        enterFirstEmail();
        takess("isReportMail");
        return (boolean) handlerCaller("isReportMail");
    }

    /**
     * return first open mail's html
     *
     * @return
     */
    public String getHTMLBody() {
        //enterFirstEmail();
        takess("getHTMLBody");
        try {
            return (String) handlerCaller("getHTMLBody");
        } catch (Throwable e) {
            System.out.println("use inline get pagesource");
            return WebportalParam.curWebDriver.getPageSource();
        }
    }

    public void MailCatch_openMailBox() {
        String url = "http://mailcatch.com/en/temporary-inbox?box=" + mailName;
        open(url);
    }
    
    public void Mailinator_openMailBox() {
        String url = "https://www.mailinator.com/v4/public/inboxes.jsp?to=" + mailName;
        open(url);
    }

    public void MailCatch_enterFirstEmail() {
        for (int i = 0; i < 30; i++) {
            System.out.println(i);
            if ($("tr td").exists()) {
                SelenideElement subject = $("tr td strong");
                //String subject = "tr td strong";
                getText(subject);
                $(subject).click();
                sleepi(10);
                break;
            } else {
                $("img[src*=reload]").click();
                sleepi(30);
            }
        }
    }
    
    public void Mailinator_enterFirstEmail() {
        for (int i = 0; i < 30; i++) {
            System.out.println(i);
            if ($x("//td[contains(text(),'insightalertstest')]").exists()) {
                SelenideElement subject = $x("//td[contains(text(),'insightalertstest')]");
                //String subject = "tr td strong";
                getText(subject);
                $(subject).click();
                sleepi(10);
                break;
            } else {
                Selenide.refresh();
                sleepi(30);
            }
        }
    }

    public boolean MailCatch_acceptInvitation() {
        String hereLink = "li a[href*=netgear]";
        waitElement(hereLink);
        open($(hereLink).getAttribute("href"));
        waitReady();
        String sGet = getText("h3");
        if ($("img[src*=success]").exists())
            return true;
        else
            return false;
    }

    public String MailCatch_getHTMLBody() {
        WebportalParam.curWebDriver.switchTo().frame("emailframe");
        String source = WebportalParam.curWebDriver.getPageSource();
        return source;
    }
    
    public String Mailinator_getHTMLBody() {
        String source = WebportalParam.curWebDriver.getPageSource();
        return source;
    }

    public void Mail4Spam_openMailBox() {
        String mfsaAddr = "https://www.mailforspam.com/mail/";
        open(mfsaAddr + mailName);
    }

    public void Mail4Spam_enterFirstEmail() {
        String sMailListLink = "tbody tr td a";
        boolean bFound = false;
        for (int i = 0; i < 10; i++) {
            if ($(sMailListLink).exists()) {
                $(sMailListLink).click();
                bFound = true;
                break;
            } else {
                refresh();
                sleepi(30);
            }
        }
    }

    public boolean Mail4Spam_acceptInvitation() {
        boolean bActive = false;
        String sMailLink = "li a";
        waitElement(sMailLink);
        for (SelenideElement se : $$(sMailLink)) {
            String toClick = se.getAttribute("href");
            if (toClick.contains("netgear.com")) {
                String toVerify = ".ActivatingCol img";
                String toVerify1 = ".AlreadyActive";
                open(toClick);
                MyCommonAPIs.sleepi(10);
                if ($(toVerify).exists() || $(toVerify1).exists()) {
                    bActive = true;
                }
                break;
            }
        }

        return bActive;
    }

    public void MailAHem_openMailBox() {
        String mailAHem = "http://xmaillab.mynetgear.com:3000/mailbox/";
        open(mailAHem + mailName);
    }

    public void MailAHem_enterFirstEmail() {
        String mailLine = "app-mailbox-emails .mat-line";
        String mailIcon = "app-mailbox-emails svg";
        timerStart(iTimeoutSeekMail);
        while (timerRunning()) {
            if ($(mailLine).exists()) {
                getTexts(mailLine);
                $(mailIcon).click();
                sleepi(4);
                break;
            } else {
                refresh();
                sleepi(30);
            }
        }
    }

    /**
     * 1. vpn user invite
     * 2. insight manager invite
     *
     * @return
     */
    public boolean MailAHem_acceptInvitation() {
        String hereLink = "a[href*=netgear]";
        waitElement(hereLink);
        open($$(hereLink).get(0).getAttribute("href"));
        sleep(30, "wait page to load");
        if ($("h3").exists()) {
            String sGet = getText("h3");
            if ($("img[src*=success]").exists())
                return true;
            else
                return false;
        } else {
            if ($("img[src*=netgear-logo]").exists())
                return true;
            else
                return false;
        }
    }

    public boolean MailAHem_confirmMailAddress() {
        String hereLink = "td a[href*=confirm]";
        waitElement(hereLink);
        open($(hereLink).getAttribute("href"));
        sleep(10, "wait page");
        String sGet = getText("h3");
        if ($("img[src*=checkmark]").exists())
            return true;
        else
            return false;
    }

    /**
     * check mail has a attach, body has "attached report"
     *
     * @return
     */
    public boolean MailAHem_isReportMail() {
        String pdfLink = "a[href*=pdf]";
        waitElement(pdfLink);
        String sGet = getText("body");
        if (sGet.contains("attached report") || sGet.contains("Insight organization") || sGet.contains("Insight Team"))
            return true;
        else
            return false;
    }
}
