package crud.pessoa.demo.fixture;

import java.time.LocalDate;

import crud.pessoa.demo.dto.PessoaDTO;

public class PessoaDTOFixture {
    
    public static PessoaDTO PessoaDTOValido() {
        return new PessoaDTO("João", LocalDate.of(1990, 5, 20), "37491502814");
    }

    public static PessoaDTO PessoaDTOInvalido() {
        return new PessoaDTO("", LocalDate.of(1990, 5, 20), "37491502814");
    }

}