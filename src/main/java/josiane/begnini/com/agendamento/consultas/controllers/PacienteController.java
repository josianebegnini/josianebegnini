package josiane.begnini.com.agendamento.consultas.controllers;

import jakarta.validation.Valid;
import josiane.begnini.com.agendamento.consultas.dtos.PacienteRequestDTO;
import josiane.begnini.com.agendamento.consultas.dtos.PacienteResponseDTO;
import josiane.begnini.com.agendamento.consultas.exceptions.ResourceNotFoundException;
import josiane.begnini.com.agendamento.consultas.mappers.PacienteMapper;
import josiane.begnini.com.agendamento.consultas.models.Paciente;
import josiane.begnini.com.agendamento.consultas.services.ConvenioService;
import josiane.begnini.com.agendamento.consultas.services.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;
    private final PacienteMapper pacienteMapper;
    private final ConvenioService convenioService;

    // 🔹 GET - listar todos
    @GetMapping
    public ResponseEntity<List<PacienteResponseDTO>> getAllPacientes() {
        List<PacienteResponseDTO> pacientes = pacienteService.findAll()
                .stream()
                .map(pacienteMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pacientes);
    }

    // 🔹 GET - buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> getPacienteById(@PathVariable Long id) {
        Paciente paciente = pacienteService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado com ID: " + id));
        return ResponseEntity.ok(pacienteMapper.toResponse(paciente));
    }

    // 🔹 GET - buscar por email
    @GetMapping("/email/{email}")
    public ResponseEntity<PacienteResponseDTO> getPacienteByEmail(@PathVariable String email) {
        Paciente paciente = pacienteService.findByEmail(email);
        return ResponseEntity.ok(pacienteMapper.toResponse(paciente));
    }

    // 🔹 GET - busca por nome (parcial)
    @GetMapping("/search")
    public ResponseEntity<List<PacienteResponseDTO>> searchPacientesByNome(@RequestParam String nome) {
        List<PacienteResponseDTO> pacientes = pacienteService.findByNomeContaining(nome)
                .stream()
                .map(pacienteMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pacientes);
    }

    // 🔹 POST - criar paciente
    @PostMapping
    public ResponseEntity<PacienteResponseDTO> createPaciente(@Valid @RequestBody PacienteRequestDTO request) {
        Paciente savedPaciente = pacienteService.saveFromRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pacienteMapper.toResponse(savedPaciente));
    }

    // 🔹 PUT - atualizar paciente completo
    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> updatePaciente(
            @PathVariable Long id,
            @Valid @RequestBody PacienteRequestDTO request) {

        Paciente updatedPaciente = pacienteService.update(id, request);
        return ResponseEntity.ok(pacienteMapper.toResponse(updatedPaciente));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> partialUpdatePaciente(
            @PathVariable Long id,
            @RequestBody PacienteRequestDTO request) {

        Paciente updatedPaciente = pacienteService.updateFromRequest(id, request);
        return ResponseEntity.ok(pacienteMapper.toResponse(updatedPaciente));
    }

    // 🔹 DELETE - remover paciente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaciente(@PathVariable Long id) {
        pacienteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}