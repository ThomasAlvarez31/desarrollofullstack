package com.perfulandia.usuarioservice.service;

import com.perfulandia.usuarioservice.model.Usuario;
import com.perfulandia.usuarioservice.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // ✅ Activa Mockito para JUnit 5
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repo;

    @InjectMocks
    private UsuarioService service;

    @Test
    void testBuscarUsuario() {
        Usuario usuario = new Usuario(1L, "Ana", "ana@mail.com", "Cliente");
        when(repo.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario resultado = service.buscar(1L);

        assertEquals("Ana", resultado.getNombre());
        verify(repo).findById(1L);
    }
}
