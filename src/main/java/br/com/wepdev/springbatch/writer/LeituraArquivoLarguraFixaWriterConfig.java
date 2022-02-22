package br.com.wepdev.springbatch.writer;

import br.com.wepdev.springbatch.dominio.Cliente;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LeituraArquivoLarguraFixaWriterConfig {
	@Bean
	public ItemWriter<Cliente> leituraArquivoLarguraFixaWriter() {
		return items -> items.forEach(System.out::println);
//		return items -> { // Configuracao para dar erro no batch
//			for(Cliente cliente: items){
//				if(cliente.getNome().equals("Maria")){
//                   throw new Exception();
//				}else{
//					System.out.println(cliente);
//				}
//			}
//		};
	}
}
