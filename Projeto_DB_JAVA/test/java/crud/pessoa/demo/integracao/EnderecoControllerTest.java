package crud.pessoa.demo.integracao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import crud.pessoa.demo.dto.EnderecoAtualizaDTO;
import crud.pessoa.demo.dto.EnderecoDTO;
import crud.pessoa.demo.fixture.*;
import crud.pessoa.demo.repository.EnderecoRepository;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class EnderecoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private EnderecoDTO dto;

    private EnderecoAtualizaDTO dtoUpdate;

    private String json;

    @Autowired
    ObjectMapper mapper;
    
    @Autowired
    EnderecoRepository enderecoRepository;

    
    @Test
    @DisplayName("Deve ser possível criar um endereço")
    @SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = SqlProvider.insertPessoa),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = SqlProvider.clearDB)
    })
    void cadatrarEnderecoTest() throws Exception{
        
        dto = EnderecoDTOFixture.EnderecoDTOValido();

        json = mapper.writeValueAsString(dto);

        mockMvc.perform(MockMvcRequestBuilders.post("/endereco")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.rua").value(dto.rua()));
    
    }

    @Test
    @DisplayName("Deve ser possivel atualizar o endereço")
    @SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = SqlProvider.insertPessoa),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = SqlProvider.insertEndereco),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = SqlProvider.clearDB)
    })
    void atualizarEnderecoTest() throws Exception{ 

        dtoUpdate = EnderecoAtualizaDTOFixture.EnderecoAtualizaDTOValido();

        json = mapper.writeValueAsString(dtoUpdate);

        String cpf = "85626848053";
        String numero = "789";
        String cep = "34567890";

        mockMvc
            .perform(MockMvcRequestBuilders.put("/endereco/"+cpf+"/"+numero+"/"+cep)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isOk());

    }
    
    
    @Test
    @DisplayName("Deve ser possivel deletar o endereço")
    @SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = SqlProvider.insertPessoa),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = SqlProvider.insertEndereco),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = SqlProvider.clearDB)
    })
    void deletarEnderecoTest() throws Exception{

        String cpf = "85626848053";
        String numero = "789";
        String cep = "34567890";

        mockMvc
            .perform(MockMvcRequestBuilders.delete("/endereco/"+cpf+"/"+numero+"/"+cep))
            .andExpect(status().isNoContent());
    }
}
