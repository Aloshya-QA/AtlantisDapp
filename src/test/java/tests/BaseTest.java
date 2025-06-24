package tests;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.SwapPage;
import pages.WalletPage;
import utils.AllureUtils;
import utils.PropertyReader;
import utils.TestListener;

import java.awt.*;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Collections;

import static utils.AllureUtils.takeScreenshot;

@Log4j2
@Listeners(TestListener.class)
public class BaseTest {

    WebDriver driver;
    SwapPage swapPage;
    WalletPage walletPage;

    private static final String
            EXTENSION_PATH = Paths.get("src/extensions/metamask").toAbsolutePath().toString();

    String
            SEED_PHRASE = System.getProperty("SEED_PHRASE", PropertyReader.getProperty("SEED_PHRASE")),
            PASSWORD = System.getProperty("PASSWORD", PropertyReader.getProperty("PASSWORD")),
            MON_TOKEN = "0x0000000000000000000000000000000000000000",
            WMON_TOKEN = "0x760afe86e5de5fa0ee542fc7b7b713e1c5425701";

    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    public void setup(@Optional("chrome") String browser) throws AWTException {
        log.info("Browser initialization");
        ChromeOptions options = getChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10L));
        driver.manage().window().setSize(new Dimension(1366, 768));
        swapPage = new SwapPage(driver);
        walletPage = new WalletPage(driver);
    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-extensions-except=" + EXTENSION_PATH);
        options.addArguments("--load-extension=" + EXTENSION_PATH);
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches",
                Collections.singletonList("enable-automation"));
        return options;
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            log.warn("Taking screenshot");
            takeScreenshot(driver);
            log.warn("Attach record");
            AllureUtils.attachScreenRecording();
        }
        if (driver != null) {
            log.info("Closing browser");
            driver.quit();
        }
    }
}
