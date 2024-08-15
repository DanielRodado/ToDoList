package com.mindhub.todolist.dto;

public record TaskApplicationDTO(String title, String description, String taskStatus, UserApplicationDTO userApp) {
}
