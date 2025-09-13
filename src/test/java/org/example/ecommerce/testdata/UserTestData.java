// src/test/java/org/example/ecommerce/testdata/UserTestData.java
package org.example.ecommerce.testdata;

import java.util.Collections;
import java.util.UUID;

import org.example.ecommerce.entity.UserEntity;
import org.example.ecommerce.model.User;

public class UserTestData {
    public static final UUID DEFAULT_USER_ID = UUID.randomUUID();
    public static final String DEFAULT_USERNAME = "testuser";
    public static final String DEFAULT_EMAIL = "test@example.com";
    public static final String DEFAULT_FIRST_NAME = "Test";
    public static final String DEFAULT_LAST_NAME = "User";

    public static UserEntity createUserEntity() {
        return createUserEntity(DEFAULT_USER_ID, DEFAULT_USERNAME, DEFAULT_EMAIL);
    }

    public static UserEntity createUserEntity(UUID userId, String username, String email) {
        return UserEntity.builder()
                .id(userId)
                .username(username)
                .email(email)
                .firstName(DEFAULT_FIRST_NAME)
                .lastName(DEFAULT_LAST_NAME)
                .userStatus("ACTIVE")
                .addresses(Collections.emptyList())
                .build();
    }

    public static User createUserModel() {
        return createUserModel(DEFAULT_USER_ID);
    }

    public static User createUserModel(UUID userId) {
        return new User()
                .id(userId.toString())
                .username(DEFAULT_USERNAME)
                .email(userId + "@example.com")
                .firstName(DEFAULT_FIRST_NAME)
                .lastName(DEFAULT_LAST_NAME);
    }

    
}