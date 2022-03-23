package br.com.wepdev.springbatch.writer;

import br.com.wepdev.springbatch.dominio.Cliente;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class ArquivoDelimitadoWriterConfig {


	@StepScope
	@Bean
	public FlatFileItemWriter<Cliente> arquivoDelimitadoWriter(@Value("#{jobParameters['arquivoClientesSaida']}")Resource arquivoClientesSaida) {
		return new FlatFileItemWriterBuilder<Cliente>()
				.name("arquivoDelimitadoWriter")
				.resource(arquivoClientesSaida)
				.delimited() // Formato do arquivo de escrita
				.delimiter(";")// Tipo do delimitador
				.names("nome", "sobrenome", "idade", "email")
				.build();

	}
}
