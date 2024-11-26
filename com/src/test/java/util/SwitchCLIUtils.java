package util;

import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import webportal.param.WebportalParam;

public class SwitchCLIUtils {
    final static Logger logger = Logger.getLogger("SwitchCLIUtils");
    // final static boolean isRltkSW = WebportalParam.isRltkSW1;
    public static String switchIp = WebportalParam.sw1IPaddress;
    
    /**
     * Note: it only change ip of sw1, to update other options, you should use {@code setSwitchInfo}
     *
     * @param is2nd
     *              true to connect 2nd switch, <B>remember</B> to set back in tearDown
     */
    public static void setSwitchIp(boolean is2nd) {
        if (is2nd) {
            switchIp = WebportalParam.sw2IPaddress;
            logger.info("set switch master to 2nd switch: " + switchIp);
        } else {
            switchIp = WebportalParam.sw1IPaddress;
            logger.info("set switch master to 1st switch: " + switchIp);
        }
    }

    /**
     * update sw1xx to current ones
     *
     * @param ip
     * @param mode
     */
    public static void setSwitchInfo(String ip, String mode) {
        WebportalParam.isRltkSW1 = WebportalParam.isRltkSW(mode);
        logger.info(String.format("set switch %s to sw1 as master for: ip<%s>/is rest<%s>", mode, ip, WebportalParam.isRltkSW1));
        WebportalParam.sw1IPaddress = ip;
        WebportalParam.sw1Model = mode;
        SwitchTelnet.switchIp = ip;
        switchIp = ip;
    }
    
    /**
     * @param  noTimeout
     *                   false to not have timeout
     * @return
     */
    public static SwitchTelnet connectSW(boolean noTimeout) {
        SwitchTelnet.cliWaitTimeout = SwitchTelnet.cliWaitTimeoutS;
        SwitchTelnet st = new SwitchTelnet(switchIp, WebportalParam.loginDevicePassword, noTimeout);
        return st;
    }
    
    /**
     * @param  noTimeout
     * @param  isEnable
     *                   true to enable, false to conf
     * @return
     */
    public static SwitchTelnet connectSW(boolean noTimeout, boolean isEnable) {
        SwitchTelnet.cliWaitTimeout = SwitchTelnet.cliWaitTimeoutS;
        SwitchTelnet st = new SwitchTelnet(switchIp, WebportalParam.loginDevicePassword, noTimeout);
        if (isEnable) {
            st.setEnable();
        } else {
            st.setEnable(true);
        }
        return st;
    }
    
    /**
     * @param st
     *             handle of current session
     * @param sCmd
     *             send cmd in current session
     * @param sExp
     *             break if text found
     * @param bNot
     *             break if text not found
     */
    public static void waitCliReady(SwitchTelnet st, String sCmd, String sExp, boolean bNot) {
        String ss;
        boolean bTimeout = true;
        for (int i = 0; i <= 100; i++) {
            ss = st.getCLICommand(sCmd);
            boolean bb = ss.contains(sExp);
            if ((bNot && !bb) || (!bNot && bb)) {
                bTimeout = false;
                logger.info("break-for-waitcli");
                break;
            }
            MyCommonAPIs.sleepi(2);
        }
        if (bTimeout) {
            logger.info("timeout-for-waitcli");
        }
    }
    
    /**
     * @param isEnable
     *                 true to cloud mode, false to sa mode
     */
    public static void CloudModeSet(boolean isEnable) {
        SwitchTelnet st = connectSW(true);
        st.setEnable(true);
        String sSta;
        if (!st.isRltkSW) {
            st.sendCLICommand("cloud-managed", null);
            sSta = st.sendCLICommand("show application status CloudAgent", null);
            if (isEnable && !sSta.contains("Operational")) {
                st.sendCLICommand("enable", null);
                st.sendCLICommand("exit", null);
                SwitchTelnet.telnet.setPrompt(st.isRltkSW, false);
                st.sendCLICommand("exit", null);
                //st.getCLICommand("clear logging buffered");
                MyCommonAPIs.sleep(30, "cool down");
                waitCliReady(st, "show logging buffered", "registerInsightCloud:Registration Successfull", false);
            } else if (!isEnable && !(sSta.contains("Disabled") || sSta.contains("Unreachable"))) {
                st.sendCLICommand("no enable", null);
                MyCommonAPIs.sleep(30, "cool down");
                waitCliReady(st, "show application status CloudAgent", "Local mode", false);
            } else {
                logger.info("no action need");
            }
        } else {
            sSta = st.sendCLICommand("do show cloudAgent", "Agent:");
            if (isEnable && !sSta.contains("enabled")) {
                st.sendCLICommand("cloudAgent", null);
                SwitchTelnet.telnet.setPrompt(st.isRltkSW, false);
                st.sendCLICommand("exit", null);
                //st.getCLICommand("clear logging buffered");
                MyCommonAPIs.sleep(30, "cool down");
                waitCliReady(st, "show logging buffered", "registerInsightCloud:Registration Successfull", false);
            } else if (!isEnable && !sSta.contains("disabled")) {
                st.sendCLICommand("no cloudAgent", null);
                SwitchTelnet.telnet.setPrompt(st.isRltkSW, false);
                st.sendCLICommand("exit", null);
                MyCommonAPIs.sleep(30, "cool down");
                waitCliReady(st, "show cloudAgent", "disabled", false);
            } else {
                logger.info("no action need");
            }
        }
    }
    
    public static void cleanVlan() {
        SwitchTelnet st = connectSW(true);
        if (st.isRltkSW) {
            st.setEnable(true);
            String[] sLines = st.sendCLICommandClear("do show vlan", "vlan").split("\n");
            for (String line : sLines) {
                String[] ids = line.split("\\|");
                if (ids.length > 1) {
                    String vlanId = ids[0].trim();
                    if (!(vlanId.equals("1") || vlanId.equals("2") || vlanId.startsWith("408"))) {
                        st.getCLICommand("no vlan " + vlanId);
                    }
                }
            }
        } else {
            st.setEnable(false);
            List<String> lsRet = st.readLines("show vlan");
            int linesNo = Integer.parseInt(lsRet.get(lsRet.size() - 1));
            for (int i = 0; i < (lsRet.size() / 3); i++) {
                String name = lsRet.get(i * linesNo);
                if (name.equals("1") || name.equals("4089")) {
                    continue;
                }
                String cmd = String.format("no vlan %s", name);
                logger.info(name);
                st.getCLICommand(cmd);
            }
        }
    }
    
    public static void cleanIpACL() {
        SwitchTelnet st = connectSW(true);
        st.setEnable(true);
        if (st.isRltkSW) {
            String splitLine = "access list";
            for (String line : st.sendCLICommandClear("do show ip acl", splitLine).split("\n")) {
                if (line.trim().length() > 1) {
                    st.getCLICommand("no ip acl " + line.split(splitLine)[1].trim());
                }
            }
        } else {
            List<String> lsRet = st.readLines("show ip access-lists");
            int linesNo = Integer.parseInt(lsRet.get(lsRet.size() - 1));
            for (int i = 0; i < (lsRet.size() / 5); i++) {
                String name = lsRet.get(i * linesNo);
                String cmd = String.format("no ip access-list %s", name);
                logger.info(name);
                st.getCLICommand(cmd);
            }
        }
    }
    
