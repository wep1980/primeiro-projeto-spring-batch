package br.com.wepdev.springbatch.processor;

import br.com.wepdev.springbatch.dominio.Transacao;
import org.springframework.batch.item.ItemProcessor;

/**
 * Classe que contem as regras de negocio relacionada a Trasação.
 * Processador customizado.
 */
public class TransacaoProcessor implements ItemProcessor<Transacao, Transacao> {


    @Override
    public Transacao process(Transacao transacao) throws Exception {
        System.out.println(String.format("\nAplicando regras de negocio na transação %s", transacao.getId()));
        return transacao;
    }
}
