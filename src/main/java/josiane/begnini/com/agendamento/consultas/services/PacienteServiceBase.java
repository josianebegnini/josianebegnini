package josiane.begnini.com.agendamento.consultas.services;

import josiane.begnini.com.agendamento.consultas.dtos.PacienteRequestDTO;
import josiane.begnini.com.agendamento.consultas.models.Paciente;

import java.util.List;
import java.util.Optional;

public interface PacienteServiceBase {

    List<Paciente> findAll();

    Optional<Paciente> findById(Long id);

    Paciente findByEmail(String email);

    Paciente saveFromRequest(PacienteRequestDTO request);

    Paciente update(Long id, PacienteRequestDTO request);

    void deleteById(Long id);

    List<Paciente> findByNomeContaining(String nome);
}
