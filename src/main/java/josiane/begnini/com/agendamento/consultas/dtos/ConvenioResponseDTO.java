package josiane.begnini.com.agendamento.consultas.dtos;

import josiane.begnini.com.agendamento.consultas.models.Convenio;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConvenioResponseDTO {

    private Long id;
    private String nome;
    private String cobertura;
    private String telefoneContato;

    // 🔹 Conversão auxiliar: transforma a entidade Convenio em DTO
    public static ConvenioResponseDTO fromEntity(Convenio convenio) {
        if (convenio == null) {
            return null;
        }

        return ConvenioResponseDTO.builder()
                .id(convenio.getId())
                .nome(convenio.getNome())
                .cobertura(convenio.getCobertura())
                .telefoneContato(convenio.getTelefoneContato())
                .build();
    }
}
