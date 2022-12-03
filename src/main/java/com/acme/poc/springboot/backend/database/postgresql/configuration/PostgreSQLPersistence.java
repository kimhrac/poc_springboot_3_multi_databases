package com.acme.poc.springboot.backend.database.postgresql.configuration;

import com.acme.poc.springboot.backend.database.postgresql.repository.PostgreSQLUserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.spi.PersistenceProvider;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;


@Configuration
//@PropertySource({ "classpath:database_postgresql.properties" })
@EnableJpaRepositories(
        basePackages = "com.acme.poc.springboot.backend.database.postgresql",
        entityManagerFactoryRef = "postgresqlUserEntityManager",
        transactionManagerRef = "postgresqlUserTransactionManager",
        repositoryBaseClass = PostgreSQLUserRepository.class
)
public class PostgreSQLPersistence {

    private final Environment env;


    public PostgreSQLPersistence(Environment env) {
        this.env = env;
    }


    @Bean("entityManager")
    public EntityManager entityManager() {
        return postgresqlUserEntityManager().getObject().createEntityManager();
    }

//    @Bean("entityManagerFactory")
//    public EntityManagerFactory entityManagerFactory() {
//        return (EntityManagerFactory) postgresqlUserEntityManager();
//    }

//    @Bean("entityManagerFactory")
//    public LocalSessionFactoryBean sessionFactory() {
//        return new LocalSessionFactoryBean();
//    }

//    @Primary
    @Bean("PoC_PostgreSQL_UserTransactionManager")
    public PlatformTransactionManager postgresqlUserTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager() {{
            setEntityManagerFactory(postgresqlUserEntityManager().getObject());
//            setEntityManagerFactory(entityManagerFactory().getObject());
        }};
        return transactionManager;
    }

//    @Primary
    @Bean("PoC_PostgreSQL_UserEntityManager")
    public LocalContainerEntityManagerFactoryBean postgresqlUserEntityManager() {
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter() {{
            setGenerateDdl(true);
        }};
        HashMap<String, Object> properties = new HashMap<>() {{
            put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
            put("hibernate.dialect", env.getProperty("application.database.postgresql.hibernate.dialect"));
        }};
        PersistenceProvider provider = new HibernatePersistenceProvider();
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean() {{
            setDataSource(postgresqlUserDataSource());
            setPackagesToScan(new String[] { "com.acme.poc.springboot.backend.database.postgresql" });
            setJpaVendorAdapter(vendorAdapter);
            setJpaPropertyMap(properties);
            setPersistenceProvider(provider);
        }};
        return em;
    }

//    @Primary
    @Bean("PoC_PostgreSQL_UserDataSource")
    public DataSource postgresqlUserDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource() {{
            setDriverClassName(env.getProperty("application.database.postgresql.driverClassName"));
            setUrl(env.getProperty("application.database.postgresql.url"));
            setUsername(env.getProperty("application.database.postgresql.username"));
            setPassword(env.getProperty("application.database.postgresql.password"));
        }};
        return dataSource;
    }

}
