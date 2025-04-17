package crud.pessoa.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import crud.pessoa.demo.dto.PessoaDTO;
import crud.pessoa.demo.dto.ResponsePessoaDTO;
import crud.pessoa.demo.services.PessoaServiceImpl;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController 
@RequestMapping("/pessoa") 
public class PessoaController {

    @Autowired
    private PessoaServiceImpl pessoaService;


    @PostMapping 
    public ResponseEntity<ResponsePessoaDTO> cadatrar(@RequestBody @Valid PessoaDTO pessoaDTO){ 

        ResponsePessoaDTO result = pessoaService.create(pessoaDTO); 

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
        
    }

    
    @GetMapping()
    public Page<Object> exibirTodos(Pageable paginacao) {
		return pessoaService.findAll(paginacao);
	}


    @PutMapping("/{cpf}")
    public ResponseEntity<ResponsePessoaDTO> atualizar(@PathVariable String cpf, @RequestBody PessoaDTO pessoaDTO) {
        
        ResponsePessoaDTO result = pessoaService.update(cpf, pessoaDTO); 

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    @DeleteMapping("/{cpf}")
	public ResponseEntity<Void> deletar(@PathVariable String cpf) {

		pessoaService.delete(cpf);
        
		return ResponseEntity.noContent().build();
	}
}

