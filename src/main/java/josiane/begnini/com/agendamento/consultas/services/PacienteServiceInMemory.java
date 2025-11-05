package josiane.begnini.com.agendamento.consultas.services;

import jakarta.annotation.PostConstruct;
import josiane.begnini.com.agendamento.consultas.dtos.PacienteRequestDTO;
import josiane.begnini.com.agendamento.consultas.exceptions.BusinessException;
import josiane.begnini.com.agendamento.consultas.exceptions.ResourceNotFoundException;
import josiane.begnini.com.agendamento.consultas.mappers.PacienteMapper;
import josiane.begnini.com.agendamento.consultas.models.Paciente;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class PacienteServiceInMemory implements PacienteServiceBase {

    private final PacienteMapper pacienteMapper;

    private final Map<Long, Paciente> pacientes = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Autowired
    public PacienteServiceInMemory(PacienteMapper pacienteMapper) {
        this.pacienteMapper = pacienteMapper;
    }

    @Override
    public List<Paciente> findAll() {
        return new ArrayList<>(pacientes.values());
    }

    @Override
    public Optional<Paciente> findById(Long id) {
        return Optional.ofNullable(pacientes.get(id));
    }

    @Override
    public Paciente findByEmail(String email) {
        return pacientes.values().stream()
                .filter(p -> p.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado com email: " + email));
    }

    @Override
    public Paciente saveFromRequest(PacienteRequestDTO request) {
        boolean exists = pacientes.values().stream()
                .anyMatch(p -> p.getEmail().equalsIgnoreCase(request.getEmail()));

        if (exists) {
            throw new BusinessException("Já existe um paciente cadastrado com este email");
        }

        Paciente paciente = pacienteMapper.toEntity(request);
        paciente.setId(idGenerator.getAndIncrement());
        pacientes.put(paciente.getId(), paciente);
        return paciente;
    }

    @Override
    public Paciente update(Long id, PacienteRequestDTO request) {
        Paciente existente = pacientes.get(id);
        if (existente == null) {
            throw new ResourceNotFoundException("Paciente não encontrado com ID: " + id);
        }

        existente.setNome(Optional.ofNullable(request.getNome()).orElse(existente.getNome()));
        existente.setEmail(Optional.ofNullable(request.getEmail()).orElse(existente.getEmail()));
        existente.setTelefone(Optional.ofNullable(request.getTelefone()).orElse(existente.getTelefone()));
        existente.setDataNascimento(Optional.ofNullable(request.getDataNascimento()).orElse(existente.getDataNascimento()));

        return existente;
    }

    @Override
    public void deleteById(Long id) {
        if (pacientes.remove(id) == null) {
            throw new ResourceNotFoundException("Paciente não encontrado com ID: " + id);
        }
    }

    @Override
    public List<Paciente> findByNomeContaining(String nome) {
        return pacientes.values().stream()
                .filter(p -> p.getNome() != null && p.getNome().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toList());
    }
}
