package models;

/**
 * Перечисление возможных периодов аренды
 */
public enum PeriodType {
    ONE_DAY(1, "сутки"),
    TWO_DAYS(2, "двое суток"),
    THREE_DAYS(3, "трое суток"),
    FOUR_DAYS(4, "четверо суток"),
    FIVE_DAYS(5, "пятеро суток"),
    SIX_DAYS(6, "шестеро суток"),
    SEVEN_DAYS(7, "семеро суток");

    private final int value;
    private final String description;

    /**
     * Конструктор периода
     *
     * @param value       числовое значение
     * @param description текстовое описание
     */
    PeriodType(int value, String description) {
        this.value = value;
        this.description = description;
    }

    /**
     * Возвращает числовое значение периода
     *
     * @return значение периода
     */
    public int getValue() {
        return value;
    }

    /**
     * Возвращает текстовое описание периода
     *
     * @return описание периода
     */
    public String getDescription() {
        return description;
    }
}
