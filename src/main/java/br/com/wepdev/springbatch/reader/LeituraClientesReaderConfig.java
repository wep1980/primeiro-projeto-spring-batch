package br.com.wepdev.springbatch.reader;

import javax.sql.DataSource;

import br.com.wepdev.springbatch.dominio.Cliente;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

@Configuration
public class LeituraClientesReaderConfig {
	@Bean
	public JdbcPagingItemReader<Cliente> leituraClientesReader(
			@Qualifier("appDataSource") DataSource dataSource,
			PagingQueryProvider queryProvider
			) {
		return new JdbcPagingItemReaderBuilder<Cliente>()
				.name("leituraClientesReader")
				.dataSource(dataSource)
				.queryProvider(queryProvider)
				.rowMapper(new BeanPropertyRowMapper<Cliente>(Cliente.class))
				.build();
	}

	@Bean
	public SqlPagingQueryProviderFactoryBean queryProvider(@Qualifier("appDataSource") DataSource dataSource) {
		SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
		queryProvider.setDataSource(dataSource);
		queryProvider.setSelectClause("select *");
		queryProvider.setFromClause("from cliente");
		queryProvider.setSortKey("email");
		return queryProvider;
	}
}
