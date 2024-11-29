package com.optimagrowth.organization.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.optimagrowth.organization.criteria.SearchCriteria;
import com.optimagrowth.organization.repository.OrganizationRepository;
import com.optimagrowth.organization.service.OrganizationService;
import com.optimagrowth.orm.model.Organization;

@Service
class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    OrganizationServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public Organization create(Organization organization) {
        organization.setId(UUID.randomUUID());

        return organizationRepository.save(organization);
    }

    @Override
    public Organization readById(UUID organizationId) {
        return organizationRepository.findById(organizationId).orElse(null);
    }

    @Override
    public Iterable<Organization> readAll() {
        return organizationRepository.findAll();
    }

    @Override
    public Iterable<Organization> read(SearchCriteria criteria) {
        return organizationRepository.findByName(criteria.name());
    }

    @Override
    public Organization update(Organization organization) {
        return organizationRepository.save(organization);
    }

    @Override
    public void delete(Organization organization) {
        organizationRepository.deleteById(organization.getId());
    }

}
