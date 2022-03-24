package br.com.wepdev.springbatch.writer;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.wepdev.springbatch.dominio.GrupoLancamento;
import br.com.wepdev.springbatch.dominio.Lancamento;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;


/**
 * SISTEMA INTEGRADO: XPTO 				 DATA: 23/03/2022
 * MÓDULO: ORÇAMENTO 					 HORA: 18:03
 * 			DEMONSTRATIVO ORCAMENTARIO
 * ----------------------------------------------------------------------------
 * CODIGO NOME VALOR
 * 	 Data Descricao Valor
 * ----------------------------------------------------------------------------
 * [44905224] EQUIPAMENTO DE PROTEÇÃO SEGURANÇA E SOCORRO - R$ 1.000,00
 * 	 [01/05/2020] Alarme - R$ 1.000,00
 * [44905287] MATERIAL DE CONSUMO DE USO DURADOURO - R$ 2.000,00
 * 	 [03/05/2020] Bandeiras - R$ 500,00
 * 	 [04/05/2020] Guarda Sol - R$ 500,00
 * 	 [02/05/2020] Cortina de sala - R$ 1.000,00
 * [33903016] MATERIAL DE EXPEDIENTE - R$ 4.000,00
 * 	 [10/05/2020] Cartucho Impressora - R$ 2.000,00
 * 	 [01/05/2020] Resma de Papel A4 - R$ 2.000,00
 * [44905218] COLEÇÕES E MATERIAIS BIBLIOGRÁFICOS - R$ 8.000,00
 * 	 [11/05/2020] Livro de Algoritmos - R$ 4.000,00
 * 	 [12/05/2020] Dicionários - R$ 4.000,00
 * [33903024] MATERIAL P/ MANUT. DE BENS IMÓVEIS/INSTALAÇÕES - R$ 16.000,00
 * 	 [11/05/2020] Aparelhos Sanitários - R$ 6.000,00
 * 	 [12/05/2020] Cimento - R$ 2.000,00
 * 	 [13/05/2020] Amianto - R$ 8.000,00
 * [33903302] PASSAGENS PARA O EXTERIOR - R$ 32.000,00
 * 	 [01/05/2020] Viagem para Las Vegas - R$ 32.000,00
 */
@Configuration
public class DemonstrativoOrcamentarioWriterConfig {


//	@Bean
//	public ItemWriter<GrupoLancamento> demonstrativoOrcamentarioWriter() {
//		return itens -> {
//			System.out.println("\n");
//			System.out.println(String.format("SISTEMA INTEGRADO: XPTO \t\t\t\t DATA: %s", new SimpleDateFormat("dd/MM/yyyy").format(new Date())));
//			System.out.println(String.format("MÓDULO: ORÇAMENTO \t\t\t\t\t HORA: %s", new SimpleDateFormat("HH:MM").format(new Date())));
//			System.out.println(String.format("\t\t\tDEMONSTRATIVO ORCAMENTARIO"));
//			System.out.println(String.format("----------------------------------------------------------------------------"));
//			System.out.println(String.format("CODIGO NOME VALOR"));
//			System.out.println(String.format("\t Data Descricao Valor"));
//			System.out.println(String.format("----------------------------------------------------------------------------"));
//
//			Double totalGeral = 0.0;
//			for (GrupoLancamento grupo : itens) {
//				System.out.println(String.format("[%d] %s - %s", grupo.getCodigoNaturezaDespesa(),
//						grupo.getDescricaoNaturezaDespesa(),
//						NumberFormat.getCurrencyInstance().format(grupo.getTotal())));
//				totalGeral += grupo.getTotal();
//				for (Lancamento lancamento : grupo.getLancamentos()) {
//					System.out.println(String.format("\t [%s] %s - %s", new SimpleDateFormat("dd/MM/yyyy").format(lancamento.getData()), lancamento.getDescricao(),
//							NumberFormat.getCurrencyInstance().format(lancamento.getValor())));
//				}
//			}
//			System.out.println("\n");
//			System.out.println(String.format("\t\t\t\t\t\t\t  Total: %s", NumberFormat.getCurrencyInstance().format(totalGeral)));
//			System.out.println(String.format("\t\t\t\t\t\t\t  Código de Autenticação: %s", "fkyew6868fewjfhjjewf"));
//		};
//	}
    @StepScope
	@Bean
	public FlatFileItemWriter<GrupoLancamento> demonstrativoOrcamentarioWriter(@Value("#{jobParameters['demonstrativoOrcamentario']}")Resource demonstrativoOrcamentario){
		return new FlatFileItemWriterBuilder<GrupoLancamento>()
				.name("demonstrativoOrcamentarioWriter")
				.resource(demonstrativoOrcamentario)
				.lineAggregator(lineAggregator()) // Tipo de formatação customizada, logica de agregação da linha
				.build();
	}

	/**
	 * Metodo agregador de linha
	 * @return
	 */
	private LineAggregator<GrupoLancamento> lineAggregator() {
		return new LineAggregator<GrupoLancamento>() {
			@Override
			public String aggregate(GrupoLancamento grupoLancamento) {

				String formatoGrupoLancamento = String.format("[%d] %s - %s\n", grupoLancamento.getCodigoNaturezaDespesa(),
						grupoLancamento.getDescricaoNaturezaDespesa(),
						NumberFormat.getCurrencyInstance().format(grupoLancamento.getTotal()));

				StringBuilder stringBuilder = new StringBuilder();

				for (Lancamento lancamento : grupoLancamento.getLancamentos()) {
					stringBuilder.append(String.format("\t [%s] %s - %s\n", new SimpleDateFormat("dd/MM/yyyy").format(lancamento.getData()), lancamento.getDescricao(),
							NumberFormat.getCurrencyInstance().format(lancamento.getValor())));
				}
				String formatLancamento = stringBuilder.toString();
				return formatoGrupoLancamento + formatLancamento;
			}
		};
	}


}
