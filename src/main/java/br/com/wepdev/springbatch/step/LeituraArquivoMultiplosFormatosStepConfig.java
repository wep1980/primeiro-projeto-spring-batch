package br.com.wepdev.springbatch.step;

import br.com.wepdev.springbatch.reader.ArquivoClienteTransacaoReader;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LeituraArquivoMultiplosFormatosStepConfig {
	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean
	public Step leituraArquivoMultiplosFormatosStep(FlatFileItemReader leituraArquivoMultiplosFormatosReader, ItemWriter leituraArquivoMultiplosFormatosItemWriter) {
		return stepBuilderFactory
				.get("leituraArquivoMultiplosFormatosStep")
				.chunk(1)
				.reader(new ArquivoClienteTransacaoReader(leituraArquivoMultiplosFormatosReader)) // Chamando a classe de leitura de arquivos customizada que le as transacoes de clientes, caso exista
				.writer(leituraArquivoMultiplosFormatosItemWriter)
				.build();
	}
}