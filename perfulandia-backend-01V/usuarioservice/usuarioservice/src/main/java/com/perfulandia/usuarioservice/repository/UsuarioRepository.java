package com.perfulandia.usuarioservice.repository;

import com.perfulandia.usuarioservice.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
//findAll()
//findById(id)
//save()
//delete(id)
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
