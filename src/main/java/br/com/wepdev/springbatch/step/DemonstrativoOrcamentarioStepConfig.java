package br.com.wepdev.springbatch.step;

import br.com.wepdev.springbatch.dominio.GrupoLancamento;
import br.com.wepdev.springbatch.reader.GrupoLancamentoReader;
import br.com.wepdev.springbatch.writer.DemonstrativoOrcamentarioRodape;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemonstrativoOrcamentarioStepConfig {


	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	
	@Bean
	public Step demonstrativoOrcamentarioStep(GrupoLancamentoReader demonstrativoOrcamentarioReader,
			ItemWriter<GrupoLancamento> demonstrativoOrcamentarioWriter, DemonstrativoOrcamentarioRodape rodapeCallback) {
		return stepBuilderFactory
				.get("demonstrativoOrcamentarioStep")
				.<GrupoLancamento,GrupoLancamento>chunk(100)
				.reader(demonstrativoOrcamentarioReader)
				.writer(demonstrativoOrcamentarioWriter)
				.listener(rodapeCallback) // Esse metodo fica escutando o evento do totalGeral, que esta no metodo writeFooter(), esse componente esta registrado no Spring.
				.build();
	}
}
