package tests;

import models.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import pages.OrderPage;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderFlowTest extends BaseTest {
    private final User user;
    private final Order order;
    private final ButtonPositionType buttonPositionType;

    public OrderFlowTest(String testCaseName, User user, Order order, ButtonPositionType buttonPositionType) {
        super(testCaseName);
        this.user = user;
        this.order = order;
        this.buttonPositionType = buttonPositionType;
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
                {"Успешный заказ через верхнюю кнопку", user1, order1, ButtonPositionType.TOP},
                {"Успешный заказ через нижнюю кнопку", user2, order2, ButtonPositionType.BOTTOM}
        });
    }

    @Test
    public void testOrderFlow() {
        logger.info("Тест начал выполняться: {}", testCaseName);

        try {
            assertTrue("Кнопка заказа не найдена", mainPage.isOrderButtonPresent(buttonPositionType));

            logger.debug("Кликаем на кнопку заказа");
            OrderPage orderPage = mainPage.clickOrderButton(buttonPositionType);

            assertTrue("Форма заказа не открылась", orderPage.isOrderFormPresent());

            logger.debug("Заполняем форму личными данными");
            orderPage.fillOrderForm(user);

            logger.debug("Переходим к следующему шагу");
            orderPage.clickNextButton();

            assertTrue("Форма второго шага не загрузилась", orderPage.isSecondStepFormPresent());

            logger.debug("Заполняем данные заказа");
            orderPage.fillOrderForm2(order);

            logger.debug("Подтверждаем заказ");
            orderPage.clickOrderButton();

            assertTrue("Модальное окно подтверждения не появилось", orderPage.isModalFormPresent());

            logger.debug("Подтверждаем в модальном окне");
            orderPage.clickYesButton();

            assertTrue("Сообщение об успешном заказе не отобразилось", orderPage.isSuccessMessagePresent());

        } catch (Exception e) {
            logger.error("Ошибка при выполнении теста: {}", testCaseName, e);
            throw e;
        }
    }
}
