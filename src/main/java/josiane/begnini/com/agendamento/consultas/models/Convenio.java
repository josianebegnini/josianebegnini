package josiane.begnini.com.agendamento.consultas.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Convenio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String nome;

    @Column(nullable=false)
    private String cobertura;

    private String telefoneContato;

    public  String toString() {
        return "Convenio{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cobertura='" + cobertura + '\'' +
                ", telefoneContato='" + telefoneContato + '\'' +
                '}';
    }
}
