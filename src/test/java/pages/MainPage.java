package pages;

import models.ButtonPositionType;
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

    public List<WebElement> getAllQuestions() {
        WebElement container = waitForVisibility(rootContainer);
        scrollToElement(container);
        return driver.findElements(questionElements);
    }

    /**
     * Переходит к вопросам и получает из них список
     *
     * @return список вопросов
     */
    public WebElement getQuestion(String text) {
        log.debug("Получаю вопрос");
        WebElement container = waitForVisibility(rootContainer);
        scrollToElement(container);
        List<WebElement> questions = driver.findElements(questionElements);
        return questions.stream().filter(element -> element.getText().contains(text)).findFirst().orElse(null);
    }

    /**
     * Получает список всех ответов
     *
     * @return список ответов
     */
    public WebElement getAnswer(String text) {
        log.debug("Получаю ответ");
        WebElement container = waitForVisibility(rootContainer);
        scrollToElement(container);
        List<WebElement> answers = driver.findElements(answerElements);
        return answers.stream().filter(element -> element.getText().contains(text)).findFirst().orElse(null);
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

    public OrderPage clickOrderButton(ButtonPositionType buttonPositionType) {
        log.debug("Кликаю на кнопку заказа: {}", buttonPositionType);
        switch (buttonPositionType) {
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

    public Boolean isOrderButtonPresent(ButtonPositionType buttonPositionType) {
        log.debug("Проверяю отображение кнопки заказа: {}", buttonPositionType);
        By buttonLocator = null;

        switch (buttonPositionType) {
            case TOP:
                buttonLocator = topOrderButton;
                break;
            case BOTTOM:
                buttonLocator = bottomOrderButton;
                break;
        }

        return isElementPresent(buttonLocator);

    }
}
