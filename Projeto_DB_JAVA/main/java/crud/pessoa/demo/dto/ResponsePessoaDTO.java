package crud.pessoa.demo.dto;

import java.time.LocalDate;
import java.util.List;

import crud.pessoa.demo.models.Endereco;
import crud.pessoa.demo.models.Pessoa;

public record ResponsePessoaDTO(

    String nome,

    LocalDate nascimento, 

    String cpf,

    int idade,

    List<Endereco> enderecos

) {
    public ResponsePessoaDTO(Pessoa pessoa){
        this(pessoa.getNome(),pessoa.getNascimento(), pessoa.getCpf(), pessoa.getIdade(), pessoa.getEnderecos());
    }
}


