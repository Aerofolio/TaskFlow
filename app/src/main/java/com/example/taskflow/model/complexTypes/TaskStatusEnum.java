package com.example.taskflow.model.complexTypes;

public enum TaskStatusEnum {
    PENDING("Pendente"),
    IN_PROGRESS("Em andamento"),
    COMPLETED("Concluída"),
    CANCELLED("Cancelada");

    private final String description;

    TaskStatusEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
