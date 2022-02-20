package br.com.wepdev.springbatch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

/**
 * Classe de configuracao para acesso dos campos do application.properties que estao armazenados em um diretorio do PC
 */
@Configuration
public class PropsConfig {

    @Bean
    public PropertySourcesPlaceholderConfigurer config(){
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setLocation(new FileSystemResource("/Users/Waldir/Desktop/cursospringbatch/application.properties"));
        return configurer;
    }
}
