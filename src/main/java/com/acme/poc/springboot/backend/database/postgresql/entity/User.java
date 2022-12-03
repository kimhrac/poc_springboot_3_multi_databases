package com.acme.poc.springboot.backend.database.postgresql.entity;

import com.acme.poc.springboot.backend.database.postgresql.entity.common.PostgreSQLEntityWithUUID;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;


/**
 * ???
 */
@Entity(name = "User")
@Table(schema = "public", name = "user", indexes = {
        @Index(name = "idx_user_username", columnList = "username")
})
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EntityListeners(AuditingEntityListener.class)
public class User extends PostgreSQLEntityWithUUID {

    @Column(name = "username", nullable = false)
    private String username;
    private String firstName;
    private String lastName;
    @Column(name = "email", unique = true)
    @NotBlank(message = "Summary cannot be empty")
    private String email;
    private Instant birthday;

}
