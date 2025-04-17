package crud.pessoa.demo.unitarios;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import crud.pessoa.demo.dto.EnderecoAtualizaDTO;
import crud.pessoa.demo.dto.EnderecoDTO;
import crud.pessoa.demo.dto.ResponseEnderecoDTO;
import crud.pessoa.demo.exceptions.NotFoundEnderecoException;
import crud.pessoa.demo.exceptions.NotFoundPessoaException;
import crud.pessoa.demo.models.Endereco;
import crud.pessoa.demo.models.Pessoa;
import crud.pessoa.demo.repository.EnderecoRepository;
import crud.pessoa.demo.services.EnderecoServiceImpl;
import crud.pessoa.demo.services.PessoaServiceImpl;
import java.time.LocalDate;


@ExtendWith(MockitoExtension.class)
public class EnderecoServiceTest {
    
    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private PessoaServiceImpl pessoaService;

    @InjectMocks
    private EnderecoServiceImpl enderecoService;

    private EnderecoDTO enderecoDTO;

    private EnderecoAtualizaDTO enderecoAtualizaDTO;

    private ResponseEnderecoDTO responseEnderecoDTO;
    
    private Pessoa pessoa;

    private Endereco endereco;

    Endereco enderecoBuscadoNull;

    @BeforeEach
    void setup(){

        pessoa = new Pessoa("Aurora", LocalDate.of(2004,05,14), "37491502814");

        endereco = new Endereco("Rua das dores", "10", "Bairro Jardins", "Cidade São Paulo", "Estado São Paulo", "36011233",pessoa, true);

        enderecoDTO = mock(EnderecoDTO.class);

        enderecoAtualizaDTO = mock(EnderecoAtualizaDTO.class);

        responseEnderecoDTO = mock(ResponseEnderecoDTO.class);

    }


    @Test
    @DisplayName("Deve inserir um endereço no banco")
    void create() {

        when(pessoaService.findByPessoaCpf(anyString())).thenReturn(Optional.of(pessoa));
        when(enderecoRepository.save(any(Endereco.class))).thenReturn(endereco);

        responseEnderecoDTO = enderecoService.create(enderecoDTO, pessoa.getCpf());

        assertNotNull(responseEnderecoDTO);
    }

    @Test
    @DisplayName("Tenta inserir um endereço, mas vai cair na exceção")
    void createEnderecoException() {

        when(pessoaService.findByPessoaCpf(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundPessoaException.class, () -> enderecoService.create(enderecoDTO, pessoa.getCpf()));
    }

    
    @Test
    @DisplayName("Deve permitir a atualização dos dados do endereço")
    void update() {
        when(enderecoRepository.findByEndereco(anyString(), anyString(), anyString())).thenReturn(endereco);
        when(enderecoRepository.save(any(Endereco.class))).thenReturn(endereco);

        responseEnderecoDTO = enderecoService.update(enderecoAtualizaDTO, pessoa.getCpf(), endereco.getNumero(), endereco.getCep());

        assertNotNull(responseEnderecoDTO);
    }

    @Test
    @DisplayName("Tenta atualizar um endereço, mas vai cair na exceção")
    void updateEnderecoException() {

        when(enderecoRepository.findByEndereco(anyString(), anyString(), anyString())).thenReturn(enderecoBuscadoNull);
        
        assertThrows(NotFoundEnderecoException.class, () -> {
            
            enderecoBuscadoNull = enderecoRepository.findByEndereco(pessoa.getCpf(), endereco.getNumero(), endereco.getCep());

            if (enderecoBuscadoNull != null) {
                enderecoService.update(enderecoAtualizaDTO,pessoa.getCpf(), endereco.getNumero(), endereco.getCep());
            } else {
                throw new NotFoundEnderecoException("Endereço não encontrado");
            }
        }); 
    }

    @Test
    @DisplayName("Deve permitir a exclusão do endereço")
    void delete() {
        enderecoRepository.delete(endereco);

        verify(enderecoRepository, times(1)).delete(endereco);
    }

    @Test
    @DisplayName("Tenta excluir um endereço, mas vai cair na exceção")
    void deleteEnderecoException() {
        when(enderecoRepository.findByEndereco(anyString(), anyString(), anyString())).thenReturn(enderecoBuscadoNull);
        
        assertThrows(NotFoundEnderecoException.class, () -> {
            
            enderecoBuscadoNull = enderecoRepository.findByEndereco(pessoa.getCpf(), endereco.getNumero(), endereco.getCep());

            if (enderecoBuscadoNull == null) {
                throw new NotFoundEnderecoException("Endereço não encontrado");
            } else {
                enderecoRepository.delete(enderecoBuscadoNull);
            }
        });
    }
}