    public static void cleanMacACL() {
        SwitchTelnet st = connectSW(true);
        st.setEnable(true);
        if (st.isRltkSW) {
            String splitLine = "access list";
            String sLine = st.sendCLICommandClear("do show mac acl", splitLine);
            String[] slines = sLine.split("\n");
            for (String line : slines) {
                if (line.trim().length() > 1) {
                    line = line.trim().split(splitLine)[1].trim();
                    st.getCLICommand("no mac acl " + line);
                }
            }
        } else {
            List<String> lsRet = st.readLines("show mac access-lists");
            int linesNo = Integer.parseInt(lsRet.get(lsRet.size() - 1));
            for (int i = 0; i < (lsRet.size() / 5); i++) {
                String name = lsRet.get(i * linesNo);
                String cmd = String.format("no mac access-list extended %s", name);
                logger.info(name);
                st.getCLICommand(cmd);
            }
        }
    }
    
    public static void cleanTimeSchdule() {
        SwitchTelnet st = connectSW(true);
        st.setEnable(true);
        if (st.isRltkSW) {
            String splitLine = "time-range entry";
            String sLine = st.sendCLICommandClear("do show time-range", splitLine);
            String[] slines = sLine.split("\n");
            for (String line : slines) {
                if (line.trim().length() > 1) {
                    line = line.replaceAll(".*:", "");
                    line = line.replaceAll("\\(.*", "");
                    st.getCLICommand("no time-range " + line.trim());
                }
            }
        } else {
            List<String> lsRet = st.readLines("show time-range");
            int linesNo = Integer.parseInt(lsRet.get(lsRet.size() - 1));
            for (int i = 0; i < (lsRet.size() / 4); i++) {
                String name = lsRet.get(i * linesNo);
                String cmd = String.format("no time-range %s", name);
                logger.info(name);
                st.getCLICommand(cmd);
            }
        }
    }
    
    public static void cleanSNMP(boolean needsleep) {
        SwitchTelnet st = connectSW(true);
        st.setEnable(true);
        if (st.isRltkSW) {
            String splitLine = "255.255.255";
            String sLine = st.sendCLICommandClear("do show snmp community", splitLine);
            String[] slines = sLine.split("\n");
            for (String line : slines) {
                if (line.trim().length() > 1) {
                    String Community = line.split("\\ +")[0].trim();
                    st.getCLICommand(String.format("no snmp community %s", Community));
                }
            }
            
            splitLine = "v2c";
            sLine = st.sendCLICommandClear("do show snmp host", splitLine);
            slines = sLine.split("\n");
            for (String line : slines) {
                if (line.trim().length() > 1) {
                    String Server = line.split("\\ +")[0].trim();
                    String Community = line.split("\\ +")[1].trim();
                    st.getCLICommand(String.format("no snmp host %s %s", Server, Community));
                }
            }
        } else {
            String splitLine = "255.255.255";
            String sLine = st.sendCLICommandClear("show snmpcommunity", splitLine);
            String[] slines = sLine.split("\n");
            for (String line : slines) {
                if (line.trim().length() > 1) {
                    String Community = line.split("\\ +")[0].trim();
                    st.getCLICommand(String.format("no snmp-server community %s", Community));
                }
            }
            
            splitLine = "snmpv2";
            sLine = st.sendCLICommandClear("show snmptrap", splitLine);
            slines = sLine.split("\n");
            for (String line : slines) {
                if (line.trim().length() > 1) {
                    String TrapName = line.split("\\ +")[0].trim();
                    String IPAddress = line.split("\\ +")[1].trim();
                    st.getCLICommand(String.format("no snmptrap %s ipaddr %s", TrapName, IPAddress));
                }
            }
        }
        if (needsleep) {
            MyCommonAPIs.sleep(120, "cool down for stp");
        }
    }
    
    /**
     * rtk cannot do modified on existed route where set by other users
     */
    public static void cleanIpRouter() {
        logger.info("TODO: no need to clean if it's manually");
        // SwitchTelnet st = connectSW(true);
        // st.setEnable(true);
        // if (st.isRltkSW) {
        // st.getCLICommand("no ip route 0.0.0.0/0");
        // } else {
        // st.getCLICommand("no ip route 0.0.0.0 0.0.0.0");
        // }
    }

    public static void cleanRadius() {
        SwitchTelnet st = connectSW(true);
        st.setEnable(true);
        if (st.isRltkSW) {
            String splitLine = "\\|";
            String sLine = st.sendCLICommandClear("do show radius", "1812");
            String[] slines = sLine.split("\n");
            for (String line : slines) {
                if (line.trim().length() > 1) {
                    String sHost = line.trim().split(splitLine)[1].trim();
                    st.getCLICommand("no radius host " + sHost);
                }
            }
        } else {
            String splitLine = "\\s+";
            String sLine = st.sendCLICommandClear("show radius servers | include 1812", null);
            String[] slines = sLine.split("\n");
            for (String line : slines) {
                if (line.trim().length() > 1) {
                    String sHost = line.trim().split(splitLine)[0].trim();
                    if (sHost.contains("*")) {
                        sHost = line.trim().split(splitLine)[1].trim();
                    }
                    st.getCLICommand("no radius server host auth " + sHost);
                }
            }
            sLine = st.sendCLICommandClear("show radius accounting name | include 1813", null);
            if(!sLine.contains("Invalid IPv6")) {
                slines = sLine.split("\n");
                for (String line : slines) {
                    if (line.trim().length() > 1) {
                        String sHost = line.trim().split(splitLine)[1].trim();
                        st.getCLICommand("no radius server host acct " + sHost);
                    }
                }
            }
            

        }
    }
    
