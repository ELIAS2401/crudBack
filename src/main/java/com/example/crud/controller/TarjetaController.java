package com.example.crud.controller;

import com.example.crud.model.Tarjeta;
import com.example.crud.model.Usuario;
import com.example.crud.repository.UsuarioRepository;
import com.example.crud.service.TarjetaService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class TarjetaController {
    private final TarjetaService tarjetaService;
    private final UsuarioRepository usuarioRepository;
    private final String USER_EMAIL = "elias@gmail.com";

    public TarjetaController(TarjetaService tarjetaService, UsuarioRepository usuarioRepository) {
        this.tarjetaService = tarjetaService;
        this.usuarioRepository = usuarioRepository;
    }

    private Usuario getUsuarioActual() {
        return usuarioRepository.findByEmail(USER_EMAIL);
    }

    @GetMapping("/tarjetas")
    public List<Tarjeta> listarTarjetas() {
        Usuario usuario = getUsuarioActual();
        return tarjetaService.listarPorUsuario(usuario);
    }

    @PostMapping
    public Tarjeta crearTarjeta(@RequestBody Tarjeta tarjeta) {
        tarjeta.setUsuario(getUsuarioActual());
        tarjeta.setId(null);
        tarjeta.setFechaCreacion(LocalDateTime.now());
        return tarjetaService.crear(tarjeta);
    }

    @GetMapping("/{id}")
    public Tarjeta obtenerTarjeta(@PathVariable Long id) {
        Optional<Tarjeta> tarjetaOpt = tarjetaService.obtenerPorId(id);
        if (tarjetaOpt.isPresent() && tarjetaOpt.get().getUsuario().getEmail().equals(USER_EMAIL)) {
            return tarjetaOpt.get();
        } else {
            throw new RuntimeException("Tarjeta no encontrada o no pertenece al usuario");
        }
    }

    @PutMapping("/{id}")
    public Tarjeta editarTarjeta(@PathVariable Long id, @RequestBody Tarjeta tarjeta) {
        tarjeta.setId(id);
        tarjeta.setUsuario(getUsuarioActual());
        return tarjetaService.crear(tarjeta);
    }

    @DeleteMapping("/{id}")
    public String eliminarTarjeta(@PathVariable Long id) {
        tarjetaService.eliminar(id);
        return "Tarjeta eliminada";
    }
}
