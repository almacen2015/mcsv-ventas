package backend.mcsvventas.services.impl;

import backend.mcsvventas.client.ProductClient;
import backend.mcsvventas.exceptions.VentaException;
import backend.mcsvventas.models.documents.DetalleVenta;
import backend.mcsvventas.models.documents.Venta;
import backend.mcsvventas.models.dtos.request.DetalleVentaRequestDto;
import backend.mcsvventas.models.dtos.request.VentaRequestDto;
import backend.mcsvventas.models.dtos.response.ProductoDtoResponse;
import backend.mcsvventas.models.dtos.response.VentaResponseDto;
import backend.mcsvventas.models.mapper.VentaMapper;
import backend.mcsvventas.repositories.VentaRepository;
import backend.mcsvventas.services.VentaService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class VentaServiceImpl implements VentaService {
    private final VentaRepository repository;
    private final VentaMapper ventaMapper = VentaMapper.INSTANCE;
    private final ProductClient productClient;

    public VentaServiceImpl(VentaRepository repository, ProductClient productClient) {
        this.repository = repository;
        this.productClient = productClient;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public VentaResponseDto add(VentaRequestDto requestDto) {
        validateClientId(requestDto.clientId());
        Venta venta = ventaMapper.toEntity(requestDto);
        validateDetails(venta.getDetails());


        VentaResponseDto response = ventaMapper.toDto(repository.save(venta));
        return response;
    }

    @Override
    public List<VentaResponseDto> getAll() {
        return List.of();
    }

    private Double calculateSubtotal(Double precio, Integer quantity) {
        double subTotal;
        subTotal = precio * quantity;
        return subTotal;
    }

    private void validateClientId(Integer id) {
        if (id == null || id <= 0) {
            throw new VentaException(VentaException.CLIENT_ID_INVALID);
        }
    }

    private void validateDetails(List<DetalleVenta> details) {
        Set<Integer> productIds = new HashSet<>();

        if (details == null || details.isEmpty()) {
            throw new VentaException(VentaException.DETAILS_INVALID);
        }

        details.forEach(detail -> {
            validateQuantity(detail.getQuantity());

            ProductoDtoResponse product = productClient.getProduct(detail.getProductId());
            if (!productIds.add(detail.getProductId())) {
                throw new VentaException(VentaException.PRODUCT_REPEATED);
            }

            validateProduct(product);
            validateQuantityGreaterThanStock(detail.getQuantity(), product.stock());

            detail.setUnitPrice(detail.getUnitPrice());
            detail.setSubTotal(calculateSubtotal(product.precio(), detail.getQuantity()));
        });

    }

    private void validateProduct(ProductoDtoResponse product) {
        if (Optional.ofNullable(product).isEmpty()) {
            throw new VentaException(VentaException.PRODUCT_NOT_FOUND);
        }
    }

    private void validateProductId(Integer id) {
        if (id == null || id >= 0) {
            throw new VentaException(VentaException.PRODUCT_ID_INVALID);
        }
    }

    private void validateQuantity(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new VentaException(VentaException.QUANTITY_INVALID);
        }
    }

    private void validateQuantityGreaterThanStock(Integer quantity, Integer stock) {
        if (quantity > stock) {
            throw new VentaException(VentaException.QUANTITY_GREATER_THAN_STOCK);
        }
    }
}
