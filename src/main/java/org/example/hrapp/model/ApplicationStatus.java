package org.example.hrapp.model;


import lombok.Getter;

@Getter
public enum ApplicationStatus {
    NEW("Новая"),
    REVIEWING("На рассмотрении"),
    INTERVIEW_SCHEDULED("Назначено собеседование"),
    INTERVIEWED("Прошел собеседование"),
    OFFER_MADE("Сделано предложение"),
    ACCEPTED("Принято"),
    REJECTED("Отклонено");

    private final String displayValue;

    ApplicationStatus(String displayValue) {
        this.displayValue = displayValue;
    }

}