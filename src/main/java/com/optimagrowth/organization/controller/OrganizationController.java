package com.optimagrowth.organization.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Locale;
import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.optimagrowth.organization.exception.NotFoundException;
import com.optimagrowth.organization.model.Organization;
import com.optimagrowth.organization.service.MessageService;
import com.optimagrowth.organization.service.OrganizationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "v1/organization")
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

    @GetMapping("/{organizationId}")
    ResponseEntity<Organization> findById(@PathVariable("organizationId") String organizationId,
            @RequestHeader(value = "Accept-Language", required = false) Locale locale) {
        Organization organization = organizationService.findById(organizationId);

        if (organization == null) {
            throw new NotFoundException(messageService.getMessage(ORGANIZATION_NOT_FOUND, locale, organizationId));
        }

        return ResponseEntity.ok(addLinks(organization, locale));
    }

    @PostMapping
    ResponseEntity<Organization> create(@RequestBody Organization organization,
            @RequestHeader(value = "Accept-Language", required = false) Locale locale) {
        Objects.requireNonNull(organization,
                messageService.getMessage(ORGANIZATION_CANNOT_BE_NULL, locale, organization));

        var newOrganization = organizationService.create(organization);

        log.info(messageService.getMessage(ORGANIZATION_CREATE_MESSAGE, locale, newOrganization));

        return ResponseEntity.ok(addLinks(newOrganization, locale));
    }

    @PutMapping("/{organizationId}")
    ResponseEntity<Organization> update(@PathVariable("organizationId") String organizationId,
            @RequestBody Organization organization,
            @RequestHeader(value = "Accept-Language", required = false) Locale locale) {
        Objects.requireNonNull(organization,
                messageService.getMessage(ORGANIZATION_CANNOT_BE_NULL, locale, organization));

        var updatedOrganization = organizationService.update(organization);

        log.info(messageService.getMessage(ORGANIZATION_UPDATE_MESSAGE, locale, updatedOrganization));

        return ResponseEntity.ok(addLinks(updatedOrganization, locale));
    }

    @DeleteMapping("/{organizationId}")
    ResponseEntity<Void> delete(@PathVariable("organizationId") String organizationId,
            @RequestHeader(value = "Accept-Language", required = false) Locale locale) {
        var organization = organizationService.findById(organizationId);

        if (organization == null) {
            throw new NotFoundException(
                    messageService.getMessage(ORGANIZATION_NOT_FOUND, locale, organizationId, organizationId));
        }

        organizationService.delete(organization);

        log.info(messageService.getMessage(ORGANIZATION_DELETE_MESSAGE, locale, organizationId, organizationId));

        return ResponseEntity.ok(null);
    }

    private Organization addLinks(Organization organization, Locale locale) {
        var methodOn = methodOn(OrganizationController.class);
        var organizationId = organization.getId();
        return organization.add(linkTo(methodOn.findById(organizationId, locale)).withSelfRel(),
                linkTo(methodOn.update(organizationId, organization, locale)).withRel("update"),
                linkTo(methodOn.delete(organizationId, locale)).withRel("delete"));
    }

}
