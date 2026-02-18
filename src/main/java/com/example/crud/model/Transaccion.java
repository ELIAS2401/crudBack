package com.example.crud.model;

import com.example.crud.model.enums.EstadoTransaccion;
import com.example.crud.model.enums.TipoTransaccion;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "transacciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal monto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTransaccion tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoTransaccion estado;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tarjeta_origen_id", nullable = false)
    private Tarjeta tarjetaOrigen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tarjeta_destino_id", nullable = false)
    private Tarjeta tarjetaDestino;
}
