package josiane.begnini.com.agendamento.consultas.dtos;

import josiane.begnini.com.agendamento.consultas.enums.StatusAgenda;
import josiane.begnini.com.agendamento.consultas.enums.TipoConsulta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgendamentoResponseDTO {
    private Long id;
    private String pacienteNome;
    private String medicoNome;
    private LocalDateTime dataHora;
    private TipoConsulta tipoConsulta;
    private StatusAgenda status;
}
