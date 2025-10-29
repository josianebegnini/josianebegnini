package josiane.begnini.com.agendamento.consultas.repositories;

import josiane.begnini.com.agendamento.consultas.models.Agenda;
import josiane.begnini.com.agendamento.consultas.models.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    boolean existsByMedicoAndDataHora(Medico medico, LocalDateTime dataHora);
}
