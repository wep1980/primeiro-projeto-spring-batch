package br.com.wepdev.springbatch.processor;

import br.com.wepdev.springbatch.dominio.Cliente;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.builder.ScriptItemProcessorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessadorScriptProcessorConfig {
	@Bean
	public ItemProcessor<Cliente, Cliente> processadorScriptProcessor() {

		return new ScriptItemProcessorBuilder<Cliente, Cliente>()
				.language("nashorn") // Linguagem na qual o script vai ser lido, existe uma lista de linguagens suportada
				.scriptSource(
						"var email = item.getEmail();" // Verifica o email do cliente
								/*
								comando shell que filtra os arquivos que tiverem o nome igual de email -- ESSE COMANDO SHELL VAI RODAR NA HOME, COMO NAO EXISTE NEHUM ARQUIVO COM ESSE NOME, NINGUEM SERA FILTRADO
								E TODOS OS ITENS SERAO LISTADOS
								 */
								//+ "var arquivoExiste = `ls | grep ${email}.txt`;"
						        +  "var arquivoExiste =$EXEC('cmd /C \"dir /b | findstr /R \"'+email+'\"\\\"');"
								+ "if (!arquivoExiste) item; else null;" // Se o arquivo existir o item sera filtrado e retornara nulo, senao retorna o item normalmente
				).build();
	}
}
