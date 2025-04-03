package tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.assertj.core.api.SoftAssertions;

@RunWith(Parameterized.class)
public class QuestionAnswerTest extends BaseTest {

    public QuestionAnswerTest(String testCaseName, String expectedQuestion, String expectedAnswer) {
        super(testCaseName);
        this.expectedQuestion = expectedQuestion;
        this.expectedAnswer = expectedAnswer;
    }

    private final String expectedQuestion;
    private final String expectedAnswer;

    @Parameters(name = "{0}")
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
                {"Тест первой пары вопрос-ответ",
                        "Сколько это стоит? И как оплатить?",
                        "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},

                {"Тест второй пары вопрос-ответ",
                        "Хочу сразу несколько самокатов! Так можно?",
                        "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},

                {"Тест третьей пары вопрос-ответ",
                        "Как рассчитывается время аренды?",
                        "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},

                {"Тест четвёртой пары вопрос-ответ",
                        "Можно ли заказать самокат прямо на сегодня?",
                        "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},

                {"Тест пятой пары вопрос-ответ",
                        "Можно ли продлить заказ или вернуть самокат раньше?",
                        "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},

                {"Тест шестой пары вопрос-ответ",
                        "Вы привозите зарядку вместе с самокатом?",
                        "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},

                {"Тест седьмой пары вопрос-ответ",
                        "Можно ли отменить заказ?",
                        "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},

                {"Тест восьмой пары вопрос-ответ",
                        "Я жизу за МКАДом, привезёте?",
                        "Да, обязательно. Всем самокатов! И Москве, и Московской области."}
        });
    }

    @Test
    public void testQuestionAnswerText() {
        SoftAssertions softly = new SoftAssertions();

        logger.info("Проверка отображения всех вопросов");
        List<WebElement> allQuestions = mainPage.getAllQuestions();
        softly.assertThat(allQuestions.size()).as("Проверка количества вопросов").isGreaterThan(0);

        logger.info("Проверка текста вопроса");
        WebElement question = mainPage.getQuestion(expectedQuestion);
        softly.assertThat(question.isDisplayed()).as("Проверка отображения вопроса").isTrue();
        softly.assertThat(question.getText()).as("Проверка текста вопроса").isEqualTo(expectedQuestion);

        logger.info("Открываю вопрос");
        mainPage.openQuestion(question);

        logger.info("Проверка отображения ответа");
        WebElement answer = mainPage.getAnswer(expectedAnswer);
        softly.assertThat(answer.isDisplayed()).as("Проверка отображения ответа").isTrue();
        softly.assertThat(answer.getText()).as("Проверка текста ответа").isEqualTo(expectedAnswer);

        softly.assertAll();
    }
}
