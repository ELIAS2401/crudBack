package com.example.crud.service;

import com.example.crud.model.Tarjeta;
import com.example.crud.model.Transaccion;

import java.util.List;
import java.util.Optional;

public interface TransaccionService {
    List<Transaccion> listarPorTarjetaOrigen(Tarjeta tarjeta);

    List<Transaccion> listarPorTarjetaDestino(Tarjeta tarjeta);

    Transaccion crear(Transaccion transaccion);

    Optional<Transaccion> obtenerPorId(Long id);

    void eliminar(Long id);
}
