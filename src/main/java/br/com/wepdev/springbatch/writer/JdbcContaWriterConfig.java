package br.com.wepdev.springbatch.writer;

import br.com.wepdev.springbatch.dominio.Conta;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Configuration
public class JdbcContaWriterConfig {


	/**
	 * Metodo que escreve no banco de dados. JdbcBatchItemWriter -> Realiza a escrita em lotes(todos os registros em uma unica transação), ao inves de ficar abrinado uma trasação para cada registro
	 * @return
	 */
	@Bean
	public JdbcBatchItemWriter<Conta> jdbcBatchItemWriter(@Qualifier("appDataSource")DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<Conta>()
				.dataSource(dataSource) // Banco de dados no qual sera gravado os dados
				.sql("INSERT INTO conta (tipo, limite, cliente_id) VALUES (?, ?, ?)") // Sql que sera executado
				.itemPreparedStatementSetter(itemPreparedStatementSetter()) // Inserindo os parametros
				.build();
	}

	/**
	 * ItemPreparedStatementSetter -> interface que precisa ser implementada
	 * @return
	 */
	private ItemPreparedStatementSetter<Conta> itemPreparedStatementSetter() {

		return new ItemPreparedStatementSetter<Conta>() {
			@Override
			public void setValues(Conta conta, PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setString(1, conta.getTipo().name());
				preparedStatement.setDouble(2, conta.getLimite());
				preparedStatement.setString(3, conta.getClienteId()); // O id do cliente e o email

			}
		};

	}
}
