package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.KeyEvent;

public class WalletPage extends BasePage {

    private static final String
            CHECKBOX = "//input[@type='checkbox']",
            IMPORT_BUTTON = "//button[@data-testid='onboarding-import-wallet']",
            ACCEPT_BUTTON = "//button[@data-testid='metametrics-i-agree']",
            NEW_PASSWORD_INPUT = "//input[@data-testid='create-password-new']",
            CONFIRM_PASSWORD_INPUT = "//input[@data-testid='create-password-confirm']",
            CONFIRM_IMPORT_BUTTON = "//button[@data-testid='create-password-import']",
            DONE_ONBOARDING_BUTTON = "//button[@data-testid='onboarding-complete-done']",
            LOGO_BUTTON = "//button[@data-testid='app-header-logo']",
            INPUT_FIELDS = "//div[@class='import-srp__srp']/descendant::input[@type='password']",
            SEED_ACCEPT_BUTTON = "//button[@data-testid='import-srp-confirm']",
            UNLOCK_INPUT = "//input[@data-testid='unlock-password']",
            UNLOCK_BUTTON = "//button[@data-testid='unlock-submit']",
            NEXT_BUTTON = "//button[@data-testid='pin-extension-next']",
            DONE_BUTTON = "//button[@data-testid='pin-extension-done']",
            NOT_NOW_BUTTON = "//button[@data-testid='not-now-button']",
            CONFIRM_BUTTON = "//button[@data-testid='confirm-btn']",
            SWITCH_NETWORK_BUTTON = "//button[text()='Switch Network']";

    public WalletPage(WebDriver driver) throws AWTException {
        super(driver);
    }

    @Override
    public WalletPage open() {
        driver.get("chrome-extension://kafmflljhfcafmhdjfjhglfehfoafkpk/home.html");
        return this;
    }

    @Override
    public WalletPage isOpened() {
        wait.until(ExpectedConditions.visibilityOf(
                driver.findElement(By.xpath(IMPORT_BUTTON))));
        return this;
    }

    public WalletPage clickCheckbox() {
        driver.findElement(By.xpath(CHECKBOX)).click();
        return this;
    }

    public WalletPage clickImport() {
        driver.findElement(By.xpath(IMPORT_BUTTON)).click();
        return this;
    }

    public WalletPage clickAccept() {
        driver.findElement(By.xpath(ACCEPT_BUTTON)).click();
        return this;
    }

    public WalletPage inputSeedPhrase(String seed) {
        String[] words = seed.split(" ");
        ArrayList<WebElement> inputs = new ArrayList<>(driver.findElements(By.xpath(INPUT_FIELDS)));
        for (int i = 0; i < inputs.size(); i++) {
            inputs.get(i).sendKeys(words[i]);
        }
        driver.findElement(By.xpath(SEED_ACCEPT_BUTTON)).click();
        return this;
    }

    public WalletPage setPassword(String password) {
        driver.findElement(By.xpath(NEW_PASSWORD_INPUT)).sendKeys(password);
        driver.findElement(By.xpath(CONFIRM_PASSWORD_INPUT)).sendKeys(password);
        driver.findElement(By.xpath(CHECKBOX)).click();
        driver.findElement(By.xpath(CONFIRM_IMPORT_BUTTON)).click();
        return this;
    }

    public WalletPage clickDone() {
        driver.findElement(By.xpath(DONE_ONBOARDING_BUTTON)).click();
        driver.findElement(By.xpath(NEXT_BUTTON)).click();
        driver.findElement(By.xpath(DONE_BUTTON)).click();
        driver.findElement(By.xpath(NOT_NOW_BUTTON)).click();
        return this;
    }

    public SwapPage switchToMetaMask() throws AWTException, InterruptedException {
        Thread.sleep(2000);

        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.delay(50);
        robot.keyPress(KeyEvent.VK_TAB);
        robot.delay(50);

        robot.keyRelease(KeyEvent.VK_TAB);
        robot.delay(50);
        robot.keyRelease(KeyEvent.VK_SHIFT);

        robot.delay(100);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(50);
        robot.keyRelease(KeyEvent.VK_ENTER);

        robot.waitForIdle();



        return new SwapPage(driver);
    }

    public SwapPage acceptSwitchNetwork() throws AWTException, InterruptedException {
        Thread.sleep(2000);

        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.delay(50);
        robot.keyPress(KeyEvent.VK_TAB);
        robot.delay(50);
        robot.keyRelease(KeyEvent.VK_TAB);
        robot.delay(50);
        robot.keyRelease(KeyEvent.VK_SHIFT);
        robot.delay(50);

        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(50);
        robot.keyRelease(KeyEvent.VK_ENTER);

        robot.waitForIdle();


        return new SwapPage(driver);
    }
}
