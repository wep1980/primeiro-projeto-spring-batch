package br.com.wepdev.springbatch.processor;

import br.com.wepdev.springbatch.dominio.Cliente;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.support.builder.CompositeItemProcessorBuilder;
import org.springframework.batch.item.validator.BeanValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class ProcessadorValidacaoProcessorConfig {

    private Set<String> emails = new HashSet<>();

	/**
	 * Valida o dado, nao e feita alterações, depois disso passa o dado valido para o escritor
	 * @return
	 */
	@Bean
	public ItemProcessor<Cliente, Cliente> procesadorValidacaoProcessor() throws Exception {
        return new CompositeItemProcessorBuilder<Cliente, Cliente>() // Processador composto, Recebe Cliente e devolve Cliente
				.delegates(beanValidatingprocessor(), emailValidatingProcessor()) // esse delegates recebe um vetor de validadores, nesse caso esta fazendo 2 validações
				.build();
	}

	/**
	 * Metodo validador dos Bean Validators, sao os validadores das anotações que estao nos campos da Classe Cliente.
	 *
	 * OBS : Ao colocar esse metodo de validacao em um processamento composto o ciclo de vida dele e menor, então é necessario utilizar o afterPropertiesSet()
	 * @return
	 */
	private BeanValidatingItemProcessor<Cliente> beanValidatingprocessor() throws Exception {
		BeanValidatingItemProcessor<Cliente> processor = new BeanValidatingItemProcessor<>();
		processor.setFilter(true); // Filtra os itens invalidos para nao impedir a execucao do batch
		processor.afterPropertiesSet();
		return processor;
	}

	/**
	 * Metodo que valida se existe emails duplicados
	 * @return
	 */
	private ValidatingItemProcessor<Cliente> emailValidatingProcessor(){
		ValidatingItemProcessor<Cliente> processor = new ValidatingItemProcessor<>(); // Configuração para validacao mais robusta
		processor.setValidator(validador());
		processor.setFilter(true); // Filtra os itens invalidos para nao impedir a execucao do batch
		return processor;
	}

	/**
	 * Metodo de validação, que verifica se o arquivo tem itens duplicados, se existir email duplicado,
	 * uma mensagem e exibida, senao ele é adicionado
	 * @return
	 */
	private Validator<Cliente> validador() {
		return new Validator<Cliente>() {
			@Override
			public void validate(Cliente cliente) throws ValidationException {
				if(emails.contains(cliente.getEmail()))
					throw new ValidationException(String.format("******************  O Cliente %s ja foi processado  *****************", cliente.getEmail()));
                emails.add(cliente.getEmail());
			}
		};
	}
}
