package com.optimagrowth.organization.model;

import java.util.List;

import lombok.Data;

@Data
public class Notification {
    private String title;
    private String body;
    private String icon;
    private List<NotificationAction> actions;
}
