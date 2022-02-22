package br.com.wepdev.springbatch.reader;

import br.com.wepdev.springbatch.dominio.Cliente;
import br.com.wepdev.springbatch.dominio.Transacao;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Classe que mapeia Objetos, no caso sera Cliente e Transacao
 */
@Configuration
public class ClienteTransacaoLineMapperConfig {


    /**
     * PatternMatchingCompositeLineMapper -> implementação do LineMapper Spring. Descobre o padrão.
     * PatternMatchingCompositeLineMapper -> Esse componente usa um padrão para descobrir qual lineMapper sera aplicado
     * PatternMatchingCompositeLineMapper -> Essa implementacao nao esta sendo tipada pois depende do tipo que estiver sendo lido na hora da execução
     */
    @Bean
    public PatternMatchingCompositeLineMapper lineMapper(){
        PatternMatchingCompositeLineMapper lineMapper = new PatternMatchingCompositeLineMapper();
        lineMapper.setTokenizers(tokenizers()); // Pega a linha e divide em palavras
        lineMapper.setFieldSetMappers(fieldSetMappers()); // Pega as palavras e mapeia pra um objeto de dominio
        return lineMapper;
    }

    private Map<String, FieldSetMapper> fieldSetMappers() {
        Map<String, FieldSetMapper> fieldSetMapper = new HashMap<>();
        fieldSetMapper.put("0*", fieldSetMapper(Cliente.class));
        fieldSetMapper.put("1*", fieldSetMapper(Transacao.class));
        return fieldSetMapper;
    }

    private FieldSetMapper fieldSetMapper(Class classe) {
        BeanWrapperFieldSetMapper fieldSetMapper = new BeanWrapperFieldSetMapper();
        fieldSetMapper.setTargetType(classe);
        return fieldSetMapper;
    }

    private Map<String, LineTokenizer> tokenizers() {
        Map<String, LineTokenizer> tokenizers = new HashMap<>();
        // Toda linha que começar com 0 seguido de qq coisa, significa que e uma linha de cliente
        tokenizers.put("0*", clienteLineTokenizer());
        // Toda linha que começar com 1 seguido de qq coisa, significa que e uma linha de transação
        tokenizers.put("1*", transacaoLineTokenizer());
        return tokenizers;
    }

    private LineTokenizer clienteLineTokenizer() {
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer(); // Arquivo do tipo delimitado
        lineTokenizer.setNames("nome", "sobrenome", "idade", "email"); // Campos do Cliente
        lineTokenizer.setIncludedFields(1, 2, 3, 4); // O campo 0 nao é mapeado pois ele indica o tipo da linha
        return lineTokenizer;
    }

    private LineTokenizer transacaoLineTokenizer() {
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer(); // Arquivo do tipo delimitado
        lineTokenizer.setNames("id", "descricao", "valor"); // Campos da Transacao
        lineTokenizer.setIncludedFields(1, 2, 3); // O campo 0 nao é mapeado pois ele indica o tipo da linha
        return lineTokenizer;
    }
}
