package com.acme.poc.springboot.backend.database.postgresql.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;


/**
 * User DTO
 *
 * See also:
 *      - https://github.com/javahippie/jukebox (for alternative to Lombok @Builder)          TODO Check this out!
 */
@Builder
//@Json
public record UserDto (
        UUID uuid,
        @JsonProperty(value = "username", required = true)
        String username,
        String firstName,
        String lastName,
        @JsonProperty(value = "email", required = true)
        String email,
        @JsonProperty(value = "birthday")
        Instant birthday
) {


    /**
     * Constructor. Needed for @Builder to work
     *
     * @param uuid
     * @param username
     * @param firstName
     * @param lastName
     * @param email
     * @param birthday
     */
    public UserDto(UUID uuid, String username, String firstName, String lastName, String email, Instant birthday) {
        this.uuid = uuid;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthday = birthday;
    }

};
