package de.hsosnabrueck.bodengefuegeapp;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        //dataSourceBuilder.driverClassName("com.mysql.jdbc.Driver");
        dataSourceBuilder.url("jdbc:mysql://131.173.124.199:3306/bodengefuege");
        dataSourceBuilder.username("user");
        dataSourceBuilder.password("bodengefuege");
        return dataSourceBuilder.build();
    }
}
