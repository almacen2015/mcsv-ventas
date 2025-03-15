package backend.mcsvventas.models.documents;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleVenta {
    private Long productId;
    private Integer quantity;
    private Double unitPrice;
    private Double subTotal;
}
