package backend.mcsvventas.services.impl;

import backend.mcsvventas.client.ClientFeign;
import backend.mcsvventas.client.MovementClient;
import backend.mcsvventas.client.ProductClient;
import backend.mcsvventas.exceptions.VentaException;
import backend.mcsvventas.models.documents.DetalleVenta;
import backend.mcsvventas.models.documents.Venta;
import backend.mcsvventas.models.dtos.request.MovimientoDtoRequest;
import backend.mcsvventas.models.dtos.request.VentaRequestDto;
import backend.mcsvventas.models.dtos.response.ClienteResponseDTO;
import backend.mcsvventas.models.dtos.response.ProductoDtoResponse;
import backend.mcsvventas.models.dtos.response.VentaResponseDto;
import backend.mcsvventas.models.mapper.VentaMapper;
import backend.mcsvventas.repositories.VentaRepository;
import backend.mcsvventas.services.VentaService;
import jakarta.transaction.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VentaServiceImpl implements VentaService {
    private final VentaRepository repository;
    private final VentaMapper ventaMapper = VentaMapper.INSTANCE;
    private final ProductClient productClient;
    private final MovementClient movementClient;
    private final ClientFeign clientFeign;

    private double total = 0.0;

    public VentaServiceImpl(VentaRepository repository, ProductClient productClient, MovementClient movementClient, ClientFeign clientFeign) {
        this.repository = repository;
        this.productClient = productClient;
        this.movementClient = movementClient;
        this.clientFeign = clientFeign;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public VentaResponseDto add(VentaRequestDto requestDto) {
        String fullName;
        ClienteResponseDTO client = validateClientId(requestDto.clientId());
        Venta venta = ventaMapper.toEntity(requestDto);
        validateDetails(venta.getDetails());

        venta.setDate(LocalDateTime.now());
        venta.setTotal(total);

        Venta ventaSaved = repository.save(venta);

        fullName = getFullNameClient(client.nombre(), client.apellido());

        VentaResponseDto response = new VentaResponseDto(ventaSaved.getId(), fullName, venta.getDate().toString(), venta.getTotal(), venta.getDetails());
        addMovement(venta.getDetails());

        return response;
    }

    private void addMovement(List<DetalleVenta> details) {
        final String TIPO_MOVIMIENTO = "SALIDA";

        for (DetalleVenta detail : details) {
            MovimientoDtoRequest movimiento = new MovimientoDtoRequest(detail.getProductId(), detail.getQuantity(), TIPO_MOVIMIENTO);

            movementClient.createMovimientoDto(movimiento);
        }
    }

    @Override
    public List<VentaResponseDto> getAll() {
        List<Venta> ventas = repository.findAll();
        List<VentaResponseDto> response = new ArrayList<>();
        for (Venta venta : ventas) {
            ClienteResponseDTO client = clientFeign.getClient(venta.getClientId().longValue());
            String fullNameClient = getFullNameClient(client.nombre(), client.apellido());
            VentaResponseDto ventaDto = new VentaResponseDto(venta.getId(), fullNameClient, venta.getDate().toString(), venta.getTotal(), venta.getDetails());
            response.add(ventaDto);
        }
        return response;
    }

    private String getFullNameClient(String nombre, String apellido) {
        return nombre + " " + apellido;
    }

    private Double calculateSubtotal(Double precio, Integer quantity) {
        double subTotal;
        subTotal = precio * quantity;
        total = total + subTotal;
        return subTotal;
    }

    private ClienteResponseDTO validateClientId(Integer id) {
        if (id == null || id <= 0) {
            throw new VentaException(VentaException.CLIENT_ID_INVALID);
        }

        ClienteResponseDTO client = clientFeign.getClient(id.longValue());
        return client;
    }

    private void validateDetails(List<DetalleVenta> details) {
        Set<Integer> productIds = new HashSet<>();

        if (details == null || details.isEmpty()) {
            throw new VentaException(VentaException.DETAILS_INVALID);
        }

        for (DetalleVenta detail : details) {
            validateQuantity(detail.getQuantity());

            ProductoDtoResponse product = productClient.getProduct(detail.getProductId());
            if (!productIds.add(detail.getProductId())) {
                throw new VentaException(VentaException.PRODUCT_REPEATED);
            }

            validateProduct(product);
            validateQuantityGreaterThanStock(detail.getQuantity(), product.stock());

            detail.setUnitPrice(product.precio());
            detail.setSubTotal(calculateSubtotal(product.precio(), detail.getQuantity()));
        }
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
