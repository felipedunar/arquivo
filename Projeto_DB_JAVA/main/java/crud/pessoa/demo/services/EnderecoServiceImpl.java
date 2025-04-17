package crud.pessoa.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import crud.pessoa.demo.dto.EnderecoAtualizaDTO;
import crud.pessoa.demo.dto.EnderecoDTO;
import crud.pessoa.demo.dto.ResponseEnderecoDTO;
import crud.pessoa.demo.exceptions.NotFoundEnderecoException;
import crud.pessoa.demo.exceptions.NotFoundPessoaException;
import crud.pessoa.demo.models.Endereco;
import crud.pessoa.demo.models.Pessoa;
import crud.pessoa.demo.mapper.EnderecoMapper;
import crud.pessoa.demo.repository.EnderecoRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class EnderecoServiceImpl implements EnderecoService {

    private Logger logger = Logger.getLogger(EnderecoServiceImpl.class.getName());

    @Autowired
    private EnderecoRepository repository;
 
    @Autowired
    private PessoaServiceImpl pessoaService;


    @Override
    @Transactional
    public ResponseEnderecoDTO create(EnderecoDTO enderecoDTO, String cpf){
        logger.info("Inserção de um endereço");

        Endereco endereco = EnderecoMapper.INSTANCE.dtoToEndereco(enderecoDTO);

        Optional<Pessoa> pessoa = pessoaService.findByPessoaCpf(cpf);
        
        if(pessoa.isEmpty()){
            throw new NotFoundPessoaException("Pessoa não encontrada");
            
        }
        
        endereco.setCpf_pessoa(pessoa.get());

        repository.save(endereco);

        ResponseEnderecoDTO responseEnderecoDTO = EnderecoMapper.INSTANCE.enderecoToDTO(endereco);

        return responseEnderecoDTO;
    }


    @Override
    @Transactional
    public ResponseEnderecoDTO update(EnderecoAtualizaDTO enderecoDTO, String cpf, String numero, String cep ) {
        
        Endereco enderecoBuscado =  repository.findByEndereco(cpf, numero, cep);
        
        if(enderecoBuscado == null){
            throw new NotFoundEnderecoException("Endereço não encontrado");      
        }

        Endereco enderecoNovo = EnderecoMapper.INSTANCE.dtoAtualizaToEndereco(enderecoDTO);
        

        enderecoBuscado.setRua(enderecoNovo.getRua());
        enderecoBuscado.setNumero(enderecoNovo.getNumero());
        enderecoBuscado.setBairro(enderecoNovo.getBairro());
        enderecoBuscado.setCidade(enderecoNovo.getCidade());
        enderecoBuscado.setEstado(enderecoNovo.getEstado());
        enderecoBuscado.setCep(enderecoNovo.getCep());
        enderecoBuscado.setPrincipal(enderecoNovo.isPrincipal());

        repository.save(enderecoBuscado);

        ResponseEnderecoDTO responseEnderecoDTO = EnderecoMapper.INSTANCE.enderecoToDTO(enderecoBuscado);

        return responseEnderecoDTO;
    } 

    
    @Override
    @Transactional
    public void delete(String cpf, String numero, String cep) {

        Endereco endereco =  repository.findByEndereco(cpf, numero, cep);

        if (endereco == null) {
            throw new NotFoundEnderecoException("Endereço não encontrado ");
        }

        repository.delete(endereco);
    }
}