    /**
     * Make Switch to use old firmware
     *
     * @param checkOnline
     *                    true to check log over telnet
     */
    public static boolean updateSWFirmwareOld(boolean checkOnline) {
        boolean bUpdated = false;
        SwitchTelnet st = connectSW(false, true);
        String sPatternFile = "((\\d+\\.){3}\\d+)";
        Pattern r = Pattern.compile(sPatternFile);
        Matcher m = r.matcher(WebportalParam.sw1Firmware);
        assertTrue(m.find(), "please check image file name from xml");
        logger.info("checking old swich image to: " + WebportalParam.sw1Firmware);
        String sOldVer = m.group(0);
        boolean noneedcheckcli = false;
        SwitchTelnet.cliWaitTimeout = SwitchTelnet.cliWaitTimeoutL;
        if (!st.isRltkSW) {
            /*
             * unit image1 image2 current-active next-active
             * 1 18.49.11.1 18.51.11.2 image2 image2
             */
            String sPatternConsole = "((\\d+\\.){3}\\d+)\\s+((\\d+\\.){3}\\d+)\\s+(image\\d)\\s+(image\\d)";
            
            r = Pattern.compile(sPatternConsole);
            m = r.matcher(st.sendCLICommandClear("show bootvar", "image"));
            assertTrue(m.find(), "please check image format from console");
            
            String sImageVer1, sImageVer2, sImageActive, sImageNext;
            sImageVer1 = m.group(1);
            sImageVer2 = m.group(3);
            sImageActive = m.group(5);
            sImageNext = m.group(6);
            if (!(sImageVer1.equals(sOldVer) || sImageVer2.equals(sOldVer))) {
                bUpdated = true;
                // assume current image is the latest
                String sImage = "image1";
                if (sImageActive.equals(sImage)) {
                    sImage = "image2";
                }
                SwitchTelnet.telnet.write("copy tftp://" + WebportalParam.TftpSvr + "/" + WebportalParam.sw1Firmware + " " + sImage);
                SwitchTelnet.telnet.readUntil("(y/n)");
                SwitchTelnet.telnet.write("y");
                SwitchTelnet.telnet.readUntil(") #");
                SwitchTelnet.telnet.write("boot system " + sImage);
                SwitchTelnet.telnet.readUntil(") #");
                st.sendCLICommandClear("show sysinfo", null);
                st.sendCLICommandClear("show bootvar", null);
            } else {
                if ((sImageVer1.equals(sOldVer) && sImageActive.equals("image1"))
                        || (sImageVer2.equals(sOldVer) && sImageActive.equals("image2"))) {
                    logger.info("SW is in old fw already");
                    noneedcheckcli = true;
                } else {
                    bUpdated = true;
                    if (sImageVer1.equals(sOldVer) && !sImageNext.equals("image1")) {
                        SwitchTelnet.telnet.write("boot system image1");
                        SwitchTelnet.telnet.readUntil(") #");
                    } else if (sImageVer2.equals(sOldVer) && !sImageNext.equals("image2")) {
                        SwitchTelnet.telnet.write("boot system image2");
                        SwitchTelnet.telnet.readUntil(") #");
                    }
                    SwitchTelnet.telnet.write("");
                    st.sendCLICommandClear("show sysinfo", null);
                    st.sendCLICommandClear("show bootvar", null);
                }
            }
        } else {
            /*
             * 0 18.51.11.1 2018-12-21 14:58:38 Active* tmp_vmlinux.bix
             * 1 18.49.11.2 2018-12-07 18:46:49 Not active
             */
            String bootvar = st.sendCLICommandClear("show bootvar", "ctive");
            String[] lsImageLine = bootvar.split("\n");
            String sPatternLine = "(\\d)\\s+(\\d.*?\\d)\\s+.*?([A|N].*e?)";
            r = Pattern.compile(sPatternLine);
            m = r.matcher(lsImageLine[0]);
            assertTrue(m.find(), "please check image format: " + lsImageLine[0]);
            
            String sImage0 = m.group(1);
            String sVer0 = m.group(2);
            String sStatus = m.group(3);
            String sImageActive, sImageNext;
            if (sStatus.contains("Active")) {
                logger.info("image0 is active");
                sImageActive = "image0";
            } else {
                logger.info("image1 is active");
                sImageActive = "image1";
            }
            if (sStatus.contains("*")) {
                logger.info("image0 is next active");
                sImageNext = "image0";
            } else {
                logger.info("image1 is next active");
                sImageNext = "image1";
            }
            
            m = r.matcher(lsImageLine[1]);
            assertTrue(m.find(), "please check image format: " + lsImageLine[1]);
            String sImage1 = m.group(1);
            String sVer1 = m.group(2);
            
            if (!(sVer0.equals(sOldVer) || sVer1.equals(sOldVer))) {
                bUpdated = true;
                SwitchTelnet.telnet.write("copy tftp://" + WebportalParam.TftpSvr + "/" + WebportalParam.sw1Firmware + " flash://" + sImageNext);
                SwitchTelnet.telnet.readUntil("(y/n)");
                SwitchTelnet.telnet.write("n");
                MyCommonAPIs.sleepi(5);
                SwitchTelnet.telnet.write("n");
                SwitchTelnet.telnet.write("show info");
                SwitchTelnet.telnet.readUntil("#");
                SwitchTelnet.telnet.write("show bootvar");
                SwitchTelnet.telnet.readUntil("#");
                st.sendCLICommandClear("show bootvar", null);
            } else {
                if ((sVer0.equals(sOldVer) && sImageActive.equals("image0")) || (sVer1.equals(sOldVer) && sImageActive.equals("image1"))) {
                    logger.info("SW is in old fw already");
                    st.sendCLICommandClear("show bootvar", null);
                    noneedcheckcli = true;
                } else {
                    bUpdated = true;
                    SwitchTelnet.telnet.write("conf");
                    SwitchTelnet.telnet.readUntil("#");
                    if (sVer0.equals(sOldVer)) {
                        SwitchTelnet.telnet.write("boot system image0");
                    } else {
                        SwitchTelnet.telnet.write("boot system image1");
                    }
                    SwitchTelnet.telnet.readUntil("#");
                    SwitchTelnet.telnet.write("exit");
                    SwitchTelnet.telnet.readUntil("#");
                    st.sendCLICommandClear("show info", null);
                    st.sendCLICommandClear("show bootvar", null);
                }
            }
        }
        
        SwitchTelnet.disconnect();
        st = connectSW(false, true);
        st.switchReboot();
        if (checkOnline && !noneedcheckcli) {
            MyCommonAPIs.waitDeviceOnlineReload();
        }
        return bUpdated;
    }
    
    public static class PortClass {
        /**
         * 0 - auto, 1 - full, 2-half
         */
        public static int     duplexMode;
        /**
         * speed 10 full-duplex
         */
        public static String  sPortFramesize;
        public static String  sPortSpeed;
        public static String  sPortEgressRate;
        public static String  sPortStormControlRate;
        public static String  sPortPvid;
        public static String  sPortQos;
        /**
         * true is shutdown
         */
        public static boolean isShutdown;
        /**
         * all output of port
         */
        public static String  sPortVal;
        
        public static void init(SwitchTelnet st, String portInfo, String runningConfig) {
            sPortVal = portInfo;
            sPortPvid = WebportalParam.includeLines(portInfo, " pvid ", false);
            if (portInfo.contains("shutdown")) {
                isShutdown = true;
            } else {
                isShutdown = false;
            }
            if (st.isRltkSW) {
                sPortFramesize = WebportalParam.includeLines(runningConfig, "jumbo-frame", false);
                sPortEgressRate = WebportalParam.includeLines(portInfo, "rate-limit egress", false);
                sPortQos = WebportalParam.includeLines(portInfo, "qos cos", false);
            } else {
                sPortFramesize = WebportalParam.includeLines(portInfo, "mtu ", false);
                sPortEgressRate = WebportalParam.includeLines(portInfo, "traffic-shape", false);
                sPortQos = WebportalParam.includeLines(portInfo, "vlan priority", false);
            }
            
            sPortStormControlRate = WebportalParam.includeLines(portInfo, "broadcast level", false);
            sPortSpeed = WebportalParam.includeLines(portInfo, "speed ", false);
            if ((st.isRltkSW && sPortVal.contains("duplex full")) || (!st.isRltkSW && sPortVal.contains("full-duplex"))) {
                duplexMode = 1;
            } else if ((st.isRltkSW && sPortVal.contains("duplex half")) || (!st.isRltkSW && sPortVal.contains("half-duplex"))) {
                duplexMode = 2;
            } else {
                duplexMode = 0;
            }
        }
    }
    
    /**
     * interface g1 no auto-negotiate vlan participation include 100 vlan tagging 100 vlan pvid 100 vlan priority 1 speed 10
     * half-duplex storm-control broadcast storm-control broadcast level 100 storm-control multicast storm-control multicast
     * level 100 storm-control unicast storm-control unicast level 100 traffic-shape 100 poe priority High poe power limit
     * dot3af
     * interface g1 switchport hybrid allowed vlan add 4088 untagged pvid 100 no snmp trap storm-control multicast
     * storm-control multicast level 100 storm-control broadcast storm-control broadcast level 100 storm-control
     * unknown-unicast storm-control unknown-unicast level 100 rate-limit egress 100 speed 10 duplex half
     *
     * @param  port
     *              g1~gn
     * @return
     */
    public static String getPortInfo(String port) {
        SwitchTelnet st = connectSW(false, true);
        port = WebportalParam.getSwitchPort(WebportalParam.sw1Model, port);
        PortClass.init(st, st.sendCLICommandClear("show running-config interface " + port, null),
                st.sendCLICommandClear("show running-config", null));
        return PortClass.sPortVal;
    }
    
