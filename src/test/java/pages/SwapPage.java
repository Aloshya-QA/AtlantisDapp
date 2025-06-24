package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.awt.*;
import java.awt.event.KeyEvent;

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
        driver.get(BASE_URL);
        return this;
    }

    @Override
    public SwapPage isOpened() {
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(CONNECT_BUTTON))));
        return this;
    }

    public SwapPage closeAnotherTabs() {
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
        driver.findElement(By.xpath(CONNECT_BUTTON)).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(BUTTON))));
        driver.findElement(By.xpath(METAMASK_BUTTON)).click();
        Thread.sleep(2000);
        return new WalletPage(driver);
    }

    public WalletPage switchNetwork() throws AWTException, InterruptedException {
        driver.findElement(By.xpath(SWITCH_NETWORK_BUTTON)).click();
        Thread.sleep(2000);
        return new WalletPage(driver);
    }

    public SwapPage switchPopupOpened() {
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(SWITCH_NETWORK_BUTTON))));
        return this;
    }

    public SwapPage switchNetworkCompleted() {
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath(SWITCH_NETWORK_BUTTON))));
        return this;
    }

    public double getTokenBalance(String token) {
        driver.findElement(By.xpath(SELL_TOKEN)).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(MODAL_PLACEHOLDER))));
        driver.findElement(By.xpath(MODAL_PLACEHOLDER)).sendKeys(token);
        driver.findElement(By.xpath(SELECT_TOKEN)).click();

        return Double.parseDouble(driver.findElement(By.xpath(BALANCE_SELL_TOKEN)).getText());
    }

    public SwapPage swapTokens(String sellToken, String buyToken, String amount, int totalTransactions) throws InterruptedException {
        if (getTokenBalance(sellToken) < 3) {
            if (getTokenBalance(buyToken) > 3) {
                driver.findElement(By.xpath(SELL_TOKEN)).click();
                wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(MODAL_PLACEHOLDER))));
                driver.findElement(By.xpath(MODAL_PLACEHOLDER)).sendKeys(buyToken);
                driver.findElement(By.xpath(SELECT_TOKEN)).click();

                driver.findElement(By.xpath(BUY_TOKEN)).click();
                wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(MODAL_PLACEHOLDER))));
                driver.findElement(By.xpath(MODAL_PLACEHOLDER)).sendKeys(sellToken);
                driver.findElement(By.xpath(SELECT_TOKEN)).click();

                while (totalTransactions > 0) {
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

                    totalTransactions --;
                }


            } else {
                Assert.fail("Not enough tokens");
            }
        } else {
            driver.findElement(By.xpath(SELL_TOKEN)).click();
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(MODAL_PLACEHOLDER))));
            driver.findElement(By.xpath(MODAL_PLACEHOLDER)).sendKeys(sellToken);
            driver.findElement(By.xpath(SELECT_TOKEN)).click();

            driver.findElement(By.xpath(BUY_TOKEN)).click();
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(MODAL_PLACEHOLDER))));
            driver.findElement(By.xpath(MODAL_PLACEHOLDER)).sendKeys(buyToken);
            driver.findElement(By.xpath(SELECT_TOKEN)).click();

            while (totalTransactions > 0) {
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
