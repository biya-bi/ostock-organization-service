package com.optimagrowth.organization.service;

import java.util.UUID;

import com.optimagrowth.orm.model.Organization;

public interface OrganizationService {
    Organization create(Organization organization);
    
    Organization readById(UUID organizationId);

    Iterable<Organization> readAll();

    Organization update(Organization organization);

    void delete(Organization organization);
}
