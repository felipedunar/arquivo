package crud.pessoa.demo.services;

import java.util.Optional;
import java.time.Period;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import crud.pessoa.demo.dto.PessoaDTO;
import crud.pessoa.demo.dto.ResponsePessoaDTO;
import crud.pessoa.demo.exceptions.FindPessoaException;
import crud.pessoa.demo.exceptions.NotFoundPessoaException;
import crud.pessoa.demo.mapper.PessoaMapper;
import crud.pessoa.demo.models.Pessoa;
import crud.pessoa.demo.repository.PessoaRepository;
import jakarta.transaction.Transactional;

@Service
public class PessoaServiceImpl implements PessoaService {

    private Logger logger = Logger.getLogger(PessoaServiceImpl.class.getName());

    @Autowired
    private PessoaRepository repository;
 

    @Override
    @Transactional 
    public ResponsePessoaDTO create(PessoaDTO pessoaDTO){
        logger.info("Inserção de uma pessoa");

        Pessoa pessoa = PessoaMapper.INSTANCE.dtoToPessoa(pessoaDTO);

        Optional<Pessoa> pessoaReturn = findByPessoaCpf(pessoa.getCpf());
        
        if(pessoaReturn.isPresent()){
            throw new FindPessoaException("Já existe uma pessoa cadastrada com esse CPF");
            
        }

        if (pessoa.getNascimento() != null) {
            calculaIdade(pessoa); // Calcular idade para cada pessoa
        }

        repository.save(pessoa);

        ResponsePessoaDTO responsePessoaDTO = PessoaMapper.INSTANCE.pessoaToDTO(pessoa);

        return responsePessoaDTO;
    }


    @Override
    public Page<Object> findAll(Pageable paginacao) {
        logger.info("Pessoas cadastradas");
		return repository.findAll(paginacao).map(pessoa -> {
            if (pessoa.getNascimento() != null) {
                calculaIdade(pessoa); // Calcular idade para cada pessoa
            }
            return new ResponsePessoaDTO(pessoa);
        });
	}


    @Override
    @Transactional
    public ResponsePessoaDTO update(String cpf, PessoaDTO pessoaDTO){
        logger.info("Atualização de uma pessoa");

        Optional<Pessoa> pessoaReturn = findByPessoaCpf(cpf);
        
        if(pessoaReturn.isEmpty()){
            throw new NotFoundPessoaException("Pessoa não encontrada");
            
        }

        Pessoa pessoa = PessoaMapper.INSTANCE.dtoToPessoa(pessoaDTO);

        Pessoa pessoaAntiga = pessoaReturn.get();

        pessoaAntiga.setNome(pessoa.getNome());
        pessoaAntiga.setNascimento(pessoa.getNascimento());
        pessoaAntiga.setCpf(pessoa.getCpf());
        
        repository.save(pessoaAntiga);

        if (pessoaAntiga.getNascimento() != null) {
            calculaIdade(pessoaAntiga); // Calcular idade para cada pessoa
        }

        ResponsePessoaDTO responsePessoaDTO = PessoaMapper.INSTANCE.pessoaToDTO(pessoaAntiga);

        return responsePessoaDTO;
    }


    @Override
    @Transactional
    public void delete(String cpf) {
        logger.info("Exclusão de uma pessoa");

        Optional<Pessoa> pessoaReturn = findByPessoaCpf(cpf);

        if (pessoaReturn.isEmpty()) {
            throw new NotFoundPessoaException("Pessoa não encontrada");
        }

        Pessoa pessoaParaExcluir = pessoaReturn.get();
        repository.delete(pessoaParaExcluir);
    }

    public void calculaIdade(Pessoa pessoa){     
        Period periodo = Period.between(pessoa.getNascimento(), pessoa.getAnoAtual());
        pessoa.setIdade(periodo.getYears());
    }

  
    public Optional<Pessoa> findByPessoaCpf(String cpf){
        return repository.findByCpf(cpf);
    }
    

    public Pessoa findById(Long id) {
		logger.info("Pessoa");
        return repository.findById(id).orElseThrow(() -> new NotFoundPessoaException("Pessoa não encontrada")); 
	}

}
