package util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.logging.Logger;

import org.apache.commons.net.telnet.TelnetClient;

import webportal.param.WebportalParam;

/**
 * Telnet操作器,基于commons-net-2.2.jar
 *
 * @author JiangKunpeng
 */
public class TelnetOperator {
    private String              prompt     = ">";                               // 结束标识字符串,Windows中是>,Linux中是#
    private TelnetClient        telnet;
    private BufferedInputStream in;                                             // 输入流,接收返回信息
    private PrintStream         out;                                            // 向服务器写入 命令
    final static Logger         logger     = Logger.getLogger("TelnetOperator");
    boolean                     bNoTimeout = true;
    
    /**
     * @param termtype
     *                 协议类型：VT100、VT52、VT220、VTNT、ANSI
     * @param prompt
     *                 结果结束标识
     */
    public TelnetOperator(String termtype, String prompt) {
        telnet = new TelnetClient(termtype);
        setPrompt(prompt);
    }
    
    public TelnetOperator(String termtype) {
        telnet = new TelnetClient(termtype);
    }
    
    public TelnetOperator() {
        telnet = new TelnetClient();
    }
    
    public void disTimeout() {
        bNoTimeout = true;
    }
    
    /**
     * 登录到目标主机
     *
     * @param ip
     * @param port
     * @param username
     * @param password
     */
    public void login(String ip, int port, String username, String password) {
        int trytimes = 0, loop = 2;
        while (true) {
            trytimes++;
            logger.info(String.format("to:%s with pw: %s(%ds)", ip, password, trytimes));
            try {
                if (!bNoTimeout) {
                    telnet.setDefaultTimeout(30 * 1000);
                    telnet.setConnectTimeout(10 * 1000);
                } else {
                    telnet.setDefaultTimeout(3000 * 1000);
                    telnet.setConnectTimeout(1000 * 1000);
                }
                telnet.connect(ip, port);
                if (!bNoTimeout) {
                    telnet.setSoTimeout(15 * 1000);
                } else {
                    telnet.setSoTimeout(1500 * 1000);
                }
                
                in = new BufferedInputStream(telnet.getInputStream());
                out = new PrintStream(telnet.getOutputStream());
                if (readUntil("User").length() < 2)
                    throw new RuntimeException("not able to see login prompt");
                write(username);
                // Thread.sleep(500);
                readUntil("Password:");
                write(password);
                // Thread.sleep(500);
                // String rs = readUntil(null);
                // if (rs != null && rs.contains("Login Failed"))
                // throw new RuntimeException("登录失败");
            } catch (Exception e) {
                if (trytimes > loop)
                    throw new RuntimeException(e);
                continue;
            }
            
            break;
        }
    }
    
    public void loginEx(String ip, int port, String username, String password) {
        int trytimes = 0, loop = 2;
        String matchUser = "User";
        String matchPrompt = ">";
        while (true) {
            trytimes++;
            logger.info(String.format("to:%s with pw: %s(%ds)", ip, password, trytimes));
            try {
                if (!bNoTimeout) {
                    telnet.setDefaultTimeout(15 * 1000);
                    telnet.setConnectTimeout(8 * 1000);
                } else {
                    telnet.setDefaultTimeout(1500 * 1000);
                    telnet.setConnectTimeout(800 * 1000);
                }
                telnet.connect(ip, port);
                if (!bNoTimeout) {
                    if (WebportalParam.enableDebug) {// for gs752
                        telnet.setSoTimeout(8 * 1000);
                    } else {
                        telnet.setSoTimeout(15 * 1000);
                    }
                } else {
                    telnet.setSoTimeout(1500 * 1000);
                }
                in = new BufferedInputStream(telnet.getInputStream());
                out = new PrintStream(telnet.getOutputStream());
                if (readUntil(matchUser).length() < 2)
                    throw new RuntimeException("not able to see login prompt");
                write(username);
                readUntil("Password:");
                write(password);
                if (bNoTimeout) {
                    readUntil(matchPrompt);
                    return;
                }
                String rs = readUntil(">");
                if (rs.contains(matchUser)) {
                    logger.info("Wrong passwd, try default");
                    write(username);
                    readUntil("Password:");
                    write("password");
                    rs = readUntil(">");
                    if (rs.contains(matchUser)) {
                        logger.info("Wrong passwd, try default in script");
                        write(username);
                        readUntil("Password:");
                        write("password1@");
                        rs = readUntil(">");
                    }
                }
                if (!rs.contains(matchPrompt)) {
                    logger.info(rs);
                    throw new RuntimeException("LoginEx is failed");
                }
            } catch (Exception e) {
                if (trytimes > loop)
                    throw new RuntimeException(e);
                if (!RunCommand.waitSWAlive(ip))
                    throw new RuntimeException("telnet to sw is failed");
                continue;
            }
            
            break;
        }
    }
    
