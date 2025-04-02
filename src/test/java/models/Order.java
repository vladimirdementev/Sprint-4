package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Класс представляет собой модель заказа.
 * Содержит информацию о пользователе, дате заказа, периоде аренды и цвете самоката.
 */
public class Order {
    private final User user;
    private final LocalDateTime orderDate;
    private final PeriodType period;
    private final ScooterColorType scooterColor;
    private final String comment;

    /**
     * Конструктор для создания объекта заказа
     *
     * @param user         объект пользователя
     * @param orderDate    дата и время заказа
     * @param period       тип периода аренды
     * @param scooterColor цвет самоката
     * @param comment      комментарий к заказу
     */
    public Order(User user, LocalDateTime orderDate, PeriodType period, ScooterColorType scooterColor, String comment) {
        this.user = user;
        this.orderDate = orderDate;
        this.period = period;
        this.scooterColor = scooterColor;
        this.comment = comment;
    }

    /**
     * Возвращает пользователя, сделавшего заказ
     *
     * @return объект пользователя
     */
    public User getUser() {
        return user;
    }

    /**
     * Возвращает дату и время заказа
     *
     * @return объект LocalDateTime с датой заказа
     */
    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public String getOrderDateAsString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return orderDate.format(formatter);
    }

    /**
     * Возвращает период аренды
     *
     * @return тип периода аренды
     */
    public PeriodType getPeriod() {
        return period;
    }

    /**
     * Возвращает цвет самоката
     *
     * @return цвет самоката
     */
    public ScooterColorType getScooterColor() {
        return scooterColor;
    }

    /**
     * Возвращает комментарий к заказу
     *
     * @return комментарий
     */
    public String getComment() {
        return comment;
    }
}