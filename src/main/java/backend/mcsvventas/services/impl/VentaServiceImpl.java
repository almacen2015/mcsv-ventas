package backend.mcsvventas.services.impl;

import backend.mcsvventas.models.documents.Venta;
import backend.mcsvventas.models.dtos.request.VentaRequestDto;
import backend.mcsvventas.models.dtos.response.VentaResponseDto;
import backend.mcsvventas.models.mapper.VentaMapper;
import backend.mcsvventas.repositories.VentaRepository;
import backend.mcsvventas.services.VentaService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VentaServiceImpl implements VentaService {
    private final VentaRepository repository;
    private final VentaMapper ventaMapper = VentaMapper.INSTANCE;

    public VentaServiceImpl(VentaRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public VentaResponseDto add(VentaRequestDto requestDto) {
        Venta venta = ventaMapper.toEntity(requestDto);
        VentaResponseDto response = ventaMapper.toDto(repository.save(venta));
        return response;
    }

    @Override
    public List<VentaResponseDto> getAll() {
        return List.of();
    }
}
