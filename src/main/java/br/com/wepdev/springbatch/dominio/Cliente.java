package br.com.wepdev.springbatch.dominio;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Cliente {

	@NotNull
	@Size(min = 1, max = 100)
	@Pattern(regexp = "[a-zA-Z\\s]+", message = "Nome deve ser alfabético") // Valida se o nome esta formado por letras e se possui alguem espaço, e considerado valido
	private String nome;

	@NotNull
	@Range(min = 18, max = 200) // Faixa de idade permitida
	private Integer idade;

	@NotNull
	@Size(min = 1, max = 50)
	@Pattern(regexp = "^[A-za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "Email inválido") // Tipo valido
	private String email;


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "Cliente{" +
	                "nome='" + nome + "'" +
	                ", idade='" + idade + "'" +
	                ", email='" + email + "'" +
				    '}';
	}
}