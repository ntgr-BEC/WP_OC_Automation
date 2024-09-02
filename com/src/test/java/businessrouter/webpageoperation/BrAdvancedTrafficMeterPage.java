package businessrouter.webpageoperation;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.codeborne.selenide.Selenide;

import businessrouter.webelements.BrAdvancedTrafficMeterElements;
import businessrouter.webelements.BrAllMenueElements;
//import webportal.weboperation.MyCommonAPIs;
import util.MyCommonAPIs;

public class BrAdvancedTrafficMeterPage extends BrAdvancedTrafficMeterElements {
    final static Logger logger = Logger.getLogger("BrAdvancedTrafficMeterPage");

    private static final String FTP_ADDRESS = "10.40.1.184";

    private static final int FTP_PORT = 21;

    private static final String FTP_USERNAME = "switch";

    private static final String FTP_PASSWORD = "switch#1";

    private static final String BASE_PATH = "";

    private static String localCharset = "GBK";

    private static String serverCharset = "ISO-8859-1";

    private static final String CHARSET_UTF8 = "UTF-8";

    private static final String OPTS_UTF8 = "OPTS UTF8";

    private static final int BUFFER_SIZE = 1024 * 1024 * 4;

    private static FTPClient ftpClient = null;

    public void OpenTrafficMeterPage() {
        BrAllMenueElements BrAllMenueElements = new BrAllMenueElements();
        logger.info("Open Traffic Meter page");
        BrAllMenueElements.advanced.click();
        MyCommonAPIs.sleepi(5);
        BrAllMenueElements.TrafficMeter.click();
    }

