package tests;

import config.AppConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.MainPage;

import java.time.Duration;

public abstract class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Logger logger;
    protected String testCaseName;
    protected MainPage mainPage;

    public BaseTest(String testCaseName) {
        logger = LoggerFactory.getLogger(getClass());
        this.testCaseName = testCaseName;
    }

    @Before
    public void setUp() {
        try {
            logger.info("Начинается настройка теста");
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            driver.manage().window().maximize();
            driver.get(AppConfig.URL);
            mainPage = new MainPage(driver);
            mainPage.acceptCookie();
            mainPage.waitForCookieBannerDisappearance();
        } catch (Exception e) {
            logger.error("Ошибка при настройке теста", e);
            throw e;
        }
    }

    @After
    public void tearDown() throws InterruptedException {
        logger.info("Завершение теста");
        if (driver != null) {
            driver.quit();
        }
        Thread.sleep(500);
    }
}
