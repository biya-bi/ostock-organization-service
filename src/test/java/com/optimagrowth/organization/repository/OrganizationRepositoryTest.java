package com.optimagrowth.organization.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import com.optimagrowth.organization.criteria.SearchCriteria;
import com.optimagrowth.orm.model.Organization;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = OrganizationRepository.class))
class OrganizationRepositoryTest {

    @Autowired
    private OrganizationRepository organizationRepository;

    private final Organization unitedNations = constructOrganization(UUID.randomUUID(), "United Nations",
            "USG Mr. Li Junhua", "population@un.org",
            "+1-212-963-3209");

    private final Organization nasa = constructOrganization(UUID.randomUUID(), "NASA",
            "Kelly Garcia", "kelly.l.garcia@nasa.gov",
            "+1-650-604-3273");

    private final List<Organization> organizations = Arrays.asList(unitedNations, nasa);

    private final String nonExistent = "nonExistent";

    @BeforeEach
    void setUp() {
        organizationRepository.saveAll(organizations);
    }

    @AfterEach
    void cleanUp() {
        organizations.forEach(organization -> organizationRepository.deleteById(organization.getId()));
    }

    @Test
    void find_ExactNameIsGiven_ReturnOrganization() {
        SearchCriteria criteria = new SearchCriteria(unitedNations.getName(), null, null, null);

        Iterable<Organization> result = organizationRepository.find(criteria);

        // Verify that the total number of organizations returned is 1
        assertEquals(1, getCount(result));
        // Verify that the returned organization is right one
        assertEquals(1, getCount(result, unitedNations.getId()));
    }

    @Test
    void find_LowercaseNameIsGiven_ReturnOrganization() {
        SearchCriteria criteria = new SearchCriteria(unitedNations.getName().toLowerCase(), null, null, null);

        Iterable<Organization> result = organizationRepository.find(criteria);

        // Verify that the total number of organizations returned is 1
        assertEquals(1, getCount(result));
        // Verify that the returned organization is right one
        assertEquals(1, getCount(result, unitedNations.getId()));
    }

    @Test
    void find_FirstThreeNameCharactersAreGiven_ReturnOrganization() {
        SearchCriteria criteria = new SearchCriteria(unitedNations.getName().substring(0, 3), null, null, null);

        Iterable<Organization> result = organizationRepository.find(criteria);

        // Verify that the total number of organizations returned is 1
        assertEquals(1, getCount(result));
        // Verify that the returned organization is right one
        assertEquals(1, getCount(result, unitedNations.getId()));
    }

    @Test
    void find_NoCriterionIsGiven_ReturnOrganizations() {
        SearchCriteria criteria = new SearchCriteria(null, null, null, null);

        Iterable<Organization> result = organizationRepository.find(criteria);

        assertEquals(organizations.size(), getCount(result));
    }

    @Test
    void find_NoneExistentNameIsGiven_NoReturnOrganization() {
        SearchCriteria criteria = new SearchCriteria(nonExistent, null, null, null);

        Iterable<Organization> result = organizationRepository.find(criteria);

        assertEquals(0, getCount(result));
    }

    @Test
    void find_ExactContactNameIsGiven_ReturnOrganization() {
        SearchCriteria criteria = new SearchCriteria(null, unitedNations.getContactName(), null, null);

        Iterable<Organization> result = organizationRepository.find(criteria);

        // Verify that the total number of organizations returned is 1
        assertEquals(1, getCount(result));
        // Verify that the returned organization is right one
        assertEquals(1, getCount(result, unitedNations.getId()));
    }

    @Test
    void find_LowercaseContactNameIsGiven_ReturnOrganization() {
        SearchCriteria criteria = new SearchCriteria(null, unitedNations.getContactName().toLowerCase(), null, null);

        Iterable<Organization> result = organizationRepository.find(criteria);

        // Verify that the total number of organizations returned is 1
        assertEquals(1, getCount(result));
        // Verify that the returned organization is right one
        assertEquals(1, getCount(result, unitedNations.getId()));
    }

    @Test
    void find_FirstThreeContactNameCharactersAreGiven_ReturnOrganization() {
        SearchCriteria criteria = new SearchCriteria(null, unitedNations.getContactName().substring(0, 3), null, null);

        Iterable<Organization> result = organizationRepository.find(criteria);

        // Verify that the total number of organizations returned is 1
        assertEquals(1, getCount(result));
        // Verify that the returned organization is right one
        assertEquals(1, getCount(result, unitedNations.getId()));
    }

