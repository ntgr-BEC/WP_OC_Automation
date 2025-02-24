package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import webportal.param.WebportalParam;

public class RunCommand {
    final static Logger logger           = Logger.getLogger("RunCommand");
    private String      workSpacePath    = System.getProperty("user.dir").replace("\\", "/");
    private String      workSpaceResPath = workSpacePath + "src/test/resources";
    private String      automationPath   = System.getProperty("user.dir").replace("\\", "/").replace("com", "");
    private String      tclRootPath      = System.getProperty("user.dir").replace("\\", "/").replace("com", "") + "/TCL_ROOT";
    private String      binRootPath      = System.getProperty("user.dir").replace("\\", "/").replace("com", "") + "/BIN";
    private String      tclShellPath     = tclRootPath + "/TESTSUITES/VBSCRIPT-CALL-TCL-SHELL.tcl";
    private String      tclTestCasePath;
    private String      tclTestCaseConfigPath;

    public String         wpBinPath           = workSpacePath + "/bin";
    public String         execPy              = wpBinPath + "/python";
    public String         wpBinToolPath       = wpBinPath + "/tool";
    public String         execcURL            = wpBinToolPath + "/curl.exe";
    String                cURLOption          = "-k -m 120 --connect-timeout 60";
    String                cURLDelOption       = "-k -m 120 --connect-timeout 60 -X \"DELETE\"";
    public static boolean bcURLDelete         = false;
    public String         execcURLOutput      = wpBinPath + "/tool/curl.txt";
    public String         execCports          = wpBinPath + "/tool/cports.exe";
    public String         sKillTelent         = execCports + " /RunAsAdmin /close * * %s 60000";
    public String         execPlink           = wpBinPath + "/tool/plink.exe";
    public String         execSnmpPath        = wpBinPath + "/net-snmp";
    public String         execSnmpget         = execSnmpPath + "/snmpget.exe";
    public String         execSnmptrapd       = execSnmpPath + "/snmptrapd.exe";
    public String         execSnmptrapdConf   = execSnmpPath + "/snmptrapd.conf";
    public String         execSnmptrapdOutput = execSnmpPath + "/snmptrapd.txt";

    // for ap502
    public String txtGetAPVer       = wpBinToolPath + "/getapversion.txt";
    public String txtGetAPVerOutput = wpBinToolPath + "/output_getversion.txt";
    public String txtSetAPVerOutput = wpBinToolPath + "/output_updatefw.txt";
    public String txtUpgradeAPFW    = wpBinToolPath + "/upapfirmware.txt";
    String        sCmdEnableRest    = "python enable_orbi_rest.py";
    String        sCmdEnableSSH     = "python enable_ssh_ap.py";

    /**
     * 执行文件
     *
     * @param commandStr
     */
    public Process exeCmd(String commandStr) {
        return exeCmd(commandStr, null);
    }

