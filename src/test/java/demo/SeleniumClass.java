package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import Tools.SeleniumTools;
import Tools.UnsupportedBrowser;

public class SeleniumClass {

    @Test
    public void denglu() throws UnsupportedBrowser, InterruptedException {
        WebDriver driver =null;
        driver = SeleniumTools.openUrl("http://www.baidu.com");
        Thread.sleep(2000);
        driver.findElement(By.id("kw")).sendKeys("美女");
        driver.findElement(By.id("id")).click();
        }

    }



