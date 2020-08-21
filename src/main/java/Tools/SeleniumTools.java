package Tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.qa.Log4jUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;

public class SeleniumTools {

    static Logger log = Log4jUtils.getLogger(SeleniumTools.class);

    /* 默认打开浏览器 */
    public static String defaultBrowser = "";

    /* 火狐浏览器的安装路径 */
    public static String firefoxBin = "";

    /* 火狐浏览器的驱动程序路径 */
    public static String geckoDriver = "";

    /* IE浏览器的驱动程序路径 */
    public static String ieDriver = "";

    /* 谷歌浏览器的驱动程序路径 */
    public static String chromeDriver = "";

    static {
        Properties properties = new Properties();
        try {
            // 加载res目录下的selenium.properties文件
            properties.load(new FileInputStream(new File("res/selenium.properties")));

            // 读取selenium.properties文件中的default选项
            defaultBrowser = properties.getProperty("default");
            firefoxBin = properties.getProperty("firefox.bin");
            geckoDriver = properties.getProperty("gecko.driver");
            ieDriver = properties.getProperty("ie.driver");
            chromeDriver = properties.getProperty("chrome.driver");

        } catch (FileNotFoundException e) {
            log.error("res目录不存在或者res目录下的selenium.properties文件不存在，请确认");
            e.printStackTrace();
        } catch (IOException e) {
            log.error("读取异常");
            e.printStackTrace();
        }
    }

    /**
     * 不指定浏览器，那么使用默认浏览器打开网页
     *
     * @throws UnsupportedBrowser
     */
    public static WebDriver openUrl(String url) throws UnsupportedBrowser {

        // 根据默认浏览器创建浏览器驱动对象
        log.debug("创建浏览器驱动对象，打开了" + defaultBrowser + "浏览器");
        WebDriver driver = createDriver(defaultBrowser);

        // 当driver为空时，抛出不支持的浏览器异常
        if (driver == null) {
            log.error("不支持的浏览器");
            throw new UnsupportedBrowser("不支持的浏览器");
        }

        // 打开指定的网站URL
        log.debug("打开" + url + "成功");
        driver.get(url);

        // 浏览器最大化
        log.debug("浏览器最大化");
        driver.manage().window().maximize();

        return driver;
    }

    /**
     * 指定浏览器，那么使用指定的浏览器打开网页
     *
     * @throws UnsupportedBrowser
     */
    public static WebDriver openUrl(String url, String browserName) throws UnsupportedBrowser {

        // 根据默认浏览器创建浏览器驱动对象
        log.debug("创建浏览器驱动对象，打开了" + browserName + "浏览器");
        WebDriver driver = createDriver(browserName);

        // 当driver为空时，抛出不支持的浏览器异常
        if (driver == null) {
            log.error("不支持的浏览器");
            throw new UnsupportedBrowser("不支持的浏览器");
        }

        // 打开指定的网站URL
        log.debug("打开" + url + "成功");
        driver.get(url);

        // 浏览器最大化
        log.debug("浏览器最大化");
        driver.manage().window().maximize();

        return driver;
    }

    /**
     * 根据传入的浏览器名称，创建驱动对象，并且返回驱动
     *
     * @param browserName
     *            浏览器名称，chrome（谷歌浏览器）、firefox（火狐）、ff（火狐）、ie（IE浏览器）
     */
    public static WebDriver createDriver(String browserName) {

        // 如果是谷歌浏览器，创建谷歌浏览器对象
        if ("chrome".equals(browserName)) {
            // 指定谷歌浏览器驱动程序存放的目录
            System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, chromeDriver);

            log.debug("谷歌浏览器驱动对象创建成功");
            // 返回谷歌浏览器驱动对象
            return new ChromeDriver();
        }

        // 如果是火狐浏览器，创建火狐浏览器对象
        if ("firefox".equals(browserName) || "ff".equals(browserName)) {
            // 火狐浏览器的安装路径
            System.setProperty(FirefoxDriver.SystemProperty.BROWSER_BINARY, firefoxBin);
            // 指定火狐浏览器驱动程序存放的目录
            System.setProperty(GeckoDriverService.GECKO_DRIVER_EXE_PROPERTY, geckoDriver);

            log.debug("火狐浏览器驱动对象创建成功");
            // 返回火狐浏览器驱动对象
            return new FirefoxDriver();
        }

        // 如果是IE浏览器，创建IE浏览器对象
        if ("ie".equals(browserName)) {
            // 指定IE浏览器驱动程序存放的目录
            System.setProperty(InternetExplorerDriverService.IE_DRIVER_EXE_PROPERTY, ieDriver);

            log.debug("IE浏览器驱动对象创建成功");

            // 返回IE浏览器驱动对象
            return new InternetExplorerDriver();
        }

        // 其他情况返回空对象
        return null;
    }
}