    /**
     * @param  port
     *              g1~gn
     *              (GC110P) #show poe port info g5
     *              High Max Output Output Intf Power Power Class Power Current Voltage Status Fault (mW) (mW) (mA) (V) Status
     *              ------ ------- -------- -------- ------- ------- ------- ----------------- ----------------- g5 No 15400
     *              Unknown 0 0 0 Searching No Error
     *              Overload Counter .............................. 0 Short Counter ................................. 0 Power
     *              Denied Counter .......................... 0 Absent Counter ................................ 0 Invalid
     *              Signature Counter ..................... 0
     *              andGS110TP# show power inline interfaces g6 Port State Status Priority Class Power Up Max.Power (Admin)
     *              (mW) ---- ----- ---------- -------- ------- ----------- ----------------- g6 Auto off low N/A 802.3at 0
     *              (10000)
     *              Port Overload Short Current Power Denied MPS Absent Invalid Sig. ---- ------------- -------------
     *              ------------- ------------- ------------- g6 0 0 0 0 4053
     *              Port Time Range Status ---- -------------------------------- -------- g6 a In
     * @return
     */
    public static class PoEClass {
        /**
         * true is enabled poe
         */
        public static boolean isEnabled;
        /**
         * power inline limit 10000 poe power limit user-defined 10000
         */
        public static String  sPoEVal;
        /**
         * 0-Pre-Dot3at, 1-Dot3at,2 -legacy, 3-af
         */
        public static int     iPoEStandard;
        
        public static void init(SwitchTelnet st, String result, String runningConfig) {
            if (!st.isRltkSW) {
                sPoEVal = WebportalParam.includeLines(runningConfig, "poe", false);
                if (result.contains("Disabled")) {
                    isEnabled = false;
                } else {
                    isEnabled = true;
                }
                if (result.contains("Pre-Dot3at")) {
                    iPoEStandard = 0;
                } else if (result.contains(" Dot3at")) {
                    iPoEStandard = 1;
                } else if (result.contains(" Legacy")) {
                    iPoEStandard = 2;
                } else if (result.contains(" Disable")) {
                    iPoEStandard = 3;
                }
            } else {
                sPoEVal = WebportalParam.includeLines(runningConfig, "power", false);
                if (result.contains("pre-802.3at")) {
                    iPoEStandard = 0;
                } else if (result.contains(" 802.3at")) {
                    iPoEStandard = 1;
                } else if (result.contains("high inrush")) {
                    iPoEStandard = 2;
                } else if (result.contains("802.3af")) {
                    iPoEStandard = 3;
                }
                if (result.contains("Never")) {
                    isEnabled = false;
                } else {
                    isEnabled = true;
                }
            }
        }
    }
    
    /**
     * @param  port
     *              g1~gn
     * @return
     */
    public static String getPoEInfo(String port) {
        SwitchTelnet st = connectSW(false, true);
        String sLine;
        port = WebportalParam.getSwitchPort(WebportalParam.sw1Model, port);

        if (!st.isRltkSW) {
            sLine = st.sendCLICommandClear("show poe port info " + port, null);
            sLine += st.sendCLICommandClear("show poe port configuration " + port, null);
        } else {
            sLine = st.sendCLICommandClear("show power inline interfaces " + port, null);
        }
        
        PoEClass.init(st, sLine, st.sendCLICommandClear("show running-config interface " + port, null));
        return PoEClass.sPoEVal;
    }
    
    /**
     * monitor session 1 destination interface g5 monitor session 1 source interface g1 monitor session 1 source interface
     * g3 monitor session 1 source interface g4
     * mirror session 1 source interfaces g1,g3-4 rx mirror session 1 source interfaces g1,g3-4 tx mirror session 1
     * destination interface g5 allow-ingress
     *
     * @return
     */
    public static String getPortMirror() {
        SwitchTelnet st = connectSW(false, true);
        if (!st.isRltkSW)
            return st.sendCLICommandClear("show running-config", "monitor");
        else {
            String sRet = st.sendCLICommandClear("show running-config", "mirror");
            if (sRet.length() > 1) {
                sRet = sRet.replace("allow-ingress", "");
                sRet = sRet.replace("interfaces", "interface");
                if (WebportalParam.isSwitcMS510()) {
                    if (sRet.contains("xmg")) {
                        sRet = sRet.replace(",xmg", "\nmirror session 1 source interface xmg");
                        sRet = sRet.replace("-", "\nmirror session 1 source interface xmg");
                    } else {
                        sRet = sRet.replace(",mg", "\nmirror session 1 source interface mg");
                        sRet = sRet.replace("-", "\nmirror session 1 source interface mg");
                    }
                }else if (WebportalParam.isSwitcMS324()) {
                    if (sRet.contains("xmg")) {
                        sRet = sRet.replace(",xmg", "\nmirror session 1 source interface xmg");
                        sRet = sRet.replace("-", "\nmirror session 1 source interface xmg");
                    } else {
                        sRet = sRet.replace(",mg", "\nmirror session 1 source interface mg");
                        sRet = sRet.replace("-", "\nmirror session 1 source interface mg");
                    }
                }else if (WebportalParam.isSwitcMS108()) {
                    if (sRet.contains("mg")) {
                        sRet = sRet.replace(",mg", "\nmirror session 1 source interface mg");
                        sRet = sRet.replace("-", "\nmirror session 1 source interface mg");
                    }
                }}else if (WebportalParam.isSwitcXS516()) {
                    if (sRet.contains("xg")) {
                        sRet = sRet.replace(",xg", "\nmirror session 1 source interface xg");
                        sRet = sRet.replace("-", "\nmirror session 1 source interface xg");
                    }
                }else {
                    sRet = sRet.replace(",g", "\nmirror session 1 source interface g");
                    sRet = sRet.replace("-", "\nmirror session 1 source interface g");
                }
            return sRet;
        }
    }
    
    public static String getIGMPVlan(String vlanId) {
        SwitchTelnet st = connectSW(false, true);
        String tomatch = "dmin";
        if (!st.isRltkSW)
            return st.sendCLICommandClear("show igmpsnooping " + vlanId, tomatch);
        else
            return st.sendCLICommandClear("show ip igmp snooping vlan " + vlanId, tomatch);
    }
    
    /**
     * @return 0 - disabled, 1 - stp, 2 - rstp, 3 - other
     */
    public static int getSTPMode() {
        SwitchTelnet st = connectSW(false, true);
        String sResult;
        if (!st.isRltkSW) {
            sResult = st.sendCLICommandClear("show spanning-tree summary", "Spanning Tree");
            if (sResult.contains("Disabled"))
                return 0;
            if (sResult.contains("802.1d"))
                return 1;
            if (sResult.contains("802.1w"))
                return 2;
            else
                return 3;
        } else {
            sResult = st.sendCLICommandClear("show spanning-tree", "Spanning tree");
            if (sResult.contains("disabled"))
                return 0;
            if (sResult.contains("mode RSTP"))
                return 2;
            else
                return 1;
        }
    }
    
    public static boolean isPortLagSTPMode(String port) {
        SwitchTelnet st = connectSW(false, true);
        port = WebportalParam.getSwitchPort(WebportalParam.sw1Model, port);
        String sResult;
        sResult = st.sendCLICommandClear("show running-config interface " + port, null);
        if (!st.isRltkSW) {
            if (WebportalParam.sw1Model.toLowerCase().contains("xs516") || WebportalParam.sw1Model.toLowerCase().contains("gs724") || WebportalParam.sw1Model.toLowerCase().contains("m4350") || WebportalParam.sw1Model.toLowerCase().contains("m4250")|| WebportalParam.sw1Model.toLowerCase().contains("gs748") || WebportalParam.sw1Model.toLowerCase().contains("gs510tpp")){
                if (!sResult.contains("port mode")) 
                    return true; 
                else
                    return false;
                
            }else {
                if (sResult.contains("port mode"))
                    return true;
                else
                    return false;
            }
            
        } else {
            if (WebportalParam.sw1Model.toLowerCase().contains("gs728") || WebportalParam.sw1Model.toLowerCase().contains("ms108")){
                if (!sResult.contains("spanning-tree"))
                    return true;
                else
                    return false;
                
            }else {
                if (sResult.contains("spanning-tree"))
                    return true;
                else
                    return false;
            }
        }
    }
    
