/**
 * @author Sujuan.Li
 *
 */
package businessrouter.webelements;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;

public class BrAdvancedStaticRoutesElements {

    public SelenideElement staticrouteaddbutton   = $("#STR_routes_add");
    public SelenideElement staticrouteeditbutton   = $("#STR_routes_edit");
    public SelenideElement staticroutedeletebutton   = $("#STR_routes_delete");
    public SelenideElement staticroutename  = $("#operatorRoute_PanelView_routeName");
    public SelenideElement staticrouteprivate  = $("#operatorRoute_PanelView_Private");
    public SelenideElement staticrouteactive  = $("#operatorRoute_PanelView_Active");
    public SelenideElement staticroutedesipv41  = $("#operatorRoute_SRouteDestAddr1");
    public SelenideElement staticroutedesipv42  = $("#operatorRoute_SRouteDestAddr2");
    public SelenideElement staticroutedesipv43  = $("#operatorRoute_SRouteDestAddr3");
    public SelenideElement staticroutedesipv44  = $("#operatorRoute_SRouteDestAddr4");
    public SelenideElement staticroutedesipmask1  = $("#operatorRoute_SRouteSubnetMask1");
    public SelenideElement staticroutedesipmask2  = $("#operatorRoute_SRouteSubnetMask2");
    public SelenideElement staticroutedesipmask3  = $("#operatorRoute_SRouteSubnetMask3");
    public SelenideElement staticroutedesipmask4  = $("#operatorRoute_SRouteSubnetMask4");
    public SelenideElement staticroutedesgateway1  = $("#operatorRoute_SRouteGatewayAddr1");
    public SelenideElement staticroutedesgateway2  = $("#operatorRoute_SRouteGatewayAddr2");
    public SelenideElement staticroutedesgateway3  = $("#operatorRoute_SRouteGatewayAddr3");
    public SelenideElement staticroutedesgateway4  = $("#operatorRoute_SRouteGatewayAddr4");
    public SelenideElement staticroutemetric  = $("#operatorRoute_Metric");
    public SelenideElement staticroutecancelbutton  = $("#route_cancel_btn");
    public SelenideElement staticrouteapplybutton  = $("#route_apply_btn");
    public SelenideElement allstaticroutesname =  $x("//tr[@class='ant-table-row  ant-table-row-level-0']/td[4]");
    public SelenideElement staticroutecheckbox =  $x("/tr[@class='ant-table-row  ant-table-row-level-0']/td[1])");
    public SelenideElement staticrouteokbutton  = $x("//button[@class = 'ant-btn ant-btn-primary ant-btn-sm']");
    public SelenideElement invalidstaticroutewarning = $x("//div[@class = 'ant-form-explain']");
  

}
