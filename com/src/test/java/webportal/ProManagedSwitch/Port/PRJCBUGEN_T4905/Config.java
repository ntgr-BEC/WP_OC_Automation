package webportal.ProManagedSwitch.Port.PRJCBUGEN_T4905;

/**
 *
 * @author Sumanta
 *
 */
public interface Config {
    public String portDesc      = "port1_desc";
    public String STROM_Restore = "100";
    public String STROM_Set     = "50";

    public static String PORT_SPEED  = "100 Mbps";
    public static String DUPLEX_MODE = "Half";

    public static String PORT_SPEED_RESTORE  = "Auto";
    public static String DUPLEX_MODE_RESTORE = "Auto";

    public static String PORTSPEED_CLI  = "100 half";
    public static String FRAMESIZE_CLI  = "mtu 9198";
    public static String DUPLEXMODE_CLI = "half-duplex";
    public static String STROM_Set_CLI  = "level " + STROM_Set;
}
