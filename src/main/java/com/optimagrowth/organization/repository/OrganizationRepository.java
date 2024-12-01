package com.optimagrowth.organization.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.optimagrowth.organization.criteria.SearchCriteria;
import com.optimagrowth.orm.model.Organization;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Repository
@CircuitBreaker(name = "organizationRepository")
public interface OrganizationRepository extends CrudRepository<Organization, UUID> {
     @Query(value = "select o from Organization o where "
            + "((:#{#criteria.name} is null) or (upper(o.name) like concat('%',upper(:#{#criteria.name}),'%'))) and "
            + "((:#{#criteria.contactName} is null) or (upper(o.contactName) like concat('%',upper(:#{#criteria.contactName}),'%'))) and "
            + "((:#{#criteria.contactEmail} is null) or (upper(o.contactEmail) like concat('%',upper(:#{#criteria.contactEmail}),'%'))) and "
            + "((:#{#criteria.contactPhone} is null) or (upper(o.contactPhone) like concat('%',upper(:#{#criteria.contactPhone}),'%'))) ", countQuery = "select count(o) from Organization o where "
                    + "((:#{#criteria.name} is null) or (upper(o.name) like concat('%',upper(:#{#criteria.name}),'%'))) and "
                    + "((:#{#criteria.contactName} is null) or (upper(o.contactName) like concat('%',upper(:#{#criteria.contactName}),'%'))) and "
                    + "((:#{#criteria.contactEmail} is null) or (upper(o.contactEmail) like concat('%',upper(:#{#criteria.contactEmail}),'%'))) and "
                    + "((:#{#criteria.contactPhone} is null) or (upper(o.contactPhone) like concat('%',upper(:#{#criteria.contactPhone}),'%')))")
    Page<Organization> find(@Param("criteria") SearchCriteria criteria, Pageable pageable);
}