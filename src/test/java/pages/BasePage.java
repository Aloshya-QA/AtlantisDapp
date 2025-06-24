package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.ArrayList;

public abstract class BasePage {

    WebDriver driver;
    WebDriverWait wait;
    Robot robot;

    public static final String BASE_URL = "https://app.atlantisdex.xyz/swap/v4/";

    public BasePage(WebDriver driver) throws AWTException {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        robot = new Robot();
    }

    public abstract BasePage open();

    public abstract BasePage isOpened();
}
