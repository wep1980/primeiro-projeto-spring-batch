package br.com.wepdev.springbatch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * Classe de configuracao de multiplos banco de dados
 */
@Configuration
public class DataSourceConfig {

    @Primary // Banco de dados default
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource springDataSource(){
        return DataSourceBuilder.create().build();
    }

    /**
     * Buscando os campos configurados no appliocation.properties
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "app.datasource")
    public DataSource appDataSource(){
        return DataSourceBuilder.create().build();
    }
}
