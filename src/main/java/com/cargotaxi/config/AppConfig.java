package com.cargotaxi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

/**
 * Created by cdc89 on 10.06.2017.
 */
@Configuration
@PropertySource(value = "classpath:app.properties")
@ComponentScan(basePackages = "com.cargotaxi.mvc")
@EnableJpaRepositories(basePackages = "com.cargotaxi.mvc")
@EnableTransactionManagement
public class AppConfig {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Value("${pgsql.driverClassName}")
    private String driverClass;
    @Value("${pgsql.url}")
    private String jdbcUrl;
    @Value("${pgsql.username}")
    private String jdbcUserName;
    @Value("${pgsql.password}")
    private String jdbcPassword;

    @Bean(name = "dataSource")
    public DriverManagerDataSource getDriverManagerDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(jdbcUserName);
        dataSource.setPassword(jdbcPassword);
        return dataSource;
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean getLocalContainerEntityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setPackagesToScan(new String[]{"com.cargotaxi.mvc"});
        em.setDataSource(getDriverManagerDataSource());

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
//        em.setPersistenceProviderClass(HibernatePersistence.class);

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        jpaProperties.put("hibernate.show_sql", true);
        jpaProperties.put("hibernate.format_sql", "false");
        jpaProperties.put("hibernate.hbm2ddl.auto", "validate");//update validate

        em.setJpaProperties(jpaProperties);
        return em;
    }

    @Bean(name = "transactionManager")
    public JpaTransactionManager getJpaTransactionManager() {
        JpaTransactionManager jpa = new JpaTransactionManager();
//        jpa.setEntityManagerFactory(getLocalContainerEntityManagerFactoryBean().getNativeEntityManagerFactory());
        jpa.setEntityManagerFactory(getLocalContainerEntityManagerFactoryBean().getObject());
        return jpa;
    }
}
