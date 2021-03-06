package br.com.wepdev.springbatch.step;

import br.com.wepdev.springbatch.dominio.Cliente;
import br.com.wepdev.springbatch.dominio.Conta;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
			// Utilizando o classificador de Contas. O Classifier nao possui o metodo(streams) de abertura e fechamento de recursos na sua implementação, entao e necessario implementa los(registra los)
			ClassifierCompositeItemWriter<Conta> classifierContaWriter,
	        @Qualifier("fileContaWriter") FlatFileItemWriter<Conta> fileContaWriter, // Cliente valido
			@Qualifier("clienteInvalidoWriter") FlatFileItemWriter<Conta> clienteInvalidoWriter) { // Cliente invalido. Utilizando o classificador de contas

		return stepBuilderFactory
				.get("criacaoContasStep")
				.<Cliente, Conta>chunk(100)// Como o tamanho do chunk e 100, serao feitos 100 inserts em uma unica transacao. Se o tamanho do chunk for 1, sera realizado 1 insert por transação
				.reader(leituraClientesReader)
				.processor(geracaoContaProcessor)
				.writer(classifierContaWriter) // Utilizando o escritor composto
				.stream(fileContaWriter)// registrando os streams desse Step - configurando a abertura e fechamento do recurso.
				.stream(clienteInvalidoWriter)// registrando os streams desse Step - configurando a abertura e fechamento do recurso.
				.build();
	}
}
