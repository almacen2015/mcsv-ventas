package backend.mcsvventas.client;

import backend.mcsvventas.models.dtos.response.ProductoDtoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "mcsv-productos", url = "http://localhost:9000/api/producto")
public interface ProductClient {
    @GetMapping("/{id}")
    ProductoDtoResponse getProduct(@PathVariable Integer id);
}