    private void login(String address, int port, String username, String password) {
        ftpClient = new FTPClient();
        try {
            ftpClient.connect(address, port);
            ftpClient.login(username, password);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                closeConnect();
                logger.info("FTP server connect fail.");
            }
        } catch (Exception e) {
            logger.info("FTP login fail.");
        }
    }

    private void closeConnect() {
        if (ftpClient != null && ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                logger.info("Close FTP connect fail.");
            }
        }
    }

    private static String changeEncoding(String ftpPath) {
        String directory = null;
        try {
            if (FTPReply.isPositiveCompletion(ftpClient.sendCommand(OPTS_UTF8, "ON"))) {
                localCharset = CHARSET_UTF8;
            }
            directory = new String(ftpPath.getBytes(localCharset), serverCharset);
        } catch (Exception e) {
            logger.info("Fail.");
        }
        return directory;
    }

    public boolean downloadFile(String ftpPath, String fileName, String savePath) {
        // 登录
        login(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD);
        boolean flag = false;
        if (ftpClient != null) {
            try {
                String path = changeEncoding(BASE_PATH + ftpPath);
                // 判断是否存在该目录
                if (!ftpClient.changeWorkingDirectory(path)) {
                    logger.info(BASE_PATH + ftpPath + "该目录不存在");
                    return flag;
                }
                ftpClient.enterLocalPassiveMode(); // 设置被动模式，开通一个端口来传输数据
                String[] fs = ftpClient.listNames();
                // 判断该目录下是否有文件
                if (fs == null || fs.length == 0) {
                    logger.info(BASE_PATH + ftpPath + "该目录下没有文件");
                    return flag;
                }
                for (String ff : fs) {
                    String ftpName = new String(ff.getBytes(serverCharset), localCharset);
                    if (ftpName.equals(fileName)) {
                        File file = new File(savePath + '/' + ftpName);
                        try (OutputStream os = new FileOutputStream(file)) {
                            flag = ftpClient.retrieveFile(ff, os);
                        } catch (Exception e) {
                            logger.info(e.getMessage());
                        }
                        break;
                    }
                }
            } catch (IOException e) {
                logger.info("Download file fail.");
            } finally {
                closeConnect();
            }
        }
        return flag;
    }

    public void ConfigWarningMessageVolume(String volume) {
        logger.info("Config warning message volume.");
        OpenTrafficMeterPage();
        MyCommonAPIs.sleepi(10);
        tafficpopwarningmessage.clear();
        MyCommonAPIs.sleepi(3);
        tafficpopwarningmessage.sendKeys(volume);
        MyCommonAPIs.sleepi(3);
        tafficmeterapplybutton.click();
        // MyCommonAPIs.sleepi(10);
    }

    public void MouseWheel() {
        logger.info("Mouse wheel start.");
        MyCommonAPIs.sleepi(5);
        Selenide.executeJavaScript("document.documentElement.scrollTop=0");
        logger.info("Mouse wheel done");
    }

    public void ConfigTrafficVolumeControl(String option, String volume) {
        logger.info("Config traffic volume control.");
        MouseWheel();
        trafficvolumecontrolvalue.click();
        MyCommonAPIs.sleepi(3);
        trafficvolumecontrolconfig(option);
        if (!option.equals("No limit")) {
            trafficmonthlylimit.clear();
            MyCommonAPIs.sleepi(3);
            trafficmonthlylimit.sendKeys(volume);
            MyCommonAPIs.sleepi(3);
        }
        tafficmeterapplybutton.click();
        MyCommonAPIs.sleepi(10);
    }

    public String[] GetRestartTime() {
        logger.info("Get restart time.");
        String time = tafficrestarttimehour.getValue().substring(0, 2) + tafficrestarttimehour.getValue().substring(3, 5)
                + tafficrestarttimehour.getValue().substring(6, 8);
        String date = tafficrestarttimeday.getText().replaceAll("[a-zA-Z]", "");
        String[] restarttime = {
                time, date
        };
        return restarttime;
    }

    public boolean CheckTrafficCounterTimeAndDate(String time, String day) {
        logger.info("Check traffic counter time and date.");
        time = time.substring(0, 2) + ":" + time.substring(2, 4) + " " + time.substring(4, 6);
        boolean result;
        if (time.equals(tafficrestarttimehour.getValue()) && tafficrestarttimeday.getText().indexOf(day) != -1) {
            result = true;
            logger.info("Traffic counter time and date are not changing.");
        } else {
            result = false;
            logger.info("Traffic counter time and date are changing.");
        }
        return result;
    }

    public String[] GetStartTimeText() {
        logger.info("Get start time text.");
        String starttime = trafficmeterstarttimetext.getText();
        String date = starttime.substring(starttime.indexOf(":") + 2, starttime.indexOf(",") + 3);
        String time = "";
        if (starttime.substring(starttime.length() - 8, starttime.length() - 6).equals("00")) {
            time = "12" + starttime.substring(starttime.length() - 5, starttime.length() - 3)
                    + starttime.substring(starttime.length() - 2, starttime.length()).toLowerCase();
        } else {
            time = starttime.substring(starttime.length() - 8, starttime.length() - 6)
                    + starttime.substring(starttime.length() - 5, starttime.length() - 3)
                    + starttime.substring(starttime.length() - 2, starttime.length()).toLowerCase();
        }
        String[] timebase = {
                date, time
        };
        return timebase;
    }

    public boolean CheckTrafficCounterRestart() {
        logger.info("Check traffic counter restart.");
        boolean result;
        String currenttime = trafficmetercurrenttimetext.getText().substring(trafficmetercurrenttimetext.getText().indexOf(":") + 2,
                trafficmetercurrenttimetext.getText().length());
        String date, time = "";
        if (!currenttime.substring(currenttime.indexOf(",") + 2, currenttime.indexOf(",") + 3).equals(" ")) {
            if (Integer.valueOf(currenttime.substring(currenttime.indexOf(",") + 1, currenttime.indexOf(",") + 3)) > 28) {
                date = "Last";
            } else {
                date = currenttime.substring(currenttime.indexOf(",") + 1, currenttime.indexOf(",") + 3);
            }
        } else {
            date = currenttime.substring(currenttime.indexOf(",") + 1, currenttime.indexOf(",") + 2);
        }
        if (Integer.valueOf(currenttime.substring(currenttime.length() - 5, currenttime.length() - 3)) < 57) {
            if (Integer.valueOf(currenttime.substring(currenttime.length() - 5, currenttime.length() - 3)) < 8) {
                if (currenttime.substring(currenttime.length() - 8, currenttime.length() - 6).equals("00")) {
                    time = "120" + String.valueOf(Integer.valueOf(currenttime.substring(currenttime.length() - 5, currenttime.length() - 3)) + 3)
                            + currenttime.substring(currenttime.length() - 2, currenttime.length()).toLowerCase();
                } else {
                    time = currenttime.substring(currenttime.length() - 8, currenttime.length() - 6) + "0"
                            + String.valueOf(Integer.valueOf(currenttime.substring(currenttime.length() - 5, currenttime.length() - 3)) + 3)
                            + currenttime.substring(currenttime.length() - 2, currenttime.length()).toLowerCase();
                }
            } else {
                if (currenttime.substring(currenttime.length() - 8, currenttime.length() - 6).equals("00")) {
                    time = "12" + String.valueOf(Integer.valueOf(currenttime.substring(currenttime.length() - 5, currenttime.length() - 3)) + 3)
                            + currenttime.substring(currenttime.length() - 2, currenttime.length()).toLowerCase();
                } else {
                    time = currenttime.substring(currenttime.length() - 8, currenttime.length() - 6)
                            + String.valueOf(Integer.valueOf(currenttime.substring(currenttime.length() - 5, currenttime.length() - 3)) + 3)
                            + currenttime.substring(currenttime.length() - 2, currenttime.length()).toLowerCase();
                }
            }
        } else {
            MyCommonAPIs.sleepi(180);
            OpenTrafficMeterPage();
            currenttime = trafficmetercurrenttimetext.getText().substring(trafficmetercurrenttimetext.getText().indexOf(":") + 2,
                    trafficmetercurrenttimetext.getText().length());
            if (currenttime.substring(currenttime.length() - 8, currenttime.length() - 6).equals("00")) {
                time = "120" + String.valueOf(Integer.valueOf(currenttime.substring(currenttime.length() - 5, currenttime.length() - 3)) + 3)
                        + currenttime.substring(currenttime.length() - 2, currenttime.length()).toLowerCase();
            } else {
                time = currenttime.substring(currenttime.length() - 8, currenttime.length() - 6) + "0"
                        + String.valueOf(Integer.valueOf(currenttime.substring(currenttime.length() - 5, currenttime.length() - 3)) + 3)
                        + currenttime.substring(currenttime.length() - 2, currenttime.length()).toLowerCase();
            }
        }
        TrafficCounterConfig(time, date);
        MyCommonAPIs.sleepi(130);
        OpenTrafficMeterPage();
        MyCommonAPIs.sleepi(5);
        String[] starttime = GetStartTimeText();
        if (date.equals("Last")) {
            date = "29,30,31";
        }
        if (date.indexOf(starttime[0].substring(starttime[0].indexOf(",") + 1, starttime[0].length() - 1)) != -1 && starttime[1].equals(time)
                && Double.valueOf(GetTodayTrafficVolume("total")).intValue() < 0.1) {
            logger.info("Traffic counter restart success.");
            result = true;
        } else {
            logger.info("Traffic counter restart unsuccess.");
            result = false;
        }
        return result;
    }

    public boolean CheckStartTime() {
        logger.info("Check start time.");
        boolean result;
        String currenttime = trafficmetercurrenttimetext.getText().substring(trafficmetercurrenttimetext.getText().indexOf(":") + 2,
                trafficmetercurrenttimetext.getText().length());
        String starttime = trafficmeterstarttimetext.getText().substring(trafficmeterstarttimetext.getText().indexOf(":") + 2,
                trafficmeterstarttimetext.getText().length());
        if (currenttime.substring(0, currenttime.length() - 2).equals(starttime.substring(0, starttime.length() - 2))) {
            if (Integer.valueOf(currenttime.substring(currenttime.length() - 5, currenttime.length() - 3)) > 1
                    && Integer.valueOf(currenttime.substring(currenttime.length() - 5, currenttime.length() - 3)) < 59) {
                if (Integer.valueOf(currenttime.substring(currenttime.length() - 5, currenttime.length() - 3))
                        - Integer.valueOf(starttime.substring(starttime.length() - 5, starttime.length() - 3)) <= 2) {
                    logger.info("Start time changed.");
                    result = true;
                } else {
                    logger.info("Start time no changed.");
                    result = false;
                }
            } else {
                MyCommonAPIs.sleepi(120);
                OpenTrafficMeterPage();
                MyCommonAPIs.sleepi(5);
                ClickTrafficRestartButton();
                currenttime = trafficmetercurrenttimetext.getText().substring(trafficmetercurrenttimetext.getText().indexOf(":") + 2,
                        trafficmetercurrenttimetext.getText().length());
                starttime = trafficmeterstarttimetext.getText().substring(trafficmeterstarttimetext.getText().indexOf(":") + 2,
                        trafficmeterstarttimetext.getText().length());
                if (Integer.valueOf(currenttime.substring(currenttime.length() - 5, currenttime.length() - 3))
                        - Integer.valueOf(starttime.substring(starttime.length() - 5, starttime.length() - 3)) <= 2) {
                    logger.info("Start time changed.");
                    result = true;
                } else {
                    logger.info("Start time no changed.");
                    result = false;
                }
            }
        } else {
            logger.info("Start time no changed.");
            result = false;
        }
        return result;
    }

    public void robotkeyboardinput(String inputtext) {
        logger.info("Switch robot keyboard.");
        try {
            Robot robot = new Robot();
            switch (inputtext) {
            case "0":
                robot.keyPress(KeyEvent.VK_NUMPAD0);
                MyCommonAPIs.sleepi(3);
                break;
            case "1":
                robot.keyPress(KeyEvent.VK_NUMPAD1);
                MyCommonAPIs.sleepi(3);
                break;
            case "2":
                robot.keyPress(KeyEvent.VK_NUMPAD2);
                MyCommonAPIs.sleepi(3);
                break;
            case "3":
                robot.keyPress(KeyEvent.VK_NUMPAD3);
                MyCommonAPIs.sleepi(3);
                break;
            case "4":
                robot.keyPress(KeyEvent.VK_NUMPAD4);
                MyCommonAPIs.sleepi(3);
                break;
            case "5":
                robot.keyPress(KeyEvent.VK_NUMPAD5);
                MyCommonAPIs.sleepi(3);
                break;
            case "6":
                robot.keyPress(KeyEvent.VK_NUMPAD6);
                MyCommonAPIs.sleepi(3);
                break;
            case "7":
                robot.keyPress(KeyEvent.VK_NUMPAD7);
                MyCommonAPIs.sleepi(3);
                break;
            case "8":
                robot.keyPress(KeyEvent.VK_NUMPAD8);
                MyCommonAPIs.sleepi(3);
                break;
            case "9":
                robot.keyPress(KeyEvent.VK_NUMPAD9);
                MyCommonAPIs.sleepi(3);
                break;
            case "p":
                robot.keyPress(KeyEvent.VK_P);
                MyCommonAPIs.sleepi(3);
                break;
            case "a":
                robot.keyPress(KeyEvent.VK_A);
                MyCommonAPIs.sleepi(3);
                break;
            }
        } catch (Exception e) {
            logger.info("Input text fail.");
        }
    }

    public void CounterTimeInput(String time) {
        logger.info("Counter time input.");
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_BACK_SPACE);
            MyCommonAPIs.sleepi(3);
            for (int i = 0; i < time.length(); i++) {
                String num = time.substring(i, i + 1);
                if (time.length() == 5 && i == 1) {
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_SEMICOLON);
                    MyCommonAPIs.sleepi(3);
                } else if (time.length() == 6 && i == 2) {
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_SEMICOLON);
                    MyCommonAPIs.sleepi(3);
                } else if (time.length() == 6 && i == 4) {
                    robot.keyPress(KeyEvent.VK_SPACE);
                    robotkeyboardinput(num);
                    break;
                } else if (time.length() == 5 && i == 3) {
                    robot.keyPress(KeyEvent.VK_SPACE);
                    robotkeyboardinput(num);
                    break;
                }
                robotkeyboardinput(num);
            }
        } catch (Exception e) {
            logger.info("Input text fail.");
        }
    }

    public void TrafficCounterDefault() {
        logger.info("Traffic counter default.");
        // EnableTrafficMeter();
        tafficrestarttimehour.click();
        MyCommonAPIs.sleepi(3);
        CounterTimeInput("1200am");
        tafficrestarttimeday.click();
        MyCommonAPIs.sleepi(3);
        trafficcounterdaydefault();
        tafficmeterapplybutton.click();
        MyCommonAPIs.sleepi(3);
    }

    public void TrafficCounterConfig(String time, String day) {
        logger.info("Config traffic counter.");
        EnableTrafficMeter();
        tafficrestarttimehour.click();
        MyCommonAPIs.sleepi(3);
        CounterTimeInput(time);
        tafficrestarttimeday.click();
        MyCommonAPIs.sleepi(3);
        trafficcounterdayselect(day);
        tafficmeterapplybutton.click();
        MyCommonAPIs.sleepi(3);
    }

    public void DisableTrafficMeter() {
        logger.info("Disable traffic meter.");
        if (trafficmeterchecked.getAttribute("class").equals("ant-checkbox ant-checkbox-checked")) {
            trafficmeterenableordisable.selectRadio("on");
            MyCommonAPIs.sleepi(3);
            tafficmeterapplybutton.click();
        }
        MyCommonAPIs.sleepi(3);
        logger.info("Disable traffic meter successful.");
    }

    public void EnableTrafficMeter() {
        logger.info("Enable traffic meter.");
        MyCommonAPIs.sleepi(10);
        System.out.println(trafficmeterchecked.getAttribute("class"));
        if (trafficmeterchecked.getAttribute("class").equals("ant-checkbox")) {
            trafficmeterenableordisable.selectRadio("on");
            MyCommonAPIs.sleepi(3);
            tafficmeterapplybutton.click();
        }
        MyCommonAPIs.sleepi(3);
        logger.info("Enable traffic meter successful.");
    }

    public String GetTodayTrafficVolume(String option) {
        logger.info("Get today traffic volume.");
        String volume = "";
        if (option.equals("upload")) {
            volume = trafficmetertrafficvolumeupload.getText();
            logger.info("Get today upload traffic volume.");
        } else if (option.equals("download")) {
            volume = trafficmetertrafficvolumedownload.getText();
            logger.info("Get today download traffic volume.");
        } else if (option.equals("total")) {
            volume = trafficmetertrafficvolumetotal.getText();
            logger.info("Get today total traffic volume.");
        }
        return volume;
    }

    public void ClickTrafficRestartButton() {
        logger.info("Click traffic restart button.");
        // OpenTrafficMeterPage();
        // MyCommonAPIs.sleepi(5);
        trafficmeterenableordisable.scrollTo();
        tafficrestrartnowbutton.click();
        MyCommonAPIs.sleepi(10);
        trafficrestartdialogokbutton.click();
        MyCommonAPIs.sleepi(3);
    }

    public boolean CheckTrafficCounterRestartInput(String option) {
        logger.info("Check traffic counter restart.");
        boolean result = true;
        EnableTrafficMeter();
        String time = tafficrestarttimehour.getAttribute("value");
        String day = tafficrestarttimeday.getText();
        MyCommonAPIs.sleepi(3);
        tafficrestrartnowbutton.click();
        MyCommonAPIs.sleepi(3);
        if (trafficrestartdialog.getText().equals("Restart Counter?") && option.equals("ok")) {
            logger.info("Check traffic counter restart dialog ok button.");
            trafficrestartdialogokbutton.click();
            MyCommonAPIs.sleepi(3);
            if (time.equals(tafficrestarttimehour.getAttribute("value")) && day.equals(tafficrestarttimeday.getText())
                    && GetTodayTrafficVolume("total").equals("0.00")) {
                result = true;
                logger.info("Traffic counter restart dialog ok button effective.");
            }
        } else if (trafficrestartdialog.getText().equals("Restart Counter?") && option.equals("cancel")) {
            logger.info("Check traffic counter restart dialog cancel button.");
            trafficrestartdialogcancelbutton.click();
            MyCommonAPIs.sleepi(3);
            if (time.equals(tafficrestarttimehour.getAttribute("value")) && day.equals(tafficrestarttimeday.getText())
                    && !GetTodayTrafficVolume("total").equals("0.00")) {
                result = true;
                logger.info("Traffic counter restart dialog cancel button effective.");
            }
        } else if (trafficrestartdialog.getText().equals("Restart Counter?") && option.equals("checkdut")) {
            trafficrestartdialogokbutton.click();
            MyCommonAPIs.sleepi(30);
            Selenide.refresh();
            MyCommonAPIs.sleepi(10);
            if (tafficrestrartnowbutton.exists()) {
                result = true;
                logger.info("DUT not restart after traffic counter restart.");
            }
        } else {
            result = false;
            logger.info("Traffic counter restart effective.");
        }
        return result;
    }

    public void ClickTrafficStatusButton() {
        logger.info("Click traffic status button.");
        tafficmetertafficstatusbutton.click();
        MyCommonAPIs.sleepi(3);
    }

    public void SetPollInterval(String interval) {
        logger.info("Set poll interval.");
        MyCommonAPIs.sleepi(5);
        tafficmeterpollinterval.clear();
        tafficmeterpollinterval.sendKeys(interval);
        tafficmetersetinterval.click();
    }

    public boolean Checkdialogtext() {
        logger.info("Check traffic status dialog.");
        boolean result;
        MyCommonAPIs.sleepi(2);
        if (trafficmeterdialogtext.exists()) {
            // System.out.println(trafficmeterdialogtext.getText());
            if (trafficmeterdialogtext.getText().equals("The valid range of Poll Interval is between 5 and 86400 seconds.")) {
                result = true;
                logger.info("Traffic status dialog display.");
            } else if (trafficmeterdialogtext.getText().equals("Watermark value should be less than Monthly Limit.")) {
                result = true;
                logger.info("Traffic limit volume dialog display.");
            } else {
                result = false;
                logger.info("Traffic status dialog not display.");
            }
        } else {
            result = false;
            logger.info("Traffic status dialog not display.");
        }
        return result;
    }

    public void CloseTrafficStatus() {
        logger.info("Close traffic status.");
        trafficmetertrafficstatusexit.click();
        MyCommonAPIs.sleepi(3);
    }

    public boolean CheckWarningPage() {
        boolean result;
        if (trafficmeterwarningpage.exists()) {
            if (trafficmeterwarningpage.getText().indexOf("Your Traffic Meter counter is") != -1
                    && trafficmeterwarningpage.getText().indexOf("before the monthly limit is reached") != -1) {
                result = true;
            } else {
                result = false;
            }
        } else {
            result = false;
        }
        return result;
    }

    public boolean CheckWarningMessage() {
        boolean result;
        logger.info("Check warning message.");
        ClickTrafficStatusButton();
        if (trafficmeterwarningmessage.exists()) {
            if (trafficmeterwarningmessage.getText().indexOf("Your Traffic Meter counter is") != -1
                    && trafficmeterwarningmessage.getText().indexOf("before the monthly limit is reached") != -1) {
                result = true;
                logger.info("Warning message display.");
                trafficmeterwarningmessageok.click();
                MyCommonAPIs.sleepi(3);
                CloseTrafficStatus();
            } else {
                result = false;
                logger.info("Warning message not display.");
            }
        } else {
            result = false;
            logger.info("Warning message not display.");
        }
        return result;
    }
}
