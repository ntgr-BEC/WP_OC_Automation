package util;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import webportal.param.WebportalParam;

/**
 * use to switch telnet send command
 *
 * @author zheli
 */
public class SwitchTelnetMNG {
    public static String         switchIp        = WebportalParam.sw1IPaddress;
    public static TelnetOperator telnet          = null;
    public static int            checkcycle      = 5;                              // 10s(rltk)/13s(gc752) one around
    final static Logger          logger          = Logger.getLogger("SwitchTelnet");
    public boolean               isRltkSW        = false;
    public static int            cliWaitTimeoutS = 400;
    public static int            cliWaitTimeoutL = 600;
    public static int            cliWaitTimeout  = cliWaitTimeoutS;
    public static Thread         thrMonitor      = null;

    /**
     * set to false once get output of telnet
     */
    public static boolean isTelnetStart;

    public void preSetup(String ip) {
        switchIp = ip;
        if (telnet != null) {
            telnet.distinct();
        }
        
        if ((thrMonitor != null) && thrMonitor.isAlive()) {
            isTelnetStart = false;
            try {
                thrMonitor.join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        thrMonitor = new Thread(MyCommonAPIs.myTelentMonitor);
        thrMonitor.start();
    }

    public SwitchTelnetMNG(String ip) {
        preSetup(ip);
        telnet = switchlogin(switchIp);
        setEnable();
    }

    /**
     * @param ip
     * @param isConfig
     *                 true to enter config, false only in enable
     */
    public SwitchTelnetMNG(String ip, boolean isConfig) {
        preSetup(ip);
        telnet = switchloginEx(switchIp, WebportalParam.loginDevicePassword, true);
        if (isConfig) {
            setEnable(true);
        } else {
            setEnable();
        }
    }

    public SwitchTelnetMNG(String ip, String passwd) {
        preSetup(ip);
        telnet = switchlogin(switchIp, passwd);
        setEnable();
    }

    /**
     * @param ip
     * @param passwd
     * @param isEx
     *               false to call disTimeout
     */
    public SwitchTelnetMNG(String ip, String passwd, boolean isEx) {
        preSetup(ip);
        telnet = switchloginEx(switchIp, passwd, isEx);
    }

    public void setSWType(boolean var) {
        isRltkSW = var;
    }

    public static void disconnect() {
        telnet.distinct();
    }
    
    public void setEnable() {
        logger.info(String.format("to: %s", switchIp));
        telnet.setPrompt(isRltkSW, false);
        telnet.write("en");
//        if (isRltkSW) {
        telnet.readUntil("Password");
        telnet.write("");
        telnet.readUntil("#");
//        } else {
//            telnet.readUntil("#");
//        }
        sendCLICommand("terminal length 0", null);
    }

    /**
     * @param send
     *             true to conf after enable, false to vlan after enable
     */
    public void setEnable(boolean toconf) {
        logger.info(String.format("to: %s-%s", switchIp, toconf));
        setEnable();
        telnet.setPrompt(isRltkSW, true);
        if (toconf) {
            sendCLICommand("conf", null);
        } else {
            sendCLICommand("vlan database", null);
        }
    }

    public TelnetOperator switchlogin(String ip) {
        isRltkSW = WebportalParam.isRltkSW(ip);
        TelnetOperator telnet = new TelnetOperator("VT220", ">"); // Windows,用VT220,否则会乱码
        if(!isRltkSW) {
        telnet.login(ip, 60000, "admin", WebportalParam.loginDevicePassword);
        }else {
            telnet.login(ip, 23, "admin", WebportalParam.loginDevicePassword);
        }
        return telnet;
    }

                

    public TelnetOperator switchlogin(String ip, String passwd) {
        isRltkSW = WebportalParam.isRltkSW(ip);
        TelnetOperator telnet = new TelnetOperator("VT220", ">"); // Windows,用VT220,否则会乱码
        if(!isRltkSW) {
            telnet.login(ip, 60000, "admin", WebportalParam.loginDevicePassword);
            }else {
                telnet.login(ip, 23, "admin", WebportalParam.loginDevicePassword);
            }
        return telnet;
    }

    public TelnetOperator switchloginEx(String ip, String passwd, boolean noTimeout) {
        
        isRltkSW = WebportalParam.isRltkSW(ip);
        TelnetOperator telnet = new TelnetOperator("VT220", ">"); // Windows,用VT220,否则会乱码
        if (!noTimeout) {
            telnet.disTimeout();
        }
        if(!isRltkSW) {
            telnet.login(ip, 60000, "admin", WebportalParam.loginDevicePassword);
            }else {
                telnet.login(ip, 23, "admin", WebportalParam.loginDevicePassword);
            }
        return telnet;
    }

    public void switchReboot() {
        setEnable();
        if (isRltkSW) {
            telnet.write("reboot");
        } else {
            telnet.write("reload");
            String sGet = telnet.readUntil("(y/n)");
            if (sGet.contains("Would you like to save them now")) {
                telnet.write("y");
                sGet = telnet.readUntil("(y/n)");
                telnet.write("reload");
                sGet = telnet.readUntil("(y/n)");
            }
            telnet.write("y");
        }
        MyCommonAPIs.sleepi(120);
    }

    public void switchDefault() {
        setEnable();
        if (!isRltkSW) {
            telnet.write("restore-defaults");
        } else {
            telnet.write("clear config");
        }
        telnet.readUntil("(y/n)");
        telnet.write("y");
        MyCommonAPIs.sleepi(120);
    }

    /**
     * @param  cmd
     *             The command to get lines like "show ip access-lists"
     * @return     2 type of results, each line will be converted to every fields, last array is count of field
     *             refer @SwitchCLIUtils
     */
    public List<String> readLines(String cmd) {
        String sRet = getCLICommand(cmd);
        String[] lsRet = sRet.split("\r\n");
        int columnCount, spaceLen;
        columnCount = spaceLen = 0;
        List<String> lsLine = new ArrayList<String>();
        List<Integer> liColumnLen = new ArrayList<>();
        boolean bLineStart = false;
        for (String line : lsRet) {
            logger.info(String.format("<%s>", line));
            if (line.trim().length() > 0) {
                if (Pattern.matches("^--+\\s+--.*", line)) { // condition to check where to start a table
                    bLineStart = true;
                    String[] _lsRet = line.split(" +");
                    columnCount = _lsRet.length;
                    Pattern pattern = Pattern.compile("(--+)(\\s+)(--.*)");
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        spaceLen = matcher.group(2).length();
                    } else {
                        System.out.println("ERROR: not able to match");
                    }
                    // use --- to determinate how long for each fields
                    for (int i = 0; i < columnCount; i++) {
                        int j = _lsRet[i].length();
                        liColumnLen.add(j);
                    }
                    continue;
                }

                if (bLineStart) {
                    int lineStart = 0;
                    for (int i = 0; i < columnCount; i++) {
                        int beginIndex = i + lineStart;
                        int endIndex = liColumnLen.get(i) + beginIndex;
                        if (endIndex <= line.length()) {
                            lsLine.add(line.substring(beginIndex, endIndex).trim());
                        } else {
                            if (beginIndex < line.length()) {
                                lsLine.add(line.substring(beginIndex, line.length()).trim());
                            } else {
                                lsLine.add("");
                            }
                        }
                        lineStart = (lineStart + liColumnLen.get(i) + spaceLen) - 1;
                    }
                }
            }
        }

        lsLine.add(String.format("%d", columnCount));
        logger.info(String.format("size->%d", lsLine.size()));
        return lsLine;
    }

    /**
     * checkExample
     *
     * @param expecet
     *                result
     */
    public String readFull(String command) {
        logger.info(String.format("run: %s", command));
        telnet.write(command);
        String sRet1 = "", sRet2;
        while (true) {
            sRet2 = telnet.readAll();
            sRet1 += sRet2;
           
            if (sRet2.contains(") #") || sRet2.contains(")#") || !(sRet2.contains("--More--"))) {
                break;
            }
            telnet.write("\n");
        }
        
        return sRet1.replace("--More-- or (q)uit", "");
    }

    public String sendCLICommandClear(String command, String match) {
        //logger.info(String.format("sendCLICommandClear -->> <%s><%s>", command, match));
        String rs = sendCLICommand(command, match);
        rs = rs.replace(command, "");
        rs = rs.replace("--More--", "");
        System.out.printf("sendCLICommandClear <<-- (%s)\n", rs.length());
        return rs;
    }
    
    public String sendCLICommandClear2(String command, String match) {
        //logger.info(String.format("sendCLICommandClear -->> <%s><%s>", command, match));
        String rs = sendCLICommand2(command, match);
        rs = rs.replace(command, "");
        rs = rs.replace("--More--", "");
        System.out.printf("sendCLICommandClear <<-- (%s)\n", rs.length());
        return rs;
    }

    public String getCLICommand(String command) {
        return sendCLICommandClear(command, null);
    }
    
    /**
     * @param  exp
     *             check whether {@link #checkExample(String)} is in running-config
     * @return
     */
    public String checkExample(String exp) {
        logger.info(String.format("check text: %s", exp));
        String result = null;
        setEnable();
        String cmdTo = String.format("show running-config | include \"%s\"", exp);
        for (int i = 0; i <= checkcycle; i++) {
            result = getCLICommand(cmdTo);
            if (result.contains(exp)) {
                System.out.println("checkExample: break 1");
                return result;
            }
            MyCommonAPIs.sleepi(10);
        }
        System.out.println("checkExample: timeout");
        getCLICommand("show application status CloudAgent");
        return "";
    }

    /**
     * @param  exp
     *                check whether string {@link exp} is in running-config or not
     * @param  notHas
     *                {@link notHas} true to match false, false to check match
     * @return
     */
    public String checkExample(String exp, boolean notHas) {
        logger.info(String.format("check text: %s-%s", exp, notHas));
        String result = null;
        setEnable();
        String cmdTo;

        if (isRltkSW) {
            System.out.println("before show running-config");
            cmdTo = "show running-config";
        } else {
            System.out.println("++++++++++++");
            cmdTo = String.format("show running-config | include \"%s\"", exp);
        }
        boolean bChecked = false;
        for (int i = 0; i <= checkcycle; i++) {
            result = getCLICommand(cmdTo);
            if (isRltkSW) {
                System.out.println("match using iclude lins");
                result = WebportalParam.includeLines(result, exp, false);
            }
            System.out.println("match using iclude lins");
            if ((notHas && (false == result.contains(exp))) || (!notHas && (true == result.contains(exp)))) {
                System.out.println("checkExample: break 2");
                return result;
            }
            MyCommonAPIs.sleepi(10);
        }
        System.out.println("checkExample: timeout");
        if (isRltkSW) {
            getCLICommand("show application status CloudAgent");
        } else {
            getCLICommand("show cloudAgent");
        }
        return "";
    }

    /**
     * @param  command
     * @param  match
     *                 line contains match
     * @return
     */
    public String sendCLICommand(String command, String match) {
        //logger.info(String.format("-->> <%s><%s>", command, match));
        String rs = telnet.sendCommands(command);
        try {
            rs = new String(rs.getBytes("ISO-8859-1"), "UTF-8"); // 转一下编码
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (match != null) {
            rs = WebportalParam.includeLines(rs, match, false);
        }
        logger.info(String.format("<<-- (%s)", rs));
        return rs;
    }
    
    public String sendCLICommand2(String command, String match) {
        //logger.info(String.format("-->> <%s><%s>", command, match));
        String rs = telnet.sendCommands2(command, match);
        try {
            rs = new String(rs.getBytes("ISO-8859-1"), "UTF-8"); // 转一下编码
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (match != null) {
            rs = WebportalParam.includeLines(rs, match, false);
        }
        logger.info(String.format("<<-- (%s)", rs));
        return rs;
    }

    /**
     * @param  port
     *              speed 100
     * @return
     */
    public String getPortSpeed(String port) {
        setEnable();
        String rs2 = sendCLICommandClear("show running-config interface " + port, "speed");
        return rs2;
    }

    public String getPortDescription(String port) {
        setEnable();
        String rs2 = sendCLICommandClear("show running-config interface " + port, "description");
        return rs2;
    }

    public String getMaxFrameSize(String portCLI) {
        setEnable();
        String rs;
        if (isRltkSW) {
            rs = sendCLICommand("show running-config", "jumbo-frame");
        } else {
            rs = sendCLICommand("show running-config interface " + portCLI, "mtu");

        }
        return rs;
    }

    public String getStormControlRate(String portCLI) {
        setEnable();
        String rs = sendCLICommandClear("show running-config interface " + portCLI, "storm-control");
        return rs;
    }

    /**
     * @param  portCLI
     * @return         true for port is shutdown/disable
     */
    public boolean getPortAdminMode(String portCLI) {
        setEnable();
        String rs = sendCLICommandClear("show running-config interface " + portCLI, "shutdown");
        if (rs.contains("shutdown"))
            return false;
        return true;
    }

    public String getEgressRateValue(String portCLI) {
        setEnable();
        String rs;
        if (isRltkSW) {
            rs = sendCLICommandClear("show running-config interface " + portCLI, "rate-limit");
        } else {
            rs = sendCLICommandClear("show running-config interface " + portCLI, "traffic-shape");
            if (!rs.contains("traffic-shape"))
                return "traffic-shape 0";
        }

        return rs;
    }

    /**
     * System Name.................................... GC728X
     * System Name : GC108PP
     *
     * @return
     */
    public String getDeviceName() {
        setEnable();
        String rs, tomatch = "System Name";
        if (!isRltkSW) {
            rs = sendCLICommandClear("show info", tomatch);
            Pattern p = Pattern.compile(".*:(.*)", Pattern.DOTALL);
            Matcher m = p.matcher(rs.trim());
            if (m.matches())
                return m.group(1);
        } else {
            rs = sendCLICommandClear("show sysinfo", tomatch);
            Pattern p = Pattern.compile(".*\\s(.*)", Pattern.DOTALL);
            Matcher m = p.matcher(rs.trim());
            if (m.matches())
                return m.group(1);
        }

        return rs;
    }

    public String getDNS() {
        setEnable();
        String rs = "";
        if (!isRltkSW) {
            rs = sendCLICommandClear("show hosts", "DHCPv4");
            rs += sendCLICommandClear("show hosts", "Static");
        } else {
            rs = sendCLICommandClear("show hosts", "servers");
        }
        return rs;
    }

    public boolean isLoginSuccess() {
        String rs = "";
        try {
            telnet.write("?");
            rs = telnet.readAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (rs.contains("show") || rs.contains("Invalid input"))
            return true;
        return false;
    }

    public String getSystemUptime() {
        setEnable();
        String rs = sendCLICommandClear("show running-config", "System Up Time");
        rs = rs.replace(",", "");
        logger.info(rs);
        return rs;
    }

    public Date getSNTPTime() {
        setEnable();
        String time = sendCLICommandClear("show clock", "UTC").trim();

        logger.info(time);
        SimpleDateFormat myFmt;
        if (!isRltkSW) {
            // Nov 26 10:23:58 2018 UTC(UTC+8)
            time = time.substring(0, 20);
            myFmt = new SimpleDateFormat("MMM dd HH:mm:ss yyyy", Locale.ENGLISH);
        } else {
            // 11:07:45 CN(UTC+8:00) Nov 26 2018
            time = time.substring(time.lastIndexOf(")") + 2, time.length()) + " " + time.substring(0, 8);
            myFmt = new SimpleDateFormat("MMM dd yyyy HH:mm:ss", Locale.ENGLISH);
        }
        Date date = null;
        try {
            date = myFmt.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        logger.info(date.toString());
        return date;
    }

    public boolean checkLoggingStatus() {
        setEnable();
        String result = sendCLICommandClear("show running-config", "logging");
        if (isRltkSW) {
            if (result.contains("logging host\r"))
                return true;
        } else {
            if (result.contains("logging syslog"))
                return true;
        }
        return false;
    }

    /**
     * show ip interface brief'(BRCM) or 'show ip interface'(RTK)
     *
     * @param  vlanId
     * @return        true vlan has dhcp enabled
     */
    public boolean checkVlanDHCP(String vlanId) {
        String result = sendCLICommandClear("show running-config interface vlan " + vlanId, null);
        if ((isRltkSW && result.contains("ip address")) || (!isRltkSW && !result.contains("ip address dhcp")))
            return false;
        else
            return true;
    }
}
