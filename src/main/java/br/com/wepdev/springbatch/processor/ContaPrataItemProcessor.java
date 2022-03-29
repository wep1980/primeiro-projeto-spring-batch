package br.com.wepdev.springbatch.processor;

import br.com.wepdev.springbatch.dominio.Cliente;
import br.com.wepdev.springbatch.dominio.Conta;
import br.com.wepdev.springbatch.dominio.TipoConta;
import org.springframework.batch.item.ItemProcessor;

public class ContaPrataItemProcessor implements ItemProcessor<Cliente, Conta> {

	@Override
	public Conta process(Cliente cliente) throws Exception {
		Conta conta = new Conta();
		conta.setClienteId(cliente.getEmail());
		conta.setTipo(TipoConta.PRATA);
		conta.setLimite(500.0);
		return conta;
	}

}
