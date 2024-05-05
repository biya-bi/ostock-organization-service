package com.optimagrowth.organization.service.impl;

import java.util.Arrays;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.optimagrowth.organization.model.Notification;
import com.optimagrowth.organization.model.NotificationAction;
import com.optimagrowth.organization.model.NotificationEvent;
import com.optimagrowth.organization.model.NotificationPayload;
import com.optimagrowth.organization.model.Organization;
import com.optimagrowth.organization.repository.OrganizationRepository;
import com.optimagrowth.organization.service.OrganizationService;
import com.optimagrowth.organization.service.client.NotificationFeignClient;

@Service
class OrganizationServiceImpl implements OrganizationService {

    private static final String NOTIFICATION_TITLE = "News from O-Stock!";

    private final OrganizationRepository organizationRepository;
    private final NotificationFeignClient notificationFeignClient;
    private final Gson gson = new Gson();

    OrganizationServiceImpl(OrganizationRepository organizationRepository,
            NotificationFeignClient notificationFeignClient) {
        this.organizationRepository = organizationRepository;
        this.notificationFeignClient = notificationFeignClient;
    }

    @Override
    public Organization create(Organization organization) {
        organization.setId(UUID.randomUUID().toString());

        var newOrganization = organizationRepository.save(organization);

        notify("OrganizationCreated", "Organization Created");

        return newOrganization;
    }

    @Override
    public Organization readById(String organizationId) {
        return organizationRepository.findById(organizationId).orElse(null);
    }

    @Override
    public Iterable<Organization> readAll() {
        return organizationRepository.findAll();
    }

    @Override
    public Organization update(Organization organization) {
        var updatedOrganization = organizationRepository.save(organization);

        notify("OrganizationUpdated", "Organization Updated");

        return updatedOrganization;

    }

    @Override
    public void delete(Organization organization) {
        organizationRepository.deleteById(organization.getId());

        notify("OrganizationDeleted", "Organization Deleted");
    }

    private NotificationEvent createEvent(String title, String body, String eventType) {
        var notification = new Notification();
        notification.setTitle(title);
        notification.setBody(body);
        notification.setActions(Arrays.asList(new NotificationAction("explore", "Go to the web site")));

        var payload = new NotificationPayload(notification);

        var event = new NotificationEvent();
        event.setType(eventType);
        event.setPayload(gson.toJson(payload));

        return event;
    }

    private void notify(String eventType, String body) {
        var event = createEvent(NOTIFICATION_TITLE, body, eventType);

        notificationFeignClient.register(event);
        notificationFeignClient.send(eventType);
    }

}
