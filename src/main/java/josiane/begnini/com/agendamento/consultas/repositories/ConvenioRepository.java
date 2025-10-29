package josiane.begnini.com.agendamento.consultas.repositories;

import josiane.begnini.com.agendamento.consultas.models.Convenio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConvenioRepository extends JpaRepository<Convenio, Long> {
    Optional<Convenio> findByNome(String nome);
    boolean existsByNome(String nome);
}