package ru.neoflex.jd.dto.enumerated;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Application {
    MOBILE("mobile"),
    MAIL("mail"),
    GOSUSLUGI("gosuslugi"),
    BANK("bank");

    public static Application fromValue(String value) {
        for (Application application : Application.values()) {
            if (application.getApplicationValue().equalsIgnoreCase(value)) {
                return application;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }

    private final String applicationValue;

}
