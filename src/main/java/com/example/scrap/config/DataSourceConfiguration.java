/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.scrap.config;

/**
 *
 * @author KALAM
 */

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.example.scrap.repository",
        entityManagerFactoryRef = "scrapEntityManagerFactory",
        transactionManagerRef = "scrapTransactionManager"
)
@PropertySource("classpath:database.properties")
//@PropertySource("file:////home/core/scrap/database.properties")
public class DataSourceConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.scrap")
    public DataSourceProperties scrapDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.scrap.configuration")
    public DataSource scrapDataSource() {
        return scrapDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "scrapEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean scrapEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(scrapDataSource())
                .packages(new String[]{"com.example.scrap.model"})
                .build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager scrapTransactionManager(
            final @Qualifier("scrapEntityManagerFactory") LocalContainerEntityManagerFactoryBean scrapEntityManagerFactory) {
        return new JpaTransactionManager(scrapEntityManagerFactory.getObject());
    }

}
