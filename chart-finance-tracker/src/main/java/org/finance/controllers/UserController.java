package org.finance.controllers;

import jakarta.enterprise.context.ApplicationScoped;
import org.finance.models.User;

import java.util.UUID;

@ApplicationScoped
public class UserController {

    private User user;

    public UUID getRandomActivityId() {
        user = getUser();
        user.setActivityId(UUID.randomUUID());
        return user.getActivityId();
    }

    private User getUser() {
        return this.user == null ? User.builder()
                .activityId(UUID.randomUUID())
                .build()
                : this.user;
    }
}
