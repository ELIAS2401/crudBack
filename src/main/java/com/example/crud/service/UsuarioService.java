package com.example.crud.service;

import com.example.crud.model.Usuario;

public interface UsuarioService {
    Usuario login(String mail, String password);
    Usuario buscarUsuario(String nombre);
}
