package br.com.wepdev.springbatch.step;

import br.com.wepdev.springbatch.dominio.Cliente;
import br.com.wepdev.springbatch.dominio.Conta;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CriacaoContasStepConfig {
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step criacaoContasStep(
			ItemReader<Cliente> leituraClientesReader,
			ItemProcessor<Cliente, Conta> geracaoContaProcessor,
			CompositeItemWriter<Conta> compositeContaWriter) { // Utilizando o escritor composto
		return stepBuilderFactory
				.get("criacaoContasStep")
				.<Cliente, Conta>chunk(100)// Como o tamanho do chunk e 100, serao feitos 100 inserts em uma unica transacao. Se o tamanho do chunk for 1, sera realizado 1 insert por transação
				.reader(leituraClientesReader)
				.processor(geracaoContaProcessor)
				.writer(compositeContaWriter) // Utilizando o escritor composto
				.build();
	}
}
