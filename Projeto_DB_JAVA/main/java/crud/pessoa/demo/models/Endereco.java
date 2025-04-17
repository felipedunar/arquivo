package crud.pessoa.demo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Endereco")
public class Endereco {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 80)
	private String rua;

	@Column(nullable = false, length = 5)
	private String numero;
	
	@Column(nullable = false, length = 30)
	private String bairro;

    @Column(nullable = false, length = 30)
	private String cidade;

    @Column(nullable = false, length = 30)
	private String estado;

	@Column(nullable = false, length = 8) 
	private String cep;

	@Getter 
	@Setter
	@ManyToOne
	@JsonBackReference
	@JsonIgnore
	@JoinColumn(name = "cpf_pessoa", referencedColumnName = "cpf")
	private Pessoa cpf_pessoa;

	@Column(nullable = true)
	private boolean principal;
	
	
	public Endereco(){}

	public Endereco(String rua, String numero, String bairro, String cidade, String estado, String cep, Pessoa cpf_pessoa,
			boolean principal) {
		this.rua = rua;
		this.numero = numero;
		this.bairro = bairro;
		this.cidade = cidade;
		this.estado = estado;
		this.cep = cep;
		this.cpf_pessoa = cpf_pessoa;
		this.principal = principal;
	}

	public Long getId(){
		return this.id;
	}

	public String getRua(){
		return this.rua;
	}

	public void setRua(String rua){
		this.rua = rua;
	}

	public String getNumero(){
		return this.numero;
	}

	public void setNumero(String numero){
		this.numero = numero;
	}

	public String getBairro(){
		return this.bairro;
	}

	public void setBairro(String bairro){
		this.bairro = bairro;
	}

	public String getCidade(){
		return this.cidade;
	}

	public void setCidade(String cidade){
		this.cidade = cidade;
	}

	public String getEstado(){
		return estado;
	}

	public void setEstado(String estado){
		this.estado = estado;
	}

	public String getCep(){
		return cep;
	}

	public void setCep(String cep){
		this.cep = cep;
	}

	public boolean isPrincipal() {
		return principal;
	}

	public void setPrincipal(boolean principal) {
		this.principal = principal;
	}

	@Override
    public String toString() {
        return "Endereço { " +
                " ID= " + this.getId() +
				" Rua= " + this.getRua() +
				" Numero= " + this.getNumero() +
				" Bairro= " + this.getBairro() +
				" Cidade= " + this.getCidade() +
				" Estado= " + this.getEstado() +
				" CEP= " + this.getCep() +
				"CPF Pessoa" + this.getCpf_pessoa()+
                '}';
    }

}
