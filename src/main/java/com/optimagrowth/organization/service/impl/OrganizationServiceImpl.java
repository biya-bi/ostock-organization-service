package com.optimagrowth.organization.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.optimagrowth.organization.model.Organization;
import com.optimagrowth.organization.repository.OrganizationRepository;
import com.optimagrowth.organization.service.OrganizationService;

@Service
class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    OrganizationServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public Organization findById(String organizationId) {
        return organizationRepository.findById(organizationId).orElse(null);
    }

    @Override
    public Organization create(Organization organization) {
        organization.setId(UUID.randomUUID().toString());

        return organizationRepository.save(organization);
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
