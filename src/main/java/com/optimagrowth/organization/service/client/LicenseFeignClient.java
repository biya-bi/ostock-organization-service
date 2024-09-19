package com.optimagrowth.organization.service.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.optimagrowth.dto.LicenseDto;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@FeignClient("license-service")
@CircuitBreaker(name = "licenseFeignClient")
@Retry(name = "licenseFeignClient")
public interface LicenseFeignClient {
    @GetMapping("/v1/license/{organizationId}")
    ResponseEntity<CollectionModel<LicenseDto>> getLicenses(@PathVariable("organizationId") UUID organizationId);
}
