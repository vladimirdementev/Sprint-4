package pages;

import model.Order;
import model.ScooterColorType;
import model.User;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Класс для работы со страницей оформления заказа
 */
public class OrderPage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(OrderPage.class);

    /**
     * Локатор поля ввода имени
     */
    private final By nameInput = By.cssSelector("input[placeholder='* Имя']");

    /**
     * Локатор поля ввода фамилии
     */
    private final By surnameInput = By.cssSelector("input[placeholder='* Фамилия']");

    /**
     * Локатор поля ввода адреса
     */
    private final By addressInput = By.cssSelector("input[placeholder='* Адрес: куда привезти заказ']");

    /**
     * Локатор поля выбора станции метро
     */
    private final By metroStationInput = By.cssSelector(".select-search__input");

    /**
     * Локатор поля ввода телефона
     */
    private final By phoneInput = By.cssSelector("input[placeholder='* Телефон: на него позвонит курьер']");

    /**
     * Локатор кнопки "Далее"
     */
    private final By nextButton = By.xpath(".//div[starts-with(@class,'Order_NextButton')]//button[contains(text(), 'Далее')]");

    /**
     * Локатор поля ввода даты доставки
     */
    private final By deliveryDateInput = By.cssSelector("input[placeholder='* Когда привезти самокат']");

    /**
     * Локатор выпадающего списка периода аренды
     */
    private final By rentalPeriodDropdown = By.cssSelector(".Dropdown-control");

    /**
     * Локатор чекбокса чёрного самоката
     */
    private final By blackScooterCheckbox = By.id("black");

    /**
     * Локатор чекбокса серого самоката
     */
    private final By greyScooterCheckbox = By.id("grey");

    /**
     * Локатор поля ввода комментария
     */
    private final By commentInput = By.cssSelector("input[placeholder='Комментарий для курьера']");

    /**
     * Локатор кнопки "Заказать"
     */
    private final By orderButton = By.xpath(".//div[starts-with(@class,'Order_Buttons')]//button[contains(text(), 'Заказать')]");

    /**
     * Локатор кнопки "Да" в модальном окне
     */
    private final By yesButton = By.xpath("//button[contains(text(), 'Да')]");

    /**
     * Локатор заголовка успешной формы
     */
    private final By successModalHeader = By.xpath("//div[text()= 'Заказ оформлен']");

    /**
     * Конструктор страницы заказа
     *
     * @param driver Экземпляр драйвера браузера
     */
    public OrderPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Заполняет основную форму заказа личными данными пользователя
     *
     * @param userData Объект с данными пользователя
     * @return Текущий экземпляр OrderPage для цепочечного вызова
     */
    public OrderPage fillOrderForm(User userData) {
        log.debug("Заполняю основную форму заказа");
        waitForVisibility(nameInput).sendKeys(userData.getName());
        waitForVisibility(surnameInput).sendKeys(userData.getSurname());
        waitForVisibility(addressInput).sendKeys(userData.getAddress());
        setMetroStation(userData.getMetroStation());
        waitForVisibility(phoneInput).sendKeys(userData.getPhone());
        return this;
    }

    /**
     * Устанавливает станцию метро в форме заказа
     *
     * @param metroStation Название станции метро
     */
    private void setMetroStation(String metroStation) {
        WebElement metroInput = waitForVisibility(metroStationInput);
        metroInput.sendKeys(metroStation);
        metroInput.sendKeys(Keys.DOWN);
        metroInput.sendKeys(Keys.ENTER);
    }

    /**
     * Нажимает кнопку "Далее" для перехода к следующему этапу заказа
     *
     * @return Текущий экземпляр OrderPage для цепочечного вызова
     */
    public OrderPage clickNextButton() {
        clickOnElement(nextButton, false);
        return this;
    }

    /**
     * Заполняет форму заказа дополнительной информацией
     *
     * @param orderData Объект с данными заказа
     * @return Текущий экземпляр OrderPage для цепочечного вызова
     */
    public OrderPage fillOrderForm2(Order orderData) {
        setDeliveryDate(orderData.getOrderDateAsString());
        selectRentalPeriod(orderData.getPeriod().getDescription());
        selectScooterColor(orderData.getScooterColor());
        setComment(orderData.getComment());
        return this;
    }

    /**
     * Устанавливает дату доставки в форме заказа
     *
     * @param date Дата доставки в формате, принятом системой
     */
    public void setDeliveryDate(String date) {
        log.debug("Устанавливаю дату доставки: {}", date);
        WebElement dateInput = waitForVisibility(deliveryDateInput);
        dateInput.clear();
        dateInput.sendKeys(date);
        dateInput.sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.attributeToBe(dateInput, "value", date));
    }

    /**
     * Выбирает период аренды из выпадающего списка
     *
     * @param period Описание периода аренды
     */
    public void selectRentalPeriod(String period) {
        clickOnElement(rentalPeriodDropdown, false);
        clickOnElement(By.xpath(".//div[@class='Dropdown-menu']/div[text()='"+period+"']"), false);
    }

    /**
     * Выбирает цвет самоката
     *
     * @param color Название цвета самоката
     * @throws IllegalArgumentException Если передан неверный цвет самоката
     */
    public void selectScooterColor(ScooterColorType color) {
        log.debug("Выбираю цвет самоката: {}", color);
        switch (color) {
            case BLACK:
                clickOnElement(blackScooterCheckbox, false);
                break;
            case GREY:
                clickOnElement(greyScooterCheckbox, false);
                break;
            default:
                throw new IllegalArgumentException("Неверный цвет самоката");
        }
    }

    /**
     * Устанавливает комментарий для курьера
     *
     * @param comment Текст комментария
     */
    public void setComment(String comment) {
        WebElement commentField = waitForVisibility(commentInput);
        commentField.clear();
        if (comment != null && !comment.isEmpty()) {
            commentField.sendKeys(comment);
        }
    }

    /**
     * Нажимает кнопку "Заказать"
     *
     * @return Текущий экземпляр OrderPage для цепочечного вызова
     */
    public OrderPage clickOrderButton() {
        clickOnElement(orderButton, false);
        return this;
    }

    /**
     * Нажимает кнопку "Да" в модальной форме подтверждения
     *
     * @return Текущий экземпляр OrderPage для цепочечного вызова
     */
    public OrderPage clickYesButton() {
        clickOnElement(yesButton, false);
        return this;
    }

    /**
     * Проверяет успешность оформления заказа
     *
     * @return true если заказ оформлен успешно, false - иначе
     */
    public boolean isOrderSuccess() {
        try {
            String successText = waitForVisibility(successModalHeader).getText().trim();
            log.debug("Текст успешной формы: {}", successText);
            return successText.contains("Заказ оформлен");
        } catch (TimeoutException | NoSuchElementException e) {
            log.warn("Форма успеха не найдена", e);
            return false;
        }
    }
}

