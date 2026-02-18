package com.example.crud.service;

import com.example.crud.model.Tarjeta;
import com.example.crud.model.Transaccion;

import java.util.List;

public interface TransaccionService {
    List<Transaccion> listarPorTarjetaOrigen(Tarjeta tarjetaOrigen);
}
