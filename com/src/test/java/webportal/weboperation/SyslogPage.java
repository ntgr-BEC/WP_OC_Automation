package webportal.weboperation;

import java.util.logging.Logger;

import webportal.param.URLParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.SyslogElement;

public class SyslogPage extends SyslogElement {
    Logger logger = Logger.getLogger("SyslogPage");

    public SyslogPage() {
        logger.info("init...");
    }

    public void gotoPage() {
        WebCheck.checkUrl(URLParam.hrefSyslog);
    }

    public void setSyslog(String ip, int port) {
        setSelected(cbEnable, true, true);
        setText(txtIp, ip);
        setText(txtPort, port);
        btnSave.click();
    }

    public void disableSyslog() {
        setSelected(cbEnable, false, true);
        btnSave.click();
    }
}
