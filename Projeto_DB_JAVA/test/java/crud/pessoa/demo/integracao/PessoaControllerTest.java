package crud.pessoa.demo.integracao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import crud.pessoa.demo.dto.PessoaDTO;
import crud.pessoa.demo.exceptions.NotFoundPessoaException;
import crud.pessoa.demo.fixture.PessoaDTOFixture;
import crud.pessoa.demo.fixture.SqlProvider;
import crud.pessoa.demo.models.Pessoa;
import crud.pessoa.demo.repository.PessoaRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class PessoaControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    private PessoaDTO dto;

    private String json;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    PessoaRepository pessoaRepository;


    @Test
    @DisplayName("Deve permitir cadastrar uma pessoa")
    void cadastrarPessoaTest() throws Exception {

        dto = PessoaDTOFixture.PessoaDTOValido();

        json = mapper.writeValueAsString(dto);

        mockMvc.perform(MockMvcRequestBuilders.post("/pessoa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value(dto.nome()));
    }


    @Test
    @DisplayName("Deve permitir exibir as pessoas cadastradas")
    @SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = SqlProvider.insertPessoa),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = SqlProvider.clearDB)
    })
    void exibirPessoasTest() throws Exception{
        dto = PessoaDTOFixture.PessoaDTOValido();

        mockMvc.perform(MockMvcRequestBuilders
            .get("/pessoa"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].nome").value("João da Silva"))
            .andExpect(jsonPath("$.content[0].enderecos").isArray());
    }
    

    @Test
    @DisplayName("Deve permitir atualizar uma pessoa")
    @SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = SqlProvider.insertPessoa),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = SqlProvider.clearDB)
    })
    void atualizarPessoaTest() throws Exception{
        String cpf = "30143518062";

        dto = PessoaDTOFixture.PessoaDTOValido();

        json = mapper.writeValueAsString(dto);

        mockMvc
            .perform(MockMvcRequestBuilders.put("/pessoa/" + cpf)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isOk());

        Pessoa pessoa = pessoaRepository.findByCpf(dto.cpf()).orElseThrow(() -> new NotFoundPessoaException("Pessoa com CPF " + cpf + " não foi encontrada após atualização."));
        assertEquals("João", pessoa.getNome());

    }

    
    @Test
    @DisplayName("Deve permitir deletar uma pessoa do banco")
    @SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = SqlProvider.insertPessoa),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = SqlProvider.clearDB)
    })
    void deletarPessoaTest() throws Exception {
        String cpf = "30143518062";

        mockMvc
            .perform(MockMvcRequestBuilders.delete("/pessoa/" + cpf))
            .andExpect(status().isNoContent());
    }

}

