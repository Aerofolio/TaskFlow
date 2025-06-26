package com.example.taskflow.model.complexTypes;

public enum TaskPriorityEnum {
    HIGH("Alta"),
    MEDIUM("Média"),
    LOW("Baixa");

    private final String description;

    TaskPriorityEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
