package backend.mcsvventas.services;

import backend.mcsvventas.models.dtos.response.VentaResponseDto;

import java.util.List;

public interface VentaService {
    List<VentaResponseDto> getAll();
}
