package backend.mcsvventas.services;

import backend.mcsvventas.models.dtos.request.VentaRequestDto;
import backend.mcsvventas.models.dtos.response.VentaResponseDto;
import backend.mcsvventas.util.Paginado;
import org.springframework.data.domain.Page;

import java.util.List;

public interface VentaService {
    List<VentaResponseDto> getAll();

    VentaResponseDto add(VentaRequestDto requestDto);

    Page<VentaResponseDto> getSalesByClient(Integer clientId, Paginado paginado);
}
