package com.DailyTwitchClipCollector.DailyTwitchClipCollector;

import com.DailyTwitchClipCollector.DailyTwitchClipCollector.Util.Dict;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.HashMap;

public class setDownloadPathTest {

    @Test
    void setChromeDownloadPathTest() throws InterruptedException {
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", Dict.FOLDER_LOCATION);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);
        download(options);
    }

    @Test
    void download(ChromeOptions options) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver(options);

        driver.get("https://clipsey.com/");
        String winHandleBefore = driver.getWindowHandle();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));

        WebElement urlBox = driver.findElement(By.className("clip-url-input"));
        WebElement downloadButton = driver.findElement(By.className("get-download-link-button"));

        urlBox.sendKeys("https://clips.twitch.tv/TenaciousPlacidBibimbapShazBotstix-ckJJcOsX-wgYClpS");
        downloadButton.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(30000));

        for(String winHandle : driver.getWindowHandles()){
            System.out.println("windows handle: " + winHandle);
        }

        WebElement downLoadLink = driver.findElement(By.className("download-clip-link"));
        String url = downLoadLink.getAttribute("href");

        downLoadLink.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(30000));
        driver.quit();
    }
}
