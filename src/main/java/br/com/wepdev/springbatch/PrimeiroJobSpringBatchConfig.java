package br.com.wepdev.springbatch;

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
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
@Configuration
public class PrimeiroJobSpringBatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

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
    public Job imprimeOlaJog(){
        return jobBuilderFactory.get("imprimeOlaJog")
                .start(imprimeOlaStep())
                .incrementer(new RunIdIncrementer()) // Adiciona um novo ID a cada execução para cada vez que esse Job for executado
                .build();
    }


    private Step imprimeOlaStep() {
        return stepBuilderFactory.get("imprimeOlaStep")
                .tasklet(imprimeOlaTasklet(null)) // Obtendo o nome dos parametros de execucao do Job
                .build();
    }

    @Bean
    @StepScope // Metodo adicionado no contexto(escopo) de Step
    public Tasklet imprimeOlaTasklet(@Value("#{jobParameters['nome']}") String nome) { // Obtendo o nome dos parametros de execucao do Job
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                System.out.println(String.format("Olá, %s!", nome));
                return RepeatStatus.FINISHED;
            }
        };
    }

}
