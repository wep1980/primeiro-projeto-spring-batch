package br.com.wepdev.springbatch.reader;

import br.com.wepdev.springbatch.dominio.Cliente;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

/**
 * A diferença entre uma consulta por Cursor e uma Paginada e que o cursor salva os resultados em memoria, se o resultado for muito grande,
 * a memoria pode ser estourada
 */
@Configuration
public class JdbcPagingReaderReaderConfig {


	/**
	 * Componente Spring batch que faz consulta paginada
	 * @return
	 */
	@Bean
	public JdbcPagingItemReader<Cliente> jdbcPagingReader(@Qualifier("appDataSource")DataSource dataSource, PagingQueryProvider queryProvider) {
		return new JdbcPagingItemReaderBuilder<Cliente>()
				.name("jdbcPagingReader")
				.dataSource(dataSource) // Configura a consulta
				.queryProvider(queryProvider) // Query feita para consultas paginadas
				.pageSize(1) // Tamanho da pagina
				.rowMapper(new BeanPropertyRowMapper<Cliente>(Cliente.class)) // Responsavel por mapear o resultado da consulta em um objeto de dominio
				.build();


	}

	/**
	 * Metodo com a consulta SQL por paginacao
	 * @param dataSource
	 * @return
	 */
	@Bean
    public SqlPagingQueryProviderFactoryBean queryProvider(@Qualifier("appDataSource") DataSource dataSource){
		SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
		queryProvider.setDataSource(dataSource); // Setando o banco de dados
		queryProvider.setSelectClause("select *"); // Consulta SQL
		queryProvider.setFromClause("from cliente");
		queryProvider.setSortKey("email"); // Ordenação feita para chave primaria. O spring precisa ordenar os resultados para garantir que em caso de reinicialização e para gerenciamento das paginas, sempre traga os resultados na mesma ordem
		//queryProvider.setSortKeys(); // no caso de chave primaria composta
		return queryProvider;
	}
}
