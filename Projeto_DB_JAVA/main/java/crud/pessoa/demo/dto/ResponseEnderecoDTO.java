package crud.pessoa.demo.dto;

public record ResponseEnderecoDTO(

    String rua,

    String numero, 

    String bairro, 

    String cidade, 

    String estado, 

    String cep,

    boolean principal
    
) {}

