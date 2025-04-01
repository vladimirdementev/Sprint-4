package tests;

import config.AppConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.MainPage;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class QuestionAnswerTest {
    private static final Logger logger = LoggerFactory.getLogger(QuestionAnswerTest.class);

    private WebDriver driver;
    private WebDriverWait wait;
    private MainPage mainPage;

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
        logger.info("Тест завершен");
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testAllAccordionQuestionsAndAnswers() {
        logger.info("Начинается проверка вопросов и ответов");

        List<WebElement> questions = mainPage.getQuestions();
        List<WebElement> answers = mainPage.getAnswers();
        assertEquals(8, questions.size());

        // Массив ожидаемых текстов вопросов
        String[] expectedQuestions = {
                "Сколько это стоит? И как оплатить?",
                "Хочу сразу несколько самокатов! Так можно?",
                "Как рассчитывается время аренды?",
                "Можно ли заказать самокат прямо на сегодня?",
                "Можно ли продлить заказ или вернуть самокат раньше?",
                "Вы привозите зарядку вместе с самокатом?",
                "Можно ли отменить заказ?",
                "Я жизу за МКАДом, привезёте?"
        };

        // Массив ожидаемых текстов ответов
        String[] expectedAnswers = {
                "Сутки — 400 рублей. Оплата курьеру — наличными или картой.",
                "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим.",
                "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30.",
                "Только начиная с завтрашнего дня. Но скоро станем расторопнее.",
                "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.",
                "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится.",
                "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои.",
                "Да, обязательно. Всем самокатов! И Москве, и Московской области."
        };

        for (int i = 0; i < questions.size(); i++) {
            WebElement question = questions.get(i);

            // Проверяем текст вопроса
            assertEquals(expectedQuestions[i], question.getText());

            // Открываем вопрос
            mainPage.openQuestion(question);
            // Получаем соответствующий ответ
            WebElement answer = answers.get(i);

            // Проверяем, что ответ отображается
            assertTrue(answer.isDisplayed());

            // Проверяем текст ответа
            assertEquals(expectedAnswers[i], answer.getText());
        }
    }
}
