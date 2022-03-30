package br.com.wepdev.springbatch.writer;

import br.com.wepdev.springbatch.dominio.Conta;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

/**
 * Classe escritora do tipo de conta Invalida
 */
@Configuration
public class ClienteInvalidoWriterConfig {


    /**
     * Metodo escritor do tipo de conta invalida
     * @return
     */
    @Bean
    public FlatFileItemWriter<Conta> clienteInvalidoWriter(){
        return new FlatFileItemWriterBuilder<Conta>()
                .name("clienteInvalidoWriter")
                .resource(new FileSystemResource("./files/clientesInvalidos.txt")) // caminho onde o arquivo de escrita se encontra
                .delimited() // Arquivo do tipo delimitado
                .names("clienteId") // propriedade mapeada
                .build();
    }
}
