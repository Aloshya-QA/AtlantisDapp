package pages;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.awt.*;
import java.awt.event.KeyEvent;

@Log4j2
public class SwapPage extends BasePage {

    private static final String
            CONNECT_BUTTON = "//button[text()=\"Connect\"]",
            METAMASK_BUTTON = "//div[text()='MetaMask']/ancestor::button",
            BUTTON = "//div[text()='Получить кошелек']/ancestor::button",
            SWITCH_NETWORK_BUTTON = "//button[text()='Switch Network']",
            BALANCE_SELL_TOKEN = "//div[text()='Sell:']/parent::div/parent::div//span[2]",
            SELL_TOKEN = "//div[text()='Sell:']/parent::div/parent::div//button[not(text()='Max')]",
            BUY_TOKEN = "//div[text()='Buy:']/parent::div/parent::div//button",
            MODAL_PLACEHOLDER = "//input[@placeholder='Search name or paste address']",
            SELECT_TOKEN = "//div[@class='text-sm']/ancestor::button",
            SELL_INPUT = "//div[text()='Sell:']/parent::div//input",
            SWAP_BUTTON = "//button[text()='Swap']";


    public SwapPage(WebDriver driver) throws AWTException {
        super(driver);
    }

    @Override
    public SwapPage open() {
        log.info("Open SwapPage");
        driver.get(BASE_URL);
        return this;
    }

    @Override
    public SwapPage isOpened() {
        try {
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(CONNECT_BUTTON))));
            log.info("SwapPage is opened");
        } catch (TimeoutException e) {
            log.error(e.getMessage());
            Assert.fail("SwapPage isn't opened");
        }

        return this;
    }

    public SwapPage closeAnotherTabs() {
        log.info("Closing another tabs");

        robot.delay(3000);

        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.delay(50);
        robot.keyPress(KeyEvent.VK_W);
        robot.delay(50);
        robot.keyRelease(KeyEvent.VK_W);
        robot.delay(50);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.waitForIdle();

        return this;
    }

    public WalletPage connectWallet() throws AWTException, InterruptedException {
        log.info("Connect wallet");
        driver.findElement(By.xpath(CONNECT_BUTTON)).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(BUTTON))));
        driver.findElement(By.xpath(METAMASK_BUTTON)).click();
        Thread.sleep(10000);
        return new WalletPage(driver);
    }

    public WalletPage switchNetwork() throws AWTException, InterruptedException {
        log.info("Switch wallet network");
        driver.findElement(By.xpath(SWITCH_NETWORK_BUTTON)).click();
        Thread.sleep(2000);
        return new WalletPage(driver);
    }

    public SwapPage switchPopupOpened() {
        try {
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(SWITCH_NETWORK_BUTTON))));
            log.info("Switch popup is opened");
        } catch (TimeoutException e) {
            log.error(e.getMessage());
            Assert.fail("Switch popup isn't opened");
        }
        return this;
    }

    public SwapPage switchNetworkCompleted() {
        try {
            wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath(SWITCH_NETWORK_BUTTON))));
            log.info("Network switched successfully");
        } catch (TimeoutException e) {
            log.error(e.getMessage());
            Assert.fail("The network is not switched");
        }
        return this;
    }

    public double getTokenBalance(String token) {
        log.info("Getting the token balance");
        driver.findElement(By.xpath(SELL_TOKEN)).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(MODAL_PLACEHOLDER))));
        driver.findElement(By.xpath(MODAL_PLACEHOLDER)).sendKeys(token);
        driver.findElement(By.xpath(SELECT_TOKEN)).click();

        return Double.parseDouble(driver.findElement(By.xpath(BALANCE_SELL_TOKEN)).getText());
    }

    public SwapPage swapTokens(String sellToken, String buyToken, String amount, int totalTransactions) throws InterruptedException {
        log.info("Start swap tokens: ");
        if (getTokenBalance(sellToken) < 3) {
            log.info("Switch to WMON token");
            if (getTokenBalance(buyToken) > 3) {
                log.info("Swapping WMON tokens...");
                driver.findElement(By.xpath(SELL_TOKEN)).click();
                wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(MODAL_PLACEHOLDER))));
                driver.findElement(By.xpath(MODAL_PLACEHOLDER)).sendKeys(buyToken);
                driver.findElement(By.xpath(SELECT_TOKEN)).click();

                driver.findElement(By.xpath(BUY_TOKEN)).click();
                wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(MODAL_PLACEHOLDER))));
                driver.findElement(By.xpath(MODAL_PLACEHOLDER)).sendKeys(sellToken);
                driver.findElement(By.xpath(SELECT_TOKEN)).click();

                while (totalTransactions > 0) {
                    log.info("Transaction #{}", totalTransactions);
                    driver.findElement(By.xpath(SELL_INPUT)).sendKeys(amount);
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(SWAP_BUTTON)));
                    driver.findElement(By.xpath(SWAP_BUTTON)).click();

                    Thread.sleep(6000);

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

                    wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.xpath(SELL_INPUT)), "value", ""));

                    totalTransactions--;
                }


            } else {
                log.warn("Not enough tokens");
                Assert.fail("Not enough tokens");
            }
        } else {
            log.info("Swapping MON tokens...");
            driver.findElement(By.xpath(SELL_TOKEN)).click();
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(MODAL_PLACEHOLDER))));
            driver.findElement(By.xpath(MODAL_PLACEHOLDER)).sendKeys(sellToken);
            driver.findElement(By.xpath(SELECT_TOKEN)).click();

            driver.findElement(By.xpath(BUY_TOKEN)).click();
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(MODAL_PLACEHOLDER))));
            driver.findElement(By.xpath(MODAL_PLACEHOLDER)).sendKeys(buyToken);
            driver.findElement(By.xpath(SELECT_TOKEN)).click();

            while (totalTransactions > 0) {
                log.info("Transaction #{}", totalTransactions);
                driver.findElement(By.xpath(SELL_INPUT)).sendKeys(amount);
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(SWAP_BUTTON)));
                driver.findElement(By.xpath(SWAP_BUTTON)).click();

                Thread.sleep(5000);

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

                wait.until(ExpectedConditions.attributeToBe(driver.findElement(By.xpath(SELL_INPUT)), "value", ""));

                totalTransactions--;
            }
        }

        return this;
    }
}
