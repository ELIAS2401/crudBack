package com.example.crud.repository;

import com.example.crud.model.Tarjeta;
import com.example.crud.model.Transaccion;
import com.example.crud.model.Usuario;
import com.example.crud.model.enums.TipoTarjeta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TarjetaRepository extends JpaRepository<Tarjeta, Long> {
    List<Tarjeta> findByUsuarioId(Long usuarioId);
}
