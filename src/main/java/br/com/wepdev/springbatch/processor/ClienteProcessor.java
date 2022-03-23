package br.com.wepdev.springbatch.processor;

import br.com.wepdev.springbatch.dominio.Cliente;
import org.springframework.batch.item.ItemProcessor;

/**
 * Classe que contem as regras de negocio relacionada ao Cliente.
 * Processador customizado.
 */
public class ClienteProcessor implements ItemProcessor<Cliente, Cliente> {

    /**
     * Metodo que contem a regra de negocio relacionada ao cliente
     * @param cliente
     * @return
     * @throws Exception
     */
    @Override
    public Cliente process(Cliente cliente) throws Exception {
        System.out.println(String.format("\nAplicando regras de negocio no cliente %s", cliente.getEmail()));
        return cliente;
    }
}
