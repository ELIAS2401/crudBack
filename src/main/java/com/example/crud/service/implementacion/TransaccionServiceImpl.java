package com.example.crud.service.implementacion;
import com.example.crud.model.Tarjeta;
import com.example.crud.model.Transaccion;
import com.example.crud.repository.TransaccionRepository;
import com.example.crud.service.TransaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransaccionServiceImpl implements TransaccionService {
    private final TransaccionRepository transaccionRepository;

    @Autowired
    public TransaccionServiceImpl(TransaccionRepository transaccionRepository) {
        this.transaccionRepository = transaccionRepository;
    }

    @Override
    public List<Transaccion> listarPorTarjetaOrigen(Tarjeta tarjeta) {
        return transaccionRepository.findByTarjetaOrigen(tarjeta);
    }

    @Override
    public List<Transaccion> listarPorTarjetaDestino(Tarjeta tarjeta) {
        return transaccionRepository.findByTarjetaDestino(tarjeta);
    }

    @Override
    public Transaccion crear(Transaccion transaccion) {
        return transaccionRepository.save(transaccion);
    }

    @Override
    public Optional<Transaccion> obtenerPorId(Long id) {
        return transaccionRepository.findById(id);
    }

    @Override
    public void eliminar(Long id) {
        transaccionRepository.deleteById(id);
    }
}