    public static String getLogging() {
        SwitchTelnet st = connectSW(false, true);
        if (!st.isRltkSW)
            return st.sendCLICommandClear2("show logging hosts", null);
        else
            return st.sendCLICommandClear("show logging file", null);
    }
    
    /**
     * @param  port
     *              g4/g5
     * @return      true if port is in lag
     */
    public static boolean isLagPort(String port) {
        SwitchTelnet st = connectSW(false, true);
        port = WebportalParam.getSwitchPort(WebportalParam.sw1Model, port);
        String result = st.sendCLICommandClear("show running-config interface " + port, null);
        // addport lag 2
        // lag 2 mode passive
        if (result.contains(" lag "))
            return true;
        return false;
    }
    
    /**
     * @param  port
     *                g4/g5
     * @param  vlanId
     *                vlan id
     * @return        true if port is in tag
     *                GS108Tv3# show vlan 1330 interfaces g2 membership
     *                ------------------------ VLAN ID : 1330 VLAN Type : Static ---------+-------------- Port | Membership
     *                ---------+-------------- g2 | Tagged ---------+--------------
     *                (GC110P) #show vlan port g1
     *                Port Port Ingress Ingress VLAN ID VLAN ID Acceptable Filtering Filtering Default Interface Configured Current Frame
     *                Types Configured Current Priority --------- ---------- -------- ------------ ---------- --------- -------- g1 1 1
     *                Admit All Disable Disable 0
     *                Protected Port .............................. False Switchport mode: General Mode Operating parameters: Port g1 is
     *                member in:
     *                VLAN Name Egress rule Type ---- --------------------------------- ----------- ------------------ 1 Default Untagged
     *                Default 1330 testvlan Tagged Static 4089 Auto-Video Tagged Auto-Video
     *                Static configuration:
     *                Port g1 is statically configured to:
     *                VLAN Name Egress rule ---- --------------------------------- ----------- 1330 testvlan Tagged
     *                Forbidden VLANS:
     *                VLAN Name ---- ---------------------------------
     */
    public static boolean isTagPort(String port, String vlanId) {
        SwitchTelnet st = connectSW(false, true);
        String result;
        port = WebportalParam.getSwitchPort(WebportalParam.sw1Model, port);
        if (st.isRltkSW) {
            result = st.sendCLICommandClear("show running-config interface " + port, vlanId);
            if (result.contains("tagged") && !result.contains("untagged"))
                return true;
        } else if(WebportalParam.sw1Model.contains("M4350")|| WebportalParam.sw1Model.contains("M4250")){
            result = st.sendCLICommandClear("show running-config interface " + port, vlanId);
            if (result.contains("tagging"))
                return true;            
        }else {        
            result = st.sendCLICommandClear("show vlan port " + port, vlanId);
            if (result.contains("Tagged"))
                return true;
        }
        
        return false;
    }
    
    /**
     * @param  port
     *                g4/g5
     * @param  vlanId
     * @return        refer {@linkplain isTagPort}
     */
    public static boolean isPortInVlan(String port, String vlanId) {
        SwitchTelnet st = connectSW(false, true);
        String result;
        port = WebportalParam.getSwitchPort(WebportalParam.sw1Model, port);
        if (st.isRltkSW) {
            result = st.sendCLICommandClear("show running-config interfaces " + port , vlanId);
            if (result.contains("tagged") || result.contains("untagged"))
                return true;
            return false;
        } else {
            result = st.sendCLICommandClear("show vlan port " + port, vlanId);
            if (result.contains("Tagged") || result.contains("Untagged"))
                return true;
            return false;
        }
    }
    
    /**
     * @return true voice vlan is enable
     */
    public static boolean checkVoiceVlan() {
        SwitchTelnet st = connectSW(false, true);
        String sStatus;
        if (!st.isRltkSW) {
            sStatus = st.sendCLICommandClear("show voice vlan", "Administrative");
        } else {
            sStatus = st.sendCLICommandClear("show voice-vlan", "GigabitEthernet1");
        }
        
        if (sStatus.toLowerCase().contains("disable"))
            return false;
        else
            return true;
    }
    
    /**
     * @return voice vlan ID
     */
    public static String getVoiceVlan() {
        SwitchTelnet st = connectSW(false, true);
        String tomatch = "4088";
        return st.sendCLICommandClear("show running-config", tomatch);
//        if (!st.isRltkSW)
//            return st.sendCLICommandClear("show auto-voip", tomatch);
//        else
//            return st.sendCLICommandClear("show running-config", tomatch);
    }
    
    public static String getVoiceVlan(String tomatch) {
        SwitchTelnet st = connectSW(false, true);
        return st.sendCLICommandClear("show running-config", tomatch);
//        if (!st.isRltkSW)
//            return st.sendCLICommandClear("show auto-voip", tomatch);
//        else
//            return st.sendCLICommandClear("show running-config", tomatch);
    }
    
