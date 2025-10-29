package josiane.begnini.com.agendamento.consultas.mappers;


import josiane.begnini.com.agendamento.consultas.dtos.EspecialidadeDTO;
import josiane.begnini.com.agendamento.consultas.models.Especialidade;

public class EspecialidadeMapper {

    public static EspecialidadeDTO toDTO(Especialidade entity) {
        return EspecialidadeDTO.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .build();
    }
    public static Especialidade toEntity(EspecialidadeDTO dto) {
        Especialidade entity = new Especialidade();
        entity.setId(dto.getId());
        entity.setNome(dto.getNome());
        return entity;
    }
}
