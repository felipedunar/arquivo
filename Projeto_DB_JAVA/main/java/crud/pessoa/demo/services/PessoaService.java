package crud.pessoa.demo.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import crud.pessoa.demo.dto.PessoaDTO;
import crud.pessoa.demo.dto.ResponsePessoaDTO;

public interface PessoaService {

    public ResponsePessoaDTO create(PessoaDTO pessoaDTO);

    public Page<Object> findAll(Pageable paginacao);
    
    public ResponsePessoaDTO update(String cpf, PessoaDTO pessoaDTO);

    public void delete(String cpf); 
}
