package br.com.wepdev.springbatch.step;

import br.com.wepdev.springbatch.dominio.Cliente;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessadorValidacaoStepConfig {

	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step processadorValidacaoStep(ItemReader<Cliente> processadorValidacaoReader, ItemProcessor<Cliente, Cliente> processadorValidacaoProcessor,
			ItemWriter<Cliente> processadorValidacaoWriter) {

		return stepBuilderFactory
				.get("processadorValidacaoStep")
				.<Cliente, Cliente>chunk(1)
				.reader(processadorValidacaoReader)
				.processor(processadorValidacaoProcessor)
				.writer(processadorValidacaoWriter)
				.build();
	}
}
