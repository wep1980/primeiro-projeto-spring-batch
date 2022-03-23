package br.com.wepdev.springbatch.processor;

import br.com.wepdev.springbatch.dominio.Cliente;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.builder.ClassifierCompositeItemProcessorBuilder;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessadorClassifierProcessorConfig {


	/**
	 * Metodo que processa a regra de negocio dependendo do Objeto
	 * @return
	 */

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Bean
	public ItemProcessor processadorClassifierProcessor() {
		return new ClassifierCompositeItemProcessorBuilder<>()
				.classifier(classifier()) // escolhe o processador adequado
				.build();
	}

	@SuppressWarnings({"rawtypes"})
	private Classifier classifier() {
		return new Classifier<Object, ItemProcessor>() {
			@Override
			public ItemProcessor classify(Object objeto) {
				if(objeto instanceof Cliente)
					return new ClienteProcessor();
				else
					return new TransacaoProcessor();
			}
		};
	}


}
