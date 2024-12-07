package com.optimagrowth.organization.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.PageRequest;

import com.optimagrowth.organization.criteria.SearchCriteria;
import com.optimagrowth.orm.model.Organization;

import jakarta.persistence.EntityManager;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = OrganizationRepository.class))
class OrganizationRepositoryTest {

	@Autowired
	private OrganizationRepository organizationRepository;

	@Autowired
	private EntityManager entityManager;

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
		organizations.forEach(entityManager::persist);
	}

	@AfterEach
	void cleanUp() {
		organizations.forEach(entityManager::remove);
	}

	@Test
	void find_ExactNameIsGiven_ReturnOrganization() {
		var criteria = new SearchCriteria(unitedNations.getName(), null, null, null);

		var page = organizationRepository.find(criteria, null);

		// Verify that the total number of organizations returned is 1
		assertEquals(1, page.getTotalElements());
		// Verify that the returned organization is right one
		assertTrue(exists(page.getContent(), unitedNations.getId()));
	}

	@Test
	void find_LowercaseNameIsGiven_ReturnOrganization() {
		var criteria = new SearchCriteria(unitedNations.getName().toLowerCase(), null, null, null);

		var page = organizationRepository.find(criteria, null);

		// Verify that the total number of organizations returned is 1
		assertEquals(1, page.getTotalElements());
		// Verify that the returned organization is right one
		assertTrue(exists(page.getContent(), unitedNations.getId()));
	}

	@Test
	void find_FirstThreeNameCharactersAreGiven_ReturnOrganization() {
		var criteria = new SearchCriteria(unitedNations.getName().substring(0, 3), null, null, null);

		var page = organizationRepository.find(criteria, null);

		// Verify that the total number of organizations returned is 1
		assertEquals(1, page.getTotalElements());
		// Verify that the returned organization is right one
		assertTrue(exists(page.getContent(), unitedNations.getId()));
	}

	@Test
	void find_NoCriterionIsGiven_ReturnOrganizations() {
		var criteria = new SearchCriteria(null, null, null, null);

		var page = organizationRepository.find(criteria, null);

		assertEquals(organizations.size(), page.getTotalElements());
	}

	@Test
	void find_NonExistentNameIsGiven_ReturnNoOrganization() {
		var criteria = new SearchCriteria(nonExistent, null, null, null);

		var page = organizationRepository.find(criteria, null);

		assertTrue(page.isEmpty());
	}

	@Test
	void find_ExactContactNameIsGiven_ReturnOrganization() {
		var criteria = new SearchCriteria(null, unitedNations.getContactName(), null, null);

		var page = organizationRepository.find(criteria, null);

		// Verify that the total number of organizations returned is 1
		assertEquals(1, page.getTotalElements());
		// Verify that the returned organization is right one
		assertTrue(exists(page.getContent(), unitedNations.getId()));
	}

	@Test
	void find_LowercaseContactNameIsGiven_ReturnOrganization() {
		var criteria = new SearchCriteria(null, unitedNations.getContactName().toLowerCase(), null, null);

		var page = organizationRepository.find(criteria, null);

		// Verify that the total number of organizations returned is 1
		assertEquals(1, page.getTotalElements());
		// Verify that the returned organization is right one
		assertTrue(exists(page.getContent(), unitedNations.getId()));
	}

	@Test
	void find_FirstThreeContactNameCharactersAreGiven_ReturnOrganization() {
		var criteria = new SearchCriteria(null, unitedNations.getContactName().substring(0, 3), null, null);

		var page = organizationRepository.find(criteria, null);

		// Verify that the total number of organizations returned is 1
		assertEquals(1, page.getTotalElements());
		// Verify that the returned organization is right one
		assertTrue(exists(page.getContent(), unitedNations.getId()));
	}

	@Test
	void find_NonExistentContactNameIsGiven_ReturnNoOrganization() {
		var criteria = new SearchCriteria(null, nonExistent, null, null);

		var page = organizationRepository.find(criteria, null);

		assertTrue(page.isEmpty());
	}

	@Test
	void find_ExactContactEmailIsGiven_ReturnOrganization() {
		var criteria = new SearchCriteria(null, null, unitedNations.getContactEmail(), null);

		var page = organizationRepository.find(criteria, null);

		// Verify that the total number of organizations returned is 1
		assertEquals(1, page.getTotalElements());
		// Verify that the returned organization is right one
		assertTrue(exists(page.getContent(), unitedNations.getId()));
	}

	@Test
	void find_LowercaseContactEmailIsGiven_ReturnOrganization() {
		var criteria = new SearchCriteria(null, null, unitedNations.getContactEmail().toLowerCase(), null);

		var page = organizationRepository.find(criteria, null);

		// Verify that the total number of organizations returned is 1
		assertEquals(1, page.getTotalElements());
		// Verify that the returned organization is right one
		assertTrue(exists(page.getContent(), unitedNations.getId()));
	}

	@Test
	void find_FirstThreeContactEmailCharactersAreGiven_ReturnOrganization() {
		var criteria = new SearchCriteria(null, null, unitedNations.getContactEmail().substring(0, 3), null);

		var page = organizationRepository.find(criteria, null);

		// Verify that the total number of organizations returned is 1
		assertEquals(1, page.getTotalElements());
		// Verify that the returned organization is right one
		assertTrue(exists(page.getContent(), unitedNations.getId()));
	}

	@Test
	void find_NonExistentContactEmailIsGiven_ReturnNoOrganization() {
		var criteria = new SearchCriteria(null, null, nonExistent, null);

		var page = organizationRepository.find(criteria, null);

		assertTrue(page.isEmpty());
	}

	@Test
	void find_ExactContactPhoneIsGiven_ReturnOrganization() {
		var criteria = new SearchCriteria(null, null, null, unitedNations.getContactPhone());

		var page = organizationRepository.find(criteria, null);

		// Verify that the total number of organizations returned is 1
		assertEquals(1, page.getTotalElements());
		// Verify that the returned organization is right one
		assertTrue(exists(page.getContent(), unitedNations.getId()));
	}

	@Test
	void find_LowercaseContactPhoneIsGiven_ReturnOrganization() {
		var criteria = new SearchCriteria(null, null, null, unitedNations.getContactPhone().toLowerCase());

		var page = organizationRepository.find(criteria, null);

		// Verify that the total number of organizations returned is 1
		assertEquals(1, page.getTotalElements());
		// Verify that the returned organization is right one
		assertTrue(exists(page.getContent(), unitedNations.getId()));
	}

	@Test
	void find_FirstFourContactPhoneCharactersAreGiven_ReturnOrganization() {
		var criteria = new SearchCriteria(null, null, null, unitedNations.getContactPhone().substring(0, 4));

		var page = organizationRepository.find(criteria, null);

		// Verify that the total number of organizations returned is 1
		assertEquals(1, page.getTotalElements());
		// Verify that the returned organization is right one
		assertTrue(exists(page.getContent(), unitedNations.getId()));
	}

	@Test
	void find_NonExistentContactPhoneIsGiven_ReturnNoOrganization() {
		var criteria = new SearchCriteria(null, null, null, nonExistent);

		var page = organizationRepository.find(criteria, null);

		assertTrue(page.isEmpty());
	}

	@Test
	void find_NameExistsButContactDoesNot_ReturnNoOrganization() {
		var criteria = new SearchCriteria(unitedNations.getName(), nonExistent, null, null);

		var page = organizationRepository.find(criteria, null);

		assertTrue(page.isEmpty());
	}

	@Test
	void find_PageNumberIs0AndPageSizeIs1_ReturnOrganization() {
		var criteria = new SearchCriteria(null, null, null, null);

		var page = organizationRepository.find(criteria, PageRequest.of(0, 1));

		var organization1 = page.getContent().stream()
				.filter(organization -> organization.getId().equals(unitedNations.getId())).findFirst().orElse(null);

		assertNotNull(organization1);
		assertEquals(0, page.getNumber());
		assertEquals(organizations.size(), page.getTotalPages());
	}

	@Test
	void find_PageNumberIs1AndPageSizeIs1_ReturnOrganization() {
		var criteria = new SearchCriteria(null, null, null, null);

		var page = organizationRepository.find(criteria, PageRequest.of(1, 1));

		var organization1 = page.getContent().stream()
				.filter(organization -> organization.getId().equals(nasa.getId())).findFirst().orElse(null);

		assertNotNull(organization1);
		assertEquals(1, page.getNumber());
		assertEquals(organizations.size(), page.getTotalPages());
	}

	private Organization constructOrganization(UUID id, String organizationName, String contactName,
			String contactEmail, String contactPhone) {
		var organization = new Organization();
		organization.setId(id);
		organization.setName(organizationName);
		organization.setContactName(contactName);
		organization.setContactEmail(contactEmail);
		organization.setContactPhone(contactPhone);
		return organization;
	}

	private boolean exists(List<Organization> organizations, UUID id) {
		return organizations.stream().anyMatch(org -> org.getId().equals(id));
	}

}