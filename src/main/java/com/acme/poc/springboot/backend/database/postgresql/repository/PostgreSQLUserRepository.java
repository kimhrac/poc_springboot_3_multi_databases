package com.acme.poc.springboot.backend.database.postgresql.repository;

import com.acme.poc.springboot.backend.database.postgresql.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;


@Repository()
public interface PostgreSQLUserRepository extends JpaRepository<User, UUID> {


    Optional<User> findById(UUID uuid);

    Page<User> findAll(Pageable pageable);
    Page<User> findAllById(UUID uuid, Pageable pageable);

    @Transactional(transactionManager = "postgresqlUserTransactionManager")
    @Modifying
    void delete(User entity);
    @Transactional(transactionManager = "postgresqlUserTransactionManager")
    @Modifying
    void deleteById(UUID uuid);

    @Transactional(transactionManager = "postgresqlUserTransactionManager")
    @Modifying
    <S extends User> S save(S entity);

    boolean existsById(UUID uuid);
    long count();

    @Query("""
        SELECT *
        FROM user
        WHERE firstName = 'John'
        """)               // TODO Change this; just an example
    Page<User> findAllWithPagination(Pageable pageable);

    @Query("""
        SELECT *
        FROM user u
        WHERE birthday IS NULL
        ORDER BY u.username
        """)
    Page<User> findAllUsersWithoutBirthday(Pageable pageable);

}
