package org.finance.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.finance.controllers.UserController;

import java.util.UUID;

@ApplicationScoped
public class UserService {
    @Inject
    UserController userController;

    public UUID getActivityId() {
        return userController.getRandomActivityId();
    }
}
