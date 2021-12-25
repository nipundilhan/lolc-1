package com.fusionx.lending.transaction.config;

import com.fusionx.lending.transaction.mt.DataSourceMultiTenantConnectionProvider;
import com.fusionx.lending.transaction.mt.MultiTenantDataSources;
import com.fusionx.lending.transaction.mt.MultiTenantProperties;
import com.fusionx.lending.transaction.mt.TenantIdentifierResolver;
import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.dialect.Oracle12cDialect;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.SpringBeanContainer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Map;

@Configuration
@EnableConfigurationProperties({
        MultiTenantProperties.class, JpaProperties.class, HibernateProperties.class
})
@EnableJpaRepositories(
        basePackages = "com.fusionx.lending.transaction",
        entityManagerFactoryRef = "defaultEntityManager",
        transactionManagerRef = "defaultTransactionManager")
public class JpaConfig {

    private ConfigurableListableBeanFactory beanFactory;

    private HibernateProperties hibernateProperties;

    private JpaProperties jpaProperties;

    private MultiTenantProperties multiTenantProperties;


    public JpaConfig(JpaProperties jpaProperties,
                     HibernateProperties hibernateProperties,
                     MultiTenantProperties multiTenantProperties,
                     ConfigurableListableBeanFactory beanFactory) {
        this.jpaProperties = jpaProperties;
        this.hibernateProperties = hibernateProperties;
        this.multiTenantProperties = multiTenantProperties;
        this.beanFactory = beanFactory;
    }


    @Bean
    public MultiTenantDataSources multiTenantDataSources() {

        System.out.println("1 multiTenantDataSources  " + multiTenantProperties.getDefaultTenantId());

        MultiTenantDataSources multiTenantDataSources = new MultiTenantDataSources(multiTenantProperties.getDefaultTenantId());
        multiTenantProperties.getDataSources().forEach(
                ds -> multiTenantDataSources.add(ds.getTenantId(), ds.initializeDataSourceBuilder().build()));

        return multiTenantDataSources;
    }


    @Bean
    public MultiTenantConnectionProvider multiTenantConnectionProvider(MultiTenantDataSources multiTenantDataSources) {
        System.out.println("1 multiTenantConnectionProvider  ");
        return new DataSourceMultiTenantConnectionProvider(multiTenantDataSources);
    }


    @Bean
    public CurrentTenantIdentifierResolver currentTenantIdentifierResolver() {
        System.out.println("1 CurrentTenantIdentifierResolver  " + multiTenantProperties.getDefaultTenantId());
        return new TenantIdentifierResolver();
    }


    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean defaultEntityManager(
            MultiTenantConnectionProvider multiTenantConnectionProvider,
            CurrentTenantIdentifierResolver currentTenantIdentifierResolver) {

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setPersistenceUnitName("default");
        em.setPackagesToScan("com.fusionx.lending.transaction");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Map<String, Object> properties = hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
        properties.put(AvailableSettings.BEAN_CONTAINER, new SpringBeanContainer(beanFactory));

        properties.put(AvailableSettings.MULTI_TENANT, MultiTenancyStrategy.DATABASE);
        properties.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
        properties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolver);


        if (multiTenantProperties.getDataSources().get(0).getDriverClassName().equalsIgnoreCase("oracle.jdbc.driver.OracleDriver"))
            properties.put(AvailableSettings.DIALECT, Oracle12cDialect.class.getName());
        else
            properties.put(AvailableSettings.DIALECT, MySQL5Dialect.class.getName());


        em.setJpaPropertyMap(properties);

        return em;
    }


    @Primary
    @Bean
    public PlatformTransactionManager defaultTransactionManager(LocalContainerEntityManagerFactoryBean defaultEntityManager) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(defaultEntityManager.getObject());
        return transactionManager;
    }


}

