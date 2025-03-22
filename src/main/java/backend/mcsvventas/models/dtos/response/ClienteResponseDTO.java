package backend.mcsvventas.models.dtos.response;

public record ClienteResponseDTO(Long id,
                                 String nombre,
                                 String apellido,
                                 String tipoDocumento,
                                 String fechaNacimiento,
                                 String numeroDocumento) {
}
