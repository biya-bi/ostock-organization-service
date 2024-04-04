package com.optimagrowth.organization.service;

import com.optimagrowth.organization.model.Organization;

public interface OrganizationService {
    Organization findById(String organizationId);

    Organization create(Organization organization);

    Organization update(Organization organization);

    void delete(Organization organization);
}
