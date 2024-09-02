/**
 *
 */
package orbi.webelements;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

/**
 * @author bingke.xue
 *
 */
public class DNIOrbiAdvancedBackupSettingsPageElement extends MyCommonAPIs {
    public static SelenideElement backupBtn   = $("#backup");
    public static SelenideElement browseBtn    = $("#browse");
    public static SelenideElement restoreBtn     = $("#restore");
    public static SelenideElement eraseBtn  = $("#erase");

}
