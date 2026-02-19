package com.example.crud.dto;

import com.example.crud.model.enums.EstadoTransaccion;
import com.example.crud.model.enums.TipoTransaccion;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransaccionDTO {
    private Long id;
    private String origenNombre;
    private String destinoNombre;
    private BigDecimal monto;
    private EstadoTransaccion estado;
    private TipoTransaccion tipo;
    private LocalDateTime fecha;
    // getters y setters

    public TransaccionDTO() {
    }

    public TransaccionDTO(Long id, String origenNombre, String destinoNombre, BigDecimal monto, EstadoTransaccion estado, TipoTransaccion tipo, LocalDateTime fecha) {
        this.id = id;
        this.origenNombre = origenNombre;
        this.destinoNombre = destinoNombre;
        this.monto = monto;
        this.estado = estado;
        this.tipo = tipo;
        this.fecha = fecha;
    }
}

