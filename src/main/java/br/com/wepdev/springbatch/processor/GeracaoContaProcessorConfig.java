package br.com.wepdev.springbatch.processor;

import br.com.wepdev.springbatch.dominio.Cliente;
import br.com.wepdev.springbatch.dominio.Conta;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.builder.ClassifierCompositeItemProcessorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class GeracaoContaProcessorConfig {
	@Bean
	public ItemProcessor<Cliente, Conta> geracaoContaProcessor() {
		return new ClassifierCompositeItemProcessorBuilder<Cliente, Conta>()
				.classifier(new GeracaoContaClassifier())
				.build();
	}
}
