package com.example.crud.controller;

import com.example.crud.dto.TransaccionDTO;
import com.example.crud.model.Tarjeta;
import com.example.crud.model.Transaccion;
import com.example.crud.model.enums.EstadoTransaccion;
import com.example.crud.model.enums.TipoTransaccion;
import com.example.crud.repository.TarjetaRepository;
import com.example.crud.service.TransaccionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transacciones")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class TransaccionController {
    private final TransaccionService transaccionService;
    private final TarjetaRepository tarjetaRepository;

    public TransaccionController(TransaccionService transaccionService, TarjetaRepository tarjetaRepository) {
        this.transaccionService = transaccionService;
        this.tarjetaRepository = tarjetaRepository;
    }

    @GetMapping
    public ResponseEntity<?> listarTodas() {
        try {
            List<TransaccionDTO> dto = transaccionService.listarTodas().stream()
                    .map(t -> new TransaccionDTO(
                            t.getId(),
                            t.getTarjetaOrigen().getNombre(),
                            t.getTarjetaDestino().getNombre(),
                            t.getMonto(),
                            t.getEstado(),
                            t.getTipo(),
                            t.getFecha()
                    )).collect(Collectors.toList());
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // Transferencia
    @PostMapping("/transferir")
    public ResponseEntity<?> transferir(@RequestBody Map<String, Object> body) {
        try {
            Long origenId = Long.valueOf(body.get("origenId").toString());
            Long destinoId = Long.valueOf(body.get("destino").toString());
            Double monto = Double.valueOf(body.get("monto").toString());

            Tarjeta origen = tarjetaRepository.findById(origenId)
                    .orElseThrow(() -> new RuntimeException("Tarjeta origen no encontrada"));
            Tarjeta destino = tarjetaRepository.findById(destinoId)
                    .orElseThrow(() -> new RuntimeException("Tarjeta destino no encontrada"));

            if (origen.getSaldo().compareTo(BigDecimal.valueOf(monto)) < 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "Saldo insuficiente"));
            }

            origen.setSaldo(origen.getSaldo().subtract(BigDecimal.valueOf(monto)));
            destino.setSaldo(destino.getSaldo().add(BigDecimal.valueOf(monto)));

            tarjetaRepository.save(origen);
            tarjetaRepository.save(destino);

            // Guardar la transacción
            Transaccion transaccion = new Transaccion();
            transaccion.setTarjetaOrigen(origen);
            transaccion.setTarjetaDestino(destino);
            transaccion.setMonto(BigDecimal.valueOf(monto));
            transaccion.setFecha(LocalDateTime.now());

            // Inicializamos campos obligatorios
            transaccion.setEstado(EstadoTransaccion.PENDIENTE); // o COMPLETADA si ya pasó
            transaccion.setTipo(TipoTransaccion.TRANSFERENCIA);

            transaccionService.crear(transaccion);

            return ResponseEntity.ok(Map.of(
                    "mensaje", "Transferencia realizada",
                    "transaccionId", transaccion.getId()
            ));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
