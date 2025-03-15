package backend.mcsvventas.controllers;

import backend.mcsvventas.models.dtos.request.VentaRequestDto;
import backend.mcsvventas.models.dtos.response.VentaResponseDto;
import backend.mcsvventas.services.VentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {
    private final VentaService service;

    public VentaController(VentaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<VentaResponseDto> createVenta(@RequestBody VentaRequestDto requestDto) {
        return new ResponseEntity<>(service.add(requestDto), HttpStatus.OK);
    }
}
