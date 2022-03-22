package br.com.wepdev.springbatch.step;

import br.com.wepdev.springbatch.dominio.Cliente;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SkipExceptionStepConfig {
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step skipExceptionStep(ItemReader<Cliente> skipExceptionReader, ItemWriter<Cliente> skipExceptionWriter) {
		return stepBuilderFactory
				.get("skipExceptionStep")
				.<Cliente, Cliente>chunk(11)
				.reader(skipExceptionReader)
				.writer(skipExceptionWriter)
				.faultTolerant()// Deixando tolerante a falhas
				.skip(Exception.class) // Informando a exeception que sera tolerada
				.skipLimit(2)// Somente 2 registros invalidos serao tolerados
				.build();
	}
}
