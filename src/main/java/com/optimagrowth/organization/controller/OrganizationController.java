package com.optimagrowth.organization.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Objects;
import java.util.UUID;

import org.nguiland.rest.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.optimagrowth.dto.OrganizationDto;
import com.optimagrowth.dto.PageDto;
import com.optimagrowth.organization.criteria.SearchCriteria;
import com.optimagrowth.organization.service.OrganizationService;
import com.optimagrowth.organization.service.client.LicenseFeignClient;
import com.optimagrowth.organization.translator.OrganizationTranslator;
import com.optimagrowth.orm.model.Organization;

import jakarta.ws.rs.NotFoundException;
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

    private OrganizationController organizationControllerMethodOn = methodOn(OrganizationController.class);
    private LicenseFeignClient licenseFeignClientMethodOn = methodOn(LicenseFeignClient.class);

    OrganizationController(OrganizationService organizationService, MessageService messageService) {
        this.organizationService = organizationService;
        this.messageService = messageService;
    }

    @PostMapping
    ResponseEntity<OrganizationDto> create(@RequestBody OrganizationDto dto) {
        Objects.requireNonNull(dto, messageService.getMessage(ORGANIZATION_CANNOT_BE_NULL));

        var organization = OrganizationTranslator.translate(dto);
        var createdOrganization = organizationService.create(organization);

        log.info(messageService.getMessage(ORGANIZATION_CREATE_MESSAGE, createdOrganization));

        return ResponseEntity.ok(toDto(createdOrganization));
    }

    @GetMapping("/{organizationId}")
    ResponseEntity<OrganizationDto> readById(@PathVariable("organizationId") UUID organizationId) {
        var organization = organizationService.readById(organizationId);

        if (organization == null) {
            throw new NotFoundException(messageService.getMessage(ORGANIZATION_NOT_FOUND, organizationId));
        }

        return ResponseEntity.ok(toDto(organization));
    }

    @PostMapping("/search")
    ResponseEntity<PageDto<OrganizationDto>> read(@RequestBody SearchCriteria criteria,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        var page = organizationService.read(criteria, pageNumber, pageSize);

        var organizations = page.getContent().stream().map(this::toDto).toList();

        var pageDto = new PageDto<>(organizations, page.getNumber(), page.getSize(), page.getTotalPages(),
                page.getNumberOfElements(), page.getTotalElements());

        return ResponseEntity.ok(pageDto);
    }

    @PutMapping("/{organizationId}")
    ResponseEntity<OrganizationDto> update(@PathVariable("organizationId") UUID organizationId,
            @RequestBody OrganizationDto dto) {
        Objects.requireNonNull(dto, messageService.getMessage(ORGANIZATION_CANNOT_BE_NULL));

        var organization = OrganizationTranslator.translate(dto, organizationId);
        var updatedOrganization = organizationService.update(organization);

        log.info(messageService.getMessage(ORGANIZATION_UPDATE_MESSAGE, updatedOrganization));

        return ResponseEntity.ok(toDto(updatedOrganization));
    }

    @DeleteMapping("/{organizationId}")
    ResponseEntity<Void> delete(@PathVariable("organizationId") UUID organizationId) {
        var organization = organizationService.readById(organizationId);

        if (organization == null) {
            throw new NotFoundException(messageService.getMessage(ORGANIZATION_NOT_FOUND, organizationId));
        }

        organizationService.delete(organization);

        log.info(messageService.getMessage(ORGANIZATION_DELETE_MESSAGE, organizationId));

        return ResponseEntity.ok(null);
    }

    private OrganizationDto toDto(Organization organization) {
        var organizationId = organization.getId();

        var dto = OrganizationTranslator.translate(organization);

        return dto.add(linkTo(organizationControllerMethodOn.readById(organizationId)).withSelfRel(),
                linkTo(organizationControllerMethodOn.update(organizationId, dto)).withRel("update"),
                linkTo(organizationControllerMethodOn.delete(organizationId)).withRel("delete"),
                linkTo(licenseFeignClientMethodOn.getLicenses(organizationId)).withRel("licenses"),
                linkTo(licenseFeignClientMethodOn.create(organizationId, null)).withRel("addLicense"));
    }

}
