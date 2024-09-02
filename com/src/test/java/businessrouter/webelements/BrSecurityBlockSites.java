package businessrouter.webelements;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;


public class BrSecurityBlockSites {
    public SelenideElement keywordblockingnever   = $x("//*[@id=\"BKS_keyword_Blocking_Never\"]/label/span[1]/input");
    public SelenideElement keywordblockingpre   = $x("//*[@id=\"BKS_keyword_Blocking_Per\"]/label/span[1]/input");
    public SelenideElement keywordblockingalways   = $x("//*[@id=\"BKS_keyword_Blocking_Always\"]/label/span[1]/input");
    public SelenideElement keywordordomainname  = $("#BKS_keyword_domainName");
    public SelenideElement keywordaddbutton   = $("#BKS_keyword_addBut");
    public SelenideElement keywordordomainlist   = $("#keyword_domainlist");
    public SelenideElement keywordordomainlistdeletebutton   = $("#BKS_keyword_delBut");
    public SelenideElement keywordordomainlistclearbutton   = $("#BKS_keyword_clearBut");
    public SelenideElement keywordtrustipenable  = $("#BKS_keyword_allow");
    public SelenideElement keywordtrustip1   = $("#BKS_keyword_IPAddress1");
    public SelenideElement keywordtrustip2  =  $("#BKS_keyword_IPAddress2");
    public SelenideElement keywordtrustip3   = $("#BKS_keyword_IPAddress3");
    public SelenideElement keywordtrustip4  =  $("#BKS_keyword_IPAddress4");
    public SelenideElement keywordblockcancelbutton  =  $("#BKS_keyword_cancel");
    public SelenideElement keywordblockapplybutton  =   $("#BKS_keyword_apply");
    public SelenideElement keywordblockingprechecked = $x("//*[@id=\"BKS_keyword_Blocking_Per\"]/label/span[1]");
    public SelenideElement keywordblockingalwayschecked = $x("//*[@id=\"BKS_keyword_Blocking_Always\"]/label/span[1]");
    public SelenideElement keywordblockingneverchecked = $x("//*[@id=\"BKS_keyword_Blocking_Never\"]/label/span[1]");
    public SelenideElement keywordordomainlistarray = $x("//*[@id=\"keyword_domainlist\"]/option");
    public SelenideElement keywordblockdialog = $x("//div[@class=\"ant-message\"]/span");

}
//*[@id="BKS_keyword_Blocking_Never"]/label/span[1]/input