    /**
     * @param  type
     *                   0 - protocol-based, 1 - oui-based
     * @param  fieldtype
     *                   0 - value(class/pri), 1 - status
     * @param  fuckus
     *                   show auto-voip protocol-based interface all VoIP VLAN ID................................... 4088
     *                   Prioritization Type............................ traffic-class Class
     *                   Value.................................... 7
     *                   Interface Auto VoIP Mode Operational Status --------- -------------- ------------------ g1 Enabled Up g2
     *                   Enabled Up g3 Enabled Up show auto-voip oui-based interface all
     *                   VoIP VLAN ID................................... 4088 Priority....................................... 7
     *                   Interface Auto VoIP Mode Operational Status --------- -------------- ------------------ g1 Enabled Up g2
     *                   Enabled Up
     *                   show voice vlan interface all
     *                   Interface...................................... g1 Voice VLAN Interface Mode......................
     *                   Disabled Voice VLAN COS Override........................ False Voice VLAN DSCP
     *                   Value.......................... 0 Voice VLAN Port Status......................... Disabled Voice VLAN
     *                   Authentication...................... Enabled
     *                   GS108Tv3# show voice-vlan Voice VLAN ID : 4088 Voice VLAN CoS : 5 Voice VLAN Port GigabitEthernet1 :
     *                   enabled Voice VLAN Port GigabitEthernet2 : enabled Voice VLAN Port GigabitEthernet3 : enabled Voice VLAN
     *                   Port GigabitEthernet4 : enabled Voice VLAN Port GigabitEthernet5 : enabled Voice VLAN Port
     *                   GigabitEthernet6 : enabled Voice VLAN Port GigabitEthernet7 : enabled Voice VLAN Port GigabitEthernet8 :
     *                   enabled
     *                   GS108Tv3# show voip VoIP Act : Traffic Class VoIP Pri : 4 VoIP Channel Detected : 0 VoIP Port
     *                   GigabitEthernet1 : enabled VoIP Port GigabitEthernet2 : enabled VoIP Port GigabitEthernet3 : enabled VoIP
     *                   Port GigabitEthernet4 : enabled VoIP Port GigabitEthernet5 : enabled VoIP Port GigabitEthernet6 : enabled
     *                   VoIP Port GigabitEthernet7 : enabled VoIP Port GigabitEthernet8 : enabled VoIP Port LAG1 : disabled VoIP
     *                   Port LAG2 : disabled VoIP Port LAG3 : disabled VoIP Port LAG4 : disabled GS108Tv3# show lldp med
     *                   Fast Start Repeat Count: 3
     *                   Network policy 1 ------------------- Application type: Voice Signaling VLAN ID: 4088 tagged Layer 2
     *                   priority: 0 DSCP: 0 Network policy 2 ------------------- Application type: Voice Signaling VLAN ID: 4088
     *                   tagged Layer 2 priority: 0 DSCP: 0
     *                   Network policy 8 ------------------- Application type: Voice Signaling VLAN ID: 4088 tagged Layer 2
     *                   priority: 0 DSCP: 0
     *                   Port | Capabilities | Network Policy | Location | Inventory | PoE PSE ------ + ------------ +
     *                   -------------- + -------- + --------- + ------- g1 | Yes | Yes | No | No | N/A g2 | Yes | Yes | No | No |
     *                   N/A g3 | Yes | Yes | No | No | N/A g4 | Yes | Yes | No | No | N/A g5 | Yes | Yes | No | No | N/A g6 | Yes
     *                   | Yes | No | No | N/A g7 | Yes | Yes | No | No | N/A g8 | Yes | Yes | No | No | N/A
     *                   lldp med network-policy 1 app voice mode vlan-id val 4088 cos enable auth enable dscp 0 lldp med
     *                   network-policy 2 app voice mode vlan-id val 4088 cos enable auth enable dscp 0
     * @return
     */
    public static String getVoiceInfo(int type, int fieldtype) {
        SwitchTelnet st = connectSW(false, true);
        String port = WebportalParam.getSwitchPort(WebportalParam.sw1Model, 1);
        
        if (!st.isRltkSW) {
            if (type == 0) {
                if (fieldtype == 0)
                    return st.sendCLICommandClear("show auto-voip protocol-based interface all", "Class Value");
                if (fieldtype == 1)
                    return st.sendCLICommandClear("show auto-voip protocol-based interface all", port);
            }
            if (type == 1) {
                if (fieldtype == 0)
                    return st.sendCLICommandClear("show auto-voip oui-based interface all", "Priority");
                if (fieldtype == 1)
                    return st.sendCLICommandClear("show auto-voip oui-based interface all", port);
            }
            if (type == 2) {
                if (fieldtype == 0)
                    return st.sendCLICommandClear("show voice vlan interface all", "VLAN ID");
                if (fieldtype == 1)
                    return st.sendCLICommandClear("show voice vlan interface all", "Interface Mode");
                return st.sendCLICommandClear("show voice vlan interface all", null);
            }
        } else {
            if (type == 0) {
                if (fieldtype == 0)
                    return st.sendCLICommandClear("show voice-vlan", "VLAN CoS");
                if (fieldtype == 1)
                    return st.sendCLICommandClear("show voip", "VoIP Port");
            }
            if (type == 1) {
                if (fieldtype == 0)
                    return st.sendCLICommandClear("show voip", "VoIP Pri");
                if (fieldtype == 1)
                    return st.sendCLICommandClear("show voice-vlan", "VLAN Port");
            }
            if (type == 2) {
                if (fieldtype == 0)
                    return st.sendCLICommandClear("show lldp med", "VLAN ID");
                if (fieldtype == 1)
                    return st.sendCLICommandClear("show lldp med", "Application type");
                return st.sendCLICommandClear("show voice-vlan", null);
            }
        }
        return "";
    }

    public static String getVlan1Ip() {
        SwitchTelnet st = connectSW(false, true);
        String result;
        if (!st.isRltkSW) {
            result = st.sendCLICommandClear("show ip interface vlan 1", null);
            result += st.sendCLICommandClear("show ip vlan", null);
        } else {
            result = st.sendCLICommandClear("show ip", null);
        }
        return result;
    }
    
    public static String getNetworkIp() {
        SwitchTelnet st = connectSW(false, true);
        String result;
        if (!st.isRltkSW) {
            result = st.sendCLICommandClear("show ip management", null);
        } else {
            result = st.sendCLICommandClear("show ip", null);
        }
        return result;
    }

    /**
     * @param  isIPacl
     *                 true to get ip acl
     * @param  vlanId
     *                 TODO
     * @return
     */
    public static String getIpMACACL(boolean isIPacl, String vlanId) {
        SwitchTelnet st = connectSW(false, true);
        String result;
        if (!st.isRltkSW) {
            if (isIPacl) {
                result = st.sendCLICommandClear("show ip access-lists", null);
            } else {
                result = st.sendCLICommandClear("show mac access-lists", null);
            }
            ACLClass.init(st, st.sendCLICommandClear("show running-config", null), isIPacl, vlanId);
        } else {
            if (isIPacl) {
                result = st.sendCLICommandClear("show running-config", null);
            } else {
                result = st.sendCLICommandClear("show running-config", null);
            }
            ACLClass.init(st, result, isIPacl, vlanId);
        }
        
       
        return result;
    }
    
    /**
     * @author laxu
     *         Assume that currently only one vlan has acl
     */
    public static class ACLClass {
        /**
         * true is permit, false is deny
         */
        public static boolean ispermitACL;
        /**
         * contains all of acl lines permit 192.168.1.0 0.0.0.255 permit aa:bb:cc:11:22:00 00:00:00:00:00:00 any
         */
        public static String  aclResult;
        
        /**
         * @param st
         *                TODO
         * @param result
         * @param isIPacl
         * @param vlanId
         *                vlan id attached to acl
         */
        public static void init(SwitchTelnet st, String result, boolean isIPacl, String vlanId) {
            // TODO Auto-generated method stub
            if (isIPacl) {
                /*
                 * permit ip 12.12.12.12 255.255.255.254 12.12.12.13 255.255.255.252 sequence 15 permit protoKey 255 proto 0 sip
                 * 12.12.12.12/255.255.255.254 sport any 0 0 0 0 dip 12.12.12.13/255.255.255.252 dport any 0 0 0 0 igmp any icmp any any
                 * any frag 0 tos any 0 0 asq any mirror any redirect any matchEvery 0 logging 0
                 */
                if (st.isRltkSW) {
                    List<String> permitacl = Arrays.asList("deny");
                    if (WebportalParam.includeLines(result, permitacl).length() > 10) {
                        logger.info("found deny ip acl");
                        ispermitACL = false;
                        aclResult = WebportalParam.includeLines(result, "deny protoKey", false);
                        aclResult += WebportalParam.includeLines(result, "permit protoKey", false);
                    } else {
                        logger.info("found allow ip acl");
                        ispermitACL = true;
                        aclResult = WebportalParam.includeLines(result, "permit protoKey", false);
                    }
                    
                    aclResult = aclResult.replaceAll(".?sequence.*?\\d+.", "");
                    aclResult = aclResult.replaceAll("protoKey.*sip any", "any");
                    aclResult = aclResult.replaceAll("protoKey.*sip.", "");
                    aclResult = aclResult.replaceAll("sport.*dip.", "");
                    aclResult = aclResult.replaceAll(".dport.*", "");
                    aclResult = aclResult.replace("/", " ");
                } else {
                    List<String> permitacl = Arrays.asList("permit_all_");
                    if (WebportalParam.includeLines(result, permitacl).length() > 10) {
                        logger.info("found deny ip acl");
                        ispermitACL = false;
                        aclResult = WebportalParam.includeLines(result, "deny ip", false);
                        aclResult += WebportalParam.includeLines(result, "permit ip", false);
                    } else {
                        logger.info("found allow ip acl");
                        ispermitACL = true;
                        aclResult = WebportalParam.includeLines(result, "permit ip", false);
                    }

                    aclResult = aclResult.replaceAll("ip host.", "");
                    aclResult = aclResult.replaceAll("ip.", "");
                    aclResult = aclResult.replaceAll("host.", "");
                }
            } else {
                if (st.isRltkSW) {
                    List<String> permitacl = Arrays.asList("deny");
                    int length = WebportalParam.includeLines(result, permitacl).length();
                    if (length > 10) {
                        logger.info("***********found deny mac acl");
                        ispermitACL = false;
                        aclResult = WebportalParam.includeLines(result, "deny", true);
                        aclResult += WebportalParam.includeLines(result, "permit", true);
                    } else {
                        logger.info("**************found allow mac acl");
                        ispermitACL = true;
                        aclResult = WebportalParam.includeLines(result, ".*permit.*", true);
                    }
                    
                    aclResult = aclResult.replaceAll(".?sequence.*?\\d+.", "");
                    aclResult = aclResult.replaceAll(".vlan.*", "");
                    aclResult = aclResult.replace("/", " ");
                } else {
                    List<String> permitacl = Arrays.asList("permit_all_");
                    if (WebportalParam.includeLines(result, permitacl).length() > 10) {
                        logger.info("found deny mac acl");
                        ispermitACL = false;
                        aclResult = WebportalParam.includeLines(result, "deny.*:", true);
                        aclResult += WebportalParam.includeLines(result, "permit.*:", true);
                    } else {
                        logger.info("found allow mac acl");
                        ispermitACL = true;
                        aclResult = WebportalParam.includeLines(result, "permit.*:", true);
                    }
                }
                aclResult = aclResult.toLowerCase();
            }
            
            logger.info(String.format("aclResult is <%s><%s>", ispermitACL, aclResult));
        }
    }
    
