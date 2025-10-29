package josiane.begnini.com.agendamento.consultas.models;

import jakarta.persistence.*;
import josiane.begnini.com.agendamento.consultas.enums.StatusAgenda;
import josiane.begnini.com.agendamento.consultas.enums.TipoConsulta;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    private TipoConsulta tipoConsulta;

    @Enumerated(EnumType.STRING)
    private StatusAgenda status;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;
}
