package com.example.taskflow.model.complexTypes;

public enum TaskStatusEnum {
    PENDING(0, "Pendente"),
    IN_PROGRESS(1, "Em andamento"),
    COMPLETED(2, "Concluída"),
    CANCELLED(3, "Cancelada");

    private final int code;
    private final String description;

    TaskStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static TaskStatusEnum fromCode(int code) {
        for (TaskStatusEnum status : values()) {
            if (status.code == code) return status;
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }
}

