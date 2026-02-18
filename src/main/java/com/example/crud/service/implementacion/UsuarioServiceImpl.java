package com.example.crud.service.implementacion;
import com.example.crud.exception.DatosLoginIncorrectosException;
import com.example.crud.model.Usuario;
import com.example.crud.repository.UsuarioRepository;
import com.example.crud.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario login(String mail, String password) {
        Usuario usuarioEncontrado = buscarUsuario(mail);
        if (usuarioEncontrado == null || !usuarioEncontrado.getPassword().equals(password)) {
            throw new DatosLoginIncorrectosException("Usuario o contraseña incorrectos");
        }
        return usuarioEncontrado;
    }

    @Override
    public Usuario buscarUsuario(String mail) {
        return this.usuarioRepository.findByEmail(mail);
    }

}
