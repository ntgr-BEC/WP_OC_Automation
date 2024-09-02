package util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SSHUtils {
    final static Logger logger    = Logger.getLogger("SSHUtils");
    public String       sUserName = "";
    public String       sPasswd   = "";
    public String       sHost     = "";
    int                 timeout   = 15000;
    String              sCMD      = "";
    String[]            sCMDs     = {};
    String              sResult   = "";
    
    public SSHUtils(String user, String pass, String ip) {
        logger.info(String.format("%s - %s - %s", user, pass, ip));
        sUserName = user;
        sPasswd = pass;
        sHost = ip;
    }

    String getOutput(boolean isOneCmd) {
        JSch jsch = new JSch();
        try {
            Session session = jsch.getSession(sUserName, sHost);
            session.setPassword(sPasswd);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect(timeout);

            sResult = "";
            if (isOneCmd) {
                logger.info("-->" + sCMD);
                ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
                InputStream in = channelExec.getInputStream();
                channelExec.setCommand(sCMD);
                channelExec.connect(timeout);
                sResult = IOUtils.toString(in, "UTF-8");
                channelExec.disconnect();

            } else {
                ChannelShell channelShell = (ChannelShell) session.openChannel("shell");
                InputStream inputStream = channelShell.getInputStream();
                channelShell.setPty(true);
                channelShell.connect(15000);

                OutputStream outputStream = channelShell.getOutputStream();
                PrintWriter printWriter = new PrintWriter(outputStream);

                for (String cmd : sCMDs) {
                    logger.info("-->" + cmd);
                    printWriter.println(cmd);
                    printWriter.flush();
                }

                byte[] tmp = new byte[1024];
                while (true) {
                    while (inputStream.available() > 0) {
                        int i = inputStream.read(tmp, 0, 1024);
                        if (i < 0) {
                            break;
                        }
                        sResult += new String(tmp, 0, i);
                    }
                    if (channelShell.isClosed()) {
                        break;
                    }
                    Thread.sleep(1000);
                }
                outputStream.close();
                inputStream.close();
                channelShell.disconnect();
            }

            session.disconnect();
        } catch (JSchException | IOException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        logger.info(String.format("len(%s):str(%s)", sResult.length(), sResult));
        return sResult;
    }

    /**
     * Used as run one command under a shell which shell will be disconnected automatically
     *
     * @param  cmd
     *             -- like "cat /tmp/xagent.log"
     * @return
     */
    public String getOutputOne(String cmd) {
        sCMD = cmd;
        return getOutput(true);
    }

    /**
     * Used as a PTY for bunch commands, caller need to make sure commands has a disconnect action
     * 
     * @param  cmd
     *             -- like {"cd /", "cli", "show system", "exit"}
     * @return
     */
    public String getOutputMore(String[] cmd) {
        sCMDs = cmd;
        return getOutput(false);
    }
    
}
