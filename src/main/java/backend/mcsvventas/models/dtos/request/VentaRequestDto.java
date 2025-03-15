package backend.mcsvventas.models.dtos.request;

import backend.mcsvventas.models.documents.DetalleVenta;

import java.util.List;

public record VentaRequestDto(Long clientId,
                              List<DetalleVentaRequestDto> details) {
}
