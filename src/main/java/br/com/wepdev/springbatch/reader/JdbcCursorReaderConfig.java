package br.com.wepdev.springbatch.reader;

import br.com.wepdev.springbatch.dominio.Cliente;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;


@Configuration
public class JdbcCursorReaderConfig {


	@Bean
	public JdbcCursorItemReader<Cliente> jdbcCursorReader(@Qualifier("appDataSource")DataSource dataSource) {
		return new JdbcCursorItemReaderBuilder<Cliente>()
				.name("jdbcCursorReader")
				.dataSource(dataSource) // Informando o banco de dados no qual sera trazido os dados, que esta configurado no pacote config e busca as informações no application.properties
				.sql("select * from cliente") // consulta SQL
				.rowMapper(new BeanPropertyRowMapper<Cliente>(Cliente.class)) // Mapeia o resultado da consulta SQL em objeto do tipo Cliente, as campos da tabelas precisam ser igual ao da classe de dominio Cliente
				.build();
	}
}
