package backend.mcsvventas.models.dtos.request;

public record DetalleVentaRequestDto(Long productId,
                                     Integer quantity,
                                     Double unitPrice,
                                     Double subTotal) {
}
