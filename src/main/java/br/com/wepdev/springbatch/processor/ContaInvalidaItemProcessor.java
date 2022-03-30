package br.com.wepdev.springbatch.processor;

import br.com.wepdev.springbatch.dominio.Cliente;
import br.com.wepdev.springbatch.dominio.Conta;
import org.springframework.batch.item.ItemProcessor;

/**
 * Classe que processa o TipoConta invalida
 */
public class ContaInvalidaItemProcessor implements ItemProcessor<Cliente, Conta> {


    /**
     * Metodo processador de contas invalidas.
     *
     * Para cliente sem faixa salarial somente o email sera mapeado.
     */
    @Override
    public Conta process(Cliente cliente) throws Exception {
        Conta conta = new Conta();
        conta.setClienteId(cliente.getEmail());
        return conta;
    }
}
