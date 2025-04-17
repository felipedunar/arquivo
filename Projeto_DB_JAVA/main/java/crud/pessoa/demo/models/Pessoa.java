package crud.pessoa.demo.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;

@Entity
@Table(name = "Pessoa")
public class Pessoa {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 80)
	private String nome;

	@Column(nullable = true, length = 11)
	private LocalDate nascimento;
	
	@Column(nullable = false, length = 11, unique = true)
	private String cpf;

	@Getter 
	@OneToMany(mappedBy = "cpf_pessoa", cascade = CascadeType.ALL)
	@JsonIgnore
    @JsonManagedReference
	private List<Endereco> enderecos = new ArrayList<>();

	@Transient 
	private LocalDate anoAtual = LocalDate.now();

	@Transient
	private int idade;

	public Pessoa(){}; 

	public Pessoa(String nome, LocalDate nascimento, String cpf) {
		this.nome = nome;
		this.nascimento = nascimento;
		this.cpf = cpf;
	}

	public long getId(){
		return this.id;
	}

	public String getNome(){
		return this.nome;
	}

	public void setNome(String nome){
		this.nome = nome;
	}

	public LocalDate getNascimento(){
		return this.nascimento;
	}

	public void setNascimento(LocalDate nascimento){
		this.nascimento = nascimento;
	}

	public String getCpf(){
		return this.cpf;
	}

	public void setCpf(String cpf){
		this.cpf = cpf;
	}

	public LocalDate getAnoAtual(){
		return this.anoAtual;
	}

	public int getIdade(){
		return this.idade;
	}

	public void setIdade(int idade){
		this.idade = idade;
	}

}
