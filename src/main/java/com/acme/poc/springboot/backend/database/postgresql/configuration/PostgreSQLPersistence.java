package com.acme.poc.springboot.backend.database.postgresql.configuration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.spi.PersistenceProvider;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.acme.poc.springboot.backend.database.postgresql.repository",
        entityManagerFactoryRef = "postgresqlEntityManagerFactory",
        transactionManagerRef = "postgresqlUserTransactionManager"
)
@EntityScan(basePackages = {
        "com.acme.poc.springboot.backend.database.postgresql.entity"
})
public class PostgreSQLPersistence {

    private final Environment env;


    public PostgreSQLPersistence(Environment env) {
        this.env = env;
    }


    @Autowired
    @Bean("postgresqlEntityManager")
    public EntityManager postgresqlEntityManager(@Qualifier("postgresqlEntityManagerFactory")
                                       LocalContainerEntityManagerFactoryBean factoryBean) {
        return factoryBean.getObject().createEntityManager();
    }

    @Autowired
    @Bean("postgresqlUserTransactionManager")
    public PlatformTransactionManager postgresqlUserTransactionManager(@Qualifier("postgresqlEntityManagerFactory")
                                                                       LocalContainerEntityManagerFactoryBean factoryBean) {
        return new JpaTransactionManager() {{
            setEntityManagerFactory(factoryBean.getObject());
        }};
    }

    @Autowired
    @Bean("postgresqlEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean postgresqlUserEntityManagerFactory(@Qualifier("postgresqlUserDataSource")
                                                                                     DataSource postgresqlUserDataSource) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter() {{
            setGenerateDdl(true);
        }};
        HashMap<String, Object> properties = new HashMap<>() {{
            put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
            put("hibernate.dialect", env.getProperty("application.database.postgresql.hibernate.dialect"));
        }};
        PersistenceProvider provider = new HibernatePersistenceProvider();
        return new LocalContainerEntityManagerFactoryBean() {{
            setDataSource(postgresqlUserDataSource);
            setPackagesToScan("com.acme.poc.springboot.backend.database.postgresql");
            setJpaVendorAdapter(vendorAdapter);
            setJpaPropertyMap(properties);
            setPersistenceProvider(provider);
        }};
    }

    @Bean("postgresqlUserDataSource")
    public DataSource postgresqlUserDataSource() {
        return new DriverManagerDataSource() {{
            setDriverClassName(env.getProperty("application.database.postgresql.driverClassName"));
            setUrl(env.getProperty("application.database.postgresql.url"));
            setUsername(env.getProperty("application.database.postgresql.username"));
            setPassword(env.getProperty("application.database.postgresql.password"));
        }};
    }

}