    public static class IGMPSnoopingClass {
        public static boolean isEnabled;
        public static boolean isVlanEnabled;
        public static String  IGMPSnoopingResult;
        
        public static void init(SwitchTelnet st, String result) {
        }
    }
    
    /**
     * GS108Tv3# show ip igmp snooping vlan 200
     * IGMP Snooping is globaly enabled IGMP Snooping VLAN 200 admin : enabled IGMP Snooping operation mode : enabled IGMP
     * Snooping robustness: admin 2 oper 2 IGMP Snooping query interval: admin 60 sec oper 60 sec IGMP Snooping query max
     * response : admin 10 sec oper 10 sec IGMP Snooping last member query counter: admin 2 oper 2 IGMP Snooping last member
     * query interval: admin 1 sec oper 1 sec IGMP Snooping immediate leave: enabled IGMP Snooping automatic learning of
     * multicast router ports: disabled
     * (GC110P) #show igmpsnooping
     * Admin Mode..................................... Enable Multicast Control Frame Count.................. 0 IGMP header
     * validation......................... Enabled Interfaces Enabled for IGMP Snooping........... None VLANs enabled for
     * IGMP snooping................ 1 (GC110P) #show igmpsnooping 200
     * VLAN ID........................................ 200 IGMP Snooping Admin Mode....................... Enabled Fast
     * Leave Mode................................ Disabled Group Membership Interval (secs)............... 260 Max Response
     * Time (secs)....................... 10 Multicast Router Expiry Time (secs)............ 0 Report Suppression
     * Mode........................ Disabled
     *
     * @param  vlanId
     * @return
     */
    public static String getIGMPSnoopingInfo(String vlanId) {
        SwitchTelnet st = connectSW(false, true);
        String toTest;
        if (st.isRltkSW) {
            IGMPSnoopingClass.IGMPSnoopingResult = st.sendCLICommandClear("show running-config ", "igmp snooping");
            toTest = WebportalParam.includeLines(IGMPSnoopingClass.IGMPSnoopingResult, "igmp snooping", false);
            if (toTest.contains("ip igmp snooping")) {
                System.out.print("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
                IGMPSnoopingClass.isEnabled = true;
            } else {
                System.out.print("ggggggggggggggggggggggggggg");
                IGMPSnoopingClass.isEnabled = false;
            }
            
            toTest = WebportalParam.includeLines(IGMPSnoopingClass.IGMPSnoopingResult, "immediate-leave", false);
            if (toTest.contains(vlanId)) {
                System.out.print("fffffffffffffffffffffffffffffffffffff");
                IGMPSnoopingClass.isVlanEnabled = true;
            } else {
                System.out.print("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
                IGMPSnoopingClass.isVlanEnabled = false;
            }
        } else {
            IGMPSnoopingClass.IGMPSnoopingResult = st.sendCLICommandClear("show igmpsnooping", null);
            toTest = WebportalParam.includeLines(IGMPSnoopingClass.IGMPSnoopingResult, "Admin Mode", false);
            if (toTest.contains("Enable")) {
                System.out.print("dddddddddddddddddddddddddddddddddddddd");
                IGMPSnoopingClass.isEnabled = true;
            } else {
                System.out.print("ccccccccccccccccccccccccccccccccccc");
                IGMPSnoopingClass.isEnabled = false;
            }
            
            String vlanIgmp = st.sendCLICommandClear("show igmpsnooping  " + vlanId, null);
            IGMPSnoopingClass.IGMPSnoopingResult += vlanIgmp;
            toTest = WebportalParam.includeLines(vlanIgmp, "Admin Mode", false);
            if (toTest.contains("Enable")) {
                System.out.print("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
                IGMPSnoopingClass.isVlanEnabled = true;
            } else {
                System.out.print("aaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                IGMPSnoopingClass.isVlanEnabled = false;
            }
        }
        
        IGMPSnoopingClass.init(st, IGMPSnoopingClass.IGMPSnoopingResult);
        return IGMPSnoopingClass.IGMPSnoopingResult;
    }
    
    public static class RadiusClass {
        /**
         * true if radius server is configured
         */
        public static boolean isServerConfiged;
        /**
         * true if dot1x is enabled
         */
        public static boolean isEnabled;
        /**
         * 0 - auto 1 - force-auth 2 - force-unauth
         */
        public static int     portStatus;
        public static String  radiusResult;
        
        public static void init(SwitchTelnet st, String result) {
            if (result.contains("force-auth")) {
                portStatus = 1;
            } else if (result.contains("force-unauth")) {
                portStatus = 2;
            } else {
                portStatus = 0;
            }
        }
    }
    
    /**
     * (GC110P) #show running-config interface g4 interface g4 dot1x port-control force-unauthorized dot1x re-authentication
     * GS108Tv3# show running-config interfaces g8 interface g8 authentication port-control force-auth
     *
     * @param  port
     *              g0-gn
     * @return
     */
    public static String getRadiusInfo(String port) {
        SwitchTelnet st = connectSW(false, true);
        port = WebportalParam.getSwitchPort(WebportalParam.sw1Model, port);
        String result1, result2,resultT;
        if (st.isRltkSW) {
            resultT = st.sendCLICommandClear("show running-config", null);
            result1 = WebportalParam.includeLines(resultT, "radius", false);
            result2 = WebportalParam.includeLines(resultT, "authentication", false);
            //result1 = st.sendCLICommandClear("show running-config", "radius");
            //result2 = st.sendCLICommandClear("show authentication", "Autentication dot1x state");
        } else {
            result1 = st.sendCLICommandClear("show radius servers", null);
            result2 = st.sendCLICommandClear("show dot1x", "Administrative Mode");
        }
        
        if (result1.contains("1812")) {
            RadiusClass.isServerConfiged = true;
        } else {
            RadiusClass.isServerConfiged = false;
        }
        
        if (st.isRltkSW) {
            if (result2.contains("authentication method none")) {
                RadiusClass.isEnabled = false;
            } else {
                RadiusClass.isEnabled = true;
            }
        } else {
            if (result2.contains("disabled") || result2.contains("Disabled")) {
                RadiusClass.isEnabled = false;
            } else {
                RadiusClass.isEnabled = true;
            }
        }
        
        MyCommonAPIs.sleep(20, "cool down for stp");
        if (st.isRltkSW) {
            SwitchTelnet stN = connectSW(false, true);
            RadiusClass.init(st, stN.sendCLICommandClear("show running-config interfaces " + port, null));
        } else {
            RadiusClass.init(st, st.sendCLICommandClear("show running-config interface " + port, null));
        }
        return RadiusClass.radiusResult;
    }
    
    public static void forceMangeportAuth() {
        String sw1Port = WebportalParam.getSwitchPort(WebportalParam.sw1Model, WebportalParam.sw1ManagePort);
        String sw2Port = WebportalParam.getSwitchPort(WebportalParam.sw2Model, WebportalParam.sw2ManagePort);
        boolean sw1RTK = WebportalParam.isRltkSW1;
        boolean sw2RTK = WebportalParam.isRltkSW2;
        String sRTKCMD = "authentication port-control force-auth";
        String sBCMCMD = "dot1x port-control force-authorized";
        
        SwitchTelnet st = connectSW(false, false);
        st.sendCLICommandClear("interface " + sw1Port, null);
        if (sw1RTK) {
            st.sendCLICommandClear(sRTKCMD, null);
        } else {
            st.sendCLICommandClear(sBCMCMD, null);
        }
        SwitchTelnet.disconnect();
        if (WebportalParam.enaTwoSwitchMode) {
            setSwitchIp(true);
            try {
                st = connectSW(false, false);
                st.sendCLICommandClear("interface " + sw2Port, null);
                if (sw2RTK) {
                    st.sendCLICommandClear(sRTKCMD, null);
                } else {
                    st.sendCLICommandClear(sBCMCMD, null);
                }
                SwitchTelnet.disconnect();
            } finally {
                setSwitchIp(false);
            }
        }
    }
    
    public static class SNMPClass {
        public static String  snmpResult;
        public static boolean isTrapEnable;
        public static boolean isReadyOnly;
        
        /**
         * @param st
         *             TODO
         * @param res1
         *             with trap info
         * @param res2
         *             with community info
         */
        public static void init(SwitchTelnet st, String res1, String res2) {
            if (st.isRltkSW) {
                if (res1.contains("no snmp trap auth")) {
                    isTrapEnable = false;
                } else {
                    isTrapEnable = true;
                }
            } else {
                if (res1.contains("no snmp-server enable traps")) {
                    isTrapEnable = false;
                } else {
                    isTrapEnable = true;
                }
            }
            
            if (res2.contains("ro") || res2.contains("Read Only") || res1.contains("Read Only")) {
                isReadyOnly = true;
            } else {
                isReadyOnly = false;
            }
            snmpResult = res1 + res2;
        }
    }
    
    public static String getSNMPInfo() {
        SwitchTelnet st = connectSW(false, true);
        String trapRes, commRes,retTem;
        if (st.isRltkSW) {
            retTem = st.sendCLICommandClear2("show running-config", null); //retTem = st.sendCLICommandClear("show running-config", null);
            trapRes = WebportalParam.includeLines(retTem, "Authentication trap", false);
            commRes = WebportalParam.includeLines(retTem, "snmp community", false);
            commRes += WebportalParam.includeLines(retTem, "snmp host", false);
            //commRes = st.sendCLICommandClear("show snmp community", null);
            //commRes += st.sendCLICommandClear("show snmp host", null);
        } else {
            
            if(WebportalParam.sw1Model.contains("M4")) {
                
                retTem = st.sendCLICommandClear2("show running-config", "snmp"); //retTem = st.sendCLICommandClear("show running-config", null);
                trapRes = WebportalParam.includeLines(retTem, "traps", false);
                commRes = WebportalParam.includeLines(retTem, "snmp-server community", false);
                commRes += WebportalParam.includeLines(retTem, "snmp host", false);
                System.out.println("trapRes" +trapRes);
                System.out.println("commRes" +commRes);
                
                
            }else {
            trapRes = st.sendCLICommandClear("show snmptrap", null);
            commRes = st.sendCLICommandClear("show snmpcommunity", null);
            System.out.println("trapRes="+trapRes);
            if(trapRes.contains("Invalid")) {
                trapRes = st.sendCLICommandClear("show snmp", null);
            }
            System.out.println("trapRes="+trapRes);
            }
        }
        
        SNMPClass.init(st, trapRes, commRes);
        return SNMPClass.snmpResult;
    }
    
    public static String getVlanRoute(String vlanId) {
        SwitchTelnet st = connectSW(false, true);
        return st.sendCLICommandClear("show ip interface vlan " + vlanId, null);
    }
    
    /**
     * @param  poeName
     *                 GS110TPv3# show time-range sanityPoE
     *                 time-range entry: sanityPoE (inactive)
     *                 periodic everyday daily infinite 21:19 to 23:59 may 27 2020 to jun 28 2020
     *                 periodic everyday daily infinite 00:00 to 06:16 may 27 2020 to jun 28 2020
     *                 periodic everyday daily infinite 00:00 to 23:59 dec 22 2020 to dec 30 2020
     *                 (GC752XP) #show time-range sanityPoE
     *                 Time Range Name................................ sanityPoE
     *                 Time Range Status.............................. Inactive
     *                 Entry Number: 1
     *                 Frequency: 0 (Every Day/Week/Month(s))
     *                 Periodic Start Time............................ SUN MON TUE WED THU FRI SAT 21:19
     *                 Periodic End Time.............................. SUN MON TUE WED THU FRI SAT 23:59
     *                 Entry Number: 2
     *                 Frequency: 0 (Every Day/Week/Month(s))
     *                 Periodic Start Time............................ SUN MON TUE WED THU FRI SAT 00:00
     *                 Periodic End Time.............................. SUN MON TUE WED THU FRI SAT 06:16
     *                 (GC752XP) #show time-range daily
     *                 Time Range Name................................ daily
     *                 Time Range Status.............................. Active
     *                 Entry Number: 1
     *                 Absolute Start Time............................ 00:00 16 Dec 2020
     *                 Absolute End Time.............................. 23:59 24 Dec 2020
     * @return
     */
    public static String getPoETimeRange(String poeName) {
        SwitchTelnet st = connectSW(false, true);
        //String toret = st.sendCLICommandClear("show time-range " + poeName, null);
        //if (!st.isRltkSW) {
        //    toret += st.sendCLICommandClear("show running-config", "periodic");
        //}
        String toret = st.sendCLICommandClear("show running-config", "periodic");
        return toret;
    }
    
    public static void triggerSnmpTrap() {
        SwitchTelnet st = connectSW(false, false);
        for (int i = 0; i < 2; i++) {
            st.sendCLICommandClear("no spanning-tree", null);
            MyCommonAPIs.sleep(30, "cool down for stp");
            st.sendCLICommandClear("spanning-tree", null);
        }
    }
    
    public static void SwitchOfflineOnline( String status) {
        if (status.equals("Disconnect")) {
            doSwitchCommandforDeviceDisconnect(1);
            MyCommonAPIs.sleepi(300);
        }
        else if(status.equals("Connect")) {
            doSwitchCommandforDeviceDisconnect(2);         //status is Connected
            MyCommonAPIs.sleepi(300);
        }      
    }
    
    public static void doSwitchCommandforDeviceDisconnect(int cmdIndex) {
        logger.info("Will generate a critical notification");
        SwitchTelnet st = new SwitchTelnet(WebportalParam.sw1IPaddress, WebportalParam.loginDevicePassword, true);
        if (cmdIndex == 1) {
            logger.info("try to disconncet sw");
            st.switchDisconnect();
            new Pause().seconds(60, "sleep for switch disconnect");
        } else if (cmdIndex == 2) {
            logger.info("try to connect sw");
            st.switchConnect();
            new Pause().seconds(60, "sleep for switch connect");
     
        }
        logger.info("try to disconnect the switch via command");
    }
}

