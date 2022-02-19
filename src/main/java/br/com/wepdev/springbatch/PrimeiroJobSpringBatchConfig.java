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

//    @Bean // Metodo colocado no contexto do Spring
//    public Job imprimeOlaJog(){
//        return jobBuilderFactory.get("imprimeOlaJog")
//                .start(imprimeOlaStep())
//                .incrementer(new RunIdIncrementer()) // Adiciona um novo ID a cada execução para cada vez que esse Job for executado
//                .build();
//    }

    @Bean // Metodo colocado no contexto do Spring
    public Job imprimeParImparJob(){
        return jobBuilderFactory.get("imprimeOlaJog").start(imprimeParImparStep())
                .incrementer(new RunIdIncrementer()) // Adiciona um novo ID a cada execução para cada vez que esse Job for executado
                .build();
    }


//    private Step imprimeOlaStep() {
//        return stepBuilderFactory.get("imprimeOlaStep")
//                .tasklet(imprimeOlaTasklet(null)) // Obtendo o nome dos parametros de execucao do Job
//                .build();
//    }

    private Step imprimeParImparStep() {
        return stepBuilderFactory
                .get("imprimeOlaStep")
                .<Integer, String>chunk(1)  // Le um Integer e escreve uma String
                .reader(contaAteDezReader())
                .processor(paraOuImparProcessor())
                .writer(imprimeWriter())
                .build();
    }

    /**
     * Metodo que conta de 1 a 10
     * ItemWriter é uma interface e para implementar essa interface sera usado o IteratorItemReader
     * @return
     */
    private IteratorItemReader<Integer> contaAteDezReader() {
        List<Integer> numerosDeUmAteDez = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        return new IteratorItemReader<Integer>(numerosDeUmAteDez.iterator());
    }

    /**
     * Metodo que verifica se o numero e par ou impar
     * ItemProcessor e uma interface, e para implementar essa interface sera usado o FunctionItemProcessor.
     * @return
     */
    private FunctionItemProcessor<Integer, String> paraOuImparProcessor() { // recebe do leitor um Integer e devolve para o escritor uma String
        return new FunctionItemProcessor<Integer, String>
                (item ->  item % 2 == 0 ? String.format("Item %s é Par", item) : String.format("Item %s é Impar", item));
    }

    private ItemWriter<String> imprimeWriter(){
       return itens -> itens.forEach(System.out::println);
    }

//    @Bean
//    @StepScope // Metodo adicionado no contexto(escopo) de Step
//    public Tasklet imprimeOlaTasklet(@Value("#{jobParameters['nome']}") String nome) { // Obtendo o nome dos parametros de execucao do Job
//        return new Tasklet() {
//            @Override
//            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
//                System.out.println(String.format("Olá, %s!", nome));
//                return RepeatStatus.FINISHED; // termina a execução
//            }
//        };
//    }

}
