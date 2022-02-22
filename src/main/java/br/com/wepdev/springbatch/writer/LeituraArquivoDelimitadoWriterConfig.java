package br.com.wepdev.springbatch.writer;

import br.com.wepdev.springbatch.dominio.Cliente;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class LeituraArquivoDelimitadoWriterConfig {
	@Bean
	public ItemWriter<Cliente> leituraArquivoDelimitadoWriter() {
		return items -> items.forEach(System.out::println);
	}
}
