package josiane.begnini.com.agendamento.consultas.exceptions;

/**
 * Lançada quando um recurso (Paciente, Médico, Agenda, etc.) não é encontrado.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}