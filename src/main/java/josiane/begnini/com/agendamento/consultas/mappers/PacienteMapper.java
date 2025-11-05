package josiane.begnini.com.agendamento.consultas.mappers;

import josiane.begnini.com.agendamento.consultas.dtos.*;
import josiane.begnini.com.agendamento.consultas.models.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PacienteMapper {

    public Paciente toEntity(PacienteRequestDTO request) {
        Paciente paciente = Paciente.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .telefone(request.getTelefone())
                .dataNascimento(request.getDataNascimento())
                .build();

        // Converte os endereços da requisição (se houver)
        if (request.getEnderecos() != null && !request.getEnderecos().isEmpty()) {
            List<Endereco> enderecos = request.getEnderecos().stream()
                    .map(this::toEnderecoEntity)
                    .collect(Collectors.toList());
            enderecos.forEach(e -> e.setPaciente(paciente));
            paciente.setEnderecos(enderecos);
        }

        return paciente;
    }

    public PacienteResponseDTO toResponse(Paciente paciente) {
        return PacienteResponseDTO.builder()
                .id(paciente.getId())
                .nome(paciente.getNome())
                .email(paciente.getEmail())
                .telefone(paciente.getTelefone())
                .dataNascimento(paciente.getDataNascimento())
                .convenio(paciente.getConvenio() != null ?
                        ConvenioResponseDTO.builder()
                                .id(paciente.getConvenio().getId())
                                .nome(paciente.getConvenio().getNome())
                                .cobertura(paciente.getConvenio().getCobertura())
                                .telefoneContato(paciente.getConvenio().getTelefoneContato())
                                .build() : null)
                .enderecos(paciente.getEnderecos() != null ?
                        paciente.getEnderecos().stream()
                                .map(this::toEnderecoResponse)
                                .collect(Collectors.toList()) : new ArrayList<>())
                .build();
    }

    public void updateEntityFromRequest(PacienteRequestDTO request, Paciente paciente) {
        if (request.getNome() != null) {
            paciente.setNome(request.getNome());
        }
        if (request.getTelefone() != null) {
            paciente.setTelefone(request.getTelefone());
        }
        if (request.getDataNascimento() != null) {
            paciente.setDataNascimento(request.getDataNascimento());
        }

        // Atualizar endereços, se vierem na requisição
        if (request.getEnderecos() != null && !request.getEnderecos().isEmpty()) {
            List<Endereco> novosEnderecos = request.getEnderecos().stream()
                    .map(this::toEnderecoEntity)
                    .collect(Collectors.toList());
            novosEnderecos.forEach(e -> e.setPaciente(paciente));
            paciente.setEnderecos(novosEnderecos);
        }
    }

    private Endereco toEnderecoEntity(EnderecoRequestDTO dto) {
        return Endereco.builder()
                .logradouro(dto.getLogradouro())
                .numero(dto.getNumero())
                .complemento(dto.getComplemento())
                .bairro(dto.getBairro())
                .cidade(dto.getCidade())
                .estado(dto.getEstado())
                .cep(dto.getCep())
                .build();
    }

    private EnderecoResponseDTO toEnderecoResponse(Endereco endereco) {
        return EnderecoResponseDTO.builder()
                .id(endereco.getId())
                .logradouro(endereco.getLogradouro())
                .numero(endereco.getNumero())
                .complemento(endereco.getComplemento())
                .bairro(endereco.getBairro())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .cep(endereco.getCep())
                .build();
    }

    public List<Endereco> toEnderecoEntityList(List<EnderecoRequestDTO> enderecos) {
        if (enderecos == null || enderecos.isEmpty()) {
            return new ArrayList<>();
        }

        return enderecos.stream()
                .map(this::toEnderecoEntity)
                .collect(Collectors.toList());
    }
}
