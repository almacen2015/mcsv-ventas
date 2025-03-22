package backend.mcsvventas.client;

import backend.mcsvventas.models.dtos.request.MovimientoDtoRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "mcsv-movimientos", url = "http://localhost:9000/api/movimiento")
public interface MovementClient {
    @PostMapping
    MovimientoDtoRequest createMovimientoDto(MovimientoDtoRequest movimientoDtoRequest);
}
