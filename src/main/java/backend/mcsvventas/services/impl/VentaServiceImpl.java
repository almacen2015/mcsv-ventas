package backend.mcsvventas.services.impl;

import backend.mcsvventas.exceptions.VentaException;
import backend.mcsvventas.models.documents.DetalleVenta;
import backend.mcsvventas.models.documents.Venta;
import backend.mcsvventas.models.dtos.request.DetalleVentaRequestDto;
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
        validateClientId(requestDto.clientId());
        validateDetails(requestDto.details());

        Venta venta = ventaMapper.toEntity(requestDto);
        VentaResponseDto response = ventaMapper.toDto(repository.save(venta));
        return response;
    }

    @Override
    public List<VentaResponseDto> getAll() {
        return List.of();
    }

    private void calculateSubtotal(List<DetalleVenta> details) {
    }

    private void validateClientId(Long id) {
        if (id == null || id >= 0) {
            throw new VentaException(VentaException.CLIENT_ID_INVALID);
        }
    }

    private void validateDetails(List<DetalleVentaRequestDto> details) {
        if (details == null || details.isEmpty()) {
            throw new VentaException(VentaException.DETAILS_INVALID);
        }

        details.forEach(detail -> {
            validateProduct(detail.productId());
            validateQuantity(detail.quantity());
        });
    }

    private void validateProduct(Long productId) {
        validateProductId(productId);
    }

    private void validateProductId(Long id) {
        if (id == null || id >= 0) {
            throw new VentaException(VentaException.PRODUCT_ID_INVALID);
        }
    }

    private void validateQuantity(Integer quantity) {
        if (quantity == null || quantity >= 0) {
            throw new VentaException(VentaException.QUANTITY_INVALID);
        }
    }
}
