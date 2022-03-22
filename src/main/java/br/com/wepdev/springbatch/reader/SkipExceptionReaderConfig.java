package br.com.wepdev.springbatch.reader;

import javax.sql.DataSource;

import br.com.wepdev.springbatch.dominio.Cliente;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Ignora itens invalidos na leitura, que nao compromete o processamento de todos os dados
 */
@Configuration
public class SkipExceptionReaderConfig {
	@Bean
	public ItemReader<Cliente> skipExceptionReader(@Qualifier("appDataSource") DataSource dataSource) {
		return new JdbcCursorItemReaderBuilder<Cliente>()
				.name("skipExceptionReader")
				.dataSource(dataSource)
				.sql("select * from cliente")
				//.beanRowMapper(Cliente.class)
				.rowMapper(rowMapper()) // Aqui e forçado acontecer um erro
				.build();
	}

	private RowMapper<Cliente> rowMapper() {
		return new RowMapper<Cliente>() {
			@Override
			public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
				if(rs.getRow() == 11) // Verificando se o ultimo registro a ser lido, se for lança a exception abaixo
				//if(rs.getRow() <= 11) // Gera falha em todos os itens
				throw new SQLException(String.format("Encerrando a execução - Cliente inválido %s", rs.getString("email")));
				else return clienteRowMapper(rs);
			}

			/**
			 * Classe auxiliar que mapeia o resultado do SQL para um objeto de dominio
			 * @param rs
			 * @return
			 * @throws SQLException
			 */
			private Cliente clienteRowMapper(ResultSet rs) throws SQLException {
				Cliente cliente = new Cliente();
				cliente.setNome(rs.getString("nome"));
				cliente.setSobrenome(rs.getString("sobrenome"));
				cliente.setIdade(rs.getString("idade"));
				cliente.setEmail(rs.getString("email"));
				return cliente;
			}
		};
	}
}
