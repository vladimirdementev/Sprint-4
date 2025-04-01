package pages;

import model.ButtonType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Главная страница приложения
 */
public class MainPage extends BasePage {
    private static final Logger log = LoggerFactory.getLogger(MainPage.class);
    /**
     * Контейнер со списком вопросов
     */
    private final By rootContainer = By.cssSelector("[data-accordion-component='Accordion']");

    /**
     * Вопрос в списке
     */
    private final By questionElements = By.cssSelector("[data-accordion-component='AccordionItemButton']");

    /**
     * Ответ на вопрос
     */
    private final By answerElements = By.cssSelector("[data-accordion-component='AccordionItemPanel']");

    /**
     * Кнопка заказа в верхней части страницы
     */
    private final By topOrderButton = By.xpath("//div[contains(@class, 'Header_Nav')]//button[normalize-space(text()) = 'Заказать']");

    /**
     * Кнопка заказа в нижней части страницы
     */
    private final By bottomOrderButton = By.xpath("//div[contains(@class, 'Home_FinishButton')]//button[contains(@class, 'Button_Button') and normalize-space(text()) = 'Заказать']");

    /**
     * Конструктор страницы
     *
     * @param driver экземпляр драйвера
     */
    public MainPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Переходит к вопросам и получает из них список
     *
     * @return список вопросов
     */
    public List<WebElement> getQuestions() {
        log.debug("Получаю список вопросов");
        WebElement container = waitForVisibility(rootContainer);
        scrollToElement(container);
        List<WebElement> questions = driver.findElements(questionElements);
        if (questions.isEmpty()) {
            log.warn("Вопросы не найдены");
        }
        return questions;
    }

    /**
     * Получает список всех ответов
     *
     * @return список ответов
     */
    public List<WebElement> getAnswers() {
        waitForVisibility(rootContainer);
        List<WebElement> answers = driver.findElements(answerElements);
        if (answers.isEmpty()) {
            log.warn("Ответы не найдены");
        }
        return answers;
    }

    /**
     * Открывает указанный вопрос
     *
     * @param questionLocator локатор вопроса
     */
    public void openQuestion(By questionLocator) {
        clickOnElement(questionLocator, true);
    }

    /**
     * Открывает указанный вопрос
     *
     * @param webElement элемент вопроса
     */
    public void openQuestion(WebElement webElement) {
        if (webElement == null) {
            throw new IllegalArgumentException("Элемент вопроса не должен быть null");
        }
        log.debug("Открываю вопрос: {}", webElement.getText());
        clickOnElement(webElement, true);
        waitLoadAfterClick(webElement);
    }

    /**
     * Клик на кнопку заказа в верхней части страницы
     *
     * @return страницу оформления заказа
     */
    private OrderPage clickOrderButtonTop() {
        clickOnElement(topOrderButton, false);
        return new OrderPage(driver);
    }

    /**
     * Клик на кнопку заказа в нижней части страницы
     *
     * @return страницу оформления заказа
     */
    private OrderPage clickOrderButtonBottom() {
        clickOnElement(bottomOrderButton, false);
        return new OrderPage(driver);
    }


    public OrderPage clickOrderButton(ButtonType buttonType) {
        log.debug("Кликаю на кнопку заказа: {}", buttonType);
        switch (buttonType) {
            case TOP:
                clickOnElement(topOrderButton, false);
                break;
            case BOTTOM:
                clickOnElement(bottomOrderButton, false);
                break;
            default:
                throw new IllegalArgumentException("Неверный тип кнопки");
        }
        return new OrderPage(driver);
    }
}
