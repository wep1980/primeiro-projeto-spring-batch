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
public class ArquivoLarguraFixaWriterConfig {
	// FIXME: Modificar para escrever em arquivo de largura fixa!


	/**
	 * Metodo responsavel por escrever aquivo flat formatado
	 * @return
	 */
	@StepScope
	@Bean
	public FlatFileItemWriter<Cliente> escritaArquivoLarguraFixaWriter(@Value("#{jobParameters['arquivoClienteSaida']}") Resource arquivoClientesSaida) {

		return new FlatFileItemWriterBuilder<Cliente>()
				.name("escritaArquivoLarguraFixaWriter")
				.resource(arquivoClientesSaida)
				.formatted() // Formato customizado do arquivo de saida
				.format("%-9s %-9s %-2s %-19s") // Arquivo de largura fixa informado atraves da expressao regular
				.names("nome", "sobrenome", "idade", "email") // Os nome de cada String que foi colocada acima
				.build();
	}
}
