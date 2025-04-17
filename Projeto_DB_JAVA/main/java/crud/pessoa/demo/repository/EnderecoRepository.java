package crud.pessoa.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import crud.pessoa.demo.models.Endereco;


@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    @Query("SELECT e FROM Endereco e WHERE e.cpf_pessoa.cpf = :cpf AND e.numero = :numero AND e.cep = :cep")
    Endereco findByEndereco(@Param("cpf") String cpf, @Param("numero") String numero, @Param("cep") String cep);
    
}