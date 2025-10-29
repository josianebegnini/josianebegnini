package josiane.begnini.com.agendamento.consultas.services;

import josiane.begnini.com.agendamento.consultas.dtos.EspecialidadeDTO;
import josiane.begnini.com.agendamento.consultas.exceptions.ResourceNotFoundException;
import josiane.begnini.com.agendamento.consultas.mappers.EspecialidadeMapper;
import josiane.begnini.com.agendamento.consultas.models.Especialidade;
import josiane.begnini.com.agendamento.consultas.repositories.EspecialidadeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EspecialidadeService {

    private final EspecialidadeRepository repository;

    public EspecialidadeService(EspecialidadeRepository repository) {
        this.repository = repository;
    }

    public List<EspecialidadeDTO> listarTodas() {
        return repository.findAll()
                .stream()
                .map(EspecialidadeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<EspecialidadeDTO> buscarPorId(Long id) {
        return repository.findById(id).map(EspecialidadeMapper::toDTO);
    }

    public List<Especialidade> buscarPorIds(List<Long> ids) {
        List<Especialidade> especialidades = repository.findAllById(ids);

        if (especialidades.size() != ids.size()) {
            throw new ResourceNotFoundException("Algumas especialidades não foram encontradas.");
        }

        return especialidades;
    }

    public EspecialidadeDTO salvar(EspecialidadeDTO dto) {
        if (repository.existsByNome(dto.getNome())) {
            throw new RuntimeException("Especialidade já cadastrada: " + dto.getNome());
        }
        Especialidade entity = EspecialidadeMapper.toEntity(dto);
        return EspecialidadeMapper.toDTO(repository.save(entity));
    }

    public EspecialidadeDTO atualizar(Long id, EspecialidadeDTO dto) {
        return repository.findById(id)
                .map(entity -> {
                    entity.setNome(dto.getNome());
                    return EspecialidadeMapper.toDTO(repository.save(entity));
                })
                .orElseThrow(() -> new RuntimeException("Especialidade não encontrada com id " + id));
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Especialidade não encontrada com id " + id);
        }
        repository.deleteById(id);
    }
}
