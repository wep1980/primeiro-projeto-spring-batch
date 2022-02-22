package br.com.wepdev.springbatch.reader;

import br.com.wepdev.springbatch.dominio.Cliente;
import br.com.wepdev.springbatch.dominio.Transacao;
import org.springframework.batch.item.*;

/**
 * Classe de leitor customizado para inserir as transações no Cliente
 */
public class ArquivoClienteTransacaoReader implements ItemStreamReader<Cliente> {

    private Object objetoAtual; // Objeto que guarda o registro atual que esta sendo verificado
    private ItemStreamReader<Object> delegate;

    /**
     * Construtor que inicializa o delegate
     * @param delegate
     */
    public ArquivoClienteTransacaoReader(ItemStreamReader<Object> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Cliente read() throws Exception {
        if(objetoAtual == null) // O Objeto ainda nao foi lido
            objetoAtual = delegate.read(); // Ler o objeto. Delegar a logica de leitura para o objeto que ja existe
            Cliente cliente = (Cliente) objetoAtual; // Fazendo um Cast de objectAtual para Cliente
            objetoAtual = null; // e colocado null para que seja lido novamente por outro metodo

            if(cliente != null){ // Se tem clientes para serem lidos
                while (peek() instanceof Transacao) // Se o resultado for uma instancia de transacao
                    cliente.getTransacoes().add((Transacao) objetoAtual); // essa transacao e adicionada no cliente
        }
        return cliente;
    }

    /**
     * Metodo que verifica se é transacao
     * @return
     */
    private Object peek() throws Exception {
        objetoAtual = delegate.read(); // leitura do proximo item
        return objetoAtual;
    }


    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        delegate.open(executionContext);
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        delegate.update(executionContext);

    }

    @Override
    public void close() throws ItemStreamException {
        delegate.close();

    }
}
