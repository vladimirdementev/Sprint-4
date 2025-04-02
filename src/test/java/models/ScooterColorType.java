package models;

/**
 * Перечисление доступных цветов самокатов
 */
public enum ScooterColorType {
    BLACK("чёрный жемчуг"),
    GREY("серая безысходность");

    private final String colorName;

    /**
     * Конструктор цвета
     *
     * @param colorName название цвета
     */
    ScooterColorType(String colorName) {
        this.colorName = colorName;
    }

    /**
     * Возвращает название цвета
     *
     * @return название цвета
     */
    public String getColorName() {
        return colorName;
    }
}
