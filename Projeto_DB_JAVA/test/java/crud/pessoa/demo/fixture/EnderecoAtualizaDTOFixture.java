package crud.pessoa.demo.fixture;

import crud.pessoa.demo.dto.EnderecoAtualizaDTO;

public class EnderecoAtualizaDTOFixture {
    public static EnderecoAtualizaDTO EnderecoAtualizaDTOValido() {
        return new EnderecoAtualizaDTO("Rua A", "123", "Centro", "Cidade A", "Estado A", "12345678", true);
    }

    public static EnderecoAtualizaDTO EnderecoAtualizaDTOInvalido() {
        return new EnderecoAtualizaDTO("", "123", "Centro", "Cidade A", "Estado A", "12345678", true);
    }
}
