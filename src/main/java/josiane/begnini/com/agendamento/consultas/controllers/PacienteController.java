package josiane.begnini.com.agendamento.consultas.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Pacientes", description = "Gerenciamento de pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;
    private final PacienteMapper pacienteMapper;
    private final ConvenioService convenioService;

    // 🔹 GET - listar todos
    @Operation(
            summary = "Listar todos os pacientes",
            description = "Retorna uma lista com todos os pacientes cadastrados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PacienteResponseDTO.class)))
            }
    )
    @GetMapping
    public ResponseEntity<List<PacienteResponseDTO>> getAllPacientes() {
        List<PacienteResponseDTO> pacientes = pacienteService.findAll()
                .stream()
                .map(pacienteMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pacientes);
    }

    // 🔹 GET - buscar por ID
    @Operation(
            summary = "Buscar paciente por ID",
            description = "Retorna os dados do paciente correspondente ao ID informado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Paciente encontrado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PacienteResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Paciente não encontrado",
                            content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> getPacienteById(@PathVariable Long id) {
        Paciente paciente = pacienteService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado com ID: " + id));
        return ResponseEntity.ok(pacienteMapper.toResponse(paciente));
    }

    // 🔹 GET - buscar por email
    @Operation(
            summary = "Buscar paciente por e-mail",
            description = "Retorna os dados de um paciente com base em seu e-mail.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Paciente encontrado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PacienteResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Paciente não encontrado", content = @Content)
            }
    )
    @GetMapping("/email/{email}")
    public ResponseEntity<PacienteResponseDTO> getPacienteByEmail(@PathVariable String email) {
        Paciente paciente = pacienteService.findByEmail(email);
        return ResponseEntity.ok(pacienteMapper.toResponse(paciente));
    }

    // 🔹 GET - busca por nome (parcial)
    @Operation(
            summary = "Buscar pacientes por nome",
            description = "Busca pacientes cujo nome contenha o texto informado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de pacientes retornada com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PacienteResponseDTO.class)))
            }
    )
    @GetMapping("/search")
    public ResponseEntity<List<PacienteResponseDTO>> searchPacientesByNome(@RequestParam String nome) {
        List<PacienteResponseDTO> pacientes = pacienteService.findByNomeContaining(nome)
                .stream()
                .map(pacienteMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pacientes);
    }

    // 🔹 POST - criar paciente
    @Operation(
            summary = "Cadastrar um novo paciente",
            description = "Cria um novo paciente com os dados fornecidos.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Paciente criado com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PacienteResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<PacienteResponseDTO> createPaciente(@Valid @RequestBody PacienteRequestDTO request) {
        Paciente savedPaciente = pacienteService.saveFromRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pacienteMapper.toResponse(savedPaciente));
    }

    // 🔹 PUT - atualizar paciente completo
    @Operation(
            summary = "Atualizar paciente (substituição completa)",
            description = "Atualiza completamente um paciente existente com os novos dados fornecidos.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Paciente atualizado com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PacienteResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Paciente não encontrado", content = @Content)
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> updatePaciente(
            @PathVariable Long id,
            @Valid @RequestBody PacienteRequestDTO request) {

        Paciente updatedPaciente = pacienteService.update(id, request);
        return ResponseEntity.ok(pacienteMapper.toResponse(updatedPaciente));
    }

    // 🔹 PATCH - atualização parcial
    @Operation(
            summary = "Atualizar parcialmente um paciente",
            description = "Atualiza apenas os campos informados de um paciente existente.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Paciente atualizado com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PacienteResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Paciente não encontrado", content = @Content)
            }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> partialUpdatePaciente(
            @PathVariable Long id,
            @RequestBody PacienteRequestDTO request) {

        Paciente updatedPaciente = pacienteService.updateFromRequest(id, request);
        return ResponseEntity.ok(pacienteMapper.toResponse(updatedPaciente));
    }

    // 🔹 DELETE - remover paciente
    @Operation(
            summary = "Excluir paciente",
            description = "Remove permanentemente um paciente pelo ID informado.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Paciente excluído com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Paciente não encontrado", content = @Content)
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaciente(@PathVariable Long id) {
        pacienteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}