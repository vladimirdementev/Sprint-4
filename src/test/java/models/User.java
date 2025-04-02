package models;

import java.util.Objects;

/**
 * Класс, представляющий пользователя
 */
public class User {
    private final String name;
    private final String surname;
    private final String address;
    private final String metroStation;
    private final String phone;

    private User(Builder builder) {
        this.name = Objects.requireNonNull(builder.name, "Имя не может быть null");
        this.surname = Objects.requireNonNull(builder.surname, "Фамилия не может быть null");
        this.address = Objects.requireNonNull(builder.address, "Адрес не может быть null");
        this.metroStation = builder.metroStation;
        this.phone = Objects.requireNonNull(builder.phone, "Телефон не может быть null");
    }

    /**
     * Возвращает имя пользователя
     *
     * @return имя
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает фамилию пользователя
     *
     * @return фамилия
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Возвращает адрес пользователя
     *
     * @return адрес
     */
    public String getAddress() {
        return address;
    }

    /**
     * Возвращает станцию метро
     *
     * @return станция метро
     */
    public String getMetroStation() {
        return metroStation;
    }

    /**
     * Возвращает телефон
     *
     * @return телефон
     */
    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", address='" + address + '\'' +
                ", metroStation='" + metroStation + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(address, user.address) &&
                Objects.equals(metroStation, user.metroStation) &&
                Objects.equals(phone, user.phone);
    }

    /**
     * Строитель для создания объектов User
     */
    public static class Builder {
        private String name;
        private String surname;
        private String address;
        private String metroStation;
        private String phone;

        public Builder() {
        }

        /**
         * Устанавливает имя
         *
         * @param name имя
         * @return текущий билдер
         */
        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Устанавливает фамилию
         *
         * @param surname фамилия
         * @return текущий билдер
         */
        public Builder setSurname(String surname) {
            this.surname = surname;
            return this;
        }

        /**
         * Устанавливает адрес
         *
         * @param address адрес
         * @return текущий билдер
         */
        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        /**
         * Устанавливает станцию метро
         *
         * @param metroStation станция метро
         * @return текущий билдер
         */
        public Builder setMetroStation(String metroStation) {
            this.metroStation = metroStation;
            return this;
        }

        /**
         * Устанавливает телефон
         *
         * @param phone телефон
         * @return текущий билдер
         */
        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        /**
         * Создает новый объект User
         *
         * @return новый объект User
         */
        public User build() {
            if (name == null || surname == null || address == null || phone == null || metroStation == null) {
                throw new IllegalStateException("Обязательные поля не заполнены");
            }
            return new User(this);
        }
    }
}