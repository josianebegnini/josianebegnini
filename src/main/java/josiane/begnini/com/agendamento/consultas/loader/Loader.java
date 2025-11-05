package josiane.begnini.com.agendamento.consultas.loader;

import josiane.begnini.com.agendamento.consultas.dtos.EnderecoRequestDTO;
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
import java.util.ArrayList;
import java.util.List;

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

                // Exemplo esperado de linha:
                // nome;email;telefone;dataNascimento;logradouro;numero;bairro;cidade;estado;cep
                String[] parts = line.split(";");
                if (parts.length < 10) {
                    System.out.println("⚠️ Linha inválida: " + line);
                    continue;
                }

                List<EnderecoRequestDTO> enderecos = new ArrayList<>();
                EnderecoRequestDTO endereco = new EnderecoRequestDTO();
                endereco.setLogradouro(parts[4].trim());
                endereco.setNumero(parts[5].trim());
                endereco.setBairro(parts[6].trim());
                endereco.setCidade(parts[7].trim());
                endereco.setEstado(parts[8].trim());
                endereco.setCep(parts[9].trim());
                enderecos.add(endereco);

                PacienteRequestDTO dto = new PacienteRequestDTO();
                dto.setNome(parts[0].trim());
                dto.setEmail(parts[1].trim());
                dto.setTelefone(parts[2].trim());
                dto.setDataNascimento(LocalDate.parse(parts[3].trim()));
                dto.setEnderecos(enderecos);
                dto.setConvenioId(null); // Pode ser adicionado se quiser carregar convênios

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
