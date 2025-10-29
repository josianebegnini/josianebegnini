package josiane.begnini.com.agendamento.consultas.mappers;

import josiane.begnini.com.agendamento.consultas.dtos.MedicoRequestDTO;
import josiane.begnini.com.agendamento.consultas.dtos.MedicoResponseDTO;
import josiane.begnini.com.agendamento.consultas.models.Especialidade;
import josiane.begnini.com.agendamento.consultas.models.Medico;

import java.util.List;
import java.util.stream.Collectors;

public class MedicoMapper {

    public static MedicoResponseDTO toResponseDTO(Medico medico) {
        return MedicoResponseDTO.builder()
                .id(medico.getId())
                .nome(medico.getNome())
                .crm(medico.getCrm())
                .endereco(medico.getEndereco())
                .especialidades(
                        medico.getEspecialidades()
                                .stream()
                                .map(Especialidade::getNome)
                                .collect(Collectors.toList())
                )
                .build();
    }

    public static Medico toEntity(MedicoRequestDTO dto, List<Especialidade> especialidades) {
        return Medico.builder()
                .nome(dto.getNome())
                .crm(dto.getCrm())
                .endereco(dto.getEndereco())
                .especialidades(especialidades)
                .build();
    }
}