    /**
     * @return read all output at once
     */
    public String readAll() {
        StringBuffer sb = new StringBuffer();
        if ((telnet != null) && telnet.isConnected()) {
            byte[] buff = new byte[1024];
            try {
                int ret_read = 0;
                do {
                    ret_read = in.read(buff);
                    if (ret_read > 0) {
                        sb.append(new String(buff, 0, ret_read));
                    }
                } while (ret_read != -1);
            } catch (Exception e) {
                if (e instanceof SocketTimeoutException) {
                    System.out.println("...#");
                } else {
                    System.out.println("...*");
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
    
    /**
     * 读取分析结果
     *
     * @param  pattern
     *                 匹配到该字符串时返回结果
     * @return
     */
    public String readUntil(String pattern) {
        logger.info(String.format("(%s)", pattern));
        StringBuffer sb = new StringBuffer();
        if ((telnet != null) && telnet.isConnected()) {
            try {
                char lastChar = (char) -1;
                boolean flag = (pattern != null) && (pattern.length() > 0);
                if (flag) {
                    lastChar = pattern.charAt(pattern.length() - 1);
                }
                char ch;
                int code = -1;
                while ((code = in.read()) != -1) {
                    ch = (char) code;
                    sb.append(ch);
                    String tocheck = sb.toString();
                    // to break if see these critical
                    if (tocheck.contains("--More--") || tocheck.endsWith(pattern) || tocheck.endsWith("#") || tocheck.endsWith(prompt) || tocheck.endsWith("Login Failed")) {
                        break;
                    }
                    if (tocheck.contains("Over maximum cli session")) {
                        new RunCommand().shutdownTelnet();
                        break;
                    }
                }
                if (code == -1) {
                    logger.info("warning: cli is disconnected");
                }
            } catch (Exception e) {
                logger.info("error: cli is aborted");
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    
    public String readUntil2(String pattern) {
        logger.info(String.format("(%s)", pattern));
        StringBuffer sb = new StringBuffer();
        if ((telnet != null) && telnet.isConnected()) {
            try {
                char lastChar = (char) -1;
                boolean flag = (pattern != null) && (pattern.length() > 0);
                if (flag) {
                    lastChar = pattern.charAt(pattern.length() - 1);
                }
                char ch;
                int code = -1;
                while ((code = in.read()) != -1) {
                    ch = (char) code;
                    sb.append(ch);
                    String tocheck = sb.toString();
                    // to break if see these critical
                    if (tocheck.contains("--More--") || tocheck.endsWith(prompt) || tocheck.endsWith("Login Failed")) {
                        break;
                    }
                    if (tocheck.contains("Over maximum cli session")) {
                        new RunCommand().shutdownTelnet();
                        break;
                    }
                }
                if (code == -1) {
                    logger.info("warning: cli is disconnected");
                }
            } catch (Exception e) {
                logger.info("error: cli is aborted");
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    
    /**
     * 发送命令
     *
     * @param value
     */
    public void write(String value) {
        logger.info(String.format("(%s)", value));
        if ((telnet != null) && telnet.isConnected()) {
            try {
                out.println(value);
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 发送命令,返回执行结果
     *
     * @param  command
     * @return
     */
    public String sendCommands(String command) {
        logger.info(String.format("(%s)", command));
        String retValue = "";
        try {
            write(command);
            // modify by jim.xie for show running-config
            String ret = readUntil(prompt);
            retValue = ret;
            while (ret.endsWith("--More--")) {
                write("\n");
                ret = readUntil(prompt);
                retValue = retValue + ret;
            }
            //System.out.printf("******retValue=%s",retValue);
            return retValue;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public String sendCommands2(String command, String match) {
        logger.info(String.format("(%s)", command));
        String retValue = "";
        try {
            write(command);
            // modify by jim.xie for show running-config
            String ret = readUntil2(match);
            retValue = ret;
            while (ret.endsWith("--More--")) {
                write("\n");
                ret = readUntil(prompt);
                retValue = retValue + ret;
            }
            //System.out.printf("******retValue=%s",retValue);
            return retValue;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 关闭连接
     */
    public void distinct() {
        logger.info(String.format("quit"));
        try {
            if ((telnet != null) && telnet.isConnected()) {
                telnet.disconnect();
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            // e.printStackTrace();
        }
    }
    
    public void setPrompt(String prompt) {
        if (prompt != null) {
            this.prompt = prompt;
        }
    }
    
    /**
     * @param isGS
     *               true is gs switch
     * @param isConf
     *               true to config
     */
    public static String gs_conf_prompt = ")#";
    public static String gc_conf_prompt = ")#";
    public static String gs_en_prompt   = "# ";
    public static String gc_en_prompt   = ") #";
    
    public void setPrompt(boolean isGS, boolean isConf) {
        if (isGS) {
            if (isConf) {
                setPrompt(gs_conf_prompt);
            } else {
                setPrompt(gs_en_prompt);
            }
        } else {
            if (isConf) {
                setPrompt(gc_conf_prompt);
            } else {
                setPrompt(gc_en_prompt);
            }
        }
    }
    
    public static void main(String[] args) {
        TelnetOperator telnet = new TelnetOperator("VT220", ">"); // Windows,用VT220,否则会乱码
        telnet.login("10.1.1.63", 60000, "admin", "password");
        String rs = telnet.sendCommands("en");
        
        try {
            rs = new String(rs.getBytes("ISO-8859-1"), "GBK"); // 转一下编码
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String rs2 = telnet.sendCommands("show running-config interface g1 | include description");
        try {
            rs2 = new String(rs2.getBytes("ISO-8859-1"), "GBK"); // 转一下编码
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    
    // add by bingke.xue at 2019/6/3
    
    public void loginorbi(String ip, int port, String username, String password) {
        int trytimes = 0, loop = 2;
        prompt = "#";
        
        while (true) {
            trytimes++;
            logger.info(String.format("to:%s with pw: %s(%ds)", ip, password, trytimes));
            try {
                if (!bNoTimeout) {
                    telnet.setDefaultTimeout(30 * 1000);
                    telnet.setConnectTimeout(10 * 1000);
                }
                telnet.connect(ip, port);
                if (!bNoTimeout) {
                    telnet.setSoTimeout(5 * 1000);
                }
                
                in = new BufferedInputStream(telnet.getInputStream());
                out = new PrintStream(telnet.getOutputStream());
                readUntil("login:");
                write(username);
                // Thread.sleep(500);
                readUntil("Password:");
                write(password);
                readUntil(prompt);
                // Thread.sleep(500);
                // String rs = readUntil(null);
                // if (rs != null && rs.contains("Login Failed"))
                // throw new RuntimeException("登录失败");
            } catch (Exception e) {
                if (trytimes > loop)
                    throw new RuntimeException(e);
                continue;
            }
            
            break;
        }
    }
    
    public void loginorbiEx(String ip, int port, String username, String password, String matchUser, String matchPassword, String matchPrompt) {
        int trytimes = 0, loop = 2;
        while (true) {
            trytimes++;
            logger.info(String.format("to:%s with pw: %s(%ds)", ip, password, trytimes));
            try {
                if (!bNoTimeout) {
                    telnet.setDefaultTimeout(15 * 1000);
                    telnet.setConnectTimeout(8 * 1000);
                }
                telnet.connect(ip, port);
                in = new BufferedInputStream(telnet.getInputStream());
                out = new PrintStream(telnet.getOutputStream());
                readUntil(matchUser);
                write(username);
                readUntil(matchPassword);
                write(password);
                if (bNoTimeout) {
                    readUntil(matchPrompt);
                    return;
                }
                String rs = readAll();
                if (rs.contains(matchUser)) {
                    logger.info("Wrong passwd, try default");
                    write(username);
                    readUntil(matchPassword);
                    write(password);
                    rs = readAll();
                    if (rs.contains(matchUser)) {
                        logger.info("Wrong passwd, try default in script");
                        write(username);
                        readUntil(matchPassword);
                        write("password1@");
                        rs = readAll();
                    }
                }
                if (!rs.contains(matchPrompt)) {
                    logger.info(rs);
                    throw new RuntimeException("LoginEx is failed");
                }
            } catch (Exception e) {
                if (trytimes > loop)
                    throw new RuntimeException(e);
                if (!RunCommand.waitSWAlive(ip))
                    throw new RuntimeException("telnet to orbi is failed");
                continue;
            }
            
            break;
        }
    }
    
    public String sendCommandsOrbi(String command) {
        logger.info(String.format("(%s)", command));
        try {
            String s1 = readUntil(prompt);
            logger.info("s1 is" + s1);
            write(command);
            String ss = readUntil(prompt);
            logger.info("ss is" + ss);
            return ss;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
