package tests;

import org.testng.annotations.Test;
import java.awt.*;

public class SwapTest extends BaseTest {

    @Test(testName = "Проверка логина с неверным юзером", groups = {"RunSwap"})
    public void checkSwap() throws AWTException, InterruptedException {
        swapPage.open()
                .isOpened()
                .closeAnotherTabs();
        walletPage.open()
                .isOpened()
                .clickCheckbox()
                .clickImport()
                .clickAccept()
                .inputSeedPhrase(SEED_PHRASE)
                .setPassword(PASSWORD)
                .clickDone();
        swapPage.open()
                .isOpened()
                .connectWallet()
                .switchToMetaMask()
                .switchPopupOpened()
                .switchNetwork()
                .acceptSwitchNetwork()
                .switchNetworkCompleted()
                .swapTokens(MON_TOKEN, WMON_TOKEN, "0.1", 21);

    }

    @Test
    public void checkWebDriverPrivacy() {
        driver.get("https://intoli.com/blog/not-possible-to-block-chrome-headless/chrome-headless-test.html");
    }
}
