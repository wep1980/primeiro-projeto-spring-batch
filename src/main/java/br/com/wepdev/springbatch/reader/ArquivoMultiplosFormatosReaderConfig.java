package br.com.wepdev.springbatch.reader;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class ArquivoMultiplosFormatosReaderConfig {

	/**
	 * Sao lidos nesse metodo o tipo Cliente e Transacao, por isso o FlatFileItemReader nao é tipado
	 * @param arquivoClientes
	 * @param lineMapper
	 * @return
	 */
//	@StepScope
//	@Bean
//	public FlatFileItemReader arquivoMultiplosFormatosItemReader(@Value("#{jobParameters['arquivoClientes']}")Resource arquivoClientes, LineMapper lineMapper) {
//
//		return new FlatFileItemReaderBuilder()
//				.name("arquivoMultiplosFormatosItemReader")
//				.resource(arquivoClientes)
//				.lineMapper(lineMapper)
//				.build();
//	}

}