    public Process exeCmd(String commandStr, String execPwd) {
        File file;
        Process p = null;
        logger.info(commandStr + " / " + execPwd);
        if (execPwd == null) {
            file = new File(tclRootPath + "/TESTSUITES");
        } else {
            file = new File(execPwd);
        }
        try {
            String line;
            p = Runtime.getRuntime().exec(commandStr, null, file);
            if (p.waitFor(120, TimeUnit.SECONDS)) {
                BufferedReader out = new BufferedReader(new InputStreamReader(p.getInputStream())),
                        err = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                while ((line = out.readLine()) != null) {
                    logger.info(line);
                }
                while ((line = err.readLine()) != null) {
                    logger.info(line);
                }
                out.close();
            }
        } catch (IOException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return p;
    }

    /**
     * @param cmd
     *            the whole cmd to run
     * @param exec
     *            the execute file to be used by killall
     * @param timeout
     *            how long will be treated as timeout
     * @return
     */
    public String getCmdOutput(String cmd, String exec, int timeout) {
        MyCommonAPIs.myCMDMonitor(exec, timeout);
        Process p;
        String lines = "", line = "";
        logger.info(cmd + "/" + exec);
        try {
            p = Runtime.getRuntime().exec(cmd, null, null);
            BufferedReader out = new BufferedReader(new InputStreamReader(p.getInputStream())),
                    err = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            p.waitFor(120, TimeUnit.SECONDS);
            try {
                while ((line = out.readLine()) != null) {
                    lines += line + "\n";
                }
                while ((line = err.readLine()) != null) {
                    lines += line + "\n";
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (IOException | InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        MyCommonAPIs.isCmdStart = false;
        return lines;
    }

    public void SendPostSanityMail() {
        Process proc;
        logger.info(WebportalParam.noStepPass + "/" + WebportalParam.noStepFail);
        if (WebportalParam.noStepPass == 0)
            return;
        String sendMail = String.format("python \"" + execPy + "/sendresult4postsanity.py\" %s %s", WebportalParam.noStepPass,
                WebportalParam.noStepFail);

        exeCmd(sendMail, execPy);
    }

    public void SendSanityMail(boolean isOk, int itotal, int ifail, String fileName) {
        Process proc;
        logger.info(itotal + "/" + ifail + "/" + isOk);
        if (isOk) {
            try {
                proc = Runtime.getRuntime().exec(String.format("C:/Automation/BIN/SanityMail/sendmail.exe wp p %s %s", itotal, ifail));
                try {
                    proc.waitFor(20, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            try {
                proc = Runtime.getRuntime().exec(String.format("C:/Automation/BIN/SanityMail/sendmail.exe wp f %s", fileName));
                try {
                    proc.waitFor(20, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
	
	public void SendeMailPython(boolean isOk, int itotal, int ifail, String fileName) {
        Process proc;
        System.out.println("FileName: " + fileName);
        if (isOk) {
            try {
                final String[] envp = null;
                
                proc = Runtime.getRuntime().exec(String.format("C:/Users/DELL/AppData/Local/Programs/Python/Python312/python.exe mailer.py %s", isOk), envp, new File("C:/Python312"));
                try {
                    
                    proc.waitFor(20, TimeUnit.SECONDS);
                    BufferedReader stdInput = new BufferedReader(new 
                             InputStreamReader(proc.getInputStream()));

                    BufferedReader stdError = new BufferedReader(new 
                         InputStreamReader(proc.getErrorStream()));

                    // Read the output from the command
                    System.out.println("Here is the standard output of the command:\n");
                    String s = null;
                    while ((s = stdInput.readLine()) != null) {
                        System.out.println(s);
                    }
                 // Read any errors from the attempted command
                    System.out.println("Here is the standard error of the command (if any):\n");
                    while ((s = stdError.readLine()) != null) {
                        System.out.println(s);
                    }

                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            try {
                System.out.println(String.format("C:/Python312/python.exe mailer.py %s", fileName));
                final String[] envp = null;
                proc = Runtime.getRuntime().exec(String.format("C:/Users/DELL/AppData/Local/Programs/Python/Python312/python.exe mailer.py %s %s", isOk, fileName), envp, new File("C:/Python312"));
                try {
                    proc.waitFor(20, TimeUnit.SECONDS);
                    BufferedReader stdInput = new BufferedReader(new 
                         InputStreamReader(proc.getInputStream()));

                    BufferedReader stdError = new BufferedReader(new 
                         InputStreamReader(proc.getErrorStream()));
    
                    // Read the output from the command
                    System.out.println("Here is the standard output of the command:\n");
                    String s = null;
                    while ((s = stdInput.readLine()) != null) {
                        System.out.println(s);
                    }
                 // Read any errors from the attempted command
                    System.out.println("Here is the standard error of the command (if any):\n");
                    while ((s = stdError.readLine()) != null) {
                        System.out.println(s);
                    }
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * @param processname
     *            进程名
     * @author lizhenwei
     */
    public void killProcess(String processname) {
        BufferedReader bufferedreader = null;
        logger.info("kill proc: " + processname);
        try {
            Process proc = Runtime.getRuntime().exec("taskkill /F /IM " + processname);
            bufferedreader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            if (proc.waitFor(60, TimeUnit.SECONDS)) {
                while ((line = bufferedreader.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (bufferedreader != null) {
                try {
                    bufferedreader.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        new Pause().seconds(3);
    }

    /**
     * 读文件
     *
     * @param filePath
     * @return file line with \n append
     */

    public static String read(String filePath) {
        StringBuilder str = new StringBuilder();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(filePath));
            String s;
            try {
                while ((s = in.readLine()) != null) {
                    str.append(s + '\n');
                }
            } finally {
                in.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        int iLen = str.length();
        String sStr = str.toString();
        logger.info(String.format("%s contains: %d string, %s", filePath, iLen, sStr.substring(0, iLen < 1000 ? iLen : 1000)));
        return sStr;
    }

    // 写入指定的文本文件，append为true表示追加，false表示重头开始写，
    // text是要写入的文本字符串，text为null时直接返回
    /**
     * 写文件
     *
     * @param filePath
     * @param append
     * @param text
     */
    public void write(String filePath, boolean append, String text) {
        if (text == null)
            return;
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(filePath, append));
            try {
                out.write(text);
            } finally {
                out.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 创建文件
     *
     * @param path
     */
    public void createFile(String path) {
        File f = new File(path);
        if (!f.getParentFile().exists()) {
            try {
                f.getParentFile().mkdirs();
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            f.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * createDir
     *
     * @param destDirName
     * @return
     */
    public static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists())
            return false;
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        // 创建目录
        if (dir.mkdirs())
            return true;
        else
            return false;
    }

    /**
     * 添加一行TCL 命令在指定目录下VBSCRIPT-CALL-TCL-SHELL.tcl
     *
     * @param text
     */
    public void addTclCommand(String text) {
        boolean append = true;
        write(tclShellPath, append, text + "\r\n");
    }

    /**
     * 创建TCL的执行文件
     *
     * @param tclShellpath
     */
    public void createTclShell() {
        write(tclShellPath, false, "");
        addTclCommand("#########################################################################################");
        addTclCommand("#                                                                                       #");
        addTclCommand("#                    THIS FILE WAS GENERATED BY VBSCRIPT DYNAMICALLY                    #");
        addTclCommand("#                                                                                       #");
        addTclCommand("#                            USED TO CALL TCL SHELL FROM QTP                            #");
        addTclCommand("#                                                                                       #");
        addTclCommand("#                        CRATED BY Zhenwei Li,       2017/10/23                         #");
        addTclCommand("#                                                                                       #");
        addTclCommand("#########################################################################################");
        addTclCommand("## Transmited TCL variables from Java....");
        addTclCommand("if { [ catch {");
        // 从XML里读数据
        readXMLIntoTcl();
        String msgOK = "\"Debug Single T3636 for Win7 is PASSED\"";
        String msgNG = "\"Debug Single T3636 for Win7 is FAILED\"";
        addTclCommand("    set NTGR_OK_MSG " + msgOK + "");
        addTclCommand("    set NTGR_NG_MSG " + msgNG + "");
        addTclCommand("    set NTGR_SCRIPT_NAME VBSCRIPT-CALL-TCL-SHELL");
        addTclCommand(
                "    array set ntgr_logOptions {LOG_FLAG 163 LOG_HOST {} LOG_HOST_PATH {} LOG_USER_NAME {} LOG_USER_PASSWD {} LOG_FD 0 TRAFFIC_LOG_FD 0}");
        addTclCommand("    source common_env.tcl");

        addTclCommand("    if { 0 == $argc } {");
        addTclCommand("        Netgear_start_log");
        addTclCommand("    } else {");
        addTclCommand("        Netgear_start_log [lindex $argv 1]");
        addTclCommand("        Netgear_start_traffic_log [lindex $argv 2]");
        addTclCommand("    }");

        addTclCommand("    Netgear_SyncFromConfigDUTXml");
        String sTclFilePath = automationPath + "/CONFIG/META-DATA/ProductParameterTable.tcl";
        addTclCommand("    source " + "\"" + sTclFilePath + "\"");
        addTclCommand("    source " + "\"" + tclTestCaseConfigPath + "\"");
        addTclCommand("    source " + "\"" + tclTestCasePath + "\"");
        addTclCommand("    if { 0 == $argc } {");
        addTclCommand("        Netgear_stop_log");
        addTclCommand("    } else {");
        addTclCommand("        Netgear_stop_log [lindex $argv 1]");
        addTclCommand("    }");

        addTclCommand("} errMsg ] } { ");
        addTclCommand("    source common_env.tcl");
        addTclCommand("    Netgear_log_file TCL__RUNTIME__ERROR $errMsg");
        addTclCommand("    Netgear_log_file $NTGR_SCRIPT_NAME.tcl {Resetting configuration to automation default}");

        addTclCommand("    if { $ConfigBy == \"CLI\"} {");
        addTclCommand("        foreach sw [string trim $NTGR_DUT_LIST] {");
        addTclCommand("            set ipaddr [_get_switch_ip_addr $sw]");
        addTclCommand("            _ntgrResetConfig2AutomationDefault $sw \"tftp://$TftpSvr/$ipaddr.txt\"");
        addTclCommand("        }");
        addTclCommand("    }");

        addTclCommand("    if { 0 == $argc } {");
        addTclCommand("        Netgear_stop_log");
        addTclCommand("    } else {");
        addTclCommand("        Netgear_stop_log [lindex $argv 1]");
        addTclCommand("    }");
        addTclCommand("}");
    }

    public void runTclShell(String tclname) {
        String tclcatlog = tclname.replace(".", "/");
        String filePath = workSpacePath + "/log/" + tclcatlog;
        // 覆盖掉原来automation下的config_port和config_dut.xml
        // File sourceport = new File(workSpacePath + "/src/test/resources/config_port.xml");
        File targetport = new File(automationPath + "/CONFIG/config_port.xml");
        // File sourcedut = new File(workSpacePath + "/src/test/resources/config_dut.xml");
        File targetdut = new File(automationPath + "/CONFIG/config_dut.xml");
        /*
         * try { FileUtils.copyFile(sourceport, targetport); FileUtils.copyFile(sourcedut, targetdut); } catch (IOException e) {
         * // TODO Auto-generated catch block e.printStackTrace(); }
         */
        // 为存TCL日志建立目录
        createDir(filePath);
        tclTestCasePath = workSpacePath + "/src/test/java/" + tclcatlog + ".tcl";
        tclTestCaseConfigPath = workSpacePath + "/src/test/java/" + tclcatlog + ".cfg";

        String commandStr = "cmd /c start tclsh " + tclRootPath + "/TESTSUITES/VBSCRIPT-CALL-TCL-SHELL.tcl --log-file \"" + filePath
                + "/tcl.log\" \"" + filePath + "/traffic.log\"";
        createTclShell();

        Process process = exeCmd(commandStr);
        // 休眠3秒确保TCL脚本的socket server运行起来
    }

    public void readXMLIntoTcl() {
        SAXReader sax = new SAXReader();// 创建一个SAXReader对象
        File xmlFile = new File(workSpacePath + "/src/test/resources/config_port.xml");// 根据指定的路径创建file对象
        Document document = null;
        try {
            document = sax.read(xmlFile);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // 获取document对象,如果文档无节点，则会抛出Exception提前结束
        Element root = document.getRootElement();
        getNodes(root);// 从根节点开始遍历所有节点
    }

    public void getNodes(Element node) {
        // 当前节点的名称、文本内容和属性
        String text = "  set " + node.getName() + " " + '"' + node.getTextTrim() + '"';
        if ("".equals(node.getTextTrim())) {
        } else {
            addTclCommand(text);
        }
        // 递归遍历当前节点所有的子节点
        List<Element> listElement = node.elements();// 所有一级子节点的list
        for (Element e : listElement) {// 遍历所有一级子节点
            getNodes(e);// 递归
        }
    }

    public static boolean isHostAlive(String ip, int port) {
        System.out.printf("isHostAlive: %s/%d\n", ip, port);
        try {
            Socket client = new Socket();
            client.connect(new InetSocketAddress(ip, port), 5 * 1000);
            client.close();
            return true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
        return false;
    }

    /**
     * wait ip reachable up to 150s
     *
     * @param ip
     * @return
     */
    public static boolean waitSWAlive(String ip) {
        boolean flag = false;
        for (int i = 0; i < 10; i++) {
            System.out.printf("waitHostAlive: %s-%d\n", ip, i);
            if (isHostAlive(ip, 80)) { // 5s
                flag = true;
                break;
            }
            new Pause().seconds(10);
        }
        if (!flag) {
            System.out.println("timeout-for-waitSWAlive");
        }

        return flag;
    }

    public void shutdownTelnet() {
        try {
            logger.info("Kill telnet for 2 switches");
            Runtime.getRuntime().exec(String.format(sKillTelent, WebportalParam.sw1IPaddress), null, null);
            new Pause().seconds(1);
            if (WebportalParam.enaTwoSwitchMode) {
                Runtime.getRuntime().exec(String.format(sKillTelent, WebportalParam.sw2IPaddress), null, null);
                new Pause().seconds(1);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    String syslog_path = "C:/tftpd32/";
    String syslog_exec = "tftpd32.exe";
    String syslog_file = "syslog.txt";

    public void restartTftp() {
        killProcess(syslog_exec);
        new File(syslog_path + syslog_file).delete();
        exeCmd(syslog_path + syslog_exec, syslog_path);
    }

    public String readSyslog() {
        try {
            String result = new String(Files.readAllBytes(Paths.get(syslog_path + syslog_file)));
            System.out.println("readSyslog:" + result);
            return result;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "empty";
        }
    }

    public String getSWRESTResult(String host, String api) {
        return getRESTResult(host, 8443, api, null, "https");
    }

    public String getSWRESTResult(String host, String api, String data) {
        return getRESTResult(host, 8443, api, data, "https");
    }

    public String getBRRESTResult(String host, String api) {
        return getRESTResult(host, 9988, api, null, "http");
    }

    public String getBRRESTResult(String host, String api, String data) {
        return getRESTResult(host, 9988, api, data, "http");
    }
    
    public String getBRRESTResult_ap(String host, String api) {
        return getRESTResult(host, 9988, api, null, "http");
    }

    public String getBRRESTResult_ap(String host, String api, String data) {
        return getRESTResult(host, 9988, api, data, "http");
    }
    
    static boolean enableOrbiRestOnce = false;

    public String getOBRESTResultStub(String host, String api, String data) {
        String toRet = "";
        for (int i = 0; i < 2; i++) {
            toRet = getRESTResult(host, 9988, api, data, "http");
            if (toRet.isEmpty() && !enableOrbiRestOnce) {
                // enableOrbiRestOnce = true;
                Process p = exeCmd(
                        String.format("%s -b -p %s -i %s", sCmdEnableRest, WebportalParam.loginDevicePassword, WebportalParam.ob1IPaddress),
                        execPy);
                try {
                    p.waitFor(30, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                continue;
            }
            break;
        }
        return toRet;
    }

    public String getOBRESTResult(String host, String api) {
        return getOBRESTResultStub(host, api, null);
    }

    public String getOBRESTResult(String host, String api, String data) {
        return getOBRESTResultStub(host, api, data);
    }

    public String getRESTResult(String host, int port, String api, String data, String protocol) {
        String sCmd, scurlopt = cURLOption;
        if (bcURLDelete) {
            scurlopt = cURLDelOption;
        }
        if (data == null) {
            sCmd = String.format("\"%s\" -o \"%s\" %s -H \"Content-Type: application/json\" -u admin:%s \"%s://%s:%s/api/v1/%s\"", execcURL,
                    execcURLOutput, scurlopt, WebportalParam.loginDevicePassword, protocol, host, port, api);
        } else {
            sCmd = String.format("\"%s\" -o \"%s\" %s -H \"Content-Type: application/json\" -u admin:%s -d \"%s\" \"%s://%s:%s/api/v1/%s\"",
                    execcURL, execcURLOutput, scurlopt, WebportalParam.loginDevicePassword, data, protocol, host, port, api);
        }

        Thread t = new Thread(MyCommonAPIs.myCurlMonitor);
        t.start();
        // make sure result is updated
        new File(execcURLOutput).delete();
        Process p = null;
        System.out.println(sCmd);
        try {
            p = Runtime.getRuntime().exec(sCmd, null, null);
            p.waitFor(30, TimeUnit.SECONDS);
        } catch (IOException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        MyCommonAPIs.isCurlStart = false;
        try {
            t.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            sCmd = read(execcURLOutput);
        } catch (Throwable e) {
            sCmd = "";
        }

        System.out.println(sCmd);
        return sCmd;
    }

    /**
     * @param host
     * @param count
     * @param srcIP
     *            Souce Ip to use
     * @return return lost value 100-0
     */
    public int RunPing(String host, int count, String srcIP) {
        ProcessBuilder pb;
        if (srcIP == null) {
            pb = new ProcessBuilder("ping", "-4", "-w", "1000", "-n", "" + count, host);
        } else {
            pb = new ProcessBuilder("ping", "-4", "-w", "1000", "-n", "" + count, "-S", srcIP, host);
        }
        try {
            Process process = pb.start();
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            try {
                process.waitFor(count + 10, TimeUnit.SECONDS);
                String s = "";
                int countError = 0;
                while ((s = stdInput.readLine()) != null) {
                    if (s.contains("unreachable") || s.contains("timed out") || s.contains("expired")) {
                        countError++;
                    }
                    if (s.contains("Lost =")) {
                        System.out.println(s);
                        break;
                    }
                }
                if (countError == count)
                    return 100;
                else {
                    // Lost = 3 (100% loss)
                    int i = s.indexOf("(");
                    int j = s.indexOf("%");
                    return Integer.parseInt(s.substring(i + 1, j));
                }
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return 100;
    }

    public void enableSSH4AP() {
        Process p = exeCmd(String.format("%s -p %s -i %s", sCmdEnableSSH, WebportalParam.loginDevicePassword, WebportalParam.ap1IPaddress), execPy);
        try {
            p.waitFor(30, TimeUnit.SECONDS);
            new Pause().seconds(10);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void enableSSHByVbs() {
        Runtime run = Runtime.getRuntime();
        try {
            Process process = run.exec("cmd.exe /c " + wpBinPath + "/enableSsh.vbs");
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void enableSSH4AP(String ipadress, String Password) {
        Process p = exeCmd(String.format("%s -p %s -i %s", sCmdEnableSSH, Password, ipadress), execPy);
        try {
            p.waitFor(30, TimeUnit.SECONDS);
            new Pause().seconds(10);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("AP SSH is enabled.");
    }
    
    public void enableSSH4APALL(String ap1IPaddress) {
        Process p = exeCmd(String.format("%s -p %s -i %s", sCmdEnableSSH, WebportalParam.loginDevicePassword, ap1IPaddress), execPy);
        try {
            p.waitFor(30, TimeUnit.SECONDS);
            new Pause().seconds(10);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
        
        public boolean enableSSH4APResponse() {
            Process p = exeCmd(String.format("%s -p %s -i %s", sCmdEnableSSH, WebportalParam.loginDevicePassword, WebportalParam.ap1IPaddress), execPy);
            try {
                p.waitFor(30, TimeUnit.SECONDS);
                new Pause().seconds(10);
                int exitcode=p.exitValue();
                if(exitcode==0)
                {
                    logger.info("SSH enabled successfully");
                    return true;
                }
                else
                {
                    logger.info("SSH enabling failed with exit code: "+exitcode);
                    return false;
                }
                         
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return false;
        }
    
}
