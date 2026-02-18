package com.example.crud.dto;

import com.example.crud.model.enums.TipoTarjeta;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TarjetaDTO {
    private Long id;
    private String nombre;
    private TipoTarjeta tipo;
    private BigDecimal saldo;
    private Boolean activa;
    private LocalDateTime fechaCreacion;

    public TarjetaDTO() {}

    public TarjetaDTO(Long id, String nombre, TipoTarjeta tipo, BigDecimal saldo, Boolean activa, LocalDateTime fechaCreacion) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.saldo = saldo;
        this.activa = activa;
        this.fechaCreacion = fechaCreacion;
    }
}
