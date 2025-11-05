package josiane.begnini.com.agendamento.consultas.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnderecoResponseDTO {
    private Long id;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
}
