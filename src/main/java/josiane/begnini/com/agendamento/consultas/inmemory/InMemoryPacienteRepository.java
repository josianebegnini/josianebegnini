package josiane.begnini.com.agendamento.consultas.inmemory;

import josiane.begnini.com.agendamento.consultas.models.Paciente;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryPacienteRepository {

    private final ConcurrentHashMap<Long, Paciente> store = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong(0);

    public Paciente save(Paciente p) {
        if (p.getId() == null) {
            p.setId(idGen.incrementAndGet());
        }
        store.put(p.getId(), p);
        return p;
    }

    public Optional<Paciente> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Paciente> findAll() {
        return new ArrayList<>(store.values());
    }

    public void deleteById(Long id) {
        store.remove(id);
    }

    public void clear() {
        store.clear();
        idGen.set(0);
    }
}
