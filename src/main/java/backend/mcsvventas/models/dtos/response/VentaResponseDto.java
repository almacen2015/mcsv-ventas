package backend.mcsvventas.models.dtos.response;

import backend.mcsvventas.models.documents.DetalleVenta;

import java.util.List;

public record VentaResponseDto(
        String id,
        String client,
        String date,
        Double total,
        List<DetalleVenta> details) {
}
