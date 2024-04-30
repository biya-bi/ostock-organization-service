package com.optimagrowth.organization.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.optimagrowth.organization.exception.NotFoundException;
import com.optimagrowth.organization.model.Organization;
import com.optimagrowth.organization.service.OrganizationService;
import com.optimagrowth.service.MessageService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/organization")
@Slf4j
class OrganizationController {

    private static final String ORGANIZATION_CREATE_MESSAGE = "organization.create.message";
    private static final String ORGANIZATION_UPDATE_MESSAGE = "organization.update.message";
    private static final String ORGANIZATION_DELETE_MESSAGE = "organization.delete.message";
    private static final String ORGANIZATION_CANNOT_BE_NULL = "organization.cannot.be.null";
    private static final String ORGANIZATION_NOT_FOUND = "organization.not.found";

    private final OrganizationService organizationService;
    private final MessageService messageService;

    OrganizationController(OrganizationService organizationService, MessageService messageService) {
        this.organizationService = organizationService;
        this.messageService = messageService;
    }

    @PostMapping
    ResponseEntity<Organization> create(@RequestBody Organization organization) {
        Objects.requireNonNull(organization, messageService.getMessage(ORGANIZATION_CANNOT_BE_NULL));

        var newOrganization = organizationService.create(organization);

        log.info(messageService.getMessage(ORGANIZATION_CREATE_MESSAGE, newOrganization));

        return ResponseEntity.ok(addLinks(newOrganization));
    }

    @GetMapping("/{organizationId}")
    ResponseEntity<Organization> readById(@PathVariable("organizationId") String organizationId) {
        var organization = organizationService.readById(organizationId);

        if (organization == null) {
            throw new NotFoundException(messageService.getMessage(ORGANIZATION_NOT_FOUND, organizationId));
        }

        return ResponseEntity.ok(addLinks(organization));
    }

    @GetMapping
    ResponseEntity<Iterable<Organization>> read() {
        var organizations = StreamSupport.stream(organizationService.readAll().spliterator(), false).map(this::addLinks)
                .collect(Collectors.toList());

        return ResponseEntity.ok(organizations);
    }

    @PutMapping("/{organizationId}")
    ResponseEntity<Organization> update(@PathVariable("organizationId") String organizationId,
            @RequestBody Organization organization) {
        Objects.requireNonNull(organization, messageService.getMessage(ORGANIZATION_CANNOT_BE_NULL));

        var updatedOrganization = organizationService.update(organization);

        log.info(messageService.getMessage(ORGANIZATION_UPDATE_MESSAGE, updatedOrganization));

        return ResponseEntity.ok(addLinks(updatedOrganization));
    }

    @DeleteMapping("/{organizationId}")
    ResponseEntity<Void> delete(@PathVariable("organizationId") String organizationId) {
        var organization = organizationService.readById(organizationId);

        if (organization == null) {
            throw new NotFoundException(messageService.getMessage(ORGANIZATION_NOT_FOUND, organizationId));
        }

        organizationService.delete(organization);

        log.info(messageService.getMessage(ORGANIZATION_DELETE_MESSAGE, organizationId));

        return ResponseEntity.ok(null);
    }

    private Organization addLinks(Organization organization) {
        var methodOn = methodOn(OrganizationController.class);
        var organizationId = organization.getId();
        return organization.add(linkTo(methodOn.readById(organizationId)).withSelfRel(),
                linkTo(methodOn.update(organizationId, organization)).withRel("update"),
                linkTo(methodOn.delete(organizationId)).withRel("delete"));
    }

}
