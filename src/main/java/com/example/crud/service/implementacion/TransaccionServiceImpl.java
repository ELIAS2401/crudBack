package com.example.crud.service.implementacion;
import com.example.crud.model.Tarjeta;
import com.example.crud.model.Transaccion;
import com.example.crud.repository.TarjetaRepository;
import com.example.crud.repository.TransaccionRepository;
import com.example.crud.service.TransaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class TransaccionServiceImpl implements TransaccionService {
    private final TransaccionRepository transaccionRepository;
    private final TarjetaRepository tarjetaRepository;

    @Autowired
    public TransaccionServiceImpl(TransaccionRepository transaccionRepository, TarjetaRepository tarjetaRepository) {
        this.transaccionRepository = transaccionRepository;
        this.tarjetaRepository = tarjetaRepository;
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

    @Override
    public Transaccion transferir(Tarjeta origen, Tarjeta destino, double monto) {
        BigDecimal montoBD = BigDecimal.valueOf(monto);

        if (origen.getSaldo().compareTo(montoBD) < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }

        // Actualizar saldos
        origen.setSaldo(origen.getSaldo().subtract(montoBD));
        destino.setSaldo(destino.getSaldo().add(montoBD));

        tarjetaRepository.save(origen);
        tarjetaRepository.save(destino);

        // Crear transacción
        Transaccion transaccion = new Transaccion();
        transaccion.setTarjetaOrigen(origen);
        transaccion.setTarjetaDestino(destino);
        transaccion.setMonto(montoBD);
        transaccion.setFecha(LocalDateTime.now());

        return transaccionRepository.save(transaccion);
    }

    @Override
    public List<Transaccion> listarTodas() {
        return transaccionRepository.findAll();
    }
}
