package josiane.begnini.com.agendamento.consultas.services;

import josiane.begnini.com.agendamento.consultas.dtos.MedicoRequestDTO;
import josiane.begnini.com.agendamento.consultas.dtos.MedicoResponseDTO;
import josiane.begnini.com.agendamento.consultas.exceptions.ResourceNotFoundException;
import josiane.begnini.com.agendamento.consultas.mappers.MedicoMapper;
import josiane.begnini.com.agendamento.consultas.models.Especialidade;
import josiane.begnini.com.agendamento.consultas.models.Medico;
import josiane.begnini.com.agendamento.consultas.repositories.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EspecialidadeService especialidadeService;

    public Page<MedicoResponseDTO> listarTodos(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return medicoRepository.findAll(pageable)
                .map(MedicoMapper::toResponseDTO);
    }

    public List<MedicoResponseDTO> listarTodos() {
        return medicoRepository.findAll()
                .stream()
                .map(MedicoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public MedicoResponseDTO buscarPorId(Long id) {
        return medicoRepository.findById(id)
                .map(MedicoMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Médico não encontrado com ID: " + id));
    }

    public Page<MedicoResponseDTO> listarPorEspecialidade(String especialidade, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return medicoRepository.findByEspecialidades_NomeIgnoreCase(especialidade, pageable)
                .map(MedicoMapper::toResponseDTO);
    }

    public MedicoResponseDTO salvar(MedicoRequestDTO medicoRequestDTO) {
        List<Especialidade> especialidades = especialidadeService.buscarPorIds(medicoRequestDTO.getEspecialidades());
        Medico medico = MedicoMapper.toEntity(medicoRequestDTO, especialidades);
        return MedicoMapper.toResponseDTO(medicoRepository.save(medico));
    }

    public MedicoResponseDTO atualizar(Long id, MedicoRequestDTO dto) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Médico não encontrado com ID: " + id));

        List<Especialidade> especialidades = especialidadeService.buscarPorIds(dto.getEspecialidades());

        medico.setNome(dto.getNome());
        medico.setCrm(dto.getCrm());
        medico.setEndereco(dto.getEndereco());
        medico.setEspecialidades(especialidades);

        return MedicoMapper.toResponseDTO(medicoRepository.save(medico));
    }

    public void excluir(Long id) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Médico não encontrado com ID: " + id));
        medicoRepository.delete(medico);
    }
}