    @Test
    void find_NoneExistentContactNameIsGiven_NoReturnOrganization() {
        SearchCriteria criteria = new SearchCriteria(null, nonExistent, null, null);

        Iterable<Organization> result = organizationRepository.find(criteria);

        assertEquals(0, getCount(result));
    }

    @Test
    void find_ExactContactEmailIsGiven_ReturnOrganization() {
        SearchCriteria criteria = new SearchCriteria(null, null, unitedNations.getContactEmail(), null);

        Iterable<Organization> result = organizationRepository.find(criteria);

        // Verify that the total number of organizations returned is 1
        assertEquals(1, getCount(result));
        // Verify that the returned organization is right one
        assertEquals(1, getCount(result, unitedNations.getId()));
    }

    @Test
    void find_LowercaseContactEmailIsGiven_ReturnOrganization() {
        SearchCriteria criteria = new SearchCriteria(null, null, unitedNations.getContactEmail().toLowerCase(), null);

        Iterable<Organization> result = organizationRepository.find(criteria);

        // Verify that the total number of organizations returned is 1
        assertEquals(1, getCount(result));
        // Verify that the returned organization is right one
        assertEquals(1, getCount(result, unitedNations.getId()));
    }

    @Test
    void find_FirstThreeContactEmailCharactersAreGiven_ReturnOrganization() {
        SearchCriteria criteria = new SearchCriteria(null, null, unitedNations.getContactEmail().substring(0, 3), null);

        Iterable<Organization> result = organizationRepository.find(criteria);

        // Verify that the total number of organizations returned is 1
        assertEquals(1, getCount(result));
        // Verify that the returned organization is right one
        assertEquals(1, getCount(result, unitedNations.getId()));
    }

    @Test
    void find_NoneExistentContactEmailIsGiven_NoReturnOrganization() {
        SearchCriteria criteria = new SearchCriteria(null, null, nonExistent, null);

        Iterable<Organization> result = organizationRepository.find(criteria);

        assertEquals(0, getCount(result));
    }

    @Test
    void find_ExactContactPhoneIsGiven_ReturnOrganization() {
        SearchCriteria criteria = new SearchCriteria(null, null, null, unitedNations.getContactPhone());

        Iterable<Organization> result = organizationRepository.find(criteria);

        // Verify that the total number of organizations returned is 1
        assertEquals(1, getCount(result));
        // Verify that the returned organization is right one
        assertEquals(1, getCount(result, unitedNations.getId()));
    }

    @Test
    void find_LowercaseContactPhoneIsGiven_ReturnOrganization() {
        SearchCriteria criteria = new SearchCriteria(null, null, null, unitedNations.getContactPhone().toLowerCase());

        Iterable<Organization> result = organizationRepository.find(criteria);

        // Verify that the total number of organizations returned is 1
        assertEquals(1, getCount(result));
        // Verify that the returned organization is right one
        assertEquals(1, getCount(result, unitedNations.getId()));
    }

    @Test
    void find_FirstFourContactPhoneCharactersAreGiven_ReturnOrganization() {
        SearchCriteria criteria = new SearchCriteria(null, null, null, unitedNations.getContactPhone().substring(0, 4));

        Iterable<Organization> result = organizationRepository.find(criteria);

        // Verify that the total number of organizations returned is 1
        assertEquals(1, getCount(result));
        // Verify that the returned organization is right one
        assertEquals(1, getCount(result, unitedNations.getId()));
    }

    @Test
    void find_NoneExistentContactPhoneIsGiven_NoReturnOrganization() {
        SearchCriteria criteria = new SearchCriteria(null, null, null, nonExistent);

        Iterable<Organization> result = organizationRepository.find(criteria);

        assertEquals(0, getCount(result));
    }

    @Test
    void find_NameExistsButContactDoesNot_NoReturnOrganization() {
        SearchCriteria criteria = new SearchCriteria(unitedNations.getName(), nonExistent, null, null);

        Iterable<Organization> result = organizationRepository.find(criteria);

        assertEquals(0, getCount(result));
    }

    private Organization constructOrganization(UUID id, String organizationName, String contactName,
            String contactEmail, String contactPhone) {
        Organization organization = new Organization();
        organization.setId(id);
        organization.setName(organizationName);
        organization.setContactName(contactName);
        organization.setContactEmail(contactEmail);
        organization.setContactPhone(contactPhone);
        return organization;
    }

    private long getCount(Iterable<Organization> organizations) {
        return StreamSupport.stream(organizations.spliterator(), false).count();
    }

    private long getCount(Iterable<Organization> organizations, UUID id) {
        return StreamSupport.stream(organizations.spliterator(), false)
                .filter(org -> org.getId().equals(id)).count();
    }

}