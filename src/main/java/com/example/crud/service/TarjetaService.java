package com.example.crud.service;

import com.example.crud.model.Tarjeta;
import com.example.crud.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface TarjetaService {
    List<Tarjeta> listarPorUsuario(Usuario usuario);

    Tarjeta crear(Tarjeta tarjeta);

    Optional<Tarjeta> obtenerPorId(Long id);

    void eliminar(Long id);
}
