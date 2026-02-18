package com.example.crud.repository;

import com.example.crud.model.Tarjeta;
import com.example.crud.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
