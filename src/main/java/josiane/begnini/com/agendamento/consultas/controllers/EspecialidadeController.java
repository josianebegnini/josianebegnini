package josiane.begnini.com.agendamento.consultas.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import josiane.begnini.com.agendamento.consultas.dtos.EspecialidadeDTO;
import josiane.begnini.com.agendamento.consultas.services.EspecialidadeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@Tag(name = "Especialidades", description = "Gerenciamento de especialidades médicas")
@RequestMapping("/api/v1/especialidades")
public class EspecialidadeController {

    private final EspecialidadeService service;

    public EspecialidadeController(EspecialidadeService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Lista todas as especialidades",
            description = "Retorna uma lista com todas as especialidades cadastradas.")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public ResponseEntity<List<EspecialidadeDTO>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca especialidade por ID",
            description = "Retorna os dados de uma especialidade específica pelo ID.")
    @ApiResponse(responseCode = "200", description = "Especialidade encontrada")
    @ApiResponse(responseCode = "404", description = "Especialidade não encontrada")
    public ResponseEntity<EspecialidadeDTO> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Cadastra uma nova especialidade")
    @ApiResponse(responseCode = "201", description = "Especialidade criada com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    public ResponseEntity<EspecialidadeDTO> salvar(@RequestBody EspecialidadeDTO dto) {
        EspecialidadeDTO criada = service.salvar(dto);

        // Cria a URI do novo recurso criado
        URI location = URI.create("/api/v1/especialidades/" + criada.getId());

        // Retorna 201 Created + objeto criado + cabeçalho Location
        return ResponseEntity
                .created(location)
                .body(criada);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma especialidade existente",
            description = "Altera os dados de uma especialidade pelo ID informado.")
    @ApiResponse(responseCode = "200", description = "Especialidade atualizada com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "404", description = "Especialidade não encontrada")
    public ResponseEntity<EspecialidadeDTO> atualizar(@PathVariable Long id,
                                                      @RequestBody EspecialidadeDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove uma especialidade",
            description = "Exclui permanentemente uma especialidade pelo ID informado.")
    @ApiResponse(responseCode = "204", description = "Especialidade removida com sucesso")
    @ApiResponse(responseCode = "404", description = "Especialidade não encontrada")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}