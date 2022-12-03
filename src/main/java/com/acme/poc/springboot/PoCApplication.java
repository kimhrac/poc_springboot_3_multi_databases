package com.acme.poc.springboot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication(scanBasePackages = { "com.acme.poc.springboot" }, exclude = { DataSourceAutoConfiguration.class })
@EntityScan(basePackages = {
//        "com.acme.poc.springboot.backend.database.h2.entity",
//        "com.acme.poc.springboot.backend.database.mariadb.entity",
//        "com.acme.poc.springboot.backend.database.mysql.entity",
        "com.acme.poc.springboot.backend.database.postgresql.entity"
//        "com.acme.poc.springboot.backend.database.sqlite.entity"
})
@EnableJpaRepositories(basePackages = {
//        "com.acme.poc.springboot.backend.database.h2.repository",
//        "com.acme.poc.springboot.backend.database.mariadb.repository",
//        "com.acme.poc.springboot.backend.database.mysql.repository",
        "com.acme.poc.springboot.backend.database.postgresql.repository"
//        "com.acme.poc.springboot.backend.database.sqlite.repository"
})
@EnableJpaAuditing
@EnableTransactionManagement
public class PoCApplication {

    @Value("${server.port}")
    private int SERVER_PORT;

    private final static String RABBITMQ_EXCHANGE_NAME = "acme.request";
    private final static String RABBITMQ_ROUTING_KEY = "service.springboot";


    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(PoCApplication.class, args);
    }

}
