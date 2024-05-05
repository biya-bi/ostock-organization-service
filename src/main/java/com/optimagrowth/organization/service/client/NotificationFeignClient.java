package com.optimagrowth.organization.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.optimagrowth.organization.model.NotificationEvent;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@FeignClient("notification-service")
@CircuitBreaker(name = "notificationFeignClient")
@Retry(name = "notificationFeignClient")
public interface NotificationFeignClient {
    @PostMapping("/v1/notification/register")
    ResponseEntity<NotificationEvent> register(@Valid @NotNull @RequestBody NotificationEvent event);

    @PostMapping("/v1/notification/send/{eventType}")
    ResponseEntity<Void> send(@Valid @NotNull @PathVariable("eventType") String eventType);
}
