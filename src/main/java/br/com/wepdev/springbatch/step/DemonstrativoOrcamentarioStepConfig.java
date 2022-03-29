package br.com.wepdev.springbatch.step;

import br.com.wepdev.springbatch.dominio.GrupoLancamento;
import br.com.wepdev.springbatch.reader.GrupoLancamentoReader;
import br.com.wepdev.springbatch.writer.DemonstrativoOrcamentarioRodape;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.MultiResourceItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemonstrativoOrcamentarioStepConfig {


	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	
	@Bean
	public Step demonstrativoOrcamentarioStep(
			GrupoLancamentoReader demonstrativoOrcamentarioReader,
			MultiResourceItemWriter<GrupoLancamento> demonstrativoOrcamentarioWriter, // Configurando para o multiResource
			DemonstrativoOrcamentarioRodape rodapeCallback) {
		return stepBuilderFactory
				.get("demonstrativoOrcamentarioStep")
				.<GrupoLancamento,GrupoLancamento>chunk(1) // Tamanho do chunk = 100, 100 itens serão lidos ate o final do chunk. O tamanho do chunk foi diminuido para manter consistente a escrita em multiplos arquivos
				.reader(demonstrativoOrcamentarioReader)
				.writer(demonstrativoOrcamentarioWriter)
				.listener(rodapeCallback) // Esse metodo fica escutando o evento do totalGeral, que esta no metodo writeFooter(), esse componente esta registrado no Spring.
				.build();
	}
}
