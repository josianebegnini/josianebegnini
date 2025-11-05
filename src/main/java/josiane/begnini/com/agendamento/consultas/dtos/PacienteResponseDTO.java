package josiane.begnini.com.agendamento.consultas.dtos;

import josiane.begnini.com.agendamento.consultas.models.Paciente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PacienteResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private LocalDate dataNascimento;
    private ConvenioResponseDTO convenio;
    private List<EnderecoResponseDTO> enderecos;

    // 🔹 Conversão auxiliar usada pelo PacienteServiceInMemory
    public static PacienteResponseDTO fromEntity(Paciente paciente) {
        if (paciente == null) return null;

        return PacienteResponseDTO.builder()
                .id(paciente.getId())
                .nome(paciente.getNome())
                .email(paciente.getEmail())
                .telefone(paciente.getTelefone())
                .dataNascimento(paciente.getDataNascimento())
                .convenio(paciente.getConvenio() != null
                        ? ConvenioResponseDTO.fromEntity(paciente.getConvenio())
                        : null)
                .build();
    }
}
