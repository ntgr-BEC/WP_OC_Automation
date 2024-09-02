package businessrouter.webpageoperation;

import java.util.logging.Logger;

import businessrouter.webelements.BrAllMenueElements;
import businessrouter.webelements.BrSecurityBlockSites;
//import webportal.weboperation.MyCommonAPIs;
import util.MyCommonAPIs;

public class BrAdvancedSecurityBlockSitesPage extends BrSecurityBlockSites {
    final static Logger logger = Logger.getLogger("BrAdvancedSecurityBlockSitesPage");

    public void OpenSecurityBlockSitesPage() {
        BrAllMenueElements BrAllMenueElements = new BrAllMenueElements();
        logger.info("Open Security Block Sites page");
        BrAllMenueElements.advanced.click();
        MyCommonAPIs.sleepi(5);
        BrAllMenueElements.Security.click();
        MyCommonAPIs.sleepi(5);
        BrAllMenueElements.BlockSites.click();
    }

    public void checkboxchecked(String select) {
        logger.info("Check checkbox.");
        if (select.equals("alw")) {
            if (keywordblockingalwayschecked.getAttribute("class").equals("ant-radio ant-radio-checked")) {
                logger.info("Disable Always checkbox.");
                keywordblockingalways.selectRadio("always");
            }
        } else if (select.equals("pre")) {
            if (keywordblockingprechecked.getAttribute("class").equals("ant-radio ant-radio-checked")) {
                logger.info("Disable per schedule checkbox.");
                keywordblockingpre.selectRadio("perschedule");
            }
        } else if (select.equals("nev")) {
            if (keywordblockingneverchecked.getAttribute("class").equals("ant-radio ant-radio-checked")) {
                logger.info("Disable never checkbox.");
                keywordblockingnever.selectRadio("never");
            }
        }
    }

    public void checkboxnotchecked(String select) {
        logger.info("Check checkbox.");
        if (select.equals("alw")) {
            if (keywordblockingalwayschecked.getAttribute("class").equals("ant-radio")) {
                logger.info("Enable always checkbox.");
                keywordblockingalways.selectRadio("always");
            }
        } else if (select.equals("pre")) {
            if (keywordblockingprechecked.getAttribute("class").equals("ant-radio")) {
                logger.info("Enable per schedule checkbox.");
                keywordblockingpre.selectRadio("perschedule");
            }
        } else if (select.equals("nev")) {
            if (keywordblockingneverchecked.getAttribute("class").equals("ant-radio")) {
                logger.info("Enable never checkbox.");
                keywordblockingnever.selectRadio("never");
            }
        }
    }

    public void blocksiteselectnevandclearlist() {
        logger.info("Select never and clear list.");
        checkboxnotchecked("nev");
        keywordordomainlistclearbutton.click();
        keywordblockapplybutton.click();
    }

    public void blocksiteselectnev(String url) {
        logger.info("Select never and add keyword.");
        checkboxnotchecked("nev");
        keywordordomainname.sendKeys(url);
        keywordaddbutton.click();
        keywordblockapplybutton.click();
    }

    public void blocksiteselectalw(String url) {
        logger.info("Select always and add keyword.");
        checkboxnotchecked("alw");
        keywordordomainname.sendKeys(url);
        keywordaddbutton.click();
        keywordblockapplybutton.click();
    }

    public void blocksiteselectpre(String url) {
        logger.info("Select per schedule and add keyword.");
        checkboxnotchecked("pre");
        keywordordomainname.sendKeys(url);
        keywordaddbutton.click();
        keywordblockapplybutton.click();
    }

