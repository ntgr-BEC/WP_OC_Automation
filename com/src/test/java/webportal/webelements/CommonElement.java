package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;

import org.openqa.selenium.Keys;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import util.Pause;

public class CommonElement {
    public static SelenideElement txtUploadBackup = $(".in input[type=file]");

    public static void closeDownloadBar() {
        System.out.println("closeDownloadBar");
        Selenide.actions().sendKeys(Keys.chord(Keys.CONTROL, "j")).build().perform();
        new Pause().seconds(2);
        Selenide.actions().sendKeys(Keys.chord(Keys.CONTROL, "w")).build().perform();
        new Pause().seconds(2);
    }

    public static void ChooseFile(String path) {
        System.out.println("choose file: " + path);
        new Pause().seconds(2);
        txtUploadBackup.sendKeys(path);
    }
}
