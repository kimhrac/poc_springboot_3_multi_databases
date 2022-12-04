package com.acme.poc.springboot.backend.database.postgresql.repository;

import com.acme.poc.springboot.backend.database.postgresql.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface PostgreSQLUserRepository extends CrudRepository<User, UUID>, PagingAndSortingRepository<User, UUID> {
}
