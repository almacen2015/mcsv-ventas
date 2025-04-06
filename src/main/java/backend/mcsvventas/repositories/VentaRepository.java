package backend.mcsvventas.repositories;

import backend.mcsvventas.models.documents.Venta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VentaRepository extends MongoRepository<Venta, String> {
    Page<Venta> findByClientId(Integer clientId, Pageable pageable);
}
