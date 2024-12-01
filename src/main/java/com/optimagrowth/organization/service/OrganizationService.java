package com.optimagrowth.organization.service;

import java.util.UUID;

import org.springframework.data.domain.Page;

import com.optimagrowth.organization.criteria.SearchCriteria;
import com.optimagrowth.orm.model.Organization;

public interface OrganizationService {
    Organization create(Organization organization);

    Organization readById(UUID organizationId);

    Page<Organization> read(SearchCriteria criteria, Integer pageNumber, Integer pageSize);

    Organization update(Organization organization);

    void delete(Organization organization);
}
