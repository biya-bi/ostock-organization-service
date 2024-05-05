package com.optimagrowth.organization.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEvent {
    @NotBlank
    private String type;

    private Long occurredAt;

    @NotNull
    private String payload;
}
