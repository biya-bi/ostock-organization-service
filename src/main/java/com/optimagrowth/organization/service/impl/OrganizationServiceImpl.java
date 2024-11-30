package com.optimagrowth.organization.service.impl;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.optimagrowth.organization.criteria.SearchCriteria;
import com.optimagrowth.organization.repository.OrganizationRepository;
import com.optimagrowth.organization.service.OrganizationService;
import com.optimagrowth.orm.model.Organization;

@Service
class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    private static final int DEFAULT_PAGE_SIZE = 20;

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
    public Page<Organization> read(SearchCriteria criteria, Integer pageNumber, Integer pageSize) {
        Pageable pageable = getPageable(pageNumber, pageSize);
        return organizationRepository.find(criteria, pageable);
    }

    @Override
    public Organization update(Organization organization) {
        return organizationRepository.save(organization);
    }

    @Override
    public void delete(Organization organization) {
        organizationRepository.deleteById(organization.getId());
    }

    private Pageable getPageable(Integer pageNumber, Integer pageSize) {
        if (pageNumber == null || pageNumber < 0) {
            pageNumber = 0;
        }
        if (pageSize == null || pageSize < 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        return PageRequest.of(pageNumber, pageSize);
    }

}
