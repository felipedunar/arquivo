package crud.pessoa.demo.services;

import crud.pessoa.demo.dto.EnderecoAtualizaDTO;
import crud.pessoa.demo.dto.EnderecoDTO;
import crud.pessoa.demo.dto.ResponseEnderecoDTO;

public interface EnderecoService {

    public ResponseEnderecoDTO create(EnderecoDTO enderecoDTO, String cpf);

    public ResponseEnderecoDTO update(EnderecoAtualizaDTO enderecoDTO, String cpf, String numero, String cep);
    
    public void delete(String cpf, String numero, String cep);
    
}
