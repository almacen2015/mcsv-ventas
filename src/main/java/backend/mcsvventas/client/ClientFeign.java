package backend.mcsvventas.client;

import backend.mcsvventas.models.dtos.response.ClienteResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "mcsv-clientes", url = "http://localhost:9000/api/clientes")
public interface ClientFeign {
    @GetMapping("/{id}")
    ClienteResponseDTO getClient(@PathVariable Long id);
}
