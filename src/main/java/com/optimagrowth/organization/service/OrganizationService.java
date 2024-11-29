package com.optimagrowth.organization.service;

import java.util.UUID;

import com.optimagrowth.organization.criteria.SearchCriteria;
import com.optimagrowth.orm.model.Organization;

public interface OrganizationService {
    Organization create(Organization organization);
    
    Organization readById(UUID organizationId);

    Iterable<Organization> readAll();

    Iterable<Organization> read(SearchCriteria criteria);

    Organization update(Organization organization);

    void delete(Organization organization);
}
