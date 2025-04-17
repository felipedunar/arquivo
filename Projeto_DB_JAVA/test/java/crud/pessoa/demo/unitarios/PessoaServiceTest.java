package crud.pessoa.demo.unitarios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;

import java.util.Optional;
import crud.pessoa.demo.dto.PessoaDTO;
import crud.pessoa.demo.dto.ResponsePessoaDTO;
import crud.pessoa.demo.models.Pessoa;
import crud.pessoa.demo.repository.PessoaRepository;
import crud.pessoa.demo.services.PessoaServiceImpl;
import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@ExtendWith(MockitoExtension.class)
public class PessoaServiceTest {
    
    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private PessoaServiceImpl pessoaService;

    private Pessoa pessoa;

    private PessoaDTO pessoaDTO;

    private ResponsePessoaDTO responsePessoaDTO;

    @BeforeEach
    public void personSetup() {
        pessoa = new Pessoa("Aurora", LocalDate.of(2004,05,01), "37491502814");

        pessoaDTO = mock(PessoaDTO.class);

        responsePessoaDTO = mock(ResponsePessoaDTO.class);
    }

    @Test
    @DisplayName("Deve inserir pessoa no banco")
    void createPessoa() {
        when(pessoaRepository.findByCpf(anyString())).thenReturn(Optional.empty());
        when(pessoaRepository.save(ArgumentMatchers.any(Pessoa.class))).thenReturn(pessoa);
        when(pessoaDTO.cpf()).thenReturn("37491502814");

        responsePessoaDTO = pessoaService.create(pessoaDTO);

        assertNotNull(responsePessoaDTO);
    }

    @Test
    @DisplayName("Encontra uma pessoa no banco através do Id")
    void findById() {

        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoa)); 

        Pessoa pessoaEncontrada = pessoaService.findById(1L);

        assertNotNull(pessoaEncontrada);
    }

    @Test
    @DisplayName("Encontra uma pessoa no banco através do CPF")
    void findByCpf() {

        when(pessoaRepository.findByCpf(pessoa.getCpf())).thenReturn(Optional.of(pessoa)); 

        Optional<Pessoa> pessoaEncontrada = pessoaService.findByPessoaCpf(pessoa.getCpf());

        assertNotNull(pessoaEncontrada);
        assertEquals(pessoa.getCpf(), pessoaEncontrada.get().getCpf());
    }

    @Test
    @DisplayName("Deve permitir a atualização dos dados da pessoa")
    void updatePessoa() {
        when(pessoaRepository.findByCpf(pessoa.getCpf())).thenReturn(Optional.of(pessoa)); 
        when(pessoaRepository.save(pessoa)).thenReturn(pessoa);
        when(pessoaDTO.cpf()).thenReturn("37491502814");
        when(pessoaDTO.nome()).thenReturn("Laura");

        responsePessoaDTO = pessoaService.update(pessoa.getCpf(), pessoaDTO);

        assertNotNull(responsePessoaDTO);
    }

    @Test
    @DisplayName("Deve permitir a exclusão da pessoa")
    void deletePessoa() {
        when(pessoaRepository.findByCpf(pessoa.getCpf())).thenReturn(Optional.of(pessoa)); 

        pessoaService.delete(pessoa.getCpf());

        verify(pessoaRepository, times(1)).delete(pessoa);
    }

    @Test
    @DisplayName("Deve listar todos os registros")
    void findAll() {

        List<Pessoa> pessoas = new ArrayList<>();

        Page<Pessoa> pessoasPage = new PageImpl<>(pessoas);

        when(pessoaRepository.findAll(any(Pageable.class))).thenReturn(pessoasPage);

        Page<Object> pessoasEncontradas = pessoaService.findAll(Pageable.unpaged());

        assertTrue(pessoasEncontradas.isEmpty());
    }

    @Test
    @DisplayName("Deve retornar a idade corretamente")
    void calculaIdadeTest() {
        pessoaService.calculaIdade(pessoa);
        assertEquals(20, pessoa.getIdade()); 
    }

}
