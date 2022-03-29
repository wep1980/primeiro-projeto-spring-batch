package br.com.wepdev.springbatch.writer;

import br.com.wepdev.springbatch.dominio.GrupoLancamento;
import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeWrite;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;
import java.text.NumberFormat;
import java.util.List;

@Component
public class DemonstrativoOrcamentarioRodape implements FlatFileFooterCallback {

    // Para computar o total e necessario somar os itens que estao sendo inscritos
    private Double totalGeral = 0.0;

    /**
     * 	\t faz as tabulações para deixar o conteúdo alinhado na geração do arquivo
     * @param writer
     * @throws IOException
     */
    @Override
    public void writeFooter(Writer writer) throws IOException {
        writer.append("\n");
        writer.append(String.format("\t\t\t\t\t\t\t  Total: %s\n", NumberFormat.getCurrencyInstance().format(totalGeral)));
        writer.append(String.format("\t\t\t\t\t\t\t  Código de autenticação: %s\n", "fkyew6868fewjfhjjewf"));

    }

    /**
     * Na mudança para multiplos arquivos gerados, esse metodo passou a calcular de forma errada, entao foi necessario fazer o metodo abaixo afeterChunk()
     * Capturar o evento que o spring lança antes de acorrer a escrita, para fazer algum tipo de processamento.
     * Esse metodo precisa ser registrado no configurador de Step
     */
    @BeforeWrite
    public void beforeWrite(List<GrupoLancamento> grupos){
         for (GrupoLancamento grupoLancamento : grupos){
             totalGeral += grupoLancamento.getTotal();
         }
    }

    /**
     * Metodo que zera o totalGeral a cada chunk executado, se o tamanho do chunk e 10, esse metodo sera executado 10 vezes.
     * O tamanho do chunk e definido no step.
     * @param context
     */
    @AfterChunk // Apos o chunk esse metodo sera executado
    public void afeterChunk(ChunkContext context){
        totalGeral = 0.0;

    }
}
