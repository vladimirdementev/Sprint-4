package models;

/**
 * Перечисление возможных периодов аренды
 */
public enum PeriodType {
    ONE_DAY("сутки"),
    TWO_DAYS( "двое суток"),
    THREE_DAYS( "трое суток"),
    FOUR_DAYS( "четверо суток"),
    FIVE_DAYS( "пятеро суток"),
    SIX_DAYS( "шестеро суток"),
    SEVEN_DAYS( "семеро суток");

    private final String description;

    /**
     * Конструктор периода
     *
     * @param description текстовое описание
     */
    PeriodType(String description) {
        this.description = description;
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
