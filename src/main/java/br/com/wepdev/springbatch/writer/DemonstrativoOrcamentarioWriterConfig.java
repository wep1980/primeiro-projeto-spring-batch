package br.com.wepdev.springbatch.writer;

import java.io.IOException;
import java.io.Writer;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.wepdev.springbatch.dominio.GrupoLancamento;
import br.com.wepdev.springbatch.dominio.Lancamento;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.MultiResourceItemWriter;
import org.springframework.batch.item.file.ResourceSuffixCreator;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.builder.MultiResourceItemWriterBuilder;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class DemonstrativoOrcamentarioWriterConfig {


	/**
	 * Metodo para escrever em multiplos arquivos
	 * @param demonstrativosOrcamentarios
	 * @param demonstrativoOrcamentarioWriter
	 * @return
	 */
	@StepScope
	@Bean
	public MultiResourceItemWriter<GrupoLancamento> multiDemonstrativoOrcamentarioWriter(@Value("#{jobParameters['demonstrativosOrcamentarios']}") Resource demonstrativosOrcamentarios,
																						 FlatFileItemWriter<GrupoLancamento> demonstrativoOrcamentarioWriter){
		return new MultiResourceItemWriterBuilder<GrupoLancamento>()
				.name("multiDemonstrativoOrcamentarioWriter") // Nome do componente(nome do metodo)
				.resource(demonstrativosOrcamentarios) // onde sera escrito
				.delegate(demonstrativoOrcamentarioWriter) // o escritor, que é o responsavel pela logica da escrita. Como ele esta registrado como Bean e possivel passa lo por parametro no metodo
				.resourceSuffixCreator(SuffixCreator()) // Adicnando um sufixo do arquivo. no caso o .txt
				// a cada grupo lancamento sera criado 1 arquivo. esse limite de registros por recurso so checado depois que o chunk e escrito. Como o tamanho do chunk e 100, é necessario coloca lo com tamnho 1
				.itemCountLimitPerResource(1)
				.build();
	}

	private ResourceSuffixCreator SuffixCreator() {
		return new ResourceSuffixCreator() {
			@Override
			public String getSuffix(int index) {
				return index + ".txt";
			}
		};
	}


	@StepScope
	@Bean
	public FlatFileItemWriter<GrupoLancamento> demonstrativoOrcamentarioWriter(@Value("#{jobParameters['demonstrativoOrcamentario']}")Resource demonstrativoOrcamentario,
																			   DemonstrativoOrcamentarioRodape rodapeCallback){
		return new FlatFileItemWriterBuilder<GrupoLancamento>()
				.name("demonstrativoOrcamentarioWriter")
				.resource(demonstrativoOrcamentario)
				.lineAggregator(lineAggregator()) // Tipo de formatação customizada, logica de agregação da linha
				.headerCallback(cabecalhoCallback()) // Cabeçalho
				.footerCallback(rodapeCallback) // Rodape
				.build();
	}

	/*
	Metodo do cabeçalho que sera adicionado na escrita
	 */
	private FlatFileHeaderCallback cabecalhoCallback() {
		return new FlatFileHeaderCallback(){

			@Override
			public void writeHeader(Writer writer) throws IOException {
			    writer.append(String.format("SISTEMA INTEGRADO: XPTO \t\t\t\t DATA: %s\n", new SimpleDateFormat("dd/MM/yyyy").format(new Date())));
				writer.append(String.format("MÓDULO: ORÇAMENTO \t\t\t\t\t\t HORA: %s\n", new SimpleDateFormat("HH:MM").format(new Date())));
				writer.append(String.format("\t\t\tDEMONSTRATIVO ORCAMENTARIO\n"));
				writer.append(String.format("----------------------------------------------------------------------------\n"));
				writer.append(String.format("CODIGO NOME VALOR\n"));
				writer.append(String.format("\t Data Descricao Valor\n"));
				writer.append(String.format("----------------------------------------------------------------------------\n"));
			}
		};
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
