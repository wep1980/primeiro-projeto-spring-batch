package br.com.wepdev.springbatch.processor;

import br.com.wepdev.springbatch.dominio.Conta;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.support.builder.ClassifierCompositeItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Classe de configuração do classificador de conta
 */
@Configuration
public class ClassifierContaWriterConfig {


    /**
     * Metodo escritor de classificador de contas.
     *
     * Qualificador diferencia o escritor que sera utilizado de acordo com o tipo de conta, se conta valida ou invalida
     */
    @Bean
    public ClassifierCompositeItemWriter<Conta> classifierContaWriter(
            @Qualifier("clienteInvalidoWriter")FlatFileItemWriter<Conta> clienteInvalidoWriter,
            CompositeItemWriter<Conta> clienteValidoWriter){

        return new ClassifierCompositeItemWriterBuilder<Conta>()
                .classifier(classifier(clienteInvalidoWriter, clienteValidoWriter)) // Escolhe qual escritor sera aplicado dependendo da classificação que o tipo de conta se enquadrar
                .build();
    }

    private Classifier<Conta, ItemWriter<? super Conta>> classifier(FlatFileItemWriter<Conta> clienteInvalidoWriter, CompositeItemWriter<Conta> clienteValidoWriter) {

        return new Classifier<Conta, ItemWriter<? super Conta>>() {

            @Override
            public ItemWriter<? super Conta> classify(Conta conta) {

                if(conta.getTipo() != null) // Testando se a conta e valida ou nao
                    return clienteValidoWriter;
                else
                    return clienteInvalidoWriter;
            }
        };
    }
}
