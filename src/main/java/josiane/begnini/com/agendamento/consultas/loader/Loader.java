package josiane.begnini.com.agendamento.consultas.loader;

import josiane.begnini.com.agendamento.consultas.dtos.PacienteRequestDTO;
import josiane.begnini.com.agendamento.consultas.services.MedicoService;
import josiane.begnini.com.agendamento.consultas.services.PacienteService;
import josiane.begnini.com.agendamento.consultas.services.PacienteServiceInMemory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;

@Component
public class Loader implements ApplicationRunner {

    private final PacienteService pacienteService;
    private final PacienteServiceInMemory pacienteServiceInMemory;
    private final MedicoService medicoService;
    private final boolean useMemory;

    public Loader(PacienteService pacienteService,
                  PacienteServiceInMemory pacienteServiceInMemory,
                  MedicoService medicoService,
                  @Value("${app.useMemory:false}") boolean useMemory) {
        this.pacienteService = pacienteService;
        this.pacienteServiceInMemory = pacienteServiceInMemory;
        this.medicoService = medicoService;
        this.useMemory = useMemory;
    }

    @Override
    public void run(ApplicationArguments args) {
        System.out.println("🔹 Loader iniciado (modo memória = " + useMemory + ")");
        loadPacientes();
    }

    private void loadPacientes() {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new ClassPathResource("data/pacientes.txt").getInputStream()))) {

            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(";");
                if (parts.length < 4) continue;

                PacienteRequestDTO dto = new PacienteRequestDTO(
                        parts[0].trim(),
                        parts[1].trim(),
                        parts[2].trim(),
                        LocalDate.parse(parts[3].trim()),
                        null
                );

                if (useMemory) {
                    pacienteServiceInMemory.saveFromRequest(dto);
                } else {
                    pacienteService.saveFromRequest(dto);
                }
            }

            System.out.println("✅ Pacientes carregados com sucesso!");
        } catch (Exception e) {
            System.out.println("⚠️ Erro ao carregar pacientes: " + e.getMessage());
        }
    }
}
