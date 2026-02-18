package com.example.crud.repository;

import com.example.crud.model.Tarjeta;
import com.example.crud.model.Transaccion;
import com.example.crud.model.enums.EstadoTransaccion;
import com.example.crud.model.enums.TipoTransaccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
    List<Transaccion> findByTarjetaOrigenIdOrTarjetaDestinoId(Long origenId, Long destinoId);
}
