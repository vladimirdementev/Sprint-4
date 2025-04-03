package pages;

import config.AppConfig;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * Базовый класс для всех страниц приложения.
 * Содержит общие методы для работы с элементами страницы.
 */
public class BasePage {
    private static final Logger log = LoggerFactory.getLogger(BasePage.class);

    /**
     * Локатор секции с куками
     */
    private final By cookieSection = By.xpath("//div[contains(@class, 'App_CookieConsent')]");

    /**
     * Локатор кнопки принятия куков
     */
    private final By cookieButton = By.xpath("//button[contains(@class, 'App_CookieButton')]");

    /**
     * Драйвер браузера
     */
    protected WebDriver driver;

    /**
     * Ожидание для элементов
     */
    protected final WebDriverWait wait;

    /**
     * Конструктор базового класса
     *
     * @param driver экземпляр драйвера
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(AppConfig.DEFAULT_TIMEOUT));
    }

    /**
     * Ожидает пока элемент станет видимым
     *
     * @param locator локатор элемента
     * @return видимый элемент
     */
    public WebElement waitForVisibility(By locator) {
        try {
            log.debug("Ожидаю видимость элемента: {}", locator);
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            log.error("Элемент не стал видимым: {}", locator, e);
            throw new RuntimeException("Элемент не стал видимым в течение заданного времени", e);
        }
    }

    /**
     * Ожидает пока элемент станет кликабельным
     *
     * @param locator локатор элемента
     */
    public void waitForClickability(By locator) {
        try {
            log.debug("Ожидаю пока по элементу можно будет кликнуть: {}", locator);
            wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (TimeoutException e) {
            log.error("Элемент не стал кликабельным в течение заданного времени", e);
            throw new RuntimeException("Элемент не стал кликабельным в течение заданного времени", e);
        }
    }

    /**
     * Ожидает появления элемента в DOM
     *
     * @param locator локатор элемента
     */
    public void waitForPresence(By locator) {
        try {
            log.debug("Ожидаю появления в дереве DOM элемента: {}", locator);
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (TimeoutException e) {
            log.debug("Элемент не появился в DOM в течение заданного времени", e);
            throw new RuntimeException("Элемент не появился в DOM в течение заданного времени", e);
        }
    }

    /**
     * Прокручивает страницу до элемента
     *
     * @param element элемент для прокрутки
     */
    protected void scrollToElement(WebElement element) {
        if (element == null) {
            log.error("При прокрутке элемент не может быть null");
            throw new IllegalArgumentException("Элемент не должен быть null");
        }
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", element);
    }

    /**
     * Кликает по элементу с возможностью предварительной прокрутки
     *
     * @param locator локатор элемента
     * @param scrollable флаг прокрутки
     */
    public void clickOnElement(By locator, boolean scrollable) {
        waitForPresence(locator);
        waitForVisibility(locator);
        waitForClickability(locator);
        WebElement element = waitForVisibility(locator);
        clickOnElement(element, scrollable);
    }

    /**
     * Кликает по элементу с возможностью предварительной прокрутки
     *
     * @param webElement элемента
     * @param scrollable флаг прокрутки
     */
    public void clickOnElement(WebElement webElement, boolean scrollable) {
        log.debug("Попытка кликнуть по элементу: {}", webElement);
        if (scrollable) {
            scrollToElement(webElement);
        }
        webElement.click();
    }

    /**
     * Ожидает загрузки контента после клика
     *
     * @param webElement элемент
     */
    public void waitLoadAfterClick(WebElement webElement) {
        log.debug("Ожидание загрузки контента элемента: {}", webElement);

        // Ждём, пока текст появится
        wait.until(ExpectedConditions.attributeToBeNotEmpty(webElement, "textContent"));

        // Дополнительно проверяем, что текст не пустой
        wait.until(ExpectedConditions.and(
                ExpectedConditions.visibilityOf(webElement),
                driver -> !webElement.getText().trim().isEmpty()
        ));
    }

    /**
     * Ожидает исчезновения баннера с куками
     */
    public void waitForCookieBannerDisappearance() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(cookieSection));
    }

    /**
     * Принимает куки
     */
    public void acceptCookie() {
        if (waitForVisibility(cookieSection).isDisplayed()) {
            log.info("Найден баннер с куками");
            clickOnElement(cookieButton, false);
            log.info("Куки приняты");
        }
    }

    /**
     * Проверяет наличие элемента на странице
     *
     * @param by Объект By для поиска элемента
     * @return true, если элемент найден и отображается, иначе false
     */
    public boolean isElementPresent(By by) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return element != null && element.isDisplayed();
        } catch (TimeoutException e) {
            log.error("Элемент по локатору {} не отобразился", by.toString());
            return false;
        }
    }


}