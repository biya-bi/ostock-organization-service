package com.optimagrowth.organization.model;

import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;

@Data
public class License extends RepresentationModel<License> {
    private UUID id;
    private String description;
    private UUID organizationId;
    private String productName;
    private String licenseType;
    private String comment;
}
