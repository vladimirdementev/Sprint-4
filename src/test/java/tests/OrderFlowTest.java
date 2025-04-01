package tests;

import config.AppConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pages.MainPage;
import pages.OrderPage;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.time.Duration;

@RunWith(Parameterized.class)
public class OrderFlowTest {
    private static final Logger logger = LoggerFactory.getLogger(OrderFlowTest.class);

    private WebDriver driver;
    private WebDriverWait wait;
    private MainPage mainPage;
    private final User user;
    private final Order order;
    private final ButtonType buttonType;
    private final String testCaseName;

    public OrderFlowTest(String testCaseName, User user, Order order, ButtonType buttonType) {
        this.testCaseName = testCaseName;
        this.user = user;
        this.order = order;
        this.buttonType = buttonType;
    }

    @Parameters(name = "{0}")
    public static Collection<Object[]> testData() {
        User user1 = new User.Builder()
                .setName("Иван")
                .setSurname("Петров")
                .setAddress("г. Москва, ул. Примерная, д. 1")
                .setMetroStation("Щукинская")
                .setPhone("+79001234567")
                .build();

        Order order1 = new Order(user1, LocalDateTime.now().plusDays(2).withHour(12).withMinute(0), PeriodType.ONE_DAY, ScooterColorType.BLACK, "");

        User user2 = new User.Builder()
                .setName("Мария")
                .setSurname("Иванова")
                .setAddress("г. Санкт-Петербург, пр. Ветеранов, д. 5")
                .setMetroStation("Проспект Ветеранов")
                .setPhone("+79007654321")
                .build();

        Order order2 = new Order(user1, LocalDateTime.now().minusDays(2).withHour(15).withMinute(35), PeriodType.FIVE_DAYS, ScooterColorType.GREY, "Позвонить за 15 минут");

        return Arrays.asList(new Object[][]{
                {"Успешный заказ через верхнюю кнопку", user1, order1, ButtonType.TOP},
                {"Успешный заказ через нижнюю кнопку", user2, order2, ButtonType.BOTTOM}
        });
    }

    @Before
    public void setUp() {
        try {
            logger.info("Начинается настройка теста");
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            //driver = new FirefoxDriver();
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
    public void tearDown() {
        logger.info("Тест завершен: {}", testCaseName);
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testOrderFlow() {
        logger.info("Тест начал выполняться: {}", testCaseName);
        try {
            logger.debug("Кликаем на кнопку заказа");
            OrderPage orderPage = mainPage.clickOrderButton(buttonType);

            logger.debug("Заполняем форму личными данными");
            orderPage.fillOrderForm(user);

            logger.debug("Переходим к следующему шагу");
            orderPage.clickNextButton();

            logger.debug("Заполняем данные заказа");
            orderPage.fillOrderForm2(order);

            logger.debug("Подтверждаем заказ");
            orderPage.clickOrderButton();

            logger.debug("Подтверждаем в модальном окне");
            orderPage.clickYesButton();

        } catch (Exception e) {
            logger.error("Ошибка при выполнении теста: {}", testCaseName, e);
            throw e;
        }
    }
}
