package com.optimagrowth.organization.service;

import com.optimagrowth.organization.model.Organization;

public interface OrganizationService {
    Organization create(Organization organization);
    
    Organization readById(String organizationId);

    Iterable<Organization> readAll();

    Organization update(Organization organization);

    void delete(Organization organization);
}
