package crud.pessoa.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import crud.pessoa.demo.dto.EnderecoAtualizaDTO;
import crud.pessoa.demo.dto.EnderecoDTO;
import crud.pessoa.demo.dto.ResponseEnderecoDTO;
import crud.pessoa.demo.services.EnderecoServiceImpl;
import jakarta.validation.Valid;



public class EnderecoController {
    
    
    private EnderecoServiceImpl enderecoService;


 
    public ResponseEntity<ResponseEnderecoDTO> cadatrar(enderecoDTO){ 

        ResponseEnderecoDTO result = enderecoService.create(enderecoDTO, enderecoDTO.cpf_pessoa()); 

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
        
    }

    ("/{cpf}/{numero}/{cep}")
    public ResponseEntity<ResponseEnderecoDTO> atualizar(String cpf,String numero,String cep,EnderecoAtualizaDTO enderecoDTO) {
    
        ResponseEnderecoDTO result = enderecoService.update(enderecoDTO, cpf, numero, cep); 

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    ("/{cpf}/{numero}/{cep}")
	public ResponseEntity<Void> deletar( String cpf,String numero, String cep) {

		enderecoService.delete(cpf, numero, cep);
        
		return ResponseEntity.noContent().build();
	}

}
