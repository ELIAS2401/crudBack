package com.example.crud.service.implementacion;

import com.example.crud.model.Tarjeta;
import com.example.crud.model.Usuario;
import com.example.crud.repository.TarjetaRepository;
import com.example.crud.service.TarjetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarjetaServiceImpl implements TarjetaService {

    private final TarjetaRepository tarjetaRepository;

    @Autowired
    public TarjetaServiceImpl(TarjetaRepository tarjetaRepository){
        this.tarjetaRepository = tarjetaRepository;
    }

    @Override
    public List<Tarjeta> listarPorUsuario(Usuario usuario) {
        return this.tarjetaRepository.findByUsuarioId(usuario.getId());
    }

    @Override
    public Tarjeta crear(Tarjeta tarjeta) {
        return tarjetaRepository.save(tarjeta);
    }

    @Override
    public Optional<Tarjeta> obtenerPorId(Long id) {
        return tarjetaRepository.findById(id);
    }

    @Override
    public void eliminar(Long id) {
        tarjetaRepository.deleteById(id);
    }
}
