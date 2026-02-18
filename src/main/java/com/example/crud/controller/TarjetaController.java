package com.example.crud.controller;

import com.example.crud.dto.TarjetaDTO;
import com.example.crud.model.Tarjeta;
import com.example.crud.model.Usuario;
import com.example.crud.repository.UsuarioRepository;
import com.example.crud.service.TarjetaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tarjetas")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class TarjetaController {
    private final TarjetaService tarjetaService;
    private final UsuarioRepository usuarioRepository;
    private final String USER_EMAIL = "elias@gmail.com";
    private final HttpSession session;

    public TarjetaController(TarjetaService tarjetaService, UsuarioRepository usuarioRepository, HttpSession session) {
        this.tarjetaService = tarjetaService;
        this.usuarioRepository = usuarioRepository;
        this.session = session;
    }

    private Usuario getUsuarioActual() {
        Usuario usuario = (Usuario) session.getAttribute("usuarioActual");
        System.out.println("Usuario en sesión: " + usuario); // 🔹 DEBUG
        if (usuario == null) {
            throw new RuntimeException("Usuario no logueado");
        }
        return usuario;
    }

    @GetMapping
    public List<TarjetaDTO> listarTarjetas() {
        Usuario usuario = getUsuarioActual();
        return tarjetaService.listarPorUsuario(usuario).stream()
                .map(this::convertirADTO)
                .toList();
    }

    @PostMapping
    public TarjetaDTO crearTarjeta(@RequestBody TarjetaDTO tarjetaDTO) {
        Usuario usuario = getUsuarioActual();
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setNombre(tarjetaDTO.getNombre());
        tarjeta.setTipo(tarjetaDTO.getTipo());
        tarjeta.setSaldo(tarjetaDTO.getSaldo() != null ? tarjetaDTO.getSaldo() : BigDecimal.ZERO);
        tarjeta.setActiva(true);
        tarjeta.setFechaCreacion(LocalDateTime.now());
        tarjeta.setUsuario(usuario);

        Tarjeta tarjetaCreada = tarjetaService.crear(tarjeta);
        return convertirADTO(tarjetaCreada);
    }

    @GetMapping("/{id}")
    public TarjetaDTO obtenerTarjeta(@PathVariable Long id) {
        Usuario usuario = getUsuarioActual();
        Tarjeta tarjeta = tarjetaService.obtenerPorId(id)
                .filter(t -> t.getUsuario().getId().equals(usuario.getId()))
                .orElseThrow(() -> new RuntimeException("Tarjeta no encontrada o no pertenece al usuario"));

        return convertirADTO(tarjeta);
    }

    @PutMapping("/{id}")
    public TarjetaDTO editarTarjeta(@PathVariable Long id, @RequestBody TarjetaDTO tarjetaDTO) {
        Usuario usuario = getUsuarioActual();
        Tarjeta tarjeta = tarjetaService.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Tarjeta no encontrada"));

        tarjeta.setNombre(tarjetaDTO.getNombre());
        tarjeta.setTipo(tarjetaDTO.getTipo());
        tarjeta.setSaldo(tarjetaDTO.getSaldo());
        tarjeta.setActiva(tarjetaDTO.getActiva());
        tarjeta.setFechaCreacion(tarjetaDTO.getFechaCreacion() != null ? tarjetaDTO.getFechaCreacion() : LocalDateTime.now());
        tarjeta.setUsuario(usuario);

        return convertirADTO(tarjetaService.crear(tarjeta));
    }

    // Convertidor Tarjeta -> DTO
    private TarjetaDTO convertirADTO(Tarjeta tarjeta) {
        return new TarjetaDTO(
                tarjeta.getId(),
                tarjeta.getNombre(),
                tarjeta.getTipo(),
                tarjeta.getSaldo(),
                tarjeta.getActiva(),
                tarjeta.getFechaCreacion()
        );
    }
    @DeleteMapping("/{id}")
    public String eliminarTarjeta(@PathVariable Long id) {
        tarjetaService.eliminar(id);
        return "Tarjeta eliminada";
    }
}
