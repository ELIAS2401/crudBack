package com.example.crud.controller;

import com.example.crud.model.Tarjeta;
import com.example.crud.model.Transaccion;
import com.example.crud.repository.TarjetaRepository;
import com.example.crud.service.TransaccionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transacciones")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class TransaccionController {
    private final TransaccionService transaccionService;
    private final TarjetaRepository tarjetaRepository;

    // Para pruebas podemos hardcodear tarjetas
    private final Long TARJETA_ORIGEN_ID = 1L;
    private final Long TARJETA_DESTINO_ID = 2L;

    public TransaccionController(TransaccionService transaccionService, TarjetaRepository tarjetaRepository) {
        this.transaccionService = transaccionService;
        this.tarjetaRepository = tarjetaRepository;
    }

    private Tarjeta getTarjetaOrigen() {
        return tarjetaRepository.findById(TARJETA_ORIGEN_ID).orElseThrow(() -> new RuntimeException("Tarjeta origen no encontrada"));
    }

    private Tarjeta getTarjetaDestino() {
        return tarjetaRepository.findById(TARJETA_DESTINO_ID).orElseThrow(() -> new RuntimeException("Tarjeta destino no encontrada"));
    }

    // Listar todas las transacciones
    @GetMapping
    public ResponseEntity<Object> listarTodas() {
        try {
            Tarjeta origen = getTarjetaOrigen();
            List<Transaccion> transacciones = transaccionService.listarPorTarjetaOrigen(origen);

            if (transacciones.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(Map.of("mensaje", "No hay transacciones"));
            }

            return ResponseEntity.ok(transacciones);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    // Obtener por id
    @GetMapping("/{id}")
    public Transaccion obtenerPorId(@PathVariable Long id) {
        return transaccionService.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));
    }

    // Crear nueva transacción
    @PostMapping
    public Transaccion crear(@RequestBody Transaccion transaccion) {
        transaccion.setTarjetaOrigen(getTarjetaOrigen());
        transaccion.setTarjetaDestino(getTarjetaDestino());
        transaccion.setFecha(LocalDateTime.now());
        return transaccionService.crear(transaccion);
    }

    // Editar transacción
    @PutMapping("/{id}")
    public Transaccion editar(@PathVariable Long id, @RequestBody Transaccion transaccion) {
        transaccion.setId(id);
        transaccion.setTarjetaOrigen(getTarjetaOrigen());
        transaccion.setTarjetaDestino(getTarjetaDestino());
        return transaccionService.crear(transaccion);
    }

    // Eliminar transacción
    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {
        transaccionService.eliminar(id);
        return "Transacción eliminada";
    }
}
