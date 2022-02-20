package br.com.wepdev.springbatch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.function.FunctionItemProcessor;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@EnableBatchProcessing
@Configuration
public class PrimeiroJobSpringBatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    /**
     * Um Job e definido por uma sequencia encadeada de Steps, e cada Step tem sua propria logica, e as logicas podem ser do tipo
     * Tasklet ou Chunk.
     * Tasklet => para pequenas tarefas
     * Chunk => utilizado para processamentos complexos, precisam ser realizados em pedaços, que sao eles :
     * ItemReader -> Leitura
     * ItemProcessor -> Processamento
     * ItemWriter -> Escrita
     * Chunk => cada chunk possui sua propria transação
     */


    @Bean // Metodo colocado no contexto do Spring
    public Job imprimeOlaJog(Step imprimeOlaStep){
        return jobBuilderFactory
                .get("imprimeOlaJog")
                .start(imprimeOlaStep)
                .build();
    }


}