    public boolean checkdomainlist(String url) {
        boolean result;
        // Selenide.refresh();
        MyCommonAPIs.sleepi(10);
        logger.info("Check domainlist");
        if (keywordblockingprechecked.getAttribute("class").equals("ant-radio ant-radio-checked") && keywordordomainlistarray.text().equals(url)) {
            logger.info("Check domainlist result true.");
            result = true;
        } else if (keywordblockingneverchecked.getAttribute("class").equals("ant-radio ant-radio-checked") && !keywordordomainlistarray.exists()) {
            logger.info("Check domainlist result true.");
            result = true;
        } else if (keywordblockingalwayschecked.getAttribute("class").equals("ant-radio ant-radio-checked")
                && keywordordomainlistarray.text().equals(url)) {
            logger.info("Check domainlist result true.");
            result = true;
        } else {
            logger.info("Check domainlist result false.");
            result = false;
        }
        return result;
    }

    public boolean CheckKeywordMax(String keyword) {
        boolean result;
        logger.info("Check keyword max.");
        if (keywordordomainlistarray.getText().length() == 60) {
            logger.info("Check keyword max result true.");
            result = true;
        } else {
            logger.info("Check keyword max result false.");
            result = false;
        }
        return result;
    }

    public boolean checkdomainlistarray(String[] urls) {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        logger.info("Check domainlist");
        if (keywordordomainlistarray.exists()) {
            
            String options = keywordordomainlist.innerHtml();
            System.out.println(keywordordomainlist.innerHtml());
            for (int i = 0; i < urls.length; i++) {
                System.out.print(options.indexOf(urls[i]));
                System.out.println(urls[i]);
                if (keywordblockingprechecked.getAttribute("class").equals("ant-radio ant-radio-checked") && options.indexOf(urls[i]) != -1) {
                    logger.info("Check domainlist result true.");
                    result = true;
                } else if (keywordblockingneverchecked.getAttribute("class").equals("ant-radio ant-radio-checked")
                && options.indexOf(urls[i]) != -1) {
                    logger.info("Check domainlist result true.");
                    result = true;
                } else if (keywordblockingalwayschecked.getAttribute("class").equals("ant-radio ant-radio-checked")
                        && options.indexOf(urls[i]) != -1) {
                    
                    logger.info("Check domainlist result true.");
                    result = true;
                } else {
                    logger.info("Check domainlist result false.");
                    result = false;
                    break;
                }
            }
        } else {
            if (keywordblockingneverchecked.getAttribute("class").equals("ant-radio ant-radio-checked")) {
                logger.info("Check domainlist result true.");
                result = true;
            } else {
                logger.info("Check domainlist result false.");
                result = false;
            }
        }
        return result;
    }

    public boolean checkdomainlistmaxerror() {
        boolean result;
        logger.info("Check add keyword max error.");
        System.out.println(keywordblockdialog.getText());
        if (keywordblockdialog.getText().equals("The router can support only 32 keywords or domain names.")) {
            logger.info("Add keyword max display dialog.");
            result = true;
        } else {
            logger.info("Add keyword max not display dialog.");
            result = false;
        }
        return result;
    }

    public boolean allowtrustedIP(String ip4) {
        boolean result;
      System.out.println(ip4);
        logger.info("Enable allow trusted IP and add IP.");
        if (!keywordtrustipenable.isSelected()) {
            keywordtrustipenable.selectRadio("on");
        }
        MyCommonAPIs.sleepi(3);
        keywordtrustip4.clear();
        MyCommonAPIs.sleepi(3);
        keywordtrustip4.sendKeys(ip4);
        MyCommonAPIs.sleepi(3);
        keywordblockapplybutton.click();
        MyCommonAPIs.sleepi(5);
        logger.info("Check Trusted IP Address.");
        if (keywordtrustip4.getValue().equals(ip4)) {
            logger.info("Enable allow trusted IP and add IP success.");
            result = true;
        } else {
            logger.info("Enable allow trusted IP and add IP fail.");
            result = false;
        }
        return result;
    }

    public void disableallowtrustedIP() {
        logger.info("Disable allow trusted IP and add IP.");
        keywordtrustip4.clear();
        MyCommonAPIs.sleepi(3);
        if (keywordtrustipenable.isSelected()) {
            keywordtrustipenable.selectRadio("on");
        }
        MyCommonAPIs.sleepi(3);
        keywordblockapplybutton.click();
    }

}
