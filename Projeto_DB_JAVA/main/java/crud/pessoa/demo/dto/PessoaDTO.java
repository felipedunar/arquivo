package crud.pessoa.demo.dto;

import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PessoaDTO(

    @NotBlank(message = "O nome não pode estar vazio") 
    String nome,

    @NotNull(message = "A data de nascimento não pode estar vazia") 
    LocalDate nascimento, 

    @CPF 
    String cpf

){}
