package com.optimagrowth.organization.translator;

import java.util.UUID;

import com.optimagrowth.dto.OrganizationDto;
import com.optimagrowth.orm.model.Organization;

public final class OrganizationTranslator {
    private OrganizationTranslator() {
    }

    public static OrganizationDto translate(Organization organization) {
        return new OrganizationDto(organization.getName(), organization.getContactName(),
                organization.getContactEmail(),
                organization.getContactPhone());
    }

    public static Organization translate(OrganizationDto dto) {
        return translate(dto, null);
    }

    public static Organization translate(OrganizationDto dto, UUID organizationId) {
        var organization = new Organization();

        organization.setId(organizationId);
        organization.setName(dto.getName());
        organization.setContactName(dto.getContactName());
        organization.setContactEmail(dto.getContactEmail());
        organization.setContactPhone(dto.getContactPhone());

        return organization;
    }
}
