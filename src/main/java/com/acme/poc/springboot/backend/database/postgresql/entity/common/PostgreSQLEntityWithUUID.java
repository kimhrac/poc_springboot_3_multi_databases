package com.acme.poc.springboot.backend.database.postgresql.entity.common;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import java.util.UUID;


/**
 * ???
 */
@MappedSuperclass                                                        // Tells JPA this class is a superclass of an entity and should have its attributes mapped into the entity
@TypeDefs({
    @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Data
public class PostgreSQLEntityWithUUID {

    @Id                                                                 // Set 'id' to be the primary key
    @Column(name = "uuid", nullable = false)
//    @Type(type = "pg-uuid")                                             // Set type to be the PostgreSQL specific 'pg-uuid' type
//    @Type(type = "org.hibernate.type.PostgresUUIDType")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;


    /**
     * Constructor
     */
    public PostgreSQLEntityWithUUID() {
        this.uuid = UUID.randomUUID();
    }

}
