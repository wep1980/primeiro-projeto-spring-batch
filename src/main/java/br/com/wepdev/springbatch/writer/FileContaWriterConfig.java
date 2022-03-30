package br.com.wepdev.springbatch.writer;

import br.com.wepdev.springbatch.dominio.Conta;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class FileContaWriterConfig {


    /**
     * Nesse metodo o recurso nao esta sendo passado como parametro de metodo, ele e colocado direto no metodo
     * @return
     */
    @Bean
    public FlatFileItemWriter<Conta> fileContaWriter(){
        return new FlatFileItemWriterBuilder<Conta>()
                .name("fileContaWriter")
                .resource(new FileSystemResource("./files/contas.txt")) // caminho onde o arquivo de escrita se encontra
                .delimited() // Arquivo do tipo delimitado
                .names("tipo", "limite", "clienteId")
                .build();
    }


}
