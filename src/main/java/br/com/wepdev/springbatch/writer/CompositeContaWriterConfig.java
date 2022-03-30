package br.com.wepdev.springbatch.writer;

import br.com.wepdev.springbatch.dominio.Conta;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.support.builder.CompositeItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Classe de escrita composta, serão 2 escritores
 */
@Configuration
public class CompositeContaWriterConfig {


    /**
     * Metodo que escreve em 2 escritores, nesse caso e no banco de dados e em um arquivo
     * @return
     */
    @Bean
    public CompositeItemWriter<Conta> compositeContaWriter(
            // Foi necessario utilizar o Qualifier aqui pois existem 2 FlatFileItemWriter, esse aqui de conta Valida que escreve em banco e arquivo, e o que escreve as contas invalidas.
            @Qualifier("fileContaWriter") FlatFileItemWriter<Conta> flatFileItemWriter,
            JdbcBatchItemWriter<Conta> jdbcBatchItemWriter){

        return new CompositeItemWriterBuilder<Conta>()
                .delegates(flatFileItemWriter, jdbcBatchItemWriter) // escritores invocados de acordo com a ordem
                .build();

    }
}
